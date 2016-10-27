package org.jeecgframework.web.oct.activiti.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.oct.activiti.entity.ProcessAgent;
import org.jeecgframework.web.oct.activiti.entity.TSProcess;
import org.jeecgframework.web.oct.activiti.service.interf.ProcessAgentService;
import org.jeecgframework.web.system.pojo.base.TSNotice;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**   
 * @Title: Controller
 * @Description: 流程代理控制器
 * @author 凡艺
 * @date 2016-06-17 17:12:40
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/processAgentController")
public class ProcessAgentController {
	
	@Autowired
	CommonService commonService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	ProcessAgentService processAgentService;
	/**
	*功能描述：流程代理跳转页面
	*@author 凡艺
	*@since 2016-07-22
	 */
	@RequestMapping(params = "processAgentList")
	public ModelAndView toDoList(HttpServletRequest request) {
		request.setAttribute("status", 1);
		return new ModelAndView("oct/activiti/process/processagent/processagentList");
	}
	/**
	*功能描述：流程代理分页显示功能
	*@author 凡艺
	*@since 2016-07-22
	 */
	@RequestMapping(params = "processAgentgrid")
	public void processAgentgrid(ProcessAgent processAgent,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ProcessAgent.class, dataGrid);
		cq.notEq("status", "4");
		//查询条件组装器
		String userId=ResourceUtil.getSessionUserName().getId();
		cq.eq("creater.id", userId);
		cq.add();
		//获取页数=1000时强制为10
		if(cq.getPageSize()==10000){
			cq.setPageSize(10);
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, processAgent,request.getParameterMap());
		this.commonService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	*功能描述：流程代理添加跳转
	*@author 凡艺
	*@since 2016-07-22
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest request) {	
	
			return new ModelAndView("oct/activiti/process/processagent/processagent-add");
	
	}
	
	/**
	 * 流程代理查看
	 * 
	 * @return
	 */
	@RequestMapping(params = "detail")
	public ModelAndView goUpdate(ProcessAgent processAgent, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(processAgent.getId())) {
			processAgent = processAgentService.getEntity(ProcessAgent.class, processAgent.getId());
			req.setAttribute("processAgent", processAgent);
			String id = req.getParameter("id");
			TSUser creater = processAgent.getCreater();
			TSUser tSUser = processAgent.getAgent();
			TSProcess tSProcess = processAgent.getPath();
			Date start = processAgent.getBegintime();
			Date end = processAgent.getEndtime();
			Calendar calendars = Calendar.getInstance();
			Calendar calendare = Calendar.getInstance();
			calendars.setTime(start);
			calendare.setTime(end);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String starttime = sdf.format(calendars.getTime());
			String endtime = sdf.format(calendare.getTime());
			String style = processAgent.getStyle();
			String status = processAgent.getStatus();
			String agentStyle;
			String agentStatus;
			if (style.equals("1")) {
				agentStyle = "是";
			} else {
				agentStyle = "否";
			}
		
			if(status.equals("0")){
				agentStatus = "未开始";
			}else if(status.equals("1")){
				agentStatus = "进行中";
			}else{
				agentStatus = "已结束";
			}		
			req.setAttribute("starttime", starttime);
			req.setAttribute("endtime", endtime);
			req.setAttribute("username", tSUser.getRealName());
			req.setAttribute("path",tSProcess.getName());
			req.setAttribute("type",processAgent.getType());
			req.setAttribute("creater",creater.getRealName());
			req.setAttribute("agentStyle", agentStyle);
			req.setAttribute("agentStatus", agentStatus);
		}
		return new ModelAndView("oct/activiti/process/processagent/processagent-edit");
	}
	
	/**
	*功能描述：保存代理
	*@author 凡艺
	*@since 2016-07-22
	 */
	@RequestMapping(params = "saveProcessAgent")
	@ResponseBody
	public AjaxJson saveProcessAgent(HttpServletRequest request,java.util.Date begintime,ProcessAgent processAgent) {	
		processAgent.setStatus("1");
		ProcessAgent temp=processAgent;
		String scope=request.getParameter("scope");
		AjaxJson j = new AjaxJson();
		String message =  "保存成功";
		List checklist=new ArrayList();
		Date date=new Date();
		if(scope.equals("0"))
		{
			String ids=request.getParameter("ids");
			String[] idss=ids.split(",");
			for(String id:idss)
			{
				processAgent=new ProcessAgent();
				processAgent.setAgent(temp.getAgent());processAgent.setBegintime(temp.getBegintime());
				processAgent.setCreater(temp.getCreater());processAgent.setEndtime(temp.getEndtime());
				processAgent.setStatus(temp.getStatus());processAgent.setStyle(temp.getStyle());
				TSProcess process=commonService.getEntity(TSProcess.class, id);
				processAgent.setType(process.getType().getName());
				processAgent.setPath(process);
				commonService.save(processAgent);	
				//添加代理标示
				if(processAgent.getStyle().equals("1")&&date.after(processAgent.getBegintime())&&date.before(processAgent.getEndtime()))
				{
					TSUser creater=commonService.getEntity(TSUser.class, processAgent.getCreater().getId());
					TSUser agenter=commonService.getEntity(TSUser.class, processAgent.getAgent().getId());
					String processdefinekey=processAgent.getPath().getProcessdefineid();
					processAgentService.addagent(creater, agenter, processdefinekey);
				}
			}
		}else if(scope.equals("1")){
			List<TSProcess> list=commonService.getList(TSProcess.class);
			//去掉已经代理的选择，防止二次代理
			Date currentdate=new Date();
			String hql="from ProcessAgent where agent.id=? and endtime>=? ";	
			List<ProcessAgent> agenylist=commonService.findHql(hql, processAgent.getCreater().getId(),currentdate);
			List<String> agentids=new ArrayList();
			for(ProcessAgent agent:agenylist)
			{
				boolean b=false;
				if(processAgent.getBegintime().before(agent.getEndtime())&&processAgent.getBegintime().after(agent.getBegintime())){
					b=true;
				}else if(processAgent.getEndtime().before(agent.getEndtime())&&processAgent.getEndtime().after(agent.getBegintime())){
					b=true;
				}
				if(b){
					agentids.add(agent.getPath().getId());
				}
			}
			for(TSProcess process:list)
			{
				if(agentids.contains(process.getId()))
				{
					continue;
				}
				processAgent=new ProcessAgent();
				processAgent.setAgent(temp.getAgent());processAgent.setBegintime(temp.getBegintime());
				processAgent.setCreater(temp.getCreater());processAgent.setEndtime(temp.getEndtime());
				processAgent.setStatus(temp.getStatus());processAgent.setStyle(temp.getStyle());
				processAgent.setType(process.getType().getName());
				processAgent.setPath(process);
				commonService.save(processAgent);		
				//添加代理标示
				if(processAgent.getStyle().equals("1")&&date.after(processAgent.getBegintime())&&date.before(processAgent.getEndtime()))
				{
					TSUser creater=commonService.getEntity(TSUser.class, processAgent.getCreater().getId());
					TSUser agenter=commonService.getEntity(TSUser.class, processAgent.getAgent().getId());
					String processdefinekey=processAgent.getPath().getProcessdefineid();
					processAgentService.addagent(creater, agenter, processdefinekey);
				}
			}
		}else{
			 message =  "保存失败";			
		}	
	
		
		j.setMsg(message);
			return j;
	
	}
	/**
	*功能描述：删除代理
	*@author 凡艺
	*@since 2016-07-22
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson delete(HttpServletRequest request,String id) {	
		AjaxJson j = new AjaxJson();	
		ProcessAgent processAgent=commonService.getEntity(ProcessAgent.class, id);	
		processAgent.setStatus("4");
		processAgentService.deleteagent(processAgent.getCreater(), processAgent.getAgent(), processAgent.getPath().getProcessdefineid());
		commonService.save(processAgent);
		String message =  "撤销成功";
		j.setMsg(message);
			return j;
	
	}
	/**
	*功能描述：流程选择跳转页面
	*@author 凡艺
	*@since 2016-07-22
	 */
	@RequestMapping(params = "processSelect")
	public ModelAndView processSelect(HttpServletRequest request,ProcessAgent processAgent) {	
		ModelAndView mv=new ModelAndView();
		String ids=request.getParameter("ids");
		mv.addObject("ids", ids);
		//去掉已经代理的选择，防止二次代理
		Date date=new Date();
		String hql="from ProcessAgent where agent.id=? and endtime>=? ";	
		List<ProcessAgent> list=commonService.findHql(hql, processAgent.getCreater().getId(),date);
		StringBuffer buffer=new StringBuffer();
		for(ProcessAgent agent:list)
		{
			boolean b=false;
			if(processAgent.getBegintime().before(agent.getEndtime())&&processAgent.getBegintime().after(agent.getBegintime())){
				b=true;
			}else if(processAgent.getEndtime().before(agent.getEndtime())&&processAgent.getEndtime().after(agent.getBegintime())){
				b=true;
			}
			if(b){
				buffer.append(agent.getPath().getId()+",");
			}
		}
		mv.addObject("agentlist",buffer);
		System.out.println(processAgent.getCreater().getId()+"--"+processAgent.getAgent().getId()+"--"+processAgent.getBegintime()+"--"+processAgent.getEndtime());
		mv.setViewName("oct/activiti/process/processagent/processagent-select");
		return mv;
	
	}
	/**
	*功能描述：流程选择分页显示功能
	*@author 凡艺
	*@since 2016-07-22
	 */
	@RequestMapping(params = "processdatagrid")
	public void processdatagrid(TSProcess tsProcess,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,String agentlist) {	
		CriteriaQuery cq = new CriteriaQuery(TSProcess.class, dataGrid);	
		String[] ids= agentlist.split(",");
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tsProcess,request.getParameterMap());
		System.out.println(agentlist);
        for(String id:ids)
        {
        	cq.notEq("id", id);
        }
        cq.add();
		this.commonService.getDataGridReturn(cq, true);
		
		TagUtil.datagrid(response, dataGrid);
	}

	
}
