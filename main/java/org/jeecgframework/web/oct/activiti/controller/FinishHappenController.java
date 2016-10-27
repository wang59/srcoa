package org.jeecgframework.web.oct.activiti.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.oct.activiti.entity.ActHiTaskinst;
import org.jeecgframework.web.oct.activiti.entity.ActRuTask;
import org.jeecgframework.web.oct.activiti.entity.ActTaskBusiness;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sun.star.util.Date;

/**
 * @Title: Controller
 * @Description: 已办事宜
 * @author bin
 * @date 2016-06-27
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/finishHappenController")
public class FinishHappenController {

	private static final Logger logger = Logger.getLogger(FinishHappenController.class);

	@Autowired
	private CommonService commonService;

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "finishHappen")
	public ModelAndView finish(HttpServletRequest request) {
		return new ModelAndView("oct/activiti/process/finish/finishHappen");
	}

	@RequestMapping(params = "finishHappenList")
	public void finishListById(ActHiTaskinst actHiTaskinst,
			HttpServletRequest request, HttpServletResponse response,DataGrid dataGrid) {
		// 创建一个对象，获取该对象的属性和方法
		CriteriaQuery cq = new CriteriaQuery(ActHiTaskinst.class, dataGrid);
		// 根据访问的用户信息获取访问的用户id
		String userName = ResourceUtil.getSessionUserName().getUserName();
		//assignee是ActHiTaskinst的属性
		cq.eq("assignee", userName);
		cq.add();
		// 查询条件组装器
		//获取页数=1000时强制为10
		if(cq.getPageSize()==10000){
			cq.setPageSize(10);
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, actHiTaskinst,
				request.getParameterMap());
		// 将数据封装到EASYUI当中
//        List<ActHiTaskinst> actHiTaskinsts=new ArrayList<ActHiTaskinst>();
		this.commonService.getDataGridReturn(cq, true);
//
//		for (Object o : dataGrid.getResults()) {
//			ActHiTaskinst cfe = (ActHiTaskinst) o;
//			if(cfe.getDuration()!=null && !"".equals(cfe.getDuration())){
//				Double  duration = Double.valueOf(cfe.getDuration())/36000;
//				  DecimalFormat df = new DecimalFormat("#.##");//格式化小数，不足的补0
//				   Double  filesize = Double.valueOf(df.format(duration));//返回的是String类型的
//				   cfe.setDuration(new Double(filesize).longValue());	
//				   
//			}
//			actHiTaskinsts.add(cfe);	
//		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 已办事宜查看
	 * 
	 * @param actRuTask
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "finishRead")
	public void applicationRead(ActHiTaskinst actHiTaskinst, 
			HttpServletRequest request,HttpServletResponse response) {
		// 创建一个实体类对象
		String id = "";// ActHiTaskinstId
		String procInstId = "";// 流程实例ID
		String businessId = "";// 业务申请单ID
		String procDefId="";
		// 通过request获取请求的ActRuTask 中的iD
		id = request.getParameter("id");
		// 通过ID来反射获取该实体类
		actHiTaskinst = this.commonService.get(ActHiTaskinst.class, id);
		// 通过实体类ActHiTaskinst来获取流程procInstId
		procInstId = actHiTaskinst.getProcInstId();
		//通过实体类ActHiTaskinst来获取流程procInstId
		procDefId=actHiTaskinst.getProcDefId();
		// 通过H的特性来获取与该表关联的ActTaskBusiness实体类并获取其中的业务申请单ID		
		ActTaskBusiness actTaskBusiness = this.commonService.findUniqueByProperty(ActTaskBusiness.class, "processInstanceId", procInstId);
		businessId=actTaskBusiness.getBusinessId();
		String formName=actTaskBusiness.getFormName();	
		try {
			response.sendRedirect(
					"workFlowAutoFormController.do?viewContent=&op=view&formName="+formName+"&taskId=" + id
							+ "&id=" + businessId + "&procInstId=" + procInstId+"&procDefId="+procDefId);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		} catch (Exception e) {
			logger.error("已办事宜查询请求有误", e);
			e.printStackTrace();
			// TODO: handle exception
		}

	}

}
