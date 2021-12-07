module flowershop {
  requires transitive javafx.graphics;
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires javafx.base;
  
  opens flowershop to javafx.fxml;
  opens flowershop.controllers to javafx.fxml;
  
  exports flowershop;
  exports flowershop.controllers;
}
