package com.supermarket.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OnlineBill extends Bill {
    private String deliveryAddress;
    private OnlineCart onlineCart;
    private Staff deliveryStaff;
    private Staff warehouseStaff;
    
    public OnlineBill() {
        super();
    }
    
    public OnlineBill(int id, String paymentMethod, String status, Timestamp date,
                      Customer customer, BigDecimal totalAmount, String deliveryAddress,
                      OnlineCart onlineCart, Staff deliveryStaff, Staff warehouseStaff) {
        super(id, paymentMethod, status, date, customer, totalAmount);
        this.deliveryAddress = deliveryAddress;
        this.onlineCart = onlineCart;
        this.deliveryStaff = deliveryStaff;
        this.warehouseStaff = warehouseStaff;
    }
    
    public OnlineBill(String paymentMethod, String status, Timestamp date,
                      Customer customer, BigDecimal totalAmount, String deliveryAddress,
                      OnlineCart onlineCart, Staff deliveryStaff, Staff warehouseStaff) {
        super(paymentMethod, status, date, customer, totalAmount);
        this.deliveryAddress = deliveryAddress;
        this.onlineCart = onlineCart;
        this.deliveryStaff = deliveryStaff;
        this.warehouseStaff = warehouseStaff;
    }
    
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    
    public OnlineCart getOnlineCart() {
        return onlineCart;
    }
    
    public void setOnlineCart(OnlineCart onlineCart) {
        this.onlineCart = onlineCart;
    }
    
    public Staff getDeliveryStaff() {
        return deliveryStaff;
    }
    
    public void setDeliveryStaff(Staff deliveryStaff) {
        this.deliveryStaff = deliveryStaff;
    }
    
    public Staff getWarehouseStaff() {
        return warehouseStaff;
    }
    
    public void setWarehouseStaff(Staff warehouseStaff) {
        this.warehouseStaff = warehouseStaff;
    }
    
    // Convenience methods to get staff IDs
    public Integer getDeliveryStaffId() {
        return deliveryStaff != null ? deliveryStaff.getId() : null;
    }
    
    public Integer getWarehouseStaffId() {
        return warehouseStaff != null ? warehouseStaff.getId() : null;
    }
    
    public Integer getOnlineCartId() {
        return onlineCart != null ? onlineCart.getId() : null;
    }
    
    @Override
    public String toString() {
        return "OnlineBill{" +
                "id=" + getId() +
                ", paymentMethod='" + getPaymentMethod() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", date=" + getDate() +
                ", customer=" + (getCustomer() != null ? getCustomer().getFullname() : "null") +
                ", totalAmount=" + getTotalAmount() +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", deliveryStaff=" + (deliveryStaff != null ? deliveryStaff.getFullname() : "null") +
                ", warehouseStaff=" + (warehouseStaff != null ? warehouseStaff.getFullname() : "null") +
                '}';
    }
}
