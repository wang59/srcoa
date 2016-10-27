package org.jeecgframework.web.oct.common.service.impl;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.web.oct.common.entity.OctSqlQueryEntity;
import org.jeecgframework.web.oct.common.service.ICrudService;
import org.jeecgframework.web.oct.common.util.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Todo		使用原始的SQL，实现CRUD功能。
 * @author		kuangy
 * @lastTime	2016-6-21
 */
@Service("crudService")
public class CRUDService implements ICrudService{
	
//	private static final Logger logger = LoggerFactory.getLogger(CRUDService.class);
	
	@Autowired
	private ICommonDao commonDao;

	@Override
	@Transactional(readOnly=true)
	public List<Map<String, Object>> sqlQuery(String sqlName, String p) {
		OctSqlQueryEntity sqlr = commonDao.findUniqueByProperty(OctSqlQueryEntity.class, "name", sqlName);
		String sql =String.format(sqlr.getSqlQuery(), p.split(","));//获取SQL语句的参数
		return commonDao.findForJdbc(sql);//用现成的，省的自己再去写
//		String[] columns = sqlr.getColumns().split(",");
//		List<Object[]> resultList = commonDao.findListbySql(sql);
//		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(resultList.size());
//		Object[] os;
//		for(Iterator iterator = resultList.iterator();iterator.hasNext();){
//			Map<String, Object> m = new HashMap<String, Object>(columns.length);
//			os = (Object[]) iterator.next();
//			for (int i = 0; i < columns.length; i++) {
//				m.put(columns[i], os[i]);
//			}
//			result.add(m);
//		}
//		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public PageResult<List<Map<String, Object>>> sqlQuery(Integer page, Integer rows, String sqlName, String p) {
		OctSqlQueryEntity sqlr = commonDao.findUniqueByProperty(OctSqlQueryEntity.class, "name", sqlName);
		String sql =String.format(sqlr.getSqlQuery(), p.split(","));//获取SQL语句的参数
		PageResult<List<Map<String, Object>>> result = new PageResult<>();
		result.setTotal(commonDao.getCountForJdbc(new StringBuffer("SELECT COUNT(*) FROM (").append(sql).append(") as t").toString()));
		result.setRows((List)commonDao.findForJdbc(sql, page, rows));
		return result;
	}
	
	
	
	
	
}
