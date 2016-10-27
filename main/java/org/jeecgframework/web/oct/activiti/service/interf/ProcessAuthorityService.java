package org.jeecgframework.web.oct.activiti.service.interf;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.jeecgframework.web.cgform.entity.autoform.AutoFormEntity;
import org.jeecgframework.web.oct.activiti.entity.ActRuTask;

/**
 *功能描述： 工作流Service，按钮权限控制
 */
/*
 * 类名：ProcessService
 *作者：凡艺
 *日期: 2016-06-16
 */
public interface ProcessAuthorityService {
	/**
	*功能描述：转办按钮权限控制。
	* @param taskid – 任务id
	* @author 凡艺
	* @since 2016-07-22
	 */
	public  boolean isAllowTurnToDo(String taskid);
	/**
	*功能描述：协办按钮权限控制。
	* @param taskid – 任务id
	* @author 凡艺
	* @since 2016-07-22
	 */
	public  boolean isAllowAssit(String taskid);
	/**
	*功能描述：退回按钮权限控制。
	* @param taskid – 任务id
	* @author 凡艺
	* @since 2016-07-22
	 */
	public  boolean isAllowTurnBack(String taskid);
	/**
	*功能描述：打印按钮权限控制。
	*使用方法isAllowPrint(taskid,null,null)根据任务id查询
	*使用方法isAllowPrint(null,activityKey,definitionid)根据节点id查询
	* @param taskid – 任务id
	* @param activityKey – 节点id
	* @param definitionid – 流程定义id
	* @author 凡艺
	* @since 2016-07-22
	 */
	public  boolean isAllowPrint(String taskid,String activityKey,String definitionid);
	/**
	*功能描述：退回按钮权限控制。
	* @param activityKey – 节点id
	* @param definitionid – 流程定义id
	* @author 凡艺
	* @since 2016-07-22
	 */
	
	public  boolean isAllowGetBack(String activityKey,String definitionid);
	/**
	*功能描述：重复说审批跳过功能
	* @param taskid – 任务id
	* @author 凡艺
	* @since 2016-08-04
	 */
	public  void isAllowSkip(String taskid);
	/**
	*功能描述：驳回按钮权限控制。
	* @param taskid – 任务id
	* @author 凡艺
	* @since 2016-08-24
	 */
	public  boolean isAllowReject(String taskid);
}
