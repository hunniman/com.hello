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
<jsp:include page="common/loading.jsp" />

<script src="resource/js/reg.js"></script>
</head>
<body>
	<jsp:include page="common/header.jsp" />
	<div class="jumbotron">
	
	</div>
	<div class="container">
		<form id="regForm" class="form-horizontal" role="form">
			<div class="form-group">
				<label class="col-sm-2 control-label">邮箱</label>
				<div class="col-sm-10">
					<input class="form-control" id="focusedInput" type="text" name="email" placeholder="请输入邮箱...">
				</div>
			</div>
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
<!-- 					<button type="button" id="btnReg" class="btn btn-primary">注册</button> -->
					 <button id="btnReg" class="btn btn-primary ladda-button" data-style="expand-right">
						  <span class="ladda-label">注册</span>
					 </button>
				   </div>
				</div>
			</div>
		</form>
	</div>

</body>
</html>
