package com.backend.fruitrr.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResponseData<TEntity> {
    public boolean Success = false;
    public int Code;
    public String Message = "";
    public TEntity Data = null;
    public boolean IsSuccess = false;
    public List<TEntity> Entity = Collections.emptyList();
    public int StatusCode = 401;
    public int TotalRecordCount = 0;
    public Object Token;

    public ResponseData(boolean success, String message, List<TEntity> entity, int statusCode, int totalRecordCount) {
        Success = success;
        Message = message;
        Entity = entity;
        StatusCode = statusCode;
        TotalRecordCount = totalRecordCount;
    }
    public ResponseData(boolean success, String message, List<TEntity> entity, int statusCode, int totalRecordCount, Object token) {
        Success = success;
        Message = message;
        Entity = entity;
        StatusCode = statusCode;
        TotalRecordCount = totalRecordCount;
        Token = token;
    }

    public void setEntity(List<TEntity> data){
        if(data != null && !data.isEmpty()){
            this.Entity = data;
        }
    }
}



