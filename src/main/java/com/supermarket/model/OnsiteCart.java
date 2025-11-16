package com.supermarket.model;

import java.math.BigDecimal;
import java.util.List;


public class OnsiteCart extends Cart {
    
    public OnsiteCart() {
        super();
        this.setType("onsite");
    }
    
    public OnsiteCart(int id, BigDecimal provisionalPrice, int itemCount, 
                      List<CartItem> items, Customer customer) {
        super(id, provisionalPrice, itemCount, items, customer, "onsite");
    }
    
    // Constructor without ID
    public OnsiteCart(BigDecimal provisionalPrice, int itemCount, 
                      List<CartItem> items, Customer customer) {
        super(provisionalPrice, itemCount, items, customer, "onsite");
    }
    
    public OnsiteCart(Customer customer) {
        super();
        this.setCustomer(customer);
        this.setType("onsite");
    }
    
    @Override
    public String toString() {
        return "OnsiteCart{" +
                "id=" + getId() +
                ", provisionalPrice=" + getProvisionalPrice() +
                ", itemCount=" + getItemCount() +
                ", customer=" + (getCustomer() != null ? getCustomer().getFullname() : "null") +
                '}';
    }
}
