package com.example.basemodule.network;

import java.io.IOException;

/**
 * create by zy on 2019/9/26
 * </p>
 */
public class ApiException extends IOException {

    private int error;

    private String message;


    public ApiException(int error, String message) {
        this.error = error;
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
