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
<jsp:include page="common/loading.jsp" />
<script src="resource/js/doActivity.js"></script>

<style type="text/css">
.bs-docs-sidebar .nav>.active:focus>a, .bs-docs-sidebar .nav>.active:hover>a, .bs-docs-sidebar .nav>.active>a {
padding-left: 18px;
font-weight: 700;
color: #563d7c;
background-color: transparent;
border-right: 2px solid #563d7c;
}

</style>
</head>
<body>
	<jsp:include page="common/header.jsp" />
	<div class="jumbotron">
	</div>
	<div class="container">
<%-- 	    <c:choose> --%>
<%-- 	       <c:when test="${userInfo!=null&&userInfo.email!=null}"> --%>
	       
	       <div class="row">
	          <div class="col-sm-3">
						<nav class="bs-docs-sidebar hidden-print hidden-xs hidden-sm affix">
						            <ul  class="nav bs-docs-sidenav">
						               	 <li>
											  <ul id="leftSide" class="nav">
											    <li class="active" name="baseInfoArea"><a href="#">基本信息</a></li>
											    <li class="" name="headerArea"><a href="#">头像信息</a></li>
											    <li class="" name="pwdArea"><a href="#">修改密码</a></li>
											  </ul>
										 </li>
						            </ul>
						 </nav>
	          </div>
	          <div class="col-sm-9">
	                <div id="headerArea" style="display:none;">
	                         <div class="row" id="orgiHeaderArea">
								<div class="col-sm-3">
									<table class="table">
									   <tr>
									      <td align="center">
									      	<img id="headerImg" data-src="holder.js/200x200" class="img-thumbnail" alt="我是头像" src="${sessionScope.USER_SESSION.headerImage}" data-holder-rendered="true" style="width: 100px; height: 100px;">
									      </td>
									   </tr>
									   <tr>
									      <td align="center">
									      	<span id="updateHeader" class="label label-primary">修改</span>
											<input type="file" id="editorFile" name="editorFile" style="display:none;"/>
									      </td>
									   </tr>
									</table>
								</div>
								<div class="col-sm-7">
									<div id="progress"  style="display:none;">
								       	<div class="progress" style="margin-top:60px;">
								    	 	<div class="progress-bar progress-bar-success" role="progressbar" data-transitiongoal="0" ></div>
									   </div>
									</div>
								</div>
	                         </div>
	                         <div class="row" id="captureArea" style="display:none;"> 
							        <div id="rcropArea" class="col-md-8">
<!-- 							        	<img src="" id="target" alt="[Jcrop Example]"  > -->
							        </div>
<!-- 							    <div class="col-md-4" > -->
<!-- 							      	  	<div style="width:100px;height:100px;overflow:hidden;"> -->
<!-- 							        	 	<img src="" id="preview" class="jcrop-preview" alt="Preview"> -->
<!-- 							            </div> -->
<!-- 							     </div> -->
							         <input type="hidden" id="x" name="x">
							         <input type="hidden" id="y" name="y">
							         <input type="hidden" id="w" name="w">
							         <input type="hidden" id="h" name="h">
									<button id="btnCapture" type="button" class="btn btn-primary">确定</button>
									<button id="btnCaptureCancel" type="button" class="btn btn-primary">取消</button>
							 </div>
	              </div>
	             
	             <div id="baseInfoArea" >
		              <form id="fillInfoForm" class="form-horizontal" role="form">
											<div class="form-group">
												<label class="col-sm-2 control-label">昵称</label>
												<div class="col-sm-10">
													<input class="form-control" id="focusedInput" type="text" name="userName" placeholder="请输昵称..." value="${sessionScope.USER_SESSION.userName}">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-2 control-label">性别</label>
												<div class="col-lg-5">
													<div class="radio">
														<label> <input type="radio" name="gender" value="0" <c:if test="${sessionScope.USER_SESSION.gender==0}">checked="checked"</c:if> />
															男
														</label>
														<label> <input type="radio" name="gender" value="1" <c:if test="${sessionScope.USER_SESSION.gender==1}">checked="checked"</c:if>/>
															女
														</label>
														<label> <input type="radio" name="gender" value="2" <c:if test="${sessionScope.USER_SESSION.gender==2}">checked="checked"</c:if>/>
															其他
														</label>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">
													联系方式</label>
												<div class="col-sm-10">
													<input class="form-control" id="contract" type="text" name="contract" placeholder="该输入联系方式..." value="${sessionScope.USER_SESSION.contract}">
												</div>
											</div>
											<div class="form-group">
												<div class="col-sm-10">
												</div>
												<div class="col-sm-2">
												   <div style="text-align: right;">
													<button type="button" id="btnReg" class="btn btn-primary">修改基础信息</button>
												   </div>
												</div>
											</div>
								  </form>
	            		 </div>
	            		 <!-- 基础的信息结束 -->
	            		 
	            		<div id="pwdArea" style="display:none;">
		              		   <form id="pwdInfoForm" class="form-horizontal" role="form">
											<div class="form-group">
												<label for="inputPassword" class="col-sm-2 control-label">
													密码</label>
												<div class="col-sm-10">
													<input class="form-control" id="disabledInput" type="password" name="textPwd" placeholder="该输入密码..." >
												</div>
											</div>
											<div class="form-group">
												<label for="inputPassword" class="col-sm-2 control-label">
													确认密码</label>
												<div class="col-sm-10">
													<input class="form-control" id="disabledInput" type="password" name="textConfirmPwd" placeholder="该确认密码..." >
												</div>
											</div>
											<div class="form-group">
												<div class="col-sm-10">
												</div>
												<div class="col-sm-2">
												   <div style="text-align: right;">
													<button type="button" id="btnSavePwd" class="btn btn-primary">修改密码</button>
												   </div>
												</div>
											</div>
								  </form>
	            		 </div>
	            		 <!-- 密码模块结束 -->
	          		</div>
	       </div>
<%-- 	       </c:when> --%>
<%-- 	        <c:when test="${userInfo==null}"> --%>
<!-- 	          	<h2>此链接已经失效</h2> -->
<%-- 	        </c:when> --%>
<%-- 	       <c:otherwise> --%>
<!-- 	            <h2>已经激活过了</h2> -->
<%-- 	       </c:otherwise> --%>
<%-- 	    </c:choose> --%>
		<!-- 主要内容结束 -->
	</div>























   
   
</body>
</html>
