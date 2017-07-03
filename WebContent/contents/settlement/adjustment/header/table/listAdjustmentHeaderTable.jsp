<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.AdjustmentHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder adjustmentHeaderHolder = ( PagedListHolder ) request.getAttribute( "adjustmentHeaderHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 5%" class="header <%=adjustmentHeaderHolder.getCurrentSortClass("adjustmentHeaderId")%>">
				<a onclick="submitForm('searchAdjustmentHeader_form', null, null, 'adjustmentHeaderId', '<%=adjustmentHeaderHolder.getNextSortOrder("adjustmentHeaderId")%>', 'tableWrapper');">调整ID</a>
			</th>
			<th style="width: 7%" class="header <%=adjustmentHeaderHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchAdjustmentHeader_form', null, null, 'employeeId', '<%=adjustmentHeaderHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');"><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>ID</a>
			</th>
			<th style="width: 10%" class="header <%=adjustmentHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchAdjustmentHeader_form', null, null, 'employeeNameZH', '<%=adjustmentHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');"><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（中文）</a>
			</th>
			<th style="width: 10%" class="header <%=adjustmentHeaderHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchAdjustmentHeader_form', null, null, 'employeeNameEN', '<%=adjustmentHeaderHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');"><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（英文）</a>
			</th>
			<logic:equal name="role" value="1">
				<th style="width: 7%" class="header <%=adjustmentHeaderHolder.getCurrentSortClass("clientId")%>">
					<a onclick="submitForm('searchAdjustmentHeader_form', null, null, 'clientId', '<%=adjustmentHeaderHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">客户ID</a>
				</th>
				<th style="width: 17%" class="header-nosort">
					客户名称
				</th>
			</logic:equal>
			<th style="width: 7%" class="header <%=adjustmentHeaderHolder.getCurrentSortClass("orderId")%>">
				<a onclick="submitForm('searchAdjustmentHeader_form', null, null, 'orderId', '<%=adjustmentHeaderHolder.getNextSortOrder("orderId")%>', 'tableWrapper');"><logic:equal name="role" value="1">订单</logic:equal><logic:equal name="role" value="2">结算规则</logic:equal>ID</a>
			</th>
			<th style="width: 7%" class="header <%=adjustmentHeaderHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchAdjustmentHeader_form', null, null, 'contractId', '<%=adjustmentHeaderHolder.getNextSortOrder("contractId")%>', 'tableWrapper');"><logic:equal name="role" value="1">派送协议</logic:equal><logic:equal name="role" value="2">劳动合同</logic:equal>ID</a>
			</th>
			<th style="width: <logic:equal name="role" value="1">6</logic:equal><logic:equal name="role" value="2">12</logic:equal>%" class="header <%=adjustmentHeaderHolder.getCurrentSortClass("billAmountCompany")%>">
				<a onclick="submitForm('searchAdjustmentHeader_form', null, null, 'billAmountCompany', '<%=adjustmentHeaderHolder.getNextSortOrder("billAmountCompany")%>', 'tableWrapper');">公司营收</a>
			</th>
			<th style="width: <logic:equal name="role" value="1">6</logic:equal><logic:equal name="role" value="2">12</logic:equal>%" class="header <%=adjustmentHeaderHolder.getCurrentSortClass("costAmountCompany")%>">
				<a onclick="submitForm('searchAdjustmentHeader_form', null, null, 'costAmountCompany', '<%=adjustmentHeaderHolder.getNextSortOrder("costAmountCompany")%>', 'tableWrapper');">公司成本</a>
			</th>
			<th style="width: <logic:equal name="role" value="1">6</logic:equal><logic:equal name="role" value="2">12</logic:equal>%" class="header <%=adjustmentHeaderHolder.getCurrentSortClass("costAmountPersonal")%>">
				<a onclick="submitForm('searchAdjustmentHeader_form', null, null, 'costAmountPersonal', '<%=adjustmentHeaderHolder.getNextSortOrder("costAmountPersonal")%>', 'tableWrapper');">个人支出</a>
			</th>
			<th style="width: <logic:equal name="role" value="1">6</logic:equal><logic:equal name="role" value="2">12</logic:equal>%" class="header <%=adjustmentHeaderHolder.getCurrentSortClass("billAmountPersonal")%>">
				<a onclick="submitForm('searchAdjustmentHeader_form', null, null, 'billAmountPersonal', '<%=adjustmentHeaderHolder.getNextSortOrder("billAmountPersonal")%>', 'tableWrapper');">个人收入</a>
			</th>
			<th style="width: 6%" class="header <%=adjustmentHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchAdjustmentHeader_form', null, null, 'status', '<%=adjustmentHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="adjustmentHeaderHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="adjustmentHeaderVO" name="adjustmentHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>	
						<logic:equal name="adjustmentHeaderVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="adjustmentHeaderVO" property="adjustmentHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="adjustmentHeaderVO" property="encodedId"/>" />
						</logic:equal>
						<logic:equal name="adjustmentHeaderVO" property="status" value="4">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="adjustmentHeaderVO" property="adjustmentHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="adjustmentHeaderVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('adjustmentDetailAction.do?proc=list_object&id=<bean:write name="adjustmentHeaderVO" property="encodedId"/>');">
							<bean:write name="adjustmentHeaderVO" property="adjustmentHeaderId" />
						</a>
					</td>
					<td class="left">
						<a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="adjustmentHeaderVO" property="encodedEmployeeId"/>');">
							<bean:write name="adjustmentHeaderVO" property="employeeId" />
						</a>
					</td>
					<td class="left"><bean:write name="adjustmentHeaderVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="adjustmentHeaderVO" property="employeeNameEN" /></td>
					<logic:equal name="role" value="1">
						<td class="left">
							<a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="adjustmentHeaderVO" property="encodedClientId"/>');">
								<bean:write name="adjustmentHeaderVO" property="clientId" />
							</a>
						</td>
						<td class="left"><bean:write name="adjustmentHeaderVO" property="clientName" /></td>
					</logic:equal>
					<td class="left">
						<a onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="adjustmentHeaderVO" property="encodedOrderId"/>');">
							<bean:write name="adjustmentHeaderVO" property="orderId" />
						</a>
					</td>
					<td class="left">
						<a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="adjustmentHeaderVO" property="encodedContractId"/>');">
							<bean:write name="adjustmentHeaderVO" property="contractId" />
						</a>
					</td>
					<td class="right"><bean:write name="adjustmentHeaderVO" property="billAmountCompany" /></td>
					<td class="right"><bean:write name="adjustmentHeaderVO" property="costAmountCompany" /></td>
					<td class="right"><bean:write name="adjustmentHeaderVO" property="billAmountPersonal" /></td>
					<td class="right"><bean:write name="adjustmentHeaderVO" property="costAmountPersonal" /></td>
					<td class="left">
						<bean:write name="adjustmentHeaderVO" property="decodeStatus" />
						<kan:auth right="submit" action="<%=AdjustmentHeaderAction.accessAction%>">
							<logic:equal name="adjustmentHeaderVO" property="status" value="1">
								&nbsp;&nbsp;<a onclick="submit_object('<bean:write name="adjustmentHeaderVO" property="encodedId"/>')">提交</a>
							</logic:equal>
							<logic:equal name="adjustmentHeaderVO" property="status" value="4">
								&nbsp;&nbsp;<a onclick="submit_object('<bean:write name="adjustmentHeaderVO" property="encodedId"/>')">提交</a>
							</logic:equal>
						</kan:auth>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="adjustmentHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="15" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="adjustmentHeaderHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="adjustmentHeaderHolder" property="indexStart" /> - <bean:write name="adjustmentHeaderHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('searchAdjustmentHeader_form', null, '<bean:write name="adjustmentHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchAdjustmentHeader_form', null, '<bean:write name="adjustmentHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchAdjustmentHeader_form', null, '<bean:write name="adjustmentHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchAdjustmentHeader_form', null, '<bean:write name="adjustmentHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="adjustmentHeaderHolder" property="realPage" />/<bean:write name="adjustmentHeaderHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;跳转至：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="adjustmentHeaderHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />页</label>&nbsp;
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
		submitForm('searchAdjustmentHeader_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>