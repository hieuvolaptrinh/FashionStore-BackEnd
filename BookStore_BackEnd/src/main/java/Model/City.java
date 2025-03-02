package Model;

import java.util.List;

import Model.Country;
import Model.District;
import jakarta.persistence.*;

@Entity
@Table(name = "City")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id") // Đặt tên cột rõ ràng
    private int cityId;

    @Column(name = "cityName", nullable = false, unique = true)
    private String cityName;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false) // Thêm nullable=false để tránh lỗi dữ liệu null
    private Country country;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<District> districts;

    // Constructor không tham số (bắt buộc)
    public City() {}

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}
