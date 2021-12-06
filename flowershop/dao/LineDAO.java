package flowershop.dao;

import java.util.List;
import flowershop.Line;

public interface LineDAO extends DAO<Line, Long> {
  void insert(Line l) throws DAOException;
  void update(Line l) throws DAOException;
  void delete(Long id) throws DAOException;
  List<Line> getAll() throws DAOException;
  Line get(Long id) throws DAOException;
}
