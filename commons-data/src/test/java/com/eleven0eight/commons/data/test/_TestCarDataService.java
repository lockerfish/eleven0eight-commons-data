/**
 * 
 */
package com.eleven0eight.commons.data.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Test;

import com.eleven0eight.commons.data.ValidationException;

/**
 * @author Hendrix Tavarez
 *
 */
public class _TestCarDataService {
  
  @Test
  public void validator() {
    
    CarValidator validator = new CarValidator();
    
    // the good
    Car car = new Car("Maxima", "Nissan", 1997, new Driver("Tony", "Kanaan", 37));
    try {
      validator.validate(car);
    } catch (ValidationException e) {
      e.printStackTrace();
      fail("car is valid, yet the validation rules failed it.");
    } catch (RuntimeException re) {
      re.printStackTrace();
      fail("bloody hell, something is terribly wrong.");
    }
    
    // and the bad
    car.setMake("Hugo");    
    try {
      validator.validate(car);
      assertTrue(false); // should have failed with line above
    } catch (ValidationException e) {
      assertTrue(e.getErrors().length == 1);
      assertTrue(e.getErrors()[0].equalsIgnoreCase("car_Make_WTF"));
    } catch (RuntimeException re) {
      re.printStackTrace();
      fail("bloody hell, something is terribly wrong.");
    }
  }
  
  @Test
  public void insert() {
    
    CarService service = new CarService();
    try {
      Collection<Car> cars = service.getAll();
      int size = cars.size();
      Car car = service.store(new Car("Nissan", "Maxima", 2012, new Driver("Tony", "Kanaan", 37)));
      assertEquals(size + 1, service.getAll().size());
      service.remove(car);
    } catch (ValidationException ve) {
      fail(ve.getLocalizedMessage());
    } catch (RuntimeException re) {
      fail(re.getLocalizedMessage());
    }
  }
  
  @Test
  public void delete() {
    CarService service = new CarService();
    try {      
      service.store(new Car("Nissan", "Maxima", 2012, new Driver("Tony", "Kanaan", 37)));
      Collection<Car> cars = service.getAll();
      for (Car car : cars) {
        service.remove(car);
      }
      assertEquals(0, service.getAll().size());
    } catch (ValidationException ve) {
      fail(ve.getLocalizedMessage());
    } catch (RuntimeException re) {
      fail(re.getLocalizedMessage());
    }
  }
  
  @Test
  public void update() {
    CarService service = new CarService();
    try {      
      Car car = service.store(new Car("Nissan", "Maxima", 2012, new Driver("Tony", "Kanaan", 37)));
      car.setYear(2000);
      car = service.store(car);
      assertEquals(2000, car.getYear());
      service.remove(car);
    } catch (ValidationException ve) {
      fail(ve.getLocalizedMessage());
    } catch (RuntimeException re) {
      fail(re.getLocalizedMessage());
    }
  }

  @Test
  public void select() {
    
    CarService service = new CarService();
    try {
      Car car = new Car("Nissan", "Maxima", 2012, new Driver("Tony", "Kanaan", 37));
      assertNull(car.getId());
      car = service.store(car);
      assertNotNull(car.getId());
      service.remove(car);
    } catch (ValidationException ve) {
      fail(ve.getLocalizedMessage());
    } catch (RuntimeException re) {
      fail(re.getLocalizedMessage());
    }
    
  }
}
