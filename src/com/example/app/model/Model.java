package com.example.app.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//model class name
public class Model {
    
    private static Model instance = null;
    
    public static Model getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }
    
    //declaring lists/gateways
    private List<Product> products;
    private List<Category> categories;
    ProductTableGateway gateway;
    CategoryTableGateway cGateway;
    
    private Model() throws DataAccessException {
      try { 
        Connection conn = DBConnection.getInstance();
        this.gateway  = new ProductTableGateway(conn);
        this.cGateway = new CategoryTableGateway(conn);

        this.products = this.gateway.getProducts();
        this.categories = this.cGateway.getCategories();
      }
      catch (ClassNotFoundException ex) {
          throw new DataAccessException("Exception initialising Model object" + ex.getMessage());
      }
      catch (SQLException ex) {
          throw new DataAccessException("Exception initialising Model object" + ex.getMessage());
      }
    }
    
    
    // method to add product
    public boolean addProduct(Product p) throws DataAccessException {
        boolean result = false;
        
        try {
            int id = this.gateway.insertProduct(p.getName(),p.getDescription() , p.getCostPrice(), p.getSalePrice(), p.getQuantity(), p.getCategoryId());
            if (id != -1) {
                p.setId(id);
                this.products.add(p);
                result = true;
            }
        } 
        catch (SQLException ex) {
            throw new DataAccessException("Exception adding product" + ex.getMessage());
        }
        return result;
    }
    
    // method to remove product
    public boolean removeProduct(Product p) throws DataAccessException {
        boolean removed = false;
        
        try {
            removed = this.gateway.deleteProduct(p.getId());
            if (removed) {
                removed = this.products.remove(p);
            } 
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception removing programmer" + ex.getMessage());
        }
        return removed;
    }
    
   //method to get products
    public List<Product> getProducts() {
        return this.products;
    }

    //method to get products by category id
    public List<Product> getProductsByCategoryId(int categoryId) {
        List<Product> list = new ArrayList<Product>();
        for (Product p : this.products) {
            if(p.getCategoryId() == categoryId) {
                list.add(p);
            }
        }
        return list;
    }
    
    //method to find product by id
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

    // method to update a product
    boolean updateProduct(Product p) throws DataAccessException {
        boolean updated = false;
        
        try {
            updated = this.gateway.updateProduct(p);
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception updating programmer:" + ex.getMessage());
        }
        return updated;
    }
        
    // method to add a category 
    public boolean addCategory(Category c) throws DataAccessException {
        boolean result = false;
        
        try {
            int id = this.cGateway.insertCategory(c.getCategoryId(), c.getName(),c.getDescription());
            if (id != -1) {
                c.setCategoryId(id);
                this.categories.add(c);
                result = true;
            }
        } 
        catch (SQLException ex) {
            throw new DataAccessException("Exception adding category" + ex.getMessage());        }
        return result;
    }
    
    // method to remove a category
    public boolean removeCategory(Category c) throws DataAccessException {
        boolean removed = false;
        
        try {
            removed = this.cGateway.deleteCategory(c.getCategoryId());
            if (removed) {
                removed = this.categories.remove(c);
            } 
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception removing manager" + ex.getMessage());
        }
        return removed;
    }
    
    // method to retrieve category
    public List<Category> getCategories() {
        return this.categories;
    }

    //method to find category id
    Category findCategoryById(int id) {
        Category c = null;
        int i = 0;
        boolean found = false;
        while (i < this.categories.size() && !found) {
            c = this.categories.get(i);
            if (c.getCategoryId() == id) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            c = null;
        }
        return c;
    }

    // method to update a category
    boolean updateCategory(Category c) throws DataAccessException {
        boolean updated = false;
        
        try {
            updated = this.cGateway.updateCategory(c);
        }
        catch (SQLException ex) {
           throw new DataAccessException("Exception updating category" + ex.getMessage());
        }
        return updated;
    }
    
    
}
