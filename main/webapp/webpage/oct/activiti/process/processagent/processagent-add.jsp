<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil" %>
<!DOCTYPE html>
<html>
<head>
<title>新建代理</title>

<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style=" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="processAgentController.do?saveProcessAgent">
	<table style="width: 600px;" cellpadding="0" cellspacing="1"  >
		
		<tr >
			<td align="right" ><label class="Validform_label" > 创建人: </label></td>
			<td class="value"><input  class="inputxt" readonly="readonly" name="look"  value=<%=ResourceUtil.getSessionUserName().getRealName() %> id="createname"> 
		              	      <input  class="inputxt" name="creater.id"  value=<%=ResourceUtil.getSessionUserName().getId() %> id="createid" style="display: none">
		              	      
			</td>
			
		</tr>
		<tr >
			<td align="right" ><label class="Validform_label"> 代理人: </label></td>
			<td class="value"><input  class="inputxt" name="realname"  id="realname"> <input  class="inputxt" name="agent.id"   id="userid" style="display: none;">
			     
			</td>
		</tr>
	
		<tr>
			<td align="right"><label class="Validform_label"> 生效时间起: </label></td>
			<td class="value"><input name="begintime" id="begintime"class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate: '%y-%M-%d',maxDate:'#F{$dp.$D(\'endtime\')}'})" style="width: 150px"
				 errormsg="日期格式不正确!" ignore="ignore"> </td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 生效时间止: </label></td>
			<td class="value"><input name="endtime" id="endtime" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'begintime\')}'})" style="width: 150px"
			      errormsg="日期格式不正确!" ignore="ignore"> <span class="Validform_checktip"></span></td>
		</tr>
		<tr >
			<td align="right" ><label class="Validform_label"> 代理已有待办事项: </label></td>
			<td class="value">
			<input type="radio" name="style" value="1" checked="checked">是
			<input type="radio" name="style" value="2" >否
			 <span class="Validform_checktip"></span></td>
		</tr>
		<tr >
			<td align="right" ><label class="Validform_label"> 代理流程范围: </label></td>
			<td class="value">
			<select name="scope" id="scope" style="width: 80px;">
			  <option value="0" selected="selected" id="option1">选择</option>
			  <option value="1" id="option2">全部</option>
			</select>
						
			</td>
		</tr>
		<tr >
			<td align="right" ><label class="Validform_label"> </label></td>
			<td class="value">
			<input  style="width:100%;"  class="inputxt" name="scopes"  id="scopes"><span class="Validform_checktip"></span>
			<input  class="inputxt" name="ids"  id="ids" style="display: none;">
			</td>
		</tr>
	</table>
</t:formvalid>
</body>
<script type="text/javascript">
	$("#realname").click(function(){
		openUserSelectSingle("realname","userid");
	});
	$(function(){    	
    	$("#option1").click(function(){
    		$("#scopes").show(); 
    	});
    	$("#option2").click(function(){
    		$("#scopes").hide(); 
    	});
    	$("#scopes").click(function(){
    		var create=$("#createid").val();
    		var agent=$("#userid").val();
    		var begintime=$("#begintime").val();
    		var endtime=$("#endtime").val();
    		if(create==null||create=="") {
    			alert("请选择创建人");
    			return;
    		}
    		if(agent==null||agent=="") {
    			alert("请选择代理人");
    			return;
    		}
    		if(begintime==null||begintime==""){
    			alert("请选择开始时间");
    			return;
    		}
    		if(endtime==null||endtime==""){
    			alert("请选择结束时间");
    			return;
    		}
    		$.dialog.setting.zIndex = 9999; 
    		var ids=$("#ids").val();
  		   	$.dialog({content: 'url:processAgentController.do?processSelect&ids='+ids+"&agent.id="+agent+"&begintime="+begintime+"&endtime="+endtime+"&creater.id="+create, zIndex: 2105, title: '流程列表', lock: true, width: '800px', height: '600px', opacity: 0.4, button: [
  		    {name: '<t:mutiLang langKey="common.confirm"/>', callback: callbackProcessSelect, focus: true},
  		    {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
  		   	]}).zindex();	 
    	});
	});
    function callbackProcessSelect() {
 		var iframe = this.iframe.contentWindow;		
 		var rowsData = iframe.$('#processselect').datagrid('getSelections');
	 	if(rowsData.length>0){		
			var ids='';
		    var names='';
		    for(i=0;i<rowsData.length;i++){				  
 				ids+=rowsData[i].id+',';
 				names+=rowsData[i].name+',';				     
 			}
 	        $("#scopes").val(names);
 	        $("#ids").val(ids);
 		}
	}
</script>
</html>
