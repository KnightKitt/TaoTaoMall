package com.taotao.manage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemService;

@RequestMapping("item")
@Controller
public class ItemController {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ItemCatController.class);

    @Autowired
    private ItemService itemService;
    
    @Autowired
    private ItemDescService itemDescService;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc")String desc,
            @RequestParam("itemParams")String itemParams){
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("新增商品！item = {}, desc = {}", item, desc);
            }
            
            //新增商品的数据
            this.itemService.save(item, desc, itemParams);
            
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("新增商品成功！itemId = {}", item.getId());
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("新增商品出错！item = " + item.getId(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
    }
    
    /**
     * 查询列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<EasyUIResult> queryItemList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "30") Integer rows){
        
        try {
            PageInfo<Item> pageInfo = this.itemService.queryItemList(page, rows);
            long total = pageInfo.getTotal();
            List<Item> itemList = pageInfo.getList();
            return ResponseEntity.ok(new EasyUIResult(total, itemList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    /**
     * 实现商品的逻辑删除
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteItemByIds(@RequestParam("ids")List<Object> ids){
        
        try {
            this.itemService.updateByIds(ids);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc") String desc,
            @RequestParam("itemParams")String itemParams,  @RequestParam("itemParamId")Long itemParmId){
        try {
            //封装规格参数数据
            ItemParamItem itemParamItem = null;
            if (null != itemParmId) {
                itemParamItem = new ItemParamItem();
                itemParamItem.setId(itemParmId);
                itemParamItem.setParamData(itemParams);
            }
            
            this.itemService.updateItem(item, desc, itemParamItem);
            return ResponseEntity.status(HttpStatus.OK).build();//可以返回OK,也可以返回NO_CONTENT
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    
}
