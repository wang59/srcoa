package org.jeecgframework.web.oct.oa.qualityback.service.interf;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.jeecgframework.web.cgform.entity.autoform.AutoFormEntity;
import org.jeecgframework.web.oct.activiti.entity.ActRuTask;
import org.jeecgframework.web.oct.oa.qualityback.entity.QualityBackEntity;
import org.jeecgframework.web.oct.oa.qualityback.vo.QualityBackEntityVo;

/**
 *功能描述： 质量反馈单
 */
/*
* 
*作者：fany
*日期: 2016-09-28
*/
public interface QualityBackService {
	/**
	*功能描述：转换数据
	* @author 凡艺
	* @since 2016-09-28
	 */
    public List<QualityBackEntityVo> getData(List<QualityBackEntity> list);
   

}
