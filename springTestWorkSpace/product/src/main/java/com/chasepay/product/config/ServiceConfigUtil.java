package com.chasepay.product.config;

import java.util.Map;

import com.chasepay.product.constant.ProductConstants;

public class ServiceConfigUtil {
	
	public static String getDataSourceKey(Map<String,String> map)
	{
		return  ProductConstants.PRODUCT_KEY;
	}

}
