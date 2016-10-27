package org.jeecgframework.web.oct.oa.travel.app.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author wusl
 * 出差申请技术支持
 * @date 2016-10-23
 */

@Scope("prototype")
@Controller
@RequestMapping(value ="/techTravelApplicationController")
public class TechTravelApplicationController {
	
	@Autowired
	private SystemService systemService;
	
	/**
	 * 根据当前用户名，获取相关出差技术支持，未审批的技术支持数据
	 */
	@RequestMapping(params = "queryTravel")
	@ResponseBody
	public List<Map<String, Object>> queryTravel(){
		TSUser tsUser = ResourceUtil.getSessionUserName();
		String sql = "select b.* from businesstravel b left join oct_tech_travel_application o on "
				   + "b.id = o.travel_id where o.travel_id is null and b.applicant_id like '%" + tsUser.getUserName() + "%' ";
		List<Map<String, Object>> list_res = systemService.findForJdbc(sql);
		for(Map<String, Object> map : list_res){
			Object createTime = map.get("create_date");
			if(null != createTime && !StringUtils.isEmpty(createTime.toString())){
				map.put("create_date", createTime.toString().split("/.")[0]);
			}
			Object beginTime = map.get("begin_time");
			if(null != beginTime && !StringUtils.isEmpty(beginTime.toString())){
				map.put("begin_time", beginTime.toString().split("/.")[0]);
			}
			Object endTime = map.get("end_time");
			if(null != endTime && !StringUtils.isEmpty(endTime.toString())){
				map.put("end_time", endTime.toString().split("/.")[0]);
			}
		}
		return list_res;
	}
 }
