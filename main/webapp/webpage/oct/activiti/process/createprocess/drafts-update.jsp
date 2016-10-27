<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <title>草稿箱</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
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
            html,body{
                height:100%;
                width:100%;
                padding:0;
                margin:0;
                font-family:"Microsoft Yahei";
            }
            #preview{
                width:100%;
                height:100%;
                padding:0;
                margin:0!important;
                
            }
            #preview *{
            	font-size:16px;
            }
            .container{
            	position:relative;
            }
            .btn-group{
            	position:absolute;
            	right:0;
            }
            .easyui-tabs{
            	margin-top:30px;
            }
            .btn-group .btn:hover{
            	background:rgb(0, 176, 240);
            	color:#ffffff;
            }
			#preview tr td input[type="text"]{
				border:0;
				border-bottom:1px solid rgb(0, 176, 240);
				box-shadow:none;
				border-radius:0;
			}
			#table-select{
				display:none;
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
			.select-tabs .select-option{
				display:none
			}
			#user-select{
 				position:absolute;
 				top:45px;
			}
			#user-select li{
				float:left;
				padding:5px 15px;
				cursor:pointer;
				color:#adadad;
				border-right:1px solid #adadad
			}
			#user-select li:last-child{
				border-right:0;
			}
			#user-select li:hover{
				color:rgb(0, 176, 240);
			}
			.active{
				color:rgb(0, 176, 240)!important;
			}
			/*tab样式*/
        </style>
    </head>
    <body class="view row">
    <form id="formSubmit" action="autoFormController.do?updateForm" method="post">
      <input type="hidden" id="btn_sub">
       <input type="hidden" name="formName" value="${param.formName}">
      <input type="hidden" name="workflowName" value="${param.workflowName}">
      <c:forEach items="${param}" var="item">
        <input type="hidden" name="param.${item.key}" value="${item.value}">
	  </c:forEach>
      <div class="container">
		<div class="btn-group">
		<button type="button" class="btn navbar-button" onclick="chang();"> 提交 </button>
			<button type="button" class="btn navbar-button" onclick="formSubmit();"> 保存 </button>
			
		</div>
		<ul id="user-select">
			<li>申请信息</li>
			<li>流程图</li>
		</ul>
		<div class="select-tabs" id="tt">
				<div id="preview" style=" margin-bottom: 0; padding-bottom:400px;"title="申请信息" class="select-option">${formContent}</div>
			<div title="流程图" class="liuchengtu select-option">
           			<img alt="" src="flowsheetController.do?photo&deployname=${param.workflowName}">  
           			<c:if test="${document!=null }">
           			 <br>流程描述<br>
           			    ${document}       			    
           			</c:if>           			
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
										if(data.success){
						            		alert(data.msg);
						            		var id = data.obj;
						            		$("#param_op").val("update");
						            		$("#param_id").val(id);
						            		$("#reloadViewForm").submit();
						            		window.opener.location.reload();
						            		window.close();//关闭窗口
						            	}else{
						            		alert(data.msg);
						            		window.opener.location.reload();
						            		window.close();//关闭窗口
						            	}
									}
								});
							form.tipmsg.s="非空";
							form.tipmsg.r=" ";
						});
						 function formSubmit(){
							 $("#btn_sub").click();
						 }
						 function chang(){
							$("#formSubmit").attr("action","workFlowAutoFormController.do?start&type=1");
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
    
</script>   
    </body>
</html>