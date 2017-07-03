<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.base.web.renders.util.EmployeeUserManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SETTLEMENT", "employeeContractSettlementForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {	
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SETTLEMENT", "init();", null, null, "" ) %>
		//��Ա��½�������ذ�ť
		<%= EmployeeUserManageRender.generateDisplayBtnJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SETTLEMENT")%>
		$('#btnList').val('<bean:message bundle="public" key="button.back.fh" />');
		
		$('#btnList').click(function() {
			if(agreest())
			link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>');
		});
	
		$("#itemId").change(function(){
			if($(this).val()!='0'){
				$("#baseFromLI").hide();
			}else{
				$("#baseFromLI").show();
			}
		});
		
		$("#baseFrom").change(function(){
			if($(this).val()!='0'){
				$("#itemIdLI").hide();
			}else{
				$("#itemIdLI").show();
			}
		});
	})(jQuery);
	
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
			//$('#pageTitle').html('�Ͷ���ͬ - �ɱ����� ');
		}
		else{
			$('.required').parent().after(
					'<ol class="auto"><li><label><bean:write name="employeeContractVO" property="decodeFlag" />ID</label><span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span></li><li><label><bean:write name="employeeContractVO" property="decodeFlag" />����</label><span><bean:write name="employeeContractVO" property="name" /></span></li></ol>'
			);
			$('#pageTitle').html('<bean:write name="employeeContractVO" property="decodeFlag" /> - Ӫ��');
			$("#resultCapLI").find("label").html("Ӫ������");
			$("#resultFloorLI").find("label").html("Ӫ������");
		}
		$("#menu_employee_ServiceAgreement").addClass('selected');

		// Ԫ��������
		loadItems('<bean:write name="employeeContractSettlementForm" property='employeeSettlementId'/>','<bean:write name="employeeContractSettlementForm" property='itemId'/>');
		
		$("#baseFrom").change();
	};
	
	function loadItems(id,itemId){
		$.post("itemAction.do?proc=list_options_ajax",{"id":id,"itemId":itemId},function(data){
			jQuery(data).appendTo("#itemId");
			$("#itemId").change();
		},"text");
	};
	
</script>

