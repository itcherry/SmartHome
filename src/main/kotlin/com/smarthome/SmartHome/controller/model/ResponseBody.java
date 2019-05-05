package com.smarthome.SmartHome.controller.model;


import com.smarthome.SmartHome.error.CustomError;

public class ResponseBody<T>{
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private String status;
    private CustomError error;
    private T data;

    public ResponseBody(String status, CustomError error, T data){
        this.status = status;
        this.error = error;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public CustomError getError() {
        return error;
    }

    public Object getData() {
        return data;
    }
}
