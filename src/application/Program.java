package application;

import java.sql.Connection;

import db.DB;
import models.services.DepartmentService;
import models.services.SellerService;

public class Program {
  public static void main(String[] args) throws Exception {
    Connection connection = DB.getConnection();

    SellerService sellerService = new SellerService(connection, "seller");
    DepartmentService departmentService = new DepartmentService(connection, "department");

    sellerService.getList(null, null)
        .forEach(System.out::println);

    departmentService.getList(null, null).forEach(System.out::println);

    DB.closeConnection();
  }
}
