package com.yang.gulimall.member.exception;

public class PhoneNumExistException extends RuntimeException {
    public PhoneNumExistException() {
        super("该电话号码已被注册");
    }
}
