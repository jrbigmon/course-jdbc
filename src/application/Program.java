package application;

import java.sql.Connection;

import db.DB;
import models.entities.Department;
import models.services.DepartmentService;
import models.services.SellerService;

public class Program {
  public static void main(String[] args) throws Exception {
    Connection connection = DB.getConnection();

    SellerService sellerService = new SellerService(connection, "seller");
    DepartmentService departmentService = new DepartmentService(connection,
        "department");

    var departments = departmentService.getList(null, null);

    Department computerDepartment = departments.filter(d -> d.getName().equals("Computers")).findFirst().orElse(null);

    Integer sellerIdCreated = null;
    if (computerDepartment != null) {
      sellerIdCreated = sellerService.create("Vagner", "vagner@mail.com",
          "20/10/1997", 2000.00,
          computerDepartment.getId());
    }

    System.out.println("Seller id created: " + sellerIdCreated);

    sellerService.getList(null, null)
        .forEach(System.out::println);

    DB.closeConnection();
  }
}
