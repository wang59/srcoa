package org.jeecgframework.web.oct.activiti.timer;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.istack.logging.Logger;

import jodd.util.StringUtil;

import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.oct.activiti.entity.ActRuIdentitylink;
import org.jeecgframework.web.oct.activiti.entity.ActTaskBusiness;
import org.jeecgframework.web.oct.activiti.entity.ProcessAgent;
import org.jeecgframework.web.oct.activiti.service.interf.ProcessAuthorityService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.util.HttpClientUtil;
import org.jeecgframework.web.system.util.IMServiceUtils;

/**
 * @Title: 定时任务和Controller
 * @Description: 定时解析待签收的角色，并自动的使用第一个人签收。
 * @author 汪旭军
 * @date 2016-05-13 17:12:40
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/activitiTimer")
public class ActivitiTimer {
	@Autowired
	protected TaskService taskService;
	@Autowired
	private SystemService systemService;
	@Autowired
	IdentityService identityService;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	private ProcessAuthorityService processAuthorityService;
	private static Logger logger = Logger.getLogger(ActivitiTimer.class);
	/**
	 *功能描述：定时解析待签收的角色，并自动的使用第一个人签收。
	 *@author 汪旭军
	 *@since 2016-07-11
	 */
	public void toParseActivitiRole(){
		String taskId="";
		String roleCode="";
		List<User> users;
		User user;		
		//获取所有的待签收任务
		ActRuIdentitylink actRuIdentitylink = new ActRuIdentitylink();
		String hql="from ActRuIdentitylink aRIL where type='candidate' and aRIL.actRuTask.assignee is null or aRIL.actRuTask.assignee=''";
		List<ActRuIdentitylink> listActRuIdentitylink=systemService.findByQueryString(hql);
		Iterator it =listActRuIdentitylink.iterator();
		while(it.hasNext()){

			actRuIdentitylink=(ActRuIdentitylink)it.next();
			//获取任务ID
			taskId=actRuIdentitylink.getActRuTask().getId();
			String defid=actRuIdentitylink.getActRuTask().getProcDefId();
			//获取角色编码
			roleCode=actRuIdentitylink.getGroupId();
			//根据角色编码，获取一个用户ID；
			users = identityService.createUserQuery().memberOfGroup(roleCode).list();
			if(users.size()>0){//只获取角色里面的第一个用户签收任务。
				user =users.get(0);//只获取角色里面的第一个用户签收任务。
				sendIm(user.getId(),taskId);
				this.agent(user, defid, taskId);	
				processAuthorityService.isAllowSkip(taskId);
			}else{
				users=identityService.createUserQuery().userId(roleCode).list();					
				if(users.size()>0){
					user =users.get(0);//只获取角色里面的第一个用户签收任务。
					sendIm(user.getId(),taskId);
					this.agent(user, defid, taskId);
					processAuthorityService.isAllowSkip(taskId);
				}
			}
		}
	}


	/**
	 *功能描述：解析待签收的角色，并自动的使用第一个人签收。(通过网页调用）
	 *调用地址：activitiTimer.do?toParseActivitiRoleWeb
	 *@author 汪旭军
	 *@since 2016-07-11
	 */
	@RequestMapping(params = "toParseActivitiRoleWeb")
	public String toParseActivitiRoleWeb() {
		toParseActivitiRole();
		return "执行完成！";
	}
	/**
	 *功能描述：任务的处理，包含代理，签收，设置过期日期。
	 *@author 凡艺
	 *@since 2016-08-18
	 */
	public void agent(User user,String defid,String taskId)
	{

		String userName=user.getId();
		List<TSUser> list=systemService.findByProperty(TSUser.class, "userName", userName);
		if(list.size()>0)
		{						
			TSUser tsuser=list.get(0);	
			Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
			String description=task.getDescription();//当前节点的配置信息
			java.util.Date date=new Date();	
			//processdefineid 待办对应的流程的ID
			String key=repositoryService.createProcessDefinitionQuery().processDefinitionId(defid).singleResult().getKey();					

			//设置过期的日期-------------------------------
			setDueDate(task,description);

			//代理处理-----------------------------------
			String hql2="from ProcessAgent where creater.id=? and begintime<=? and endtime>=? and path.processdefineid=? and status='1'";	
			List<ProcessAgent> agentlist=systemService.findHql(hql2, tsuser.getId(),date,date,key);				
			if(agentlist.size()>0)
			{							
				ProcessAgent processAgent=agentlist.get(0);
				String taskName=task.getName();
				taskName+="(代理"+tsuser.getRealName()+")";
				task.setName(taskName);	//修改环节名称			
				if(StringUtil.isNotEmpty(description)&&!description.contains("processAgentFor"))
				{											
					StringBuffer buffer=new StringBuffer(description.substring(0, description.lastIndexOf("}")));
					buffer.append(",");
					buffer.append("\"processAgentFor\":\""+userName+"\"");
					buffer.append("}");
					task.setDescription(buffer.toString());
				}else if(StringUtil.isEmpty(description)){
					StringBuffer buffer=new StringBuffer();
					buffer.append("{");
					buffer.append("\"processAgentFor\":\""+userName+"\"");
					buffer.append("}");
					task.setDescription(description);		
				}
				taskService.saveTask(task);

				//签收------------------------------------
				taskService.claim(taskId,processAgent.getAgent().getUserName());
			}else{
				//签收
				taskService.claim(taskId, user.getId());
			}
		}else{
			taskService.claim(taskId, user.getId());
		}			
	}

	/**
	 *功能描述：首先判断是否需要给任务设置过期日期，如果需要就设置过期的日期
	 *@author 汪旭军
	 *@since 2016-09-28
	 */
	public void setDueDate(Task task,String description){
		Date tDueDate = task.getDueDate();
		//是否设置过期日期
		if(StringUtil.isNotEmpty(description) && description.indexOf("hasDueDate")>0 && description.indexOf("dueDays")>0 && tDueDate ==null){
			String hasDueDate=JSONHelper.json2String(description, "hasDueDate");
			String dueDays=JSONHelper.json2String(description, "dueDays");//过期天数
			int intDueDays=0;
			String dueDate="";
			if(StringUtil.isNotEmpty(hasDueDate)  && StringUtil.isNotEmpty(dueDays) && hasDueDate.toUpperCase()=="Y"){
				intDueDays=Integer.parseInt(dueDays);
				String curDate=DateUtils.getCurDate();
				try {
					dueDate=DateUtils.formatAddDate(curDate, "", intDueDays);
					task.setDueDate(DateUtils.str2Date(dueDate, DateUtils.date_sdf));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void sendIm(String userName,String taskId){
		if(!userName.trim().equals("")){
			String sql="select id,nid from t_s_base_user where username=? and !isnull(nid)";
			List<Map<String, Object>>  list=systemService.findForJdbc(sql, userName);
			if(list.size()>0){
				String nid=(String) list.get(0).get("nid");
				if(StringUtil.isNotEmpty(nid)){
					Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
					String hql="from ActTaskBusiness where processInstanceId=?";
					List<ActTaskBusiness> alist=systemService.findHql(hql, task.getProcessInstanceId());
					if(alist.size()>0){
				    	IMServiceUtils.sendMsgIMPopup(alist.get(0).getBusinessCreateName()+"-"+alist.get(0).getBusinessTitle(), "workFlowAutoFormController.do?newapproval&id="+taskId, nid);
					}
				}
			}
		}else{
			logger.info("Task Id:"+taskId+" 解析得到的userName是空，请处理。类：ActivitiTimer，方法：sendIm");
		}
	}

}
