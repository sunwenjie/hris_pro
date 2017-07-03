<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@page import="com.kan.hro.web.actions.biz.client.ClientAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="com.kan.base.web.renders.util.ManageRender"%>
<%@page import="com.kan.hro.domain.biz.client.ClientGroupVO"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_CLIENT", "clientForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {
		var status = $('#status').val();
		var subAction = getSubAction();
		
		//״̬��1:�½���2:����ˣ�3:��׼��4:�˻أ�5:������
		if( status == '1' || status == '4'){
		 	$('#btnSubmit').show(); 	
		}else if( status == '2' ){
			$('#btnEdit').hide();
		}
		
		var disable = true;
		if(getSubAction() == 'createObject'){
			disable = false;
		};
		
		// ��ӡ�������Ϣ����ť
		if($('#clientId').val()){
			<kan:auth right="new" action="<%=ClientAction.accessAction%>">
				$('#btnList').after('<a name="copyInfo" title="������Ϣ" class="commonTools" id="copyInfo" onclick="if(confirm(\'�Ƿ�ȷ�����Ʋ�������ͬ ����Ϣ�ͻ�����\')){link(\'clientAction.do?proc=copy_object&clientId=<bean:write name="clientForm" property="encodedId"/>&com.kan.token=' + $("#com.kan.token").val() + '\');}"><u>����</u></a>');
			</kan:auth>
		};

		// ������ʾ������ʾ��Ϣ
		$('#groupNameLI img').addClass('hide');
		// JS of the List
		<%
			final StringBuffer submitAdditionalCallback = new StringBuffer();
			submitAdditionalCallback.append( "if( $('#im1Type').val() != 0 ){" );
			submitAdditionalCallback.append( "flag = flag + validate(\"im1\", true, \"common\", 20, 0);" );
			submitAdditionalCallback.append( "}" );
			submitAdditionalCallback.append( "if( $('#im2Type').val() != 0 ){" );
			submitAdditionalCallback.append( "flag = flag + validate(\"im2\", true, \"common\", 20, 0);" );
			submitAdditionalCallback.append( "}" );
			submitAdditionalCallback.append( "if( flag==0 ){$('#status').attr('disabled', false);}" );

			final StringBuffer editCallBack = new StringBuffer();
			editCallBack.append( "$('#status').attr('disabled', 'disabled');");
			editCallBack.append( "if( $('#status').val() != 3 ){" );
			editCallBack.append( "disableLinkById(\"#addContract\");disableLinkById(\"#addOrder\");" );
			editCallBack.append( "}" );
			editCallBack.append( "if( status == '3' ){");
			editCallBack.append( "$('#btnSubmit').show();");
			editCallBack.append( "$('#btnEdit').hide();");
			editCallBack.append( "}");
			editCallBack.append( "if( $('#groupId').val() ){" );
			editCallBack.append( "$('#groupName').attr('disabled', 'disabled');");
			editCallBack.append( "$('#groupNameLI img').removeClass('hide');;");
			editCallBack.append( "}" );
			/* // ������ѹ鵵�����������ǲ����޸�
			if((Boolean)request.getAttribute("isFiling")){
			   editCallBack.append("$('#number').attr('disabled', 'disabled');");
			} */
			editCallBack.append( "$('img.deleteImg').each(function(i){$(this).show();});" );
			editCallBack.append( "var uploadObject = _createUploadObject('addPhoto', 'image', '/"+KANConstants.SHAREFOLDER_SUB_DIRECTORY_CLIENT+"/"+BaseAction.getAccountId(request, response)+"/');" );
			editCallBack.append( "var uploadLogo = _createUploadLogo('uploadAttachment', 'image', '/"+KANConstants.SHAREFOLDER_SUB_DIRECTORY_CLIENT+"/"+BaseAction.getAccountId(request, response)+"/');" );
		%>
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT", "initCallBack();", editCallBack.toString(), null, submitAdditionalCallback.toString() ) %>
		
		// ������ģʽ��ӡ��鿴������Ϣ��
		if(getSubAction() != 'createObject' && $('#groupId').val()){
			$('#groupNameLI label:eq(0)').append('<a onclick="link(\'clientGroupAction.do?proc=to_objectModify&id=<bean:write name="clientForm" property="encodedGroupId" />\');" ><img src="images/find.png" title="�鿴���ż�¼" /></a>');
		}
	
		// �ύ��ť�¼�
		$('#btnSubmit').click( function () { 
			if(validate_manage_primary_form() == 0){
				// ���ĵ�ǰForm��SubAction
				if(getSubAction() != 'createObject'){
					$('.manage_primary_form').attr('action', 'clientAction.do?proc=modify_object');
				}
				$('.manage_primary_form input#subAction').val('submitObject');
				
	    		enableForm('manage_primary_form');
	    		submitForm('manage_primary_form');
			}
		});
		
		// ����ϵ�˸����Զ��޸Ĳ�����Ϣ
		$('#mainContact').bind('change', function(){
			$.ajax({url: 'clientContactAction.do?proc=get_object_json&clientContactId=' + $(this).val() + '&date=' + new Date(),
				dataType : 'Json',
				success : function(data){
					cleanError('mainContact');
					if(data.success = 'true'){
						$('#phone').val(data.bizPhone);
						$('#mobile').val(data.bizMobile);
						$('#fax').val(data.fax);
						$('#email').val(data.bizEmail);
						$('#cityId').val(data.cityId);
						$('#address').val(data.address);
						$('#postcode').val(data.postcode);
						$('#im1Type').val(data.im1Type);
						$('#im1').val(data.im1);
						$('#im2Type').val(data.im2Type);
						$('#im2').val(data.im2);
						$('#branch').val(data.branch);
						$('#owner').val(data.owner);
					}
				}
			});
		});
		
		// ����tab
		loadHtml('#special_info', 'clientAction.do?proc=list_special_info_html&clientId=<bean:write name="clientForm" property="encodedId"/>', disable);
		// ������ϵ��������
		loadHtml('#mainContact', 'clientContactAction.do?proc=list_object_options_ajax&clientId=<bean:write name="clientForm" property="encodedId"/>', true );
		// ������ҵ����������
		loadHtml('#industry', 'industryTypeAction.do?proc=list_object_options_ajax&typeId=<bean:write name="clientForm" property="industry"/>', true );
		// ��ʼ��ʡ�ݿؼ�
		provinceChange('provinceId', 'viewObject', $('#temp_cityId').val(), 'cityId');

		// ��ʡ��Change�¼�
		$('.provinceId').change( function () { 
			provinceChange('provinceId', 'modifyObject', 0, 'cityId');
		});
		
		// ��ʼ�����ſؼ�
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
		
		// �󶨲���Change�¼�
		$('.branch').change( function () { 
			branchChange('branch', null, 0, 'owner');
		});	
		
		// ��ʡ��Change�¼�
		$('#provinceId').change( function () { 
			loadHtml('#cityId', 'cityAction.do?proc=list_object_options_ajax&provinceId=' + $('#provinceId').val(), true );
		});
		
		isArchiveClient();
	})(jQuery);	
	
	function initCallBack(){
		var $label = $("#logoFileLI").find("label");
		$label.html($label.text()+"<a title='�Ƽ�ͼƬ�ߴ�160*38'><img src='images/tips.png'></a>");
		$("#logoFileLI").find("input").remove();
		var html = "";
		html = html + "<span id='attachmentsLogoOL'>";
		<logic:notEmpty name="clientForm" property="logoFile">
		html = html + "<input type='hidden' id='logoFile' name='logoFile' value='<bean:write name='clientForm' property='logoFile'/>' /> ";
		html = html + "<input type='hidden' id='logoFile' name='logoFileSize' value='<bean:write name='clientForm' property='logoFileSize'/>' /> ";
		html = html + "<img name='disable_img' width='12' height='12' id='disable_img'  src='images/disable-btn.png'> ";
		html = html + "<img name='warning_img' width='12' height='12' id='warning_img' style='display: none;' onclick=\"$('#_attachmentSpan').remove();\" src='images/warning-btn.png'> ";
		html = html + "&nbsp;<bean:write name='clientForm' property='logoFileName'/>";
		</logic:notEmpty>
		html = html + "</span>";
		html = html + "<span><a name='uploadAttachment' id='uploadAttachment' class='disabled' >�ϴ�ͼƬ</a></span>";
		$("#logoFileLI").append(html);
	}
	
	function _createUploadLogo(id, fileType, attachmentFolder) {
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
				createFileProgressLogoDIV(filename);
				disableLinkById(id);
				setTimeout("_ajaxLogoBackState(true,'uploadFileAction.do?proc=getStatusMessage&postfixRandom="+postfixRandom+"')", 500);
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

	function _ajaxLogoBackState(first,progressURL) {
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
						setTimeout("_ajaxLogoBackState(false,'"+progressURL+"')", 100);
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
						$("#attachmentsLogoOL").html('<li><input type="hidden" id="logoFile" name="logoFile" value="'
												+ statusMsg.split('##')[0]
												+ '" /><input type="hidden" id="logoFile" name="logoFileSize" value="'
												+ statusMsg.split('##')[2]
												+ '" /><img onclick=\"removeAttachment(this);\" src=\"images/warning-btn.png\"> &nbsp; '
												+ statusMsg.split('##')[1]
												+ "</li>");
						$('#uploadFileLi').remove();

					}
				},'json');
	};
	
	function createFileProgressLogoDIV(fileName) {
		$("#attachmentsLogoOL").html($("#attachmentsLogoOL").html()
			+ '<li id="uploadFileLi" style="margin: 5px 0 0 0;"><div id="progressBarOut" style="text-align: center; width: 200px; height: 14px; padding: 1px 1px 1px 1px; font-size: 10px; border: solid #d4e4ff 1px;"><div id="progressBarIn" style="width: 0; height: 12px; background-color: #d4e4ff; margin: 0; padding: 0;"></div></div><div id="fileNameDiv" style="margin: 0 5px 0 5px; border: 0;">'
			+ fileName
			+ '</div> &nbsp; <div id="progressSize" style="margin: 0; border: 0;"></div></li>');
	};
	
	function isArchiveClient(){

		if ($('#clientId').val() != null && $('#clientId').val() !=""){
			$.ajax({
				url: 'clientContractAction.do?proc=getArchiveClientContractCount', 
				type: 'POST', 
				traditional: true,
				data:'clientId='+$('#clientId').val(),
				dataType : 'json',
				async:false,
				success: function(result){
					if (result != "0") {
						$('#number').attr("readonly","readonly");
						$('#number').css("background-color", "#e5e5e5");
					}
				} 
			});
		}
	}
</script>