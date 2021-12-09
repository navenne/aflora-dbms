package flowershop.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import flowershop.Bill;
import flowershop.Bouquet;
import flowershop.Customer;
import flowershop.Flower;
import flowershop.Line;
import flowershop.dao.DAOException;
import flowershop.dao.DAOManager;
import flowershop.dao.mysql.MySQLDAOManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class Bills implements Initializable {

  @FXML
  private Button homeButton;

  @FXML
  private Button bouquetButton;

  @FXML
  private Button saveButton;

  @FXML
  private TextField customerInput;

  @FXML
  private TextField totalInput;

  @FXML
  private TableView<BillLine> table1;

  @FXML
  private TableColumn<BillLine, String> bouquetCol;

  @FXML
  private TableColumn<BillLine, Float> priceCol1;

  @FXML
  private TableColumn<BillLine, Integer> quantityCol1;

  @FXML
  private TableColumn<BillLine, Float> subtotalCol1;

  @FXML
  private TableView<Flower> table2;

  @FXML
  private TableColumn<Flower, Long> idCol;
  
  @FXML
  private TableColumn<Flower, String> speciesCol;
  
  @FXML
  private TableColumn<Flower, String> colorCol;
  
  @FXML
  private TableColumn<Flower, Float> priceCol2;
  
  @FXML
  private TableColumn<Flower, Integer> stockCol;

  @FXML
  private TableView<AuxBouquet> table3;

  @FXML
  private TableColumn<AuxBouquet, String> flowerCol;

  @FXML
  private TableColumn<AuxBouquet, Float> priceCol3;
  
  @FXML
  private TableColumn<AuxBouquet, Integer> quantityCol3;

  @FXML
  private TableColumn<AuxBouquet, Float> subtotalCol3;
  
  private ObservableList<BillLine> obList1 = FXCollections.observableArrayList();
  private ObservableList<Flower> obList2 = FXCollections.observableArrayList();
  private ObservableList<AuxBouquet> obList3 = FXCollections.observableArrayList();
  
  private Set<AuxBouquet> setBouquets = new TreeSet<>();
  private Set<BillLine> setBillLines = new HashSet<>();

  private DAOManager manager;
  
  private Customer customer;

  private Utils utils = new Utils();
  
  private Alert a = new Alert(null);

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    customerInput.setDisable(true);
    totalInput.setDisable(true);
    showTable2();
  }

  void initData(Customer customer) {
    this.customer = customer;
    customerInput.setText(customer.getName());
  }

  @FXML
  void backToHome(ActionEvent event) {
    try {
      utils.switchScene("Menu", event);
    } catch (IOException e) {
      Utils.alert(a, AlertType.ERROR, "Scene Error", "Scene could not be loaded.");
    }
  }

  @FXML
  void onBouquetButton(ActionEvent event) {
    Bouquet bouquet = new Bouquet();
    for (AuxBouquet auxBouquet : setBouquets) {
      bouquet.addFlower(auxBouquet.getFlower(), auxBouquet.getQuantity());
    }
    BillLine billLine = new BillLine(bouquet, 1);
    for (BillLine bL : setBillLines) {
      if(bL.getBouquet().equals(bouquet)) {
        bL.setQuantity((bL.getQuantity()+1));
      }
    }
    setBillLines.add(billLine);
    
    setBouquets.clear();
    obList3.clear();
    showTable3();
    
    showTable1();
    
    float total = 0;
    for (BillLine bL : setBillLines) {
      total += bL.getSubtotal();
    }
    
    totalInput.setText(String.valueOf(total));
  }
  
  @FXML
  void onSaveButton(ActionEvent event) {
    try {
      Bill bill = new Bill(new Date(System.currentTimeMillis()), this.customer.getId());
      for (BillLine billLine : setBillLines) {
        Line line = new Line(billLine.getQuantity(), billLine.getSubtotal(), billLine.getBouquet());
        bill.addLine(line);
      }
      manager = new MySQLDAOManager();
      manager.getBillDAO().insert(bill);
      setBillLines.clear();
      obList1.clear();
      showTable1();
    } catch (SQLException e) {
      Utils.alert(a, AlertType.ERROR, "Connection failed", "Could not stablish connection with database.");
    } catch (DAOException e) {
      Utils.alert(a, AlertType.ERROR, "Bill creation failed", "Could not create bill.");
    }
  }

  @FXML
  void onTable2ItemSelected(MouseEvent event) {
    try {
      Flower flower = table2.getSelectionModel().getSelectedItem();
      AuxBouquet auxBouquet = new AuxBouquet(flower, 1);
      for (AuxBouquet aB : setBouquets) {
        if(aB.equals(auxBouquet)) {
          if (flower.getStock() > aB.getQuantity()) {
            aB.setQuantity((aB.getQuantity()+1));
            aB.setSubtotal((float) (Math.round((aB.getFlower().getPrice()*aB.getQuantity())*100.0)/100.0));
          } else {
            Utils.alert(a, AlertType.ERROR, "Invalid flower quantity", "There are no more " + aB.getFlowerText() + " available.");
          }
        }
      }
      
      setBouquets.add(auxBouquet);
      
      showTable3();
    } catch (NullPointerException e) {
    }
  }
  
  @FXML
  void onTable3ItemSelected(MouseEvent event) {
  try {
    boolean flag = false;
    AuxBouquet auxBouquet = table3.getSelectionModel().getSelectedItem();
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
    showTable3();
  } catch (NullPointerException e) {
  }
  }
  
  private void showTable1() {
    obList1.clear();
    for (BillLine billLine : setBillLines) {
      obList1.add(billLine);
    }

    bouquetCol.setCellValueFactory(new PropertyValueFactory<>("bouquetText"));
    priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
    quantityCol1.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    subtotalCol1.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

    table1.setItems(obList1);
  }

  private void showTable2() {
    try {
      manager = new MySQLDAOManager();
      for (Flower flower : manager.getFlowerDAO().getAll()) {
        obList2.add(flower);
      }
    } catch (DAOException e) {
      Utils.alert(a, AlertType.ERROR, "Search error", "Could not find any customers.");
    } catch (SQLException e) {
      Utils.alert(a, AlertType.ERROR, "Connection failed", "Could not stablish connection with database.");
    }

    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    speciesCol.setCellValueFactory(new PropertyValueFactory<>("species"));
    colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
    priceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
    stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

    table2.setItems(obList2);
  }
  
  private void showTable3() {
    obList3.clear();
    for (AuxBouquet auxBouquet : setBouquets) {
      obList3.add(auxBouquet);
    }

    flowerCol.setCellValueFactory(new PropertyValueFactory<>("flowerText"));
    priceCol3.setCellValueFactory(new PropertyValueFactory<>("price"));
    quantityCol3.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    subtotalCol3.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

    
    table3.setItems(obList3);
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


    private Bills getEnclosingInstance() {
      return Bills.this;
    }


    @Override
    public int compareTo(AuxBouquet o) {
      return this.getFlower().getId().compareTo(o.getFlower().getId());
    }
    
    
  }

  public class BillLine {
    private Bouquet bouquet;
    private String bouquetText;
    private float price;
    private int quantity;
    private float subtotal;


    public BillLine(Bouquet bouquet, int quantity) {
      this.bouquet = bouquet;
      this.bouquetText = bouquet.name();
      this.price = bouquet.total();
      this.quantity = quantity;
      this.subtotal = this.price*this.quantity;
    }


    public Bouquet getBouquet() {
      return bouquet;
    }


    public String getBouquetText() {
      return bouquetText;
    }


    public float getPrice() {
      return price;
    }


    public int getQuantity() {
      return quantity;
    }


    public float getSubtotal() {
      return subtotal;
    }


    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }

    
  }
}
