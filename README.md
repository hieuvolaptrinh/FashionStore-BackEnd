# FashionStore Backend - Spring Boot Project

**Chủ sở hữu:** [Hieuvolaptrinh](https://github.com/hieuvolaptrinh)

## Mô tả dự án:
**FashionStore** là website bán hàng thời trang trực tuyến với các tính năng như:
- Mua sắm trực tuyến: Cho phép người dùng duyệt sản phẩm, thêm vào giỏ hàng và thực hiện thanh toán.
- **Giỏ hàng:** Quản lý các sản phẩm đã chọn, thay đổi số lượng hoặc xóa sản phẩm trong giỏ.
- **Thanh toán:** Hỗ trợ thanh toán qua MoMo và VNPAY.
- **Hệ thống quản trị (Admin):** Cung cấp giao diện để quản lý sản phẩm, đơn hàng, người dùng và phân quyền tài khoản.
- **Xác thực và phân quyền:** Phân quyền tài khoản với JWT và Spring Security.

## Công nghệ sử dụng:
### **Frontend:**
- **React:** Framework chính để xây dựng giao diện người dùng.
- **TypeScript:** Đảm bảo an toàn về kiểu dữ liệu trong suốt quá trình phát triển.
- **Bootstrap:** Tạo giao diện responsive và hiện đại.
- **MaterialUI:** Cung cấp các component UI đẹp và dễ sử dụng.

### **Backend:**
- **Java Spring Boot:** Framework backend, xử lý logic nghiệp vụ và API.
- **Spring Security:** Quản lý xác thực và phân quyền người dùng.
- **Spring Data JPA:** Quản lý kết nối và thao tác dữ liệu với cơ sở dữ liệu.
- **SQL Server:** Cơ sở dữ liệu lưu trữ thông tin sản phẩm và đơn hàng.
- **RESTful API:** Giao tiếp giữa frontend và backend qua các API.

### **Khác:**
- **JWT:** Cung cấp cơ chế bảo mật cho các API.
- **BCrypt:** Mã hóa mật khẩu người dùng.
- **VNPAY API:** Tích hợp thanh toán qua VNPAY.
- **Postman:** Kiểm tra và thử nghiệm API.
- **GitHub:** Quản lý mã nguồn và các đóng góp từ cộng đồng.

## Cài đặt và sử dụng:

### Yêu cầu hệ thống:
- Java 11 hoặc cao hơn
- Gradle 6.0 hoặc cao hơn
- SQL Server (hoặc các cơ sở dữ liệu tương thích)

### Cài đặt dự án:
1. **Clone repository:**
   ```bash
   git clone https://github.com/hieuvolaptrinh/FashionStore-BackEnd.git
