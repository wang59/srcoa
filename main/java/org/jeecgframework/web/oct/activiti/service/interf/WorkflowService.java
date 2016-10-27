package org.jeecgframework.web.oct.activiti.service.interf;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.jeecgframework.web.cgform.entity.autoform.AutoFormEntity;
import org.jeecgframework.web.oct.activiti.entity.ActRuTask;

/**
 *功能描述： 工作流Service，实现启动和提交流程等。
 */
/*
* 类名：WorkflowService
*作者：汪旭军
*日期: 2016-06-16
*/
public interface WorkflowService {

	/**
	*功能描述：启动流程。
	* @param businessKey – 申请单记录的ID
	* @param workflowName – 流程名
	* @param processVariables -流程变量
	* @return ProcessInstance – 返回流程实体对象
	* @author 汪旭军
	* @since 2016-06-16
	 */
    public ProcessInstance startWorkflow(String businessKey,String workflowName, Map<String,Object> processVariables,String actTaskBusinessid,String proid,String saveSerialnumber);
    
    
	/**
	*功能描述：创建流程任务和申请单的对应关系。
	* @param businessKey – 申请单记录的ID
	* @param processInstanceId 系流程实例的ID。
	* @param title – 标题
	* @author 汪旭军
	* @since 2016-06-16
	 */
   public boolean addTaskToBusiness(String businessKey, String processInstanceId, String title,String proid,String serialnumber);
   /**
	*功能描述：根据草稿创建流程任务和申请单的对应关系。
	* @param processInstanceId 系流程实例的ID。
	* @param title – 标题
	* @author 凡艺
	* @since 2016-07-28
	 */
   public boolean draftsaddTaskToBusiness(String actTaskBusinessid,String processInstanceId,String serialnumber);
   /**
	*功能描述：保存草稿信息
	* @param businessKey – 申请单记录的ID
	* @param title – 标题
	* @author 汪旭军
	* @since 2016-06-16
	 */
  public boolean savedrafts(String businessKey,String workflowName, String title,String proid);
 
  
	/**
	*功能描述：创建审批记录
	* @param businessKey – 申请单的ID
	* @param nodeName 流程节点名称。
	* @param nodeId 流程节点ID。
	* @param action – 操作
	* @param comment – 审批意见
	* @author 汪旭军
	* @since 2016-06-23
	 */
   public boolean addActApproveLog(String businessKey, String nodeName, String nodeId,String action,String comment);

	/**
	*功能描述：提交/完成任务
	* @param taskId – 任务的ID
	* @param variables - 传给流程的变量，影响流程的流转。
	* @author 汪旭军
	* @since 2016-06-24
	 */
  public boolean completeTask(String taskId, Map<String, Object> variables);
  
	/**
	*功能描述：修改审批人员、转办
	* @param taskId – 任务的ID
	* @param userId - 用户的ID。
	* @author 汪旭军
	* @since 2016-06-24
	 */
public boolean transferAssignee(String taskId,String userId);

/**
	*功能描述：判断当前的用户是否是指定的任务的处理人。
	*@author 汪旭军
	*@since 2016-7-6
	 */
public boolean isCurrentUserTask(ActRuTask actRuTask);

/**
*功能描述：根据流程定义ID,读取流程的document里面指定的变量，根据userName获取上级领导和一级领导等，组合成流程变量。
*@param processDefinitionId 流程定义的ID，需要带流程的版本等信息
*@param userName 用户名/ID
*@author 汪旭军
*@since 2016-7-14
 */
public Map<String,Object> processVariables(String processDefinitionIdOrKey, String userName,String mainTableSource,Map paramData,HttpServletRequest req);
/**
*功能描述：根据任务ID,读取流程的document里面指定的变量，根据userName获取上级领导和一级领导等，组合成流程变量。
*@param paramData url带的数据，mainTableSource自定义表单的数据源
*@param userName 用户名/ID
*@author 凡艺
*@since 2016-8-22
 */
public Map<String,Object> activitiVariables(String taskid, String userName,String mainTableSource,Map paramData,HttpServletRequest req);

/**
*功能描述：根据流程定义Key,获取流程定义对象。
*@param processDefinitionKey 流程定义的key(key不带版本号);
*@author 汪旭军
*@since 2016-7-14
 */
public ProcessDefinition getProcessDefinitionByKey(String processDefinitionKey);

/**
*功能描述：根据流程定义Id,获取流程定义对象。
*@param processDefinitionId 流程定义的id(带版本号);
*@author 汪旭军
*@since 2016-7-14
 */
public ProcessDefinition getProcessDefinitionById(String processDefinitionId);

/**
*功能描述：流程跳转
*@param processDefinitionId 流程定义的id(带版本号);
*@param activityId 目标节点id
*@author 凡艺
*@since 2016-7-22
 */
public void processJump( ProcessInstance processInstance,String activityId);
/**
*功能描述：保存所有可到达当前节点的节点
*@param current 当前节点id
*@param parallelGatewayactivity 根据连线向前遍历遇到的第一个并行网关并保存
*@param allINusertask 保存所有可到达当前节点的节点，key-节点id,value-节点名
*@author 凡艺
*@since 2016-7-22
 */
public void getAllUserTask(ActivityImpl current,List<ActivityImpl> parallelGatewayactivity,Map<String,String> allINusertask,int i);
/**
*功能描述：保存所有当前节点可到达节点
*@param current 当前节点id
*@param allOUTusertask 保存所有当前节点可到达节点，key-节点id,value-节点名
*@author 凡艺
*@since 2016-7-22
 */
public  void activityImpl(ActivityImpl current, Map<String,String> allOUTusertask,int i);
/**
*功能描述：获取一对网关中的任务节点
*@param current 当前节点id(保证节点是并行网关节点并以-end结尾
*@param excludeusertask 保存一对并行网关中间的任务节点，key-节点id,value-节点名
*@author 凡艺
*@since 2016-7-22
 */
public  void parallelgatewayactivity(ActivityImpl current,String id,Map<String,String> excludeusertask);
/**
*功能描述：获取结束节点
*@param definition 流程定义实体
*@return 当前流程的结束节点
*@author 凡艺
*@since 2016-7-22
 */
public  ActivityImpl endactivity(ProcessDefinitionEntity definition);
/**
*功能描述：获取可退回节点
*@return key-节点id,value-节点名
*@author 凡艺
*@since 2016-7-22
 */
public Map<String,String> turnbackActiviti(String taskId,HttpServletRequest request);
/**
*功能描述：获取抄送人id列表,taskid 任务id和proDefid 流程定义id 只需要一个
*@return variables-变量
*@author 凡艺
*@since 2016-7-22
 */
public List<String> getCopyTo(String taskid,Map<String, Object> variables,String proDefid);
/**
*功能描述：添加流程阅览记录
*@return key-节点id,value-节点名
*@author 凡艺
*@since 2016-7-22
 */
public void addReadLog(String id);
/**
*功能描述：添加抄送人数据
*@return key-节点id,value-节点名
*@author 凡艺
*@since 2016-7-22
 */
public void addCopyTo(String userid,String proinstid);
/**
*功能描述：添加流水号
*@return 
*@author 凡艺
*@since 2016-9-01
 */
public String addSerialNumber(Map<String,Map<String,Object>> dataMap,String formName,String proid,String businessId);
/**
*功能描述：获取流程结束时的抄送人员
*@return key-节点id,value-节点名
*@author 凡艺
*@since 2016-7-22
 */
public void getEndCopyTo(String processdef,String proIns);
}
