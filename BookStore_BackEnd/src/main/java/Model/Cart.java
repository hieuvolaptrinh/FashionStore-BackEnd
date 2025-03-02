package Model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int cartId;

    private Date createAt;

    private Date updateAt;

    private double totalPrices;

    @OneToOne(mappedBy = "cart")
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartDetail> cartDetails;

    public Cart(List<CartDetail> cartDetails, int cartId, Date createAt, double totalPrices, Date updateAt, Users user) {
        this.cartDetails = cartDetails;
        this.cartId = cartId;
        this.createAt = createAt;
        this.totalPrices = totalPrices;
        this.updateAt = updateAt;
        this.user = user;
    }

    public Cart() {

    }

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public double getTotalPrices() {
        return totalPrices;
    }

    public void setTotalPrices(double totalPrices) {
        this.totalPrices = totalPrices;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
