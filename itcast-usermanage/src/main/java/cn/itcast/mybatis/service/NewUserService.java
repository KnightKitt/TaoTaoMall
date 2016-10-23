package cn.itcast.mybatis.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.mybatis.bean.EasyUIResult;
import cn.itcast.mybatis.mapper.NewUserMapper;
import cn.itcast.mybatis.pojo.User;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class NewUserService {

    @Resource
    private NewUserMapper newUserMapper;

    public EasyUIResult queryUserList(Integer page, Integer rows) {
        //设置分页参数
        PageHelper.startPage(page, rows);
        Example example = new Example(User.class);
        example.setOrderByClause("created desc");
        List<User> users = this.newUserMapper.selectByExample(example);
        System.out.println("users.size=" + users.size());
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
    }

    public User queryUserById(Long id) {
        return this.newUserMapper.selectByPrimaryKey(id);
    }

    public void saveUser(User user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        this.newUserMapper.insertSelective(user);
    }

    public void updateUser(User user) {
        this.newUserMapper.updateByPrimaryKeySelective(user);
    }

    public void deleteUserById(Long id) {
       this.newUserMapper.deleteByPrimaryKey(id);
    }
}
