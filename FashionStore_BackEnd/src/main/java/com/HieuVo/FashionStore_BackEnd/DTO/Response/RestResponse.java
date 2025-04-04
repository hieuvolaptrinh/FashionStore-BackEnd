package com.HieuVo.FashionStore_BackEnd.DTO.Response;

import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)  // Đảm bảo không bao gồm các trường null trong JSON
public class RestResponse<T> {
    private int status;
    private Object error;
    private Object message;
    private T data;

}
