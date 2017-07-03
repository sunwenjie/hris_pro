<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientContractVO"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<% 
	final ClientContractVO clientContractVO = (ClientContractVO) request.getAttribute("clientContractForm"); 
%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_CLIENT_CONTRACT", "clientContractForm", true ) %>
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
			final StringBuffer submitAdditionalCallBack = new StringBuffer();
			submitAdditionalCallBack.append( "if(flag==0){$('#status').attr('disabled', false);}" );
		
			final StringBuffer editCallBack = new StringBuffer();
			editCallBack.append("$('#clientId').addClass('important');");
			editCallBack.append("$('#clientNameZH').attr('disabled', 'disabled');");
			editCallBack.append("$('#clientNameEN').attr('disabled', 'disabled');");
			editCallBack.append("$('#status').attr('disabled', 'disabled');");
			
			final StringBuffer initCallBack = new StringBuffer();
			if(request.getAttribute("clientIdError") != null){
				final String clientIdError = (String)request.getAttribute("clientIdError");
				initCallBack.append("addError('clientId', '" + clientIdError + "');");
				initCallBack.append("$('#rePageFlag').val('true');");
				initCallBack.append("if(getSubAction() == 'viewObject'){");
				initCallBack.append("$('#btnEdit').trigger('click');");
				initCallBack.append("}" );
			}

			if(clientContractVO != null && clientContractVO.getStatus() != null && !clientContractVO.getStatus().trim().equals("3") && !clientContractVO.getStatus().trim().equals("5") && !clientContractVO.getStatus().trim().equals("6")){
			   editCallBack.append("disableLinkById('#addOrder');");
			}
		%>
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_CONTRACT", initCallBack.toString(), editCallBack.toString(), null, submitAdditionalCallBack.toString() ) %>
		
		// ��ʼ���������鿴��ť
		loadWorkflowSeach('status','<bean:write name="clientContractForm" property="workflowId"/>');
		
		// ��������½����˻�״̬�����ر༭��ť
		if($('#status').val() != '1' && $('#status').val() != '4'){
			$('#btnEdit').hide();
		}

		// ������ɵĺ�ͬ���������ͬ��ţ����鵵�ţ�
		if($('#status').val() == '5' ){
			$('#contractNo').attr('disabled', false);
		}
		
		// ҳ���ʼ�� - ��ʾ����һ������ť
		$('#btnNext').show();
		$('#btnNext').click( function () { generateContract(); } );
		
		// ��ӡ������ͻ���Ϣ��
		$('#clientIdLI').append('<a onclick="popupClientSearch()" class="kanhandle"><img src="images/search.png" title="�����ͻ���¼" /></a>');
		
		$('#clientIdLI label').append('&nbsp;');
		
		// ������ģʽ��ӡ��鿴�ͻ���Ϣ��
		if(getSubAction() != 'createObject' && $('#rePageFlag').val() == 'false'){
			$('#clientIdLI label:eq(0)').append('<a onclick="link(\'clientAction.do?proc=to_objectModify&id=<bean:write name="clientContractForm" property="encodedClientId" />\');" ><img src="images/find.png" title="�鿴�ͻ���¼" /></a>');
		}
	
		// ����Tab
		loadHtml('#special_info', 'clientContractAction.do?proc=list_special_info_html&clientContractId=' + $('#id').val(), getDisable());
		
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
						}else if(data.success == 'false'){
							$('#clientId').val('');
							$('#clientNameZH').val('');
							$('#clientNameEN').val('');
							addError('clientId', '�ͻ�ID��Ч');
						}

						$('#entityId').val(data.legalEntity);
						$('#entityId').change();
						loadContractOptions();
						loadInvoiceAddressOptions();
					}
				});
			}
		});
		
		// �󶨷���ʵ��Change�¼�
		$('#entityId').change(function (){
			$.ajax({url: 'entityAction.do?proc=get_object_json&entityId=' + $(this).val() + '&date=' + new Date(),
				dataType : 'json',
				success: function(data){
					if(data.success == 'true'){
						if($('#businessTypeId').val() == '0'){
							$('#businessTypeId').val(data.bizType);
						}
						$('#businessTypeId').change();
					}
				}
			});
		});
		
		// ��ҵ������Change�¼�
		$('#businessTypeId').change(function (){
			var date = new Date();
			var contractName = $('#businessTypeId option:selected').text() + '��ͬ - ' + date.getFullYear();
			if(date.getMonth() + 1 < 10){
				contractName = contractName + "0";
			}
			contractName = contractName + eval(date.getMonth() + 1);
			
			if($('#nameZH').val() == ''){
				$('#nameZH').val(contractName);
			}
			//jzy add 2014/04/11 ����ʱ����պ�ͬ������
			$('#templateId')[0].length=0;
			loadBusinessContractTemplateOptions();
		});
		
		// ��ʼ�����ſؼ�
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
		
		// �󶨲���Change�¼�
		$('.branch').change( function () { 
			branchChange('branch', null, 0, 'owner');
		});	
	})(jQuery);
	
	// ��ǰ�Ƿ���ҪDisable
	function getDisable(){
		if($('.manage_primary_form input#subAction').val() == 'viewObject'){
			return true;
		}else{
			return false;
		}
	};
	
	// To Next Page - Generate Contract
	function generateContract(){
		var flag = 0;

		//	��֤��ͬģ��
		if(checkContractTemplate()){
			flag = flag + 1;
			addError('templateId', '��ѡ���ͬģ����ϴ��ͻ����ͬ��');
			$('#templateId').removeAttr('disabled');
		}
		
		if(flag == 0 && validate_manage_primary_form() == 0){
			$('.manage_primary_form').attr('action', 'clientContractAction.do?proc=generate_contract');
			if($('.manage_primary_form #subAction').val() == 'viewObject'){
				$('.manage_primary_form #subAction').val('modifyObject');
			}
			enableForm('manage_primary_form');
			submitForm('manage_primary_form');
		}
	};

	//	��֤��ͬģ���¼�
	function checkContractTemplate(){
		cleanError('templateId');
		var templateFlag = false;
		//	���ģ��ID
		var templateID = $('#templateId').val();
		//	����û��ϴ�����
		var attachmentLength = $('#attachmentsOL > li').length;
		
		if( templateID == 0 && attachmentLength == 0 )
		{
			templateFlag = true;
		}
		
		return templateFlag;		
	};
	
	// ��������ͬ�������¼�
	function loadContractOptions(contractId){
		loadHtml('#masterContractId', 'clientContractAction.do?proc=list_object_options_ajax&flag=contract&contractId=' + contractId + '&clientId=' + $('#clientId').val(), getDisable() );
	};
	
	// ���ط�Ʊ��ַ�������¼�
	function loadInvoiceAddressOptions(invoiceAddressId){
		loadHtml('#invoiceAddressId', 'clientInvoiceAction.do?proc=list_object_options_ajax&invoiceAddressId=' + invoiceAddressId + '&clientId=' + $('#clientId').val(), getDisable(), 'if($("#invoiceAddressId").children().length == 2 && getSubAction == "createObject"){$("#invoiceAddressId option:nth-child(2)").attr("selected" , "selected");}' );
	};
	
	// ���غ�ͬģ���������¼�
	function loadBusinessContractTemplateOptions(templateId){
		loadHtml('#templateId', 'businessContractTemplateAction.do?proc=list_object_options_ajax&templateId=' + templateId + '&entityId=' + $('#entityId').val() + '&businessTypeId=' + $('#businessTypeId').val(), getDisable(), 'if($("#templateId").children().length == 2 && getSubAction == "createObject"){$("#templateId option:nth-child(2)").attr("selected" , "selected");}' );
	};
</script>
