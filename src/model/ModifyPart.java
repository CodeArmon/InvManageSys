package model;

import controller.*;
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

/** This class is used as a controller for the ModifyPart screen.*/
public class ModifyPart implements Initializable {
    public RadioButton inhouseRdbtnModifyPart;
    public RadioButton outsourcedRdbtnModifyPart;
    public Label machineIdModify;
    public TextField idTextFieldModifyPart;
    public TextField nameTextFieldModifyPart;
    public TextField invTextFieldModifyPart;
    public TextField priceTextFieldModifyPart;
    public TextField maxTextFieldModifyPart;
    public TextField mach_outTextFieldModifyPart;
    public TextField minTextFieldModifyPart;
    public Label modPartIdLabel;
    public Label modPartNameLabel;
    public Label modPartInvLabel;
    public Label modPartPriceLabel;
    public Label modPartMaxLabel;
    public Label modPartMinLabel;
    Part delPart = null;


    /**This method sends Part data from Main Screen to Modify form
     @param part Sends Part
     */
        public void sendPart(Part part)
    {
        delPart = part;
        idTextFieldModifyPart.setText(String.valueOf(part.getId()));
        nameTextFieldModifyPart.setText(part.getName());
        invTextFieldModifyPart.setText(String.valueOf(part.getStock()));
        priceTextFieldModifyPart.setText(String.valueOf(part.getPrice()));
        minTextFieldModifyPart.setText(String.valueOf(part.getMin()));
        maxTextFieldModifyPart.setText(String.valueOf(part.getMax()));

        if(part instanceof InHouse)
        {
            inhouseRdbtnModifyPart.setSelected(true);
            mach_outTextFieldModifyPart.setText(String.valueOf(((InHouse) part).getMachineId()));
        }
        else if(part instanceof OutSourced)
        {
            outsourcedRdbtnModifyPart.setSelected(true);
            mach_outTextFieldModifyPart.setText(((OutSourced) part).getCompanyName());
            machineIdModify.setText("Company Name");
        }
    }

    /**This is an initialize method*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**This method takes you back to main screen. After clicking cancel form will revert to main screen.
     @param actionEvent Back to Main Screen
     @throws IOException Catches Exception
     */
    public void toMain(ActionEvent actionEvent) throws IOException {
        Alert alertModifyCancel = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all data entered, do you wish to continue?");

        Optional<ButtonType> result = alertModifyCancel.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
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
        machineIdModify.setText("Machine ID");
    }

    /**
     This method sets the text label to Company Name
     @param actionEvent Sets label to Company Name
     */
    public void onOutsourced(ActionEvent actionEvent) {
        machineIdModify.setText("Company Name");
    }

    /**This method saves modified Part to Inventory
     @param actionEvent Saves modified Part
     @throws IOException Catches Exception
     */
    public void modifySave(ActionEvent actionEvent) throws IOException
    {
        Alert alertOnSavePart = new Alert(Alert.AlertType.CONFIRMATION, "Clicking OK will save data, do you wish to continue?");

        Optional<ButtonType> result = alertOnSavePart.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            try {
                Alert alertEmptyField = new Alert(Alert.AlertType.WARNING, "All Fields must be filled out to save part");
                String partName = nameTextFieldModifyPart.getText();
                String partStock = invTextFieldModifyPart.getText();
                String partPrice = priceTextFieldModifyPart.getText();
                String partMax = maxTextFieldModifyPart.getText();
                String partMin = minTextFieldModifyPart.getText();
                String partCompOut = mach_outTextFieldModifyPart.getText();
                int id = 0;
                String name = "";
                int stock = 0;
                double price = 0;
                int max = 0;
                int min = 0;


                if (partName.isEmpty() || partName.length() == 0 || partStock == null || partPrice == null || partMax == null || partMin == null || partCompOut == null) {
                    alertEmptyField.showAndWait();
                }
                else {
                    id = Integer.parseInt(idTextFieldModifyPart.getText());
                    name = nameTextFieldModifyPart.getText();
                    stock = Integer.parseInt(invTextFieldModifyPart.getText());
                    price = Double.parseDouble(priceTextFieldModifyPart.getText());
                    max = Integer.parseInt(maxTextFieldModifyPart.getText());
                    min = Integer.parseInt(minTextFieldModifyPart.getText());


                if (max < stock || (min > stock)) {
                    Alert alertInv = new Alert(Alert.AlertType.ERROR, "Inventory Value must between Min and Max Values");
                    alertInv.showAndWait();
                }
                else if (min > max) {
                    Alert alertMinLessMax = new Alert(Alert.AlertType.ERROR, "Min Value must be less than Max Value");
                    alertMinLessMax.showAndWait();
                }


                else {

                    if(inhouseRdbtnModifyPart.isSelected()){
                        int machineId = Integer.parseInt(mach_outTextFieldModifyPart.getText());
                        Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                        Inventory.getAllParts().remove(delPart);
                    }
                    if (outsourcedRdbtnModifyPart.isSelected())
                    {
                        String companyName = mach_outTextFieldModifyPart.getText();
                        Inventory.addPart(new OutSourced(id, name, price, stock, min, max, companyName));
                        Inventory.getAllParts().remove(delPart);
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