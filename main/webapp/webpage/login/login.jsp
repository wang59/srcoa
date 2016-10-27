<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.jeecgframework.core.util.SysThemesUtil,org.jeecgframework.core.enums.SysThemesEnum"%>
<%@page import="org.jeecgframework.core.util.IpUtil"  %>
<%@include file="/context/mytags.jsp"%>
<%
  session.setAttribute("lang","zh-cn");
  SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
  String lhgdialogTheme = SysThemesUtil.getLhgdialogTheme(sysTheme);
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta charset="utf-8" />
  <title><t:mutiLang langKey="jeect.platform"/></title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
  <!-- bootstrap & fontawesome -->
  <link rel="stylesheet" href="plug-in/ace/css/bootstrap.css" />
  <link rel="stylesheet" href="plug-in/ace/css/font-awesome.css" />
  <link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
  <!-- text fonts -->
  <link rel="stylesheet" href="plug-in/ace/css/ace-fonts.css" />

  <link rel="stylesheet" href="plug-in/ace/css/jquery-ui.css" />
  <!-- ace styles -->
  <link rel="stylesheet" href="plug-in/ace/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
  <!--[if lte IE 9]>
  <link rel="stylesheet" href="plug-in/ace/css/ace-part2.css" class="ace-main-stylesheet" />
  <![endif]-->
  <link rel="stylesheet" href="style/common.css"/>
  <!--[if lte IE 9]>
  <link rel="stylesheet" href="plug-in/ace/css/ace-ie.css" />
  <![endif]-->
  <!-- ace settings handler -->
  <script src="plug-in/ace/js/ace-extra.js"></script>

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

  <!--[if lte IE 8]>
  <script src="plug-in/ace/js/html5shiv.js"></script>
  <script src="plug-in/ace/js/respond.js"></script>
  <![endif]-->
<style>
@media (max-width: 1400px) {
.login-cache .pull-right{
	float:left!important;
}
 }

</style>
</head>
<body class="login-layout light-login">
<%
   String ip=IpUtil.checkIpAddr(request);
 if(ip.endsWith("0")){
	 request.setAttribute("check", true);
 }else{
	 request.setAttribute("check", false);
 }
%>
<div class="main-container">
  <div class="main-content">
    <div class="row">
      <div class="col-sm-10 col-sm-offset-1">
        <div class="login-container">
          <div class="center">
            <h1 id="id-text2" class="grey">
              <img src="webpage/images/logo.png">
            </h1>
          </div>
          <div class="space-6"></div>
          <div class="position-relative">
            <div id="login-box" class="login-box visible widget-box no-border">
              <div class="widget-body">
              <div class="widget-header blue ">
              	<h4 class="header lighter bigger">
                    <i class="ace-icon fa login-edit"></i>
                	    <span>用户登录</span>
                  </h4>
              </div>
                <form id="loinForm" class="form-horizontal"  check="loginController.do?checkuser"  role="form" action="loginController.do?login"  method="post">
                <div class="widget-main">
                 <div class="alert alert-warning alert-dismissible" role="alert" id="errMsgContiner">
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <div id="showErrMsg"></div>
				</div>
                  
                      <label class="block clearfix fill-text">
								<span class="block input-icon input-icon-right">
									<input type="text"  name="userName" class="form-control" placeholder="账号"  id="userName" value=""/>
									<i class="ace-icon fa login-user"></i>
								</span>
								<span class="block input-icon input-icon-right">
									<input type="password" name="password" class="form-control" placeholder="密码" id="password" value=""/>
									<i class="ace-icon fa login-lock"></i>
								</span>
                      </label>      
                      <c:if test="${check=='false' }">          
                      <div >
                      <label class="block clearfix">
                        <div class="input-group">
                          <input value="ff" type="text" style="width:150px" name="randCode" class="form-control" placeholder="请输入验证码"  id="randCode" onkeydown="randCodeKeyDown()"/>
                          <span class="input-group-addon" style="padding: 0px;"><img id="randCodeImage" src="randCodeImage"  /></span>
                        </div>
                      </label>
                      </div>  
                      </c:if>                  
                      <div class="clearfix login-cache">
                        <label class="inline">
                          <input type="checkbox" class="ace" id="on_off"  name="remember" value="yes"/>
                          <span class="lbl">记住用户名</span>
                        </label>
                        <label class="inline pull-right">
                          <input type="checkbox" class="ace" id="on_auto"  name="remember" value="yes"/>
                          <span class="lbl">自动登录</span>
                        </label>
                      </div>
                      <div class="space-4"></div>

                </div>
                
                <div class="toolbar clearfix">
                <p style="color: red;text-align: left;" id="showwarn"></p>
                	<a id="but_login"  onclick="checkUser()" class="uncan-click">
                          <span class="bigger-110" >登录</span>
                          
                    </a>
                 
                </div>
                </form>
              </div>
            </div>
            <div class="center"><h4  id="id-company-text">&copy; 华侨城文旅科技版权所有</h4></div>
           
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
<div style="display: none">
<iframe id="iframe"></iframe>
<iframe id="iframe_pm"></iframe>
</div>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="plug-in/ace/js/jquery.cookie.js"></script>
<script>

function checkLogin(){
	//test();
	if($("#userName").val() != "" && $("#password").val() != ""){
		$(but_login).removeClass("uncan-click").addClass("can-click");
	}else{
		$(but_login).removeClass("can-click").addClass("uncan-click");
	}
}
	
window.onload=function(){

	window.onkeydown=function(e){
		if(window.event){
			var lKeyCode =window.event.keyCode;
		}else{
		  var lKeyCode = e.which; //event.keyCode按的建的代码，13表示回车
		}
		if(lKeyCode == 13){
			checkUser();
		}
	}
	if ($.cookie("autoLogin") == "true"){
		  checkUser();
	}

}
$(window).keyup(function(){
	checkLogin();
})
$(document).ready(function () {
	 
    if ($.cookie("rmbUser") == "true") {
    	$("#on_off").attr("checked", true);
    	$("#userName").val($.cookie("username"));
    	$("#password").val($.cookie("password"));
    	//$("#on_auto").attr("checked", true);
    }
	setTimeout(function(){
		checkLogin();
	},100)
   
});
</script>
<script src="plug-in/ace/js/bootstrap.js"></script>
<script src="plug-in/ace/js/bootbox.js"></script>
<script src="plug-in/ace/js/jquery-ui.js"></script>
<script src="plug-in/ace/js/jquery.ui.touch-punch.js"></script>
<!-- ace scripts -->
<script src="plug-in/ace/js/ace/elements.scroller.js"></script>
<script src="plug-in/ace/js/ace/elements.colorpicker.js"></script>
<script src="plug-in/ace/js/ace/elements.fileinput.js"></script>
<script src="plug-in/ace/js/ace/elements.typeahead.js"></script>
<script src="plug-in/ace/js/ace/elements.wysiwyg.js"></script>
<script src="plug-in/ace/js/ace/elements.spinner.js"></script>
<script src="plug-in/ace/js/ace/elements.treeview.js"></script>
<script src="plug-in/ace/js/ace/elements.wizard.js"></script>
<script src="plug-in/ace/js/ace/elements.aside.js"></script>
<script src="plug-in/ace/js/ace/ace.js"></script>
<script src="plug-in/ace/js/ace/ace.ajax-content.js"></script>
<script src="plug-in/ace/js/ace/ace.touch-drag.js"></script>
<script src="plug-in/ace/js/ace/ace.sidebar.js"></script>
<script src="plug-in/ace/js/ace/ace.sidebar-scroll-1.js"></script>
<script src="plug-in/ace/js/ace/ace.submenu-hover.js"></script>
<script src="plug-in/ace/js/ace/ace.widget-box.js"></script>
<script src="plug-in/ace/js/ace/ace.settings.js"></script>
<script src="plug-in/ace/js/ace/ace.settings-rtl.js"></script>
<script src="plug-in/ace/js/ace/ace.settings-skin.js"></script>
<script src="plug-in/ace/js/ace/ace.widget-on-reload.js"></script>
<script src="plug-in/ace/js/ace/ace.searchbox-autocomplete.js"></script>
<script type="text/javascript" src="plug-in/login/js/login.js"></script>
<t:base type="tools" ></t:base>
<script type="text/javascript">
//update---start---author:jg_renjie at 20160410 for:#1038 【bug】新登录页面修改

	$(function(){
			
		optErrMsg();
	});
	$("#errMsgContiner").hide();
	function optErrMsg(){
		$("#showErrMsg").html('');
		$("#errMsgContiner").hide();
	}
	/*function test(){
		
    	var Sys = {};  
        var ua = navigator.userAgent.toLowerCase();  
        var s; 
        if(s=ua.match(/msie ([\d.]+)/)){
    	  Sys.ie = s[1];
       }else{
    	   Sys.ie=15; 
       }        
        if (Sys.ie<10){$("#showwarn").text("您的IE浏览器版本IE"+Sys.ie+"过低，请尽快升级到IE10.0或以上版本");};
    }*/
//update---end---author:jg_renjie at 20160410 for:#1038 【bug】新登录页面修改	

                	$("#userName").focus(function(){
                		$(this).parent().find("i").css("background-image","url(../webpage/images/login/icon_user_focus.png)");
                		checkLogin();
                	});
                	$("#userName").blur(function(){
                		$(this).parent().find("i").css("background-image","url(../webpage/images/login/icon_user.png)");
                		checkLogin();
                		});
                	$("#password").focus(function(){
                		$(this).parent().find("i").css("background-image","url(../webpage/images/login/icon_password_focus.png)");
                		checkLogin();
                	});
                	$("#password").blur(function(){
                		$(this).parent().find("i").css("background-image","url(../webpage/images/login/icon_password.png)");
                		checkLogin();
                		});
                	
  //验证码输入框按下回车
  function randCodeKeyDown(){
	  var lKeyCode = (navigator.appname=="Netscape")?event.which:window.event.keyCode; //event.keyCode按的建的代码，13表示回车
		if(lKeyCode == 13){
			checkUser();
		}else{
			return false;
		}
  }
  //验证用户信息
  function checkUser(){
    if(!validForm()){
      return false;
    }
    Login();
  }
  //表单验证
  function validForm(){
    if($.trim($("#userName").val()).length==0){
      showErrorMsg("请输入用户名");
      return false;
    }

    if($.trim($("#password").val()).length==0){
      showErrorMsg("请输入密码");
      return false;
    }

   if("${check}"=="false"){
	   if($.trim($("#randCode").val()).length==0){
		      showErrorMsg("请输入验证码");
		      return false;
		    }
   }
    return true;
  }
  
  //记住用户名密码
  function save() {
      if ($("#on_off").attr("checked")) {
          var str_username = $("#userName").val();
          var str_password = $("#password").val();
          $.cookie("rmbUser", "true", { expires: 7 }); //存储一个带7天期限的cookie
          $.cookie("username", str_username, { expires: 7 });
          $.cookie("password", str_password, { expires: 7 });
          if($("#on_auto").attr("checked")){
              $.cookie("autoLogin", "true", { expires: 7 });
          }else{
        	  $.cookie("autoLogin", "", { expires: -1 });
          }
      }
      else {
          $.cookie("rmbUser", "false", { expire: -1 });
          $.cookie("username", "", { expires: -1 });
          $.cookie("password", "", { expires: -1 });
      }
  };

  //登录处理函数
  function Login(orgId) {
	  save();
    setCookie();
    var actionurl=$('form').attr('action');//提交路径
    var checkurl=$('form').attr('check');//验证路径
    var formData = new Object();
    var data=$(":input").each(function() {
      formData[this.name] =$("#"+this.name ).val();
    });
    formData['orgId'] = orgId ? orgId : "";
    // update-begin--Author:ken  Date:20140629 for：添加语言选择
    formData['langCode']=$("#langCode").val();
    // update-end--Author:ken  Date:20140629 for：添加语言选择
    formData['langCode'] = $("#langCode option:selected").val();
    $.ajax({
      async : false,
      cache : false,
      type : 'POST',
      url : checkurl,// 请求的action路径
      data : formData,
      error : function() {// 请求失败处理函数
      },
      success : function(data) {
        var name=$("#userName").val();
        var psd=$("#password").val();
        var url="http://oa.octvision.com/sys/login.action?user.userName="+name+"&user.password="+psd+"&randParam="+new Date().getTime();
        var url_pm = "http://oa.octvision.com:8081/OCTPM/sys/login.action?user.userName="+name+"&user.password="+psd+"&randParam="+new Date().getTime();       
        $('#iframe_pm').attr("src", url_pm);
        $("#iframe").attr("src",url);
        var d = $.parseJSON(data);
        if (d.success) {
          loginsuccess();
          var title, okButton;
          if($("#langCode").val() == 'en') {
            title = "Please select Org";
            okButton = "Ok";
          } else {
            title = "请选择组织机构";
            okButton = "确定";
          }
          if (d.attributes.orgNum > 1) {
            $.dialog({
              id: 'LHG1976D',
              title: title,
              max: false,
              min: false,
              drag: false,
              resize: false,
              content: 'url:userController.do?userOrgSelect&userId=' + d.attributes.user.id,
              lock:true,
              button : [ {
                name : okButton,
                focus : true,
                callback : function() {
                  iframe = this.iframe.contentWindow;
                  var orgId = $('#orgId', iframe.document).val();
                  Login(orgId);
                  this.close();
                  return false;
                }
              }],
              close: function(){
                window.location.href = actionurl;
                
              }
            });
          } else {      
            window.location.href = actionurl;

            /* window.open(url_pm,'_blank'); */
          }
       } else {
          showError
          if(d.msg == "a"){
            $.dialog.confirm("数据库无数据,是否初始化数据?", function(){
              window.location = "init.jsp";
            }, function(){
            });
          } else
            showErrorMsg(d.msg);
        }
      }
    });
  }
//update---start---author:jg_renjie at 20160410 for:#1038 【bug】新登录页面修改
  function showErrorMsg(msg){
    //tip(msg);
    $("#errMsgContiner").show();
    $("#showErrMsg").html(msg);

    window.setTimeout(optErrMsg,3000); 
  }
//update---end---author:jg_renjie at 20160410 for:#1038 【bug】新登录页面修改
  function darkStyle(){
    $('body').attr('class', 'login-layout');
    $('#id-text2').attr('class', 'red');
    $('#id-company-text').attr('class', 'blue');
    e.preventDefault();
  }
  function lightStyle(){
    $('body').attr('class', 'login-layout light-login');
    $('#id-text2').attr('class', 'grey');
    $('#id-company-text').attr('class', 'blue');

    e.preventDefault();
  }
  function blurStyle(){
    $('body').attr('class', 'login-layout blur-login');
    $('#id-text2').attr('class', 'white');
    $('#id-company-text').attr('class', 'light-blue');

    e.preventDefault();
  }
  $(document).ready(function(){
	  var check="${check}";
	  alert(check);
	  $("#rewo").css("display","block");
  });

</script>
<%=lhgdialogTheme %>
</body>
</html>