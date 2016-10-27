package org.jeecgframework.web.oct.activiti.interceptors;

import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;

public  class PreActivitiCmdInterceptor extends AbstractCommandInterceptor {
	@Override
	public <T> T execute(CommandConfig commandConfig, Command<T> command) {
		System.out.println("流程任务监听："+command.getClass().getName());
		return next.execute(commandConfig, command);
	}
}
