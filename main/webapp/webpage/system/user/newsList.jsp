
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="sss" iframe="true" tabPosition="top">
	<t:tab href="noticeController.do?allnoticeList&type=2&noticeType=3" icon="icon-search" title="全部" id="all"></t:tab>
	<t:tab href="noticeController.do?allnoticeList&type=1&noticeType=3" icon="icon-search" title="已读" id="no"></t:tab>
	<t:tab href="noticeController.do?allnoticeList&type=0&noticeType=3" icon="icon-search" title="未读" id="noread"></t:tab>
</t:tabs>













