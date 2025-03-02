package Model;

import jakarta.persistence.*;

@Entity
public class WishListDetail {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int wishListDetailId;

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
    @JoinColumn(name = "wish_list_id")
    private WishList wishList;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public WishList getWishList() {
        return wishList;
    }

    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }

    public int getWishListDetailId() {
        return wishListDetailId;
    }

    public void setWishListDetailId(int wishListDetailId) {
        this.wishListDetailId = wishListDetailId;
    }

    public WishListDetail(Product product) {
        this.product = product;
    }

    public WishListDetail(Product product, WishList wishList, int wishListDetailId) {
        this.product = product;
        this.wishList = wishList;
        this.wishListDetailId = wishListDetailId;
    }
}
