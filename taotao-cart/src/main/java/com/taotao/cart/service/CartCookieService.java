package com.taotao.cart.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.pojo.Item;
import com.taotao.common.utils.CookieUtils;

@Service
public class CartCookieService {
    
    @Autowired
    private ItemService itemService;
    
    private static final String COOKIE_NAME = "TT_CART";

    private static final Integer COOKIE_TIME = 60 * 60 * 24 * 30;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void addItemToCart(Long itemId, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String jsonData = CookieUtils.getCookieValue(request, COOKIE_NAME, true);
//        List<Cart> carts = null;
//        if (StringUtils.isEmpty(jsonData)) {
//            carts = new ArrayList<>();
//        }else {
//            //反序列化
//            carts = MAPPER.readValue(jsonData, 
//                    MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
//        }
        
        List<Cart> carts =  this.queryCartList(request);
        
        Cart cart = null;
        for (Cart c : carts) {
            if (itemId == c.getItemId()) {
                //该商品已经存在购物车中
                cart = c;
                break;
            }
        }
        
        if (null == cart) {
            //不存在
            cart = new Cart();
            cart.setCreated(new Date());
            cart.setUpdated(cart.getCreated());
            //商品的基本数据，需要通过后台系统查询
            Item item = this.itemService.queryById(itemId);
            if (item != null) {
                cart.setItemId(itemId);
                cart.setItemTitle(item.getTitle());
                cart.setItemPrice(item.getPrice());
                cart.setItemImage(StringUtils.split(item.getImage(), ',')[0]);//取一张图片
                cart.setNum(1);//TODO
                //
                carts.add(cart);
            }
        }else {
            //存在
            cart.setNum(cart.getNum() + 1);
            cart.setUpdated(new Date());
        }
        
        //将购物车中的列表数据写入到Cookie中
        CookieUtils.setCookie(request, response, COOKIE_NAME, MAPPER.writeValueAsString(carts), COOKIE_TIME, true);
    }

    public List<Cart> queryCartList(HttpServletRequest request) throws Exception {
        String jsonData = CookieUtils.getCookieValue(request, COOKIE_NAME, true);
        List<Cart> carts = null;
        if (StringUtils.isEmpty(jsonData)) {
            carts = new ArrayList<>();
        }else {
            carts = MAPPER.readValue(jsonData, MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
            //TODO 排序
        }
        return carts;
    }

    
    public void updateNum(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Cart> carts =  this.queryCartList(request);
        Cart cart = null;
        for (Cart c : carts) {
            if (itemId == c.getItemId()) {
                //该商品已经存在购物车中
                cart = c;
                break;
            }
        }
        
        if (null == cart) {
            //不存在,说明参数非法，直接返回
            return ;
        }else {
            //存在
            cart.setNum(num);
            cart.setUpdated(new Date());
        }
        
        //将购物车中的列表数据写入到Cookie中
        CookieUtils.setCookie(request, response, COOKIE_NAME, MAPPER.writeValueAsString(carts), COOKIE_TIME, true);
    }

    public void delete(Long itemId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Cart> carts =  this.queryCartList(request);
        Cart cart = null;
        for (Cart c : carts) {
            if (itemId == c.getItemId()) {
                //该商品已经存在购物车中
                cart = c;
                break;
            }
        }
        
        if (null == cart) {
            //不存在,说明参数非法，直接返回
            return ;
        }else {
            //存在
            carts.remove(cart);
        }
        
        //将购物车中的列表数据写入到Cookie中
        CookieUtils.setCookie(request, response, COOKIE_NAME, MAPPER.writeValueAsString(carts), COOKIE_TIME, true);
    }

}
