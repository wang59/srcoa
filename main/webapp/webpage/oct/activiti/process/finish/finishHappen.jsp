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
top:4px;
margin-right:4px;
background:url("webpage/images/main/icon-watch.png") no-repeat;
background-size:100%;
}
</style>
<script type="text/javascript">
function toHour(value, row, index){
	var result = (parseFloat(value)/3600000).toFixed(2);
	if(result == 'NaN')
		result= '';
	return result;
}
</script>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<!--name 表格表示 ， title标题  autoLoadData 是否自动扩展 sortName按创建日期排序 -->
	<t:datagrid name="finishHappen" title="已办事宜" autoLoadData="true" queryBuilder="false"
			sortName="endTime" fitColumns="true"
			idField="id" fit="true" queryMode="group" checkbox="false"
			actionUrl="finishHappenController.do?finishHappenList" sortOrder="desc">
			<!--申明列  -->
		<t:dgCol field="id" title="编号" hidden="true"></t:dgCol>
		<!--数据显示的编号,隐藏-->
		<t:dgCol  field="actTaskBusiness.businessTitle" title="请求标题" query="true" width="60" style="color:#23527c;cursor:pointer"></t:dgCol>
		<t:dgCol  field="actTaskBusiness.serialnumber" title="流水号" query="true" width="40" ></t:dgCol>
		<t:dgCol field="tSBaseUser.realName" title="处理人" width="30"></t:dgCol>
		<t:dgCol field="startTime" title="收到待办时间"
				formatter="yyyy-MM-dd hh:mm:ss" align="center" query="false"
				queryMode="group" sortable="true" width="50" ></t:dgCol>
		<t:dgCol field="endTime" title="处理时间" formatter="yyyy-MM-dd hh:mm:ss"
				align="center" query="true" queryMode="group" sortable="true"
				width="50"></t:dgCol>
		<t:dgCol field="duration" title="时长(小时)" width="20" query="false" formatterjs='toHour'></t:dgCol>
		<t:dgCol field="name" title="节点名称" width="30" query="true"></t:dgCol>
		<t:dgCol field="actTaskBusiness.businessCreateName" title="创建人" width="30" query="true"></t:dgCol>
		<t:dgCol field="actTaskBusiness.businessCreateTime" title="创建时间"
				formatter="yyyy-MM-dd hh:mm:ss" align="center" query="false"
				queryMode="group" sortable="true" width="50"></t:dgCol>
		<%-- <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
		<t:dgFunOpt funname="finishHappen(id)" title="<i class='btn-watch'></i>查看" /> --%>
	</t:datagrid>
	</div>
</div>

<script type="text/javascript">
	function testReloadPage() {
		document.location = "http://www.baidu.com";
	}
	function finishHappen(id) {
		var purl = "finishHappenController.do?finishRead&id=" + id;
		// // 		var purl="workFlowAutoFormController.do?viewContent&formName=employee_entry_form&id="+id+"&op=view";
		// 		addOneTab("已办事宜记录", purl);
		window.open(purl);
		//createwindow('审核', 'jeecgDemoController.do?doCheck&id=' + id);
	}
	function getListSelections() {
		var ids = '';
		var rows = $("#jeecgDemoList").datagrid("getSelections");
		for (var i = 0; i < rows.length; i++) {
			ids += rows[i].id;
			ids += ',';
		}
		ids = ids.substring(0, ids.length - 1);
		return ids;
	}
	//表单 sql导出
	function doMigrateOut(title, url, id) {
		url += '&ids=' + getListSelections();
		window.location.href = url;
	}
	function doMigrateIn() {
		openuploadwin('Xml导入', 'transdata.do?toMigrate', "jeecgDemoList");
	}
	$(document).ready(function() {
				$("input[name='createTime_begin']").attr("class", "Wdate")
						.click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd'
							});
						});
				$("input[name='createTime_end']").attr("class", "Wdate").click(
						function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd'
							});
						});
				
			});
	
	function addMobile(title, addurl, gname, width, height) {
		window.open(addurl);
	}

	function updateMobile(title, url, id, width, height) {
		gridname = id;
		var rowsData = $('#' + id).datagrid('getSelections');
		if (!rowsData || rowsData.length == 0) {
			tip('请选择编辑项目');
			return;
		}
		if (rowsData.length > 1) {
			tip('请选择一条记录再编辑');
			return;
		}

		url += '&id=' + rowsData[0].id;
		window.open(url);
	}
	$(document).ready(function(){
		$("input[name='endTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("input[name='endTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("input[name='actTaskBusiness.businessTitle']").attr("style","width:160px");
		$("input[name='actTaskBusiness.serialnumber']").attr("style","width:160px");
		
	});		

	$("#finishHappen").datagrid({
		  onClickCell:function(rowIndex, field, value){
			if(field=="actTaskBusiness.businessTitle"){
				var rows = $('#finishHappen').datagrid('getRows');	
				var data = rows[rowIndex];
				//szqm(data.id);
				finishHappen(data.id);
			}
		  }
	}); 
	
</script>
