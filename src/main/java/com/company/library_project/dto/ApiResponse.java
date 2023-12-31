package com.company.library_project.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse<T> {
    private String message;
    private Integer code;
    private Boolean status;
    private T data;

    public ApiResponse(String message, Integer code, Boolean status) {
        this.message = message;
        this.code = code;
        this.status = status;
    }

    public ApiResponse(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResponse(Boolean status, T data) {
        this.status = status;
        this.data = data;
    }

    public ApiResponse(Boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


}
