<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="processAgentlist" title="质量反馈单查询" actionUrl="qualityBackController.do?qualityBackgrid" idField="id" fit="true" queryMode="group" queryBuilder="true" sortName="businessCreateTime" sortOrder="desc">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="创建人" field="businessCreateBy" hidden="true"></t:dgCol>
	<t:dgCol title="反馈部门" field="qualityBackEntity.orgcode"  frozenColumn="true"  width="120" ></t:dgCol>
	<t:dgCol title="反馈人" field="businessCreateName" query="false"   width="60"></t:dgCol>
	<t:dgCol title="反馈时间" field="businessCreateTime"  width="150" query="false" ></t:dgCol>
	<t:dgCol title="工程项目" field="qualityBackEntity.octEngineerManage.engineerName" query="false" width="120"></t:dgCol>
	<t:dgCol title="项目名称" field="qualityBackEntity.octProjectManage.projectName"  query="false"  width="120"></t:dgCol>
	<t:dgCol title="工程项目" field="qualityBackEntity.octEngineerManage.id"  width="60" dictionary="oct_engineer_manage,id,engineer_name" query="true" hidden="true"></t:dgCol>
	<t:dgCol title="项目名称" field="qualityBackEntity.octProjectManage.id"  width="60"  dictionary="oct_project_manage,id,project_name" query="true" hidden="true"></t:dgCol>
	<t:dgCol title="产品名称" field="qualityBackEntity.octProjectManage.machineName"  query="false"  width="120"></t:dgCol>
	<t:dgCol title="状态" field="status" replace="进行中_1,拒绝_2,已结束_3,申请人撤销_4" width="60" query="true"></t:dgCol>
	<t:dgCol title="当前审批人" field="currentTodo"  width="60" query="false"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="150"></t:dgCol>
	<t:dgFunOpt funname="applicationDate(id)" title="查看"></t:dgFunOpt>
	<t:dgToolBar title="新增质量反馈单" icon="icon-putout" funname="addnew"></t:dgToolBar>
</t:datagrid>
</div>
<script type="text/javascript">
function applicationDate(id) {
	var purl = "myApplicationController.do?read&id=" + id;
	window.open(purl);
}
function addnew(){
	var url='workFlowAutoFormController.do?viewContent&formName=qualityback&id=&op=add&workflowName=qualityprocess&proid=4028d24a574f75ba015750ec60d9001d'
    window.open(url);
}
</script>
</div>
