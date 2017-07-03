<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder sbHeaderHolder = (PagedListHolder) request.getAttribute("sbHeaderHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 5%" class="header <%=sbHeaderHolder.getCurrentSortClass("headerId")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'headerId', '<%=sbHeaderHolder.getNextSortOrder("headerId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.header.id" /></a>
			</th>
			<th style="width: 5%" class="header <%=sbHeaderHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'monthly', '<%=sbHeaderHolder.getNextSortOrder("monthly")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.monthly" /></a>
			</th>
			<th style="width: 15%" class="header <%=sbHeaderHolder.getCurrentSortClass("employeeSBId")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'employeeSBId', '<%=sbHeaderHolder.getNextSortOrder("employeeSBId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.sb.solution" /></a>
			</th>
			<th style="width: 15%" class="header <%=sbHeaderHolder.getCurrentSortClass("entityId")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'entityId', '<%=sbHeaderHolder.getNextSortOrder("entityId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.legal.entity" /></a>
			</th>
			<th style="width: 7%" class="header <%=sbHeaderHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'employeeId', '<%=sbHeaderHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: 8%" class="header <%=sbHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'employeeNameZH', '<%=sbHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</a>
			</th>
			<th style="width: 10%" class="header <%=sbHeaderHolder.getCurrentSortClass("onboardDate")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'onboardDate', '<%=sbHeaderHolder.getNextSortOrder("onboardDate")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.employee.entry.time" /></a>
			</th>
			<th style="width: 5%" class="header <%=sbHeaderHolder.getCurrentSortClass("employStatus")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'employStatus', '<%=sbHeaderHolder.getNextSortOrder("employStatus")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.status" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.status" /></logic:equal>
				</a>
			</th>
			<th style="width: 10%" class="header <%=sbHeaderHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'startDate', '<%=sbHeaderHolder.getNextSortOrder("startDate")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.sb.start.date" /></a>
			</th>
			<th style="width: 5%" class="header <%=sbHeaderHolder.getCurrentSortClass("sbStatus")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'sbStatus', '<%=sbHeaderHolder.getNextSortOrder("sbStatus")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.sb.status" /></a>
			</th>
			<th style="width: 10%" class="header <%=sbHeaderHolder.getCurrentSortClass("amountCompany")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'amountCompany', '<%=sbHeaderHolder.getNextSortOrder("amountCompany")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.sb.company" /></a>
			</th>
			<th style="width: 10%" class="header <%=sbHeaderHolder.getCurrentSortClass("amountPersonal")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'amountPersonal', '<%=sbHeaderHolder.getNextSortOrder("amountPersonal")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.sb.personal" /></a>
			</th>
			<th style="width: 5%" class="header <%=sbHeaderHolder.getCurrentSortClass("vendorServiceFee")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'vendorServiceFee', '<%=sbHeaderHolder.getNextSortOrder("vendorServiceFee")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.service.fee" /></a>
			</th>
			<th style="width: 5%" class="header <%=sbHeaderHolder.getCurrentSortClass("additionalStatus")%>">
				<a onclick="submitForm('listHeader_form', null, null, 'additionalStatus', '<%=sbHeaderHolder.getNextSortOrder("additionalStatus")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="sbHeaderHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="sbHeaderVO" name="sbHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><a onclick="link('vendorPaymentAction.do?proc=to_sbDetail&vendorId=<bean:write name="sbHeaderVO" property="encodedVendorId"/>&monthly=<bean:write name="sbHeaderVO" property="monthly"/>&headerId=<bean:write name="sbHeaderVO" property="encodedId"/>&additionalStatus=<bean:write name="sbHeaderVO" property="additionalStatus"/>');"><bean:write name="sbHeaderVO" property="headerId" /></a></td>
					<td class="left"><bean:write name="sbHeaderVO" property="monthly" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="employeeSBName" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="decodeEntityId" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="employeeId" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="onboardDate" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="decodeEmployStatus" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="startDate" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="decodeSbStatus" /></td>
					<td class="right"><bean:write name="sbHeaderVO" property="decodeAmountCompany" /></td>
					<td class="right"><bean:write name="sbHeaderVO" property="decodeAmountPersonal" /></td>
					<td class="left"><bean:write name="sbHeaderVO" property="vendorServiceFee"/></td>
					<td class="left"><bean:write name="sbHeaderVO" property="decodeAdditionalStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="sbHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="16" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="sbHeaderHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="sbHeaderHolder" property="indexStart" /> - <bean:write name="sbHeaderHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listHeader_form', null, '<bean:write name="sbHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="sbHeaderHolder" property="realPage" />/<bean:write name="sbHeaderHolder" property="pageCount" /> </label>&nbsp;</td>
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
