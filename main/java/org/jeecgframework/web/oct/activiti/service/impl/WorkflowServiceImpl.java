package org.jeecgframework.web.oct.activiti.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.beanutils.ConvertUtils;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.constant.DataBaseConstant;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.cgform.entity.autoform.AutoFormEntity;
import org.jeecgframework.web.demo.service.test.JeecgDemoServiceI;
import org.jeecgframework.web.oct.activiti.entity.ActApproveLog;
import org.jeecgframework.web.oct.activiti.entity.ActHiTaskinst;
import org.jeecgframework.web.oct.activiti.entity.ActRuExecution;
import org.jeecgframework.web.oct.activiti.entity.ActRuTask;
import org.jeecgframework.web.oct.activiti.entity.ActTaskBusiness;
import org.jeecgframework.web.oct.activiti.entity.OctActCopyTo;
import org.jeecgframework.web.oct.activiti.entity.ProcessPrefix;
import org.jeecgframework.web.oct.activiti.entity.SysReadLog;
import org.jeecgframework.web.oct.activiti.entity.TSProcess;
import org.jeecgframework.web.oct.activiti.service.interf.WorkflowService;
import org.jeecgframework.web.oct.common.util.OrgUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *功能描述： 工作流Service，实现启动和提交流程等。
 */
/*
 * 类名：WorkflowService
 *作者：汪旭军
 *日期: 2016-06-16
 */
@Service("workflowService")
@Transactional
public class WorkflowServiceImpl  implements WorkflowService {

	private static Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected HistoryService historyService;

	@Autowired
	protected RepositoryService repositoryService;

	@Autowired
	private IdentityService identityService;
	@Autowired
	CommonService commonService;
	/**
	 *功能描述：启动流程。
	 * @param businessKey – 申请单记录的ID
	 * @param autoForm 系统表单，含有创建人，创建时间，部门等基础信息。
	 * @param dataMap – 前端传递到后端的所有数据。用于获取工作流需要的变量
	 * @return ProcessInstance – 返回流程实体对象
	 * @author 汪旭军
	 * @since 2016-06-16
	 */
	@Override
	public ProcessInstance startWorkflow(String businessKey,String workflowName, Map<String,Object> processVariables,String actTaskBusinessid,String proid,String saveSerialnumber) {
		ProcessInstance processInstance = null;
		String curUserName=ResourceUtil.getSessionUserName().getUserName();//当前登录用户
		//启动流程
		identityService.setAuthenticatedUserId(curUserName);
		processInstance = runtimeService.startProcessInstanceByKey(workflowName, businessKey,processVariables);
		String processInstanceId = processInstance.getId();
		//创建流程任务和申请单的对应关系。
		if(StringUtil.isNotEmpty(actTaskBusinessid)){
			draftsaddTaskToBusiness(actTaskBusinessid,processInstanceId, saveSerialnumber);
		}else{
			addTaskToBusiness(businessKey,processInstanceId,"申请表",proid,saveSerialnumber);
		}
		addActApproveLog(businessKey,  "开始", "",  "提交", ""); 

		return processInstance;
	}

	/**
	 *功能描述：创建流程任务和申请单的对应关系。
	 * @param businessKey – 申请单记录的ID
	 * @param processInstanceId 系流程实例的ID。
	 * @param title – 标题
	 * @author 汪旭军
	 * @since 2016-06-16
	 */
	public boolean addTaskToBusiness(String businessKey,String processInstanceId,String title,String proid,String serialnumber) {
		//oct_act_task_business 增加了流程定义ID （process_def_id）  bind  insert
		//ActRuExecution actRuExecution=new ActRuExecution();
		ActTaskBusiness actTaskBusiness = new ActTaskBusiness();
		ActRuExecution actRuExecution = this.commonService.findUniqueByProperty(ActRuExecution.class, "procInstId",processInstanceId);		
		actTaskBusiness.setProcessDefId(actRuExecution.getProcDefId());// 流程定义ID，后期增加字段
		TSProcess tSProcess=this.commonService.getEntity(TSProcess.class,proid);
		actTaskBusiness.setTSProcess(tSProcess);
		actTaskBusiness.setFormName(tSProcess.getForm());
		actTaskBusiness.setBusinessId(businessKey);
		actTaskBusiness.setProcessInstanceId(processInstanceId);
		actTaskBusiness.setBusinessTitle(tSProcess.getName()+"-"+title);
		actTaskBusiness.setSerialnumber(serialnumber);
		TSUser tuser =ResourceUtil.getSessionUserName();
		actTaskBusiness.setBusinessCreateBy(tuser.getUserName());//登录账户
		actTaskBusiness.setBusinessCreateName(tuser.getRealName());//姓名
		actTaskBusiness.setStatus("1");//流程已启动
		Date date1 = new Date();//获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date1);//时间存储为字符串
		actTaskBusiness.setBusinessCreateTime(Timestamp.valueOf(str));
		commonService.save(actTaskBusiness);
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 *功能描述：创建流程任务和申请单的对应关系。
	 * @param businessKey – 申请单记录的ID
	 * @param processInstanceId 系流程实例的ID。
	 * @param title – 标题
	 * @author 凡艺
	 * @since 2016-06-16
	 */
	public boolean draftsaddTaskToBusiness(String actTaskBusinessid,String processInstanceId,String serialnumber) {
		//oct_act_task_business 增加了流程定义ID （process_def_id）  bind  insert
		//ActRuExecution actRuExecution=new ActRuExecution();
		ActTaskBusiness actTaskBusiness = commonService.getEntity(ActTaskBusiness.class, actTaskBusinessid);
		ActRuExecution actRuExecution = this.commonService.findUniqueByProperty(ActRuExecution.class, "procInstId",processInstanceId);		
		actTaskBusiness.setProcessDefId(actRuExecution.getProcDefId());// 流程定义ID，后期增加字段
		actTaskBusiness.setProcessInstanceId(processInstanceId);
		actTaskBusiness.setStatus("1");//流程已启动
		actTaskBusiness.setSerialnumber(serialnumber);//流水号
		Date date1 = new Date();//获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date1);//时间存储为字符串
		actTaskBusiness.setBusinessCreateTime(Timestamp.valueOf(str));
		return true;
	}
	/**
	 *功能描述：保存草稿信息
	 * @param businessKey – 申请单记录的ID	
	 * @param title – 标题
	 * @author 凡艺
	 * @since 2016-06-16
	 */
	public boolean savedrafts(String businessKey,String workflowName,String title,String proid) {
		//oct_act_task_business 增加了流程定义ID （process_def_id）  bind  insert
		//ActRuExecution actRuExecution=new ActRuExecution();
		ActTaskBusiness actTaskBusiness = new ActTaskBusiness();	
		actTaskBusiness.setBusinessId(businessKey);
		actTaskBusiness.setProcessDefId(workflowName);
		TSProcess tSProcess=this.commonService.getEntity(TSProcess.class,proid);
		actTaskBusiness.setTSProcess(tSProcess);
		actTaskBusiness.setFormName(tSProcess.getForm());
		actTaskBusiness.setBusinessTitle(tSProcess.getName()+"-"+title);
		TSUser tuser =ResourceUtil.getSessionUserName();
		actTaskBusiness.setBusinessCreateBy(tuser.getUserName());//登录账户
		actTaskBusiness.setBusinessCreateName(tuser.getRealName());//姓名
		actTaskBusiness.setStatus("0");//草稿
		Date date1 = new Date();//获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date1);//时间存储为字符串
		actTaskBusiness.setBusinessCreateTime(Timestamp.valueOf(str));
		commonService.save(actTaskBusiness);
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean addActApproveLog(String businessKey, String nodeName, String nodeId, String action, String comment) {
		ActApproveLog actApproveLog =new ActApproveLog();
		actApproveLog.setNodeName(nodeName);
		actApproveLog.setNodeId(nodeId);
		TSUser tuser =ResourceUtil.getSessionUserName();
		actApproveLog.setApproverId(tuser.getUserName());
		actApproveLog.setApproverName(tuser.getRealName());
		actApproveLog.setAction(action);
		actApproveLog.setComment(comment);
		Date date1 = new Date();//获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date1);//时间存储为字符串
		actApproveLog.setCreateTime(Timestamp.valueOf(str));
		actApproveLog.setBusinessId(businessKey);
		commonService.save(actApproveLog);
		return true;
	}

	@Override
	public boolean completeTask(String taskId, Map<String, Object> variables) {
		List<String > userids=getCopyTo(taskId,variables,null);
		Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
		for(String userid:userids){ 
			addCopyTo(userid,task.getProcessInstanceId());
		}
			taskService.complete(taskId, variables);
		return true;
	}
	@Override
	public List<String> getCopyTo(String taskid, Map<String, Object> variables,String proDefid) {
		String description="";
		if(StringUtil.isNotEmpty(taskid)){
		   Task task=taskService.createTaskQuery().taskId(taskid).singleResult();
		   description=task.getDescription();
		}
		if(StringUtil.isNotEmpty(proDefid)){
			 ProcessDefinition  processDefinition;
			 if(proDefid.indexOf(":")>0){
					processDefinition=getProcessDefinitionById(proDefid);//根据流程定义ID查找
				}else{
					processDefinition=getProcessDefinitionByKey(proDefid);//根据流程定义key查找
				}
			 description=processDefinition.getDescription();
			
		}
		List<String> userids=new ArrayList();
		if(StringUtil.isNotEmpty(description)){
			if(StringUtil.isNotEmpty(description)&&description.contains("copyToUser")){
				String copyToUserst=JSONHelper.json2String(description, "copyToUser");
				String[] copyToUsers=copyToUserst.split(";");
				for(String userid:copyToUsers){
					TSUser user=commonService.findByProperty(TSUser.class, "userName", userid).get(0);
					userids.add(user.getId());
				}
			}
		
		if(variables==null){
			variables=new HashMap();
		}
		if(StringUtil.isNotEmpty(description)&&description.contains("copyToVar")){
			String copyToVarstr=JSONHelper.json2String(description, "copyToVar");
			String[]  copyToVars=copyToVarstr.split(";");
			for(String copyToVar:copyToVars){
				Object idss= variables.get(copyToVar);
				if(idss instanceof String){
					TSUser user=commonService.findByProperty(TSUser.class, "userName", (String)idss).get(0);
					userids.add(user.getId());
				}else if(idss instanceof List){
					List<String> list=(List) idss;
					for(String username:list){
						TSUser user=commonService.findByProperty(TSUser.class, "userName", username).get(0);
						userids.add(user.getId());
					}
				}
			}
		}
		}
		return userids;
	}
	//向待阅表中添加记录
     public void addCopyTo(String userid,String proinstid)
     {
    	 OctActCopyTo octActCopyTo=new OctActCopyTo();
    	 TSUser user=new TSUser();
    	 System.out.println(userid);
    	 user.setId(userid);
    	 octActCopyTo.setUser(user);
    	 Date date=new Date();
    	 octActCopyTo.setCreatetime(date);
    	 octActCopyTo.setStatus("0");
    	 ActTaskBusiness actTaskBusiness= commonService.findByProperty(ActTaskBusiness.class, "processInstanceId", proinstid).get(0);
    	 octActCopyTo.setActTaskBusiness(actTaskBusiness);
    	 commonService.save(octActCopyTo);
     }
	@Override
	public boolean transferAssignee(String taskId, String userId) {
		taskService.setAssignee(taskId.trim(), userId.trim());  
		return true;
	}

	public boolean isCurrentUserTask(ActRuTask actRuTask){
		boolean rtn=true;
		String currentUser =ResourceUtil.getSessionUserName().getUserName();//获取当前用户账号
		String taskAsigness=actRuTask.getAssignee();
		if(taskAsigness.indexOf(currentUser)==-1){
			rtn=false;
		}
		return rtn;
	}

	@Override
	public Map<String,Object> processVariables(String processDefinitionIdOrKey, String userName,String mainTableSource,Map paramData,HttpServletRequest req) {
		Map<String,Object> processVariables = new HashMap<String,Object>();
		Map param=(Map) paramData.get(mainTableSource);
		ProcessDefinition processDefinition;//当前流程定义对象
		String pDescription="";//流程的描述和流程变量等信息
		TSUser user = commonService.findUniqueByProperty(TSUser.class,"userName",userName);
		OrgUtil org = new OrgUtil(user); 
		org.putAllLeaders(processVariables);//领导数据c
		processVariables.put("applyUserId", userName);//申请人
		String postLevel=user.getPostLevel();//职级
		if(StringUtil.isNotEmpty(postLevel)){
			processVariables.put("level", postLevel);
		}else{
			processVariables.put("level", "1");
		}
		//获取流程定义
		if(processDefinitionIdOrKey.indexOf(":")>0){
			processDefinition=getProcessDefinitionById(processDefinitionIdOrKey);//根据流程定义ID查找
		}else{
			processDefinition=getProcessDefinitionByKey(processDefinitionIdOrKey);//根据流程定义key查找
		}
		pDescription=processDefinition.getDescription();
		if(!oConvertUtils.isEmpty(pDescription)){
			if(pDescription.contains("activitiVar")){
				String strActivitiVar=JSONHelper.json2String(pDescription, "activitiVar");
				String activitiVarValue="";
				for(String activitiVar:strActivitiVar.split(";")){
					activitiVarValue=(String) param.get(activitiVar);
					if(StringUtil.isEmpty(activitiVarValue)){
						activitiVarValue=req.getParameter(activitiVar);
					}
					if(StringUtil.isNotEmpty(activitiVarValue)&&activitiVarValue.contains(";")){
						String[] strs=activitiVarValue.split(";");
						List list=new ArrayList();
						for(String str:strs){
							list.add(str);
						}
						processVariables.put(strActivitiVar, list);
					}else{

						processVariables.put(activitiVar, ConvertUtils.convert(activitiVarValue,String.class));
					}

				}
			}
			//获取抄送人信息
			if(pDescription.contains("copyToVar")){
				//获取抄送人信息
				String strcopyToVar=JSONHelper.json2String(pDescription, "copyToVar");
				String strcopyToVarValue="";
				for(String copyToVar:strcopyToVar.split(";")){
					strcopyToVarValue=(String) param.get(copyToVar);
					if(StringUtil.isEmpty(strcopyToVarValue)){
						strcopyToVarValue=req.getParameter(copyToVar);
					}
					if(StringUtil.isNotEmpty(strcopyToVarValue)&&strcopyToVarValue.contains(";")){
						String[] strs=strcopyToVarValue.split(";");
						List list=new ArrayList();
						for(String str:strs){
							list.add(str);
						}
						processVariables.put(copyToVar, list);
					}else{
						
						processVariables.put(copyToVar, ConvertUtils.convert(strcopyToVarValue,String.class));
					}

				}
			}
			
		}		
		return processVariables;
	}

	public ProcessDefinition getProcessDefinitionByKey(String processDefinitionKey){
		ProcessDefinitionQuery processDefinitionQuery =repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey);
		processDefinitionQuery=processDefinitionQuery.latestVersion();
		return processDefinitionQuery.singleResult();
	}

	public ProcessDefinition getProcessDefinitionById(String processDefinitionId){
		ProcessDefinition processDefinition=null;
		ProcessDefinitionQuery processDefinitionQuery=repositoryService.createProcessDefinitionQuery();
		List<ProcessDefinition> listProcessDefinition=processDefinitionQuery.processDefinitionId(processDefinitionId).list();
		Iterator it = listProcessDefinition.iterator();
		if(it.hasNext()){
			processDefinition=(ProcessDefinition) it.next();
		}
		return processDefinitionQuery.singleResult();
	}

	@Override
	public void processJump(ProcessInstance processInstance, String activityId) {
		//获取流程定义实体
		ProcessDefinitionEntity definition =(ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
				.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		// 查找所有并行任务节点
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(  
				processInstance.getId()).list();  
		//获取目标节点可到达的节点。
		ActivityImpl point=definition.findActivity(activityId) ;
		Map<String,String> allOUTusertask=new HashMap();
		this.activityImpl(point, allOUTusertask,0);
		Set<String> set=allOUTusertask.keySet();      
		for(Task task:taskList)
		{
			if(!set.contains(task.getTaskDefinitionKey()))
			{
				taskList.remove(task);
			}
		}

		int index=0;//判断是否首次for循环
		//驳回
		for (Task task2 : taskList) {  

			// 当前节点  
			ActivityImpl currActivity = definition.findActivity(task2.getTaskDefinitionKey()) ; 	            
			// 存储当前节点所有流向临时变量  
			List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();    
			// 获取当前节点所有流向，存储到临时变量，然后清空  
			List<PvmTransition> pvmTransitionList = currActivity.getOutgoingTransitions();  
			for (PvmTransition pvmTransition : pvmTransitionList) {  
				oriPvmTransitionList.add(pvmTransition);  
			}  
			pvmTransitionList.clear();  
			//流向清空完毕
			// 创建新流向  
			TransitionImpl newTransition = currActivity.createOutgoingTransition();  
			// 目标节点  
			ActivityImpl pointActivity;
			if(index==0){
				pointActivity=definition.findActivity(activityId) ;   
			}else{
				pointActivity=this.endactivity(definition);
			}
			index++;
			// 设置新流向的目标节点  
			newTransition.setDestination(pointActivity);         
			// 执行转向任务  
			taskService.complete(task2.getId());  
			// 删除目标节点新流入  
			pointActivity.getIncomingTransitions().remove(newTransition);         
			// 清空现有流向  
			List<PvmTransition> pvmTransitionList2 = currActivity.getOutgoingTransitions();  
			pvmTransitionList2.clear();  
			// 还原以前流向  
			for (PvmTransition pvmTransition : oriPvmTransitionList) {  
				pvmTransitionList2.add(pvmTransition);  
			}  
		}

	}

	@Override
	public void getAllUserTask(ActivityImpl current, List<ActivityImpl> parallelGatewayactivity,
			Map<String, String> allINusertask,int i) {
		i++;
		if(i>=100){
			return;
		}
		List<PvmTransition> income=current.getIncomingTransitions();
		for(PvmTransition pvm:income)
		{
			ActivityImpl impl=(ActivityImpl) pvm.getSource();
			String type=(String) impl.getProperty("type");
			if("startEvent".equals(type)){
				continue;
			}else if("userTask".equals(type)){
				allINusertask.put(impl.getId(),(String) impl.getProperty("name"));
				getAllUserTask(impl,parallelGatewayactivity,allINusertask,i);
			}else if("parallelGateway".equals(type)&&parallelGatewayactivity.size()==0){

				String id=impl.getId();
				if(id.contains("-"))
				{
					String endId=id.substring(id.lastIndexOf("-")+1);
					if(endId!=null&&endId.toUpperCase().equals("END")){
						parallelGatewayactivity.add(impl);						
					}
				}
				getAllUserTask(impl,parallelGatewayactivity,allINusertask,i);
			}else{
				getAllUserTask(impl,parallelGatewayactivity,allINusertask,i);
			}

		}
	}


	public  void activityImpl(ActivityImpl current, Map<String,String> allOUTusertask,int i)
	{
		i++;
		if(i>100){
			return;
		}
		List<PvmTransition> outgo=current.getOutgoingTransitions();
		for(PvmTransition pvm:outgo)
		{
			ActivityImpl impl= (ActivityImpl) pvm.getDestination();
			String type=(String) impl.getProperty("type");
			if(type.equals("userTask")){
				allOUTusertask.put(impl.getId(), (String)impl.getProperty("name"));
				activityImpl(impl,allOUTusertask,i);
			}else if(type.equals("endEvent")){
				continue;
			}else{
				activityImpl(impl,allOUTusertask,i);
			}
		}
	}
	//并行网关用户任务节点
	public  void parallelgatewayactivity(ActivityImpl current,String id,Map<String,String> excludeusertask)
	{
		List<PvmTransition> incom=current.getIncomingTransitions();   	
		for(PvmTransition pvm:incom)
		{
			ActivityImpl impl=(ActivityImpl) pvm.getSource();
			String ids=(String) impl.getId();  //获取节点id
			String idstart="";
			if(ids.lastIndexOf("-")>0){
				idstart=ids.substring(0, ids.lastIndexOf("-"));
			}
			String type=(String) impl.getProperty("type");
			if(idstart.equals(id))
			{
				return;
			}else if(type.equals("userTask")){
				excludeusertask.put(impl.getId(),(String)impl.getProperty("name"));
				parallelgatewayactivity(impl,id,excludeusertask);
			}else if(type.equals("startEvent")){
				return; 
			} else{
				parallelgatewayactivity(impl,id,excludeusertask); 
			}
		}    	
	}
	//获取当前流程的end节点
	public  ActivityImpl endactivity(ProcessDefinitionEntity definition)
	{
		List<ActivityImpl> definitionActivities=definition.getActivities();
		for(ActivityImpl impl:definitionActivities)
		{
			String st=(String) impl.getProperty("type");
			if("endEvent".equals(st))
			{
				return impl;
			}
		}
		return null;
	}
	/**
	 *功能描述：获取可退回任务节点
	 *@author 凡艺
	 *@since 2016-07-13
	 */
	public Map<String,String> turnbackActiviti(String taskId,HttpServletRequest request)

	{

		//根据任务id查询任务实体
		TaskEntity task=(TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		//根据任务id查询流程实例
		ProcessInstance processInstance = runtimeService  
				.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId())
				.singleResult();  
		//获取流程定义实体
		ProcessDefinitionEntity definition =(ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
				.getDeployedProcessDefinition(task.getProcessDefinitionId());
		ActivityImpl current=definition.findActivity(task.getTaskDefinitionKey());
		List<ActivityImpl> parallelGatewayactivity=new ArrayList();//
		Map<String,String> allINusertask=new HashMap();
		Map<String,String> excludeusertask=new HashMap();  

		getAllUserTask(current,parallelGatewayactivity,allINusertask,0);
		if(parallelGatewayactivity.size()==1){
			String id=parallelGatewayactivity.get(0).getId();
			if(id.lastIndexOf("-")>0){
				id=id.substring(0, id.lastIndexOf("-"));
				parallelgatewayactivity(parallelGatewayactivity.get(0),id,excludeusertask);
			}   	     
		}

		Set<String> all=allINusertask.keySet();
		Set<String> exclud=excludeusertask.keySet();
		for(String st:exclud)
		{
			if(all.contains(st)) allINusertask.remove(st);
		}
		//去除没有审批记录的节点
		 String businessId=commonService.findUniqueByProperty(ActTaskBusiness.class, "processInstanceId", task.getProcessInstanceId()).getBusinessId();
		 Set<String> allid=allINusertask.keySet();
		List<String> telist=new ArrayList();//保存需要删除的退回节点;
		 Iterator<String> it=allid.iterator();
			 while(it.hasNext()){
				 String st=it.next();
			 if(st.equals("applicant")){
				 String value=allINusertask.get(st);
				 allINusertask.put(st, value);
				 continue;
			 }
			 String hql="from ActApproveLog where businessId=? and nodeId=?";
			 List list=commonService.findHql(hql, businessId,st);
			if(list==null||list.size()==0){
				telist.add(st);
			}
		 }
			 for(String st:telist){
				 allINusertask.remove(st);
			 }
		return allINusertask;
	}

	@Override
	public void addReadLog(String id) {	
		List list=commonService.findByProperty(SysReadLog.class, "vieweddataid", id);
		if(list==null||list.size()==0){
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String str = sdf.format(date);
	    SysReadLog sysReadLog=new SysReadLog();
	    sysReadLog.setReadtime(Timestamp.valueOf(str));
	    TSUser user = ResourceUtil.getSessionUserName();
	    sysReadLog.setReader(user.getRealName());
	    sysReadLog.setReaderId(user.getId());
	    sysReadLog.setVieweddataid(id);
	    System.out.println(sysReadLog.getReader());
	    commonService.save(sysReadLog);	
		}
	}

	public Map<String,Object> activitiVariables(String taskid, String userName,String mainTableSource,Map paramData,HttpServletRequest req)
	{
		Map<String,Object> processVariables = new HashMap<String,Object>();
		Map param=(Map) paramData.get(mainTableSource);
		Task task=taskService.createTaskQuery().taskId(taskid).singleResult();
		String describe=task.getDescription();
		if(StringUtil.isNotEmpty(describe)&&describe.contains("activitiVar")){
			String strActivitiVar=JSONHelper.json2String(describe, "activitiVar");
			String activitiVarValue="";		
			for(String activitiVar:strActivitiVar.split(";")){
				activitiVarValue=(String) param.get(activitiVar);
				if(StringUtil.isEmpty(activitiVarValue)){
					activitiVarValue=req.getParameter(activitiVar);
				}								
				if(StringUtil.isNotEmpty(activitiVarValue)&&activitiVarValue.contains(";")){
					String[] strs=activitiVarValue.split(";");
					List list=new ArrayList();
					for(String str:strs){
						list.add(str);
					}
					processVariables.put(strActivitiVar, list);
				}else{
					
					processVariables.put(activitiVar, ConvertUtils.convert(activitiVarValue,String.class));
				}
			}
			
			
		}else if(StringUtil.isNotEmpty(describe)&&describe.contains("copyToVar")){
			//获取抄送人信息
			String strcopyToVar=JSONHelper.json2String(describe, "copyToVar");
			String strcopyToVarValue="";
			for(String copyToVar:strcopyToVar.split(";")){
				strcopyToVarValue=(String) param.get(copyToVar);
				if(StringUtil.isEmpty(strcopyToVarValue)){
					strcopyToVarValue=req.getParameter(copyToVar);
				}
				if(StringUtil.isNotEmpty(strcopyToVarValue)&&strcopyToVarValue.contains(";")){
					String[] strs=strcopyToVarValue.split(";");
					List list=new ArrayList();
					for(String str:strs){
						list.add(str);
					}
					processVariables.put(copyToVar, list);
				}else{
					
					processVariables.put(copyToVar, ConvertUtils.convert(strcopyToVarValue,String.class));
				}

			}
		}
		return processVariables;
	}

	@Override
	public String addSerialNumber(Map<String, Map<String, Object>> dataMap, String formName,String proid,String businessId) {
		String  serialNumber="";
		if(formName.toLowerCase().indexOf(".jsp")==-1){
			AutoFormEntity autoForm = commonService.findUniqueByProperty(AutoFormEntity.class, "formName",formName);
			String tablesource=autoForm.getMainTableSource();
			if(StringUtil.isEmpty(proid)){
				String hql="from ActTaskBusiness where businessId=?";
				List<ActTaskBusiness> list=commonService.findHql(hql, businessId);
				if(list.size()>0){
					proid=list.get(0).getTSProcess().getId();
				}
			}
			  serialNumber=getNumber(proid);		
			Map<String, Object> map=dataMap.get(tablesource);
			map.put("serialnumber", serialNumber);
		}else{
			  serialNumber=getNumber(proid);
			Map<String, Object> map=dataMap.get("ds");
			map.put("serialnumber", serialNumber);				
		}				
		return serialNumber;		
	}
	public String getNumber(String proid){
		String  serialNumber="";
		List<ProcessPrefix> list=commonService.findByProperty(ProcessPrefix.class, "proId", proid);
		if(list!=null&&list.size()==1){
			ProcessPrefix processPrefix=list.get(0);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String current=sdf.format(new Date());//当前时间	
			String date=sdf.format(processPrefix.getCurrentTime());
			if(current.equals(date)){
				String st=date.replaceAll("-", "");
			    serialNumber=processPrefix.getPrefix()+st+processPrefix.getCurrentValue();
				String value=processPrefix.getCurrentValue();
				value=Integer.parseInt(value)+1+"";
				processPrefix.setCurrentValue(value);
				commonService.updateEntitie(processPrefix);
			}else{
				String st=current.replaceAll("-", "");
			    serialNumber=processPrefix.getPrefix()+st+"1001";
				String value=processPrefix.getCurrentValue();
				processPrefix.setCurrentValue("1002");
				try {
					Date currentTime=sdf.parse(sdf.format(new Date()));
					processPrefix.setCurrentTime(currentTime);
					commonService.updateEntitie(processPrefix);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return serialNumber;
	}

	@Override
	public void getEndCopyTo(String processdef,String proIns) {
		try{
			ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().processDefinitionId(processdef).singleResult();
			String description=processDefinition.getDescription();
			if(StringUtil.isNotEmpty(description)&&description.contains("endCopyToUser")){
				String copyToUserst=JSONHelper.json2String(description, "endCopyToUser");
				String[] copyToUsers=copyToUserst.split(";");
				for(String userid:copyToUsers){
					TSUser user=commonService.findByProperty(TSUser.class, "userName", userid).get(0);
					this.addCopyTo(user.getId(), proIns);
				}
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}