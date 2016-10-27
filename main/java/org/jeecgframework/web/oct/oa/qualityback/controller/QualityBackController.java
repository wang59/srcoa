package org.jeecgframework.web.oct.oa.qualityback.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.oct.activiti.entity.ActTaskBusiness;
import org.jeecgframework.web.oct.activiti.entity.ProcessClass;
import org.jeecgframework.web.oct.activiti.entity.TSProcess;
import org.jeecgframework.web.oct.oa.interfacemanagement.entity.DocInterfaceCatalogueEntity;
import org.jeecgframework.web.oct.oa.qualityback.entity.QualityBack;
import org.jeecgframework.web.oct.oa.qualityback.entity.QualityBackEntity;
import org.jeecgframework.web.oct.oa.qualityback.service.interf.QualityBackService;
import org.jeecgframework.web.oct.oa.qualityback.vo.QualityBackEntityVo;
import org.jeecgframework.web.onlinedocsort.entity.OnlineDocSortEntity;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/***
 * 质量反馈控制器
 * @author fany
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/qualityBackController")
public class QualityBackController {
	@Autowired
	private CommonService commonService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private QualityBackService qualityBackService;
	/**
	 *功能描述：质量反馈跳转页面
	 *@author 凡艺
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 *@since 2016-09-27
	 */
	@RequestMapping(params = "qualityBackList")
	public ModelAndView qualityBackList(HttpServletRequest request) throws IllegalAccessException, Exception {
		return new ModelAndView("oct/oa/qualityback/qualitybacklist");
	}
	/**
	 *功能描述：返回具体质量反馈表数据
	 *@author 凡艺
	 *@since 2016-06-22
	 */
	@RequestMapping(params = "qualityBackgrid")
	public void qualityBackgrid(QualityBack qualityBack,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(QualityBack.class, dataGrid);
		String type=request.getParameter("type");
		cq.eq("tSProcess.id", "4028d24a574f75ba015750ec60d9001d");
		if(StringUtil.isEmpty(type)){
			cq.eq("businessCreateBy", ResourceUtil.getSessionUserName().getUserName());
		}
		cq.notEq("status", "0");
		cq.notEq("status", "4");
		cq.add();
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, qualityBack,request.getParameterMap());
		this.commonService.getDataGridReturn(cq, true);
		List<QualityBack> list=dataGrid.getResults();
		for(QualityBack qualityBacks:list){
			if(qualityBacks.getStatus().equals("1")){
				List<Task> tasklist=taskService.createTaskQuery().processInstanceId(qualityBacks.getProcessInstanceId()).list();
				String assign="";
				for(Task task:tasklist){
					List<TSUser> userList=systemService.findByProperty(TSUser.class, "userName", task.getAssignee());
					if(userList!=null&&userList.size()==1){
					assign+=userList.get(0).getRealName()+",";
					}
				}
				qualityBacks.setCurrentTodo(assign);
			}
		}
		
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 *功能描述：质量反馈查询跳转页面
	 *@author 凡艺
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 *@since 2016-09-27
	 */
	@RequestMapping(params = "qualityBackListAll")
	public ModelAndView qualityBackListAll(HttpServletRequest request) throws IllegalAccessException, Exception {
		return new ModelAndView("oct/oa/qualityback/qualitybacklistAll");
	}
	/**
	 *功能描述：获取businessid
	 *@author 凡艺
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 *@since 2016-09-27
	 */
	@RequestMapping(params = "getid")
	@ResponseBody
	public AjaxJson getid(HttpServletRequest request,String id) throws IllegalAccessException, Exception {
		AjaxJson j=new AjaxJson();
		QualityBack qualityBack=commonService.getEntity(QualityBack.class, id);
		String eid=qualityBack.getQualityBackEntity().getId();
		j.setMsg(eid);
		return j;
	}
	/**
	 *功能描述：质量反馈单报表跳转页面
	 *@author 凡艺
	 *@since 2016-09-28
	 */
	@RequestMapping(params = "qualityBackListform")
	public ModelAndView qualityBackListform(HttpServletRequest request) throws IllegalAccessException, Exception {
		return new ModelAndView("oct/oa/qualityback/qualitybacklistform");
	}
	/**
	 *功能描述：质量反馈单报表数据
	 *@author 凡艺
	 *@since 2016-09-28
	 */
	@RequestMapping(params = "formgrid")
	public void processAgentgrid(QualityBackEntity qualityBackEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(QualityBackEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, qualityBackEntity,request.getParameterMap());
		this.commonService.getDataGridReturn(cq, true);
		List<QualityBackEntity> list=dataGrid.getResults();
		dataGrid.setResults(qualityBackService.getData(list));
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
		
		List<QualityBackEntity> list=commonService.getList(QualityBackEntity.class);
		List<QualityBackEntityVo> volist=qualityBackService.getData(list);
		modelMap.put(NormalExcelConstants.FILE_NAME,"用户表");
		modelMap.put(NormalExcelConstants.CLASS,QualityBackEntityVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("质量反馈单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,volist);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
}
