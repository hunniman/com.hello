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
                      <c:choose>
                        <c:when test="${list.size()<=0}">
                           	<h3>这家伙什么也没有发布喔~~~</h3>
                        </c:when>
                        <c:otherwise>
	              
			             		<c:set var="count" value="0"/>
								<c:set var="index" value="0"/>
								<c:forEach var="hourse" items="${list}">
							           <c:set value="${count + 1}" var="count" />  
							           <c:set value="${index + 1}" var="index" />  
							           <c:if test="${count==1}">
							            	 <div class="row">
							           </c:if>
							           <div class="col-md-6" style="border:1px solid #08080">
											 <a href="<%=request.getContextPath()%>/HourseDetail/${hourse.id}" target="_blank">
											  	<img width="300px" height="200px" id="21099071" src="<%=request.getContextPath()%>/${hourse.imgData[0]}" alt="  ">
											  </a>
							 				 <h4>${hourse.title}</h4>
										     <p>${hourse.address}</p>
										     <p>${hourse.room}房${hourse.ting}厅      ${hourse.scare}平方</p>
										     <jsp:useBean id="myDate" class="java.util.Date"/> 
										     <p>电话  ：${hourse.phone} </p>
										     <c:if test="${hourse.qq.length()>0}">
										     	 <p>qq:${hourse.qq}</p>
										     </c:if>
										     <c:if test="${hourse.weixin.length()>0}">
										     	 <p>微信:${hourse.weixin}</p>
										     </c:if>
										     <p>发布时间: ${hourse.showDay}</p>
										     <div>
											     <div style="float:left;"><span class="label label-info">${hourse.money} 元/月</span></div>
										     </div>
										 </div>
									     <c:if test="${count!=2&&index==list.size()}">
									       </div>
									       <hr>
									     </c:if>
										 <c:if test="${count==2}">
							           		<c:set value="0" var="count" />  
							             	</div>
							             	<hr>
							           	 </c:if>
							    </c:forEach>
				              </div>
				              <div class="col-sm-4">
					              <ul class="list-group">
					              	 <li class="list-group-item active">${pUser.userName}发布的内容</li>
										  <li class="list-group-item ">
											  <c:if test="${pUser.headerImage.length()>0}">
												 	 <img  class="img-thumbnail" alt="" src="<%=request.getContextPath()%>/${pUser.headerImage}" style="width: 50px; height: 50px;">
											  </c:if>
											      联系方式：${pUser.contract}
										  </li>
								</ul>
				              </div>
                        </c:otherwise>
                      </c:choose>
    
    <!-- /container -->

</body>
</html>
