<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@page import="com.kan.hro.web.actions.biz.employee.EmployeeSalaryAdjustmentAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">
	<div class="box searchForm toggableForm" id="location-information">
		<div class="head">
			<label><bean:message bundle="business" key="employee.salary.adjustment.search.title" /></label>
		</div>
		<!-- inner -->
		<a href="#" class="toggle tiptip" title="<bean:message bundle="security" key="security.business.type.search.title" />">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('salaryAdjustment_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="employeeSalaryAdjustmentAction.do?proc=list_object" styleClass="salaryAdjustment_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="salaryAdjustmentHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="salaryAdjustmentHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="salaryAdjustmentHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="salaryAdjustmentHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
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
	<!-- Location-information -->
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
				<kan:auth right="new" action="<%=EmployeeSalaryAdjustmentAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('employeeSalaryAdjustmentAction.do?proc=to_objectNew');" />
				</kan:auth>
				<kan:auth right="submit" action="<%=EmployeeSalaryAdjustmentAction.accessAction%>">
					<input type="button" class="function" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.submit" />" onclick="submitSalaryAdjustmentForm()"/> 
				</kan:auth>
				<kan:auth right="submit" action="<%=EmployeeSalaryAdjustmentAction.accessAction%>">
					<input type="button" class="function" name="btnSynchronized" id="btnSynchronized" value="<bean:message bundle="public" key="button.synchronized" />" onclick="synchronizedData()"/> 
				</kan:auth>
				<kan:auth right="delete" action="<%=EmployeeSalaryAdjustmentAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('salaryAdjustment_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
				<a id="exportExcel" name="exportExcel" class="commonTools" title="<%=KANUtil.getProperty( request.getLocale(), "img.title.tips.export.excel" ) %>" onclick="linkForm('salaryAdjustment_form', 'downloadObjects', null, 'fileType=excel');">
					<img src="images/appicons/excel_16.png" />
				</a>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/business/employee/salaryAdjustment/table/listSalaryAdjustmentTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
			<!-- List Component -->
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->
</div>

<script type="text/javascript">
	(function($) {
		$('#menu_employee_Modules').addClass('current');			
		$('#menu_employee_salary_adjustment').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	function submitSalaryAdjustmentForm(){
		
		if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
			if(confirm('<bean:message bundle="public" key="popup.confirm.submit.records" />')){
				$('.salaryAdjustment_form').attr('action', 'employeeSalaryAdjustmentAction.do?proc=submit_objects');
				submit('salaryAdjustment_form');
				$('#selectedIds').val('');
			}
		}else{
			alert('<bean:message bundle="public" key="popup.select.submit.records" />');
		}
		
	};
	
	function synchronizedData(){
		$('.salaryAdjustment_form').attr('action', 'employeeSalaryAdjustmentAction.do?proc=synchronized_objects');
		submit('salaryAdjustment_form');
	};

	function resetForm(){
		$('.searchSalaryAdjustment_employeeId').val('');
		$('.searchSalaryAdjustment_employeeNo').val('');
		$('.searchSalaryAdjustment_employeeNameZH').val('');
		$('.searchSalaryAdjustment_employeeNameEN').val('');
		$('.searchSalaryAdjustment_employeeCertificateNumber').val('');
		$('.searchSalaryAdjustment_employeeContractId').val('');
		$('.searchSalaryAdjustment_employeeContractNameZH').val('');
		$('.searchSalaryAdjustment_employeeContractNameEN').val('');
		$('.searchSalaryAdjustment_status').val('0');
	};
</script>