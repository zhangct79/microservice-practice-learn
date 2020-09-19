package com.zhangct.example.util;

import com.zhangct.example.base.Result;
import com.zhangct.example.base.ResultEnum;

/**
 * @author zhangct
 * @date 20/8/7
 */


public class ResultUtil {
    public static Result success(Object object) {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),object);
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        return new Result(code,msg,null);
    }
}
