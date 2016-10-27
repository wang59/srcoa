<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>打印</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge;IE=11;IE=10;IE=9"/>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript"
	src="plug-in/jquery-plugs/form/jquery.form.js"></script>
<link rel="stylesheet"
	href="plug-in/ace/css/bootstrap.css">
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
	.row h2{
	font-size:24px;
	}
	#dataCommit{
	line-height:24px;
	font-size:14px;
	}
	#dataCommit thead{
	font-weight:bold;
	}
	body>table{
	padding:10px!important;
	margin-top:10px!important;
	line-height:1.2;
	}
	table tr td:first-child{
            padding-left:20px;
        }
    #dataCommit{
    width:100%;
    margin:0 auto!important;
    } 
    .rest-time{
	display:none;
	}   
	#printContainer table{
	width:100%;
	}
	#printContainer>p:nth-child(1){
	font-size:24px;
	font-weight:bold;
	line-height:3;
	}
	textarea:disabled{
	background:#ffffff;
	border:0;
	}
	</style>
</head>
<body class="view container-fluid row" style="padding:0;width:80%;margin:0 auto;">
		<div id="printContainer">
				${formContent}	
			
			<div class="" id="dataCommit">
				
				<div class="row" style="margin:0;">
					<h2 class="col-md-12">流程意见</h2>
					<table class="table table-striped">
					
							<tr>
								<td class="col-md-1">编号</td>
								<td class="col-md-2">环节名称 </td>
								<td class="col-md-2">处理人</td>
								<td class="col-md-2">处理时间</td>
								<td class="col-md-5">审批意见</td>								
							</tr>
						
					</table>
				</div>
				<a class="btn btn-success"id="makeure">确认打印</a>
			</div>
			
		</div>
		
	

 <script type="text/javascript">
 
 //print_plugin
 (function($) {
	    var opt;

	    $.fn.jqprint = function (options) {
	        opt = $.extend({}, $.fn.jqprint.defaults, options);

	        var $element = (this instanceof jQuery) ? this : $(this);
	        
	        if (opt.operaSupport && $.browser.opera) 
	        { 
	            var tab = window.open("","jqPrint-preview");
	            tab.document.open();

	            var doc = tab.document;
	        }
	        else 
	        {
	            var $iframe = $("<iframe  />");
	        
	            if (!opt.debug) { $iframe.css({ position: "absolute", width: "0px", height: "0px", left: "-600px", top: "-600px" }); }

	            $iframe.appendTo("body");
	            var doc = $iframe[0].contentWindow.document;
	        }
	        
	        if (opt.importCSS)
	        {
	            if ($("link[media=print]").length > 0) 
	            {
	                $("link[media=print]").each( function() {
	                    doc.write("<link type='text/css' rel='stylesheet' href='" + $(this).attr("href") + "' media='print' />");
	                });
	            }
	            else 
	            {
	                $("link").each( function() {
	                    doc.write("<link type='text/css' rel='stylesheet' href='" + $(this).attr("href") + "' />");
	                });
	            }
	        }
	        
	        if (opt.printContainer) { doc.write($element.outer()); }
	        else { $element.each( function() { doc.write($(this).html()); }); }
	        
	        doc.close();
	        
	        (opt.operaSupport && $.browser.opera ? tab : $iframe[0].contentWindow).focus();
	        setTimeout( function() { (opt.operaSupport && $.browser.opera ? tab : $iframe[0].contentWindow).print(); if (tab) { tab.close(); } }, 1000);
	    }
	    
	    $.fn.jqprint.defaults = {
			debug: false,
			importCSS: true, 
			printContainer: true,
			operaSupport: true
		};
	    jQuery.fn.outer = function() {
	      return $($('<div></div>').html(this.clone())).html();
	    } 
	})(jQuery);
 //print_plugin
        
		$(function() {
			$(":input").attr("disabled", "true");
			$.ajax({
				type:"get",
    			datatype:"json",
    			url:"workFlowAutoFormController.do?ApproveLogajax&businessid=${param.id}",
    			success:function(data){
    				function getLocalTime(nS) {     
    					 return new Date(parseInt(nS) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");  
 					};
    				var data=eval('['+data+']')[0].attributes;    				
    				var j=1;
    				for(i in data){
        					var addContent='<tr><td class="col-md-1">'+j+'</td><td class="col-md-2">'+data[i].nodeName+'</td><td class="col-md-2">'+data[i].approverName+'</td><td class="col-md-2">'+getLocalTime(data[i].createTime/1000)+'</td><td class="col-md-5">'+data[i].comment+'</td></tr>';
        					$("#dataCommit .row .table tbody").append(addContent);
        					j++;
        			};
        			
    			}
			});
			 $("#makeure").click(function(){
				 $("#printContainer").jqprint();				 
			 })
			
		});

		
	</script>
</body>
</html>