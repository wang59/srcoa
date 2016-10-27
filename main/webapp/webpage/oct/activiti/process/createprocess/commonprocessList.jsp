
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta charset="UTF-8">
<title>Basic Tabs - jQuery EasyUI Demo</title>
<link rel="stylesheet" type="text/css"
	href="plug-in/easyui/themes/default/easyui.css">
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
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#p").click(function() {
			alert(123);
			$.ajax({
				url : "one.do",
				dataType : 'json',
				success : function(msg) {
					alert(msg.name);
				},
			});
		});
	});
</script>
</head>
<body>
		<div title="" style="padding: 10px">
			<c:forEach var="pro" items="${commonVos}" varStatus="b">
			   <c:if test="${ (b.count-1)%4!=0}">
			       <div class="li">
							<a href="${pro.url}&proid=${pro.id}" target="_blank">${pro.name }</a>							                       
					</div>
			   </c:if>
		  <c:if test="${ (b.count-1)%4==0}">
			       <div class="li" style="clear: left">
							<a href="${pro.url}&proid=${pro.id}" target="_blank">${pro.name }</a>							                       
					</div>
			   </c:if>
			</c:forEach>
		</div>	
</body>

</html>