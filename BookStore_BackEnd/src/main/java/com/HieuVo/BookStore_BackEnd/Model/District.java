package com.HieuVo.BookStore_BackEnd.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer districtId;

    @Column(columnDefinition = "NVARCHAR(500)")
    private String districtName;

    @ManyToOne
    @JoinColumn(name = "city_id") // Khóa ngoại
    private City city;


}


