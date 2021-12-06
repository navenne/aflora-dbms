package flowershop;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    Parent root = FXMLLoader.load(this.getClass().getResource("views/Menu.fxml"));
 
    Scene scene = new Scene(root);

    stage.setTitle("Flower Shop");
    stage.setScene(scene);
    stage.show();

    stage.setResizable(false);
  }
}