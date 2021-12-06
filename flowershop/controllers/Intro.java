package flowershop.controllers;

import java.sql.SQLException;
import flowershop.Flower;
import flowershop.dao.DAOException;
import flowershop.dao.DAOManager;
import flowershop.dao.mysql.MySQLDAOManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Intro {

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private TextField input2;

    @FXML
    private TextField input3;

    @FXML
    private TextField input4;

    @FXML
    private Button insertButton;

    @FXML
    private TextField insertColor;

    @FXML
    private TextField insertPrice;

    @FXML
    private TextField insertSpecies;

    @FXML
    private TextField insertStock;

    @FXML
    private TextArea insertTextArea;

    @FXML
    private TextArea textArea;

    @FXML
    private TextArea textArea2;

    @FXML
    private TextArea textArea3;

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void insert(ActionEvent event) throws DAOException {
      try {
        DAOManager manager = new MySQLDAOManager();
        Flower flower = new Flower(insertSpecies.getText(), insertColor.getText(), Float.parseFloat(insertPrice.getText()), Integer.parseInt(insertStock.getText()));
        manager.getFlowerDAO().insert(flower);
        insertTextArea.setText(flower.toString());
        insertTextArea.appendText("Flower inserted");
      } catch (SQLException e) {
        throw new DAOException("Could not stablish connection with database.");
      }
    }

    @FXML
    void modify(ActionEvent event) {

    }

    @FXML
    void search(ActionEvent event) {

    }

}