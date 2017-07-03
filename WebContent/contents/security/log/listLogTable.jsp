<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder logHolder = (PagedListHolder) request.getAttribute("logHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 14%" class="header <%=logHolder.getCurrentSortClass("operateTime")%>">
				<a onclick="submitForm('log_form', null, null, 'operateTime', '<%=logHolder.getNextSortOrder("operateTime")%>', 'tableWrapper');">Operate Time</a>
			</th>
			<th style="width: 14%" class="header <%=logHolder.getCurrentSortClass("ip")%>">
				<a onclick="submitForm('log_form', null, null, 'ip', '<%=logHolder.getNextSortOrder("ip")%>', 'tableWrapper');">IP</a>
			</th>
			<th style="width: 14%" class="header <%=logHolder.getCurrentSortClass("type")%>">
				<a onclick="submitForm('log_form', null, null, 'type', '<%=logHolder.getNextSortOrder("type")%>', 'tableWrapper');">Operate Type</a>
			</th>
			<th style="width: 14%" class="header <%=logHolder.getCurrentSortClass("module")%>">
				<a onclick="submitForm('log_form', null, null, 'module', '<%=logHolder.getNextSortOrder("module")%>', 'tableWrapper');">Effective Module</a>
			</th>
			<th style="width: 14%" class="header-nosort">
				Effective Content
			</th>
			<th style="width: 14%" class="header <%=logHolder.getCurrentSortClass("pKey")%>">
				<a onclick="submitForm('log_form', null, null, 'pKey', '<%=logHolder.getNextSortOrder("pKey")%>', 'tableWrapper');">Effective Primary Key</a>
			</th>
			<th style="width: 16%" class="header <%=logHolder.getCurrentSortClass("operateBy")%>">
				<a onclick="submitForm('log_form', null, null, 'operateBy', '<%=logHolder.getNextSortOrder("operateBy")%>', 'tableWrapper');">Operate By</a>
			</th>
			
		</tr>
	</thead>
	<logic:notEqual name="logHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="logVO" name="logHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td class="left"><bean:write name="logVO" property="operateTime"/></td>
					<td class="left"><bean:write name="logVO" property="ip"/></td>
					<td class="left"><bean:write name="logVO" property="decodeOperateType"/></td>
					<td class="left"><bean:write name="logVO" property="module"/></td>
					<td class="left">
						<a  href="logAction.do?proc=formatJson&id=<bean:write name="logVO" property="id"/>" target="_blank">Get Content Json</a>
					</td>
					<td class="left"><bean:write name="logVO" property="pKey"/></td>
					<td class="left"><bean:write name="logVO" property="operateBy"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="logHolder">
		<tfoot>
			<tr class="total">
				<td colspan="7" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="logHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="logHolder" property="indexStart" /> - <bean:write name="logHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('log_form', null, '<bean:write name="logHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('log_form', null, '<bean:write name="logHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('log_form', null, '<bean:write name="logHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('log_form', null, '<bean:write name="logHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="logHolder" property="realPage" />/<bean:write name="logHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>