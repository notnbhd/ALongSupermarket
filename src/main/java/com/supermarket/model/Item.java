package com.supermarket.model;

import java.math.BigDecimal;

public class Item {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private String unit;
    
    public Item() {
        this.price = BigDecimal.ZERO;
    }
    
    public Item(int id, String name, String description, BigDecimal price, String unit) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price != null ? price : BigDecimal.ZERO;
        this.unit = unit;
    }
    
    public Item(String name, String description, BigDecimal price, String unit) {
        this.name = name;
        this.description = description;
        this.price = price != null ? price : BigDecimal.ZERO;
        this.unit = unit;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", unit='" + unit + '\'' +
                '}';
    }
}
