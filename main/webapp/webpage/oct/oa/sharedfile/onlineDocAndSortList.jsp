<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="sss" iframe="true" tabPosition="top">
	<t:tab href="sharedFileController.do?documentslist&type=${param.type }"  title="文件目录" id="all"></t:tab>
	<t:tab href="sharedFileController.do?fileslist&type=${param.type} "  title="文件夹目录" id="no"></t:tab>
	
</t:tabs>