<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>二维码</title>
</head>
<body>
<form  id="formid" method="post" onsubmit="return check()">
   请输入内容：<input type="text" name="fname" id="wenben"/><br>
 <input type="submit" value="生成二维码" id ="sce"/>
</form>

</body>

<script type="text/javascript">

function check(){

		var a=$("#wenben").val();
		 
		     if(a==""){
			alert("内容不能为空！");
			return false;
			}
		       else{
		    	   var purl="twoDimensionCodeController.do?getTwoCode&wenben="+a;
		   		window.open(purl);
		    	 return true;
		        }
			}
	


</script>
</html>