package flowershop.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import flowershop.Customer;
import flowershop.dao.CustomerDAO;
import flowershop.dao.DAOException;

public class MySQLCustomerDAO implements CustomerDAO {

  private String INSERT = "INSERT INTO customer (name, phoneNumber) VALUES (?,?)";
  private String UPDATE = "UPDATE customer SET name = ?, phoneNumber = ? WHERE id = ?";
  private String DELETE = "DELETE FROM customer WHERE id = ?";
  private String GETALL = "SELECT * FROM customer";
  private String GET = "SELECT * FROM customer WHERE id = ?";

  private Connection conn;

  public MySQLCustomerDAO(Connection conn) {
    this.conn = conn;
  }

  @Override
  public void insert(Customer c) throws DAOException {
    try (PreparedStatement pst = conn.prepareStatement(INSERT);) {

      pst.setString(1, c.getName());
      pst.setString(2, c.getPhoneNumber());

      if (pst.executeUpdate() == 0) {
        throw new DAOException("Customer could not be inserted.");
      }


    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
  }

  @Override
  public void update(Customer c) throws DAOException {
    try (PreparedStatement pst = conn.prepareStatement(UPDATE);) {

      pst.setString(1, c.getName());
      pst.setString(2, c.getPhoneNumber());
      pst.setLong(3, c.getId());

      if (pst.executeUpdate() == 0) {
        throw new DAOException("Customer could not be updated.");
      }

    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
  }

  @Override
  public void delete(Long id) throws DAOException {
    try (PreparedStatement pst = conn.prepareStatement(DELETE);) {
      pst.setLong(1, id);

      if (pst.executeUpdate() == 0) {
        throw new DAOException("Customer could not be deleted.");
      }

    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
  }

  @Override
  public List<Customer> getAll() throws DAOException {
    List<Customer> customers = new ArrayList<Customer>();
    try (PreparedStatement pst = conn.prepareStatement(GETALL);
        ResultSet rs = pst.executeQuery();) {

      while (rs.next()) {
        Customer customer = new Customer(rs.getString("name"), rs.getString("phoneNumber"));
        customer.setId(rs.getLong("id"));
        customers.add(customer);
      }

    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }

    return customers;
  }

  @Override
  public Customer get(Long id) throws DAOException {
    Customer customer = null;
    try (PreparedStatement pst = conn.prepareStatement(GET);) {

      pst.setLong(1, id);
      ResultSet rs = pst.executeQuery();

      if (rs.next()) {
        customer = new Customer(rs.getString("name"), rs.getString("phoneNumber"));
        customer.setId(rs.getLong("id"));
      } else {
        throw new DAOException("No customer found with id " + id);
      }

    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
    return customer;
  }

}
