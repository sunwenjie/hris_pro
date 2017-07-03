<%@ page pageEncoding="GBK"%>
<%@page import="com.kan.hro.web.actions.biz.importExcel.EmployeeContractBatchAction"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>
<div id="content">	
	<!-- Information Search Form -->
	<%= SearchRender.generateSearch( request, EmployeeContractAction.getAccessAction(request, response), "employeeContractTempForm" ) %>
	<ol class="auto" id="tempStatusOL" style="display:none">
		<li>
			<label><bean:message bundle="public" key="public.status" /></label> 
			<select name="tempStatus" id = "tempStatus">
			<logic:iterate id="tempStatus" name="employeeContractTempForm" property="tempStatuses">
				<option value="<bean:write name="tempStatus" property="mappingId"/>" <logic:equal value="${tempStatus.mappingId}" name="employeeContractTempForm" property="tempStatus">selected</logic:equal> >
					<bean:write name="tempStatus" property="mappingValue"/>
				</option>
			</logic:iterate>
			</select>
		</li>
	</ol>
	
	<!-- paymentBatch-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="submit" action="<%=EmployeeContractBatchAction.accessAction%>">
					<input type="button" class="" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>
				<kan:auth right="back" action="<%=EmployeeContractBatchAction.accessAction%>">
					<input type="button" class="delete" name="btnDelete" id="btnDelete" value="�˻�" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
				<jsp:include page="/contents/business/employee/contract/table/listEmployeeContractHeaderTable.jsp" flush="true"/>
			</div>
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
	
</div>
<div id="handlePopupWrapper">
	<jsp:include page="/popup/handleEmployeeContract.jsp"></jsp:include>
</div>
<script type="text/javascript">	
	(function($) {
	// ���ö����˵�ѡ����ʽ
	$('#menu_employee_Modules').addClass('current');
	$('#menu_employee_Import').addClass('selected');
	$('#menu_employee_ImportLaborContract').addClass('selected');
	
	// �������������״̬��������Form
	$(".list_form ol").append($("#tempStatusOL").html());
	
	 // �޸�Action ��ַ
	 $(".list_form").attr('action','employeeContractTempAction.do?proc=list_object').append('<input type="hidden" name="batchId" id="batchId" value="<bean:write name="employeeContractTempForm" property="batchId" />" />');
		
	//���θ����¼�
	$('#btnSubmit').click( function(){
		if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
			if(confirm("ȷ���ύѡ�е��Ͷ���ͬ��")){
				$('.list_form').attr('action', 'employeeContractTempAction.do?proc=submit_temp');
				submitForm('list_form', "submitObjects", null, null, null, 'tableWrapper');
				$('#selectedIds').val('');
			}
		}else{
			alert("��ѡ��Ҫ�ύ�����Ρ�");
		}
	});
	
	// �����˻��¼�
	$('#btnDelete').click( function(){
		if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
			if(confirm("ȷ���˻�ѡ�е��Ͷ���ͬ��")){
				$('.list_form').attr('action', 'employeeContractTempAction.do?proc=rollback_temp');
				submitForm('list_form', "rollbackObjects", null, null, null, 'tableWrapper');
				$('#selectedIds').val('');
			}
		}else{
			alert("��ѡ��Ҫ�˻ص����Ρ�");
		}
	});
	
	})(jQuery);
	
	//
	loadHtml('#orderId', 'clientOrderHeaderAction.do?proc=list_object_options_ajax&orderId=<bean:write name="employeeContractTempForm" property="encodedOrderId" />', false );
	
	// Esc�����¼� - ���ص�����
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#importDIVModalId').hide();
	    }
	});
</script>
