package com.laofan.iantha.security;

import java.io.Serial;

public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private String code;
    private String message;

    public BusinessException(){
        super();
    }

    public BusinessException(String message,Throwable c){
        super(message,c);
    }


    public BusinessException(String  message) {
        super();
        this.message = message;
    }

    public BusinessException(String code ,String  message) {
        super();
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
