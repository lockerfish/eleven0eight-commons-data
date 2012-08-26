/**
 * 
 */
package com.eleven0eight.commons.data;

/**
 * @author Hendrix Tavarez
 *
 */
public class ValidationException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -7527149149324938300L;
  
  private String message;
  
  private String[] errors;
  
  @SuppressWarnings("unused")
  private ValidationException() {}
  
  public ValidationException(String message, String[] errors) {
    this.message = message;
    this.errors = errors;
  }
    
  public ValidationException(String message, String error) {
    this.message = message;
    this.errors = new String[]{ error };
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return the errors
   */
  public String[] getErrors() {
    return errors;
  }

}
