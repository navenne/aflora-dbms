package flowershop.controllers;

import java.io.IOException;

/**
 * @author Laura Hidalgo Rivera
 *
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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

    @FXML
    void viewBills(ActionEvent event) {
      try {
        switchScene("Bills", event);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    @FXML
    void viewBouquetMaker(ActionEvent event) {
      try {
        switchScene("Bouquets", event);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    @FXML
    void viewCustomers(ActionEvent event) {
      try {
        switchScene("Customers", event);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    @FXML
    void viewFlowers(ActionEvent event) {
      try {
        switchScene("Flowers", event);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    
    private void switchScene(String fxml, ActionEvent event) throws IOException {
      Parent root = FXMLLoader.load(this.getClass().getResource("/flowershop/views/" + fxml + ".fxml"));
      
      Scene scene = new Scene(root);

      Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
      
      stage.setTitle("Flower Shop - " + fxml);
      stage.setScene(scene);
      stage.show();

      stage.setResizable(false);
    }

}

