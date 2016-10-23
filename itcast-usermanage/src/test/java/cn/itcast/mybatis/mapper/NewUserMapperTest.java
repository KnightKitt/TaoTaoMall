package cn.itcast.mybatis.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.mybatis.pojo.User;

public class NewUserMapperTest {
    
    private NewUserMapper newUserMapper;
    
    @Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");
        this.newUserMapper = applicationContext.getBean(NewUserMapper.class);
    }

    @Test
    public void testSelectOne() {
        User record = new User();
        record.setuserName("zhangsan");
        User user = this.newUserMapper.selectOne(record);
        System.out.println(user);
    }

    @Test
    public void testSelect() {
        List<User> users = this.newUserMapper.select(null);
        for (User user : users) {
            System.out.println(user);
        }
        
    }

    @Test
    public void testSelectCount() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectByPrimaryKey() {
        fail("Not yet implemented");
    }

    @Test
    public void testInsert() {
        fail("Not yet implemented");
    }

    @Test
    public void testInsertSelective() {
        fail("Not yet implemented");
    }

    @Test
    public void testDelete() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteByPrimaryKey() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateByPrimaryKey() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectCountByExample() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteByExample() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectByExample() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateByExampleSelective() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateByExample() {
        fail("Not yet implemented");
    }

}
