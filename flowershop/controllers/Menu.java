package flowershop.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Laura Hidalgo Rivera
 *
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class Menu {

    @FXML
    private Button billsButton;

    @FXML
    private Button bouquetMakerButton;

    @FXML
    private Button customersButton;

    @FXML
    private Button flowersButton;

    @FXML
    private Label title;
    
    private Utils utils = new Utils();

    private Alert a = new Alert(null);
    
    @FXML
    void viewCustomers(ActionEvent event) {
      try {
        utils.switchScene("Customers", event);
      } catch (IOException e) {
        Utils.alert(a, AlertType.ERROR, "Scene error", "Scene could not be loaded.");
      }
    }

    @FXML
    void viewFlowers(ActionEvent event) {
      try {
        utils.switchScene("Flowers", event);
      } catch (IOException e) {
        Utils.alert(a, AlertType.ERROR, "Scene error", "Scene could not be loaded.");
      }
    }

}

