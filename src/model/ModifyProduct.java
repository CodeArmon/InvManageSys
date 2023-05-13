package model;

import controller.Inventory;
import controller.Part;
import controller.Product;
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

/** This class is used as a controller for the ModifyProduct screen.*/
public class ModifyProduct implements Initializable {
    public TextField nameTextFieldModifyProduct;
    public TextField invTextFieldModifyProduct;
    public TextField priceTextFieldModifyProduct;
    public TextField minTextFieldModifyProduct;
    public TextField maxTextFieldModifyProduct;
    public TextField searchTextFieldModifyProduct;
    public TableView <Part>topTableModifyProduct;
    public TableColumn <Part,Integer> topModProductPartIdCol;
    public TableColumn <Part, String>topModProductPartNameCol;
    public TableColumn <Part, Integer>topModProductInvCol;
    public TableColumn <Part, Double> topModProductPriceCol;
    public TableView <Part> btmTableModifyProduct;
    public TableColumn <Part, Integer> btmModProductPartIdCol;
    public TableColumn <Part, String> btmModProductPartNameCol;
    public TableColumn <Part, Integer>btmModProductInvCol;
    public TableColumn <Part, Double>btmModProductPriceCol;
    public TextField idTextFieldModifyProduct;
    private Product product = new Product( 0,  null,  0,  0,  0,  0);
    Product delProduct = null;



    /**This method sends Product data selected from Main Screen to Modify Product form.
     @param product Sent Product data
     */
    public void sendProduct(Product product) {
        btmTableModifyProduct.setItems(product.getAllAssociatedParts());
        btmModProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        btmModProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        btmModProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        btmModProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        delProduct = product;
        idTextFieldModifyProduct.setText(String.valueOf(product.getId()));
        nameTextFieldModifyProduct.setText(product.getName());
        invTextFieldModifyProduct.setText(String.valueOf(product.getStock()));
        priceTextFieldModifyProduct.setText(String.valueOf(product.getPrice()));
        minTextFieldModifyProduct.setText(String.valueOf(product.getMin()));
        maxTextFieldModifyProduct.setText(String.valueOf(product.getMax()));
    }

    /**This method sets the top table with all Inventory Parts*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topTableModifyProduct.setItems(Inventory.getAllParts());

        topModProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        topModProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        topModProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topModProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**This method takes you back to main screen. After clicking cancel form will revert to main screen.
     @param actionEvent Back to Main Screen
     @throws IOException Catches Exception
     */
    public void toMain(ActionEvent actionEvent) throws IOException {
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
    /**This method removes Part from the Product
     @param actionEvent Removes Part
     @throws IOException Catches Exception
     */
    public void onModifyProductRemove(ActionEvent actionEvent) throws IOException{
        Alert alertOnModifyProductRemove = new Alert(Alert.AlertType.CONFIRMATION, "Clicking OK will delete Part from Product, do you wish to continue?");
        Optional<ButtonType> result = alertOnModifyProductRemove.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (btmTableModifyProduct.getSelectionModel().isEmpty()) {
                Alert noSelection1 = new Alert(Alert.AlertType.ERROR, "You must first select a part to remove to continue.");

                Optional<ButtonType> result2 = noSelection1.showAndWait();
                if (result2.isPresent() && result2.get() == ButtonType.OK) {
                    return;
                }
            }
            Part pt = btmTableModifyProduct.getSelectionModel().getSelectedItem();
            if (pt != null) {
                Part selectedPart = btmTableModifyProduct.getSelectionModel().getSelectedItem();
                delProduct.deleteAssociatedPart(selectedPart);

            } else {
                return;
            }

        }
    }

    /**This method saves the modified Product
     @param actionEvent Saves Product
     */
    public void onSaveModifyProduct(ActionEvent actionEvent) {
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
                String productName = nameTextFieldModifyProduct.getText();
                String productStock = invTextFieldModifyProduct.getText();
                String productPrice = priceTextFieldModifyProduct.getText();
                String productMax = maxTextFieldModifyProduct.getText();
                String productMin = minTextFieldModifyProduct.getText();

                if (productName == null || productName.length() == 0 || productStock == null || productPrice == null || productMin == null || productMax == null ) {
                    alertEmptyField.showAndWait();
                }
                else{
                id = Integer.parseInt(idTextFieldModifyProduct.getText());
                name = nameTextFieldModifyProduct.getText();
                stock = Integer.parseInt(invTextFieldModifyProduct.getText());
                price = Double.parseDouble(priceTextFieldModifyProduct.getText());
                max = Integer.parseInt(maxTextFieldModifyProduct.getText());
                min = Integer.parseInt(minTextFieldModifyProduct.getText());

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
                ObservableList<Part> associatedParts = delProduct.getAllAssociatedParts();
                for(Part part: associatedParts ){
                    savedProduct.addAssociatedPart(part);
                }
                savedProduct.getAllAssociatedParts().addAll(product.getAllAssociatedParts());
                Inventory.addProduct(savedProduct);
                Inventory.getAllProducts().remove(delProduct);

                Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 600);
                stage.setTitle("Back to Inventory Management System");
                stage.setScene(scene);
                stage.show();
            }}}
            catch(NumberFormatException | IOException ex)
            {
                Alert alertOnAddPart = new Alert(Alert.AlertType.ERROR);
                alertOnAddPart.setTitle("Error Dialog");
                alertOnAddPart.setContentText("Please enter a valid value for each text field!");
                alertOnAddPart.showAndWait();
            }
        }
    }

    /**This method adds Part to the Product
     @param actionEvent Adds Part
     */
    public void onModifyProductAdd(ActionEvent actionEvent)
    {
        if (topTableModifyProduct.getSelectionModel().isEmpty())
        {
        Alert noSelection = new Alert(Alert.AlertType.ERROR, "You must first select a part to add to continue.");

        Optional<ButtonType> result = noSelection.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return;
        }
        }
        Part selectedPart = topTableModifyProduct.getSelectionModel().getSelectedItem();
        delProduct.getAllAssociatedParts();
        delProduct.addAssociatedPart(selectedPart);




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
    /**This method searches the top Parts table
     @param actionEvent Searches Parts table
     */
    public void onModifyProductSearch(ActionEvent actionEvent) {
        String p = searchTextFieldModifyProduct.getText();
        ObservableList<Part> pd2 = searchByPartName(p);

        try {
            int id = Integer.parseInt(p);
            Part pt = getAId(id);
            if (pd2 != null)
                pd2.add(pt);
        } catch (NumberFormatException e) {
            pd2 = searchByPartName(p);
        }
        if (pd2.size() == 0) {
            Alert noResults = new Alert(Alert.AlertType.ERROR);
            noResults.setTitle("No Search Results Found");
            noResults.setContentText("Please try your search again.");
            noResults.showAndWait();
            searchTextFieldModifyProduct.setText("");

        } else {
            topTableModifyProduct.setItems(pd2);
            searchTextFieldModifyProduct.setText("");
        }
    }
}