<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder importDetailHolder = (PagedListHolder)request.getAttribute("importDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 20%" class="header <%=importDetailHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'nameZH', '<%=importDetailHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">字段名称（中文）</a>
			</th>
			<th style="width: 20%" class="header <%=importDetailHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'nameEN', '<%=importDetailHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">字段名称（英文）</a>
			</th>
			<th style="width: 20%" class="header <%=importDetailHolder.getCurrentSortClass("columnId")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'columnId', '<%=importDetailHolder.getNextSortOrder("columnId")%>', 'tableWrapper');">原字段名称</a>
			</th>
			<th style="width: 18%" class="header <%=importDetailHolder.getCurrentSortClass("tempValue")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'tempValue', '<%=importDetailHolder.getNextSortOrder("tempValue")%>', 'tableWrapper');">示例值</a>
			</th>
			<th style="width: 8%" class="header <%=importDetailHolder.getCurrentSortClass("columnWidth")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'columnWidth', '<%=importDetailHolder.getNextSortOrder("columnWidth")%>', 'tableWrapper');">字段宽度</a>
			</th>
			<th style="width: 8%" class="header <%=importDetailHolder.getCurrentSortClass("columnIndex")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'columnIndex', '<%=importDetailHolder.getNextSortOrder("columnIndex")%>', 'tableWrapper');">字段顺序</a>
			</th>
			<th style="width: 8%" class="header <%=importDetailHolder.getCurrentSortClass("fontSize")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'fontSize', '<%=importDetailHolder.getNextSortOrder("fontSize")%>', 'tableWrapper');">字体大小</a>
			</th>
			<th style="width: 8%" class="header <%=importDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'status', '<%=importDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>							
		</tr>
	</thead>							
	<logic:notEqual name="importDetailHolder" property="holderSize" value="0">						
		<tbody>
			<logic:iterate id="importDetailVO" name="importDetailHolder" property="source" indexId="number">
				<input type="hidden" id="columnId" name="columnId" value="<bean:write name="importDetailVO" property="columnId"/>" />
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="importDetailVO" property="importDetailId"/>" name="chkSelectRow[]" value="<bean:write name="importDetailVO" property="importDetailId"/>" />
					</td>
					<td class="left">
						<a onclick="objectModify('<bean:write name="importDetailVO" property="encodedId"/>', '<bean:write name="importDetailVO" property="columnId"/>');"><bean:write name="importDetailVO" property="nameZH"/></a>
					</td>	
					<td class="left">
						<a onclick="objectModify('<bean:write name="importDetailVO" property="encodedId"/>', '<bean:write name="importDetailVO" property="columnId"/>');"><bean:write name="importDetailVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="importDetailVO" property="decodeColumn"/></td>
					<td class="left"><bean:write name="importDetailVO" property="tempValue"/></td>
					<td class="left"><bean:write name="importDetailVO" property="columnWidth"/></td>
					<td class="left"><bean:write name="importDetailVO" property="columnIndex"/></td>
					<td class="left"><bean:write name="importDetailVO" property="fontSize"/></td>
					<td class="left"><bean:write name="importDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="importDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="11" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="importDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="importDetailHolder" property="indexStart" /> - <bean:write name="importDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listImportDetail_form', null, '<bean:write name="importDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listImportDetail_form', null, '<bean:write name="importDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listImportDetail_form', null, '<bean:write name="importDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listImportDetail_form', null, '<bean:write name="importDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="importDetailHolder" property="realPage" />/<bean:write name="importDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
		 	</tr>
		</tfoot>
	</logic:present>
</table>