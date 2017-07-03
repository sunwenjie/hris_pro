<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder employeeContractResignHolder = (PagedListHolder) request.getAttribute("employeeContractResignHolder");
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
			<th style="width: 2%" class="header <%=employeeContractResignHolder.getCurrentSortClass("a.employeeContractResignId")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'a.employeeContractResignId', '<%=employeeContractResignHolder.getNextSortOrder("a.employeeContractResignId")%>', 'tableWrapper');">序号ID</a>
			</th>
			<th style="width: 6%" class="header <%=employeeContractResignHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'contractId', '<%=employeeContractResignHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1">派送协议ID</logic:equal><logic:equal name="role" value="2">劳动合同ID</logic:equal>
				</a>
			</th>
			<th style="width: 4%" class="header <%=employeeContractResignHolder.getCurrentSortClass("resignDate")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'resignDate', '<%=employeeContractResignHolder.getNextSortOrder("resignDate")%>', 'tableWrapper');">离职日期</a>
			</th>
			<th style="width: 4%" class="header <%=employeeContractResignHolder.getCurrentSortClass("leaveReasons")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'leaveReasons', '<%=employeeContractResignHolder.getNextSortOrder("leaveReasons")%>', 'tableWrapper');">离职原因</a>
			</th>
			<th style="width: 4%" class="header <%=employeeContractResignHolder.getCurrentSortClass("lastWorkDate")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'lastWorkDate', '<%=employeeContractResignHolder.getNextSortOrder("lastWorkDate")%>', 'tableWrapper');">最后工作日期</a>
			</th>
			<th style="width: 6%" class="header <%=employeeContractResignHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'c.employeeId', '<%=employeeContractResignHolder.getNextSortOrder("c.employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>ID
				</a>
			</th>
			<th style="width: 6%" class="header-nosort">
				<logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名
			</th>
			<th style="width: 6%" class="header <%=employeeContractResignHolder.getCurrentSortClass("employStatus")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'employStatus', '<%=employeeContractResignHolder.getNextSortOrder("employStatus")%>', 'tableWrapper');">雇佣状态</a>
			</th>
			<th style="width: 8%" class="header <%=employeeContractResignHolder.getCurrentSortClass("sb1Id")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'sb1Id', '<%=employeeContractResignHolder.getNextSortOrder("sb1Id")%>', 'tableWrapper');">社保方案1</a>
			</th>
			<th style="width: 6%" class="header <%=employeeContractResignHolder.getCurrentSortClass("sb1EndDate")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'sb1EndDate', '<%=employeeContractResignHolder.getNextSortOrder("sb1EndDate")%>', 'tableWrapper');">结束时间</a>
			</th>
			<th style="width: 8%" class="header <%=employeeContractResignHolder.getCurrentSortClass("sb2Id")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'sb2Id', '<%=employeeContractResignHolder.getNextSortOrder("sb2Id")%>', 'tableWrapper');">社保方案2</a>
			</th>
			<th style="width: 6%" class="header <%=employeeContractResignHolder.getCurrentSortClass("sb2EndDate")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'sb2EndDate', '<%=employeeContractResignHolder.getNextSortOrder("sb2EndDate")%>', 'tableWrapper');">结束时间</a>
			</th>
			<th style="width: 8%" class="header <%=employeeContractResignHolder.getCurrentSortClass("sb3Id")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'sb3Id', '<%=employeeContractResignHolder.getNextSortOrder("sb3Id")%>', 'tableWrapper');">社保方案3</a>
			</th>
			<th style="width: 6%" class="header <%=employeeContractResignHolder.getCurrentSortClass("sb3EndDate")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'sb3EndDate', '<%=employeeContractResignHolder.getNextSortOrder("sb3EndDate")%>', 'tableWrapper');">结束时间</a>
			</th>
			<th style="width: 8%" class="header <%=employeeContractResignHolder.getCurrentSortClass("cb1Id")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'cb1Id', '<%=employeeContractResignHolder.getNextSortOrder("cb1Id")%>', 'tableWrapper');">商保方案1</a>
			</th>
			<th style="width: 6%" class="header <%=employeeContractResignHolder.getCurrentSortClass("cb1EndDate")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'cb1EndDate', '<%=employeeContractResignHolder.getNextSortOrder("cb1EndDate")%>', 'tableWrapper');">结束时间</a>
			</th>
			<th style="width: 8%" class="header <%=employeeContractResignHolder.getCurrentSortClass("cb2Id")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'cb2Id', '<%=employeeContractResignHolder.getNextSortOrder("cb2Id")%>', 'tableWrapper');">商保方案2</a>
			</th>
			<th style="width: 6%" class="header <%=employeeContractResignHolder.getCurrentSortClass("cb2EndDate")%>">
				<a onclick="submitForm('searchEmployeeContractResign_form', null, null, 'cb2EndDate', '<%=employeeContractResignHolder.getNextSortOrder("cb2EndDate")%>', 'tableWrapper');">结束时间</a>
			</th>
			<th style="width: 6%" class="header <%=employeeContractResignHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listHeaderTemp_form', null, null, 'status', '<%=employeeContractResignHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="employeeContractResignHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="employeeContractResignVO" name="employeeContractResignHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="employeeContractResignVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="employeeContractResignVO" property="employeeContractResignId"/>" name="chkSelectRow[]" value="<bean:write name="employeeContractResignVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left"><bean:write name="employeeContractResignVO" property="employeeContractResignId" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="contractId" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="resignDate" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="leaveReasons" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="lastWorkDate" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="employeeId" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="employeeName" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="decodeEmployStatus" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="decodeSb1Id" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="sb1EndDate" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="decodeSb2Id" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="sb2EndDate" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="decodeSb3Id" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="sb3EndDate"/></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="decodeCb1Id" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="cb1EndDate" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="decodeCb2Id" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="cb2EndDate" /></td>
					<td class="left"><bean:write name="employeeContractResignVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="employeeContractResignHolder">
		<tfoot>
			<tr class="total">
				<td colspan="20" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="employeeContractResignHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="employeeContractResignHolder" property="indexStart" /> - <bean:write name="employeeContractResignHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchEmployeeContractResign_form', null, '<bean:write name="employeeContractResignHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeContractResign_form', null, '<bean:write name="employeeContractResignHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeContractResign_form', null, '<bean:write name="employeeContractResignHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchEmployeeContractResign_form', null, '<bean:write name="employeeContractResignHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="employeeContractResignHolder" property="realPage" />/<bean:write name="employeeContractResignHolder" property="pageCount" /> </label>&nbsp;</td>
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
