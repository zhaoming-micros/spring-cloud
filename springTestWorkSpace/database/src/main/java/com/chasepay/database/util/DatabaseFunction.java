package com.chasepay.database.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chasepay.constants.error.AccountingErrorConstant;
import com.chasepay.constants.error.ErrorMessage;
import com.chasepay.database.config.ChasePayDatabaseMetaData;
import com.chasepay.database.config.ChasepayDatabaseConnection;
import com.chasepay.database.constants.DatabaseConstant;
import com.chasepay.databse.entity.DataParameterEntity;
import com.chasepay.databse.entity.ResultSetCallBack;
public class DatabaseFunction {

	private static Logger logger = LogManager.getLogger(DatabaseFunction.class);

    private ErrorMessage error = new ErrorMessage(AccountingErrorConstant.ERROR_TYPE_DATABASE);
	
	public ErrorMessage getError()
	{
		return error;
	}
	
	
	
	
	
	public boolean createTable(String datasourceKey,String tableName, String newTableName)
	{
		logger.info("start to create table.datasourceKey = "+datasourceKey+",newTableName = "+newTableName); 
		if (ChasePayDatabaseMetaData.getInstsance().tableExists(datasourceKey,newTableName))
		{
			logger.info(newTableName+"is existed!"); 
			return true;
		}

		Connection conn = null;
		Statement stmt = null;
		try 
		{
			conn  = ChasepayDatabaseConnection.getConnection(datasourceKey);
			if(null==conn)
			{
				logger.error("Could not get database connection");
				error.setCode(AccountingErrorConstant.CODE_1005);
				return false;
			}
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			int result = stmt.executeUpdate("create table "+newTableName +" like "+tableName);
			logger.info("execute result size = "+result);
			ChasePayDatabaseMetaData.getInstsance().addTable(datasourceKey,newTableName);
			conn.commit();
			return false;
		} 
		catch (SQLException e) {

			if (e.getErrorCode() == DatabaseConstant.MYSQL_TABLE_EXIST)
			{
				logger.info("Table already exist,tableName is "+newTableName);
				return true;
			}
			error.setCode(AccountingErrorConstant.CODE_1007);
			logger.error("create table fail:" + e.getMessage(),e);
			return false;
		} 
		catch (Exception e) {

			error.setCode(AccountingErrorConstant.CODE_1007);
			logger.error("create table fail:" + e.getMessage(),e);

			return false;
		} 
		finally
		{
			if (stmt != null)	try {stmt.close();}catch(Exception e1){}
			if (conn != null)	try {conn.close();}catch(Exception e){}
		}
	}
	
	
	public String getPrimaryKey(String datasourceKey,String tableName)
	{
		return ChasePayDatabaseMetaData.getInstsance().getPrimayKey(datasourceKey,tableName);
	}
	
	private String getKey1RelatedTableName(String datasourceKey,String tableName, String realtionKey1)
	{
		if(null==realtionKey1)
		{
			return tableName;
		}
		boolean notIn = true;
		
		List<String> relationTables = ChasePayDatabaseMetaData.getInstsance().getRelationTables1(datasourceKey);
		
		for (String s:relationTables)
		{
			if (s.equals(tableName))
			{
				notIn = false;
				break;
			}
		}
		
		if (notIn)
			return tableName;
		
		String newName = tableName+"_"+realtionKey1;
		
		boolean result = createTable(datasourceKey,tableName, newName);
		if (!result)
			return null;
		
		return newName;

	}
	
	public String getkey1Key2RelatedTableName(String datasourceKey,String tableName, String key1, String key2)
	{
		if (key2 == null)
			return getKey1RelatedTableName(datasourceKey,tableName, key1);
					
		boolean notIn = true;
		List<String> relationTables = ChasePayDatabaseMetaData.getInstsance().getRelationTables2(datasourceKey);
		for (String s:relationTables)
		{
			if (s.equals(tableName))
			{
				notIn = false;
				break;
			}
		}
		
		if (notIn)
		{
			return getKey1RelatedTableName(datasourceKey,tableName, key1);
		}
		
		String newName = tableName+"_"+key1+"_"+key2;
		
		boolean result = createTable(datasourceKey,tableName, newName);
		if (!result)
			return null;
		
		return newName;

	}
	
	public Map<String, DataParameterEntity> getTableMetadataMap(String datasourceKey,String tableName)
	{
		return ChasePayDatabaseMetaData.getInstsance().getTableMetadataMap(datasourceKey,tableName);
	}
	
	
	
	public List<DataParameterEntity> getTableMetadata(String datasourceKey,String tableName)
	{
		return ChasePayDatabaseMetaData.getInstsance().getMetaData(datasourceKey,tableName);
	}
	

	
	public String insertDataReturnId(Connection conn, String sql,List<DataParameterEntity> params) 
	{
		if(DatabaseConstant.ENABLE_SQL)
		{
			logger.info("sql is : "+sql);
		}
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			//conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			if(null!=params)
			{
				setPreparedStatementParam(params, stmt);
			}
			int result = stmt.executeUpdate();
			if (result > 0)
			{
				rs = stmt.executeQuery(DatabaseConstant.INSERT_GET_ID_SQL);
				if (rs.next())
				{
					long id = rs.getLong(1);
					//conn.commit();
					return String.valueOf(id);
				}
			}
			
            
			error.setCode(AccountingErrorConstant.CODE_1012);
			return null;
			
		} catch (SQLException e) {

			if (e!=null && DatabaseConstant.MYSQL_DUPLICATE == e.getErrorCode())
			{
				error.setCode(AccountingErrorConstant.CODE_1008);
				logger.error("insert duplicate 1");
			}
			else
			{
				error.setCode(AccountingErrorConstant.CODE_1007);
				logger.error("update fail:" + e.getMessage(),e);
			}
			return null;
		} catch (Exception e) {

			error.setCode(AccountingErrorConstant.CODE_1007);
			logger.error("update fail:" + e.getMessage(),e);

			return null;
		} 
		finally
		{
			if (rs != null)	try {rs.close();}catch(Exception e){}
			if (stmt != null)	try {stmt.close();}catch(Exception e){}
		}
	}
	
	
	
	public boolean updateData(Connection conn, String sql,List<DataParameterEntity> params) {
		if(DatabaseConstant.ENABLE_SQL)
		{
			logger.info("sql is : "+sql);
		}
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			//conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			if(null!=params)
			{
				setPreparedStatementParam(params, stmt);
			}
			int count = stmt.executeUpdate();
			logger.info("execute result count = "+count);
			return true;
			
		} catch (SQLException e) {

			if (e!=null && DatabaseConstant.MYSQL_DUPLICATE == e.getErrorCode())
			{
				error.setCode(AccountingErrorConstant.CODE_1008);
				logger.error("insert duplicate 1");
			}
			else
			{
				error.setCode(AccountingErrorConstant.CODE_1007);
				logger.error("insert fail:" + e.getMessage(),e);
			}
			return false;
		} catch (Exception e) {

			error.setCode(AccountingErrorConstant.CODE_1007);
			logger.error("insert fail:" + e.getMessage(),e);

			return false;
		} 
		finally
		{
			if (rs != null)	try {rs.close();}catch(Exception e){}
			if (stmt != null)	try {stmt.close();}catch(Exception e){}
		}
	}
	
	
	
	public boolean updateBulkData(Connection conn, List<String> sqls)
	{
		Statement stmt = null;
		try 
		{
			//conn.setAutoCommit(false);
			
			stmt = conn.createStatement();
			
			logger.info("update Batch :");
			
			for (String sql:sqls)
			{
				stmt.addBatch(sql);
				if (DatabaseConstant.ENABLE_SQL)
				{
					logger.info("sql is :"+sql);
				}
			}	
			
			int[] count = stmt.executeBatch();
			logger.info("succeed to update bulk :"+count);
			return true;
		}
		catch(Exception e)
		{
			error.setCode(AccountingErrorConstant.CODE_1007);
			logger.info("fail to insertBulkDataNoCommit");
			logger.info(e.getMessage(),e);
			return false;
		}
		finally
		{
			if (stmt != null)	try {stmt.close();}catch(Exception e){}
		}
		
	}
	
	
	
	
	public boolean updateSameBulkData(Connection conn, String sql ,List<List<DataParameterEntity>> paramsList) 
	{
		PreparedStatement stmt = null;
		try 
		{
			//conn.setAutoCommit(false);
			
			if (DatabaseConstant.ENABLE_SQL)
			{
				logger.info("sql is :"+sql);
			}
				

			stmt = conn.prepareStatement(sql);
			
			for (int i = 0; i < paramsList.size(); i++) 
			{
				List<DataParameterEntity> params = paramsList.get(i);
				setPreparedStatementParam(params, stmt);
				stmt.addBatch();
			}
			int[] count = stmt.executeBatch();
			logger.info("succeed to update:"+count);
			return true;
			
		} catch(Exception e)
		{
			error.setCode(AccountingErrorConstant.CODE_1007);
			logger.info("fail to insertBulkDataNoCommit");
			logger.info(e.getMessage(),e);
			return false;
		}
		finally
		{
			if (stmt != null)	try {stmt.close();}catch(Exception e){}
		}
	}
	

	public String getOneResult(Connection conn,String sql,List<DataParameterEntity> params) {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			
			if (DatabaseConstant.ENABLE_SQL)
			{
				logger.info("sql is :"+sql);
			}
				
			stmt = conn.prepareStatement(sql);
			if(null!=params)
			{
				setPreparedStatementParam(params, stmt);
			}

			rs = stmt.executeQuery();
			if (rs.next())
				return rs.getString(1);
			
			logger.info("no record found");
			return "";

		} 
		catch (Exception e) 
		{
			error.setCode(AccountingErrorConstant.CODE_1007);
			logger.error("fail to execute getSelectOneData:" + e.getMessage(),e);
			if (rs != null)	try {rs.close();}catch(Exception e1){}
			if (stmt != null)	try {stmt.close();}catch(Exception e1){}
		    return null;
		} 
		finally
		{
			if (rs != null)	try {rs.close();}catch(Exception e){}
			if (stmt != null)	try {stmt.close();}catch(Exception e){}
		}
	}
	
	
	private void setPreparedStatementParam(List<DataParameterEntity> params,PreparedStatement stmt) throws Exception
	{
		for(int i=0;i<params.size();i++)
		{
			DataParameterEntity dataParameterEntity = params.get(i);
			if(dataParameterEntity.getType()==DatabaseConstant.SQL_PARAMETER_INT)
			{
				//param-int
				int value = -1;
				if (dataParameterEntity.getValue() instanceof String)
					value = Integer.parseInt((String)dataParameterEntity.getValue());
				else
					value = (Integer)dataParameterEntity.getValue();
				stmt.setInt(i+1, value);
			}
			else if(dataParameterEntity.getType()==DatabaseConstant.SQL_PARAMETER_STRING)
			{
				//param-String
				String value = (String)dataParameterEntity.getValue();
				stmt.setString(i+1, value);
			}  
			else if(dataParameterEntity.getType()==DatabaseConstant.SQL_PARAMETER_DECIMAL)
			{
				//param-String
				long value = -1;
				if (dataParameterEntity.getValue() instanceof String)
					value = Long.parseLong((String)dataParameterEntity.getValue());
				else
					value = (Long)dataParameterEntity.getValue();
				stmt.setLong(i+1, value);
			}     
		}
	}


	
	public Collection getSelectData(Connection conn,String sql, List<DataParameterEntity> params,
			ResultSetCallBack handler) {
		
		if (DatabaseConstant.ENABLE_SQL)
		{
			logger.info("sql is :"+sql);
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection list = null;
		try 
		{

			stmt = conn.prepareStatement(sql);
			if(null!=params)
			{
				setPreparedStatementParam(params, stmt);
			}			
			rs = stmt.executeQuery();
			list = handler.setListData(rs);
			return list;

		} 
		catch (Exception e) 
		{
			error.setCode(AccountingErrorConstant.CODE_1007);
			logger.error("fail to execute getSelectData:" + e.getMessage(),e);
			if (rs != null)	try {rs.close();}catch(Exception e1){}
			if (stmt != null)	try {stmt.close();}catch(Exception e1){}
			
			return null;
		} 
		finally
		{
			if (rs != null)	try {rs.close();}catch(Exception e){}
			if (stmt != null)	try {stmt.close();}catch(Exception e){}
				
		}

	}
}
