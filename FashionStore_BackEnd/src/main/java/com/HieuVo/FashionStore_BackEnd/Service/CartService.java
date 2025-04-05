package com.HieuVo.FashionStore_BackEnd.Service;

import com.HieuVo.FashionStore_BackEnd.Model.Cart;
import com.HieuVo.FashionStore_BackEnd.Model.CartDetail;
import com.HieuVo.FashionStore_BackEnd.Model.Product;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Repository.CartDetailRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.CartRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.ProductRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    private final CartDetailRepository cartDetailRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, CartDetailRepository cartDetailRepository,
            ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public CartDetail addToCart(UserDetails userDetails, int productId, int quantity) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }

        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tìm hoặc tạo giỏ hàng cho user
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            // cart.setUser(user);
            cart.setCreateAt(new Date(System.currentTimeMillis()));
            cart.setUpdateAt(new Date(System.currentTimeMillis()));
            cart.setTotalPrices(0.0);
            cart.setListCartDetails(new ArrayList<>());

            cart = cartRepository.save(cart);
        }
        System.out.println("cart: " + cart.toString());

        // Tìm sản phẩm
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Kiểm tra số lượng tồn kho
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Not enough product in stock");
        }

        // Tìm cartDetail nếu sản phẩm đã có trong giỏ
        Optional<CartDetail> existingCartDetail = cart.getListCartDetails().stream()
                .filter(cd -> cd.getProduct().getProductId() == productId)
                .findFirst();

        CartDetail cartDetail;
        if (existingCartDetail.isPresent()) {
            // Cập nhật số lượng nếu sản phẩm đã có trong giỏ
            cartDetail = existingCartDetail.get();
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
        } else {
            // Tạo mới cartDetail nếu sản phẩm chưa có trong giỏ
            cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProduct(product);
            cartDetail.setQuantity(quantity);
        }

        // Cập nhật giá
        cartDetail.setPrice(product.getSalePrice() > 0 ? product.getSalePrice() : product.getOriginalPrice());

        // Lưu cartDetail
        cartDetail = cartDetailRepository.save(cartDetail);

        // Cập nhật tổng giá và thời gian của giỏ hàng
        updateCartTotalPrice(cart);

        return cartDetail;
    }

    // Lấy giỏ hàng của user
    public Cart getCart(UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }

        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getCart();
    }

    // Cập nhật số lượng sản phẩm trong giỏ
    public CartDetail updateCartItemQuantity(UserDetails userDetails, int cartDetailId, int quantity) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }

        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // Kiểm tra quyền truy cập
        // if (cartDetail.getCart().getUser().getUserId() != user.getUserId()) {
        // throw new RuntimeException("Unauthorized access to cart item");
        // }

        // Kiểm tra số lượng tồn kho
        if (cartDetail.getProduct().getQuantity() < quantity) {
            throw new RuntimeException("Not enough product in stock");
        }

        cartDetail.setQuantity(quantity);
        cartDetail = cartDetailRepository.save(cartDetail);

        // Cập nhật tổng giá của giỏ hàng
        updateCartTotalPrice(cartDetail.getCart());

        return cartDetail;
    }

    // Xóa sản phẩm khỏi giỏ
    public void removeFromCart(UserDetails userDetails, int cartDetailId) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }

        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Cart cart = cartDetail.getCart();
        cartDetailRepository.delete(cartDetail);

        // Cập nhật tổng giá của giỏ hàng
        updateCartTotalPrice(cart);
    }

    // Xóa toàn bộ giỏ hàng
    public void clearCart(UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }

        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = user.getCart();
        if (cart != null) {
            cart.getListCartDetails().clear();
            cart.setTotalPrices(0.0);
            cart.setUpdateAt(new Date(System.currentTimeMillis()));
            cartRepository.save(cart);
        }
    }

    // Helper method để cập nhật tổng giá của giỏ hàng
    private void updateCartTotalPrice(Cart cart) {
        double totalPrice = cart.getListCartDetails().stream()
                .mapToDouble(cd -> cd.getPrice() * cd.getQuantity())
                .sum();
        cart.setTotalPrices(totalPrice);
        cart.setUpdateAt(new Date(System.currentTimeMillis()));
        cartRepository.save(cart);
    }
}