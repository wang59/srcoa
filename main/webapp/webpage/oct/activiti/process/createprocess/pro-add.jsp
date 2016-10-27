<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil" %>
<!DOCTYPE html>
<html>
<head>
<title>增加流程类别</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style=" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="createAppController.do?saveProcess">
	<table style="width: 600px;" cellpadding="0" cellspacing="1"  >		
		<tr >
			<td align="right" ><label class="Validform_label" > 流程类别: </label></td>
			<td class="value"><input  class="inputxt" name="name"   > </td>
		</tr>
	</table>
</t:formvalid>
</body>
</html>
