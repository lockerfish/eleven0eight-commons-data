/**
 * 
 */
package com.eleven0eight.commons.data.test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Hendrix Tavarez
 *
 */
public class Driver extends Person {

  /**
   * 
   */
  private static final long serialVersionUID = 7523309985648920580L;

  
  private int age;
  
  private Set<Car> cars = new HashSet<Car>();
 

  /**
   * @param firstName
   * @param lastName
   * @param age
   */
  public Driver(String firstName, String lastName, int age) {
    super(firstName, lastName, null);
    this.age = age;
  }

  /**
   * @return the age
   */
  public int getAge() {
    return age;
  }

  /**
   * @param age the age to set
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * @param cars the cars to set
   */
  public void setCars(Set<Car> cars) {
    this.cars = cars;
  }

  /**
   * @return the cars
   */
  public Collection<Car> getCars() {
    return cars;
  }
  
  public void addCar(Car car) {
    this.cars.add(car);
  }
  
  public void removeCar(Car car) {
    this.cars.remove(car);
  }
  

}
