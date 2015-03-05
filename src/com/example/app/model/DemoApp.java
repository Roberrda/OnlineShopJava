package com.example.app.model;

import java.util.List;
import java.util.Scanner;

public class DemoApp {
    
    public static void main(String[] args){
        Scanner keyboard = new Scanner(System.in);
        Model model = Model.getInstance();
        int opt;
            
        do {
            System.out.println("1. Create new Product");
            System.out.println("2. Delete existing Product");
            System.out.println("3. Edit existing Products");
            System.out.println("4. View all Products");
            System.out.println("5. Exit");
            System.out.println();

            System.out.print("Enter option: ");
            String line = keyboard.nextLine();
            opt = Integer.parseInt(line);

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
                    viewProducts(model);
                    break;
                }                            
            }
        }
        while(opt != 5);
    }
    
    private static void createProduct(Scanner keyboard, Model model) {
        Product p = readProduct(keyboard);
        if (model.addProduct(p)){
            System.out.println("Programmer added to database.");
        }
        else {
            System.out.println("Product added to database.");
        }
        System.out.println();
    }

    private static void viewProducts(Model mdl) {
        List<Product> products = mdl.getProducts();
        System.out.println();
        if (products.isEmpty()) {
            System.out.println("There are no products in the database.");
        }
        else {
            System.out.printf("%-5s %-20s %-50s %10s %10s %8s\n","Id","Name","Description","Cost Price","Sale Price","Quantity");
            for (Product pr : products) {
                System.out.printf("%-5s %-20s %-50s %10.2f %10.2f %8d\n",
                        pr.getId(),
                        pr.getName(),
                        pr.getDescription(),
                        pr.getCostPrice(),
                        pr.getSalePrice(),
                        pr.getQuantity());
            }
        }
        System.out.println();
    }
    
    private static Product readProduct(Scanner keyb) {
        String name, description;
        int costPrice, salePrice, quantity;
        String line;

        name = getString(keyb, "Enter name: ");
        description = getString(keyb, "Enter description: ");
        line = getString(keyb, "Enter cost price: ");
        costPrice = Integer.parseInt(line);
        line = getString(keyb, "Enter sale price: ");
        salePrice = Integer.parseInt(line);
        line = getString(keyb, "Enter quantity: ");
        quantity = Integer.parseInt(line);
        
        Product p = 
                new Product(name, description, costPrice, 
                        salePrice, quantity);
        
        return p;
    }
    
     private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private static void deleteProduct(Model model, Scanner keyboard) {
          System.out.println("Enter the id number of the product to delete:");
            int id = Integer.parseInt(keyboard.nextLine());
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

    private static void editProduct(Scanner keyboard, Model model) {
        System.out.println("Enter the id number of the product to edit:");
            int id = Integer.parseInt(keyboard.nextLine());
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

    private static void editProductDetails(Scanner keyboard, Product p) {
       String name, description;
       int quantity;
       double costPrice, salePrice;
       String line1, line2, line3;
       
       name = getString(keyboard, "Enter name [" + p.getName() + "]:");
       description = getString(keyboard, "Enter description [" + p.getDescription() + "]:");
       line1 = getString(keyboard, "Enter cost price [" + p.getCostPrice() + "]:");
       line2 = getString(keyboard, "Enter sale price [" + p.getSalePrice() + "]:");
       line3 = getString(keyboard, "Enter quantity [" + p.getQuantity() + "]:");
       
       if (name.length() !=0) {
           p.setName(name);
       }
       
       if (description.length() !=0) {
           p.setDescription(description);
       }
       
       if (line1.length() !=0) {
           costPrice = Integer.parseInt(line1);
           p.setCostPrice(costPrice);
       }
       
        if (line2.length() !=0) {
           salePrice = Integer.parseInt(line2);
           p.setSalePrice(salePrice);
       }
        
        if (line3.length() !=0) {
          quantity = Integer.parseInt(line3);
          p.setQuantity(quantity);
       }
    }
}
        

 