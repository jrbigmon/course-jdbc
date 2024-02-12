package models.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
  private String[] columnsSelect = new String[] { "Id", "Name", "Email", "BirthDate", "BaseSalary", "DepartmentId" };
  private String[] columnsInsert = new String[] { "Name", "Email", "BirthDate", "BaseSalary", "DepartmentId" };
  private String[] columnsUpdate = new String[] { "Name", "Email", "BirthDate", "BaseSalary", "DepartmentId" };
  private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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
      result = stt.executeQuery(SQLQuery.getList(tableName, columnsSelect, where, null, limit));

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

      return list.parallelStream();
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(stt);
      DB.closeResultSet(null);
    }
  }

  public Integer create(String name, String email, String birthDate, Double baseSalary, Integer departmentId) {
    PreparedStatement preparedStatement = null;
    ResultSet result = null;

    try {
      preparedStatement = connection.prepareStatement(
          SQLQuery.create(tableName, columnsInsert), Statement.RETURN_GENERATED_KEYS);

      preparedStatement.setString(1, name);
      preparedStatement.setString(2, email);
      preparedStatement.setDate(3, new java.sql.Date(sdf.parse(birthDate).getTime()));
      preparedStatement.setDouble(4, baseSalary);
      preparedStatement.setInt(5, departmentId);

      Integer rowsEffected = preparedStatement.executeUpdate();

      Integer id = null;

      if (rowsEffected > 0) {
        result = preparedStatement.getGeneratedKeys();

        while (result.next()) {
          id = result.getInt(1);
        }
      }

      return id;
    } catch (SQLException | ParseException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preparedStatement);
      DB.closeResultSet(result);
    }
  }

  public Boolean update(Integer id, String name, String email, String birthDate, Double baseSalary,
      Integer departmentId) {
    PreparedStatement preparedStatement = null;

    try {
      preparedStatement = connection.prepareStatement(SQLQuery.update(tableName, columnsUpdate, id));

      preparedStatement.setString(1, name);
      preparedStatement.setString(2, email);
      preparedStatement.setDate(3, new java.sql.Date(sdf.parse(birthDate).getTime()));
      preparedStatement.setDouble(4, baseSalary);
      preparedStatement.setInt(5, departmentId);

      int rowsEffected = preparedStatement.executeUpdate();

      return rowsEffected > 0;
    } catch (Exception e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(preparedStatement);
    }
  }
}
