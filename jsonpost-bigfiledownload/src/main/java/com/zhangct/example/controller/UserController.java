package com.zhangct.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangct.example.base.Result;
import com.zhangct.example.model.DeploymentModel;
import com.zhangct.example.model.UserModel;
import com.zhangct.example.service.UserService;
import com.zhangct.example.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author zhangct
 * @date 20/8/7
 */
@RestController
@ResponseStatus(HttpStatus.ACCEPTED)
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/users")
    public Result<UserModel> users() throws Exception {
        return ResultUtil.success(userService.getUsers());
    }



    @RequestMapping(value = "/deployments", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result<UserModel> deployments(@RequestBody JSONObject jsonParam) throws Exception {
        System.out.println(jsonParam.toJSONString());
        DeploymentModel deploymentModel = JSON.parseObject(jsonParam.toJSONString(), DeploymentModel.class);
        System.out.println(deploymentModel);

        return ResultUtil.success(deploymentModel);
    }

    @RequestMapping(value = "/request/deployments", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result<UserModel> getByRequest(HttpServletRequest request) {
        //获取到JSONObject
        JSONObject jsonParam = this.getJSONParam(request);
        DeploymentModel deploymentModel = JSON.parseObject(jsonParam.toJSONString(), DeploymentModel.class);
        System.out.println(deploymentModel);

        return ResultUtil.success(deploymentModel);
    }

    public JSONObject getJSONParam(HttpServletRequest request){
        JSONObject jsonParam = null;
        try {
            // 获取输入流
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

            // 写入数据到Stringbuilder
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            jsonParam = JSONObject.parseObject(sb.toString());
            // 直接将json信息打印出来
            System.out.println(jsonParam.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonParam;
    }
}
