package org.jeecgframework.web.oct.activiti.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.p3.core.common.utils.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.oct.activiti.entity.ActRuTask;
import org.jeecgframework.web.oct.activiti.entity.ActTaskBusiness;
import org.jeecgframework.web.oct.activiti.entity.ProcessAgent;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Title: Controller
 * @Description: 待办事宜控制器
 * @author 汪旭军
 * @date 2016-05-13 17:12:40
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/toDoController")
public class ToDoController {
	
	@Autowired
	CommonService commonService;
	
	 /**
		*功能描述：打开待办事宜页面
		*@author 汪旭军
		*@since 2016-05-13
		 */
	@RequestMapping(params = "toDoList")
	public ModelAndView toDoList(HttpServletRequest request) {
		return new ModelAndView("oct/activiti/process/todo/toDoList");
	}
	
	 /**
		*功能描述：待办事宜分页显示功能
		*@author 汪旭军
		*@since 2016-05-13
		 */
	@RequestMapping(params = "todogrid")
	public void datagrid2(ActRuTask actRuTask,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ActRuTask.class, dataGrid);
		String userId = ResourceUtil.getSessionUserName().getUserName();
		cq.eq("assignee",userId);
		cq.add();
		//查询条件组装器
		
		//获取页数=1000时强制为10
		if(cq.getPageSize()==10000){
			cq.setPageSize(10);
		}
		String type=request.getParameter("type");
		if(!StringUtil.isEmpty(type)&&type.equals("long")){
			cq.setPageSize(1000);
		}
		
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, actRuTask,request.getParameterMap());
		this.commonService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "editapp")
	public void editapp(ActRuTask actRuTask,HttpServletRequest request, HttpServletResponse response) {
		//CriteriaQuery cq = new CriteriaQuery(ActRuTask.class, dataGrid);
		//查询条件组装器
	//	org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, actRuTask,request.getParameterMap());
		//this.commonService.getDataGridReturn(cq, true);
		ActTaskBusiness actTaskBusiness =new ActTaskBusiness();
		String taskId = "";//任务ID
		String businessId="";//业务申请单ID
		String procInstId="";//流程实例ID
		taskId=request.getParameter("taskId");
		try {
		actRuTask=this.commonService.get(ActRuTask.class, taskId);
		procInstId=actRuTask.getProcInstId();
		actTaskBusiness=this.commonService.findUniqueByProperty(ActTaskBusiness.class, "processInstanceId", procInstId);
		businessId=actTaskBusiness.getBusinessId();		
			response.sendRedirect("workFlowAutoFormController.do?viewContent=&op=update&formName="+actTaskBusiness.getFormName()+"&taskId="+taskId+"&id="+businessId+"&procInstId="+procInstId);
		} catch (IOException e) {
			try {
				response.getWriter().println("该任务已完成，请重新刷新本页面");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//return new ModelAndView("workFlowAutoFormController.do?viewContent=&op=update&formName=employee_entry_form&workflowName=test_simple1&id=");
	}
	/**
	*功能描述：获取已过期待办事宜
	*@author 凡艺
	*@since 2016-10-10
	 */
	@RequestMapping(params = "getOutDueDate")
	@ResponseBody
	public AjaxJson getOutDueDate(HttpServletRequest request) {
		AjaxJson j=new AjaxJson();
		String userName= ResourceUtil.getSessionUserName().getUserName();
		String sql="select count(1) count from act_ru_task where DUE_DATE_>'"+DateUtils.getCurDate()+"' and ASSIGNEE_='"+userName+"'";
		System.out.println(userName);
		List<Map<String, Object>> resultList =  commonService.findForJdbc(sql);
		Object count = resultList.get(0).get("count");
		String sql2="select count(1) count from act_ru_task where ASSIGNEE_='"+userName+"'";
		List<Map<String, Object>> resultList2 =  commonService.findForJdbc(sql2);
	    Map maps=resultList2.get(0);	 
		Object count2 = resultList2.get(0).get("count");
		if(null==count){
			count="0";
		}
		if(null==count2){
			count2="0";
		}		
		Map map=new HashMap();
		map.put("some", count);
		map.put("all", count2);
		j.setAttributes(map);
		return j;	
		
	}
}
