<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.sb.SBAdjustmentHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder sbAdjustmentHeaderHolder = ( PagedListHolder ) request.getAttribute( "sbAdjustmentHeaderHolder" ); 
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "12" : "14" %>%" class="header <%=sbAdjustmentHeaderHolder.getCurrentSortClass("sbSolutionId")%>">
				<a onclick="submitForm('searchSBAdjustmentHeader_form', null, null, 'sbSolutionId', '<%=sbAdjustmentHeaderHolder.getNextSortOrder("sbSolutionId")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.adjustment.header.sb.solution" /></a>
			</th>
			<th style="width: 6%" class="header <%=sbAdjustmentHeaderHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('searchSBAdjustmentHeader_form', null, null, 'monthly', '<%=sbAdjustmentHeaderHolder.getNextSortOrder("monthly")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.adjustment.header.monthly" /></a>
			</th>
			<th style="width: 7%" class="header <%=sbAdjustmentHeaderHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchSBAdjustmentHeader_form', null, null, 'employeeId', '<%=sbAdjustmentHeaderHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: 8%" class="header <%=sbAdjustmentHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchSBAdjustmentHeader_form', null, null, 'employeeNameZH', '<%=sbAdjustmentHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</a>
			</th>
			<th style="width: 8%" class="header <%=sbAdjustmentHeaderHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchSBAdjustmentHeader_form', null, null, 'employeeNameEN', '<%=sbAdjustmentHeaderHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</a>
			</th>
			<logic:equal name="role" value="1">
				<th style="width: 7%" class="header <%=sbAdjustmentHeaderHolder.getCurrentSortClass("clientId")%>">
					<a onclick="submitForm('searchSBAdjustmentHeader_form', null, null, 'clientId', '<%=sbAdjustmentHeaderHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">客户ID</a>
				</th>
				<th style="width: 10%" class="header-nosort">
					客户名称
				</th>
				<th style="width: 7%" class="header <%=sbAdjustmentHeaderHolder.getCurrentSortClass("orderId")%>">
					<a onclick="submitForm('searchSBAdjustmentHeader_form', null, null, 'orderId', '<%=sbAdjustmentHeaderHolder.getNextSortOrder("orderId")%>', 'tableWrapper');">订单ID</a>
				</th>
			</logic:equal>
			<logic:equal name="role" value="2">
				<th style="width: 16%" class="header <%=sbAdjustmentHeaderHolder.getCurrentSortClass("orderDescription")%>">
					<a onclick="submitForm('searchSBAdjustmentHeader_form', null, null, 'orderDescription', '<%=sbAdjustmentHeaderHolder.getNextSortOrder("orderDescription")%>', 'tableWrapper');"><bean:message bundle="public" key="public.order2" /></a>
				</th>
			</logic:equal>
			<th style="width: 7%" class="header <%=sbAdjustmentHeaderHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchSBAdjustmentHeader_form', null, null, 'contractId', '<%=sbAdjustmentHeaderHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "10" : "16" %>%" class="header-nosort">
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.name" /></logic:equal>
				<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.name" /></logic:equal>
			</th>
			<th style="width: 6%" class="header <%=sbAdjustmentHeaderHolder.getCurrentSortClass("amountCompany")%>">
				<a onclick="submitForm('searchSBAdjustmentHeader_form', null, null, 'amountCompany', '<%=sbAdjustmentHeaderHolder.getNextSortOrder("amountCompany")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.adjustment.header.money.company" /></a>
			</th>
			<th style="width: 6%" class="header <%=sbAdjustmentHeaderHolder.getCurrentSortClass("amountPersonal")%>">
				<a onclick="submitForm('searchSBAdjustmentHeader_form', null, null, 'amountPersonal', '<%=sbAdjustmentHeaderHolder.getNextSortOrder("amountPersonal")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.adjustment.header.money.personal" /></a>
			</th>
			<th style="width: 6%" class="header <%=sbAdjustmentHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchSBAdjustmentHeader_form', null, null, 'status', '<%=sbAdjustmentHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="sbAdjustmentHeaderHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="sbAdjustmentHeaderVO" name="sbAdjustmentHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="sbAdjustmentHeaderVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="sbAdjustmentHeaderVO" property="adjustmentHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="sbAdjustmentHeaderVO" property="encodedId"/>" />
						</logic:equal>
						<logic:equal name="sbAdjustmentHeaderVO" property="status" value="4">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="sbAdjustmentHeaderVO" property="adjustmentHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="sbAdjustmentHeaderVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('sbAdjustmentDetailAction.do?proc=list_object&id=<bean:write name="sbAdjustmentHeaderVO" property="encodedId"/>');">
							<bean:write name="sbAdjustmentHeaderVO" property="decodeSbSolutionId" />
						</a>
					</td>
					<td class="left">
						<a onclick="link('sbAdjustmentDetailAction.do?proc=list_object&id=<bean:write name="sbAdjustmentHeaderVO" property="encodedId"/>');">
							<bean:write name="sbAdjustmentHeaderVO" property="monthly" />
						</a>
					</td>
					<td class="left">
						<a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="sbAdjustmentHeaderVO" property="encodedEmployeeId"/>');">
							<bean:write name="sbAdjustmentHeaderVO" property="employeeId" />
						</a>
					</td>
					<td class="left"><bean:write name="sbAdjustmentHeaderVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="sbAdjustmentHeaderVO" property="employeeNameEN" /></td>
					<logic:equal name="role" value="1">
						<td class="left">
							<a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="sbAdjustmentHeaderVO" property="encodedClientId"/>');">
								<bean:write name="sbAdjustmentHeaderVO" property="clientId" />
							</a>
						</td>
						<td class="left"><bean:write name="sbAdjustmentHeaderVO" property="clientName" /></td>
						<td class="left">
							<a onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="sbAdjustmentHeaderVO" property="encodedOrderId"/>');">
								<bean:write name="sbAdjustmentHeaderVO" property="orderId" />
							</a>
						</td>
					</logic:equal>
					<logic:equal name="role" value="2">
						<td class="left"><bean:write name="sbAdjustmentHeaderVO" property="orderDescription" /></td>
					</logic:equal>
					<td class="left">
						<a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="sbAdjustmentHeaderVO" property="encodedContractId"/>');">
							<bean:write name="sbAdjustmentHeaderVO" property="contractId" />
						</a>
					</td>
					<td class="left"><bean:write name="sbAdjustmentHeaderVO" property="contractName" /></td>
					<td class="right"><bean:write name="sbAdjustmentHeaderVO" property="amountCompany" /></td>
					<td class="right"><bean:write name="sbAdjustmentHeaderVO" property="amountPersonal" /></td>
					<td class="left">
						<bean:write name="sbAdjustmentHeaderVO" property="decodeStatus" />
						<kan:auth right="submit" action="<%=SBAdjustmentHeaderAction.accessAction%>">
							<logic:equal name="sbAdjustmentHeaderVO" property="status" value="1" >
								&nbsp;&nbsp;<a onclick="submit_object('<bean:write name="sbAdjustmentHeaderVO" property="encodedId"/>')"><bean:message bundle="public" key="button.submit" /></a>
							</logic:equal>
							<logic:equal name="sbAdjustmentHeaderVO" property="status" value="4" >
								&nbsp;&nbsp;<a onclick="submit_object('<bean:write name="sbAdjustmentHeaderVO" property="encodedId"/>')"><bean:message bundle="public" key="button.submit" /></a>
							</logic:equal>
						</kan:auth>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="sbAdjustmentHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="16" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="sbAdjustmentHeaderHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="sbAdjustmentHeaderHolder" property="indexStart" /> - <bean:write name="sbAdjustmentHeaderHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('searchSBAdjustmentHeader_form', null, '<bean:write name="sbAdjustmentHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchSBAdjustmentHeader_form', null, '<bean:write name="sbAdjustmentHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchSBAdjustmentHeader_form', null, '<bean:write name="sbAdjustmentHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchSBAdjustmentHeader_form', null, '<bean:write name="sbAdjustmentHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="sbAdjustmentHeaderHolder" property="realPage" />/<bean:write name="sbAdjustmentHeaderHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="sbAdjustmentHeaderHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
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
		submitForm('searchSBAdjustmentHeader_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>