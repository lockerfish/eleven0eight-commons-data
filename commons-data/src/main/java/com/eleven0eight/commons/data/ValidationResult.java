/**
 * 
 */
package com.eleven0eight.commons.data;

import java.util.ArrayList;

/**
 * @author Hendrix Tavarez
 *
 */
public class ValidationResult {

	ArrayList<String> errorCodes = new ArrayList<String>();

	/**
	 * This method adds business rules validation errors.
	 * 
	 * @param errorCode
	 */
	public void addError(String errorCode) {
		errorCodes.add(errorCode);
	}

	/**
	 * @return all validation errors
	 */
	public String[] getErrors() {
		return errorCodes.toArray(new String[errorCodes.size()]);
	}

	/**
	 * @return true if not validation errors found
	 */
	public boolean isValid() {
		return errorCodes.size() == 0;
	}

	/**
	 * clears all validation errors.
	 */
	public void reset() {
		errorCodes.clear();
	}

	@Override
	public String toString() {
		final String LS = String.valueOf((char) 13);
		final StringBuffer sb = new StringBuffer();

		String[] errors = getErrors();
		for (int i = 0; i < errors.length; i++) {
			sb.append(errors[i] + LS);
		}

		return sb.toString();
	}
}
