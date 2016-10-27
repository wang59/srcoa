<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<SCRIPT type=text/javascript src="plug-in/clipboard/ZeroClipboard.js"></SCRIPT>
<t:base type="jquery,easyui,jqueryui-sortable,tools"></t:base>
<style type="text/css">
.table-list {
	margin: 0;
	width: auto;
	margin-left: 0px;
	margin-right: 0px;
	overflow: hidden;
}

.table-list td,.table-list th {
	text-align: center;
}

.t_table {
	overflow: auto; /*让内容表格外面的div自动有滚动条*/
	margin-left: 0px;
	margin-right: 0px;
	width: auto;
}

#tab_div_database tr {
	border-bottom: 1px solid #e6e6e6;
	cursor: n-resize;
}
/*update-end--Author:liuht  Date:20131207 for[333]：OL模块，增加一个特效 调整字段顺序（上下挪动）*/
</style>
<body style="overflow-y: hidden; overflow-x: hidden;" scroll="no">
<t:formvalid formid="formobj" dialog="true"  beforeSubmit="" usePlugin="password"  layout="table" tiptype="1"  action="autoFormController.do?synAutoForm">
<table cellpadding="0" cellspacing="1" class="formtable">
	<input type="hidden" name="id" value="${autoFormEntity.id}">
		<tr>
			<td align="right">导出数据源: </label></td>
			<td class="value">
			  <span class="Validform_checktip"></span>
			  <select name="srcDataSource" id="srcDataSource">
			  	 <option value="">---请选择---</option>
			  </select>
			</td>
	</tr>
	<tr>
			<td align="right">导入数据源: </label></td>
			<td class="value">
			  <span class="Validform_checktip"></span>
			  <select name="desDataSource" id="desDataSource">
			  	 <option value="">---请选择---</option>
			  </select>
			</td>
	</tr>
</table>
</t:formvalid>
<script type="text/javascript" src="webpage/jeecg/cgform/autoform/syndatasource.js"></script>
</body>
