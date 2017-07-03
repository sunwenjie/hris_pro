<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder contractHolder = (PagedListHolder) request.getAttribute("contractHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 6%" class="header <%=contractHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchContract_form', null, null, 'contractId', '<%=contractHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">序号</a>
			</th>
			<th style="width: 6%" class="header <%=contractHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('searchContract_form', null, null, 'monthly', '<%=contractHolder.getNextSortOrder("monthly")%>', 'tableWrapper');">结算月份</a>
			</th>
			<th style="width: 8%" class="header <%=contractHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchContract_form', null, null, 'employeeId', '<%=contractHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">雇员ID</a>
			</th>
			<th style="width: 10%" class="header <%=contractHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchContract_form', null, null, 'employeeNameZH', '<%=contractHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">雇员姓名（中文）</a>
			</th>
			<th style="width: 10%" class="header <%=contractHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchContract_form', null, null, 'employeeNameEN', '<%=contractHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">雇员姓名（英文）</a>
			</th>
			<th style="width: 8%" class="header <%=contractHolder.getCurrentSortClass("employeeContractId")%>">
				<a onclick="submitForm('searchContract_form', null, null, 'employeeContractId', '<%=contractHolder.getNextSortOrder("employeeContractId")%>', 'tableWrapper');">派送信息ID</a>
			</th>
			<th style="width: 8%" class="header <%=contractHolder.getCurrentSortClass("timesheetId")%>">
				<a onclick="submitForm('searchContract_form', null, null, 'timesheetId', '<%=contractHolder.getNextSortOrder("timesheetId")%>', 'tableWrapper');">考勤表ID</a>
			</th>
			<th style="width: 8%" class="header <%=contractHolder.getCurrentSortClass("billAmountCompany")%>">
				<a onclick="submitForm('searchContract_form', null, null, 'billAmountCompany', '<%=contractHolder.getNextSortOrder("billAmountCompany")%>', 'tableWrapper');">公司营收</a>
			</th>
			<th style="width: 8%" class="header <%=contractHolder.getCurrentSortClass("costAmountCompany")%>">
				<a onclick="submitForm('searchContract_form', null, null, 'costAmountCompany', '<%=contractHolder.getNextSortOrder("costAmountCompany")%>', 'tableWrapper');">公司成本</a>
			</th>
			<th style="width: 8%" class="header <%=contractHolder.getCurrentSortClass("billAmountPersonal")%>">
				<a onclick="submitForm('searchContract_form', null, null, 'billAmountPersonal', '<%=contractHolder.getNextSortOrder("billAmountPersonal")%>', 'tableWrapper');">个人收入</a>
			</th>
			<th style="width: 8%" class="header <%=contractHolder.getCurrentSortClass("costAmountPersonal")%>">
				<a onclick="submitForm('searchContract_form', null, null, 'costAmountPersonal', '<%=contractHolder.getNextSortOrder("costAmountPersonal")%>', 'tableWrapper');">个人支出</a>
			</th>
			<th style="width: 12%" class="header-nosort">备注</th>
		</tr>
	</thead>
	<logic:notEqual name="contractHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="serviceContractVO" name="contractHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="serviceContractVO" property="contractId"/>" name="chkSelectRow[]" value="<bean:write name="serviceContractVO" property="encodedId"/>" />
					</td>
					<td class="left"><a onclick="link('settlementAction.do?proc=to_contractDetail&batchId=<bean:write name="batchForm" property="encodedId"/>&orderHeaderId=<bean:write name="serviceContractVO" property="encodedOrderHeaderId"/>&contractId=<bean:write name="serviceContractVO" property="encodedId"/>');"><bean:write name="serviceContractVO" property="contractId" /></a></td>
					<td class="left"><bean:write name="serviceContractVO" property="monthly"/></td>
					<td class="left"><a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="serviceContractVO" property="encodedEmployeeId"/>');"><bean:write name="serviceContractVO" property="employeeId" /></a></td>
					<td class="left"><bean:write name="serviceContractVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="serviceContractVO" property="employeeNameEN" /></td>
					<td class="left"><a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="serviceContractVO" property="encodedEmployeeContractId"/>');"><bean:write name="serviceContractVO" property="employeeContractId"/></a></td>
					<td class="left"><bean:write name="serviceContractVO" property="timesheetId"/></td>
					<td class="right"><bean:write name="serviceContractVO" property="billAmountCompany"/></td>
					<td class="right"><bean:write name="serviceContractVO" property="costAmountCompany"/></td>
					<td class="right"><bean:write name="serviceContractVO" property="billAmountPersonal"/></td>
					<td class="right"><bean:write name="serviceContractVO" property="costAmountPersonal"/></td>
					<td class="left">科目数量：<bean:write name="serviceContractVO" property="countItemId"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="contractHolder">
		<tfoot>
			<tr class="total">
				<td colspan="13" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="contractHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="contractHolder" property="indexStart" /> - <bean:write name="contractHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('searchContract_form', null, '<bean:write name="contractHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchContract_form', null, '<bean:write name="contractHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchContract_form', null, '<bean:write name="contractHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchContract_form', null, '<bean:write name="contractHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="contractHolder" property="realPage" />/<bean:write name="contractHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;跳转至：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="contractHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />页</label>&nbsp;
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
		submitForm('searchContract_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>