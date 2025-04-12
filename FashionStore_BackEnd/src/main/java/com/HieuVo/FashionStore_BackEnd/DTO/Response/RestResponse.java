package com.HieuVo.FashionStore_BackEnd.DTO.Response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class RestResponse<T> {
    private int status;
    private Object error;
    private Object message;
    private T data;

}
