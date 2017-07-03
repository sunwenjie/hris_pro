<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ page import=" com.kan.hro.web.actions.biz.employee.EmployeeContractSalaryAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>


<%
	final PagedListHolder employeeContractSalaryHolder = (PagedListHolder) request.getAttribute("employeeContractSalaryHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="employeeContractSalary-information">
		<div class="head">
			<label id="itleLable">
				<logic:equal name="role" value="1"><bean:message bundle="payment" key="payment.employee1.salary" /></logic:equal>
				<logic:equal name="role" value="2"><bean:message bundle="payment" key="payment.employee2.salary" /></logic:equal>
			</label>
		</div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="employeeContractSalaryAction.do?proc=list_object" styleClass="list_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeContractSalaryHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeContractSalaryHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeeContractSalaryHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="employeeContractSalaryHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>客户ID</label>
							<html:text property="clientId" styleId="clientId" maxlength="10" styleClass="searchEmployeeContractSalary_clientId" />
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
							</label>
							<logic:equal name="role" value="1">
								<html:text property="orderId" maxlength="10" styleClass="searchEmployeeContractSalary_orderId" /> 
							</logic:equal>
							<logic:equal name="role" value="2">
								<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="searchEmployeeContractSalary_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
		   						</logic:notEmpty>
							</logic:equal>
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
							</label> 
							<html:text property="contractId" styleId="contractId" maxlength="10" styleClass="searchEmployeeContractSalary_contractId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label> 
							<html:text property="employeeId" maxlength="10" styleClass="searchEmployeeContractSalary_employeeSalaryId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
							</label> 
							<html:text property="employeeNameZH" styleId="employeeNameZH" maxlength="50" styleClass="searchEmployeeContractSalary_employeeNameZH" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
							</label> 
							<html:text property="employeeNameEN" styleId="employeeNameEN" maxlength="50" styleClass="searchEmployeeContractSalary_employeeNameEN" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.status" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.status" /></logic:equal>
							</label> 
							<html:select property="contractStatus" styleClass="searchEmployeeContractSalary_contractStatus">
								<html:optionsCollection property="contractStatuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label>
							<html:select property="status" styleClass="searchEmployeeContractSalary_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<!-- employeeContractSalary-information -->
	<div class="box noHeader" id="search-results">
		<!-- Include table jsp 包含tabel对应的jsp文件 -->
		<jsp:include page="/contents/payment/setting/table/listEmployeeContractSalaryTable.jsp" flush="true"/> 
	</div>
	
	<div id="managePopupWrapper">
		<jsp:include page="/popup/manageEmployeeContractSalary.jsp"></jsp:include>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Employee').addClass('selected');
		
		$('#searchDiv').hide();
	})(jQuery);

	function resetForm() {
		$('.searchEmployeeContractSalary_employeeSalaryId').val('');
		$('.searchEmployeeContractSalary_clientId').val('');
		<logic:equal name="role" value="1">
			$('.searchEmployeeContractSalary_orderId').val('');
		</logic:equal>	
		<logic:equal name="role" value="2">
			$('.searchEmployeeContractSalary_orderId').val('0');
		</logic:equal>	
		$('.searchEmployeeContractSalary_employeeSalaryId').val('');
		$('.searchEmployeeContractSalary_employeeNameZH').val('');
		$('.searchEmployeeContractSalary_employeeNameEN').val('');
		$('.searchEmployeeContractSalary_contractId').val('');
		$('.searchEmployeeContractSalary_contractStatus').val('0');
	};
</script>