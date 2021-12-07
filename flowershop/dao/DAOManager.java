package flowershop.dao;

public interface DAOManager {
  CustomerDAO getCustomerDAO();
  BillDAO getBillDAO();
  LineDAO getLineDAO();
  BouquetDAO getBouquetDAO();
  FlowerDAO getFlowerDAO();
}
