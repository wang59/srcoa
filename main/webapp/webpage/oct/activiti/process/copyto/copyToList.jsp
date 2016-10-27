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
.btn-read{
width:16px;
height:17px;
display:inline-block;
position:relative;
margin-right:4px;
top:4px;
background:url("webpage/images/main/icon-read.png") no-repeat;
background-size:100%;
}
</style>
<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
<t:datagrid name="toReadList" title="待阅事宜" autoLoadData="true" actionUrl="copyToController.do?copyTogrid&status=${param.status }" sortName="createtime"  sortOrder="desc" fitColumns="true"
	idField="id" fit="true" queryMode="group" checkbox="false">
	<t:dgCol title="编号" field="id" hidden="true" width="50"></t:dgCol>
	<t:dgCol title="标题" field="actTaskBusiness.businessTitle" width="200" style="color:#23527c;cursor:pointer"></t:dgCol>
	<t:dgCol title="创建时间" field="createtime" formatter="yyyy-MM-dd" width="50" align="center"></t:dgCol>
	<t:dgCol title="待阅人" field="user.realName" width="50"></t:dgCol>	
	<t:dgCol title="状态" field="status" width="50" replace="已读_1,未读_0"></t:dgCol>	
</t:datagrid></div>
</div>
<script type="text/javascript">
  function szqm(id){
	var url="copyToController.do?editapp&id="+id;
	window.open(url);
	location.reload(); 
  }
//点击标题后，跳转到查看页面
  $("#toReadList").datagrid({
	  onClickCell:function(rowIndex, field, value){
		if(field=="actTaskBusiness.businessTitle"){
			var rows = $('#toReadList').datagrid('getRows');	
			var data=rows[rowIndex];
			szqm(data.id);
		}
	  }
  });
</script>