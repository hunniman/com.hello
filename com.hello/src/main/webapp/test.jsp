<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
			<h1>Hello, world!</h1>
			<p>This is a template for a simple marketing or informational
				website. It includes a large callout called a jumbotron and three
				supporting pieces of content. Use it as a starting point to create
				something more unique.</p>
			<p>
				<a class="btn btn-primary btn-lg" href="#" role="button">Learn
					more &raquo;</a>
			</p>
		</div>
	</div>
	
	<div class="container">
		<!-- Example row of columns -->
		<div class="row">
			<div class="col-md-4" style="border:1px solid #08080">
				<img data-src="holder.js/400x400" alt="200x200" src="http://advertise.shijue.cvidea.cn/tf/151218/2417340/567369be3dfae975d8000001.JPEG" data-holder-rendered="true" style="width:360px;" >
				<p> <h5> 天河德埔小区 业主自主精装小两房首次出租 拎包即入住 </h5></p>
				<p>地點</p>
				<p>2室1厅1卫/50㎡/5/8层/精装修/东南向/今天</p>
				<span class="label label-info">3000元/月</span>
				<div style="float:right;">
					  <span class=" glyphicon glyphicon-envelope" aria-hidden="true">4</span>
					  <span class=" glyphicon glyphicon-eye-open" aria-hidden="true">4</span>
				</div>
			</div>
		<div class="col-md-4" style="border:1px solid #08080">
				<img data-src="holder.js/400x400" alt="200x200" src="http://advertise.shijue.cvidea.cn/tf/151217/2417191/56720c573dfae9252a000001.JPEG" data-holder-rendered="true" style="width:360px;">
				<p>Donec id elit non mi porta gravida at eget metus. Fusce
					dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
					ut fermentum massa justo sit amet risus. Etiam porta sem malesuada
					magna mollis euismod. Donec sed odio dui.</p>
			</div>
		<div class="col-md-4" style="border:1px solid #08080">
				<img data-src="holder.js/400x400" alt="200x200" src="http://advertise.shijue.cvidea.cn/tf/151218/2417341/56736d303dfae928a2000001.JPEG" data-holder-rendered="true" style="width:360px;">
				<p>Donec id elit non mi porta gravida at eget metus. Fusce
					dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
					ut fermentum massa justo sit amet risus. Etiam porta sem malesuada
					magna mollis euismod. Donec sed odio dui.</p>
			</div>
		</div>
		<hr class="featurette-divider">
		
		<input type="file" id="editorFile" name="editorFile" />
		
    	<ul>
          	<li>
              	<div class="process clearfix" id="process">
				<span class="progress-box">
					<span class="progress-bar" style="width: 0%;" id="progress_bar"></span>
				</span>
                      <span id="progress_percent">0%</span>
                  </div>
                  <div class="info" id="info">已上传：<span id="has_upload">0</span>MB  速度：<span id="upload_speed">0</span>KB/s</div>
                  <div class="info" id="success_info" style="display: none;"></div>
              </li>
          </ul>

      <c:forEach var="user" items="${list}">
           ${user.email}
      </c:forEach>
      
      <button class="ladda-button" data-style="expand-right">
		  <span class="ladda-label">Submit</span>
	  </button>

      <br>
        <a id="codeRefresh" href="javascript:void(0);"><img alt="" src="generateCharCode"></a>
		<footer>
		<p>&copy; Company 2014</p>
		</footer>
	</div>
	<!-- /container -->

</body>
</html>
