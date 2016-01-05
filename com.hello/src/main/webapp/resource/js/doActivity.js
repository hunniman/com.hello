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
			}else if(name==="baseInfoArea"){
				$("#headerArea").hide();
				$("#baseInfoArea").show();
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
				        	toastr.error('修改成功');
				        }
				    });
			   }
		});
		
		self.initHeaderUpload();
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
	
	
};


$(function() {
	var baseInfo=new doActivity();
	baseInfo.initEvent();
});