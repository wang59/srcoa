<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style>
	.panel-header{
		display:none;
	}
	.datagrid-toolbar{
		padding:0!important;
	}
	.panel-header, .panel-body,#actApproveLogtb,.tabs-panels{
		border-right-width:0!important;
		border-width:0!important;
		border-left-width:0!important;
	}
	.panel-fit, .panel-fit body{
		border-image-width:0!important;
	}
</style>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
<t:datagrid pagination="false" name="actApproveLog" title="审批记录" autoLoadData="true" actionUrl="workFlowAutoFormController.do?datagridlog&businessId=${businessId}" sortName="createTime"
	idField="id" fit="true" queryMode="group">
	<t:dgCol title="编号" field="id" sortable="false" hidden="true"></t:dgCol>
	<t:dgCol title="环节名称" field="nodeName" align="center" frozenColumn="true" sortable="false"  width="120"></t:dgCol>
	<t:dgCol title="处理人" field="approverName" align="center" sortable="false" width="20"></t:dgCol>
	<t:dgCol title="处理时间" field="createTime" align="center" formatter="yyyy-MM-dd hh:mm:ss" width="60"></t:dgCol>
	<t:dgCol title="操作." field="action" align="center" sortable="false" width="20"></t:dgCol>
	<t:dgCol title="审批意见" field="comment" sortable="false" width="200"></t:dgCol>
</t:datagrid></div>
</div>
