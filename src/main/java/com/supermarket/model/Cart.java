package com.supermarket.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Cart {
    private int id;
    private BigDecimal provisionalPrice;
    private int itemCount;
    private List<CartItem> items;
    private Customer customer;
    private String type; 
    
    public Cart() {
        this.provisionalPrice = BigDecimal.ZERO;
        this.itemCount = 0;
        this.items = new ArrayList<>();
        this.type = "onsite"; 
    }
    
    public Cart(int id, BigDecimal provisionalPrice, int itemCount, 
                List<CartItem> items, Customer customer, String type) {
        this.id = id;
        this.provisionalPrice = provisionalPrice != null ? provisionalPrice : BigDecimal.ZERO;
        this.itemCount = itemCount;
        this.items = items != null ? items : new ArrayList<>();
        this.customer = customer;
        this.type = type != null ? type : "onsite";
    }
    
    public Cart(BigDecimal provisionalPrice, int itemCount, 
                List<CartItem> items, Customer customer, String type) {
        this.provisionalPrice = provisionalPrice != null ? provisionalPrice : BigDecimal.ZERO;
        this.itemCount = itemCount;
        this.items = items != null ? items : new ArrayList<>();
        this.customer = customer;
        this.type = type != null ? type : "onsite";
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public BigDecimal getProvisionalPrice() {
        return provisionalPrice;
    }
    
    public void setProvisionalPrice(BigDecimal provisionalPrice) {
        this.provisionalPrice = provisionalPrice;
    }
    
    public int getItemCount() {
        return itemCount;
    }
    
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
    
    public List<CartItem> getItems() {
        return items;
    }
    
    public void setItems(List<CartItem> items) {
        this.items = items;
        if (items != null) {
            this.itemCount = items.size();
        }
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public void addItem(CartItem item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
        this.itemCount = this.items.size();
        recalculateProvisionalPrice();
    }
    
    public void removeItem(CartItem item) {
        if (this.items != null) {
            this.items.remove(item);
            this.itemCount = this.items.size();
            recalculateProvisionalPrice();
        }
    }
    
    public void recalculateProvisionalPrice() {
        if (this.items == null || this.items.isEmpty()) {
            this.provisionalPrice = BigDecimal.ZERO;
            return;
        }
        
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : this.items) {
            total = total.add(item.getSubtotal());
        }
        this.provisionalPrice = total;
    }
    
    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", provisionalPrice=" + provisionalPrice +
                ", itemCount=" + itemCount +
                ", customer=" + (customer != null ? customer.getFullname() : "null") +
                '}';
    }
}
