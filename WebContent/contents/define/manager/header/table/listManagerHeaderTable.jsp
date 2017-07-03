<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder managerHeaderHolder = (PagedListHolder) request.getAttribute("managerHeaderHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=managerHeaderHolder.getCurrentSortClass("managerHeaderId")%>">
				<a onclick="submitForm('listManagerHeader_form', null, null, 'managerHeaderId', '<%=managerHeaderHolder.getNextSortOrder("managerHeaderId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.manager.header.id" /></a>
			</th>	
			<th style="width: 20%" class="header <%=managerHeaderHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listManagerHeader_form', null, null, 'nameZH', '<%=managerHeaderHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="define" key="define.manager.header.name.cn" /></a>
			</th>					
			<th style="width: 20%" class="header <%=managerHeaderHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listManagerHeader_form', null, null, 'nameEN', '<%=managerHeaderHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="define" key="define.manager.header.name.en" /></a>
			</th>		
			<th style="width: 20%" class="header <%=managerHeaderHolder.getCurrentSortClass("tableId")%>">
				<a onclick="submitForm('listManagerHeader_form', null, null, 'tableId', '<%=managerHeaderHolder.getNextSortOrder("tableId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.table" /></a>
			</th>
			<th style="width: 20%" class="header <%=managerHeaderHolder.getCurrentSortClass("comments")%>">
				<a onclick="submitForm('listManagerHeader_form', null, null, 'comments', '<%=managerHeaderHolder.getNextSortOrder("comments")%>', 'tableWrapper');"><bean:message bundle="define" key="define.manager.header.note" /></a>
			</th>
			<th style="width: 10%" class="header <%=managerHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listManagerHeader_form', null, null, 'status', '<%=managerHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="managerHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="managerHeaderVO" name="managerHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="managerHeaderVO" property="managerHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="managerHeaderVO" property="managerHeaderId"/>" />
					</td>
					<td class="left"><a onclick="link('managerDetailAction.do?proc=list_object&id=<bean:write name="managerHeaderVO" property="encodedId"/>');"><bean:write name="managerHeaderVO" property="managerHeaderId" /></a></td>
					<td class="left"><a onclick="link('managerDetailAction.do?proc=list_object&id=<bean:write name="managerHeaderVO" property="encodedId"/>');"><bean:write name="managerHeaderVO" property="nameZH" /></a></td>
					<td class="left"><a onclick="link('managerDetailAction.do?proc=list_object&id=<bean:write name="managerHeaderVO" property="encodedId"/>');"><bean:write name="managerHeaderVO" property="nameEN" /></a></td>
					<td class="left"><bean:write name="managerHeaderVO" property="decodeTable" /></td>
					<td class="left"><bean:write name="managerHeaderVO" property="comments" /></td>
					<td class="left"><bean:write name="managerHeaderVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="managerHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="managerHeaderHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="managerHeaderHolder" property="indexStart" /> - <bean:write name="managerHeaderHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listManagerHeader_form', null, '<bean:write name="managerHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listManagerHeader_form', null, '<bean:write name="managerHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listManagerHeader_form', null, '<bean:write name="managerHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listManagerHeader_form', null, '<bean:write name="managerHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="managerHeaderHolder" property="realPage" />/<bean:write name="managerHeaderHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>