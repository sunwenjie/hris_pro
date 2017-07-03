<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ page import="com.kan.hro.web.actions.biz.importExcel.EmployeeContractBatchAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="employeeContractBatch-information">
		<div class="head">
			<label id="itleLable">
				<logic:equal name="role" value="1">派送协议导入批次</logic:equal>
				<logic:equal name="role" value="2"><bean:message bundle="business" key="business.employee.contract.import.batch" /></logic:equal>
			</label>
		</div>
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions" />">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="employeeContractBatchAction.do?proc=list_object" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeContractBatchHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeContractBatchHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeeContractBatchHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="employeeContractBatchHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.import.batch.id" /></label> <bean:write name="employeeContractImportBatchForm" property="batchId"/>
							<input type="text" id="batchId" class ="searchEmployeeContractTemp_BatchId" maxlength="12" value="<bean:write name="employeeContractImportBatchForm" property="batchId"/>">
						</li>
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.import.batch.report.name" /></label> 
							<input type="text" id="importExcelName" name="importExcelName" class ="searchEmployeeContractTemp_importExcelName" maxlength="12" value="<bean:write name="employeeContractImportBatchForm" property="importExcelName"/>">
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label>
							<html:select property="status" styleClass="searchEmployeeContractTemp_status">
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
				<kan:auth right="submit" action="<%=EmployeeContractBatchAction.accessAction%>">
					<input type="button" class="" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>
				<kan:auth right="back" action="<%=EmployeeContractBatchAction.accessAction%>">
					<input type="button" class="function" name="btnDelete" id="btnDelete" value="<bean:message bundle="public" key="button.return" />" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
				<kan:auth right="import" action="<%=EmployeeContractBatchAction.accessAction%>">
					<img style='float:right' src='images/import.png' onclick='popupExcelImport();' title='<bean:message bundle="public" key="img.title.tips.import.excel" />' />
				</kan:auth>
			</div>
			<!-- top -->
			<div id="tableWrapper">
			<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="table/listEmployeeContractBatchTable.jsp" flush="true"/> 
			</div>
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/importExcel.jsp" flush = "true">
		<jsp:param  name="accessAction" value="<%=EmployeeContractAction.getAccessAction(request,response) %>"/>
		<jsp:param  name="needRemark" value="true"/>
		<jsp:param  name="closeRefesh" value="true"/>
	</jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Import').addClass('selected');
		$('#menu_employee_ImportLaborContract').addClass('selected');
		
		$('#searchDiv').hide();
		<logic:equal name="role" value="1">
			$('#importExcelTitleLableId').html('<bean:message bundle="business" key="business.employee.contract1.import" />');
		</logic:equal>
		<logic:equal name="role" value="2">
			$('#importExcelTitleLableId').html('<bean:message bundle="business" key="business.employee.contract2.import" />');
		</logic:equal>
		// 批次更新事件
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.submit.selected.batch" />')){
					$('.list_form').attr('action', 'employeeContractBatchAction.do?proc=submit_batch');
					submitForm('list_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
		// 批次退回事件
		$('#btnDelete').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.return.selected.batch" />')){
					$('.list_form').attr('action', 'employeeContractBatchAction.do?proc=rollback_batch');
					submitForm('list_form', "rollbackObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
	})(jQuery);
	
	function resetForm() {
		$(".searchEmployeeContractTemp_BatchId").val("");
		$(".searchEmployeeContractTemp_importExcelName").val("");
		
	};
</script>
