<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="processAgentlist" title="流程实体" actionUrl="createAppController.do?processgrid" idField="id" fit="true" queryMode="group" queryBuilder="true" 
sortName="sort" 
>
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="流程名" field="name" width="120" query="true"></t:dgCol>
	<t:dgCol title="类别" field="type.name" width="120"  query="true"></t:dgCol>
	<t:dgCol title="地址" field="url" width="120" hidden="true"></t:dgCol>	
	<t:dgCol title="排序" field="sort" width="120" ></t:dgCol>	
	<t:dgCol title="绑定流程" field="processdefineid" width="120"></t:dgCol>
	<t:dgCol title="绑定表单" field="form" width="120"></t:dgCol>
	<t:dgCol title="流程描述" field="processdescribe" width="120"></t:dgCol>
	<t:dgCol title="流水号前缀" field="prefix" width="120"></t:dgCol>
	<t:dgCol title="常用流程" field="common" width="120" replace="是_1,否_0"></t:dgCol>	
	<t:dgCol title="操作" field="opt" width="120"></t:dgCol>
	<t:dgDelOpt  title="删除" url="createAppController.do?processdel&id={id}" />
	<t:dgToolBar  title="增加" icon="icon-add" url="createAppController.do?processadd" funname="add"></t:dgToolBar>
	<t:dgToolBar title="修改"  icon="icon-edit" url="createAppController.do?addorupdate" funname="update"></t:dgToolBar>
</t:datagrid>
</div>
</div>
