package com.leyou.common.vo;

import com.leyou.common.enums.ExceptionEnum;
import lombok.Data;

@Data
public class ExceptionResultVO {
    // 状态码
    private int status;
    // 返回信息
    private String message;
    // 时间戳
    private Long timestamp;

    public ExceptionResultVO(ExceptionEnum exceptionEnum) {
        this.status = exceptionEnum.getCode();
        this.message = exceptionEnum.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
