<%@	page import="com.kan.hro.web.actions.biz.employee.EmployeeSalaryAdjustmentBatchAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="employeeSalaryAdjustmentBatch-information">
		<div class="head">
			<label id="itleLable">
				<bean:message bundle="business" key="employee.salary.adjustment.import.batch" />
			</label>
		</div>
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions" />">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('salaryAdjustmentTemp_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="employeeSalaryAdjustmentTempAction.do?proc=list_object" styleClass="salaryAdjustmentTemp_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeSalaryAdjustmentTempHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeSalaryAdjustmentTempHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeeSalaryAdjustmentTempHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="employeeSalaryAdjustmentTempHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value="<bean:write name="employeeSalaryAdjustmentTempForm" property="batchId" />" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.employee2.id" /></label>
							<html:text property="employeeId" maxlength="50" styleClass="searchSalaryAdjustment_employeeId" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.employee2.no" /></label>
							<html:text property="employeeNo" maxlength="50" styleClass="searchSalaryAdjustment_employeeNo" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.employee2.name.cn" /></label> 
							<html:text property="employeeNameZH" maxlength="50" styleClass="searchSalaryAdjustment_employeeNameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.employee2.name.en" /></label> 
							<html:text property="employeeNameEN" maxlength="50" styleClass="searchSalaryAdjustment_employeeNameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.certificate.number" /></label>
							<html:text property="employeeCertificateNumber" maxlength="50" styleClass="searchSalaryAdjustment_employeeCertificateNumber" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.contract2.id" /></label>
							<html:text property="contractId" maxlength="50" styleClass="searchSalaryAdjustment_employeeContractId" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.contract2.name.cn" /></label>
							<html:text property="employeeContractNameZH" maxlength="50" styleClass="searchSalaryAdjustment_employeeContractNameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.contract2.name.en" /></label>
							<html:text property="employeeContractNameEN" maxlength="50" styleClass="searchSalaryAdjustment_employeeContractNameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchSalaryAdjustment_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	
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
				<kan:auth right="submit" action="<%=EmployeeSalaryAdjustmentBatchAction.ACCESS_ACTION%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>	
				<kan:auth right="back" action="<%=EmployeeSalaryAdjustmentBatchAction.ACCESS_ACTION%>">	
					<input type="button" class="delete" name="btnDelete" id="btnDelete" value="<bean:message bundle="public" key="button.return" />" />
				</kan:auth>
				<kan:auth right="list" action="<%=EmployeeSalaryAdjustmentBatchAction.ACCESS_ACTION%>">	
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" onclick="link('employeeSalaryAdjustmentBatchAction.do?proc=list_object')" />
				</kan:auth>		
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
			<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/business/employee/salaryAdjustmentImport/table/listSalaryAdjustmentTempTable.jsp" flush="true"/> 
			</div>
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/importEmployeeSalaryAdjustment.jsp" flush = "true">
		<jsp:param name="accessAction" value="<%=EmployeeSalaryAdjustmentBatchAction.ACCESS_ACTION %>"/>
		<jsp:param name="needRemark" value="true"/>
		<jsp:param name="closeRefesh" value="true"/>
	</jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Import').addClass('selected');
		$('#menu_employee_batch_salary_adjustment').addClass('selected');
		
		$('#searchDiv').hide();
		
		// 批次更新事件
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.submit.selected.batch" />')){
					$('.salaryAdjustmentTemp_form').attr('action', 'employeeSalaryAdjustmentTempAction.do?proc=submit_temp');
					submitForm('salaryAdjustmentTemp_form', "submitObjects", null, null, null, 'tableWrapper');
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
					$('.salaryAdjustmentTemp_form').attr('action', 'employeeSalaryAdjustmentTempAction.do?proc=rollback_temp');
					submitForm('salaryAdjustmentTemp_form', "rollbackObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
	})(jQuery);
	
	function resetForm() {
	   var oForm = $('.salaryAdjustmentTemp_form');
	   oForm.find('input:visible').val('');
	   oForm.find('select:visible').val('0');
	};
</script>