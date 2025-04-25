package com.HieuVo.FashionStore_BackEnd.Util.Error;


import com.HieuVo.FashionStore_BackEnd.DTO.Response.RestResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExecption {
    @ExceptionHandler(value = {MainException.class,
            MainException.class,
            BadRequestException.class,
    })
    public ResponseEntity<RestResponse<Object>> handleIdInvalidException(MainException idException) {
        RestResponse<Object> restResponse = new RestResponse<Object>();
        restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        restResponse.setError(idException.getMessage());
        restResponse.setMessage("id invalidexeception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> validationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();


        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.setError(ex.getBody().getDetail());

        // Lọc lỗi và lấy thông tin lỗi
        List<String> errors = fieldErrors.stream()
                .map(f -> f.getDefaultMessage())
                .collect(Collectors.toList());

//        res.setMessage(errors.size() > 1 ? errors : errors.get(0)); // nếu có một lỗi thì String
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }


}
