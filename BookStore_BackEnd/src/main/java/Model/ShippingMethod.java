package Model;

import java.util.List;

import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class ShippingMethod {
    private int shippingMethodID;
    private String shippingMethodName;
    private String description;
    private double fee;
    @OneToMany(mappedBy = "shippingMethod")
    private List<Order> listOrder;
}
