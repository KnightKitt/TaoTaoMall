package com.taotao.cart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.cart.pojo.Cart;
import com.taotao.cart.pojo.User;
import com.taotao.cart.service.CartCookieService;
import com.taotao.cart.service.CartService;
import com.taotao.cart.threadlocal.UserThreadLocal;

@RequestMapping("cart")
@Controller
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private CartCookieService cartCookieService;

    /**
     * 跳转到购物车页面
     * 
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView cartList(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("cart");
        User user = UserThreadLocal.get();
        
        List<Cart> carts= null;
        if (user == null) {
          //未登录状态
          try {
            carts = this.cartCookieService.queryCartList(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }else {
          //登录状态
          carts = this.cartService.queryCartList();
        }
        mv.addObject("cartList", carts);
        return mv;
    }

    /**
     * 加入商品到购物车
     * 
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public String addItemToCart(@PathVariable("itemId")Long itemId, HttpServletRequest request, HttpServletResponse response){
        User user = UserThreadLocal.get();
        if (user != null) {
            //登录状态
            this.cartService.addItemToCart(itemId);
        }else {
            //未登录状态
            try {
                this.cartCookieService.addItemToCart(itemId, request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        //重定向到购物车列表页面
        return "redirect:/cart/list.html"; //springmvc如果视图名以“redirect:”开头话，就会做重定向
    }
    
    /**
     * 修改购买商品的数量
     * 
     * @param itemId
     * @param num
     * @return
     */
    @RequestMapping(value = "update/num/{itemId}/{num}", method = RequestMethod.POST)
    public ResponseEntity<Void> updateNum(@PathVariable("itemId")Long itemId,
            @PathVariable("num")Integer num, HttpServletRequest request, HttpServletResponse response){//返回值为ResponseEntity<Void>，实际上 和Void等价
        User user = UserThreadLocal.get();
        if (null == user) {
            //未登录状态
            try {
                this.cartCookieService.updateNum(itemId, num, request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //登录状态
            this.cartService.updateNum(itemId, num);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        
    }
    
    /**删除购物车中的商品
     * 
     * @param itemId
     * @return
     */
    @RequestMapping(value = "delete/{itemId}", method = RequestMethod.GET)
    public String delete(@PathVariable("itemId")Long itemId,
            HttpServletRequest request, HttpServletResponse response){
        User user = UserThreadLocal.get();
        if (null == user) {
            //未登录状态
            try {
                this.cartCookieService.delete(itemId, request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //登录状态
            this.cartService.delete(itemId);
        }
        //重定向到购物车列表页面
        return "redirect:/cart/list.html";
    }
}
