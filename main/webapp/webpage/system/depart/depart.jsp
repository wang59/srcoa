<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>部门信息</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
function userClean(id){
	$('#'+id).val('');
	$('#'+id+'2').val('');
}
	$(function() {
	
		$('#cc').combotree({
			url : 'departController.do?setPFunction&selfId=${depart.id}',
            width: 155,
            onSelect : function(node) {
//                alert(node.text);
                changeOrgType();
            }
        });
        if(!$('#cc').val()) { // 第一级，只显示公司选择项
            var orgTypeSelect = $("#orgType");
            var companyOrgType = '<option value="1" <c:if test="${orgType=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.company"/></option>';
            orgTypeSelect.empty();
            orgTypeSelect.append(companyOrgType);
        } else { // 非第一级，不显示公司选择项
            $("#orgType option:first").remove();
        }
        if($("#id").val()) {
            $('#cc').combotree('disable');
        }
        if('${empty pid}' == 'false') { // 设置新增页面时的父级
            $('#cc').combotree('setValue', '${pid}');
        }
	});
    function changeOrgType() { // 处理组织类型，不显示公司选择项
        var orgTypeSelect = $("#orgType");
        var optionNum = orgTypeSelect.get(0).options.length;
        if(optionNum == 1) {
            $("#orgType option:first").remove();
            var bumen = '<option value="2" <c:if test="${orgType=='2'}">selected="selected"</c:if>><t:mutiLang langKey="common.department"/></option>';
            var gangwei = '<option value="3" <c:if test="${orgType=='3'}">selected="selected"</c:if>><t:mutiLang langKey="common.position"/></option>';
            orgTypeSelect.append(bumen).append(gangwei);
        }
    }

</script>
</head>
<body style=" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="systemController.do?saveDepart">
	<input id="id" name="id" type="hidden" value="${depart.id }">
	<fieldset class="step">
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="common.department.name"/>: </label>
            <input name="departname" class="inputxt" type="text" value="${depart.departname }"  datatype="s1-20">
            <span class="Validform_checktip"><t:mutiLang langKey="departmentname.rang1to20"/></span>
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="position.desc"/>: </label>
            <input name="description" class="inputxt" value="${depart.description }">
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="parent.depart"/>: </label>
            <input id="cc" name="TSPDepart.id" value="${depart.TSPDepart.id}">
        </div>
        <div class="form">
            <input type="hidden" name="orgCode" value="${depart.orgCode }">
            <label class="Validform_label"> <t:mutiLang langKey="common.org.type"/>: </label>
            <select name="orgType" id="orgType">
                <option value="1" <c:if test="${orgType=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.company"/></option>
                <option value="2" <c:if test="${orgType=='2'}">selected="selected"</c:if>><t:mutiLang langKey="common.department"/></option>
                <option value="3" <c:if test="${orgType=='3'}">selected="selected"</c:if>><t:mutiLang langKey="common.position"/></option>
                <option value="4" <c:if test="${orgType=='4'}">selected="selected"</c:if>><t:mutiLang langKey="common.position"/></option>
            </select>
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="common.mobile"/>: </label>
            <input name="mobile" class="inputxt" value="${depart.mobile }">
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="common.fax"/>: </label>
            <input name="fax" class="inputxt" value="${depart.fax }">
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="common.address"/>: </label>
            <input name="address" class="inputxt" value="${depart.address }" datatype="s1-50">
            <span class="Validform_checktip"><t:mutiLang langKey="departmentaddress.rang1to50"/></span>
        </div>
        <div class="form">
         <label class="Validform_label"> im组织机构类型</label>
            <input name="type" class="inputxt" value="${depart.type }">
        </div>
        <div class="form">
          <label class="Validform_label"> 部门状态</label>
            <input name="status" class="inputxt" value="${depart.status }">
        </div>
        <div class="form">
         <label class="Validform_label"> pm相关</label>
            <input name="pm_duty_flag" class="inputxt" value="${depart.pm_duty_flag }">
        </div>
        
          <div class="form">
         <label class="Validform_label"> 排序</label>
            <input name="ordernumber" class="inputxt" datatype="*" value="${depart.ordernumber }">
        </div>
        
        <div class="form">
         <label class="Validform_label"> 部门负责人(副)</label>
            <input name="leaders" class="inputxt" id="leader" value="${depart.leader.realName }">
            <input class="inputxt" name="leader.id" id="leader2" value="${depart.leader.id }" style="display: none;">
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
					id="departSearch" onclick="openUserSelectSingle('leader','leader2')">选择</a>
					<a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"  onclick="userClean('leader')">清空</a>
        </div>
        <div class="form">
         <label class="Validform_label"> 部门负责人(正)</label>
            <input name="leader_ids" class="inputxt" id="leader_id" value="${depart.leader_id.realName }">
            	<input class="inputxt" name="leader_id.id" id="leader_id2" value="${depart.leader_id.id }" style="display: none;" >
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
					id="departSearch" onclick="openUserSelectSingleSingle('leader_id','leader_id2')">选择</a>
						<a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"  onclick="userClean('leader_id')">清空</a>
        </div>
        <div class="form">
         <label class="Validform_label"> 中心负责人(副)</label>
            <input name="managers" class="inputxt" id="manager" value="${depart.manager.realName }">
            	<input class="inputxt" name="manager.id" id="manager2" value="${depart.manager.id }" style="display: none;">
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
					id="departSearch" onclick="openUserSelectSingle('manager','manager2')">选择</a>
						<a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"  onclick="userClean('manager')">清空</a>
        </div>
        <div class="form">
         <label class="Validform_label"> 中心负责人(正)</label>
            <input name="manager_ids" class="inputxt" id="manager_id" value="${depart.manager_id.realName }">
            <input class="inputxt" name="manager_id.id" id="manager_id2" value="${depart.manager_id.id }" style="display: none;">
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
					id="departSearch" onclick="openUserSelectSingle('manager_id','manager_id2')">选择</a>
							<a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"  onclick="userClean('manager_id')">清空</a>
        </div>
        <div class="form">
        <label class="Validform_label"> 分管经理</label>
            <input name="dep_leaders" class="inputxt" id="dep_leader" value="${depart.dep_leader.realName }">
            <input class="inputxt" name="dep_leader.id" id="dep_leader2" value="${depart.dep_leader.id }" style="display: none;">
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
					id="departSearch" onclick="openUserSelectSingle('dep_leader','dep_leader2')">选择</a>
						<a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"  onclick="userClean('dep_leader')">清空</a>
        </div>
        <div class="form">
         <label class="Validform_label"> 总经理</label>
            <input name="dep_leader_ids" class="inputxt" id="dep_leader_id" value="${depart.dep_leader_id.realName }">
            <input class="inputxt" name="dep_leader_id.id" id="dep_leader_id2" value="${depart.dep_leader_id.id }" style="display: none;">
            <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
					id="departSearch" onclick="openUserSelectSingle('dep_leader_id','dep_leader_id2')">选择</a>
					<a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"  onclick="userClean('dep_leader_id')">清空</a>
        </div>
        
	</fieldset>
</t:formvalid>
</body>
</html>
