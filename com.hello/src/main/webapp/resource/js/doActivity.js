$(function() {
	
	
	var jcrop_api,
	  boundx,
	  boundy,
	  
	  // Grab some information about the preview pane
	  $preview = $('#preview-pane'),
	  $pimg = $('#targetPre');
	  
//	  console.log('init',[xsize,ysize]);

	  $('#target').Jcrop({
	    onChange: updatePreview,
	    onSelect: updatePreview,
	    aspectRatio: 1,
	    minSize:[100,100],
	    allowResize:false
	  },function(){
	    // Use the API to get the real image size
	    var bounds = this.getBounds();
	    boundx = bounds[0];
	    boundy = bounds[1];
	    // Store the API in the jcrop_api variable
	    jcrop_api = this;
	    
	    // Move the preview into the jcrop container for css positioning
	    $preview.appendTo(jcrop_api.ui.holder);
	  });
	  
	  function updatePreview(c){
	    if (parseInt(c.w) > 0) {
	      var rx = xsize / c.w;
	      var ry = ysize / c.h;
	      
	      $pimg.css({
	        width: Math.round(rx * boundx) + 'px',
	        height: Math.round(ry * boundy) + 'px',
	        marginLeft: '-' + Math.round(rx * c.x) + 'px',
	        marginTop: '-' + Math.round(ry * c.y) + 'px'
	      });
	    }
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