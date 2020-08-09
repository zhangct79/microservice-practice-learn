package com.zhangct.unifiedexceptionhandler.dao;

import com.zhangct.unifiedexceptionhandler.base.ResultEnum;
import com.zhangct.unifiedexceptionhandler.exception.NotExistsException;
import com.zhangct.unifiedexceptionhandler.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("zhangct","123456","zhangct@123.com"));
        users.add(new User("zhangln","123456","zhangln@123.com"));

        return users;
    }

    public List<User> getUsers(int flag) {
        if (flag==0)
        {
            errorUnknow();
        }
        else if (flag==1) {
            errorNotExists();
        }

        return getUsers();
    }

    private void errorUnknow() {
        int y = 0;
        int x = 1/y;
    }

    private void errorNotExists() {
        throw new NotExistsException("用户信息不存在");

    }
}
