package model;

import controller.*;
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
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class is used as a controller for the AddProduct screen.*/
public class AddProduct implements Initializable {
    public TextField idTextFieldAddProduct;
    public TextField nameTextFieldAddProduct;
    public TextField inventoryTextFieldAddProduct;
    public TextField priceTextFieldAddProduct;
    public TextField minTextFieldAddProduct;
    public TextField maxTextFieldAddProduct;
    public TableView <Part> topAddProductTblView;
    public TableView <Part> btmAddProductTblView;
    public TableColumn <Part, Integer> topProductPartIdCol;
    public TableColumn<Part, String> topProductPartNameCol;
    public TableColumn<Part, Integer> topProductInvCol;
    public TableColumn <Part, Double>topProductPriceCol;
    public TableColumn<Part, Integer> btmProductPartIdCol;
    public TableColumn <Part, String>btmProductPartNameCol;
    public TableColumn<Part, Integer> btmProductInvCol;
    public TableColumn <Part, Double>btmProductPriceCol;
    public TextField addProductSearchField;
    private Product product = new Product( 0,  null,  0,  0,  0,  0);



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topAddProductTblView.setItems(Inventory.getAllParts());

        topProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        topProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        topProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        btmAddProductTblView.setItems(product.getAllAssociatedParts());
        btmProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        btmProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        btmProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        btmProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    public static int productIdInititalizer = 1001;

    public static int assignProductId(Product P)
    {
        P.setId(productIdInititalizer);
        return P.getId();
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


    /**This method takes you back to main screen. After clicking cancel form will revert to main screen.
     @param actionEvent Back to Main Screen
     @throws IOException Catches Exception
     */
    public void toMain(ActionEvent actionEvent) throws IOException
    {
            Alert alertAddProductCancel = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all data entered, do you wish to continue?");

            Optional<ButtonType> result = alertAddProductCancel.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 600);
            stage.setTitle("Back to Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**This method adds Part to the Product
     @param actionEvent Adds Part
     */
    public void onAddProductAdd(ActionEvent actionEvent)
    {
        if (topAddProductTblView.getSelectionModel().isEmpty())
        {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "You must first select a part to add to continue.");

            Optional<ButtonType> result = noSelection.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                return;
            }
        }
        Part selectedPart = topAddProductTblView.getSelectionModel().getSelectedItem();
        product.addAssociatedPart(selectedPart);

    }

    /**This method removes Part from the Product
     @param actionEvent Removes Part
     @throws IOException Catches Exception
     */
    public void onAddProductRemove(ActionEvent actionEvent) throws IOException {
        Alert alertOnProductRemove = new Alert(Alert.AlertType.CONFIRMATION, "Clicking OK will delete Product, do you wish to continue?");
        Optional<ButtonType> result = alertOnProductRemove.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (btmAddProductTblView.getSelectionModel().isEmpty()) {
                Alert noSelection1 = new Alert(Alert.AlertType.ERROR, "You must first select a part to remove to continue.");

                Optional<ButtonType> result2 = noSelection1.showAndWait();
                if (result2.isPresent() && result2.get() == ButtonType.OK) {
                    return;
                }
            }
            Part pt = btmAddProductTblView.getSelectionModel().getSelectedItem();
            if (pt != null) {
                Part selectedPart = btmAddProductTblView.getSelectionModel().getSelectedItem();
                product.deleteAssociatedPart(selectedPart);
            } else {
                return;
            }

        }
    }

    /**This method saves the Product
     @param actionEvent Saves Product
     @throws IOException Catches Exception
     */
    public void onAddProductSave(ActionEvent actionEvent) throws IOException
    {
        Alert alertOnSaveProduct = new Alert(Alert.AlertType.CONFIRMATION, "Clicking OK will save data, do you wish to continue?");

        Optional<ButtonType> result = alertOnSaveProduct.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try
                {   Alert alertEmptyField = new Alert(Alert.AlertType.WARNING, "All Fields must be filled out to save part");
                    int id = 0;
                    String name = "";
                    int stock = 0;
                    double price = 0;
                    int max = 0;
                    int min = 0;
                    String productName = nameTextFieldAddProduct.getText();
                    String productStock = inventoryTextFieldAddProduct.getText();
                    String productPrice = priceTextFieldAddProduct.getText();
                    String productMax = maxTextFieldAddProduct.getText();
                    String productMin = minTextFieldAddProduct.getText();


                    if (productName == null || productName.length() == 0 || productStock == null || productPrice == null || productMin == null || productMax == null ) {
                        alertEmptyField.showAndWait();
                    }
                    else{
                        id = productIdInititalizer;
                        name = nameTextFieldAddProduct.getText();
                        stock = Integer.parseInt(inventoryTextFieldAddProduct.getText());
                        price = Double.parseDouble(priceTextFieldAddProduct.getText());
                        max = Integer.parseInt(maxTextFieldAddProduct.getText());
                        min = Integer.parseInt(minTextFieldAddProduct.getText());
                        productIdInititalizer+=2;

                        if (max < stock || (min > stock)) {
                            Alert alertInv = new Alert(Alert.AlertType.ERROR, "Inventory Value must between Min and Max Values");
                            alertInv.showAndWait();
                        }
                        else if (min > max) {
                            Alert alertMinLessMax = new Alert(Alert.AlertType.ERROR, "Min Value must be less than Max Value");
                            alertMinLessMax.showAndWait();
                        }
                        else {
                    Product savedProduct = new Product(id, name, price, stock, min, max);
                    savedProduct.getAllAssociatedParts().addAll(product.getAllAssociatedParts());
                    Inventory.addProduct(savedProduct);

                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1000, 600);
                    stage.setTitle("Back to Inventory Management System");
                    stage.setScene(scene);
                    stage.show();
                }
                    }
                }
            catch(NumberFormatException ex)
            {
                Alert alertOnAddPart = new Alert(Alert.AlertType.ERROR);
                alertOnAddPart.setTitle("Error Dialog");
                alertOnAddPart.setContentText("Please enter a valid value for each text field!");
                alertOnAddPart.showAndWait();
            }
        }
    }

    /**This method searches the top Parts table
     @param actionEvent Searches Parts table
     */
    public void onAddProductSearch(ActionEvent actionEvent) {
        String p = addProductSearchField.getText();
        ObservableList<Part> pd1 = searchByPartName(p);
        try {
            int id = Integer.parseInt(p);
            Part pt = getAId(id);
            if (pd1 != null)
                pd1.add(pt);
        } catch (NumberFormatException e) {
            pd1 = searchByPartName(p);
        }
        if (pd1.size() == 0) {
            Alert noResults = new Alert(Alert.AlertType.ERROR);
            noResults.setTitle("No Search Results Found");
            noResults.setContentText("Please try your search again.");
            noResults.showAndWait();
            addProductSearchField.setText("");

        } else {
            topAddProductTblView.setItems(pd1);
            addProductSearchField.setText("");
        }
    }


}
