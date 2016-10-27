package org.jeecgframework.web.oct.oa.transfer.datasource.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeecgframework.core.util.DynamicDBUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author wusl
 * 数据源迁移controller
 * 2016-10-24
 *
 */
@Scope("prototype")
@Controller
@RequestMapping(value = "/transferDataSourceController")
public class TransferDataSourceController {

	@Autowired
	private SystemService systemService;
	private static final Logger logger = Logger.getLogger(TransferDataSourceController.class);
	
	@RequestMapping(params = "index")
	public ModelAndView indexPage(){
		if(logger.isDebugEnabled()){
			logger.debug(String.format("数据源迁移首页"));
		}
		ModelAndView mv = new ModelAndView("oct/oa/datasource/transferDataSource");
		return mv;
	}
	
	@RequestMapping(params = "dataSourceSelect")
	@ResponseBody
	public List<Map<String, Object>> initDataSourceSelect(){
		List<Map<String, Object>> resSelect = new ArrayList<Map<String, Object>>();
		
		String sql = "select db_key as `key`, description as `des` from t_s_data_source";
		resSelect = systemService.findForJdbc(sql);
		
		return resSelect;
	}
	
	@RequestMapping(params = "showTables")
	@ResponseBody
	public List<Map<String,Object>> showTables(HttpServletRequest request, @RequestParam String key){
		List<Map<String, Object>> resSelect = new ArrayList<Map<String, Object>>();
		resSelect = DynamicDBUtil.findList(key, "show tables");
		return resSelect;		
	}
}
