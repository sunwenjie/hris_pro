<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder tablePagedListHolder = (PagedListHolder) request.getAttribute("tablePagedListHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<logic:present name="accountId">
				<logic:equal name="accountId" value="1">
					<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
				</logic:equal>
			</logic:present>
			<th style="width: 7%" class="header <%=tablePagedListHolder.getCurrentSortClass("tableId")%>">
				<a onclick="submitForm('listtable_form', null, null, 'tableId', '<%=tablePagedListHolder.getNextSortOrder("tableId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.table.id" /></a>
			</th>
			<th style="width: 15%" class="header <%=tablePagedListHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listtable_form', null, null, 'nameZH', '<%=tablePagedListHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="define" key="define.table.name.cn" /></a>
			</th>
			<th style="width: 15%" class="header <%=tablePagedListHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listtable_form', null, null, 'nameEN', '<%=tablePagedListHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="define" key="define.table.name.en" /></a>
			</th>
			<th style="width: 7%" class="header <%=tablePagedListHolder.getCurrentSortClass("tableType")%>">
				<a onclick="submitForm('listtable_form', null, null, 'tableType', '<%=tablePagedListHolder.getNextSortOrder("tableType")%>', 'tableWrapper');"><bean:message bundle="public" key="public.type" /></a>
			</th>
			<th style="width: 15%" class="header <%=tablePagedListHolder.getCurrentSortClass("accessAction")%>">
				<a onclick="submitForm('listtable_form', null, null, 'accessAction', '<%=tablePagedListHolder.getNextSortOrder("accessAction")%>', 'tableWrapper');"><bean:message bundle="public" key="public.access.action" /></a>
			</th>
			<th style="width: 15%" class="header <%=tablePagedListHolder.getCurrentSortClass("accessName")%>">
				<a onclick="submitForm('listtable_form', null, null, 'accessName', '<%=tablePagedListHolder.getNextSortOrder("accessName")%>', 'tableWrapper');"><bean:message bundle="define" key="define.table.name.db" /></a>
			</th>
			<th style="width: 10%" class="header <%=tablePagedListHolder.getCurrentSortClass("tableIndex")%>">
				<a onclick="submitForm('listtable_form', null, null, 'tableIndex', '<%=tablePagedListHolder.getNextSortOrder("tableIndex")%>', 'tableWrapper');"><bean:message bundle="public" key="public.show.index" /></a>
			</th>
			<th style="width: 11%" class="header <%=tablePagedListHolder.getCurrentSortClass("role")%>">
				<a onclick="submitForm('listtable_form', null, null, 'role', '<%=tablePagedListHolder.getNextSortOrder("role")%>', 'tableWrapper');"><bean:message bundle="define" key="define.table.role" /></a>
			</th>
			<th style="width: 5%" class="header <%=tablePagedListHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listtable_form', null, null, 'status', '<%=tablePagedListHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="tablePagedListHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="tableVO" name="tablePagedListHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<logic:present name="accountId">
						<logic:equal name="accountId" value="1">
							<td>
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="tableVO" property="tableId"/>" name="chkSelectRow[]" value="<bean:write name="tableVO" property="tableId"/>" />
							</td>
						</logic:equal>
					</logic:present>
					<td class="left"><a onclick="link('tableAction.do?proc=to_objectModify&tableId=<bean:write name="tableVO" property="encodedId"/>');"><bean:write name="tableVO" property="tableId" /></a></td>
					<td class="left"><bean:write name="tableVO" property="nameZH" /></td>
					<td class="left"><bean:write name="tableVO" property="nameEN" /></td>
					<td class="left"><bean:write name="tableVO" property="decodeTableType" /></td>
					<td class="left"><bean:write name="tableVO" property="accessAction" /></td>
					<td class="left"><bean:write name="tableVO" property="accessName" /></td>
					<td class="left"><bean:write name="tableVO" property="tableIndex" /></td>
					<td class="left"><bean:write name="tableVO" property="decodeRole" /></td>
					<td class="left"><bean:write name="tableVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="tablePagedListHolder">
		<tfoot>
			<tr class="total">
				<td colspan="10" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="tablePagedListHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="tablePagedListHolder" property="indexStart" /> - <bean:write name="tablePagedListHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listtable_form', null, '<bean:write name="tablePagedListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listtable_form', null, '<bean:write name="tablePagedListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listtable_form', null, '<bean:write name="tablePagedListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listtable_form', null, '<bean:write name="tablePagedListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="tablePagedListHolder" property="realPage" />/<bean:write name="tablePagedListHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>