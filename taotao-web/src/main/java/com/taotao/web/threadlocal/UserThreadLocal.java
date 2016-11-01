package com.taotao.web.threadlocal;

import com.taotao.web.bean.User;

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
