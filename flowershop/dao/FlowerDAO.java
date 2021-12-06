package flowershop.dao;

import java.util.List;
import flowershop.Flower;

public interface FlowerDAO extends DAO<Flower, Long>{
  void insert(Flower f) throws DAOException;
  void update(Flower f) throws DAOException;
  void delete(Long id) throws DAOException;
  List<Flower> getAll() throws DAOException;
  Flower get(Long id) throws DAOException;
}
