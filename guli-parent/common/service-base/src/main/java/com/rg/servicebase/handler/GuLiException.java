package com.rg.servicebase.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author lxy
 * @version 1.0
 * @Description 自定义异常类---谷粒异常
 * @date 2022/2/8 15:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuLiException extends RuntimeException{
    private Integer code;//状态码
    private String msg;//异常信息
}
