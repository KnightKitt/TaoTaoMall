package com.taotao.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.Content;

public interface ContentMapper extends Mapper<Content>{

    List<Content> queryContentList(@Param("categoryId")Long categoryId);//此处注解的作用与配置文件中的参数占位符对应，指明了参数的名字

}
