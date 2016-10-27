package org.jeecgframework.web.oct.common.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.DynamicDBUtil;
import org.jeecgframework.p3.core.util.UUIDGenerator;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.web.system.pojo.base.DynamicDataSourceEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

/**
 * 
 * @author 吴思亮
 * @数据源导入导出
 * @date 2016-10-20
 */
public class JdbcUtils {
	
	private static final Logger logger = Logger.getLogger(JdbcUtils.class);
	private static final String dirverClassName = "com.mysql.jdbc.Driver";
	private static DynamicDataSourceEntity exportDataSource = null;
	private static DynamicDataSourceEntity importDataSource = null; 
	
	public static void main(String[] args){
		//executeImport(exportDataSource, importDataSource, "businesstravel");
		List<Map<String, Object>> list = DynamicDBUtil.queryMetaData(exportDataSource, "cgform_head");
		String sql = DynamicDBUtil.buildMysqlUpdateSql(list, "cgform_head", "id");
		System.out.println(sql);
	}
	
	static{
		//设置日志级别
		logger.setLevel(Level.ALL);
		exportDataSource = getDynamicDataSource(dirverClassName, "jdbc:mysql://192.168.82.202:3306/octoa", "root", "Oct!2022");
		importDataSource = getDynamicDataSource(dirverClassName, "jdbc:mysql://192.168.82.201:3306/octoa", "root", "Oct!2011");
	}
	
	/**
	 * 
	 * @param driverClassName 驱动
	 * @param url db地址
	 * @param dbUser 用户名
	 * @param pwd 密码
	 * @return  数据源实体类
	 * @Date 2016-10-20
	 */
	public static DynamicDataSourceEntity getDynamicDataSource(String driverClassName, String url, String dbUser, String pwd){
		DynamicDataSourceEntity entity = new DynamicDataSourceEntity();
		
		logger.info(String.format("实例化动态数据源，驱动名：%s, 地址为：%s, 用户名：%s, 密码：%s", driverClassName, url, dbUser, pwd));
		entity.setDbKey(UUIDGenerator.generate());
		entity.setDriverClass(driverClassName);
		entity.setUrl(url);
		entity.setDbUser(dbUser);
		entity.setDbPassword(pwd);
		
		return entity;
	}
	
	/**
	 * 
	 * @param exportDataSource 导出数据源
	 * @param importDataSource 导入数据源
	 * @param tableName  表名
	 * 执行 导数据操作
	 */
	public static void executeImport(DynamicDataSourceEntity exportDataSource, DynamicDataSourceEntity importDataSource, String tableName){
		JdbcTemplate exportTemplate = DynamicDBUtil.getJdbcTemplate(exportDataSource);
		JdbcTemplate importTemplate = DynamicDBUtil.getJdbcTemplate(importDataSource);
		List<Map<String, Object>> metaData = DynamicDBUtil.queryMetaData(exportDataSource, tableName);
		if(null == metaData || 0 == metaData.size()){
			return;
		}
		//判断表是否存在
		boolean exist = findMysqlTableExist(importDataSource, tableName);
		if(!exist){
			//生成creat sql
			String sql = buildMysqlCreateTableSql(metaData, tableName);
			logger.info(String.format("创建表，表名为：%s,元属性为%s ---------------开始", tableName, metaData));
			importTemplate.update(sql);
			logger.info(String.format("创建表，表名为：%s,元属性为%s----------------结束", tableName, metaData));
		}
		//查询数据 ps：数据量大，请使用文件，或者分页
		String queryExportSql = "select * from " + tableName;
		List<Map<String, Object>> exportData = exportTemplate.queryForList(queryExportSql);
		if(null == exportData || 0 == exportData.size()){
			return;
		}
		String insertImportSql = buildMySqlInsertSql(metaData, tableName);
		for(Map<String, Object> map: exportData){
			try{
				importTemplate.update(insertImportSql, new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						int i = 1;
						for(Entry<String, Object> entry : map.entrySet()){
							ps.setObject(i++, entry.getValue());
						}
					}
				});
				logger.info(String.format("导入数据成功，数据为：%s,导入表名：%s", map,tableName));
			}
			catch(Exception ex){
				ex.printStackTrace();
				logger.info(String.format("导入数据失败，数据为：%s,导入表名：%s", map,tableName));
			}
		}
	}
	/**
	 * 
	 * @param metaData 表结构元数据
	 * @param tableName 表名
	 * @return 返回插入sql
	 */
	private static String buildMySqlInsertSql(List<Map<String, Object>> metaData, String tableName){
		if(0 == metaData.size() || StringUtils.isEmpty(tableName)){
			return null;
		}
		StringBuffer sb = new StringBuffer("INSERT INTO " + tableName + "(");
		for(Map<String, Object> meta : metaData){
			sb.append("`" + meta.get("columnName") +"`,");
		}
		sb.delete(sb.length()-1, sb.length());
		sb.append(" )");
		sb.append("VALUES (");
		for(int i = 0; i < metaData.size(); i++){
			sb.append(" ?,");
		}
		sb.delete(sb.length()-1, sb.length());
		sb.append(" )");
		return sb.toString();
	}
	
	/**
	 * 
	 * @param metaData 表元数据
	 * @param tableName 表名
	 * @return 返回create table sql
	 */
	public static String buildMysqlCreateTableSql(List<Map<String, Object>> metaData, String tableName){
		StringBuilder strBuilder = new StringBuilder();
		if(StringUtils.isEmpty(tableName) || 0 == metaData.size()){
			return null;
		}
		strBuilder.append("CREATE TABLE `" + tableName + "` (");
		for(Map<String, Object> meta : metaData){
			String columnName = meta.get("columnName").toString().toLowerCase();
			String columnTypeName = meta.get("columnTypeName").toString();
			String columnTypeSize = meta.get("columnTypeSize").toString();
			StringBuilder sb = new StringBuilder(" `" + columnName + "` " + buildProperties(columnTypeName, columnTypeSize));
			if("id".equals(columnName)){
				sb.append("NOT NULL,");
			}
			else{
				sb.append("DEFAULT NULL,");
			}
			strBuilder.append(sb.toString());
		}
		strBuilder.append("PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8");
		
		return strBuilder.toString();
	}
	
	public static String buildProperties(String columnTypeName, String columnTypeSize){
		columnTypeName = columnTypeName.toUpperCase();
		String str = null;
		switch(columnTypeName){
			case "DATETIME": str = columnTypeName + " "; break;
			default : str = columnTypeName + "(" + columnTypeSize + ") ";
		}
		return str;
	}
	
	
	/**
	 * 
	 * @param dynamicDataSource 数据源
	 * @param tableName 表名
	 * @return 验证表是否存在
	 */
	public static boolean findMysqlTableExist(DynamicDataSourceEntity dynamicDataSource, String tableName){
		if(StringUtils.isEmpty(tableName)){
			logger.info(String.format("判断表名是否存在参数为空"));
			throw new RuntimeException("表名不能为空");
		}
		tableName = tableName.trim();
		StringBuilder sb = new StringBuilder("select `TABLE_NAME` from `INFORMATION_SCHEMA`.`TABLES` where ");
		//默认是mysql
		sb.append(" `TABLE_SCHEMA`='" + getMysqlDbName(dynamicDataSource));
		sb.append("' and `TABLE_NAME`='" + tableName + "'");
		JdbcTemplate jdbc = DynamicDBUtil.getJdbcTemplate(dynamicDataSource);
		List<Map<String, Object>> listMap = jdbc.queryForList(sb.toString());
		logger.info(String.format("查询表是否存在，%s", listMap));
		return listMap.size() > 0;
	}
	

	/**
	 * 
	 * @param dynamicDataSource 数据源
	 * @return 返回db name
	 */
	public static String getMysqlDbName(DynamicDataSourceEntity dynamicDataSource){
		return dynamicDataSource.getUrl().substring(dynamicDataSource.getUrl().lastIndexOf("/") + 1);
	}
	
}

