<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/oct-oa/plug-in/jquery/jquery-1.8.3.js"></script>
</head>
<body>
<script type="text/javascript">

	 	 $.post("/activiti-explorer/ui/1/loginHandler?username=kermit&password=kermit",
	function(data){
		window.location.href="/activiti-explorer/ui/1/loginHandler"; 
     }
	 )
</script>
</body>
</html>