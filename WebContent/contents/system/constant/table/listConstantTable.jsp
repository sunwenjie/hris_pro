<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder constantHolder = (PagedListHolder) request.getAttribute("constantHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 20%" class="header <%=constantHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listConstant_form', null, null, 'nameZH', '<%=constantHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">参数名称（中文）</a>
			</th>
			<th style="width: 20%" class="header <%=constantHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listConstant_form', null, null, 'nameEN', '<%=constantHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">参数名称（英文）</a>
			</th>
			<th style="width: 20%" class="header <%=constantHolder.getCurrentSortClass("propertyName")%>">
				<a onclick="submitForm('listConstant_form', null, null, 'propertyName', '<%=constantHolder.getNextSortOrder("propertyName")%>', 'tableWrapper');">字段名称</a>
			</th>
			<th style="width: 8%" class="header <%=constantHolder.getCurrentSortClass("lengthType")%>">
				<a onclick="submitForm('listConstant_form', null, null, 'lengthType', '<%=constantHolder.getNextSortOrder("lengthType")%>', 'tableWrapper');">字段长度</a>
			</th>
			<th style="width: 8%" class="header <%=constantHolder.getCurrentSortClass("scopeType")%>">
				<a onclick="submitForm('listConstant_form', null, null, 'scopeType', '<%=constantHolder.getNextSortOrder("scopeType")%>', 'tableWrapper');">适用范围</a>
			</th>
			<th style="width: 8%" class="header <%=constantHolder.getCurrentSortClass("characterType")%>">
				<a onclick="submitForm('listConstant_form', null, null, 'characterType', '<%=constantHolder.getNextSortOrder("characterType")%>', 'tableWrapper');">参数性质</a>
			</th>
			<th style="width: 8%" class="header <%=constantHolder.getCurrentSortClass("valueType")%>">
				<a onclick="submitForm('listConstant_form', null, null, 'valueType', '<%=constantHolder.getNextSortOrder("valueType")%>', 'tableWrapper');">内容类型</a>
			</th>
			<th style="width: 8%" class="header <%=constantHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listConstant_form', null, null, 'status', '<%=constantHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="constantHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="constantVO" name="constantHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="constantVO" property="constantId"/>" name="chkSelectRow[]" value="<bean:write name="constantVO" property="constantId"/>" />
					</td>
					<td class="left">
						<a onclick="link('constantAction.do?proc=to_objectModify&constantId=<bean:write name="constantVO" property="encodedId"/>');"><bean:write name="constantVO" property="nameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('constantAction.do?proc=to_objectModify&constantId=<bean:write name="constantVO" property="encodedId"/>');"><bean:write name="constantVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="constantVO" property="propertyName"/></td>
					<td class="left"><bean:write name="constantVO" property="decodeLengthType"/></td>
					<td class="left"><bean:write name="constantVO" property="decodeScopeType"/></td>
					<td class="left"><bean:write name="constantVO" property="decodeCharacterType"/></td>
					<td class="left"><bean:write name="constantVO" property="decodeValueType"/></td>
					<td class="left"><bean:write name="constantVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="constantHolder">
		<tfoot>
			<tr class="total">
				<td colspan="9" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="constantHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="constantHolder" property="indexStart" /> - <bean:write name="constantHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listConstant_form', null, '<bean:write name="constantHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listConstant_form', null, '<bean:write name="constantHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listConstant_form', null, '<bean:write name="constantHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listConstant_form', null, '<bean:write name="constantHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="constantHolder" property="realPage" />/<bean:write name="constantHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>