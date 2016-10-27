package org.jeecgframework.web.oct.activiti.service.impl;

import java.util.Date;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.entity.autoform.AutoFormDbEntity;
import org.jeecgframework.web.cgform.entity.autoform.AutoFormEntity;
import org.jeecgframework.web.oct.activiti.service.interf.CustomFormService;
import org.jeecgframework.web.oct.activiti.service.interf.ProcessAgentService;
import org.jeecgframework.web.oct.activiti.service.interf.ProcessAuthorityService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("customFormService")
@Transactional
public class CustomFormServiceImpl implements CustomFormService {
	@Autowired
	private CommonService commonService;
	@Override
	public void deleteautoform(String formName, String id) {
		AutoFormEntity autoForm = commonService.findUniqueByProperty(AutoFormEntity.class, "formName",formName);
		List<AutoFormDbEntity> formDbList = commonService.findByProperty(AutoFormDbEntity.class, "autoFormId", autoForm.getId());
		if(formDbList.size()>0){
			AutoFormDbEntity autoFormDbEntity=formDbList.get(0);
			String formname=formDbList.get(0).getTbDbTableName();	
			String sql="delete from "+formname+" where id=?";
			commonService.executeSql(sql, id);
		}
		
	}	
	
}
