package flowershop;

import java.io.IOException;
import flowershop.controllers.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * @author Laura Hidalgo Rivera
 *
 */

public class Launcher extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws IOException {
    Alert a = new Alert(null);
    Utils.alert(a, AlertType.CONFIRMATION, "Database info", "Create clean database?");
    Parent root = FXMLLoader.load(this.getClass().getResource("views/Menu.fxml"));
 
    Scene scene = new Scene(root);

    stage.setTitle("Aflora - a flowershop management system");
    stage.setScene(scene);
    stage.show();

    stage.setResizable(false);
  }
}