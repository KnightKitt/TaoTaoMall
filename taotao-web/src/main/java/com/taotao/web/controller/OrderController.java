package com.taotao.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.web.bean.Item;
import com.taotao.web.bean.Order;
import com.taotao.web.bean.User;
import com.taotao.web.interceptors.UserLoginHandlerInterceptor;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.OrderService;
import com.taotao.web.service.UserService;
import com.taotao.web.threadlocal.UserThreadLocal;


@RequestMapping("order")
@Controller
public class OrderController {
    
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;

    /**
     * 去订单确认页
     * 
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ModelAndView toOrder(@PathVariable("itemId")Long itemId){
        ModelAndView mv = new ModelAndView("order");
        //设置模型数据
        Item item = this.itemService.queryById(itemId);
        mv.addObject("item", item);
        return mv;
    }
    
    /**
     * 提交订单
     * 
     * @return
     */
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submit(Order order/*,
            @CookieValue(UserLoginHandlerInterceptor.COOKIE_NAME)String token*/){//改用ThreadLocal的方式获取user的信息
        Map<String, Object> result = new HashMap<String, Object>();
        
        //填充当前登录用户的信息
//        User user = this.userService.queryByToken(token);//注：由于有登录拦截器，因此，如果user为空的话就会跳转到登录页面 ，
                                                         //所以不需要判断了
        
        User user = UserThreadLocal.get();
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());
        
        String orderId = this.orderService.submit(order);
        if (StringUtils.isEmpty(orderId)) {
            result.put("status", "500");
        }else {
            result.put("status", "200");
            result.put("data", orderId);
        }
        return result;
    }
    
    /**
     * 成功页
     * 
     * @param orderId
     * @return
     */
    @RequestMapping(value = "success", method = RequestMethod.GET)
    public ModelAndView success(@RequestParam("id")String orderId){
        ModelAndView mv = new ModelAndView("success");
        //订单数据
        Order order = this.orderService.queryByOrderId(orderId);
        mv.addObject("order", order);
        //送货时间，当前时间向后推2天，格式：08月18日（joda-time的时间处理工具包的使用）
        mv.addObject("", new DateTime().plusDays(2).toString("MM月dd日"));
        return mv;
    }
}
