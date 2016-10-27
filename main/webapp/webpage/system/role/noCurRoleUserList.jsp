<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<%--非当前角色的用户列表--%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" type="text/css" href="plug-in/ztree/css/zTreeStyle.css">
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<%--update-start--Author:wangkun  Date:20160327 TASK #956 【UI标签】封装选择用户标签--%>
<script type="text/javascript">
    var setting = {
        check: {
            enable: false,
            chkboxType: { "Y": "", "N": "" }
        },
        data: {
            simpleData: {
                enable: true
            }
        },callback: {
            onExpand: zTreeOnExpand,
            onClick:onClick
        }
    };
    function onClick(event, treeId, treeNode){      
        var queryParams = $('#noCurRoleUserList').datagrid('options').queryParams;
        queryParams.orgIds = treeNode.id;
        $('#noCurRoleUserList').datagrid('options').queryParams=queryParams;
        $("#noCurRoleUserList").datagrid("reload");
    }
    //加载展开方法
    function zTreeOnExpand(event, treeId, treeNode){
        var treeNodeId = treeNode.id;
        $.post(
                'departController.do?getDepartInfo',
                {parentid:treeNodeId},
                function(data){
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var dbDate = eval(d.msg);
                        var tree = $.fn.zTree.getZTreeObj("departSelect");                     
                        var children=treeNode.children;
                        if(children==null)
                        	{
                               tree.addNodes(treeNode, dbDate);
                        	}
                    }
                }
        );
    }

    //首次进入加载level为1的
    $(function(){
        $.post(
                'departController.do?getDepartInfo',
                function(data){
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var dbDate = eval(d.msg);
                        $.fn.zTree.init($("#departSelect"), setting, dbDate);
                    }
                }
        );
    });
</script>

<div id="main_role_list" class="easyui-layout" fit="true">
	<div data-options="region:'west',split:true"
		title="<t:mutiLang langKey='common.department'/>"
		style="width: 200px;">
		<ul id="departSelect" class="ztree" style="margin-top: 30px;"></ul>
	</div>
	<div region="center" style="padding:0px;border:0px">
        <t:datagrid name="noCurRoleUserList" title="common.operation"
                    actionUrl="roleController.do?addUserToRoleList&roleId=${param.roleId}" fit="true" fitColumns="true"
                    idField="id" checkbox="true" queryMode="group">
            <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="common.username" sortable="false" field="userName" query="true"></t:dgCol>
            <t:dgCol title="common.real.name" field="realName" query="true"></t:dgCol>
            <t:dgCol title="common.status" sortable="true" field="status" replace="common.active_1,common.inactive_0,super.admin_-1"></t:dgCol>
        </t:datagrid>
    </div>
</div>

<div style="display: none">
    <t:formvalid formid="formobj" layout="div" dialog="true" action="roleController.do?doAddUserToRole&roleId=${param.roleId}" beforeSubmit="setUserIds">
        <input id="userIds" name="userIds">
    </t:formvalid>
</div>
<script>
    function setUserIds() {
        $("#userIds").val(getUserListSelections('id'));
        return true;
    }

    function getUserListSelections(field) {
        var ids = [];
        var rows = $('#noCurRoleUserList').datagrid('getSelections');
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i][field]);
        }
        ids.join(',');
        return ids
    }
</script>
