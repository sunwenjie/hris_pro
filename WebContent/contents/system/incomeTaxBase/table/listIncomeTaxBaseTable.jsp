<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder incomeTaxBaseHolder = ( PagedListHolder )request.getAttribute( "incomeTaxBaseHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 6%" class="header <%=incomeTaxBaseHolder.getCurrentSortClass("baseId")%>">
				<a onclick="submitForm('listIncomeTaxBase_form', null, null, 'baseId', '<%=incomeTaxBaseHolder.getNextSortOrder("baseId")%>', 'tableWrapper');">起征ID</a>
			</th>
			<th style="width: 20%" class="header <%=incomeTaxBaseHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listIncomeTaxBase_form', null, null, 'nameZH', '<%=incomeTaxBaseHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">个税起征名称（中文）</a>
			</th>
			<th style="width: 20%" class="header <%=incomeTaxBaseHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listIncomeTaxBase_form', null, null, 'nameEN', '<%=incomeTaxBaseHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">个税起征名称（英文）</a>
			</th>
			<th style="width: 12%" class="header <%=incomeTaxBaseHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('listIncomeTaxBase_form', null, null, 'startDate', '<%=incomeTaxBaseHolder.getNextSortOrder("startDate")%>', 'tableWrapper');">生效日期</a>
			</th>
			<th style="width: 12%" class="header <%=incomeTaxBaseHolder.getCurrentSortClass("endDate")%>">
				<a onclick="submitForm('listIncomeTaxBase_form', null, null, 'endDate', '<%=incomeTaxBaseHolder.getNextSortOrder("endDate")%>', 'tableWrapper');">失效日期</a>
			</th>
			<th style="width: 12%" class="header <%=incomeTaxBaseHolder.getCurrentSortClass("base")%>">
				<a onclick="submitForm('listIncomeTaxBase_form', null, null, 'base', '<%=incomeTaxBaseHolder.getNextSortOrder("base")%>', 'tableWrapper');">个税起征点</a>
			</th>
			<th style="width: 12%" class="header <%=incomeTaxBaseHolder.getCurrentSortClass("baseForeigner")%>">
				<a onclick="submitForm('listIncomeTaxBase_form', null, null, 'baseForeigner', '<%=incomeTaxBaseHolder.getNextSortOrder("baseForeigner")%>', 'tableWrapper');">个税起征点（外籍）</a>
			</th>
			<th style="width: 6%" class="header <%=incomeTaxBaseHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listIncomeTaxBase_form', null, null, 'status', '<%=incomeTaxBaseHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="incomeTaxBaseHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="incomeTaxBaseVO" name="incomeTaxBaseHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="incomeTaxBaseVO" property="baseId"/>" name="chkSelectRow[]" value="<bean:write name="incomeTaxBaseVO" property="baseId"/>" />
					</td>
					<td class="left"><bean:write name="incomeTaxBaseVO" property="baseId"/></td>
					<td class="left">
						<a onclick="link('incomeTaxBaseAction.do?proc=to_objectModify&id=<bean:write name="incomeTaxBaseVO" property="encodedId"/>');"><bean:write name="incomeTaxBaseVO" property="nameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('incomeTaxBaseAction.do?proc=to_objectModify&id=<bean:write name="incomeTaxBaseVO" property="encodedId"/>');"><bean:write name="incomeTaxBaseVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="incomeTaxBaseVO" property="startDate"/></td>
					<td class="left"><bean:write name="incomeTaxBaseVO" property="endDate"/></td>
					<td class="right"><bean:write name="incomeTaxBaseVO" property="base"/></td>
					<td class="right"><bean:write name="incomeTaxBaseVO" property="baseForeigner"/></td>
					<td class="left"><bean:write name="incomeTaxBaseVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="incomeTaxBaseHolder">
		<tfoot>
			<tr class="total">
				<td colspan="10" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="incomeTaxBaseHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="incomeTaxBaseHolder" property="indexStart" /> - <bean:write name="incomeTaxBaseHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listIncomeTaxBase_form', null, '<bean:write name="incomeTaxBaseHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listIncomeTaxBase_form', null, '<bean:write name="incomeTaxBaseHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listIncomeTaxBase_form', null, '<bean:write name="incomeTaxBaseHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listIncomeTaxBase_form', null, '<bean:write name="incomeTaxBaseHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="incomeTaxBaseHolder" property="realPage" />/<bean:write name="incomeTaxBaseHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>