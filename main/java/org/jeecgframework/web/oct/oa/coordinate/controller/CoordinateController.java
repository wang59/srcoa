package org.jeecgframework.web.oct.oa.coordinate.controller;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.p3.core.common.utils.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.oct.activiti.entity.ActRuTask;
import org.jeecgframework.web.oct.oa.coordinate.entity.Coordinate;
import org.jeecgframework.web.oct.oa.coordinate.vo.CoordinateVo;
import org.jeecgframework.web.oct.oa.hr.entity.OctHrPost;
import org.jeecgframework.web.oct.oa.qualityback.entity.QualityBack;
import org.jeecgframework.web.oct.oa.qualityback.entity.QualityBackEntity;
import org.jeecgframework.web.oct.oa.qualityback.vo.QualityBackEntityVo;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门协调单控制器
 *
 * @author fany
 */
@Scope("prototype")
@Controller
@RequestMapping("/coordinateController")
public class CoordinateController {
	@Autowired
	private CommonService commonService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	
	/***
	 * 根据表单id查询协调人数据
	 * @param sid
	 * @throws IOException 
	 */
	@RequestMapping(params="perAdd")
	@ResponseBody
	public  String perAdd(String id){
		String st="";
		String sql="select a.coordinate_per from oct_coordinate a where id=?";	
		List<Map<String,Object>> list=commonService.findForJdbc(sql, id);
        if(list.size()>0){
        	st=(String) list.get(0).get("coordinate_per");
        	if(StringUtil.isEmpty(st)){
        		st="";
        	}
        }
        return st;
	}
	/**
	 *功能描述：任务协调单跳转界面
	 *@author 凡艺
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 *@since 2016-10-26
	 */
	@RequestMapping(params = "coordinateList")
	public ModelAndView coordinateList(HttpServletRequest request) throws IllegalAccessException, Exception {
		return new ModelAndView("oct/oa/coordinate/coodinatelist");
	}
	/**
	 *功能描述：任务协调单跳转界面
	 *@author 凡艺
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 *@since 2016-10-26
	 */
	@RequestMapping(params = "coordinateListform")
	public ModelAndView coordinateListform(HttpServletRequest request) throws IllegalAccessException, Exception {
		return new ModelAndView("oct/oa/coordinate/coodinatelistform");
	}
	/**
	 *功能描述：任务协调单跳转界面
	 *@author 凡艺
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 *@since 2016-10-26
	 */
	@RequestMapping(params = "coordinateListAll")
	public ModelAndView coordinateListAll(HttpServletRequest request) throws IllegalAccessException, Exception {
		return new ModelAndView("oct/oa/coordinate/coodinatelistAll");
	}
	/**
	 *功能描述：返回具体质任务协调单数据
	 *@author 凡艺
	 *@since  2016-10-26
	 */
	@RequestMapping(params = "coordinategrid")
	public void coordinategrid(Coordinate coordinate,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(Coordinate.class, dataGrid);
		String type=request.getParameter("type");
		cq.eq("tSProcess.id", "4028d24a57f987fa0157f9d581ff00aa");
		if(StringUtil.isEmpty(type)){
			cq.eq("businessCreateBy", ResourceUtil.getSessionUserName().getUserName());
		}
		cq.notEq("status", "0");
		cq.notEq("status", "4");
		cq.add();
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, coordinate,request.getParameterMap());
		this.commonService.getDataGridReturn(cq, true);
		List<Coordinate> list=dataGrid.getResults();
		for(Coordinate coordinates:list){
			if(StringUtil.isEmpty(coordinates.getCoordinateEntity().getDostatus())){
				coordinates.getCoordinateEntity().setDostatus("1");
			}
			if(coordinates.getStatus().equals("1")){
				List<Task> tasklist=taskService.createTaskQuery().processInstanceId(coordinates.getProcessInstanceId()).list();
				String assign="";
				for(Task task:tasklist){
					List<TSUser> userList=systemService.findByProperty(TSUser.class, "userName", task.getAssignee());
					if(userList!=null&&userList.size()==1){
					assign+=userList.get(0).getRealName()+",";
					}
				}
				coordinates.setCurrentTodo(assign);
			}
		}
		
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 *功能描述：质量反馈单报表数据
	 *@author 凡艺
	 *@since 2016-10-26
	 */
	@RequestMapping(params = "formgrid")
	public void processAgentgrid(Coordinate coordinate,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(Coordinate.class, dataGrid);
		//查询条件组装器
		cq.eq("tSProcess.id", "4028d24a57f987fa0157f9d581ff00aa");
		cq.notEq("status", "0");
		cq.notEq("status", "4");
		cq.add();
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, coordinate,request.getParameterMap());
		this.commonService.getDataGridReturn(cq, true);
		List<Coordinate> list=dataGrid.getResults();
		dataGrid.setResults(this.getDate(list));
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,Date start,Date end) {
		String status=request.getParameter("status");
		StringBuffer buffer=new StringBuffer("from Coordinate where tSProcess.id=? and status!=? and status!=?");
		if(start!=null){
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
			String st=sf.format(start);
			buffer.append(" and businessCreateName>='"+st+"'");
		}
		if(end !=null){
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
			String st=sf.format(end);
			buffer.append(" and businessCreateName<='"+ st+"'");
		}
		if(!StringUtil.isEmpty(status)&&!status.equals(",")){
			buffer.append(" and status='"+status+"'");
		}
		String hql=buffer.toString();
		List<Coordinate> list=commonService.findHql(hql,"4028d24a57f987fa0157f9d581ff00aa","0","4");		
		List<CoordinateVo> volist=this.getDate(list);
		modelMap.put(NormalExcelConstants.FILE_NAME,"任务协调单列表");
		modelMap.put(NormalExcelConstants.CLASS,CoordinateVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("任务协调单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,volist);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	public List<CoordinateVo> getDate(List<Coordinate> list){
		List<CoordinateVo> coos=new ArrayList();
		for(Coordinate coordinate:list)
		{
			CoordinateVo vo=new CoordinateVo();
			vo.setBusinessCreateName(coordinate.getBusinessCreateName());
			vo.setBusinessCreateTime(coordinate.getBusinessCreateTime());
			vo.setDepart(coordinate.getCoordinateEntity().getReceivedep());
			String userNames=coordinate.getCoordinateEntity().getCoordinate_id();
			String realNames="";
			if(!StringUtil.isEmpty(userNames)){
				for(String st:userNames.split(";")){
					List<TSUser> users=commonService.findByProperty(TSUser.class, "userName", st);
					if(users.size()>0){
						realNames+=users.get(0).getRealName()+",";
					}
				}
			}
			vo.setSerialnumber(coordinate.getSerialnumber());
			vo.setDoman(realNames);
			vo.setExplain(coordinate.getCoordinateEntity().getExplain());
			vo.setNeesdate(coordinate.getCoordinateEntity().getNeesdate());
			vo.setProjectname(coordinate.getCoordinateEntity().getProjectname());
			String status=coordinate.getStatus();
			if("1".equals(status)){
				status="已启动";
			}else if("2".equals(status)){
				status="拒绝";
			}else if("3".equals(status)){
				status="已结束";
			}
			vo.setStatus(status);
			coos.add(vo);
		}
		return coos;
	}
}
