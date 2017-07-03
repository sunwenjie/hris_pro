<%@page import="com.kan.hro.domain.biz.employee.EmployeeContractLeaveReportVO"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder annualLeaveDetailsHolder = ( PagedListHolder ) request.getAttribute( "annualLeaveDetailsHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th rowspan="2">
				<span id="employeeId"><bean:message bundle="public" key="public.employee2.id" /></span>
			</th>
			<th rowspan="2">
				<span id="employeeNameZH"><bean:message bundle="public" key="public.employee2.name.cn" /></span>
			</th>
			<th rowspan="2">
				<span id="employeeNameEN"><bean:message bundle="public" key="public.employee2.name.en" /></span>
			</th>
			<th rowspan="2">
				<span id="employeeShortName"><bean:message bundle="public" key="public.employee2.short.name" /></span>
			</th>
			<th colspan="3" class="center">
				<span id="lastLeaveDetails"><%=Integer.valueOf(((EmployeeContractLeaveReportVO)annualLeaveDetailsHolder.getObject()).getCurrYear()) - 1 %><bean:message bundle="business" key="annual.leave.report.annualLeave"/></span>
			</th>
			<th colspan="3" class="center">
				<span id="thisLeaveDetails"><%=((EmployeeContractLeaveReportVO)annualLeaveDetailsHolder.getObject()).getCurrYear() %><bean:message bundle="business" key="annual.leave.report.annualLeave"/></span>
			</th>
			<th rowspan="2">
				<span id="decodeContractStatus"><bean:message bundle="public" key="public.contract2.status" /></span>
			</th>
		</tr>
		<tr>
			<th><bean:message bundle="business" key="annual.leave.report.totalHours"/></th>
			<th><bean:message bundle="business" key="annual.leave.report.probationUsing"/></th>
			<th><bean:message bundle="business" key="annual.leave.report.delayMonth"/></th>	
			<th><bean:message bundle="business" key="annual.leave.report.totalHours"/></th>
			<th><bean:message bundle="business" key="annual.leave.report.probationUsing"/></th>
			<th><bean:message bundle="business" key="annual.leave.report.delayMonth"/></th>		
		</tr> 
		   
	</thead>
	<logic:notEqual name="annualLeaveDetailsHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="annualLeaveDetails" name="annualLeaveDetailsHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><bean:write name="annualLeaveDetails" property="employeeId" /></td>
					<td class="left"><bean:write name="annualLeaveDetails" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="annualLeaveDetails" property="employeeNameEN" /></td>
					<td class="left"><bean:write name="annualLeaveDetails" property="shortName" /></td>
					<td class="right"><bean:write name="annualLeaveDetails" property="lastYearTotalHours" /></td>
					<td class="center"><bean:write name="annualLeaveDetails" property="decodeLastYearProbationUsing" /></td>
					<td class="right"><bean:write name="annualLeaveDetails" property="lastYearDelayMonth" /></td>
					<td class="right"><bean:write name="annualLeaveDetails" property="thisYearTotalHours" /></td>
					<td class="center"><bean:write name="annualLeaveDetails" property="decodeThisYearProbationUsing" /></td>
					<td class="right"><bean:write name="annualLeaveDetails" property="thisYearDelayMonth" /></td>
					<td class="left"><bean:write name="annualLeaveDetails" property="decodeContractStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="annualLeaveDetailsHolder">
		<tfoot>
			<tr class="total">
				<td colspan="40" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="annualLeaveDetailsHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="annualLeaveDetailsHolder" property="indexStart" /> - <bean:write name="annualLeaveDetailsHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchEmployeeContractLeaveReport_form', null, '<bean:write name="annualLeaveDetailsHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeContractLeaveReport_form', null, '<bean:write name="annualLeaveDetailsHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeContractLeaveReport_form', null, '<bean:write name="annualLeaveDetailsHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeContractLeaveReport_form', null, '<bean:write name="annualLeaveDetailsHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="annualLeaveDetailsHolder" property="realPage" />/<bean:write name="annualLeaveDetailsHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>