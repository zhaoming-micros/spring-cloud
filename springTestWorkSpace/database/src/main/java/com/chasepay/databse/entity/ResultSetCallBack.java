package com.chasepay.databse.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public interface ResultSetCallBack {

	public Collection setListData(ResultSet rs) throws SQLException,Exception;

	public Collection setListDataWithColumnNumbers(ResultSet rs, int len) throws SQLException,Exception;
}
