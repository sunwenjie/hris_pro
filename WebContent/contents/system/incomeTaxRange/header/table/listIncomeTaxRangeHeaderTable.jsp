<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder incomeTaxRangeHeaderHolder = (PagedListHolder) request.getAttribute("incomeTaxRangeHeaderHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=incomeTaxRangeHeaderHolder.getCurrentSortClass("headerId")%>">
				<a onclick="submitForm('listIncomeTaxRangeHeader_form', null, null, 'headerId', '<%=incomeTaxRangeHeaderHolder.getNextSortOrder("headerId")%>', 'tableWrapper');">税率ID</a>
			</th>
			<th style="width: 20%" class="header <%=incomeTaxRangeHeaderHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listIncomeTaxRangeHeader_form', null, null, 'nameZH', '<%=incomeTaxRangeHeaderHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">税率名称（中文）</a>
			</th>
			<th style="width: 20%" class="header <%=incomeTaxRangeHeaderHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listIncomeTaxRangeHeader_form', null, null, 'nameEN', '<%=incomeTaxRangeHeaderHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">税率名称（英文）</a>
			</th>
			<th style="width: 20%" class="header <%=incomeTaxRangeHeaderHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('listIncomeTaxRangeHeader_form', null, null, 'startDate', '<%=incomeTaxRangeHeaderHolder.getNextSortOrder("startDate")%>', 'tableWrapper');">生效日期</a>
			</th>
			<th style="width: 20%" class="header <%=incomeTaxRangeHeaderHolder.getCurrentSortClass("endDate")%>">
				<a onclick="submitForm('listIncomeTaxRangeHeader_form', null, null, 'endDate', '<%=incomeTaxRangeHeaderHolder.getNextSortOrder("endDate")%>', 'tableWrapper');">失效日期</a>
			</th>
			<th style="width: 10%" class="header <%=incomeTaxRangeHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listIncomeTaxRangeHeader_form', null, null, 'status', '<%=incomeTaxRangeHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="incomeTaxRangeHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="incomeTaxRangeHeaderVO" name="incomeTaxRangeHeaderHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="incomeTaxRangeHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="incomeTaxRangeHeaderVO" property="headerId"/>" />
					</td>
					<td class="left"><bean:write name="incomeTaxRangeHeaderVO" property="headerId"/></td>
					<td class="left">
						<a onclick="link('incomeTaxRangeDetailAction.do?proc=list_object&id=<bean:write name="incomeTaxRangeHeaderVO" property="encodedId"/>');"><bean:write name="incomeTaxRangeHeaderVO" property="nameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('incomeTaxRangeDetailAction.do?proc=list_object&id=<bean:write name="incomeTaxRangeHeaderVO" property="encodedId"/>');"><bean:write name="incomeTaxRangeHeaderVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="incomeTaxRangeHeaderVO" property="startDate"/></td>
					<td class="left"><bean:write name="incomeTaxRangeHeaderVO" property="endDate"/></td>
					<td class="left"><bean:write name="incomeTaxRangeHeaderVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="incomeTaxRangeHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="incomeTaxRangeHeaderHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="incomeTaxRangeHeaderHolder" property="indexStart" /> - <bean:write name="incomeTaxRangeHeaderHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listIncomeTaxRangeHeader_form', null, '<bean:write name="incomeTaxRangeHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listIncomeTaxRangeHeader_form', null, '<bean:write name="incomeTaxRangeHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listIncomeTaxRangeHeader_form', null, '<bean:write name="incomeTaxRangeHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listIncomeTaxRangeHeader_form', null, '<bean:write name="incomeTaxRangeHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="incomeTaxRangeHeaderHolder" property="realPage" />/<bean:write name="incomeTaxRangeHeaderHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>