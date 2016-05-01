<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="common/css.jsp" />
<jsp:include page="common/js.jsp" />
<jsp:include page="common/loading.jsp" />
<script type="text/javascript">
$(function(){
	var oTimer=null;
	$("#editorFile").on("change",function(){ uploadPhoto($(this).val(),"editorFile","fileUpload",function(a){alert(a);});});
	var uploadPhoto=function(fileValue,fileId,url,callBack){
		   var reg=/^.*[^a][^b][^c]\.(?:png|jpg|bmp|gif|jpeg)$/;
		   if(!reg.test(fileValue.toLowerCase())){
			  	alert("上传的图片格式不对!","");
		    	return;
		   }
		   oTimer = setInterval(function(){getProgress();}, 500);
// 		   $.jBox.tip("正在加载...","loading"); 
		   $.ajaxFileUpload({
				url :url,
				secureuri : false,
				fileElementId :fileId,
				dataType :'text',
				success : function(data, status) {
// 					$.jBox.closeTip();
					window.clearInterval(oTimer);
					callBack(data);
				},
				error : function(data, status, e) {
// 					$.jBox.closeTip();
					alert("系统出错，请稍后再试！","");
				}
			});
	};
	
	
	var getProgress=function() {
		var now = new Date();
	    $.ajax({
	        type: "post",
	        dataType: "json",
	        url: "fileStatus/upfile/progress",
	        data: now.getTime(),
	        success: function(data) {
	        	$("#progress_percent").text(data.percent);
	            $("#progress_bar").width(data.percent);
	            $("#has_upload").text(data.mbRead);
	            $("#upload_speed").text(data.speed);
	        },
	        error: function(err) {
	        	$("#progress_percent").text("Error");
	        }
	    });
	}
	
	$("#codeRefresh").bind("click",function(){
		$(this).find("img").attr("src","generateCharCode?"+Math.random());
	});
	
	Ladda.bind('div:not(.progress-demo) button');
	
});

</script>
</head>
<body>
	<jsp:include page="common/header.jsp" />
		<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<div class="container">
		</div>
	</div>
	
	<div class="container">
		<!-- Example row of columns -->
		<c:set var="count" value="0"/>
		<c:set var="index" value="0"/>
		<c:forEach var="hourse" items="${list}">
	           <c:set value="${count + 1}" var="count" />  
	           <c:set value="${index + 1}" var="index" />  
	           <c:if test="${count==1}">
	            	 <div class="row">
	           </c:if>
	           
	           <div class="col-md-4">
	                 <div style="max-height: 300px; overflow-y:hidden;">
						  <a href="HourseDetail/${hourse.id}" target="_blank">
						  	<img  width="300px"  src="${hourse.imgData[0]}">
<%-- 						    <span class="badge pull-left">${hourse.imgData.size()}</span> --%>
						  </a>
	                 </div>
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
<!-- 					     <div style="float:right;padding-right: 50px;"> -->
<!-- 						      <span class=" glyphicon glyphicon-envelope" aria-hidden="true">4</span> -->
<!-- 						      <span class=" glyphicon glyphicon-eye-open" aria-hidden="true">4</span> -->
<!-- 						  </div> -->
				     </div>
				 </div>
			     <c:if test="${count!=3&&index==list.size()}">
			     	<hr>
			       </div>
			     </c:if>
				 <c:if test="${count==3}">
	           		<c:set value="0" var="count" />  
	             	</div>
	             	<hr>
	           	 </c:if>
	    </c:forEach>
	</div>
	<!-- /container -->

</body>
</html>
