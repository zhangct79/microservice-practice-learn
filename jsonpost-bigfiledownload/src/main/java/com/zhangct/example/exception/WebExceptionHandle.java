package com.zhangct.example.exception;

import com.zhangct.example.base.Result;
import com.zhangct.example.base.ResultEnum;
import com.zhangct.example.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author zhangct
 * @date 20/8/7
 */


@ControllerAdvice
@ResponseBody
public class WebExceptionHandle {
    private static Logger logger = LoggerFactory.getLogger(WebExceptionHandle.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result runtimeExceptionHandler(Exception exception) {
        logger.error(exception.getMessage());
        return ResultUtil.error(ResultEnum.UNKNOW_ERROR.getCode(), ResultEnum.UNKNOW_ERROR.getMsg());
    }

    @ExceptionHandler(NotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result notExistsExceptionHandler(NotExistsException e) {
        logger.error(e.getMessage());
        return ResultUtil.error(ResultEnum.USER_NOT_EXISTS.getCode(), ResultEnum.USER_NOT_EXISTS.getMsg());
    }
}
