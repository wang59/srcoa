<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<%--非当前组织机构的用户列表--%>

<t:base type="jquery,easyui,tools"></t:base>
	<script type="text/javascript">
		function openUserSelect() {
		   $.dialog.setting.zIndex = 9999; 
		   $.dialog({content: 'url:userController.do?userselectauto', zIndex: 2105, title: '用户列表', lock: true, width: '827px', height: '690px', opacity: 0.4, button: [
		      {name: '<t:mutiLang langKey="common.confirm"/>', callback: callbackUserSelect, focus: true},
		      {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
		   ]}).zindex();
		}
		
		function callbackUserSelect() {
		var iframe = this.iframe.contentWindow;		
		var rowsData = iframe.$('#userList1').datagrid('getSelections');
		var realname=rowsData[0].realName;
		var username=rowsData[0].userName;
		$('#realname').val(realname);
		$('#username').val(username);
		$('#user').blur();
		}
		</script>
		</head>
<body  style="overflow-x: hidden" scroll="no">
<table>
  <tr>
    <td>协办接收人：</td>
    <td><input type="text" id="realname"><button onclick="openUserSelect()">选择</button><br>
        <input type="text" id="username" style="display: none;"><br></td>
  </tr>
  <tr>
   <td>协办原因：</td>
   <td><textarea rows="3" cols="80" id="comment"></textarea></td>
  </tr>
</table>
 </body>      
						
