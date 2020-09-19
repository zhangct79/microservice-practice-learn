package com.zhangct.example.service;

import com.zhangct.example.dao.UserDao;
import com.zhangct.example.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangct
 * @date 20/8/7
 */


@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public List<UserModel> getUsers() throws Exception {
        return userDao.getUsers();
    }
}

