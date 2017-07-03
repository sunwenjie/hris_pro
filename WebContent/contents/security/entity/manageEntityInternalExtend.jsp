<%@page import="com.kan.base.domain.security.EntityVO"%>
<%@page import="com.kan.base.domain.system.AccountVO"%>
<%@page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<style type="text/css">
	.deleteImg{
		position : absolute;  
	    top : -9px;  
	    right : 3px;  
	    z-index: 1001;
	    display: none;
	    cursor: pointer;
	    width: 32px;
	    height: 32px;
	}
	.imgtooltip{
		position : relative;  
	    margin-top: 10px; 
	}
</style>


<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover" >LOGO</li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab kanMultipleChoice1">
			<span><a name="addPhoto" id="addPhoto" class="kanhandle"><bean:message bundle="public" key="oper.new" /></a></span>
			<div id="attachmentsDiv">
				<ol id="attachmentsOL" class="auto thumb" >
					<% final EntityVO entityVO = (EntityVO)request.getAttribute("entityForm"); %>
					<%= AttachmentRender.getPhotos(request, entityVO.getLogoFile(), null,180,43,160,38) %>
				</ol>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function(){
		imageMouseover();
	});
	// functions
	function imageMouseover(){
		var x = 100;
		var y = 100;
		$("a.tooltip").mouseover(function(e){ 
			this.myTitle = this.title;
			this.title = "";
			var tooltip = "<div style='position: absolute;z-index: 1001';  id='tooltip'><img src='"+ $(this).find('img').attr('src') +"'/><\/div>"; //创建 div 元素
			$("body").append(tooltip);	//把它追加到文档中
			// 创建一个临时位置存放图片大小
			$("<img/>").attr("src", $(this).find('img').attr('src')).load(function() {
				y = this.height;
			});
			$("#tooltip")
				.css({
					"top": (e.pageY-y)+"px",
					"left": (e.pageX+x)+"px"
				}).show("fast");	  //设置x坐标和y坐标，并且显示
	    }).mouseout(function(){
			this.title = this.myTitle;	
			$("#tooltip").remove();	 //移除 
	    }).mousemove(function(e){
			$("#tooltip")
				.css({
					"top": (e.pageY-y) + "px",
					"left":  (e.pageX+x)  + "px"
				});
		});

	}
	
	
	function _createUploadObject(id, fileType, attachmentFolder) {
		var postfixRandom = generateMixed(6);
		var uploadObject = new AjaxUpload(id, {
			// 文件上传Action
			action : 'uploadFileAction.do?proc=upload&fileItem=1&extType='+ fileType + '&folder=' + attachmentFolder +'&postfixRandom='+postfixRandom,
			// 名称
			name : 'file',
			// 自动上传
			autoSubmit : true,
			// 返回Text格式数据
			responseType : false,
			// 上传处理
			onSubmit : function(filename, ext) {
				createFileProgressDIV(filename);
				disableLinkById(id);
				//$("#progress_debug").append('action = '+'uploadFileAction.do?proc=upload&fileItem=1&extType='+ fileType + '&folder=' + attachmentFolder +'&postfixRandom='+postfixRandom);
				setTimeout("_ajaxBackState(true,'"+"uploadFileAction.do?proc=getStatusMessage&postfixRandom="+postfixRandom+"',200)");
				// 上传不可用
				// $('#' + id).disable();
			},

			// 上传完成后取得文件名filename为本地取得的文件名，msg为服务器返回的信息
			onComplete : function(filename, msg) {
				// 上传可用
				// $('#' + id).enable();
				enableLinkById(id);
			}
		});

		return uploadObject;
	};
	
	function _ajaxBackState(first,progressURL) {
		// Ajax调用并刷新当前上传状态
		$.post(progressURL+'&first=' + first+ '&date=' + new Date(),null,
				function(result) {
					var obj = result;
					var readedBytes = obj["readedBytes"];
					var totalBytes = obj["totalBytes"];
					var statusMsg = obj["statusMsg"];
					progressbar(readedBytes,totalBytes);

					//$("#progress_debug").append('info = '+obj["info"]+' readedBytes'+readedBytes+'    totalBytes='+totalBytes+'    progressURL='+progressURL+'<br/>');
					
					// 正常状态
					if (obj["info"] == "0") {
						setTimeout("_ajaxBackState(false,'"+progressURL+"',200)");
					}
					// 上传失败，提示失败原因
					else if (obj["info"] == "1") {
						alert(obj["statusMsg"]);
						$('#btnAddAttachment').attr("disabled", false);
						// 删除上传失败的文件和进度信息
						$('#uploadFileLi').remove();
					}
					// 上传完成
					else if (obj["info"] == "2") {
						$('#uploadAttachment').attr("disabled", false);
						var imageSrc = "downloadFileAction.do?proc=download&fileString=/" + encodeURI(encodeURI(statusMsg.split('##')[0]));
						$("#attachmentsOL").append('<li style="width:180px !important; height:43px !important;"><input type="hidden" id="imageFileArray" name="imageFileArray" value="'
												+ statusMsg.split('##')[0]
												+ '" />'
												+'<img src="images/close_pop.png" class="deleteImg" onclick="removeAttachment(this);">'
												+' <a class="tooltip" href="'+imageSrc+'" > <img class="imgtooltip" src="'+imageSrc+'" width="160" height="38" /> </a>'
												+ "</li>");
						$('#uploadFileLi').remove();
						$('img.deleteImg').each(function(i){
			   				$(this).show();
			   			});
						imageMouseover();
					}
				},'json');
	};
	
</script>