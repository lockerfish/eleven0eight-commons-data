/**
 * 
 */
package com.eleven0eight.commons.data;

/**
 * @author Hendrix Tavarez
 *
 */
public class ComponentField {
  
	private String jdbcType;
	
	private Integer length;
	
	private Boolean allowsNull;
	
	ComponentField(String jdbcType, Integer integer, Boolean allowsNull) {
		this.jdbcType = jdbcType;
		this.length = integer;
		this.allowsNull = allowsNull;
	}
	
	public String getJdbcType() {
		return jdbcType;
	}
	
	public Integer getLength() {
		return length;
	}
	
	public Boolean getAllowsNull() {
		return allowsNull;
	}
}