package com.taotao.cart.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.pojo.Item;
import com.taotao.common.service.ApiService;

@Service
public class ItemService {

    @Autowired
    private ApiService apiService;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Value("${TAOTAO_MANAGE_URL}")
    private String TAOTAO_MANAGE_URL;
    
    public Item queryById(Long itemId){
        String url = TAOTAO_MANAGE_URL + "/rest/api/item/" + itemId;
        try {
            String jsonData = apiService.doGet(url);
            if (StringUtils.isNotEmpty(jsonData)) {
                return MAPPER.readValue(jsonData, Item.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
