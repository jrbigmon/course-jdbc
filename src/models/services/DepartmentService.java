package models.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Stream;

import db.DB;
import db.DbException;
import models.entities.Department;
import models.utils.SQLQuery;

public class DepartmentService {
  private Connection connection;
  private String tableName;

  public DepartmentService(Connection connection, String tableName) {
    this.connection = connection;
    this.tableName = tableName;
  }

  public Stream<Department> getList(String where, Integer limit) {
    Statement stt = null;
    ResultSet result = null;

    try {
      var list = new ArrayList<Department>();

      stt = connection.createStatement();
      result = stt.executeQuery(SQLQuery.getList(tableName, null, where, null, limit));

      while (result.next()) {
        Integer id = result.getInt("Id");
        String name = result.getString("Name");

        var department = new Department(id, name);

        list.add(department);
      }

      return list.stream();
    } catch (SQLException e) {
      throw new DbException(e.getMessage());
    } finally {
      DB.closeStatement(stt);
      DB.closeResultSet(result);
    }
  }
}
