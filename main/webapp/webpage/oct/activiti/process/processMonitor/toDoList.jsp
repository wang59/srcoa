<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
<t:datagrid name="toDoList" title="待办事宜" autoLoadData="true" actionUrl="processMonitorController.do?todogrid" sortName="createTime"   fitColumns="true"
	idField="id" fit="true" queryMode="group" checkbox="false" sortOrder="desc">
	<t:dgCol title="编号" field="id" query="true"></t:dgCol>
	<t:dgCol title="申请单ID" field="actTaskBusiness.businessId" hidden="true" width="20"></t:dgCol>
	<t:dgCol title="标题" field="actTaskBusiness.businessTitle" query="true" width="40"></t:dgCol>
	<t:dgCol title="创建人" field="actTaskBusiness.businessCreateName"  align="center" query="true" frozenColumn="false"  width="20"></t:dgCol>
	<t:dgCol title="创建时间" sortable="true" field="createTime" formatter="yyyy-MM-dd hh:mm:ss"  align="center"  query="false" queryMode="group" width="40"></t:dgCol>
	<t:dgCol title="处理人" field="tSBaseUser.realName" query="true"  align="center" width="20"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="150"></t:dgCol>
	<t:dgFunOpt  funname="change(id)" title="修改审批人" />
</t:datagrid></div>
</div>
		<script type="text/javascript" src="plug-in/ace/js/bootstrap-tab.js"></script>
<script type="text/javascript">
function change(taskId) {
	   $.dialog.setting.zIndex = 8999; 
	   $.dialog({content: 'url:processMonitorController.do?editapp&taskId='+taskId, zIndex: 2100, title: '', lock: true, width: '600px', height: '350px', opacity: 0.4, button: [
	      {name: '<t:mutiLang langKey="common.confirm"/>', callback:function(){
	    	 
	    	  var iframe = this.iframe.contentWindow;
	    	  var user = iframe.$('#username').val();	
	    	  var comment=iframe.$('#comment').val();	    	
	    	  $.ajax({
	    			url:"processMonitorController.do?changApprover",
	    			data:{"userId":user,"taskId":taskId,"comment":comment},
	    			dataType:'json',
	    			type:'post', 
	    			success:function(msg) {	    				
	    			location.reload();
	    				
	    			}				
	    		});
	      }, focus: true},
	      {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
	   ]}).zindex();
	}

	</script>

