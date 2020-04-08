package com.chasepay.utilities.entity;

public class Entity_Address extends Entity {

	public static String[] getAddressAttribute() {
		return address_attribute;
	}
	
	private static final String[] address_attribute = {"street line1", "street_line2", "street_line3", "city", "state", "country", "zip code"};

}
