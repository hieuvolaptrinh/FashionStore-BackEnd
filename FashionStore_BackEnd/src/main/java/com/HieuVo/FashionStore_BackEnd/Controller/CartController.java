package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.Model.Cart;
import com.HieuVo.FashionStore_BackEnd.Model.CartDetail;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Service.CartService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam int productId,
            @RequestParam int quantity) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }
        CartDetail cartDetail = cartService.addToCart(userDetails, productId, quantity);
        return ResponseEntity.ok(cartDetail);
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        Cart cart = cartService.getCart(userDetails);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update/{cartDetailId}")
    public ResponseEntity<CartDetail> updateCartItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int cartDetailId,
            @RequestParam int quantity) {
        CartDetail cartDetail = cartService.updateCartItemQuantity(userDetails, cartDetailId, quantity);
        return ResponseEntity.ok(cartDetail);
    }

    @DeleteMapping("/remove/{cartDetailId}")
    public ResponseEntity<Void> removeFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int cartDetailId) {
        cartService.removeFromCart(userDetails, cartDetailId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        cartService.clearCart(userDetails);
        return ResponseEntity.ok().build();
    }
}