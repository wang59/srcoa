package org.jeecgframework.web.oct.common.service;

import java.util.List;
import java.util.Map;

import org.jeecgframework.web.oct.common.util.PageResult;

/**
 * @Todo		使用原始的SQL，实现CRUD功能。
 * @author		kuangy
 * @lastTime	2016-6-21
 */
public interface ICrudService {
	
	/**
	 * @Todo		使用原始的SQL
	 * @author		kuangy
	 * @lastTime	2016-6-21
	 */
	public List<Map<String, Object>> sqlQuery(String sqlName, String p);
	
	/**
	 * @Todo		使用原始的SQL分页查询
	 * @author		kuangy
	 * @lastTime	2016-6-21
	 */
	public PageResult<List<Map<String, Object>>> sqlQuery(Integer page, Integer rows, String sqlName, String p);

}
