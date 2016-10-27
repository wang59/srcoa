<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
 <t:base type="jquery,easyui,tools"></t:base>

<div class="easyui-layout" fit="true">	
			<div region="center" style="padding: 1px;" >
				<t:datagrid name="noticeList" title="新闻公告"
					actionUrl="noticeController.do?approvedatagrid&edit=${type }" idField="id"
					fit="true" sortName="createTime" sortOrder="desc">
					<t:dgCol title="标题" field="noticeTitle"  width="160" style="color:#23527c;cursor:pointer" ></t:dgCol>
					<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
					<t:dgCol title="发布人" field="createUser" width="40"></t:dgCol>
					<t:dgCol title="发布部门" field="createDepart" width="40"></t:dgCol>
					<t:dgCol title="发布时间" field="createTime"
						formatter="yyyy-MM-dd hh:mm" width="40" align="center"></t:dgCol>			
					<t:dgCol title="阅读期限" field="noticeTerm"
						formatter="yyyy-MM-dd hh:mm" width="40" align="center"></t:dgCol>		

				</t:datagrid>
			</div>
</div>
 <script type="text/javascript" charset="utf-8">
 $("#noticeList").datagrid({
	  onClickCell:function(rowIndex, field, value){
		if(field=="noticeTitle"){
			var rows = $('#noticeList').datagrid('getRows');	
			var data=rows[rowIndex];
			approve(data.id);
		}
	  }
 });
    function approve(id){
    	var url="noticeController.do?golookup&id="+id+"&type=approve";
    	var type="${type}";
    	if(type!=null&&type=='edit'){
    		url="noticeController.do?golookup&id="+id+"&type=edit";
    	}
    	window.open(url);		
    }
 </script>