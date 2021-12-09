package flowershop.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import flowershop.Flower;
import flowershop.dao.DAOException;
import flowershop.dao.DAOManager;
import flowershop.dao.mysql.MySQLDAOManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * @author Laura Hidalgo Rivera
 *
 */

public class Flowers implements Initializable {

  @FXML
  private Button homeButton;

  @FXML
  private Button searchButton;

  @FXML
  private Button newButton;

  @FXML
  private Button deleteButton;

  @FXML
  private Button saveButton;

  @FXML
  private TextField searchInput;

  @FXML
  private TextField idInput;

  @FXML
  private TextField speciesInput;

  @FXML
  private TextField colorInput;
  
  @FXML
  private TextField priceInput;
  
  @FXML
  private TextField stockInput;

  @FXML
  private TableView<Flower> table;

  @FXML
  private TableColumn<Flower, Long> idCol;

  @FXML
  private TableColumn<Flower, String> speciesCol;

  @FXML
  private TableColumn<Flower, String> colorCol;
  
  @FXML
  private TableColumn<Flower, Float> priceCol;
  
  @FXML
  private TableColumn<Flower, Integer> stockCol;

  private ObservableList<Flower> obList = FXCollections.observableArrayList();

  private DAOManager manager;

  private Utils utils = new Utils();
  
  private Alert a = new Alert(null);

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    idInput.setDisable(true);
    showTable();
  }

  @FXML
  void onTableItemSelected(MouseEvent event) {
    try {
      Flower flower = table.getSelectionModel().getSelectedItem();
      idInput.setText(flower.getId().toString());
      speciesInput.setText(flower.getSpecies());
      colorInput.setText(flower.getColor());
      priceInput.setText(String.valueOf(flower.getPrice()));
      stockInput.setText(String.valueOf(flower.getStock()));
    } catch (NullPointerException e) {
    }
  }

  @FXML
  void onDeleteButton(ActionEvent event) {
    try {
      manager = new MySQLDAOManager();
      manager.getFlowerDAO().delete(Long.parseLong(idInput.getText()));
      clear();
      obList.clear();
      showTable();

    } catch (NumberFormatException e) {
      Utils.alert(a, AlertType.ERROR, "Invalid value", "Please enter a number.");
    } catch (DAOException e) {
      Utils.alert(a, AlertType.ERROR, "Delete error", "Could not delete flower from database.");
    } catch (SQLException e) {
      Utils.alert(a, AlertType.ERROR, "Connection failed", "Could not stablish connection with database.");
    }
  }

  @FXML
  void onSaveButton(ActionEvent event) {
    try {
      manager = new MySQLDAOManager();
      if (idInput.getText() == "") {
        manager.getFlowerDAO()
            .insert(new Flower(speciesInput.getText(), colorInput.getText(), Float.parseFloat(priceInput.getText()), Integer.parseInt(stockInput.getText())));
      } else {
        Flower flower = table.getSelectionModel().getSelectedItem();
        flower.setSpecies(speciesInput.getText());
        flower.setColor(colorInput.getText());
        flower.setPrice(Float.parseFloat(priceInput.getText()));
        flower.setStock(Integer.parseInt(stockInput.getText()));
        manager.getFlowerDAO().update(flower);
      }

      obList.clear();
      showTable();

    } catch (NumberFormatException e) {
      Utils.alert(a, AlertType.ERROR, "Invalid value", "Please enter a number.");
    } catch (DAOException e) {
      Utils.alert(a, AlertType.ERROR, "Error", "Could not save nor modify flower.");
    } catch (SQLException e) {
      Utils.alert(a, AlertType.ERROR, "Connection failed", "Could not stablish connection with database.");
    }
  }

  @FXML
  void onSearchButton(ActionEvent event) {
    try {
      manager = new MySQLDAOManager();
      Flower flower = manager.getFlowerDAO().get(Long.parseLong(searchInput.getText()));

      idInput.setText(flower.getId().toString());
      speciesInput.setText(flower.getSpecies());
      colorInput.setText(flower.getColor());
      priceInput.setText(String.valueOf(flower.getPrice()));
      stockInput.setText(String.valueOf(flower.getStock()));

      obList.clear();
      obList.add(flower);
      table.setItems(obList);

    } catch (NumberFormatException e) {
      Utils.alert(a, AlertType.ERROR, "Invalid value", "Please enter a number.");
    } catch (DAOException e) {
      Utils.alert(a, AlertType.ERROR, "Search error", "Could not find flower.");
    } catch (SQLException e) {
      Utils.alert(a, AlertType.ERROR, "Connection failed", "Could not stablish connection with database.");
    }
  }

  @FXML
  void onBackToListButton(ActionEvent event) {
    clear();
    obList.clear();
    showTable();
  }

  @FXML
  void backToHome(ActionEvent event) throws IOException {
    utils.switchScene("Menu", event);
  }

  private void showTable() {
    try {
      manager = new MySQLDAOManager();
      for (Flower flower : manager.getFlowerDAO().getAll()) {
        obList.add(flower);
      }
    } catch (DAOException e) {
      Utils.alert(a, AlertType.ERROR, "Search error", "Could not find any flowers.");
    } catch (SQLException e) {
      Utils.alert(a, AlertType.ERROR, "Connection failed", "Could not stablish connection with database.");
    }

    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    speciesCol.setCellValueFactory(new PropertyValueFactory<>("species"));
    colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
    priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

    table.setItems(obList);
  }

  private void clear() {
    searchInput.clear();
    idInput.clear();
    speciesInput.clear();
    colorInput.clear();
    priceInput.clear();
    stockInput.clear();
  }
}
