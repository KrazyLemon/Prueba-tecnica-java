package com.prueba.main.exceptions;

import com.prueba.main.model.ErrorResponse;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice(basePackages = "com.prueba.main.controller")
@io.swagger.v3.oas.annotations.Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(com.prueba.main.exceptions.BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            RuntimeException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                400,
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.prueba.main.exceptions.ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            RuntimeException ex,
            HttpServletRequest request){

        ErrorResponse response = new ErrorResponse(
                404,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(com.prueba.main.exceptions.ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            RuntimeException ex,
            HttpServletRequest request){

        ErrorResponse response = new ErrorResponse(
                409,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(com.prueba.main.exceptions.InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleInternal(
            RuntimeException ex,
            HttpServletRequest request){

        ErrorResponse response = new ErrorResponse(
                500,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }



//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleAllUncaughtException(
//            Exception ex,
//            HttpServletRequest request) {
//
//        System.out.println("Handler Generico ejecutado");
//        // Esto te ayudará a ver en el JSON qué excepción real está llegando
//        ErrorResponse response = new ErrorResponse(
//                500,
//                "Internal Error: " + ex.getClass().getName() + " - " + ex.getMessage(),
//                request.getRequestURI()
//        );
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }

}