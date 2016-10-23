package cn.itcast.mybatis.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.itcast.mybatis.bean.EasyUIResult;
import cn.itcast.mybatis.mapper.UserMapper;
import cn.itcast.mybatis.pojo.User;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public EasyUIResult queryUserList(Integer page, Integer rows) {
        //设置分页参数
        PageHelper.startPage(page, rows);
        List<User> users = this.userMapper.queryUserList();
        System.out.println("users.size=" + users.size());
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
    }
}
