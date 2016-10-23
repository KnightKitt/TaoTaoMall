package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;

@RequestMapping("item/param")
@Controller
public class ItemParamController {
    
    @Autowired
    private ItemParamService itemParamService;

    /**
     * 根据类目ID查找规格参数模板
     * @param itemCatId
     * @return
     */
    @RequestMapping(value = "{itemCatId}", method = RequestMethod.GET)
    public ResponseEntity<ItemParam> queryByItemCatId(@PathVariable("itemCatId")Long itemCatId){
        try {
            ItemParam itemParam = new ItemParam();
            itemParam.setItemCatId(itemCatId);
            itemParam = itemParamService.queryOneByWhere(itemParam);
            if (itemParam == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return ResponseEntity.ok(itemParam);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 新增规格参数模板
     * @param itemCatId
     * @return
     */
    @RequestMapping(value = "{itemCatId}", method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId")Long itemCatId,
                                                      @RequestParam("paramData")String paramData){
        try {
            ItemParam itemParam = new ItemParam();
            itemParam.setItemCatId(itemCatId);
            itemParam.setParamData(paramData);
            itemParamService.save(itemParam);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    /**
     * 查询模板列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryList(
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows){
        try {
            PageInfo<ItemParam> pageInfo = this.itemParamService.queryPageListByWhere(null, page, rows);
            return ResponseEntity.ok(new EasyUIResult(pageInfo.getTotal(), pageInfo.getList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
