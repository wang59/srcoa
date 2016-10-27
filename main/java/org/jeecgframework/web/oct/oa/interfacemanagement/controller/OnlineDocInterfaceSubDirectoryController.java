package org.jeecgframework.web.oct.oa.interfacemanagement.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.oct.oa.interfacemanagement.entity.DocInterfaceCatalogueEntity;
import org.jeecgframework.web.oct.oa.interfacemanagement.entity.DocInterfaceSubdirectoryEntity;
import org.jeecgframework.web.oct.oa.interfacemanagement.entity.OnlineDocInterfaceEntity;
import org.jeecgframework.web.oct.oa.interfacemanagement.entity.OnlineDocInterfaceListEntity;
import org.jeecgframework.web.oct.oa.interfacemanagement.service.OnlineDocInterfaceServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 接口录入控制器
 * @author lizh
 *
 */

@Scope("prototype")
@Controller
@RequestMapping("/onlineDocInterfaceSubDirectoryController")
public class OnlineDocInterfaceSubDirectoryController {

	
	@Autowired
	private OnlineDocInterfaceServiceI onlineDocInterfaceService;
	
	
	@Autowired
	private ICommonDao commonDao ;
	@Autowired
	private SystemService systemService;
	private String message;
	
	
	
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("oct/oa/onlinedocinterface/docInterfaceSubDirectoryList");
	}
	
	

	@RequestMapping(params = "datagrid")
	public void datagrid(DocInterfaceSubdirectoryEntity onlineDoc,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DocInterfaceSubdirectoryEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, onlineDoc, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		commonDao.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	
	
	/**
	 * 接口录入编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView goUpdate(DocInterfaceSubdirectoryEntity onlineDoc, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(onlineDoc.getId())) {
			onlineDoc = commonDao.getEntity(DocInterfaceSubdirectoryEntity.class, onlineDoc.getId());
			req.setAttribute("onlineDocPage", onlineDoc);
		}
		return new ModelAndView("oct/oa/onlinedocinterface/docInterfaceSubDirectoryUpdateOrSave");
	}
	
	
	
	/**
	 * 批量删除接口录入
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "在线文档删除成功";
		try{
			for(String id:ids.split(",")){
				DocInterfaceSubdirectoryEntity onlineDoc = systemService.getEntity(DocInterfaceSubdirectoryEntity.class, 
				id
				);
				onlineDocInterfaceService.delMain(onlineDoc);
				
			}
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "在线文档删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	 
	 
	 /**
		 * 添加接口录入
		 * 
		 * @param ids
		 * @return
		 */
		@RequestMapping(params = "doAdd")
		@ResponseBody
		public AjaxJson doAdd(DocInterfaceSubdirectoryEntity onlineDoc,OnlineDocInterfaceListEntity oDocInterfaceListEntity, HttpServletRequest request) {
			AjaxJson j = new AjaxJson();
			String userName=ResourceUtil.getSessionUserName().getUserName();
			List<OnlineDocInterfaceEntity> list=oDocInterfaceListEntity.getOnlineDocInterfaceEntityList();
			Date date=new Date();
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = simpleDateFormat.format(date);
		
			Date date2 = null;
			try {
				date2 = simpleDateFormat.parse(dateString);
			} catch (ParseException e1) {
				
				e1.printStackTrace();
			}
			if(StringUtil.isEmpty(onlineDoc.getId())){
			String treeNode=request.getParameter("treeNode");
			DocInterfaceCatalogueEntity onlineDocSortEntity=systemService.get(DocInterfaceCatalogueEntity.class, treeNode);
			onlineDoc.setCreateDate(date2);
			onlineDoc.setUpdateDate(date2);
			onlineDoc.setCreateName(userName);
			onlineDoc.setUpdateName(userName);
			onlineDoc.setTreeNode(treeNode);
			
			onlineDoc.setUpdateBy(userName);
			
		
			message = "接口录入添加成功";
			try{
				
				onlineDocInterfaceService.addMain(onlineDoc, list);
				commonDao.save(onlineDoc);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}catch(Exception e){
				e.printStackTrace();
				message = "接口录入添加失败";
				throw new BusinessException(e.getMessage());
			   }
			}else{
				message = "接口录入更新成功";
				DocInterfaceSubdirectoryEntity t = commonDao.get(DocInterfaceSubdirectoryEntity.class, onlineDoc.getId());
				try {
					onlineDoc.setUpdateDate(date2);
					onlineDoc.setUpdateName(userName);
					MyBeanUtils.copyBeanNotNull2Bean(onlineDoc, t);
					onlineDocInterfaceService.updateMain(t, list);
					
					//onlineDocService.updateEntitie(onlineDoc);
					systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
				} catch (Exception e) {
					e.printStackTrace();
					message = "接口录入更新失败";
					throw new BusinessException(e.getMessage());
				}
			}
			j.setMsg(message);
			return j;
		}

		
		
		
		@RequestMapping(params = "onlinedocinterfacelist")
		public ModelAndView findOnlineDocinTerfaceList(DocInterfaceSubdirectoryEntity onlineDoc, HttpServletRequest req)
		{
			if(StringUtil.isNotEmpty(onlineDoc.getId())){

				List<OnlineDocInterfaceEntity> onlineDocInterfaceEntity =  onlineDocInterfaceService.findByProperty(OnlineDocInterfaceEntity.class, "code", onlineDoc.getId());
				req.setAttribute("onlineDocInterfaceEntityList", onlineDocInterfaceEntity);
			
			}
			return new ModelAndView("oct/oa/onlinedocinterface/onlineDocInterfaceList");
		}
		
		
		/**
		 * 删除接口录入
		 * 
		 * @return
		 */
		@RequestMapping(params = "doDel")
		@ResponseBody
		public AjaxJson doDel(DocInterfaceSubdirectoryEntity onlineDoc, HttpServletRequest request) {
			AjaxJson j = new AjaxJson();
			
			message = "接口录入删除成功";
			try{
				onlineDocInterfaceService.delMain(onlineDoc);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}catch(Exception e){
				e.printStackTrace();
				message = "接口录入删除失败";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}
}
