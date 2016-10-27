package com.taotao.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.sso.service.UserService;

@RequestMapping("user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    /**
     * 注册页面
     * 
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(){//返回值类型为String，其代表视图名
        return "register";
    }
    
    /**
     * 检查数据是否可用
     * 
     * @param param
     * @param type
     * @return
     */
    @RequestMapping(value = "{param}/{type}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> check(@PathVariable("param")String param,
            @PathVariable("type")Integer type){
        try {
            Boolean bool = this.userService.check(param, type);
            if (null == bool) {
                //参数有误
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.ok(!bool);//为了兼容前端的逻辑，做取反操作
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
