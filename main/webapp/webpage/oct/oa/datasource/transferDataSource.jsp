<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>数据源迁移</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<table align="center" style="width: 800px;height:600px;" >
		<tr>
			<td align="left">
				<select name="srcSelect" style="width:300px;">
					<option value="">---请选择旧数据源---</option>
				</select>
			</td>
			<td></td>
			<td align="right">
				<select name="desSelect" style="width:300px;">
					<option value="">---请选择新数据源---</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="left">
				<select id="user_select" name="srcTablesSelect" multiple="multiple"  size="15" style="width:300px;height:500px;">
				</select>
			</td>
			<td align="right">
				<input type="button" value="&gt&gt"  style="font: 9pt 宋体;width: 60px;height: 20px;margin: 0px;padding: 0px;color: #333333;background: #CCCCCC;border: 0px solid #0000FF;	border: 3px double #DDDDDD;cursor: pointer;"/>
				<br/><br/><br/><br/>
				<input type="button" value="&lt&lt"  style="font: 9pt 宋体;width: 60px;height: 20px;margin: 0px;padding: 0px;color: #333333;background: #CCCCCC;border: 0px solid #0000FF;	border: 3px double #DDDDDD;cursor: pointer;"/>
			</td>
			<td align="right">
				<select id="user_select" name="desTablesSelect"  multiple="multiple"  size="15" style="width:300px;height:500px;">
				</select>
			</td>
		</tr>
	</table>
	<script type="text/javascript" src="webpage/oct/oa/datasource/js/transferDataSource.js"></script>
</body>
</html>


