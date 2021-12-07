package flowershop.dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import flowershop.dao.BillDAO;
import flowershop.dao.BouquetDAO;
import flowershop.dao.CustomerDAO;
import flowershop.dao.DAOManager;
import flowershop.dao.FlowerDAO;
import flowershop.dao.LineDAO;

/**
 * @author Laura Hidalgo Rivera
 *
 */

public class MySQLDAOManager implements DAOManager {
  
  private Connection conn;
  
  private static final String DB_URL = "jdbc:mysql://localhost/flowershop?";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "";
  
  private CustomerDAO client = null;
  private BillDAO bill = null;
  private LineDAO line = null;
  private BouquetDAO bouquet = null;
  private FlowerDAO flower = null;

  public MySQLDAOManager() throws SQLException {
      conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
  }
  
  @Override
  public CustomerDAO getCustomerDAO() {
    if (client == null) {
      client = new MySQLCustomerDAO(conn);
    }
    return client;
  }

  @Override
  public BillDAO getBillDAO() {
    if (bill == null) {
      bill = new MySQLBillDAO(conn);
    }
    return bill;
  }

  @Override
  public LineDAO getLineDAO() {
    if (line == null) {
      line = new MySQLLineDAO(conn);
    }
    return line;
  }

  @Override
  public BouquetDAO getBouquetDAO() {
    if (bouquet == null) {
      bouquet = new MySQLBouquetDAO(conn);
    }
    return bouquet;
  }

  @Override
  public FlowerDAO getFlowerDAO() {
    if (flower == null) {
      flower = new MySQLFlowerDAO(conn);
    }
    return flower;
  }
  
  

}
