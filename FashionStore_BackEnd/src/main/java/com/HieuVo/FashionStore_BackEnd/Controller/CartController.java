package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.DTO.CartDTO;
import com.HieuVo.FashionStore_BackEnd.DTO.CartDetailDTO;
import com.HieuVo.FashionStore_BackEnd.DTO.Notification;
import com.HieuVo.FashionStore_BackEnd.Model.Cart;
import com.HieuVo.FashionStore_BackEnd.Model.CartDetail;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Service.CartService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<Notification> addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam int productId,
            @RequestParam int quantity) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body(new Notification("Bạn chưa đăng nhập"));
        }
        this.cartService.addToCart(userDetails, productId, quantity);
        return ResponseEntity.ok(new Notification("Tạo giỏ hàng mới và thêm vào giỏ hàng thành công"));
    }

    @GetMapping
    public ResponseEntity<CartDTO> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            CartDTO cartDTO = cartService.getCart(userDetails);
            return ResponseEntity.ok(cartDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/cart-detail")
    public ResponseEntity<List<CartDetailDTO>> getCartDetail(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            List<CartDetailDTO> cartDetailDTOs = cartService.getAllCartDetail(userDetails);
            return ResponseEntity.ok(cartDetailDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/update")
    public ResponseEntity<Notification> updateCartItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam int cartDetailId,
            @RequestParam int quantity) {
        CartDetail cartDetail = cartService.updateCartItemQuantity(userDetails, cartDetailId, quantity);
        return ResponseEntity.ok(new Notification("Cập nhật số lượng sản phẩm thành công"));
    }

    @DeleteMapping("/remove/{cartDetailId}")
    public ResponseEntity<Notification> removeFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int cartDetailId) {
        cartService.removeFromCart(userDetails, cartDetailId);
        return ResponseEntity.ok(new Notification("Xóa sản phẩm khỏi giỏ hàng thành công"));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Notification> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        cartService.clearCart(userDetails);
        return ResponseEntity.ok(new Notification("Xóa tất cả sản phẩm khỏi giỏ hàng thành công"));
    }
}