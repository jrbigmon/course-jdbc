package application;

import java.sql.Connection;

import db.DB;

public class Program {
  public static void main(String[] args) throws Exception {
    Connection connection = DB.getConnection();

    DB.closeConnection();
  }
}
