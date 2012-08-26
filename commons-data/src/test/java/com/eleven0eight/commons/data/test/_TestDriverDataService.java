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
public class _TestDriverDataService {
  
  @Test
  public void insertByReachability() {
    
    // note that validation-by-reachability is not currently supported.
    
    DriverService service = new DriverService();
    try {      
      int origCarCount = new CarService().getAll().size();
      Driver driver = new Driver("Tony", "Kanaan", 37);
      driver.addCar(new Car("Dallara", "Honda", 2011, driver));
      service.store(driver);
      int newCarCount = new CarService().getAll().size();
      
      //confirm that the Car was added to the DB
      assertEquals(origCarCount+1, newCarCount);
      
      service.remove(driver);
      
    } catch (ValidationException ve) {
      ve.printStackTrace();
      fail(ve.getLocalizedMessage());
    } catch (RuntimeException re) {
      re.printStackTrace();
      fail(re.getLocalizedMessage());
    }
  }
  
  @Test
  public void updateByReachability() {

    // note that validation-by-reachability is not currently supported.
    
    DriverService service = new DriverService();
    try {
      Driver driver = new Driver("Tony", "Kanaan", 37);
      driver.addCar(new Car("Dallara", "Honda", 2011, driver));
      driver = service.store(driver);
      
      Car car = (Car) driver.getCars().toArray()[0];
      car.setMake("Nissan");
      service.store(driver);
      
      car = (Car) driver.getCars().toArray()[0];
      assertTrue(car.getMake().equals("Nissan"));
      
      service.remove(driver);
      
    } catch (ValidationException ve) {
      ve.printStackTrace();
      fail(ve.getLocalizedMessage());
    } catch (RuntimeException re) {
      re.printStackTrace();
      fail(re.getLocalizedMessage());
    }
  }
  
  @Test
  public void deleteByReachability() {
    
    // note that validation-by-reachability is not currently supported.
    
    DriverService service = new DriverService();
    try {
      Driver driver = new Driver("Tony", "Kanaan", 37);
      driver.addCar(new Car("Dallara", "Honda", 2011, driver));
      driver = service.store(driver);
      
      Long carId = ((Car)driver.getCars().toArray()[0]).getId();
      service.remove(driver);
      
      Car car = new CarService().getById(carId);
      assertNull(car);
      
    } catch (ValidationException ve) {
      ve.printStackTrace();
      fail(ve.getLocalizedMessage());
    } catch (RuntimeException re) {
      re.printStackTrace();
      fail(re.getLocalizedMessage());
    }
  }
  
  
  @Test
  public void insert() {

    DriverService service = new DriverService();
    try {
      Collection<Driver> drivers = service.getAll();
      int size = drivers.size();
      Driver driver = service.store(new Driver("Tony", "Kanaan", 37));
      assertEquals(size + 1, service.getAll().size());
      service.remove(driver);
    } catch (ValidationException ve) {
      fail(ve.getLocalizedMessage());
    } catch (RuntimeException re) {
      fail(re.getLocalizedMessage());
    }
  }

  @Test
  public void delete() {
    DriverService service = new DriverService();
    try {
      service.store(new Driver("Tony", "Kanaan", 37));
      Collection<Driver> drivers = service.getAll();
      for (Driver driver : drivers) {
        service.remove(driver);
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
    DriverService service = new DriverService();
    try {
      Driver driver = service.store(new Driver("Tony", "Kanaan", 37));
      driver.setAge(40);
      driver = service.store(driver);
      assertEquals(40, driver.getAge());
      service.remove(driver);
    } catch (ValidationException ve) {
      fail(ve.getLocalizedMessage());
    } catch (RuntimeException re) {
      fail(re.getLocalizedMessage());
    }
  }

  @Test
  public void select() {

    DriverService service = new DriverService();
    try {
      Driver driver = new Driver("Tony", "Kanaan", 37);
      assertNull(driver.getId());
      driver = service.store(driver);
      assertNotNull(driver.getId());
      service.remove(driver);
    } catch (ValidationException ve) {
      fail(ve.getLocalizedMessage());
    } catch (RuntimeException re) {
      fail(re.getLocalizedMessage());
    }

  }
}
