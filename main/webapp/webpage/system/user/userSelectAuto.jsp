<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.js" type="text/javascript"></script>
<link href="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="plug-in/ztree/css/zTreeStyle.css">
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<%--update-start--Author:wangkun  Date:20160327 TASK #956 【UI标签】封装选择用户标签--%>
<style>
/*选项卡样式*/
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
/*选项卡样式*/
/*选项卡样式*/
.panel-body[data-options="region:'west',split:true"]{
height:488px!important;
}
.datagrid-view1 .datagrid-body,.datagrid-view2 .datagrid-body{
height:344!important;
}
.panel-noscroll{
height:520px!important;
}
.panel-noscroll .datagrid-wrap{
height:488px!important;
}
/*选项卡样式*/      
</style>
<script type="text/javascript">
    var setting = {
        check: {
            enable: false,
            chkboxType: { "Y": "", "N": "" }
        },
        data: {
            simpleData: {
                enable: false
            }
        },callback: {
            onExpand: zTreeOnExpand,
            onClick:onClick
        }
    };
    function onClick(event, treeId, treeNode){
        var queryParams = $('#userList1').datagrid('options').queryParams;
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
<body style="overflow-y: hidden" scroll="no">
<div class="selectuser-header">
		<div class="select-img"></div>
		<h1>人员选择</h1>
 		<ul class="select-list" id="user-select">
        	<li id="autoselect"><a href="#">按组织结构</a></li>
        	<li id="select"><a href="#">同部门</a></li>
        	<li id="subordinate"><a href="#" >我的下属</a></li>
    	</ul>
</div>
<div class="select-content" id="user-select-content">
        <div class="select-pane">
        	<div class="easyui-layout" style="width:800px;height:522px;">
    			<div data-options="region:'west',split:true" title="<t:mutiLang langKey='common.department'/>" style="width:200px;">
        			<ul id="departSelect" class="ztree" style="margin-top: 30px;"></ul>
   				</div>
    			<div data-options="region:'center'">
        			<t:datagrid name="userList1" title="common.user.select" actionUrl="userController.do?selectusers&ids=${id }"
                    fit="true" fitColumns="true" idField="id" queryMode="group" sortName="createDate" sortOrder="desc">
            			<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
            			<t:dgCol title="common.real.name" field="realName" query="false"></t:dgCol> 
            			<t:dgCol title="common.username" sortable="false" field="userName" query="false" autocomplete="true"></t:dgCol>
            			<t:dgCol title="common.department" sortable="false" field="userOrgList.tsDepart.departname" query="false"></t:dgCol>   
            
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
        <div class="select-pane">
        </div>
        </body>
<script type="text/javascript">
//tab选项卡
/*$(function(){
    $("#user-select-content .select-pane").hide();
    $("#user-select>li:first").addClass("active");
    $("#user-select-content .select-pane:first").show();
    $("#user-select>li").bind('click',function(){
        $(this).addClass("active").siblings().removeClass("active");
        var i=$(this).index("li");
        $("#user-select-content .select-pane:eq("+i+")").show().siblings().hide();
    });
});*/
//tab选项卡

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
    		 $(this).addClass("active").siblings('li').removeClass("active");
    		var queryParams = new Object();//$('#userList1').datagrid('options').queryParams;
    	    queryParams.check = "test";
    	    $('#userList1').datagrid('options').queryParams=queryParams;
    	    $("#userList1").datagrid("reload");
    	    $('#departSelect').hide();
    	});
    	});    	

</script>
<script>
$('.datagrid-btable>tbody>td').click(function(){
});
  $("#userList1").datagrid({
	  onDblClickRow:function(rowIndex, rowData){	
		  var api = frameElement.api;
		  	W = api.opener;
		api.iframe.parentNode.parentNode.parentNode.parentNode.lastChild.firstChild.firstChild.firstChild.click();
	  }
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
  </SCRIPT>