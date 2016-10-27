<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>目录管理</title>
	<t:base type="jquery,easyui,tools"></t:base>
	<script type="text/javascript">
		$(function() {
			$('#onlineDocSortTree').combotree({
				url : 'sharedFileController.do?tree',
				panelHeight : 200,
				width : 157,
				value :"${name}",
				onClick : function(node) {
					$("#parentId").val(node.id);
				}
			});
		});
	</script>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="sharedFileController.do?doUpdate">
		<table cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">目录名称:</label>
				</td>
				<td class="value">
					<input class="inputxt" id="name" name="fileName"  value="${shareFile.fileName }" style="vertical-align: middle;"/>
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr style="display: none">
				<td align="right">
					<label class="Validform_label">id:</label>
				</td>
				<td class="value">
					<input class="inputxt"  name="id"  value="${shareFile.id }" style="vertical-align: middle;"/>
					<span class="Validform_checktip"></span>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>