package  org.jeecgframework.web.oct.activiti.controller;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
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
import org.apache.commons.beanutils.BeanUtils;
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
import org.jeecgframework.web.oct.activiti.entity.ProcessAgent;
import org.jeecgframework.web.oct.activiti.entity.ProcessClass;
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
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**   
 * @Title: 流程图片控制器
 * @Description: 显示流程图
 * @author 凡艺
 * @date 2016-06-21
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/ flowsheetController")
public class FlowsheetController extends BaseController {
	/**
	 * Logger for this class
	 */
	private  Color HIGHLIGHT_COLOR = Color.RED;  
	private  Stroke THICK_TASK_BORDER_STROKE = new BasicStroke(3.0f);  
	@Autowired
	CommonService commonService;
	@Autowired
	private AutoFormServiceI autoFormService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private AutoFormDbServiceI autoFormDbService;
	@Autowired
	private DynamicDataSourceServiceI dynamicDataSourceServiceI;

	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;

	/**
	*功能描述：根据流程定义key值或流程定义id查询流程图
	*@param deployname 程定义key值或流程定义id
	*@author 凡艺
	*@since 2016-06-16
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "photo")
	public void photo(HttpServletRequest request,HttpServletResponse response,String deployname) throws Exception {
		response.setContentType("image/*");		  
		// 获取流程定义对应的查询对象
		ProcessDefinitionQuery query=repositoryService.createProcessDefinitionQuery();
		ProcessDefinition processDefinition;

		List<ProcessDefinition> list=query.processDefinitionKey(deployname).orderByProcessDefinitionVersion().desc().list();
		//流程的png名字
		if(list==null||list.size()==0)
		{
			processDefinition=repositoryService.createProcessDefinitionQuery().processDefinitionId(deployname).singleResult();
		}else{
			processDefinition=list.get(0);
		}
		//流程发布id				
		String deploymentId = processDefinition.getDeploymentId();  
		List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
		String imageName = null;
		for (String name : names) {
			System.out.println(name);
			if(name.endsWith("png")){
				imageName = name;		    
				break;
			}
		}
		System.out.println(names.size()+"  "+imageName);
		InputStream in =repositoryService.getResourceAsStream(deploymentId, imageName); 
		OutputStream out=response.getOutputStream();
		byte[] A=new byte[1024];
		int b;
		while((b=in.read(A))>-1)
		{
			out.write(A, 0, b);
		}
		out.close();
		in.close();				 		   		 		


	}
	/**
	 *功能描述：显示流程图（高亮显示当前节点）
	 *@author 凡艺
	 *@since 2016-06-27
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "photo2")
	public void photo2(HttpServletRequest request,HttpServletResponse response,String procInstId) throws Exception {
		response.setContentType("image/*");
		String procDefId=request.getParameter("procDefId");
		String processInstanceId=procInstId;	
		if(StringUtil.isEmpty(processInstanceId)){
			processInstanceId="";
		}
		//根据流程实例id获取流程实例
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()  
				.processInstanceId(processInstanceId).singleResult();  
		if(processInstance==null)
		{	
			ProcessDefinition definition=repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();		
			//流程的png名字
			String diagramResourceName = definition.getDiagramResourceName(); 
			//流程发布id
			String deploymentId = definition.getDeploymentId();
			//流程图的输入流
			InputStream in = repositoryService.getResourceAsStream(deploymentId, diagramResourceName); 
			OutputStream out=response.getOutputStream();
			byte[] A=new byte[1024];
			int b;
			while((b=in.read(A))>-1)
			{
				out.write(A, 0, b);
			}
			out.close();
			in.close();				 		   		 		
			return;			 
		}
		//根据流程实例获取流程定义id
		String processDefinitionId = processInstance.getProcessDefinitionId();  
		//获取流程定义实体
		ProcessDefinitionEntity definition =(ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
				.getDeployedProcessDefinition(processDefinitionId);  		
		//流程的png名字
		String diagramResourceName = definition.getDiagramResourceName();  		
		//流程发布id
		String deploymentId = definition.getDeploymentId();
		//流程图的输入流
		InputStream originDiagram = repositoryService.getResourceAsStream(deploymentId, diagramResourceName); 
		//将图片加载到内存中
		BufferedImage image = ImageIO.read(originDiagram);  
		// ，获取当前任务的所有节点
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);		
		System.out.println(activeActivityIds);
		//根据流程定义实体获取所有节点
		List<ActivityImpl> definitionActivities = getFlatAllActivities(definition);  
		List<ActivityImpl>  temp=definition.getActivities();		
		for (ActivityImpl activity : definitionActivities) {  
			if (activeActivityIds.contains(activity.getId())) {  
				decorate(image,activity.getX(), activity.getY(), activity.getWidth(), activity.getHeight());  
			}  
		}  
		OutputStream out=response.getOutputStream();
		//获取diagramResourceName的图片格式png
		String formatName = getDiagramExtension(diagramResourceName);  	    
		ImageIO.write(image, formatName, out);  
	}  
	private  void decorate(BufferedImage image, int x, int y, int width, int height) {  
		Graphics2D g = image.createGraphics();  
		try {  
			drawHighLight(x, y, width, height, g);  
		} finally {  
			g.dispose();  
		}  
	}  
	private  String getDiagramExtension(String diagramResourceName) {  
		//	      return diagramResourceName.substring(diagramResourceName.lastIndexOf(".") + 1);  
		return FilenameUtils.getExtension(diagramResourceName);  
	}  
	private  void drawHighLight(int x, int y, int width, int height, Graphics2D g) {  
		Paint originalPaint = g.getPaint();  
		Stroke originalStroke = g.getStroke();  

		g.setPaint(HIGHLIGHT_COLOR);  
		g.setStroke(THICK_TASK_BORDER_STROKE);  

		RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, 20, 20);  
		g.draw(rect);  

		g.setPaint(originalPaint);  
		g.setStroke(originalStroke);  
	}  
	public  List<ActivityImpl> getFlatAllActivities(ProcessDefinitionEntity processDefinition) {  
		List<ActivityImpl> result = new ArrayList<ActivityImpl>();  
		//获取当前任务的所有节点
		List<ActivityImpl> ancestors=processDefinition.getActivities();
		flattenActivities(result, ancestors);  
		return result;  
	}  
	private int i=0;
	private  void flattenActivities(List<ActivityImpl> container, List<ActivityImpl> ancestors) {  
		if (ancestors.size() > 0) {  
			for (ActivityImpl activity : ancestors) {  
				flattenActivities(container, activity.getActivities());  
			}  
			container.addAll(ancestors);  
		}  
	} 		 
}