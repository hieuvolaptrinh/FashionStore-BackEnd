package Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
@Data
@Entity
public class PaymentType {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int paymentTypeID;

    private String paymentTypeName;

    private String description;

    private double fee;

    @OneToMany(mappedBy = "paymentType")
    private List<Order> listOrder;
}
