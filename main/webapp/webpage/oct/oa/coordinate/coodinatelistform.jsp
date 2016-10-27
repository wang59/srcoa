<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="coordinatelist" title="任务协调单查询" actionUrl="coordinateController.do?formgrid" idField="id" fit="true" queryMode="group" queryBuilder="true" sortName="businessCreateTime" sortOrder="desc">
	<t:dgCol title="流水号" field="serialnumber"    width="120" query="false" ></t:dgCol>   
	<t:dgCol title="提单时间" field="businessCreateTime"  width="150" query="true" queryMode="group"></t:dgCol>
	<t:dgCol title="提单人" field="businessCreateName" query="false"   width="60"></t:dgCol>
	<t:dgCol title="项目名称" field="projectname" query="true" width="120"></t:dgCol>
	<t:dgCol title="任务描述" field="explain" query="false"   width="60"></t:dgCol>
	<t:dgCol title="涉及部门" field="depart" query="false"   width="60"></t:dgCol>
	<t:dgCol title="负责人" field="doman" query="false"   width="60"></t:dgCol>	
	<t:dgCol title="要求完成日期" field="neesdate" query="false"   width="60"></t:dgCol>	
	<t:dgCol title="审核状态" field="status" replace="进行中_1,拒绝_2,已结束_3,申请人撤销_4" width="60" query="true"></t:dgCol>
	<t:dgToolBar title="excelOutput" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
</t:datagrid>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$("input[name='businessCreateTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("input[name='businessCreateTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	
});
function ExportXls() {
	var start=$("input[name='businessCreateTime_begin']").val();
	var end=$("input[name='businessCreateTime_end']").val();
	var status=$("select[name='status'] option:selected").val();
	var url="coordinateController.do?exportXls&start="+start+"&end="+end+"&status="+status;
alert(url);
	JeecgExcelExport(url, "coordinatelist");
}
</script>
</div>
