<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge;IE=11;IE=10;IE=9"/>
        <t:base type="jquery,easyui,tools"></t:base>
        <script type="text/javascript" src="plug-in/jquery-plugs/form/jquery.form.js"></script>
	    <link rel="stylesheet" href="plug-in/Formdesign/js/ueditor/formdesign/bootstrap/css/bootstrap.css">
	    
	    <!-- add start by jg_renjie at 20151025 for:增加支持Validform插件的相关文件 -->
	    <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css">
	    <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css">
	    <script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
	    <script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
	    <script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
	    <script type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></script>
	    <!-- add end by jg_renjie at 20151025 for:增加支持Validform插件的相关文件 -->	    
	    <script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
	    <link rel="stylesheet" href="plug-in/My97DatePicker/skin/WdatePicker.css" type="text/css"></link>
	    <link rel="stylesheet" href="plug-in/Formdesign/js/ueditor/formdesign/leipi.style.css">
	    <style>
	    table td,table th{
	    padding:12px 48px;
	    text-align:center;
	    }
	    table{
	    margin-top:20px;
	    margin-left:20px;
	    }
	    </style>
	 </head>
     <body>
	<div title="流程图">
	<c:if test="${ check==true}">
		<img alt="" src="flowsheetController.do?photo2&procInstId=${param.procInstId }">
		<table border="1">
		    <th>环节名称</th>  <th>处理人</th>  <th>开始时间</th> 
		    <c:forEach var="map" items="${taskinfo }">
		     <tr>
		         <c:forEach var="task" items="${map }">
		          <td>${task.value }</td>
		         </c:forEach>
		     </tr>		    
		    </c:forEach>
		</table>
	</c:if>
	<c:if test="${ check==false}">
	  <img alt="" src="flowsheetController.do?photo2&procDefId=${processDefId }&procInstId=${param.procInstId }">
	  <br>
	  <div style="text-align: center;">
	           流程已结束
	   </div>
	</c:if>
	</div>



</body>
</html>