package com.ets.filemanager.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResponse <T> implements Serializable {
    private boolean success;
    private String errorMessage;
    private T result;

    public static <T> ApiResponse<T> ok(T result) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = true;
        response.errorMessage = null;
        response.result = result;
        return response;
    }

    public static <T> ApiResponse<T> fail(String errorMessage) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = false;
        response.errorMessage = errorMessage;
        response.result = null;
        return response;
    }
}