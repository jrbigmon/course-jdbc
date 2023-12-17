package models.utils;

import java.util.Arrays;

public class SQLQuery {
  private SQLQuery() {
    throw new IllegalStateException("SQLQuery is not initialized");
  }

  private static String replaceBrackets(String value) {
    return value.replace("[", "").replace("]", "");
  }

  private static String getReplacements(Integer columnsLength) {
    String replacements = "(";

    for (int i = 0; i < columnsLength; i++) {
      replacements += "?";
      if (i != columnsLength - 1) {
        replacements += ",";
      }
    }

    return replacements += ")";
  }

  public static String getList(String tableName, String[] columns, String where, String[] joins, Integer limit) {
    if (tableName == null) {
      throw new IllegalArgumentException("tableName is required");
    }

    String columnsString = columns != null ? replaceBrackets(Arrays.toString(columns)) : "*";
    String joinsString = joins != null ? replaceBrackets(Arrays.toString(joins)) : "";
    String whereString = where != null ? where.replace(";", "") : "";
    String limitString = limit != null ? "limit " + limit : "";

    return "SELECT " + columnsString + " FROM " + tableName + " " + joinsString + " " + whereString + " " + limitString;
  }

  public static String create(String tableName, String[] columns) {
    return "INSERT INTO "
        + tableName
        + " "
        + "(" + replaceBrackets(Arrays.toString(columns)) + ")"
        + " "
        + "VALUES "
        + getReplacements(columns.length)
        + ";";
  }
}
