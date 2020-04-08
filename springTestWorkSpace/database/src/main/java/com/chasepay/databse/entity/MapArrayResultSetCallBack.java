package com.chasepay.databse.entity;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapArrayResultSetCallBack implements ResultSetCallBack {

	public MapArrayResultSetCallBack()
	{

	}
	
	public List<Map<String,String>> setListData(ResultSet rs) throws SQLException,Exception
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		while (rs.next())
		{
			Map<String,String> ht = new HashMap<String,String>();
			
			ResultSetMetaData m = rs.getMetaData();
			
			int count = m.getColumnCount();
			//List<String> l = new ArrayList<String>();
			for (int i=1; i<=count; i++)
			{
				String data = rs.getString(i);
				if (data != null)
				{
					//l.add(data);
					ht.put(m.getColumnName(i), data);
				}
			}
			list.add(ht);
		}

		return list;
	}
	
	public List<Map<String,String>> setListDataWithColumnNumbers(ResultSet rs, int len) throws SQLException, Exception {
		
		return setListData(rs);
	}

}
