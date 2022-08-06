package com.devh.project.item.advice;

import com.devh.project.common.constant.ApiStatus;
import com.devh.project.common.dto.ApiResponseDTO;
import com.devh.project.item.exception.ItemException;
import com.devh.project.item.exception.NotEnoughStockException;
import com.devh.project.item.exception.UnknownDiscriminatorException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ItemExceptionAdvice {
    @ExceptionHandler({NotEnoughStockException.class})
    public <T> ApiResponseDTO<T> handleNotEnoughStockException(Exception e) {
        return ApiResponseDTO.customError(ApiStatus.CustomError.NOT_ENOUGH_STOCK_ERROR, e.getMessage());
    }
    @ExceptionHandler({ItemException.class})
    public <T> ApiResponseDTO<T> handleItemException(Exception e) {
        return ApiResponseDTO.customError(ApiStatus.CustomError.ITEM_ERROR, e.getMessage());
    }
    @ExceptionHandler({UnknownDiscriminatorException.class})
    public <T> ApiResponseDTO<T> handleUnknownDiscriminatorException(Exception e) {
        return ApiResponseDTO.customError(ApiStatus.CustomError.ITEM_ERROR, e.getMessage());
    }
}
