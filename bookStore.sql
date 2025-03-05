-- Insert vào bảng shipping_method
INSERT INTO shipping_method (shipping_method_name, description, fee) VALUES
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

-- Insert vào bảng country
INSERT INTO country (country_name) VALUES 
(N'Việt Nam'), (N'Mỹ'), (N'Nhật Bản'), (N'Anh'), (N'Pháp');
GO

-- Insert vào bảng city
INSERT INTO city (country_id, city_name) VALUES 
(1, N'Hà Nội'), (1, N'Hồ Chí Minh'), (2, N'New York'), (3, N'Tokyo'), (4, N'London');
GO

-- Insert vào bảng district
INSERT INTO district (city_id, district_name) VALUES 
(1, N'Ba Đình'), (1, N'Hoàn Kiếm'), (2, N'Quận 1'), (3, N'Shibuya'), (4, N'Westminster');
GO

-- Insert vào bảng ward
INSERT INTO ward (district_id, ward_name) VALUES 
(1, N'Kim Mã'), (2, N'Hàng Bông'), (3, N'Bến Nghé'), (4, N'Harajuku'), (5, N'Soho');
GO

-- Insert vào bảng users
INSERT INTO users (user_name, first_name, last_name, email, password, phone_number, sex, address_id) 
VALUES (N'nguyenvana', N'Nguyễn Văn', N'A', N'vana@example.com', N'pass123', N'0987654321', N'Nam', 1),
       (N'tranthib', N'Trần Thị', N'B', N'thib@example.com', N'pass456', N'0976543210', N'Nữ', 2),
       (N'lequocd', N'Lê Quốc', N'D', N'quocd@example.com', N'pass789', N'0912345678', N'Nam', 3),
       (N'phamthie', N'Phạm Thị', N'E', N'thie@example.com', N'pass321', N'0934567890', N'Nữ', 4),
       (N'hoangminh', N'Hoàng', N'Minh', N'minh@example.com', N'pass654', N'0967891234', N'Nam', 5);
GO

-- Insert vào bảng role
INSERT INTO role (role_name, description) VALUES 
(N'Admin', N'Quản trị viên'), 
(N'Khách hàng', N'Người mua hàng'), 
(N'Nhân viên', N'Nhân viên cửa hàng');
GO

-- Insert vào bảng user_role
INSERT INTO user_role (user_id, role_id) VALUES 
(1, 1), (2, 2), (3, 2), (4, 2), (5, 3);
GO

-- Insert vào bảng product
INSERT INTO product (product_name, description, author, original_price, sale_price, manufacture_date, quantity, avg_stars)
VALUES (N'Lập Trình Java', N'Học Java từ cơ bản đến nâng cao', N'Nguyễn Văn A', 200000, 180000, '2023-01-01', 50, 4.5),
       (N'Spring Boot Toàn Tập', N'Hướng dẫn đầy đủ về Spring Boot', N'Trần Thị B', 250000, 220000, '2023-02-15', 40, 4.7),
       (N'Cấu Trúc Dữ Liệu', N'Tư duy thuật toán và dữ liệu', N'Lê Quốc D', 180000, 150000, '2022-10-10', 60, 4.3),
       (N'Học SQL Server', N'Từ cơ bản đến chuyên sâu', N'Phạm Thị E', 230000, 210000, '2023-03-05', 30, 4.6),
       (N'Thuật Toán Cơ Bản', N'Những thuật toán quan trọng trong lập trình', N'Hoàng Minh', 190000, 170000, '2023-05-20', 55, 4.4);
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
INSERT INTO orders (user_id, create_at, delivery_date, total_price, payment_type_id, shipping_method_id, shipping_ward_id, status) 
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

INSERT INTO type (type_name) VALUES 
(N'Khoa học máy tính'),
(N'Phát triển phần mềm'),
(N'Lập trình'),
(N'Cơ sở dữ liệu'),
(N'Kỹ thuật phần mềm');
GO


INSERT INTO product_type (type_id, product_id) VALUES 
(1, 1), -- "Lập Trình Java" thuộc "Khoa học máy tính"
(2, 2), -- "Spring Boot Toàn Tập" thuộc "Phát triển phần mềm"
(3, 1), -- "Lập Trình Java" thuộc "Lập trình"
(3, 3), -- "Cấu Trúc Dữ Liệu" thuộc "Lập trình"
(4, 4), -- "Học SQL Server" thuộc "Cơ sở dữ liệu"
(5, 2), -- "Spring Boot Toàn Tập" thuộc "Kỹ thuật phần mềm"
(5, 5); -- "Thuật Toán Cơ Bản" thuộc "Kỹ thuật phần mềm"
GO
