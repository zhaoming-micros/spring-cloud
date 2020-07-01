package com.chasepay.database.util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.chasepay.constants.error.AccountingErrorConstant;
import com.chasepay.constants.webutil.AccountDatabaseConstants;
import com.chasepay.database.config.ChasePayDatabaseMetaData;
import com.chasepay.database.config.ChasepayDatabaseConnection;
import com.chasepay.database.constants.DatabaseConstant;
import com.chasepay.databse.entity.DataParameterEntity;
import com.chasepay.databse.entity.MapArrayResultSetCallBack;

public class ChasepayDatabaseUtil {

	static Logger logger = LogManager.getLogger(ChasepayDatabaseUtil.class);
	

	public String getErrorCode()
	{
		return errorCode;
	}

	
	public String insertDataReturnId(String dataSourceKey,Map<String,String> ht, String key1, String key2, String tableName)
	{
		/*String dataSourceKey = ht.get(DatabaseConstant.DATASOURCE_KEY);
		if(null==dataSourceKey||"".equals(dataSourceKey))
		{
			logger.error("dataSourceKey is empty.dataSourceKey = "+dataSourceKey);
			errorCode = AccountingErrorConstant.CODE_1005;
			return null;
		}*/
		
		Connection conn = null;
		try
		{
			conn = ChasepayDatabaseConnection.getConnection(dataSourceKey);
			if (conn == null)
			{
				errorCode = AccountingErrorConstant.CODE_1005;
			    return null;
			}
			conn.setAutoCommit(false);
			String result = insertDataReturnId(conn,dataSourceKey, ht, key1, key2, tableName);
			if(null==result)
			{
				throw new Exception("insert insertDataReturnId error.dataSourceKey = "+dataSourceKey+",and map = "+ht.toString());
			}
			commit(conn);
			return result;
		} 
		catch (Exception e) 
		{
			logger.error("insertDataReturnID error.");
			logger.error(e.getMessage(),e);
			errorCode = AccountingErrorConstant.CODE_1007;
			return null;
		}
		finally
		{
			close(conn);
		}
	}
	
	public String insertDataReturnId(Connection conn,String dataSourceKey, Map<String,String> ht, String key1, String key2, String tableName)
	{
		List<DataParameterEntity> pps = ChasePayDatabaseMetaData.getInstsance().getMetaData(dataSourceKey, tableName);
		if (pps == null)
		{
			errorCode = AccountingErrorConstant.CODE_1005;
			return null;
		}

		String newTableName = util2.getkey1Key2RelatedTableName(dataSourceKey, tableName, key1, key2);
		
		StringBuilder s1 = new StringBuilder(SQL_LEN);
		StringBuilder s2 = new StringBuilder(SQL_LEN);
		s1.append("insert into ")
		.append(newTableName)
		.append(" (");
		s2.append(" values (");
		
		List<DataParameterEntity> vpps = new ArrayList<DataParameterEntity>();
		
		StringBuilder s = new StringBuilder(SQL_LEN2);
		
		//System.out.println("pps="+pps.size()+" tablename="+tableName);
		int columns = 0;
		for (DataParameterEntity p:pps)
		{
			String columnName = (String)p.getValue();
			if ( ((String)p.getValue()).equalsIgnoreCase(AccountDatabaseConstants.ID))
				continue;
			
			String value = ht.get(columnName);
			if (value==null)
				continue;
			
			columns ++;
			if (columns == 1)
			{
				
			}
			else
			{
				s1.append(",");
				s2.append(",");
			}
			s1.append(columnName);
			s2.append("?");
			
			//DataParameterEntity vpp = new DataParameterEntity();
			DataParameterEntity vpp = p.clone();
			vpp.setValue(value);
			/*if (p.getType() == java.sql.Types.INTEGER)
				vpp.setType(DatabaseConstant.SQL_PARAMETER_INT);
			else if (p.getType() == java.sql.Types.DECIMAL)
				vpp.setType(DatabaseConstant.SQL_PARAMETER_DECIMAL);*/
			
			vpps.add(vpp);
			
			s.append(columnName).append("=").append(value).append("|");
		}
		
		s1.append(") ");
		s2.append(")");

		
		String id = f.insertDataReturnId(conn, s1.toString()+s2.toString(), vpps);
		if(null==id)
		{
			errorCode = f.getError().getCode();
		}
		return id;
	}
	
	
	
	
	
	public boolean insertBulkDataNonReturnID(String dataSourceKey, List<Map<String,String>> listHt, String key1, String key2, String tableName)
	{
		Connection conn = null;
		try
		{
			conn = ChasepayDatabaseConnection.getConnection(dataSourceKey);
			if (conn == null)
			{
				errorCode = AccountingErrorConstant.CODE_1005;
			    return false;
			}
			conn.setAutoCommit(false);
			boolean result = insertBulkDataNonReturnID(conn, dataSourceKey, listHt, key1, key2, tableName);
			if(!result)
			{
				throw new Exception("insert insertBulkDataNonReturnID error.dataSourceKey = "+dataSourceKey);
			}
			commit(conn);
			return result;
		} 
		catch (Exception e) 
		{
			logger.error("insertBulkDataNonReturnID error.");
			logger.error(e.getMessage(),e);
			errorCode = AccountingErrorConstant.CODE_1007;
			rollback(conn);
			return false;
		}
		finally
		{
			close(conn);
		}
	}
	
	
	public boolean insertBulkDataNonReturnID(Connection conn,String dataSourceKey, List<Map<String,String>> listHt, String key1, String key2, String tableName)
	{

		List<DataParameterEntity> pps = ChasePayDatabaseMetaData.getInstsance().getMetaData(dataSourceKey, tableName);
		if (pps == null)
		{
			errorCode = AccountingErrorConstant.CODE_1005;
			return false;
		}

		String newTableName = util2.getkey1Key2RelatedTableName(dataSourceKey, tableName, key1, key2);
		List<String> sqls = new ArrayList<String>();
		for (Map<String,String> ht:listHt)
		{
			StringBuilder s1 = new StringBuilder(SQL_LEN);
			StringBuilder s2 = new StringBuilder(SQL_LEN);
			s1.append("insert into ")
			.append(newTableName)
			.append(" (");
			s2.append(" values (");
			int columns = 0;

			for (DataParameterEntity p:pps)
			{

				String columnName = (String)p.getValue();
				if ( ((String)p.getValue()).equalsIgnoreCase(AccountDatabaseConstants.ID))
					continue;

				String value = ht.get(columnName);
				if (value==null || value.trim().length()==0)
					continue;

				columns ++;
				if (columns == 1)
				{

				}
				else
				{
					s1.append(",");
					s2.append(",");
				}
				s1.append(columnName);

				int type = p.getType();
				if (type == DatabaseConstant.SQL_PARAMETER_INT ||
					type == DatabaseConstant.SQL_PARAMETER_DECIMAL)
				{
					s2.append(value);
				}
				else
					s2.append("'").append(escapeSQL(value)).append("'");

			}

			s1.append(") ");
			s2.append(")");
			sqls.add(s1.toString()+s2.toString());
		}
		boolean result = f.updateBulkData(conn, sqls);
		if(!result)
		{
			errorCode = f.getError().getCode();
		}
		return result;
        
	}
	

	public boolean updateData(String dataSourceKey,Map<String,String> ht, String key1, String key2, String tableName, Map<String,String> keyHt)
	{
		Connection conn = null;
		try
		{
			conn = ChasepayDatabaseConnection.getConnection(dataSourceKey);
			if (conn == null)
			{
				errorCode = AccountingErrorConstant.CODE_1005;
			    return false;
			}
			conn.setAutoCommit(false);
			boolean result = updateData(conn,dataSourceKey, ht, key1, key2, tableName, keyHt);
			if(!result)
			{
				throw new Exception("insert insertBulkDataNonReturnID error.dataSourceKey = "+dataSourceKey);
			}
			commit(conn);
			return result;
		} 
		catch (Exception e) 
		{
			logger.error("updateData error.");
			logger.error(e.getMessage(),e);
			errorCode = AccountingErrorConstant.CODE_1007;
			rollback(conn);
			return false;
		}
		finally
		{
			close(conn);
		}

	}

	public boolean updateData(Connection conn, String dataSourceKey,Map<String,String> ht,String key1, String key2, String tableName, Map<String,String> keyHt)
	{
		List<DataParameterEntity> pps = ChasePayDatabaseMetaData.getInstsance().getMetaData(dataSourceKey, tableName);
		if (pps == null)
		{
			errorCode = AccountingErrorConstant.CODE_1005;
			return false;
		}

		String newTableName = util2.getkey1Key2RelatedTableName(dataSourceKey, tableName, key1, key2);
		
		StringBuilder s1 = new StringBuilder(SQL_LEN);
		StringBuilder s2 = new StringBuilder(SQL_LEN);
		List<DataParameterEntity> vpps = new ArrayList<DataParameterEntity>();
			
			s1.append("update ")
			.append(newTableName)
			.append(" set ");
			
			s2.append(" where ");
			
			int columns1 = 0;
			int columns2 = 0;
			
			for (DataParameterEntity p:pps)
			{

				String columnName = (String)p.getValue();
				String value = ht.get(columnName);
				String updateValue = keyHt.get(columnName);
				
				if (value == null && updateValue == null)
					continue;
				
				int type = p.getType();
				DataParameterEntity vpp = null;
				if (value!=null && value.trim().length()>0)
				{
					columns1 ++;
					if (columns1 > 1)
						s1.append(",");
					
					s1.append(" ").append(columnName)
					.append("= ? ");
					
					vpp = p.clone();
					vpp.setValue(value);
					
					/*vpp = new DataParameterEntity();
					
					if (type == DatabaseConstant.SQL_PARAMETER_INT ||
							type == DatabaseConstant.SQL_PARAMETER_DECIMAL)
					{
						vpp.setValue(value);
						vpp.setType(DatabaseConstant.SQL_PARAMETER_INT);
					}
					else
					{
						vpp.setValue(value);
					}*/
					vpps.add(vpp);
					continue;
				}
				
				if (updateValue!=null && updateValue.trim().length()>0)
				{
					columns2 ++;
					if (columns2 > 1)
						s2.append(" and ");

					boolean addCond = false;
					for (String c:DatabaseConstant.CONDITIONS)
					{
						if (updateValue.trim().startsWith(c.toLowerCase()))
						{
							addCond = true;
							s2.append(columnName).append(updateValue).append(" ");
							break;
						}
					}
					
					if (addCond)
					{
						continue;
					}
					
					s2.append(" ").append(columnName)
					.append("= ? ");
					/*vpp = new DataParameterEntity();

					if (type == DatabaseConstant.SQL_PARAMETER_INT ||
							type == DatabaseConstant.SQL_PARAMETER_DECIMAL)
					{
						vpp.setValue(updateValue);
						vpp.setType(DatabaseConstant.SQL_PARAMETER_INT);
					}
					else
					{
						vpp.setValue(updateValue);
					}*/
					vpp = p.clone();
					vpp.setValue(value);

					vpps.add(vpp);
					continue;
				}

			}
			
			//logger.info(s1.toString()+s2.toString());
			
			boolean result = f.updateData(conn, s1.toString()+s2.toString(), vpps);
			if(!result)
			{
				errorCode = f.getError().getCode();
			}
			
			logger.info("update "+result);
			return result;
		
	}
	
	public boolean updateBulkData(String dataSourceKey,List<Map<String,String>> listHt, List<Map<String,String>> updateKeys, String key1, String key2, String tableName)
	{
		Connection conn = null;
		try
		{
			conn = ChasepayDatabaseConnection.getConnection(dataSourceKey);
			if (conn == null)
			{
				errorCode = AccountingErrorConstant.CODE_1005;
			    return false;
			}
			conn.setAutoCommit(false);
			boolean result = updateBulkData(conn,dataSourceKey, listHt, updateKeys, key1, key2, tableName);
			if(!result)
			{
				throw new Exception("updateBulkData error.dataSourceKey = "+dataSourceKey);
			}
            commit(conn);
			return result;
		} 
		catch (Exception e) 
		{
			logger.error("updateBulkData error.");
			logger.error(e.getMessage(),e);
			errorCode = AccountingErrorConstant.CODE_1007;
			rollback(conn);
			return false;
		}
		finally
		{
			close(conn);
		}

	}
	
	public boolean updateBulkData(Connection conn,String dataSourceKey, List<Map<String,String>> listHt, List<Map<String,String>> updateKeys, String key1, String key2, String tableName)
	{
		List<DataParameterEntity> pps = ChasePayDatabaseMetaData.getInstsance().getMetaData(dataSourceKey, tableName);
		if (pps == null)
		{
			errorCode = AccountingErrorConstant.CODE_1005;
			return false;
		}

		String newTableName = util2.getkey1Key2RelatedTableName(dataSourceKey, tableName, key1, key2);

		int seq = 0;
		
		List<String> sqls = new ArrayList<String>();

		
			for (Map<String,String> ht:listHt)
			{
				Map<String,String> keyHt = updateKeys.get(seq);
				seq ++;
				
				StringBuilder s1 = new StringBuilder(SQL_LEN);
				StringBuilder s2 = new StringBuilder(SQL_LEN);
				s1.append("update ")
				.append(newTableName)
				.append(" set ");
				
				s2.append(" where ");
				int columns1 = 0;
				int columns2 = 0;

				for (DataParameterEntity p:pps)
				{

					String columnName = (String)p.getValue();
					String value = ht.get(columnName);
					String updateValue = keyHt.get(columnName);
					
					if (value == null && updateValue == null)
						continue;
					
					int type = p.getType();
					
					if (value!=null)
					{
						columns1 ++;
						if (columns1 > 1)
							s1.append(",");
						
						s1.append(" ").append(columnName)
						.append("=");
						
						if (type == DatabaseConstant.SQL_PARAMETER_INT ||
								type == DatabaseConstant.SQL_PARAMETER_DECIMAL)
						{
							s1.append(value);
						}
						else
							s1.append("'").append(escapeSQL(value)).append("'");
						
						continue;
					}
					
					if (updateValue!=null && updateValue.trim().length()>0)
					{
						columns2 ++;
						if (columns2 > 1)
							s2.append(" and ");

						boolean addCond = false;
						for (String c:DatabaseConstant.CONDITIONS)
						{
							if (updateValue.trim().startsWith(c.toLowerCase()))
							{
								addCond = true;
								s2.append(columnName).append(escapeSQL(updateValue)).append(" ");
								break;
							}
						}
						
						if (addCond)
						{
							continue;
						}
						
						s2.append(" ").append(columnName)
						.append("=");

						if (type == DatabaseConstant.SQL_PARAMETER_INT ||
								type == DatabaseConstant.SQL_PARAMETER_DECIMAL)
						{
							s2.append(updateValue);
						}
						else
							s2.append("'").append(escapeSQL(updateValue)).append("'");

						continue;
					}

				}
				sqls.add(s1.toString()+s2.toString());
				//logger.info(s1.toString()+s2.toString());
				//stmt.executeUpdate(s1.toString()+s2.toString());
                
			}
			boolean result = f.updateBulkData(conn, sqls);
			if(!result)
			{
				errorCode = f.getError().getCode();
			}
			return result;
	}
	
	public boolean updateSameBulkData(String dataSourceKey, List<Map<String,String>> listHt, List<Map<String,String>> updateKeys, String key1, String key2, String tableName)
	{
		Connection conn = null;
		try
		{
			conn = ChasepayDatabaseConnection.getConnection(dataSourceKey);
			if (conn == null)
			{
				errorCode = AccountingErrorConstant.CODE_1005;
			    return false;
			}
			conn.setAutoCommit(false);
			boolean result = updateSameBulkData(conn,dataSourceKey, listHt, updateKeys, key1, key2, tableName);
			if(!result)
			{
				throw new Exception("updateSameBulkData error.dataSourceKey = "+dataSourceKey);
			}
			commit(conn);
			return result;
		} 
		catch (Exception e) 
		{
			logger.error("updateSameBulkData error.");
			logger.error(e.getMessage(),e);
			errorCode = AccountingErrorConstant.CODE_1007;
			rollback(conn);
			return false;
		}
		finally
		{
			close(conn);
		}

	}
	
	public boolean updateSameBulkData(Connection conn,String dataSourceKey, List<Map<String,String>> listHt, List<Map<String,String>> updateKeys, String key1, String key2, String tableName)
	{
		List<DataParameterEntity> pps = ChasePayDatabaseMetaData.getInstsance().getMetaData(dataSourceKey, tableName);
		if (pps == null)
		{
			errorCode = AccountingErrorConstant.CODE_1005;
			return false;
		}

		String newTableName = util2.getkey1Key2RelatedTableName(dataSourceKey, tableName, key1, key2);

		int seq = 0;
		List<List<DataParameterEntity>> vppsList = new ArrayList<List<DataParameterEntity>>();
		StringBuilder s1 = new StringBuilder(SQL_LEN);
		StringBuilder s2 = new StringBuilder(SQL_LEN);
			for (Map<String,String> ht:listHt)
			{
				List<DataParameterEntity> vpps = new ArrayList<DataParameterEntity>();
				Map<String,String> keyHt = updateKeys.get(seq);
				seq ++;
				
				
				s1.append("update ")
				.append(newTableName)
				.append(" set ");
				
				s2.append(" where ");
				int columns1 = 0;
				int columns2 = 0;
				DataParameterEntity vpp = null;
				for (DataParameterEntity p:pps)
				{

					String columnName = (String)p.getValue();
					String value = ht.get(columnName);
					String updateValue = keyHt.get(columnName);
					
					if (value == null && updateValue == null)
						continue;
					
					int type = p.getType();
					
					if (value!=null)
					{
						vpp = new DataParameterEntity();
						columns1 ++;
						if (columns1 > 1)
							s1.append(",");
						
						s1.append(" ").append(columnName)
						.append("= ? ");
						
						/*if (type == DatabaseConstant.SQL_PARAMETER_INT ||
								type == DatabaseConstant.SQL_PARAMETER_DECIMAL)
						{
							//s1.append(value);
							vpp.setValue(value);
							vpp.setType(DatabaseConstant.SQL_PARAMETER_INT);
						}
						else
						{
							vpp.setValue(value);
						}*/
						vpp = p.clone();
						vpp.setValue(value);
						vpps.add(vpp);
						continue;
					}
					
					if (updateValue!=null && updateValue.trim().length()>0)
					{
						columns2 ++;
						if (columns2 > 1)
							s2.append(" and ");

						boolean addCond = false;
						for (String c:DatabaseConstant.CONDITIONS)
						{
							if (updateValue.trim().startsWith(c.toLowerCase()))
							{
								addCond = true;
								s2.append(columnName).append(escapeSQL(updateValue)).append(" ");
								break;
							}
						}
						
						if (addCond)
						{
							continue;
						}
						
						s2.append(" ").append(columnName)
						.append("= ? ");

						/*if (type == DatabaseConstant.SQL_PARAMETER_INT ||
								type == DatabaseConstant.SQL_PARAMETER_DECIMAL)
						{
							//s2.append(updateValue);
							vpp.setValue(updateValue);
							vpp.setType(DatabaseConstant.SQL_PARAMETER_INT);
						}
						else
						{
							vpp.setValue(updateValue);
						}*/
						vpp = p.clone();
						vpp.setValue(value);
						vpps.add(vpp);
						continue;
					}

				}
				vppsList.add(vpps);
				//sqls.add(s1.toString()+s2.toString());
				//logger.info(s1.toString()+s2.toString());
				//stmt.executeUpdate(s1.toString()+s2.toString());
                
			}
			boolean result = f.updateSameBulkData(conn, s1.toString()+s2.toString(), vppsList);
			if(!result)
			{
				errorCode = f.getError().getCode();
			}
			return result;


	}
	
	public boolean deleteData(String dataSourceKey,Map<String,String> keyHt, String key1, String key2, String tableName)
	{
		Connection conn = null;
		try
		{
			conn = ChasepayDatabaseConnection.getConnection(dataSourceKey);
			if (conn == null)
			{
				errorCode = AccountingErrorConstant.CODE_1005;
			    return false;
			}
			conn.setAutoCommit(false);
			boolean result = deleteData(conn, dataSourceKey, keyHt, key1, key2, tableName);
			if(!result)
			{
				throw new Exception("deleteData error.dataSourceKey = "+dataSourceKey);
			}
			commit(conn);
			return result;
		} 
		catch (Exception e) 
		{
			logger.error("updateSameBulkData error.");
			logger.error(e.getMessage(),e);
			errorCode = AccountingErrorConstant.CODE_1007;
			rollback(conn);
			return false;
		}
		finally
		{
			close(conn);
		}
	}
	
	
	public boolean truncateTable(String dataSourceKey, String key1, String key2, String tableName)
	{
		Connection conn = null;
		try
		{
			conn = ChasepayDatabaseConnection.getConnection(dataSourceKey);
			if (conn == null)
			{
				errorCode = AccountingErrorConstant.CODE_1005;
			    return false;
			}
			conn.setAutoCommit(false);
			boolean result = truncateTable(conn, dataSourceKey, key1, key2, tableName);
			if(!result)
			{
				throw new Exception("truncateTable error.dataSourceKey = "+dataSourceKey);
			}
			commit(conn);
			return result;
		} 
		catch (Exception e) 
		{
			logger.error("updateSameBulkData error.");
			logger.error(e.getMessage(),e);
			errorCode = AccountingErrorConstant.CODE_1007;
			rollback(conn);
			return false;
		}
		finally
		{
			close(conn);
		}
	}
	
	public boolean truncateTable(Connection conn,String dataSourceKey, String key1, String key2, String tableName)
	{
		Map<String, DataParameterEntity> tableMetadataMap = ChasePayDatabaseMetaData.getInstsance().getTableMetadataMap(dataSourceKey, tableName);
		if (tableMetadataMap == null)
		{
			errorCode = AccountingErrorConstant.CODE_1014;
			return false;
		}
		String newTableName = util2.getkey1Key2RelatedTableName(dataSourceKey, tableName, key1, key2);	
		DataParameterEntity dataParameterEntity = new DataParameterEntity();
		dataParameterEntity.setValue(newTableName);

		return f.updateData(conn, "truncate table ? ", Arrays.asList(dataParameterEntity));
	}
	
	public boolean deleteData(Connection conn,String dataSourceKey, Map<String,String> keyHt, String key1, String key2, String tableName)
	{
		Map<String, DataParameterEntity> tableMetadataMap = ChasePayDatabaseMetaData.getInstsance().getTableMetadataMap(dataSourceKey, tableName);
		if (tableMetadataMap == null)
		{
			errorCode = AccountingErrorConstant.CODE_1014;
			return false;
		}

		String newTableName = util2.getkey1Key2RelatedTableName(dataSourceKey, tableName, key1, key2);	

		Iterator<Entry<String, String>> keyMapIterator = keyHt.entrySet().iterator();
		StringBuilder s1 = new StringBuilder(SQL_LEN);
		
		List<DataParameterEntity> vpps = new ArrayList<DataParameterEntity>();
			
			s1.append("delete from ")
			.append(newTableName)
			.append(" where ");
			DataParameterEntity vpp = null;
			while(keyMapIterator.hasNext())
			{
				Entry<String, String> next = keyMapIterator.next();
				String key = next.getKey();
				String value = next.getValue();
				DataParameterEntity dataParameterEntity = tableMetadataMap.get(key);
				if(null==dataParameterEntity)
				{
					continue;
				}
				s1.append(key);
				s1.append("= ? ");
				
				
				/*vpp = new DataParameterEntity();
				
				if (dataParameterEntity.getType() == java.sql.Types.INTEGER)
				{
					vpp.setValue(value);
					vpp.setType(DatabaseConstant.SQL_PARAMETER_INT);
				}
				else if(dataParameterEntity.getType() == java.sql.Types.DECIMAL)
				{
					vpp.setValue(value);
				}
				else
				{
					vpp.setValue(value);
				}*/
				vpp = dataParameterEntity.clone();
				vpp.setValue(value);
				
				s1.append(" and ");
				
				vpps.add(vpp);
			}
			
			s1.delete(s1.length()-5, s1.length());
			
			//logger.info(s1.toString());
			boolean result = f.updateData(conn, s1.toString(), vpps);
			
			logger.info("delete "+result);
			return result;
			
		
		
	}
	
	public boolean deleteBulkData(String dataSourceKey,List<Map<String,String>> listHt, String key1, String key2, String tableName)
	{
		Connection conn = null;
		try
		{
			conn = ChasepayDatabaseConnection.getConnection(dataSourceKey);
			if (conn == null)
			{
				errorCode = AccountingErrorConstant.CODE_1005;
			    return false;
			}
			conn.setAutoCommit(false);
			boolean result = deleteBulkData(conn, dataSourceKey, listHt, key1, key2, tableName);
			if(!result)
			{
				throw new Exception("deleteBulkData error.dataSourceKey = "+dataSourceKey);
			}
			commit(conn);
			return result;
		} 
		catch (Exception e) 
		{
			logger.error("deleteBulkData error.");
			logger.error(e.getMessage(),e);
			errorCode = AccountingErrorConstant.CODE_1007;
			rollback(conn);
			return false;
		}
		finally
		{
			close(conn);
		}
	}
	
	
	public boolean deleteBulkData(Connection conn,String dataSourceKey, List<Map<String,String>> listHt, String key1, String key2, String tableName)
	{
		Map<String, DataParameterEntity> tableMetadataMap = ChasePayDatabaseMetaData.getInstsance().getTableMetadataMap(dataSourceKey, tableName);
		if (tableMetadataMap == null)
		{
			errorCode = AccountingErrorConstant.CODE_1014;
			return false;
		}
		String newTableName = util2.getkey1Key2RelatedTableName(dataSourceKey, tableName, key1, key2);	

			List<String> sqls = new ArrayList<String>();
			for(Map<String, String> map : listHt)
			{
				Iterator<Entry<String, String>> keyMapIterator = map.entrySet().iterator();
		
				StringBuilder s1 = new StringBuilder(SQL_LEN);
				
				s1.append("delete from ")
				.append(newTableName)
				.append(" where ");
				while(keyMapIterator.hasNext())
				{
					Entry<String, String> next = keyMapIterator.next();
					String key = next.getKey();
					String value = next.getValue();
					DataParameterEntity dataParameterEntity = tableMetadataMap.get(key);
					if(null==dataParameterEntity)
					{
						continue;
					}
					s1.append(key);
					s1.append("=");
					
					if (dataParameterEntity.getType() == java.sql.Types.INTEGER)
						s1.append(value);
					else if(dataParameterEntity.getType() == java.sql.Types.DECIMAL)
						s1.append(value);
					else
						s1.append("'").append(value).append("'");
					
					s1.append(" and ");

				}
				
				s1.delete(s1.length()-5, s1.length());
				
				//logger.info(s1.toString());
				sqls.add(s1.toString());
			}
			return f.updateBulkData(conn, sqls);

	}
	
	public boolean dataExist(String dataSourceKey,Map<String,String> ht, String companyId, String year, String tableName)
	{
		
		return false;
	}
	
	public List<Map<String,String>> selectAllData(String dataSourceKey,String key1, String key2, String tableName, String orderBy)
	{
		Connection conn = null;
		try 
		{
			conn = ChasepayDatabaseConnection.getConnection(dataSourceKey);

			if (conn == null)
			{
				errorCode = AccountingErrorConstant.CODE_1015;
				return null;
			}

			List<Map<String, String>> selectAllData = selectAllData(conn, dataSourceKey, key1, key2, tableName, orderBy);
			
			return selectAllData;
		} 
		catch (Exception e) 
		{
			logger.error("selectAllData error.");
			logger.error(e.getMessage(),e);
			errorCode = AccountingErrorConstant.CODE_1007;
			return null;
		}
		finally
		{
			close(conn);
		}
		
	}
	
	public List<Map<String,String>> selectAllData(Connection conn,String dataSourceKey, String key1, String key2, String tableName, String orderBy)
	{
		List<DataParameterEntity> pps = ChasePayDatabaseMetaData.getInstsance().getMetaData(dataSourceKey, tableName);
		if (pps == null)
		{
			errorCode = AccountingErrorConstant.CODE_1005;
			return null;
		}

		String newTableName = util2.getkey1Key2RelatedTableName(dataSourceKey, tableName, key1, key2);
		
		DataParameterEntity vpp = new DataParameterEntity();
		
		StringBuilder s = new StringBuilder(SQL_LEN);

		s.append("select * from  ");
		s.append(newTableName);
		
		if (orderBy != null)
		{
			if (orderBy.indexOf("order by")>-1)
				s.append(" ").append(orderBy);
			else
				s.append(" order by "+orderBy);
		}
		
		Collection c = f.getSelectData(conn, s.toString(),null, new MapArrayResultSetCallBack());
		if (c==null)
		{
			errorCode = f.getError().getCode();
			return null;
		}
		return (List<Map<String,String>>)c;

	}
	
	public List<Map<String,String>> selectDataWithMaskColumns(Connection conn,String dataSourceKey, 
			Map<String,String> ht, String key1, String key2, 
			String tableName, String orderBy,
			List<String> maskColumns)
	{
		List<DataParameterEntity> pps = ChasePayDatabaseMetaData.getInstsance().getMetaData(dataSourceKey, tableName);
		if (pps == null)
		{
			errorCode = AccountingErrorConstant.CODE_1005;
			return null;
		}

		String newTableName = util2.getkey1Key2RelatedTableName(dataSourceKey, tableName, key1, key2);
		
		StringBuilder s = new StringBuilder(SQL_LEN);
		StringBuilder s2 = new StringBuilder(SQL_LEN);
		
		s.append("select ");

		List<DataParameterEntity> vpps = new ArrayList<DataParameterEntity>();
		int columns = 0;
		int conditionColumns = 0;
		DataParameterEntity vpp = null;
		for (DataParameterEntity p:pps)
		{		
			
			String columnName = (String)p.getValue();
			if (maskColumns.contains(columnName))
			{
				
			}
			else
			{
				columns ++;
				s.append(columnName).append(",");
			}
			
			if (columns == 1)
			{
				s2.append(" where ");
			}
			
			String value = ht.get(columnName);
			if (value == null)
				continue;
			
			conditionColumns ++;
			if (conditionColumns > 1)
				s2.append(" and ");
			
			boolean addCond = false;
			String value2 = value.trim();
			for (String c:DatabaseConstant.CONDITIONS)
			{
				if (value2.startsWith(c))
				{
					addCond = true;
					s2.append(columnName).append(value).append(" ");
					break;
				}
			}

			if (addCond)
			{
				continue;
			}
			
			if (value2.startsWith(DatabaseConstant.CONDITIONS2))
			{
				s2.append(value);
				continue;
			}
			
			
			s2.append(columnName)
			.append("=?");
			
			vpp = p.clone();
			vpp.setValue(value);
			
			/*if (p.getType() == java.sql.Types.INTEGER)
			{
				s2.append(columnName)
				.append("=?");
				
				vpp.setValue(value);
				vpp.setType(DatabaseConstant.SQL_PARAMETER_INT);
			}
			else
			{
				s2.append(columnName)
				.append("=?");
				
				vpp.setValue(value);
			}*/
			
			vpps.add(vpp);
			
		}
		
		s.delete(s.length()-1, s.length());
		s.append(" from ")
		.append(newTableName);

		Collection c = null;
		if (s2.toString().equals(" where "))
		{
			
		}
		else
		{
			s.append(s2);
		}
		
		if (orderBy != null)
		{
			if (orderBy.indexOf("order by")>-1)
				s.append(" ").append(orderBy);
			else
				s.append(" order by "+orderBy);
		}
		
		c = f.getSelectData(conn, s.toString(), vpps, new MapArrayResultSetCallBack());
		
		if (c==null)
		{
			errorCode = f.getError().getCode();
			return null;
		}
		
		return (List<Map<String,String>>)c;
	}
	
	public List<Map<String,String>> selectData(Connection conn,String dataSourceKey, Map<String,String> ht, String key1, String key2, String tableName, String orderBy)
	{
		List<DataParameterEntity> pps = ChasePayDatabaseMetaData.getInstsance().getMetaData(dataSourceKey, tableName);
		if (pps == null)
		{
			errorCode = AccountingErrorConstant.CODE_1005;
			return null;
		}

		String newTableName = util2.getkey1Key2RelatedTableName(dataSourceKey, tableName, key1, key2);

		StringBuilder s = new StringBuilder(SQL_LEN);
		StringBuilder s2 = new StringBuilder(SQL_LEN);
		
		s.append("select * ");

		List<DataParameterEntity> vpps = new ArrayList<DataParameterEntity>();
		int columns = 0;
		int conditionColumns = 0;
		DataParameterEntity vpp = null;
		for (DataParameterEntity p:pps)
		{		
			columns ++;
			if (columns == 1)
			{
				s2.append(" where ");
			}
			
			String columnName = (String)p.getValue();
			
			String value = ht.get(columnName);
			if (value == null)
				continue;
			
			conditionColumns ++;
			if (conditionColumns > 1)
				s2.append(" and ");
			
			boolean addCond = false;
			String value2 = value.trim();
			for (String c:DatabaseConstant.CONDITIONS)
			{
				if (value2.startsWith(c))
				{
					addCond = true;
					s2.append(columnName).append(value).append(" ");
					break;
				}
			}

			if (addCond)
			{
				continue;
			}
			
			if (value2.startsWith(DatabaseConstant.CONDITIONS2))
			{
				s2.append(value);
				continue;
			}
			
			/*DataParameterEntity vpp = new DataParameterEntity();
			
			if (p.getType() == java.sql.Types.INTEGER)
			{
				s2.append(columnName)
				.append("=?");
				
				vpp.setValue(value);
				vpp.setType(DatabaseConstant.SQL_PARAMETER_INT);
			}
			else
			{
				s2.append(columnName)
				.append("=?");
				
				vpp.setValue(value);
			}*/
			
			s2.append(columnName)
			.append("=?");
			
			vpp = p.clone();
			vpp.setValue(value);
			vpps.add(vpp);
			
		}
		
		s.append(" from ")
		.append(newTableName);

		Collection c = null;
		if (s2.toString().equals(" where "))
		{
			
		}
		else
		{
			s.append(s2);
		}
		
		if (orderBy != null)
		{
			if (orderBy.indexOf("order by")>-1)
				s.append(" ").append(orderBy);
			else
				s.append(" order by "+orderBy);
		}
		
		c = f.getSelectData(conn, s.toString(), vpps, new MapArrayResultSetCallBack());
		
		if (c==null)
		{
			errorCode = f.getError().getCode();
			return null;
		}
		
		return (List<Map<String,String>>)c;
	}
	
	public List<Map<String,String>> selectData(String dataSourceKey,Map<String,String> ht, String key1, String key2, String tableName, 
			String orderBy, 
			String groupBy, 
			String groupList)
	{
		
		Connection conn = null;
		try 
		{
			conn = ChasepayDatabaseConnection.getConnection(dataSourceKey);

			if (conn == null)
			{
				errorCode = AccountingErrorConstant.CODE_1015;
				return null;
			}

			List<Map<String, String>> list = selectData(conn,dataSourceKey, ht, key1, key2, tableName, orderBy, groupBy, groupList);
			
			return list;
		} 
		catch (Exception e) 
		{
			logger.error("selectData error.");
			logger.error(e.getMessage(),e);
			errorCode = AccountingErrorConstant.CODE_1007;
			return null;
		}
		finally
		{
			close(conn);
		}
				
	}
	
	public List<Map<String,String>> selectData(Connection conn,String dataSourceKey, Map<String,String> ht, String key1, String key2, String tableName, 
			String orderBy, 
			String groupBy, 
			String groupList, 
			String limit)
	{
		if (groupBy == null)
			return selectData(conn,dataSourceKey, ht, key1, key2, tableName, orderBy);
					
		List<DataParameterEntity> pps = ChasePayDatabaseMetaData.getInstsance().getMetaData(dataSourceKey, tableName);
		if (pps == null)
		{
			errorCode = AccountingErrorConstant.CODE_1005;
			return null;
		}

		String newTableName = util2.getkey1Key2RelatedTableName(dataSourceKey, tableName, key1, key2);
		
		StringBuilder s = new StringBuilder(SQL_LEN);
		StringBuilder s2 = new StringBuilder(SQL_LEN);
		
		s.append("select ");
		s.append(groupList).append(" ");

		List<DataParameterEntity> vpps = new ArrayList<DataParameterEntity>();
		int columns = 0;
		int conditionColumns = 0;
		DataParameterEntity vpp = null;
		for (DataParameterEntity p:pps)
		{		
			columns ++;
			if (columns == 1)
			{
				s2.append(" where ");
			}
			
			String columnName = (String)p.getValue();
			
			String value = ht.get(columnName);
			if (value == null)
				continue;
			
			conditionColumns ++;
			if (conditionColumns > 1)
				s2.append(" and ");
			
			boolean addCond = false;
			for (String c:DatabaseConstant.CONDITIONS)
			{
				if (value.trim().startsWith(c.toLowerCase()))
				{
					addCond = true;
					s2.append(columnName).append(value).append(" ");
					break;
				}
			}
			
			if (addCond)
			{
				continue;
			}
			
			//DataParameterEntity vpp = new DataParameterEntity();
			
			/*if (p.getType() == java.sql.Types.INTEGER)
			{
				s2.append(columnName)
				.append("=?");
				
				vpp.setValue(value);
				vpp.setType(DatabaseConstant.SQL_PARAMETER_INT);
			}
			else
			{
				s2.append(columnName)
				.append("=?");
				
				vpp.setValue(value);
			}*/
			
			s2.append(columnName)
			.append("=?");
			
			vpp = p.clone();
			vpp.setValue(value);
			
			vpps.add(vpp);
			
		}
		
		s.append(" from ")
		.append(newTableName);

		Collection c = null;
		if (s2.toString().equals(" where "))
		{
			
		}
		else
		{
			s.append(s2);
		}
		
		
		if (groupBy != null)
		{
			s.append(" ").append(groupBy);
		}

		if (orderBy != null)
		{
			s.append(" ").append(orderBy);
		}
		
		if (limit != null)
			s.append(" ").append(limit);
		
		c = f.getSelectData(conn, s.toString(), vpps, new MapArrayResultSetCallBack());
		
		if (c==null)
		{
			errorCode = f.getError().getCode();
			return null;
		}
		
		return (List<Map<String,String>>)c;
	}
	
	public List<Map<String,String>> selectData(Connection conn,String dataSourceKey, Map<String,String> ht, String key1, String key2, String tableName, 
			String orderBy, 
			String groupBy, 
			String groupList)
	{
		if (groupBy == null)
			return selectData(conn,dataSourceKey, ht, key1, key2, tableName, orderBy);
					
		List<DataParameterEntity> pps = ChasePayDatabaseMetaData.getInstsance().getMetaData(dataSourceKey, tableName);
		if (pps == null)
		{
			errorCode = AccountingErrorConstant.CODE_1005;
			return null;
		}

		String newTableName = util2.getkey1Key2RelatedTableName(dataSourceKey, tableName, key1, key2);
		
		StringBuilder s = new StringBuilder(SQL_LEN);
		StringBuilder s2 = new StringBuilder(SQL_LEN);
		
		s.append("select ");
		s.append(groupList).append(" ");

		List<DataParameterEntity> vpps = new ArrayList<DataParameterEntity>();
		int columns = 0;
		int conditionColumns = 0;
		DataParameterEntity vpp = null;
		for (DataParameterEntity p:pps)
		{		
			columns ++;
			if (columns == 1)
			{
				s2.append(" where ");
			}
			
			String columnName = (String)p.getValue();
			
			String value = ht.get(columnName);
			if (value == null)
				continue;
			
			conditionColumns ++;
			if (conditionColumns > 1)
				s2.append(" and ");
			
			boolean addCond = false;
			for (String c:DatabaseConstant.CONDITIONS)
			{
				if (value.trim().startsWith(c.toLowerCase()))
				{
					addCond = true;
					s2.append(columnName).append(value).append(" ");
					break;
				}
			}
			
			if (addCond)
			{
				continue;
			}
			
			/*DataParameterEntity vpp = new DataParameterEntity();
			
			if (p.getType() == java.sql.Types.INTEGER)
			{
				s2.append(columnName)
				.append("=?");
				
				vpp.setValue(value);
				vpp.setType(DatabaseConstant.SQL_PARAMETER_INT);
			}
			else
			{
				s2.append(columnName)
				.append("=?");
				
				vpp.setValue(value);
			}*/
			
			s2.append(columnName)
			.append("=?");
			
			vpp = p.clone();
			vpp.setValue(value);
			vpps.add(vpp);
			
		}
		
		s.append(" from ")
		.append(newTableName);

		Collection c = null;
		if (s2.toString().equals(" where "))
		{
			
		}
		else
		{
			s.append(s2);
		}
		
		
		if (groupBy != null)
			s.append(" ").append(groupBy);
		
		if (orderBy != null)
			s.append(" ").append(orderBy);
		
		c = f.getSelectData(conn, s.toString(), vpps, new MapArrayResultSetCallBack());
		
		if (c==null)
		{
			errorCode = f.getError().getCode();
			return null;
		}
		
		return (List<Map<String,String>>)c;
	}
	
	
	public List<Map<String,String>> selectDataWithLikeMap(Connection conn,String dataSourceKey, Map<String,String> ht, String key1, String key2, String tableName, 
			String orderBy, 
			String groupBy, 
			Map<String,String> likeMap)
	{
					
		List<DataParameterEntity> pps = ChasePayDatabaseMetaData.getInstsance().getMetaData(dataSourceKey, tableName);
		if (pps == null)
		{
			errorCode = AccountingErrorConstant.CODE_1005;
			return null;
		}

		String newTableName = util2.getkey1Key2RelatedTableName(dataSourceKey, tableName, key1, key2);
		
		StringBuilder s = new StringBuilder(SQL_LEN);
		StringBuilder s2 = new StringBuilder(SQL_LEN);
		
		s.append("select * ");

		List<DataParameterEntity> vpps = new ArrayList<DataParameterEntity>();
		int columns = 0;
		int conditionColumns = 0;
		DataParameterEntity vpp = null;
		for (DataParameterEntity p:pps)
		{		
			columns ++;
			if (columns == 1)
			{
				s2.append(" where ");
			}
			
			String columnName = (String)p.getValue();
			
			String value = ht.get(columnName);
			if (value == null)
				continue;
			
			conditionColumns ++;
			if (conditionColumns > 1)
				s2.append(" and ");
			
			boolean addCond = false;
			for (String c:DatabaseConstant.CONDITIONS)
			{
				if (value.trim().startsWith(c.toLowerCase()))
				{
					addCond = true;
					s2.append(columnName).append(value).append(" ");
					break;
				}
			}
			
			if (addCond)
			{
				continue;
			}
			
			/*DataParameterEntity vpp = new DataParameterEntity();
			
			if (p.getType() == java.sql.Types.INTEGER)
			{
				s2.append(columnName)
				.append("=?");
				
				vpp.setValue(value);
				vpp.setType(DatabaseConstant.SQL_PARAMETER_INT);
			}
			else
			{
				s2.append(columnName)
				.append("=?");
				
				vpp.setValue(value);
			}*/
			s2.append(columnName)
			.append("=?");
			
			vpp = p.clone();
			vpp.setValue(value);
			vpps.add(vpp);
			
		}
		
		
		int likeconditionColumns = 0;
		for (DataParameterEntity p:pps)
		{		
			
			String columnName = (String)p.getValue();
			
			String value = likeMap.get(columnName);
			if (value == null)
				continue;
			
			likeconditionColumns ++;
			if (likeconditionColumns > 1)
			{
				s2.append(" or ");
			}
			else
			{
				if (conditionColumns > 0)
				{
					s2.append(" or ");
				}
			}
				
			
			
			vpp = p.clone();
			
			s2.append(columnName)
			.append(" like ? ");
			
			vpp.setValue(value);
			
			vpps.add(vpp);
			
		}
		
		
		
		
		s.append(" from ")
		.append(newTableName);

		Collection c = null;
		if (s2.toString().equals(" where "))
		{
			
		}
		else
		{
			s.append(s2);
		}
		
		
		if (groupBy != null)
			s.append(" ").append(groupBy);
		
		if (orderBy != null)
			s.append(" ").append(orderBy);
		
		c = f.getSelectData(conn, s.toString(), vpps, new MapArrayResultSetCallBack());
		
		if (c==null)
		{
			errorCode = f.getError().getCode();
			return null;
		}
		
		return (List<Map<String,String>>)c;
	}
	
	
	
	
	public List<Map<String,String>> selectData(Map<String,String> ht,String dataSourceKey, String key1, String key2, String tableName, String orderBy)
	{


		
		Connection conn = null;
		try 
		{
			conn = ChasepayDatabaseConnection.getConnection(dataSourceKey);

			if (conn == null)
			{
				errorCode = AccountingErrorConstant.CODE_1015;
				return null;
			}

			List<Map<String,String>> c = selectData(conn,dataSourceKey, ht, key1, key2, tableName, orderBy);
			
			return c;
		} 
		catch (Exception e) 
		{
			logger.error("selectData error.");
			logger.error(e.getMessage(),e);
			errorCode = AccountingErrorConstant.CODE_1007;
			return null;
		}
		finally
		{
			close(conn);
		}
	}

	
	public void close(Connection conn)
	{
		try {
			
			if (conn!=null) conn.close();
			logger.info("call connection close");
		}
		catch(Exception e)
		{
		}
		finally
		{
			if (conn!=null) try {conn.close();}catch(Exception ee){}
		}
	}
	
	public boolean commit(Connection conn)
	{
		try {
			if (conn == null)
				return false;
			
			conn.commit();
			logger.info("commit");
			return true;
		}
		catch(Exception e)
		{
			try {conn.rollback();}catch(Exception ee){}
			return false;
		}
		finally
		{
			if (conn!=null) try {conn.close();}catch(Exception ee){logger.info(ee.getStackTrace());}
		}
	}
	
	public void rollback(Connection conn)
	{
		try {
			if (conn == null)
				return;
			
			logger.info("call connection rollback");
			conn.rollback();
		}
		catch(Exception e)
		{
		}
		finally
		{
			if (conn!=null) try {conn.close();}catch(Exception ee){}
		}
	}
	
	
	
	
	
	
	
	
	
	private String escapeSQL(String str)
	{
		if (str == null || str.length() == 0) return str;
		return str.replaceAll("[\']", "''");
	}
	
	private DatabaseFunction f = new DatabaseFunction();
	
	private String errorCode = AccountingErrorConstant.CODE_0000;
	private DatabaseFunction util2 = new DatabaseFunction();
	private static final int SQL_LEN = 1024;
	private static final int SQL_LEN2 = 512;
}