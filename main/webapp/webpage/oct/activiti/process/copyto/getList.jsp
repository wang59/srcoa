
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="sss" iframe="true" tabPosition="top">
	<t:tab href="copyToController.do?copyToList" title="全部" id="all"></t:tab>
	<t:tab href="copyToController.do?copyToList&status=1"  title="已读" id="no"></t:tab>
	<t:tab href="copyToController.do?copyToList&status=0"  title="未读" id="noread"></t:tab>
</t:tabs>













