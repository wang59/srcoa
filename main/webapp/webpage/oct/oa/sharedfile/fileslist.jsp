<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="fileList" title="common.department.list"
                    actionUrl="sharedFileController.do?datagrid&type=${param.type }"
                    treegrid="true" idField="departid" pagination="false">
            <t:dgCol title="id" field="id" treefield="id" hidden="true"></t:dgCol>
            <t:dgCol title="名称" field="fileName" treefield="text"></t:dgCol>
            <t:dgToolBar title="录入" icon="icon-add" url="sharedFileController.do?goAdd&type=${param.type }" funname="add" operationCode="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="sharedFileController.do?goUpdate" funname="update" operationCode="update"></t:dgToolBar>  
            <t:dgToolBar title="删除" icon="icon-edit"  funname="dodel" operationCode="del"></t:dgToolBar>                  
        </t:datagrid>
        </div>
        <script type="text/javascript">
       
        function dodel(id){
        	var row = $('#fileList').datagrid('getSelected');
        	var url="sharedFileController.do?doDel&id="+row.id;
        	alert(url);
        	if(row!=null){
        	$.ajax({
    			url : "sharedFileController.do?doDel",
    			data : {
    				"id" : row.id,
    			},
    			dataType : 'json',
    			type : 'post',
    			success : function(msg) {
    				alert(msg.msg);
    				location.reload();
    				
    			}
    		});
        	}
        }
        </script>