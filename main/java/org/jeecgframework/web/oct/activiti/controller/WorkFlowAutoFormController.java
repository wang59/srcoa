package  org.jeecgframework.web.oct.activiti.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.DynamicDBUtil;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.RequestUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.cgform.entity.autoform.*;
import org.jeecgframework.web.cgform.service.autoform.AutoFormDbServiceI;
import org.jeecgframework.web.cgform.service.autoform.AutoFormServiceI;
import org.jeecgframework.web.cgform.util.AutoFormCommUtil;
import org.jeecgframework.web.cgform.util.AutoFormTemplateParseUtil;
import org.jeecgframework.web.oct.activiti.entity.ActApproveLog;
import org.jeecgframework.web.oct.activiti.entity.ActRuTask;
import org.jeecgframework.web.oct.activiti.entity.ActTaskBusiness;
import org.jeecgframework.web.oct.activiti.entity.OctActRuAssign;
import org.jeecgframework.web.oct.activiti.entity.TSProcess;
import org.jeecgframework.web.oct.activiti.entity.UserOpinion;
import org.jeecgframework.web.oct.activiti.service.interf.CustomFormService;
import org.jeecgframework.web.oct.activiti.service.interf.ProcessAuthorityService;
import org.jeecgframework.web.oct.activiti.service.interf.WorkflowService;
import org.jeecgframework.web.system.pojo.base.DynamicDataSourceEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.DynamicDataSourceServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.util.IMServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**   
 * @Title: Controller
 * @Description: 创建、提交、转交等流程操作
 * @author 汪旭军
 * @date 2016-06-16
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/workFlowAutoFormController")
public class WorkFlowAutoFormController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WorkFlowAutoFormController.class);
	
	@Autowired
	private AutoFormServiceI autoFormService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ProcessAuthorityService processAuthorityService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private AutoFormDbServiceI autoFormDbService;
	@Autowired
	private DynamicDataSourceServiceI dynamicDataSourceServiceI;

	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private CustomFormService customFormService;

	/**
	 *功能描述：打开流程表单,包含新建，提交，更新，查看等。
	 *@author 汪旭军
	 *@since 2016-06-16
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "viewContent")
	public ModelAndView viewContent(AutoFormEntity autoForm, HttpServletRequest req) {
		String message = "";	
		boolean isMobile=RequestUtil.requestlsMobile(req);//是否移动端的访问。
		req.setAttribute("currentUser", ResourceUtil.getSessionUserName().getUserName());
		try {
			//获取参数
			Map<String, String[]> tem = req.getParameterMap();
			Map<String,Object> paramMap = new HashMap<String, Object>();
			String formName= req.getParameter("formName");
			String id = req.getParameter("id");
			for(String key :tem.keySet()){
				paramMap.put(key, tem.get(key));
			}
			//申请单是否是JSP页面；
			boolean isJSP=true;
			if(formName.toLowerCase().indexOf(".jsp")==-1){
				isJSP=false;
			}
			String op = req.getParameter("op");
			if(StringUtil.isEmpty(op)){
				op = AutoFormTemplateParseUtil.OP_VIEW;
			}
			//设置显示到页面的属性
			setAttributeToReq(req);
			//流程定义/绑定流程记录的ID
			String proid=req.getParameter("proid");
			TSProcess tSProcess=getTSProcess(proid,id);//流程定义/绑定流程记录的对象
			proid=tSProcess.getId();
			String proName=tSProcess.getName();
			req.setAttribute("proName", proName);
			req.setAttribute("proid", proid);
			Map<String, List<Map<String, Object>>> paras = new HashMap<String, List<Map<String, Object>>>();
			if(!isJSP){
				//自定义表单，获取配置信息			
				if(StringUtils.isNotBlank(autoForm.getFormName())){
					autoForm = this.systemService.findUniqueByProperty(AutoFormEntity.class, "formName", autoForm.getFormName());
					if(autoForm==null){
						return new ModelAndView("jeecg/cgform/autoform/autoForm-error").addObject("message", "表单信息获取失败！");
					}
				}else{
					return new ModelAndView("jeecg/cgform/autoform/autoForm-error").addObject("message", "formName不能为空！");
				}
				//根据formid查询表单的参数
				List<String> paramList = getFormParams(autoForm.getId());
				if(paramList!=null&&paramList.size()>0){
					for(String param:paramList){
						String paramValue = req.getParameter(param);
						if(paramValue==null){
							return new ModelAndView("jeecg/cgform/autoform/autoForm-error").addObject("message", "缺少表单参数："+param);
						}
					}
				}
				req.setAttribute("formName", autoForm.getFormName());
				paras = getFormData(autoForm,paramMap);
			}else{
				//JSP页面处理
				req.setAttribute("isJSP", "Y");
				req.setAttribute("formName", tSProcess.getForm());
				//根据ID取申请的数据，并返回页面；
				Map<String,String> appData =new HashMap<String,String>();
				appData=getFormDataForJSP(req.getParameter("tableName"),id);
				req.setAttribute("ds", appData);
			}
			req.setAttribute("param", paramMap);
			String ftlContent = autoForm.getFormParse();
			// 1.获取表单数据
			if(AutoFormTemplateParseUtil.OP_VIEW.equals(op)){
				// 2.解析ueditor html 注入数据【预览查看模式】
				ftlContent = AutoFormTemplateParseUtil.parseHtmlForView(ftlContent,paras);
			}else if(AutoFormTemplateParseUtil.OP_ADD.equals(op)){
				// 2.解析ueditor html 注入数据【添加提交模式】
				if(!isJSP){
					//解析自定义表单
					ftlContent = AutoFormTemplateParseUtil.parseHtmlForAdd(ftlContent,paras);
					req.setAttribute("formContent", ftlContent);
				}
				req.setAttribute("op", op);

				//获取流程描述
				String workflowName=req.getParameter("workflowName");
				ProcessDefinition  processDefinition=workflowService.getProcessDefinitionByKey(workflowName);
				String descript=processDefinition.getDescription();
				if(StringUtil.isNotEmpty(descript)&&descript.contains("document")){
					String document=JSONHelper.json2String(descript, "document");
					req.setAttribute("document", document);
				}
				//返回新建流程填写申请单界面
				if(isMobile){
					return new ModelAndView("oct/activiti/process/appform/m-app-add");
				}else{
					return new ModelAndView("oct/activiti/process/appform/app-add");
				}
			}else if("print".equals(op)){
				//打印功能
				ftlContent = AutoFormTemplateParseUtil.parseHtmlForAdd(ftlContent,paras);
				req.setAttribute("formContent", ftlContent);
				req.setAttribute("op", op);
				return new ModelAndView("oct/activiti/process/appform/print");
			}else if("adminupdate".equals(op)){
				if(StringUtil.isEmpty(id)){
					throw new BusinessException("参数id不能为空！");
				}

				// 2.解析ueditor html 注入数据【更新提交模式】
				ftlContent = AutoFormTemplateParseUtil.parseHtmlForView(ftlContent,paras);//默认使用阅读模式打开审批的申请单。
				req.setAttribute("formContent", ftlContent);
				req.setAttribute("op", op);	
				
				if(isMobile){
					return new ModelAndView("oct/activiti/process/processMonitor/m-app-approve-edit");
				}else{
					return new ModelAndView("oct/activiti/process/processMonitor/app-approve-edit");
				}
			}else if(AutoFormTemplateParseUtil.OP_UPDATE.equals(op)){
				if(StringUtil.isEmpty(id)){
					throw new BusinessException("参数id不能为空！");
				}

				// 2.解析ueditor html 注入数据【更新提交模式】
				ftlContent = AutoFormTemplateParseUtil.parseHtmlForView(ftlContent,paras);//默认使用阅读模式打开审批的申请单。
				req.setAttribute("formContent", ftlContent);
				req.setAttribute("op", op);
				String type=req.getParameter("type");
				if(StringUtil.isNotEmpty(type)&&type.equals("update")){
					String workflowName=req.getParameter("workflowName");
					req.setAttribute("workflowName", workflowName);
					//获取流程描述
					ProcessDefinition  processDefinition=workflowService.getProcessDefinitionByKey(workflowName);
					String descript=processDefinition.getDescription();
					if(StringUtil.isNotEmpty(descript)&&descript.contains("document")){
						String document=JSONHelper.json2String(descript, "document");
						req.setAttribute("document", document);
					}

					return new ModelAndView("oct/activiti/process/createprocess/drafts-update");
				}

				//按钮权限控制
				String taskid=req.getParameter("taskId");	
				ActRuTask actRuTask=commonService.getEntity(ActRuTask.class, taskid);
				
				String assit=actRuTask.getDelegation();//协办
				String applicant=actRuTask.getTaskDefKey();//发起人
				if(applicant.equals("applicant")||StringUtil.isNotEmpty(assit)&&assit.equals("PENDING")){
					//协办则按钮不显示
					req.setAttribute("isAllowTurnToDo", false);
					req.setAttribute("isAllowAssit", false);
					req.setAttribute("isAllowTurnBack", false);
					req.setAttribute("isAllowReject", false);
					req.setAttribute("changname", "提交");
				}else{
					if(!(processAuthorityService.isAllowTurnToDo(taskid))){

						req.setAttribute("isAllowTurnToDo", false);
					}
					if(!(processAuthorityService.isAllowAssit(taskid))){
						req.setAttribute("isAllowAssit", false);
					}
					if(!(processAuthorityService.isAllowPrint(taskid, null, null))){
						req.setAttribute("isAllowPrint", false);
					}
					if(!(processAuthorityService.isAllowTurnBack(taskid))){
						req.setAttribute("isAllowTurnBack", false);
					}
					if(!processAuthorityService.isAllowReject(taskid)){
						req.setAttribute("isAllowReject", false);
					}
				}
				
				//待办属性显示在页面
				req.setAttribute("flow_NodeName",actRuTask.getName());
				req.setAttribute("flow_NodeId",actRuTask.getTaskDefKey());
				//返回审批申请单界面
				
				if(isMobile){
					return new ModelAndView("oct/activiti/process/appform/m-app-approve-edit");
				}else{
					return new ModelAndView("oct/activiti/process/appform/app-approve-edit");
				}
			}else if(AutoFormTemplateParseUtil.OP_ADD_OR_UPDATE.equals(op)){
				// 2.解析ueditor html 注入数据【智能提交模式】
				ftlContent = AutoFormTemplateParseUtil.parseHtmlForAddOrUpdate(ftlContent,paras);
				req.setAttribute("formContent", ftlContent);
				req.setAttribute("op", op);
				return new ModelAndView("jeecg/cgform/autoform/autoForm-review-addorupdate");
			}

			//根据businessid查询审批记录
			String hql="from ActApproveLog ac where ac.businessId=? order by createTime desc";
			String businessid=req.getParameter("id");
			List<ActApproveLog> list=commonService.findHql(hql, businessid);	
			TSUser user = ResourceUtil.getSessionUserName();
			String procDefId=req.getParameter("procDefId");
			//如果该表单的审批记录数量小于2条，表示只有一个审批人进行操作，不能取回
			if(list!=null&&list.size()>1)
			{
				//根据businessId获取审批记录表的最新记录
				ActApproveLog log=list.get(0);
				String username=log.getApproverId();
				String activityid=list.get(0).getNodeId();				
				if(user.getUserName().equals(username)&&log.getAction().equals("提交"))
				{
					if(!(processAuthorityService.isAllowGetBack(activityid, procDefId))){
						req.setAttribute("isAllowGetBack", false);
					}				
				}else{
					req.setAttribute("isAllowGetBack", false);
				}

			}else{
				req.setAttribute("isAllowGetBack", false);
			}
			//取回结束
			//打印按钮权限控制
			String hql2="from ActApproveLog ac where ac.businessId=? and approverId=?";
			List<ActApproveLog> printlist=commonService.findHql(hql2, businessid,user.getUserName());	
			for(ActApproveLog actApproveLog:printlist)
			{
				String activityid=actApproveLog.getNodeId();
				if(!(processAuthorityService.isAllowPrint(null,activityid, procDefId))){
					req.setAttribute("isAllowPrint", false);
					break;
				}	
			}
			req.setAttribute("formContent", ftlContent);
			req.setAttribute("op", op);
			return new ModelAndView("oct/activiti/process/appform/app-review");
		} catch (Exception e) {
			e.printStackTrace();
			message = "表单添加失败："+e.getMessage();
		} 
		return new ModelAndView("jeecg/cgform/autoform/autoForm-error").addObject("message", message);
	}

	/**
	 *功能描述：设置显示到页面的属性。
	 *@author 汪旭军
	 *@since 2016-09-27
	 */
	public void setAttributeToReq(HttpServletRequest req){
		TSUser curUser=ResourceUtil.getSessionUserName();//获取当前用户对象
		req.setAttribute("sys_curUserID", curUser.getId());
		req.setAttribute("sys_curUserName", curUser.getUserName());
		req.setAttribute("sys_curRealName", curUser.getRealName());
		//附件服务器的地址，如：http://192.168.82.202
		req.setAttribute("sys_fileServer", ResourceUtil.getConfigByName("fileServer"));
	}
	
	/**
	 *功能描述：根据绑定流程的id，或者申请单的id获取绑定流程对象。有一个有值就可以。
	 *@author 汪旭军
	 *@since 2016-08-31
	 */
	public TSProcess getTSProcess(String proid,String id){
		TSProcess tSProcess=null;
		if(StringUtil.isNotEmpty(proid)){
			tSProcess=commonService.getEntity(TSProcess.class, proid);
		}else{
			ActTaskBusiness actTaskBusiness=(ActTaskBusiness) commonService.findByProperty(ActTaskBusiness.class, "businessId", id).get(0);
			tSProcess=commonService.getEntity(TSProcess.class, actTaskBusiness.getTSProcess().getId());
		}		
		return tSProcess;
	}


	/**
	 *功能描述：先保存数据，然后启动流程。适合自定义表单
	 *@author 汪旭军
	 *@since 2016-06-16
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "start")
	@ResponseBody
	public AjaxJson addForm(HttpServletRequest request) {
		System.out.println("开始提交流程");
		AjaxJson j = new AjaxJson();
		String message = "";
		try{
			Map paramData = request.getParameterMap();
			String workflowName=request.getParameter("workflowName");
			String formName = request.getParameter("formName");
			String curUserName=ResourceUtil.getSessionUserName().getUserName();//当前登录用户
			String dataSourceKey="ds";
			String tableName=request.getParameter("tableName");//表名
			//流程定义/绑定流程记录的ID
			String proid=request.getParameter("proid");
			String businessId=request.getParameter("param.id");
			Map<String,Object> processVariables;//流程变量，用于判断流程的走向。
			if(paramData!=null){
				Map<String,Map<String,Object>> dataMap = AutoFormCommUtil.mapConvert(paramData);
								
				//添加流水号
				String saveSerialnumber=workflowService.addSerialNumber(dataMap, formName,proid,businessId);
				// 获取参数
				Map<String, Object> param = dataMap.get("param");
				
				//获取原始数据
				Map<String, List<Map<String, Object>>> oldDataMap = new HashMap<String, List<Map<String, Object>>>();
				//inert或者update数据。 
				String id ="";
				AutoFormEntity autoForm =null;
				if(formName.toLowerCase().indexOf(".jsp")==-1){
					 autoForm = this.systemService.findUniqueByProperty(AutoFormEntity.class, "formName", formName);
					oldDataMap = getFormData(autoForm,param);
					dataSourceKey=autoForm.getMainTableSource();
					id = this.autoFormService.doUpdateTable(formName,dataMap,oldDataMap);
				}else{
					oldDataMap = null;
					id = this.autoFormService.doUpdateTableForJSP(tableName,dataMap,oldDataMap);
				}
				j.setObj(id);
				//获取流程变量
				processVariables=workflowService.processVariables(workflowName, curUserName,dataSourceKey,dataMap,request);
				//启动流程		
				ProcessInstance ProcessInstance;
				String type=request.getParameter("type");
				//判断是直接提交还是草稿箱提交
				if(StringUtil.isNotEmpty(type)){
					ActTaskBusiness actTaskBusiness=systemService.findByProperty(ActTaskBusiness.class, "businessId", id).get(0);
					String actTaskBusinessid=actTaskBusiness.getId();
					ProcessInstance=workflowService.startWorkflow(actTaskBusinessid,workflowName,processVariables,actTaskBusinessid,proid,saveSerialnumber);
				}else{
					ProcessInstance=workflowService.startWorkflow(id,workflowName,processVariables,null,proid,saveSerialnumber);
				}
				if(ProcessInstance!=null){
					List<String> list=workflowService.getCopyTo(null, processVariables, workflowName);
					for(String ids:list){
						workflowService.addCopyTo(ids, ProcessInstance.getId());
					}
					message = "流程提交成功";
				}else{
					message = "流程提交失败";
				}
			}

		}catch(Exception e){
			e.printStackTrace();
			j.setSuccess(false);
			message = "流程提交失败："+e.getMessage();
		}
		j.setMsg(message);
		return j;
	}


	/**
	 *功能描述：提交申请
	 *@author 汪旭军
	 *@since 2016-06-29
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "updateForm")
	@ResponseBody
	public AjaxJson updateForm(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "表单提交成功";
		try{
			Map paramData = request.getParameterMap();
			String formName = request.getParameter("formName");//表名
			String taskId=request.getParameter("taskId");//任务/待办ID
			String businessId=request.getParameter("param.id");//申请单ID
			String curNodeName ="";//当前审批节点的名称
			String curNodeId = "";//当前审批节点的ID
			String curUser=ResourceUtil.getSessionUserName().getUserName();//当前用户
			Map<String,Object> processVariables;//流程变量，用于判断流程的走向。
			if(paramData!=null){
				Map<String,Map<String,Object>> dataMap = AutoFormCommUtil.mapConvert(paramData);
				// 获取参数
				Map<String, Object> param = dataMap.get("param");	
				AutoFormEntity autoForm = this.systemService.findUniqueByProperty(AutoFormEntity.class, "formName", formName);
				//获取原始数据		
				Map<String, List<Map<String, Object>>> oldDataMap = new HashMap<String, List<Map<String, Object>>>();
				oldDataMap = getFormData(autoForm,param);
				this.autoFormService.doUpdateTable(formName,dataMap,oldDataMap);			
				if(taskId==null){
					taskId=request.getParameter("param.taskId");
				}

				//获取审批记录信息
				String comment=request.getParameter("comment");
				ActRuTask actRuTask =new ActRuTask();
				actRuTask= commonService.get(ActRuTask.class, taskId);
				if (actRuTask != null && actRuTask.getAssignee().indexOf(curUser)>-1) {
					curNodeName = actRuTask.getName();
					curNodeId = actRuTask.getTaskDefKey();
					String proDef=actRuTask.getProcDefId();
					String proince=actRuTask.getProcInstId();
					//获取流程变量
					ActTaskBusiness actTaskBusiness=systemService.findUniqueByProperty(ActTaskBusiness.class, "businessId",businessId);
					processVariables=workflowService.activitiVariables(taskId, actTaskBusiness.getBusinessCreateBy(), autoForm.getMainTableSource(), dataMap,request);
					if(actRuTask.getDelegation()!=null && actRuTask.getDelegation().equals("PENDING")){
						//协办处理人办理
						taskService.resolveTask(taskId);
						// 保存审批记录
						workflowService.addActApproveLog(businessId, curNodeName, curNodeId, "协办", comment);
					}else{
						// 提交任务
						workflowService.completeTask(taskId, processVariables);
						// 保存审批记录
						workflowService.addActApproveLog(businessId, curNodeName, curNodeId, "提交", comment);
						//判断是否流程最后一个节点
						ProcessInstance instance=runtimeService.createProcessInstanceQuery().processInstanceId(proince).singleResult();
						if(instance==null){				
							workflowService.getEndCopyTo(proDef,proince);
							actTaskBusiness.setStatus("3");
							commonService.updateEntitie(actTaskBusiness);
						}
					}
				} else {
					message = "提交失败，待办任务：" + taskId + " 不存在，或者您不是任务的处理人。";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			j.setSuccess(false);
			message = "表单提交失败："+e.getMessage();
		}
		j.setMsg(message);
		return j;
	}


	/**
	 *功能描述：显示审批的记录
	 *@author 汪旭军
	 *@since 2016-06-23
	 */
	@RequestMapping(params = "showApproveLog")
	public ModelAndView showApproveLog(HttpServletRequest request) {
		request.setAttribute("businessId", request.getParameter("businessId"));
		return new ModelAndView("oct/activiti/process/appform/show-approve-log");
	}

	@RequestMapping(params = "datagridlog")
	public void datagrid2(ActApproveLog actApproveLog,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ActApproveLog.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, actApproveLog,request.getParameterMap());
		this.commonService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 *功能描述：显示审批的记录
	 *@author 凡艺
	 *@since 2016-07-7
	 */
	@RequestMapping(params = "showApproveLogajax")
	@ResponseBody
	public AjaxJson showApproveLogajax(String businessid,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		List<ActApproveLog> list=systemService.findByProperty(ActApproveLog.class, "businessId", businessid);
		if(list!=null&&list.size()==1)
		{
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
		}
		return j;
	}
	/**
	 *功能描述：显示审批的记录
	 *@author 凡艺
	 *@since 2016-07-7
	 */
	@RequestMapping(params = "ApproveLogajax")
	@ResponseBody
	public AjaxJson ApproveLogajax(String businessid,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		String hql="from ActApproveLog where businessId=? order by createTime";
		List<ActApproveLog> list=systemService.findHql(hql, businessid);		
		LinkedHashMap map=new LinkedHashMap();
		for(ActApproveLog log:list){
			map.put(log.getId(), log);
		}
		j.setAttributes(map);
		return j;
	}
	/**
	 *功能描述：转办跳转页面
	 *@author 凡艺
	 *@since 2016-06-27
	 */
	@RequestMapping(params = "changApproverselect")
	public ModelAndView select(HttpServletRequest request) {
		return new ModelAndView("oct/activiti/process/createprocess/changApprover");			
	}
	/**
	/**
	 *功能描述：转办
	 *@author 汪旭军
	 *@since 2016-06-27
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "changApprover")
	@ResponseBody
	public AjaxJson changApprover(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String userId = request.getParameter("userId");
		String taskId=request.getParameter("taskId");
		String comment=request.getParameter("comment");
		String businessid=request.getParameter("businessid");
		TaskEntity task=(TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		ActApproveLog act=new ActApproveLog();
		TSUser user = ResourceUtil.getSessionUserName();
		act.setAction("转办");
		act.setApproverId(user.getUserName());
		act.setApproverName(user.getRealName());
		act.setBusinessId(businessid);
		TSUser changeuser=commonService.findUniqueByProperty(TSUser.class, "userName", userId);
		act.setComment(comment+"转给:"+changeuser.getRealName());
		Date date1 = new Date();//获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date1);//时间存储为字符串	
		act.setCreateTime(Timestamp.valueOf(str));
		act.setNodeId(task.getTaskDefinitionKey());
		act.setNodeName(task.getName());
		systemService.save(act);
		workflowService.transferAssignee(taskId, userId);
		String message = "转办成功";		
		j.setMsg(message);
		return j;
	}

	/**
	 * 获取表单数据
	 * @param formName
	 * @param paramMap
	 * @return
	 */
	private Map<String, List<Map<String, Object>>> getFormData(AutoFormEntity autoForm,Map<String,Object> paramMap){
		String message = "";
		//装载数值的容器
		Map<String, List<Map<String, Object>>> paras = new HashMap<String, List<Map<String, Object>>>();
		List<AutoFormDbEntity> formDbList = this.systemService.findByProperty(AutoFormDbEntity.class, "autoFormId", autoForm.getId());
		for(AutoFormDbEntity formDb: formDbList){
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> typeList = getColumnTypes(formDb.getTbDbTableName(),formDb.getDbKey());

			if("table".equals(formDb.getDbType())){
				//如果数据源类型为表类型，通过属性表里面的属性拼出SQL
				String hqlField = "from AutoFormDbFieldEntity where 1 = 1 AND aUTO_FORM_DB_ID = ? ";
				try{
					List<AutoFormDbFieldEntity> autoFormDbFieldEntityList = systemService.findHql(hqlField,formDb.getId());

					if(autoFormDbFieldEntityList.size()>0){
						StringBuffer hqlTable = new StringBuffer().append("select ");
						for(AutoFormDbFieldEntity autoFormDbFieldEntity:autoFormDbFieldEntityList){
							//author:jg_renjie----start-----date:20160228--------for：TASK #704 【表单填报预览】针对特殊类型数据，需要进行转换，比如blob
							boolean flag = false;
							for(Map<String,Object> typeMap:typeList){
								String dataType = typeMap.get("dataType").toString().toUpperCase();
								String columnNm = typeMap.get("columnNm").toString().toUpperCase();

								if(dataType.contains("BLOB") && columnNm.equals(autoFormDbFieldEntity.getFieldName().toUpperCase())){
									hqlTable.append("CONVERT(GROUP_CONCAT("+autoFormDbFieldEntity.getFieldName()+") USING utf8) as "+autoFormDbFieldEntity.getFieldName()+",");
									flag = true;
								} 
							}
							if(!flag){
								hqlTable.append(autoFormDbFieldEntity.getFieldName()+",");
							}
							//author:jg_renjie----end-----date:20160228--------for：TASK #704 【表单填报预览】针对特殊类型数据，需要进行转换，比如blob

						}
						hqlTable.deleteCharAt(hqlTable.length()-1).append(" from "+formDb.getDbTableName());
						String id = "";
						Object value = paramMap.get("id");
						if(value instanceof String[]){
							String[] paramValue=(String[])value;
							id = paramValue[0];
						}else{
							id = value.toString();
						}
						hqlTable.append(" where ID ='").append(id).append("'");

						//update-start--Author:luobaoli  Date:20150701 for：如果数据源为空，那么以当前上下文中的DB配置为准，查询出表数据
						if("".equals(formDb.getDbKey())){
							//当前上下文中的DB环境，获取数据库表中的所有数据
							data = systemService.findForJdbc(hqlTable.toString());
						}
						//update-end--Author:luobaoli  Date:20150701 for：如果数据源为空，那么以当前上下文中的DB配置为准，查询出表数据
						else{
							DynamicDataSourceEntity dynamicDataSourceEntity = dynamicDataSourceServiceI.getDynamicDataSourceEntityForDbKey(formDb.getDbKey());
							if(dynamicDataSourceEntity!=null){
								data = DynamicDBUtil.findList(formDb.getDbKey(),hqlTable.toString());
							}
						}

					}else{
						message = "表属性配置有误！";
						throw new BusinessException(message);
					}
					paras.put(formDb.getDbName(), data);
				}catch(Exception e){
					logger.info(e.getMessage());
				}
			}else if("sql".equals(formDb.getDbType())){
				//如果数据源类型为SQL类型，直接通过替换SQL里面的参数变量解析出可执行的SQL
				String dbDynSql = formDb.getDbDynSql();
				List<String> params = autoFormDbService.getSqlParams(dbDynSql);
				for(String param:params){
					Object value = paramMap.get(param);
					if(value instanceof String[]){
						String[] paramValue=(String[])value;
						dbDynSql = dbDynSql.replaceAll("\\$\\{"+param+"\\}", paramValue[0]);
					}else{
						String paramValue= value.toString();
						dbDynSql = dbDynSql.replaceAll("\\$\\{"+param+"\\}", paramValue);
					}
				}

				//判断sql中是否还有没有被替换的变量，如果有，抛出错误！
				if(dbDynSql.contains("\\$")){
					message = "动态SQL数据查询失败！";
					throw new BusinessException(message);
				}else{
					try {
						data = systemService.findForJdbc(dbDynSql);
					} catch (Exception e) {
						logger.info(e.getMessage());
						message = "动态SQL数据查询失败！";
						throw new BusinessException(message);
					}

				}
				paras.put(formDb.getDbName(), formatData(typeList,data));
			}else{
				//预留给CLAZZ类型
			}
		}
		return  paras;
	}

	
	/**
	 * 获取表单数据
	 * @param tableName 表名
	 * @param idValue id值 
	 * @return
	 */
	private Map<String,String> getFormDataForJSP(String tableName,String idValue){
		String message = "";
		//装载数值的容器
		Map<String, String> paras = new HashMap<String, String>();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> typeList = getColumnTypes(tableName,null);
		//如果数据源类型为SQL类型，直接通过替换SQL里面的参数变量解析出可执行的SQL
		String dbDynSql = "select * from "+tableName+" where id='"+idValue+"'";
		try {
			data = systemService.findForJdbc(dbDynSql);
		} catch (Exception e) {
			logger.info(e.getMessage());
			message = "动态SQL数据查询失败！";
			throw new BusinessException(message);
		}
		List<Map<String,Object>> formatData =formatData(typeList,data);
		for(Map<String,Object> tempMap:formatData){
			Entry<String, Object> entry= null;
			Iterator<Entry<String, Object>> it = tempMap.entrySet().iterator();
			while(it.hasNext()){
				entry = (Entry<String, Object>) it.next();
				if(StringUtil.isNotEmpty(entry.getValue())){
					paras.put(entry.getKey().toString(),entry.getValue().toString());
				}else{
					paras.put(entry.getKey().toString(),"");
				}
			}
		}
		//paras.put("ds", paraData);
		return  paras;
	}
	
	
	/**
	 * 根据表名获取各个字段的类型
	 * @param dbTableNm
	 * @return
	 */
	//author:jg_renjie----start-----date:20160228--------for：TASK #704 【表单填报预览】针对特殊类型数据，需要进行转换，比如blob
	private List<Map<String, Object>> getColumnTypes(String dbTableNm,String dbkey){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select  DATA_TYPE as dataType,COLUMN_NAME as columnNm from information_schema.COLUMNS where TABLE_NAME='"+dbTableNm+"'";
		if(StringUtils.isNotBlank(dbkey)){
			list= DynamicDBUtil.findList(dbkey,sql);
		} else {
			list  = systemService.findForJdbc(sql);
		}
		return list;
	} 

	/**
	 * 格式化BLOB类型列的数据，将其设置为UTF-8格式，其他的字段的数据不处理。
	 * @param typeList 数据类型列表
	 * @param data   数据列表。
	 * @return
	 */
	private List<Map<String, Object>> formatData(List<Map<String, Object>> typeList,List<Map<String, Object>> data){
		for(Map<String, Object> hashmap:data){

			java.util.Map.Entry entry = null;

			Iterator it = hashmap.entrySet().iterator();
			while(it.hasNext()){
				entry = (java.util.Map.Entry)it.next();

				for(Map<String,Object> typeMap:typeList){
					String dataType = typeMap.get("dataType").toString().toUpperCase();
					String columnNm = typeMap.get("columnNm").toString().toUpperCase();

					if(dataType.contains("BLOB") && columnNm.equals(entry.getKey().toString().toUpperCase())){

						String srt2;
						try {
							srt2 = new String((byte[])entry.getValue(),"UTF-8");
							hashmap.put(entry.getKey().toString(), srt2);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}

				}
			}
		}
		return data;
	}


	private List<String> getFormParams(String fromId){
		List<String> paramList = new ArrayList<String>();
		List<AutoFormDbEntity> formDbList = this.systemService.findByProperty(AutoFormDbEntity.class, "autoFormId", fromId);
		//2.循环dbList,如果AutoFormDbEntity是sql类型，则将该sql下的param添加到paramList中
		for(AutoFormDbEntity dbEntity:formDbList){
			if("sql".equals(dbEntity.getDbType())){
				List<AutoFormParamEntity> params = this.systemService.findByProperty(AutoFormParamEntity.class, "autoFormDbId", dbEntity.getId());
				if(params.size()>0){
					//2.1 添加的参数去重，如果之前添加的有则不添加
					for(AutoFormParamEntity entity:params){
						if(!paramList.contains(entity.getParamName())){
							paramList.add(entity.getParamName());
						}
					}
				}
			}else if("table".equals(dbEntity.getDbType())){
				paramList.add("id");
			}
		}
		return paramList;
	}

	/**
	 *功能描述：协办，打开填写协办页面
	 *@author 汪旭军
	 *@since 2016-06-30
	 */
	@RequestMapping(params = "assitAdd")
	public ModelAndView assitAdd(HttpServletRequest request) {
		return new ModelAndView("oct/activiti/process/createprocess/task-assit-add");			
	}

	/**
	 *功能描述：协办，保存协办任务
	 *@author 汪旭军
	 *@since 2016-06-30
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "assitSave")
	@ResponseBody
	public AjaxJson assitSave(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message="";
		//获取传递过来的相关的参数
		String usernameView =request.getParameter("userNames");//协办处理人姓名
		String userIdAll = request.getParameter("userId");//协办处理人账号
		String taskId=request.getParameter("taskId");
		String businessid=request.getParameter("businessid");
		String comment=request.getParameter("comment");

		//当前登录用户
		String curUser=ResourceUtil.getSessionUserName().getUserName();//获取当前用户账号;
		//获取当前的待办任务
		ActRuTask actRuTask =new ActRuTask();
		actRuTask= commonService.get(ActRuTask.class, taskId);
		if (actRuTask != null && userIdAll !=null && !userIdAll.equals("")) {
			if(workflowService.isCurrentUserTask(actRuTask)){
				//增加协办的记录
				String[] arrUserId=userIdAll.split(",");
				for(String userId:arrUserId){
					OctActRuAssign octActRuAssign =new OctActRuAssign();
					octActRuAssign.setAssignee(userId);
					octActRuAssign.setOwner(curUser);
					octActRuAssign.setTaskId(taskId);
					//保存
					systemService.save(octActRuAssign);

					//委托任务给用户userId处理
					taskService.delegateTask(taskId, userId);
					workflowService.addActApproveLog(businessid, actRuTask.getName(), actRuTask.getTaskDefKey(), "发出协办", comment+" 协办人员："+usernameView);
					/*						//创建子任务
						Task task=taskService.newTask();
						task.setAssignee(userId);
						task.setOwner(actRuTask.getAssignee());
						task.setParentTaskId(taskId);
						task.setName(actRuTask.getName()+"(协办)");
						task.setDescription(comment);
						taskService.saveTask(task);*/
					message = "已经发送任务给协办人";		
				}
			}else{
				message = "您不是任务：" + taskId + " 的审批人，不允许发起协办。";
			}
		} else {
			message = "操作失败，协助处理人为空，或者待办任务：" + taskId + " 不存在。";
		}
		j.setMsg(message);
		return j;
	}

	/**
	 *功能描述：退回，打开退回页面
	 *@author 凡艺
	 *@since 2016-07-07
	 */
	@RequestMapping(params = "taskselect")
	public ModelAndView taskselect(HttpServletRequest request,String procInstId) {
		request.setAttribute("procInstId", procInstId);
		return new ModelAndView("oct/activiti/process/appform/app-approve-return");			
	}
	/**
	 *功能描述：退回，打开填写退回页面
	 *@author 凡艺
	 *@since 2016-07-13
	 */
	@RequestMapping(params = "jumpNodeAdd")
	public ModelAndView jumpNodeAdd(HttpServletRequest request,String taskid) {
		String procInstId=request.getParameter("procInstId");
		request.setAttribute("procInstId", procInstId);		
		Map activiti=workflowService.turnbackActiviti(taskid,request);
		request.setAttribute("activiti", activiti);
		return new ModelAndView("oct/activiti/process/appform/app-approve-back");			
	}
	/**
	 *功能描述：退回，保存退回任务
	 *@author 汪旭军
	 *@since 2016-06-30
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "jumpNodeSave")
	@ResponseBody
	public AjaxJson jumpNodeSave(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "退回成功！";		
		j.setMsg(message);
		return j;
	}


	/**
	 *先添加撤销记录，后撤销流程实例
	 *@author 凡艺
	 *@since 2016-07-07
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "revoke")
	@ResponseBody
	public AjaxJson revoke(String procInstId, HttpServletRequest req,String businessId) {
		ProcessInstance	processInstance=runtimeService.createProcessInstanceQuery().processInstanceId(procInstId).singleResult();
		AjaxJson j=new AjaxJson();
		String msg="撤销失败";
		if(processInstance!=null)
		{
			ActApproveLog act=new ActApproveLog();
			TSUser user = ResourceUtil.getSessionUserName();
			act.setAction("撤销");
			act.setApproverId(user.getUserName());
			act.setApproverName(user.getRealName());
			act.setBusinessId(businessId);
			act.setComment("申请人撤销");
			act.setNodeId("申请人撤销：未审批");
			act.setNodeName("申请人撤销：未审批");
			Date date1 = new Date();//获取当前时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = sdf.format(date1);//时间存储为字符串	
			act.setCreateTime(Timestamp.valueOf(str));
			systemService.save(act);
			runtimeService.deleteProcessInstance(procInstId, "发起人撤销");
			msg="撤销成功";
			j.setMsg(msg);
			//修改申请记录状态
			ActTaskBusiness actbu=systemService.findUniqueByProperty(ActTaskBusiness.class, "businessId", businessId);
			actbu.setStatus("4");
			systemService.save(actbu);
		}
		return j;
	}
	/**
	 *退回
	 *@author 凡艺
	 *@since 2016-07-07
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "backProcess")
	@ResponseBody
	public  AjaxJson backProcess(String taskId, String activityId,String businessId,String comment) throws Exception {  
		AjaxJson j = new AjaxJson();		
		//根据任务id查询任务实体
		TaskEntity task=(TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		//根据任务id查询流程实例
		ProcessInstance processInstance = runtimeService  
				.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId())
				.singleResult();  
		workflowService.processJump(processInstance,activityId);	     	 
		//添加退回记录
		TSUser user = ResourceUtil.getSessionUserName();	     
		ActApproveLog act=new ActApproveLog();
		act.setAction("退回");
		act.setApproverId(user.getUserName());
		act.setApproverName(user.getRealName());
		act.setBusinessId(businessId);
		act.setComment(comment);
		act.setNodeId(task.getTaskDefinitionKey());
		act.setNodeName(task.getName());
		Date date1 = new Date();//获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date1);//时间存储为字符串	
		act.setCreateTime(Timestamp.valueOf(str));
		systemService.save(act);
		j.setMsg("退回成功");
		return j;
	}
	/**
	 *功能描述：取回操作
	 *@author 凡艺
	 * @throws Exception 
	 *@since 2016-07-12
	 */
	@RequestMapping(params = "getbackforinstance")
	@ResponseBody
	public AjaxJson getbackforinstance(HttpServletRequest request,String businessid,String procInstId ) throws Exception {	
		AjaxJson j=new AjaxJson();
		ProcessInstance processInstance = runtimeService  
				.createProcessInstanceQuery().processInstanceId(procInstId)
				.singleResult();
		if(processInstance==null){
			j.setMsg("流程已结束");
			return j;
		}
		String hql="from ActApproveLog ac where ac.businessId=? order by createTime desc";
		List<ActApproveLog> list=commonService.findHql(hql, businessid);	
		ActApproveLog log=list.get(0);
		String username=log.getApproverId();
		String activityid=list.get(0).getNodeId();
		TSUser user = ResourceUtil.getSessionUserName();
		if(user.getUserName().equals(username)&&log.getAction().equals("提交"))
		{


			List<Task> tasklist=taskService.createTaskQuery().processInstanceId(procInstId).list();
			//获取流程定义实体
			ProcessDefinitionEntity definition =(ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
					.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
			//判断是否是并行节点
			List<String> sts=new ArrayList();
			sts.add("parallelGateway");
			ActivityImpl impl=definition.findActivity(activityid);
			List<PvmTransition> outgoing= impl.getOutgoingTransitions();
			for( PvmTransition pvm:outgoing)
			{
				ActivityImpl pl= (ActivityImpl) pvm.getDestination();		
				if(pl!=null){
					String st=(String) pl.getProperty("type");		    	    
					if(sts.contains(st)){
						j.setMsg("网关节点不能返回");
						return j;
					}
				}
			}
			workflowService.processJump(processInstance, activityid);
			//添加取回记录		
			ActApproveLog act=new ActApproveLog();
			act.setAction("取回");
			act.setApproverId(user.getUserName());
			act.setApproverName(user.getRealName());
			act.setBusinessId(businessid);				
			act.setNodeId(log.getNodeId());
			act.setNodeName(log.getNodeName());
			Date date1 = new Date();//获取当前时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = sdf.format(date1);//时间存储为字符串	
			act.setCreateTime(Timestamp.valueOf(str));
			systemService.save(act);
			j.setMsg("成功取回");
		}else{
			j.setMsg("已审批，不能取回");			
		}
		return j;	
	}
	/**
	 *功能描述：驳回操作
	 *@author 凡艺
	 *@since 2016-08-24
	 */
	@RequestMapping(params = "reject")
	@ResponseBody
	public AjaxJson turndown(HttpServletRequest request,String comment,String taskid,String businessid) {	
		AjaxJson j = new AjaxJson();
		j.setMsg("驳回成功");
		Task task=taskService.createTaskQuery().taskId(taskid).singleResult();
		runtimeService.deleteProcessInstance(task.getProcessInstanceId(),"驳回流程"); 
		workflowService.addActApproveLog(businessid, task.getName(), task.getTaskDefinitionKey(), "驳回", comment);
		ActTaskBusiness actTaskBusiness=commonService.findUniqueByProperty(ActTaskBusiness.class, "businessId", businessid);
		actTaskBusiness.setStatus("2");
		commonService.updateEntitie(actTaskBusiness);
		workflowService.addCopyTo(actTaskBusiness.getBusinessCreateBy(), task.getProcessInstanceId());
		return j;

	}
	@RequestMapping(params = "showcustomopinion")
	public ModelAndView showcustomopinion(HttpServletRequest request)
	{
		return new ModelAndView("oct/activiti/process/appform/show-customopinion");
	}
	@RequestMapping(params = "commonopiniongrid")
	@ResponseBody
	public AjaxJson commonopiniongrid(UserOpinion userOpinion,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		TSUser user = ResourceUtil.getSessionUserName();
		List<UserOpinion> list=systemService.findByProperty(UserOpinion.class, "userId", user.getId());
		LinkedHashMap<String, Object> attributes=new LinkedHashMap();
		for(UserOpinion opi:list)
		{
			attributes.put(opi.getId(), opi.getComment());
		}
		j.setAttributes(attributes);
		return j;
	}
	@RequestMapping(params = "saveOpinion")
	@ResponseBody
	public AjaxJson saveOpinion(HttpServletRequest request,UserOpinion userOpinion) {	
		TSUser user=ResourceUtil.getSessionUserName();
		userOpinion.setUserId(user.getId());
		AjaxJson j = new AjaxJson();
		commonService.save(userOpinion);
		String message =  "保存成功";
		j.setMsg(message);
		return j;

	}

	@RequestMapping(params = "delOpinion")
	@ResponseBody
	public AjaxJson delOpinion(HttpServletRequest request,String id) {	
		AjaxJson j = new AjaxJson();
		UserOpinion userOpinion=commonService.getEntity(UserOpinion.class, id);	
		commonService.delete(userOpinion);
		String message =  "删除成功";
		j.setMsg(message);
		return j;

	}
	//获取流程图
	@RequestMapping(params = "getflowsheet")
	public ModelAndView getfloesheet(HttpServletRequest request) {	
		ModelAndView mv=new ModelAndView();
		String procInstId=request.getParameter("procInstId");
		String processDefId=request.getParameter("processDefId");
		if(StringUtil.isNotEmpty(procInstId)){
			ProcessInstance processInstance=runtimeService.createProcessInstanceQuery().processInstanceId(procInstId).singleResult();		
			if(processInstance!=null){
				List<Map> list=new ArrayList();

				String descript=processInstance.getDescription();
				List<Task> tasklist=taskService.createTaskQuery().processInstanceId(procInstId).list();
				for(Task task:tasklist)
				{
					Map map=new LinkedHashMap();
					map.put("name", task.getName());
					List<TSUser> user=systemService.findByProperty(TSUser.class, "userName", task.getAssignee());
					if(user!=null&&user.size()>0){
						map.put("assignee", user.get(0).getRealName());
					}
					Date date= task.getCreateTime();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String str = sdf.format(date);//时间存储为字符串	
					map.put("createTime", str);
					list.add(map);
				}

				if(list!=null&&list.size()>0){
					mv.addObject("taskinfo", list);	        
					mv.addObject("check",true);
				}else{
					mv.addObject("processDefId",processDefId);
					mv.addObject("check",false);
				}
				if(StringUtil.isNotEmpty(descript)&&descript.contains("document")){
					String document=JSONHelper.json2String(descript, "document");
					mv.addObject("document", document);
				}
				mv.setViewName("oct/activiti/process/appform/flowsheet-show");
				return mv;
			}
		}
		mv.addObject("processDefId",processDefId);
		mv.addObject("check",false);
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefId).singleResult();
		String descript=processDefinition.getDescription();
		if(StringUtil.isNotEmpty(descript)&&descript.contains("document")){
			String document=JSONHelper.json2String(descript, "document");
			mv.addObject("document", document);
		}
		mv.setViewName("oct/activiti/process/appform/flowsheet-show");
		return mv;
	}
	//保存表单数据

	@RequestMapping(params = "saveform")	
	@ResponseBody
	public AjaxJson saveform(HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();
		String message = "";
		try{
			Map paramData = request.getParameterMap();
			String workflowName=request.getParameter("workflowName");
			String formName = request.getParameter("formName");
			String curUserName=ResourceUtil.getSessionUserName().getUserName();//当前登录用户
			if(paramData!=null){
				Map<String,Map<String,Object>> dataMap = AutoFormCommUtil.mapConvert(paramData);
				// 获取参数
				Map<String, Object> param = dataMap.get("param");
				AutoFormEntity autoForm = this.systemService.findUniqueByProperty(AutoFormEntity.class, "formName", formName);
				//获取原始数据
				Map<String, List<Map<String, Object>>> oldDataMap = new HashMap<String, List<Map<String, Object>>>();
				oldDataMap = getFormData(autoForm,param);
				String id = this.autoFormService.doUpdateTable(formName,dataMap,oldDataMap);
				j.setObj(id);
				String proid=request.getParameter("proid");
				workflowService.savedrafts(id, workflowName,"申请表",proid);
				message = "流程保存成功";
			}
		}catch(Exception e){
			e.printStackTrace();
			j.setSuccess(false);
			message = "流程提交失败："+e.getMessage();
		}
		j.setMsg(message);
		return j;
	}
	//草稿箱列表跳转界面
	@RequestMapping(params = "draftsList")
	public ModelAndView draftsList(HttpServletRequest request) { 
		return new ModelAndView("oct/activiti/process/createprocess/draftsList");			
	}
	//草稿箱列表跳转界面
	@RequestMapping(params = "draftsgrid")
	public void draftsgrid(ActTaskBusiness actTaskBusiness,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//		CriteriaQuery cq = new CriteriaQuery(ActTaskBusiness.class, dataGrid);
		//		String username = ResourceUtil.getSessionUserName().getUserName();
		//		//查询条件组装器
		//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, actTaskBusiness,request.getParameterMap());
		//		cq.eq("status", "0");
		//		cq.eq("businessCreateBy",username );
		//		cq.add();
		//		this.commonService.getDataGridReturn(cq, true);
		//		TagUtil.datagrid(response, dataGrid);
		TSUser user = ResourceUtil.getSessionUserName();
		String hql = "from  ActTaskBusiness where businessCreateBy=? and status=?";
		List<ActTaskBusiness> list=systemService.findHql(hql, user.getUserName(),"0");
		List<Map<String, Object>> businessList=new ArrayList();
		for(ActTaskBusiness ab:list)
		{
			Map<String, Object> map= new HashMap<String, Object>();
			map.put("id", ab.getId());
			map.put("createtime", ab.getBusinessCreateTime());
			String processkey=ab.getProcessDefId();
			List<TSProcess> prolist=systemService.findByProperty(TSProcess.class, "processdefineid", processkey);
			if(prolist!=null&&prolist.size()>0){
				TSProcess pro=prolist.get(0);
				map.put("name", pro.getName());
			}
			businessList.add(map);
		}
		dataGrid.setResults(businessList);
		dataGrid.setTotal(businessList.size());
		TagUtil.datagrid(response, dataGrid);
	}
	//修改草稿
	@RequestMapping(params = "updatedrafts")
	public ModelAndView readdrafts(HttpServletRequest request,String id) { 
		ActTaskBusiness actTaskBusiness=systemService.getEntity(ActTaskBusiness.class, id);
		String businessId=actTaskBusiness.getBusinessId();
		String formName=actTaskBusiness.getFormName();
		String prodefid=actTaskBusiness.getProcessDefId();
		return new ModelAndView("redirect:workFlowAutoFormController.do?viewContent&op=update&type=update&formName="+formName+"&id="+businessId+"&workflowName="+prodefid);			
	}
	//		//直接提交草稿
	//		@RequestMapping(params = "startdrafts")
	//		@ResponseBody
	//		public AjaxJson startdrafts(HttpServletRequest request,String id) { 
	//			ActTaskBusiness actTaskBusiness=systemService.getEntity(ActTaskBusiness.class, id);
	//			AjaxJson j = new AjaxJson();
	//			String message = "";
	//			String businessId=actTaskBusiness.getBusinessId();
	//			String formName=actTaskBusiness.getFormName();
	//			String workflowName=actTaskBusiness.getProcessDefId();
	//			String curUserName=ResourceUtil.getSessionUserName().getUserName();//当前登录用户
	//			Map<String, Object> param = new HashMap();
	//			param.put("op", "update");
	//			param.put("formName", formName);
	//			param.put("id", businessId);
	//			AutoFormEntity autoForm = this.systemService.findUniqueByProperty(AutoFormEntity.class, "formName", formName);
	//			Map<String, List<Map<String, Object>>> oldDataMap = new HashMap<String, List<Map<String, Object>>>();
	//			oldDataMap = getFormData(autoForm,param);
	//			List<Map<String, Object>> list=oldDataMap.get(autoForm.getMainTableSource());
	//			Map<String,Map<String,Object>> paramMap=new HashMap();
	//			paramMap.put(autoForm.getMainTableSource(), list.get(0));			
	//			//获取流程变量
	//			Map<String,Object> processVariables=workflowService.processVariables(workflowName, curUserName,autoForm.getMainTableSource(),paramMap,request);
	//			if(workflowService.startWorkflow(id,workflowName,processVariables,id,formName) !=null){
	//				//建立表和业务申请单的关系
	//				message = "流程提交成功";
	//			}else{
	//				message = "流程提交失败";
	//			}
	//			j.setMsg(message);
	//			return j;
	//		}
	//删除草稿
	@RequestMapping(params = "delrafts")
	@ResponseBody
	public AjaxJson delrafts(HttpServletRequest request,String id) { 
		AjaxJson j = new AjaxJson();
		ActTaskBusiness actTaskBusiness=systemService.getEntity(ActTaskBusiness.class, id);
		String businessId=actTaskBusiness.getBusinessId();
		String formName=actTaskBusiness.getFormName();
		customFormService.deleteautoform(formName, businessId);
		systemService.delete(actTaskBusiness);
		j.setMsg("删除流程");
		return j;
	}

	@RequestMapping(params = "newapproval")
	public ModelAndView newapproval(HttpServletRequest request,String id) { 		  
		return new ModelAndView("oct/activiti/process/appform/new-approval");			
	}
	/**
	 *功能描述：驳回操作
	 *@author 凡艺
	 *@since 2016-08-24
	 */
	@RequestMapping(params = "getDocument")
	@ResponseBody
	public AjaxJson getDocument(HttpServletRequest request,String workFlowName) { 
		AjaxJson j=new AjaxJson();
		j.setSuccess(false);
		String msg="";
		ProcessDefinition  processDefinition=workflowService.getProcessDefinitionByKey(workFlowName);
		String descript=processDefinition.getDescription();
		if(StringUtil.isNotEmpty(descript)&&descript.contains("document")){
			msg=JSONHelper.json2String(descript, "document");
			j.setSuccess(true);
		}
		j.setMsg(msg);
		return j;
	}
	
}
