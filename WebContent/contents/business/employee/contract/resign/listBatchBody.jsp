<%@ page import="com.kan.hro.web.actions.biz.importExcel.EmployeeContractResignImportAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<div class="box searchForm toggableForm" id="sbTempBatch-information">
		<div class="head"><label id="itleLable">������ְ</label></div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="employeeContractResignImportAction.do?proc=list_object" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeContractResignHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeContractResignHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeeContractResignHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="employeeContractResignHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>����ID</label> 
							<html:text property="batchId" maxlength="12" styleClass="searchSbTemp_BatchId" /> 
						</li>
						<li>
							<label>����EXCEL����</label> 
							<html:text property="importExcelName" maxlength="12" styleClass="searchSbTemp_importExcelName" /> 
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<!-- vendorBatch-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div class="top">
	       		<input type="hidden" name="definedMessage" id="definedMessage" value="true" />
				<input type="button" class="" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
				<input type="button" class="delete" name="btnRollback" id="btnRollback" value="�˻�" />
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
				<img style='float:right' src='images/import.png' onclick='popupExcelImport();' title='������ְ' />
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� --> 
				<jsp:include page="table/listBatchTable.jsp" flush="true"/> 
			</div>
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/importExcel.jsp" flush = "true">
		<jsp:param name="accessAction" value="<%=EmployeeContractResignImportAction.ACCESSACTION%>"/>
	</jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// ���ö����˵�ѡ����ʽ
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Import').addClass('selected');
		$('#menu_employee_Contract_Resign').addClass('selected');
		
		$('#searchDiv').hide();
		$('#importExcelTitleLableId').html('������ְ');

		// ���θ����¼�
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���ύѡ�е����Σ�")){
					submitForm('list_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ���µ����Ρ�");
			}
		});
		
		// �����˻��¼�
		$('#btnRollback').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("ȷ���˻�ѡ�е����Σ�")){
					submitForm('list_form', "rollbackObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("��ѡ��Ҫ�˻ص����Ρ�");
			}
		});
		
		<%
		final Boolean messageInfo = (Boolean) request.getAttribute("messageInfo");
			if(messageInfo!=null && messageInfo){
		%>
			$('#messageWrapper').html('<div class="message success fadable">��������û�����ݣ�<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
	 			messageWrapperFada();
		<%	}%>
	})(jQuery);
	
	function resetForm() {
		$(".searchSbTemp_BatchId").val("");
		$(".searchSbTemp_importExcelName").val("");
	};
</script>
