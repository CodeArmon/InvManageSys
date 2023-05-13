package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class extends lists allParts and allProducts.*/
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**This method adds a new Part
     @param newPart New Part added
     */
    public static void addPart(Part newPart)
        {
            allParts.add(newPart);
        }

    /**This method adds a new Product
     @param newProduct New Product added
     */
    public static void addProduct(Product newProduct)
        {
            allProducts.add(newProduct);
        }

    /**This method searches part by name
     @param partName  Part name to search
     @return Found part
     */
        public static ObservableList<Part> lookupPart(String partName)
        {
            ObservableList<Part> lookedPart = FXCollections.observableArrayList();

            for(Part part: allParts)
                if(part.getName().equals(partName))
                {
                    lookedPart.add(part);
                }
                else
                    return null;
            return lookedPart;
        }

    /**This method searches product by name
     @param productName Product name to search
     @return Found product
     */
        public static ObservableList<Product> lookupProduct(String productName)
    {
        ObservableList<Product> lookedProduct = FXCollections.observableArrayList();

        for(Product product: allProducts)
            if(product.getName().equals(productName))
            {
                lookedProduct.add(product);
            }
            else
                return null;
        return lookedProduct;
    }

    /**This method returns all parts
     @return Returns all parts in a list
     */
    public static ObservableList<Part> getAllParts()
        {
            return allParts;
        }

    /**This method returns all products
     @return Returns all products in a list
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
   /**This method searches a part by Part ID
    @param partId Part ID to lookup
    @return Gets part from Part ID
    */
    public static Part lookupPart(int partId)
    {
        for(Part part: allParts)
        {
            if(partId == part.getId())
                return part;
        }
       return null;
    }

    /**This method searches a product by Product ID
     @param productId Product ID to lookup
     @return Gets product from Product ID
     */
    public static Product lookupProduct(int productId)
    {
        for(Product product: allProducts)
        {
            if(productId == product.getId())
                return product;
        }
        return null;
    }

    /**This method updates the selected Part from Inventory
     @param selectedPart Part to update in Inventory
     @param index Position of part in Inventory
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }

    /**This method updates the selected Product from Inventory
     @param newProduct Product to update in Inventory
     @param index Position of product in Inventory
     */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }

    /**This method deletes the selected Part
     @param selectedPart Part to delete
     @return Deletes part if one selected
     */
    public static boolean delelePart(Part selectedPart)
    {
        if(allParts.contains(selectedPart))
        {
            allParts.remove(selectedPart);
            return true;
        }
        else
            return false;
    }

    /**This method deletes the selected Product
     @param selectedProduct Product to delete
     @return Deletes product if one selected
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        if(allProducts.contains(selectedProduct))
        {
            allProducts.remove(selectedProduct);
            return true;
        }
        else
            return false;
    }

}

