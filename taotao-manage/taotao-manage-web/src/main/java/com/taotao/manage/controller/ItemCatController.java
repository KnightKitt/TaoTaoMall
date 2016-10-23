package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

/**
 * 
 * @author iMichael
 *
 */
@RequestMapping("item/cat")
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId){//?后面的参数使用@RequestParam接收
        try {
//            List<ItemCat> itemCatList = this.itemCatService.queryItemCatList(parentId);
            ItemCat itemCat = new ItemCat();
            itemCat.setParentId(parentId);
            List<ItemCat> itemCatList = this.itemCatService.queryByWhere(itemCat);
            if (itemCatList == null || itemCatList.size() == 0) {
                //资源不存在，返回404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemCatList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    /**
     * 按照前台系统结构返回商品类目的json数据
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ResponseEntity<ItemCatResult> queryItemCatAll(){
        return ResponseEntity.ok(this.itemCatService.queryAllToTree());
    }
    
//    @RequestMapping(value = "all", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
//    public ResponseEntity<String> queryItemCatAll(){
//        try {
//            String json = MAPPER.writeValueAsString(this.itemCatService.queryAllToTree());
//            return ResponseEntity.ok("category.getDataService(" + json + ")");
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }
}
