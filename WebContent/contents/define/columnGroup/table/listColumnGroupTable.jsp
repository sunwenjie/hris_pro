<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder columnGroupHolder = (PagedListHolder) request.getAttribute("columnGroupHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 25%" class="header <%=columnGroupHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listcolumnGroup_form', null, null, 'nameZH', '<%=columnGroupHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.group.name.cn" /></a>
			</th>
			<th style="width: 25%" class="header <%=columnGroupHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listcolumnGroup_form', null, null, 'nameEN', '<%=columnGroupHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.group.name.en" /></a>
			</th>
			<th style="width: 10%" class="header <%=columnGroupHolder.getCurrentSortClass("useName")%>">
				<a onclick="submitForm('listcolumnGroup_form', null, null, 'useName', '<%=columnGroupHolder.getNextSortOrder("useName")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.group.use.name" /></a>
			</th>
			<th style="width: 10%" class="header <%=columnGroupHolder.getCurrentSortClass("useBorder")%>">
				<a onclick="submitForm('listcolumnGroup_form', null, null, 'useBorder', '<%=columnGroupHolder.getNextSortOrder("useBorder")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.group.use.border" /></a>
			</th>
			<th style="width: 10%" class="header <%=columnGroupHolder.getCurrentSortClass("isFlexable")%>">
				<a onclick="submitForm('listcolumnGroup_form', null, null, 'isFlexable', '<%=columnGroupHolder.getNextSortOrder("isFlexable")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.group.is.flexable" /></a>
			</th>
			<th style="width: 10%" class="header <%=columnGroupHolder.getCurrentSortClass("isDisplayed")%>">
				<a onclick="submitForm('listcolumnGroup_form', null, null, 'isDisplayed', '<%=columnGroupHolder.getNextSortOrder("isDisplayed")%>', 'tableWrapper');" title="<bean:message bundle="define" key="define.column.group.is.display.tips" />"><bean:message bundle="define" key="define.column.group.is.display" /> <img src="images/tips.png" width="14" height="14px" /></a>
			</th>
			<th style="width: 10%" class="header <%=columnGroupHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listcolumnGroup_form', null, null, 'status', '<%=columnGroupHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="columnGroupHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="columnGroupVO" name="columnGroupHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="columnGroupVO" property="extended" value="2">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="columnGroupVO" property="groupId"/>" name="chkSelectRow[]" value="<bean:write name="columnGroupVO" property="groupId"/>" />
						</logic:equal>
					</td>
					<td class="left"><a onclick="link('columnGroupAction.do?proc=to_objectModify&id=<bean:write name="columnGroupVO" property="encodedId"/>');"><bean:write name="columnGroupVO" property="nameZH" /></a></td>
					<td class="left"><a onclick="link('columnGroupAction.do?proc=to_objectModify&id=<bean:write name="columnGroupVO" property="encodedId"/>');"><bean:write name="columnGroupVO" property="nameEN" /></a></td>
					<td class="left"><bean:write name="columnGroupVO" property="decodeUseName" /></td>
					<td class="left"><bean:write name="columnGroupVO" property="decodeUseBorder" /></td>
					<td class="left"><bean:write name="columnGroupVO" property="decodeIsFlexable" /></td>
					<td class="left"><bean:write name="columnGroupVO" property="decodeIsDisplayed" /></td>
					<td class="left"><bean:write name="columnGroupVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="columnGroupHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="columnGroupHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="columnGroupHolder" property="indexStart" /> - <bean:write name="columnGroupHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listcolumnGroup_form', null, '<bean:write name="columnGroupHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listcolumnGroup_form', null, '<bean:write name="columnGroupHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listcolumnGroup_form', null, '<bean:write name="columnGroupHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listcolumnGroup_form', null, '<bean:write name="columnGroupHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="columnGroupHolder" property="realPage" />/<bean:write name="columnGroupHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>