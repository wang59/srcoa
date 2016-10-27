package org.jeecgframework.web.oct.oa.sharedfile.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.oct.oa.sharedfile.entity.ShareDocument;
import org.jeecgframework.web.oct.oa.sharedfile.entity.ShareFile;
import org.jeecgframework.web.onlinedocsort.entity.OnlineDocSortEntity;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSDocument;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
/***
 * 共享文件控制器
 * @author fany
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/sharedFileController")
public class SharedFileController {
	@Autowired
	private SystemService systemService;
	/**
	 * 共享文件跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "onlinelist")
	public ModelAndView onlinelist(HttpServletRequest request) {
		return new ModelAndView("oct/oa/sharedfile/onlineDocAndSortList");
	}
	/**
	 * 文件夹管理跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "fileslist")
	public ModelAndView fileslist(HttpServletRequest request) {
		String type=request.getParameter("type");
		return new ModelAndView("oct/oa/sharedfile/fileslist");
	}
	/**
	 * 共享文件夹树形图数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	@ResponseBody
	public Object datagrid(ShareFile shareFile,HttpServletRequest request, HttpServletResponse response, TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(ShareFile.class);
		
		if (shareFile.getId() == null || StringUtils.isEmpty(shareFile.getId())) {
			cq.isNull("parent");
		} else {
			cq.eq("parent.id", shareFile.getId());
			shareFile.setId(null);
		}
		if(StringUtil.isNotEmpty(shareFile.getType())){
			cq.eq("type", shareFile.getType());
		}
		cq.add();
		List<TreeGrid> list = systemService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setIdField("id");
		treeGridModel.setSrc("id");
		treeGridModel.setTextField("fileName");
		treeGridModel.setParentText("parent_fileName");
		treeGridModel.setParentId("parent_id");
		treeGridModel.setChildList("child");
		treeGrids = systemService.treegrid(list, treeGridModel);
		 JSONArray jsonArray = new JSONArray();
	        for (TreeGrid treeGrid : treeGrids) {
	            jsonArray.add(JSON.parse(treeGrid.toJson()));
	        }
	        return jsonArray;
	}
	/**
	 * 添加共享文件夹页面跳转
	 * 
	 * 
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HttpServletRequest request,String type) {	
		
		return new ModelAndView("oct/oa/sharedfile/fileslist-add");
	}
	/**
	 * 共享文件夹树形图下拉列表
	 * 
	 * 
	 */
	@RequestMapping(params = "tree")
	@ResponseBody
	public List<ComboTree> tree(String selfCode,ComboTree comboTree, boolean isNew,HttpServletRequest request) {
		CriteriaQuery cq = new CriteriaQuery(ShareFile.class);
		cq.isNull("parent");
		String type=request.getParameter("type");
		if(StringUtil.isNotEmpty(type)){
			cq.eq("type", type);
		}
		cq.add();
		
		
		List<ShareFile> categoryList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		for (int i = 0; i < categoryList.size(); i++) {
			comboTrees.add(onlineDocSortEntityConvertToTree(categoryList.get(i)));
		}
		return comboTrees;
	}
	private ComboTree onlineDocSortEntityConvertToTree(ShareFile entity) {
		ComboTree tree = new ComboTree();
		tree.setId(entity.getId());
		tree.setText(entity.getFileName());
		if (entity.getChild()!= null && entity.getChild().size() > 0) {
			List<ComboTree> comboTrees = new ArrayList<ComboTree>();
			for (int i = 0; i < entity.getChild().size(); i++) {
				comboTrees.add(onlineDocSortEntityConvertToTree(entity.getChild().get(i)));
			}
			tree.setChildren(comboTrees);
		}
		return tree;
	}
	/**
	 * 添加共享文件夹
	 * 
	 * 
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(ShareFile shareFile, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		boolean flag = StringUtil.isEmpty(shareFile.getParent().getId());
		String message = "在线文档分类添加成功";
		try{
			if (flag) {
				shareFile.setParent(null);
			}
			systemService.save(shareFile);
		}catch(Exception e){
			e.printStackTrace();
			message = "在线文档分类添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 修改共享文件夹跳转页面
	 * 
	 * 
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request) {
		String id=request.getParameter("id");
		ShareFile shareFile=systemService.getEntity(ShareFile.class, id);
		request.setAttribute("shareFile", shareFile);
		return new ModelAndView("oct/oa/sharedfile/fileslist-update");
	}
	/**
	 * 修改共享文件夹
	 * 
	 * 
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(ShareFile shareFile, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		ShareFile file=systemService.getEntity(ShareFile.class, shareFile.getId());
		file.setFileName(shareFile.getFileName());
		systemService.updateEntitie(file);
		j.setMsg("修改成功");
		return j;
		
	}
	/**
	 * 删除共享文件夹
	 * 
	 * 
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(ShareFile shareFile, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		shareFile=systemService.getEntity(ShareFile.class, shareFile.getId());
		if(shareFile.getChild()!=null&&shareFile.getChild().size()>0){
			j.setMsg("请先删除子目录");	
		}else{
		  systemService.delete(shareFile);
		  j.setMsg("删除成功");
		}
		return j;
		
	}
	/**
	 * 文件添加跳转
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addFiles")
	public ModelAndView addFiles(HttpServletRequest req) {
		return new ModelAndView("oct/oa/sharedfile/documents");
	}
	/**
	 * 保存文件
	 *
	 * @param document
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "saveFiles", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveFiles(HttpServletRequest request, HttpServletResponse response, ShareDocument shareDocument) {
		AjaxJson j=new AjaxJson();
		Date date=new Date();
		TSUser user=ResourceUtil.getSessionUserName();
		shareDocument.setCreateTime(date);	
		shareDocument.setCreatename(user.getRealName());
		shareDocument.setCreateid(user.getId());
		String name=request.getParameter("cname");
		UploadFile uploadFile = new UploadFile(request, shareDocument);
		uploadFile.setCusPath("files");
		uploadFile.setSwfpath("swfpath");
		shareDocument = systemService.uploadFile(uploadFile);
		j.setMsg("保存成功");
		return j;
	}
	/**
	 * 文件列表跳转
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "documentslist")
	public ModelAndView listTurn(HttpServletRequest request) {
		return new ModelAndView("oct/oa/sharedfile/documentslist");
	}	
	/**
	 * 文件列表数据显示
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "documentList")
	public void documentList(ShareDocument shareDocument,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {		
		CriteriaQuery cq = new CriteriaQuery(ShareDocument.class, dataGrid);
		String documentId=request.getParameter("documentId");
		if(StringUtil.isNotEmpty(documentId)){
		   cq.eq("documentId", documentId);
		   cq.add();
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, shareDocument);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}	
	/**
	 * 附件预览读取
	 * 
	 * @return
	 */
	@RequestMapping(params = "viewFile")
	public void viewFile(HttpServletRequest request, HttpServletResponse response) {
		String fileid =oConvertUtils.getString(request.getParameter("fileid"));
		Class fileClass = MyClassLoader.getClassByScn("org.jeecgframework.web.oct.oa.sharedfile.entity.ShareDocument");// 附件的实际类
		Object fileobj = systemService.getEntity(fileClass, fileid);
		ReflectHelper reflectHelper = new ReflectHelper(fileobj);
		UploadFile uploadFile = new UploadFile(request, response);
		String contentfield = oConvertUtils.getString(request.getParameter("contentfield"), uploadFile.getByteField());
		byte[] content = (byte[]) reflectHelper.getMethodValue(contentfield);
		String path = oConvertUtils.getString(reflectHelper.getMethodValue("realpath"));
		String extend = oConvertUtils.getString(reflectHelper.getMethodValue("extend"));
		String attachmenttitle = oConvertUtils.getString(reflectHelper.getMethodValue("attachmenttitle"));
		uploadFile.setExtend(extend);
		uploadFile.setTitleField(attachmenttitle);
		uploadFile.setRealPath(path);
		uploadFile.setContent(content);
		//uploadFile.setView(true);
		systemService.viewOrDownloadFile(uploadFile);
	}
	/**
	 * 删除文档
	 *
	 * @param document
	 * @return
	 */
	@RequestMapping(params = "delDocument")
	@ResponseBody
	public AjaxJson delDocument(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		ShareDocument shareDocument = systemService.getEntity(ShareDocument.class, id);
		String message = "文件\'" + shareDocument.getAttachmenttitle() + "\'删除成功";
		systemService.delete(shareDocument);
		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}
	/**
	 * 文件树形图显示
	 *
	 * @param document
	 * @return
	 */
	@RequestMapping(params = "getFileInfo")
	@ResponseBody
	public AjaxJson getFileInfo(HttpServletRequest request, HttpServletResponse response){		
		AjaxJson j = new AjaxJson();
		String parentid = request.getParameter("parentid");
		List<ShareFile> shareFiles = new ArrayList<ShareFile>();
		StringBuffer hql = new StringBuffer(" from ShareFile t where 1=1 ");
		String type=request.getParameter("type");
		if(StringUtil.isNotEmpty(type)){
			hql.append(" and type='"+type+"'");
		}
		if(StringUtils.isNotBlank(parentid)){
			hql.append(" and parent.id = ?");
			shareFiles = this.systemService.findHql(hql.toString(), parentid);
		} else {
			hql.append(" and parent is null");
			shareFiles = this.systemService.findHql(hql.toString());
		}
		
		List<Map<String,Object>> dateList = new ArrayList<Map<String,Object>>();
		if(shareFiles.size()>0){
			Map<String,Object> map = null;
			String sql = null;
			 Object[] params = null;
			for(ShareFile shareFile:shareFiles){
				map = new HashMap<String,Object>();
				map.put("id", shareFile.getId());
				map.put("name", shareFile.getFileName());
				if(StringUtils.isNotBlank(parentid)){
					map.put("pId", parentid);
				} else{
					map.put("pId", null);
				}
				map.put("isParent",true);
				dateList.add(map);
			}
		}
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(dateList);
		j.setMsg(jsonArray.toString());
		return j;
	}
}
