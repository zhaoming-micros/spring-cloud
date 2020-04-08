package com.chasepay.database.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.chasepay.database.constants.DatabaseConstant;
import com.chasepay.databse.entity.ChasepayBasicDatasource;
import com.chasepay.databse.entity.DataParameterEntity;



public class ChasePayDatabaseMetaData {
	
	private static Logger logger = LogManager.getLogger(ChasePayDatabaseMetaData.class);
	private static ChasePayDatabaseMetaData instance = null;
	private static Map<String, Map<String, List<DataParameterEntity>>> mht = new ConcurrentHashMap<String, Map<String, List<DataParameterEntity>>>();
	private static Map<String, Map<String, Map<String, DataParameterEntity>>> mht2 = new ConcurrentHashMap<String, Map<String, Map<String, DataParameterEntity>>>();
	private static Map<String, Map<String, String>> mht3 = new ConcurrentHashMap<String, Map<String, String>>();
	private static Map<String, List<String>> mtableNames = new ConcurrentHashMap<String, List<String>>();
	private static Map<String, List<String>> allHt = new ConcurrentHashMap<String, List<String>>();
	private static Map<String, List<String>> mRelationTableNames1 = new ConcurrentHashMap<String, List<String>>();
	private static Map<String, List<String>> mRelationTableNames2 = new ConcurrentHashMap<String, List<String>>();
	
	private ChasePayDatabaseMetaData()
	{
		
	}
	
	public static ChasePayDatabaseMetaData getInstsance()
	{
		if (instance == null)
		{
			instance = new ChasePayDatabaseMetaData();
		}
		
		return instance;
	}
	
	public boolean init(ChasepayBasicDatasource chasepayBasicDatasource)
	{

		System.out.println("init DatabaseMetaData");
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		DatabaseMetaData d = null;
		
		String dataSourceKey = chasepayBasicDatasource.getKey();
		BasicDataSource basicDataSource = chasepayBasicDatasource.getBasicDataSource();
		String dbName = chasepayBasicDatasource.getDbName();
		

		List<String> newTableNamesList = new CopyOnWriteArrayList<String>();
		allHt.put(dataSourceKey, newTableNamesList);
			
		List<String> tableNames = new ArrayList<String>();
		mtableNames.put(dataSourceKey,  tableNames);
		
		mRelationTableNames1.put(dataSourceKey,chasepayBasicDatasource.getRelationTables1());
		mRelationTableNames2.put(dataSourceKey,chasepayBasicDatasource.getRelationTables2());

		Map<String, Map<String, DataParameterEntity>> ht2 = new HashMap<String, Map<String, DataParameterEntity>>();
		mht2.put(dataSourceKey,  ht2);

		Map<String, String> ht3 = new HashMap<String, String>();
		mht3.put(dataSourceKey, ht3);

		Map<String, List<DataParameterEntity>> ht = new HashMap<String, List<DataParameterEntity>>();
		mht.put(dataSourceKey, ht);
		Connection connection = null;
		try {

			    connection = basicDataSource.getConnection();
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables where table_schema='"+dbName+"'");

				while (rs.next())
				{
					String tableName = rs.getString(1);

					if (isCompanyRelatedName(tableName))
					{
						tableNames.add(tableName);
						newTableNamesList.add(tableName);
						continue;
					}//setTableData(conn, tableName, rs2);

					d = connection.getMetaData();
					rs2 = d.getColumns(dbName, dbName, tableName, "");

					List<DataParameterEntity> pps = new ArrayList<DataParameterEntity>();
					Map<String, DataParameterEntity> ppht = new HashMap<String, DataParameterEntity>();
					while (rs2.next())
					{

						String name = rs2.getString(4);

						if (ppht.get(name)!=null)
							continue;

						DataParameterEntity p = new DataParameterEntity();
						p.setValue(rs2.getString(4));
						
						int type = rs2.getInt(5);
						if (type==Types.INTEGER)
							p.setType(DatabaseConstant.SQL_PARAMETER_INT);
						else if (type==Types.DECIMAL)
							p.setType(DatabaseConstant.SQL_PARAMETER_DECIMAL);
						else
							p.setType(DatabaseConstant.SQL_PARAMETER_STRING);
						
						pps.add(p);

						DataParameterEntity p2 = new DataParameterEntity();
						p2.setClumnName((String)p.getValue());
						p2.setType(p.getType());

						ppht.put(p2.getClumnName(),  p2);

					}
					rs2.close();

					ht2.put(tableName.toLowerCase(), ppht);
					ht.put(tableName.toLowerCase(), pps);

					rs2 = d.getPrimaryKeys(dbName, null, tableName);
					if (rs2!=null && rs2.next())
					{
						ht3.put(tableName.toLowerCase(), rs2.getString(4));
					}

				}

				logger.info("Get db meta data, ht="+ht.keySet());

			} catch (SQLException e) {

				e.printStackTrace();
				logger.error("getMetadata fail 1:" + e.getMessage(),e);


			} catch (Exception e) {

				e.printStackTrace();
				logger.error("getMetadata fail 2:" + e.getMessage(),e);

			} 
			finally
			{
				if (rs != null)	try {rs.close();}catch(Exception e1){}
				if (stmt != null)	try {stmt.close();}catch(Exception e1){}
				if (connection != null)	try {connection.close();}catch(Exception e1){}
			}

		
		return true;
	}
	

	public List<DataParameterEntity> getMetaData(String datasourceKey,String tableName)
	{
		Map<String, List<DataParameterEntity>> h = mht.get(datasourceKey);
		if (h != null)
			return h.get(tableName.toLowerCase());
		
		return null;
	}
	
	public Map<String, DataParameterEntity> getTableMetadataMap(String datasourceKey,String tableName)
	{
		Map<String, Map<String, DataParameterEntity>> ht2 = mht2.get(datasourceKey);
		if (ht2 != null)
			return ht2.get(tableName.toLowerCase());
		
		return null;
	}
	
	public String getPrimayKey(String datasourceKey,String tableName)
	{
		Map<String,String> ht3 = mht3.get(datasourceKey);
		if (ht3!=null)
			return ht3.get(tableName.toLowerCase());
		
		return null;
	}
	
	public List<String> getRelationTables1(String datasourceKey)
	{
		return mRelationTableNames1.get(datasourceKey);
	}
	
	public List<String> getRelationTables2(String datasourceKey)
	{
		return mRelationTableNames2.get(datasourceKey);
	}
	
	private static boolean isCompanyRelatedName(String name)
	{
		int i = name.lastIndexOf("_");
		if (i==-1)
			return false;
		
		String seq = name.substring(i+1);
		if (seq.matches("[0-9]{1,5}"))
			return true;
		
		return false;
	}
	
	public void addTable(String datasourceKey, String newTableName)
	{
		logger.info("addTable:"+datasourceKey+" "+newTableName);
		List<String> l = allHt.get(datasourceKey);
		l.add(newTableName);
	}
	
	public boolean tableExists(String datasourceKey, String newTableName)
	{
		return allHt.get(datasourceKey).contains(newTableName);
	}
}
