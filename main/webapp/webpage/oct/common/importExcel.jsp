<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<t:base type="jquery,easyui,tools"></t:base>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入EXCEL</title>
</head>
<body style="overflow-y: hidden" scroll="no">
<form id="uForm" action="importExcelController.do?importExcel&saveToDb=${saveToDb }&className=${className }" id="importExcel" enctype="multipart/form-data" method="post">
					<br><br><input type="file" name="File1">
</form>
<script type="text/javascript">
	function upload(){ $("#uForm").submit();}
	function cancel(){$.dialog().close(); 
}
</script>
</body>
</html>