<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/ztree/css/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<SCRIPT type="text/javascript">
	
	var setting = {
		async: {
			enable: true,
			url:"onlineDocInterfaceController.do?tree"
		},
		data: {
			key: {
				name: "text"
			}
		},
		callback: {
			onAsyncSuccess: zTreeOnAsyncSuccess,
			onClick: zTreeOnClick
		}
	};
	
	var treeObj;
	var currentNode = null;
	$(document).ready(function(){
		$.fn.zTree.init($("#ztree"), setting);
		treeObj = $.fn.zTree.getZTreeObj("ztree");
	});
	
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		treeObj.expandAll(true);
		currentNode = treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true).id;
	}
	
	function zTreeOnClick(event, treeId, treeNode) {
		if(treeNode.parentTId == null){
			$('#onlineDocList').datagrid('load', {});
		}else{
			$('#onlineDocList').datagrid('load', {    
				treeNode: treeNode.id
			});
		}
		currentNode = treeNode.id;
	};
	
	function getCurrentNode(){
		return currentNode;
	}
	
	
	
</SCRIPT>
<div class="easyui-layout" fit="true">
	<div region="west" style="width: 150px;" title="接口文档目录" split="true" collapsed="false">
		<div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false">
			<ul id="ztree" class="ztree"></ul>
		</div>
	</div>
	<div region="center" style="padding:0px;border:0px">
		<t:datagrid name="onlineDocList" checkbox="true" fitColumns="false" title="接口文档" actionUrl="onlineDocInterfaceSubDirectoryController.do?datagrid" idField="id" fit="true" queryMode="group">
			<t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
			<t:dgCol title="子类"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
			<t:dgCol title="父类"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
			<t:dgCol field="treeNode" title="接口模块" width="120" dictionary="t_s_doc_interface_catalogue,id,name" align="center"></t:dgCol>
			<t:dgCol title="服务名称"  field="serviceName"    queryMode="single"  width="120" align="center"></t:dgCol>
			<t:dgCol title="请求地址"  field="serviceUrl"    queryMode="single"  width="150" align="center"></t:dgCol>
			<t:dgCol title="请求方式"  field="requestMode"    queryMode="single"  width="100" align="center"></t:dgCol>
			<t:dgCol title="操作类型"  field="operationType"    queryMode="single"  width="120" align="center"></t:dgCol>
			<t:dgCol title="接口描述"  field="description"    queryMode="single"  width="120"></t:dgCol>
			<t:dgCol title="返回结果"  field="resulTant"    queryMode="single"  width="120"></t:dgCol>
			<t:dgCol title="创建人"    field="createName" queryMode="single"  width="120" align="center"></t:dgCol>
			<t:dgCol title="最后修改人" field="updateName"   hidden="false"  queryMode="single"  width="120" align="center"></t:dgCol>
			<t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd hh:mm:ss" queryMode="single"  width="160" align="center"></t:dgCol>
			<t:dgCol title="最后修改日期"  field="updateDate" formatter="yyyy-MM-dd hh:mm:ss" align="center" hidden="false"  queryMode="single"  width="160"></t:dgCol>

			<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
			<t:dgDelOpt title="删除" url="onlineDocInterfaceSubDirectoryController.do?doDel&id={id}"  urlStyle="color: red; padding-left: 5px;" />
			<t:dgToolBar title="录入" icon="icon-add" url="onlineDocInterfaceSubDirectoryController.do?addorupdate" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit" url="onlineDocInterfaceSubDirectoryController.do?addorupdate" funname="update"></t:dgToolBar>
			<t:dgToolBar title="批量删除"  icon="icon-remove" url="onlineDocInterfaceSubDirectoryController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search" url="onlineDocInterfaceSubDirectoryController.do?addorupdate" funname="detail"></t:dgToolBar>
			
		</t:datagrid>
	</div>
</div>
<script src = "webpage/jeecg/onlinedoc/onlineDocList.js"></script>		
<script type="text/javascript">
	
	
</script>