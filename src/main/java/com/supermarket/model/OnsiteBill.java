package com.supermarket.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OnsiteBill extends Bill {
    private OnsiteCart onsiteCart;
    private Staff salesStaff;
    
    public OnsiteBill() {
        super();
    }
    
    public OnsiteBill(int id, String paymentMethod, String status, Timestamp date,
                      Customer customer, BigDecimal totalAmount,
                      OnsiteCart onsiteCart, Staff salesStaff) {
        super(id, paymentMethod, status, date, customer, totalAmount);
        this.onsiteCart = onsiteCart;
        this.salesStaff = salesStaff;
    }
    
    public OnsiteBill(String paymentMethod, String status, Timestamp date,
                      Customer customer, BigDecimal totalAmount,
                      OnsiteCart onsiteCart, Staff salesStaff) {
        super(paymentMethod, status, date, customer, totalAmount);
        this.onsiteCart = onsiteCart;
        this.salesStaff = salesStaff;
    }
    
    public OnsiteCart getOnsiteCart() {
        return onsiteCart;
    }
    
    public void setOnsiteCart(OnsiteCart onsiteCart) {
        this.onsiteCart = onsiteCart;
    }
    
    public Staff getSalesStaff() {
        return salesStaff;
    }
    
    public void setSalesStaff(Staff salesStaff) {
        this.salesStaff = salesStaff;
    }
    
    public Integer getOnsiteCartId() {
        return onsiteCart != null ? onsiteCart.getId() : null;
    }
    
    public Integer getSalesStaffId() {
        return salesStaff != null ? salesStaff.getId() : null;
    }
    
    @Override
    public String toString() {
        return "OnsiteBill{" +
                "id=" + getId() +
                ", paymentMethod='" + getPaymentMethod() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", date=" + getDate() +
                ", customer=" + (getCustomer() != null ? getCustomer().getFullname() : "null") +
                ", totalAmount=" + getTotalAmount() +
                ", salesStaff=" + (salesStaff != null ? salesStaff.getFullname() : "null") +
                '}';
    }
}
