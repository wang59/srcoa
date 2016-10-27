<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户信息</title>
<t:base type="jquery,easyui,tools"></t:base>
<%--update-start--Author:jg_renjie  Date:20160320 for：#942 【组件封装】组织机构弹出模式，目前是列表，得改造成树方式--%>
<%--update-start--Author:zhangguoming  Date:20140825 for：添加组织机构combobox多选的处理方法--%>
<script>
<%-- //        update-start--Author:zhangguoming  Date:20140826 for：将combobox修改为combotree
function setOrgIds() {
//    var orgIds = $("#orgSelect").combobox("getValues");
    var orgIds = $("#orgSelect").combotree("getValues");
    $("#orgIds").val(orgIds);
}
$(function() {
    $("#orgSelect").combotree({
        onChange: function(n, o) {
            if($("#orgSelect").combotree("getValues") != "") {
                $("#orgSelect option").eq(1).attr("selected", true);
            } else {
                $("#orgSelect option").eq(1).attr("selected", false);
            }
        }
    });
    $("#orgSelect").combobox("setValues", ${orgIdList});
    $("#orgSelect").combotree("setValues", ${orgIdList});
}); --%>
		function openDepartmentSelect() {
		
			$.dialog.setting.zIndex = 9999; 
			$.dialog({content: 'url:departController.do?departSelect', zIndex: 2100, title: '组织机构列表', lock: true, width: '400px', height: '350px', opacity: 0.4, button: [
			   {name: '<t:mutiLang langKey="common.confirm"/>', callback: callbackDepartmentSelect, focus: true},
			   {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
		   ]}).zindex();
		}
			
		function callbackDepartmentSelect() {
			  var iframe = this.iframe.contentWindow;
			  var treeObj = iframe.$.fn.zTree.getZTreeObj("departSelect");
			  var nodes = treeObj.getCheckedNodes(true);
			  if(nodes.length>0){
			  var ids='',names='';
			  for(i=0;i<nodes.length;i++){
			     var node = nodes[i];
			     ids += node.id+',';
			    names += node.name+',';
			 }
			 $('#departname').val(names);
			 $('#departname').blur();		
			 $('#orgIds').val(ids);	
			 leaderset(nodes[0].id);
			}
		}
		
		function callbackClean(){
			$('#departname').val('');
			 $('#orgIds').val('');	
		}
		function userClean(id){
			$('#'+id).val('');
    		$('#'+id+'2').val('');
		}
		function setOrgIds() {}
		$(function(){
			$("#departname").prev().hide();
		});
		function leaderset(departid)
		{
			$.ajax({
				url : "departController.do?getdepartLeaders",
				data : {
					"departid" : departid					
				},
				dataType : 'json',
				type : 'post',
				success : function(data) {
					var leaders=data.attributes;
					for(var i in leaders)
					{
						var te=leaders[i];
						if(te!=null&&te!=""){
							te=te.split(";");
							$("#"+i).val(te[0]);
							$("#"+i+"2").val(te[1]);
						}
					}
				}
			});
		}
 <%--update-end--Author:zhangguoming  Date:20140825 for：添加组织机构combobox多选的处理方法--%>
    </script>
<%--update-end--Author:jg_renjie  Date:20160320 for：#942 【组件封装】组织机构弹出模式，目前是列表，得改造成树方式--%>
</head>
<body style=" scroll="no">
	<%--update-start--Author:zhangguoming  Date:20140825 for：格式化页面代码 并 添加组织机构combobox多选框--%>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="userController.do?saveUser"
		beforeSubmit="setOrgIds">
		<input id="id" name="id" type="hidden" value="${user.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right" width="25%" nowrap><label
					class="Validform_label"> <t:mutiLang
							langKey="common.username" />:
				</label></td>
				<td class="value" width="85%"><c:if test="${user.id!=null }"> ${user.userName } </c:if>
					<c:if test="${user.id==null }">
						<input id="userName" class="inputxt" name="userName"
							validType="t_s_base_user,userName,id" value="${user.userName }"
							datatype="s2-10" />
						<span class="Validform_checktip"> <t:mutiLang
								langKey="username.rang2to10" /></span>
					</c:if></td>
			</tr>
			<tr>
				<td align="right" width="10%" nowrap><label
					class="Validform_label"> <t:mutiLang
							langKey="common.real.name" />:
				</label></td>
				<td class="value" width="10%"><input id="realName"
					class="inputxt" name="realName" value="${user.realName }"
					datatype="s2-10"> <span class="Validform_checktip"><t:mutiLang
							langKey="fill.realname" /></span></td>
			</tr>
			<c:if test="${user.id==null }">
				<tr>
					<td align="right"><label class="Validform_label"> <t:mutiLang
								langKey="common.password" />:
					</label></td>
					<td class="value"><input type="password" value="123456"
						class="inputxt" value="" name="password" plugin="passwordStrength"
						datatype="*6-18" errormsg="" /> <span class="passwordStrength"
						style="display: none;"> <span><t:mutiLang
									langKey="common.weak" /></span> <span><t:mutiLang
									langKey="common.middle" /></span> <span class="last"><t:mutiLang
									langKey="common.strong" /></span>
					</span> <span class="Validform_checktip"> <t:mutiLang
								langKey="password.rang6to18" /></span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label"> <t:mutiLang
								langKey="common.repeat.password" />:
					</label></td>
					<td class="value"><input id="repassword" class="inputxt"
						value="123456" type="password" value="${user.password}"
						recheck="password" datatype="*6-18" errormsg="两次输入的密码不一致！">
						<span class="Validform_checktip"><t:mutiLang
								langKey="common.repeat.password" /></span></td>
				</tr>
			</c:if>
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang
							langKey="common.department" />:
				</label></td>
				<td class="value">
					<%--update-start--Author:jg_renjie  Date:20160320 for：#942 【组件封装】组织机构弹出模式，目前是列表，得改造成树方式--%>
					<%--update-start--Author:zhangguoming  Date:20140826 for：将combobox修改为combotree--%>
					<%--<select class="easyui-combobox" data-options="multiple:true, editable: false" id="orgSelect" datatype="*">--%>
					<%--<select class="easyui-combotree" data-options="url:'departController.do?getOrgTree', multiple:true, cascadeCheck:false"
                        id="orgSelect" name="orgSelect" datatype="select1">
                update-end--Author:zhangguoming  Date:20140826 for：将combobox修改为combotree
                    <c:forEach items="${departList}" var="depart">
                        <option value="${depart.id }">${depart.departname}</option>
                    </c:forEach>
                </select> --%> <%--  <t:departSelect departId="${tsDepart.id }" departName="${tsDepart.departname }"></t:departSelect>--%>

					<input id="departname" name="departname" type="text"
					readonly="readonly" class="inputxt" datatype="*"
					value="${departname}"> <input id="orgIds" name="orgIds"
					type="hidden" value="${orgIds}"> <a href="#"
					class="easyui-linkbutton" plain="true" icon="icon-search"
					id="departSearch" onclick="openDepartmentSelect()">选择</a> <a
					href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"
					id="departRedo" onclick="callbackClean()">清空</a> <%--update-end--Author:jg_renjie  Date:20160320 for：#942 【组件封装】组织机构弹出模式，目前是列表，得改造成树方式--%>
					<span class="Validform_checktip"><t:mutiLang
							langKey="please.muti.department" /></span>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang
							langKey="common.role" />:
				</label></td>
				<td class="value" nowrap><input name="roleid" name="roleid"
					type="hidden" value="${id}" id="roleid"> <input
					name="roleName" class="inputxt" value="${roleName }" id="roleName"
					readonly="readonly" datatype="*" /> <t:choose hiddenName="roleid"
						hiddenid="id" url="userController.do?roles" name="roleList"
						icon="icon-search" title="common.role.list" textname="roleName"
						isclear="true" isInit="true"></t:choose> <span
					class="Validform_checktip"><t:mutiLang
							langKey="role.muti.select" /></span></td>
			</tr>
			<tr>
				<td align="right" nowrap><label class="Validform_label">
						<t:mutiLang langKey="common.phone" />:
				</label></td>
				<td class="value"><input class="inputxt" name="mobilePhone"
					value="${user.mobilePhone}" datatype="m" errormsg="手机号码不正确"
					> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang
							langKey="common.tel" />:
				</label></td>
				<td class="value"><input class="inputxt" name="officePhone"
					value="${user.officePhone}" datatype="n" errormsg="办公室电话不正确"
					ignore="ignore"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang
							langKey="common.common.mail" />:
				</label></td>
				<td class="value"><input class="inputxt" name="email"
					value="${user.email}" datatype="e" errormsg="邮箱格式不正确!"
					ignore="ignore"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 职位描述:
				</label></td>
				<td class="value"><input class="inputxt" name="post"
					value="${user.post}" datatype="*"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 职级:
				</label></td>
				<td class="value">
			 <select name="postLevel">
			   <option value="1" <c:if test="${user.postLevel=='1' }">selected="selected"</c:if>>普通员工</option>
			   <option value="2" <c:if test="${user.postLevel=='2' }">selected="selected"</c:if>>部长级</option>
			   <option value="3" <c:if test="${user.postLevel=='3' }">selected="selected"</c:if>>中心级</option>
			   <option value="4" <c:if test="${user.postLevel=='4' }">selected="selected"</c:if>>副总级</option>
			   <option value="5" <c:if test="${user.postLevel=='5' }">selected="selected"</c:if>>总经理</option>
			 </select>
			</tr>
			
			
			<tr>
				<td align="right"><label class="Validform_label"> 打卡机号:
				</label></td>
				<td class="value"><input class="inputxt" name="zkem_card"
					value="${user.zkem_card}" datatype="*"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						style: </label></td>
				<td class="value"><input class="inputxt" name="style"
					value="${user.style}"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						外网权限（0、1）: </label></td>
				<td class="value"><input class="inputxt" name="internet"
					value="${user.internet}"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 电脑ip:
				</label></td>
				<td class="value"><input class="inputxt" name="login_ip"
					value="${user.login_ip}"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						is_update_news: </label></td>
				<td class="value"><input class="inputxt" name="is_update_news"
					value="${user.is_update_news}"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						is_view_in_im: </label></td>
				<td class="value"><input class="inputxt" name="is_view_in_im"
					value="${user.is_view_in_im}"> <span
					class="Validform_checktip"></span></td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label"> 个人邮箱:
				</label></td>
				<td class="value"><input class="inputxt" name="personal_email"
					value="${user.personal_email}"> <span
					class="Validform_checktip"></span></td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label">
						pic_url: </label></td>
				<td class="value"><input class="inputxt" name="pic_url"
					value="${user.pic_url}"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						PM职务ID: </label></td>
				<td class="value"><input class="inputxt" name="pm_dutyId"
					value="${user.pm_dutyId}"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 性别: </label></td>
				<td class="value">
					<t:dictSelect field="sex" typeGroupCode="sex" hasLabel="false" defaultVal="${user.sex}"></t:dictSelect>
					
					<span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> qq号:
				</label></td>
				<td class="value"><input class="inputxt" name="qq"
					value="${user.qq }"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 备注: </label></td>
				<td class="value"><input class="inputxt" name="remark"
					value="${user.remark }"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 序号:
				</label></td>
				<td class="value"><input class="inputxt" name="ordernumber" datatype="*"
					value="${user.ordernumber }"> <span class="Validform_checktip"></span></td>
			</tr>
			
			
			<tr>
				<td align="right"><label class="Validform_label">
						部门负责人（副）: </label></td>
				<td class="value"><input class="inputxt" name="leaders"
					id="leader" value="${user.leader.realName }">
					<input class="inputxt" name="leader.id" id="leader2" value="${user.leader.id }" style="display: none;">
					 <a href="#"
					class="easyui-linkbutton" plain="true" icon="icon-search"
					id="departSearch" onclick="openUserSelectSingle('leader','leader2')">选择</a>
					<a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"  onclick="userClean('leader')">清空</a>
					 <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						部门负责人（正）: </label></td>
				<td class="value"><input class="inputxt" name="leader_ids"
					id="leader_id" value="${user.leader_id.realName }"> 
					<input class="inputxt" name="leader_id.id" id="leader_id2" value="${user.leader_id.id }" style="display: none;">
					<a href="#"
					class="easyui-linkbutton" plain="true" icon="icon-search"
					id="departSearch" onclick="openUserSelectSingle('leader_id','leader_id2')">选择</a>
					<a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"  onclick="userClean('leader_id')">清空</a>
					 <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						中心负责人（副）: </label></td>
				<td class="value"><input class="inputxt" name="managers"
					id="manager" value="${user.manager.realName }">
					<input class="inputxt" name="manager.id" id="manager2" value="${user.manager.id }" style="display: none;">
					 <a href="#"
					class="easyui-linkbutton" plain="true" icon="icon-search"
					id="departSearch" onclick="openUserSelectSingle('manager','manager2')">选择</a>
					<a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"  onclick="userClean('manager')">清空</a>
					 <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						中心负责人（正）: </label></td>
				<td class="value"><input class="inputxt" name="manager_ids"
					id="manager_id" value="${user.manager_id.realName }"> 
					<input class="inputxt" name="manager_id.id" id="manager_id2" value="${user.manager_id.id }" style="display: none;">
					<a href="#"
					class="easyui-linkbutton" plain="true" icon="icon-search"
					id="departSearch" onclick="openUserSelectSingle('manager_id','manager_id2')">选择</a> 
					<a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"  onclick="userClean('manager_id')">清空</a>
					<span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 分管经理:
				</label></td>
				<td class="value"><input class="inputxt" name="dep_leaders"
					id="dep_leader" value="${user.dep_leader.realName }">
					<input class="inputxt" name="dep_leader.id" id="dep_leader2" value="${user.dep_leader.id }" style="display: none;">
					 <a
					href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
					id="departSearch" onclick="openUserSelectSingle('dep_leader','dep_leader2')">选择</a>
					<a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"  onclick="userClean('dep_leader')">清空</a>
					 <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 总经理:
				</label></td>
				<td class="value"><input class="inputxt" name="dep_leader_ids"
					id="dep_leader_id" value="${user.dep_leader_id.realName }">
					<input class="inputxt" name="dep_leader_id.id" id="dep_leader_id2" value="${user.dep_leader_id.id }" style="display: none;">
					 <a
					href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
					id="departSearch" onclick="openUserSelectSingle('dep_leader_id','dep_leader_id2')">选择</a>
					<a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"  onclick="userClean('dep_leader_id')">清空</a>
					<span class="Validform_checktip"></span></td>
			</tr>
		</table>
	</t:formvalid>
	
</body>