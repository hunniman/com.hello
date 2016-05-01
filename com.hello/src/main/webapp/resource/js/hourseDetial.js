var hourseDetial=function(){
	var self=this;
	var imgLoading=Ladda.create(document.querySelector('#btnLeaving'));
	var basePath="";
	var publishEmail="";
	self.init=function(){
		basePath=$("#basePath").val();
		publishEmail=$("#publishEmail").val();
		$("#btnLeaving").bind("click",function(){
			self.publisData();
		});
	};
	
	self.publisData=function(){
//		$('#hourseDetailForm').bootstrapValidator('validate');
//		if($('#hourseDetailForm').data('bootstrapValidator').isValid()){
		    if($.trim($("#txtLeaving").val()).length<=0){
		    	toastr.warning('请输入留言内容...');
		    	return;
		    }
			imgLoading.start();
			var data=$('#hourseDetailForm').serialize();
			$.ajax({
		        type: "post",
		        dataType: "json",
		        url: basePath+"/addLeaving",
		        data: data,
		        success: function(data) {
		        	imgLoading.stop();
		        	if(data.valid==="failed")
		        		toastr.error('发布失败');
		        	else if(data.valid=="auth"){
		        		toastr.warning('请先登录');	
		        	}else{
		        		$("#txtLeaving").val("");
		        		toastr.success('发布成功');
		        		var backJson=eval('(' + data.valid + ')'); 
		        		var tempHtml="",direction="pull-left",style="";
		        		if(backJson.userEmail===publishEmail){
		        			direction="pull-right";
		        			style="text-align: right;"
		        		}
		        		tempHtml+="<li class=\"media well\">";
		        		tempHtml+="<a class=\""+direction+"\" href=\""+basePath+"/UserHourseList/"+backJson.userId+"\">"; 
		        			tempHtml+="<img class=\"img-thumbnail\" data-src=\"holder.js/64x64\" \ src=\""+basePath+"/"+backJson.userHeader+"\" style=\"width: 50px; height: 50px;\">"; 
		        			tempHtml+="</a>";
		        			tempHtml+="<div class=\"media-body\" style=\""+style+"\">";
		        			tempHtml+=backJson.message;
		        			tempHtml+="<p><span class=\"badge "+direction+"\">刚刚</span></p>";
		        			tempHtml+="</div>";
		        			tempHtml+="</li>";
		        		
		        		$(tempHtml).appendTo($(".media-list"));
		        		
		        	}
		        		
		        },
		        error: function(err) {
		        	imgLoading.stop();
		        	toastr.error('发布失败');
		        }
		    });
//		}
//		var imgData=self.getImageData();
//		var formData=$('#publishForm').serialize();
	};
	

	self.initValidation=function(){
			$('#hourseDetailForm').bootstrapValidator({
		        message: 'This value is not valid',
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	txtLeaving: {
		                message: '请输入...',
		                validators: {
		                    notEmpty: {
		                        message: '请输入内容'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 1000,
		                        message: '昵称长度在1~1000个字符长度'
		                    }
		                }
		            }
		        }
		    });
		};
		
};

var hourseDetialModel;
$(function() {
	hourseDetialModel=new hourseDetial();
	hourseDetialModel.init();
});
