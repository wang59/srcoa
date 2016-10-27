<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.js" type="text/javascript"></script>
<link href="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="plug-in/ztree/css/zTreeStyle.css">
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<%--update-start--Author:wangkun  Date:20160327 TASK #956 【UI标签】封装选择用户标签--%>
<script type="text/javascript">
//首次进入加载level为1的
$(function(){
    init();
});
function init(){
	var setting = {
        check: {
            enable: false,
            chkboxType: { "Y": "", "N": "" }
        },
        data:{ // 必须使用data  
            simpleData : {  
                enable : true,  
            }  
        }, 
        callback: {
			onExpand: zTreeOnExpand,
//			onAsyncSuccess :zTreeOnAsyncSuccess,
            onClick:onClick
        }
    };

	$.post('departController.do?getDepartInfo', function(data){
            var d = $.parseJSON(data);
            if (d.success) {
                var dbDate = eval(d.msg);
                $.fn.zTree.init($("#departSelect"), setting, dbDate);
            }
        }
    );

	try{
		var ids = window.parent.document.getElementById("${_ids}").value.split(",");
		var names = window.parent.document.getElementById("${_ids}View").value.split(",");
		if(ids != "" && ids.length == names.length&& ids.length > 0){
			for(var i=0; i<ids.length; i++){
				$('#user_select').append("<option value='"+ids[i]+"'>"+names[i]+"</option>");
			}
		}
	}catch (e){}
}

function onClick(event, treeId, treeNode){
    var departname = treeNode.name;
    var queryParams = new Object();//$('#userList1').datagrid('options').queryParams;
    queryParams.orgIds = treeNode.id;
    $('#userList1').datagrid('options').queryParams=queryParams;
    $("#userList1").datagrid("reload");
}
//加载展开方法
function zTreeOnExpand(event, treeId, treeNode){
	if(treeNode.children == null){
	    var treeNodeId = treeNode.id;
	    $.post('departController.do?getDepartInfo',
	    	{parentid:treeNodeId},
	        function(data){
	            var d = $.parseJSON(data);
	            if (d.success) {
	                var dbDate = eval(d.msg);
	                var tree = $.fn.zTree.getZTreeObj("departSelect");
	                tree.addNodes(treeNode, dbDate);
	            }
	        }
	    );
	}
}

function callbackUserSelect(_ids) {//回调
	var rowsData = $('#user_select option');
	if (!rowsData || rowsData.length == 0) {
		tip('<t:mutiLang langKey="common.please.select.edit.item"/>');
		return;
	}
	var ids = new Array(), names = new Array();
	for(var i=0;i<rowsData.length;i++){
		ids.push($(rowsData[i]).val());
		names.push($(rowsData[i]).text());
	}
	try{
		window.parent.document.getElementById(_ids).value = ids;
		window.parent.document.getElementById(_ids+ "View").value = names;
	}catch (e){}
	var map = new Object();
	map.ids=ids;
	map.names=names;
	return map;
}
function addSelect(rowsData, select) {
	for(var i=0;i<rowsData.length;i++){
		if($(select).find("option[value="+rowsData[i].id + "]").length==0)
			select.append("<option value='"+rowsData[i].id+"'>"+rowsData[i].realName+"</option>");
	}
}
</script>
<style>
	.selectuser-header ul li{
          padding:2px 10px;
          float:left;
          border-right: 1px solid #787878;
          display: block;
      }
      .selectuser-header ul li:last-child{
      	border-right:0;
      }
      .selectuser-header ul li:first-child{
      padding-left:0px;
      }
      .select-list{
      	display:block;
      }
      .select-content{
      	margin:10px 0 0 10px;
      }
      .selectuser-header{
      	position:relative;
      }
      .select-img{
      	width:60px;
      	height:60px;
      	background:rgb(0, 176, 240);
      	margin:10px 0 0 10px;
      }
      .selectuser-header h1{
      font-weight:bold;
      font-size:18px;
      position:absolute;
      left:80px;
      top:6px;
      }
      .selectuser-header ul{
      font-size:18px;
      position:absolute;
      left:80px;
      top:32px;
      }
      .selectuser-header ul li a{
      color:#787878;
      }
      .selectuser-header ul li.active a{
      color:rgb(0, 176, 240);
      }
      
      /*panel样式修改*/
      #userList1Form>span{
      	position:absolute;
      	top:10px;
      }
      .datagrid-wrap{
      height:532px!important;
      }
      .layout-panel-center>.panel-noscroll{
      height:540px!important;
      }
      .datagrid>.panel-header{
      display:none;
      }
      .panel.layout-panel.layout-panel-west.layout-split-west{
      top:48px!important;
      }
      .layout-split-east{
      height:486px!important;
      }
      .panel.layout-panel.layout-panel-east.layout-split-east{
      top:48px!important;
      }
      .panel.layout-panel.layout-panel-center{
      height:600px!important;
      left:0!important;
      width:1000px!important
      }
      .datagrid-pager.pagination{
      width:396px!important;
      margin-left:200px;
      border:1px solid #ddd!important;
      }
      .datagrid-view{
      width:396px!important;
      margin-left:200px;
      border:1px solid #ddd!important;
      }
      .datagrid-view2{
      width:342px!important;
      }
      .layout-split-east .panel-body-noheader table{
      height:487px!important;
      }
      .layout-split-west .layout-body{
      height:455px!important;
      }
      #user_select{
      width:200px!important;
      height:400px!important;
      }
      /*panel样式修改*/
      
      
</style>
<body style="overflow-y: hidden" scroll="no">
	<div class="selectuser-header">
		<div class="select-img"></div>
		<h1>人员选择</h1>
 		<ul class="select-list" id="user-select">
        	<li id="autoselect"><a href="#">按组织结构</a></li>
        	<li id="select"><a href="#">同部门</a></li>
        	<li id="subordinate"><a href="#">我的下属</a></li>
    	</ul>
    </div>
    <div class="select-content" id="user-select-content">
        <div class="select-pane">
            <div class="easyui-layout" style="width:1000px;height:600px;">
    			<div data-options="region:'west',split:true" title="<t:mutiLang langKey='common.department'/>" style="width:200px;">
        			<ul id="departSelect" class="ztree" style="margin-top: 30px;"></ul>
    			</div>
    			<div data-options="region:'east',split:true" style="width:400px;overflow: hidden;">
					<table align="center" style="width: 400px;height:600px;" >
						<tr>
							<td align=right>
								<input type="button" value="&gt&gt" onclick="addSelect($('#userList1').datagrid('getSelections'),$('#user_select'));" style="font: 9pt 宋体;width: 60px;height: 20px;margin: 0px;padding: 0px;color: #333333;background: #CCCCCC;border: 0px solid #0000FF;	border: 3px double #DDDDDD;cursor: pointer;"/>
								<br/><br/><br/><br/>
								<input type="button" value="&lt&lt" onclick="$('#user_select option:selected').remove();" style="font: 9pt 宋体;width: 60px;height: 20px;margin: 0px;padding: 0px;color: #333333;background: #CCCCCC;border: 0px solid #0000FF;	border: 3px double #DDDDDD;cursor: pointer;"/>
							</td>
							<td align="center">
								<select id="user_select" name="user_select" multiple="multiple"  size="15" style="width:150px;height:250px;">
								</select>
							</td>
						</tr>
					</table>
    			</div>
    				<div data-options="region:'center'">
        				<t:datagrid name="userList1" title="common.user.select" actionUrl="userController.do?selectusers" extendParams="queryParams:{orgIds:'${orgIds }'}," checkbox="true"
                   	 	fit="true" fitColumns="true" idField="id" queryMode="group" sortName="createDate" sortOrder="desc">
            				<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
            				<t:dgCol title="common.username" sortable="false" field="userName" query="false"></t:dgCol>
            				<t:dgCol title="common.real.name" field="realName" query="false"></t:dgCol>
            				<t:dgCol title="common.department" sortable="false" field="userOrgList.tsDepart.departname" query="false"></t:dgCol>
            				<t:dgCol title="common.role" field="userKey" hidden="true" ></t:dgCol>
            				<t:dgCol title="common.createby" field="createBy" hidden="true"></t:dgCol>
            				<t:dgCol title="common.createtime" field="createDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
            				<t:dgCol title="common.updateby" field="updateBy" hidden="true"></t:dgCol>
            				<t:dgCol title="common.updatetime" field="updateDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
        				</t:datagrid>
				<div id="userListtb" style="padding: 3px; height: 25px">
					<div style="float: left;">
						用户名:
						<t:autocomplete minLength="1"
							dataSource="commonController.do?getAutoList&type=1" closefun="close"
							valueField="id" searchField="userName" labelField="userName"
							parse="parse" formatItem="formatItem" result="callBack2"
							name="userName" entityName="TSUser" datatype="*" maxRows="10"
							nullmsg="请输入关键字" errormsg="数据不存在,请重新输入"></t:autocomplete>
						真实姓名:
						<t:autocomplete minLength="1"
							dataSource="commonController.do?getAutoList&type=1" closefun="close"
							valueField="id" searchField="realName" labelField="realName"
							parse="parse" formatItem="formatItem2" result="callBack3"
							name="realName" entityName="TSUser" datatype="*" maxRows="10"
							nullmsg="请输入关键字" errormsg="数据不存在,请重新输入"></t:autocomplete>
						<a href="#" class="easyui-linkbutton" onclick="userListsearch();">查询</a>
					</div>
				</div>
			</div>
    			</div>
			</div>
        </div>
    </div>
    </body>
<script>
    $(function(){
    	$("#user-select>li:first").addClass("active");
    	$("#select").click(function(){
    		$(this).addClass("active").siblings().removeClass("active");
    		var queryParams = new Object();//$('#userList1').datagrid('options').queryParams;
    	    queryParams.currentuser = "test";
    	    $('#userList1').datagrid('options').queryParams=queryParams;
    	    $("#userList1").datagrid("reload"); 
    	    $('#departSelect').hide();
    	});
    	$("#autoselect").click(function(){
    		$(this).addClass("active").siblings().removeClass("active");
    		$('#departSelect').show();
    		var queryParams = new Object();//$('#userList1').datagrid('options').queryParams;
    	    queryParams.currentuser = "test";
    	    $('#userList1').datagrid('options').queryParams=queryParams;
    	    $("#userList1").datagrid("reload"); 
    	});
    	$("#subordinate").click(function(){
    		$(this).addClass("active").siblings().removeClass("active");
    		var queryParams = new Object();//$('#userList1').datagrid('options').queryParams;
    	    queryParams.check = "test";
    	    $('#userList1').datagrid('options').queryParams=queryParams;
    	    $("#userList1").datagrid("reload");
    	    $('#departSelect').hide();
    	});
    	});    	
</script>
<SCRIPT type="text/javascript">
        function parse(data){
            	var parsed = [];
		        	$.each(data.rows,function(index,row){
		        		parsed.push({data:row,result:row,value:row.id});
		        	});
        				return parsed;
        }       
        function callBack2(data) {
        	$("#userName").val(data.userName);
        }
        function callBack3(data) {
        	$("#realName").val(data.realName);
        } 
        function formatItem(data) {
        	return data.userName;
        }
        function formatItem2(data) {
        	return data.realName;
        }
function userListsearch(){
	var queryParams = new Object();
	var userName=$("#userName").val();
	var realName=$("#realName").val();
    queryParams.userName=userName;
    queryParams.realName=realName;
    $('#userList1').datagrid('options').queryParams=queryParams;
    $("#userList1").datagrid("reload");
}
//去掉滚动条。
function fitwindowIframe(){
	$("iframe[name^='JDG']",parent.document).css("height","99%");
}
$(document).ready(function(){
	setTimeout(fitwindowIframe,1000);
	});
  </SCRIPT>