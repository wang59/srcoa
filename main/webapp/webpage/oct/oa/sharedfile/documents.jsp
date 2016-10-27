<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>文件列表</title>
<t:base type="jquery,easyui,tools"></t:base>
	<script type="text/javascript">
	
		$(function() {
			$('#onlineDocSortTree').combotree({
				url : 'sharedFileController.do?tree&type=${param.type}',
				panelHeight : 200,
				width : 157,
				value :"${name}",
				onClick : function(node) {
					$("#documentId").val(node.id);
				}
			});
		});
	</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="upload">
	<fieldset class="step">
	<div class="form">
		<label class="Validform_label"> 文件夹名称: </label> 
		<input id="onlineDocSortTree" value="${name}">
		<input id="documentId" name="documentId" style="display: none;" value="">
		<span class="Validform_checktip"></span>
	</div>
	<div class="form">
		<label class="Validform_label"> 文件标题: </label> 
		<input name="docname"  datatype="s3-50" type="text" id="docname"> 
		<span class="Validform_checktip">标题名称在3~50位字符,且不为空</span>
	</div>
	
	<div class="form">
	<!-- 		update-begin--Author:huangzq  Date:20151205 for：[733]上传下载，没有编辑功能-->
		<t:upload name="fiels" buttonText="上传文件" uploader="sharedFileController.do?saveFiles&type=${param.type }" extend="office" id="file_upload" formData="docname,documentId"></t:upload>
	<!-- 		update-end--Author:huangzq  Date:20151205 for：[733]上传下载，没有编辑功能-->
	</div>
	<div class="form" id="filediv" style="height: 50px"></div>
	</fieldset>
</t:formvalid>
</body>
</html>
