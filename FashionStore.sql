/*
IF EXISTS (  
    SELECT name  
    FROM sys.databases  
    WHERE name = 'FashionStore1'  
)  

BEGIN  
		USE master
		
		alter database FashionStore1 set single_user with rollback immediate
		drop database FashionStore1;
END  
GO
CREATE DATABASE FashionStore1
GO
USE FashionStore1;
GO
*/
-- Insert vào bảng shipping_method
INSERT INTO shipping_method(shipping_method_name, description, fee) VALUES
(N'Giao hàng nhanh', N'Nhận hàng trong 1-2 ngày', 30000),
(N'Giao hàng tiêu chuẩn', N'Nhận hàng trong 3-5 ngày', 20000),
(N'Giao hàng tiết kiệm', N'Nhận hàng trong 5-7 ngày', 10000),
(N'Nhận tại cửa hàng', N'Đến cửa hàng nhận hàng', 0),
(N'Giao hàng hỏa tốc', N'Nhận hàng trong 24 giờ', 50000);
GO

-- Insert vào bảng payment_type
INSERT INTO payment_type (payment_type_name, description, fee) VALUES
(N'Thanh toán khi nhận hàng', N'Thanh toán tiền mặt khi nhận hàng', 0),
(N'Thẻ tín dụng', N'Thanh toán qua thẻ Visa/MasterCard', 10000),
(N'Chuyển khoản ngân hàng', N'Chuyển khoản qua ngân hàng nội địa', 5000),
(N'Ví điện tử', N'Thanh toán qua Momo, ZaloPay, ShopeePay', 5000),
(N'PayPal', N'Thanh toán quốc tế qua PayPal', 15000);
GO


INSERT INTO Address (street_name, city_name, district_name, ward_name)
VALUES 
(N'123 Đường Lê Lợi', N'Hà Nội', N'Ba Đình', N'Phúc Xá'),
(N'456 Đường Trần Hưng Đạo', N'TP. Hồ Chí Minh', N'Quận 1', N'Bến Nghé'),
(N'789 Đường Nguyễn Trãi', N'Đà Nẵng', N'Hải Châu', N'Hòa Thuận Tây'),
(N'321 Đường Phạm Văn Đồng', N'Hà Nội', N'Cầu Giấy', N'Dịch Vọng'),
(N'654 Đường Nguyễn Văn Linh', N'Hải Phòng', N'Lê Chân', N'Vĩnh Niệm'),
(N'987 Đường Võ Văn Kiệt', N'TP. Hồ Chí Minh', N'Quận 5', N'Phường 10'),
(N'741 Đường Điện Biên Phủ', N'Cần Thơ', N'Ninh Kiều', N'An Hòa'),
(N'852 Đường Lý Thường Kiệt', N'Bình Dương', N'Thủ Dầu Một', N'Phú Cường'),
(N'159 Đường Quang Trung', N'Đà Lạt', N'Trạm Hành', N'Xuân Trường'),
(N'357 Đường Trường Chinh', N'Nha Trang', N'Vĩnh Hải', N'Vĩnh Phước');

-- Insert vào bảng role
INSERT INTO role (role_name, description) VALUES 
(N'ADMIN', N'Quản trị viên'), 
(N'USER', N'Người mua hàng'), 
(N'STAFF', N'Nhân viên cửa hàng');
GO
-- Insert vào bảng users
INSERT INTO users (user_name, first_name, last_name, email, password, phone_number, sex, address_id,is_active) 
VALUES (N'hieudaika', N'Hiếu Đại Ka', N'A', N'admin@example.com', N'$2a$12$2.4hPnA9HII.Hzt3i1J2O.Bmhm0N.LRUTmChV4/ToBzveXZUV9kF6', N'0987654321', N'Nam', 1,1),
       (N'user', N'User', N'B', N'user@example.com', N'$2a$12$2.4hPnA9HII.Hzt3i1J2O.Bmhm0N.LRUTmChV4/ToBzveXZUV9kF6', N'0976543210', N'Nữ', 2,1),
       (N'lequocd', N'Lê Quốc', N'D', N'quocd@example.com', N'$2a$12$2.4hPnA9HII.Hzt3i1J2O.Bmhm0N.LRUTmChV4/ToBzveXZUV9kF6', N'0912345678', N'Nam', 3,1),
       (N'phamthie', N'Phạm Thị', N'E', N'thie@example.com', N'$2a$12$2.4hPnA9HII.Hzt3i1J2O.Bmhm0N.LRUTmChV4/ToBzveXZUV9kF6', N'0934567890', N'Nữ', 4,1),
       (N'hoangminh', N'Hoàng', N'Minh', N'minh@example.com', N'$2a$12$2.4hPnA9HII.Hzt3i1J2O.Bmhm0N.LRUTmChV4/ToBzveXZUV9kF6', N'0967891234', N'Nam', 5,1);
GO




-- Insert vào bảng user_role
INSERT INTO user_role (user_id, role_id) VALUES 
(1, 1), (2, 2), (3, 2), (4, 2), (5, 3);
GO

INSERT INTO product (product_name, description, production_infor, original_price, sale_price, manufacture_date, quantity, avg_stars)
VALUES 
    (N'Áo sơ mi nam', N'Áo sơ mi nam dài tay, chất liệu cotton', N'Công ty TNHH Võ Nguyễn Đại Hiếu, chuyên cung cấp sản phẩm thời trang chất lượng cao', 300000, 250000, '2024-01-10', 100,1.2),
    (N'Áo thun nữ', N'Áo thun nữ cổ tròn, phong cách trẻ trung', N'Công ty TNHH Võ Nguyễn Đại Hiếu, chuyên cung cấp sản phẩm thời trang chất lượng cao', 200000, 180000, '2024-02-05', 120, 4.6),
    (N'Quần jean nam', N'Quần jean nam ống đứng, chất vải bền đẹp',N'Công ty TNHH Võ Nguyễn Đại Hiếu, chuyên cung cấp sản phẩm thời trang chất lượng cao', 450000, 400000, '2024-01-15', 80, 3),
    (N'Giày sneaker', N'Giày sneaker phong cách thể thao, phù hợp đi chơi, đi học',N'Công ty TNHH Võ Nguyễn Đại Hiếu, chuyên cung cấp sản phẩm thời trang chất lượng cao', 500000, 450000, '2024-01-20', 90, 4.8),
    (N'Giày cao gót nữ', N'Giày cao gót nữ thanh lịch, gót 5cm',N'Công ty TNHH Võ Nguyễn Đại Hiếu, chuyên cung cấp sản phẩm thời trang chất lượng cao', 550000, 500000, '2024-02-01', 70, 4.6),
    (N'Áo khoác da nam', N'Áo khoác da cao cấp, phong cách lịch lãm', N'Công ty TNHH Võ Nguyễn Đại Hiếu, chuyên cung cấp sản phẩm thời trang chất lượng cao', 800000, 750000, '2024-02-10', 50, 2),
    (N'Váy dạ hội', N'Váy dạ hội sang trọng, chất liệu cao cấp', N'Công ty TNHH Võ Nguyễn Đại Hiếu, chuyên cung cấp sản phẩm thời trang chất lượng cao', 1200000, 1100000, '2024-03-01', 40, 1.7),
    (N'Túi xách nữ', N'Túi xách nữ hàng hiệu, kiểu dáng thanh lịch', N'Công ty TNHH Võ Nguyễn Đại Hiếu, chuyên cung cấp sản phẩm thời trang chất lượng cao', 600000, 550000, '2024-03-10', 60, 4.7),
    (N'Kính mát nam', N'Kính mát chống tia UV, phong cách hiện đại', N'Công ty TNHH Võ Nguyễn Đại Hiếu, chuyên cung cấp sản phẩm thời trang chất lượng cao', 300000, 270000, '2024-03-15', 75, 4.6),
    (N'Đồng hồ nam', N'Đồng hồ thời trang, chống nước, bảo hành 2 năm',N'Công ty TNHH Võ Nguyễn Đại Hiếu, chuyên cung cấp sản phẩm thời trang chất lượng cao', 1500000, 1400000, '2024-03-20', 30, 4.9);
GO

INSERT INTO type (type_name) VALUES 
    (N'Áo quần nam'),
    (N'Áo quần nữ'),
    (N'Giày dép'),
    (N'Phụ kiện');
GO

-- Insert vào bảng product
INSERT INTO product_type (type_id, product_id) VALUES 
    (1, 1), -- Áo sơ mi nam -> Áo quần nam
    (2, 2), -- Áo thun nữ -> Áo quần nữ
    (1, 3), -- Quần jean nam -> Áo quần nam
    (3, 4), -- Giày sneaker -> Giày dép
    (3, 5), -- Giày cao gót nữ -> Giày dép
    (1, 6), -- Áo khoác da nam -> Áo quần nam
    (2, 7), -- Váy dạ hội -> Áo quần nữ
    (4, 8), -- Túi xách nữ -> Phụ kiện
    (4, 9), -- Kính mát nam -> Phụ kiện
    (4, 10); -- Đồng hồ nam -> Phụ kiện
GO


-- Insert vào bảng cart
INSERT INTO cart (user_id, create_at, update_at, total_prices) VALUES 
(1, '2024-03-01', '2024-03-02', 380000),
(2, '2024-03-05', '2024-03-06', 220000),
(3, '2024-03-07', '2024-03-08', 170000),
(4, '2024-03-10', '2024-03-11', 210000),
(5, '2024-03-12', '2024-03-13', 150000);
GO

-- Insert vào bảng cart_detail
INSERT INTO cart_detail (cart_id, product_id, quantity, price) VALUES 
(1, 1, 2, 360000), (1, 2, 1, 220000),
(2, 3, 1, 150000), (2, 4, 1, 210000),
(3, 5, 1, 170000), (4, 1, 1, 180000),
(5, 2, 1, 220000);
GO

-- Insert vào bảng orders
INSERT INTO orders (user_id, create_at, delivery_date, total_price, payment_type_id, shipping_method_id, shipping_address_id, status) 
VALUES (1, '2024-02-20', '2024-02-25', 380000, 1, 1, 1, N'Đã giao'),
       (2, '2024-02-22', '2024-02-27', 220000, 2, 2, 2, N'Chờ xử lý'),
       (3, '2024-02-23', '2024-02-28', 170000, 1, 3, 3, N'Đã giao'),
       (4, '2024-02-25', '2024-03-01', 210000, 2, 1, 4, N'Đang vận chuyển'),
       (5, '2024-02-28', '2024-03-04', 150000, 1, 2, 5, N'Đã giao');
GO

-- Insert vào bảng order_detail
INSERT INTO order_detail (order_id, product_id, quantity, price) VALUES 
(1, 1, 2, 360000), (1, 2, 1, 220000),
(2, 3, 1, 150000), (2, 4, 1, 210000),
(3, 5, 1, 170000), (4, 1, 1, 180000),
(5, 2, 1, 220000);
GO

-- Insert vào bảng review
INSERT INTO review (user_id, product_id, stars, content) VALUES 
(1, 1, 5, N'Sách rất hay!'),
(2, 2, 4, N'Hướng dẫn chi tiết, dễ hiểu'),
(3, 3, 5, N'Tư duy thuật toán tuyệt vời'),
(4, 4, 4, N'Sách về SQL đầy đủ thông tin'),
(5, 5, 5, N'Những thuật toán rất hữu ích cho lập trình viên');
GO


GO

INSERT INTO [image](link,icon,image_name,product_id)
values('product-1.jpg',1,N'Ảnh 1.1',1),
('product-2.jpg',0,N'Ảnh 2',1),
('product-3.jpg',0,N'Ảnh 2',1),
('product-4.jpg',0,N'Ảnh 2',2),
('product-5.jpg',1,N'Ảnh 2',2),
('product-6.jpg',1,N'Ảnh 1.1',1),
('product-7.jpg',1,N'Ảnh 1.1',4),
('product-7.jpg',1,N'Ảnh 1.1',5),
('product-9.jpg',1,N'Ảnh 1.1',6),
('product-5.jpg',1,N'Ảnh 1.1',6),
('product-4.jpg',1,N'Ảnh 1.1',7),
('product-2.jpg',1,N'Ảnh 1.1',8),
('product-1.jpg',1,N'Ảnh 1.1',9);


select users.password, users.user_name, user_role.role_id , role.role_name
from users, user_role, role