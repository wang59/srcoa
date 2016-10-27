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
.formSubmit{
padding-bottom:300px;
position:relative;
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
	background:url(../webpage/images/main/icon-lc.png) no-repeat;
	position:absolute;
	top:20px;
	margin-left:15px;
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
	padding-bottom:50px;
}

.temp {
	height: 30px;
	width: 190px;
	border: #ccc solid 1px;
	float: left;
	margin: 10px;
	text-align: center;
}
.select-option>iframe{
	margin-bottom:40px;
}
#preview * {
	font-size: 14px;
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
	bottom:0px;margin-top:60px;
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
iframe{
	border:0;
	height:300px;
}
.row>table tr{
	border-left:1px solid #00b0f0!important;
	border-right:1px solid #00b0f0!important;
	padding:10px;
}
.row>table tr:first-child,.row>table tr:nth-child(1){
	border:0!important;
}
.row>table tr:nth-child(2){
	border-top:1px solid #00b0f0!important;
}
.row>table tr:last-child{
	border-bottom:1px solid #00b0f0!important;
}
.row>table tr td{
	padding:10px;
}

/*tab样式*/
.view{
height:98%;
}
.select-tabs{
position:relative;
width:100%;
top:68px;
display:inline-block;
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
::-ms-clear, ::-ms-reveal{
display: none;
}
#table-wrap>h1{
	line-height:4;
	color:#538de3!important;
}
.form-bottom .tabs-header>.tabs-wrap>.tabs{
	display:none;
}
</style>
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

$(document).ready(function(){
	//自定义表单的域初始状态设置为distabled;
	intputSetDisabled();
});

function reject(){
       var comment=$("#comment").val();
       if(comment==null||comment==""){
			alert("意见不能为空");
			return false;
		}
		$.messager.confirm('确认框', '确认不同意吗?', function(r){
			if (r){
				$.ajax({
					url : "workFlowAutoFormController.do?reject",
					data : {
						"taskid" : "${param.taskId}",
						"comment":comment,
						"businessid":"${param.id}"
					},
					dataType : 'json',
					type : 'post',
					success : function(msg) {
						changeSelectIframe();
						top.window.opener.location.reload();
						closeWin();
						
					}
					
			});
			}
		});     
       
	}
	function change() {
		$.dialog.setting.zIndex = 8999;
		$.dialog({
			content : 'url:workFlowAutoFormController.do?changApproverselect',
			zIndex : 2100,
			title : '',
			lock : true,
			width : '600px',
			height : '350px',
			opacity : 0.4,
			button : [ {
				name : '<t:mutiLang langKey="common.confirm"/>',
				callback : callback,
				focus : true
			}, {
				name : '<t:mutiLang langKey="common.cancel"/>',
				callback : function() {
				}
			} ]
		}).zindex();
	}
	function callback() {
		var iframe = this.iframe.contentWindow;
		var user = iframe.$('#username').val();
		var userName="${currentUser}";
		var comment = iframe.$('#comment').val();

		if(user==null||user==""){
			alert("转办人不能为空");
			return false;
		}
		if(user==userName){
			alert("转办人不能为自身");
			return false;
		}
		if(comment==null||comment==""){
			alert("转办原因不能为空");
			return false;
		}
		var taskid = $("#p1").text();
		var businessid = $("#p2").text();
		$.ajax({
			url : "workFlowAutoFormController.do?changApprover",
			data : {
				"userId" : user,
				"taskId" : taskid,
				"comment" : comment,
				"businessid" : businessid
			},
			dataType : 'json',
			type : 'post',
			success : function(msg) {
				alert(msg.msg);
				changeSelectIframe();
				top.window.opener.location.reload();
				closeWin();
			}
		});
	}

	//协办,弹出协办框
	function assit() {
		$.dialog.setting.zIndex = 8999;
		$.dialog({
			title : '协办',
			content : 'url:workFlowAutoFormController.do?assitAdd',
			zIndex : 2100,
			title : '',
			lock : true,
			width : '600px',
			height : '350px',
			opacity : 0.4,
			button : [ {
				name : '<t:mutiLang langKey="common.confirm"/>',
				callback : savAssit,
				focus : true
			}, {
				name : '<t:mutiLang langKey="common.cancel"/>',
				callback : function() {
				}
			} ]
		}).zindex();
	}
	//协办,保存协办信息
	function savAssit() {
		var iframe = this.iframe.contentWindow;
		var user = iframe.$('#username').val();
		var userName = iframe.$('#realname').val();
		var comment = iframe.$('#comment').val();		
		var name="${currentUser}";
		
		if(user==null||user==""){
			alert("协办人不能空");
			return false;
		}
		if(user==name){
			alert("协办人不能为自身");
			return false;
		}
		
		if(comment==null||comment==""){
			alert("协办原因不能为空");
			return false;
		}
		var taskid = $("#p1").text();
		var businessid = $("#p2").text();
		$.ajax({
			url : "workFlowAutoFormController.do?assitSave",
			data : {
				"userId" : user,
				"userNames" : userName,
				"taskId" : taskid,
				"businessid" : businessid,
				"comment" : comment
			},
			dataType : 'json',
			type : 'post',
			success : function(msg) {
				alert(msg.msg);
				changeSelectIframe();
				top.window.opener.location.reload();
				closeWin();
			}
		});
	}

	//退回,弹出退回框
	function jumpNode() {
		var procInstId = $("#p3").text();
		var taskid = $("#p1").text();
		$.dialog.setting.zIndex = 8999;
		$
				.dialog(
						{
							title : '退回',
							content : 'url:workFlowAutoFormController.do?jumpNodeAdd&procInstId='
									+ procInstId + "&taskid=" + taskid,
							zIndex : 2100,
							title : '',
							lock : true,
							width : '600px',
							height : '350px',
							opacity : 0.4,
							button : [
									{
										name : '<t:mutiLang langKey="common.confirm"/>',
										callback : jumpNodeSave,
										focus : true
									},
									{
										name : '<t:mutiLang langKey="common.cancel"/>',
										callback : function() {
										}
									} ]
						}).zindex();
	}
	//,保存退回信息
	function jumpNodeSave() {
		var iframe = this.iframe.contentWindow;
		var activityId = iframe.$('#activityId').val();
		var comment = iframe.$('#comment').val();
		//var comment = iframe.$('#comment').val();
			
		if(activityId!=""&&typeof(activityId)!="undefined"&&comment!=""){
			var taskid = $("#p1").text();
			var businessId = $("#p2").text();
			$.ajax({
				url : "workFlowAutoFormController.do?backProcess",
				data : {
					"taskId" : taskid,
					"activityId" : activityId,
					"comment" : comment,
					"businessId" : businessId
				},
				dataType : 'json',
				type : 'post',
				success : function(msg) {
					changeSelectIframe();
					top.window.opener.location.reload();
					closeWin();
				}
			});			
		}else if(typeof(activityId)=="undefined"){
			closeWin();	
		}else if(typeof(activityId)!="undefined"&&(comment==null||comment=="")){
			alert("退回原因不能为空");
			return false;
		}		
	}

	function callbackOpinionSelect() {
		var iframe = this.iframe.contentWindow;
		var rowsData = iframe.$('#commonopinion').datagrid('getSelections');
		var content = rowsData[0].content;
		$('#comment').val(content);
	}
</script>
</head>
<body class="view">
		<div class="form-title">
				<div class="form-title-img"></div>
				<h2>流程:处理 - ${proName}</h2>
		</div>
		<div class="form-head">
				<div class="btn-group pull-right" role="group">
					<button type="button" class="btn" onclick="formSubmit();" id="agree">同意</button>	
					<button type="button" class="btn" onclick="reject();" id="reject">不同意</button>				
					<button type="button" class="btn" onclick="change()" id="change">转办</button>
					<button type="button" class="btn" onclick="assit()" id="assit">协办</button>
					<button type="button" class="btn" onclick="jumpNode()" id="jumpNode">退回</button>
					<a  class="btn"   id="printpage" href="workFlowAutoFormController.do?viewContent=&op=print&formName=${formName}&id=${param.id}" target="_blank" >打印</a>
				</div>
		</div>

	<form id="formSubmit" action="workFlowAutoFormController.do?updateForm"
		method="post" style="padding-top: 10px;">
		<input type="hidden" id="btn_sub"> <input type="hidden"
			name="formName" value="${formName}">
			      <!-- 流程定义/绑定流程记录的ID -->
      <input type="hidden" name="proid" value="${proid}">
       <!-- 插入或者更新数据时用于生成SQL语句 -->
       <input type="hidden" name="tableName" value="${tableName}">
		<c:forEach items="${param}" var="item">
			<input type="hidden" name="param.${item.key}" value="${item.value}">
		</c:forEach>
        <p id="p1" style="display: none;">${param.taskId }</p>
         <p id="p2" style="display: none;">${param.id }</p>
		  <p id="p3" style="display: none;">${param.procInstId }</p>

			<%-- <div class="page-header">
		  <h1>预览表单 <small>如无问题请保存你的设计</small></h1>
		</div> --%>
			<ul id="user-select">
			<li>流程审批</li>
			<li>流程图</li>
		</ul>
		<div class="select-tabs" id="tt">
				<div id="preview" style=" margin-bottom: 0;" class="select-option">			
				<div class="container">
					<div class="row" id="table-wrap">		
						<c:if test="${isJSP!='Y'}">
							   <!-- 显示自动以表单内容  开始 -->
	                           ${formContent}
	                           <!-- 显示自动以表单内容 结束 -->
                            </c:if>
							<c:if test="${isJSP=='Y'}">
							  <!-- 插入JSP页面  开始-->
	                          <jsp:include page="${formName}"></jsp:include>
	                           <!-- 插入JSP页面  结束-->
                            </c:if>				
					</div>
				</div>
					
				</div>
				<div title="流程图"class="select-option">
					<iframe src="workFlowAutoFormController.do?getflowsheet&procInstId=${param.procInstId }" width="100%" ></iframe>
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
						<input type="text" id="comment" name="comment" placeholder="填写意见"></input>
				</div>
				
				<div class="easyui-tabs" id="tt">
					
					<div>
						<iframe src="workFlowAutoFormController.do?showApproveLog&businessId=${param.id}" width="100%" name="iframe1" style="height:172px;" id="comment-iframe" > </iframe>
					</div>										
				</div>
				
						
					
			</div>

	</form>
	<script type="text/javascript">

	
	//printArea插件
	 
	(function($) {  
		var printAreaCount = 0; 
		$.fn.printArea = function() {  
			var ele = $(this);  
			var idPrefix = "printArea_";  
			removePrintArea( idPrefix + printAreaCount ); 
			printAreaCount++;  
			var iframeId = idPrefix + printAreaCount; 
			var iframeStyle = 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;'; 
			iframe = document.createElement('IFRAME'); 
			$(iframe).attr({ style : iframeStyle, id    : iframeId });
			document.body.appendChild(iframe); 
			var doc = iframe.contentWindow.document;
			$(document).find("link").filter(function(){  
				return $(this).attr("rel").toLowerCase() == "stylesheet"; 
			}).each(function(){
				doc.write('<link type="text/css" rel="stylesheet" href="' +$(this).attr("href") + '" >'); 
			});  
			doc.write('<div class="' + $(ele).attr("class") + '">' + $(ele).html() + '</div>'); 
			doc.close();  
			var frameWindow = iframe.contentWindow; 
			framecloseWin(); 
			frameWindow.focus(); 
			frameWindow.print(); 
			};  
			var removePrintArea = function(id){  
				$( "iframe#" + id ).remove(); 
			};  
		})(jQuery);
		
		
	//printArea插件
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
						changeSelectIframe();
						var id = data.obj;
						$("#param_op").val("update");
						$("#param_id").val(id);
						$("#reloadViewForm").submit();
						 top.window.opener.location.reload();
						closeWin(); 
					} else {
						alert(data.msg);
					}
				}
				
			});
			form.tipmsg.s = " ";
			form.tipmsg.r = " ";
			
			$("#comment-iframe")
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
					$("#u-comment-container").mouseleave(function(){
						$("#u-comment-container").hide();
					});
					for(var i=0;i<$("#u-comment-container ul li").length;i++){
						$("#u-comment-container ul li a")[i].onclick=function(){
							if($("#comment").val() == $("#comment").attr("placeholder")){
								$("#comment").val( "")
							}
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
			$(".form-title,.form-head,.easyui-tabs,.select-tabs").click(function(){
				$("#u-comment-wrap").hide();
				$("#u-comment-container").hide();
				$("#comment").css('height','20px');
			});
			//意见框失去焦点事件
			
		});
		 var beforeSubmit;//防止判断beforeSubmit是否为函数的时候出错。
		function formSubmit() {
			 var content=$("#comment").val();
			 if(content==null||content==''||content=='填写意见'){
				// alert("意见不能为空");     
				// $("#comment").attr("value","同意");
				 $("#comment").val("同意");
				 //点击同意按钮时不需要填写意见校验，如果没有填写意见则设置为同意
				 //return false;				 
			 }
			//判断提交前是否需要执行js函数；
			 var hasBeforeSubmit=jQuery.isFunction(beforeSubmit);
			 if(hasBeforeSubmit){
				 //提交前执行js函数
				 var rtnbeforeSubmit=beforeSubmit();
				 //返回值是true,才正式提交。
				 if(rtnbeforeSubmit == true){
						$("#btn_sub").click();
						
				 }
			 }else{
					$("#btn_sub").click();
					
			 }

		};
		
		//流程结束后显示其他的审批内容
		function changeSelectIframe(){
			var src="toDoController.do?editapp&taskId="+$(window.parent.document).find('.list-active').next().attr('myid'),
					index=$(window.parent.document).find('.list-active');
			if($(window.parent.document).find('.list-active').next().length){
				$(window.parent.document).find('.list-active').next().addClass('list-active').siblings().removeClass('list-active');
			}else{
				$("#approval-list li:first",parent.document).addClass('list-active').siblings().removeClass('list-active');
				src="toDoController.do?editapp&taskId="+$("#approval-list li:first",parent.document).attr('myid')
			}
			index.remove();
			if($('#approval-list', parent.document).html()!=""){
				$(window.parent.document).find('#approval-content').attr('src',src);
			}else{
				top.window.opener.location.reload();
				top.window.opener= null;
				top.window.open("","_self"); 
				top.window.close();
			}	
		}
		//流程结束后显示其他的审批内容
		
		  $(function()
		      {				 
	             var isAllowAssit="${isAllowAssit}";	
	             var isAllowTurnToDo="${isAllowTurnToDo}";
	             var isAllowPrint="${isAllowPrint}";
	             var isAllowTurnBack="${isAllowTurnBack}";
	             var isAllowReject="${isAllowReject}";	 
	             var changname="${changname}";	  
	            if(isAllowAssit!=null&&isAllowAssit=='false')
	            	{	            	 
	            	  $("#assit").hide();
	            	}	          
	            if(isAllowTurnToDo!=null&&isAllowTurnToDo=='false')
            	{	            	 
            	  $("#change").hide();
            	}
	            if(isAllowPrint!=null&&isAllowPrint=='false')
            	{	            	 
            	  $("#printpage").hide();
            	}
	            if(isAllowTurnBack!=null&&isAllowTurnBack=='false')
            	{	            	 
            	  $("#jumpNode").hide();
            	}
	            if(isAllowReject!=null&&isAllowReject=='false')
            	{	   	    
            	  $("#reject").hide();
            	}	          
	            if(changname!=null&&changname!='')
            	{	  	            
            	  $("#agree").text(changname);
            	}
	          }
		   );
	</script>
	 <script>
    $(function(){
    	$("#user-select>li:first").addClass("active");
    	$(".select-tabs>.select-option:first").show();
    	$("#user-select>li").click(function(){
    		var index=$(this).index('li');
    		$(this).addClass("active").siblings("li").removeClass("active");
    		$(".select-tabs>.select-option:nth-child("+(index+1)+")").show().siblings().hide();
  
    	});
    	
    	
    });    	
    $(function(){
    	if(!placeholderSupport()){   // 判断浏览器是否支持 placeholder
    	    $('[placeholder]').focus(function() {
    	        var input = $(this);
    	        if (input.val() == input.attr('placeholder')) {
    	            input.val('');
    	            input.removeClass('placeholder');
    	        }
    	    }).blur(function() {
    	        var input = $(this);
    	        if (input.val() == '' || input.val() == input.attr('placeholder')) {
    	            input.addClass('placeholder');
    	            input.val(input.attr('placeholder'));
    	        }
    	    }).blur();
    	};
    	})
    	function placeholderSupport() {
    	    return 'placeholder' in document.createElement('input');
    	}
</script> 
</body>
</html>