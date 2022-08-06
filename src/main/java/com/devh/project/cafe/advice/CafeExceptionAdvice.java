package com.devh.project.cafe.advice;

import com.devh.project.cafe.exception.CafeMenuServiceException;
import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponseDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CafeExceptionAdvice {
    @ExceptionHandler({CafeMenuServiceException.class})
    public <T> ApiResponseDTO<T> handleCafeMenuServiceException(Exception e) {
        return ApiResponseDTO.customError(ApiStatus.CustomError.CAFE_MENU_SERVICE_ERROR, e.getMessage());
    }
}
