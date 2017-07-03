<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder sbHeaderTempHolder = (PagedListHolder) request.getAttribute("sbHeaderTempHolder");
%>

<div id="messageWrapper">
	<logic:present name="MESSAGE">
		<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
			<bean:write name="MESSAGE" />
	  			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
		</div>
	</logic:present>
</div>
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 5%" class="header <%=sbHeaderTempHolder.getCurrentSortClass("a.headerId")%>">
				<a onclick="submitForm('searchSBHeaderTemp_form', null, null, 'a.headerId', '<%=sbHeaderTempHolder.getNextSortOrder("a.headerId")%>', 'tableWrapper');">序号ID</a>
			</th>
			<th style="width: 6%" class="header <%=sbHeaderTempHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('searchSBHeaderTemp_form', null, null, 'monthly', '<%=sbHeaderTempHolder.getNextSortOrder("monthly")%>', 'tableWrapper');">申报月份</a>
			</th>
			<th style="width: 7%" class="header <%=sbHeaderTempHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchSBHeaderTemp_form', null, null, 'contractId', '<%=sbHeaderTempHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">劳动合同ID</a>
			</th>
			<th style="width: 7%" class="header <%=sbHeaderTempHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchSBHeaderTemp_form', null, null, 'employeeId', '<%=sbHeaderTempHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">员工ID</a>
			</th>
			<th style="width: 15%" class="header <%=sbHeaderTempHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchSBHeaderTemp_form', null, null, 'employeeNameZH', '<%=sbHeaderTempHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">员工姓名（中）</a>
			</th>
			<th style="width: 14%" class="header <%=sbHeaderTempHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchSBHeaderTemp_form', null, null, 'employeeNameEN', '<%=sbHeaderTempHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">员工姓名（英）</a>
			</th>
			<th style="width: 12%" class="header <%=sbHeaderTempHolder.getCurrentSortClass("employStatus")%>">
				<a onclick="submitForm('searchSBHeaderTemp_form', null, null, 'employStatus', '<%=sbHeaderTempHolder.getNextSortOrder("employStatus")%>', 'tableWrapper');">雇佣状态</a>
			</th>
			<th style="width: 11%" class="header <%=sbHeaderTempHolder.getCurrentSortClass("amountCompany")%>">
				<a onclick="submitForm('searchSBHeaderTemp_form', null, null, 'amountCompany', '<%=sbHeaderTempHolder.getNextSortOrder("amountCompany")%>', 'tableWrapper');">社保公积金（公司）</a>
			</th>
			<th style="width: 11%" class="header <%=sbHeaderTempHolder.getCurrentSortClass("amountPersonal")%>">
				<a onclick="submitForm('searchSBHeaderTemp_form', null, null, 'amountPersonal', '<%=sbHeaderTempHolder.getNextSortOrder("amountPersonal")%>', 'tableWrapper');">社保公积金（个人）</a>
			</th>
			<th style="width: 7%" class="header-nosort">
				<span>备注</span>
			</th>
			<th style="width: 5%" class="header <%=sbHeaderTempHolder.getCurrentSortClass("tempStatus")%>">
				<a onclick="submitForm('listHeaderTemp_form', null, null, 'tempStatus', '<%=sbHeaderTempHolder.getNextSortOrder("tempStatus")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="sbHeaderTempHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="sbHeaderTempVO" name="sbHeaderTempHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="sbHeaderTempVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="sbHeaderTempVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="sbHeaderTempVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left"><a onclick="link('sbFeedbackDetailTempAction.do?proc=list_object&headerId=<bean:write name="sbHeaderTempVO" property="encodedId"/>');"><bean:write name="sbHeaderTempVO" property="headerId" /></a></td>
					<td class="left"><bean:write name="sbHeaderTempVO" property="monthly" /></td>
					<td class="left"><bean:write name="sbHeaderTempVO" property="contractId" /></td>
					<td class="left"><bean:write name="sbHeaderTempVO" property="employeeId" /></td>
					<td class="left"><bean:write name="sbHeaderTempVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="sbHeaderTempVO" property="employeeNameEN" /></td>
					<td class="left"><bean:write name="sbHeaderTempVO" property="decodeEmployStatus" /></td>
					<td class="right"><bean:write name="sbHeaderTempVO" property="decodeAmountCompany" /></td>
					<td class="right"><bean:write name="sbHeaderTempVO" property="decodeAmountPersonal" /></td>
					<td class="left">科目：<bean:write name="sbHeaderTempVO" property="countItemId"/></td>
					<td class="left"><bean:write name="sbHeaderTempVO" property="decodeTempStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="sbHeaderTempHolder">
		<tfoot>
			<tr class="total">
				<td colspan="17" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="sbHeaderTempHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="sbHeaderTempHolder" property="indexStart" /> - <bean:write name="sbHeaderTempHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchSBHeaderTemp_form', null, '<bean:write name="sbHeaderTempHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchSBHeaderTemp_form', null, '<bean:write name="sbHeaderTempHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchSBHeaderTemp_form', null, '<bean:write name="sbHeaderTempHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchSBHeaderTemp_form', null, '<bean:write name="sbHeaderTempHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="sbHeaderTempHolder" property="realPage" />/<bean:write name="sbHeaderTempHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
		// JS of the List
		kanList_init();
		kanCheckbox_init();
		messageWrapperFada();
	})(jQuery);	
</script>
