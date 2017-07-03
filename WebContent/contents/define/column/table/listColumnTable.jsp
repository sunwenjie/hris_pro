<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder columnHolder = (PagedListHolder) request.getAttribute("columnHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 8%" class="header <%=columnHolder.getCurrentSortClass("columnId")%>">
				<a onclick="submitForm('listcolumn_form', null, null, 'columnId', '<%=columnHolder.getNextSortOrder("columnId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.id" /></a>
			</th>
			<th style="width: 9%" class="header <%=columnHolder.getCurrentSortClass("tableId")%>">
				<a onclick="submitForm('listcolumn_form', null, null, 'tableId', '<%=columnHolder.getNextSortOrder("tableId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.table" /></a>
			</th>
			<th style="width: 9%" class="header <%=columnHolder.getCurrentSortClass("nameDB")%>">
				<a onclick="submitForm('listcolumn_form', null, null, 'nameDB', '<%=columnHolder.getNextSortOrder("nameDB")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.name.db" /></a>
			</th>
			<th style="width: 9%" class="header <%=columnHolder.getCurrentSortClass("nameSys")%>">
				<a onclick="submitForm('listcolumn_form', null, null, 'nameSys', '<%=columnHolder.getNextSortOrder("nameSys")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.name.sys" /></a>
			</th>
			<th style="width: 9%" class="header <%=columnHolder.getCurrentSortClass("groupId")%>">
				<a onclick="submitForm('listcolumn_form', null, null, 'groupId', '<%=columnHolder.getNextSortOrder("groupId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.group" /></a>
			</th>
			<th style="width: 8%" class="header <%=columnHolder.getCurrentSortClass("isRequired")%>">
				<a onclick="submitForm('listcolumn_form', null, null, 'isRequired', '<%=columnHolder.getNextSortOrder("isRequired")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.is.required" /></a>
			</th>
			<th style="width: 8%" class="header <%=columnHolder.getCurrentSortClass("inputType")%>">
				<a onclick="submitForm('listcolumn_form', null, null, 'inputType', '<%=columnHolder.getNextSortOrder("inputType")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.input.type" /></a>
			</th>
			<th style="width: 8%" class="header <%=columnHolder.getCurrentSortClass("valueType")%>">
				<a onclick="submitForm('listcolumn_form', null, null, 'valueType', '<%=columnHolder.getNextSortOrder("valueType")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.value.type" /></a>
			</th>
			<th style="width: 8%" class="header <%=columnHolder.getCurrentSortClass("validateType")%>">
				<a onclick="submitForm('listcolumn_form', null, null, 'validateType', '<%=columnHolder.getNextSortOrder("validateType")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.validate.type" /></a>
			</th>
			<th style="width: 8%" class="header <%=columnHolder.getCurrentSortClass("columnIndex")%>">
				<a onclick="submitForm('listcolumn_form', null, null, 'columnIndex', '<%=columnHolder.getNextSortOrder("columnIndex")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.column.index" /></a>
			</th>
			<th style="width: 8%" class="header <%=columnHolder.getCurrentSortClass("displayType")%>">
				<a onclick="submitForm('listcolumn_form', null, null, 'displayType', '<%=columnHolder.getNextSortOrder("displayType")%>', 'tableWrapper');"><bean:message bundle="define" key="define.column.display.type" /></a>
			</th>
			<th style="width: 8%" class="header <%=columnHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listcolumn_form', null, null, 'status', '<%=columnHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="columnHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="columnVO" name="columnHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="columnVO" property="extended" value="2">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="columnVO" property="columnId"/>" name="chkSelectRow[]" value="<bean:write name="columnVO" property="columnId"/>" />
						</logic:equal>
					</td>
					<td class="left"><a onclick="link('columnAction.do?proc=to_objectModify&id=<bean:write name="columnVO" property="encodedId"/>');"><bean:write name="columnVO" property="columnId" /></a></td>
					<td class="left"><a onclick="link('tableAction.do?proc=to_objectModify&tableId=<bean:write name="columnVO" property="encodedTableId"/>');"><bean:write name="columnVO" property="decodeTable" /></a></td>
					<td class="left"><bean:write name="columnVO" property="nameDB" /></td>
					<td class="left"><bean:write name="columnVO" property="nameSys" /></td>
					<td class="left"><a onclick="link('columnGroupAction.do?proc=to_objectModify&id=<bean:write name="columnVO" property="encodedGroupId"/>');"><bean:write name="columnVO" property="decodeGroup" /></a></td>
					<td class="left"><bean:write name="columnVO" property="decodeIsRequired" /></td>
					<td class="left"><bean:write name="columnVO" property="decodeInputType" /></td>
					<td class="left"><bean:write name="columnVO" property="decodeValueType" /></td>
					<td class="left"><bean:write name="columnVO" property="decodeValidateType" /></td>
					<td class="left"><bean:write name="columnVO" property="columnIndex" /></td>
					<td class="left"><bean:write name="columnVO" property="decodeDisplayType" /></td>
					<td class="left"><bean:write name="columnVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="columnHolder">
		<tfoot>
			<tr class="total">
				<td colspan="13" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="columnHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="columnHolder" property="indexStart" /> - <bean:write name="columnHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listcolumn_form', null, '<bean:write name="columnHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listcolumn_form', null, '<bean:write name="columnHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listcolumn_form', null, '<bean:write name="columnHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listcolumn_form', null, '<bean:write name="columnHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="columnHolder" property="realPage" />/<bean:write name="columnHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>