package Model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class WishList {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int wishListId;

    @OneToOne(mappedBy = "wishList")
    @JoinColumn(name = "user_id",nullable = false)
    private Users user;
    @OneToMany(mappedBy = "wishList")
    private List<WishListDetail> listWishListDetails;

    public WishList() {

    }

    public List<WishListDetail> getListWishListDetails() {
        return listWishListDetails;
    }

    public void setListWishListDetails(List<WishListDetail> listWishListDetails) {
        this.listWishListDetails = listWishListDetails;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getWishListId() {
        return wishListId;
    }

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    public WishList(List<WishListDetail> listWishListDetails, Users user, int wishListId) {
        this.listWishListDetails = listWishListDetails;
        this.user = user;
        this.wishListId = wishListId;
    }
}
