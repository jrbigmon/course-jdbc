package application;

import java.sql.Connection;
import java.util.Comparator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import db.DB;
import models.entities.Department;
import models.entities.Seller;
import models.services.DepartmentService;
import models.services.SellerService;

public class Program {
  public static void main(String[] args) throws Exception {
    Connection connection = DB.getConnection();

    SellerService sellerService = new SellerService(connection, "seller");

    DB.initTransaction(connection, Connection.TRANSACTION_READ_UNCOMMITTED);
    try {
      sellerService.delete(3);
      sellerService.delete(4);
      sellerService.delete(5);

      int num = 2;
      if (num < 2) {
        throw new Error("fake error");
      }

      DB.commitTransaction(connection);
      System.out.println("Successfully");
    } catch (Exception e) {
      DB.rollbackTransaction(connection);
      e.printStackTrace();
    } finally {
      DB.closeConnection();
    }
  }
}
