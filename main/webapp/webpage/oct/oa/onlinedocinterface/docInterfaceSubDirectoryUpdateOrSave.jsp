<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>接口文档目录</title>
		<t:base type="jquery,easyui,tools,DatePicker"></t:base>
		<script src="webpage/jeecg/onlinedoc/ajaxfileupload.js"></script>
		
		<script type="text/javascript">
  //初始化下标
	function resetTrNum(tableId) {
		$tbody = $("#"+tableId+"");
		$tbody.find('>tr').each(function(i){
			$(':input, select', this).each(function(){
				var $this = $(this), name = $this.attr('name'), val = $this.val();
				if(name!=null){
					if (name.indexOf("#index#") >= 0){
						$this.attr("name",name.replace('#index#',i));
					}else{
						var s = name.indexOf("[");
						var e = name.indexOf("]");
						var new_name = name.substring(s+1,e);
						$this.attr("name",name.replace(new_name,i));
					}
				}
			});
		});
	}
 </script>
 
		<script type="text/javascript">
		 	$(function(){
		 		//触发click方法
		 		$('#onlineDocSortTree').combotree({
					url : 'onlineDocInterfaceController.do?tree',
					panelHeight : 200,
					width : 157,
					value : '${onlineDocPage.treeNode}',
					onClick : function(node) {
						$("#treeNode").val(node.id);
					}
				});
		 		
		 		
		 		//检查是否是新添加
		 		var thisId = $("#id").val();
		 		if(thisId != ''){
		 			$("#showAppUrlDemo").show();
		 		}else{
		 			$("#showAppUrlDemo").hide();
		 		}
		 	});
			
		</script>
	</head>
	<body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="onlineDocInterfaceSubDirectoryController.do?doAdd" tiptype="1">
		<input id="id" name="id" type="hidden" value="${onlineDocPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" id = "formtableId">
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							接口模块：
						</label>
					</td>
					<td class="value">
						<input id="onlineDocSortTree" />
						<input id="treeNode" name="treeNode" style="display: none;" value="${onlineDocPage.treeNode}" datatype="*"/>
						<span class="Validform_checktip">请选择接口模块</span>
						<label class="Validform_label" style="display: none;">接口模块</label>
					</td>
				</tr>
		        <tr>
			<td align="right"><label class="Validform_label">服务名称： </label></td>
			<td class="value"><input class="inputxt" name="serviceName" value="${onlineDocPage.serviceName}" datatype="*"> <span class="Validform_checktip">请填写服务名称</span></td>
		       </tr>
		       
		         <tr>
			<td align="right"><label class="Validform_label">请求地址： </label></td>
			<td class="value"><input style="width: 350px;" class="inputxt" name="serviceUrl" value="${onlineDocPage.serviceUrl}" datatype="*"> <span class="Validform_checktip">请输入请求地址</span>&nbsp;&nbsp;<span id="showAppUrlDemo" ><a href="${onlineDocPage.serviceUrl}" target="_blank" >演示地址</a></span></td>
		       </tr>
		       
		       
		    <tr>
			<td align="right"><label class="Validform_label"> 请求方式： </label></td>
			<td class="value">
			<select id="requestMode" name="requestMode" datatype="*">
				<option value="POST" <c:if test="${onlineDocPage.requestMode=='POST'}">selected="selected"</c:if>>POST</option>
				<option value="GET" <c:if test="${onlineDocPage.requestMode=='GET'}">selected="selected"</c:if>>GET</option>
				<option value="POST+GET" <c:if test="${onlineDocPage.requestMode=='POST+GET'}">selected="selected"</c:if>>POST+GET</option>
				</select> <span class="Validform_checktip">请填写请求方式</span></td>
		   </tr> 
		       
		       
		  <!-- 
		       <tr>
		       <td align="right"><label class="Validform_label">请求方式： </label></td>
			    <td class="value"><input class="inputxt" name="requestMode" value="${onlineDocPage.requestMode}" datatype="s"> <span class="Validform_checktip">请填写请求方式</span></td>
		       </tr>
		        -->     
		       
		       <tr>
			<td align="right"><label class="Validform_label"> 操作类型： </label></td>
			<td class="value">
			<select id="operationType" name="operationType" datatype="*">
			
				<option value="select" <c:if test="${onlineDocPage.operationType=='select'}">selected="selected"</c:if>>select</option>
				<option value="update" <c:if test="${onlineDocPage.operationType=='update'}">selected="selected"</c:if>>update</option>
				<option value="delete" <c:if test="${onlineDocPage.operationType=='delete'}">selected="selected"</c:if>>delete</option>
				<option value="save" <c:if test="${onlineDocPage.operationType=='save'}">selected="selected"</c:if>>save</option>
				<option value="batchselect" <c:if test="${onlineDocPage.operationType=='batchSelect'}">selected="selected"</c:if>>batchSelect</option>
				<option value="batchUpdate" <c:if test="${onlineDocPage.operationType=='batchUpdate'}">selected="selected"</c:if>>batchUpdate</option>
				<option value="batchdelete" <c:if test="${onlineDocPage.operationType=='batchDelete'}">selected="selected"</c:if>>batchDelete</option>
				<option value="batchsave" <c:if test="${onlineDocPage.operationType=='batchSave'}">selected="selected"</c:if>>batchSave</option>
				<option value="addOrUpdate" <c:if test="${onlineDocPage.operationType=='addOrUpdate'}">selected="selected"</c:if>>addOrUpdate</option>
				
			</select> <span class="Validform_checktip">请选择操作类型</span></td>
		</tr>
		       
		       <tr>
					<td align="right">
						<label class="Validform_label">
							功能说明:
						</label>
					</td>
					<td class="value">
						<textarea rows="10" cols="80" id="description" name="description" >${onlineDocPage.description}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">描述</label>
					</td>
				</tr>
		       
		       <tr>
					<td align="right">
						<label class="Validform_label">
							返回结果:
						</label>
					</td>
					<td class="value">
						<textarea rows="10" cols="80" id="resulTant" name="resulTant" >${onlineDocPage.resulTant}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">返回结果</label>
					</td>
				</tr>
		       
		       
				
			</table>
			
		<div style="width: auto; height: 200px;"><%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
	    <div style="width: 690px; height: 1px;"></div>
	    <t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
		<t:tab href="onlineDocInterfaceSubDirectoryController.do?onlinedocinterfacelist&id=${onlineDocPage.id }" icon="icon-search" title="字段说明" id="Product"></t:tab>
		
	</t:tabs></div>
		</t:formvalid>
      <!-- 添加 产品明细 模版 -->
<table style="display: none">
	<tbody id="add_jeecgOrderProduct_table_template">
		<tr>
				<td align="center"><input style="width: 10px;" type="checkbox" name="ck" /></td>
				 <td align="left"><input nullmsg="id"  errormsg="" name="onlineDocInterfaceEntityList[0].id" maxlength="10" type="hidden" value=""
					style="width: 120px;"></td>
				 <td align="center"><input nullmsg="请输入属性名！"  errormsg="属性名" name="onlineDocInterfaceEntityList[0].propertyName" maxlength="100" type="text" value=""
					style="width: 120px;"></td>
				<td align="center"><input nullmsg="请输入字段类型"  errormsg="" name="onlineDocInterfaceEntityList[0].fieldType" maxlength="30" type="text" value="" 
					style="width: 120px;"></td>
				
				<td align="left"><input nullmsg="字段说明" errormsg="" name="onlineDocInterfaceEntityList[0].fieldDescription" maxlength="100" type="text" value=""
					style="width: 300px;"></td>
				<td align="left"><input nullmsg="与主表关联的编码"  errormsg="" name="onlineDocInterfaceEntityList[0].code" maxlength="10" type="hidden" value=""
					style="width: 120px;"></td>
			</tr>
	</tbody>

</table>
	</body>
