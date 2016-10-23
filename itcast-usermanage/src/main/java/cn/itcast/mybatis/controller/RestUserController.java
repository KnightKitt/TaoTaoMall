package cn.itcast.mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.mybatis.pojo.User;
import cn.itcast.mybatis.service.NewUserService;

@RequestMapping("restful/user")
@Controller
public class RestUserController {
    
    @Autowired
    private NewUserService newUserService;

    /**
     * 根据id查找user
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> queryUserById(@PathVariable("id")Long id){
        try {
            User user = this.newUserService.queryUserById(id);
            if (null == user) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
//            int n = 1/0;
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    /**
     * 新增用户（资源）
     * 
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveUser(User user){
        try {
            this.newUserService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    /**
     * 修改user
     * 
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(User user){
        try {
            this.newUserService.updateUser(user);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        
        
    }

    /**
     * 根据id删除user
     * 
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@RequestParam(value = "id", defaultValue = "0")Long id){
        try {
            if (id == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            this.newUserService.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        
        
    }
}
