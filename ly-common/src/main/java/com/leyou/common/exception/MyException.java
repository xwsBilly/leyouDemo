package com.leyou.common.exception;

import com.leyou.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 自定义一个通用异常类
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MyException extends RuntimeException{
    private ExceptionEnum exceptionEnum;

}
