<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=Edge;IE=11;IE=10;IE=9"/>
<title>审批</title>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" type="text/css" href="plug-in/ztree/css/zTreeStyle.css">
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<style>
*{
padding:0;
margin:0;
font-family:"Microsoft-Yahei";
}
body{
height:100%;
position:absolute;
}
.approval-wrap{
width:100%;
height:100%
}
.approval-wrap>tbody>tr>td{
vertical-align:top;
}
#approval-content{
width:100%;
height:100%;
display:inline;
margin-left:-2px;
border:1px #dddddd solid;
}
.approval-list-head{
display:inline-block;
height:2px;
width:94%;
position:relative;
border-top:20px solid #005d98;
border-bottom:20px solid #005d98;
border-left:6px solid #005d98;
border-right:6px solid #005d98;
background-color:#004674;
}
.list-push,.list-draw{
width:20px;
height:20px;
display:inline-block;
position:absolute;
top:-10px;
right:44%;
margin-left:10px;
border:1px solid #004674;
border-radius:10px;
cursor:pointer;
}
.list-push {
background:url("webpage/images/main/icon-double-angle-left.png") no-repeat;
}
.list-draw {
width:42px;
height:17px;
right:-5px;
background:url("webpage/images/main/icon-double-angle-right.png") no-repeat;
}
.approval-list-wrap{
width:226px;
height:100%;
position:relative;
 background:#005d98;
}
#approval-list{
 border-top:1px solid #4190c3;
}
#approval-list li{
 line-height:1.5;
 font-size:14px;
 color:#ffffff;
 white-space:nowrap; 
 overflow:hidden; 
 text-overflow:ellipsis;
 border-top:1px solid #4190c3;
 border-bottom:1px solid #004674;
 padding:8px;
}
#approval-list li:hover,.active{
 cursor:pointer;
 color:#00b0f0;
}
#approval-list li:hover>i,.list-active>i{
background:url("webpage/images/main/icon-page-hover.png") no-repeat;
}
#approval-list h2{
font-weight:bold;
}

.icon-page{
display:inline-block;
width:24px;
height:22px;
position:relative;
top:7px;
margin-right:5px;
background:url("webpage/images/main/icon-page.png") no-repeat;
}
.list-active{
color:#00b0f0!important;
}
.slide-wrap{
position:relative;
width:100%;
height:100%;
}
.approval-iframe-wrap{
width:100%;
height:100%;
}
.approval-iframe{
height:100%;
}
</style>
</head>
<body>
<table class="approval-wrap">
	<tr>
		<td class="approval-list-wrap">
			<div class="slide-wrap">
				<div class="approval-list-head">
					<a id="list-push" class="list-push"></a>
				</div>
				<ul id="approval-list"></ul>
			</div>
		</td>
		<td class="approval-iframe">
			<div class="approval-iframe-wrap">
				<iframe id="approval-content"></iframe>
			</div>
		</td>
	</tr>
</table>
<script>

$(function(){
	$.ajax({
		url:"toDoController.do?todogrid&field=id,actTaskBusiness.businessId,actTaskBusiness.businessTitle,actTaskBusiness.businessCreateName,createTime,tSBaseUser.realName&order=desc&sort=createTime&type=long",
		typy:"GET",
		success:function(data){
			var data=data.rows;
			for(var i=0;i<data.length;i++){
				var result="<li myid=\'"+data[i].id+"\'><i class='icon-page'></i>"+data[i]['actTaskBusiness.businessTitle']+"</li>";
				$("#approval-list").append(result);
			}
			
		}
	});

	setTimeout(function(){
		
		if(${param.id}){
			firstId=${param.id};
		}else{
			firstId=$('#approval-list li:first').attr('myid');
		}
		
		if($("#approval-list li[myid='"+firstId+"']").length){
			$('#approval-list li[myid='+firstId+']').addClass('list-active');
			var myId="toDoController.do?editapp&taskId="+firstId;
			$("#approval-content").attr("src",myId);
		}else{
			$('#approval-list li:first').addClass('list-active');
			var myId="toDoController.do?editapp&taskId="+$('#approval-list li:first').attr('myid');
			$("#approval-content").attr("src",myId);
		}
		
		$("#approval-list li").click(function(){
			var myId="toDoController.do?editapp&taskId="+$(this).attr('myid');
			$("#approval-content").attr("src",myId);
			$(this).addClass('list-active').siblings().removeClass('list-active');
		});
	},300);
	$("#list-push").click(function(){
		if($('.approval-list-wrap').css('width')!='50px'){			
			$('.approval-list-wrap').css('width','50px');
			$('.approval-list-head').css('width','71%');
			$('#approval-list').hide();
			$("#list-push").removeClass('list-push').addClass('list-draw');
			$(window.frames["approval-content"].document).find("#comment-iframe").reload();
		}else if($('.approval-list-wrap').css('width')=='50px'){
			$('.approval-list-wrap').css('width','226px');
			$('.approval-list-head').css('width','94%');
			$('#approval-list').show();
			$("#list-push").addClass('list-push').removeClass('list-draw');
		}
		
	});
});
</script>
</body>
</html>