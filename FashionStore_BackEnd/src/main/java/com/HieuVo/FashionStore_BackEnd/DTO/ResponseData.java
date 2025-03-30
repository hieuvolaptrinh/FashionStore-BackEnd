package com.HieuVo.FashionStore_BackEnd.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {
    private int status;
    private String message;
    private T data;



    public static <T> ResponseData<T> of(int code, String message, T data) {
        return new ResponseData<>(code, message, data);
    }

    // Getters
}
