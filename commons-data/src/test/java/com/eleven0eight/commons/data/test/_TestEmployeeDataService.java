/**
 * 
 */
package com.eleven0eight.commons.data.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Test;

import com.eleven0eight.commons.data.ValidationException;


/**
 * @author Hendrix Tavarez
 *
 */
public class _TestEmployeeDataService {

  @Test
  public void insert() {

    EmployeeService service = new EmployeeService();
    try {
      Collection<Employee> employees = service.getAll();
      int size = employees.size();
      Employee employee = service.store(new Employee("Tony", "Kanaan", "", "dept", "title"));
      assertEquals(size + 1, service.getAll().size());
      service.remove(employee);
    } catch (ValidationException ve) {
      fail(ve.getLocalizedMessage());
    } catch (RuntimeException re) {
      fail(re.getLocalizedMessage());
    }
  }
}
