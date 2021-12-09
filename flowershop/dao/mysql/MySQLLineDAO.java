package flowershop.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import flowershop.Line;
import flowershop.dao.DAOException;
import flowershop.dao.LineDAO;

public class MySQLLineDAO implements LineDAO {
  
  private String INSERT = "INSERT INTO line (quantity, subtotal, billId) VALUES (?,?,?)";
  private String UPDATE = "UPDATE line SET quantity = ?, subtotal = ?, billId = ? WHERE id = ?";
  private String DELETE = "DELETE FROM line WHERE id = ?";
  private String GETALL = "SELECT * FROM line";
  private String GET = "SELECT * FROM line WHERE id = ?";
  
  private Connection conn;
  
  public MySQLLineDAO(Connection conn) {
    this.conn = conn;
  }
  
  @Override
  public void insert(Line l) throws DAOException {
    try (PreparedStatement pst = conn.prepareStatement(INSERT);) {
      
      pst.setInt(1, l.getQuantity());
      pst.setFloat(2, l.getSubtotal());
      pst.setLong(3, l.getBillId());

      if (pst.executeUpdate() == 0) {
        throw new DAOException("Line could not be inserted.");
      }
      
      
    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
  }

  @Override
  public void update(Line l) throws DAOException {
    try (PreparedStatement pst = conn.prepareStatement(UPDATE);) {
      
      pst.setInt(1, l.getQuantity());
      pst.setFloat(2, l.getSubtotal());
      pst.setLong(3, l.getBillId());
      pst.setLong(4, l.getId());

      if (pst.executeUpdate() == 0) {
        throw new DAOException("Line could not be updated.");
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
        throw new DAOException("Line could not be deleted.");
      }
      
    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
  }

  @Override
  public List<Line> getAll() throws DAOException {
    List<Line> lines = new ArrayList<Line>();
    try (PreparedStatement pst = conn.prepareStatement(GETALL);
        ResultSet rs = pst.executeQuery();) {
        
        while(rs.next()) {
          Line line = new Line(rs.getInt("quantity"), rs.getFloat("subtotal"));
          line.setId(rs.getLong("id"));
          lines.add(line);
      }

    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
    
    return lines;
  }

  @Override
  public Line get(Long id) throws DAOException {
    Line line = null;
    try (PreparedStatement pst = conn.prepareStatement(GET);
        ResultSet rs = pst.executeQuery();) {
        
        pst.setLong(1, id);
        
        if (rs.next()) {
          line = new Line(rs.getInt("quantity"), rs.getFloat("subtotal"));
          line.setId(rs.getLong("id"));
        } else {
          throw new DAOException("No line found with id " + id);
        }

    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
    return line;
  }

}
