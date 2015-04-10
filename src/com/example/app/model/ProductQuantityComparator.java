package com.example.app.model;

import java.util.Comparator;

public class ProductQuantityComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return(int)(p1.getQuantity() - p2.getQuantity());
    }
    
}
