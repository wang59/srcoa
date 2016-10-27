<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
.btn-watch{
width:16px;
height:17px;
display:inline-block;
position:relative;
margin-right:4px;
top:4px;
background:url("webpage/images/main/icon-watch.png") no-repeat;
background-size:100%;
}
</style>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
	<!--name 表格表示 ， title标题  autoLoadData 是否自动扩展 sortName按创建日期排序 -->
	<t:datagrid name="myApplication" title="我的申请" autoLoadData="true"
			sortName="businessCreateTime" fitColumns="true"
			idField="id" fit="true" queryMode="group" checkbox="false"
			actionUrl="myApplicationController.do?applicationList" sortOrder="desc">
			<!--申明列  -->
		<t:dgCol field="id" title="编号" hidden="true"></t:dgCol>
		<!--数据显示的编号,隐藏-->
		<t:dgCol field="businessTitle" title="请求标题" width="80" query="true" align="" style="color:#23527c;cursor:pointer"></t:dgCol>
		<t:dgCol field="serialnumber" title="流水号" width="30" query="true"></t:dgCol>
		<t:dgCol field="actReProcdef.name" title="工作流" width="30" query="true"></t:dgCol>
		<t:dgCol field="businessCreateTime" title="创建时间"
				formatter="yyyy-MM-dd hh:mm:ss" align="center" query="false"
				queryMode="group" sortable="true" width="50"></t:dgCol>
		<t:dgCol field="actRuTask.name" title="当前节点" width="50" ></t:dgCol>
		<t:dgCol field="actRuTask.tSBaseUser.realName" title="当前处理人" width="40"></t:dgCol>
		<t:dgCol field="status" title="状态" width="40" replace="进行中_1,拒绝_2,已结束_3,申请人撤销_4" query="true"></t:dgCol>
	</t:datagrid>
	</div>
</div>

<script type="text/javascript">
	function testReloadPage() {
		document.location = "http://www.baidu.com";
	}
	function applicationDate(id) {
		var purl = "myApplicationController.do?read&id=" + id;
		// 		var purl="workFlowAutoFormController.do?viewContent&formName=employee_entry_form&id="+id+"&op=view";
		// 		addOneTab("我的申请记录", purl);
		window.open(purl);
		// 		createwindow('审核', purl);
	}
	
	//点击标题打开查看页面

  $("#myApplication").datagrid({
	  onClickCell:function(rowIndex, field, value){
		if(field=="businessTitle"){
			var rows = $('#myApplication').datagrid('getRows');	
			var data = rows[rowIndex];
			//szqm(data.id);
			applicationDate(data.id);
		}
	  }
}); 
$(function(){
	$("input[name='businessTitle']").attr("style","width:160px");
	$("input[name='serialnumber']").attr("style","width:160px");
	$("input[name='actReProcdef.name']").attr("style","width:160px");
});
</script>
