<%@page import="java.util.Map"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="header-nosort">
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
				<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
			</th>
			<th class="header-nosort">
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
				<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
			</th>
			<th class="header-nosort">
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
				<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.description" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="employee.change.report.changeType" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="employee.change.report.changeContent" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="employee.change.report.operateBy" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="employee.change.report.operateType" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="employee.change.report.operateTime" />
			</th>
		</tr>
	</thead>
	
	<logic:notEqual name="pagedListHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="employeeChangeReportVO" name="pagedListHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><bean:write name="employeeChangeReportVO" property="employeeId" /></td>
					<td class="left"><bean:write name="employeeChangeReportVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="employeeChangeReportVO" property="employeeNameEN" /></td>
					<td class="left"><bean:write name="employeeChangeReportVO" property="decodeChangeReason" /></td>
					<td class="left"><bean:write name="employeeChangeReportVO" property="decodeChangeType" /></td>
					<td class="left"><bean:write name="employeeChangeReportVO" property="changeContent" /></td>
					<td class="left"><bean:write name="employeeChangeReportVO" property="operateBy" /></td>
					<td class="left"><bean:write name="employeeChangeReportVO" property="decodeOperateType" /></td>
					<td class="left"><bean:write name="employeeChangeReportVO" property="decodeOperateTime" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="pagedListHolder">
		<tfoot>
			<tr class="total">
				<td colspan="9" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="pagedListHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="pagedListHolder" property="indexStart" /> - <bean:write name="pagedListHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchEmployeeChangeForm', null, '<bean:write name="pagedListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeChangeForm', null, '<bean:write name="pagedListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeChangeForm', null, '<bean:write name="pagedListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeChangeForm', null, '<bean:write name="pagedListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="pagedListHolder" property="realPage" />/<bean:write name="pagedListHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>

<script type="text/javascript">
	(function($) {
		$('#resultTable tbody tr').each(function(){
			var html = $(this).find('td:eq(5)').text();
			$(this).find('td:eq(5)').html(html);
		});
		
	})(jQuery); 
</script>