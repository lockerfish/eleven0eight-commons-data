/**
 * 
 */
package com.eleven0eight.commons.data;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import org.apache.log4j.Logger;


/**
 * @author Hendrix Tavarez
 *
 */
public abstract class RepositoryService <E extends Serializable, D extends GenericDAOImpl<E, Serializable>, R extends Validator<E>> {

  private static final Logger _logger = Logger.getLogger(RepositoryService.class);
    
  private D dao = null;
  
  private R validator = null;

  @SuppressWarnings("unchecked")
  public D getDAO() {
    try {
      _logger.info("instanciating DAO object.");
      this.dao = ((Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]).newInstance();
      _logger.info("DAO object " + this.dao.getClass().getSimpleName() + " created.");
    } catch (InstantiationException ie) {
      _logger.debug("InstantiationException: repository service failed.", ie);
      throw new RuntimeException(ie);
    } catch (IllegalAccessException iae) {
      _logger.debug("IllegalAccessException: repository service failed.", iae);
      throw new RuntimeException(iae);
    }
    return this.dao;
  }
  
  @SuppressWarnings("unchecked")
  public R getValidator() {
    if(this.validator == null) {
      try {
        _logger.info("instanciating Validator object.");
        this.validator = ((Class<R>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2]).newInstance();
        _logger.info("Validator object " + this.validator.getClass().getSimpleName() + " created.");
      } catch (InstantiationException ie) {
        _logger.debug("InstantiationException: repository service failed.", ie);
        throw new RuntimeException(ie);
      } catch (IllegalAccessException iae) {
        _logger.debug("IllegalAccessException: repository service failed.", iae);
        throw new RuntimeException(iae);
      }  
    }
    return this.validator;
  }

  public E store(E obj) throws ValidationException{
    _logger.info("storing object: " + obj);
    doValidation(obj);
    obj = getDAO().save(obj);
    _logger.info("storing object successful"); 
    return obj;

  }

  private void doValidation(E obj) throws ValidationException {
    _logger.info("validating object: " + obj);
    
    getValidator().validate(obj);
    _logger.info("validating successful");
  } 

  public void remove(E obj) {
    _logger.info("removing object: " + obj);
    getDAO().delete(obj);
    _logger.info("removing object successful");
  }

  public Collection<E> getAll() {
    return getDAO().getAll();
  }  

  public E getById(Long id) {
    return getDAO().get(id);
  }  
}
