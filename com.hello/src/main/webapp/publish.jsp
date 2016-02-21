<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="http://netdna.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.css" rel="stylesheet">
<jsp:include page="common/css.jsp" />
<jsp:include page="common/js.jsp" />
<jsp:include page="common/validator.jsp" />
<%-- <jsp:include page="common/jcrop.jsp" /> --%>
<jsp:include page="common/progressbar.jsp" />
<jsp:include page="common/loading.jsp" />
<jsp:include page="common/summernote.jsp" />


<script src="resource/js/publish.js"></script>
<style type="text/css">
  ul{
     list-style:none; /* 去掉ul前面的符号 */  
     margin: 0px; /* 与外界元素的距离为0 */  
     padding: 0px; /* 与内部元素的距离为0 */  
     width: auto; /* 宽度根据元素内容调整 */  
  }
  ul li{
      float:left; /* 向左漂移，将竖排变为横排 */  
     margin-right: 30px;
     margin-bottom: 10px;
  }
</style>
</head>
<body>
    <jsp:include page="common/header.jsp" />
        <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
        <div class="container">
        </div>
    </div>
    <input type="hidden" id="publishId" name="x">
    <div class="container">
        <form id="publishForm" class="form-horizontal" role="form">
            <div class="form-group">
                <label class="col-sm-2 control-label">标题</label>
                <div class="col-sm-10">
                    <input class="form-control" id="title" type="text" name="title" placeholder="请输入...">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">地址</label>
                <div class="col-sm-10">
                    <input class="form-control" id="address" type="text" name="address" placeholder="请输入...">
                </div>
            </div>
            <div class="form-group">
                <label  class="col-sm-2 control-label">
                                                    房屋类型</label>
                <div class="col-sm-1">
                        <input class="form-control" id="room" type="text" name="room">
                </div>
                <label class="col-sm-1 control-label" style="text-align:left;padding-left: 0px;">室</label>
                
                <div class="col-sm-1">
                        <input class="form-control" id="ting" type="text" name="ting"  >
                </div>
                <label class="col-sm-1 control-label" style="text-align:left;padding-left: 0px;">厅</label>
                
                <div class="col-sm-1">
                        <input class="form-control" id="wei" type="text" name="wei"  >
                </div>
                <label class="col-sm-1 control-label" style="text-align:left;padding-left: 0px;">卫</label>
                
                <div class="col-sm-2">
                        <input class="form-control" id="scare" type="text" name="scare"  >
                </div>
                <label class="col-sm-1 control-label" style="text-align:left;padding-left: 0px;">㎡</label>
            </div>
                <div class="form-group">
                <label  class="col-sm-2 control-label">
                                                    楼层</label>
                <div class="col-sm-1">
                       	 <input class="form-control" id="room" type="text" name="floor">
                </div>
                <label class="col-sm-1 control-label" style="text-align:left;padding-left: 0px;">层</label>
                <label class="col-sm-1 control-label" style="text-align:right;padding-right: 0px;">共</label>
                <div class="col-sm-2">
                       	<input class="form-control" id="ting" type="text" name="totalFloor"  >
                </div>
                <label class="col-sm-1 control-label" style="text-align:left;padding-left: 0px;">层</label>
            </div>
            <div class="form-group">
                <label for="inputPassword" class="col-sm-2 control-label">
                                                       租金(元/月)</label>
                <div class="col-sm-10">
                    <input class="form-control" id="money" type="text" name="money" placeholder="该输入..." >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">联系人</label>
                <div class="col-sm-10">
                        <input class="form-control" id="contractPeople" type="text" name="contractPeople" placeholder="该输入..." >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">电话</label>
                <div class="col-sm-10">
                        <input class="form-control" id="phone" type="text" name="phone" placeholder="该输入..." >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">qq</label>
                <div class="col-sm-10">
                        <input class="form-control" id="qq" type="text" name="qq" placeholder="该输入..." >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">微信</label>
                <div class="col-sm-10">
                        <input class="form-control" id="weixin" type="text" name="weixin" placeholder="该输入..." >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">描述</label>
                <div class="col-sm-10">
                	<div id="summernote">
                	</div>
<!--                     <textarea class="form-control" name="declare" rows="10" maxlength="1000"></textarea> -->
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">图片</label>
                <div class="col-sm-2">
                        <button id="btnUpload" class="btn btn-info ladda-button" type ="button" data-style="expand-right">
                          <span class="ladda-label">上传</span>
                        </button>
                        <input type="file" id="editorFile" name="editorFile" style="display:none;"/>
                </div>
                
                <div class="col-sm-8">
	                <div id="progress" style="display:none;">
					       	<div class="progress" style="margin-top:10px;">
					    	 	<div class="progress-bar progress-bar-success" role="progressbar" data-transitiongoal="0" ></div>
						   </div>
					</div>
				</div>
            </div>
            <div class="form-group" id="productArea">
             	<label class="col-sm-2 control-label"></label>
                <div class="col-sm-10">
                   <ul id="pictureArea">
                   </ul>
                </div>
                
            </div>
            <div class="form-group">
                <div class="col-sm-10">
                </div>
                <div class="col-sm-2">
                   <div style="text-align: right;">
                      <button id="btnPreLook" class="btn btn-primary ladda-button" type="button" data-style="expand-right">
                          <span class="ladda-label">预览</span>
                     </button>
                     
                     <button id="btnPublish" class="btn btn-primary ladda-button" type="button" data-style="expand-right">
                          <span class="ladda-label">发布</span>
                     </button>
                   </div>
                </div>
            </div>
        </form>
    </div>
    <!-- /container -->

</body>
</html>
