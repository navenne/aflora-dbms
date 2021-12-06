module flowershop {
  requires transitive javafx.graphics;
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  
  opens flowershop to javafx.fxml;
  opens flowershop.controllers to javafx.fxml;
  
  exports flowershop;
  exports flowershop.controllers;
}
