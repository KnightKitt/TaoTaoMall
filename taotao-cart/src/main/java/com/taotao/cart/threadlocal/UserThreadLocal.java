package com.taotao.cart.threadlocal;

import com.taotao.cart.pojo.User;

public class UserThreadLocal {
    
    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();
    
    private UserThreadLocal(){};
    
    public static void set(User user){
        LOCAL.set(user);
    }
    
    public static User get(){
        return LOCAL.get();
    }

}
