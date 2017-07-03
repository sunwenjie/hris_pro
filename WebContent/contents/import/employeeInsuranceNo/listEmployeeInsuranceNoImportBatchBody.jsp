<%@page import="com.kan.hro.web.actions.biz.importExcel.EmployeeInsuranceNoImportBatchAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="employeeInsuranceNoImportBatch-information">
		<div class="head"><label id="itleLable">�����˺ŵ���</label></div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="employeeInsuranceNoImportBatchAction.do?proc=list_object" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeInsuranceNoImportBatchHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeInsuranceNoImportBatchHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeeInsuranceNoImportBatchHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="employeeInsuranceNoImportBatchHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>ID</label>
							<html:text property="employeeId" maxlength="11" styleClass="searchEmployeeInsuranceNoImportBatch_employeeId" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>���������ģ�</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchEmployeeInsuranceNoImportBatch_employeeNameZH" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>������Ӣ�ģ�</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchEmployeeInsuranceNoImportBatch_employeeNameEN" /> 
						</li>
		   				<logic:equal name="role" value="1">
							<li>
								<label>����Э��ID</label>
								<html:text property="contractId" maxlength="11" styleClass="searchEmployeeInsuranceNoImportBatch_contractId" /> 
							</li> 
						</logic:equal>
						<li>
							<label>��������ID</label>
							<html:text property="batchId" maxlength="11" styleClass="searchEmployeeInsuranceNoImportBatch_batchId" /> 
						</li>
						<li>
							<label>���뱨������</label> 
							<html:text property="importExcelName" maxlength="11" styleClass="searchEmployeeInsuranceNoImportBatch_importExcelName" /> 
						</li>
						<li>
							<label>״̬</label>
							<html:select property="status" styleClass="searchEmployeeInsuranceNoImportBatch_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue"  />
							</html:select>
						</li>
					</ol>
				</fieldset> 
			</html:form>
		</div>
	</div>

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
				<kan:auth right="submit" action="<%=EmployeeInsuranceNoImportBatchAction.accessAction%>">
					<input type="button" class="" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>
				<kan:auth right="back" action="<%=EmployeeInsuranceNoImportBatchAction.accessAction%>">
					<input type="button" class="function" name="btnDelete" id="btnDelete" value="�˻�" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
				<kan:auth right="import" action="<%=EmployeeInsuranceNoImportBatchAction.accessAction%>">
					<img style='float:right' src='images/import.png' onclick='popupExcelImport();' title='�����˺ŵ���' />
				</kan:auth>
			</div>
			<!-- top -->
			<div id="tableWrapper">
			<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
				<jsp:include page="table/listEmployeeInsuranceNoImportBatchTable.jsp" flush="true"/> 
			</div>
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/importExcel.jsp" flush = "true">
		<jsp:param  name="accessAction" value="<%=EmployeeInsuranceNoImportBatchAction.accessAction %>"/>
		<jsp:param  name="needRemark" value="true"/>
		<jsp:param  name="closeRefesh" value="true"/>
	</jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// ���ö����˵�ѡ����ʽ
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Import').addClass('selected');
		$('#menu_employee_import_insuranceNo').addClass('selected');
		
		$('#searchDiv').hide();
		
		// ���θ����¼�
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���ύѡ�е����Σ�")){
					$('.list_form').attr('action', 'employeeInsuranceNoImportBatchAction.do?proc=submit_batch');
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
				if(confirm("ȷ���˻�ѡ�е����Σ�")){
					$('.list_form').attr('action', 'employeeInsuranceNoImportBatchAction.do?proc=rollback_batch');
					submitForm('list_form', "rollbackObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ�˻ص����Ρ�");
			}
		});
		
	})(jQuery);
	
	function resetForm() {
		$(".searchEmployeeInsuranceNoImportBatch_BatchId").val("");
		$(".searchEmployeeInsuranceNoImportBatch_importExcelName").val("");
		
	};
</script>
