package flowershop.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import flowershop.Bill;
import flowershop.Flower;
import flowershop.Line;
import flowershop.dao.BillDAO;
import flowershop.dao.DAOException;

public class MySQLBillDAO implements BillDAO {

  private String INSERT = "INSERT INTO bill (date, customerId) VALUES (?,?)";
  private String RETRIEVE_ID = "SELECT last_insert_id() \"id\"";
  private String INSERT_LINE = "INSERT INTO line (quantity, subtotal, billId) VALUES (?,?,?)";
  private String INSERT_BOUQUET = "INSERT INTO bouquet (line_id) VALUES (?)";
  private String INSERT_FLOWER_BOUQUET = "INSERT INTO flowerBouquet (bouquetId, flowerId, quantity) VALUES (?,?,?)";
  private String UPDATE = "UPDATE bill SET date = ?, customerId = ? WHERE id = ?";
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
      conn.setAutoCommit(false);

      pst.setDate(1, new Date(b.getDate().getTime()));
      pst.setLong(2, b.getCustomerId());

      if (pst.executeUpdate() == 0) {
        throw new DAOException("Bill could not be inserted.");
      }
      Long billId = null;
      try (PreparedStatement pst2 = conn.prepareStatement(RETRIEVE_ID);) {
        ResultSet rs = pst2.executeQuery();
        rs.next();
        billId = rs.getLong("id");
      }

      try (PreparedStatement pst3 = conn.prepareStatement(INSERT_LINE);) {
        for (Line line : b.getLines()) {
          pst3.setInt(1, line.getQuantity());
          pst3.setFloat(2, line.getSubtotal());
          pst3.setLong(3, billId);
          if (pst3.executeUpdate() == 0) {
            throw new DAOException("Line could not be inserted.");
          }
          Long lineId = null;
          try (PreparedStatement pst4 = conn.prepareStatement(RETRIEVE_ID);) {
            ResultSet rs = pst4.executeQuery();
            rs.next();
            lineId = rs.getLong("id");
          }
          try (PreparedStatement pst5 = conn.prepareStatement(INSERT_BOUQUET);) {
            pst5.setLong(1, lineId);
            if (pst5.executeUpdate() == 0) {
              throw new DAOException("Bouquet could not be assigned to line.");
            }
            Long bouquetId = null;
            try (PreparedStatement pst7 = conn.prepareStatement(RETRIEVE_ID);) {
              ResultSet rs = pst7.executeQuery();
              rs.next();
              bouquetId = rs.getLong("id");
            }
            try (PreparedStatement pst6 = conn.prepareStatement(INSERT_FLOWER_BOUQUET);) {
              
              for (Flower f : line.getBouquet().getFlowers().keySet()) {
                pst6.setLong(1,bouquetId);
                pst6.setLong(2, f.getId());
                pst6.setInt(3, line.getBouquet().getFlowers().get(f));
                if (pst6.executeUpdate() == 0) {
                  throw new DAOException("Flower could not be assigned to bouquet.");
                }
              }
              
            }
            
          }
        }
      }
      conn.commit();
    } catch (SQLException e) {
      if (conn != null) {
        try {
          conn.rollback();
        } catch (SQLException excep) {
          throw new DAOException("SQL Error", e);
        }
      }
    }

  }

  @Override
  public void update(Bill b) throws DAOException {
    try (PreparedStatement pst = conn.prepareStatement(UPDATE);) {

      pst.setDate(1, new Date(b.getDate().getTime()));
      pst.setLong(2, b.getCustomerId());
      pst.setLong(3, b.getId());

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

      while (rs.next()) {
        Bill bill = new Bill(rs.getDate("date"), rs.getLong("customerId"));
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
    try (PreparedStatement pst = conn.prepareStatement(GET); ResultSet rs = pst.executeQuery();) {

      pst.setLong(1, id);

      if (rs.next()) {
        bill = new Bill(rs.getDate("date"), rs.getLong("customerId"));
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
