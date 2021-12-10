package com.uhwenkang.week3.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class curdController {


        @Resource
        private UserMapper userDao;

        //根据用户名获取账户和密码
        @RequestMapping(value = "/user", method = RequestMethod.GET,produces = "application/json")
        public User postUser(@RequestParam(value = "userName") String username,
                             HttpServletRequest request){
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("user_name",username);
            List<User> users = userDao.selectByMap(objectObjectHashMap);
            if(!users.isEmpty()) {
                log.info(request.getRemoteHost()+":GET:"+"username:"+username);
                return users.get(0);
            }
            log.info(request.getRemoteHost()+":GET:"+"username:"+username+"不存在");
            return null;
        }

        //增加用户
        @RequestMapping(value = "/user", method = RequestMethod.POST, produces = "application/json")
        public String getUser(@RequestParam("userName")String username,
                              @RequestParam("userPassWord")String password,
                              HttpServletRequest request){
            User user = new User();
            user.setUserName(username);
            user.setUserPassWord(password);
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("user_name",username);
            if (userDao.selectByMap(objectObjectHashMap).isEmpty()) {
                userDao.insert(user);
                log.info(request.getRemoteHost()+":POST:"+"username:"+username+"password:"+password);
                return "添加成功";
            }
            log.info(request.getRemoteHost()+":POST:"+"username"+"已存在");
            return "用户已存在";
        }

        //根据用户名删除用户
        @RequestMapping(value = "/user", method = RequestMethod.DELETE, produces = "application/json")
        public String deleteUser(@RequestParam("userName")String username,
                                 HttpServletRequest request){
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("user_name",username);
            if(userDao.selectByMap(objectObjectHashMap).isEmpty()) {
                log.info(request.getRemoteHost()+":DELETE:"+"username:"+username+" 不存在");
                return "没有该用户";
            }
            userDao.deleteByMap(objectObjectHashMap);
            log.info(request.getRemoteHost()+":DELETE:"+"username:"+username);
            return "删除成功";
        }

        //根据用户名修改账户
        @RequestMapping(value = "/user", method = RequestMethod.PUT, produces = "application/json")
        public String putUser(@RequestParam("userName")String username,
                              @RequestParam("userPassWord")String password,
                              HttpServletRequest request) {
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("user_name",username);
            List<User> users = userDao.selectByMap(objectObjectHashMap);
            if(users.isEmpty()){
                log.info(request.getRemoteHost()+":PUT:"+"username"+"不存在");
                return "用户不存在";
            }
            else {
                User user1 = users.get(0);
                User user = new User();
                user.setUserPassWord(password);
                user.setId(user1.getId());
                userDao.updateById(user);
                log.info(request.getRemoteHost()+":PUT:"+"username:"+username+" password-->"+password);
                return "修改成功";
            }
        }


}
