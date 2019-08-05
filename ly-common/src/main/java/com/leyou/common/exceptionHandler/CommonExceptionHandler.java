package com.leyou.common.exceptionHandler;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.MyException;
import com.leyou.common.vo.ExceptionResultVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 默认拦截所有加了controller注解的类的通知
@ControllerAdvice
public class CommonExceptionHandler {
    // 需要捕获的异常类
    @ExceptionHandler(MyException.class)
    // 返回的类型即为在页面上所需要看见的类型
    public ResponseEntity<ExceptionResultVO> handleException(MyException e){
        ExceptionEnum exceptionEnum = e.getExceptionEnum();
        return ResponseEntity.status(exceptionEnum.getCode())
                .body(new ExceptionResultVO(exceptionEnum));
    }
}
