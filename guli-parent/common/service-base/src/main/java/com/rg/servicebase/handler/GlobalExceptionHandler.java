package com.rg.servicebase.handler;


import com.rg.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lxy
 * @version 1.0
 * @Description  异常处理类
 * @date 2022/2/8 12:47
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //统一异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error();
    }

    //处理特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了自定义异常");
    }

    //自定义异常
    @ExceptionHandler(GuLiException.class)
    @ResponseBody
    public R error(GuLiException e){
        log.error(e.getMessage());//将错误信息输出到日志中...
        e.printStackTrace();;
        return R.error().code(e.getCode()).message(e.getMsg());
    }






}
