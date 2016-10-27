<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="departUserList" title="common.operation"
            actionUrl="departController.do?userDatagrid&departid=${departid}" fit="true" fitColumns="true" idField="id" queryMode="group" checkbox="true">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="common.username" sortable="false" field="userName" query="true"></t:dgCol>
	<t:dgCol title="common.real.name" field="realName" query="true"></t:dgCol>
	<t:dgCol title="common.status" sortable="true" field="status" replace="common.active_1,common.inactive_0,super.admin_-1"></t:dgCol>
	<t:dgToolBar title="common.add.exist.user" icon="icon-add" url="departController.do?goAddUserToOrg&orgId=${departid}" funname="add" width="500"></t:dgToolBar>
    <t:dgToolBar title="移除用户" icon="icon-putout" funname="deleteuser"></t:dgToolBar>
    <%--update-end--Author:zhangguoming  Date:20140826 for：添加有客户--%>
</t:datagrid>
<script type="text/javascript">
  function deleteuser(){
	  var datas=$('#departUserList').datagrid('getSelections');
	  var ids="";
	  for(var i=0;i<datas.length;i++){
		  ids+=datas[i].id+",";
	  }
	  $.ajax({
	   		url:"departController.do?deleteuser",
	   		data:{"departid":"${departid}","ids":ids},
	   		dataType:'json',
	   		type:'post', 
			success:function(msg) {
				 location.reload(); 
			}				
		});
  }