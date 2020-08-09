package com.zhangct.unifiedexceptionhandler.handler;


import com.zhangct.unifiedexceptionhandler.base.Result;
import com.zhangct.unifiedexceptionhandler.base.ResultEnum;
import com.zhangct.unifiedexceptionhandler.exception.NotExistsException;
import com.zhangct.unifiedexceptionhandler.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ControllerAdvice
public class WebExceptionHandler {
    Logger logger = (Logger) LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity unknow_exception(Exception e) {
        logger.error(e.getMessage());
        Result result = ResultUtil.error(ResultEnum.UNKNOW.getCode(), ResultEnum.UNKNOW.getMsg());
        ResponseEntity responseEntity = new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);

        return responseEntity;
    }

//    @ExceptionHandler(Exception.class)
//    public Result unknow_exception(Exception e) {
//        logger.error(e.getMessage());
//        return ResultUtil.error(ResultEnum.UNKNOW.getCode(), ResultEnum.UNKNOW.getMsg());
//    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotExistsException.class)
    public Result notexists_exception(NotExistsException e) {
        logger.error(e.getMessage());
        Result result = ResultUtil.error(ResultEnum.UNKNOW.getCode(), ResultEnum.UNKNOW.getMsg());
        return result;
    }
}
