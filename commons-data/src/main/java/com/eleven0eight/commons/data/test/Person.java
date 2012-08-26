/**
 * 
 */
package com.eleven0eight.commons.data.test;

import java.io.Serializable;

/**
 * @author Hendrix Tavarez
 *
 */
public abstract class Person implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 9094232912039202966L;

  private Long id;

  private String firstName;
  
  private String lastName;
  
  private String phone;

  /**
   * @param firstName
   * @param lastName
   * @param phone
   */
  public Person(String firstName, String lastName, String phone) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }


  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @param phone the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }
}
