package com.taotao.manage.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.service.ApiService;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;

@Service
public class ItemService extends BaseService<Item>{
    
    @Autowired
    private ItemDescService itemDescService;
    
    @Autowired
    private ItemParamItemService itemParamItemService;
    
    @Autowired
    private ApiService apiService;
    
    @Value("${TAOTAO_WEB_URL}")
    private String TAOTAO_WEB_URL;

    public void save(Item item, String desc, String itemParams) {
        
        //注意：事务的传播性？如何测试事务的传播性★★★★查看Spring的日志
        //Service中不能try/catch，否则上网不能回滚
        //新增商品的数据
        item.setStatus(1);//初始状态
        item.setId(null);//强制ID为null，考虑安全性
        super.save(item);
        
        //新增商品描述信息
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        this.itemDescService.save(itemDesc);
        
        if (StringUtils.isNotEmpty(itemParams)) {
            ItemParamItem itemParamItem = new ItemParamItem();
            itemParamItem.setItemId(item.getId());
            itemParamItem.setParamData(itemParams);
            this.itemParamItemService.save(itemParamItem);
        }
    }

    public PageInfo<Item> queryItemList(Integer page, Integer rows) {
        Example example = new Example(Item.class);
        example.setOrderByClause("updated desc");
        example.createCriteria().andNotEqualTo("status", 3);
        return super.queryPageListByExample(example, page, rows);
    }

    /**
     * 实现商品的逻辑删除
     * @param ids
     */
    public void updateByIds(List<Object> ids) {
        Item item = new Item();
        item.setStatus(3);//更改状态为3，表明商品已被删除
        Example example = new Example(Item.class);
        example.createCriteria().andIn("id", ids);
        super.getMapper().updateByExampleSelective(item, example);
    }

    public void updateItem(Item item, String desc, ItemParamItem itemParamItem) {
        //强制设置不可更新的内容为null，这样就不会更新这些字段
        item.setStatus(null);
        item.setCreated(null);
        super.updateSelective(item);
        //更新描述信息
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        this.itemDescService.updateSelective(itemDesc);
        //更新商品规格参数
        if (null != itemParamItem) {
            this.itemParamItemService.updateSelective(itemParamItem);
        }
        
        try {
            //通知其他系统该商品已经更新
            String url = TAOTAO_WEB_URL + "/item/cache/" + item.getId() + ".html";
            this.apiService.doPost(url, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//    @Autowired
//    private ItemMapper itemMapper;
    

//    @Override
//    public Mapper<Item> getMapper() {
//        return itemMapper;
//    }

}
