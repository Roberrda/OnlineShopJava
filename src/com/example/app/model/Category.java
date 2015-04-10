package com.example.app.model;

//method for category class
public class Category {
    private int categoryId;
    private String name;
    private String description;

    //category declaring ints
    public Category(int cid, String n, String d) {
        this.categoryId = cid;
        this.name = n;
        this.description = d;
    }
    
    //category declaring strings
    public Category(String n, String d) {
        this(-1, n, d);
    }
    
    // category id get method
    public int getCategoryId() {
        return categoryId;
    }

    //category id set method
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    // name get method
    public String getName() {
        return name;
    }

    //name set method
    public void setName(String name) {
        this.name = name;
    }

    // description get method
    public String getDescription() {
        return description;
    }

    //description set method
    public void setDescription(String description) {
        this.description = description;
    }
    
}
