<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<%--非当前组织机构的用户列表--%>

<t:base type="jquery,easyui,tools"></t:base>
<body style="overflow-x: hidden" scroll="no">
 <p id="p3" style="display: none;">${param.procInstId }</p>             
    <c:if test="${fn:length(activiti)<=0}"> 无可退回节点</c:if>
        <c:if test="${fn:length(activiti)>0}"> 
                              退回节点:<select id="activityId" name="activityId">
                      <c:forEach var="activity" items="${activiti }">                    
                         <option value="${activity.key }">${activity.value }</option>                                            
                      </c:forEach>       
               </select>
               <br>
                              退回原因:<textarea rows="3" cols="100" id="comment"></textarea>        
        </c:if>
 </body>       
				
