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

<script src="resource/js/doActivity.js"></script>
</head>
<body>
	<jsp:include page="common/header.jsp" />
	<div class="jumbotron">
	
	</div>
	
	<div class="container">
	    <c:choose>
	       <c:when test="${userInfo.email!=null}">
	         	<form id="fillInfoForm" class="form-horizontal" role="form">
	         		<div class="form-group">
	         			<div class="col-sm-2"></div>
						<div class="col-sm-3">
						<div class="col-sm-5">
							<img data-src="holder.js/200x200" class="img-thumbnail" alt="我是头像" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDIwMCAyMDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjwhLS0KU291cmNlIFVSTDogaG9sZGVyLmpzLzIwMHgyMDAKQ3JlYXRlZCB3aXRoIEhvbGRlci5qcyAyLjYuMC4KTGVhcm4gbW9yZSBhdCBodHRwOi8vaG9sZGVyanMuY29tCihjKSAyMDEyLTIwMTUgSXZhbiBNYWxvcGluc2t5IC0gaHR0cDovL2ltc2t5LmNvCi0tPjxkZWZzPjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+PCFbQ0RBVEFbI2hvbGRlcl8xNTFmMDIxMzFmMCB0ZXh0IHsgZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQgfSBdXT48L3N0eWxlPjwvZGVmcz48ZyBpZD0iaG9sZGVyXzE1MWYwMjEzMWYwIj48cmVjdCB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9Ijc1LjUiIHk9IjEwNC41Ij4yMDB4MjAwPC90ZXh0PjwvZz48L2c+PC9zdmc+" data-holder-rendered="true" style="width: 80px; height: 80px;">
						</div>
						<div class="col-sm-2">
							<div style="margin-top:60px;">
								<span class="label label-primary" data-toggle="modal" data-target="#myModal">修改</span>
							</div>
						</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">昵称</label>
						<div class="col-sm-10">
							<input class="form-control" id="focusedInput" type="text" name="userName" placeholder="请输昵称...">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">性别</label>
						<div class="col-lg-5">
							<div class="radio">
								<label> <input type="radio" name="gender" value="0" />
									男
								</label>
								<label> <input type="radio" name="gender" value="1" />
									女
								</label>
								<label> <input type="radio" name="gender" value="2" />
									其他
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">
							联系方式</label>
						<div class="col-sm-10">
							<input class="form-control" id="contract" type="text" name="contract" placeholder="该输入联系方式..." >
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-10">
						</div>
						<div class="col-sm-2">
						   <div style="text-align: right;">
							<button type="button" id="btnReg" class="btn btn-primary">保存基础信息</button>
						   </div>
						</div>
					</div>
				</form>
	       </c:when>
	       <c:otherwise>
	            <h2>已经激活过了</h2>
	       </c:otherwise>
	    </c:choose>
    
<!-- 		<form id="regForm" class="form-horizontal" role="form"> -->
<!-- 			<div class="form-group"> -->
<!-- 				<label class="col-sm-2 control-label">邮箱</label> -->
<!-- 				<div class="col-sm-10"> -->
<!-- 					<input class="form-control" id="focusedInput" type="text" name="email" placeholder="请输入邮箱..."> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="form-group"> -->
<!-- 				<label for="inputPassword" class="col-sm-2 control-label"> -->
<!-- 					密码</label> -->
<!-- 				<div class="col-sm-10"> -->
<!-- 					<input class="form-control" id="disabledInput" type="password" name="textPwd" placeholder="该输入密码..." > -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="form-group"> -->
<!-- 				<label for="inputPassword" class="col-sm-2 control-label"> -->
<!-- 					确认密码</label> -->
<!-- 				<div class="col-sm-10"> -->
<!-- 					<input class="form-control" id="disabledInput" type="password" name="textConfirmPwd" placeholder="该确认密码..." > -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="form-group"> -->
<!-- 				<div class="col-sm-10"> -->
<!-- 				</div> -->
<!-- 				<div class="col-sm-2"> -->
<!-- 				   <div style="text-align: right;"> -->
<!-- 					<button type="button" id="btnReg" class="btn btn-primary">注册</button> -->
<!-- 					<a href="#" class="btn btn-primary ladda-button" data-style="expand-left"> -->
<!-- 						  <span class="ladda-label">expand-left</span> -->
<!-- 					</a>  -->
<!-- 				   </div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</form> -->


		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
					</div>
					<div class="modal-body">
						<div class="row">
<!-- 							<div class="col-md-8"> -->
<!-- 						    	<img src="cacheUpload/èµå®èç¿-æç»ç¨¿1.jpg" id="target" alt="[Jcrop Example]" width=200px; height=200px; > -->
<!-- 						    </div> -->
<!-- 					        <div id="preview-pane" class="col-md-4"> -->
<!-- <!-- 					          <div class="preview-container"> --> -->
<!-- 					            <img src="cacheUpload/èµå®èç¿-æç»ç¨¿1.jpg" class="jcrop-preview" alt="Preview" width=100px; height=100px;> -->
<!-- <!-- 					          </div> --> -->
<!-- 					        </div> -->
				        </div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							关闭</button>
						<button type="button" class="btn btn-primary">提交更改</button>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
		        <div class="col-md-8">
		        	<img src="cacheUpload/eee.png" id="target" alt="[Jcrop Example]" width=200px; height=200px; >
		        </div>
		        <div class="col-md-4">
		        	 <img src="cacheUpload/eee.png" id="targetPre" class="jcrop-preview" alt="Preview" width=100px; height=100px;>
		        </div>
		 </div>

				        
	</div>

</body>
</html>
