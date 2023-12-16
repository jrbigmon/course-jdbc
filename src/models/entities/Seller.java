package models.entities;

import java.util.Date;

public class Seller {
  private Integer id;
  private String name;
  private String email;
  private Date birthDate;
  private Double baseSalary;
  private Integer departmentId;

  public Seller(Integer id, String name, String email, Date birthDate, Double baseSalary,
      Integer departmentId) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.birthDate = birthDate;
    this.baseSalary = baseSalary;
    this.departmentId = departmentId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public Double getBaseSalary() {
    return baseSalary;
  }

  public void setBaseSalary(Double baseSalary) {
    this.baseSalary = baseSalary;
  }

  public Integer getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }

  @Override
  public String toString() {
    return "Seller [id=" + id + ", name=" + name + ", email=" + email + ", birthDate=" + birthDate + ", baseSalary="
        + baseSalary + ", departmentId=" + departmentId + "]";
  }
}
