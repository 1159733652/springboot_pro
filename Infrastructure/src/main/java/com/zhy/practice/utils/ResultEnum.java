package com.zhy.practice.utils;

import lombok.*;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @Author zhanghongyu
 * @Date 2022/8/29
 * @DESC
 */
public enum ResultEnum implements IResult {

    SUCCESS(2001, "nterface call succeeded", "接口调用成功"),
    VALIDATE_FAILED(2002, "Parameter verification failed", "参数校验失败"),
    COMMON_FAILED(2003, "interface call failed", "接口调用失败"),
    FORBIDDEN(2004, "do not have permission to access resources", "没有权限访问资源");

    private Integer code;
    private final String enMsg;
    private final String zhMsg;

    ResultEnum(Integer code, String enMsg, String zhMsg) {
        this.code = code;
        this.enMsg = enMsg;
        this.zhMsg = zhMsg;
    }


    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        if (Locale.SIMPLIFIED_CHINESE.getLanguage().equals(LocaleContextHolder.getLocale().getLanguage())) {
            return this.zhMsg;
        } else {
            return this.enMsg;
        }
    }

}
