<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>common.notice</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script>UEDITOR_HOME_URL='<%=path%>/plug-in/Formdesign/js/ueditor/';</script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.config.js?2023"></script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.all.js?2023"> </script>
  <script type="text/javascript">
  //编写自定义JS代码
    function setContent(){
	    if(editor.queryCommandState( 'source' ))
	    	editor.execCommand('source');//切换到编辑模式才提交，否则有bug
	            
	    if(editor.hasContents()){
	    	editor.sync();
		    $("#noticeContent").val(editor.getContent());
		}
	}
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="noticeController.do?doUpdate" tiptype="1" beforeSubmit="setContent()">
					<input id="id" name="id" type="hidden" value="${tSNoticePage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label" >
								标题:
							</label>
						</td>
						<td class="value">
						     	 <input id="noticeTitle" name="noticeTitle" type="text" style="width: 95%" class="inputxt"  datatype="*"  value='${tSNoticePage.noticeTitle}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">通知标题</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								内容:
							</label>
						</td>
						<td class="value">
								<input id="noticeContent" name="noticeContent" type="hidden" value='${tSNoticePage.noticeContent}'>
								<script id="content" type="text/plain" style="width:700px;" value='${tSNoticePage.noticeContent}'></script>
						  	 	<script type="text/javascript">
						  	 	var editor = UE.getEditor('content',{
					        	    toolleipi:true,//是否显示，设计器的 toolbars
					        	    textarea: 'design_content',   
					        	    //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个/*
					        	    toolbars: [[
					        	                'fullscreen', 'source', '|', 'undo', 'redo', '|','bold', 'italic', 'underline', 'fontborder',
								        	    'fontfamily', 'fontsize', '|', 'indent', '|',
								        	    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 
								        	    'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', '|',
								        	    'simpleupload','insertvideo','attachment'
						        	    ]],
					        	    wordCount:false,
					        	    elementPathEnabled:false,
					        	    initialFrameHeight:400
					        	});
						  	 	editor.ready(function() {
						  	 		editor.setContent($('#noticeContent').val());
						  	    });
							 </script>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">通知公告内容</label>
						</td>
					</tr>
					
					<tr>
					  <td align="right">
							<label class="Validform_label" >
								发布人:
							</label>
				      </td>
				      <td class="value">
				      <input readonly type="text" name="createUser" value="${tSNoticePage.createUser}">
				      </td>
				</tr>
					<tr>
					  <td align="right">
							<label class="Validform_label" >
								发布部门:
							</label>
				      </td>
				      <td class="value">
				      <input readonly type="text" name="createDepart" value="${tSNoticePage.createDepart}">
				      </td>
				</tr>
					<tr style="display: none">
					  <td align="right">
							<label class="Validform_label" >
								审批人:
							</label>
				      </td>
				      <td class="value">
				        <input type="text" name="approvername" id="approvername" value="${tSNoticePage.approver.realName}">
				        <input type="text" name="approver.id" id="approverid" value="${tSNoticePage.approver.id}" style="display: none;"><a onclick="selectapprover()">选择</a>
				      </td>
				</tr>																			
					<tr>
						<td align="right">
							<label class="Validform_label" >
								类型:
							</label>
						</td>
						<td class="value">
						     	<input type="radio" name="noticeType" value="2" <c:if test="${tSNoticePage.noticeType=='2'}">checked="checked"</c:if> />公告
         				&nbsp;&nbsp;<input type="radio" name="noticeType" value="3" <c:if test="${tSNoticePage.noticeType=='3'}">checked="checked"</c:if> />新闻
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">类型</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label" style="white-space:nowrap;">
								授权级别:
							</label>
						</td>
						<td class="value">
						     	 <input type="radio" id="noticeLevel" name="noticeLevel" value="1" id="nolevel" datatype="*"  <c:if test="${tSNoticePage.noticeLevel=='1'}">checked="checked"</c:if> 
						     	 <c:if test="${empty tSNoticePage.noticeLevel}">checked="checked"</c:if> />全员
         				&nbsp;&nbsp;<input type="radio" name="noticeLevel" id="rolelevel" value="2" <c:if test="${tSNoticePage.noticeLevel=='2'}">checked="checked"</c:if> />角色授权
         				&nbsp;&nbsp;<input type="radio" name="noticeLevel" id="userlevel" value="3" <c:if test="${tSNoticePage.noticeLevel=='3'}">checked="checked"</c:if> />用户授权
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">授权级别</label>
						</td>
					</tr>
					<tr id="scope">
				<td align="right"><label class="Validform_label"
					style="white-space: nowrap;"> 授权范围: </label></td>
                <td class="value">
                     <input type="text" name="userOrroleName" id="userOrroleName" value="${names }"style="width:300px">
                      <input type="text" name="userOrroleId" id="userOrroleId" style="display: none;" value="${ids }">
                </td>
			</tr>
					<tr>
						<td align="right">
							<label class="Validform_label" style="white-space:nowrap;">
								阅读期限:
							</label>
						</td>
						<td class="value">
						     <input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})"  style="width: 150px" id="noticeTerm" 
						     name="noticeTerm" ignore="ignore" value="<fmt:formatDate value='${tSNoticePage.noticeTerm}' type="date" pattern="yyyy-MM-dd"/>"> 		  
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">阅读期限</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
			<script type="text/javascript">
			function addrole(){
				 $.dialog.setting.zIndex = 9999; 
				   $.dialog({content: 'url:noticeAuthorityRoleController.do?selectRole', zIndex: 2500, title: '用户列表', lock: true, width: '800px', height: '600px', opacity: 0.4, button: [
				      {name: '<t:mutiLang langKey="common.confirm"/>', callback: function(){
				    	  var iframe = this.iframe.contentWindow;	 
				    	  var rowsData = iframe.$('#roleList').datagrid('getSelections');
							var realname=rowsData[0].roleName;
							var id=rowsData[0].id;
							$('#userOrroleName').val(realname);
							$('#userOrroleId').val(id);
				      }, focus: true},
				      {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
				   ]}).zindex();
			}
			$(function(){
				var tSNoticePage="${tSNoticePage.noticeLevel}";
				if(tSNoticePage=='1'){
					$("#scope").hide();
				}else if(tSNoticePage=='2'||tSNoticePage=='3'){
					$("#scope").show();
				}
				$("#userlevel").click(function(){
					$("#scope").show();
					openUserSelectMulti("userOrroleName","userOrroleId");
					//selectapprover();
				});	
				$("#rolelevel").click(function(){
					$("#scope").show();
					addrole();
				});
				$("#nolevel").click(function(){
					$("#userOrroleName").val("");
					$("#userOrroleId").val("");
					$("#scope").hide();
					
				});
			});
		</script>
 </body>
	