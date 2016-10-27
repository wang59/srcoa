<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style>
td[field="opt"] .datagrid-cell{
color:rgb(18,156,235);
text-align:center;
}
td[field="opt"] .datagrid-cell a{
color:rgb(255,80,80)!important;
margin-right:20px;
}
td[field="opt"] .datagrid-cell a:first-child{
color:rgb(18,156,235)!important;
}
.btn-throw,.btn-write{
width:16px;
height:17px;
display:inline-block;
position:relative;
top:4px;
margin-right:4px;
background:url("webpage/images/main/icon-throw.png") no-repeat;
background-size:100%;
}
.btn-write{
width:20px;
background:url("webpage/images/main/icon-write.png") no-repeat;
}
</style>
<div id="main_depart_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tSNoticeList" checkbox="false" fitColumns="false" title="新闻公告" actionUrl="noticeController.do?datagrid2" idField="id" fit="true" queryMode="group" sortName="createTime" sortOrder="desc">
   <t:dgCol title="ID"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="noticeTitle"  hidden="false"  queryMode="group"  width="240" style="color:#23527c;cursor:pointer"></t:dgCol>
   <%-- <t:dgCol title="通知公告内容"  field="noticeContent"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="common.type.name"  field="noticeType"  hidden="false"  queryMode="group"  width="60" replace="通知_1,公告_2,新闻_3"></t:dgCol>
   <t:dgCol title="授权级别"  field="noticeLevel"  hidden="false"  queryMode="group"  width="100" replace="全员_1,角色授权_2,用户授权_3"></t:dgCol>
   <t:dgCol title="阅读期限"  field="noticeTerm" formatter="yyyy-MM-dd" hidden="false"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="发布人"  field="createUser"  hidden="false"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="发布部门"  field="createDepart"  hidden="false"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="审批人"  field="approver.realName"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="状态"  field="deploy"  hidden="false"  queryMode="group"  width="60" replace="未审批_0,通过_1,未通过_2,修改中_3"></t:dgCol>
   <t:dgCol title="common.operation" field="opt" width="150"></t:dgCol>
   <t:dgFunOpt funname="goedit(id,deploy)" title="<i class='btn-write'></i>编辑" exp="deploy#ne#1"></t:dgFunOpt>
   <t:dgConfOpt exp="deploy#ne#1" url="noticeController.do?doDel&id={id}" message="确定删除吗" title="<i class='btn-throw'></i>删除"></t:dgConfOpt>
   <t:dgToolBar title="common.add" icon="icon-add" url="noticeController.do?goAdd" funname="add" width="800" height="600"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <div data-options="region:'east',
	title:'通知公告授权管理',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 500px; overflow: hidden;">
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="subListpanel"></div>
</div>

 <script type="text/javascript">

 function goedit(id,deploy){
			createwindow("编辑","noticeController.do?goUpdate&id="+id,800,600);	
 }
 
 $("#tSNoticeList").datagrid({
	  onClickCell:function(rowIndex, field, value){
		if(field=="noticeTitle"){
			var rows = $('#tSNoticeList').datagrid('getRows');	
			var data=rows[rowIndex];
			doRead(data.id,data.isRead);
		}
	  }
 });
 
 function doRead(id,isRead){
	  var addurl = "noticeController.do?goNotice&id="+id;
		window.open( addurl);
 }
 </script>
