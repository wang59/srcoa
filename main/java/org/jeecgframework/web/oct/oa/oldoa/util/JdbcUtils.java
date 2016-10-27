package org.jeecgframework.web.oct.oa.oldoa.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.PasswordUtil;

/**
 * 
 * @author wusl
 * step1: 配置jdbc先关参数   
 * step2: 配置表表映射规则
 *
 */

public class JdbcUtils {
	private static final Logger logger = Logger.getLogger(JdbcUtils.class);
	//private static final String EXPORT_URL = "jdbc:mysql://192.168.1.87:3306/oct_oa_hr_2";
	//private static final String IMPORT_URL = "jdbc:mysql://192.168.82.201:3306/octoa";//开发环境
	//private static final String IMPORT_URL = "jdbc:mysql://192.168.82.202:3306/octoa";//测试环境
	private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	
	private static final String MAPPING_RULE_BASE_USER[][]  = {{"nid","realname","username","departid"},{"id","real_name","user_name","org_id"}};
	private static final String MAPPING_RULE_USER[][] = {{"username","mobilePhone","officePhone"},{"user_name","phone","tel"}};
	
	private static final String MAPPING_RULE_ROLE[][] = {{"ID","rolename"},{"id","role_name"}};
	private static final String MAPPING_RULE_ROLE_USER[][] = {{"ID","userid","roleid"},{"id","user_id","role_id"}};
	
	private static final String MAPPING_ROLE_USER_ORG[][] = {{"user_id","org_id"},{"_id","DID"}};
	
	//导出库jdbc相关参数
	private static final String JDBC_EXPORT_PARAM[] = {"jdbc:mysql://192.168.1.11:3306/octoa", "root", "octoa"};
	//导入库jdbc相关参数
	private static final String JDBC_IMPORT_PARAM[] = {"jdbc:mysql://192.168.82.202:3306/octoa", "root", "Oct!2022"};
	
	
	
	static{
		try {
			Class.forName(MYSQL_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Map<String, Object>> query(String url, String userName, String pwd, String sql){
		if(logger.isDebugEnabled()){
			logger.info("jdbc查询数据");
		}
		System.out.println("-------------------------------------------");
		System.out.println("开始查询数据....");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try{
			con = DriverManager.getConnection(url, userName, pwd);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int ccount = rsmd.getColumnCount();
			while(rs.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i = 1; i <= ccount; i++){
					map.put(rsmd.getColumnName(i), rs.getObject(i));
				}
				resultList.add(map);
				map = null;
			}
			System.out.println("查询数据完成....得到结果集:\t" + resultList);
			System.out.println("-------------------------------------------");
			return resultList;
		}
		catch(Exception ex){
			ex.printStackTrace();
			if(logger.isDebugEnabled()){
				logger.info(ex.getMessage());
			}
			return resultList;
		}
		finally{
			closeResouce(rs, ps, con);
		}
	}
	public static List<Map<String,Object>> excuteQuery(String url, String userName, String pwd, String tableName){
		if(logger.isDebugEnabled()){
			logger.debug("jdbc查询数据begin");
		}
		Connection con = null;
		String sql = "SELECT * FROM " + tableName;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		System.out.println("-------------------------------------------");
		System.out.println("开始查询数据....");
		try{
			con = DriverManager.getConnection(url, userName, pwd);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int ccount = rsmd.getColumnCount();
			while(rs.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i = 1; i <= ccount; i++){
					map.put(rsmd.getColumnName(i), rs.getObject(i));
				}
				resultList.add(map);
				map = null;
			}
			System.out.println("查询数据完成....得到结果集:\t" + resultList);
			System.out.println("-------------------------------------------");
			return resultList;
		}
		catch(Exception ex){
			ex.printStackTrace();
			if(logger.isDebugEnabled()){
				logger.debug("jdbc导出数据异常：" + ex.getMessage());
			}
			return resultList;
		}
		finally{
			closeResouce(rs, ps, con);
		}	
	}
	
	
	public static int excuteInsertSql(String url, String userName, String pwd, String sql, List<String> columnName, Map<String, Object> data, String priKey, String[][] rule){
		if(logger.isDebugEnabled()){
			logger.info("jdbc执行update");
		}
		Map<String, Object> row = new HashMap<String, Object>(data);
		if(!StringUtils.isEmpty(priKey))
			row.put(priKey, getUUID());
		Connection con = null;
		PreparedStatement ps = null;
		int countUpdate = 0;
		System.out.println("-------------------------------------------");
		System.out.println("开始执行插入操作....");
		try{
			con = DriverManager.getConnection(url, userName, pwd);
			ps = con.prepareStatement(sql);
			if(null != columnName && 0 != columnName.size() && null != data && 0 != data.size()){
				for(int j = 0; j < columnName.size(); j++){
					String mappingStr = getRule(rule, columnName.get(j));
					if(StringUtils.isEmpty(mappingStr))
						ps.setObject(j + 1, row.get(columnName.get(j)));
					else
						ps.setObject(j + 1, row.get(mappingStr));
				}
			}
			countUpdate = ps.executeUpdate();
			System.out.println("插入操作成功....");
			System.out.println("-------------------------------------------");
			
			return countUpdate;
		}
		catch(Exception ex){
			ex.printStackTrace();
			if(logger.isDebugEnabled()){
				logger.info(ex.getMessage());
			}
			return countUpdate;
		}
		finally{
			closeResouce(null, ps, con);
		}
	}
	
	public static boolean existSql(String url, String userName, String pwd, String sql){
		if(logger.isDebugEnabled()){
			logger.info("执行jdbc 统计");
		}
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean countQuery = false;
		try{
			con = DriverManager.getConnection(url, userName, pwd);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			return rs.next();
		}
		catch(Exception ex){
			ex.printStackTrace();
			if(logger.isDebugEnabled()){
				logger.info(ex.getMessage());
			}
			return countQuery;
		}
		finally{
			closeResouce(rs, ps, con);
		}
	}
	
	public static int excuteDel(String url, String userName, String pwd, String sql){
		if(logger.isDebugEnabled()){
			logger.info("执行删除操作");
		}
		Connection con = null;
		PreparedStatement ps = null;
		int delCount = 0;
		System.out.println("-------------------------------------------");
		System.out.println("开始执行 jdbc删除操作");
		try{
			con = DriverManager.getConnection(url, userName, pwd);
			ps = con.prepareStatement(sql);
			delCount = ps.executeUpdate();
			return delCount;
		}
		catch(Exception ex){
			if(logger.isDebugEnabled()){
				logger.info(ex.getMessage());
			}
			return delCount;
		}
		finally{
			closeResouce(null, ps, con);
		}
	}
	
	public static int excuteUpdate(String url, String userName, String pwd, String sql, List<String> columnName, Map<String, Object> data, String condition, String[][] rule){
		if(logger.isDebugEnabled()){
			logger.info("jdbc update");
		}
		Connection con = null;
		PreparedStatement ps = null;
		int countUpdate = 0;
		List<String> column = new ArrayList<String>(columnName);
		Map<String, Object> row = new HashMap<String, Object>(data);
		System.out.println("-------------------------------------------");
		System.out.println("开始执行修改操作....");
		try{
			column.remove(condition);
			con = DriverManager.getConnection(url, userName, pwd);
			ps = con.prepareStatement(sql);
			if(null != columnName && 0 != columnName.size() && null != data && 0 != data.size()){

				int j = 0;
				for(; j < column.size(); j++){
					String mappingStr = getRule(rule, column.get(j));
					if(StringUtils.isEmpty(mappingStr))
						ps.setObject(j + 1, row.get(column.get(j)));
					else
						ps.setObject(j + 1, row.get(mappingStr));
				}
				String mappingStr = getRule(rule, condition);
				if(StringUtils.isEmpty(mappingStr)){
					ps.setObject(j + 1, row.get(condition));
				}
				else{
					ps.setObject(j + 1, row.get(mappingStr));
				}
			}
			countUpdate = ps.executeUpdate();
			System.out.println("修改操作执行完成....");
			System.out.println("-------------------------------------------");
			return countUpdate;
		}
		catch(Exception ex){
			ex.printStackTrace();
			if(logger.isDebugEnabled()){
				logger.info(ex.getMessage());
			}
			return countUpdate;
		}
		finally{
			closeResouce(null, ps, con);
		}
	}
	
	
	public static List<String> getColumnName(String url, String userName, String pwd, String tableName){
		Connection con = null;
		String sql = "SELECT * FROM " + tableName + " LIMIT 1 ";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> columnName = new ArrayList<String>();
		try{
			con = DriverManager.getConnection(url, userName, pwd);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int ccount = rsmd.getColumnCount();
			for(int i = 1; i <= ccount; i++){
				columnName.add(rsmd.getColumnName(i));
			}
			return columnName;
		}
		catch(Exception ex){
			ex.printStackTrace();
			if(logger.isDebugEnabled()){
				logger.debug(ex.getMessage());
			}
			return columnName;
		}
		finally{
			closeResouce(rs, ps, con);
		}
	}
	
	private static String buildMySqlInsertSql(List<String> columnName, String tableName){
		if(0 == columnName.size() || StringUtils.isEmpty(tableName)){
			return null;
		}
		StringBuffer sb = new StringBuffer("INSERT INTO " + tableName + "(");
		for(String name : columnName){
			sb.append("`" + name +"`,");
		}
		sb.delete(sb.length()-1, sb.length());
		sb.append(" )");
		sb.append("VALUES (");
		for(int i = 0; i < columnName.size(); i++){
			sb.append(" ?,");
		}
		sb.delete(sb.length()-1, sb.length());
		sb.append(" )");
		return sb.toString();
	}
	
	private static String buildMysqlUpdateSql(List<String> columnName, String tableName, String[] condition){
		if(null == columnName || 0 == columnName.size() || StringUtils.isEmpty(tableName)){
			return null;
		}
		for(String con : condition){
			columnName.remove(con);
		}
		StringBuffer sb = new StringBuffer("UPDATE " + tableName + " SET ");
		for(String name : columnName){
			sb.append(" `" + name + "`= ?," );
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append(" WHERE " + condition[0] +" =?");
		return sb.toString();
	}
	
	private static void closeResouce(ResultSet rs, PreparedStatement ps, Connection con){
		if(null != rs){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(null != ps){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(null != con){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getRule(String[][] arr, String key){
		if(null == arr || 0 == arr.length){
			return null;
		}
		for(int i = 0; i < arr[0].length; i++){
			if(key.equals(arr[0][i])){
				return arr[1][i];
			}
		}
		return null;
	}
	//备份数据
		public static void dempData(List<Map<String, Object>> map, String fileName){
			logger.info("------------------------------------");
			logger.info("开始备份数据，备份路径：" + fileName);
			PrintWriter pw = null;
			try{
				if(null == map || map.isEmpty()){
					return;
				}
				pw = new PrintWriter(new FileOutputStream(fileName));
				for(Map<String, Object> row : map){
					pw.println(row.toString());
				}
				pw.flush();
			}
			catch(Exception ex){
				ex.printStackTrace();
				logger.info(ex.getMessage());
				logger.info("备份数据异常" + ex.getMessage());
				logger.info("------------------------------------");
			}
			finally{
				if(null != pw){
					pw.close();
				}
				logger.info("备份数据结束");
				logger.info("------------------------------------");
			}
		}

	
	public static void main(String[] args){
		//importProcess();//执行t_s_base_user
		//System.out.println(existSql(IMPORT_URL, "root", "Oct!2011", "select * from t_s_base_user where username = 'administrator'"));
		//System.out.println(getUUID().length());
		importTSUser();//执行t_s_user
		//importTSRole();//执行t_s_role
		//importTSRoleUser();//执行t_s_role_user
		//importTSUserOrg();//执行组织结构中间表 t_s_user_org
		//System.out.println(getUUID());		
	}
	
	public static String getUUID(){	
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}
	
	
	//导库 t_s_user_org
	public static void importTSUserOrg(){
		//查出数据
		String sql_demp = "select * from t_s_user_org";
		List<Map<String, Object>> query_temp = query(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], sql_demp);
		//备份数据
		dempData(query_temp,"d:\\temp_t_s_user_org_" + new java.util.Date().getTime() + ".txt");
		//删除数据
		String sql_del = "delete from t_s_user_org";
		excuteDel(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], sql_del);
		//查找用户表
		String sql_base = "select * from t_s_base_user ";
		List<Map<String, Object>> query_res = query(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], sql_base);
		if(null == query_res || 0 == query_res.size()){
			return;
		}
		for(Map<String, Object> map : query_res){
			Object departid = map.get("departid");
			if(null == departid || StringUtils.isEmpty(departid.toString())){
				continue;
			}
			String sql_depart = "select * from t_s_depart where nid = '" + departid + "'";
			List<Map<String, Object>> query_res_depart = query(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], sql_depart);
			if(null == query_res_depart || 0 == query_res_depart.size()){
				continue;
			}
			Object DID = query_res_depart.get(0).get("ID");
			map.put("DID", DID);
			map.put("_id", map.get("ID"));
			List<String> columnName = getColumnName(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], "t_s_user_org");
			String build_insert_sql = buildMySqlInsertSql(columnName, "t_s_user_org");
			excuteInsertSql(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], build_insert_sql, columnName, map, "ID", MAPPING_ROLE_USER_ORG);
		}
	}
	
	/**
	 * 从旧OA数据导到新OA
	 * 旧OA表oct_sys_user_role
	 * 新OA表 t_s_role_user
	 */
	public static void importTSRoleUser(){
		String sql = "select * from oct_sys_user_role";
		List<Map<String, Object>> result = query(JDBC_EXPORT_PARAM[0], JDBC_EXPORT_PARAM[1], JDBC_EXPORT_PARAM[2], sql);
		for(Map<String, Object> row : result){
			if(null == row.get("user_id") || StringUtils.isEmpty(row.get("user_id").toString())){
				continue;
			}
			//根据userId查新oa用户
			String sql_query = "select * from t_s_base_user where nid = '" + row.get("user_id") + "'";
			List<Map<String, Object>> list = query(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], sql_query);
			if(null == list || 0 == list.size()){
				continue;
			}
			row.put("user_id", list.get(0).get("ID"));
			String sql_query_tsuser = "select * from t_s_user where id = '" + list.get(0).get("ID") + "'";
			List<Map<String,Object>> list_query_tsuser = query(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], sql_query_tsuser);
			if(null == list_query_tsuser || 0 == list_query_tsuser.size()){
				continue;
			}
			//根据userid,roleid查新oa t_s_role_user
			String sql_query_role_user = "select * from t_s_role_user where roleid = '" + row.get("role_id") + "' and userid = '" + row.get("user_id") + "'";
			List<Map<String, Object>> list_query_role_user = query(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], sql_query_role_user);
			if(null != list_query_role_user && 0 != list_query_role_user.size()){
				continue;
			}
			List<String> listName = getColumnName(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], "t_s_role_user");
			String sql_insert = buildMySqlInsertSql(listName, "t_s_role_user");
			System.out.println("执行数据集：\t" + row);
			excuteInsertSql(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], sql_insert, listName, row, null, MAPPING_RULE_ROLE_USER);
		}
	}
	
	/**
	 * 从旧OA数据导到新OA
	 * 旧OA表oct_sys_role
	 * 新OA表t_s_role
	 */
	public static void importTSRole(){
		String sql = "select * from oct_sys_role";
		List<Map<String, Object>> result = query(JDBC_EXPORT_PARAM[0], JDBC_EXPORT_PARAM[1], JDBC_EXPORT_PARAM[2], sql);
		for(Map<String, Object> row : result){
			if("admin".equals(row.get("role_name").toString())){
				continue;
			}
			String sql_check = "select * from t_s_role where rolename = '" + row.get("role_name") + "'";
			List<Map<String, Object>> list = query(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], sql_check);
			if(null == list || 0 == list.size()){
				List<String> listName = getColumnName(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], "t_s_role");
				String sql_insert = buildMySqlInsertSql(listName, "t_s_role");
				System.out.println("执行数据集：\t" + row);
				excuteInsertSql(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], sql_insert, listName, row, null, MAPPING_RULE_ROLE);
			}
		}
	}
	
	/**
	 * 从旧oa数据导到新oa
	 * 旧oa表  oct_sys_oa_user
	 * 新oa表 t_s_user
	 */
	
	public static void importTSUser(){
		String _sql = "select * from oct_sys_oa_user base inner join oct_sys_userinfo info on base.id = info.id ";
		List<Map<String, Object>> exportData = query(JDBC_EXPORT_PARAM[0], JDBC_EXPORT_PARAM[1], JDBC_EXPORT_PARAM[2], _sql);
		//获取导出数据
		//List<Map<String, Object>> exportData = excuteQuery(JDBC_EXPORT_PARAM[0],JDBC_EXPORT_PARAM[1],JDBC_EXPORT_PARAM[2],"oct_sys_oa_user");
		//获取表列相关信息
		@SuppressWarnings("unused")
		List<String> listName = getColumnName(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], "t_s_user");
		//迭代数据
		for(Map<String, Object> row : exportData){
			String sql = "select * from t_s_base_user where username = '" + row.get("user_name") + "'";
			List<Map<String, Object>> list = query(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], sql);

			if(null == list || 0 == list.size()){
				continue;
			}
			//取得id
			String id = list.get(0).get("ID").toString();
			//验证数据是否存在
			String sql_check = "select * from t_s_user where id = '" + id + "'";
			boolean exist = existSql(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], sql_check);
			row.put("id", id);
			if(exist){
				//修改
				String[] condition = {"id"};
				String update_sql = buildMysqlUpdateSql(listName, "t_s_user", condition);
				excuteUpdate(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], update_sql, listName, row, "id", MAPPING_RULE_USER);
				listName.add("id");
			}
			else{
				//添加
				String insert_sql = buildMySqlInsertSql(listName, "t_s_user");
				excuteInsertSql(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], insert_sql, listName, row, null, MAPPING_RULE_USER);
			}
		}
		
	}
	/**
	 *  从旧OA数据导到新oa
	 *  旧oa表   oct_sys_oa_user
	 *  新oa表  t_s_base_user
	 */
	public static void importProcess(){
		//获取导出数据
		//String sql = "select * from oct_sys_oa_user base inner join oct_sys_userinfo info on base.id = info.id";
		//List<Map<String, Object>> exportData = query(JDBC_EXPORT_PARAM[0], JDBC_EXPORT_PARAM[1], JDBC_EXPORT_PARAM[2], sql);
		List<Map<String, Object>> exportData = excuteQuery(JDBC_EXPORT_PARAM[0],JDBC_EXPORT_PARAM[1],JDBC_EXPORT_PARAM[2],"oct_sys_oa_user");
		//获取用户基础表列元素信息
		List<String> listName = getColumnName(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], "t_s_base_user");
		for(Map<String, Object> row : exportData){
			if("admin".equals(row.get("user_name"))){
				continue;
			}
			row.put("password", PasswordUtil.encrypt(row.get("user_name").toString(), row.get("password").toString(), PasswordUtil.getStaticSalt()));
			try{
				String userName = row.get("user_name").toString();
				String sql = "SELECT * FROM t_s_base_user WHERE username = '" + userName + "'";
				boolean exist = existSql(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], sql);
				if(exist){
					String[] condition = {"username","ID"};
					String updateSql = buildMysqlUpdateSql(listName, "t_s_base_user", condition);
					excuteUpdate(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], updateSql, listName, row, "username",MAPPING_RULE_BASE_USER);
					listName.add("ID");
					listName.add("username");
				}
				else{
					String insertSql = buildMySqlInsertSql(listName, "t_s_base_user");
					excuteInsertSql(JDBC_IMPORT_PARAM[0], JDBC_IMPORT_PARAM[1], JDBC_IMPORT_PARAM[2], insertSql, listName, row, "ID", MAPPING_RULE_BASE_USER);
				}
			}
			catch(Exception ex){
				System.out.println(row);
				ex.getMessage();
				logger.info(ex.getMessage());
			}

		}
		
	}
}
