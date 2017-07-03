<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder headerTempListHolder = (PagedListHolder) request.getAttribute("headerTempListHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 6%;" class="header <%=headerTempListHolder.getCurrentSortClass("orderHeaderId")%>">
				<a onclick="submitForm('searchHeaderTemp_form', null, null, 'orderHeaderId', '<%=headerTempListHolder.getNextSortOrder("orderHeaderId")%>', 'tableWrapper');">序号</a>
			</th>
			<th style="width: 6%;" class="header <%=headerTempListHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('searchHeaderTemp_form', null, null, 'monthly', '<%=headerTempListHolder.getNextSortOrder("monthly")%>', 'tableWrapper');">账单月份</a>
			</th>
			<th style="width: 7%;" class="header <%=headerTempListHolder.getCurrentSortClass("clientId")%>">
				<a onclick="submitForm('searchHeaderTemp_form', null, null, 'clientId', '<%=headerTempListHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">客户ID</a>
			</th>
			<th style="width: 22%;" class="header <%=headerTempListHolder.getCurrentSortClass("clientNameZH")%>">
				<a onclick="submitForm('searchHeaderTemp_form', null, null, 'clientNameZH', '<%=headerTempListHolder.getNextSortOrder("clientNameZH")%>', 'tableWrapper');">客户名称</a>
			</th>
			<th style="width: 7%;" class="header <%=headerTempListHolder.getCurrentSortClass("orderId")%>">
				<a onclick="submitForm('searchHeaderTemp_form', null, null, 'orderId', '<%=headerTempListHolder.getNextSortOrder("orderId")%>', 'tableWrapper');">订单ID</a>
			</th>
			<th style="width: 8%;" class="header <%=headerTempListHolder.getCurrentSortClass("billAmountCompany")%>">
				<a onclick="submitForm('searchHeaderTemp_form', null, null, 'billAmountCompany', '<%=headerTempListHolder.getNextSortOrder("billAmountCompany")%>', 'tableWrapper');">公司营收</a>
			</th>
			<th style="width: 8%;" class="header <%=headerTempListHolder.getCurrentSortClass("costAmountCompany")%>">
				<a onclick="submitForm('searchHeaderTemp_form', null, null, 'costAmountCompany', '<%=headerTempListHolder.getNextSortOrder("costAmountCompany")%>', 'tableWrapper');">公司成本</a>
			</th>
			<th style="width: 8%;" class="header <%=headerTempListHolder.getCurrentSortClass("billAmountPersonal")%>">
				<a onclick="submitForm('searchHeaderTemp_form', null, null, 'billAmountPersonal', '<%=headerTempListHolder.getNextSortOrder("billAmountPersonal")%>', 'tableWrapper');">个人收入</a>
			</th>
			<th style="width: 8%;" class="header <%=headerTempListHolder.getCurrentSortClass("costAmountPersonal")%>">
				<a onclick="submitForm('searchHeaderTemp_form', null, null, 'costAmountPersonal', '<%=headerTempListHolder.getNextSortOrder("costAmountPersonal")%>', 'tableWrapper');">个人支出</a>
			</th>
			<th style="width: 21%;" class="header-nosort">
				<span>备注</span>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="headerTempListHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="headerTempVO" name="headerTempListHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="headerTempVO" property="orderHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="headerTempVO" property="encodedId"/>" />
					</td>
					<td class="left"><a onclick="link('settlementTempAction.do?proc=to_headerDetail&batchId=<bean:write name="headerTempVO" property="encodedBatchId"/>&orderHeaderId=<bean:write name="headerTempVO" property="encodedId"/>');"><bean:write name="headerTempVO" property="orderHeaderId" /></a></td>
					<td class="left"><bean:write name="headerTempVO" property="monthly" /></td>
					<td class="left"><a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="headerTempVO" property="encodedClientId"/>');"><bean:write name="headerTempVO" property="clientId" /></a></td>
					<td class="left"><bean:write name="headerTempVO" property="clientName" /></td>
					<td class="left"><a onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="headerTempVO" property="encodedOrderId"/>');"><bean:write name="headerTempVO" property="orderId" /></a></td>
					<td class="right"><bean:write name="headerTempVO" property="billAmountCompany" /></td>
					<td class="right"><bean:write name="headerTempVO" property="costAmountCompany" /></td>
					<td class="right"><bean:write name="headerTempVO" property="billAmountPersonal" /></td>
					<td class="right"><bean:write name="headerTempVO" property="costAmountPersonal" /></td>
					<td class="left">雇员人次：<bean:write name="headerTempVO" property="countContractId" />；科目：<bean:write name="headerTempVO" property="countItemId" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="headerTempListHolder">
		<tfoot>
			<tr class="total">
				<td colspan="13" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="headerTempListHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="headerTempListHolder" property="indexStart" /> - <bean:write name="headerTempListHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('searchHeaderTemp_form', null, '<bean:write name="headerTempListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchHeaderTemp_form', null, '<bean:write name="headerTempListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchHeaderTemp_form', null, '<bean:write name="headerTempListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchHeaderTemp_form', null, '<bean:write name="headerTempListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="headerTempListHolder" property="realPage" />/<bean:write name="headerTempListHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;跳转至：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="headerTempListHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />页</label>&nbsp;
				</td>
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
	
	function forward() {
		var value = Number($('#forwardPage').val());
		// 如果页数无效自动跳转到第一页
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage = Number($('.forwardPage').val()) - 1;
		submitForm('searchHeaderTemp_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>