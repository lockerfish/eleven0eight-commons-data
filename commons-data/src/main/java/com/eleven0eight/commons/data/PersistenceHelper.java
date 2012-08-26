/**
 * 
 */
package com.eleven0eight.commons.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.metadata.ColumnMetadata;
import javax.jdo.metadata.MemberMetadata;
import javax.jdo.metadata.TypeMetadata;

import org.apache.log4j.Logger;

/**
 * @author Hendrix Tavarez
 *
 * The <code>PersistenceHelper</code> class represents a ....
 *
 */
public class PersistenceHelper {
  
  private static final Logger _logger = Logger.getLogger(PersistenceHelper.class);

  /**
   * This is the actual properties values passed-on to JDO PersistenceManagerFactory.
   * 
   * The values for this properties are a mix from hardcoded values defined in the 
   * getDatanucleusProperties() method and the values in the inventory properties
   * 
   */
  private static Properties properties = null;

  private static String PROPERTY_FILENAME = "datanucleus.properties";

  /**
   * This is the inventory properties file.
   * 
   * It is used to load the actual properties file and select ONLY the JDO connections 
   * arguments, if they are not defined in the Context (Websphere) Namespace.
   */
  private static Properties propertiesFile = new Properties();

  static {
    try {
      _logger.info("propertiesFile.load: " + PROPERTY_FILENAME);
      propertiesFile.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTY_FILENAME));  
    } catch (RuntimeException re) {
      _logger.debug("RuntimeException-fail to get persistence manager instance", re.fillInStackTrace());
      throw new RuntimeException(re);      
    } catch (Exception ex) {
      _logger.debug("Exception-fail to get persistence manager instance", ex.fillInStackTrace());
      throw new RuntimeException(ex);    
    }
  }

  /**
   * Load and append the datanucleus properties
   * 
   * @return properties
   * @throws Exception
   */
  public static Properties getDatanucleusProperties() {

    if(properties == null) {
      
      properties = new Properties();

      _logger.info("loading property file");
      loadPropertyFile();
      _logger.info("done property file");
    }

    return properties;
  }

  private static void loadPropertyFile() {
    for(Object key: propertiesFile.keySet()) {
      if(!properties.containsKey(key)) {
        properties.put(key, getPropertyValue((String)key).trim());
      }
    }
  }


  private static String getPropertyValue(String key) {

    return propertiesFile.getProperty(key);

  }

  public static void logProperties(Properties prop) {

    for (Object key : prop.keySet()) {
      if( ((String)key).matches("(?i).*password.*")) {
        _logger.debug(key + "=XXXXXXXXXXXXXXXXXXX");
      } else {
        _logger.debug(key + "=" + prop.get(key));
      }
    }    
  }

  @SuppressWarnings("unchecked")
  public static <T> Collection<T> getElementsBy(GenericDAOImpl<T, Serializable> tInstance, String jdoql, Object... params) {

    Collection<T> elements = null;
    try {
      _logger.info("get " + tInstance.getPersistentClass() + " - jdoql: " + jdoql);
      for (Object o : params) {
        _logger.info("params: " + o.toString());
      }
      Query query = tInstance.getPersistenceManager().newQuery(tInstance.getPersistentClass(), jdoql);
      elements = (Collection<T>) query.executeWithArray(params);
      return (Collection<T>) tInstance.getPersistenceManager().detachCopyAll(elements);

    } catch (RuntimeException re) {
      _logger.debug("getElement() failed", re);
      throw re;
    } finally {
      tInstance.getPersistenceManager().close();
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T getElementBy(GenericDAOImpl<T, Serializable> tInstance, String jdoql, Object... params) {

    T element = null;
    try {
      _logger.info("get " + tInstance.getPersistentClass() + " - jdoql: " + jdoql);
      Query query = tInstance.getPersistenceManager().newQuery(tInstance.getPersistentClass(), jdoql);
      query.setUnique(true);
      element = (T) query.executeWithArray(params);
      return (T) tInstance.getPersistenceManager().detachCopy(element);

    } catch (RuntimeException re) {
      _logger.debug("getElement() failed", re);
      throw re;       
    } finally {
      tInstance.getPersistenceManager().close();
    }
  }

  public static <T> HashMap<String, ComponentField> getComponentFields(HashMap<String, ComponentField> fields, String objName) {

    try {
      _logger.debug("getting component fields");
      PersistenceManager pm = AppPersistenceFactory.getInstance().getPM();
      TypeMetadata metadata = pm.getPersistenceManagerFactory().getMetadata(objName);
      _logger.debug("type metadata: " + objName);

      MemberMetadata[] members =  metadata.getMembers();
      for (MemberMetadata member : members) {
        if ( !member.getPrimaryKey() ) {
          ColumnMetadata[] cols = member.getColumns();
          for (ColumnMetadata col : cols) {
            fields.put(col.getName().toLowerCase(), new ComponentField(col.getJDBCType(), col.getLength(), col.getAllowsNull()));
          }
        }
      }
      

    } catch (RuntimeException re) {
      _logger.debug("RuntimeException", re);
      throw re;
    } catch (Exception e) {
      _logger.debug("Exception", e);
      throw new RuntimeException(e);
    }
    _logger.debug("done component fields");
    return fields;

  }

}
