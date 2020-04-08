package com.chasepay.databse.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StringArrayResultSetCallBack implements ResultSetCallBack {

	private int len = 0;
	
	public StringArrayResultSetCallBack(int len)
	{
		this.len = len;
	}
	
	
	public List<List<String>> setListData(ResultSet rs) throws SQLException,Exception
	{
		return setListDataWithColumnNumbers(rs, len);
	}
	
	public List<List<String>> setListDataWithColumnNumbers(ResultSet rs, int len) throws SQLException, Exception {
		
		List<List<String>> list = new ArrayList<List<String>>();
		while (rs.next())
		{
			List<String> l = new ArrayList<String>();
			for (int i=1; i<=len; i++)
			{
				l.add(rs.getString(i));
			}
			list.add(l);
		}
		
		//if (list.size() == 0)
		//	list = null;

		return list;
	}

}
