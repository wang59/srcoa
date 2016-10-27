<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<link rel="stylesheet" type="text/css" href="plug-in/ztree/css/zTreeStyle.css">
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<div class="easyui-layout" style="width:800px;height:600px;">   
    <div data-options="region:'center'">
        <t:datagrid name="processselect" title="流程选择" actionUrl="processAgentController.do?processdatagrid&agentlist=${agentlist }"
                    fit="true" fitColumns="true" idField="id" queryMode="group" checkbox="true" onLoadSuccess="initCheck">
           <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="流程名" field="name" query="true"></t:dgCol> 
            <t:dgCol title="类别"  field="type.name" query="true"></t:dgCol>                    
        </t:datagrid>
    </div>
</div>

<script type="text/javascript">
function initCheck(data){
	var ids = "${ids}";
	var idArr = ids.split(",");
	for(var i=0;i<idArr.length;i++){
		if(idArr[i]!=""){
			$("#processselect").datagrid("selectRecord",idArr[i]);
		}
	}
}
</script>
