package Model;

import jakarta.persistence.*;


import java.util.List;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> listOrders;

    public Users() {
    }

    public Ward getAddress() {
        return address;
    }

    public void setAddress(Ward address) {
        this.address = address;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Order> getListOrders() {
        return listOrders;
    }

    public void setListOrders(List<Order> listOrders) {
        this.listOrders = listOrders;
    }

    public List<Review> getListReviews() {
        return listReviews;
    }

    public void setListReviews(List<Review> listReviews) {
        this.listReviews = listReviews;
    }

    public List<Role> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<Role> listRoles) {
        this.listRoles = listRoles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public WishList getWishList() {
        return wishList;
    }

    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }

    public Users(Ward address, Cart cart, String email, String firstName, String lastName, List<Order> listOrders, List<Review> listReviews, List<Role> listRoles, String password, String phoneNumber, String sex, int userId, String userName, WishList wishList) {
        this.address = address;
        this.cart = cart;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.listOrders = listOrders;
        this.listReviews = listReviews;
        this.listRoles = listRoles;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.userId = userId;
        this.userName = userName;
        this.wishList = wishList;
    }
}

