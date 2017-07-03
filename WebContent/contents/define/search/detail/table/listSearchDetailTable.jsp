<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%					
	final PagedListHolder searchDetailHolder = (PagedListHolder)request.getAttribute("searchDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 25%" class="header <%=searchDetailHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'nameZH', '<%=searchDetailHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="define" key="define.manager.detail.name.cn" /></a>
			</th>
			<th style="width: 25%" class="header <%=searchDetailHolder.getCurrentSortClass("columnId")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'columnId', '<%=searchDetailHolder.getNextSortOrder("columnId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.manager.detail.original.column.name" /></a>
			</th>			
			<th style="width: 10%" class="header <%=searchDetailHolder.getCurrentSortClass("columnIndex")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'columnIndex', '<%=searchDetailHolder.getNextSortOrder("columnIndex")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.column.index" /></a>
			</th>
			<th style="width: 10%" class="header <%=searchDetailHolder.getCurrentSortClass("fontSize")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'fontSize', '<%=searchDetailHolder.getNextSortOrder("fontSize")%>', 'tableWrapper');"><bean:message bundle="define" key="define.search.detail.font.size" /></a>
			</th>	
		 	<th style="width: 10%" class="header <%=searchDetailHolder.getCurrentSortClass("useThinking")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'useThinking', '<%=searchDetailHolder.getNextSortOrder("useThinking")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.use.thinking" /></a>
			</th>	
			<th style="width: 10%" class="header <%=searchDetailHolder.getCurrentSortClass("display")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'display', '<%=searchDetailHolder.getNextSortOrder("display")%>', 'tableWrapper');"><bean:message bundle="define" key="define.serach.detail.is.display" /></a>
			</th>							
			<th style="width: 10%" class="header <%=searchDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'status', '<%=searchDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>							
		</tr>
	</thead>							
	<logic:notEqual name="searchDetailHolder" property="holderSize" value="0">						
		<tbody>
			<logic:iterate id="searchDetailVO" name="searchDetailHolder" property="source" indexId="number">
				<input type="hidden" id="columnId" name="columnId" value="<bean:write name="searchDetailVO" property="columnId"/>" />
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="searchDetailVO" property="searchDetailId"/>" name="chkSelectRow[]" value="<bean:write name="searchDetailVO" property="searchDetailId"/>" />
						<input type="hidden" class="resultTable_columnIdAndDetailId" value="<bean:write name='searchDetailVO' property='columnId'/>" />
					</td>
					<td class="left">
						<a onclick="objectModify('<bean:write name="searchDetailVO" property="encodedId"/>', '<bean:write name="searchDetailVO" property="columnId"/>');"><bean:write name="searchDetailVO" property="nameZH"/></a>
					</td>
					<td class="left"><bean:write name="searchDetailVO" property="decodeColumn"/></td>
					<td class="left"><bean:write name="searchDetailVO" property="columnIndex"/></td>
					<td class="left"><bean:write name="searchDetailVO" property="fontSize"/></td>
					<td class="left"><bean:write name="searchDetailVO" property="decodeUseThinking"/></td>
					<td class="left"><bean:write name="searchDetailVO" property="decodeDisplay"/></td>
					<td class="left"><bean:write name="searchDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	
	<logic:present name="searchDetailHolder">
		<tfoot>
			<tr class="total">
	  			<td colspan="8" class="left"> 
	  				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="searchDetailHolder" property="holderSize" /></label>
	  				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="searchDetailHolder" property="indexStart" /> - <bean:write name="searchDetailHolder" property="indexEnd" /></label>
	  				<label>&nbsp;&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="searchDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="searchDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="searchDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="searchDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
	  				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="searchDetailHolder" property="realPage" />/<bean:write name="searchDetailHolder" property="pageCount" /></label>&nbsp;
	  			</td>					
	    	</tr>
		</tfoot>
	</logic:present>
</table>