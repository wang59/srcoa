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
public interface CustomFormService {

	/**
	*功能描述：删除表单数据
	* @param formName – 动态表单名
	* @param id – 申请单id
	* @author 凡艺
	* @since 2016-07-28
	 */
    public void deleteautoform(String formName,String id);
    
   
}
