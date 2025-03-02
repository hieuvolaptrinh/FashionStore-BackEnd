package Model;

import jakarta.persistence.*;

import Model.Users;
import java.util.List;

@Entity
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wardId;

    @Column(nullable = false)
    private String wardName;

    @ManyToOne
    @JoinColumn(name = "district_id") // Đảm bảo không null
    private District district;

    @OneToMany(mappedBy = "address")
    private List<Users> listUsers;

    @OneToMany(mappedBy = "shippingWard")
    private List<Order> listOrders;

    public Ward() {

    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public List<Order> getListOrders() {
        return listOrders;
    }

    public void setListOrders(List<Order> listOrders) {
        this.listOrders = listOrders;
    }

    public List<Users> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<Users> listUsers) {
        this.listUsers = listUsers;
    }

    public Integer getWardId() {
        return wardId;
    }

    public void setWardId(Integer wardId) {
        this.wardId = wardId;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public Ward(District district, List<Order> listOrders, List<Users> listUsers, Integer wardId, String wardName) {
        this.district = district;
        this.listOrders = listOrders;
        this.listUsers = listUsers;
        this.wardId = wardId;
        this.wardName = wardName;
    }
}
