package com.chasepay.utilities.util;

import java.util.UUID;

public class IncreIdGenerator {
	
	public static String generateId()
	{
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
