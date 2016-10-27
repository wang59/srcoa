package org.jeecgframework.web.oct.activiti.service.interf;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSUser;

/**
 *功能描述： 流程代理Service，增加和删除代理时去掉代理标志
 */
/*
 * 类名：ProcessAgentService
 *作者：凡艺
 *日期: 2016-07-22
 */
public interface ProcessAgentService extends CommonService {
	/**
	*功能描述：把代理的任务返回原任务人
	* @param creater – 任务创建人
	* @param agenter – 任务代理人
	* @param processdefinekey – 流程定义key
	* @author 凡艺
	* @since 2016-07-22
	 */
	public void deleteagent(TSUser creater, TSUser agenter,String processdefinekey);
	/**
	*功能描述：把任务交给代理人
	* @param creater – 任务创建人
	* @param agenter – 任务代理人
	* @param processdefinekey – 流程定义key
	* @author 凡艺
	* @since 2016-07-22
	 */
	public void addagent(TSUser creater, TSUser agenter,String processdefinekey);

}
