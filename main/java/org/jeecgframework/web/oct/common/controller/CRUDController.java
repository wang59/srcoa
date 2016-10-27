package org.jeecgframework.web.oct.common.controller;

import org.jeecgframework.web.oct.common.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**   
 * @Title: Controller
 * @Description: 使用原始的SQL，实现CRUD功能。
 * @author 汪旭军
 * @date 2016-06-19
 * @version V1.0   
 *@sample
 *   crudController.do?query&sqlid=333&p=name,sdf
 */
@Scope("prototype")
@Controller
@RequestMapping("/crudController")
public class CRUDController {
	
//	@Autowired
//	private SystemService systemService;
	
	@Autowired
	private ICrudService crudService;
	
	/**
	 * @Todo		通用grid
	 * @author		汪旭军
	 * @lastTime	2016-6-20
	 * 测试地址：http://127.0.0.1:8080/octoa/crudController.do?query&p=&sqlName=123
	 */
	@RequestMapping(params = "query")
	public @ResponseBody Object sqlQuery(Integer page, Integer rows, String sqlName, String p){
		//获取SQL语句
//		StringBuffer sqlr= new StringBuffer("SELECT ID_,NAME_ FROM octoa.act_ru_task where TASK_DEF_KEY_ like '%s' and ID_='%s'");
//		for(String param:paramArr){//拼接SQL语句
//			sqlr.append(" and ").append("属性").append("判断符号").append("值");
//		}
//		sqlr =String.format(sqlr, paramArr);
		if(page !=null && rows != null)
			return crudService.sqlQuery(page, rows, sqlName, p);
		else
			return crudService.sqlQuery(sqlName, p);
	}
}
