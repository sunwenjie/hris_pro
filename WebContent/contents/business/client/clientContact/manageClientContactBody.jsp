<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="com.kan.base.web.renders.util.ManageRender"%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_CLIENT_CONTACT", "clientContactForm", true ) %>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/searchClient.jsp"></jsp:include>
</div>
		
<%-- �Ƿ��������������ҳ�� --%>
<input id="rePageFlag" name="rePageFlag" type="hidden" value="false"/>
							
<script type="text/javascript">
	(function($) {	
		// Load Clent Use Info
		loadHtml('#special_info', 'clientContactAction.do?proc=list_special_info_html&clientUserId=<bean:write name="clientContactForm" property="clientUserId"/>&username=<bean:write name="clientContactForm" property="username"/>', !(getSubAction() == 'createObject') );

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
		
		// JS of the List
		<%
			final StringBuffer initCallBack = new StringBuffer();
			if(request.getAttribute("clientIdError") != null){
				final String clientIdError = (String)request.getAttribute("clientIdError");
				initCallBack.append("addError('clientId', '" + clientIdError + "');");
				initCallBack.append("$('#rePageFlag').val('true');");
				initCallBack.append("if( getSubAction() == 'viewObject'){");
				initCallBack.append("$('#btnEdit').trigger('click');");
				initCallBack.append("}" );
			}

			final StringBuffer editCallBack = new StringBuffer();
			editCallBack.append( "if($('.client_user_username').val() != ''){" );
			editCallBack.append( "$('.client_user_username').attr('disabled', 'disabled');" );
			editCallBack.append( "$('#resetPassword').show();}" );
			editCallBack.append( "$('#clientId').addClass('important');" );
			editCallBack.append( "$('#clientNameZH').attr('disabled', 'disabled');" );
			editCallBack.append( "$('#clientNameEN').attr('disabled', 'disabled');" );
			
			final StringBuffer submitAdditionalCallback = new StringBuffer();
			submitAdditionalCallback.append( "if( $('#im1Type').val() != 0 ){" );
			submitAdditionalCallback.append( "flag = flag + validate(\"im1\", true, \"common\", 100, 0);" );
			submitAdditionalCallback.append( "};" );
			submitAdditionalCallback.append( "if( $('#im2Type').val() != 0 ){" );
			submitAdditionalCallback.append( "flag = flag + validate(\"im2\", true, \"common\", 100, 0);" );
			submitAdditionalCallback.append( "};" );
			submitAdditionalCallback.append( "if( $('#username').val() != null && $('#username').val() != '' ){" );
			submitAdditionalCallback.append( "flag = flag + validate(\"bizEmail\", true, \"email\", 0, 0);" );
			submitAdditionalCallback.append( "};" );
		%>
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_CONTACT", initCallBack.toString(), editCallBack.toString(), null, submitAdditionalCallback.toString() ) %>

		$('#clientIdLI label').append('&nbsp;');
		// ������ģʽ��ӡ��鿴�ͻ���Ϣ��
		if(getSubAction() != 'createObject' && $('#rePageFlag').val() == 'false'){
			$('#clientIdLI label:eq(0)').append('<a onclick="link(\'clientAction.do?proc=to_objectModify&id=<bean:write name="clientContactForm" property="encodedClientId" />\');" ><img src="images/find.png" title="�鿴�ͻ���¼" /></a>');
		}

		// ��ӡ������ͻ���Ϣ��
		$('#clientIdLI').append('<a onclick="popupClientSearch();" class="kanhandle"><img src="images/search.png" title="�����ͻ���¼" /></a>');
		
		// �ͻ�ID�����¼�
		$("#clientId").bind('keyup', function(){
			if($("#clientId").val().length >= 9){			
				$.ajax({url: 'clientAction.do?proc=get_object_json&clientId=' + $(this).val() + '&date=' + new Date(),
					dataType : 'json',
					success: function(data){
						cleanError('clientId');
						$('#clientIdLI label em').next('a').remove();
						
						if(data.success == 'true'){
							$('#clientNameZH').val(data.nameZH);
							$('#clientNameEN').val(data.nameEN);
							
							$('#clientIdLI label').append('<a onclick="link(\'clientAction.do?proc=to_objectModify&id=' + data.encodedId + '\');" ><img src="images/find.png" title="�鿴�ͻ���¼" /></a>');
							$('#bizPhone').val(data.phone);
							$('#bizMobile').val(data.mobile);
							$('#fax').val(data.fax);
							$('#bizEmail').val(data.email);
							$('#cityId').val(data.cityId);
							$('#address').val(data.address);
							$('#postcode').val(data.postcode);
							$('#branch').val(data.branch);
							$('#owner').val(data.owner);
						}else if(data.success == 'false'){
							$('#clientId').val('');
							$('#clientNameZH').val('');
							$('#clientNameEN').val('');
							addError('clientId', '�ͻ�ID��Ч');
						}
					}
				});
			}
		});

		// ҳ���ʼ�� - �鿴
		if(getSubAction() != 'viewObject'){
			// �ͻ�ID������ر���
			$('#clientId').addClass('important');
			
			// ���ÿͻ��������ƺ�Ӣ�����Ʋ��ܱ༭
			$('#clientNameZH').attr('disabled', 'disabled');
			$('#clientNameEN').attr('disabled', 'disabled');
		}
		
		// ҳ���ʼ�� - ���½����
		if(getSubAction() != 'createObject'){
			disableLink('manage_primary_form');
			$('.manage_primary_form input#subAction').val('viewObject');
		}
	})(jQuery);
</script>

