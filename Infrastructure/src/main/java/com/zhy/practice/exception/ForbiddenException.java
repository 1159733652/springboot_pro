package com.zhy.practice.exception;

/**
 * @Author zhanghongyu
 * @Date 2022/8/29
 * @DESC    自定义异常
 */
public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String message) {
        super(message);
    }
}
