package Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
@Data
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
}
