<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<t:base type="jquery,easyui,tools"></t:base>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入EXCEL</title>
</head>
<body style="overflow-y: hidden" scroll="no">
	<form id="uForm"
		action="importExcelController.do?importExcel&className=${className }"
		id="importExcel" enctype="multipart/form-data" method="post">
		<br>
		<br> <br>
		<br>
		<font color="red">导入成功！</font>
	</form>
	<script type="text/javascript">
		//上传成功调用的父窗口函数
		var jsonEXcelData='${jsonEXcelData}';
		var objjsonEXcelData=jQuery.parseJSON(jsonEXcelData);
		parent.uploadSucess(objjsonEXcelData);	
		function upload() {
		}
		function cancel() {
			$.dialog().close();
		}
	</script>
</body>
</html>