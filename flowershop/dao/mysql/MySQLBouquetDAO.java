package flowershop.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import flowershop.Bouquet;
import flowershop.dao.BouquetDAO;
import flowershop.dao.DAOException;

public class MySQLBouquetDAO implements BouquetDAO {

  private String INSERT = "INSERT INTO bouquet (lineId) VALUES (?)";
  private String INSERT_FLOWER_BOUQUET = "INSERT INTO flowerBouquet (bouquetId, flowerId, quantity) VALUES (?,?,?)";
  private String UPDATE = "UPDATE bouquet SET lineId = ? WHERE id = ?";
  private String UPDATE_FLOWER_BOUQUET = "UPDATE flowerBouquet SET quantity = ? WHERE bouquetId = ? AND flowerId = ?";
  private String DELETE = "DELETE FROM bouquet WHERE id = ?";
  private String DELETE_FLOWER_BOUQUET = "DELETE FROM flowerBouquet WHERE bouquetId = ? AND flowerId = ?";
  private String GETALL = "SELECT * FROM bouquet";
  private String GET = "SELECT * FROM bouquet WHERE id = ?";
  
  private Connection conn;
  
  public MySQLBouquetDAO(Connection conn) {
    this.conn = conn;
  }
  
  @Override
  public void insert(Bouquet b) throws DAOException {
    try (PreparedStatement pst = conn.prepareStatement(INSERT);) {
      
      pst.setLong(1, b.getLineId());

      if (pst.executeUpdate() == 0) {
        throw new DAOException("Bouquet could not be inserted.");
      }
      
      
    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
  }

  @Override
  public void update(Bouquet b) throws DAOException {
    try (PreparedStatement pst = conn.prepareStatement(UPDATE);) {
      
      pst.setLong(1, b.getLineId());
      pst.setLong(2, b.getId());

      if (pst.executeUpdate() == 0) {
        throw new DAOException("Bouquet could not be updated.");
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
        throw new DAOException("Bouquet could not be deleted.");
      }
      
    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
  }

  @Override
  public List<Bouquet> getAll() throws DAOException {
    List<Bouquet> bouquets = new ArrayList<Bouquet>();
    try (PreparedStatement pst = conn.prepareStatement(GETALL);
        ResultSet rs = pst.executeQuery();) {
        
        while(rs.next()) {
          Bouquet bouquet = new Bouquet(rs.getLong("lineId"));
          bouquet.setId(rs.getLong("id"));
          bouquets.add(bouquet);
      }

    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
    
    return bouquets;
  }

  @Override
  public Bouquet get(Long id) throws DAOException {
    Bouquet bouquet = null;
    try (PreparedStatement pst = conn.prepareStatement(GET);
        ResultSet rs = pst.executeQuery();) {
        
        pst.setLong(1, id);
        
        if (rs.next()) {
          bouquet = new Bouquet(rs.getLong("lineId"));
          bouquet.setId(rs.getLong("id"));
        } else {
          throw new DAOException("No bouquet found with id " + id);
        }

    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
    return bouquet;
  }

}
