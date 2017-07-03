<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder listHeaderHolder = (PagedListHolder) request.getAttribute("listHeaderHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 16%" class="header <%=listHeaderHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listListHeader_form', null, null, 'nameZH', '<%=listHeaderHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="define" key="define.list.header.name.cn" /></a>
			</th>					
			<th style="width: 16%" class="header <%=listHeaderHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listListHeader_form', null, null, 'nameEN', '<%=listHeaderHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="define" key="define.list.header.name.en" /></a>
			</th>		
			<th style="width: 16%" class="header <%=listHeaderHolder.getCurrentSortClass("tableId")%>">
				<a onclick="submitForm('listListHeader_form', null, null, 'tableId', '<%=listHeaderHolder.getNextSortOrder("tableId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.table" /></a>
			</th>
			<th style="width: 16%" class="header <%=listHeaderHolder.getCurrentSortClass("searchId")%>">
				<a onclick="submitForm('listListHeader_form', null, null, 'searchId', '<%=listHeaderHolder.getNextSortOrder("searchId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.search" /></a>
			</th>	
			<th style="width: 10%" class="header <%=listHeaderHolder.getCurrentSortClass("isSearchFirst")%>">
				<a onclick="submitForm('listListHeader_form', null, null, 'isSearchFirst', '<%=listHeaderHolder.getNextSortOrder("isSearchFirst")%>', 'tableWrapper');"><bean:message bundle="define" key="define.list.header.search.first" /></a>
			</th>															
			<th style="width: 10%" class="header <%=listHeaderHolder.getCurrentSortClass("pageSize")%>">
				<a onclick="submitForm('listListHeader_form', null, null, 'pageSize', '<%=listHeaderHolder.getNextSortOrder("pageSize")%>', 'tableWrapper');"><bean:message bundle="define" key="define.list.header.page.size" /></a>
			</th>
			<th style="width: 8%" class="header <%=listHeaderHolder.getCurrentSortClass("usePagination")%>">
				<a onclick="submitForm('listListHeader_form', null, null, 'usePagination', '<%=listHeaderHolder.getNextSortOrder("usePagination")%>', 'tableWrapper');"><bean:message bundle="define" key="define.list.header.use.pagination" /></a>
			</th>												
			<th style="width: 8%" class="header <%=listHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listListHeader_form', null, null, 'status', '<%=listHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="listHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="listHeaderVO" name="listHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="listHeaderVO" property="listHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="listHeaderVO" property="listHeaderId"/>" />
					</td>
					<td class="left"><a onclick="link('listDetailAction.do?proc=list_object&listHeaderId=<bean:write name="listHeaderVO" property="encodedId"/>');"><bean:write name="listHeaderVO" property="nameZH" /></a></td>
					<td class="left"><a onclick="link('listDetailAction.do?proc=list_object&listHeaderId=<bean:write name="listHeaderVO" property="encodedId"/>');"><bean:write name="listHeaderVO" property="nameEN" /></a></td>
					<td class="left"><bean:write name="listHeaderVO" property="decodeTable" /></td>
					<td class="left"><bean:write name="listHeaderVO" property="decodeSearchHeader" /></td>
					<td class="left"><bean:write name="listHeaderVO" property="decodeIsSearchFirst" /></td>
					<td class="left"><bean:write name="listHeaderVO" property="pageSize" /></td>
					<td class="left"><bean:write name="listHeaderVO" property="decodeUsePagination" /></td>
					<td class="left">
						<bean:write name="listHeaderVO" property="decodeStatus" />&nbsp;
						<logic:equal name="listHeaderVO" property="useJavaObject" value="1">
							<a id="quickColumnIndex" name="quickColumnIndex" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.quick.set.list.sequence" />" onclick="alert('<bean:message bundle="public" key="popup.img.quick.set.list.sequence" />');"><img src="images/trans.png" /></a>
						</logic:equal>
						<logic:notEqual name="listHeaderVO" property="useJavaObject" value="1">
							<a id="quickColumnIndex" name="quickColumnIndex" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.quick.set.list.sequence" />" onclick="showQuickColumnIndexPopup('<bean:write name="listHeaderVO" property="listHeaderId"/>');";><img src="images/trans.png" /></a>
						</logic:notEqual>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="listHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="11" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="listHeaderHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="listHeaderHolder" property="indexStart" /> - <bean:write name="listHeaderHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listListHeader_form', null, '<bean:write name="listHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listListHeader_form', null, '<bean:write name="listHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listListHeader_form', null, '<bean:write name="listHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listListHeader_form', null, '<bean:write name="listHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="listHeaderHolder" property="realPage" />/<bean:write name="listHeaderHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>