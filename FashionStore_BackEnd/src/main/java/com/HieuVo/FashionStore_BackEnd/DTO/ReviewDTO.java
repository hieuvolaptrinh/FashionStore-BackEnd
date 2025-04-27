package com.HieuVo.FashionStore_BackEnd.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private int reviewId;

    private int productId;

    @NotBlank(message = "Nội dung đánh giá không được để trống")
    @Size(min = 10, max = 500, message = "Nội dung đánh giá phải có độ dài từ 10 đến 500 ký tự")
    private String content;

    @NotNull(message = "Số sao không được để trống")
    @Min(value = 1, message = "Số sao tối thiểu là 1")
    @Max(value = 5, message = "Số sao tối đa là 5")
    private byte stars;

    @NotBlank(message = "Tên người đánh giá không được để trống")
    private String name;

    private String avatar;

}
