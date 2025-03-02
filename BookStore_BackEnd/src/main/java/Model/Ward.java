package Model;

import jakarta.persistence.*;
import lombok.Data;
import Model.Users;
import java.util.List;


@Data
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
}
