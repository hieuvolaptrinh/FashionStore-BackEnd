package com.HieuVo.FashionStore_BackEnd.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int userId;

    private String sex;

    private String email;

    private String activationCode;

    private String phoneNumber;

    @Column(columnDefinition = "NVARCHAR(256)")
    private String firstName;

    @Column(columnDefinition = "NVARCHAR(256)")
    private String lastName;

    private String password;

    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(unique = true)
    private String userName;

    @Lob
    @Column(columnDefinition = "VARBINARY(MAX)")
    private byte[] avatarData;

    private String avataUrl;

    private Long money;

    @OneToMany(mappedBy = "user")
    private List<Address> listAddress;

    @OneToMany(mappedBy = "user")
    private List<Review> listReviews;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            jakarta.persistence.CascadeType.PERSIST,
            jakarta.persistence.CascadeType.MERGE,
            jakarta.persistence.CascadeType.DETACH,
            jakarta.persistence.CascadeType.REFRESH
    })
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> listRoles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> listOrders;





    @Override
    public String toString() {
        return "User{id=" + userId + ", username='" + userName + "'}"; // Don't include listRoles here
    }
}
