package com.ets.filemanager.config;

import com.ets.filemanager.dto.ApiResponse;
import com.ets.filemanager.exception.BusinessException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler({MaxUploadSizeExceededException.class, FileSizeLimitExceededException.class})
    public ResponseEntity<ApiResponse<?>> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail("File size is too large allowed size is 5 MB."));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException exc) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(exc.getMessage()));
    }
}
