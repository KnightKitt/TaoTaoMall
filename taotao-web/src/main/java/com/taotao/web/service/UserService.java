package com.taotao.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.web.bean.User;

@Service
public class UserService {
    
    @Autowired
    private ApiService apiService;//注意：这里不能直接注入RedisService，因为应该把项目当作两个不同的团队来开发的，
                                   //实际开发中两个团队使用的不是同意Redis
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Value("${TAOTAO_SSO_URL}")
    public String TAOTAO_SSO_URL;
    
    public User queryByToken(String token){
        String url = TAOTAO_SSO_URL + "/service/user/" + token;
        try {
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isNotEmpty(jsonData)) {
                return MAPPER.readValue(jsonData, User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
