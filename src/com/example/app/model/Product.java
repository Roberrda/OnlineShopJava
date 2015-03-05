package com.example.app.model;

public class Product {

    private int id;
    private String name;
    private String description;
    private double costPrice;
    private double salePrice;
    private int quantity;

    public Product(int id, String n, String d, double cp, double sp, int q) {
        this.id = id;
        this.name = n;
        this.description = d;
        this.costPrice = cp;
        this.salePrice = sp;
        this.quantity = q;
    }

    public Product(String n, String d, double cp, double sp, int q) {
        this(-1, n, d, cp, sp, q);
    }
    
    //id
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    // name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    //cost price
    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }
    
    //sale price
    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }
    
    //quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}


