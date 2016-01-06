$(function() {
	    var loading=Ladda.create(document.querySelector('#btnReg'));
		$("#btnReg").bind("click",function(){
		   $('#regForm').bootstrapValidator('validate');
		   if($('#regForm').data('bootstrapValidator').isValid()){
			   loading.start();
			   $.ajax({
			        type: "post",
			        dataType: "json",
			        url: "doSignUp",
			        data: $('#regForm').serialize(),
			        success: function(data) {
			        	loading.stop();
			        	if(data.valid==="success"){
			        		toastr.success('注册成功');
			        	}else{
			        		toastr.error('注册失败');
			        	}
			        },
			        error: function(err) {
			        	loading.stop();
			        	toastr.error('注册失败');
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
	                    },
	                    stringLength: {
	                        min: 6,
	                        max: 30,
	                        message: '密码长度在6~30个字符长度'
	                    },
	                    regexp: {
	                        regexp: /^[a-zA-Z0-9_]+$/,
	                        message: '密码不能包含特殊字符'
	                    }
	                }
	            },
	            textConfirmPwd: {
	                message: '输入的密码无效',
	                validators: {
	                    notEmpty: {
	                        message: '请输入密码'
	                    },
	                    stringLength: {
	                        min: 6,
	                        max: 30,
	                        message: '密码长度在6~30个字符长度'
	                    },
	                    identical: {
	                        field: 'textPwd',
	                        message: '两次输入的密码不相同'
	                    },
	                    regexp: {
	                        regexp: /^[a-zA-Z0-9_]+$/,
	                        message: '密码不能包含特殊字符'
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
	                    },
	                    remote: {
	                        type: 'POST',
	                        url: 'checkEmail',
	                        message: '此邮箱已经被注册了',
	                        delay: 1000
	                    }
	                }
	            }
	        }
	    });
		
	});