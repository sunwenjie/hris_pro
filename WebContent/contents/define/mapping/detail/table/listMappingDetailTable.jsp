<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	PagedListHolder mappingDetailHolder = (PagedListHolder)request.getAttribute("mappingDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 25%" class="header <%=mappingDetailHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listmappingDetail_form', null, null, 'nameZH', '<%=mappingDetailHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">字段名称（中文）</a>
			</th>
			<th style="width: 25%" class="header <%=mappingDetailHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listmappingDetail_form', null, null, 'nameEN', '<%=mappingDetailHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">字段名称（英文）</a>
			</th>
			<th style="width: 20%" class="header <%=mappingDetailHolder.getCurrentSortClass("columnIndex")%>">
				<a onclick="submitForm('listmappingDetail_form', null, null, 'columnIndex', '<%=mappingDetailHolder.getNextSortOrder("columnIndex")%>', 'tableWrapper');">排列顺序</a>
			</th>
			<th style="width: 20%" class="header <%=mappingDetailHolder.getCurrentSortClass("columnWidth")%>">
				<a onclick="submitForm('listmappingDetail_form', null, null, 'columnWidth', '<%=mappingDetailHolder.getNextSortOrder("columnWidth")%>', 'tableWrapper');">字段宽度</a>
			</th>	
			<th style="width: 10%" class="header <%=mappingDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listmappingDetail_form', null, null, 'status', '<%=mappingDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>							
		</tr>
	</thead>							
	<logic:notEqual name="mappingDetailHolder" property="holderSize" value="0">						
		<tbody>
			<logic:iterate id="mappingDetailVO" name="mappingDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="mappingDetailVO" property="mappingDetailId"/>" name="chkSelectRow[]" value="<bean:write name="mappingDetailVO" property="mappingDetailId"/>" />
					</td>
					<td class="left"><a onclick="mappingDetailModify('<bean:write name="mappingDetailVO" property="encodedId"/>','<bean:write name="mappingDetailVO" property="columnId"/>');"><bean:write name="mappingDetailVO" property="nameZH"/></a></td>
					<td class="left"><a onclick="mappingDetailModify('<bean:write name="mappingDetailVO" property="encodedId"/>','<bean:write name="mappingDetailVO" property="columnId"/>');"><bean:write name="mappingDetailVO" property="nameEN"/></a></td>
					<td class="left"><bean:write name="mappingDetailVO" property="columnIndex"/></td>
					<td class="left"><bean:write name="mappingDetailVO" property="columnWidth"/></td>
					<td class="left"><bean:write name="mappingDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="mappingDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="9" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="mappingDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="mappingDetailHolder" property="indexStart" /> - <bean:write name="mappingDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listmappingDetail_form', null, '<bean:write name="mappingDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listmappingDetail_form', null, '<bean:write name="mappingDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listmappingDetail_form', null, '<bean:write name="mappingDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listmappingDetail_form', null, '<bean:write name="mappingDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="mappingDetailHolder" property="realPage" />/<bean:write name="mappingDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>