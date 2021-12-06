package flowershop.dao;

public interface DAOManager {
  CustomerDAO getClientDAO();
  BillDAO getBillDAO();
  LineDAO getLineDAO();
  BouquetDAO getBouquetDAO();
  FlowerDAO getFlowerDAO();
}
