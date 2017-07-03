<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder settlementAdjustmentImportHeaderHolder = ( PagedListHolder ) request.getAttribute( "settlementAdjustmentImportHeaderHolder" ); 
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 3%" class="header <%=settlementAdjustmentImportHeaderHolder.getCurrentSortClass("adjustmentHeaderId")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, null, 'adjustmentHeaderId', '<%=settlementAdjustmentImportHeaderHolder.getNextSortOrder("adjustmentHeaderId")%>', 'tableWrapper');">ID</a>
			</th>
			<th style="width: 6%" class="header <%=settlementAdjustmentImportHeaderHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, null, 'monthly', '<%=settlementAdjustmentImportHeaderHolder.getNextSortOrder("monthly")%>', 'tableWrapper');">调整月份</a>
			</th>
			<th style="width: 7%" class="header <%=settlementAdjustmentImportHeaderHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, null, 'employeeId', '<%=settlementAdjustmentImportHeaderHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');"><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>ID</a>
			</th>
			<th style="width: 8%" class="header <%=settlementAdjustmentImportHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, null, 'employeeNameZH', '<%=settlementAdjustmentImportHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');"><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（中文）</a>
			</th>
			<th style="width: 8%" class="header <%=settlementAdjustmentImportHeaderHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, null, 'employeeNameEN', '<%=settlementAdjustmentImportHeaderHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');"><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（英文）</a>
			</th>
			<logic:equal name="role" value="1">
				<th style="width: 7%" class="header <%=settlementAdjustmentImportHeaderHolder.getCurrentSortClass("clientId")%>">
					<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, null, 'clientId', '<%=settlementAdjustmentImportHeaderHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">客户ID</a>
				</th>
				<th style="width: 10%" class="header-nosort">
					客户名称
				</th>
				<th style="width: 7%" class="header <%=settlementAdjustmentImportHeaderHolder.getCurrentSortClass("orderId")%>">
					<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, null, 'orderId', '<%=settlementAdjustmentImportHeaderHolder.getNextSortOrder("orderId")%>', 'tableWrapper');">订单ID</a>
				</th>
			</logic:equal>
			<logic:equal name="role" value="2">
				<th style="width: 16%" class="header <%=settlementAdjustmentImportHeaderHolder.getCurrentSortClass("orderDescription")%>">
					<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, null, 'orderDescription', '<%=settlementAdjustmentImportHeaderHolder.getNextSortOrder("orderDescription")%>', 'tableWrapper');">结算规则名称</a>
				</th>
			</logic:equal>
			<th style="width: 7%" class="header <%=settlementAdjustmentImportHeaderHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, null, 'contractId', '<%=settlementAdjustmentImportHeaderHolder.getNextSortOrder("contractId")%>', 'tableWrapper');"><logic:equal name="role" value="1">派送协议</logic:equal><logic:equal name="role" value="2">劳动合同</logic:equal>ID</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "10" : "16" %>%" class="header-nosort">
				<logic:equal name="role" value="1">派送协议名称</logic:equal><logic:equal name="role" value="2">劳动合同名称</logic:equal>
			</th>
			<th style="width: 6%" class="header <%=settlementAdjustmentImportHeaderHolder.getCurrentSortClass("amountCompany")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, null, 'amountCompany', '<%=settlementAdjustmentImportHeaderHolder.getNextSortOrder("billAmountCompany")%>', 'tableWrapper');">公司营收</a>
			</th>
			<th style="width: 6%" class="header <%=settlementAdjustmentImportHeaderHolder.getCurrentSortClass("amountCompany")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, null, 'amountCompany', '<%=settlementAdjustmentImportHeaderHolder.getNextSortOrder("costAmountCompany")%>', 'tableWrapper');">公司成本</a>
			</th>
			<th style="width: 6%" class="header <%=settlementAdjustmentImportHeaderHolder.getCurrentSortClass("amountPersonal")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, null, 'amountPersonal', '<%=settlementAdjustmentImportHeaderHolder.getNextSortOrder("billAmountPersonal")%>', 'tableWrapper');">个人收入</a>
			</th>
			<th style="width: 6%" class="header <%=settlementAdjustmentImportHeaderHolder.getCurrentSortClass("amountPersonal")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, null, 'amountPersonal', '<%=settlementAdjustmentImportHeaderHolder.getNextSortOrder("costAmountPersonal")%>', 'tableWrapper');">个人支出</a>
			</th>
			<th style="width: 6%" class="header <%=settlementAdjustmentImportHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, null, 'status', '<%=settlementAdjustmentImportHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="settlementAdjustmentImportHeaderHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="settlementAdjustmentHeaderVO" name="settlementAdjustmentImportHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="settlementAdjustmentHeaderVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="settlementAdjustmentHeaderVO" property="adjustmentHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="settlementAdjustmentHeaderVO" property="adjustmentHeaderId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('settlementAdjustmentImportDetailAction.do?proc=list_object&headerId=<bean:write name="settlementAdjustmentHeaderVO" property="encodedId" />');"><bean:write name="settlementAdjustmentHeaderVO" property="adjustmentHeaderId" /></a>
					</td>
					<td class="left">
						<bean:write name="settlementAdjustmentHeaderVO" property="monthly" />
					</td>
					<td class="left">
						<a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="settlementAdjustmentHeaderVO" property="encodedEmployeeId"/>');">
							<bean:write name="settlementAdjustmentHeaderVO" property="employeeId" />
						</a>
					</td>
					<td class="left"><bean:write name="settlementAdjustmentHeaderVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="settlementAdjustmentHeaderVO" property="employeeNameEN" /></td>
					<logic:equal name="role" value="1">
						<td class="left">
							<a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="settlementAdjustmentHeaderVO" property="encodedClientId"/>');">
								<bean:write name="settlementAdjustmentHeaderVO" property="clientId" />
							</a>
						</td>
						<td class="left"><bean:write name="settlementAdjustmentHeaderVO" property="clientName" /></td>
						<td class="left">
							<a onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="settlementAdjustmentHeaderVO" property="encodedOrderId"/>');">
								<bean:write name="settlementAdjustmentHeaderVO" property="orderId" />
							</a>
						</td>
					</logic:equal>
					<logic:equal name="role" value="2">
						<td class="left"><bean:write name="settlementAdjustmentHeaderVO" property="orderDescription" /></td>
					</logic:equal>
					<td class="left">
						<a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="settlementAdjustmentHeaderVO" property="encodedContractId"/>');">
							<bean:write name="settlementAdjustmentHeaderVO" property="contractId" />
						</a>
					</td>
					<td class="left"><bean:write name="settlementAdjustmentHeaderVO" property="contractNameZH" /></td>
					<td class="right"><bean:write name="settlementAdjustmentHeaderVO" property="billAmountCompany" /></td>
					<td class="right"><bean:write name="settlementAdjustmentHeaderVO" property="costAmountCompany" /></td>
					<td class="right"><bean:write name="settlementAdjustmentHeaderVO" property="billAmountPersonal" /></td>
					<td class="right"><bean:write name="settlementAdjustmentHeaderVO" property="costAmountPersonal" /></td>
					<td class="left">
						<bean:write name="settlementAdjustmentHeaderVO" property="decodeStatus" />
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="settlementAdjustmentImportHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="16" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="settlementAdjustmentImportHeaderHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="settlementAdjustmentImportHeaderHolder" property="indexStart" /> - <bean:write name="settlementAdjustmentImportHeaderHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, '<bean:write name="settlementAdjustmentImportHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, '<bean:write name="settlementAdjustmentImportHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, '<bean:write name="settlementAdjustmentImportHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchSettlementAdjustmentImportHeader_form', null, '<bean:write name="settlementAdjustmentImportHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="settlementAdjustmentImportHeaderHolder" property="realPage" />/<bean:write name="settlementAdjustmentImportHeaderHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;跳转至：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="settlementAdjustmentImportHeaderHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />页</label>&nbsp;
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
	
	function forward() {
		var value = Number($('#forwardPage').val());
		// 如果页数无效自动跳转到第一页
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage = Number($('.forwardPage').val()) - 1;
		submitForm('searchSettlementAdjustmentImportHeader_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>