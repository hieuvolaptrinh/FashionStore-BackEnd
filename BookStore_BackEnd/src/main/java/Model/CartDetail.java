package Model;
import jakarta.persistence.*;

@Entity
public class CartDetail {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int cartDetailId;

    private int quantity;

    private double price;

    @ManyToOne( cascade ={
            jakarta.persistence.CascadeType.PERSIST,
            jakarta.persistence.CascadeType.MERGE,
            jakarta.persistence.CascadeType.DETACH,
            jakarta.persistence.CascadeType.REFRESH
    })
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne( cascade ={
            jakarta.persistence.CascadeType.PERSIST,
            jakarta.persistence.CascadeType.MERGE,
            jakarta.persistence.CascadeType.DETACH,
            jakarta.persistence.CascadeType.REFRESH
    })
    @JoinColumn(name = "order_id")
    private Order cart;

    public CartDetail() {
    }

    public Order getCart() {
        return cart;
    }

    public void setCart(Order cart) {
        this.cart = cart;
    }

    public int getCartDetailId() {
        return cartDetailId;
    }

    public void setCartDetailId(int cartDetailId) {
        this.cartDetailId = cartDetailId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartDetail(Order cart, int cartDetailId, double price, Product product, int quantity) {
        this.cart = cart;
        this.cartDetailId = cartDetailId;
        this.price = price;
        this.product = product;
        this.quantity = quantity;
    }
}
