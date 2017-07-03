<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage(request, "HRO_BIZ_CLIENT_ORDER_HEADER", "clientOrderHeaderForm", true) %>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/searchClient.jsp"></jsp:include>
</div>

<%-- �Ƿ��������������ҳ�� --%>
<input id="rePageFlag" name="rePageFlag" type="hidden" value="false"/>

<html:form action="employeeContractAction.do?proc=list_object_order" styleClass="listServiceContract_form">
	<input type="hidden" name="sortColumn" id="sortColumn" value="" /> 
	<input type="hidden" name="sortOrder" id="sortOrder" value="" />
	<input type="hidden" name="page" id="page" value="" />
	<input type="hidden" name="subAction" id="subAction" value="" />
	<input type="hidden" name="selectedIds" id="selectedIds" value="" />
	<input type="hidden" name="orderId" id="orderId" value="<bean:write name="clientOrderHeaderForm" property="encodedId"/>" />
</html:form>

<script type="text/javascript">

	// ��ʼ���������鿴��ť
	loadWorkflowSeach('status','<bean:write name="clientOrderHeaderForm" property="workflowId"/>');
	// ��ʼ������ͬ�¼�
	var orderBindContract = false;
	//	��ʼ�������ͬ������	
	loadContractOptions('<bean:write name="clientOrderHeaderForm" property="contractId"/>');
	
	function checkContract(){
		if(orderBindContract){
			return validate("contractId", true, "select", "0", "0", "0", "0");
		}else{
			return 0;
		}
	};
	
	(function($) {
		if(getSubAction()=='viewObject'){
			var noticeExpire = "<bean:write name='clientOrderHeaderForm' property='noticeExpire'/>";
 			if(noticeExpire){
				$("input[name=noticeExpire]").each(function(i){
					for(var i = 0;i<noticeExpire.length;i++){
						if($(this).val()==noticeExpire[i]){
							$(this).attr("checked","checked");						
						}
					}
				});
 			}
			var noticeProbationExpire = "<bean:write name='clientOrderHeaderForm' property='noticeProbationExpire'/>";
 			if(noticeProbationExpire){
				$("input[name=noticeProbationExpire]").each(function(i){
					for(var i = 0;i<noticeProbationExpire.length;i++){
						if($(this).val()==noticeProbationExpire[i]){
							$(this).attr("checked","checked");						
						}
					}
				});
 			}
 			var noticeRetire = "<bean:write name='clientOrderHeaderForm' property='noticeRetire'/>";
 			if(noticeRetire){
				$("input[name=noticeRetire]").each(function(i){
					for(var i = 0;i<noticeRetire.length;i++){
						if($(this).val()==noticeRetire[i]){
							$(this).attr("checked","checked");						
						}
					}
				});
 			}
		}
		
		if(($('#status').val() == '1' && getSubAction() != 'createObject') || $('#status').val() == '4'){
			$('#btnSubmit').show();
			$('#btnCancel').show();
		}
		
		if($('#status').val() == '2' ||$('#status').val() == '6' || $('#status').val() == '7'){
			$('#btnEdit').hide();
		}
		
		if($('#status').val() == '3' || $('#status').val() == '5'){
			$('#btnStop').hide();
		}
		
		if($('#status').val() == '8'){
			$('#btnSubmit').show();
		}
		
		// �ύ��ť�¼�
		$('#btnSubmit').click( function () {
			if(validate_manage_primary_form() == 0){
				// ���ĵ�ǰForm��SubAction
				if(getSubAction() != 'createObject'){
					$('.manage_primary_form').attr('action', 'clientOrderHeaderAction.do?proc=modify_object');
				}
				
				$('.manage_primary_form input#subAction').val('submitObject');
				
	    		enableForm('manage_primary_form');
	    		submitForm('manage_primary_form');
			}
		});
		
		// ȡ����ť�¼�
		$('#btnCancel').click( function () { 
			$('.manage_primary_form').attr('action', 'clientOrderHeaderAction.do?proc=cancel_object');
			enableForm('manage_primary_form');
			submit('manage_primary_form');
		});
		
		// ��ֹ��ť�¼�
		$('#btnStop').click( function () { 
			$('.manage_primary_form').attr('action', 'clientOrderHeaderAction.do?proc=stop_object');
			enableForm('manage_primary_form');
			submit('manage_primary_form');
		});
	
		// ��ʼ�����ſؼ�
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
			
		// �󶨲���Change�¼�
		$('.branch').change(function (){ 
			branchChange('branch', null, 0, 'owner');
		});	 
	
		// ���Tab
		loadHtml('#special_info', 'clientOrderHeaderAction.do?proc=list_special_info_html&subAction=<bean:write name="clientOrderHeaderForm" property="subAction"/>&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', getSubAction() == 'viewObject');

		// JS of the List
		<%
			final StringBuffer editCallBack = new StringBuffer();
			editCallBack.append("$('#clientId').addClass('important');");
			editCallBack.append("$('#clientNameZH').attr('disabled', 'disabled');");
			editCallBack.append("$('#clientNameEN').attr('disabled', 'disabled');");
			editCallBack.append("$('#status').attr('disabled', 'disabled');");
			editCallBack.append("if( $('#status').val() == 8 ){" );
			editCallBack.append("disableLinkById(\"#addContract\");" );
			editCallBack.append("}" );
			
			final StringBuffer initCallBack = new StringBuffer();
			
			if(request.getAttribute("clientIdError") != null)
			{
				final String clientIdError = (String)request.getAttribute("clientIdError");
				initCallBack.append("addError('clientId', '" + clientIdError + "');");
				initCallBack.append("$('#rePageFlag').val('true');");
				initCallBack.append("if(getSubAction() == 'viewObject'){");
				initCallBack.append("$('#btnEdit').trigger('click');");
				initCallBack.append("}" );
			}

			final StringBuffer submitAdditionalCallback = new StringBuffer();
			submitAdditionalCallback.append("flag = flag + validate('otLimitByDay', false, 'currency', 2, 0, 10, 0);");
			submitAdditionalCallback.append("flag = flag + validate('otLimitByMonth', false, 'currency', 3, 0, 200, 0);");
			submitAdditionalCallback.append("flag = flag + checkContract();");
		%>
		<%= ManageRender.generateManageJS(request, "HRO_BIZ_CLIENT_ORDER_HEADER", initCallBack.toString(), editCallBack.toString(), null, submitAdditionalCallback.toString()) %>

		// ��ӡ������ͻ���Ϣ��
		$('#clientIdLI').append('<a onclick="popupClientSearch();" class="kanhandle"><img src="images/search.png" title="�����ͻ���¼" /></a>');

		$('#clientIdLI label').append('&nbsp;');
		// ������ģʽ��ӡ��鿴�ͻ���Ϣ��
		if(getSubAction() != 'createObject' && $('#rePageFlag').val() == 'false'){
			$('#clientIdLI label:eq(0)').append('<a onclick="link(\'clientAction.do?proc=to_objectModify&id=<bean:write name="clientOrderHeaderForm" property="encodedClientId" />\');" ><img src="images/find.png" title="�鿴�ͻ���¼" /></a>');
		}
		
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
						loadContractOptions('<bean:write name="clientOrderHeaderForm" property="contractId"/>');
						loadInvoiceAddressOptions();

						$('#contractIdLI label').html($('#contractIdLI label').html().replace(' <em>*</em>', ''));
						orderBindContract = false;
						if(data.orderBindContract){
							$('#contractIdLI label').html($('#contractIdLI label').html() + ' <em>*</em>');
							orderBindContract = true;
						}
					}
				});
			}
		});
		
		// �󶨺�ͬChange�¼�
		$('#contractId').change(function(){
			$.ajax({url: 'clientContractAction.do?proc=get_object_ajax&contractId=' + $(this).val() + '&date=' + new Date(),
				dataType: "json",
				success: function( data ){
					$('#entityId').val(data.entityId);
					$('#businessTypeId').val(data.businessTypeId);
					$('#invoiceAddressId').val(data.invoiceAddressId);
					$('#startDate').val(data.startDate);
					$('#endDate').val(data.finalEndDate);
					$('#branch').val(data.branch);
					$("#owner").val(data.owner);
					$('#entityId').change();
					branchChange('branch', null, data.owner, 'owner');
				}
			});
		});
		
		// ����ʵ��Change�¼�
		$('#entityId').change(function(){
			$.ajax({url: 'entityAction.do?proc=get_object_json&entityId=' + $(this).val() + '&date=' + new Date(),
				dataType : 'json',
				success: function(data){
					if($('#contractId').val() == '0' || $('#businessTypeId').val() == '0'){
						$('#businessTypeId').val(data.bizType);
					}
					$('#businessTypeId').change();
				}
			});
		});
		
		// ҵ������Change�¼�
		$('#businessTypeId').change(function(){
			$.ajax({url: 'taxAction.do?proc=get_object_json&entityId=' + $('#entityId').val() + '&businessTypeId=' + $(this).val() + '&date=' + new Date(),
				dataType : 'json',
				success: function(data){
					$('#taxId').val(data.taxId);
				}
			});
		});
		
		// ��������н���ڡ���ʼ���ڽ���һ�죨��ʼ1�ţ�����31�����⣩
		$('#circleStartDay').change(function(){
			if($('#circleStartDay').val() == 1){
				$('#circleEndDay').val(31);
			}else{
				$('#circleEndDay').val($('#circleStartDay').val() - 1);
			}
		});
		
		<logic:present name="orderBindContract">
			<logic:equal name="orderBindContract" value="true">
				$('#contractIdLI label').html($('#contractIdLI label').html() + ' <em>*</em>');
				orderBindContract = true;
			</logic:equal>
		</logic:present>
		
		// ��ӡ�������Ϣ����ť
		if($('#orderHeaderId').val() != ''){
// 			<kan:auth right="new" action="HRO_BIZ_CLIENT_ORDER_HEADER_IN_HOUSE">
				$('#btnList').after('<a name="copyInfo" title="������Ϣ" class="commonTools" id="copyInfo" onclick="if(confirm(\'�Ƿ�ȷ�����Ʋ�������ͬ ����������\')){link(\'clientOrderHeaderAction.do?proc=copy_object&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>&com.kan.token=' + $("#com.kan.token").val() + '\');}"><u>����</u></a>');
// 			</kan:auth>
		}
	})(jQuery);

	// ���ط�Ʊ��ַ������
	function loadInvoiceAddressOptions(invoiceAddressId){
		loadHtml('#invoiceAddressId', 'clientInvoiceAction.do?proc=list_object_options_ajax&invoiceAddressId=' + invoiceAddressId + '&clientId=' + $('#clientId').val(), getSubAction() == 'viewObject', 'if($("#invoiceAddressId").children().length == 2 && !getSubAction() == "viewObject"){$("#invoiceAddressId option:nth-child(2)").attr("selected" , "selected");}' );
	};
	
	// ���غ�ͬ������
	function loadContractOptions(contractId){
		loadHtml('#contractId', 'clientContractAction.do?proc=list_object_options_ajax&flag=order&contractId=' + contractId + '&clientId=' + $('#clientId').val(), getSubAction() == 'viewObject', '' );
	};
</script>

