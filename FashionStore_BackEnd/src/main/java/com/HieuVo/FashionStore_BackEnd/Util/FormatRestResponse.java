package com.HieuVo.FashionStore_BackEnd.Util;

import com.HieuVo.FashionStore_BackEnd.DTO.Response.RestResponse;
import com.HieuVo.FashionStore_BackEnd.Util.Anotation.ApiMessage;
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
    }

    @Override
    public Object beforeBodyWrite(Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();

        // Nếu body đã là RestResponse, trả về nguyên bản
        if (body instanceof RestResponse) {
            return body;
        }
        if (body instanceof String) {
            return body;
        }


        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse.setStatus(status);

        // Xử lý lỗi
        if (status >= 400) {
            if (body instanceof String) {
                restResponse.setError((String) body);
            } else {
                restResponse.setError(body);
            }
            restResponse.setMessage("Error occurred");
            return restResponse;
        }

        // Xử lý thành công
        restResponse.setData(body);
        ApiMessage message = returnType.getMethodAnnotation(ApiMessage.class);
        restResponse.setMessage(message != null ? message.value() : "Call API successfully");

        return restResponse;
    }
}
