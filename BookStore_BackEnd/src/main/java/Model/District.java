package Model;

import jakarta.persistence.*;
import Model.City;
import lombok.Data;
@Data
@Entity
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer districtId;

    @Column(nullable = false)
    private String districtName;

    @ManyToOne
    @JoinColumn(name = "city_id") // Khóa ngoại
    private City city;

    public District() {
    }
}


