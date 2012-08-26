/**
 * 
 */
package com.eleven0eight.commons.data;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Hendrix Tavarez
 *
 * The <code>GenericDAO</code> class represents a ....
 *
 */
public interface GenericDAO <T, PK extends Serializable>  {

  /**
   * Persist the newInstance object into database
   * 
   * @param newInstance
   * @return detached copy of persisted object
   */
  T save(T newInstance);

  /**
   * Retrieve an object that was previously persisted to the database using
   * the indicated id as primary key
   * 
   * @param id
   * @return object
   */
  T get(PK id);
  
  /**
   * Remove an object from persistent storage in the database
   * 
   * @param persistentObject
   */
  void delete(T persistentObject);
  
  /**
   * @return all objects from persistent storage in the database
   */
  public Collection<T> getAll();

  
}
