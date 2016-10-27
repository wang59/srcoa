//返回例如：http://127.0.0.1:8080
function getRoot(){
	return document.getElementById('_oct_js').src.replace("/js/oct.js", "");
}

/**
 * 功能：获取URL的参数
 * @param name，URL中参数的名字
 *  作者：汪旭军
 * 日期：2016-10-18
 */
function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var url=unescape( window.location.search);
	var r =url.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
	} 
/**
 * 功能：显示上传附件的控件
 * @param iframe_id，附件iframe的ID；
 * @param UUID_field_id，当前表单中UUID的字段的名字，用于和附件记录关联。
 * @param file_area_id，标识显示第几个上传附件的控件；
 * @param isFileEdit，标识是上传还是查看附件。
 *  作者：汪旭军
 * 日期：2016-10-18
 */
function showFile(iframe_id,UUID_field_id,file_area_id,isFileEdit){
	var attrURL = sys_fileServer + '/oct-attachment/upload.jsp?';
	attrURL = attrURL + 'userName=' + sys_curRealName + '&isEdit=' + isFileEdit + '&parentId=' + $('#'+UUID_field_id).val() + '&fileAreaId='+file_area_id;
	$('#'+iframe_id).attr('src', attrURL);
}
function openUserSelect(_ids) {//组织机构选接收人
    $.dialog.setting.zIndex = 9999;
    $.dialog({
		content: 'url:'+getRoot()+'/userController.do?getOrganizationPersion&_ids='+_ids,
        zIndex: 5000,
        title: '用户列表',
        lock: true,
        width: '1026px',
        height: '690px',
        opacity: 0.4,
        button: [{
            name: '确定',
			callback: function(){
				
				var result=this.iframe.contentWindow.callbackUserSelect(_ids);
				window.document.getElementById('usersName').value=result.names;
			},
            focus: true
        },{
            name: '取消',
            callback: function() {}
        }]
    }).zindex();
}
//关闭当前页面。
function closeWin(){
	window.opener= null;
	window.open("","_self"); 
	window.close();
}
//---------------------------- 自动补全功能开始---------------------------------------------------
function sys_parse(data){
    	var parsed = [];
        	$.each(data.rows,function(index,row){
        		parsed.push({data:row,result:row,value:row.id});
        	});
				return parsed;
}
/**
 * 选择后回调，给相应的字段赋值。 
 * 
 * @param {Object} data
 */
function sys_callBack(data) {
	    $.each( data, function(fieldId, fieldValue){
	  if(fieldId != "state"){
		  $("#"+fieldId).val(fieldValue);
	  }
	});
}

 /**
  * 每一个选择项显示的信息,下拉框数据的显示样式
  * 
  * @param {Object} data
  */
function sys_formatItem(data) {
	  var rtn="";
	  $.each( data, function(fieldId, fieldValue){
		  if(fieldId != "state")
		  	 if(rtn==""){
		  		 rtn=fieldValue;
		  	 }else{
		  		 rtn=rtn + "-->" +fieldValue;
		  	 }
		});
	return rtn;
}
//---------------------------- 自动补全功能结束---------------------------------------------------

/*
 * 功能：将所有input的name中含有.的字段设置为disable,name中含有param.和.id的除外。
 * 作者：汪旭军
 * 日期：2016-10-11
 */
function intputSetDisabled(){
	//自定义表单的域初始状态设置为distabled;
	var allInput=$(":input");
	$.each( allInput, function(i, n){
  		if(n.name.indexOf(".")>0 && n.name.indexOf("param.")==-1 && n.name.toLowerCase().indexOf(".id")==-1){
      		$(n).attr("disabled",true);
 		}
	});
}
/*
 * 功能：设置隐藏属性,可传递多个id值，id值用^隔开。
 * 作者：xiejz
 * 日期：2016-10-18
 */
function setHide(id){
	var arr = id.split("^");
	$.each(arr, function(i, n){
		$("#"+arr[i]).attr("style","display: none;");
	});
}
/**
 * 功能：单个用户选择弹出框,
 * 作者：xiejz
 * 日期：2016-10-26
 * @param realname,id realname:保存用户姓名，id:用户账号id
 */
function openUserSelectSingle(realname,id) {
	   $.dialog.setting.zIndex = 9999; 
	   $.dialog({content: 'url:userController.do?userselectauto', zIndex: 2105, title: '用户列表', lock: true, width: '830px', height: '600px', opacity: 0.4, button: [
	      {name: '确定', callback: function(){
	    		var iframe = this.iframe.contentWindow;		
	    		var rowsData = iframe.$('#userList1').datagrid('getSelections');
	    		var names=rowsData[0].realName;
	    		var ids=rowsData[0].id;
	    		$('#'+realname).val(names);
	    		$('#'+id).val(ids);
	      }, focus: true},
	      {name: '关闭', callback: function (){}}
	   ]}).zindex();
	}
/**
 * 功能：多个用户选择弹出框,
 * 作者：xiejz
 * 日期：2016-10-26
 * @param realname,id realnames:保存用户姓名，ids:用户账号id
 */
function openUserSelectMulti(realnames,ids){
	$.dialog.setting.zIndex = 9999; 
	$.dialog({content: 'url:userController.do?getOrganizationPersion&userNames', zIndex: 2500, title: '用户列表', lock: true, width: '1000px', height: '600px', opacity: 0.4, button: [
	{name: '确定', callback: function(){
		var iframe = this.iframe.contentWindow;		
		var select=iframe.$('#user_select option');
		var realNames="";
		var userNames="";	
		for(var i=0;i<select.length;i++){
			userNames=userNames+select[i].value+";";
			realNames=realNames+select[i].text+";";
		}
		$('#'+realnames).val(realNames);
		$('#'+ids).val(userNames);//用户id		
	}, focus: true},
	{name: '取消', callback: function (){}}
	]}).zindex();	
}

 
/**
 * 功能：清除选择的用户,
 * 作者：xiejz
 * 日期：2016-10-26
 * @param realname,id
 */
function userClean(realname,id){
	$('#'+realname).val('');
	$('#'+id).val('');
}
