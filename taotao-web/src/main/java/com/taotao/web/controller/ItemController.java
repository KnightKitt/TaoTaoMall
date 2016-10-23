package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.web.bean.Item;
import com.taotao.web.service.ItemService;

@RequestMapping("item")
@Controller
public class ItemController {
    
    @Autowired
    private ItemService itemService;
    
    @RequestMapping(value="{itemId}", method=RequestMethod.GET)
    public ModelAndView itemDetail(@PathVariable("itemId")Long itemId){//返回内容既有页面又有模型数据，因此是ModelAndView
        ModelAndView modelAndView = new ModelAndView("item");
        //设置模型数据
        Item item = this.itemService.queryById(itemId);
        modelAndView.addObject("item", item);
        
        //商品描述数据
        ItemDesc itemDesc = this.itemService.queryDescByItemId(itemId);
        modelAndView.addObject("itemDesc", itemDesc);
        
        //商品规格参数数据
        String itemParam = this.itemService.queryItemParamItemByItemId(itemId);
        modelAndView.addObject("itemParam", itemParam);
        return modelAndView;
    }

}
