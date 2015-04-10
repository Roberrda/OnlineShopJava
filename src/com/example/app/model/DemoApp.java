package com.example.app.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

//name of app
public class DemoApp {
    
    // organising the layout of products/categories
    private static final int NAME_ORDER = 1;
    private static final int QUANTITY_ORDER = 2;
    
    public static void main(String[] args){
        Scanner keyboard = new Scanner(System.in);       
        int opt = 12;
            
        do {
            try {               
                Model model = Model.getInstance();
                System.out.println("1. Create new Product");
                System.out.println("2. Delete existing Product");
                System.out.println("3. Edit existing Products");
                System.out.println("4. View all Products");
                System.out.println("5. View all Products by Quantity");
                System.out.println("6. View single Products");
                System.out.println();
                System.out.println("7. Create new Category");
                System.out.println("8. Delete existing Category");
                System.out.println("9. Edit existing Category");
                System.out.println("10. View all Categories");
                System.out.println("11. View single Categories");
                System.out.println();
                System.out.println("12. Exit");
                System.out.println();


                opt = getInt(keyboard, "Enter option: ", 12);

                System.out.println("You chose option " + opt);
                switch (opt) {
                    case 1: {
                        System.out.println("Creating Products");
                        createProduct(keyboard, model);
                        break;
                    }

                    case 2: {
                        System.out.println("Deleting Products");
                        deleteProduct(model,keyboard);
                        break;
                    }                            

                    case 3: {
                        System.out.println("Editing Products");
                        editProduct(keyboard, model);
                        break;
                    }
                    case 4: {
                        System.out.println("Viewing Products"); 
                        viewProducts(model, NAME_ORDER);
                        break;
                    }
                    
                    case 5: {
                        System.out.println("Viewing Products"); 
                        viewProducts(model, QUANTITY_ORDER);
                        break;
                    }
                    
                    case 6: {
                        System.out.println("Viewing single Product"); 
                        viewProduct(keyboard, model);
                        break;
                    }
                    
                    case 7: {
                        System.out.println("Creating Categories");
                        createCategory(keyboard, model);
                        break;
                    }

                    case 8: {
                        System.out.println("Deleting Categories");
                        deleteCategory(model,keyboard);
                        break;
                    }                            

                    case 9: {
                        System.out.println("Editing Categories");
                        editCategory(keyboard, model);
                        break;
                    }
                    case 10: {
                        System.out.println("Viewing Categories"); 
                        viewCategories(model);
                        break;
                    }
                    case 11: {
                        System.out.println("Viewing Categories"); 
                        viewCategory(keyboard, model);
                        break;
                    }                    
                }
            }
            catch(DataAccessException e){
                System.out.println();
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
        while(opt != 12);
    }
    
    // method of creating a product
    private static void createProduct(Scanner keyboard, Model model) throws DataAccessException {
        Product p = readProduct(keyboard);
        if (model.addProduct(p)){
            System.out.println("Product added to database.");
        }
        else {
            System.out.println("Product added to database.");
        }
        System.out.println();
    }

    //method of viewing a product
    private static void viewProducts(Model mdl, int order) {
        List<Product> products = mdl.getProducts();
        System.out.println();
        if (products.isEmpty()) {
            System.out.println("There are no products in the database.");
        }
        else {
            if (order == NAME_ORDER) {
                Collections.sort(products);
            }
            else if (order == QUANTITY_ORDER) {
                Comparator<Product> cmptr = new ProductQuantityComparator();
                Collections.sort(products, cmptr);
            } 
            displayProducts(products, mdl);
        }
        System.out.println();
    }
    
    //method of displaying a product
    private static void displayProducts(List <Product> products, Model mdl) {
        System.out.printf("%-5s %-20s %-50s %10s %10s %8s\n","Id","Name","Description","Cost Price","Sale Price","Quantity");
            for (Product pr : products) {
                Category c = mdl.findCategoryById(pr.getCategoryId());
                System.out.printf("%-5s %-20s %-50s %10.2f %10.2f %8d\n",
                        pr.getId(),
                        pr.getName(),
                        pr.getDescription(),
                        pr.getCostPrice(),
                        pr.getSalePrice(),
                        pr.getQuantity(),
                        (c != null) ? c.getName() : "");
            }
    }
    
    //method of deleting a product
    private static void deleteProduct(Model model, Scanner keyboard) throws DataAccessException {
          
            int id = getInt(keyboard, "Enter the id number of the product to delete:", -1);
            Product p;

            p = model.findProductById(id);
            if (p != null) {
                if(model.removeProduct(p)) {
                    System.out.println("Product deleted");
                }
                else{
                    System.out.println("Product not deleted");
                }
            }
            else {
                System.out.println("Product not found");
            }
    }
    
    //method of reading a product
    private static Product readProduct(Scanner keyb) {
        
        //declaring variables
        String name, description;
        double costPrice, salePrice;
        int quantity, categoryId;

        name = getString(keyb, "Enter name: ");
        description = getString(keyb, "Enter description: ");
        costPrice = getDouble(keyb, "Enter cost price: ", 0);
        salePrice = getDouble(keyb, "Enter sale price: ", 0);
        quantity = getInt(keyb, "Enter quantity: ", 0);
        categoryId = getInt(keyb, "Enter category id: ", 0);
        
        Product p;
        p = new Product(name, description, costPrice, 
                salePrice, quantity, categoryId);
        
        return p;
    }
    
    //method of viewing a product
    private static void viewProduct(Scanner keyboard, Model model) {
        
        int id = getInt(keyboard, "Enter the id number of the product you want to view:", -1);
        Product p;

        p = model.findProductById(id);
        System.out.println();
        if (p != null) {
            Category c = model.findCategoryById(p.getCategoryId());
            System.out.println("Name          : " + p.getName());
            System.out.println("Description   : " + p.getDescription());
            System.out.println("Cost Price    : " + p.getCostPrice());
            System.out.println("Sale Price    : " + p.getSalePrice());
            System.out.println("Quantity      : " + p.getQuantity());
            System.out.println("Category      : " + ((c != null) ? p.getName() : ""));

            List<Product> productList = model.getProductsByCategoryId(c.getCategoryId());
            System.out.println();
            if (productList.isEmpty()){
                System.out.println("This manager manages no products");
            }
            System.out.println();
        }
        else  {
            System.out.println("Product not found");
        }
        System.out.println();
    }
    
    //method of editing a product
    private static void editProduct(Scanner keyboard, Model model) throws DataAccessException {
         int id = getInt(keyboard, "Enter the id number of the product to edit:", -1);
            Product p;

            p = model.findProductById(id);
            if (p != null) {
                editProductDetails(keyboard, p);
                if (model.updateProduct(p)) {                
                    System.out.println("Product updated");
                }
                else{
                    System.out.println("Product not updated");
                }
            }
            else {
                System.out.println("Product not found");
            }
    }
    
    //method of editing product details
    private static void editProductDetails(Scanner keyboard, Product p) {
       
       //declaring variables
       String name, description;
       int quantity;
       double costPrice, salePrice;
       
       name = getString(keyboard, "Enter name [" + p.getName() + "]:");
       description = getString(keyboard, "Enter description [" + p.getDescription() + "]:");
       costPrice = getDouble(keyboard, "Enter cost price [" + p.getCostPrice() + "]:", 0);
       salePrice = getDouble(keyboard, "Enter sale price [" + p.getSalePrice() + "]:", 0);
       quantity = getInt(keyboard, "Enter quantity [" + p.getQuantity() + "]:", 0);
       
       if (name.length() !=0) {
           p.setName(name);
       }
       
       if (description.length() !=0) {
           p.setDescription(description);
       }
       
       if (costPrice != p.getCostPrice()) {
           p.setCostPrice(costPrice);
       }
       
        if (salePrice != p.getSalePrice()) {
           p.setSalePrice(salePrice);
       }
        
        if (quantity != p.getQuantity()) {
          p.setQuantity(quantity);
       }
    }
   
    //method of creating a category 
    private static void createCategory(Scanner keyboard, Model model) throws DataAccessException {
        Category c = readCategory(keyboard);
        if (model.addCategory(c)){
            System.out.println("Category added to database.");
        }
        else {
            System.out.println("Category added to database.");
        }
        System.out.println();
    }

    //method of viewing categories
    private static void viewCategories(Model mdl) {
        List<Category> categories = mdl.getCategories();
        System.out.println();
        if (categories.isEmpty()) {
            System.out.println("There are no categories in the database.");
        }
        else {
            System.out.printf("%-5s %-20s %-50s\n","Id","Name","Description");
            for (Category ca : categories) {
                System.out.printf("%-5s %-20s %-50s\n",
                        ca.getCategoryId(),
                        ca.getName(),
                        ca.getDescription());
            }
        }
        System.out.println();
    } 
    
    //method of viewing a category
    private static void viewCategory(Scanner keyboard, Model model) {

        int id = getInt(keyboard, "Enter the id number of the category to view:", -1);
        Category c;

        c = model.findCategoryById(id);
        System.out.println();
        if (c != null) {
            System.out.println("Id          : " + c.getCategoryId());
            System.out.println("Name        : " + c.getName());
            System.out.println("Description : " + c.getDescription());

            List<Product> productList = model.getProductsByCategoryId(c.getCategoryId());
            System.out.println();
            if (productList.isEmpty()) {
                System.out.println("This product contains no categories");
            }
            else {
               System.out.println("This product contains the following categories:"); 
               System.out.println();
               displayProducts(productList, model); 
            }
            System.out.println();
        }
        else {
            System.out.println("Category not found");
        }
        System.out.println();
    }
      
    //method of reading a category
    private static Category readCategory(Scanner keyb) {
        String name, description;
        int id;
        String line;

        line = getString(keyb, "Enter id: ");
        id = Integer.parseInt(line);
        name = getString(keyb, "Enter name: ");
        description = getString(keyb, "Enter description: ");
        Category c = 
                new Category(id, name, description);
        
        return c;
    }
    
    //method of deleting a category
    private static void deleteCategory(Model model, Scanner keyboard) throws DataAccessException {
        int id = getInt(keyboard, "Enter the id number of the category to delete:", -1);
        Category c;

        c = model.findCategoryById(id);
        if (c != null) {
            if(model.removeCategory(c)) {
                System.out.println("Category deleted");
            }
            else{
                System.out.println("Category not deleted");
            }
        }
        else {
            System.out.println("Category not found");
        }
    }

    //method of editing a category
    private static void editCategory(Scanner keyboard, Model model) throws DataAccessException {
        int id = getInt(keyboard, "Enter the id number of the category to edit:", -1);
        Category c;

        c = model.findCategoryById(id);
        if (c != null) {
            editCategoryDetails(keyboard, c);
            if (model.updateCategory(c)) {                
                System.out.println("Category updated");
            }
            else{
                System.out.println("Category not updated");
            }
        }
        else {
            System.out.println("Category not found");
        }
    }

    //method of editing category details
    private static void editCategoryDetails(Scanner keyboard, Category c) {
       String name, description;
       int id;
       String line1;
       
       line1 = getString(keyboard, "Enter id [" + c.getCategoryId() + "]:");
       name = getString(keyboard, "Enter name [" + c.getName() + "]:");
       description = getString(keyboard, "Enter description [" + c.getDescription() + "]:");
       
       if (line1.length() !=0) {
           id = Integer.parseInt(line1);
           c.setCategoryId(id);
       }
       
       if (name.length() !=0) {
           c.setName(name);
       }
       
       if (description.length() !=0) {
           c.setDescription(description);
       }
    }
     
    //method to get string
    private static String getString(Scanner keyboard, String prompt) {
         System.out.print(prompt);
         return keyboard.nextLine();
     }

    //method to get int
    private static int getInt(Scanner keyb, String prompt, int defaultValue) {
        int opt = defaultValue;
        boolean finished = false;

        do {
            try {
            System.out.print(prompt);
            String line = keyb.nextLine();
            if (line.length() > 0) {
                opt = Integer.parseInt(line);
            }
            finished = true;
            }
            catch (NumberFormatException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
        while(!finished);
        
        return opt;
    }

    //method to get double
    private static double getDouble(Scanner keyb, String prompt, double defaultValue){
        double opt=defaultValue;
        boolean finished = false;
       do{ 
        try{
            System.out.print(prompt);
            String line = keyb.nextLine();
            if(line.length()>0){
                 opt = Double.parseDouble(line);
            }
            finished = true;
        }
        catch (NumberFormatException e){
            System.out.println("Exception: " + e.getMessage());
        }
       }
       while(!finished);
        return opt;
    }

   

  

    
       
        
 }


