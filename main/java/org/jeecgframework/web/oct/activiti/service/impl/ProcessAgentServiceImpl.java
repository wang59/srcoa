package org.jeecgframework.web.oct.activiti.service.impl;

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
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.oct.activiti.service.interf.ProcessAgentService;
import org.jeecgframework.web.oct.activiti.service.interf.ProcessAuthorityService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("processAgentService")
@Transactional
public class ProcessAgentServiceImpl extends CommonServiceImpl implements ProcessAgentService {
	@Autowired
	private CommonService commonService;	
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	@Override
	public void deleteagent(TSUser creater, TSUser agenter, String processdefinekey) {
		List<Task> tasklist=taskService.createTaskQuery().taskAssignee(agenter.getUserName()).processDefinitionKey(processdefinekey).list();
		if(tasklist!=null&&tasklist.size()>0)
		{
			String userName="("+"代理"+creater.getRealName();
			for(Task task:tasklist)
			{
				String taskName=task.getName();
				if(taskName!=null&&taskName.contains(userName))
				{
					task.setAssignee(creater.getUserName());
					taskName=taskName.substring(0, taskName.lastIndexOf(userName));
					task.setName(taskName);
					//修改任务描述，去掉代理人字段
					String description=task.getDescription();															
					String processAgentFor="\"processAgentFor\":"+"\""+creater.getUserName()+"\"";
					if(StringUtil.isNotEmpty(description)&&description.contains(","+processAgentFor)){
						description=description.replace(","+processAgentFor, "");
						
					}else if(StringUtil.isNotEmpty(description)&&description.contains("{"+processAgentFor+"}")){
						description="";
					}
					task.setDescription(description);						
					taskService.saveTask(task);
				}
			}
		}
		
	}
	@Override
	public void addagent(TSUser creater, TSUser agenter, String processdefinekey) {
		List<Task> tasklist=taskService.createTaskQuery().taskAssignee(creater.getUserName()).processDefinitionKey(processdefinekey).list();
		if(tasklist!=null&&tasklist.size()>0)
		{
			String userName="("+"代理"+creater.getRealName()+")";
			for(Task task:tasklist)
			{
				//修改任务名
				String taskName=task.getName();
				task.setAssignee(agenter.getUserName());
				taskName+=userName;			
				task.setName(taskName);
				//任务描述添加代理字段
				String description=task.getDescription();
				if(StringUtil.isNotEmpty(description)&&!description.contains("processAgentFor"))
				{											
					StringBuffer buffer=new StringBuffer(description.substring(0, description.lastIndexOf("}")));
					buffer.append(",");
					buffer.append("\"processAgentFor\":\""+creater.getUserName()+"\"");
					buffer.append("}");
					task.setDescription(buffer.toString());
				}else if(StringUtil.isEmpty(description)){
					StringBuffer buffer=new StringBuffer();
					buffer.append("{");
					buffer.append("\"processAgentFor\":\""+creater.getUserName()+"\"");
					buffer.append("}");
					task.setDescription(description);		
				}
				
				taskService.saveTask(task);			
			}
		}
	}	
}
