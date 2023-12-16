package models.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import db.DbException;
import models.entities.Seller;

public class SellerService {
  private Connection connection = null;

  public SellerService(Connection connection) {
    this.connection = connection;
  }

  public Stream<Seller> getList(Integer limit) {
    try (Statement stt = connection.createStatement()) {
      var list = new ArrayList<Seller>();

      ResultSet result = stt.executeQuery("SELECT * FROM seller limit " + limit + ";");

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
    }
  }
}
