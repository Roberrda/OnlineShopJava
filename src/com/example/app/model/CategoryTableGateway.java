package com.example.app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//name of class
public class CategoryTableGateway {

    private Connection mConnection;

    //declaring category table name/column
    private static final String TABLE_NAME = "categories";
    private static final String COLUMN_CATEGORY_ID = "categoryId";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";

    //connection for table gateway
    public CategoryTableGateway(Connection connection) {
        mConnection = connection;
    }

    //method for inserting a category
    public int insertCategory(int cid, String n, String d) throws SQLException {
        String query;       // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;
        

        // the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "INSERT INTO " + TABLE_NAME + " ("
                + COLUMN_CATEGORY_ID + ", "
                + COLUMN_NAME + ", "
                + COLUMN_DESCRIPTION
                + ") VALUES (?, ?)";

        // create a PreparedStatement object to execute the query and insert the values into the query
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, cid);
        stmt.setString(2, n);
        stmt.setString(3, d);

        //  execute the query and make sure that one and only one row was inserted into the database
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            // if one row was inserted, retrieve the id assigned to that row and create a Programmer object to return
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();

            cid = keys.getInt(1);
        }

        // return the id object created or null if there was a problem
        return cid;
    }

    //method for deleting a category
    public boolean deleteCategory(int id) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;

        query = "DELETE FROM " + TABLE_NAME + " WHERE" + COLUMN_CATEGORY_ID + " = ?";
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);

        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);
    }

    //get categories
    public List<Category> getCategories() throws SQLException {
        String query;                   // the SQL query to execute
        Statement stmt;                 // the java.sql.Statement object used to execute the SQL query
        ResultSet rs;                   // the java.sql.ResultSet representing the result of SQL query
        List<Category> categories;   // the java.util.List containing the Programmer objects created for each row
        // in the result of the query the id of a programmer

        String name, description;
        int id;

        Category c;

        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

        // iterate through the result set, extracting the data from each row
        // and storing it in a Programmer object, which is inserted into an initially
        // empty ArrayList
        categories = new ArrayList<Category>();
        while (rs.next()) {
            id = rs.getInt(COLUMN_CATEGORY_ID);
            name = rs.getString(COLUMN_NAME);
            description = rs.getString(COLUMN_DESCRIPTION);

            c = new Category(id, name, description);
            categories.add(c);
        }

        // return the list of Programmer objects retrieved
        return categories;
    }

    //method for updating categories
    boolean updateCategory(Category c) throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;

        query = "UPDATE " + TABLE_NAME + " SET "
                + COLUMN_NAME + " = ?, "
                + COLUMN_DESCRIPTION + " = ? "
                + " WHERE " + COLUMN_CATEGORY_ID + " = ?";

        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, c.getName());
        stmt.setString(2, c.getDescription());
        stmt.setInt(3, c.getCategoryId());

        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);
    }

  

}
