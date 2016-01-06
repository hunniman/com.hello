var doActivity=function(){
	var self=this;
	var jcrop_api=null;
	self.initEvent=function(){
		$("#leftSide li").bind("click",function(){
			$("#leftSide li").attr("class","");
			$(this).attr("class","active");
			var name=$(this).attr("name");
			if(name==="headerArea"){
				$("#headerArea").show();
				$("#baseInfoArea").hide();
				$("#pwdArea").hide();
			}else if(name==="baseInfoArea"){
				$("#headerArea").hide();
				$("#baseInfoArea").show();
				$("#pwdArea").hide();
			}else if(name==="pwdArea"){
				$("#headerArea").hide();
				$("#baseInfoArea").hide();
				$("#pwdArea").show();
			}
		});
		
		$("#btnCapture").bind("click",function(){
			self.cutImage();
		});
		$("#btnCaptureCancel").bind("click",function(){
			self.resetOrgiArea();
		});
		$("#updateHeader").bind("click",function(){$("#editorFile").trigger("click");});
		
		$("#btnReg").bind("click",function(){
			   $('#fillInfoForm').bootstrapValidator('validate');
			   if($('#fillInfoForm').data('bootstrapValidator').isValid()){
				   $.ajax({
				        type: "post",
				        dataType: "json",
				        url: "updateBaseInfo",
				        data: $('#fillInfoForm').serialize(),
				        success: function(data) {
				        	if(data.valid==="success")
				        		toastr.success('修改成功');
				        	else
				        		toastr.error('修改失败');
				        },
				        error: function(err) {
				        	toastr.error('修改失败');
				        }
				    });
			   }
		});
		
		$("#btnSavePwd").bind("click",function(){
			   $('#pwdInfoForm').bootstrapValidator('validate');
			   if($('#pwdInfoForm').data('bootstrapValidator').isValid()){
				   $.ajax({
				        type: "post",
				        dataType: "json",
				        url: "updatePassword",
				        data: $('#pwdInfoForm').serialize(),
				        success: function(data) {
				        	if(data.valid==="success")
				        		toastr.success('修改成功');
				        	else
				        		toastr.error('修改失败');
				        },
				        error: function(err) {
				        	toastr.error('修改失败');
				        }
				    });
			   }
		});
		
		
		self.initHeaderUpload();
		self.initValidation();
		self.initPwdValidation();
	};
	
	self.resetOrgiArea=function(){
		$("#orgiHeaderArea").show();
		$("#captureArea").hide();
		$("#rcropArea").empty();
	};
	
	self.initProgress=function(){
		 $('#progress').show();
		 $('.progress .progress-bar').progressbar({
			 display_text: 'center', 
			 use_percentage: false, 
			 amount_format: function(speed) {
				 return  speed +"%";
			 }
		 });
	};
	
	self.initHeaderUpload=function(){
		$("#editorFile").bind("change",function(){
			self.uploadPhoto($(this).val(),"editorFile","headerPhoUpload",function(a){
				self.initHeaderUpload();
				$("#orgiHeaderArea").hide();
				$('#progress').hide();
				
				var jcropArea="<img src="+a+" id='target' />";
				$(jcropArea).appendTo($("#rcropArea"));
				$("#myModal img").attr("src",a);
				$("#target").attr("src",a);
				$("#captureArea").show();
				if(jcrop_api!==null){
					jcrop_api.destroy();
				}
				$("#target").load(function(){
					self.initJcrop();
				});
				toastr.success('上传成功');
			});
		});
		
		
		self.initJcrop=function(){
			  $('#target').Jcrop({
				    onChange: self.updateCoords,
				    aspectRatio: 1,
				    minSize:[100,100],
				    allowResize:false,
				    allowSelect:false
				  },function(){
				      jcrop_api = this;
				      jcrop_api.animateTo([50, 50, 100, 100]);
				  });

		};
		
		
	};
	
	
	self.uploadPhoto=function(fileValue,fileId,url,callBack){
		   var reg=/^.*[^a][^b][^c]\.(?:png|jpg|bmp|gif|jpeg)$/;
		   if(!reg.test(fileValue.toLowerCase())){
			  	toastr.error('上传的图片格式不对!');
		    	return;
		   }
		   self.initProgress();
		   oTimer = setInterval(function(){self.getProgress();}, 100);
		   $.ajaxFileUpload({
				url :url,
				secureuri : false,
				fileElementId :fileId,
				dataType :'text',
				success : function(data, status) {
					window.clearInterval(oTimer);
					callBack(data);
				},
				error : function(data, status, e) {
					toastr.error('系统出错，请稍后再试！');
				}
			});
	};
	
	
	self.getProgress=function() {
		var now = new Date();
	    $.ajax({
	        type: "post",
	        dataType: "json",
	        url: "fileStatus/upfile/progress",
	        data: now.getTime(),
	        success: function(data) {
	            $('.progress .progress-bar').attr("data-transitiongoal",data.percent*100).progressbar();
	        },
	        error: function(err) {
	        	toastr.error('系统出错，请稍后再试！');
	        }
	    });
	};
	 
	
	self.cutImage=function(){
		var x=$("#x").val();
		var y=$("#y").val();
		var w=$("#w").val();
		var h=$("#h").val();
		var fileName=$('#target').attr("src");
		 $.ajax({
		        type: "post",
		        dataType: "text",
		        url: "cutheaderPho",
		        data: {fileName:fileName,x:x,y:y,w:w,h:h},
		        success: function(data) {
		        	if(data==="failed"){
		        		toastr.error('修改成功');
		        		return;
		        	}
		        	toastr.success('修改成功');
		        	$("#headerImg").attr("src",data);
		        	self.resetOrgiArea();
		        },
		        error: function(err) {
		        	toastr.error('修改成功');
		        }
		    });
	};
	self.updateCoords=function(c){
		  $('#x').val(c.x);
		  $('#y').val(c.y);
		  $('#w').val(c.w);
		  $('#h').val(c.h);
	};
	
	self.initValidation=function(){
		$('#fillInfoForm').bootstrapValidator({
	        message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	userName: {
	                message: '输入昵称',
	                validators: {
	                    notEmpty: {
	                        message: '请输入昵称'
	                    },
	                    stringLength: {
	                        min: 1,
	                        max: 30,
	                        message: '昵称长度在1~30个字符长度'
	                    }
	                }
	            },
	            gender: {
                    validators: {
                        notEmpty: {
                            message: ' '
                        }
                    }
	            },
	            contract: {
	                message: '输入联系方式',
	                validators: {
	                    notEmpty: {
	                        message: '输入联系方式'
	                    },
	                    stringLength: {
	                        min: 1,
	                        max: 30,
	                        message: '联系方式长度在1~30个字符长度'
	                    }
	                }
	            },
	            
	        }
	    });
	};
	
	self.initPwdValidation=function(){
		$('#pwdInfoForm').bootstrapValidator({
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
	            }
	        }
	    });
	};
	
};


$(function() {
	var baseInfo=new doActivity();
	baseInfo.initEvent();
});