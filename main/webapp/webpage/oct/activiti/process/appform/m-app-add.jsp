<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <title>新建:${proName}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge;IE=11;IE=10;IE=9"/>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <t:base type="jquery,easyui,tools"></t:base>
        <script type="text/javascript" src="plug-in/jquery-plugs/form/jquery.form.js"></script>
	    <link rel="stylesheet" href="plug-in/Formdesign/js/ueditor/formdesign/bootstrap/css/bootstrap.css">
	    
	    <!-- add start by jg_renjie at 20151025 for:增加支持Validform插件的相关文件 -->
	    <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css">
	    <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css">
	    <link rel="stylesheet" href="style/form-common.css" type="text/css">
	    <script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
	    <script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
	    <script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
	    <script type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></script>
	    <!-- add end by jg_renjie at 20151025 for:增加支持Validform插件的相关文件 -->
	    
	    <script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
	    <link rel="stylesheet" href="plug-in/My97DatePicker/skin/WdatePicker.css" type="text/css"></link>
	    
	    <!--[if lte IE 6]>
	    <link rel="stylesheet" type="text/css" href="plug-in/Formdesign/js/ueditor/formdesign/bootstrap/css/bootstrap-ie6.css">
	    <![endif]-->
	    <!--[if lte IE 7]>
	    <link rel="stylesheet" type="text/css" href="plug-in/Formdesign/js/ueditor/formdesign/bootstrap/css/ie.css">
	    <![endif]-->
	    <link rel="stylesheet" href="plug-in/Formdesign/js/ueditor/formdesign/leipi.style.css">
<style>
 #preview{
      width:100%;
      height:100%;
      padding:0;
      margin:0;
}
#preview *{font-family:sans-serif;font-size:16px;}
.easyui-tabs{
      position:relative;
}
body {
	height: 100%;
	width: 100%;
	padding: 0;
	margin: 0;
	font-family:"Microsoft YaHei"!important;
	background:#fff;
}
.tabs-container{
	padding-top:48px;
}
input,textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input{
	border:0!important;
	border-radius:0!important;
}
textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input{
	-webkit-box-shadow:0!important;
	box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0)!important;
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
	font-family:"Microsoft YaHei"!important;
}
.form-header p{
	font-family:"Microsoft YaHei";
	font-size:9pt;
	color:#666666;
	font-family:"Microsoft YaHei"!important;
}
.form-head{
	height:30px;
	padding-top:10px;
	padding-bottom:10px;
	position:absolute;
	right:20px;
	top:30px;
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
	top:10px;
}
.container{
	margin-top:20px;
}
.form-title h2{
	font-size:18px;
	padding-left:70px;
	line-height:24px;
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
.liuchengtu{
padding-bottom:65px;
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
</style>
        <script type="text/javascript">
        var sys_curUserID="${sys_curUserID}";//当前用户ID
        var sys_curUserName="${sys_curUserName}";//当前用户登录名、账号
        var sys_curRealName="${sys_curRealName}";//当前用户姓名
      //附件服务器的地址，如：http://192.168.82.202
        var sys_fileServer="${sys_fileServer}";
        $(document).ready(function(){
        	var mydate=new Date();
        	var year=mydate.getFullYear();
        	var month=mydate.getMonth()+1;
        	var day=mydate.getDate();
        	var mytime=year+"-"+month+"-"+day;
        	$("input[name='ds.create_date']").val(mytime);
        });
        </script>
    </head>
    <body class="view">
    <form id="formSubmit" action="workFlowAutoFormController.do?start" method="post">
      <input type="hidden" id="btn_sub">
      <input type="hidden" name="formName" value="${formName}">
      <input type="hidden" name="workflowName" value="${param.workflowName}">
      <!-- 插入数据用于根据类型生成ID， -->
      <input type="hidden" name="idType" value="${idType}">
      <!-- 流程定义/绑定流程记录的ID -->
      <input type="hidden" name="proid" value="${proid}">
       <!-- 插入或者更新数据时用于生成SQL语句 -->
       <input type="hidden" name="tableName" value="${tableName}">
       
      <c:forEach items="${param}" var="item">
        <input type="hidden" name="param.${item.key}" value="${item.value}">
	  </c:forEach>
	  
	  <div class="form-title">
				<div class="form-title-img"></div>
				<h2>移动流程:处理 -${proName} </h2>
		</div>
		<div class="form-head">
				<div class="btn-group pull-right" role="group">
				    
					<button type="button" class="btn" onclick="formSubmit();">提交</button>	
					<button type="button" class="btn" onclick="saveform();">保存</button>									
				</div>
		</div>
		<ul id="user-select">
			<li>申请信息</li>
			<li>流程图</li>
		</ul>
		<div  id="select-tabs"class="select-tabs" id="tt">
				<div id="preview" style=" margin-bottom: 0; padding-bottom:80px;" class="select-option">
					<div class="container">
						<div class="row" id="table-wrap">		
							<c:if test="${isJSP!='Y'}">
							   <!-- 显示自动以表单内容  开始 -->
	                           ${formContent}
	                           <!-- 显示自动以表单内容 结束 -->
                            </c:if>
							<c:if test="${isJSP=='Y'}">
							  <!-- 插入JSP页面  开始-->
	                          <jsp:include page='<%=request.getParameter("formName") %>'></jsp:include>
	                           <!-- 插入JSP页面  结束-->
                            </c:if>
						</div>
					</div>
					
				</div>
				 <!-- 选项卡  流程图-->
           	 	<div title="流程图2" class="liuchengtu select-option">
           			<img alt="" src="flowsheetController.do?photo&deployname=${param.workflowName}" id="flowsheet">   
           			<c:if test="${document!=null }">
           			 <div class="progress-content" id="content">
           			 <h2>流程描述</h2>
           			    <span id="document">${document}</span>    			    
           			</c:if>  
           			</div>      			
            	</div>
            	
      <!-- 声明是选项卡 容器 结束-->
		</div>
			
	 
	  
     </form>   
     <br><br>
     <nav class="navbar navbar-default navbar-fixed-bottom" role="navigation">
         <div class="navbar-inner">
		    <div class="container">
		      <div class="nav-collapse collapse">
		        <ul class="nav" style="float: right;">
		            <li></li>
		        </ul>
		      </div>
		    </div>
		    <hr>
		  </div>
     </nav>   
     <script type="text/javascript">
                        var form;
						$(function() {
							form = $("#formSubmit").Validform({
									tiptype : 4,
									btnSubmit : "#btn_sub",
									btnReset : "#btn_reset",
									ajaxPost : true,
									callback : function(data) {
										if(data.success){
						            		alert(data.msg);						            		
						            		closeWin();//关闭窗口
						            	}else{
						            		alert(data.msg);
						            	}
									}
								});
						
							form.tipmsg.s=" ";
							form.tipmsg.r=" ";
							
						});
						 function formSubmit(){
							 //判断提交前是否需要执行js函数；							
							 if( typeof beforeSubmit === 'function' ){
								    //存在且是function
								 var hasBeforeSubmit=true;
								}else{
									var hasBeforeSubmit=false; 
								}							
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
						 }
						 function saveform(){
							 
							 var b=$("#formSubmit")[0];
							 b.action="workFlowAutoFormController.do?saveform";
							 $("#btn_sub").click();
							
						 }
						 
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
    //根据传入的流程名修改流程图、流程描述和参数workflowName
    function changeWorkFlowName(workFlowName){
    	$("input[name='workflowName']").val(workFlowName);
    	$("#flowsheet").attr("src","flowsheetController.do?photo&deployname="+workFlowName);
    	$.ajax({
			url : "workFlowAutoFormController.do?getDocument",
			data : {
				"workFlowName" : workFlowName
			},
			dataType : 'json',
			type : 'post',
			success : function(msg) {
				if(msg.success){
					$("#content").show();
				   $("#document").text(msg.msg);
				}else{
				  $("#content").hide();
				}				
			}
		});
    }
</script>   
    </body>
</html>