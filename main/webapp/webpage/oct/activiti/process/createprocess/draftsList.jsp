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
margin-right:20px;
}
td[field="opt"] .datagrid-cell a:first-child{
color:rgb(255,80,80)!important;
}
.btn-throw,.btn-write{
width:16px;
height:17px;
display:inline-block;
position:relative;
top:4px;
margin-right:4px;
background:url("webpage/images/main/icon-throw.png") no-repeat;
background-size:100%;
}
.btn-write{
width:20px;
background:url("webpage/images/main/icon-write.png") no-repeat;
}
</style>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="processAgentlist" title="草稿箱" actionUrl="workFlowAutoFormController.do?draftsgrid" idField="id" fit="true" queryMode="group" queryBuilder="true">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="流程名" field="name"  width="60" query="true" style="color:#23527c;cursor:pointer"></t:dgCol>
	<t:dgCol title="创建时间" field="createtime" formatter="yyyy-MM-dd "  width="100" align="center"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="150"></t:dgCol>
	<t:dgDelOpt  title="<i class='btn-throw'></i>删除" url="workFlowAutoFormController.do?delrafts&id={id}" />
	<t:dgFunOpt funname="update(id)" title="<i class='btn-write'></i>编辑"></t:dgFunOpt>
</t:datagrid>
<script type="text/javascript">
  function update(id)
  {
	  var url="workFlowAutoFormController.do?updatedrafts&id="+id;
	  window.open(url);
	  window.opener.location.reload();
	  window.close();//关闭窗口
  }
  
  $("#processAgentlist").datagrid({
	  onClickCell:function(rowIndex, field, value){
		if(field=="name"){
			var rows = $('#processAgentlist').datagrid('getRows');	
			var data=rows[rowIndex];
			update(data.id);
		}
	  }
  });
 
</script>
</div>