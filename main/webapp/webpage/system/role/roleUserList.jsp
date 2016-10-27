<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%--<t:datagrid name="userList" title="user.manage" actionUrl="roleController.do?roleUserDatagrid&roleId=${roleId}" fit="true" fitColumns="true" idField="id">--%>
<%--	<t:dgCol title="common.id" field="id" hidden="true" ></t:dgCol>--%>
<%--	<t:dgCol title="common.username" sortable="false" field="userName" width="5"></t:dgCol>--%>
<%--	<t:dgCol title="common.real.name" field="realName" width="5"></t:dgCol>--%>
<%--</t:datagrid>--%>

<t:datagrid name="roleUserList" title=""
            actionUrl="roleController.do?roleUserDatagrid&roleId=${roleId}" fit="true" fitColumns="true" idField="id" queryMode="group" checkbox="true">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="common.username" sortable="false" field="userName" query="true"></t:dgCol>
	<t:dgCol title="common.real.name" field="realName" query="true"></t:dgCol>
	<t:dgCol title="common.status" sortable="true" field="status" replace="common.active_1,common.inactive_0,super.admin_-1"></t:dgCol>
	<%--<t:dgCol title="common.operation" field="opt"></t:dgCol>	  
	<t:dgDelOpt title="common.delete" url="userController.do?del&id={id}&userName={userName}" />--%>
	<%--<t:dgToolBar title="common.add.param" langArg="common.user" icon="icon-add" url="userController.do?addorupdate&roleId=${roleId}" funname="add"></t:dgToolBar>--%>
	<%--<t:dgToolBar title="common.edit.param" langArg="common.user" icon="icon-edit" url="userController.do?addorupdate&roleId=${roleId}" funname="update"></t:dgToolBar>--%>
	<t:dgToolBar title="common.add.exist.user" icon="icon-add" url="roleController.do?goAddUserToRole&roleId=${roleId}" funname="add" width="800" height="600"></t:dgToolBar>
	<t:dgToolBar title="移除用户" icon="icon-putout" funname="deleteuser"></t:dgToolBar>
</t:datagrid>
<script type="text/javascript">
  function deleteuser(){
	  var datas=$('#roleUserList').datagrid('getSelections');
	  var ids="";
	  for(var i=0;i<datas.length;i++){
		  ids+=datas[i].id+",";
	  }
	  $.ajax({
	   		url:"roleController.do?deleteuser",
	   		data:{"roleId":"${roleId}","ids":ids},
	   		dataType:'json',
	   		type:'post', 
			success:function(msg) {
				 location.reload(); 
			}				
		});
  }
</script>

