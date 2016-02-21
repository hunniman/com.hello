var publish=function(){
	var self=this;
	var imgLoading=Ladda.create(document.querySelector('#btnUpload'));
	var btnPublishLoading=Ladda.create(document.querySelector('#btnPublish'));
	var publishId="";
	self.init=function(){
		var id=$("#publishId").val();
		$("#btnUpload").bind("click",function(){$("#editorFile").trigger("click");});
		$("#btnPublish").bind("click",self.publisData);
		
		self.initHeaderUpload();
		self.initValidation();
		
		$('#summernote').summernote({ height: 100,toolbar: [
		                                                    // [groupName, [list of button]]
		                                                    ['style', ['bold', 'italic', 'underline', 'clear']],
		                                                    ['font', ['strikethrough']],
		                                                    ['fontsize', ['fontsize']],
		                                                    ['para', ['ul', 'ol', 'paragraph']],
		                                                    ['height', ['height']]
		                                                  ] });
	};
	
	self.publisData=function(){
		$('#publishForm').bootstrapValidator('validate');
		if($('#publishForm').data('bootstrapValidator').isValid()){
			
			var declare = $('#summernote').summernote('code');
			if($.trim(declare).length<=0){
				toastr.error('请填上描述');
				return;
			}
			var imageData=self.getImageData();
			if($.trim(imageData).length<=0){
				toastr.error('请上传至少一张图片');
				return;
			}
			var data=$('#publishForm').serialize();
			data+="&imgData="+imageData;
			data+="&publishId="+publishId;
			data+="&declare="+declare;
			btnPublishLoading.start();
			$.ajax({
		        type: "post",
		        dataType: "json",
		        url: "updatePublishData",
		        data: data,
		        success: function(data) {
		        	btnPublishLoading.stop();
		        	if(data.valid==="success")
		        		toastr.success('发布成功');
		        	else
		        		toastr.error('发布失败');
		        },
		        error: function(err) {
		        	btnPublishLoading.stop();
		        	toastr.error('发布失败');
		        }
		    });
		}
//		var imgData=self.getImageData();
//		var formData=$('#publishForm').serialize();
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
				self.uploadPhoto($(this).val(),"editorFile","hoursePhoUpload",function(a){
					if(a==="failed"){
						toastr.success('上传失败');
						return;
					}
					self.initHeaderUpload();
					$("#orgiHeaderArea").hide();
					$('#progress').hide();
//					var jcropArea="<li><img src="+a+" id='"+a+"' style='width:130px;height:100px;'/>";
//					jcropArea+="<span class='label label-info'>Info</span>";
//					jcropArea+="<span class='label label-info'>Info</span>";
//					jcropArea+="<span class='label label-info'>Info</span></li>";
					var eleCount= $("#pictureArea").find("li").length;
					var jcropArea=self.generalImg(a,eleCount==0);
					$(jcropArea).appendTo($("#pictureArea"));
					$("#"+a).attr("src",a);
					$("#"+a).load(function(){
						
					});
					toastr.success('上传成功');
				});
			});
    };
	
	self.uploadPhoto=function(fileValue,fileId,url,callBack){
			   var reg=/^.*[^a][^b][^c]\.(?:png|jpg|bmp|gif|jpeg)$/;
			   if(!reg.test(fileValue.toLowerCase())){
				  	toastr.error('上传的图片格式不对!');
			    	return;
			   }
			   self.initProgress();
			   imgLoading.start();
			   oTimer = setInterval(function(){self.getProgress();}, 100);
			   $.ajaxFileUpload({
					url :url,
					secureuri : false,
					fileElementId :fileId,
					dataType :'text',
					success : function(data, status) {
						imgLoading.stop();
						window.clearInterval(oTimer);
						callBack(data);
					},
					error : function(data, status, e) {
						imgLoading.stop();
						window.clearInterval(oTimer);
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
		
		self.generalImg=function(src,isFirst){
		     var areaId=Date.parse(new Date());
			 var imgArea="<li id=\"li_"+areaId+"\"><div class=\"panel panel-default\" style=\"text-align: center;\" >";
			 		imgArea+="<div class=\"panel-heading\">";
			 			imgArea+="<h3 class=\"panel-title\" >";
			 			if(isFirst){
//			 				imgArea+="<span class=\"badge\">封面</span>";
			 			}
			 			imgArea+="<button onClick=\"publishModel.removeHourseImgeBlock('li_"+areaId+"')\" type=\"button\" class=\"btn btn-xs btn-danger\">删除</button>";
			 			imgArea+="</h3>";
			 			imgArea+="</div>";
			 			imgArea+="<div class=\"panel-body\">";
			 			imgArea+="<img class=\"img-thumbnail\" src=\""+src+"\" id=\""+areaId+"\" style=\"width:200px;height:200px;\">";
			 			imgArea+="</div>";
			 imgArea+="  </div></li>";
			 return imgArea;
		};
		
		/**
		 * 删除图片
		 */
		self.removeHourseImgeBlock=function(id){
			$("#"+id).remove();
		};
		
		self.initValidation=function(){
			$('#publishForm').bootstrapValidator({
		        message: 'This value is not valid',
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	title: {
		                message: '请输入...',
		                validators: {
		                    notEmpty: {
		                        message: ' '
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 30,
		                        message: '昵称长度在1~30个字符长度'
		                    }
		                }
		            },
		            room: {
		                message: '请输入...',
		                validators: {
		                    notEmpty: {
		                        message: ' '
		                    },
		                    digits: {
		                        message: '请输入数字'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 6,
		                        message: '昵称长度在1~6个字符长度'
		                    }
		                }
		            },
		            ting: {
		                message: '请输入...',
		                validators: {
		                    notEmpty: {
		                        message: ' '
		                    },
		                    digits: {
		                        message: '请输入数字'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 6,
		                        message: '昵称长度在1~6个字符长度'
		                    }
		                }
		            },
		            wei: {
		                message: '请输入...',
		                validators: {
		                    notEmpty: {
		                        message: ' '
		                    },
		                    digits: {
		                        message: '请输入数字'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 6,
		                        message: '昵称长度在1~6个字符长度'
		                    }
		                }
		            },
		            scare: {
		                message: '请输入...',
		                validators: {
		                    notEmpty: {
		                        message: ' '
		                    },
		                    digits: {
		                        message: '请输入数字'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 6,
		                        message: '长度在1~6个字符长度'
		                    }
		                }
		            },
		            floor: {
		                message: '请输入...',
		                validators: {
		                    notEmpty: {
		                        message: ' '
		                    },
		                    digits: {
		                        message: '请输入数字'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 6,
		                        message: '长度在1~6个字符长度'
		                    }
		                }
		            },
		            totalFloor: {
		                message: '请输入...',
		                validators: {
		                    notEmpty: {
		                        message: ' '
		                    },
		                    digits: {
		                        message: '请输入数字'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 6,
		                        message: '长度在1~6个字符长度'
		                    }
		                }
		            },
		            money: {
		                message: '请输入...',
		                validators: {
		                    notEmpty: {
		                        message: ' '
		                    },
		                    digits: {
		                        message: '请输入数字'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 6,
		                        message: '长度在1~6个字符长度'
		                    }
		                }
		            },
		            summernote: {
		                message: '请输入...',
		                validators: {
		                    notEmpty: {
		                        message: ' '
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 1000,
		                        message: '描述长度在1~1000个字符长度'
		                    }
		                }
		            },
		            contractPeople: {
		                message: '请输入...',
		                validators: {
		                    notEmpty: {
		                        message: ' '
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 20,
		                        message: '昵称长度在1~20个字符长度'
		                    }
		                }
		            },
		            phone: {
		                message: '请输入...',
		                validators: {
		                    notEmpty: {
		                        message: ' '
		                    },
		                    digits: {
		                        message: '请输入数字'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 11,
		                        message: '昵称长度在1~11个字符长度'
		                    }
		                }
		            },
		            address: {
		                message: '请输入...',
		                validators: {
		                    notEmpty: {
		                        message: ' '
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 200,
		                        message: '描述长度在1~200个字符长度'
		                    }
		                }
		            },
		        }
		    });
		};
		
		
		/**
		 * 获取上传的
		 */
		self.getImageData=function(){
			var imgStrings='';
			$("#pictureArea").find("li").each(function(i){
				var img=$(this).find("img").eq(0);
				imgStrings+=img.attr("src");
				imgStrings+="_";
			});
			return imgStrings;
		};
	
};

var publishModel;
$(function() {
	publishModel=new publish();
	publishModel.init();
});
