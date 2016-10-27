<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style>
.select-option>iframe{
width:100%;
height:80%;
overflow:auto;
border:0;
}
#tab-btn li{
float:left;
padding:4px 12px;
cursor:pointer;
margin-top:4px;
font-size:16px;
border-right:1px rgb(0, 176, 240) solid;
}
#tab-btn li:last-child{
border:0;
}
.active{
color:rgb(0, 176, 240);
}
.select-option{
display:none;
}
</style>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!--<t:tabs id="sss" iframe="true" tabPosition="top" fit="true">
	<t:tab href="createAppController.do?getAll"  title="全部流程" id="all"></t:tab>
	<t:tab href="createAppController.do?getCollection"  title="我的收藏" id="no"></t:tab>
	<t:tab href="createAppController.do?getCommon"  title="常用流程" id="noread"></t:tab>
</t:tabs>-->
<div class="process-create-wrap">
	<ul id="tab-btn">
		<li>全部流程</li>
		<li>我的收藏</li>
		<li>常用流程</li>
	</ul>
	<div class="select-tabs">
		<div class="select-option">
			<iframe src="createAppController.do?getAll"></iframe>
		</div>
		<div class="select-option">
			<iframe src="createAppController.do?getCollection"></iframe>
		</div>
		<div class="select-option">
			<iframe src="createAppController.do?getCommon"></iframe>
		</div>
	</div>
</div>
<script>
$(function(){
	$("#tab-btn>li:first").addClass("active");
	$(".select-tabs>.select-option:first").show();
	$("#tab-btn>li").click(function(){
		var index=$(this).index('li');
		$(this).addClass("active").siblings("li").removeClass("active");
		$(".select-tabs>.select-option:nth-child("+(index+1)+")").show().siblings().hide();
	});
	
	
})

</script>