<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder searchHeaderHolder = (PagedListHolder) request.getAttribute("searchHeaderHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 35%" class="header <%=searchHeaderHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listSearchHeader_form', null, null, 'nameZH', '<%=searchHeaderHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="define" key="define.search.header.name.cn" /></a>
			</th>
			<th style="width: 35%" class="header <%=searchHeaderHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listSearchHeader_form', null, null, 'nameEN', '<%=searchHeaderHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="define" key="define.search.header.name.en" /></a>
			</th>	
			<th style="width: 20%" class="header <%=searchHeaderHolder.getCurrentSortClass("tableId")%>">
				<a onclick="submitForm('listSearchHeader_form', null, null, 'tableId', '<%=searchHeaderHolder.getNextSortOrder("tableId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.table" /></a>
			</th>												
			<th style="width: 10%" class="header <%=searchHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listSearchHeader_form', null, null, 'status', '<%=searchHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="searchHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="searchHeaderVO" name="searchHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="searchHeaderVO" property="extended" value="2">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="searchHeaderVO" property="searchHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="searchHeaderVO" property="searchHeaderId"/>" />
						</logic:equal>
					</td>
					<td class="left"><a  onclick="link('searchDetailAction.do?proc=list_object&id=<bean:write name="searchHeaderVO" property="encodedId"/>');"><bean:write name="searchHeaderVO" property="nameZH" /></a></td>
					<td class="left"><a  onclick="link('searchDetailAction.do?proc=list_object&id=<bean:write name="searchHeaderVO" property="encodedId"/>');"><bean:write name="searchHeaderVO" property="nameEN" /></a></td>
					<td class="left"><bean:write name="searchHeaderVO" property="decodeTable" /></td>
					<td class="left"><bean:write name="searchHeaderVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="searchHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="5" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="searchHeaderHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="searchHeaderHolder" property="indexStart" /> - <bean:write name="searchHeaderHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listSearchHeader_form', null, '<bean:write name="searchHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listSearchHeader_form', null, '<bean:write name="searchHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listSearchHeader_form', null, '<bean:write name="searchHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listSearchHeader_form', null, '<bean:write name="searchHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="searchHeaderHolder" property="realPage" />/<bean:write name="searchHeaderHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>