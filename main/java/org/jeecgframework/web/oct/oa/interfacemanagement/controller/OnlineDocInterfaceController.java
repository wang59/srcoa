package org.jeecgframework.web.oct.oa.interfacemanagement.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.oct.oa.interfacemanagement.entity.DocInterfaceCatalogueEntity;
import org.jeecgframework.web.onlinedocsort.entity.OnlineDocSortEntity;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/***
 * 接口目录控制器
 * @author lizh
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/onlineDocInterfaceController")
public class OnlineDocInterfaceController {
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private ICommonDao commonDao ;
	private String message;
	
	
	
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("oct/oa/onlinedocinterface/docInterfaceList");
	}
   
	
	@RequestMapping(params = "datagrid")
	@ResponseBody
	public List<TreeGrid> datagrid(DocInterfaceCatalogueEntity onlineDocSort,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DocInterfaceCatalogueEntity.class, dataGrid);
		if (onlineDocSort.getId() == null || StringUtils.isEmpty(onlineDocSort.getId())) {
			cq.isNull("parent");
		} else {
			cq.eq("parent.id", onlineDocSort.getId());
			onlineDocSort.setId(null);
		}
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, onlineDocSort, request.getParameterMap());
		List<DocInterfaceCatalogueEntity> list = commonDao.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setIdField("id");
		treeGridModel.setSrc("id");
		treeGridModel.setTextField("name");
		treeGridModel.setParentText("parent_name");
		treeGridModel.setParentId("parent_id");
		treeGridModel.setChildList("list");
		treeGrids = systemService.treegrid(list, treeGridModel);
		return treeGrids;
	}
    
	
	
	@RequestMapping(params = "tree")
	@ResponseBody
	public List<ComboTree> tree(String selfCode,ComboTree comboTree, boolean isNew) {
		CriteriaQuery cq = new CriteriaQuery(DocInterfaceCatalogueEntity.class);
		cq.isNull("parent");
		cq.add();
		List<DocInterfaceCatalogueEntity> categoryList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		for (int i = 0; i < categoryList.size(); i++) {
			comboTrees.add(onlineDocSortEntityConvertToTree(categoryList.get(i)));
		}
		return comboTrees;
	}

	private ComboTree onlineDocSortEntityConvertToTree(DocInterfaceCatalogueEntity entity) {
		ComboTree tree = new ComboTree();
		tree.setId(entity.getId());
		tree.setText(entity.getName());
		if (entity.getList() != null && entity.getList().size() > 0) {
			List<ComboTree> comboTrees = new ArrayList<ComboTree>();
			for (int i = 0; i < entity.getList().size(); i++) {
				comboTrees.add(onlineDocSortEntityConvertToTree(entity.getList().get(i)));
			}
			tree.setChildren(comboTrees);
		}
		return tree;
	}
	
	
	
	/**
	 * 接口目录分类新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAddOrUpdate(DocInterfaceCatalogueEntity onlineDocSort,HttpServletRequest request) {
		
		String id = request.getParameter("id");
		
		if (StringUtil.isNotEmpty(id)) {
			onlineDocSort = commonDao.getEntity(DocInterfaceCatalogueEntity.class, id);
			request.setAttribute("onlineDocSortPage", onlineDocSort);
		}
		return new ModelAndView("oct/oa/onlinedocinterface/docInterfaceAdd");
	}
	
	
	/**
	 * 接口目录分类编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(DocInterfaceCatalogueEntity onlineDocSort, HttpServletRequest request) {
		
		String id = request.getParameter("id");
		
		if (StringUtil.isNotEmpty(id)) {
			onlineDocSort = commonDao.getEntity(DocInterfaceCatalogueEntity.class, id);
			request.setAttribute("onlineDocSortPage", onlineDocSort);
		}
		return new ModelAndView("oct/oa/onlinedocinterface/docInterfaceUpdate");
	}
	
	
	/**
	 * 接口目录文档分类
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(DocInterfaceCatalogueEntity onlineDocSort, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		onlineDocSort = systemService.getEntity(DocInterfaceCatalogueEntity.class, onlineDocSort.getId());
		message = "接口目录分类删除成功";
		try{
			commonDao.delete(onlineDocSort);
			
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "接口目录分类删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(DocInterfaceCatalogueEntity onlineDocSort, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		boolean flag = StringUtil.isEmpty(onlineDocSort.getParent().getId());
		message = "接口目录分类添加成功";
		try{
			if (flag) {
				onlineDocSort.setParent(null);
			}
			commonDao.save(onlineDocSort);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "接口目录分类添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(DocInterfaceCatalogueEntity onlineDocSort, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		boolean flag = StringUtil.isEmpty(onlineDocSort.getParent().getId());
		
		message = "接口目录分类更新成功";
		DocInterfaceCatalogueEntity t = commonDao.get(DocInterfaceCatalogueEntity.class, onlineDocSort.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(onlineDocSort, t);
			if (flag) {
				t.setParent(null);
			}
			commonDao.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "接口目录分类更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
