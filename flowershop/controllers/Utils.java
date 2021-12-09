package flowershop.controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Utils {

  void switchScene(String fxml, ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(this.getClass().getResource("/flowershop/views/" + fxml + ".fxml"));
 
    Scene scene = new Scene(root);

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    stage.setTitle("Flower Shop - " + fxml);
    stage.setScene(scene);
    stage.show();

    stage.setResizable(false);
  }

  static void alert(Alert a, AlertType alertType, String headerText, String contentText) {
    a.setAlertType(alertType);
    a.setHeaderText(headerText);
    a.setContentText(contentText);
    if (alertType == AlertType.CONFIRMATION) {
      Optional<ButtonType> action = a.showAndWait();
      if (action.get() == ButtonType.OK) {
        try (Connection conn =
            DriverManager.getConnection("jdbc:mysql://localhost/flowershop", "root", "");
            PreparedStatement pst = conn.prepareStatement("DROP DATABASE IF EXISTS flowershop");) {
          pst.executeUpdate();
        } catch (SQLException e){
        }
        try {
          createDB();
        } catch (SQLException e) {
          System.err.println("Could not create database.");
        }
      }
    } else {
      a.show();
    }
    
  }
  
  static void createDB() throws SQLException {
    try (
        BufferedReader inputStream = new BufferedReader(new FileReader("src/flowershop/controllers/flowershop.sql"));
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost", "root", "");
        Scanner s = new Scanner(inputStream);) {

      s.useDelimiter("(;(\r)?\n)|(--\n)");
      try (Statement st = conn.createStatement();) {
        while (s.hasNext()) {
          String line = s.next();
          if (line.startsWith("/!") && line.endsWith("/")) {
            int i = line.indexOf(' ');
            line = line.substring(i + 1, line.length() - " */".length());
          }

          if (line.trim().length() > 0) {
            st.execute(line);
          }
        }
      } catch (SQLException e) {
        System.err.println("Could not create database.");
      }
    } catch (FileNotFoundException e) {
      System.err.println("Could not find file flowershop.sql");
    } catch (IOException e) {
      System.err.println("Could not close inputStream");
    }
  
}
}
