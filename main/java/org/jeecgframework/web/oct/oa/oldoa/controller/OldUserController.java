package org.jeecgframework.web.oct.oa.oldoa.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.jeecgframework.core.util.DynamicDBUtil;
import org.jeecgframework.core.util.HttpClientUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.oct.oa.interfacemanagement.entity.DocInterfaceCatalogueEntity;
import org.jeecgframework.web.oct.oa.oldoa.service.interf.OldUserService;
import org.jeecgframework.web.onlinedocsort.entity.OnlineDocSortEntity;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.util.IMServiceUtils;
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
@RequestMapping("/oldUserController")
public class OldUserController {
	
	@Autowired
	private OldUserService oldUserService;
	@Autowired
	private SystemService systemService;
	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping(params = "savenewpwd")
	@ResponseBody
	public AjaxJson savenewpwd(HttpServletRequest request,String psd) {
		AjaxJson j = new AjaxJson();
		TSUser user = ResourceUtil.getSessionUserName();
		//oldUserService.updatePSD(user.getUserName(), psd);
		return j;
	}
	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping(params = "savenewpwdad")
	@ResponseBody
	public AjaxJson savenewpwdad(HttpServletRequest request,String psd,String id) {
		AjaxJson j = new AjaxJson();
		TSUser user=systemService.getEntity(TSUser.class, id);
		oldUserService.updatePSD(user.getUserName(), psd);
		return j;
	}
	/**
	 * 登陆旧oa
	 * 
	 * @return
	 */
	@RequestMapping(params = "login")
	@ResponseBody
	public AjaxJson login(HttpServletRequest request,String psd,String userName) {
		AjaxJson j=new AjaxJson();
		String url="http://192.168.1.87:8080//sys/login.action";
		Map<String,String> map=new HashMap();
		 map.put("user.password",psd );
		 map.put("user.userName",userName );
		String value=HttpClientUtil.post(url, map);
		return j;
	}
	/**
	 * 获取考勤信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "findCheckWork")
	@ResponseBody
	public AjaxJson findCheckWork(HttpServletRequest request) {
		AjaxJson j=new AjaxJson();
		String nid=getoldId();		
		if(StringUtil.isEmpty(nid)){
			j.setSuccess(false);
			return j;
		}
		j.setMsg(nid);
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		String sql="select * from oct_attendance_days where user_id=? and att_Year=? and att_Month=? and att_Day=? order by create_Date limit 1";
		Map<String, Object> map=new HashMap();
		try{
		 map=(Map<String, Object>) DynamicDBUtil.findOne("oldoa", sql, nid,year,month,day);
		}catch (Exception e) {
		    j.setSuccess(false);
			return j;
		}
		Object time=map.get("create_Date");
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		Calendar calendarNew = Calendar.getInstance();
		calendarNew.setTime((Date)time);
		Date aa=calendarNew.getTime();
		Date bb=calendar.getTime();
		int a=calendar.compareTo(calendarNew);
		Map nmap=new HashMap();
		if(a<0){			
			nmap.put("check", true);
		}else{
			nmap.put("check", false);
		}
		j.setAttributes(nmap);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String str = sdf.format(time);//时间存储为字符串	
		j.setObj(str);
		
		return j;
	}
	/**
	 * 获取当前用户的旧oaid
	 * 
	 * @return
	 */
	public String getoldId(){
		String userName=ResourceUtil.getSessionUserName().getUserName();
		 String sql="select id,nid from t_s_base_user where username=?";
		   List<Map<String, Object>>  list=systemService.findForJdbc(sql, userName);
		   if(list.size()>0){
			   String nid=(String) list.get(0).get("nid");
			  if(StringUtil.isNotEmpty(nid)){
				  return nid;
			  }
		   }
		   return null;
	}
	/**
	 * 旧oa为url添加登陆用户
	 * 
	 * @return
	 */
	@RequestMapping(params = "url")
	public String findCurrentId(HttpServletRequest request,String url) {
	    String nid=getoldId();
	    if(StringUtil.isNotEmpty(nid)){
	    	url+=nid;
	    }else{
	    	url+="1";
	    }
	    return "redirect:"+url;
	}
	@RequestMapping(params = "updateUSerByOrg")
	public String updateUSerByOrg(HttpServletRequest request,String url) {
	   List<TSDepart> list=systemService.getList(TSDepart.class);
	   for(TSDepart tSDepart:list)
	   {
		   String hql="from TSUserOrg where tsDepart.id=?";
		   List<TSUserOrg> tSUserOrg=systemService.findHql(hql, tSDepart.getId());
		   for(TSUserOrg org:tSUserOrg){
			   TSUser user=org.getTsUser();
			   user.setLeader(tSDepart.getLeader());
			   user.setLeader_id(tSDepart.getLeader_id());
			   user.setManager(tSDepart.getManager());
			   user.setManager_id(tSDepart.getManager_id());
			   user.setDep_leader(tSDepart.getDep_leader());
			   user.setDep_leader_id(tSDepart.getDep_leader_id());
			   systemService.save(user);
		   }
	   }
	   return null;
	}
	
}
