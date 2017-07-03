<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder cbHeaderHolder = (PagedListHolder) request.getAttribute("cbHeaderHolder");
	final boolean IN_HOUSE = request.getAttribute( "role" ).equals( "2" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 6%" class="header <%=cbHeaderHolder.getCurrentSortClass("a.headerId")%>">
				<a onclick="submitForm('searchCBHeader_form', null, null, 'a.headerId', '<%=cbHeaderHolder.getNextSortOrder("a.headerId")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.header.id" /></a>
			</th>
			<th style="width: 18%" class="header <%=cbHeaderHolder.getCurrentSortClass("employeeCBId")%>">
				<a onclick="submitForm('searchCBHeader_form', null, null, 'employeeCBId', '<%=cbHeaderHolder.getNextSortOrder("employeeCBId")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.batch.solution" /></a>
			</th>
			<th style="width: 7%" class="header <%=cbHeaderHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchCBHeader_form', null, null, 'contractId', '<%=cbHeaderHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: 7%" class="header <%=cbHeaderHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchCBHeader_form', null, null, 'employeeId', '<%=cbHeaderHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: 10%" class="header <%=cbHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchCBHeader_form', null, null, 'employeeNameZH', '<%=cbHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</a>
			</th>
			<th style="width: 10%" class="header <%=cbHeaderHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchCBHeader_form', null, null, 'employeeNameEN', '<%=cbHeaderHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</a>
			</th>
			<th style="width: 6%" class="header <%=cbHeaderHolder.getCurrentSortClass("employStatus")%>">
				<a onclick="submitForm('searchCBHeader_form', null, null, 'employStatus', '<%=cbHeaderHolder.getNextSortOrder("employStatus")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.status" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.status" /></logic:equal>
				</a>
			</th>
			<th style="width: 6%" class="header <%=cbHeaderHolder.getCurrentSortClass("cbStatus")%>">
				<a onclick="submitForm('searchCBHeader_form', null, null, 'cbStatus', '<%=cbHeaderHolder.getNextSortOrder("cbStatus")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.header.status" /></a>
			</th>
			<logic:equal name="role" value="1">
			<th style="width: 8%" class="header <%=cbHeaderHolder.getCurrentSortClass("amountSalesPrice")%>">
				<a onclick="submitForm('searchCBHeader_form', null, null, 'amountSalesPrice', '<%=cbHeaderHolder.getNextSortOrder("amountSalesPrice")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.batch.amount.sales.price" /></a>
			</th>
			</logic:equal>
			<th style="width: 8%" class="header <%=cbHeaderHolder.getCurrentSortClass("amountSalesCost")%>">
				<a onclick="submitForm('searchCBHeader_form', null, null, 'amountPurchaseCost', '<%=cbHeaderHolder.getNextSortOrder("amountPurchaseCost")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.batch.amount.sales.cost" /></a>
			</th>
			<th style="width: <%=IN_HOUSE ? "16" : "8" %>%" class="header-nosort">
				<span><bean:message bundle="public" key="public.note" /></span>
			</th>
			<th style="width: 6%" class="header <%=cbHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchCBHeader_form', null, null, 'status', '<%=cbHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="cbHeaderHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="cbHeaderVO" name="cbHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" <logic:greaterEqual name="cbHeaderVO" property="additionalStatus" value="4">class="hide"</logic:greaterEqual> id="kanList_chkSelectRecord_<bean:write name="cbHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="cbHeaderVO" property="encodedId"/>" />
					</td>
					<td class="left"><a onclick="link('cbAction.do?proc=to_cbDetail&batchId=<bean:write name="cbHeaderVO" property="encodedBatchId"/>&contractId=<bean:write name="cbHeaderVO" property="encodedContractId"/>&headerId=<bean:write name="cbHeaderVO" property="encodedId"/>&statusFlag=<bean:write name="cbBatchForm" property="statusFlag"/>');"><bean:write name="cbHeaderVO" property="headerId" /></a></td>
					<td class="left"><span><bean:write name="cbHeaderVO" property="employeeCBName" /></span></td>
					<td class="left"><a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="cbHeaderVO" property="encodedContractId"/>');"><bean:write name="cbHeaderVO" property="contractId" /></a></td>
					<td class="left"><a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="cbHeaderVO" property="encodedEmployeeId"/>');"><bean:write name="cbHeaderVO" property="employeeId" /></a></td>
					<td class="left"><span><bean:write name="cbHeaderVO" property="employeeNameZH" /></span></td>
					<td class="left"><span><bean:write name="cbHeaderVO" property="employeeNameEN" /></span></td>
					<td class="left"><bean:write name="cbHeaderVO" property="decodeEmployStatus" /></td>
					<td class="left"><bean:write name="cbHeaderVO" property="decodeCbStatus" /></td>
					<logic:equal name="role" value="1">
						<td class="right"><span><bean:write name="cbHeaderVO" property="decodeAmountSalesPrice" /></span></td>
					</logic:equal>
					<td class="right"><span><bean:write name="cbHeaderVO" property="decodeAmountPurchaseCost" /></span></td>
					<td class="left"><span>科目：<bean:write name="cbHeaderVO" property="countItemId"/></span></td>
					<td class="left"><bean:write name="cbHeaderVO" property="decodeAdditionalStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="cbHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="14" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="cbHeaderHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="cbHeaderHolder" property="indexStart" /> - <bean:write name="cbHeaderHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchCBHeader_form', null, '<bean:write name="cbHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchCBHeader_form', null, '<bean:write name="cbHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchCBHeader_form', null, '<bean:write name="cbHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchCBHeader_form', null, '<bean:write name="cbHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="cbHeaderHolder" property="realPage" />/<bean:write name="cbHeaderHolder" property="pageCount" /> </label>&nbsp;
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="cbHeaderHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
			</tr>
		</tfoot>
	</logic:present>
</table>
<div class="bottom">
	<p></p>
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
		submitForm('list_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>
