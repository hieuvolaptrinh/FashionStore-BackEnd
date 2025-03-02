package Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ShippingMethod {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int shippingMethodID;
    private String shippingMethodName;
    private String description;
    private double fee;
    @OneToMany(mappedBy = "shippingMethod")
    private List<Order> listOrder;

    public ShippingMethod(String description, double fee, List<Order> listOrder, int shippingMethodID, String shippingMethodName) {
        this.description = description;
        this.fee = fee;
        this.listOrder = listOrder;
        this.shippingMethodID = shippingMethodID;
        this.shippingMethodName = shippingMethodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public List<Order> getListOrder() {
        return listOrder;
    }

    public void setListOrder(List<Order> listOrder) {
        this.listOrder = listOrder;
    }

    public int getShippingMethodID() {
        return shippingMethodID;
    }

    public void setShippingMethodID(int shippingMethodID) {
        this.shippingMethodID = shippingMethodID;
    }

    public String getShippingMethodName() {
        return shippingMethodName;
    }

    public void setShippingMethodName(String shippingMethodName) {
        this.shippingMethodName = shippingMethodName;
    }
}
