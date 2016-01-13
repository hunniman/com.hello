<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="common/css.jsp" />
<jsp:include page="common/js.jsp" />
<jsp:include page="common/validator.jsp" />
<jsp:include page="common/jcrop.jsp" />
<jsp:include page="common/progressbar.jsp" />
<script type="text/javascript">
   $(function(){
	   setTimeout(function(){
		   window.location.href="<%=request.getContextPath()%>/index";
	   }, 1000);
   });
</script>
</head>
<body>
	<jsp:include page="common/header.jsp" />
	<div class="jumbotron">
	</div>
	<input id="activityResult" type="hidden" id="x" name="x" value="${result}">
	<div class="container">
	    <c:choose>
	       <c:when test="${result==2}">
	          	  <h2>激活成功，正在跳回首页......</h2>
	       </c:when>
	        <c:when test="${result==1}">
	          	  <h2>此链接已经失效，正在跳回首页......</h2>
	        </c:when>
	       <c:otherwise>
	             <h2>此链接已经失效，正在跳回首页......</h2>
	       </c:otherwise>
	    </c:choose>
		<!-- 主要内容结束 -->
	</div>
</body>
</html>
