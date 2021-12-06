package flowershop.dao;

import java.util.List;
import flowershop.Customer;

public interface CustomerDAO extends DAO<Customer, Long>{
  void insert(Customer c) throws DAOException;
  void update(Customer c) throws DAOException;
  void delete(Long id) throws DAOException;
  List<Customer> getAll() throws DAOException;
  Customer get(Long id) throws DAOException;
}
