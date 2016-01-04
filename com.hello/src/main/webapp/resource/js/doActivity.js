$(function() {
	var jcrop_api=null;
	
	$("#leftSide li").bind("click",function(){
		$("#leftSide li").attr("class","");
		$(this).attr("class","active");
		var name=$(this).attr("name");
		if(name==="headerArea"){
			$("#headerArea").show();
			$("#baseInfoArea").hide();
		}else if(name==="baseInfoArea"){
			$("#headerArea").hide();
			$("#baseInfoArea").show();
		}
	});
	
	$("#btnCapture").bind("click",function(){
		cutImage();
	});
	$("#btnCaptureCancel").bind("click",function(){
		resetOrgiArea();
	});
	
	var resetOrgiArea=function(){
		$("#orgiHeaderArea").show();
		$("#captureArea").hide();
		$("#rcropArea").empty();
	};
	
	
	var initProgress=function(){
		 $('#progress').show();
		 $('.progress .progress-bar').progressbar({
			 display_text: 'center', 
			 use_percentage: false, 
			 amount_format: function(speed) {
				 return  speed +"%";
			 }
		 });
	};
	
	var initHeaderUpload=function(){
		$("#editorFile").bind("change",function(){
			uploadPhoto($(this).val(),"editorFile","headerPhoUpload",function(a){
				initHeaderUpload();
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
					initJcrop();
				});
			});
		});
	};
	
	$("#updateHeader").bind("click",function(){$("#editorFile").trigger("click");});
	
	
	
	var uploadPhoto=function(fileValue,fileId,url,callBack){
		   var reg=/^.*[^a][^b][^c]\.(?:png|jpg|bmp|gif|jpeg)$/;
		   if(!reg.test(fileValue.toLowerCase())){
			  	alert("上传的图片格式不对!","");
		    	return;
		   }
		   initProgress();
		   oTimer = setInterval(function(){getProgress();}, 100);
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
	            $('.progress .progress-bar').attr("data-transitiongoal",data.percent*100).progressbar();
	        },
	        error: function(err) {
	        	alert("系统出错，请稍后再试！","");
	        }
	    });
	};
	 
	
	var initJcrop=function(){
		  $('#target').Jcrop({
			    onChange: updateCoords,
			    onSelect: updateCoords,
			    aspectRatio: 1,
			    minSize:[100,100],
			    allowResize:false,
			    allowSelect:false
			  },function(){
			      jcrop_api = this;
			      jcrop_api.animateTo([50, 50, 100, 100]);
			  });

	};
	
	function updateCoords(c){
		  $('#x').val(c.x);
		  $('#y').val(c.y);
		  $('#w').val(c.w);
		  $('#h').val(c.h);
	};
	


	var cutImage=function(){
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
		        		alert("更新失败！");
		        		return;
		        	}
		        	$("#headerImg").attr("src",data);
		        	resetOrgiArea();
		        },
		        error: function(err) {
		        	alert(err);
		        }
		    });
	};
	
	 
	  
	  
		$("#btnReg").bind("click",function(){
		   $('#regForm').bootstrapValidator('validate');
		   if($('#regForm').data('bootstrapValidator').isValid()){
			   $.ajax({
			        type: "post",
			        dataType: "json",
			        url: "doSignUp",
			        data: $('#regForm').serialize(),
			        success: function(data) {
			        	$("#headerImg").attr("src",data);
			        },
			        error: function(err) {
			        	alert(err);
			        }
			    });
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
		
		initHeaderUpload();
	});