package com.example.app.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {
    
    private static Model instance = null;
    
    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }
    
    List<Product> products;
    ProductTableGateway gateway;
    
    private Model() {
        try {
            Connection conn = DBConnection.getInstance();
            this.gateway  = new ProductTableGateway(conn);
            
            this.products = this.gateway.getProducts();
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean addProduct(Product p) {
        boolean result = false;
        
        try {
            int id = this.gateway.insertProduct(p.getName(),p.getDescription() , p.getCostPrice(), p.getSalePrice(), p.getQuantity());
            if (id != -1) {
                p.setId(id);
                this.products.add(p);
                result = true;
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public boolean removeProduct(Product p) {
        boolean removed = false;
        
        try {
            removed = this.gateway.deleteProduct(p.getId());
            if (removed) {
                removed = this.products.remove(p);
            } 
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return removed;
    }
    
    public List<Product> getProducts() {
        return this.products;
    }


    Product findProductById(int id) {
        Product p = null;
        int i = 0;
        boolean found = false;
        while (i < this.products.size() && !found) {
            p = this.products.get(i);
            if (p.getId() == id) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            p = null;
        }
        return p;
    }

    boolean updateProduct(Product p) {
        boolean updated = false;
        
        try {
            updated = this.gateway.updateProduct(p);
            if (updated) {
                updated = this.products.remove(p);
            } 
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updated;
    }
    
    
    
    
}
