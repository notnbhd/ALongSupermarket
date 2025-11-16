package com.supermarket.model;

import java.math.BigDecimal;

public class CustomerStat {
    private int totalBill;
    private BigDecimal totalAmount;
    
    public CustomerStat() {
        this.totalBill = 0;
        this.totalAmount = BigDecimal.ZERO;
    }
    
    public CustomerStat(int totalBill, BigDecimal totalAmount) {
        this.totalBill = totalBill;
        this.totalAmount = totalAmount != null ? totalAmount : BigDecimal.ZERO;
    }
    
    public int getTotalBill() {
        return totalBill;
    }
    
    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    @Override
    public String toString() {
        return "CustomerStat{" +
                "totalBill=" + totalBill +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
