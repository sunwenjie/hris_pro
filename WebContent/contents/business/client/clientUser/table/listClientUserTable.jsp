<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder clientUserHolder = (PagedListHolder) request.getAttribute("clientUserHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 16%" class="header <%=clientUserHolder.getCurrentSortClass("username")%>">
				<a onclick="submitForm('listuser_form', null, null, 'username', '<%=clientUserHolder.getNextSortOrder("username")%>', 'tableWrapper');"><bean:message key="user.username" /></a>
			</th>
			<th style="width: 16%" class="header <%=clientUserHolder.getCurrentSortClass("staffName")%>">
				<a onclick="submitForm('listuser_form', null, null, 'clientContactName', '<%=clientUserHolder.getNextSortOrder("clientContactName")%>', 'tableWrapper');">¿Í»§ÐÕÃû</a>
			</th>
			<th style="width: 28%" class="header <%=clientUserHolder.getCurrentSortClass("bindIP")%>">
				<a onclick="submitForm('listuser_form', null, null, 'bindIP', '<%=clientUserHolder.getNextSortOrder("bindIP")%>', 'tableWrapper');"><bean:message key="user.bindingIPaddress" /></a>
			</th>
			<th style="width: 15%" class="header <%=clientUserHolder.getCurrentSortClass("lastLoginIP")%>">
				<a onclick="submitForm('listuser_form', null, null, 'lastLoginIP', '<%=clientUserHolder.getNextSortOrder("lastLoginIP")%>', 'tableWrapper');"><bean:message key="user.lastloginIP" /></a>
			</th>
			<th style="width: 15%" class="header <%=clientUserHolder.getCurrentSortClass("lastLogin")%>">
				<a onclick="submitForm('listuser_form', null, null, 'lastLogin', '<%=clientUserHolder.getNextSortOrder("lastLogin")%>', 'tableWrapper');"><bean:message key="user.lastlogintime" /></a>
			</th>
			<th style="width: 10%" class="header <%=clientUserHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listuser_form', null, null, 'status', '<%=clientUserHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="clientUserHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="userContactVO" name="clientUserHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="userContactVO" property="username" value="Admin">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="userContactVO" property="clientUserId"/>" name="chkSelectRow[]" value="" disabled="disabled" />
						</logic:equal> 
						<logic:notEqual name="userContactVO" property="username" value="Admin">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="userContactVO" property="clientUserId"/>" name="chkSelectRow[]" value="<bean:write name="userContactVO" property="clientUserId"/>" />
						</logic:notEqual>
					</td>
					<td class="left"><a onclick="link('clientContactrAction.do?proc=to_objectModify&clientUserId=<bean:write name="userContactVO" property="encodedId"/>');"><bean:write name="userContactVO" property="username" /></a></td>
					<td class="left"><bean:write name="userContactVO" property="clientName" /></td>
					<td class="left"><bean:write name="userContactVO" property="bindIP" /></td>
					<td class="left"><bean:write name="userContactVO" property="lastLoginIP" /></td>
					<td class="left"><bean:write name="userContactVO" property="lastLogin" /></td>
					<td class="left"><bean:write name="userContactVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="clientUserHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="clientUserHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="clientUserHolder" property="indexStart" /> - <bean:write name="clientUserHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listuser_form', null, '<bean:write name="clientUserHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listuser_form', null, '<bean:write name="clientUserHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listuser_form', null, '<bean:write name="clientUserHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listuser_form', null, '<bean:write name="clientUserHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="clientUserHolder" property="realPage" />/<bean:write name="clientUserHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>