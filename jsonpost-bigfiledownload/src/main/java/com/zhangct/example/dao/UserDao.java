package com.zhangct.example.dao;

import com.zhangct.example.exception.NotExistsException;
import com.zhangct.example.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author zhangct
 * @date 20/8/7
 */

@Component
public class UserDao {
    public List<UserModel> getUsers() throws Exception {
//        error1();

        List<UserModel> users = new ArrayList<>();
        users.add(new UserModel("zhangct","123456","zhangct@asiainfo.com","152********"));
        users.add(new UserModel("zhangln","123456","zhangln@asiainfo.com","158********"));
        return users;
    }

    public void error() throws Exception {
        throw new Exception("UserDao异常模拟");
    }

    public void error1() throws Exception {
        throw new NotExistsException("用户不存在");
    }

}
