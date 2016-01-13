$(function() {
	    var loading=Ladda.create(document.querySelector('#btnLogin'));
		$("#btnLogin").bind("click",function(){
		   $('#loginForm').bootstrapValidator('validate');
		   if($('#loginForm').data('bootstrapValidator').isValid()){
			   loading.start();
			   $.ajax({
			        type: "post",
			        dataType: "json",
			        url: "doLogin",
			        data: $('#loginForm').serialize(),
			        success: function(data) {
			        	loading.stop();
			        	if(data.valid==="success"){
			        		toastr.success('成功登陆');
			        		   setTimeout(function(){
			        			   window.location.href="index";
			        		   }, 500);
			        	}else{
			        		toastr.error('账号或密码不对');
			        	}
			        },
			        error: function(err) {
			        	loading.stop();
			        	toastr.error('账号或密码不对');
			        }
			    });
		   }else{
			   loading.stop();
		   }
		});
		
		$('.form-horizontal').bootstrapValidator({
	        message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	textPwd: {
	                message: '输入的密码无效',
	                validators: {
	                    notEmpty: {
	                        message: '请输入密码'
	                    }
	                }
	            },
	            email: {
	                validators: {
	                    notEmpty: {
	                        message: '请输入邮箱'
	                    },
	                    emailAddress: {
	                        message: '输入的邮箱格式无效'
	                    }
	                }
	            }
	        }
	    });
		
	});