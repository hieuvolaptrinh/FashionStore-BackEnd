package com.HieuVo.FashionStore_BackEnd.DTO.Request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestPasswordRequest {
    private String email;
    private String userName;
}
