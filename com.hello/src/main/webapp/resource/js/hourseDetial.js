var hourseDetial=function(){
	var self=this;
	var imgLoading=Ladda.create(document.querySelector('#btnLeaving'));
	var basePath="";
	self.init=function(){
		basePath=$("#basePath").val();
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
		        	if(data.valid==="success")
		        		toastr.success('发布成功');
		        	else
		        		toastr.error('发布失败');
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
