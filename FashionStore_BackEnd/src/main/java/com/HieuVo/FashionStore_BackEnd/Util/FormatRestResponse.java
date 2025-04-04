package com.HieuVo.FashionStore_BackEnd.Util;


import com.HieuVo.FashionStore_BackEnd.DTO.Response.RestResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
//        return false; // tạm thời tắt vi ben front end chưa chỉnh lại gọi api
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();

        RestResponse<Object> restResponse = new RestResponse<Object>();
        restResponse.setStatus(status);
        if (body instanceof String) {
            return body;
        }
//        case error
        if (status >= 400) {
            restResponse.setError(body.toString());
            restResponse.setMessage("Call api thất bại");
        } else {
            restResponse.setData(body);
            restResponse.setMessage("Call api thành công");

        }
        return restResponse;
    }
}
