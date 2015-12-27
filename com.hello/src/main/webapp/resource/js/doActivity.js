$(function() {
	
	var i=0;
	
	 $('.progress .progress-bar').progressbar({
		 display_text: 'center', 
		 use_percentage: false, 
		 amount_format: function(speed,p) {return '速度: ' + speed + '   已上传: ' + speed }
	 });
	 
	
	var int= setInterval(function(){
		 $('.progress .progress-bar').attr("data-transitiongoal",i+=10).progressbar();
		 if(i>=100){
			 window.clearInterval(int);
		 }
	 },1000);
	 
	 $('.progress .progress-bar').update(a,b)
	 
	 
	
	var jcrop_api,
	  boundx,
	  boundy;
	  
	  // Grab some information about the preview pane
	  
//	  console.log('init',[xsize,ysize]);

	
	  $('#target').Jcrop({
	    onChange: showPreview,
	    onSelect: showPreview,
	    aspectRatio: 1,
	    minSize:[100,100],
	    allowResize:false,
	    allowSelect:false
	  },function(){
	      jcrop_api = this;
	      jcrop_api.animateTo([50, 50, 100, 100]);
	  });
	  
	  var $preview = $('#preview');
	  function showPreview(coords)
	  {
	    if (parseInt(coords.w) > 0)
	    {
	      var rx = 100 / coords.w;
	      var ry = 100 / coords.h;

	      $preview.css({
	        width: Math.round(rx * 558) + 'px',
	        height: Math.round(ry * 790) + 'px',
	        marginLeft: '-' + Math.round(rx * coords.x) + 'px',
	        marginTop: '-' + Math.round(ry * coords.y) + 'px'
	      }).show();
	    }
	  }
	  
	  
		$("#btnReg").bind("click",function(){
		   $('#regForm').bootstrapValidator('validate');
		   if($('#regForm').data('bootstrapValidator').isValid()){
			   $.ajax({
			        type: "post",
			        dataType: "json",
			        url: "doSignUp",
			        data: $('#regForm').serialize(),
			        success: function(data) {
			        	alert(data);
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
	});