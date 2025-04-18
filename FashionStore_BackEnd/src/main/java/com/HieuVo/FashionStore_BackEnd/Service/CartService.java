package com.HieuVo.FashionStore_BackEnd.Service;

import com.HieuVo.FashionStore_BackEnd.DTO.Response.CartResponse;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.CartDetailDTO;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.ProductCartResponse;
import com.HieuVo.FashionStore_BackEnd.Model.Cart;
import com.HieuVo.FashionStore_BackEnd.Model.CartDetail;
import com.HieuVo.FashionStore_BackEnd.Model.Product;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Repository.CartDetailRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.CartRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.ProductRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


//    lấy các sản phẩm đã selected
    public List<CartDetailDTO> getSelectedCartItems(UserDetails userDetails, List<Integer> listId) {


        Optional<List<CartDetail>> cartDetailsOptional = cartDetailRepository.findByCartDetailIdIn(listId);
        if (cartDetailsOptional.isEmpty()) {
            throw new RuntimeException("No cart items found");
        }
        List<CartDetail> cartDetails = cartDetailsOptional.get();
        // Chuyển đổi sang CartDetailDTO
        return cartDetails.stream()
                .map(cartDetail -> this.convertToCartDetailDTO(cartDetail))
                .collect(Collectors.toList());
    }
    // Lấy giỏ hàng của user
    public CartResponse getCart(UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }

        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToCartDTO(user.getCart());
    }

    public void addToCart(UserDetails userDetails, int productId, int quantity) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }
        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setCreateAt(new Date(System.currentTimeMillis()));
            cart.setUpdateAt(new Date(System.currentTimeMillis()));
            cart.setTotalPrices(0.0);
            cart.setListCartDetails(new ArrayList<>());
            cart = cartRepository.save(cart);
            user.setCart(cart);
        }
        System.out.println("cart: " + cart.toString());

        // Tìm sản phẩm
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Kiểm tra số lượng tồn kho
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Not enough product in stock");
        }

        Optional<CartDetail> existingCartDetail = cartDetailRepository.findByCartAndProduct(cart, product);

        CartDetail cartDetail;
        if (existingCartDetail.isPresent()) {
            cartDetail = existingCartDetail.get();
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
        } else {
            cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProduct(product);
            cartDetail.setQuantity(quantity);
            // Thêm cartDetail vào danh sách trong bộ nhớ
            cart.getListCartDetails().add(cartDetail);
        }
        cartDetail.setPrice(product.getSalePrice() > 0 ? product.getSalePrice() : product.getOriginalPrice());
        this.cartDetailRepository.save(cartDetail);
//        updateCartTotalPrice(cart);

    }

    // update quantity
    public CartDetail updateCartItemQuantity(UserDetails userDetails, int cartDetailId, int quantity) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }
        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));


        if (cartDetail.getProduct().getQuantity() < quantity) {
            throw new RuntimeException("Not enough product in stock");
        }

        cartDetail.setQuantity(quantity);
        cartDetail = cartDetailRepository.save(cartDetail);

//        updateCartTotalPrice(cartDetail.getCart());

        return cartDetail;
    }

    public void removeFromCart(UserDetails userDetails, int cartDetailId) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }

        this.userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CartDetail cartDetail = this.cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Cart cart = cartDetail.getCart();
        this.cartDetailRepository.delete(cartDetail);

//        updateCartTotalPrice(cart); // lỗi vì đã sử dụng trigger
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

    // cập nhật tổng giá của giỏ hàng (viết SQL triggers cũng đc)
    private void updateCartTotalPrice(Cart cart) {
        List<CartDetail> updatedCartDetails = cartDetailRepository.findByCart(cart);

        double totalPrice = updatedCartDetails.stream()
                .mapToDouble(cd -> cd.getPrice() * cd.getQuantity())
                .sum();

        cart.setTotalPrices(totalPrice);
        cart.setUpdateAt(new Date(System.currentTimeMillis()));
        cartRepository.save(cart);
    }

    // Cart => CartDTO
    private CartResponse convertToCartDTO(Cart cart) {
        if (cart == null)
            return null;
        CartResponse cartDTO = new CartResponse();
        cartDTO.setCartId(cart.getCartId());
        cartDTO.setCreateAt(cart.getCreateAt());
        cartDTO.setUpdateAt(cart.getUpdateAt());
        cartDTO.setTotalPrices(cart.getTotalPrices());
        return cartDTO;
    }

    public List<CartDetailDTO> getAllCartDetail(UserDetails userDetails) {
        if (userDetails == null)
            throw new RuntimeException("User not authenticated");

        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();
        if (cart == null) {
            return new ArrayList<>();
        }
        List<CartDetail> cartDetails = cartDetailRepository.findByCart(cart);
        return cartDetails.stream()
                .map(this::convertToCartDetailDTO)
                .collect(Collectors.toList());
    }

    // CartDetail => CartDetailDTO
    private CartDetailDTO convertToCartDetailDTO(CartDetail cartDetail) {
        CartDetailDTO dto = new CartDetailDTO();
        dto.setCartDetailId(cartDetail.getCartDetailId());
        dto.setQuantity(cartDetail.getQuantity());
        dto.setPrice(cartDetail.getPrice());
        dto.setProduct(convertToProductCartDTO(cartDetail.getProduct()));
        return dto;
    }

    // Product => ProductCartDTO
    private ProductCartResponse convertToProductCartDTO(Product product) {
        ProductCartResponse dto = new ProductCartResponse();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setOriginalPrice(product.getOriginalPrice());
        dto.setSalePrice(product.getSalePrice());
        dto.setProductionInfor(product.getProductionInfor());

        if (product.getListImages() != null && !product.getListImages().isEmpty()) {
            dto.setMainImage(product.getListImages().get(0).getLink());
        }

        return dto;
    }
}