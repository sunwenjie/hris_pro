<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientGroupVO"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_CLIENT_IN_HOUSE", "clientForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {
		// ����"StatusFlag"���ö����˵�ѡ����ʽ
		$('#menu_security_Modules').addClass('current');			
		$('#menu_security_ClientInformation').addClass('selected');
		
		$('#menu_client_Modules').removeClass('current');			
		$('#menu_client_Client').removeClass('selected');
		$('#menu_client_ClientInfo').removeClass('selected');
		
		// ��ҵ���ơ���Ų������޸�
		$('#nameZH').attr('disabled', 'disabled');
		$('#nameEN').attr('disabled', 'disabled');
		$('#number').attr('disabled', 'disabled');
		
		var status = $('#status').val();
		var subAction = getSubAction();
		
		// �б�ť����
		$('#btnList').hide();
		// ������Ϣ����
		$('#groupNameLI').hide();
		
		var disable = true;
		if(getSubAction() == 'createObject'){
			disable = false;
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
			editCallBack.append( "$('#nameZH').attr('disabled', 'disabled');");
			editCallBack.append( "$('#nameEN').attr('disabled', 'disabled');");
			editCallBack.append( "$('#number').attr('disabled', 'disabled');");
			editCallBack.append( "$('img.deleteImg').each(function(i){$(this).show();});" );
			editCallBack.append( "var uploadObject = _createUploadObject('addPhoto', 'image', '/"+KANConstants.SHAREFOLDER_SUB_DIRECTORY_CLIENT+"/"+BaseAction.getAccountId(request, response)+"/');" );
			editCallBack.append( "var uploadLogo = _createUploadLogo('uploadAttachment', 'image', '/"+KANConstants.SHAREFOLDER_SUB_DIRECTORY_CLIENT+"/"+BaseAction.getAccountId(request, response)+"/');" );
			editCallBack.append( "$('img.deleteImg').each(function(i){$(this).show();});" );
			editCallBack.append( "$('#pageTitle').html('" + KANUtil.getProperty( request.getLocale(), "business.client.edit" ) + "');" );
		%>
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_IN_HOUSE", "initCallBack();", editCallBack.toString(), null, submitAdditionalCallback.toString() ) %>
		
		// Add by siuxia
		if( subAction == 'viewObject' ){
			$('#pageTitle').html('<bean:message bundle="business" key="business.client.view" />');
		}
		
		$('#branchLI').hide();
		$('#ownerLI').hide();
		
		// ������ģʽ��ӡ��鿴������Ϣ��
		if(getSubAction() != 'createObject' && $('#groupId').val()){
			$('#groupNameLI label:eq(0)').append('<a onclick="link(\'clientGroupAction.do?proc=to_objectModify&id=<bean:write name="clientForm" property="encodedGroupId" />\');" ><img src="images/find.png" title="�鿴���ż�¼" /></a>');
		}
		
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
		
		// ���ؿͻ�������Ƭ
		var disable = true;
		if($('.subAction').val() == 'createObject'){
			disable = false;
		};
		
		loadHtml("#special_info","clientAction.do?proc=load_image_file&subAction=<bean:write name='clientForm' property='subAction' />&clientId=<bean:write name='clientForm' property='clientId' />",disable);
	})(jQuery);		
	
	function initCallBack(){
		var $label = $("#logoFileLI").find("label");
		$label.html($label.text() + "<a title='<bean:message bundle="business" key="business.client.logo.tips" />'><img src='images/tips.png'></a>");
		$("#logoFileLI").find("input").remove();
		
		var html = "<span id='attachmentsLogoOL'>";
		<logic:notEmpty name="clientForm" property="logoFile">
			html = html + "<input type='hidden' id='logoFile' name='logoFile' value='<bean:write name='clientForm' property='logoFile'/>' /> ";
			html = html + "<input type='hidden' id='logoFile' name='logoFileSize' value='<bean:write name='clientForm' property='logoFileSize'/>' /> ";
			html = html + "<img name='disable_img' width='12' height='12' id='disable_img'  src='images/disable-btn.png'> ";
			html = html + "<img name='warning_img' width='12' height='12' id='warning_img' style='display: none;' onclick=\"$('#_attachmentSpan').remove();\" src='images/warning-btn.png'> ";
			html = html + "&nbsp;<bean:write name='clientForm' property='logoFileName'/>";
		</logic:notEmpty>
		html = html + "</span>";
		html = html + ' <span><a name="uploadAttachment" id="uploadAttachment" class="disabled" ><bean:message bundle="public" key="link.upload.picture" /></a></span>';
		$("#logoFileLI").append(html);
	};
	
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
			},

			// �ϴ���ɺ�ȡ���ļ���filenameΪ����ȡ�õ��ļ�����msgΪ���������ص���Ϣ
			onComplete : function(filename, msg) {
				// �ϴ�����
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
</script>

