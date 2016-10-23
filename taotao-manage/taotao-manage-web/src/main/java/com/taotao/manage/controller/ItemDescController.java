package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;

@RequestMapping("item/desc")
@Controller
public class ItemDescController {
    
    @Autowired
    private ItemDescService itemDescService;

    /**
     * 根据商品ID查询商品描述
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ItemDesc> queryItemDescByItemId(@PathVariable("itemId")Long itemId){
        try {
            ItemDesc itemDesc = this.itemDescService.queryById(itemId);
            if (itemDesc == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return ResponseEntity.ok(itemDesc);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(null);
        
    }
}
