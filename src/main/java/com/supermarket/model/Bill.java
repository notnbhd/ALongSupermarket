package com.supermarket.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Bill {
    private int id;
    private String paymentMethod;
    private String status;
    private Timestamp date;
    private Customer customer;
    private BigDecimal totalAmount;
    
    public Bill() {
        this.totalAmount = BigDecimal.ZERO;
    }
    
    public Bill(int id, String paymentMethod, String status, Timestamp date, 
                Customer customer, BigDecimal totalAmount) {
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.date = date;
        this.customer = customer;
        this.totalAmount = totalAmount != null ? totalAmount : BigDecimal.ZERO;
    }
    
    public Bill(String paymentMethod, String status, Timestamp date, 
                Customer customer, BigDecimal totalAmount) {
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.date = date;
        this.customer = customer;
        this.totalAmount = totalAmount != null ? totalAmount : BigDecimal.ZERO;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Timestamp getDate() {
        return date;
    }
    
    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Integer getCustomerId() {
        return customer != null ? customer.getId() : null;
    }
    
    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                ", date=" + date +
                ", customer=" + (customer != null ? customer.getFullname() : "null") +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
