package com.supermarket.model;

import java.math.BigDecimal;
import java.util.List;

public class OnlineCart extends Cart {
    
    public OnlineCart() {
        super();
        this.setType("online");
    }
    
    public OnlineCart(int id, BigDecimal provisionalPrice, int itemCount, 
                      List<CartItem> items, Customer customer) {
        super(id, provisionalPrice, itemCount, items, customer, "online");
    }
    
    public OnlineCart(BigDecimal provisionalPrice, int itemCount, 
                      List<CartItem> items, Customer customer) {
        super(provisionalPrice, itemCount, items, customer, "online");
    }
    
    public OnlineCart(Customer customer) {
        super();
        this.setCustomer(customer);
        this.setType("online");
    }
    
    @Override
    public String toString() {
        return "OnlineCart{" +
                "id=" + getId() +
                ", provisionalPrice=" + getProvisionalPrice() +
                ", itemCount=" + getItemCount() +
                ", customer=" + (getCustomer() != null ? getCustomer().getFullname() : "null") +
                '}';
    }
}
