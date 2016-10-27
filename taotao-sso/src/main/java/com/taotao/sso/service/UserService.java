package com.taotao.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;

@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;

    public Boolean check(String param, Integer type) {
        User record = new User();
        switch (type) {
        case 1:
            record.setUsername(param);
            break;
        case 2:
            record.setPhone(param);
            break;
        case 3:
            record.setEmail(param);
            break;
        default:
            //参数有误
            return null;
        }
        return this.userMapper.selectOne(record) == null;
    }
    
}
