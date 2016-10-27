<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="ttt" title="质量反馈单查询" actionUrl="qualityBackController.do?formgrid" idField="id" fit="true" queryMode="group" queryBuilder="true" sortName="createdate" sortOrder="desc">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="流水号" field="serialnumber" query="false"  width="100"></t:dgCol>	
	<t:dgCol title="提单时间" field="createdate" query="false"  width="100"></t:dgCol>	
	<t:dgCol title="提单人" field="createname" query="false"  width="100"></t:dgCol>
	<t:dgCol title="项目归属" field="project" query="false"  width="100"></t:dgCol>
	<t:dgCol title="项目名称" field="item" query="false"  width="100"></t:dgCol>
	<t:dgCol title="问题描述" field="content"  query="false"  width="100"></t:dgCol>
	<t:dgCol title="涉及部门" field="departmentname"  query="false"  width="100"></t:dgCol>
	<t:dgCol title="涉及部门意见" field="departmentid"  query="false"  width="100"></t:dgCol>
	<t:dgCol title="实施部门" field="departmentname"  query="false"  width="100"></t:dgCol>
	<t:dgCol title="实施部门意见" field="departmentid"  query="false"  width="100"></t:dgCol>
	<t:dgCol title="同类时间是否需处理" field="yesOrNo"  query="false"  width="100" replace="是_1,否_0"></t:dgCol>
	<t:dgCol title="要求完成日期" field="deadline"  query="false"  width="100"></t:dgCol>
	<t:dgCol title="状态" field="status"  width="60" query="false"></t:dgCol>	
	<t:dgToolBar title="excelOutput" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
</t:datagrid>
</div>
<script type="text/javascript">
function ExportXls() {
	JeecgExcelExport("qualityBackController.do?exportXls", "ttt");
}
</script>
</div>
