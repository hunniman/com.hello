<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="common/css.jsp" />
<jsp:include page="common/js.jsp" />
<jsp:include page="common/validator.jsp" />
<%-- <jsp:include page="common/jcrop.jsp" /> --%>
<jsp:include page="common/progressbar.jsp" />
<jsp:include page="common/loading.jsp" />

<script src="<%=request.getContextPath()%>/resource/js/hourseDetial.js"></script>
<style type="text/css">
 .img-responsive {
  display: block;
  height: auto;
  max-width: 100%;
}
.bs-example {
	margin-left: 0;
	margin-right: 0;
	background-color: #fff;
	border: 1px solid  #ddd;
	border-radius: 4px 4px 0 0;
	box-shadow: none;
	padding: 5px;
}
</style>
</head>
<body>
	<input type="hidden" id="basePath" value="<%=request.getContextPath()%>"/>
	<input type="hidden" id="publishEmail" value="${hoursePublishInfo.userEmail}"/>
    <jsp:include page="common/header.jsp" />
        <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
        <div class="container">
        </div>
    </div>
    <input type="hidden" id="publishId" name="x">
    <div class="container">
        <div class="row">
              <div class="col-sm-8">
		              <div class="form-group">
		                <div class="col-sm-12">
		                        <div class="jumbotron">
							        <div class="container">
							             <p>${hoursePublishInfo.declare}</p>
							             <c:forEach var="pic" items="${hoursePublishInfo.imgData}">
							               <p><img src="<%=request.getContextPath()%>/${pic}" alt="" class="img-responsive"></p>
							             </c:forEach>
							        </div>
							    </div>
		                </div>
		            </div>
              </div>
              <div class="col-sm-4">
	              <ul class="list-group">
					  <li class="list-group-item">地址：${hoursePublishInfo.address}</li>
					  <li class="list-group-item">${hoursePublishInfo.room} 室      ${hoursePublishInfo.ting} 厅   ${hoursePublishInfo.wei} 卫    总共 ${hoursePublishInfo.scare}㎡    在  ${hoursePublishInfo.totalFloor}/${hoursePublishInfo.totalFloor}楼</li>
					  <li class="list-group-item">租金：${hoursePublishInfo.money} 元/月</li>
					  <li class="list-group-item">联系人：${hoursePublishInfo.contractPeople}   电话：${hoursePublishInfo.phone}</li>
					  <c:if test="${hoursePublishInfo.qq.length()>0}"> <li class="list-group-item"> qq：${hoursePublishInfo.qq}</li> </c:if>
					  <c:if test="${hoursePublishInfo.qq.length()>0}"> <li class="list-group-item"> weixin：${hoursePublishInfo.weixin}</li> </c:if>
					  <li class="list-group-item">发布时间：${hoursePublishInfo.showDay}</li>
					  <c:if test="${pUser.headerImage.length()>0}">
						  <li class="list-group-item">
						     <a href="<%=request.getContextPath()%>/UserHourseList/${hoursePublishInfo.userId}">
							 	 <img  class="img-thumbnail" alt="" src="<%=request.getContextPath()%>/${pUser.headerImage}" style="width: 50px; height: 50px;">
							 	 ${pUser.userName}
						 	 </a>
						  </li>
					  </c:if>
				</ul>
				<div class="bs-example">
				    <ul class="media-list">
				    	<li class="media"> 
				    	 <a class="pull-left" href="#">
				    	 		<img class="img-thumbnail" data-src="holder.js/64x64" alt="" 
				    	 	      <c:choose>
				    	 	         <c:when test="${sessionScope.USER_SESSION==null}">
				    	 	         	src="<%=request.getContextPath()%>/headerImage/default.png"
				    	 	         </c:when>
				              		 <c:otherwise>
				              			 src="<%=request.getContextPath()%>/${sessionScope.USER_SESSION.headerImage}"
				              		 </c:otherwise>
				    	 	      </c:choose>		
				    	 		 style="width: 50px; height:50px;">
				    	  </a>
			              <div class="media-body">
			                 <form id="hourseDetailForm" class="form-horizontal" role="form">
			                        <input type="hidden" name="publishId" value="${hoursePublishInfo.id}"/>
			                 		<textarea id="txtLeaving" rows="5" cols="28" class="form-control" name="txtLeaving" maxLength="500"></textarea>
			                 </form>
			                 <div style="text-align: right;">
				                 <button id="btnLeaving" class="btn btn-default ladda-button" type="button" data-style="expand-right" type="button">
			                          <span class="ladda-label">留言</span>
			                     </button>
			                 </div>
			              </div>
			            </li>
<!-- 			            <div style="max-height:400px;  overflow-y: scroll;"> -->
				            <c:forEach var="back" items="${hoursePublishInfo.feedBackList}">
					            <c:choose>
					               <c:when test="${back.userEmail==hoursePublishInfo.userEmail}">
						                <li class="media well">
							                 <a class="pull-right" href="<%=request.getContextPath()%>/UserHourseList/${back.userId}">
								           		 <img class="img-thumbnail" data-src="holder.js/64x64" alt="64x64" src="<%=request.getContextPath()%>/${back.userHeader}" style="width: 50px; height: 50px;">  
								             </a>
								             <div class="media-body" style="text-align: right;">
								                 ${back.message}
								                 <jsp:useBean id="dateValue" class="java.util.Date"/> 
												 <jsp:setProperty name="dateValue" property="time" value="${back.createTime}"/> 
								                 <p><span class="badge pull-right"><fmt:formatDate value="${dateValue}" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
								             </div>
							            </li>
					               </c:when>
					               <c:otherwise>
							           <li class="media well">
							              <a class="pull-left" href="<%=request.getContextPath()%>/UserHourseList/${back.userId}"> 
							           		 <img class="img-thumbnail" data-src="holder.js/64x64" alt="64x64" src="<%=request.getContextPath()%>/${back.userHeader}" style="width: 50px; height: 50px;"> 
							              </a>
							              <div class="media-body">
							                     ${back.message}
												 <jsp:setProperty name="dateValue" property="time" value="${back.createTime}"/> 
								                 <p><span class="badge pull-left"><fmt:formatDate value="${dateValue}" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
							              </div>
							            </li>
					                </c:otherwise>
					            </c:choose>
				            </c:forEach>
<!-- 			            </div> -->
			        </ul>
				</div>
              </div>
        </div>
    
       
    </div>
    <!-- /container -->

</body>
</html>
