<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${proName}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge;IE=11;IE=10;IE=9"/>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript"
	src="plug-in/jquery-plugs/form/jquery.form.js"></script>
<link rel="stylesheet"
	href="plug-in/Formdesign/js/ueditor/formdesign/bootstrap/css/bootstrap.css">

<!-- add start by jg_renjie at 20151025 for:增加支持Validform插件的相关文件 -->
<link rel="stylesheet" href="plug-in/Validform/css/style.css"
	type="text/css">
<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css"
	type="text/css">
<link rel="stylesheet" href="style/form-common.css" type="text/css">	
<script type="text/javascript"
	src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
<script type="text/javascript"
	src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
<script type="text/javascript"
	src="plug-in/Validform/js/datatype_zh-cn.js"></script>
<script type="text/javascript"
	src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></script>
<!-- add end by jg_renjie at 20151025 for:增加支持Validform插件的相关文件 -->

<script type="text/javascript"
	src="plug-in/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet"
	href="plug-in/My97DatePicker/skin/WdatePicker.css" type="text/css"></link>

<!--[if lte IE 6]>
	    <link rel="stylesheet" type="text/css" href="plug-in/Formdesign/js/ueditor/formdesign/bootstrap/css/bootstrap-ie6.css">
	    <![endif]-->
<!--[if lte IE 7]>
	    <link rel="stylesheet" type="text/css" href="plug-in/Formdesign/js/ueditor/formdesign/bootstrap/css/ie.css">
	    <![endif]-->
<link rel="stylesheet"
	href="plug-in/Formdesign/js/ueditor/formdesign/leipi.style.css">
<style>
html, body {
	height: 100%;
	width: 100%;
	padding: 0;
	margin: 0;
}
*{
	font-family:"微软雅黑","Tahoma","Verdana","新宋体", "Helvetica", "Arial", "sans-serif";
}
#preview {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
}

.temp {
	height: 30px;
	width: 190px;
	border: #ccc solid 1px;
	float: left;
	margin: 10px;
	text-align: center;
}

#preview * {
	font-family: "微软雅黑",sans-serif;
	font-size: 16px;
}
#preview table{
	width:98%!important;
}
#preview{
	margin:8px!important;
	width:960px!important;
	display:inline;
}
.handup-btn {
	background-color: rgb(0, 176, 240);
	padding: 2px;
	color: #fff;
	position: absolute;
	right: 190px;
	top: 0;
}
input,textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input{
	border:0!important;
	border-radius:0!important;
}
textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input{
	-webkit-box-shadow:0!important;
	box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0)!important;
}
input[disabled]{
 background:#ffffff;
}
.form-header h1{
	line-height:12px;
	margin-top:20px;
}
.container{
	width:980px!important;
	padding:60px 20px;
	position:relative;
}
.btn{
	background-color:#fff;
	background-image:none;
	color:rgb(0, 176, 240);
}
.btn:hover{
	background-color:#03a996;
	color:#fff;
	cursor:pointer;
}
.form-head{
	height:30px;
	padding-top:10px;
	padding-bottom:10px;
	position:absolute;
	right:20px;
	top:10px;
	z-index:2000;
}
#preview iframe{
 	border:1px solid rgb(0, 176, 240)!important;
}
#preview table td{
	border:1px solid #000000;
	padding:8px;
	overflow:auto;
}
.panel-body-noborder iframe{
	border:0;
	overflow:hidden;
}
.row>table{
	border:1px solid #00b0f0!important;
}
/*tab样式*/
.view{
height:98%;
}
.select-tabs{
position:absolute;
width:100%;
top:80px;
}
.tab-select-option,#table-select{
display:inline;
}
.rest-time{
width:100%;
}
.rest-time td,.rest-time .firstRow{
padding:20px!important;
text-align:center;
}
.select-tabs>.select-option{
display:none
}
#table-select{
margin-top:20px;
margin-bottom:20px;
display:inline-block;
}
.tab-select-option{
float:left
}
#user-select{
 position:absolute;
 top:45px;
 left:55px;
}
#user-select li,#table-select li{
	float:left;
	padding:5px 15px;
	cursor:pointer;
	color:#adadad;
	border-right:1px solid #adadad
}
#user-select li:last-child,#table-select li:last-child{
	border-right:0;
}
#user-select li:hover,#table-select li:hover{
	color:rgb(0, 176, 240);
}
.active{
	color:rgb(0, 176, 240)!important;
}
.progress-content{
	margin-left:20px;
	margin-top:20px;
}
.progress-content h2{
	font-size:18px;
}
.form_table_examine{
	width:100%;
	margin-top:20px;
}
/*tab样式*/
.rest-time{
display:none;
}
.tabs-container>.tabs-panels>.panel:nth-child(2)>.panel-body{
height:500px!important;
}
.tabs-container>.tabs-panels>.panel>.panel-body iframe.tabs-container>.tabs-panels>.panel-body{
height:497px!important;
}
.tabs-panels>.panel:nth-child(1){
padding:5px!important;
}
input[disabled], select[disabled], textarea[disabled], input[readonly], select[readonly], textarea[readonly]{
background:#ffffff;
}
</style>

</head>
<body class="view">

	<form id="formSubmit" action="workFlowAutoFormController.do?updateForm"
		method="post">
		<input type="hidden" id="btn_sub"> <input type="hidden"
			name="formName" value="${formName}">
		<c:forEach items="${param}" var="item">
			<input type="hidden" name="param.${item.key}" value="${item.value}">
		</c:forEach>
		<div class="container">

			<%-- <div class="page-header">
		  <h1>预览表单 <small>如无问题请保存你的设计</small></h1>
		</div> --%>
		 <p id="p2" style="display: none;">${param.id }</p>
	     <p id="p3" style="display: none;">${param. procInstId}</p>
               
        
         <div class="form-head">
				<div class="btn-group pull-right" role="group" id="ishide">
					<a href="#" class="btn" id="getback" >取回</a>				
					<a  class="btn"   id="printpage" href="workFlowAutoFormController.do?viewContent=&op=print&formName=${formName}&id=${param.id}" target="_blank" >打印</a>
					<a class="btn"  id="rewo" style="display: none" onclick="del()">撤销</a>
				</div>
		</div>
			<div class="easyui-tabs" id="tt">
			
				<div id="preview" style="margin: 8px; margin-bottom: 0;"
					title="审批记录">
					${formContent}
					<iframe
						src="workFlowAutoFormController.do?showApproveLog&businessId=${param.id}"
						width="100%" style="margin-top:10px;"> </iframe>
				</div>
				<div title="流程图">				
					<iframe src="workFlowAutoFormController.do?getflowsheet&procInstId=${param.procInstId }&processDefId=${param.procDefId}" width="100%" height="99%"></iframe>	
				</div>
			</div>
		</div>

	</form>
	<script type="text/javascript">
	var sys_curUserID="${sys_curUserID}";//当前用户ID
	var sys_curUserName="${sys_curUserName}";//当前用户登录名、账号
	var sys_curRealName="${sys_curRealName}";//当前用户姓名
	//附件服务器的地址，如：http://192.168.82.202
	var sys_fileServer="${sys_fileServer}";
	//当前节点名；
	var flow_NodeName="${flow_NodeName}";
	//当前节点ID；
	var flow_NodeId="${flow_NodeId}";
	
		var form;
		$(function() {
			form = $("#formSubmit").Validform({
				tiptype : 4,
				btnSubmit : "#btn_sub",
				btnReset : "#btn_reset",
				ajaxPost : true,
				callback : function(data) {
					if (data.success) {
						alert(data.msg);
						var id = data.obj;
						$("#param_op").val("update");
						$("#param_id").val(id);
						$("#reloadViewForm").submit();
						window.close();//关闭窗口
					} else {
						alert(data.msg);
					}
				}
			});
			form.tipmsg.s = " ";
			form.tipmsg.r = " ";
		});
		function formSubmit() {
			$("#btn_sub").click();
		};
		
	</script>
 <script type="text/javascript">
        $(function(){
        	$(":input").attr("disabled","true");
        	var businessid=$("#p2").text();
        	var procInstId=$("#p3").text();     
        	$.ajax({
		   		url:"workFlowAutoFormController.do?showApproveLogajax",
		   		data:{"businessid":businessid},
		   		dataType:'json',
		   		type:'post', 
				success:function(msg) {				
					if(msg.success){						
						$("#rewo").css("display","inline-block");
					}
							
				}				
			});
        	$("#getback").click(function(){         	            	          
            	$.ajax({
    		   		url:"workFlowAutoFormController.do?getbackforinstance",
    		   		data:{"businessid":businessid,"procInstId":procInstId},
    		   		dataType:'json',
    		   		type:'post', 
    				success:function(msg) {							
    					alert(msg.msg);
    					window.opener.location.reload();
    					window.close();
    				}				
    			});
        		
        	});
        });
        $(document).ready
		   (function()
		      {
			   var ishide="${param.hide}";
			   if(ishide!=null&&ishide=='true'){
				   $("#ishide").hide();
			   }
	             var isAllowGetBack="${isAllowGetBack}";	
	             var isAllowPrint="${isAllowPrint}";
	            if(isAllowGetBack!=null&&isAllowGetBack=='false')
         	       {	            	 
         	          $("#getback").hide();
         	       }
	            if(isAllowPrint!=null&&isAllowPrint=='false')
      	       {	            	 
      	          $("#print").hide();
      	       }
	          }
		   );
        function del()
        {
        	var url2="workFlowAutoFormController.do?revoke&procInstId=${param.procInstId }&businessId=${param.id}";
        	$.ajax({
    			url : url2,
    			dataType : 'json',
    			success : function(msg) {
    				alert(msg.msg);
    				window.opener.location.reload();
    				window.close();
    			}
    		});
        }
        </script>
</body>
</html>