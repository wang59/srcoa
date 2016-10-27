package  org.jeecgframework.web.oct.activiti.controller;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.utility.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.BLOB;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Property;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DynamicDBUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SqlUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.cgform.entity.autoform.*;
import org.jeecgframework.web.cgform.service.autoform.AutoFormDbServiceI;
import org.jeecgframework.web.cgform.service.autoform.AutoFormServiceI;
import org.jeecgframework.web.cgform.util.AutoFormCommUtil;
import org.jeecgframework.web.cgform.util.AutoFormTemplateParseUtil;
import org.jeecgframework.web.cgform.util.TemplateUtil;
import org.jeecgframework.web.oct.activiti.entity.ActreProcess;
import org.jeecgframework.web.oct.activiti.entity.ProcessAgent;
import org.jeecgframework.web.oct.activiti.entity.ProcessClass;
import org.jeecgframework.web.oct.activiti.entity.ProcessPrefix;
import org.jeecgframework.web.oct.activiti.service.interf.WorkflowService;
import org.jeecgframework.web.system.pojo.base.DynamicDataSourceEntity;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
import org.jeecgframework.web.system.service.DynamicDataSourceServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.jeecgframework.web.oct.activiti.entity.TSProcess;
import org.jeecgframework.web.oct.activiti.entity.ProcessUser;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**   
 * @Title: 新建流程
 * @Description: 表单表
 * @author 凡艺
 * @date 2016-06-29
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/createAppController")
public class CreateAppController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CreateAppController.class);
	@Autowired
	private CommonService commonService;
	@Autowired
	private SystemService systemService;
	 /**
	*功能描述：返回流程信息跳转页面
	*@author 凡艺
 * @throws Exception 
 * @throws IllegalAccessException 
	*@since 2016-08-25
	 */
 @RequestMapping(params = "createprocessList")
	public ModelAndView createprocessList(HttpServletRequest request) throws IllegalAccessException, Exception {
	 ModelAndView mv=new ModelAndView();
	 mv.setViewName("oct/activiti/process/createprocess/processList");
	return mv;
	}
	/**
	 *功能描述：返回流程分类页面
	 *@author 凡艺
	 *@since 2016-06-22
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "list")
	public ModelAndView List(HttpServletRequest request) {
		return new ModelAndView("oct/activiti/process/createprocess/pro-class");
	}
	/**
	 *功能描述：返回流程实体页面
	 *@author 凡艺
	 *@since 2016-06-22
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "processlist")
	public ModelAndView processList(HttpServletRequest request) {
		return new ModelAndView("oct/activiti/process/createprocess/pro-impl");
	}
	/**
	 *功能描述：返回流程分类表数据
	 *@author 凡艺
	 *@since 2016-06-22
	 */
	@RequestMapping(params = "creategrid")
	public void processAgentgrid(ProcessClass processclass,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ProcessClass.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, processclass,request.getParameterMap());
		this.commonService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 *功能描述：返回具体流程表数据
	 *@author 凡艺
	 *@since 2016-06-22
	 */
	@RequestMapping(params = "processgrid")
	public void processgrid(TSProcess processImpl,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSProcess.class, dataGrid);
		//查询条件组装器
		cq.eq("status", "1");
		cq.add();
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, processImpl,request.getParameterMap());
		this.commonService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 *功能描述：流程类别增加页面
	 *@author 凡艺
	 *@since 2016-06-22
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest request) {	
			return new ModelAndView("oct/activiti/process/createprocess/pro-add");
	
	}
	/**
	 *功能描述：流程实体增加页面
	 *@author 凡艺
	 *@since 2016-06-22
	 */
	@RequestMapping(params = "processadd")
	public ModelAndView processadd(HttpServletRequest request) {
		    List<ProcessClass> list=systemService.getList(ProcessClass.class);
		    request.setAttribute("processclasslist", list);
			return new ModelAndView("oct/activiti/process/createprocess/pro-impl-add");
	
	}
	/**
	 *功能描述：保存流程类别
	 *@author 凡艺
	 *@since 2016-06-22
	 */
	@RequestMapping(params = "saveProcess")
	@ResponseBody
	public AjaxJson saveProcess(HttpServletRequest request,java.util.Date begintime,ProcessClass processClass) {	
		AjaxJson j = new AjaxJson();
		commonService.save(processClass);
		String message =  "保存成功";
		j.setMsg(message);
			return j;
	
	}
	/**
	 *功能描述：保存流程实体
	 *@author 凡艺
	 *@since 2016-06-22
	 */
		@RequestMapping(params = "save")
		@ResponseBody
		public AjaxJson save(HttpServletRequest request,TSProcess processImpl) {	
			
			AjaxJson j = new AjaxJson();
			if(StringUtil.isNotEmpty(processImpl.getId()))
			{
				processImpl.setStatus("1");
				processImpl.setUrl("workFlowAutoFormController.do?viewContent&formName="+processImpl.getForm()+"&id=&op=add&workflowName="+processImpl.getProcessdefineid());
				commonService.updateEntitie(processImpl);
				ProcessPrefix processPrefix=commonService.findUniqueByProperty(ProcessPrefix.class, "proId", processImpl.getId());
				if(processPrefix!=null){
					if(!processPrefix.getPrefix().equals(processImpl.getPrefix())){
						processPrefix.setPrefix(processImpl.getPrefix());
						commonService.updateEntitie(processPrefix);
					}
				}else{
					 ProcessPrefix processprefix=new ProcessPrefix();
					 processprefix.setPrefix(processImpl.getPrefix());
					 processprefix.setProId(processImpl.getId());
					 processprefix.setCurrentValue("1001");
					    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					    try {
							Date date=sdf.parse(sdf.format(new Date()));
							processprefix.setCurrentTime(date);
							commonService.save(processprefix);					
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			   		 
				}
			}else{
				processImpl.setStatus("1");
				processImpl.setUrl("workFlowAutoFormController.do?viewContent&formName="+processImpl.getForm()+"&id=&op=add&workflowName="+processImpl.getProcessdefineid());
			    String proId= (String) commonService.save(processImpl);
			    ProcessPrefix processPrefix=new ProcessPrefix();
			    processPrefix.setPrefix(processImpl.getPrefix());
			    processPrefix.setProId(proId);
			    processPrefix.setCurrentValue("1001");
			    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			    try {
					Date date=sdf.parse(sdf.format(new Date()));
					processPrefix.setCurrentTime(date);
					commonService.save(processPrefix);					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			   		    
			}
			String message =  "保存成功";
			j.setMsg(message);
				return j;
		
		}
		/**
		 *功能描述：删除流程类别
		 *@author 凡艺
		 *@since 2016-06-22
		 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson delete(HttpServletRequest request,String id) {	
		AjaxJson j = new AjaxJson();
		ProcessClass processClass=commonService.getEntity(ProcessClass.class, id);	
		commonService.delete(processClass);
		String message =  "删除成功";
		j.setMsg(message);
			return j;
	
	}
	/**
	 *功能描述：删除流程实体
	 *@author 凡艺
	 *@since 2016-06-22
	 */
	@RequestMapping(params = "processdel")
	@ResponseBody
	public AjaxJson processdel(HttpServletRequest request,String id) {	
		AjaxJson j = new AjaxJson();
		TSProcess processImpl=commonService.getEntity(TSProcess.class, id);	
		processImpl.setStatus("0");
		commonService.save(processImpl);
		List list=commonService.findByProperty(ProcessPrefix.class, "proId", id);
		if(list!=null&&list.size()>0){
			commonService.delete(list.get(0));
		}
		String message =  "删除成功";
		j.setMsg(message);
			return j;
	
	}
	/**
	 *功能描述：更新流程实体
	 *@author 凡艺
	 *@since 2016-06-22
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(HttpServletRequest request,TSProcess processImpl) {	
		if (StringUtil.isNotEmpty(processImpl.getId())) {
			 List<ProcessClass> list=systemService.getList(ProcessClass.class);			  
			processImpl = systemService.getEntity(TSProcess.class, processImpl.getId());			
			request.setAttribute("impl", processImpl);
			request.setAttribute("processclasslist", list);	
		
		}
			return new ModelAndView("oct/activiti/process/createprocess/pro-impl-add");	
	}
	/**
	 *功能描述：加入收藏
	 *@author 凡艺
	 *@since 2016-06-22
	 */
	@RequestMapping(params = "collection")
	@ResponseBody
	public AjaxJson collection(HttpServletRequest request,String id,String type) {	
		AjaxJson j=new AjaxJson();
		TSUser user=ResourceUtil.getSessionUserName();
		TSProcess impl=systemService.getEntity(TSProcess.class, id);	
		ProcessUser process=new ProcessUser();
		process.setProcess(impl);
		process.setUser(user);
		process.setType(type);
		systemService.save(process);
		return j;		
	}
	/**
	 *功能描述：取消收藏
	 *@author 凡艺
	 *@since 2016-06-22
	 */
		@RequestMapping(params = "delcollection")
		@ResponseBody
		public AjaxJson delcollection(HttpServletRequest request,String id) {	
			AjaxJson j=new AjaxJson();
			commonService.deleteEntityById(ProcessUser.class, id);
				return j;	
		}
		@RequestMapping(params = "processselectgrid")
		public void processselectgrid(ActreProcess actreProcess,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
			CriteriaQuery cq = new CriteriaQuery(ActreProcess.class, dataGrid);
			//查询条件组装器
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, actreProcess,request.getParameterMap());
			this.commonService.getDataGridReturn(cq, true);
			TagUtil.datagrid(response, dataGrid);
		}
		//流程选择跳转页面
		@RequestMapping(params = "processselect")
		public ModelAndView processselect(HttpServletRequest request) {					
				return new ModelAndView("oct/activiti/process/createprocess/pro-select");	
		}
		/**
		 *功能描述：返回全部流程信息
		 *@author 凡艺
		 *@since 2016-08-25
		 */	
		@RequestMapping(params = "getAll")
		public ModelAndView getAll(HttpServletRequest request) {					
			List<ProcessClass> proList=systemService.getList(ProcessClass.class);	
		     ModelAndView mv=new ModelAndView();
			 Map map=new LinkedHashMap();//全部流程数据		
			 List<TSProcess> list;
			 TSUser user=ResourceUtil.getSessionUserName();
			 for(ProcessClass pro:proList) {
				 String st=pro.getId();	
				 String hql="from TSProcess pi where pi.type.id=? and pi.status='1' order by pi.sort ";
				  list=systemService.findHql(hql, st);	
				 for(TSProcess tspro:list ){
					String sql="select * from t_s_pro_user where pro_id=? and user_id=?";
				    List<Map<String, Object>> collectioninfo =  systemService.findForJdbc(sql,tspro.getId(),user.getId());
					 if(collectioninfo!=null&&collectioninfo.size()>0){
						 tspro.setCollection(true); 
					 }else{
						 tspro.setCollection(false);  
					 }
				 }
				 if(list.size()>0){
				 map.put(pro.getName(), list);
				 }
			 }
			 mv.addObject("pro_map", map);
			 mv.setViewName("oct/activiti/process/createprocess/allprocesslist");
			 return mv;
	}
		/**
		 *功能描述：获取我收藏的流程数据
		 *@author 凡艺
		 *@since 2016-08-25
		 */	
		@RequestMapping(params = "getCollection")
		public ModelAndView getCollection(HttpServletRequest request) {	
			List<ProcessClass> proList=systemService.getList(ProcessClass.class);
			ModelAndView mv=new ModelAndView();
			 Map mapUser=new LinkedHashMap();//全部流程数据		
			 List<TSProcess> list;
			 TSUser user=ResourceUtil.getSessionUserName();
			 for(ProcessClass pro:proList) {
				String hql="from ProcessUser a where a.user.id=?  and a.type=?";
				list=systemService.findHql(hql, user.getId(),pro.getId());
				if(list.size()>0){
				mapUser.put(pro.getName(), list);
				}
			 }
			 mv.addObject("user_map", mapUser);	
			 mv.setViewName("oct/activiti/process/createprocess/collectionprocessList");
			 return mv;
		}
		/**
		 *功能描述：获取常用流程流程数据
		 *@author 凡艺
		 *@since 2016-08-25
		 */	
		@RequestMapping(params = "getCommon")
		public ModelAndView getCommon(HttpServletRequest request) {	
			String hql="from TSProcess a where a.common=? and a.status='1'";
			List<TSProcess> commonVos=systemService.findHql(hql, "1");
			ModelAndView mv=new ModelAndView();
			 mv.addObject("commonVos", commonVos);
			 mv.setViewName("oct/activiti/process/createprocess/commonprocessList");
			 return mv;
		}
}
