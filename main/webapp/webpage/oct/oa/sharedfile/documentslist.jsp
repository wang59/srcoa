<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
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
        var departname = treeNode.name;
        var queryParams = $('#fList').datagrid('options').queryParams;
        queryParams.documentId = treeNode.id;
        $('#fList').datagrid('options').queryParams=queryParams;
        $("#fList").datagrid("reload");
    }
  
    //加载展开方法
    function zTreeOnExpand(event, treeId, treeNode){
        var treeNodeId = treeNode.id;
        $.post(
                'sharedFileController.do?getFileInfo',
                {parentid:treeNodeId,type:'${param.type}'},
                function(data){
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var dbDate = eval(d.msg);
                        var tree = $.fn.zTree.getZTreeObj("shareFile");                     
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
                'sharedFileController.do?getFileInfo&type=${param.type}',
                function(data){
                    var d = $.parseJSON(data);
                    if (d.success) {         
                        var dbDate = eval(d.msg);                      
                        $.fn.zTree.init($("#shareFile"), setting, dbDate);
                    }
                }
        );
    });
</script>
<div class="easyui-layout" style="width: 100%; height: 683px;">
    <div data-options="region:'west',split:true" title="文档文件" style="width:100px;>
        <ul id="shareFile" class="ztree" style="margin-top: 30px;"></ul>
    </div>
    <div data-options="region:'center'">
       <t:datagrid name="fList" title="文件" actionUrl="sharedFileController.do?documentList&type=${param.type }" idField="id" fit="true" fitColumns="true"  queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="名称" field="attachmenttitle" width="120" query="true" style="color:blue;cursor:pointer"></t:dgCol>
	<t:dgCol title="文件大小" field="docsize" hidden="false" width="20"></t:dgCol>
	<t:dgCol title="类型" field="extend" hidden="false" width="20"></t:dgCol>
	<t:dgCol title="上传时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss" align="center" hidden="false" width="40"></t:dgCol>
	<t:dgCol title="上传人" field="createname" hidden="false" width="20"></t:dgCol>
	<t:dgCol title="操作" field="opt" extend="" hidden="false" width="20"></t:dgCol>
	<t:dgToolBar title="文件录入" icon="icon-add" funname="add" url="sharedFileController.do?addFiles&docId=${param.documentId }&type=${param.type }" operationCode="add"></t:dgToolBar>
	<t:dgToolBar title="文件删除"  funname="del" operationCode="del"></t:dgToolBar>
	<t:dgFunOpt funname="download(id)" title="<i style='color:red'>下载</i>"></t:dgFunOpt>
</t:datagrid>
    </div>
    <iframe id="iframe" style="display: none"></iframe>
</div>

<script type="text/javascript">
function del()
{
	var row = $('#fList').datagrid('getSelected');
	if(row!=null){
		$.ajax({
			url : "sharedFileController.do?delDocument",
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
function download(id){
	var url="sharedFileController.do?viewFile&fileid="+id;
	$("#iframe").attr("src",url);
}
$("#fList").datagrid({
	onDblClickCell:function(rowIndex, field, value){
		if(field=="attachmenttitle"){
			var rows = $('#fList').datagrid('getRows');	
			var data=rows[rowIndex];
			var url="sharedFileController.do?viewFile&fileid="+data.id;
			$("#iframe").attr("src",url);
		}
	  }
});
</script>

