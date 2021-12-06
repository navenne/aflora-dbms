package flowershop.dao;

import java.util.List;
import flowershop.Bouquet;

public interface BouquetDAO extends DAO<Bouquet, Long> {
  void insert(Bouquet b) throws DAOException;
  void update(Bouquet b) throws DAOException;
  void delete(Long id) throws DAOException;
  List<Bouquet> getAll() throws DAOException;
  Bouquet get(Long id) throws DAOException;
}
