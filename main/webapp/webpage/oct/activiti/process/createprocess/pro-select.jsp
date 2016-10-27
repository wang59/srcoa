<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<link rel="stylesheet" type="text/css" href="plug-in/ztree/css/zTreeStyle.css">
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<%--update-start--Author:wangkun  Date:20160327 TASK #956 【UI标签】封装选择用户标签--%>

<div class="easyui-layout" style="width:800px;height:600px;">
    <div data-options="region:'center'">
      <t:datagrid name="processselect" title="流程选择" actionUrl="createAppController.do?processselectgrid" idField="id" fit="true" queryMode="group" queryBuilder="true">
	<t:dgCol title="编号" field="id" ></t:dgCol>
	<t:dgCol title="key" field="key"   width="120" query="true"></t:dgCol>
	<t:dgCol title="流程名" field="name"   width="120"  query="true"></t:dgCol>
	<t:dgCol title="版本" field="version"  hidden="true" sortable="true" width="120"></t:dgCol>
	
</t:datagrid>
    </div>
</div>

