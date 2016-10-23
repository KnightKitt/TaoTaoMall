package com.taotao.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemParamItemService;

@RequestMapping("api/item/param/item")
@Controller
public class ApiItemParamItemController {

    @Autowired
    private ItemParamItemService itemParamItemService;
    
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ResponseEntity<ItemParamItem> queryItemParamItemByItemId(@PathVariable("itemId")Long itemId){
        ItemParamItem t = new ItemParamItem();
        t.setItemId(itemId);
        try {
            ItemParamItem itemParamItem = this.itemParamItemService.queryOneByWhere(t);
            if (itemParamItem == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
