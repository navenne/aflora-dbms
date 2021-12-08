package flowershop.controllers;
/**
 * @author Laura Hidalgo Rivera
 */

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import flowershop.Bouquet;
import flowershop.Flower;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Bouquets implements Initializable {

    @FXML
    private Button homeButton;

    @FXML
    private TableView<Flower> table1;
    
    @FXML
    private TableView<AuxBouquet> table2;

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

    @FXML
    private TableColumn<AuxBouquet, String> flowerCol;

    @FXML
    private TableColumn<AuxBouquet, Float> priceCol2;
    
    @FXML
    private TableColumn<AuxBouquet, Integer> quantityCol;

    @FXML
    private TableColumn<AuxBouquet, Float> subtotalCol;
    
    private ObservableList<Flower> obList = FXCollections.observableArrayList();
    private ObservableList<AuxBouquet> obList2 = FXCollections.observableArrayList();
    
    private Set<AuxBouquet> setBouquets = new TreeSet<>();

    private DAOManager manager;

    private Alert a = new Alert(null);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      showTable();
    }

    @FXML
    void backToHome(ActionEvent event) throws IOException {
      Parent root = FXMLLoader.load(this.getClass().getResource("/flowershop/views/Menu.fxml"));

      Scene scene = new Scene(root);

      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

      stage.setTitle("Flower Shop");
      stage.setScene(scene);
      stage.show();

      stage.setResizable(false);
    }

    @FXML
    void onTableItemSelected(MouseEvent event) {
      try {
        Flower flower = table1.getSelectionModel().getSelectedItem();
        AuxBouquet auxBouquet = new AuxBouquet(flower, 1);
        for (AuxBouquet aB : setBouquets) {
          if(aB.equals(auxBouquet)) {
            if (flower.getStock() > aB.getQuantity()) {
              aB.setQuantity((aB.getQuantity()+1));
              aB.setSubtotal((float) (Math.round((aB.getFlower().getPrice()*aB.getQuantity())*100.0)/100.0));
            } else {
              alert(AlertType.ERROR, "Invalid flower quantity", "There are no more " + aB.getFlowerText() + " available.");
            }
          }
        }
        
        
        setBouquets.add(auxBouquet);
        
        
        showTable2();
      } catch (NullPointerException e) {
      }
    }
    
    @FXML
    void onTable2ItemSelected(MouseEvent event) {
      try {
        boolean flag = false;
        AuxBouquet auxBouquet = table2.getSelectionModel().getSelectedItem();
        for (AuxBouquet aB : setBouquets) {
          if(aB.equals(auxBouquet)) {
            if (aB.getQuantity() > 1) {
              aB.setQuantity((aB.getQuantity()-1));
              aB.setSubtotal(Math.round((aB.getFlower().getPrice()*aB.getQuantity())*100)/100);
              setBouquets.add(auxBouquet);
            } else {
              flag = true;
            }
          }
        }
        if(flag) setBouquets.remove(auxBouquet);
        showTable2();
      } catch (NullPointerException e) {
      }
    }
    
    @FXML
    void onSaveButton(ActionEvent event) {
      Bouquet bouquet = new Bouquet();
      for (AuxBouquet auxBouquet : setBouquets) {
        bouquet.addFlower(auxBouquet.getFlower(), auxBouquet.getQuantity());
      }

      try {
        DAOManager manager = new MySQLDAOManager();
        manager.getBouquetDAO().insert(bouquet);
        
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (DAOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
    }
    
    private void showTable() {
      try {
        manager = new MySQLDAOManager();
        for (Flower flower : manager.getFlowerDAO().getAll()) {
          obList.add(flower);
        }
      } catch (DAOException e) {
        alert(AlertType.ERROR, "Search error", "Could not find any customers.");
      } catch (SQLException e) {
        alert(AlertType.ERROR, "Connection failed", "Could not stablish connection with database.");
      }

      idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
      speciesCol.setCellValueFactory(new PropertyValueFactory<>("species"));
      colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
      priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
      stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

      table1.setItems(obList);
    }
    
    private void showTable2() {
      obList2.clear();
      for (AuxBouquet auxBouquet : setBouquets) {
        obList2.add(auxBouquet);
      }

      flowerCol.setCellValueFactory(new PropertyValueFactory<>("flowerText"));
      priceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
      quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
      subtotalCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

      
      table2.setItems(obList2);
    }    
    private void alert(AlertType alertType, String headerText, String contentText) {
      a.setAlertType(alertType);
      a.setHeaderText(headerText);
      a.setContentText(contentText);
      a.show();
    }

    public class AuxBouquet implements Comparable<AuxBouquet> {
      private Flower flower;
      private String flowerText;
      private float price;
      private int quantity;
      private float subtotal;


      public AuxBouquet(Flower flower, int quantity) {
        this.flower = flower;
        this.quantity = quantity;
        this.price = this.flower.getPrice();
        this.subtotal = this.price*quantity;
        this.flowerText = this.flower.getColor() + " " + this.flower.getSpecies();
      }


      public String getFlowerText() {
        return flowerText;
      }


      public Flower getFlower() {
        return flower;
      }


      public void setFlower(Flower flower) {
        this.flower = flower;
      }


      public float getPrice() {
        return price;
      }


      public void setPrice(float price) {
        this.price = price;
      }


      public int getQuantity() {
        return quantity;
      }


      public void setQuantity(int quantity) {
        this.quantity = quantity;
      }


      public float getSubtotal() {
        return subtotal;
      }


      public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
      }


      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + getEnclosingInstance().hashCode();
        result = prime * result + Objects.hash(flowerText);
        return result;
      }


      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (getClass() != obj.getClass())
          return false;
        AuxBouquet other = (AuxBouquet) obj;
        if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
          return false;
        return Objects.equals(flowerText, other.flowerText);
      }


      private Bouquets getEnclosingInstance() {
        return Bouquets.this;
      }


      @Override
      public int compareTo(AuxBouquet o) {
        return this.getFlower().getId().compareTo(o.getFlower().getId());
      }
      
      
    }
}



