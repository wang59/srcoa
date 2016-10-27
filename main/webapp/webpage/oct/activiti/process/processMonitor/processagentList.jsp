<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style>
td[field="opt"] .datagrid-cell{
color:rgb(250,80,80);
text-align:center;
}
td[field="opt"] .datagrid-cell a{
color:rgb(250,80,80)!important;
}
.btn-back{
width:16px;
height:17px;
display:inline-block;
position:relative;
top:4px;
margin-right:4px;
background:url("webpage/images/main/icon-back.png") no-repeat;
background-size:100%;
}
</style>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="processAgentlist" title="流程代理" actionUrl="processMonitorController.do?processAgentgrid" idField="id" fit="true" queryMode="group" queryBuilder="true">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="代理流程" field="path.name" width="180" style="color:#23527c;cursor:pointer"></t:dgCol>
	<t:dgCol title="类型" field="type"  width="60" query="true"></t:dgCol>
	<t:dgCol title="创建人" field="creater.realName"  frozenColumn="false"  width="120" ></t:dgCol>
	<t:dgCol title="代理人" field="agent.realName" query="true"   width="120"></t:dgCol>	
	<t:dgCol title="开始时间" field="begintime" formatter="yyyy-MM-dd " query="true" queryMode="group" width="100" align="center"></t:dgCol>
	<t:dgCol title="结束时间" field="endtime" formatter="yyyy-MM-dd " query="true" queryMode="group" width="100" align="center"></t:dgCol>
	<t:dgCol title="当前状态" field="status" replace="未开始_0,进行中_1,已结束_2" width="60" query="true"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="150"></t:dgCol>
	<t:dgDelOpt  title="<i class='btn-back'></i>撤销代理" url="processAgentController.do?del&id={id}" />
	<t:dgToolBar  title="新建代理" icon="icon-add" url="processMonitorController.do?add" funname="add"></t:dgToolBar>
</t:datagrid>

</div>
<script type="text/javascript">
$(document).ready(function(){
	$("input[name='begintime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("input[name='begintime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("input[name='endtime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("input[name='endtime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	
});

function detail(id){
	createdetailwindow("查看","processAgentController.do?detail&id="+id);
}

$("#processAgentlist").datagrid({
	  onClickCell:function(rowIndex, field, value){
		if(field=="path.name"){
			var rows = $('#processAgentlist').datagrid('getRows');	
			var data=rows[rowIndex];
			detail(data.id);
		}
	  }
});
</script>
</div>
