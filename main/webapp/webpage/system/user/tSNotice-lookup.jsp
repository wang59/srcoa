<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>${ tSNoticePage.noticeTitle}</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script>UEDITOR_HOME_URL='<%=path%>/plug-in/Formdesign/js/ueditor/';</script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.config.js?2023"></script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.all.js?2023"> </script>
 </head>
 <body>



	<div class="value" style="width: 900px;">
		<h6 style="text-align: center;font-size:18px;">${ tSNoticePage.noticeTitle}</h6>
		<p style="text-align: center;">发布部门:${tSNoticePage.createDepart }
			发布人:${tSNoticePage.createUser } 发布时间: ${date }
			</p>
		${tSNoticePage.noticeContent}


	</div>
 <c:if test="${approve=='approve' }">
		<div style="text-align: center;">
			<button onclick="approve(1)">同意并发布</button>
			<button onclick="approve(2)">拒绝</button>
			<button onclick="closeWindow(1)">关闭</button>
		</div>
	</c:if>
<c:if test="${approve=='edit' }">
<div style="text-align: center;">
 <button onclick="approve(3)">同意修改</button> 
 <button onclick="closeWindow(2)">关闭</button> 
 <div>
</c:if>
<c:if test="${approve=='view' }">
<div style="text-align: center;">
 <button onclick="closeWindow(3)">关闭</button> 
 <div>
 </c:if>

<script type="text/javascript">
 function approve(type){
	 $.ajax({
	   		url:"noticeController.do?noticeapprove",
	   		data:{"type":type,"id":"${tSNoticePage.id}"},
	   		dataType:'json',
	   		type:'post', 
			success:function(msg) {
				window.opener.location.reload();
				window.close();
			}				
		});
 }
 function closeWindow(type){
	 closeWin();
 }
</script>
</body>
	