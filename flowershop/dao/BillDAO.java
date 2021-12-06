package flowershop.dao;

import java.util.List;
import flowershop.Bill;

public interface BillDAO extends DAO<Bill, Long> {
  void insert(Bill b) throws DAOException;
  void update(Bill b) throws DAOException;
  void delete(Long id) throws DAOException;
  List<Bill> getAll() throws DAOException;
  Bill get(Long id) throws DAOException;
}
