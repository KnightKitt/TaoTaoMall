package com.taotao.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;

public abstract class BaseService<T extends BasePojo> {

        /**
         * 由子类实现该方法，返回具体的Mapper实现类
         * @return
         */
//        public abstract Mapper<T> getMapper();
        @Autowired
        private Mapper<T> mapper;
        
        public Mapper<T> getMapper() {
            return mapper;
        }

        /**
         * 根据主键查询
         * @param id
         * @return
         */
        public T queryById(Long id){
//            return this.getMapper().selectByPrimaryKey(id);
            return this.mapper.selectByPrimaryKey(id);
        }
        
        public List<T> queryAll() {
//            return this.getMapper().select(null);
            return this.mapper.select(null);
        }
        
        public List<T> queryByWhere(T t) {
//            return this.getMapper().select(t);
            return this.mapper.select(t);
        }
        
        public T queryOneByWhere(T t){
//            return this.getMapper().selectOne(t);
            return this.mapper.selectOne(t);
        }
        
        public PageInfo<T> queryPageListByWhere(T t, Integer page, Integer rows){
            PageHelper.startPage(page, rows, true);//设置分页
            List<T> list = this.queryByWhere(t);
            return new PageInfo<>(list);
        }
        
        /**
         * 自定义查询条件的分页程序
         * @param example
         * @param page
         * @param rows
         * @return
         */
        public PageInfo<T> queryPageListByExample(Example example, Integer page, Integer rows){
            PageHelper.startPage(page, rows, true);//设置分页
            List<T> list = this.mapper.selectByExample(example);
            return new PageInfo<>(list);
        }
        

        public Integer save(T t){
            t.setCreated(new Date());
            t.setUpdated(t.getCreated());
//            return this.getMapper().insert(t);
            return this.mapper.insert(t);
        }
        
        public Integer saveSelective(T t){
            t.setCreated(new Date());
            t.setUpdated(t.getCreated());
            return this.mapper.insertSelective(t);
        }

        public Integer update(T t){
            t.setUpdated(new Date());
            return this.mapper.updateByPrimaryKey(t);
        }

        public Integer updateSelective(T t){
            t.setUpdated(new Date());
//            return this.getMapper().updateByPrimaryKeySelective(t);
            return this.mapper.updateByPrimaryKeySelective(t);
        }

        public Integer deleteById(Long id){
//            return this.getMapper().deleteByPrimaryKey(id);
            return this.mapper.deleteByPrimaryKey(id);
        }

        /**
         * 批量删除
         * @param ids
         * @param property
         * @param clazz
         * @return
         */
        public Integer deleteByIds(List<Object> ids, String property, Class<T> clazz){
            Example example = new Example(clazz);
            example.createCriteria().andIn(property, ids);
//            return this.getMapper().deleteByExample(example);
            return this.mapper.deleteByExample(example);
        }
        
}
