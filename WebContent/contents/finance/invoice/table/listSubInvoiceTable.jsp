<%@ page pageEncoding="GB2312"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder headerListHolder = (PagedListHolder) request.getAttribute("headerListHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 6%;" class="header <%=headerListHolder.getCurrentSortClass("invoiceId")%>">
				<a onclick="submitForm('listComInvoice_form', null, null, 'invoiceId', '<%=headerListHolder.getNextSortOrder("invoiceId")%>', 'tableWrapper');">序号</a>
			</th>
			<th style="width: 16%;" class="header <%=headerListHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('listComInvoice_form', null, null, 'monthly', '<%=headerListHolder.getNextSortOrder("monthly")%>', 'tableWrapper');">月份</a>
			</th>
			<th style="width: 17%;" class="header <%=headerListHolder.getCurrentSortClass("clientId")%>">
				<a onclick="submitForm('listComInvoice_form', null, null, 'clientId', '<%=headerListHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">客户ID</a>
			</th>
			<th style="width: 25%;" class="header <%=headerListHolder.getCurrentSortClass("clientNameZH")%>">
				<a onclick="submitForm('listComInvoice_form', null, null, 'clientNameZH', '<%=headerListHolder.getNextSortOrder("clientNameZH")%>', 'tableWrapper');">客户名称</a>
			</th>
			<th style="width: 7%;" class="header <%=headerListHolder.getCurrentSortClass("orderId")%>">
				<a onclick="submitForm('listComInvoice_form', null, null, 'orderId', '<%=headerListHolder.getNextSortOrder("orderId")%>', 'tableWrapper');">订单ID</a>
			</th>
			<th style="width: 8%;" class="header <%=headerListHolder.getCurrentSortClass("billAmountCompany")%>">
				<a onclick="submitForm('listComInvoice_form', null, null, 'billAmountCompany', '<%=headerListHolder.getNextSortOrder("billAmountCompany")%>', 'tableWrapper');">公司营收</a>
			</th>
			<th style="width: 8%;" class="header <%=headerListHolder.getCurrentSortClass("costAmountCompany")%>">
				<a onclick="submitForm('listComInvoice_form', null, null, 'costAmountCompany', '<%=headerListHolder.getNextSortOrder("costAmountCompany")%>', 'tableWrapper');">公司成本</a>
			</th>
			<th style="width: 8%;" class="header <%=headerListHolder.getCurrentSortClass("taxAmount")%>">
				<a onclick="submitForm('listComInvoice_form', null, null, 'taxAmount', '<%=headerListHolder.getNextSortOrder("taxAmount")%>', 'tableWrapper');">个税</a>
			</th>
			<th style="width: 17%;" class="header-nosort">
				<span>备注</span>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="headerListHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="headerVO" name="headerListHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
<!-- 					<td> -->
<%-- 						<logic:empty name="headerVO" property="parentInvoiceId"> --%>
<%-- 							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="headerVO" property="invoiceId"/>" name="chkSelectRow[]" value="<bean:write name="headerVO" property="encodedId"/>" /> --%>
<%-- 						</logic:empty> --%>
<!-- 					</td> -->
					<td class="left"><a onclick="link('systemInvoiceDetailAction.do?proc=list_object&pageFlag=pageFlag=Split&invoiceId=<bean:write name="headerVO" property="encodedId"/>&batchId=<bean:write name="headerVO" property="encodedBatchId"/>');"><bean:write name="headerVO" property="invoiceId" /></a></td>
					<td class="left"><bean:write name="headerVO" property="monthly" /></td>
					<td class="left"><a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="headerVO" property="encodedClientId"/>');"><bean:write name="headerVO" property="clientId" /></a></td>
					<td class="left"><bean:write name="headerVO" property="clientName" /></td>
					<td class="left"><a onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="headerVO" property="encodedOrderId"/>');"><bean:write name="headerVO" property="orderId" /></a></td>
					<td class="right"><bean:write name="headerVO" property="billAmountCompany" /></td>
					<td class="right"><bean:write name="headerVO" property="costAmountCompany" /></td>
					<td class="right"><bean:write name="headerVO" property="taxAmount" /></td>
					<td class="left"></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="headerListHolder">
		<tfoot>
			<tr class="total">
				<td colspan="13" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="headerListHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="headerListHolder" property="indexStart" /> - <bean:write name="headerListHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('listComInvoice_form', null, '<bean:write name="headerListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listComInvoice_form', null, '<bean:write name="headerListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listComInvoice_form', null, '<bean:write name="headerListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listComInvoice_form', null, '<bean:write name="headerListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="headerListHolder" property="realPage" />/<bean:write name="headerListHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
<div class="bottom">
	<p/>
</div>
						
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
	})(jQuery);	
</script>
