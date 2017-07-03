<%@ page pageEncoding="GBK"%>
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
			<th style="width: 6%;" class="header <%=headerListHolder.getCurrentSortClass("orderHeaderId")%>">
				<a onclick="submitForm('searchHeader_form', null, null, 'orderHeaderId', '<%=headerListHolder.getNextSortOrder("orderHeaderId")%>', 'tableWrapper');">���</a>
			</th>
			<th style="width: 6%;" class="header <%=headerListHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('searchHeader_form', null, null, 'monthly', '<%=headerListHolder.getNextSortOrder("monthly")%>', 'tableWrapper');">�����·�</a>
			</th>
			<th style="width: 7%;" class="header <%=headerListHolder.getCurrentSortClass("clientId")%>">
				<a onclick="submitForm('searchHeader_form', null, null, 'clientId', '<%=headerListHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">�ͻ�ID</a>
			</th>
			<th style="width: 25%;" class="header <%=headerListHolder.getCurrentSortClass("clientNameZH")%>">
				<a onclick="submitForm('searchHeader_form', null, null, 'clientNameZH', '<%=headerListHolder.getNextSortOrder("clientNameZH")%>', 'tableWrapper');">�ͻ�����</a>
			</th>
			<th style="width: 7%;" class="header <%=headerListHolder.getCurrentSortClass("orderId")%>">
				<a onclick="submitForm('searchHeader_form', null, null, 'orderId', '<%=headerListHolder.getNextSortOrder("orderId")%>', 'tableWrapper');">����ID</a>
			</th>
			<th style="width: 8%;" class="header <%=headerListHolder.getCurrentSortClass("billAmountCompany")%>">
				<a onclick="submitForm('searchHeader_form', null, null, 'billAmountCompany', '<%=headerListHolder.getNextSortOrder("billAmountCompany")%>', 'tableWrapper');">��˾Ӫ��</a>
			</th>
			<th style="width: 8%;" class="header <%=headerListHolder.getCurrentSortClass("costAmountCompany")%>">
				<a onclick="submitForm('searchHeader_form', null, null, 'costAmountCompany', '<%=headerListHolder.getNextSortOrder("costAmountCompany")%>', 'tableWrapper');">��˾�ɱ�</a>
			</th>
			<th style="width: 8%;" class="header <%=headerListHolder.getCurrentSortClass("billAmountPersonal")%>">
				<a onclick="submitForm('searchHeader_form', null, null, 'billAmountPersonal', '<%=headerListHolder.getNextSortOrder("billAmountPersonal")%>', 'tableWrapper');">��������</a>
			</th>
			<th style="width: 8%;" class="header <%=headerListHolder.getCurrentSortClass("costAmountPersonal")%>">
				<a onclick="submitForm('searchHeader_form', null, null, 'costAmountPersonal', '<%=headerListHolder.getNextSortOrder("costAmountPersonal")%>', 'tableWrapper');">����֧��</a>
			</th>
			<th style="width: 17%;" class="header-nosort">
				<span>��ע</span>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="headerListHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="headerVO" name="headerListHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="headerVO" property="orderHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="headerVO" property="encodedId"/>" />
					</td>
					<td class="left"><a onclick="link('settlementContractAction.do?proc=list_object&batchId=<bean:write name="headerVO" property="encodedBatchId"/>&orderHeaderId=<bean:write name="headerVO" property="encodedId"/>');"><bean:write name="headerVO" property="orderHeaderId" /></a></td>
					<td class="left"><bean:write name="headerVO" property="monthly" /></td>
					<td class="left"><a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="headerVO" property="encodedClientId"/>');"><bean:write name="headerVO" property="clientId" /></a></td>
					<td class="left"><bean:write name="headerVO" property="clientName" /></td>
					<td class="left"><a onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="headerVO" property="encodedOrderId"/>');"><bean:write name="headerVO" property="orderId" /></a></td>
					<td class="right"><bean:write name="headerVO" property="billAmountCompany" /></td>
					<td class="right"><bean:write name="headerVO" property="costAmountCompany" /></td>
					<td class="right"><bean:write name="headerVO" property="billAmountPersonal" /></td>
					<td class="right"><bean:write name="headerVO" property="costAmountPersonal" /></td>
					<td class="left">��Ա�˴Σ�<bean:write name="headerVO" property="countContractId" />����Ŀ��<bean:write name="headerVO" property="countItemId" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="headerListHolder">
		<tfoot>
			<tr class="total">
				<td colspan="13" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />�� <bean:write name="headerListHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />��<bean:write name="headerListHolder" property="indexStart" /> - <bean:write name="headerListHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('searchHeader_form', null, '<bean:write name="headerListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchHeader_form', null, '<bean:write name="headerListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchHeader_form', null, '<bean:write name="headerListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchHeader_form', null, '<bean:write name="headerListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />��<bean:write name="headerListHolder" property="realPage" />/<bean:write name="headerListHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;��ת����<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="headerListHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />ҳ</label>&nbsp;
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
		// ���ҳ����Ч�Զ���ת����һҳ
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage = Number($('.forwardPage').val()) - 1;
		submitForm('searchHeader_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>