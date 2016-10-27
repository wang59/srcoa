<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="processAgentlist" title="流程分类" actionUrl="createAppController.do?creategrid" idField="id" fit="true" queryMode="group" queryBuilder="true">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="类别" field="name" width="120" query="true"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="150"></t:dgCol>
	<t:dgDelOpt  title="删除" url="createAppController.do?del&id={id}" />
	<t:dgToolBar  title="增加" icon="icon-add" url="createAppController.do?add" funname="add"></t:dgToolBar>
</t:datagrid>
</div>
</div>
