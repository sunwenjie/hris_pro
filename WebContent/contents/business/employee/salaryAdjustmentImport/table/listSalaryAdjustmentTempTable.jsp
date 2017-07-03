<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder employeeSalaryAdjustmentTempHolder = (PagedListHolder) request.getAttribute("employeeSalaryAdjustmentTempHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 8%" class="header <%=employeeSalaryAdjustmentTempHolder.getCurrentSortClass("salaryAdjustmentId")%>">
				<a onclick="submitForm('salaryAdjustmentTemp_form', null, null, 'salaryAdjustmentId', '<%=employeeSalaryAdjustmentTempHolder.getNextSortOrder("salaryAdjustmentId")%>', 'tableWrapper');">
					ID
				</a>
			</th>
			<th style="width: 8%" class="header <%=employeeSalaryAdjustmentTempHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('salaryAdjustmentTemp_form', null, null, 'employeeId', '<%=employeeSalaryAdjustmentTempHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.employee2.id" />
				</a>
			</th>
			<th style="width: 12%" class="header-nosort">
				<bean:message bundle="public" key="public.employee2.name" />
			</th>
			<th style="width: 12%" class="header <%=employeeSalaryAdjustmentTempHolder.getCurrentSortClass("employeeCertificateNumber")%>">
				<a onclick="submitForm('salaryAdjustmentTemp_form', null, null, 'employeeCertificateNumber', '<%=employeeSalaryAdjustmentTempHolder.getNextSortOrder("employeeCertificateNumber")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.certificate.number" />
				</a>
			</th>
			<th style="width: 8%" class="header <%=employeeSalaryAdjustmentTempHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('salaryAdjustmentTemp_form', null, null, 'contractId', '<%=employeeSalaryAdjustmentTempHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.contract2.id" />
				</a>
			</th>
			<th style="width: 12%" class="header-nosort">
				<bean:message bundle="public" key="public.contract2.name" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.salary.adjustment.item" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.salary.adjustment.adjustment.before.base" />
			</th>
			<th style="width: 12%" class="header-nosort">
				<bean:message bundle="business" key="employee.salary.adjustment.adjustment.before.period" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.salary.adjustment.adjustment.after.base" />
			</th>
			<th style="width: 12%" class="header-nosort">
				<bean:message bundle="business" key="employee.salary.adjustment.adjustment.after.period" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="public" key="public.status" />
			</th>
		</tr>
	</thead>
	<logic:notEqual name="employeeSalaryAdjustmentTempHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="salaryAdjustmentTempVO" name="employeeSalaryAdjustmentTempHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<logic:equal name="salaryAdjustmentTempVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="salaryAdjustmentTempVO" property="salaryAdjustmentId"/>" name="chkSelectRow[]" value="<bean:write name="salaryAdjustmentTempVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left"><bean:write name="salaryAdjustmentTempVO" property="salaryAdjustmentId"/></td>
					<td class="left"><bean:write name="salaryAdjustmentTempVO" property="employeeId"/></td>
					<td class="left"><bean:write name="salaryAdjustmentTempVO" property="employeeName"/></td>
					<td class="left"><bean:write name="salaryAdjustmentTempVO" property="employeeCertificateNumber"/></td>
					<td class="left"><bean:write name="salaryAdjustmentTempVO" property="contractId"/></td>
					<td class="left"><bean:write name="salaryAdjustmentTempVO" property="employeeContractName" /></td>
					<td class="left"><bean:write name="salaryAdjustmentTempVO" property="decodeItemId" /></td>
					<td class="right"><bean:write name="salaryAdjustmentTempVO" property="oldBase" /></td>
					<td class="left">
						<logic:notEmpty name="salaryAdjustmentTempVO" property="oldStartDate">
							<bean:write name="salaryAdjustmentTempVO" property="oldStartDate" />  ~  <bean:write name="salaryAdjustmentTempVO" property="oldEndDate" />
						</logic:notEmpty>
					</td>
					<td class="right"><bean:write name="salaryAdjustmentTempVO" property="newBase" /></td>
					<td class="left">
						<bean:write name="salaryAdjustmentTempVO" property="newStartDate" />  ~  <bean:write name="salaryAdjustmentTempVO" property="newEndDate" />
					</td>
					<td class="left">
						<bean:write name="salaryAdjustmentTempVO" property="decodeStatus" />
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="employeeSalaryAdjustmentTempHolder">
		<tfoot>
			<tr class="total">
	  			<td colspan="14" class="left"> 
	  				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="employeeSalaryAdjustmentTempHolder" property="holderSize" /></label>
	  				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="employeeSalaryAdjustmentTempHolder" property="indexStart" /> - <bean:write name="employeeSalaryAdjustmentTempHolder" property="indexEnd" /></label>
	  				<label>&nbsp;&nbsp;<a onclick="submitForm('salaryAdjustmentTemp_form', null, '<bean:write name="employeeSalaryAdjustmentTempHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('salaryAdjustmentTemp_form', null, '<bean:write name="employeeSalaryAdjustmentTempHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('salaryAdjustmentTemp_form', null, '<bean:write name="employeeSalaryAdjustmentTempHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('salaryAdjustmentTemp_form', null, '<bean:write name="employeeSalaryAdjustmentTempHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
	  				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="employeeSalaryAdjustmentTempHolder" property="realPage" />/<bean:write name="employeeSalaryAdjustmentTempHolder" property="pageCount" /></label>&nbsp;
	  			</td>					
	    	</tr>
		</tfoot>
	</logic:present>
</table>

<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
	})(jQuery);	
</script>