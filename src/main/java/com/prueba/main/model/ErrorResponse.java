package com.prueba.main.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private String timestamp;
    private Integer code;
    private String message;
    private String path;

    public ErrorResponse(int code, String message, String path){
        this.code = code;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now().toString();
    }
}
