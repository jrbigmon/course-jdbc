package models.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;

import db.DB;
import db.DbException;
import models.entities.Seller;
import models.utils.SQLQuery;

public class SellerService {
  private Connection connection = null;
  private String tableName;

  public SellerService(Connection connection, String tableName) {
    this.connection = connection;
    this.tableName = tableName;
  }

  public Stream<Seller> getList(String where, Integer limit) {
    Statement stt = null;
    ResultSet result = null;

    try {
      var list = new ArrayList<Seller>();

      stt = connection.createStatement();
      result = stt.executeQuery(SQLQuery.getList(tableName, null, where, null, limit));

      while (result.next()) {
        Integer id = result.getInt("Id");
        String name = result.getString("Name");
        String email = result.getString("Email");
        Date birthDate = result.getDate("BirthDate");
        Double baseSalary = result.getDouble("BaseSalary");
        Integer departmentId = result.getInt("DepartmentId");

        var seller = new Seller(id, name, email, birthDate, baseSalary, departmentId);

        list.add(seller);
      }

      return list.stream();
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(stt);
      DB.closeResultSet(null);
    }
  }
}
