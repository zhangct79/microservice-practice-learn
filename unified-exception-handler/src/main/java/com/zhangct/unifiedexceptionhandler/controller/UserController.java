package com.zhangct.unifiedexceptionhandler.controller;

import com.zhangct.unifiedexceptionhandler.base.Result;
import com.zhangct.unifiedexceptionhandler.base.ResultEnum;
import com.zhangct.unifiedexceptionhandler.model.User;
import com.zhangct.unifiedexceptionhandler.service.UserService;
import com.zhangct.unifiedexceptionhandler.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/users/success")
    public Result users_success() {
        List<User> users = userService.getUsers();
        return ResultUtil.success(users);
    }

    @RequestMapping("/users/unknow")
    public Result users_unknow() {
        List<User> users = userService.getUsers(0);
        return ResultUtil.success(users);
    }

    @RequestMapping("/users/notexists")
    public Result users_notexists() {
        List<User> users = userService.getUsers(1);
        return ResultUtil.success(users);
    }

}
