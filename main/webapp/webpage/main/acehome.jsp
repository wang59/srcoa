<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--360浏览器优先以webkit内核解析-->
    <title>华侨城文旅科技</title>
    <link rel="shortcut icon" href="favicon.ico"> <link href="plug-in-ui/hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/font-awesome.css?v=4.4.0" rel="stylesheet">
	<link rel="stylesheet" href="plug-in/ace/assets/css/fullcalendar.css" />
	<link rel="stylesheet" href="plug-in/ace/assets/css/ace.min.css" />
	<link rel="stylesheet" href="plug-in/ace/assets/css/ace-rtl.min.css" />
	<link rel="stylesheet" href="plug-in/ace/assets/css/ace-skins.min.css" />
    <link href="plug-in-ui/hplus/css/animate.css" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="style/common.css" rel="stylesheet">
    <!-- 全局js -->
	<script src="plug-in/chart/Chart.bundle.js"></script>
	<script src="plug-in-ui/hplus/js/jquery.min.js?v=2.1.4"></script>
	<script src="plug-in-ui/hplus/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>
	<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>
	<script src="assets/js/typeahead-bs2.min.js"></script>
	<!-- page specific plugin scripts -->
	<script src="plug-in/ace/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="plug-in/ace/assets/js/jquery.ui.touch-punch.min.js"></script>
	<script src="plug-in/ace/assets/js/fullcalendar.min.js"></script>
	<script src="plug-in/ace/assets/js/bootbox.min.js"></script>
	<!-- ace scripts -->
	<script src="plug-in/ace/assets/js/ace-elements.min.js"></script>
	<script src="plug-in/ace/assets/js/ace.min.js"></script>
	<script src="plug-in/tools/curdtools_zh-cn.js"></script>
    <style>
    #news-msg li,#notice-msg li{
    	display:none;
    }
    .nav-tabs h2{
    	cursor:pointer;
    }
    .col-xs-12, .col-sm-12, .col-md-12, .col-lg-12{
    float:none;
    }
    .panel-body>ul>li>a{
    width:75%;
    white-space: nowrap;
    overflow: hidden; 
    text-overflow:ellipsis;
    display:inline-block;
    line-hight:1;
    }
    .tab-content>.tab-pane{
    width:90%;
    position:relative;
    margin:10px auto;
    }
    .dashboard-header{
    padding:20px 20px 0px 20px;
    }
    
@media screen and (max-width: 1440px) {
    .mywork>.col-md-6{
        width:49%;
    }
}
    </style>

</head>

<body class="gray-bg">
    <div class="row  border-bottom white-bg dashboard-header">
    		<div class="col-md-7 plan">
            	<ul class="nav nav-tabs" role="tablist">
               		<li role="presentation" class="active">
               			<a href="#home" aria-controls="home" role="tab" data-toggle="tab">
               				<h2 onclick="addOneTab('等待审批', 'toDoController.do?toDoList')"></h2>
               				<p class="wait-img wait-img-pass"></p>
               			</a>
               		</li>
                	<li role="presentation">
                		<a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">
                			<h2 onclick="addOneTab('等待阅读', 'copyToController.do?copyToList')"></h2>
                			<p class="wait-img wait-img-read"></p>
                		</a>
                	</li>
                	<li role="presentation"><a href="#onaylanmamSize" aria-controls="onaylanmamSize" role="tab" data-toggle="tab" onclick="addOneTab('PM任务待审', 'http://oa.octvision.com:8081/OCTPM/pages/project/handTaskFrame.jsp?typeFlag=1')"><h2 id="onaylanmamSize">0</h2><p class="wait-img wait-img-pm"></p></a></li>
                	<!-- onclick="addOneTab('PM任务待办', 'http://192.168.1.11:8081/OCTPM/pages/project/handTaskFrame.jsp?typeFlag=0')" -->
                	<li role="presentation">
                		<a onclick="addOneTab('PM任务待办', 'http://oa.octvision.com:8081/OCTPM/pages/project/handTaskFrame.jsp?typeFlag=0')" aria-controls="unfinishedSize" role="tab" data-toggle="tab"><h2 id="unfinishedSize">0</h2><p class="wait-img wait-img-work"></p></a>
                		<!-- <a class="about-more pull-right" href="noticeController.do?newsList" target="_blank"><img src="webpage/images/main/main_more.png"/></a> -->
                	</li>
            	</ul>

            <!-- Tab panes -->
            	<div class="tab-content">
                	<div role="tabpanel" class="tab-pane active" id="home">
                		<div class="canvas-body">
                			<div class="canvas-wrapper">
                				<div id="canvas-holder" style="width:316px">
       					 			<canvas id="chart-area"></canvas>
  								</div>
                			</div>
                			<div class="canvas-detail">
                				<span><i class="icon-red"></i>已过期</span>
                				<span><i class="icon-blue"></i>待处理</span>
                			</div>
                		</div>
                		
  						<div class="cav-table">
  							<table class="table" id="cav-table">
  								<tr>
  									<td>状态</td>
  									<td>标题</td>
  									<td>创建人</td>
  									<td>创建时间</td>
  								</tr>
  								
  							</table>
  						</div>
                	</div>
                	<div role="tabpanel" class="tab-pane" id="profile">
                		
                		<div class="cav-table">
  							<table class="table" id="cav-table2">
  								<tr>
  									<td>状态</td>
  									<td>标题</td>
  									<td>创建人</td>
  									<td>创建时间</td>
  								</tr>
  								
  							</table>
  						</div>
					</div>
                	<div role="tabpanel" class="tab-pane" id="messages">...</div>
                	<div role="tabpanel" class="tab-pane" id="settings">...</div>
            	</div>
        	</div>
		<div class="col-md-5 plan my-work">
			<div class="panel panel-default">
				<div class="panel-heading">
					<img src="webpage/images/main/icon-state-work.png" class="main_box_title"/>
				</div>
				<div class=" mywork">
					<div class="col-md-6">
						<a id="work-time" target="_blank">
						<span>
							<i></i>
							<h2>我的考勤</h2>
							<p></p>
						</span>
						</a>
						
					</div>
					<div class="col-md-6">
						<a id="work-perfo" href="http://oa.octvision.com/performance/showUserPerformanceMonth.action" target="_blank">
						<span>
							<i></i>
							<h2>我的绩效</h2>
							<p></p>
						</span>
						</a>
					</div>
					<div class="col-md-6">
						<a id="work-test"   target="_blank">
						<span>
							<i></i>
							<h2>我的考试</h2>
							<p></p>
						</span>
						</a>
					</div>
					<div class="col-md-6">
						<a id="work-money" href="http://oa.octvision.com/salaryManage/showSalary.action"  target="_blank">
						<span>
							<i></i>
							<h2>我的财务</h2>
							<p></p>
						</span>
						</a>
					</div>
				</div>
			</div>
		</div>
		
        <div class="col-md-7" id="news-msg">
            <div class="panel panel-default">
                <div class="panel-heading">
                	<div class="main-heading-title"><img src="webpage/images/main/main_news.png" class="main_box_title"/></div>
                	 <div class="has-border-bottom">
                	 	 <a class="about-more pull-right" onclick="addOneTab('新闻消息','noticeController.do?newsList')" target="_blank"><img src="webpage/images/main/main_more.png"/></a>
                	 </div>
                	
                </div>
                <div class="panel-body" id='news-msg'>
                    <ul>
                       
                    </ul>
                </div>
            </div>
        </div>

        <div class="col-md-5 " id="notice-msg">
            <div class="panel panel-default">
            	<div class="panel-heading">
                	<div class="main-heading-title"><img src="webpage/images/main/main_ann.png" class="main_box_title"/></div>
                	 <div class="has-border-bottom">
                	 	 <a class="about-more pull-right" onclick="addOneTab('公告通知','noticeController.do?noticeList')" target="_blank"><img src="webpage/images/main/main_more.png"/></a>
                	 </div>
                	
                </div>
                
                <div class="panel-body">
                    <ul>
                        
                    </ul>
                </div>
            </div>
        </div>

        <div class="col-md-7 " id="bbs-msg">
            <div class="panel panel-default">
            	<div class="panel-heading">
                	<div class="main-heading-title"><img src="webpage/images/main/main_topic.png" class="main_box_title"/></div>
                	 <div class="has-border-bottom">
                	 	 <a class="about-more pull-right" onclick="addOneTab('讨论话题','http://192.168.1.11:81/')"  target="_blank"><img src="webpage/images/main/main_more.png"/></a>
                	 </div>
                	
                </div>
               
                <div class="panel-body">
                  <ul>
                    </ul>
                </div>
            </div>
        </div>

        <div class="col-md-5 " id="doc-msg">
            <div class="panel panel-default">
            	<div class="panel-heading">
                	<div class="main-heading-title"><img src="webpage/images/main/main_doc.png" class="main_box_title"/></div>
                	 <div class="has-border-bottom">
                	 	 <!-- href="http://oa.octvision.com/pages/document/PublicFileIndex.jsp" target="_blank" -->
                	 	 <a class="about-more pull-right" onclick="addOneTab('知识文档','sharedFileController.do?onlinelist')" ><img src="webpage/images/main/main_more.png"/></a>
                	 </div>
                	
                </div>
                <div class="panel-body">
                    <ul>
                    </ul>
                </div>
            </div>
        </div>


    </div>

	<script>
	function getLocalTime(nS) {     
	       return new Date(parseInt(nS) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");      
	    }  
		jQuery(function($) {
			$.ajax({
				type : "POST",
				url : "oldUserController.do?findCheckWork",
				success : function(data) {	
					var time=new Date(data.obj);
					if(data.success){
						$("#work-time p").html(data.obj+"打卡");
					}else{
						$("#work-time p").html("您今天未打卡");
						$("#work-time p").css("color","#cf0000");
					}
					$("#work-time").attr('href',"http://oa.octvision.com/attendance/showMyAttendance.action?userId="+data.msg);
					$("#work-test").attr('href',"http://oa.octvision.com/examination/examinationPlanJoin.action?examinationJoin.oaUser.id="+data.msg);
					if(data.attributes.check){
						$("#work-time p").css("color","#cf0000");
					}else{
						$("#work-time p").css("color","#ffffff");
					}
				},
				dataType : "json"
	})
			//环形图显示
			$.ajax({
						type : "POST",
						url : "toDoController.do?getOutDueDate",
						success : function(data) {	
							var data=data.attributes,
								overdate=data.some-0,
								readyTodo=(data.all-overdate!=0)?(data.all-overdate):1;
							
							var config = {
									type : 'doughnut',
									data : {
										datasets : [ {
											data : [ readyTodo, overdate ],
											backgroundColor : [ "#46BFBD",
													"#F7464A" ],
										} ],
										labels : [ "待处理", "已过期" ]
									},
									options : {
										responsive : true
									}
								};
							
							var ctx = document.getElementById("chart-area").getContext("2d");
							window.myPie = new Chart(ctx, config);
						},
						dataType : "json"
			})
			
			//环形图显示
			//等待审批显示
			$.ajax({
						type : "POST",
						url : "toDoController.do?todogrid&field=id,actTaskBusiness.businessId,actTaskBusiness.businessTitle,actTaskBusiness.businessCreateName,createTime,tSBaseUser.realName,&rows=5&order=desc&sort=createTime",
						success : function(data) {
							
							toDoRead();//待办执行成功，在执行待阅读显示，避免冲突。
							
							var finished = 0, unfinished = 0, tatal = data.total, data = data.rows;
							$("a[href='#home'] h2").text(tatal);//等待审批条数
							for (var i = 0; i < 4; i++) {
								var addContent = "<tr><td><i class='icon-blue'></i></td><td><a target='blank' href='workFlowAutoFormController.do?newapproval&id="
										+ data[i]['id']
										+ "'>"
										+ data[i]['actTaskBusiness.businessTitle']
										+ "</a></td><td>"
										+data[i]['actTaskBusiness.businessCreateName']
										+"</td><td>"
										+ data[i].createTime.substr(0, 16)
										+ "</td></tr>";
								$("#cav-table").append(addContent);
							}
						},
						dataType : "json"
					});

			//待阅读显示,因为待阅读显示和待办如果同时调优的话，2者有时会冲突导致待办的失败，所有改为一个函数，放在待办里面执行。 2016-09-29 汪旭军
			function toDoRead() {
				$
						.ajax({
							type : "POST",
							url : "copyToController.do?copyTogrid&field=id,createtime,user.realName,actTaskBusiness.businessTitle,status,&order=desc&sort=createtime&rows=4&status=0",
							success : function(data) {
								filedo();
								var finished = 0, unfinished = 0, tatal = data.total, data = data.rows;
								$("a[href='#profile'] h2").text(tatal);
								for (var i = 0; i < 4; i++) {
									var addContent = "<tr><td><i class='icon-blue'></i></td><td><a target='blank' href='copyToController.do?editapp&id="
											+ data[i]['id']
											+ "'>"
											+ data[i]['actTaskBusiness.businessTitle']
											+ "</a></td><td>"
											+ data[i]['user.realName']
											+ "</td><td>"
											+ data[i].createtime.substr(0, 16)
											+ "</td></tr>";
									$("#cav-table2").append(addContent);
								}

							},
							dataType : "json"
						});
			}
			//获取文档信息
			function filedo(){
				
				$
						.ajax({
							type : "post",
							datatype : "json",
							url : "sharedFileController.do?documentList&type=1&field=id,docname,createTime",
							success : function(data) {
								var obj = data;
								if (obj.rows) {
										var arr = obj.rows;									
										if (arr && 0 < arr.length) {
											var g = arr.length;
											for (var i = 0; i < g; i++) {
												var d = arr[i].createTime.split(' ')[0];
												var title = $.trim(arr[i].docname);
												if (title.length >= 25)//判断P里面字符串的长度是否大于15
												{
													var a = title.substr(0, 25);
													title = a + "...";
												}

												$('#doc-msg')
														.find('ul')
														.append(
																'<li><a href=sharedFileController.do?viewFile&fileid='+arr[i].id
																		+ ' target="_blank">'
																		+ title
																		+ '</a><span class="date pull-right">['
																		+ d
																		+ ']</span></li>');
											}
											$('#doc-msg li').hide();
											$(
													'#doc-msg li:eq(0),#doc-msg li:eq(1),#doc-msg li:eq(2)')
													.show();
											var j = 3;
											setInterval(
													function() {
														$('#doc-msg li').fadeOut(
																400);
														setTimeout(
																function() {
																	$(
																			'#doc-msg li:eq('
																					+ j
																					% g
																					+ '),'
																					+ '#doc-msg li:eq('
																					+ (j + 1)
																					% g
																					+ '),'
																					+ '#doc-msg li:eq('
																					+ (j + 2)
																					% g
																					+ ')')
																			.fadeIn(
																					400);
																}, 500);

														j += 3;
													}, 8000);
										}
								}
							}
						});
			}
			//待阅读显示结束
			//获取新闻消息
			$
					.ajax({
						type : "post",
						datatype : "json",
						url : "noticeController.do?getNoticeList&type=3",
						success : function(data) {

							var data = eval('[' + data + ']')[0].attributes.noticeList, g = data.length;
							for (var i = 0; i < data.length; i++) {
								$('#news-msg')
										.find('ul')
										.append(
												'<li><a target="_blank" href="noticeController.do?goNotice&id='
														+ data[i].id
														+ '\">'
														+ data[i].noticeTitle
														+ '</a><span class="date pull-right">['
														+ data[i].createtime
														+ ']</span></li>');
							}
							$(
									'#news-msg li:eq(0),#news-msg li:eq(1),#news-msg li:eq(2)')
									.show();
							var j = 3;
							setInterval(function() {
								$('#news-msg li').fadeOut(400);
								setTimeout(function() {
									$(
											'#news-msg li:eq(' + j % g + '),'
													+ '#news-msg li:eq('
													+ (j + 1) % g + '),'
													+ '#news-msg li:eq('
													+ (j + 2) % g + ')')
											.fadeIn(400);
								}, 500);

								j += 3;
							}, 8000);
						}
					});
			//获取新闻消息

			//获取公告消息
			$
					.ajax({
						type : "post",
						datatype : "json",
						url : "noticeController.do?getNoticeList&type=2",
						success : function(data) {

							var data = eval('[' + data + ']')[0].attributes.noticeList, g = data.length;
							for (var i = 0; i < data.length; i++) {
								$('#notice-msg')
										.find('ul')
										.append(
												'<li><a target="_blank" href="noticeController.do?goNotice&id='
														+ data[i].id
														+ '\">'
														+ data[i].noticeTitle
														+ '</a><span class="date pull-right">['
														+ data[i].createtime
														+ ']</span></li>');
							}
							$(
									'#notice-msg li:eq(0),#notice-msg li:eq(1),#notice-msg li:eq(2)')
									.show();
							var j = 3;
							setInterval(function() {
								$('#notice-msg li').fadeOut(400);
								setTimeout(function() {
									$(
											'#notice-msg li:eq(' + j % g + '),'
													+ '#notice-msg li:eq('
													+ (j + 1) % g + '),'
													+ '#notice-msg li:eq('
													+ (j + 2) % g + ')')
											.fadeIn(400);
								}, 500);

								j += 3;
							}, 8000);
						}
					});
			//获取公告消息

			//获取论坛信息
			$
					.ajax({
						type : "post",
						datatype : "json",
						url : "corsController.do?acehomeBbs",
						success : function(data) {
							var obj = $.parseJSON(data);
							if (obj.success) {
								var val = obj.success;
								if (val) {
									var arr = val.ls;
									if (arr && 0 < arr.length) {
										var g = arr.length;
										for (var i = 0; i < g; i++) {
											var subject = $
													.trim(arr[i].subject);
											if (subject && 25 < subject.length) {
												var ject = subject
														.substr(0, 25);
												subject = ject + '...';
											}
											$('#bbs-msg')
													.find('ul')
													.append(
															'<li><a target="_blank" onclick="addOneTab(\''+subject.substring(0,6)+'\',\'http://192.168.1.11:81/forum.php?mod=viewthread&tid='
																	+ arr[i].tid
																	+ '&extra=page%3D1\')">'
																	+ subject
																	+ '</a><span class="date pull-right">['
																	+ arr[i].dateline
																	+ ']</span></li>');
										}
										$('#bbs-msg li').hide();
										$(
												'#bbs-msg li:eq(0),#bbs-msg li:eq(1),#bbs-msg li:eq(2)')
												.show();
										var j = 3;
										setInterval(
												function() {
													$('#bbs-msg li').fadeOut(
															400);
													setTimeout(
															function() {
																$(
																		'#bbs-msg li:eq('
																				+ j
																				% g
																				+ '),'
																				+ '#bbs-msg li:eq('
																				+ (j + 1)
																				% g
																				+ '),'
																				+ '#bbs-msg li:eq('
																				+ (j + 2)
																				% g
																				+ ')')
																		.fadeIn(
																				400);
															}, 500);

													j += 3;
												}, 8000);
									}
								}
							}
						}
					});

			

			/*
			   获取PM任务待办/待审数目
			 */
				$.ajax({
				type:"post",
				datatype:"json",
				url:"corsController.do?queryPmSize",
				success : function(data){
					if(data){
						var obj = $.parseJSON(data);
						if(obj.success){
							obj = $.parseJSON(obj.success);
							$('#unfinishedSize').text(obj.pmSize[0].unfinishedSize);
							$('#onaylanmamSize').text(obj.pmSize[0].onaylanmamSize);
						}
					}
				}
			}); 

			/* initialize the external events
				-----------------------------------------------------------------*/

			$('#external-events div.external-event').each(function() {

				// create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
				// it doesn't need to have a start or end
				var eventObject = {
					title : $.trim($(this).text())
				// use the element's text as the event title
				};

				// store the Event Object in the DOM element so we can get to it later
				$(this).data('eventObject', eventObject);

				// make the event draggable using jQuery UI
				$(this).draggable({
					zIndex : 999,
					revert : true, // will cause the event to go back to its
					revertDuration : 0
				//  original position after the drag
				});

			});

			/* initialize the calendar
			-----------------------------------------------------------------*/

			var date = new Date();
			var d = date.getDate();
			var m = date.getMonth();
			var y = date.getFullYear();

			var calendar = $('#calendar')
					.fullCalendar(
							{
								height : '350',
								buttonText : {
									prev : '<i class="icon-chevron-left"></i>',
									next : '<i class="icon-chevron-right"></i>'
								},

								header : {
									left : 'prev,next today',
									center : 'title',
									right : 'month,agendaWeek,agendaDay'
								},
								monthNames : [ "一月", "二月", "三月", "四月", "五月",
										"六月", "七月", "八月", "九月", "十月", "十一月",
										"十二月" ],
								monthNamesShort : [ "一月", "二月", "三月", "四月",
										"五月", "六月", "七月", "八月", "九月", "十月",
										"十一月", "十二月" ],
								dayNames : [ "星期日", "星期一", "星期二", "星期三", "星期四",
										"星期五", "星期六" ],
								dayNamesShort : [ "星期日", "星期一", "星期二", "星期三",
										"星期四", "星期五", "星期六" ],
								buttonText : {
									prev : "<span class='fc-text-arrow'>&lsaquo;</span>",
									next : "<span class='fc-text-arrow'>&rsaquo;</span>",
									prevYear : "<span class='fc-text-arrow'>&laquo;</span>",
									nextYear : "<span class='fc-text-arrow'>&raquo;</span>",
									today : '今天',
									month : '月',
									week : '周',
									day : '天'
								},

								editable : true,
								droppable : true, // this allows things to be dropped onto the calendar !!!
								drop : function(date, allDay) { // this function is called when something is dropped

									// retrieve the dropped element's stored Event Object
									var originalEventObject = $(this).data(
											'eventObject');
									var $extraEventClass = $(this).attr(
											'data-class');

									// we need to copy it, so that multiple events don't have a reference to the same object
									var copiedEventObject = $.extend({},
											originalEventObject);

									// assign it the date that was reported
									copiedEventObject.start = date;
									copiedEventObject.allDay = allDay;
									if ($extraEventClass)
										copiedEventObject['className'] = [ $extraEventClass ];

									// render the event on the calendar
									// the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
									$('#calendar').fullCalendar('renderEvent',
											copiedEventObject, true);

									// is the "remove after drop" checkbox checked?
									if ($('#drop-remove').is(':checked')) {
										// if so, remove the element from the "Draggable Events" list
										$(this).remove();
									}

								},
								selectable : true,
								selectHelper : true,
								select : function(start, end, allDay) {

									bootbox.prompt("New Event Title:",
											function(title) {
												if (title !== null) {
													calendar.fullCalendar(
															'renderEvent', {
																title : title,
																start : start,
																end : end,
																allDay : allDay
															}, true // make the event "stick"
													);
												}
											});

									calendar.fullCalendar('unselect');

								},
								eventClick : function(calEvent, jsEvent, view) {

									var form = $("<form class='form-inline'><label>Change event name &nbsp;</label></form>");
									form
											.append("<input class='middle' autocomplete=off type=text value='" + calEvent.title + "' /> ");
									form
											.append("<button type='submit' class='btn btn-sm btn-success'><i class='icon-ok'></i> Save</button>");

									var div = bootbox
											.dialog({
												message : form,

												buttons : {
													"delete" : {
														"label" : "<i class='icon-trash'></i> Delete Event",
														"className" : "btn-sm btn-danger",
														"callback" : function() {
															calendar
																	.fullCalendar(
																			'removeEvents',
																			function(
																					ev) {
																				return (ev._id == calEvent._id);
																			})
														}
													},
													"close" : {
														"label" : "<i class='icon-remove'></i> Close",
														"className" : "btn-sm"
													}
												}

											});

									form.on('submit', function() {
										calEvent.title = form.find(
												"input[type=text]").val();
										calendar.fullCalendar('updateEvent',
												calEvent);
										div.modal("hide");
										return false;
									});
									// change the border color just for fun
									//$(this).css('border-color', 'red');

								}

							});

		});
		$(function() {
			setTimeout(function() {
				if (!$("#cav-table tr:nth-child(1)")) {
					window.location.reload();
				}
			}, 300)
		});
	</script>

    <!-- 自定义js -->
    <script src="plug-in-ui/hplus/js/content.js"></script>

    <!-- 欢迎信息 -->
    <script src="plug-in-ui/hplus/js/welcome.js"></script>
</body>

</html>
