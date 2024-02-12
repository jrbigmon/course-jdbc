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

    sellerService.update(35, "Tenente test", "tenenttest@mail.com",
        "29/10/1997", 3000.00, 1);

    sellerService.delete(30);

    var sellers = sellerService.getList(null, null).sorted(Comparator.comparing(Seller::getId))
        .collect(Collectors.toList());

    sellers.forEach(System.out::println);
    DB.closeConnection();
  }
}
