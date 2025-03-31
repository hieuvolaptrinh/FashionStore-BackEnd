package com.HieuVo.FashionStore_BackEnd.Util;


import com.HieuVo.FashionStore_BackEnd.DTO.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class FormatRestResponse  implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return true;

        return false; // tạm thời tắt  api viet  tra ve ?
    }
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();

        // Xử lý đặc biệt cho trường hợp body là String
        if (body instanceof String) {
            // Bạn cần tạo một ObjectMapper để chuyển RestResponse thành String
            try {
                ObjectMapper mapper = new ObjectMapper();
                RestResponse<Object> restResponse = new RestResponse<>();
                restResponse.setStatus(status);
                restResponse.setData(body);
                restResponse.setMessage("Call api thành công");
                return mapper.writeValueAsString(restResponse);
            } catch (Exception e) {
                // xử lý ngoại lệ
                return body;
            }
        }

        // Xử lý các trường hợp khác
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse.setStatus(status);

        // case error
        if (status >= 400) {
            restResponse.setError(body != null ? body.toString() : "Unknown error");
            restResponse.setMessage("Call api thất bại");
        } else {
            restResponse.setData(body);
            restResponse.setMessage("Call api thành công");
        }
        return restResponse;
    }
//    @Override
//    public Object beforeBodyWrite(Object body,
//                                  MethodParameter returnType,
//                                  MediaType selectedContentType,
//                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
//                                  ServerHttpRequest request, ServerHttpResponse response ) {
//        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
//        int status = servletResponse.getStatus();
//
//        RestResponse<Object> restResponse = new RestResponse<Object>();
//        restResponse.setStatus(status);
//
////        case error
//        if(status >=400){
//            restResponse.setError(body.toString());
//            restResponse.setMessage("Call api thất bại");
//        }
//        else {
//            restResponse.setData(body);
//            restResponse.setMessage("Call api thành công");
//
//        }
//        return restResponse;
//    }
}
