<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
 <t:base type="jquery,easyui,tools"></t:base>
 
 
<div class="easyui-layout" fit="true">	
			<div region="center" style="padding: 1px;" >
				<t:datagrid name="noticeList" title=""
					actionUrl="noticeController.do?datagrid&type=${type}&noticeType=${ noticeType}" idField="id"
					fit="true" sortName="createTime" sortOrder="desc">
					<t:dgCol title="标题" field="noticeTitle" width="160" frozenColumn="ture" style="color:#23527c;cursor:pointer"></t:dgCol>
					<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
					<t:dgCol title="发布人" field="createUser" width="40"></t:dgCol>
					<t:dgCol title="发布部门" field="createDepart" width="40"></t:dgCol>
					<t:dgCol title="发布时间" field="createTime"
						formatter="yyyy-MM-dd hh:mm" width="40" align="center"></t:dgCol>					
					<t:dgCol title="阅读期限" field="noticeTerm"
						formatter="yyyy-MM-dd " width="40" align="center"></t:dgCol>
					<t:dgCol title="状态" field="isRead" width="40" replace="已读_1,未读_0"></t:dgCol>

				</t:datagrid>
			</div>
		</div>
 <script type="text/javascript" charset="utf-8">
  $('#noticeList').datagrid({   
	    rowStyler:function(index,row){   
	        if (row.isRead!=1){
	            return 'font-weight:bold !important;';   
	        }   
	    }
	});
  //点击新闻标题后，跳转到查看页面
  $("#noticeList").datagrid({
	  onClickCell:function(rowIndex, field, value){
		if(field=="noticeTitle"){
			var rows = $('#noticeList').datagrid('getRows');	
			var data=rows[rowIndex];
			doRead(data.id,data.isRead);
		}
	  }
  });
  //查看新闻公告内容
  function doRead(id,isRead){
	  if(isRead!=1){
		  var url = "noticeController.do?updateNoticeRead";
			$.ajax({
	    		url:url,
	    		type:"GET",
	    		dataType:"JSON",
	    		data:{
	    			noticeId:id
	    		},
	    		success:function(data){
	    			if(data.success){
	    				$('#noticeList').datagrid({   
	    				    rowStyler:function(index,row){   
	    				        if (row.isRead!=1){
	    				            return 'font-weight:bold !important;';   
	    				        }else{
	    				        	return 'font-weight:normal;';
	    				        }
	    				    }   
	    				});
	    			}
	    		}
	    	});
	  }
	  var addurl = "noticeController.do?goNotice&id="+id;
		window.open( addurl);
  }
  
 </script>