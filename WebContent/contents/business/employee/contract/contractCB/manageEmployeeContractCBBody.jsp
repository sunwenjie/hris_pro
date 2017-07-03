<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractCBVO"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.EmployeeUserManageRender"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_CONTRACT_CB", "employeeContractCBForm", true ) %>
</div>
							
<script type="text/javascript">
    var status = null;

	(function($) {	
		// ��ʼ��״̬
		status = $('#status').val();
		
		// ״̬���˹�����Ĭ�ϲ��ɱ༭
		$('#status').attr('disabled', 'disabled');
		$('#endDate').attr('disabled','disabled');
		
		// ����̱�������� - ��Ҫ��ʾ�ύ��ť
		if( getSubAction() == 'createObject' ){
			$('#btnSubmit').show();
			$('#btnSubmit').val("<bean:message bundle="public" key="button.purchase.submit" />");
		}

		// �깺�ύ,�˹��ύ,���˹���״̬���ܱ༭
		if(status == '1' ||status == '4' || status == '5'){
			$('#btnEdit').hide();
		}
		
		// JS of the List
		<%
			// ��ʼ��
			final String initCallBack = "$('#solutionHeaderId').change();init();";
			// ����༭��֤
			final String editCallBack = "editCallBackInit();";
			
			// ���ύ�Ķ�����֤
			final String submitAdditionalCallBack = "flag = flag + validateOthers();";
		%>
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_CB", initCallBack, editCallBack, null,null) %>
		 //��Ա��½�������ذ�ť
		<%= EmployeeUserManageRender.generateDisplayBtnJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_CB")%>
		<%
			final EmployeeContractCBVO employeeContractCBVO = (EmployeeContractCBVO)request.getAttribute("employeeContractCBForm");
			final String contractId = employeeContractCBVO == null ? "" : employeeContractCBVO.getContractId();
			final String targateId = employeeContractCBVO == null ? "" : employeeContractCBVO.encodedField(contractId);
			final String solutionId = employeeContractCBVO == null ? "" : employeeContractCBVO.getSolutionId();
		%> 
		
		$('#btnList').val('<bean:message bundle="public" key="button.back.fh" />');
		
		// ��ʼ���������鿴��ť
		loadWorkflowSeach('status','<bean:write name="employeeContractCBForm" property="workflowId"/>');

		<%
			if(request.getParameter("comefrom") != null && !request.getParameter("comefrom").trim().isEmpty() && request.getParameter("comefrom").trim().equals("setting")){
	  	%>
			   $('#btnList').click(function() {
				   if(agreest())
					link('employeeContractCBAction.do?proc=list_object');
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
			
			if(status == '0' && $('#startDate').val() == ''){
				$("#startDate").addClass('error');
				$("#startDate").after('<label class="error startDate_error">&#8226; ����Ϊ�գ�</label>');
				return;
			}
			
			if((status == '2' || status == '3') && $('#endDate').val() == ''){
				$("#endDate").addClass('error');
				$("#endDate").after('<label class="error endsDate_error">&#8226; ����Ϊ�գ�</label>');
				return;
			}
			
			if(validate_manage_primary_form() == 0){
				// ���ĵ�ǰForm��SubAction
				if(getSubAction() != 'createObject'){
					$('.manage_primary_form').attr('action', 'employeeContractCBAction.do?proc=modify_object');
				}
				$('.manage_primary_form input#subAction').val('submitObject');
				
	    		enableForm('manage_primary_form');
	    		submitForm('manage_primary_form');
			}
		});
		
		// ѡ���̱����������� ��ȫ����Ѻ� ��ȫ�¼Ʒ�
		$("#solutionId").change(function(){
			var solutionId = $("#solutionId").val();
			var orderId = <bean:write name="employeeContractVO" property="orderId"/>;
			$.post("clientOrderCBAction.do?proc=get_object_json",{'solutionId':solutionId,'orderId':orderId},function(data){
				if(data.success == 'true'){
					$("#freeShortOfMonth").val(data.freeShortOfMonth);
					$("#chargeFullMonth").val(data.chargeFullMonth);
				}else{
					$("#freeShortOfMonth").val('0');
					$("#chargeFullMonth").val('0');
				}
			},'json');
		});
	
		$('#chargeFullMonthLI').hide();
		//�� ��ȫ����� �� ���¼Ʒ� �¼�
		$('#freeShortOfMonth').change(function(){
			if( $(this).val() == 2){
				$('#chargeFullMonthLI').show();
			}else{
				$('#chargeFullMonth').val('0');
				$('#chargeFullMonthLI').hide();
			}
		});
		
		$('#freeShortOfMonth').trigger('change'); 
		
		// �����ViewObject,�����̱������򣨼�������������
		if(getSubAction() == 'viewObject') {
			loadHtml('.solutionId', 'clientOrderCBAction.do?proc=list_object_options_ajax&solutionId=<bean:write name="employeeContractCBForm" property="solutionId"/>', null, null);
		}
		// �����̱�������ֻ����δ��ӵģ�
		else{
			loadHtml('.solutionId', 'employeeContractCBAction.do?proc=list_object_options_manage_ajax&contractId=<bean:write name="employeeContractVO" property="encodedId"/>', null, null);
		}		
	})(jQuery);
	
	function validateOthers(){
		var chargeFullMonthVal = $('#chargeFullMonth').val();
		var freeShortOfMonthVal = $('#freeShortOfMonth').val();
		
		if(freeShortOfMonthVal==chargeFullMonthVal && freeShortOfMonthVal==1){
			alert('\'��ȫ�����\'  ��  \'���¼Ʒ�\'  ����ͬʱѡ��  \'��\'');
			return 1;
		}
		
		return 0;
	};
	
	// ����༭��֤
	function editCallBackInit(){
		// ״̬���ɱ༭
		$('#status').attr('disabled', 'disabled');	
		
		// ��������̱����˱����ڲ�����д
		if(status == '0'){
			$('#btnSubmit').show();
			$('#btnSubmit').val("<bean:message bundle="public" key="button.purchase.submit" />");
			$('#endDate').attr('disabled', 'disabled');	
		}
		
		// ���깺- ���������˱��������򡢵����˵������
		if(status == '2'){
			$('#btnSubmit').show();
			$('#btnSubmit').val("<bean:message bundle="public" key="button.return.submit" />");
			$('#startDate,#solutionHeaderId,#solutionId').attr('disabled', 'disabled');
		}
		
		// ��������
		if(status == '3'){
			$('#btnSubmit').show();
			$('#btnSubmit').val("<bean:message bundle="public" key="button.return.submit" />");
			$('#startDate,#solutionHeaderId').attr('disabled', 'disabled');
		}
		
		$('#solutionId').attr('disabled','disabled');
	};
	
	function init(){
		// �����inHouse
		if('<bean:write name="role"/>' == 2){
			var html = '<ol class="auto"><li>';
			html = html + '<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal></label>';
			html = html + '<span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span>';
			html = html + '</li><li>';
			html = html + '<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.name" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.name" /></logic:equal></label>';
			html = html + '<span><bean:write name="employeeContractVO" property="name" /></span></li></ol>';
			$('.required').parent().after( html );
			//$('#pageTitle').html('�Ͷ���ͬ - �̱��������� ');
		}
		else{
			$('.required').parent().after(
					'<ol class="auto"><li><label><bean:write name="employeeContractVO" property="decodeFlag" />ID</label><span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span></li><li><label><bean:write name="employeeContractVO" property="decodeFlag" />����</label><span><bean:write name="employeeContractVO" property="name" /></span></li></ol>'
			);
			$('#pageTitle').html('<bean:write name="employeeContractVO" property="decodeFlag" /> - �̱�����');
		}
		
		$("#menu_employee_ServiceAgreement").addClass('selected');
	};
</script>

