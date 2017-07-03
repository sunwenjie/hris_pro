<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder importHeaderPagedListHolder = (PagedListHolder) request.getAttribute("importHeaderPagedListHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 30%" class="header <%=importHeaderPagedListHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listImportHeader_form', null, null, 'nameEN', '<%=importHeaderPagedListHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="define" key="define.import.header.name.cn" /></a>
			</th>					
			<th style="width: 30%" class="header <%=importHeaderPagedListHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listImportHeader_form', null, null, 'nameEN', '<%=importHeaderPagedListHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="define" key="define.import.header.name.en" /></a>
			</th>		
			<th style="width: 30%" class="header <%=importHeaderPagedListHolder.getCurrentSortClass("tableId")%>">
				<a onclick="submitForm('listImportHeader_form', null, null, 'tableId', '<%=importHeaderPagedListHolder.getNextSortOrder("tableId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.table" /></a>
			</th>
			<th style="width: 10%" class="header <%=importHeaderPagedListHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listImportHeader_form', null, null, 'status', '<%=importHeaderPagedListHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="importHeaderPagedListHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="importHeaderVO" name="importHeaderPagedListHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="importHeaderVO" property="importHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="importHeaderVO" property="importHeaderId"/>" />
					</td>
					<td class="left"><a onclick="link('importHeaderAction.do?proc=to_objectModify&importHeaderId=<bean:write name="importHeaderVO" property="encodedId"/>');"><bean:write name="importHeaderVO" property="nameZH" /></a></td>
					<td class="left"><a onclick="link('importHeaderAction.do?proc=to_objectModify&importHeaderId=<bean:write name="importHeaderVO" property="encodedId"/>');"><bean:write name="importHeaderVO" property="nameEN" /></a></td>
					<td class="left"><bean:write name="importHeaderVO" property="decodeTable" /></td>
					<td class="left"><bean:write name="importHeaderVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="importHeaderPagedListHolder">
		<tfoot>
			<tr class="total">
				<td colspan="11" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="importHeaderPagedListHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="importHeaderPagedListHolder" property="indexStart" /> - <bean:write name="importHeaderPagedListHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listImportHeader_form', null, '<bean:write name="importHeaderPagedListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listImportHeader_form', null, '<bean:write name="importHeaderPagedListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listImportHeader_form', null, '<bean:write name="importHeaderPagedListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listImportHeader_form', null, '<bean:write name="importHeaderPagedListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="importHeaderPagedListHolder" property="realPage" />/<bean:write name="importHeaderPagedListHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>