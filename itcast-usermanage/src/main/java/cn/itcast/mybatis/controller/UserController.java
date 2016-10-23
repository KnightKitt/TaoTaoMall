package cn.itcast.mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.mybatis.bean.EasyUIResult;
import cn.itcast.mybatis.service.NewUserService;

@RequestMapping("user")
@Controller
public class UserController {
    
//    @Autowired
//    private UserService userService;
    @Autowired
    private NewUserService userService;

    //使用通用的页面跳转Controller，不再用这个
//    @RequestMapping(value = "users", method = RequestMethod.GET)//method表示限制请求方式只能是GET方式
//    public String toUsers(){
//        return "users";
//    }
    
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public EasyUIResult queryUserList(@RequestParam(value = "page", defaultValue = "1")Integer page, 
            @RequestParam(value = "rows", defaultValue = "5")Integer rows){
        return this.userService.queryUserList(page, rows);
    }
    
}
