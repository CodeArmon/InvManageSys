package model;

import controller.InHouse;
import controller.Inventory;
import controller.OutSourced;
import controller.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class is used as a controller for the AddPart screen.*/
public class AddPart implements Initializable {
    public RadioButton inhouseAddPartRdbtn;
    public RadioButton outsourcedAddPartRdbtn;
    public Label machineId;
    public TextField idAddPartTextField;
    public TextField nameAddPartTextField;
    public TextField inventoryAddPartTextField;
    public TextField priceAddPartTextField;
    public TextField maxAddPartTextField;
    public TextField mach_outsoAddPartTextField;
    public TextField minAddPartTextField;

    /**This is an initialize method*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**This method takes you back to main screen. After clicking cancel form will revert to main screen.
     @param actionEvent Back to Main Screen
     @throws IOException Catches Exception
     */
    public void toMain(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all data entered, do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
            {
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 600);
                stage.setTitle("Back to Inventory Management System");
                stage.setScene(scene);
                stage.show();
            }
    }

    /**
     This method sets the text label to Machine ID
     @param actionEvent Sets label to Machine ID
     */
    public void onInhouse(ActionEvent actionEvent) {
        machineId.setText("Machine ID");
    }

    /**
     This method sets the text label to Company Name
     @param actionEvent Sets label to Company Name
     */
    public void onOutsourced(ActionEvent actionEvent) {
        machineId.setText("Company Name");
    }

    public static int partIdIntitalizer = 1000;

    /**
     This method sets the Part ID
     @param P Assigns Part an ID
     @return Returns Part ID
     */
    public static int assignId(Part P)
        {
            P.setId(partIdIntitalizer);
            return P.getId();
        }

    /**This method saves Part to Inventory
     @param actionEvent Saves Part
     @throws IOException Catches Exception
     *RUNTIME ERROR - Number Format Exception was given used try catch block as fix
     */
    public void onActionSavePart(ActionEvent actionEvent) throws IOException {
        Alert alertOnSavePart = new Alert(Alert.AlertType.CONFIRMATION, "Clicking OK will save data, do you wish to continue?");
        Optional<ButtonType> result = alertOnSavePart.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {


            try
            {
                Alert alertEmptyField = new Alert(Alert.AlertType.WARNING, "All Fields must be filled out to save part");
                int id = 0;
                String name = "";
                int stock = 0;
                double price = 0;
                int max = 0;
                int min = 0;
                String partName = nameAddPartTextField.getText();
                String partStock = inventoryAddPartTextField.getText();
                String partPrice = inventoryAddPartTextField.getText();
                String partMax = maxAddPartTextField.getText();
                String partMin = minAddPartTextField.getText();
                String partCompOut = mach_outsoAddPartTextField.getText();


                if (partName == null || partName.length() == 0 || partStock == null || partPrice == null || partMax == null || partMin == null || partCompOut == null) {
                    alertEmptyField.showAndWait();
                }
                else {
                    id = partIdIntitalizer;
                    name = nameAddPartTextField.getText();
                    stock = Integer.parseInt(inventoryAddPartTextField.getText());
                    price = Double.parseDouble(priceAddPartTextField.getText());
                    max = Integer.parseInt(maxAddPartTextField.getText());
                    //int machineId = Integer.parseInt(mach_outsoAddPartTextField.getText());
                    min = Integer.parseInt(minAddPartTextField.getText());
                    partIdIntitalizer += 2;

                if (max < stock || (min > stock)) {
                    Alert alertInv = new Alert(Alert.AlertType.ERROR, "Inventory Value must between Min and Max Values");
                    alertInv.showAndWait();
                }
                else if (min > max) {
                    Alert alertMinLessMax = new Alert(Alert.AlertType.ERROR, "Min Value must be less than Max Value");
                    alertMinLessMax.showAndWait();
                }

                else {
                    if (inhouseAddPartRdbtn.isSelected()) {
                        int machineId = Integer.parseInt(mach_outsoAddPartTextField.getText());
                        Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                    }
                    if (outsourcedAddPartRdbtn.isSelected()) {
                        String companyName = mach_outsoAddPartTextField.getText();
                        Inventory.addPart(new OutSourced(id, name, price, stock, min, max, companyName));
                    }
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1000, 600);
                    stage.setTitle("Back to Inventory Management System");
                    stage.setScene(scene);
                    stage.show();
                }
            }}
            catch (NumberFormatException e)
            {
                Alert alertNonInt = new Alert(Alert.AlertType.ERROR);
                alertNonInt.setTitle("Error Dialog");
                alertNonInt.setContentText("Please enter a Integer value for Inventory, Price, Mix, Max and Machine ID text fields!");
                alertNonInt.showAndWait();
            }
        }

    }

}


