<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder userHolder = (PagedListHolder) request.getAttribute("userHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 16%" class="header <%=userHolder.getCurrentSortClass("username")%>">
				<a onclick="submitForm('listuser_form', null, null, 'username', '<%=userHolder.getNextSortOrder("username")%>', 'tableWrapper');"><bean:message key="user.username" /></a>
			</th>
			<th style="width: 16%" class="header <%=userHolder.getCurrentSortClass("staffName")%>">
				<a onclick="submitForm('listuser_form', null, null, 'staffName', '<%=userHolder.getNextSortOrder("staffName")%>', 'tableWrapper');"><bean:message key="user.empname" /></a>
			</th>
			<th style="width: 10%" class="header <%=userHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listuser_form', null, null, 'status', '<%=userHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			<th style="width: 28%" class="header <%=userHolder.getCurrentSortClass("bindIP")%>">
				<a onclick="submitForm('listuser_form', null, null, 'bindIP', '<%=userHolder.getNextSortOrder("bindIP")%>', 'tableWrapper');"><bean:message key="user.bindingIPaddress" /></a>
			</th>
			<th style="width: 15%" class="header <%=userHolder.getCurrentSortClass("lastLoginIP")%>">
				<a onclick="submitForm('listuser_form', null, null, 'lastLoginIP', '<%=userHolder.getNextSortOrder("lastLoginIP")%>', 'tableWrapper');"><bean:message key="user.lastloginIP" /></a>
			</th>
			<th style="width: 15%" class="header <%=userHolder.getCurrentSortClass("lastLogin")%>">
				<a onclick="submitForm('listuser_form', null, null, 'lastLogin', '<%=userHolder.getNextSortOrder("lastLogin")%>', 'tableWrapper');"><bean:message key="user.lastlogintime" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="userHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="userVO" name="userHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><a onclick="link('userAction.do?proc=to_objectModify&userId=<bean:write name="userVO" property="encodedId"/>');"><bean:write name="userVO" property="username" /></a></td>
					<td class="left"><a onclick="link('staffAction.do?proc=to_objectModify&id=<bean:write name="userVO" property="encodedStaffId"/>');"><bean:write name="userVO" property="staffName" /></a></td>
					<td class="left"><bean:write name="userVO" property="decodeStatus" /></td>
					<td class="left"><bean:write name="userVO" property="bindIP" /></td>
					<td class="left"><bean:write name="userVO" property="lastLoginIP" /></td>
					<td class="left"><bean:write name="userVO" property="decodeLastLogin" /></td>
					
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="userHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="userHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="userHolder" property="indexStart" /> - <bean:write name="userHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listuser_form', null, '<bean:write name="userHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listuser_form', null, '<bean:write name="userHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listuser_form', null, '<bean:write name="userHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listuser_form', null, '<bean:write name="userHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="userHolder" property="realPage" />/<bean:write name="userHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>