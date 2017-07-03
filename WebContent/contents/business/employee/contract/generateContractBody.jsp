<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
	<div id="contract" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<logic:equal value="1" name="role">
					<bean:message bundle="business" key="business.employee.contract1.content.view" />
				</logic:equal>
				<logic:equal value="2" name="role">
					<bean:message bundle="business" key="business.employee.contract2.content.view" />
				</logic:equal>
			</label>
		</div>
		<div class="inner" style="overflow: auto;">
			<div class="top">
				<kan:auth right="modify" action="<%=EmployeeContractAction.getAccessAction(request,response)%>">
					<input type="button" class="" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" style="display: none;" /> 
				</kan:auth>
				<kan:auth right="export" action="<%=EmployeeContractAction.getAccessAction(request,response)%>">
					<input type="button" class="" name="btnExportPDF" id="btnExportPDF" value="<bean:message bundle="public" key="button.export.pdf" />" style="display: none;" onclick="exportPDF();" />
				</kan:auth>
				<kan:auth right="submit" action="<%=EmployeeContractAction.getAccessAction(request,response)%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" style="display: none;" />  
				</kan:auth>
				<kan:auth right="previous" action="<%=EmployeeContractAction.getAccessAction(request,response)%>">
					<input type="button" class="" name="btnPrevious" id="btnPrevious" value="<bean:message bundle="public" key="button.back" />" />
				</kan:auth>
				<logic:notEqual name="role" value="5">
					<kan:auth right="sealed" action="<%=EmployeeContractAction.getAccessAction(request,response)%>">
						<input type="button" class="function" name="btnChop" id="btnChop" value="<bean:message bundle="public" key="button.sealed" />"  style="display: none;" /> 
					</kan:auth>
					<kan:auth right="archive" action="<%=EmployeeContractAction.getAccessAction(request,response)%>">
						<input type="button" class="function" name="btnArchive" id="btnArchive" value="<bean:message bundle="public" key="button.archive" />"  style="display: none;" />
					</kan:auth>
				</logic:notEqual>
				<kan:auth right="list" action="<%=EmployeeContractAction.getAccessAction(request,response)%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
				<kan:auth right="export" action="<%=EmployeeContractAction.getAccessAction(request,response)%>">
					<a id="exportPDF" name="exportPDF" class="commonTools" title="����PDF�ļ�" style="display: none;" onclick="exportPDF();"><img src="images/appicons/pdf_16.png" /></a>
				</kan:auth>
			</div>
			<div class="kantab" style="width: 794px; padding: 75px 56px 75px 56px;">
				<html:form action="employeeContractAction.do?proc=modify_object_step2" styleClass="manageContract_form">
					<%= BaseAction.addToken( request ) %>
					<input type="hidden" name="id" id="id" value="<bean:write name="employeeContractForm" property="encodedId" />" />
					<input type="hidden" name="subAction" id="subAction" value="<bean:write name="employeeContractForm" property="subAction" />" />
					<input type="hidden" name="status" id="status" value="<bean:write name="employeeContractForm" property="status" />" />
					<input type="hidden" name="flag" id="flag" value="<bean:write name="employeeContractForm" property="flag" />" />
					<bean:write name="employeeContractForm" property="content" filter="false" />
					<input type="hidden" name="employeeId" id="employeeId" value="<bean:write name="employeeContractForm" property="employeeId" />" />
					<input type="hidden" name="orderId" id="orderId" value="<bean:write name="employeeContractForm" property="orderId" />" />
				</html:form>
			</div>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_employee_Modules').addClass('current');	
		$('#menu_employee_Labor').addClass('selected');
		$('#menu_employee_Contract').addClass('selected');

		// �༭��ť�¼�
		$('#btnEdit').click( function () { 
			if($('.manageContract_form input#subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageContract_form');
				// ����Subaction
        		$('.manageContract_form input#subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// ����Page Title
        		<logic:equal name="role"  value="2" >$('#pageTitle').html('<bean:message bundle="business" key="business.employee.contract2.content.edit" />');</logic:equal>
				<logic:equal name="role"  value="1" >$('#pageTitle').html('<bean:message bundle="business" key="business.employee.contract1.content.edit" />');</logic:equal>
        	}else{
				submit('manageContract_form');
        	}
		});
		
		// ��һ����ť�¼�
		$('#btnPrevious').click(function(){
			link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractForm" property="encodedId" />');
		});
		
		// �ύ��ť�¼�
		$('#btnSubmit').click( function () { 
			// ���ĵ�ǰForm��SubAction
			$('.manageContract_form input#subAction').val('submitObject');
			// Enable form
    		enableForm('manageContract_form');
			submit('manageContract_form');
		});
		
		// ���°�ť�¼�
		$('#btnChop').click( function () { 
			// ����Form Action
    		$('.manageContract_form').attr('action', 'employeeContractAction.do?proc=chop_object');
    		enableForm('manageContract_form');
			submit('manageContract_form');
		});
		
		// �鵵��ť�¼�
		$('#btnArchive').click( function () { 
			// ����Form Action
    		$('.manageContract_form').attr('action', 'employeeContractAction.do?proc=archive_object');
    		enableForm('manageContract_form');
			submit('manageContract_form');
		});

		// �б�ť�¼�
		$('#btnList').click(function(){
			if (agreest()){
				<logic:equal name="employeeContractForm" property="flag" value="1" >link('employeeContractAction.do?proc=list_object'); </logic:equal>
				<logic:equal name="employeeContractForm" property="flag" value="2" >link('employeeContractAction.do?proc=list_object&flag=2');</logic:equal>
			}
		});	
		
		// �鿴ģʽ
		if($('.manageContract_form input#subAction').val() == 'viewObject'){
			// ��Form��ΪDisable
			disableForm('manageContract_form');
			// ���İ�ť��ʾ��
    		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			// ����Page Title
			<logic:equal name="role" value="2" >$('#pageTitle').html('<bean:message bundle="business" key="business.employee.contract2.content.view" />');</logic:equal>
			<logic:equal name="role" value="1" >$('#pageTitle').html('<bean:message bundle="business" key="business.employee.contract1.content.view" />');</logic:equal>
		}
		
		if($('#status').val() == '1' || $('#status').val() == '4'){
			$('#btnEdit').show();
			$('#btnSubmit').show();
		}
		
		if($('#status').val() == '3'){
			$('#btnChop').show();
		}
		
		if($('#status').val() == '3' || $('#status').val() == '5' || $('#status').val() == '6' || $('#status').val() == '7'){
			$('#btnExportPDF').show();
			$('#exportPDF').show();
		}
		
		if($('#status').val() == '5'){
			$('#btnArchive').show();
		}
		setInputValueForPage();
	})(jQuery);
	
	function exportPDF(){
		link('employeeContractAction.do?proc=export_contract_pdf&id=<bean:write name="employeeContractForm" property="encodedId" />');
	};
	
	function setInputValueForPage(){
		$.ajax({
			url: 'employeeContractAction.do?proc=setInputValueForPage&contractId='+$("#id").val(), 
			type: 'POST', 
			traditional: true,
			data:$('.otBackForm').serialize(),
			dataType : 'json',
			async:false,
			success: function(result){
				for(var i = 0 ; i < result.length ; i++){
					if($("#"+result[i].id).length !=0){
						if($("#"+result[i].id)[0].tagName = "INPUT"){
							$("#"+result[i].id).val(result[i].value);
						}
						if($("#"+result[i].id)[0].tagName = "TEXTAREA"){
							$("#"+result[i].id).text(result[i].value);
						}
					}
				}
			} 
		});
	}
</script>

