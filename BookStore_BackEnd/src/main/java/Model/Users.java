package Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int userId;

    private String sex;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String password;

    private String userName;

    //    address default
    @ManyToOne(cascade = {
            jakarta.persistence.CascadeType.PERSIST,
            jakarta.persistence.CascadeType.MERGE,
            jakarta.persistence.CascadeType.DETACH,
            jakarta.persistence.CascadeType.REFRESH
    })
    @JoinColumn(name = "address_id")
    private Ward address;
//
//    private Ward sellingWard;

    @OneToMany(mappedBy = "user")
    private List<Review> listReviews;

    @OneToOne(mappedBy = "user")
    private WishList wishList;

    @ManyToMany(cascade = {
            jakarta.persistence.CascadeType.PERSIST,
            jakarta.persistence.CascadeType.MERGE,
            jakarta.persistence.CascadeType.DETACH,
            jakarta.persistence.CascadeType.REFRESH
    })
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> listRoles;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Order> listOrders;
}
