package application;

import java.sql.Connection;

import db.DB;
import models.services.SellerService;

public class Program {
  public static void main(String[] args) throws Exception {
    Connection connection = DB.getConnection();

    SellerService sellerService = new SellerService(connection, "seller");

    sellerService.getList(null, null)
        .forEach(System.out::println);

    DB.closeConnection();
  }
}
