package flowershop.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import flowershop.Bill;
import flowershop.dao.BillDAO;
import flowershop.dao.DAOException;

public class MySQLBillDAO implements BillDAO {
  
  private String INSERT = "INSERT INTO bill (date, total, customerId) VALUES (?,?,?)";
  private String UPDATE = "UPDATE bill SET date = ?, total = ?, customerId = ? WHERE id = ?";
  private String DELETE = "DELETE FROM bill WHERE id = ?";
  private String GETALL = "SELECT * FROM bill";
  private String GET = "SELECT * FROM bill WHERE id = ?";
  
  private Connection conn;
  
  public MySQLBillDAO(Connection conn) {
    this.conn = conn;
  }

  @Override
  public void insert(Bill b) throws DAOException {
    try (PreparedStatement pst = conn.prepareStatement(INSERT);) {
      
      pst.setDate(1, new Date(b.getDate().getTime()));
      pst.setFloat(2, b.getTotal());
      pst.setLong(3, b.getCustomerId());

      if (pst.executeUpdate() == 0) {
        throw new DAOException("Bill could not be inserted.");
      }
      
      
    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
  }

  @Override
  public void update(Bill b) throws DAOException {
    try (PreparedStatement pst = conn.prepareStatement(UPDATE);) {
      
      pst.setDate(1, new Date(b.getDate().getTime()));
      pst.setFloat(2, b.getTotal());
      pst.setLong(3, b.getCustomerId());
      pst.setLong(4, b.getId());

      if (pst.executeUpdate() == 0) {
        throw new DAOException("Bill could not be updated.");
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
        throw new DAOException("Bill could not be deleted.");
      }
      
    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
  }

  @Override
  public List<Bill> getAll() throws DAOException {
    List<Bill> bills = new ArrayList<Bill>();
    try (PreparedStatement pst = conn.prepareStatement(GETALL);
        ResultSet rs = pst.executeQuery();) {
        
        while(rs.next()) {
          Bill bill = new Bill(rs.getDate("date"), rs.getFloat("total"), rs.getLong("customerId"));
          bill.setId(rs.getLong("id"));
          bills.add(bill);
      }

    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
    
    return bills;
  }

  @Override
  public Bill get(Long id) throws DAOException {
    Bill bill = null;
    try (PreparedStatement pst = conn.prepareStatement(GET);
        ResultSet rs = pst.executeQuery();) {
        
        pst.setLong(1, id);
        
        if (rs.next()) {
          bill = new Bill(rs.getDate("date"), rs.getFloat("total"), rs.getLong("customerId"));
          bill.setId(rs.getLong("id"));
        } else {
          throw new DAOException("No bill found with id " + id);
        }

    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
    return bill;
  }


}
