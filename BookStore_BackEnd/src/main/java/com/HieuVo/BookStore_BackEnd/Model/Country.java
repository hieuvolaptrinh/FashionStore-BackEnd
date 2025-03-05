package com.HieuVo.BookStore_BackEnd.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity

public class Country {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer countryId;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String countryName;

    @OneToMany(mappedBy = "country")
    private List<City> citys;

}
