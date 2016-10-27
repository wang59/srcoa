<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style>
td[field="opt"] .datagrid-cell{
color:rgb(18,156,235);
text-align:center;
}
td[field="opt"] .datagrid-cell a{
color:rgb(18,156,235)!important;
}
.btn-clear{
width:16px;
height:17px;
display:inline-block;
position:relative;
top:4px;
margin-right:4px;
background:url("webpage/images/main/icon-clear.png") no-repeat;
background-size:100%;
}
</style>
<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
<t:datagrid name="toDoList" title="待办事宜" autoLoadData="true" sortOrder="desc" actionUrl="toDoController.do?todogrid" sortName="createTime"   fitColumns="true"
	idField="id" fit="true" queryMode="group" checkbox="false">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="申请单ID" field="actTaskBusiness.businessId" hidden="true" width="20"></t:dgCol>
	<t:dgCol title="标题" field="actTaskBusiness.businessTitle" query="true" width="40" style="color:#23527c;cursor:pointer"></t:dgCol>
	<t:dgCol title="流水号" field="actTaskBusiness.serialnumber" query="true" width="30"></t:dgCol>
	<t:dgCol title="创建人" field="actTaskBusiness.businessCreateName"  align="center" query="true" frozenColumn="false"  width="20"></t:dgCol>
	<t:dgCol title="创建时间" sortable="true" field="createTime" formatter="yyyy-MM-dd hh:mm:ss"  align="center"  query="false" queryMode="group" width="40"></t:dgCol>	
	<t:dgCol title="处理人" field="tSBaseUser.realName" query="true"  align="center" width="20"></t:dgCol>
</t:datagrid></div>
</div>

		<script type="text/javascript" src="plug-in/ace/js/bootstrap-tab.js"></script>
<script type="text/javascript">

		function testReloadPage(){
			document.location = "http://www.baidu.com"; 
		}
	function szqm(taskId){
		//var purl="toDoController.do?editapp&taskId="+taskId;
		var purl = "workFlowAutoFormController.do?newapproval&id="+taskId;
		window.open(purl);
// 		addOneTab('新建流程',purl);
		//createwindow('审核', 'jeecgDemoController.do?doCheck&id=' + id);
	}
	function getListSelections(){
		var ids = '';
		var rows = $("#jeecgDemoList").datagrid("getSelections");
		for(var i=0;i<rows.length;i++){
			ids+=rows[i].id;
			ids+=',';
		}
		ids = ids.substring(0,ids.length-1);
		return ids;
	}	
	//表单 sql导出
	function doMigrateOut(title,url,id){
		url += '&ids='+ getListSelections();
		window.location.href= url;
	}
	function doMigrateIn(){
		openuploadwin('Xml导入', 'transdata.do?toMigrate', "jeecgDemoList");
	}
	$(document).ready(function(){
		$("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	});
	function addMobile(title,addurl,gname,width,height){
		window.open(addurl);
	}
	
	function updateMobile(title,url, id,width,height){
		gridname=id;
		var rowsData = $('#'+id).datagrid('getSelections');
		if(!rowsData || rowsData.length==0) {
			tip('请选择编辑项目');
			return;
		}
		if (rowsData.length>1) {
			tip('请选择一条记录再编辑');
			return;
		}
		
		url += '&id='+rowsData[0].id;
		window.open(url);
	}
	
	</script>
	<script>
	$(function(){
		$(".datagrid-cell-c1-opt").each().text().replace('[',"");
		$("input[name='actTaskBusiness.businessTitle']").attr("style","width:160px");
		$("input[name='actTaskBusiness.serialnumber']").attr("style","width:160px");
	});
	//点击标题后，跳转到查看页面
  $("#toDoList").datagrid({
	  onClickCell:function(rowIndex, field, value){
		if(field=="actTaskBusiness.businessTitle"){
			var rows = $('#toDoList').datagrid('getRows');	
			var data=rows[rowIndex];
			szqm(data.id);
		}
	  }
  });
	</script>

