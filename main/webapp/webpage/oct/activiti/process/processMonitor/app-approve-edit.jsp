<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
	font-family:Microsoft YaHei!important;
	background:#fff;
}
input,textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input{
	border:0!important;
	border-radius:0!important;
}
textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input{
	-webkit-box-shadow:0!important;
	box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0)!important;
}
table tbody tr td span{
	color:rgb(0, 176, 240);
}
input[type="text"]{
	border-bottom:1px #999999 solid!important;
}
textarea{
	border:1px #999999 solid!important;
}
.row{
	margin-left:0;
}
.form-header h1{
	line-height:12px;
	margin-top:20px;
}
.form-header p{
	font-family:"Microsoft YaHei";
	font-size:9pt;
	color:#666666;
}
.form-head{
	height:30px;
	padding-top:10px;
	padding-bottom:10px;
	position:absolute;
	right:20px;
	top:40px;
	z-index:2000;
}
.form-title{
	position:absolute;
	left:20px;
	top:0px;
	z-index:2000;
}
.form-title-img{
	width:60px;
	height:60px;
	background-color:rgb(0, 176, 240);
	position:absolute;
	top:20px;
}
.container{
	margin-top:20px;
}
.form-title h2{
	font-size:18px;
	padding-left:70px;
}
.view{
	position:relative;
}
#preview {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
	padding-bottom:500px;
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
	font-size: 12px;
}

.handup-btn {
	background-color: rgb(0, 176, 240);
	padding: 2px;
	color: #fff;
	position: absolute;
	right: 190px;
	top: 0;
}
#preview iframe{
	border:0;
}
.tabs{
	padding-left:20px;
}
.tabs-header{
	border-width:0;
}
input[typr="radio"]{
	margin-left:10px;
	margin-right:5px;
}
.form-bottom{
	width:100%;
	position:fixed;
	bottom:0px;
}
#comment{
	width:99%;
	height:20px;
	margin-bottom:0px;
	border:1px solid #ddd!important;
	padding:0.2% 0.5%;
}

.bottom-ad{
	padding:20px;
	border-top:1px solid #ddd;
	border-bottom:1px solid #ddd;
	background-color:#efefef;
}
.tabs-panels{
	border-bottom:0;
}
#formSubmit .easyui-tabs .tabs-wrap .tabs{
	padding-left:80px;
}
.form-bottom .easyui-tabs .tabs-wrap .tabs{
	padding-left:20px!important;
}
.btn{
	background-color:#fff;
	background-image:none;
	color:rgb(0, 176, 240);
}
.btn:first-child{
	background-color:rgb(0, 176, 240);
	background-image:none;
	color:#fff;
}
.btn:hover{
	background-color:#03a996;
	color:#fff;
}
.datagrid-btable .datagrid-body td, .datagrid-footer td{
	border-width:0!important;
}
.datagrid-body td,
.datagrid-footer td {
	border-width: 0;
    border-style: solid
}
.u-comment-wrap{
	position:relative;
	display:none
}
.u-comment-btn span{
	margin-bottom:4px;
}
.u-comment-container{
	position:absolute;
	width:140px;
	left:0px;
	top:18px;
	border:1px solid #666666;
	background-color:#fff;
	display:none;
	overflow:hidden;
}
.u-comment-container ul{
	width:100%;
	height:50px;
	margin:0;
	text-align: center;
	overflow: auto;
	padding:10px 0px 10px 20px;
	border-bottom:1px solid #dddddd;
	
}
.u-comment-container ul li{
	width:80px;
	padding:4px 20px;
	margin-left:-10px;
	overflow: hidden; 
	text-overflow:ellipsis;
	white-space: nowrap;
	position:relative;
}
.u-comment-wrap a{
	cursor:pointer;
}
.u-comment-container ul li a{
	width:100px;
	text-overflow:ellipsis;
	white-space: nowrap;
	position:relative;
}
.u-comment-container>a{
	line-height:30px;
	text-align:center;
	display:block;
}
.u-comment-btn{
	color:#005580
}
.add-conmment-wrap input{
	width:65px;
	margin-left:6px;
	margin-right:4px;
	border:1px solid #dddddd!important;
}
.add-conmment-wrap a{
	margin-top:-8px;
	padding:4px;
	border:1px solid #dddddd;
}
.remove-btn{
	width:13px;
	height:13px;
	background:url("././././webpage/images/remove.png");
	position:absolute;
	top:9px;
	right:0px;
	cursor:pointer;
}
</style>
<script type="text/javascript">  
		function change() {
			   $.dialog.setting.zIndex = 8999; 
			   $.dialog({content: 'url:workFlowAutoFormController.do?changApproverselect', zIndex: 2100, title: '', lock: true, width: '600px', height: '350px', opacity: 0.4, button: [
			      {name: '<t:mutiLang langKey="common.confirm"/>', callback:callback, focus: true},
			      {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
			   ]}).zindex();
			}
		function callback(){
			var iframe = this.iframe.contentWindow;		
			var user = iframe.$('#username').val();	
			var comment=iframe.$('#comment').val();							
			var taskid=$("#p1").text();
			var businessid=$("#p2").text();
			$.ajax({
		   		url:"processMonitorController.do?changApprover",
		   		data:{"userId":user,"taskId":taskid,"comment":comment,"businessid":businessid},
		   		dataType:'json',
		   		type:'post', 
				success:function(msg) {
					alert(msg.msg);
					 window.opener.location.reload();
					window.close();
				}				
			});
		}
		
		
		
		//协办,弹出协办框
		function assit() {
			   $.dialog.setting.zIndex = 8999; 
			   $.dialog({title:'协办',content: 'url:workFlowAutoFormController.do?assitAdd', zIndex: 2100, title: '', lock: true, width: '600px', height: '350px', opacity: 0.4, button: [
			      {name: '<t:mutiLang langKey="common.confirm"/>', callback:savAssit, focus: true},
			      {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
			   ]}).zindex();
			}
		//协办,保存协办信息
		function savAssit(){
			var iframe = this.iframe.contentWindow;		
			var user = iframe.$('#username').val();	
			var userName = iframe.$('#usernameView').val();	
			var comment=iframe.$('#comment').val();							
			var taskid=$("#p1").text();
			var businessid=$("#p2").text();
			$.ajax({
		   		url:"workFlowAutoFormController.do?assitSave",
		   		data:{"userId":user,"userNames":userName,"taskId":taskid,"businessid":businessid,"comment":comment},
		   		dataType:'json',
		   		type:'post', 
				success:function(msg) {
					alert(msg.msg);
				    window.opener.location.reload();
					window.close();
				}				
			});
		}
		
		//退回,弹出退回框
		function jumpNode() {
			   var procInstId=$("#p3").text();
			   var taskid=$("#p1").text();
			   $.dialog.setting.zIndex = 8999; 
			   $.dialog({title:'退回',content: 'url:workFlowAutoFormController.do?jumpNodeAdd&procInstId='+procInstId+"&taskid="+taskid, zIndex: 2100, title: '', lock: true, width: '600px', height: '350px', opacity: 0.4, button: [
			      {name: '<t:mutiLang langKey="common.confirm"/>', callback:jumpNodeSave, focus: true},
			      {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
			   ]}).zindex();
			}
		//,保存退回信息
		function jumpNodeSave(){
			var iframe = this.iframe.contentWindow;		
			var activityId = iframe.$('#activityId').val();	
			var comment=iframe.$('#comment').val();							
			var taskid=$("#p1").text();	
			var businessId=$("#p2").text();	
			$.ajax({
		   		url:"workFlowAutoFormController.do?backProcess",
 		   		data:{"taskId":taskid,"activityId":activityId,"comment":comment,"businessId":businessId},
		   		dataType:'json',
		   		type:'post', 
				success:function(msg) {
					window.opener.location.reload();
					window.close();
				}				
			});
		}
	
		function callbackOpinionSelect() {
			var iframe = this.iframe.contentWindow;		
			var rowsData = iframe.$('#commonopinion').datagrid('getSelections');
			var content=rowsData[0].content;			
			$('#comment').val(content);
			}
		</script>
</head>
<body class="view">
		<div class="form-title">
				<div class="form-title-img"></div>
				<h2>流程:处理 - 入职申请表 -</h2>
		</div>
		<div class="form-head">
				<div class="btn-group pull-right" role="group">
					<!--  <button type="button" class="btn" onclick="formSubmit();" >提交</button>	-->			
					<button type="button" class="btn" onclick="change()" id="change">修改审批人</button>
				<!--  	<button type="button" class="btn" onclick="assit()" id="assit">协办</button>
					<button type="button" class="btn" onclick="jumpNode()" id="jumpNode">退回</button>
					<a  class="btn"   id="printpage" href="workFlowAutoFormController.do?viewContent=&op=print&formName=${formName}&id=${param.id}" target="_blank" >打印</a>
				--></div>
		</div>

	<form id="formSubmit" action="workFlowAutoFormController.do?updateForm"
		method="post" style="padding-top: 60px;">
		<input type="hidden" id="btn_sub"> <input type="hidden"
			name="formName" value="${formName}">
		<c:forEach items="${param}" var="item">
			<input type="hidden" name="param.${item.key}" value="${item.value}">
		</c:forEach>
        <p id="p1" style="display: none;">${param.taskId }</p>
         <p id="p2" style="display: none;">${param.id }</p>
		  <p id="p3" style="display: none;">${param.procInstId }</p>

			<%-- <div class="page-header">
		  <h1>预览表单 <small>如无问题请保存你的设计</small></h1>
		</div> --%>
			
			<div class="easyui-tabs" id="tt">
				<div id="preview" style=" margin-bottom: 0; padding-bottom:400px;"title="流程审批">
				<div class="container">
					<div class="row">		
						${formContent}
						
					</div>
				</div>
					
				</div>
				<div title="流程图">
					<iframe src="workFlowAutoFormController.do?getflowsheet&procInstId=${param.procInstId }" width="100%" height="100%"></iframe>
				</div>
			</div>
			<div class="form-bottom">
				<div title="意见" class=" bottom-ad">	
						  
						<div class="u-comment-wrap" id="u-comment-wrap">
							<a herf="#" id="u-comment-btn"><span>常用批示语</span></a>
							<div class="u-comment-container" id="u-comment-container">
								<ul>
									<li><a herf="#">同意</a></li>
									<li><a herf="#">不同意</a></li>
									
								</ul>
								<a id="dlg" herf="#">增加批示语</a>
								<div class="add-conmment-wrap" id="commentInput" style="display:none"><input type="text" id="addContent"/><a id="addConmmentBtn">提交</a></div>
							</div>
						</div>
						<input type="text" id="comment" name="comment" placeholder="意见"></input>
				</div>
				
				<div class="easyui-tabs" id="tt">
					
					<div title="流程意见">
						<iframe src="workFlowAutoFormController.do?showApproveLog&businessId=${param.id}" width="100%" name="iframe1" style="height:210px;"> </iframe>
					</div>										
				</div>
				
						
					
			</div>

	</form>
	<script type="text/javascript">

	
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
						 window.opener.location.reload();
						window.close(); 
					} else {
						alert(data.msg);
					}
				}
				
			});
			form.tipmsg.s = " ";
			form.tipmsg.r = " ";
			
			
			//初始化时将批示语导入
	    	$.ajax({
	    		type:"POST",
	    		url:"workFlowAutoFormController.do?commonopiniongrid",
	    		success:function(data){
	    			for (i in data.attributes){
	    				var msg='<li><a herf="#">'+data.attributes[i]+'</a><i class="remove-btn" id='+i+'></i></li>';
	    				$('#u-comment-container').find('ul').append(msg);
	    				$('.remove-btn').click(function(){//移除标示语
	    					var msgId=$(this).context.id;
	    					$(this).parent().remove();
	    					$.ajax({
	    		    			type:"post",
	    		    			data:{"id":msgId},
	    		    			datatype:"json",
	    		    			url:"workFlowAutoFormController.do?delOpinion",
	    		    			success:function(data){	    		    				
	    		    			}
	    		    			
	    		    		});
	    				})
	    			}
	    		},
	    		dataType:"json"
	    	});
	    	//初始化时将批示语导入
	    	$("#dlg").click(function(){
	    		$('#commentInput').show();	 
	    	})
	    	//增加标示语
	    	$("#dlg").click(function(){
	    		$("#commentInput").slideOut();
	    	})
	    	$("#addConmmentBtn").click(function(){
	    		var tex=$("#addContent").val();
	    		var msg='<li><a herf="#">'+tex+'</a><i class="remove-btn"></i></li>';
				$('#u-comment-container').find('ul').append(msg);
	    		$.ajax({
	    			type:"post",
	    			data:{"comment":tex},
	    			datatype:"json",
	    			url:"workFlowAutoFormController.do?saveOpinion",
	    			success:function(data){
	    				alert("添加成功！");
	    				$("#addContent").val("");
	    				$('#commentInput').hide();
	    			}
	    			
	    		});
	    	});
	    	//增加标示语
	    	
	    	//意见框焦点事件及标示语点击事件
			$("#comment").focus(function(){
				$(this).css('height','140px');
				$("#u-comment-wrap").show();
				$("#u-comment-btn").click(function(){
					$("#u-comment-container").show();
					for(var i=0;i<$("#u-comment-container ul li").length;i++){
						$("#u-comment-container ul li a")[i].onclick=function(){
							var addTag=$(this).text();
							var result=$('#comment').val()+addTag;
							$("#comment").val(result);
							$("#u-comment-container").hide();
						};
					}
					
				});
			});
			//意见框焦点事件及标示语点击事件		
			//意见框失去焦点事件
			$(".form-title,.form-head,.easyui-tabs").click(function(){
				$("#u-comment-wrap").hide();
				$("#u-comment-container").hide();
				$("#comment").css('height','20px');
			});
			$("#tt",document.frames('iframe1').document).click(function(){
				$("#u-comment-wrap").hide();
				$("#u-comment-container").hide();
				$("#comment").css('height','20px');
			});
			//意见框失去焦点事件
			
		});
		function formSubmit() {
			$("#btn_sub").click();
		};
		  
	</script>
</body>
</html>