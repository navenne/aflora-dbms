package flowershop.dao;

import java.util.List;

public interface DAO<T, K> {
  void insert(T a) throws DAOException;
  void update(T a) throws DAOException;
  void delete(K id) throws DAOException;
  List<T> getAll() throws DAOException;
  T get(K id) throws DAOException;
}
