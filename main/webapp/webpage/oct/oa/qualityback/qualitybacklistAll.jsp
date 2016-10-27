<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style>
.datagrid-pager{
	position:absolute!important;
	bottom:0!important;
}
.datagrid-row td[field="opt"] a{
margin-right:10px;
}
</style>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="processAgentlist" title="质量反馈单查询" actionUrl="qualityBackController.do?qualityBackgrid&type=1" idField="id" fit="true" queryMode="group" queryBuilder="true" sortName="businessCreateTime" sortOrder="desc">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="流水号" field="qualityBackEntity.serialnumber" query="true"  width="120"></t:dgCol>	
	<t:dgCol title="工程项目" field="qualityBackEntity.octEngineerManage.engineerName" query="false" width="120"></t:dgCol>
	<t:dgCol title="项目名称" field="qualityBackEntity.octProjectManage.projectName"  query="false"  width="120"></t:dgCol>
	<t:dgCol title="状态" field="status" replace="进行中_1,拒绝_2,已结束_3,申请人撤销_4" width="60" query="true"></t:dgCol>
	<t:dgCol title="反馈时间" field="businessCreateTime"  width="150" query="true" queryMode="group"></t:dgCol>
	<t:dgCol title="当前审批人" field="currentTodo"  width="60" query="false"></t:dgCol>
	<t:dgCol title="工程项目" field="qualityBackEntity.octEngineerManage.id"  width="60" dictionary="oct_engineer_manage,id,engineer_name" query="true" hidden="true"></t:dgCol>
	<t:dgCol title="项目名称" field="qualityBackEntity.octProjectManage.id"  width="60"  dictionary="oct_project_manage,id,project_name" query="true" hidden="true"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="150"></t:dgCol>
	<t:dgFunOpt funname="applicationDate(id)" title="查看"></t:dgFunOpt>
	<t:dgFunOpt funname="print(id)" title="打印"></t:dgFunOpt>
</t:datagrid>
</div>
 
<script type="text/javascript">
function applicationDate(id) {
	var purl = "myApplicationController.do?read&id=" + id;
	window.open(purl);
}

function print(id) {
	$.ajax({
		url : "qualityBackController.do?getid",
		data : {
			"id" : id
		},
		dataType : 'json',
		type : 'post',
		success : function(msg) {
			var purl ="workFlowAutoFormController.do?viewContent=&op=print&formName=qualityback&id="+msg.msg; 
			window.open(purl);
		}
	});
	
}
$(function(){
	$("input[name='businessCreateTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("input[name='businessCreateTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	
})
</script>
</div>
