<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder managerDetailHolder = (PagedListHolder)request.getAttribute("managerDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 20%" class="header <%=managerDetailHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listManagerDetail_form', null, null, 'nameZH', '<%=managerDetailHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="define" key="define.manager.detail.name.cn" /></a>
			</th>
			<th style="width: 20%" class="header <%=managerDetailHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listManagerDetail_form', null, null, 'nameEN', '<%=managerDetailHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="define" key="define.manager.detail.name.en" /></a>
			</th>
			<th style="width: 20%" class="header <%=managerDetailHolder.getCurrentSortClass("columnId")%>">
				<a onclick="submitForm('listManagerDetail_form', null, null, 'columnId', '<%=managerDetailHolder.getNextSortOrder("columnId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.manager.detail.original.column.name" /></a>
			</th>
			<th style="width: 20%" class="header <%=managerDetailHolder.getCurrentSortClass("groupId")%>">
				<a onclick="submitForm('listManagerDetail_form', null, null, 'groupId', '<%=managerDetailHolder.getNextSortOrder("groupId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.group" /></a>
			</th>
			<th style="width: 10%" class="header <%=managerDetailHolder.getCurrentSortClass("columnIndex")%>">
				<a onclick="submitForm('listManagerDetail_form', null, null, 'columnIndex', '<%=managerDetailHolder.getNextSortOrder("columnIndex")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.column.index" /></a>
			</th>
			<th style="width: 10%" class="header <%=managerDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listManagerDetail_form', null, null, 'status', '<%=managerDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>							
		</tr>
	</thead>	
	<logic:notEqual name="managerDetailHolder" property="holderSize" value="0">						
		<tbody>
			<logic:iterate id="managerDetailVO" name="managerDetailHolder" property="source" indexId="number">
				<input type="hidden" id="columnId" name="columnId" value="<bean:write name="managerDetailVO" property="columnId"/>" />
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="managerDetailVO" property="managerDetailId"/>" name="chkSelectRow[]" value="<bean:write name="managerDetailVO" property="managerDetailId"/>" />
					</td>
					<td class="left">
						<a onclick="managerDetailModify('<bean:write name="managerDetailVO" property="encodedId"/>', '<bean:write name="managerDetailVO" property="columnId"/>');"><bean:write name="managerDetailVO" property="nameZH"/></a>
					</td>	
					<td class="left">
						<a onclick="managerDetailModify('<bean:write name="managerDetailVO" property="encodedId"/>', '<bean:write name="managerDetailVO" property="columnId"/>');"><bean:write name="managerDetailVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="managerDetailVO" property="decodeColumn"/></td>
					<td class="left"><bean:write name="managerDetailVO" property="decodeGroup"/></td>
					<td class="left"><bean:write name="managerDetailVO" property="columnIndex"/></td>
					<td class="left"><bean:write name="managerDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="managerDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="managerDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="managerDetailHolder" property="indexStart" /> - <bean:write name="managerDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listManagerDetail_form', null, '<bean:write name="managerDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listManagerDetail_form', null, '<bean:write name="managerDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listManagerDetail_form', null, '<bean:write name="managerDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listManagerDetail_form', null, '<bean:write name="managerDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="managerDetailHolder" property="realPage" />/<bean:write name="managerDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
		 	</tr>
		</tfoot>
	</logic:present>
</table>