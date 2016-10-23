package com.taotao.web.bean;

import org.apache.commons.lang3.StringUtils;

public class Item extends com.taotao.manage.pojo.Item{

    public String[] getImages(){
        //JDK中提供的String类的split()方法，不但支持按字符（串）分隔，还支持按正则表达式分隔，功能强大，但性能会低一些
        //如果只是简单的按字符分隔的话使用工具类，如果是按正则表达式分隔的话使用String的split()方法。
        return StringUtils.split(super.getImage(), ",");
    }
}
