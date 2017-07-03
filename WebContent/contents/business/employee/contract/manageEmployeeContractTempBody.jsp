<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractVO"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<% 
	final EmployeeContractVO employeeContractVO = (EmployeeContractVO)request.getAttribute("employeeContractTempForm"); 
%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, EmployeeContractAction.getAccessAction( request, response ), "employeeContractTempForm", true ) %>
	<input id="comeFrom" name="comeFrom" type="hidden" value="<%=(request.getParameter("comeFrom")==null||request.getParameter("comeFrom").isEmpty())?"":request.getParameter("comeFrom")%>"/>
	<input id="encodedEmployeeId" name="encodedEmployeeId" type="hidden" value="" />
	<input id="encodedOrderId" name="encodedOrderId" type="hidden" value="" />
	<input id="encodedClientId" name="encodedClientId" type="hidden" value="" />
</div>

<%-- �Ƿ��������������ҳ�� --%>
<input id="rePageFlag_orderId" name="rePageFlag_orderId" type="hidden" value="false"/>
<input id="rePageFlag_employeeId" name="rePageFlag_employeeId" type="hidden" value="false"/>

<script type="text/javascript">
	(function($) {	
		// ���ö����˵�ѡ����ʽ
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Import').addClass('selected');
		$('#menu_employee_ImportLaborContract').addClass('selected');
		var overlap = false;
		// JS of the List
		<%
			final StringBuffer initCallBack = new StringBuffer();
			initCallBack.append("init();");
			initCallBack.append("$('#entityId').attr('disabled', 'disabled');");
			initCallBack.append("$('#businessTypeId').attr('disabled', 'disabled');");

			if(request.getAttribute("orderIdError") != null)
			{
				final String orderIdError = (String)request.getAttribute("orderIdError");
				initCallBack.append("addError('orderId', '" + orderIdError + "');");
				initCallBack.append("$('#rePageFlag_orderId').val('true');");
			}
			
			if(request.getAttribute("employeeIdError") != null)
			{
				final String employeeIdError = (String)request.getAttribute("employeeIdError");
				initCallBack.append("addError('employeeId', '" + employeeIdError + "');");
				initCallBack.append("$('#rePageFlag_employeeId').val('true');");
			}
		
		%>
		
		<%= ManageRender.generateManageJS( request, EmployeeContractAction.getAccessAction( request, response ), initCallBack.toString(), null, null,null) %>

		$('#menu_employee_ServiceAgreement').removeClass('selected');
		// ����������ӡ��鿴��Ա��Ϣ�������鿴������Ϣ��
		if(getSubAction() != 'createObject'){
			if( $('#rePageFlag_orderId').val() == 'false')
			{
				$('#orderIdLI label:eq(0)').append(' <a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="employeeContractTempForm" property="encodedOrderId" />\');" ><img src="images/find.png" title="�鿴������¼" /></a>');
			}
			if( $('#rePageFlag_employeeId').val() == 'false')
			{
				$('#employeeIdLI label:eq(0)').append(' <a onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name="employeeContractTempForm" property="encodedEmployeeId" />\');" ><img src="images/find.png" title="�鿴��Ա��¼" /></a>');
			}
		}
		
		loadServiceContractTemplateOptions('<bean:write name="employeeContractTempForm" property="templateId"/>');
		
		// ���ذ�ť
		$('#reAdd,#btnAdd,#btnEdit,#btnNext').hide();
		$('#btnList').val("����").click(function(){
			link('employeeContractTempAction.do?proc=list_object&batchId=<bean:write name="employeeContractTempForm" property="batchId" />');
		});
		
		// Load Special Info
		loadHtml('#special_info', 'employeeContractTempAction.do?proc=list_special_info_html&contractId=' + $('#id').val() + '&flag=' + $('#flag').val(), getDisable(),function(){
			// ���ó�����
			$('#tab a').attr('onClick','');
		});
		
		// ��ӡ�������Ա��Ϣ��
		$('#employeeId').after('<a id="employeeSearch" onclick="popupEmployeeSearch();" class="kanhandle"><img src="images/search.png" title="������Ա��¼" /></a>');
		
		// ��ӡ�����������Ϣ��
		$('#orderId').after('<a id="orderSearch" onclick="popupOrderSearch();" class="kanhandle"><img src="images/search.png" title="����������¼" /></a>');
		
		// ��ʼ�����ſؼ�
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
		
		// �󶨲���Change�¼�
		$('.branch').change( function () { 
			branchChange('branch', null, 0, 'owner');
		});	
		
		// �󶨷���ʵ��change�¼�
		$('#entityId').change(function (){
			loadLaborContractTemplateOptions('<bean:write name="employeeContractTempForm" property="templateId"/>');
			loadContractOptions('<bean:write name="employeeContractTempForm" property="masterContractId"/>');
		});
		
		//	��ҵ������change�¼�
		$('#businessTypeId').change(function (){
			loadLaborContractTemplateOptions('<bean:write name="employeeContractTempForm" property="templateId"/>');
		});
		
		function hasConflictContractInOneClient(){
			// ��֤�Ƿ���ڷ���Э��
			if($('#employeeId').val()!='' && $('#clientId').val() !='' && $('#entityId').val()!='' && $('#entityId').val()!=0 && $('#startDate').val() != '' && $('#endDate').val() != '' && $('.subAction').val()!='viewObject' ){
				$.post('employeeContractAction.do?proc=hasConflictContractInOneClient',
						{'clientId':$('#clientId').val(),
						 'employeeId':$('#employeeId').val(),
						 'startDate':$('#startDate').val(),
						 'contractId':$('#contractId').val(),
						 'endDate':$('#endDate').val(),
						 'entityId':$('#entityId').val(),
						 'flag':'2'},
				function(data){
						if(data=='1'){
							$('.startDate_error2').remove();
							$('#startDate').after("<label class='error startDate_error2 '>&#8226; ��ʱ������Ѵ����Ͷ���ͬ</label>");
							overlap = true;
						}else{
							$('.startDate_error2').remove();
							overlap = false;
						}
				},'text');
			};
			return false;
		}
		

	})(jQuery);
	
	// ��ǰ�Ƿ���ҪDisable
	function getDisable(){
		if(getSubAction() == 'viewObject'){
			return true;
		}else{
			return false;
		}
	};
	
	// ��������ͬ�������¼�
	function loadContractOptions(masterContractId){
		loadHtml('#masterContractId', 'employeeContractAction.do?proc=list_object_options_ajax&contractId=' + masterContractId + '&employeeId=' + $('#employeeId').val()+ '&entityId=' + $('#entityId').val(), getDisable() );
	};
	
	// ���غ�ͬģ���������¼�
	function loadServiceContractTemplateOptions(templateId){
		loadHtml('#templateId', 'laborContractTemplateAction.do?proc=list_object_options_ajax&templateId=' + templateId + '&entityId=' + $('#entityId').val() + '&businessTypeId=' + $('#businessTypeId').val() + '&clientIdPage='+$('#clientId').val()+'&contractTypeId=2', getDisable() );
	};	
	
	// ����ֱ�߾����������¼�
	function loadLineManagerOptions(lineManagerId){
		loadHtml('#lineManagerId', 'clientContactAction.do?proc=list_object_options_ajax&contactId=' + lineManagerId + '&clientId=' + $('#encodedClientId').val(), getDisable() );
	};

	function generateContract(){
		if(validate_manage_primary_form() == 0){
			// ��֤��ͬģ����߸�������Ϊ��
			if($('#templateId').val()==0 && $('#attachmentsOL #attachmentArray').size()==0){
				alert("ȱ�ٺ�ͬģ����߸�����");
				return false;
			}	
			
			// ҳ���޸����ݱ�������
			if(getSubAction() == 'modifyObject' && !confirm("ҳ���޸����ݽ������棡")){
				return false;
			}
			
			// Update the form action
			$('.manage_primary_form').attr('action', 'employeeContractAction.do?proc=generate_contract');
			
			// Enable the form
			enableForm('manage_primary_form');
			submit('manage_primary_form');
		};
	};
	
	function init(){
		if($(".subAction").val()=='viewObject'){
			// ���  �ٴ���� ��ť
			$("#btnEdit").before("<input type='button' name='reAdd' id='reAdd' value='����' onclick='reAdd();'>");
			$("#pageTitle").html("��Ա - ��ǲЭ���ѯ");
		}else if($(".subAction").val()=='createObject'){
			$("#pageTitle").html("��Ա - ��ǲЭ������");
		}
	};
	function reAdd(){
		link("employeeContractAction.do?proc=to_objectNew&flag=2&comeFrom=2 />");
	}
	function doEdit(){
		$("#pageTitle").html("��Ա - ��ǲЭ��༭");
	}
</script>
				
