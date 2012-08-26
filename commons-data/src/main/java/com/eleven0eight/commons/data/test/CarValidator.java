/**
 * 
 */
package com.eleven0eight.commons.data.test;

import com.eleven0eight.commons.data.ValidationException;
import com.eleven0eight.commons.data.Validator;

/**
 * @author Hendrix Tavarez
 *
 */
public class CarValidator extends Validator<Car> {
  
  /* (non-Javadoc)
   * @see com.eleven0eight.commons.data.Validator#validate(java.io.Serializable)
   */
  @Override
  public void validate(Car car) throws ValidationException {
    super.validate(car);
    
    // Cars "Oh, really!??" validations.
    if (car.getMake() != null && car.getMake().equalsIgnoreCase("hugo")) {
      throw new ValidationException(FAIL_MESSAGE, "CAR_MAKE_WTF");
    }
  }

}
