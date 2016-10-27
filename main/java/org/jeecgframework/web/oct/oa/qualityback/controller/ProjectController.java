package org.jeecgframework.web.oct.oa.qualityback.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.oct.oa.interfacemanagement.entity.DocInterfaceCatalogueEntity;
import org.jeecgframework.web.oct.oa.qualityback.entity.OctEngineerManage;
import org.jeecgframework.web.oct.oa.qualityback.entity.OctProjectManage;
import org.jeecgframework.web.onlinedocsort.entity.OnlineDocSortEntity;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/***
 * 工程项目控制器
 * @author fany
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/projectController")
public class ProjectController {
	@Autowired
	private SystemService systemService;
	/**
	 *功能描述：获取工程列表数据
	 *@author 凡艺
	 *@since 2016-09-22
	 */
	@RequestMapping(params = "getProjectList")
	@ResponseBody
	public AjaxJson getProjectList(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		Map map=new LinkedHashMap<>();
		try{
			List<OctEngineerManage> octEngineerManageList=systemService.getList(OctEngineerManage.class);
			for(OctEngineerManage octEngineerManage:octEngineerManageList){
				map.put(octEngineerManage.getId(), octEngineerManage.getEngineerName());
			}
			j.setAttributes(map);
		}catch (Exception e) {
			e.printStackTrace();			
		}
		return j;
	}
	/**
	 *功能描述：根据工程名获取项目列表
	 *@author 凡艺
	 *@since 2016-09-22
	 */
	@RequestMapping(params = "getItemList")
	@ResponseBody
	public AjaxJson getItemList(HttpServletRequest request,String id){
		AjaxJson j = new AjaxJson();
		Map map=new LinkedHashMap<>();
		try{
			String hql="from OctProjectManage where octEngineerManage.id=?";
			List<OctProjectManage> list=systemService.findHql(hql, id);
			for(OctProjectManage octProjectManage:list){
				map.put(octProjectManage.getId(), octProjectManage.getProjectName());
			}
			j.setAttributes(map);
		}catch (Exception e) {
			e.printStackTrace();			
		}
		return j;
	}
	/**
	 *功能描述：根据项目名获取设备列表
	 *@author 凡艺
	 *@since 2016-09-22
	 */
	@RequestMapping(params = "getEquipmentList")
	@ResponseBody
	public AjaxJson getEquipmentList(HttpServletRequest request,String id){
		AjaxJson j = new AjaxJson();
		Map map=new LinkedHashMap<>();
		try{
			OctProjectManage octProjectManage=systemService.getEntity(OctProjectManage.class, id);
			map.put("a", octProjectManage.getMachineName());
			map.put("b", octProjectManage.getMachineType());
			j.setAttributes(map);
		}catch (Exception e) {
			e.printStackTrace();			
		}
		return j;
	}
	/**
	 *功能描述：获取用户手机号
	 *@author 凡艺
	 *@since 2016-09-22
	 */
	@RequestMapping(params = "getPhone")
	@ResponseBody
	public AjaxJson getPhone(HttpServletRequest request){
		AjaxJson j = new AjaxJson();	  
		TSUser user=ResourceUtil.getSessionUserName();	
		j.setMsg(user.getMobilePhone());
		return j;
	}
	/**
	 *功能描述：根据部门id获取部门负责人
	 *@author 凡艺
	 *@since 2016-09-22
	 */
	@RequestMapping(params = "getLeaders")
	@ResponseBody
	public AjaxJson getLeaders(HttpServletRequest request,String ids){
		AjaxJson j = new AjaxJson();
		String userids="";
		try{
			String[] departids=ids.split(";");
			for(String id:departids){
				TSDepart tSDepart=systemService.getEntity(TSDepart.class, id);
				userids+=tSDepart.getLeader().getUserName()+";";
			}
			j.setMsg(userids);
		}catch (Exception e) {
			e.printStackTrace();			
		}
		return j;
	}
	/**
	 *功能描述：审批时获取数据
	 *@author 凡艺
	 *@since 2016-09-22
	 */
	@RequestMapping(params = "getdata")
	@ResponseBody
	public AjaxJson getdata(HttpServletRequest request,String pj,String it){
		AjaxJson j = new AjaxJson();
		Map map=new LinkedHashMap<>();
		try{
			OctEngineerManage octEngineerManage=systemService.getEntity(OctEngineerManage.class, pj);
			OctProjectManage octProjectManage=systemService.getEntity(OctProjectManage.class, it);
			if(octEngineerManage!=null){
				map.put("a", octEngineerManage.getEngineerName());
			}
			if(octProjectManage!=null){
				map.put("b", octProjectManage.getProjectName());
				map.put("c", octProjectManage.getMachineName());
				map.put("d", octProjectManage.getMachineType());
			}
			j.setAttributes(map);
		}catch (Exception e) {
			e.printStackTrace();			
		}
		return j;
	}
}
