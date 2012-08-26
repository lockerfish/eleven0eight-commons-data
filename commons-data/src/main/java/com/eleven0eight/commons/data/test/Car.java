/**
 * 
 */
package com.eleven0eight.commons.data.test;

import java.io.Serializable;

/**
 * @author Hendrix Tavarez
 *
 */
public class Car implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6536555468223602848L;

  private Long id;
  
  private String make;
  
  private String model;
  
  private int year;
  
  private Driver driver;

  /**
   * @param model
   * @param make
   * @param year
   */
  public Car(String model, String make, int year, Driver driver) {
    super();
    this.model = model;
    this.make = make;
    this.year = year;
    this.setDriver(driver);
  }

  /**
   * @return the model
   */
  public String getModel() {
    return model;
  }

  /**
   * @param model the model to set
   */
  public void setModel(String model) {
    this.model = model;
  }

  /**
   * @return the make
   */
  public String getMake() {
    return make;
  }

  /**
   * @param make the make to set
   */
  public void setMake(String make) {
    this.make = make;
  }

  /**
   * @return the year
   */
  public int getYear() {
    return year;
  }

  /**
   * @param year the year to set
   */
  public void setYear(int year) {
    this.year = year;
  }

  /**
   * @param driver the driver to set
   */
  public void setDriver(Driver driver) {
    this.driver = driver;
  }

  /**
   * @return the driver
   */
  public Driver getDriver() {
    return driver;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }
  
  
  
}
