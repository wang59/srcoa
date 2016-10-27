
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta charset="UTF-8">
<title>Basic Tabs - jQuery EasyUI Demo</title>
<link rel="stylesheet" type="text/css"
	href="plug-in/easyui/themes/default/easyui.css">
	<link rel="stylesheet" href="plug-in/ace/assets/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css"
	href="plug-in/easyui/themes/icon.css">
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="plug-in/easyui/jquery.easyui.min.js"></script>
<style type="text/css">
html, body {
	height: 100%;
	width: 100%;
	padding: 0;
	margin: 0;
	font-family:Microsoft YaHei!important;
	background:#fff;
}
.li {
	height: 30px;
	line-height: 30px;
	overflow: hidden;
	padding: 0 5px;
	width: 190px;
	text-overflow: ellipsis;
	color: #005784;
	border: #ccc solid 1px;
	float: left;
	margin: 10px;
	text-align: center;
	border-radius:4px;
}
.li a{
	text-decoration: none;
	color:#666666;
}
.clear {
	clear: left;
}

.tabs{
	padding-left:20px;
	height:40px;
	padding-left:0px;
}
.tabs-wrap,tabs-header{
	background-color:#fff;
}
.tabs-header{
	border-width:0;
	padding-top:0;
}
.tabs li.tabs-selected a.tabs-inner {
  background-color: #fff;
  color: rgb(0, 176, 240);
}
.tabs li{
	margin-right:0;
	border-right:1px solid #dddddd;
}
.tabs li:last-child{
	border-right:0;
}
.tabs li a.tabs-inner{
	border:0;
	background:#fff;
}
.tabs li.tabs-selected a.tabs-inner,.tabs li a.tabs-inner:hover{
	background:#fff;
}
.tabs li a.tabs-inner:hover{
	color:rgb(0, 176, 240);
}
.form-title h2{
	font-size:18px;
	padding-left:70px;
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
	left:-60px;
	top:0px;
	z-index:2000;
}
.form-title-img{
	width:60px;
	height:60px;
	background-color:rgb(0, 176, 240);
	position:absolute;
	top:10px;
}
.container{
	margin-top:40px;
}
.icon-heart,.icon-heart-empty{
	color:#df3434;
	cursor:pointer;
}
.icon-heart-empty,.icon-heart{
	width:20px;
	height:16px;
	background:url(../webpage/images/main/icon-heart-empty.png) no-repeat;
	display:inline-block!important;
	position:relative;
	top:-14px;
}
.icon-heart{
	background:url(../webpage/images/main/icon-heart.png) no-repeat;
}
.icon-heart-empty:before,.icon-heart:before{
	content:"";
}
</style>
<script type="text/javascript">
function delcollection(id){
	 $.ajax({
			url : "createAppController.do?delcollection",
			data : {
				"id" : id			
			},
			dataType : 'json',
			type : 'post',
			success : function(msg) {	
				location.reload();				
			},
			error:function(data){
				alert(22224444);
			}
			
	});
}
</script>
</head>
<body>
		<div title="" style="padding: 10px">
			<c:forEach var="pro" items="${user_map}">
				<h2 class="clear">${pro.key }</h2>
				<c:forEach var="impl" items="${pro.value }" varStatus="b">
					<c:if test="${ (b.count-1)%4!=0}">
						<div class="li">
							<a href="${impl.process.url}&proid=${impl.process.id}" target="_blank">${impl.process.name }</a>
							<a onclick="delcollection('${impl.id }')"><i class="icon-heart" title="取消收藏"></i></a>                       
						</div>
					</c:if>
					<c:if test="${ (b.count-1)%4==0}">
						<div class="li" style="clear: left">
							<a href="${impl.process.url}&proid=${impl.process.id}" target="_blank">${impl.process.name }</a>		
							<a onclick="delcollection('${impl.id }')"><i class="icon-heart" title="取消收藏"></i></a>         				
						</div>
					</c:if>
					
				</c:forEach>
				<br>
			</c:forEach>
		</div>		
		
<script>
$(function(){
	$('.icon-heart').mouseenter(function(){
		$(this).removeClass('icon-heart').addClass('icon-heart-empty');
	}).mouseleave(function(){
		$(this).removeClass('icon-heart-empty').addClass('icon-heart');
	});
});
</script>
</body>
</html>