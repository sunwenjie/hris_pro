<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractSBVO"%>
<%@ page import="com.kan.base.web.renders.util.EmployeeUserManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SB", "employeeContractSBForm", true ) %>
</div>
							
<script type="text/javascript">
	var status = null;

	(function($) {	
		// ��� ��Inhouse������sbbase
		<%
			if(KANConstants.ROLE_IN_HOUSE.equals(BaseAction.getRole(request,null))){
			   %>
				$("#sbBaseLI").hide();
			   <%
			}
		%>
		// ��ʼ��״̬
		status = $('#status').val();
		
		// ״̬���˱�����Ĭ�ϲ��ɱ༭
		$('#status').attr('disabled', 'disabled');
		$('#endDate').attr('disabled','disabled');
		
		// ����籣�����𷽰���� - ��Ҫ��ʾ�ύ��ť
		if( getSubAction() == 'createObject' ){
			$('#btnSubmit').show();
			$('#btnSubmit').val('<bean:message bundle="public" key="button.submit.to.claim" />');
		}

		// �ӱ��ύ���˱��ύ�����˱�״̬���ܱ༭||���ڹ��������ܱ༭
		if( '<bean:write name="employeeContractSBForm" property="workflowId" />' != '' || status == '1' ||status == '4'){
			$('#btnEdit').hide();
		}
		
		// JS of the List
		<%
			// ��ʼ��
			final String initCallBack = "$('#sbSolutionId').change();init();";
			// ����༭��֤
			final String editCallBack = "editCallBackInit();";
			// ���ύ�Ķ�����֤
			final String submitAdditionalCallBack = "flag = flag + validateBase(); flag = flag + validateVendorService();if($('.error')[0]){$('.error')[0].focus();}";
		%>
		
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SB", initCallBack, editCallBack, null, submitAdditionalCallBack ) %>
		 //��Ա��½�������ذ�ť
		<%= EmployeeUserManageRender.generateDisplayBtnJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SB")%>
		$('#btnList').val('<bean:message bundle="public" key="button.back.fh" />');
		
		// ��ʼ���������鿴��ť
		loadWorkflowSeach('status','<bean:write name="employeeContractSBForm" property="workflowId"/>');
		
		<%
			if(request.getParameter("comefrom") != null && !request.getParameter("comefrom").trim().isEmpty() && request.getParameter("comefrom").trim().equals("setting")){
	  	%>
			   $('#btnList').click(function() {
					link('employeeContractSBAction.do?proc=list_object');
				});
		<%
			}else{
	  	%>
		$('#btnList').click(function() {
			link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>');
		});
		<%
			}
	  	%>
	  	
		// �ύ��ť�¼�
		$('#btnSubmit').click( function () { 
			$("#startDate").removeClass('error');
			$("#endDate").removeClass('error');
			$(".startDate_error").remove();
			$(".endDate_error").remove();
			
			// ��������籣����ӱ����ڲ���Ϊ��
			if(status == '0' && $('#startDate').val() == ''){
				$("#startDate").addClass('error');
				$(".startDate_error").remove();
				$("#startDate").after('<label class="error startDate_error">&#8226; ����Ϊ�գ�</label>');
				return;
			}
			
			// ���걨�ӱ������������˱����ڲ���Ϊ��
			if( status == '2' || status == '3'){
				var errorCount = 0;
				
				if( $('#startDate').val() == '' ){
					$("#startDate").addClass('error');
					$(".startDate_error").remove();
					$("#startDate").after('<label class="error startDate_error">&#8226; ����Ϊ�գ�</label>');
					errorCount = errorCount + 1;
				}
				
				if( $('#endDate').val() == '' ){
					$("#endDate").addClass('error');
					$(".endDate_error").remove();
					$("#endDate").after('<label class="error endDate_error">&#8226; ����Ϊ�գ�</label>');
					errorCount = errorCount + 1;
				}
				
				if( errorCount != 0 ){
					return;
				}
			}
			
			// ���˱����  Added by siuvan at 2014-08-06
			if( status == '6'){
				
			}
			
			if(validate_manage_primary_form() == 0){
				// ���ĵ�ǰForm��SubAction
				if(getSubAction() != 'createObject'){
					$('.manage_primary_form').attr('action', 'employeeContractSBAction.do?proc=modify_object');
				}
				$('.manage_primary_form input#subAction').val('submitObject');
				
	    		enableForm('manage_primary_form');
	    		submitForm('manage_primary_form');
			}
		});
		
		// ���ع�Ӧ��������
		loadHtml('.vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>');
		// ���ع�Ӧ�̷���������
		loadHtml('.vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>&serviceId=<bean:write name="employeeContractSBForm" property="vendorServiceId"/>');
		// �����籣��������ϸTabҳ
		loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&employeeSBId=<bean:write name="employeeContractSBForm" property="employeeSBId"/>&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>', true, null);
		
		
		// �籣�����𷽰�Change�¼�
		$("#sbSolutionId").change(function(){
			if(getSubAction() != 'viewObject') {
				$.ajax({
					url:"employeeContractSBAction.do?proc=sbSolution_change_ajax&orderHeaderId=<bean:write name="employeeContractVO" property="orderId"/>&contractId=<bean:write name="employeeContractVO" property="encodedId"/>&sbSolutionId=" + $(this).val(),
					success:function(html){
						$('#personalSBBurden').val(html.trim());
					}
				});

				loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&contractId=<bean:write name="employeeContractVO" property="encodedId"/>&employeeSBId=<bean:write name="employeeContractSBForm" property="employeeSBId"/>&sbSolutionId=' + $("#sbSolutionId").val(), false, null);
			}
			
			// ���ع�Ӧ��������
			loadHtml('.vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=' + $("#sbSolutionId").val() + '&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>', null, '$("#vendorId").change();');
		});
		
		// �����ViewObject,�����籣�����������򣨼�������������
		if(getSubAction() == 'viewObject') {
			var callback = "$(\"#sbSolutionId\").trigger('change');";
			loadHtml('.sbSolutionId', 'employeeContractSBAction.do?proc=list_object_options_manage_ajax&contractId=<bean:write name="employeeContractSBForm" property="encodedContractId"/>&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>', null, callback);
		}
		// �����籣������������ֻ����δ��ӵģ�
		else{
			var callback = "$(\"#sbSolutionId\").trigger('change');";
			loadHtml('.sbSolutionId', 'employeeContractSBAction.do?proc=list_object_options_manage_ajax&contractId=<bean:write name="employeeContractVO" property="encodedId"/>', null, callback);
		}
		
		$("#vendorId").change(function(){
			// ���ع�Ӧ�̷���������
			loadHtml('.vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=' + $('#sbSolutionId').val() + '&vendorId=' + $('#vendorId').val() + '&serviceId=<bean:write name="employeeContractSBForm" property="vendorServiceId"/>',false,function(){
				if($('.vendorServiceId option').size()==2){
					$('.vendorServiceId option').eq(1).attr('selected','seleccted');
				}
			});
		});
	})(jQuery);
	
	// ��ǰ�Ƿ���ҪDisable
	function getDisable(){
		if(getSubAction() == 'viewObject'){
			return true;
		}else{
			return false;
		}
	};
	
	// ��֤����
	function validateBase(){
		var error = 0;
		$(':input[maxvalue][minvalue]').each(function(){
			if( !(/^[0-9]+(.[0-9]+)?$/.test($.trim($(this).val()))) ){
				//��ʽ��֤����
				$(this).addClass("error");
				error++;
			}else {
				if( parseFloat($(this).attr('minvalue')) <= parseFloat($(this).val()) && parseFloat($(this).val()) <= parseFloat($(this).attr('maxvalue'))){
					//��֤ͨ��
					$(this).removeClass("error");
				}else{
					//��֤ʧ��
					$(this).addClass("error");
					error++;
				}
			}
		});
		
		return error;
	};

	// ��֤��Ӧ�̷���������
	function validateVendorService(){
		$('.vendorServiceId').removeClass("error");
		$('.vendorServiceId_error').remove();
		if($('.vendorId').val()!=0 && $('.vendorServiceId').val()==0 ){
			$('.vendorServiceId').addClass("error");
			$('.vendorServiceId').after('<label class="error vendorServiceId_error">&#8226;��ѡ��</label>');
			return 1;
		}
		return 0;
	};	
	
	// ����༭��֤
	function editCallBackInit(){
		// ״̬���ɱ༭
		$('#status').attr('disabled', 'disabled');	
		enableLinkById('#convenientSettingA');
		
		// ��������籣�������˱����ڲ�����д
		if(status == '0'){
			$('#btnSubmit').show();
			$('#btnSubmit').val('<bean:message bundle="public" key="button.submit.to.claim" />');
			$('#endDate').attr('disabled', 'disabled');	
		}
		
		// ���걨�ӱ� - ���������˱��������򡢵����˵������
		if(status == '2'){
			$('#btnSubmit').show();
			$('#btnSubmit').val('<bean:message bundle="public" key="button.submit.to.stop.claim" />');
			$('#sbSolutionId').attr('disabled', 'disabled');
		}
		
		// ��������
		if(status == '3'){
			$('#btnSubmit').show();
			$('#btnSubmit').val('<bean:message bundle="public" key="button.submit.to.stop.claim" />');
			$('#startDate,#sbSolutionId').attr('disabled', 'disabled');
		}
		
		// ���걨�˱�
		if(status == '5'){
			$('#startDate,#sbSolutionId').attr('disabled', 'disabled');
		}
	
		// Add by siuvan @2014-08-06
		// ���˱�����༭��ť����¼�ʱ��1�����ر��水ť��2����ʾ�ӱ��ύ��ť��3��������ڣ��˱�����disabled����4�����°����ڿؼ����ӱ����ڲ���С��֮ǰ���˱����ڣ�
		<logic:notEmpty name="employeeContractSBForm" property="encodedId">
		if(status == '6'){
			$('#btnEdit').hide();
			$('#btnSubmit').show();
			$('#btnSubmit').val('<bean:message bundle="public" key="button.submit.to.claim" />');
			$('#startDate').val("");
			$('#endDate').val("");
			$('#endDate,#sbSolutionId').attr('disabled', 'disabled');
			
			var endDate = getAppointDate('<bean:write name="employeeContractSBForm" property="endDate" />', 1);
			// ȡ���ӱ�����onfocus�¼�
			$('#startDate').attr('onfocus',"WdatePicker({minDate:'"+ endDate +"'})");
		}
		</logic:notEmpty>
	};
	
	function init(){
		// �����Inhouse
		if('<bean:write name="role"/>' == 2){
			var html = '<ol class="auto"><li>';
			html = html + '<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal></label>';
			html = html + '<span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span>';
			html = html + '</li><li>';
			html = html + '<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.name" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.name" /></logic:equal></label>';
			html = html + '<span><bean:write name="employeeContractVO" property="name" /></span></li></ol>';
			$('.required').parent().after( html );
			//$('#pageTitle').html('�Ͷ���ͬ - �籣�����𷽰����� ');
		}
		else{
			$('.required').parent().after(
					'<ol class="auto"><li><label><bean:write name="employeeContractVO" property="decodeFlag" />ID</label><span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span></li><li><label><bean:write name="employeeContractVO" property="decodeFlag" />����</label><span><bean:write name="employeeContractVO" property="name" /></span></li></ol>'
			);
			//$('#pageTitle').html('<bean:write name="employeeContractVO" property="decodeFlag" /> - �籣�����𷽰�');
		}
		
		$("#menu_employee_ServiceAgreement").addClass('selected');
	};

	function getAppointDate( date, appoint ){
		var newDate = new Date(date);
		var hs = newDate.getTime() + parseInt(appoint) * 1000*60*60*24;
		var returnDate = new Date();
		returnDate.setTime(hs);
		
		var year = returnDate.getFullYear();
		var month = returnDate.getMonth() + 1;
		var day = returnDate.getDate();
		
		if( month < 10){
			month = "0" + month;
		}
		
		if( day < 10){
			day = "0" + day;
		}
		
		return year + "-" + month + "-" + day;
	};
</script>

