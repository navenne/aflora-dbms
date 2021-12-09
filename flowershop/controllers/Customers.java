package flowershop.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import flowershop.Customer;
import flowershop.dao.DAOException;
import flowershop.dao.DAOManager;
import flowershop.dao.mysql.MySQLDAOManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author Laura Hidalgo Rivera
 *
 */

public class Customers implements Initializable {

  @FXML
  private Button homeButton;

  @FXML
  private Button searchButton;

  @FXML
  private Button newButton;
  
  @FXML
  private Button billButton;

  @FXML
  private Button deleteButton;

  @FXML
  private Button saveButton;

  @FXML
  private TextField searchInput;

  @FXML
  private TextField idInput;

  @FXML
  private TextField nameInput;

  @FXML
  private TextField phoneNumberInput;

  @FXML
  private TableView<Customer> table;

  @FXML
  private TableColumn<Customer, Long> idCol;

  @FXML
  private TableColumn<Customer, String> nameCol;

  @FXML
  private TableColumn<Customer, String> phoneNumberCol;

  private ObservableList<Customer> obList = FXCollections.observableArrayList();

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
      Customer customer = table.getSelectionModel().getSelectedItem();
      idInput.setText(customer.getId().toString());
      nameInput.setText(customer.getName());
      phoneNumberInput.setText(customer.getPhoneNumber());
    } catch (NullPointerException e) {
    }
  }
  
  @FXML
  void onBillButton(ActionEvent event) throws IOException {
    if (idInput.getText() == "") {
      Utils.alert(a, AlertType.INFORMATION, "No customer selected", "Please select a customer to create a bill.");
    } else {
      Customer customer = table.getSelectionModel().getSelectedItem();
      FXMLLoader loader = new FXMLLoader(
          this.getClass().getResource(
            "/flowershop/views/Bills.fxml"
          )
        );
      
      Parent root = (Parent) loader.load();
      
      Bills controller = loader.getController();
      controller.initData(customer);
      
      Scene scene = new Scene(root);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

      stage.setTitle("Flower Shop - Bills");
      stage.setScene(scene);
      stage.show();

      stage.setResizable(false);
      
        
       
    }
  }

  @FXML
  void onDeleteButton(ActionEvent event) {
    try {
      manager = new MySQLDAOManager();
      manager.getCustomerDAO().delete(Long.parseLong(idInput.getText()));
      clear();
      obList.clear();
      showTable();

    } catch (NumberFormatException e) {
      Utils.alert(a, AlertType.ERROR, "Invalid value", "Please enter a number.");
    } catch (DAOException e) {
      Utils.alert(a, AlertType.ERROR, "Delete error", "Could not delete customer from database.");
    } catch (SQLException e) {
      Utils.alert(a, AlertType.ERROR, "Connection failed", "Could not stablish connection with database.");
    }
  }

  @FXML
  void onSaveButton(ActionEvent event) {
    try {
      manager = new MySQLDAOManager();
      if (idInput.getText() == "") {
        manager.getCustomerDAO()
            .insert(new Customer(nameInput.getText(), phoneNumberInput.getText()));
      } else {
        Customer customer = table.getSelectionModel().getSelectedItem();
        customer.setName(nameInput.getText());
        customer.setPhoneNumber(phoneNumberInput.getText());
        manager.getCustomerDAO().update(customer);
      }

      obList.clear();
      showTable();

    } catch (NumberFormatException e) {
      Utils.alert(a, AlertType.ERROR, "Invalid value", "Please enter a number.");
    } catch (DAOException e) {
      Utils.alert(a, AlertType.ERROR, "Error", "Could not save nor modify customer.");
    } catch (SQLException e) {
      Utils.alert(a, AlertType.ERROR, "Connection failed", "Could not stablish connection with database.");
    }
  }

  @FXML
  void onSearchButton(ActionEvent event) {
    try {
      manager = new MySQLDAOManager();
      Customer customer = manager.getCustomerDAO().get(Long.parseLong(searchInput.getText()));

      idInput.setText(customer.getId().toString());
      nameInput.setText(customer.getName());
      phoneNumberInput.setText(customer.getPhoneNumber());

      obList.clear();
      obList.add(customer);
      table.setItems(obList);

    } catch (NumberFormatException e) {
      Utils.alert(a, AlertType.ERROR, "Invalid value", "Please enter a number.");
    } catch (DAOException e) {
      Utils.alert(a, AlertType.ERROR, "Search error", "Could not find customer.");
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
      for (Customer customer : manager.getCustomerDAO().getAll()) {
        obList.add(customer);
      }
    } catch (DAOException e) {
      Utils.alert(a, AlertType.ERROR, "Search error", "Could not find any customers.");
    } catch (SQLException e) {
      Utils.alert(a, AlertType.ERROR, "Connection failed", "Could not stablish connection with database.");
    }

    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    table.setItems(obList);
  }

  private void clear() {
    searchInput.clear();
    idInput.clear();
    nameInput.clear();
    phoneNumberInput.clear();
  }
}
