/**
 * 
 */
package com.eleven0eight.commons.data;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * @author Hendrix Tavarez
 * 
 *         The <code>GenericDAOImpl</code> class represents a ....
 * 
 */
public class GenericDAOImpl<T, PK extends Serializable> implements
    GenericDAO<T, PK> {

  private Class<T>            persistentClass;

  private PersistenceManager  pm;

  private static final Logger _logger = Logger.getLogger(GenericDAOImpl.class);

  /**
   * Default constructor.
   * 
   * @throws NamingException
   * @throws IOException
   */
  @SuppressWarnings("unchecked")
  public GenericDAOImpl() {
    this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];

    try {
      pm = AppPersistenceFactory.getInstance().getPM();
      pm.setMultithreaded(true);
    } catch (RuntimeException re) {
      _logger.debug(
          "RuntimeException-fail to get persistence manager instance",
          re.fillInStackTrace());
      throw new RuntimeException(re);
    } catch (Exception ex) {
      _logger.debug("Exception-fail to get persistence manager instance",
          ex.fillInStackTrace());
      throw new RuntimeException(ex);
    }

  }

  /**
   * @return EntityManager used to persist this object
   */
  protected PersistenceManager getPersistenceManager() {
    return this.pm;
  }

  /**
   * @return class type being persisted
   */
  protected Class<T> getPersistentClass() {
    return this.persistentClass;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.sbg.inventory.dao.common.GenericDao#save(java.lang.Object)
   */
  public T save(T obj) {
    _logger.info("saving new instance: " + obj.getClass());
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      pm.makePersistent(obj);
      tx.commit();
      return (T) pm.detachCopy(obj);
    } catch (RuntimeException re) {
      _logger.debug("persist failed", re);
      throw re;
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
      _logger.info("save successful");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.sbg.inventory.dao.common.GenericDao#delete(java.lang.Object)
   */
  @SuppressWarnings("unused")
  public void delete(T obj) {
    _logger.info("deleting " + obj.getClass());
    if (obj == null)
      return;
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      pm.deletePersistent(obj);
      tx.commit();
    } catch (RuntimeException re) {
      _logger.debug("remove failed", re);
      throw re;
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
      _logger.info("delete successful");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.sbg.inventory.dao.common.GenericDao#read(java.io.Serializable)
   */
  public T get(PK id) {
    _logger.info("getting instance " + persistentClass + " with id: " + id);
    if (id == null)
      return null;
    try {
      T instance = pm.getObjectById(persistentClass, id);
      return pm.detachCopy(instance);
    } catch (JDOObjectNotFoundException onfe) {
      _logger.debug("object not found.", onfe);
      return null; // let client deal with that
    } catch (RuntimeException re) {
      _logger.debug("read failed", re);
      throw re;
    } finally {
      pm.close();
      _logger.info("get successful");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.sbg.inventory.dao.common.GenericDao#findAll()
   */
  @SuppressWarnings("unchecked")
  public Collection<T> getAll() {
    _logger.info("getting all instances " + persistentClass);
    try {
      Collection<T> query_entities = (Collection<T>) pm.newQuery(
          this.persistentClass).execute();
      Collection<T> detachedEntities = pm.detachCopyAll(query_entities);
//      entities.size();
      return detachedEntities;
    } catch (RuntimeException re) {
      _logger.debug("getAll failed", re);
      throw re;
    } finally {
      pm.close();
      _logger.info("getAll successful");
    }
  }

}
