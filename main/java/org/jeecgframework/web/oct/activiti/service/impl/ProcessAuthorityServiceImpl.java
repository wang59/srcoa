package org.jeecgframework.web.oct.activiti.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.oct.activiti.entity.ActApproveLog;
import org.jeecgframework.web.oct.activiti.entity.ActTaskBusiness;
import org.jeecgframework.web.oct.activiti.service.interf.ProcessAuthorityService;
import org.jeecgframework.web.oct.activiti.service.interf.WorkflowService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("processAuthorityService")
@Transactional
public class ProcessAuthorityServiceImpl implements ProcessAuthorityService {
	@Autowired
	private CommonService commonService;	
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private WorkflowService workflowService;

	public boolean isAllowTurnToDo(String taskid) {
		boolean b=true;
		Task task=taskService.createTaskQuery().taskId(taskid).singleResult();
		if(task!=null)
		{
			String description=task.getDescription();
			if(StringUtil.isNotEmpty(description)&&description.contains("isAllowTurnToDo"))
			{
				String isAllowTurnToDo=JSONHelper.json2String(description, "isAllowTurnToDo");
				if(isAllowTurnToDo!=null&&isAllowTurnToDo.equals("N")){
					b=false;
				}
			}
		}
		return b;
	}

	@Override
	public boolean isAllowAssit(String taskid) {
		boolean b=true;
		Task task=taskService.createTaskQuery().taskId(taskid).singleResult();
		if(task!=null)
		{
			String description=task.getDescription();
			if(StringUtil.isNotEmpty(description)&&description.contains("isAllowAssit"))
			{
				String isAllowAssit=JSONHelper.json2String(description, "isAllowAssit");
				if(isAllowAssit!=null&&isAllowAssit.equals("N")){
					b=false;
				}
			}
		}
		return b;
	}

	@Override
	public boolean isAllowTurnBack(String taskid) {
		boolean b=true;
		Task task=taskService.createTaskQuery().taskId(taskid).singleResult();
		if(task!=null)
		{
			String description=task.getDescription();
			if(StringUtil.isNotEmpty(description)&&description.contains("isAllowTurnBack"))
			{
				String isAllowTurnBack=JSONHelper.json2String(description, "isAllowTurnBack");
				if(isAllowTurnBack!=null&&isAllowTurnBack.equals("N")){
					b=false;
				}
			}
		}
		return b;
	}

	@Override
	public boolean isAllowPrint(String taskid, String activityKey, String definitionid) {
		String description="";
		boolean b=true;
		if(taskid!=null){
			Task task=taskService.createTaskQuery().taskId(taskid).singleResult();
			if(task!=null){
				description=task.getDescription();
			}
		}else if(activityKey!=null){
			ProcessDefinitionEntity definition =(ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
					.getDeployedProcessDefinition(definitionid);
			ActivityImpl impl=definition.findActivity(activityKey);	
			if(impl!=null){
				description=(String) impl.getProperty("documentation");
			}
		}
		if(description!=null&&!description.equals("")&&description.contains("isAllowPrint"))
		{
			String isAllowPrint=JSONHelper.json2String(description, "isAllowPrint");
			if(isAllowPrint!=null&&isAllowPrint.equals("N")){
				b=false;
			}
		}
		return b;
	}

	@Override
	public boolean isAllowGetBack(String activityKey, String definitionid) {
		boolean b=true;
		ProcessDefinitionEntity definition =(ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
				.getDeployedProcessDefinition(definitionid);

		ActivityImpl impl=definition.findActivity(activityKey);
		if(activityKey!=null)
		{
			String description=(String) impl.getProperty("documentation");
			if(StringUtil.isNotEmpty(description)&&description.contains("isAllowGetBack"))
			{
				String isAllowGetBack=JSONHelper.json2String(description, "isAllowGetBack");
				if(isAllowGetBack!=null&&isAllowGetBack.equals("N")){
					b=false;
				}
			}
		}
		return b;
	}

	@Override
	public void isAllowSkip(String taskid) {
		boolean b=false;
		Task task=taskService.createTaskQuery().taskId(taskid).singleResult();		
		String description=task.getDescription();
		if(StringUtil.isNotEmpty(description)&&description.contains("isAllowSkip")){
			if(description.contains("activitiVar")||description.contains("processAgentFor")){

			}else if(JSONHelper.json2String(description, "isAllowSkip").equals("Y")){
				b=true;
			}

		}
		if(b){
			String userName=task.getAssignee();
			String key=task.getTaskDefinitionKey();
			List<ActTaskBusiness> list=commonService.findByProperty(ActTaskBusiness.class, "processInstanceId", task.getProcessInstanceId());
			if(list!=null&&list.size()>0){
				String businessId=list.get(0).getBusinessId();
				//获取审批记录
				String hql="from ActApproveLog where businessId=? and approverId=?";
				List<ActApproveLog> loglist=commonService.findHql(hql, businessId,userName);
				if(loglist!=null&&loglist.size()>0){
					for(ActApproveLog log:loglist){
						if(StringUtil.isNotEmpty(log.getNodeId())&&log.getNodeId().equals(key)){
							return;
						}
					}
					workflowService.completeTask(taskid, null);
					addApprovalRecord("跳过",businessId,"重复审批，跳过",task);
				}
			}
		}else{
			return;
		}

	}
	//添加审批记录
	public void addApprovalRecord(String action,String businessid,String comment,Task task)
	{
		ActApproveLog act=new ActApproveLog();
		TSUser user = commonService.findByProperty(TSUser.class, "userName", task.getAssignee()).get(0);
		act.setAction(action);
		act.setApproverId(user.getUserName());
		act.setApproverName(user.getRealName());
		act.setBusinessId(businessid);
		act.setComment(comment);
		Date date1 = new Date();//获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String str = sdf.format(date1);//时间存储为字符串	
		act.setCreateTime(Timestamp.valueOf(str));
		act.setNodeId(task.getTaskDefinitionKey());
		act.setNodeName(task.getName());
		commonService.save(act);
	}

	@Override
	public boolean isAllowReject(String taskid) {
		boolean b=true;
		Task task=taskService.createTaskQuery().taskId(taskid).singleResult();
		if(task!=null)
		{
			String description=task.getDescription();
			if(StringUtil.isNotEmpty(description)&&description.contains("isAllowReject"))
			{
				String isAllowTurnBack=JSONHelper.json2String(description, "isAllowReject");
				if(isAllowTurnBack!=null&&isAllowTurnBack.equals("N")){
					b=false;
				}
			}
		}
		return b;
	}
}
