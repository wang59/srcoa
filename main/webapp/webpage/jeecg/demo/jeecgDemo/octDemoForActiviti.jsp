<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>


<t:formvalid formid="formobj" dialog="false" layout="div" action="demoController.do?saveDemo">

	<input type="hidden" id="ds.id" name="ds.id" value="" />
	<fieldset class="step">
	<div class="form"><label class="Validform_label"> 非空验证： </label> <input type="text" name="ds.wxj_test" id="ds.wxj_test" value="${ds.wxj_test }" datatype="*" errormsg="该字段不为空"> <span class="Validform_checktip"></span></div>
	<div class="form"><label class="Validform_label"> 至少选择2项： </label> 
	<input name="ds.wxj_test3" class="rt2" id="shoppingsite21" type="checkbox" value="1" datatype="need1" nullmsg="请选择您的爱好！" />
	阅读 <input name="ds.wxj_test3" class="rt2" id="shoppingsite22" type="checkbox" value="2" /> 
	音乐 <input name="ds.wxj_test3" class="rt2" id="shoppingsite23" type="checkbox" value="3" /> 
	运动 <span class="Validform_checktip"></span></div>
	<div class="form"><label class="Validform_label"> 邮箱： </label> <input type="text" name="ds.wxj_test2" value="${ds.wxj_test2 }" id="ds.wxj_test2" datatype="e" errormsg="邮箱非法"> <span class="Validform_checktip"></span></div>
	</fieldset>
</t:formvalid>
 	
