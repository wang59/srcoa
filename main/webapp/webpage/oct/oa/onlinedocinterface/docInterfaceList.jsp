<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="onlineDocSortList" treegrid="true" pagination="false" actionUrl="onlineDocInterfaceController.do?datagrid" idField="id"  title="接口目录管理">
	<t:dgCol title="名称" field="name" treefield="text" width="300"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="onlineDocInterfaceController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="onlineDocInterfaceController.do?goAdd" funname="onlineDocSort"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="onlineDocInterfaceController.do?goUpdate" funname="onlineDocSort"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = ""></script>		
 <script type="text/javascript">
	
 	function getSelectNodeId(){
 		var rowData = $('#onlineDocSortList').datagrid('getSelected');
		if (rowData) {
			return rowData.id;
		}else{
			return "";
		}
 	}
	 		
	function onlineDocSort(title, url, id) {
		var rowData = $('#' + id).datagrid('getSelected');
		if (rowData) {
			url += '&id=' + rowData.id;
		}
		add(title, url, 'onlineDocSortList', 300, 340);
	}
</script>