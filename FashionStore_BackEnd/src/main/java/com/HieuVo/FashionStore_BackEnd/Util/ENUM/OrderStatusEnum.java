package com.HieuVo.FashionStore_BackEnd.Util.ENUM;


public enum OrderStatusEnum {

  PENDING("Chưa xử lý"),
  SHIPPING("Đang giao"),
  DELIVERED("Đã giao"),
  CANCELLED("Đã hủy");

  private final String label;

  OrderStatusEnum(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public static OrderStatusEnum fromLabel(String label) {
    for (OrderStatusEnum status : OrderStatusEnum.values()) {
      if (status.label.equalsIgnoreCase(label)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Không tìm thấy trạng thái: " + label);
  }
}

