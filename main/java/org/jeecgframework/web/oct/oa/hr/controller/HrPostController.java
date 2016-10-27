package org.jeecgframework.web.oct.oa.hr.controller;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.oct.activiti.entity.ActRuTask;
import org.jeecgframework.web.oct.oa.hr.entity.OctHrPost;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 职位控制器
 *
 * @author xiejz
 */
@Scope("prototype")
@Controller
@RequestMapping("/hrPostController")
public class HrPostController {
	
	@Autowired
	CommonService commonService;
	
	/***
	 * 根据部门id查询职位
	 * @param sid
	 * @throws IOException 
	 */
	@RequestMapping(params="getPost")
	public  void getPost(@RequestParam String orgId,HttpServletResponse response) throws IOException{
			
		List<OctHrPost> list = commonService.findByProperty(OctHrPost.class, "orgId",orgId );
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(list);
		response.getWriter().write(array.toString());

	}

	
}
