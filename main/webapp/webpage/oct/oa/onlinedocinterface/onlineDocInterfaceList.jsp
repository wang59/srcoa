<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addBtn').bind('click', function(){   
 		 var tr =  $("#add_jeecgOrderProduct_table_template tr").clone();
	 	 $("#add_jeecgOrderProduct_table").append(tr);
	 	 resetTrNum('add_jeecgOrderProduct_table');
    });  
	$('#delBtn').bind('click', function(){   
       $("#add_jeecgOrderProduct_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_jeecgOrderProduct_table');
    });
	$(document).ready(function(){
		$(".datagrid-toolbar").parent().css("width","auto");
		//将表格的表头固定
	    $("#jeecgOrderProduct_table").createhftable({
	    	height:'200px',
			width:'auto',
			fixFooter:false
			});
});
</script>

<div style="padding: 3px; height: 25px; width: width: 900px;" class="datagrid-toolbar"><a id="addBtn" href="#">添加</a> <a id="delBtn" href="#">删除</a></div>
<table border="0" cellpadding="2" cellspacing="0" style="width: 620px;" id="jeecgOrderProduct_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">属性名</td>
		<td align="center" bgcolor="#EEEEEE">字段类型</td>
		<td align="left" bgcolor="#EEEEEE">字段说明</td>
	</tr>
	<tbody id="add_jeecgOrderProduct_table">
		<c:if test="${fn:length(onlineDocInterfaceEntityList)  <= 0 }">
			<tr>
				<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
				<td align="left"><input nullmsg="主键"  errormsg="" name="onlineDocInterfaceEntityList[0].id" maxlength="10" type="hidden" value=""
					style="width: 10px;"></td>
				<td align="center"><input nullmsg="请输入属性名！"  errormsg="属性名" name="onlineDocInterfaceEntityList[0].propertyName" maxlength="50" type="text" value=""
					style="width: 120px;"></td>
				<td align="center"><input nullmsg="请输入字段类型"  errormsg="" name="onlineDocInterfaceEntityList[0].fieldType" maxlength="10" type="text" value="" 
				style="width: 120px;"></td>
				
				<td align="left"><input nullmsg="字段说明" errormsg="" name="onlineDocInterfaceEntityList[0].fieldDescription" maxlength="50" type="text" value=""
					style="width: 300px;"></td>
				<td align="left"><input nullmsg="与主表关联的编码"  errormsg="" name="onlineDocInterfaceEntityList[0].code" maxlength="10" type="hidden" value=""
					style="width: 120px;"></td>
			</tr>
		</c:if>
		<c:if test="${fn:length(onlineDocInterfaceEntityList)  > 0 }">
			<c:forEach items="${onlineDocInterfaceEntityList}" var="poVal" varStatus="stuts">
				<tr>
					<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
					<td align="left"><input  nullmsg="id" name="onlineDocInterfaceEntityList[${stuts.index }].id" maxlength="10" type="hidden"
						value="${poVal.id}" style="width: 10px;"></td>
					<td align="center"><input nullmsg="请输入属性名！"  errormsg="" name="onlineDocInterfaceEntityList[${stuts.index }].propertyName" maxlength="50" type="text"
						value="${poVal.propertyName}" style="width: 120px;"></td>
					<td align="center"><input nullmsg="请输入字段类型！"  errormsg="" name="onlineDocInterfaceEntityList[${stuts.index }].fieldType" maxlength="10" type="text"
						value="${poVal.fieldType}" style="width: 120px;"></td>
					
					<td align="left"><input nullmsg="字段说明" errormsg="" name="onlineDocInterfaceEntityList[${stuts.index }].fieldDescription" maxlength="50" type="text"
						value="${poVal.fieldDescription}" style="width: 300px;"></td>
					<td align="left"><input nullmsg="与主表关联的编码"  errormsg="" name="onlineDocInterfaceEntityList[${stuts.index }].code" maxlength="10" type="hidden"
						value="${poVal.code}" style="width: 120px;"></td>
					
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
