package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashSet;
import java.util.Set;

/** This class creates a Product object with setters and getters.*/
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**This is the Product constructor method.
     This method creates the Product constructor.
     @param id ID for the Product
     @param name Name for the Product
     @param stock Inventory level for the Product
     @param price Price for the Product
     @param min Min Inventory for the Product
     @param max Max Inventory for the Product
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**Getter for the Product ID
     @return Gets ID of Product
     */
    public int getId() {
        return id;
    }

    /**Setter for the Product ID
     @param id Sets ID of Product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**Getter for the Product Name
     @return Gets Name of Product
     */
    public String getName() {
        return name;
    }

    /**Setter for the Product Name
     @param name  Sets Name of Product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**Getter for the Product Price
     @return Gets Price of Product
     */
    public double getPrice() {
        return price;
    }

    /**Setter for the Product Price
     @param price Sets Price of Product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**Getter for the Product Inventory
     @return Gets Inventory of Product
     */
    public int getStock() {
        return stock;
    }

    /**Setter for the Product Inventory
     @param stock Sets Inventory of Product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**Getter for the Product Min Inventory
     @return Gets Min Inventory of Product
     */
    public int getMin() {
        return min;
    }

    /**Setter for the Product Min Inventory
     @param min  Sets Min Inventory of Product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**Getter for the Product Max Inventory
     @return Gets Max Inventory of Product
     */
    public int getMax() {
        return max;
    }

    /**Setter for the Product Max Inventory
     @param max Sets Max Inventory of Product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**This method adds Part to List
     @param part Part added to the List
     */
    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
    }

    /**This method removes Part from List
     @param selectedAssociatedPart Selected Part from List
     @return Removes Part from List
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**This method returns All Parts List
     @return Returns All Parts List
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }



    }


