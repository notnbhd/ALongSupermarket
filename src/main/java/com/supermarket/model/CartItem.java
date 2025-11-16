package com.supermarket.model;

import java.math.BigDecimal;

public class CartItem {
    private int id;
    private BigDecimal quantity;
    private BigDecimal price;
    private Item item;
    
    public CartItem() {
        this.quantity = BigDecimal.ZERO;
        this.price = BigDecimal.ZERO;
    }
    
    public CartItem(int id, BigDecimal quantity, BigDecimal price, Item item) {
        this.id = id;
        this.quantity = quantity != null ? quantity : BigDecimal.ZERO;
        this.price = price != null ? price : BigDecimal.ZERO;
        this.item = item;
    }
    
    public CartItem(BigDecimal quantity, BigDecimal price, Item item) {
        this.quantity = quantity != null ? quantity : BigDecimal.ZERO;
        this.price = price != null ? price : BigDecimal.ZERO;
        this.item = item;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public BigDecimal getQuantity() {
        return quantity;
    }
    
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Item getItem() {
        return item;
    }
    
    public void setItem(Item item) {
        this.item = item;
    }
    
    public BigDecimal getSubtotal() {
        if (quantity != null && price != null) {
            return quantity.multiply(price);
        }
        return BigDecimal.ZERO;
    }
    
    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", item=" + (item != null ? item.getName() : "null") +
                ", subtotal=" + getSubtotal() +
                '}';
    }
}
