<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder listDetailHolder = (PagedListHolder)request.getAttribute("listDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=listDetailHolder.getCurrentSortClass("listDetailId")%>">
				<a onclick="submitForm('listListDetail_form', null, null, 'listDetailId', '<%=listDetailHolder.getNextSortOrder("listDetailId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.list.detail.search.title" />ID</a>
			</th>
			<th style="width: 15%" class="header <%=listDetailHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listListDetail_form', null, null, 'nameZH', '<%=listDetailHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="define" key="define.manager.detail.name.cn" /></a>
			</th>
			<th style="width: 15%" class="header <%=listDetailHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listListDetail_form', null, null, 'nameEN', '<%=listDetailHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="define" key="define.manager.detail.name.en" /></a>
			</th>
			
			<logic:equal name="listHeaderForm" property="tableId" value="0">
				<th style="width: 18%" class="header <%=listDetailHolder.getCurrentSortClass("propertyName")%>">
					<a onclick="submitForm('listListDetail_form', null, null, 'propertyName', '<%=listDetailHolder.getNextSortOrder("propertyName")%>', 'tableWrapper');"><bean:message bundle="define" key="define.search.detail.parameter.name" /></a>
				</th>
			</logic:equal>
			<logic:notEqual name="listHeaderForm" property="tableId" value="0">
				<th style="width: 18%" class="header <%=listDetailHolder.getCurrentSortClass("columnId")%>">
					<a onclick="submitForm('listListDetail_form', null, null, 'columnId', '<%=listDetailHolder.getNextSortOrder("columnId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.manager.detail.original.column.name" /></a>
				</th>
			</logic:notEqual>
			
			<th style="width: 7%" class="header <%=listDetailHolder.getCurrentSortClass("columnWidth")%>">
				<a onclick="submitForm('listListDetail_form', null, null, 'columnWidth', '<%=listDetailHolder.getNextSortOrder("columnWidth")%>', 'tableWrapper');"><bean:message bundle="define" key="define.list.detail.column.width" /></a>
			</th>
			<th style="width: 7%" class="header <%=listDetailHolder.getCurrentSortClass("columnIndex")%>">
				<a onclick="submitForm('listListDetail_form', null, null, 'columnIndex', '<%=listDetailHolder.getNextSortOrder("columnIndex")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.column.index" /></a>
			</th>
			<th style="width: 7%" class="header <%=listDetailHolder.getCurrentSortClass("fontSize")%>">
				<a onclick="submitForm('listListDetail_form', null, null, 'fontSize', '<%=listDetailHolder.getNextSortOrder("fontSize")%>', 'tableWrapper');"><bean:message bundle="define" key="define.list.detail.font.size" /></a>
			</th>
			<th style="width: 7%" class="header <%=listDetailHolder.getCurrentSortClass("isDecoded")%>">
				<a onclick="submitForm('listListDetail_form', null, null, 'isDecoded', '<%=listDetailHolder.getNextSortOrder("isDecoded")%>', 'tableWrapper');"><bean:message bundle="define" key="define.list.detail.is.decode" /></a>
			</th>
			<th style="width: 7%" class="header <%=listDetailHolder.getCurrentSortClass("isLinked")%>">
				<a onclick="submitForm('listListDetail_form', null, null, 'isLinked', '<%=listDetailHolder.getNextSortOrder("isLinked")%>', 'tableWrapper');"><bean:message bundle="define" key="define.list.detail.is.linked" /></a>
			</th>								
			<th style="width: 7%" class="header <%=listDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listListDetail_form', null, null, 'status', '<%=listDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>							
		</tr>
	</thead>							
	<logic:notEqual name="listDetailHolder" property="holderSize" value="0">						
		<tbody>
			<logic:iterate id="listDetailVO" name="listDetailHolder" property="source" indexId="number">
				<input type="hidden" id="columnId" name="columnId" value="<bean:write name="listDetailVO" property="columnId"/>" />
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="listDetailVO" property="listDetailId"/>" name="chkSelectRow[]" value="<bean:write name="listDetailVO" property="listDetailId"/>" />
					</td>
					<td class="left">
						<bean:write name="listDetailVO" property="listDetailId"/>
					</td>	
					<td class="left">
						<a onclick="objectModify('<bean:write name="listDetailVO" property="encodedId"/>', '<bean:write name="listDetailVO" property="columnId"/>');"><bean:write name="listDetailVO" property="nameZH"/></a>
					</td>	
					<td class="left">
						<a onclick="objectModify('<bean:write name="listDetailVO" property="encodedId"/>', '<bean:write name="listDetailVO" property="columnId"/>');"><bean:write name="listDetailVO" property="nameEN"/></a>
					</td>
					<td class="left">
						<logic:notEqual name="listHeaderForm" property="tableId" value="0">
							<bean:write name="listDetailVO" property="decodeColumn" />
						</logic:notEqual>
						<logic:equal name="listHeaderForm" property="tableId" value="0">
							<bean:write name="listDetailVO" property="propertyName" />
						</logic:equal>
					</td>	
					<td class="left"><bean:write name="listDetailVO" property="columnWidth"/></td>
					<td class="left"><bean:write name="listDetailVO" property="columnIndex"/></td>
					<td class="left"><bean:write name="listDetailVO" property="fontSize"/></td>
					<td class="left"><bean:write name="listDetailVO" property="decodeIsDecoded"/></td>
					<td class="left"><bean:write name="listDetailVO" property="decodeIsLinked"/></td>
					<td class="left"><bean:write name="listDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="listDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="11" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="listDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="listDetailHolder" property="indexStart" /> - <bean:write name="listDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listListDetail_form', null, '<bean:write name="listDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listListDetail_form', null, '<bean:write name="listDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listListDetail_form', null, '<bean:write name="listDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listListDetail_form', null, '<bean:write name="listDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="listDetailHolder" property="realPage" />/<bean:write name="listDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
		 	</tr>
		</tfoot>
	</logic:present>
</table>