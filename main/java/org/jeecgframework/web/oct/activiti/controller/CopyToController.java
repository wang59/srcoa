package org.jeecgframework.web.oct.activiti.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.oct.activiti.entity.ActRuTask;
import org.jeecgframework.web.oct.activiti.entity.ActTaskBusiness;
import org.jeecgframework.web.oct.activiti.entity.OctActCopyTo;
import org.jeecgframework.web.oct.activiti.service.interf.WorkflowService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Title: Controller
 * @Description: 待阅事宜控制器
 * @author 凡艺
 * @date 2016-08-16 17:12:40
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/copyToController")
public class CopyToController {
	
	@Autowired
	private CommonService commonService;
	@Autowired
	private WorkflowService workflowService;
	 /**
		*功能描述：打开待阅事宜页面
		*@author 凡艺
		*@since 2016-08-16
		 */
	@RequestMapping(params = "copyToList")
	public ModelAndView toDoList(HttpServletRequest request) {
		return new ModelAndView("oct/activiti/process/copyto/copyToList");
	}
	
	 /**
		*功能描述：待阅事宜分页显示功能
		*@author 凡艺
		*@since 2016-08-16
		 */
	@RequestMapping(params = "copyTogrid")
	public void datagrid(OctActCopyTo toread,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(OctActCopyTo.class, dataGrid);
		TSUser user=ResourceUtil.getSessionUserName();
		cq.eq("user.id",user.getId());
		String status=request.getParameter("status");
		if(StringUtil.isNotEmpty(status)){
			cq.eq("status", status);
		}
		cq.add();
		//查询条件组装器
		//获取页数=1000时强制为10
		if(cq.getPageSize()==10000){
			cq.setPageSize(10);
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, toread,request.getParameterMap());
		this.commonService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	*功能描述：阅读跳转页面
	*@author 凡艺
	*@since 2016-08-23
	 */
	@RequestMapping(params = "editapp")
	public void editapp(OctActCopyTo octActCopyTo,HttpServletRequest request, HttpServletResponse response) {
		octActCopyTo=commonService.getEntity(OctActCopyTo.class, octActCopyTo.getId());
		ActTaskBusiness actTaskBusiness=commonService.getEntity(ActTaskBusiness.class, octActCopyTo.getActTaskBusiness().getId());
		String businessId=actTaskBusiness.getBusinessId();
		String procInstId=actTaskBusiness.getProcessInstanceId();
		String proDefId=actTaskBusiness.getProcessDefId();
		//添加阅读记录
		workflowService.addReadLog(businessId);
		octActCopyTo.setStatus("1");
		commonService.updateEntitie(octActCopyTo);
		try {
			response.sendRedirect("workFlowAutoFormController.do?viewContent=&op=view&formName="+actTaskBusiness.getFormName()+"&id="+businessId+"&procInstId="+procInstId+"&procDefId="+proDefId+"&hide=true");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		*功能描述：获取待阅事宜列表
		*@author 凡艺
		*@since 2016-08-23
		 */
		
	}
	@RequestMapping(params = "getlist")
	public ModelAndView getlist(HttpServletRequest request) {
		return new ModelAndView("oct/activiti/process/copyto/getList");
	}
}
