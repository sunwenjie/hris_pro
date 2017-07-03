<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder contractTempHolder = (PagedListHolder) request.getAttribute("contractTempHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 6%" class="header <%=contractTempHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchContractTemp_form', null, null, 'contractId', '<%=contractTempHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">序号</a>
			</th>
			<th style="width: 6%" class="header <%=contractTempHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('searchContractTemp_form', null, null, 'monthly', '<%=contractTempHolder.getNextSortOrder("monthly")%>', 'tableWrapper');">月份</a>
			</th>
			<th style="width: 8%" class="header <%=contractTempHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchContractTemp_form', null, null, 'employeeId', '<%=contractTempHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">雇员ID</a>
			</th>
			<th style="width: 10%" class="header <%=contractTempHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchContractTemp_form', null, null, 'employeeNameZH', '<%=contractTempHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">雇员姓名（中文）</a>
			</th>
			<th style="width: 10%" class="header <%=contractTempHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchContractTemp_form', null, null, 'employeeNameEN', '<%=contractTempHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">雇员姓名（英文）</a>
			</th>
			<th style="width: 8%" class="header <%=contractTempHolder.getCurrentSortClass("employeeContractId")%>">
				<a onclick="submitForm('searchContractTemp_form', null, null, 'employeeContractId', '<%=contractTempHolder.getNextSortOrder("employeeContractId")%>', 'tableWrapper');">派送信息ID</a>
			</th>
			<th style="width: 8%" class="header <%=contractTempHolder.getCurrentSortClass("timesheetId")%>">
				<a onclick="submitForm('searchContractTemp_form', null, null, 'timesheetId', '<%=contractTempHolder.getNextSortOrder("timesheetId")%>', 'tableWrapper');">考勤表ID</a>
			</th>
			<th style="width: 8%" class="header <%=contractTempHolder.getCurrentSortClass("billAmountCompany")%>">
				<a onclick="submitForm('searchContractTemp_form', null, null, 'billAmountCompany', '<%=contractTempHolder.getNextSortOrder("billAmountCompany")%>', 'tableWrapper');">公司营收</a>
			</th>
			<th style="width: 8%" class="header <%=contractTempHolder.getCurrentSortClass("costAmountCompany")%>">
				<a onclick="submitForm('searchContractTemp_form', null, null, 'costAmountCompany', '<%=contractTempHolder.getNextSortOrder("costAmountCompany")%>', 'tableWrapper');">公司成本</a>
			</th>
			<th style="width: 8%" class="header <%=contractTempHolder.getCurrentSortClass("billAmountPersonal")%>">
				<a onclick="submitForm('searchContractTemp_form', null, null, 'billAmountPersonal', '<%=contractTempHolder.getNextSortOrder("billAmountPersonal")%>', 'tableWrapper');">个人收入</a>
			</th>
			<th style="width: 8%" class="header <%=contractTempHolder.getCurrentSortClass("costAmountPersonal")%>">
				<a onclick="submitForm('searchContractTemp_form', null, null, 'costAmountPersonal', '<%=contractTempHolder.getNextSortOrder("costAmountPersonal")%>', 'tableWrapper');">个人支出</a>
			</th>
			<th style="width: 14%" class="header-nosort">备注</th>
		</tr>
	</thead>
	<logic:notEqual name="contractTempHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="serviceContractTempVO" name="contractTempHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="serviceContractTempVO" property="contractId"/>" name="chkSelectRow[]" value="<bean:write name="serviceContractTempVO" property="encodedId"/>" />
					</td>
					<td class="left"><a onclick="link('settlementTempAction.do?proc=to_contractDetail&batchId=<bean:write name="batchTempForm" property="encodedId"/>&orderHeaderId=<bean:write name="serviceContractTempVO" property="encodedOrderHeaderId"/>&contractId=<bean:write name="serviceContractTempVO" property="encodedId"/>');"><bean:write name="serviceContractTempVO" property="contractId" /></a></td>
					<td class="left"><bean:write name="serviceContractTempVO" property="monthly"/></td>
					<td class="left"><a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="serviceContractTempVO" property="encodedEmployeeId"/>');"><bean:write name="serviceContractTempVO" property="employeeId" /></a></td>
					<td class="left"><bean:write name="serviceContractTempVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="serviceContractTempVO" property="employeeNameEN" /></td>
					<td class="left"><a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="serviceContractTempVO" property="encodedEmployeeContractId"/>');"><bean:write name="serviceContractTempVO" property="employeeContractId"/></a></td>
					<td class="left"><bean:write name="serviceContractTempVO" property="timesheetId"/></td>
					<td class="right"><bean:write name="serviceContractTempVO" property="billAmountCompany"/></td>
					<td class="right"><bean:write name="serviceContractTempVO" property="costAmountCompany"/></td>
					<td class="right"><bean:write name="serviceContractTempVO" property="billAmountPersonal"/></td>
					<td class="right"><bean:write name="serviceContractTempVO" property="costAmountPersonal"/></td>
					<td class="left">科目数量：<bean:write name="serviceContractTempVO" property="countItemId"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="contractTempHolder">
		<tfoot>
			<tr class="total">
				<td colspan="13" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="contractTempHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="contractTempHolder" property="indexStart" /> - <bean:write name="contractTempHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('searchContractTemp_form', null, '<bean:write name="contractTempHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchContractTemp_form', null, '<bean:write name="contractTempHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchContractTemp_form', null, '<bean:write name="contractTempHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('searchContractTemp_form', null, '<bean:write name="contractTempHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="contractTempHolder" property="realPage" />/<bean:write name="contractTempHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;跳转至：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="contractTempHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />页</label>&nbsp;
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
		submitForm('searchContractTemp_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>