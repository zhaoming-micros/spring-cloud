package com.chasepay.databse.entity;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StringMapResultSetCallBack implements ResultSetCallBack {

	private Map<String, Integer> clumnTypeMap;
	
	public StringMapResultSetCallBack(Map<String, Integer> clumnTypeMap)
	{
		this.clumnTypeMap = clumnTypeMap;
	}
	
	
	public List<Map<String, String>> setListData(ResultSet rs) throws SQLException,Exception
	{
		List<Map<String, String>> list =  new ArrayList<Map<String, String>>();
		while (rs.next())
		{
			Map<String, String> dataMap = new HashMap<String, String>();
			Iterator<Entry<String, Integer>> iterator = clumnTypeMap.entrySet().iterator();
			while(iterator.hasNext())
			{
				Entry<String, Integer> next = iterator.next();
				String key = next.getKey();
				int type = next.getValue();
				if (type == java.sql.Types.INTEGER||type == java.sql.Types.BIGINT)
				{
					int value = rs.getInt(key);
					dataMap.put(key, String.valueOf(value));
				}
				else if(type == java.sql.Types.DECIMAL)
				{
					BigDecimal value = rs.getBigDecimal(key);
					if(null==value)
					{
						dataMap.put(key, "");
					}
					else
					{
						dataMap.put(key, value.toString());
					}
				}
				else
				{
					String value = rs.getString(key);
					dataMap.put(key, value);
				}
			}
			
			list.add(dataMap);
		}
		return list;
	}
	

	public List<List<String>> setListDataWithColumnNumbers(ResultSet rs, int len) throws SQLException, Exception {

		return null;
	}

}
