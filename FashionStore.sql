/*
IF EXISTS (  
    SELECT name  
    FROM sys.databases  
    WHERE name = 'FashionStore1'  
)  

BEGIN  
		USE master
		
		alter database Handmade set single_user with rollback immediate
		drop database Handmade;
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
(N'Chuyển khoảng', N'Thanh Toán Online', 0),
(N'Thanh toán khi nhận hàng', N'Thanh toán tiền mặt khi nhận hàng', 10000);
GO

-- Insert vào bảng users
INSERT INTO users (user_name, first_name, last_name, email, password, phone_number, sex,is_active) 
VALUES (N'admin', N'Hiếu Đại Ka', N'A', N'admin@example.com', '$2a$12$TYBg.L1b5mq463Cyg4BU4.aeK/Emj79KzmfamvcNW8qQmIWC6X.fi', N'0987654321', N'Nam',1),
       (N'user', N'User', N'B', N'user@example.com', '$2a$12$TYBg.L1b5mq463Cyg4BU4.aeK/Emj79KzmfamvcNW8qQmIWC6X.fi', N'0976543210', N'Nữ',1),
       (N'lequocd', N'Lê Quốc', N'D', N'quocd@example.com', '$2a$12$TYBg.L1b5mq463Cyg4BU4.aeK/Emj79KzmfamvcNW8qQmIWC6X.fi', N'0912345678', N'Nam',1),
       (N'phamthie', N'Phạm Thị', N'E', N'thie@example.com', '$2a$12$TYBg.L1b5mq463Cyg4BU4.aeK/Emj79KzmfamvcNW8qQmIWC6X.fi', N'0934567890', N'Nữ',1),
       (N'hoangminh', N'Hoàng', N'Minh', N'minh@example.com', '$2a$12$TYBg.L1b5mq463Cyg4BU4.aeK/Emj79KzmfamvcNW8qQmIWC6X.fi', N'0967891234', N'Nam',1),
	   (N'shipper', N'Hiếu Đại Ka', N'A', N'shipper@example.com', '$2a$12$TYBg.L1b5mq463Cyg4BU4.aeK/Emj79KzmfamvcNW8qQmIWC6X.fi', N'0987654321', N'Nam',1);
GO

INSERT INTO Address (street_name, city_name, district_name, ward_name,user_id)
VALUES 
(N'123 Đường Lê Lợi', N'Hà Nội', N'Ba Đình', N'Phúc Xá',1),
(N'456 Đường Trần Hưng Đạo', N'TP. Hồ Chí Minh', N'Quận 1', N'Bến Nghé',2),
(N'789 Đường Nguyễn Trãi', N'Đà Nẵng', N'Hải Châu', N'Hòa Thuận Tây',3),
(N'321 Đường Phạm Văn Đồng', N'Hà Nội', N'Cầu Giấy', N'Dịch Vọng',4),
(N'654 Đường Nguyễn Văn Linh', N'Hải Phòng', N'Lê Chân', N'Vĩnh Niệm',5),
(N'987 Đường Võ Văn Kiệt', N'TP. Hồ Chí Minh', N'Quận 5', N'Phường 10',4),
(N'741 Đường Điện Biên Phủ', N'Cần Thơ', N'Ninh Kiều', N'An Hòa',2),
(N'852 Đường Lý Thường Kiệt', N'Bình Dương', N'Thủ Dầu Một', N'Phú Cường',1),
(N'159 Đường Quang Trung', N'Đà Lạt', N'Trạm Hành', N'Xuân Trường',1),
(N'357 Đường Trường Chinh', N'Nha Trang', N'Vĩnh Hải', N'Vĩnh Phước',2);

-- Insert vào bảng role
INSERT INTO role (role_name, description) VALUES 
(N'ADMIN', N'Quản trị viên'), 
(N'USER', N'Người mua hàng'), 
(N'SHIPEER', N'Shipper giao hàng');
GO
-- Insert vào bảng user_role
INSERT INTO user_role (user_id, role_id) VALUES 
(1, 1),(1,2), (2, 2), (3, 2),(3, 1), (4, 2), (5, 2),(6,3);
GO

INSERT INTO product (product_name, description, production_infor, original_price, sale_price, manufacture_date, quantity, avg_stars)
VALUES 
    (N'Áo sơ mi nam', N'Áo sơ mi nam dài tay, chất liệu cotton', N'Công ty TNHH Võ Nguyễn Đại Hiếu, chuyên cung cấp sản phẩm thời trang chất lượng cao', 300000, 250000, '2024-01-10', 100,1.2),
    (N'Áo thun nữ', N'Áo thun nữ cổ tròn, phong cách trẻ trung', N'Công ty TNHH Võ Nguyễn Đại Hiếu, chuyên cung cấp sản phẩm thời trang chất lượng cao', 200000, 180000, '2024-02-05', 120, 4.6),
    (N'Quần jean nam', N'Quần jean nam ống đứng, chất vải bền đẹp',N'Công ty TNHH Võ Nguyễn Đại Hiếu, chuyên cung cấp sản phẩm thời trang chất lượng cao', 450000, 400000, '2024-01-15', 80, 3),
    (N'Quần jean nam', N'Giày sneaker phong cách thể thao, phù hợp đi chơi, đi học',N'Công ty TNHH Võ Nguyễn Đại Hiếu, chuyên cung cấp sản phẩm thời trang chất lượng cao', 500000, 450000, '2024-01-20', 90, 4.8),
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

INSERT INTO cart ( create_at, update_at, total_prices) VALUES 
('2024-03-01', '2024-03-02', 380000),
( '2024-03-05', '2024-03-06', 220000),
( '2024-03-07', '2024-03-08', 170000),
( '2024-03-10', '2024-03-11', 210000),
('2024-03-12', '2024-03-13', 150000);
GO



-- Insert vào bảng cart_detail
INSERT INTO cart_detail (cart_id, product_id, quantity, price) VALUES 
(1, 1, 2, 360000), (1, 2, 1, 220000),
(2, 3, 1, 150000), (2, 4, 1, 210000),
(3, 5, 1, 170000), (4, 1, 1, 180000),
(5, 2, 1, 220000);
GO

-- Insert vào bảng orders
go
INSERT INTO orders (user_id, create_at, delivery_date, total_price, payment_type_id, shipping_method_id, shipping_address_id, status,is_pay) 
VALUES (3, '2024-02-20', '2024-02-25', 380000, 1, 1, 1, 'PENDING',0),
       (2, '2024-02-22', '2024-02-27', 220000, 2, 2, 2, 'PENDING',1),
       (3, '2024-02-23', '2024-02-28', 170000, 1, 3, 3, 'DELIVERED',0),
       (4, '2024-02-25', '2024-03-01', 210000, 2, 1, 4, 'SHIPPING',0),
       (5, '2024-02-28', '2024-03-04', 150000, 1, 2, 5, 'SHIPPING',0);
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
(1, 1, 5, N'Áo đẹp'),
(2, 2, 4, N'Chất liệu ok'),
(3, 3, 5, N'Áo đẹp'),
(4, 4, 4, N'QUần đẹp'),
(5, 5, 5, N'Hiếu đz ');
GO


GO
INSERT INTO [image](link, icon, image_name, product_id)
VALUES
-- Product 1
('https://drive.google.com/thumbnail?id=1jaYKEd4dArK6xHmALF-jM1O1TlW-Zl2Y', 1, N'p01_1', 1),
('https://drive.google.com/thumbnail?id=1-ZxGxBG4AZnoRNdcFZXIBJKLbM54fZtA', 0, N'p01_2', 1),
('https://drive.google.com/thumbnail?id=1j6N1H3bnYQ4-1lJNaPWjVMlcUOkaGy99', 0, N'p01_3', 1),
('https://drive.google.com/thumbnail?id=1oDzf_tcX9v7viG3Aonai1BpAiRv51Qnk', 0, N'p01_4', 1),

-- Product 2
('https://drive.google.com/thumbnail?id=1pG1mrn-I_qmKDyAOONF5-4TBnKjlBxE4', 1, N'p02_1', 2),
('https://drive.google.com/thumbnail?id=1mlEBU8IxAofhodJbN7r5dLtinVTX4L_M', 0, N'p02_2', 2),
('https://drive.google.com/thumbnail?id=1n4IacmWWMfNRbJuXCFO2vlUYwy9GvZdA', 0, N'p02_3', 2),

-- Product 3
('https://drive.google.com/thumbnail?id=1OVtqQuUihe3NaxHzsBou51KWXUjGyMuh', 1, N'p03_1', 3),
('https://drive.google.com/thumbnail?id=1bq3eC3tHvcGM0Ipo3B5CEippf6LfHTdQ', 0, N'p03_2', 3),
('https://drive.google.com/thumbnail?id=1rY-gnG3BwR8n_83gn3VsL6Iih1H7qLi0', 0, N'p03_3', 3),
('https://drive.google.com/thumbnail?id=1jja5-dQDpYrCbgErG3GyKhLCKojn1NP3', 0, N'p03_4', 3),

-- Product 4
('https://drive.google.com/thumbnail?id=1wsa73cmtMo3OIIhIQtHhWBNK4qsSdnDd', 1, N'p04_1', 4),
('https://drive.google.com/thumbnail?id=15F8P0gxnWsMTIIUxuI27CAQDiZjBgMwA', 0, N'p04_2', 4),

-- Product 5
('https://drive.google.com/thumbnail?id=1RIEzr8hZ1EZpJM3Enfeuij_GQp0-id4s', 1, N'p05_1', 5),
('https://drive.google.com/thumbnail?id=16jtVGL3LV0CUQT4TFgw4CtMQMSmZszYX', 0, N'p05_2', 5),
('https://drive.google.com/thumbnail?id=1jlMB5VA0Q1w-UoUL0COeXJB6205Y0lim', 0, N'p05_3', 5),

-- Product 6
('https://drive.google.com/thumbnail?id=1b1hW7lDEPApUYFVTtvyR7-8f2lnmNkl6', 1, N'p06_1', 6),
('https://drive.google.com/thumbnail?id=1GAEDMDDGhAqh177zxs87zLoC-N8iYWDA', 0, N'p06_2', 6),
('https://drive.google.com/thumbnail?id=1T2ZgZn4H5tuJ-rFK8B-7-5D-jUp1Q5Fy', 0, N'p06_3', 6),

-- Product 7
('https://drive.google.com/thumbnail?id=1FT6UP3YhxSHhUGbXBSwZWalB38yevrei', 1, N'p07_1', 7),
('https://drive.google.com/thumbnail?id=14dSd3vFkM1GVDqYVdFdvFAMzZkmdiMfP', 0, N'p07_2', 7),
('https://drive.google.com/thumbnail?id=1g8gaBfcsd0I7GPrm3-KG5gahkPtVz52n', 0, N'p07_3', 7),

-- Product 8
('https://drive.google.com/thumbnail?id=1Hw2MeF1OnG_LZAkOBkZ1Dn5hgkACXKP4', 1, N'p08_1', 8),
('https://drive.google.com/thumbnail?id=1VwzoUrVZpRXjFfJIYW4tXr9z1MHAxceS', 0, N'p08_2', 8),

-- Product 9
('https://drive.google.com/thumbnail?id=11FC8SZOIsBtcnaWrAvznw5VaxbuQL5q6', 1, N'p09_1', 9),
('https://drive.google.com/thumbnail?id=19uvA5aLbpK6QzquHwO5r80L76yGKSWqV', 0, N'p09_2', 9),
('https://drive.google.com/thumbnail?id=117iwnUqJEnv5Bgc5TULHASF_B6dVuC-H', 0, N'p09_3', 9),
('https://drive.google.com/thumbnail?id=1NZAiWRVcqPSm6-DsjwQSKxbMcL_d2M_C', 0, N'p09_4', 9),

-- Product 10
('https://drive.google.com/thumbnail?id=18Qgalh_wk7toQjXFGI64Lg95V5iFC52p', 1, N'p10_1', 10),
('https://drive.google.com/thumbnail?id=1HszspEzGSwzaZGtINbhbhI8c7N50Wsdw', 0, N'p10_2', 10);


--select users.password, users.user_name, user_role.role_id , role.role_name
--from users, user_role, role


-- nếu lười code backend thì xài thằng này luôn

-- Trigger khi thêm CartDetail (INSERT)
go
CREATE TRIGGER trg_after_insert_cart_detail
ON cart_detail
AFTER INSERT
AS
BEGIN
    DECLARE @cart_id INT;
    DECLARE @price DECIMAL(10, 2);
    DECLARE @quantity INT;

    -- Lấy cart_id, price và quantity của sản phẩm vừa được thêm
    SELECT @cart_id = cart_id, @price = price, @quantity = quantity FROM inserted;

    -- Cập nhật lại tổng giá trong bảng cart
    UPDATE cart
    SET total_prices = (SELECT SUM(price * quantity) FROM cart_detail WHERE cart_id = @cart_id)
    WHERE cart_id = @cart_id;
END;
GO

-- Trigger khi cập nhật CartDetail (UPDATE)
CREATE TRIGGER trg_after_update_cart_detail
ON cart_detail
AFTER UPDATE
AS
BEGIN
    DECLARE @cart_id INT;
    DECLARE @old_price DECIMAL(10, 2);
    DECLARE @new_price DECIMAL(10, 2);
    DECLARE @old_quantity INT;
    DECLARE @new_quantity INT;

    -- Lấy thông tin cũ và mới của cart_detail
    SELECT @cart_id = cart_id, @old_price = price, @old_quantity = quantity FROM deleted;
    SELECT @new_price = price, @new_quantity = quantity FROM inserted;

    -- Nếu giá hoặc số lượng thay đổi, cập nhật lại tổng giá
    IF (@old_price <> @new_price OR @old_quantity <> @new_quantity)
    BEGIN
        UPDATE cart
        SET total_prices = (SELECT SUM(price * quantity) FROM cart_detail WHERE cart_id = @cart_id)
        WHERE cart_id = @cart_id;
    END
END;
GO

-- Trigger khi xóa CartDetail (DELETE)
CREATE OR ALTER TRIGGER trg_after_delete_cart_detail
ON cart_detail
AFTER DELETE
AS
BEGIN
    DECLARE @cart_id INT;

    -- Lấy cart_id của sản phẩm bị xóa
    SELECT @cart_id = cart_id FROM deleted;

    -- Cập nhật lại tổng giá trong bảng cart, gán 0 nếu không còn cart_detail
    UPDATE cart
    SET total_prices = ISNULL((
        SELECT SUM(price * quantity) 
        FROM cart_detail 
        WHERE cart_id = @cart_id
    ), 0)
    WHERE cart_id = @cart_id;
END;
GO
-- Trigger khi thêm OrderDetail (INSERT)
CREATE TRIGGER trg_after_insert_order_detail
ON order_detail
AFTER INSERT
AS
BEGIN
    DECLARE @order_id INT;
    DECLARE @price DECIMAL(10, 2);
    DECLARE @quantity INT;
    DECLARE @product_id INT;

    -- Lấy order_id, price, quantity và product_id của order_detail vừa được thêm
    SELECT @order_id = order_id, @price = price, @quantity = quantity, @product_id = product_id 
    FROM inserted;

    -- Cập nhật tổng giá trong bảng orders
    UPDATE orders
    SET total_price = (
        SELECT SUM(price * quantity) 
        FROM order_detail 
        WHERE order_id = @order_id
    )
    WHERE order_id = @order_id;

    -- Cập nhật số lượng sản phẩm trong kho
    UPDATE product
    SET quantity = quantity - @quantity
    WHERE product_id = @product_id;
END;
GO

-- Trigger khi cập nhật OrderDetail (UPDATE)
CREATE TRIGGER trg_after_update_order_detail
ON order_detail
AFTER UPDATE
AS
BEGIN
    DECLARE @order_id INT;
    DECLARE @old_price DECIMAL(10, 2);
    DECLARE @new_price DECIMAL(10, 2);
    DECLARE @old_quantity INT;
    DECLARE @new_quantity INT;
    DECLARE @product_id INT;

    -- Lấy thông tin cũ và mới của order_detail
    SELECT @order_id = order_id, @old_price = price, @old_quantity = quantity, @product_id = product_id 
    FROM deleted;
    SELECT @new_price = price, @new_quantity = quantity 
    FROM inserted;

    -- Nếu giá hoặc số lượng thay đổi, cập nhật lại tổng giá
    IF (@old_price <> @new_price OR @old_quantity <> @new_quantity)
    BEGIN
        UPDATE orders
        SET total_price = (
            SELECT SUM(price * quantity) 
            FROM order_detail 
            WHERE order_id = @order_id
        )
        WHERE order_id = @order_id;

        -- Cập nhật số lượng sản phẩm trong kho
        UPDATE product
        SET quantity = quantity - (@new_quantity - @old_quantity)
        WHERE product_id = @product_id;
    END
END;
GO

-- Trigger khi xóa OrderDetail (DELETE)
CREATE TRIGGER trg_after_delete_order_detail
ON order_detail
AFTER DELETE
AS
BEGIN
    DECLARE @order_id INT;
    DECLARE @product_id INT;
    DECLARE @quantity INT;

    -- Lấy order_id, product_id và quantity của order_detail bị xóa
    SELECT @order_id = order_id, @product_id = product_id, @quantity = quantity 
    FROM deleted;

    -- Cập nhật tổng giá trong bảng orders
    UPDATE orders
    SET total_price = (
        SELECT ISNULL(SUM(price * quantity), 0) 
        FROM order_detail 
        WHERE order_id = @order_id
    )
    WHERE order_id = @order_id;

    -- Cập nhật số lượng sản phẩm trong kho (hoàn lại số lượng)
    UPDATE product
    SET quantity = quantity + @quantity
    WHERE product_id = @product_id;
END;
GO