<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder accountHolder = (PagedListHolder) request.getAttribute("accountHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 14%" class="header <%=accountHolder.getCurrentSortClass("nameCN")%>">
				<a onclick="submitForm('listaccount_form', null, null, 'nameCN', '<%=accountHolder.getNextSortOrder("nameCN")%>', 'tableWrapper');">账户名 （中文）</a>
			</th>
			<th style="width: 14%" class="header <%=accountHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listaccount_form', null, null, 'nameEN', '<%=accountHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">账户名 （英文）</a>
			</th>
			<th style="width: 24%" class="header <%=accountHolder.getCurrentSortClass("entityName")%>">
				<a onclick="submitForm('listaccount_form', null, null, 'entityName', '<%=accountHolder.getNextSortOrder("entityName")%>', 'tableWrapper');">公司名称</a>
			</th>
			<th style="width: 9%" class="header <%=accountHolder.getCurrentSortClass("linkman")%>">
				<a onclick="submitForm('listaccount_form', null, null, 'linkman', '<%=accountHolder.getNextSortOrder("linkman")%>', 'tableWrapper');">联系人</a>
			</th>
			<th style="width: 12%" class="header <%=accountHolder.getCurrentSortClass("bizPhone")%>">
				<a onclick="submitForm('listaccount_form', null, null, 'bizPhone', '<%=accountHolder.getNextSortOrder("bizPhone")%>', 'tableWrapper');">工作电话</a>
			</th>
			<th style="width: 17%" class="header <%=accountHolder.getCurrentSortClass("bizEmail")%>">
				<a onclick="submitForm('listaccount_form', null, null, 'bizEmail', '<%=accountHolder.getNextSortOrder("bizEmail")%>', 'tableWrapper');">工作邮件</a>
			</th>
			<th style="width: 10%" class="header <%=accountHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listaccount_form', null, null, 'status', '<%=accountHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="accountHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="accountVO" name="accountHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="accountVO" property="accountId" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="accountVO" property="accountId"/>" name="chkSelectRow[]" value="" disabled="disabled" />
						</logic:equal> 
						<logic:notEqual name="accountVO" property="accountId" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="accountVO" property="accountId"/>" name="chkSelectRow[]" value="<bean:write name="accountVO" property="accountId"/>" />
						</logic:notEqual>
					</td>
					<td><a onclick="link('accountAction.do?proc=to_objectModify&accountId=<bean:write name="accountVO" property="encodedId"/>');"><bean:write name="accountVO" property="nameCN" /></a></td>
					<td><a onclick="link('accountAction.do?proc=to_objectModify&accountId=<bean:write name="accountVO" property="encodedId"/>');"><bean:write name="accountVO" property="nameEN" /></a></td>
					<td class="left"><bean:write name="accountVO" property="entityName" /></td>
					<td class="left"><bean:write name="accountVO" property="linkman" /></td>
					<td class="left"><bean:write name="accountVO" property="bizPhone" /></td>
					<td class="left"><bean:write name="accountVO" property="bizEmail" /></td>
					<td class="left"><bean:write name="accountVO" property="decodeStatus" /> (<bean:write name="accountVO" property="decodeInitialized" />)</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="accountHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="accountHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="accountHolder" property="indexStart" /> - <bean:write name="accountHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listaccount_form', null, '<bean:write name="accountHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listaccount_form', null, '<bean:write name="accountHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listaccount_form', null, '<bean:write name="accountHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listaccount_form', null, '<bean:write name="accountHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="accountHolder" property="realPage" />/<bean:write name="accountHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>