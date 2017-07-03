<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder timesheetReportExportHolder = ( PagedListHolder ) request.getAttribute( "timesheetReportExportHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="header <%=timesheetReportExportHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchAVGTimesheetReportExport_form', null, null, 'employeeNameZH', '<%=timesheetReportExportHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<span id="employeeNameZH"><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal></span>&nbsp;&nbsp;
				</a>
			</th>
			<th id="nameEN" class="header <%=timesheetReportExportHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchAVGTimesheetReportExport_form', null, null, 'employeeNameEN', '<%=timesheetReportExportHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<span id="employeeNameEN"><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal></span>&nbsp;&nbsp;
				</a>
			</th>
			<th id="nameEN" class="header-nosort">
				<span id="monthlyCount"><bean:message bundle="business" key="busines.avg.attendance.days.report.month" /></span>&nbsp;&nbsp;
			</th>
			<th id="nameEN" class="header-nosort">
				<span id="avgTotalFullDays"><bean:message bundle="business" key="busines.avg.attendance.days.report.avg.total.days" /></span>&nbsp;&nbsp;
			</th>
			<th id="nameEN" class="header-nosort">
				<span id="avgTotalWorkDays"><bean:message bundle="business" key="busines.avg.attendance.days.report.avg.actual.days" /></span>&nbsp;&nbsp;
			</th>
		</tr>
	</thead>
	<logic:notEqual name="timesheetReportExportHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="timesheetReportExportVO" name="timesheetReportExportHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><bean:write name="timesheetReportExportVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="timesheetReportExportVO" property="employeeNameEN" /></td>
					<td class="left"><bean:write name="timesheetReportExportVO" property="monthlyCount" /></td>
					<td class="left"><bean:write name="timesheetReportExportVO" property="avgTotalFullDays" /></td>
					<td class="left"><bean:write name="timesheetReportExportVO" property="avgTotalWorkDays" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="timesheetReportExportHolder">
		<tfoot>
			<tr class="total">
				<td colspan="40" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="timesheetReportExportHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="timesheetReportExportHolder" property="indexStart" /> - <bean:write name="timesheetReportExportHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchAVGTimesheetReportExport_form', null, '<bean:write name="timesheetReportExportHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchAVGTimesheetReportExport_form', null, '<bean:write name="timesheetReportExportHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchAVGTimesheetReportExport_form', null, '<bean:write name="timesheetReportExportHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchAVGTimesheetReportExport_form', null, '<bean:write name="timesheetReportExportHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="timesheetReportExportHolder" property="realPage" />/<bean:write name="timesheetReportExportHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>