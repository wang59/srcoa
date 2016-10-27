<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="coordinatelist" title="任务协调单查询" actionUrl="coordinateController.do?coordinategrid" idField="id" fit="true" queryMode="group" queryBuilder="true" sortName="businessCreateTime" sortOrder="desc">
	<t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
	<t:dgCol title="创建人" field="businessCreateBy" hidden="true"></t:dgCol>
	<t:dgCol title="申请协调部门" field="coordinateEntity.depart"    width="120" ></t:dgCol>
	<t:dgCol title="协调人" field="businessCreateName" query="true"   width="60"></t:dgCol>
	<t:dgCol title="填报日期" field="businessCreateTime"  width="150" query="true" queryMode="group"></t:dgCol>
	<t:dgCol title="项目名称" field="coordinateEntity.projectname" query="true" width="120"></t:dgCol>
	<t:dgCol title="任务名称" field="coordinateEntity.taskname"  query="true"  width="120"></t:dgCol>	
	<t:dgCol title="审核状态" field="status" replace="进行中_1,拒绝_2,已结束_3,申请人撤销_4" width="60" query="true"></t:dgCol>
	<t:dgCol title="处理状态" field="coordinateEntity.dostatus" replace="未接收_1,已接收_2" width="60" query="true"></t:dgCol>
	<t:dgCol title="当前审批人" field="currentTodo"  width="60" query="false"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="150"></t:dgCol>
	<t:dgFunOpt funname="applicationDate(id)" title="查看"></t:dgFunOpt>
	<t:dgToolBar title="新增任务协调单" icon="icon-putout" funname="addnew"></t:dgToolBar>
</t:datagrid>
</div>
<script type="text/javascript">
function applicationDate(id) {
	var purl = "myApplicationController.do?read&id=" + id;
	window.open(purl);
}
function addnew(){
	var url='workFlowAutoFormController.do?viewContent&formName=oct_coordinate&id=&op=add&workflowName=coordinateprocess&proid=4028d24a57f987fa0157f9d581ff00aa';
    window.open(url);
}
$(document).ready(function(){
	$("input[name='businessCreateTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("input[name='businessCreateTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	
});
</script>
</div>
