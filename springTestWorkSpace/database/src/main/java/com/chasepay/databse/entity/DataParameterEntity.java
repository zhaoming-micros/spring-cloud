package com.chasepay.databse.entity;

public class DataParameterEntity implements Cloneable{
	
	//0-int,1-String,2-decimal,3-date
	private int type = 1 ;
	private String clumnName;
	private Object value;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getClumnName() {
		return clumnName;
	}
	public void setClumnName(String clumnName) {
		this.clumnName = clumnName;
	}
	
	 public DataParameterEntity clone() {
		 DataParameterEntity newDataParameterEntity = null;
		 try
		 {
			 newDataParameterEntity = (DataParameterEntity) super.clone();
			 newDataParameterEntity.value = null;
			 return newDataParameterEntity;
		 } 
		 catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		 }
	 
	 }

    
}
