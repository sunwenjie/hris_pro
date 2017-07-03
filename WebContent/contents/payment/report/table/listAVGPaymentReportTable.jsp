<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder paymentReportHolder = ( PagedListHolder ) request.getAttribute( "paymentReportHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="header <%=paymentReportHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchPaymentReport_form', null, null, 'employeeNameZH', '<%=paymentReportHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<span id="employeeNameZH"><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal></span>&nbsp;&nbsp;
				</a>
			</th>
			<th id="nameEN" class="header <%=paymentReportHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchPaymentReport_form', null, null, 'employeeNameEN', '<%=paymentReportHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<span id="employeeNameEN"><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal></span>&nbsp;&nbsp;
				</a>
			</th>
			<th id="nameEN" class="header-nosort">
				<span id="monthlyCount"><bean:message bundle="payment" key="payment.avg.report.month" /></span>&nbsp;&nbsp;
			</th>
			<th id="nameEN" class="header-nosort">
				<span id="avgAmount"><bean:message bundle="payment" key="payment.avg.report.salary" /></span>&nbsp;&nbsp;
			</th>
		</tr>
	</thead>
	<logic:notEqual name="paymentReportHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="paymentReportVO" name="paymentReportHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><bean:write name="paymentReportVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="paymentReportVO" property="employeeNameEN" /></td>
					<td class="left"><bean:write name="paymentReportVO" property="monthlyCount" /></td>
					<td class="left"><bean:write name="paymentReportVO" property="avgAmount" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="paymentReportHolder">
		<tfoot>
			<tr class="total">
				<td colspan="40" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="paymentReportHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="paymentReportHolder" property="indexStart" /> - <bean:write name="paymentReportHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchPaymentReport_form', null, '<bean:write name="paymentReportHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchPaymentReport_form', null, '<bean:write name="paymentReportHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchPaymentReport_form', null, '<bean:write name="paymentReportHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchPaymentReport_form', null, '<bean:write name="paymentReportHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="paymentReportHolder" property="realPage" />/<bean:write name="paymentReportHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>