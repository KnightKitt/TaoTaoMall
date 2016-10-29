package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.web.bean.Item;
import com.taotao.web.service.ItemService;


@RequestMapping("order")
@Controller
public class OrderController {
    
    @Autowired
    private ItemService itemService;

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
}
