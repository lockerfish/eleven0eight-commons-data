/**
 * 
 */
package com.eleven0eight.commons.data;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;


/**
 * @author Hendrix Tavarez
 *
 */
public abstract class Validator<T extends Serializable> {
  
  private static final Logger _logger = Logger.getLogger(Validator.class); 
  
  public static final String FAIL_MESSAGE = "VALIDATION_FAILED";

  private ValidationResult result = new ValidationResult();
  
  private void addMethods( ArrayList<Method> theMethods, Method[] methods) {
    
    for (Method method : methods) {
      if(method.getName().startsWith("get") ) {
        theMethods.add(method);
      }
    }
  }
  
  public void validate(T obj) throws ValidationException {
 
    HashMap<String, ComponentField> fields = new HashMap<String, ComponentField>();
    
    ArrayList<Method> methods = new ArrayList<Method>();
    
    PersistenceHelper.getComponentFields(fields, obj.getClass().getName());
    
    if(obj.getClass().getSuperclass() != null && !obj.getClass().getSuperclass().getSimpleName().equals("Object")) {
      _logger.info("getting super class fields");
      PersistenceHelper.getComponentFields(fields, obj.getClass().getSuperclass().getName());
      addMethods(methods, obj.getClass().getSuperclass().getDeclaredMethods());
      _logger.info("done getting super class fields");
    }

    try {
      String objName = obj.getClass().getSimpleName();
      _logger.info("running validation rules on " + objName);
      addMethods(methods, obj.getClass().getDeclaredMethods());
      for (Method method : methods) {    
        if(isGetter(method)) {

          String key = method.getName().substring(3).toLowerCase();
          if(fields.containsKey(key)) {
            
            ComponentField field = fields.get(key);            
            _logger.debug("name=" + method.getName().substring(3) + " jdbc-type=" + field.getJdbcType() + " length=" + field.getLength() + " allows-null=" + field.getAllowsNull());
            Class<?> type = method.getReturnType();
            
            _logger.debug("checking nulls");
            checkNull(objName, key, field.getAllowsNull(), method.invoke(obj));
            
            if(field.getJdbcType() != null 
                && (field.getJdbcType().matches("(?i).*char.*") || field.getJdbcType().equalsIgnoreCase("text"))
                && !type.isEnum() && type.getSimpleName().equals("String")) {
              
              String value = (String) method.invoke(obj);
              _logger.debug("checking char types length. value=" + value);
              checkCharTypesLength(objName, key, field.getLength(), value);
            }
          }
        }
      }     
    } catch (Exception e) {
      _logger.debug("running validation rules failed.", e);
      throw new RuntimeException(e);
    }
    // check if we found any errors, and throw them back as a checked exception.
    if(!result.isValid()) {
      _logger.debug("running validation rules failed.");
      throw new ValidationException(FAIL_MESSAGE, result.getErrors());
    }     
    _logger.info("validation rules successful");
  }

  /**
   * Validate fields for null.
   * 
   * @param objName
   * @param fieldName
   * @param allowsNull
   * @param value
   */
  private void checkNull(String objName, String fieldName, Boolean allowsNull, Object value) {
    
    if (allowsNull != null) {
      if(!allowsNull && value == null) {
        result.addError(objName.toUpperCase() + "_" + fieldName.toUpperCase() + "_" + "ISNULL");
        _logger.debug(objName.toUpperCase() + "_" + fieldName.toUpperCase() + "_" + "ISNULL");
      }
    }
    
  }  
  
  /**
   * Validate char type fields for char length
   * 
   * @param objName
   * @param fieldName
   * @param length
   * @param value
   */
  private void checkCharTypesLength(String objName, String fieldName, Integer length, String value) {
    
    if (length != null) {
      if( value != null && value.length() > length) {
        result.addError(objName.toUpperCase() + "_" + fieldName.toUpperCase() + "_" + "TOOLONG");
        _logger.debug(objName.toUpperCase() + "_" + fieldName.toUpperCase() + "_" + "TOOLONG");
      }
    }
  }

  public ValidationResult getValidationResult() {
    return result;
  }

  private boolean isGetter(Method method){
    if(!method.getName().startsWith("get"))     
      return false;
    if(method.getParameterTypes().length != 0)   
      return false;  
    if(void.class.equals(method.getReturnType() ))
      return false;
    return true;
  }
}
