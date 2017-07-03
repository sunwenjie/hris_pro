<%@ page pageEncoding="GBK" %>
<%@ page import="com.kan.base.util.KANUtil" %>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientOrderHeaderVO"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO" %>
<%@ page import="com.kan.base.web.renders.util.EmployeeUserManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY", "employeeContractSalaryForm", true ) %>
</div>
						
<script type="text/javascript">
	var status = null;
	
	(function($) {	
		// ��ʼ��״̬
		status = $('#status').val();
		<% 
			final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO )request.getAttribute( "employeeContractSalaryForm" );
		%>
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY", "init();",  "editCallBack();", null, "submitAdditionalCallBack();" ) %>
		// ��Ա��½�������ذ�ť
		<%= EmployeeUserManageRender.generateDisplayBtnJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY")%>
		
		<%
			if(request.getParameter("comefrom") != null && !request.getParameter("comefrom").trim().isEmpty() && request.getParameter("comefrom").trim().equals("setting")){
	  	%>
			   $('#btnList').click(function() {
					link('employeeContractSalaryAction.do?proc=list_object');
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
	  	
		// �ύ��ť����¼�
		$('#btnSubmit').click( function () { 
			if( validate_manage_primary_form() == 0){
				// ���ĵ�ǰForm��SubAction
				if( $('.manage_primary_form input#subAction').val() != 'createObject'){
					$('.manage_primary_form').attr('action', 'employeeContractSalaryAction.do?proc=modify_object');
				}
				$('.manage_primary_form input#subAction').val('submitObject');
				
	    		enableForm('manage_primary_form');
	    		submitForm('manage_primary_form');
			}
		});
		
		// ������Ϣ����¼�
		$('#HRO_BIZ_EMPLOYEE_CONTRACT_SALARY_CG13_Link').click(function(){
			if ('<bean:write name="ItemType"/>' == "13") {
				$('#isDeductLI').show();
				$('#divideTypeLI').hide();
			}else{
				$('#isDeductLI').hide();
			}
		});
		
		if ('<bean:write name="ItemType"/>' == "13") {
			$('#isDeductLI').show();
			$('#divideTypeLI').hide();
			$('#excludeDivideItemIdsLI').show();
		}else{
			$('#isDeductLI').hide();
		}

		// ����Ŀ��Change�¼���Ӱ����������Ƿ���ʾ
		$('#itemId').change(function() {
			$.ajax({url : 'itemAction.do?proc=get_object_json&itemId='+ $(this).val()+ '&date='+ new Date(),
					dataType : 'json',
					success : function(data) {
						if (data.itemType == '1') {
							$('#excludeDivideItemIdsLI').hide();
						} else {
							$('#excludeDivideItemIdsLI').show();
						}
						if (data.itemType == '13') {
							$('#isDeductLI').show();
							$('#divideTypeLI').hide();
							//��ȫ�ڼ������ѡ��Ϊ�������㡱
							$('#divideType').val("1");
						} else {
							$('#isDeductLI').hide();
						}
					}
			});
			if ($('#itemId').find("option:selected").text() == '��ʮ��н') {
				$('.formular').val('��������/365*((��ȹ���ĩ��-��ȹ�������+1)-(ȫ��ȫ��Сʱ��-ȫ�깤��Сʱ��)/8)');
			} else {
				$('.formular').val('');
			}
		});

		// �������Դ�����Change�¼�
		$('#baseFrom').change(function() {
			if ($(this).val() != 0) {
				$('#percentageLI').show();
				$('#fixLI').show();
			} else {
				$('#percentageLI').hide();
				$('#fixLI').hide();
			}
		});

		$('#baseFrom').change();

		// ����н��ʽ�����Change�¼�
		$('#salaryType').change(function() {
			if ($(this).val() == 1) {
				if ('<bean:write name="ItemType"/>' != "13") {
					$("#divideTypeLI").show();
				}
				$("#excludeDivideItemIdsLI").show();
			} else {
				$("#divideTypeLI").hide();
				$("#excludeDivideItemIdsLI").hide();
			}
		});

		// ��֧��Ƶ�ʡ�Ϊһ����ʱ����Ч���ںͽ������ڱ���
		$('#cycle').change(function() {
			if ($(this).val() == '13') {
				$('li#startDateLI,#endDateLI').find('label').append(
						'<em class="em_temp"> *</em>');
			} else {
				$('.em_temp').remove();
			}
		});

		// ������������Change�¼�
		$('#divideType').change(function() {
			if (($(this).val() == 2 || $(this).val() == 3|| $(this).val() == 5||$(this).val() == 4)
					&& $('#salaryType').val() == 1) {
				$.ajax({
					url : 'itemAction.do?proc=get_object_json&itemId='
							+ $('#itemId').val() + '&date='
							+ new Date(),
					dataType : 'json',
					success : function(data) {
						/* if (data.itemType == '1') {
								$('#excludeDivideItemIdsLI').hide();
						} else {
							$('#excludeDivideItemIdsLI').show();
						} */
						$('#excludeDivideItemIdsLI').show();
					}
				});
			} else {
				$("#excludeDivideItemIdsLI").hide();
			}
		});

		if ($('#itemId').val() != '0') {
			$('#salaryType').change();
			$('#divideType').change();
		}

		// Review by siuvan 2014-10-27
		// ҳ���ʼ�� ���ÿ�ʼʱ�� ����ʱ��
		$('#startDate').removeAttr('onfocus');
		$('#endDate').removeAttr('onfocus');
		//$('#startDate').attr('onFocus',"WdatePicker({minDate:'<bean:write name="employeeContractVO" property="startDate"/>',maxDate:'#F{ $dp.$D(\\\'endDate\\\') || $dp.$DV(\\\'<bean:write name="employeeContractVO" property="endDate"/>\\\') }'})" );
		//$('#endDate').attr('onFocus',"WdatePicker({maxDate:'<bean:write name="employeeContractVO" property="endDate"/>',minDate:'#F{ $dp.$D(\\\'startDate\\\') || $dp.$DV(\\\'<bean:write name="employeeContractVO" property="startDate"/>\\\') }' })" );
		$('#startDate').focus(function() {
			WdatePicker({
				minDate : '<bean:write name="employeeContractVO" property="startDate"/>',
				maxDate : $('#endDate').val() == '' ? '<bean:write name="employeeContractVO" property="endDate"/>'
						: $('#endDate').val(),
				oncleared : function() {
					$('#startDate').blur();
				},
				onpicked : function(dp) {
					checkHasConflictContractSalaryInOneItem();
				}
			});
		});

		$('#endDate').focus(function() {
			WdatePicker({
				minDate : $('#startDate').val() == '' ? '<bean:write name="employeeContractVO" property="startDate"/>'
						: $('#startDate').val(),
				maxDate : '<bean:write name="employeeContractVO" property="endDate"/>',
				oncleared : function() {
					$('#endDate').blur();
				},
				onpicked : function(dp) {
					checkHasConflictContractSalaryInOneItem();
				}
			});
		});
	})(jQuery);

	//  н�귽����ʼʱ���н�귽������ʱ�䣬ѡ��ʱ���¼�    ��֤��ʱ������Ƿ��Ѵ���н���Ŀ
	function checkHasConflictContractSalaryInOneItem() {
		var overlap = false;
		cleanError('startDate');
		if ($('#employeeId').val() != '' && $('#clientId').val() != ''
				&& $("#itemId").val() != "0" && $('#startDate').val() != ''
				&& $('#endDate').val() != ''
				&& $('.subAction').val() != 'viewObject') {
			$.ajax({
				url : 'employeeContractSalaryAction.do?proc=checkHasConflictContractSalaryInOneItem&employeeId=<bean:write name="employeeContractVO" property="employeeId"/>&startDate='
						+ $('#startDate').val()
						+ '&itemId='
						+ $('#itemId option:selected').val()
						+ '&contractId='
						+ $('#contractId').val()
						+ '&id='
						+ $('.manage_primary_form #id').val()
						+ '&endDate=' + $('#endDate').val() + '&flag=2',
				type : 'POST',
				async : false,
				success : function(data) {
					if (data == '1') {
						addError('startDate',
								'<bean:message bundle="public" key="error.time.period.exist.salary" />');
						overlap = true;
					} else {
						overlap = false;
					}
				}
			});
		}
		return overlap;
	};

	// ҳ����أ���ʼ��JS�¼�
	function init() {
		// ״̬���ɱ༭
		$('#status').attr('disabled','disabled');
		// ״̬Ϊ����||���ڹ��������ܱ༭
		if( '<bean:write name="employeeContractVO" property="status" />' != 1 && ( $('#status').val() == '1' || '<bean:write name="employeeContractSalaryForm" property="workflowId" />' != '')){
			$('#btnEdit').hide();
		}
		// �б�ťText�û��ɷ���
		$('#btnList').val('<bean:message bundle="public" key="button.back.fh" />');
		// ��ʼ���������鿴��ť
		loadWorkflowSeach('status','<bean:write name="employeeContractSalaryForm" property="workflowId"/>');
		
		// �����Inhouse
		if ('<bean:write name="role"/>' == 2) {
			var html = '<ol class="auto"><li>';
			html = html
					+ '<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal></label>';
			html = html
					+ '<span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span>';
			html = html + '</li><li>';
			html = html
					+ '<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.name" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.name" /></logic:equal></label>';
			html = html
					+ '<span><bean:write name="employeeContractVO" property="name" /></span></li></ol>';
			$('.required').parent().after(html);
			//$('#pageTitle').html('�Ͷ���ͬ - н�귽������ ');
		} else {
			$('.required').parent().after('<ol class="auto"><li><label><bean:write name="employeeContractVO" property="decodeFlag" />ID</label><span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span></li><li><label><bean:write name="employeeContractVO" property="decodeFlag" />����</label><span><bean:write name="employeeContractVO" property="name" /></span></li></ol>');
			$('#pageTitle').html('<bean:write name="employeeContractVO" property="decodeFlag" /> - н�귽������');
		}

		$("#menu_employee_ServiceAgreement").addClass('selected');

		// �������Ŀ����ѡ��
		<%
			final ClientOrderHeaderVO clientOrderHeaderVO = (ClientOrderHeaderVO)request.getAttribute("clientOrderHeaderVO");
			final String subAction = ((EmployeeContractSalaryVO)request.getAttribute("employeeContractSalaryForm")).getSubAction();
			String excludeDivideItemIds = "";
			if( BaseAction.VIEW_OBJECT.equalsIgnoreCase( subAction ) )
			{
			   // ����ǲ鿴����salary�����ȡ
			   excludeDivideItemIds = employeeContractSalaryVO.getExcludeDivideItemIds();
			}else
			{
			   // �����������Ӷ���/�������������
			   excludeDivideItemIds = clientOrderHeaderVO.getExcludeDivideItemIds();
			}
		%>
		// �Ƴ���ѡ��
		$("#excludeDivideItemIdsLI").find("span").remove();
		
		$("#excludeDivideItemIdsLI").find("label").after("<div style='width: 215px;'><%=KANUtil.getCheckBoxHTML(employeeContractSalaryVO.getExcludeDivideItems(), "excludeDivideItemIds", excludeDivideItemIds, subAction, "<br/>") %></div>");
	};
	
	// �༭��ť����ص�����
	function editCallBack(){
		// ״̬���ɱ༭
		$('#status').attr('disabled','disabled');
		// ״̬Ϊͣ����ʾ�ύ��ť
		if($('#status').val() == 2){
			$('#btnSubmit').show();
		}
		// ״̬Ϊ�������ر༭��ť�����Ҫ�޸ģ���Ҫ����Ա�� > ��н�����������
		else if($('#status').val() == 1){
			$('#btnEdit').hide();
		}
	};
	
	// �ύ��ť�����֤�ص�����
	function submitAdditionalCallBack(){
		// ��֧��Ƶ�ʡ�Ϊһ����ʱ����Ч���ںͽ������ڱ���
		if($('#cycle').val()=='13'){
			flag = flag + validate('startDate', true, 'common', 10, 0, 0, 0);
			flag = flag + validate('endDate', true, 'common', 10, 0, 0, 0);
		}
		// ��֤��ĿΨһ��
		if(checkHasConflictContractSalaryInOneItem() == true){
			flag = flag + 1;
		}
	}; 
</script>

