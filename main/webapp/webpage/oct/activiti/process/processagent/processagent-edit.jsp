<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil" %>
<!DOCTYPE html>
<html>
<head>
<title>部门信息</title>
<script type="text/javascript">	
</script>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" usePlugin="password" layout="div" dialog="false" action="">
<input id="id" name="id" type="hidden" value="${processAgent.id}">
	<table style="width: 600px;" cellpadding="0" cellspacing="1"  >
	
		<tr>
			<td align="right" ><label class="Validform_label"> 类型: </label></td>
			<td class="value"><input  class="inputxt"  name="type" id="type" value="${type}" readonly="readonly" >
			</td>
		</tr>
		
		<tr >
			<td align="right" ><label class="Validform_label" > 创建人: </label></td>
			<td class="value"><input  class="inputxt"  name="creater" id="creater" value="${creater}"  readonly="readonly">
			</td>
			
		</tr>
		<tr >
			<td align="right" ><label class="Validform_label"> 代理人: </label></td>
			<td class="value"><input  class="inputxt"  name="realname" id="realname" value="${username}"  readonly="readonly">    
			</td>
		</tr>
	
		<tr>
			<td align="right"><label class="Validform_label"> 生效时间起: </label></td>
			<td class="value"><input  class="inputxt"  name="starttime" id="starttime" value="${starttime}"  readonly="readonly">
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 生效时间止: </label></td>
			<td class="value"><input  class="inputxt"  name="endtime" id="endtime" value="${endtime}"  readonly="readonly">
		</tr>
		<tr >
			<td align="right" ><label class="Validform_label"> 代理已有待办事项: </label></td>
			<td class="value"><input  class="inputxt"  name="agentStyle" id="agentStyle" value="${agentStyle}"  readonly="readonly">
		</tr>
		<tr >
			<td align="right" ><label class="Validform_label"> 代理流程范围: </label></td>
			<td class="value"><input  class="inputxt"  name="path" id="path" value="${path}"  readonly="readonly"> 
		</tr>
		<tr >
			<td align="right" ><label class="Validform_label"> 当前状态: </label></td>
			<td class="value"><input  class="inputxt"  name="agentStatus" id="agentStatus" value="${agentStatus}"  readonly="readonly"> 
		</tr>
	</table>
</t:formvalid>
</body>
<script type="text/javascript">

</script>
</html>
