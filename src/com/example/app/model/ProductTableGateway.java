package com.example.app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductTableGateway {
    
    private Connection mConnection;
    
    private static final String TABLE_NAME = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_COST_PRICE = "costPrice";
    private static final String COLUMN_SALE_PRICE = "salePrice";
    private static final String COLUMN_QUANTITY = "quantity";
    
    public ProductTableGateway(Connection connection) {
        mConnection = connection;
    }
    
        public int insertProduct(String n, String d, double cp, double sp, int q) throws SQLException {
        Product p = null;

        String query;       // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;
        int id = -1;

        // the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_NAME + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_COST_PRICE + ", " +
                COLUMN_SALE_PRICE + ", " +
                COLUMN_QUANTITY +
                ") VALUES (?, ?, ?, ?, ?)";

        // create a PreparedStatement object to execute the query and insert the values into the query
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, n);
        stmt.setString(2, d);
        stmt.setDouble(3, cp);
        stmt.setDouble(4, sp);
        stmt.setInt(5, q);

        //  execute the query and make sure that one and only one row was inserted into the database
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            // if one row was inserted, retrieve the id assigned to that row and create a Programmer object to return
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();

            id = keys.getInt(1);         
        }

        // return the id object created or null if there was a problem
        return id;
    }

      public boolean deleteProduct(int id) throws SQLException {
          String query;
          PreparedStatement stmt;
          int numRowsAffected;
          
          query = "DELETE FROM" + TABLE_NAME + "WHERE" + COLUMN_ID + "+?"; 
           stmt = mConnection.prepareStatement(query);
           stmt.setInt(1, id);
           
           numRowsAffected = stmt.executeUpdate();
           
           return (numRowsAffected == 1);
                  }  
    
    public List<Product> getProducts() throws SQLException {
        String query;                   // the SQL query to execute
        Statement stmt;                 // the java.sql.Statement object used to execute the SQL query
        ResultSet rs;                   // the java.sql.ResultSet representing the result of SQL query
        List<Product> products;   // the java.util.List containing the Programmer objects created for each row
                                        // in the result of the query the id of a programmer
        
        String name, description;
        int id, quantity;
        double costPrice, salePrice;

        Product p;                 

        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

        // iterate through the result set, extracting the data from each row
        // and storing it in a Programmer object, which is inserted into an initially
        // empty ArrayList
        products = new ArrayList<Product>();
        while (rs.next()) {
            id = rs.getInt(COLUMN_ID);
            name = rs.getString(COLUMN_NAME);
            description = rs.getString(COLUMN_DESCRIPTION);
            costPrice = rs.getDouble(COLUMN_COST_PRICE);
            salePrice = rs.getDouble(COLUMN_SALE_PRICE);
            quantity = rs.getInt(COLUMN_QUANTITY);

            p = new Product(id, name, description, costPrice, salePrice, quantity);
            products.add(p);
        }

        // return the list of Programmer objects retrieved
        return products;
    }

   

    boolean updateProduct(Product p) throws SQLException {        
        String query;
        PreparedStatement stmt;
        int numRowsAffected;

        query = "UPDATE " + TABLE_NAME + " SET "
                + COLUMN_NAME + " = ?, "
                + COLUMN_DESCRIPTION + " = ?, "
                + COLUMN_COST_PRICE + " = ?, "
                + COLUMN_SALE_PRICE + " = ?, "
                + COLUMN_QUANTITY + " = ?, "                
                + " WHERE " + COLUMN_ID + " = ?";

        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, p.getName());
        stmt.setString(2, p.getDescription());
        stmt.setDouble(3, p.getCostPrice());
        stmt.setDouble(4, p.getSalePrice());
        stmt.setInt(5, p.getQuantity());
        stmt.setInt(6, p.getId());

        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);
    }
    

}

    
    

