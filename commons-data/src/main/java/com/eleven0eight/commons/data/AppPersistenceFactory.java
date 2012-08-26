/**
 * 
 */
package com.eleven0eight.commons.data;

import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.apache.log4j.Logger;

/**
 * @author Hendrix Tavarez
 *
 * The <code>AppPersistenceFactory</code> class represents a ....
 *
 */
public class AppPersistenceFactory {
  
  
  private static final Logger _logger = Logger.getLogger(AppPersistenceFactory.class);
  
  /**
   * Singlenton
   */
  private static AppPersistenceFactory instance;
  
  /**
   * Persistence Manager Factory
   */
  private static PersistenceManagerFactory pmfInstance;
  
  /**
   * Properties used to create this PersistenceManager
   */
  private Properties properties;

  /**
   * Initializes a <code>AppPersistenceFactory</code> and sets the properties and persistenceManagerFactory
   * 
   */
  private AppPersistenceFactory() {
    init();
  }
  
  /**
   * Set properties used to create the persistence manager factory
   */
  private void init() {
        
    
    try {
      _logger.info("init: get datanucleus property");
      properties = PersistenceHelper.getDatanucleusProperties();
      
      _logger.info("init: log property");
      PersistenceHelper.logProperties(properties);
      
      _logger.info("init: get persistence manager factory");    
      pmfInstance = JDOHelper.getPersistenceManagerFactory(properties);
      
    } catch (RuntimeException re) {
      _logger.debug("RuntimeException-fail to get persistence manager instance", re.fillInStackTrace());
      throw new RuntimeException(re);      
    } catch (Exception ex) {
      _logger.debug("Exception-fail to get persistence manager instance", ex.fillInStackTrace());
      throw new RuntimeException(ex);    
    } 

  }
  
  /**
   * @return properties
   */
  public Properties getProperties() {
    return properties;
  }
  
  /**
   * @return the persistence manager
   */
  public synchronized PersistenceManager getPM() {
    return pmfInstance.getPersistenceManager();
  }
  
  /**
   * 
   * @return the App Persistence Factory
   */
  public synchronized static AppPersistenceFactory getInstance() {
      if( instance == null ) {
        _logger.info("creating persistence factory instance");
        try {
          instance = new AppPersistenceFactory();
        } catch (RuntimeException re) {
          _logger.debug("RuntimeException-fail to get persistence manager instance", re.fillInStackTrace());
          throw new RuntimeException(re);      
        } catch (Exception ex) {
          _logger.debug("Exception-fail to get persistence manager instance", ex.fillInStackTrace());
          throw new RuntimeException(ex);    
        } 
        _logger.info("done creating persistence factory instance");
      }
      return instance;
  }

}

