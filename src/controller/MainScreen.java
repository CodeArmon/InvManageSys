package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ModifyPart;
import model.ModifyProduct;

import java.io.IOException;
import java.net.URL;
import java.util.Currency;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class displays the mainscreen.*/
public class MainScreen implements Initializable {
    public TableView partsTableView;
    public TableColumn partsIdCol;
    public TableColumn partsNameCol;
    public TableColumn partsInventoryCol;
    public TableColumn partsPriceCol;
    public TableView productsTableView;
    public TableColumn productsIDCol;
    public TableColumn productNameCol;
    public TableColumn productInventoryCol;
    public TableColumn productPriceCol;
    public Button partsAdd;
    public Button partsModify;
    public Button productAdd;
    public Button productModify;
    public Button partDelete;
    public Button productDelete;
    public Button partsSearch;
    public TextField partsSearchField;
    public Button getProductsResultsHandler;
    public Button exitButton;
    public TextField productsSearchField;
    public static int index;
    private Product product = new Product( 0,  null,  0,  0,  0,  0);

    private ObservableList<Part> partsData = FXCollections.observableArrayList();
    private ObservableList<Product> productData = FXCollections.observableArrayList();

    private ObservableList<Part> searchByPartName (String partialName)
    {
        ObservableList<Part> namedPart = FXCollections.observableArrayList();

        ObservableList<Part> allPart = Inventory.getAllParts();

        for(Part pt : allPart)
        {
          if (pt.getName().contains(partialName))
          {
            namedPart.add(pt);
          }
        }

        return namedPart;
    }
    private Part getAPartWithId(int id)
    {
        ObservableList<Part> allPart = Inventory.getAllParts();

        for(Part pt: allPart)
            {
                if(pt.getId() == id)
            {
                return pt;
            }
        }
        return null;
    }
    private Product getAProductWithId(int id)
    {
        ObservableList<Product> allProduct = Inventory.getAllProducts();

        for(Product pd: allProduct) {
            if (pd.getId() == id) {
                return pd;
            }
        }
        return null;
    }
    private ObservableList<Product> searchByProductName (String partialName)
    {
        ObservableList<Product> namedProduct = FXCollections.observableArrayList();

        ObservableList<Product> allProduct = Inventory.getAllProducts();

        for(Product pd : allProduct)
        {
            if (pd.getName().contains(partialName))
            {
                namedProduct.add(pd);
            }
        }

        return namedProduct;
    }
    private Part getAId(int id)
    {
        ObservableList<Part> allPart = Inventory.getAllParts();

        for(Part gi : Inventory.getAllParts())
        {
            if(gi.getId() == id){
                return gi;
            }
        }
        return null;
        }
    private Product getAProductId(int id)
    {
        ObservableList<Product> allProduct = Inventory.getAllProducts();

        for(Product pi : Inventory.getAllProducts())
        {
            if(pi.getId() == id){
                return pi;
            }
        }
        return null;
    }

    /** This is the initialize method sets part and product tables */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    addTestData();


        partsTableView.setItems(Inventory.getAllParts());
        productsTableView.setItems(Inventory.getAllProducts());

        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));



    }



    private static boolean firstTime = true;

    private void addTestData()
    {
        if(!firstTime){
            return;
        }
        firstTime = false;

        InHouse i = new InHouse(1, "Brakes", 15.00, 10, 0, 10, 1);
        Inventory.addPart(i);
        InHouse o = new InHouse(2, "Wheel", 16.00, 6, 0 , 10, 6);
        Inventory.addPart(o);
        InHouse j = new InHouse(3, "Seat", 16.00, 9, 0, 20, 2);
        Inventory.addPart(j);

        Product p = new Product(1, "Giant Bike", 299.99, 3, 1, 10);
        p.getAllAssociatedParts().addAll(product.getAllAssociatedParts());
        Inventory.addProduct(p);
        Product q = new Product(2, "Tricycle", 99.99, 3, 1, 10);
        q.getAllAssociatedParts().addAll(product.getAllAssociatedParts());
        Inventory.addProduct(q);
        Product r = new Product(3, "Scooter", 159.99, 3, 1, 10);
        r.getAllAssociatedParts().addAll(product.getAllAssociatedParts());
        Inventory.addProduct(r);
    }

    /**This method loads AddPart screen
     @param actionEvent Loads AddPart screen
     @throws IOException Cathces Exception
     */
    public void AddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 620, 500);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**This method loads ModifyPart screen
     @param actionEvent Loads ModifyPart screen
     @throws IOException Cathces Exception
     */
    public void toModifyPart(ActionEvent actionEvent) throws IOException {
        if (partsTableView.getSelectionModel().isEmpty()) {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "You must first select a part to modify to continue.");

            Optional<ButtonType> result = noSelection.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                return;
            }
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();

            ModifyPart MPController = loader.getController();
            MPController.sendPart((Part) partsTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            Scene scene = new Scene(root, 620, 500);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**This method loads AddProduct screen
     @param actionEvent Loads AddProduct screen
     @throws IOException Cathces Exception
     */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 775);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**This method loads ModifyProduct screen
     @param actionEvent Loads ModifyProduct screen
     @throws IOException Cathces Exception
     */
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        if (productsTableView.getSelectionModel().isEmpty()) {
        Alert noSelection = new Alert(Alert.AlertType.ERROR, "You must first select a product to modify to continue.");

        Optional<ButtonType> result = noSelection.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return;
        }
    } else {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
        loader.load();

        ModifyProduct MPController = loader.getController();
        MPController.sendProduct((Product) productsTableView.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root, 900, 775);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }
    }

    /**This method removes Part from Inventory
     @param actionEvent Removes Part
     */
    public void onPartDelete(ActionEvent actionEvent) {
        Part Pt = (Part) partsTableView.getSelectionModel().getSelectedItem();
        Alert alertOnProductDelete = new Alert(Alert.AlertType.CONFIRMATION, "Clicking OK will delete part, do you wish to continue?");
        Optional<ButtonType> result = alertOnProductDelete.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
        if (partsTableView.getSelectionModel().isEmpty()) {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "You must first select a part to delete to continue.");
            Optional<ButtonType> result2 = noSelection.showAndWait();

            if(Pt == null)
                return;
           } else
            {
                Inventory.delelePart(Pt);
            }

    }}

    /** This method removes Product
     @param actionEvent Removes product
     */
    public void onProductDelete(ActionEvent actionEvent) {
        Product Pd = (Product) productsTableView.getSelectionModel().getSelectedItem();
        Alert alertOnProductDelete = new Alert(Alert.AlertType.CONFIRMATION, "Clicking OK will delete product, do you wish to continue?");
        Optional<ButtonType> delresult = alertOnProductDelete.showAndWait();

        if (delresult.isPresent() && delresult.get() == ButtonType.OK) {
            if (productsTableView.getSelectionModel().isEmpty()) {
                Alert noSelection = new Alert(Alert.AlertType.ERROR, "You must first select a product to delete to continue.");
                Optional<ButtonType> result2 = noSelection.showAndWait();}
          if (Pd.getAllAssociatedParts().size() > 0)
            { Alert deleteParts = new Alert(Alert.AlertType.ERROR, "You must delete all parts associated with a product to continue.");
                Optional<ButtonType> result3 = deleteParts.showAndWait();

            }
          else{
              Inventory.deleteProduct(Pd);
          }

            }
           /* else if (Pd == null){

                    return;
            } else {
                    Inventory.deleteProduct(Pd);
                } */

            }
        //}
    //}

    /**This method exits the application
     @param actionEvent Exits Application
     */
    public void exitApplication(ActionEvent actionEvent) {
        Alert alertOnExit = new Alert(Alert.AlertType.CONFIRMATION, "Clicking OK will exit the system, do you wish to continue?");

        Optional<ButtonType> result = alertOnExit.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)

            {
                System.exit(1);
            }
    }

    /**This method searches parts table
     @param actionEvent Search parts
      *FUTURE ENHANCEMENT - Active return of search results without the need to click OK button
     */
    public void getPartsResultsHandler(ActionEvent actionEvent) {
        String p = partsSearchField.getText();
        ObservableList<Part> pt1 = searchByPartName(p);
        try {
            int id = Integer.parseInt(p);
            Part pt = getAId(id);
            if (pt1 != null)
                pt1.add(pt);
        }
        catch (NumberFormatException e)
        {
            pt1 = searchByPartName(p);

        }

        if (pt1.size() == 0) {
            Alert noResults = new Alert(Alert.AlertType.ERROR);
            noResults.setTitle("No Search Results Found");
            noResults.setContentText("Please try your search again.");
            noResults.showAndWait();
            partsSearchField.setText("");

        }
        else{



        partsTableView.setItems(pt1);
        partsSearchField.setText("");
    }}

    /**This method searches products table
     @param actionEvent Search products
     */
    public void getProductsResultsHandler(ActionEvent actionEvent) {

        String p = productsSearchField.getText();
        ObservableList<Product> pt1 = searchByProductName(p);
        try {
            int id = Integer.parseInt(p);
            Product pt = getAProductId(id);
            if (pt1 != null)
                pt1.add(pt);
        }
        catch (NumberFormatException e)
        {
            pt1 = searchByProductName(p);
        }
        if (pt1.size() == 0) {
            Alert noResults = new Alert(Alert.AlertType.ERROR);
            noResults.setTitle("No Search Results Found");
            noResults.setContentText("Please try your search again.");
            noResults.showAndWait();
            productsSearchField.setText("");

        }
        else{



        productsTableView.setItems(pt1);
        productsSearchField.setText("");
    }}


}

