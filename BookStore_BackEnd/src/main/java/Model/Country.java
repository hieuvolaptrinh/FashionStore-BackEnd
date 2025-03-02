package Model;

import jakarta.persistence.*;
import lombok.Data;
import Model.City;

import java.util.List;
@Data
@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer countryId;

    private String countryName;
    @OneToMany(mappedBy = "country")
    private List<City> citys;

    public Country() {

    }


}
