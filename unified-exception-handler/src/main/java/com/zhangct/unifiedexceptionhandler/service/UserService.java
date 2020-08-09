package com.zhangct.unifiedexceptionhandler.service;

import com.zhangct.unifiedexceptionhandler.dao.UserDao;
import com.zhangct.unifiedexceptionhandler.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public List<User> getUsers(int flag) {
        return userDao.getUsers(flag);
    }

}
