<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="com.kan.base.web.renders.util.ManageRender"%>
<%@page import="com.kan.hro.domain.biz.client.ClientInvoiceVO"%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_CLIENT_INVOICE", "clientInvoiceForm", true ) %>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/searchClient.jsp"></jsp:include>
</div>

<%-- �Ƿ��������������ҳ�� --%>
<input id="rePageFlag" name="rePageFlag" type="hidden" value="false"/>

<script type="text/javascript">
	(function($) {
		
		// JS of the List
		<%
			final StringBuffer editCallBack = new StringBuffer();
			editCallBack.append("$('#clientId').addClass('important');");
			editCallBack.append("$('#clientNameZH').attr('disabled', 'disabled');");
			editCallBack.append("$('#clientNameEN').attr('disabled', 'disabled');");
			
			final StringBuffer initCallBack = new StringBuffer();
			if(request.getAttribute("clientIdError") != null){
				final String clientIdError = (String)request.getAttribute("clientIdError");
				initCallBack.append("addError('clientId', '" + clientIdError + "');");
				initCallBack.append("$('#rePageFlag').val('true');");
				initCallBack.append("if(getSubAction() == 'viewObject'){");
				initCallBack.append("$('#btnEdit').trigger('click');");
				initCallBack.append("}" );
			}

			final StringBuffer submitAdditionalCallback = new StringBuffer();
			submitAdditionalCallback.append("flag = flag + checkClientId();");
		%>
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_INVOICE", initCallBack.toString(), editCallBack.toString(), null, submitAdditionalCallback.toString() ) %>

		// ��ӡ������ͻ���Ϣ��
		$('#clientIdLI').append('<a onclick="popupClientSearch();" class="kanhandle"><img src="images/search.png" title="�����ͻ���¼" /></a>');
		
		$('#clientIdLI label').append('&nbsp;');
		
		// ������ģʽ��ӡ��鿴�ͻ���Ϣ��
		if(getSubAction() != 'createObject' && $('#rePageFlag').val() == 'false'){
			$('#clientIdLI label:eq(0)').append('<a onclick="link(\'clientAction.do?proc=to_objectModify&id=<bean:write name="clientInvoiceForm" property="encodedClientId" />\');" ><img src="images/find.png" title="�鿴�ͻ���¼" /></a>');
		}
	
		// ��ʼ��ʡ�ݿؼ�
		provinceChange('provinceId', 'viewObject', $('#temp_cityId').val(), 'cityId');

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
							$('#title').val(data.nameZH);
							$('#cityId').val(data.cityId);
							$('#postcode').val(data.postcode);
							$('#address').val(data.address);
							$('#phone').val(data.phone);
							$('#mobile').val(data.mobile);
							$('#invoiceDate').val(data.invoiceDate);
							$('#paymentTerms').val(data.paymentTerms);
							$('#legalEntity').val(data.legalEntity);
							
							//	��ϵ�������Ϣ�Զ�����
							if(data.mainContact){
								$.ajax({url: 'clientContactAction.do?proc=get_object_json&clientContactId=' + data.mainContact + '&date=' + new Date(),
									dataType : 'json',
									success : function(data){
										$('#department').val(data.department);
										$('#position').val(data.department);
										$('#salutation').val(data.salutation);
									}
								});
							}
							
						}else if(data.success == 'false'){
							$('#clientId').val('');
							$('#clientNameZH').val('');
							$('#clientNameEN').val('');
							addError('clientId', '�ͻ�ID��Ч');
						}
						
						$('#legalEntity').val(data.legalEntity);
						$('#legalEntity').change();
					}
				});
			}
		});
		
		// ��ʡ��Change�¼�
		$('.provinceId').change( function () { 
			provinceChange('provinceId', 'modifyObject', 0, 'cityId');
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

	// ���ͻ�ID�Ƿ���Ч
	function checkClientId(){
		var flag = 0;
		cleanError('clientId');
		if($('#clientId').val()){
			$.ajax({url: 'clientAction.do?proc=get_object_json&clientId=' + $('#clientId').val() + '&date=' + new Date(),
				dataType : 'json',
				success: function(data){
					if(data.success == 'false'){
						$('#clientId').val('');
						addError('clientId', '�ͻ�ID��Ч');
						flag = 1;
					}else if(data.success == 'ture'){
						flag = 0;
					}
				}
			});
		}else{
			addError('clientId', '������ͻ�ID');
			flag = 1;
		}
		return flag;
	};
</script>

