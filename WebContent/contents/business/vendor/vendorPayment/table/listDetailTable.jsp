<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder sbDetailHolder = (PagedListHolder) request.getAttribute("sbDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 5%" class="header <%=sbDetailHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'itemId', '<%=sbDetailHolder.getNextSortOrder("itemId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.item.id" /></a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailHolder.getCurrentSortClass("itemNo")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'itemNo', '<%=sbDetailHolder.getNextSortOrder("itemNo")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.item.no" /></a>
			</th>
			<th style="width: 14%" class="header <%=sbDetailHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'nameZH', '<%=sbDetailHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.item.name" /></a>
			</th>
			<th style="width: 6%" class="header <%=sbDetailHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'monthly', '<%=sbDetailHolder.getNextSortOrder("monthly")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.sb.monthly" /></a>
			</th>
			<th style="width: 6%" class="header <%=sbDetailHolder.getCurrentSortClass("accountMonthly")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'accountMonthly', '<%=sbDetailHolder.getNextSortOrder("accountMonthly")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.account.monthly" /></a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailHolder.getCurrentSortClass("baseCompany")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'baseCompany', '<%=sbDetailHolder.getNextSortOrder("baseCompany")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.base.company" /></a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailHolder.getCurrentSortClass("basePersonal")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'basePersonal', '<%=sbDetailHolder.getNextSortOrder("basePersonal")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.base.personal" /></a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailHolder.getCurrentSortClass("rateCompany")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'rateCompany', '<%=sbDetailHolder.getNextSortOrder("rateCompany")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.rate.company" /></a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailHolder.getCurrentSortClass("ratePersonal")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'ratePersonal', '<%=sbDetailHolder.getNextSortOrder("ratePersonal")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.rate.personal" /></a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailHolder.getCurrentSortClass("fixCompany")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'fixCompany', '<%=sbDetailHolder.getNextSortOrder("fixCompany")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.fix.company" /></a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailHolder.getCurrentSortClass("fixPersonal")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'fixPersonal', '<%=sbDetailHolder.getNextSortOrder("fixPersonal")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.fix.personal" /></a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailHolder.getCurrentSortClass("amountCompany")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'amountCompany', '<%=sbDetailHolder.getNextSortOrder("amountCompany")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.amount.company" /></a>
			</th>
			<th style="width: 7%" class="header <%=sbDetailHolder.getCurrentSortClass("amountPersonal")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'amountPersonal', '<%=sbDetailHolder.getNextSortOrder("amountPersonal")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.amount.personal" /></a>
			</th>
			<th style="width: 6%" class="header <%=sbDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'status', '<%=sbDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="sbDetailHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="sbDetailVO" name="sbDetailHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><bean:write name="sbDetailVO" property="itemId" /></td>
					<td class="left"><bean:write name="sbDetailVO" property="itemNo" /></td>
					<td class="left"><bean:write name="sbDetailVO" property="nameZH" /></td>
					<td class="left"><bean:write name="sbDetailVO" property="monthly" /></td>
					<td class="left"><bean:write name="sbDetailVO" property="accountMonthly" /></td>
					<td class="right"><bean:write name="sbDetailVO" property="decodeBaseCompany" /></td>
					<td class="right"><bean:write name="sbDetailVO" property="decodeBasePersonal" /></td>
					<td class="right"><bean:write name="sbDetailVO" property="decodeRateCompany" /></td>
					<td class="right"><bean:write name="sbDetailVO" property="decodeRatePersonal" /></td>
					<td class="right"><bean:write name="sbDetailVO" property="decodeFixCompany" /></td>
					<td class="right"><bean:write name="sbDetailVO" property="decodeFixPersonal" /></td>
					<td class="right"><bean:write name="sbDetailVO" property="decodeAmountCompany" /></td>
					<td class="right"><bean:write name="sbDetailVO" property="decodeAmountPersonal" /></td>
					<td class="left"><bean:write name="sbDetailVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="sbDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="15" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="sbDetailHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="sbDetailHolder" property="indexStart" /> - <bean:write name="sbDetailHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="sbDetailHolder" property="realPage" />/<bean:write name="sbDetailHolder" property="pageCount" /> </label>&nbsp;</td>
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
