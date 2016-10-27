package org.jeecgframework.web.oct.activiti.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.oct.activiti.entity.ActApproveLog;
import org.jeecgframework.web.oct.activiti.entity.ActRuTask;
import org.jeecgframework.web.oct.activiti.entity.ActTaskBusiness;
import org.jeecgframework.web.oct.activiti.entity.ProcessAgent;
import org.jeecgframework.web.oct.activiti.service.interf.WorkflowService;
import org.jeecgframework.web.system.pojo.base.TSLog;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Title: Controller
 * @Description: 管理员监控流程
 * @author 凡艺
 * @date 2016-08-04 
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/processMonitorController")
public class ProcessMonitorController {
	
	@Autowired
	private CommonService commonService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private WorkflowService workflowService;
	 /**
		*功能描述：管理员打开代办任务页面
		*@author 凡艺
		*@since 2016-08-04
		 */
	@RequestMapping(params = "toDoList")
	public ModelAndView toDoList(HttpServletRequest request) {
		return new ModelAndView("oct/activiti/process/processMonitor/toDoList");
	}
	
	 /**
		*功能描述：管理员待办任务分页显示功能
		*@author 凡艺
		*@since 2016-08-04
		 */
	@RequestMapping(params = "todogrid")
	public void datagrid2(ActRuTask actRuTask,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ActRuTask.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, actRuTask,request.getParameterMap());
		this.commonService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	*功能描述：修改审批人跳转页面
	*@author 凡艺
	*@since 2016-08-04
	 */
	@RequestMapping(params = "editapp")
	public ModelAndView editapp(HttpServletRequest request, HttpServletResponse response,String taskId) {
			Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
			TSUser user=commonService.findByProperty(TSUser.class, "userName", task.getAssignee()).get(0);
			ModelAndView mv=new ModelAndView();
			mv.addObject("user", user);
			mv.setViewName("oct/activiti/process/processMonitor/changApprover");
			return mv;
			 
	}
	/**
	/**
	*功能描述：管理员修改审批人
	*@author 凡艺
	*@since 2016-08-04
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "changApprover")
	@ResponseBody
	public AjaxJson changApprover(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String userId = request.getParameter("userId");
		String taskId=request.getParameter("taskId");
		String comment=request.getParameter("comment");
		TaskEntity task=(TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		TSUser currentuser = ResourceUtil.getSessionUserName();
		TSUser changuser=commonService.findByProperty(TSUser.class, "userName", userId).get(0);
		String Logcontent=currentuser.getUserName()+"修改任务："+taskId+"把审批人："+task.getAssignee()+"改为："+changuser.getUserName();	
		systemService.addLog(Logcontent, Globals.Log_Type_OTHER, Globals.Log_Leavel_INFO);
		workflowService.transferAssignee(taskId, userId);
		String message = "修改审批人成功";		
		j.setMsg(message);
		return j;
	}
	/**
	*功能描述：流程代理跳转页面
	*@author 凡艺
	*@since 2016-07-22
	 */
	@RequestMapping(params = "processAgentList")
	public ModelAndView processAgentList(HttpServletRequest request) {
		request.setAttribute("status", 1);
		return new ModelAndView("oct/activiti/process/processMonitor/processagentList");
	}
	/**
	*功能描述：流程代理分页显示功能
	*@author 凡艺
	*@since 2016-07-22
	 */
	@RequestMapping(params = "processAgentgrid")
	public void processAgentgrid(ProcessAgent processAgent,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ProcessAgent.class, dataGrid);
		cq.notEq("status", "4");	
		cq.add();
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, processAgent,request.getParameterMap());
		this.commonService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	*功能描述：流程代理添加跳转
	*@author 凡艺
	*@since 2016-07-22
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest request) {	
	
			return new ModelAndView("oct/activiti/process/processMonitor/processagent-add");
	
	}
}
