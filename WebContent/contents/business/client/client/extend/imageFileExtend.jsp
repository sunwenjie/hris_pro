<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.hro.domain.biz.client.ClientVO"%>
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
			<li id="tabMenu1" onClick="changeTab(1,2)" class="first hover" >
				<bean:message bundle="public" key="menu.table.title.image.photo" />
				&nbsp;&nbsp;<img src="images/tips.png" title="<bean:message bundle="business" key="business.client.image.tips" />">
			</li> 
		</ul> 
		<ul> 
			<li id="tabMenu2" onClick="changeTab(2,2)" ><bean:message bundle="public" key="menu.table.title.wechat.right" /></li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab kanMultipleChoice1">
			<span><a name="addPhoto" id="addPhoto" class="kanhandle"><bean:message bundle="public" key="link.add.image.photo" /></a></span>
			<div id="attachmentsDiv">
				<ol id="attachmentsOL" class="auto thumb" >
					<% final ClientVO clientVO = (ClientVO)request.getAttribute("clientVO"); %>
					<%= AttachmentRender.getPhotos(request, clientVO.getImageFile(), null) %>
				</ol>
			</div>
		</div>
		<div id="tabContent2" class="kantab kanMultipleChoice2" style="display:none">
			<%=KANUtil.getCheckBoxHTML(clientVO.getMobileModuleRights(),"mobileModuleRightIds",clientVO.getMobileModuleRightIds(),((ClientVO)request.getAttribute("clientVO")).getSubAction()) %>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function(){
		imageMouseover();
		if($(".subAction").val()=='viewObject'){
			$("#addPhoto").addClass("disabled");
		}
	});
	// functions
	function imageMouseover(){
		var x = 100;
		var y = 100;
		$("a.tooltip").mouseover(function(e){ 
			this.myTitle = this.title;
			this.title = "";
			var tooltip = "<div style='position: absolute;z-index: 1001';  id='tooltip'><img src='"+ $(this).find('img').attr('src') +"'/><\/div>"; //���� div Ԫ��
			$("body").append(tooltip);	//����׷�ӵ��ĵ���
			// ����һ����ʱλ�ô��ͼƬ��С
			$("<img/>").attr("src", $(this).find('img').attr('src')).load(function() {
				y = this.height;
			});
			$("#tooltip")
				.css({
					"top": (e.pageY-y)+"px",
					"left": (e.pageX+x)+"px"
				}).show("fast");	  //����x�����y���꣬������ʾ
	    }).mouseout(function(){
			this.title = this.myTitle;	
			$("#tooltip").remove();	 //�Ƴ� 
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
			// �ļ��ϴ�Action
			action : 'uploadFileAction.do?proc=upload&fileItem=1&extType='+ fileType + '&folder=' + attachmentFolder +'&postfixRandom='+postfixRandom,
			// ����
			name : 'file',
			// �Զ��ϴ�
			autoSubmit : true,
			// ����Text��ʽ����
			responseType : false,
			// �ϴ�����
			onSubmit : function(filename, ext) {
				createFileProgressDIV(filename);
				disableLinkById(id);
				//$("#progress_debug").append('action = '+'uploadFileAction.do?proc=upload&fileItem=1&extType='+ fileType + '&folder=' + attachmentFolder +'&postfixRandom='+postfixRandom);
				setTimeout("_ajaxBackState(true,'"+"uploadFileAction.do?proc=getStatusMessage&postfixRandom="+postfixRandom+"',200)");
				// �ϴ�������
				// $('#' + id).disable();
			},

			// �ϴ���ɺ�ȡ���ļ���filenameΪ����ȡ�õ��ļ�����msgΪ���������ص���Ϣ
			onComplete : function(filename, msg) {
				// �ϴ�����
				// $('#' + id).enable();
				enableLinkById(id);
			}
		});

		return uploadObject;
	};
	
	function _ajaxBackState(first,progressURL) {
		// Ajax���ò�ˢ�µ�ǰ�ϴ�״̬
		$.post(progressURL+'&first=' + first+ '&date=' + new Date(),null,
				function(result) {
					var obj = result;
					var readedBytes = obj["readedBytes"];
					var totalBytes = obj["totalBytes"];
					var statusMsg = obj["statusMsg"];
					progressbar(readedBytes,totalBytes);

					//$("#progress_debug").append('info = '+obj["info"]+' readedBytes'+readedBytes+'    totalBytes='+totalBytes+'    progressURL='+progressURL+'<br/>');
					
					// ����״̬
					if (obj["info"] == "0") {
						setTimeout("_ajaxBackState(false,'"+progressURL+"',200)");
					}
					// �ϴ�ʧ�ܣ���ʾʧ��ԭ��
					else if (obj["info"] == "1") {
						alert(obj["statusMsg"]);
						$('#btnAddAttachment').attr("disabled", false);
						// ɾ���ϴ�ʧ�ܵ��ļ��ͽ�����Ϣ
						$('#uploadFileLi').remove();
					}
					// �ϴ����
					else if (obj["info"] == "2") {
						$('#uploadAttachment').attr("disabled", false);
						var imageSrc = "downloadFileAction.do?proc=download&fileString=/" + encodeURI(encodeURI(statusMsg.split('##')[0]));
						$("#attachmentsOL").append('<li style="width:140px !important; height:110px !important;"><input type="hidden" id="imageFileArray" name="imageFileArray" value="'
												+ statusMsg.split('##')[0]
												+ '" />'
												+'<img src="images/close_pop.png" class="deleteImg" onclick="removeAttachment(this);">'
												+' <a class="tooltip" href="'+imageSrc+'" > <img class="imgtooltip" src="'+imageSrc+'" width="120" height="90" /> </a>'
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