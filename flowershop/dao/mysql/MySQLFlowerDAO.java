package flowershop.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import flowershop.Flower;
import flowershop.dao.DAOException;
import flowershop.dao.FlowerDAO;

public class MySQLFlowerDAO implements FlowerDAO {

  private String INSERT = "INSERT INTO flower (species, color, price, stock) VALUES (?,?,?,?)";
  private String UPDATE = "UPDATE flower SET species = ?, color = ?, price = ?, stock = ? WHERE id = ?";
  private String DELETE = "DELETE FROM flower WHERE id = ?";
  private String GETALL = "SELECT * FROM flower";
  private String GET = "SELECT * FROM flower WHERE id = ?";
  
  private Connection conn;
  
  public MySQLFlowerDAO(Connection conn) {
    this.conn = conn;
  }
  
  @Override
  public void insert(Flower f) throws DAOException {
    try (PreparedStatement pst = conn.prepareStatement(INSERT);) {
      
      pst.setString(1, f.getSpecies());
      pst.setString(2, f.getColor());
      pst.setFloat(3, f.getPrice());
      pst.setInt(4, f.getStock());

      if (pst.executeUpdate() == 0) {
        throw new DAOException("Flower could not be inserted.");
      }
      
      
    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
  }

  @Override
  public void update(Flower f) throws DAOException {
    try (PreparedStatement pst = conn.prepareStatement(UPDATE);) {
      
      pst.setString(1, f.getSpecies());
      pst.setString(2, f.getColor());
      pst.setFloat(3, f.getPrice());
      pst.setInt(4, f.getStock());
      pst.setLong(5, f.getId());

      if (pst.executeUpdate() == 0) {
        throw new DAOException("Flower could not be updated.");
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
          throw new DAOException("Flower could not be deleted.");
        }
        
      } catch (SQLException e) {
        throw new DAOException("SQL Error", e);
      }
    
  }

  @Override
  public List<Flower> getAll() throws DAOException {
    List<Flower> flowers = new ArrayList<Flower>();
    try (PreparedStatement pst = conn.prepareStatement(GETALL);
        ResultSet rs = pst.executeQuery();) {
        
        while(rs.next()) {
          Flower flower = new Flower(rs.getString("species"), rs.getString("color"), rs.getFloat("price"), rs.getInt("stock"));
          flower.setId(rs.getLong("id"));
          flowers.add(flower);
      }

    } catch (SQLException e) {
      throw new DAOException("SQL Error", e);
    }
    
    return flowers;
  }

  @Override
  public Flower get(Long id) throws DAOException {
    Flower flower = null;
    try (PreparedStatement pst = conn.prepareStatement(GET);) {

        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();        
        if (rs.next()) {
          flower = new Flower(rs.getString("species"), rs.getString("color"), rs.getFloat("price"), rs.getInt("stock"));
          flower.setId(rs.getLong("id"));
        } else {
          throw new DAOException("No flower found with id " + id);
        }

    } catch (SQLException e) {
        throw new DAOException("SQL Error", e);
    }
    return flower;
  }

}
