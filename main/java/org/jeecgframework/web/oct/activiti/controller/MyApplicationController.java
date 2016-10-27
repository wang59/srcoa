package org.jeecgframework.web.oct.activiti.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.cgform.entity.autoform.AutoFormEntity;
import org.jeecgframework.web.oct.activiti.entity.ActRuTask;
import org.jeecgframework.web.oct.activiti.entity.ActTaskBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Title: Controller
 * @Description: 我的申请
 * @author bin
 * @date 2016-06-24
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/myApplicationController")
public class MyApplicationController {

	private static final Logger logger = Logger.getLogger(MyApplicationController.class);

	@Autowired
	private CommonService commonService;

	/**
	 * 页面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "myApplication")
	public ModelAndView myApplication(HttpServletRequest request) {
		// 返回一个ModelAndView到myApplication页面
		return new ModelAndView("oct/activiti/process/application/myApplication");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "applicationList")
	public void applicationListById(ActTaskBusiness actTaskBusiness, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		// 创建一个对象，获取该对象的属性和方法
		CriteriaQuery cq = new CriteriaQuery(ActTaskBusiness.class, dataGrid);
		// 根据访问的用户获取用户Id来做权限控制
		String userName = ResourceUtil.getSessionUserName().getUserName();
		cq.eq("businessCreateBy", userName);
		cq.notEq("status", "0");
		cq.add();
		// 查询条件组装器
		//获取页数=1000时强制为10
		if(cq.getPageSize()==10000){
			cq.setPageSize(10);
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, actTaskBusiness, request.getParameterMap());
		// 将数据封装到EASYUI当中
		this.commonService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	 /**我的申请查看
	  * 
	  * @param actRuTask
	  * @param request
	  * @param response
	  */
	@RequestMapping(params = "read")
	public void applicationRead(ActTaskBusiness taskBusiness, HttpServletRequest request, HttpServletResponse response) {
		// 创建一个实体类对象
		String id = "";// ActRuTaskID
		String procInstId = "";// 流程实例ID
		String businessId = "";// 业务申请单ID
		String procDefId="";//流程定义ID
		// 通过request获取请求的ActRuTask 中的iD
		id = request.getParameter("id");
		// 通过ID来反射获取该实体类
		taskBusiness = this.commonService.get(ActTaskBusiness.class, id);
		// 通过实体类ActTaskBusiness来获取流程procInstId
		procInstId = taskBusiness.getProcessInstanceId();
		// 通过实体类ActTaskBusiness来获取流程procDefId
		procDefId=taskBusiness.getProcessDefId();
		// 通过H的特性来获取与该表关联的ActTaskBusiness实体类并获取其中的业务申请单ID
		businessId = taskBusiness.getBusinessId();
		try {
			response.sendRedirect(
					"workFlowAutoFormController.do?viewContent=&op=view&formName="+taskBusiness.getFormName()+"&taskId=" + id
							+ "&id=" + businessId + "&procInstId=" + procInstId+"&procDefId="+procDefId);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		} catch (Exception e) {
			logger.error("我的申请查询请求有误", e);
			e.printStackTrace();
			// TODO: handle exception
		}

	}

}
