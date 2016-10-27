package org.jeecgframework.web.oct.activiti.timer;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
import org.jeecgframework.web.oct.activiti.entity.ActRuTask;
import org.jeecgframework.web.oct.activiti.entity.ProcessAgent;
import org.jeecgframework.web.oct.activiti.service.interf.ProcessAuthorityService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.util.IMServiceUtils;

/**
 * @Title: 定时任务和Controller
 * @Description: 到期定时通知。每天早上通知一次。
 * @author 汪旭军
 * @date 2016-09-28 17:12:40
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/dueDateAndNotice")
public class DueDateAndNotice {
	@Autowired
	protected TaskService taskService;
	@Autowired
	private SystemService systemService;
	@Autowired
	IdentityService identityService;
	@Autowired
	protected RepositoryService repositoryService;


	/**
	 *功能描述：到期定时通知。每天早上通知一次。
	 *@author 汪旭军
	 *@since 2016-09-28
	 */
	public void checkDueDateAndNotice(){
		String configInfo="";//配置信息
		String dueDaysNoticeType="";//通知类型
		String dueDateNoticeTitle="";//通知标题
		String dueDateNoticeContent="";//通知内容
		String dueDaysNoticeUsers="";//通知用户
		String dueDaysNoticeRoles="";//通知角色
		String dueDaysNoticeFields="";//通知字段
		String hql="from ActRuTask where dueDate>'"+DateUtils.getCurDate()+"'";
		List<ActRuTask> listActRuTask=systemService.findByQueryString(hql);
		Iterator it=listActRuTask.iterator();
		//循环发送提醒消息
		while(it.hasNext()){
			ActRuTask actRuTask =(ActRuTask)it.next();
			configInfo =actRuTask.getDescription();
			if(StringUtil.isNotEmpty(configInfo) && configInfo.indexOf("dueDaysNoticeType")>0 && configInfo.indexOf("dueDateNoticeTitle")>0){
					dueDaysNoticeType=JSONHelper.json2String(configInfo,"dueDaysNoticeType");
					dueDateNoticeTitle=JSONHelper.json2String(configInfo,"dueDateNoticeTitle");
					dueDateNoticeContent=getValueFormJson(configInfo,"dueDateNoticeContent");
					dueDaysNoticeUsers=getValueFormJson(configInfo,"dueDaysNoticeUsers");
					dueDaysNoticeRoles=getValueFormJson(configInfo,"dueDaysNoticeRoles");
					dueDaysNoticeFields=getValueFormJson(configInfo,"dueDaysNoticeFields");
					if(dueDaysNoticeType.equals("1")){
						//给用户发送IM消息
						String[] userNames = dueDaysNoticeUsers.split(";");
						for(String userName:userNames){
							IMServiceUtils.sendMsg2IMPopup(dueDateNoticeContent, "callbackUrl", userName, "type", null);
						}
						
						//给角色发送IM消息
						String[] roleNames = dueDaysNoticeRoles.split(";");
						for(String roleName:roleNames){
							IMServiceUtils.sendMsg2IMPopup(dueDateNoticeContent, "callbackUrl", roleName, "type", null);
						}
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
	@RequestMapping(params = "toParseActivitiRoleWeb12")
	public String toParseActivitiRoleWeb12() {
		checkDueDateAndNotice();
		return "执行完成！";
	}

	/**
	 *功能描述：获取配置的属性值。
	 *@author 汪旭军
	 *@since 2016-09-28
	 */
	public String getValueFormJson(String configInfo,String attributeName) {
		String temValue="";
		if(configInfo.indexOf(attributeName)>0){
			temValue=JSONHelper.json2String(configInfo,attributeName);
		}else{
			temValue="";
		}
		return temValue;
	}
}
