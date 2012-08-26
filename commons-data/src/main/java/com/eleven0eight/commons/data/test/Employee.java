/**
 * 
 */
package com.eleven0eight.commons.data.test;

/**
 * @author Hendrix Tavarez
 *
 */
public class Employee extends Person {


  /**
   * 
   */
  private static final long serialVersionUID = 6418494926970274472L;

  private String department;
  
  private String title;
  
  /**
   * @param firstName
   * @param lastName
   * @param phone
   */
  public Employee(String firstName, String lastName, String phone, String dept, String title) {
    super(firstName, lastName, phone);
    this.department = dept;
    this.title = title;
  }

  /**
   * @return the department
   */
  public String getDepartment() {
    return department;
  }

  /**
   * @param department the department to set
   */
  public void setDepartment(String department) {
    this.department = department;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }
  

}
