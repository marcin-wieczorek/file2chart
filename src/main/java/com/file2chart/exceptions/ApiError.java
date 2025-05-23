package com.file2chart.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.file2chart.exceptions.http.HttpException;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {

    @NotNull
    private HttpStatus status;

    @NotNull
    private ErrorCode code;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    @NotNull
    private String message;

    private String debugMessage;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status, ErrorCode code, String message) {
        this();
        this.status = status;
        this.code = code;
        this.message = message;
    }

    ApiError(HttpException ex) {
        this();
        this.status = ex.getStatus();
        this.code = ex.getCode();
        this.message = ex.getCode().getMessage();
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(Exception ex) {
        this();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.code = ErrorCode.UNKNOWN;
        this.message = ErrorCode.UNKNOWN.getMessage();
        this.debugMessage = ex.getLocalizedMessage();
    }
}
