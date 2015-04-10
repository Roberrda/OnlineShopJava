package com.example.app.model;

//product class
public class Product implements Comparable<Product> {

    //declaring variables in product class
    private int id;
    private String name;
    private String description;
    private double costPrice;
    private double salePrice;
    private int quantity;
    private int categoryId;

    //declaring variables in product class
    public Product(int id, String n, String d, double cp, double sp, int q, int cid) {
        this.id = id;
        this.name = n;
        this.description = d;
        this.costPrice = cp;
        this.salePrice = sp;
        this.quantity = q;
        this.categoryId = cid;
    }

    public Product(String n, String d, double cp, double sp, int q, int cid) {
        this(-1, n, d, cp, sp, q, cid);
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

    //category id
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int compareTo(Product that) {
        String myName = this.getName();
        String yourName = that.getName();
        
        return myName.compareTo(yourName);
    }
    
}


