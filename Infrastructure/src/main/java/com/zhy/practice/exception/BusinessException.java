package com.zhy.practice.exception;

/**
 * @Author zhanghongyu
 * @Date 2022/8/29
 * @DESC    自定义异常
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
