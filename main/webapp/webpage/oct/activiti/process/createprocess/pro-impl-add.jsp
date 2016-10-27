<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil" %>
<!DOCTYPE html>
<html>
<head>
<title>增加流程类别</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style=" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="createAppController.do?save">
   <input  class="inputxt" name="id"  value="${impl.id }" style="display: none;">
	<table style="width: 600px;" cellpadding="0" cellspacing="1"  >		
		<tr >
			<td align="right" ><label class="Validform_label" > 流程名: </label></td>
			<td class="value"><input  class="inputxt" name="name"  value="${impl.name }" > </td>
		</tr>
		<tr >
			<td align="right" ><label class="Validform_label" > 流程类别: </label></td>
			<td class="value">
			<select name="type.id">
			  <c:forEach var="processclass" items="${processclasslist }">
			 <option value="${processclass.id }" 
			 <c:if test="${processclass.id==impl.type.id }">
			 selected="selected"
			 </c:if>
			 >${processclass.name }</option>  
			  </c:forEach>		
			</select>						
			</td>
		</tr>
		<tr >
			<td align="right" ><label class="Validform_label" > 序号: </label></td>
			<td class="value"><input  class="inputxt" name="sort"  value="${impl.sort }" > 			
		</tr>
		<tr >
			<td align="right" ><label class="Validform_label" > 绑定流程: </label></td>
			<td class="value"><input  class="inputxt" name="processdefineid"  value="${impl.processdefineid }" id="processid"> 
			<a href="#" class="easyui-linkbutton"  icon="icon-search" id="processselect" onclick="processclassselect()">选择</a></td>
		</tr>
		<tr >
			<td align="right" ><label class="Validform_label" > 绑定表单: </label></td>
			<td class="value"><input  class="inputxt" name="form"  value="${impl.form }" > </td>
		</tr>
		<tr >
			<td align="right" ><label class="Validform_label" > 流水号前缀: </label></td>
			<td class="value"><input  class="inputxt" name="prefix"  value="${impl.prefix }" datatype="*" validType="t_s_pro,prefix,id"> </td>
		</tr>
			
			<tr >
			<td align="right" ><label class="Validform_label" > 常用流程: </label></td>
			<td class="value"><input type="radio" name=common value="1" 
			<c:if test="${impl.common==1 }">checked="checked"</c:if> />是
                              <input type="radio" name="common" value="0" 
                      <c:if test="${impl.common==0 }">checked="checked"</c:if> 		      
                              />否</p> </td>
		</tr>	
		<tr >
			<td align="right" ><label class="Validform_label" > 描述: </label></td>
			<td class="value"><input  class="inputxt" name="processdescribe"  value="${impl.processdescribe }" >  </td>
		</tr>
		
	</table>
</t:formvalid>
</body>
<script type="text/javascript">
function processclassselect() {
	   $.dialog.setting.zIndex = 9999; 
	   $.dialog({content: 'url:createAppController.do?processselect', zIndex: 2105, title: '用户列表', lock: true, width: '800px', height: '600px', opacity: 0.4, button: [
	      {name: '<t:mutiLang langKey="common.confirm"/>', callback: callbackUserSelect, focus: true},
	      {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
	   ]}).zindex();
	}
	
	function callbackUserSelect() {
	var iframe = this.iframe.contentWindow;		
	var rowsData = iframe.$('#processselect').datagrid('getSelections');
	var key=rowsData[0].key;	
	$('#processid').val(key);
	}
</script>
</html>
