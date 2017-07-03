<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder incomeTaxRangeDetailHolder = ( PagedListHolder )request.getAttribute( "incomeTaxRangeDetailHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=incomeTaxRangeDetailHolder.getCurrentSortClass("detailId")%>">
				<a onclick="submitForm('listIncomeTaxRangeDetail_form', null, null, 'detailId', '<%=incomeTaxRangeDetailHolder.getNextSortOrder("detailId")%>', 'tableWrapper');">ID</a>
			</th>
			<th style="width: 20%" class="header <%=incomeTaxRangeDetailHolder.getCurrentSortClass("rangeFrom")%>">
				<a onclick="submitForm('listIncomeTaxRangeDetail_form', null, null, 'rangeFrom', '<%=incomeTaxRangeDetailHolder.getNextSortOrder("rangeFrom")%>', 'tableWrapper');">收入基数（开始）</a>
			</th>
			<th style="width: 20%" class="header <%=incomeTaxRangeDetailHolder.getCurrentSortClass("rangeTo")%>">
				<a onclick="submitForm('listIncomeTaxRangeDetail_form', null, null, 'rangeTo', '<%=incomeTaxRangeDetailHolder.getNextSortOrder("rangeTo")%>', 'tableWrapper');">收入基数（结束）</a>
			</th>
			<th style="width: 20%" class="header <%=incomeTaxRangeDetailHolder.getCurrentSortClass("percentage")%>">
				<a onclick="submitForm('listIncomeTaxRangeDetail_form', null, null, 'percentage', '<%=incomeTaxRangeDetailHolder.getNextSortOrder("percentage")%>', 'tableWrapper');">税率</a>
			</th>
			<th style="width: 20%" class="header <%=incomeTaxRangeDetailHolder.getCurrentSortClass("deduct")%>">
				<a onclick="submitForm('listIncomeTaxRangeDetail_form', null, null, 'deduct', '<%=incomeTaxRangeDetailHolder.getNextSortOrder("deduct")%>', 'tableWrapper');">扣除金额</a>
			</th>
			<th style="width: 10%" class="header <%=incomeTaxRangeDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listIncomeTaxRangeDetail_form', null, null, 'status', '<%=incomeTaxRangeDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="incomeTaxRangeDetailHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="incomeTaxRangeDetailVO" name="incomeTaxRangeDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="incomeTaxRangeDetailVO" property="detailId"/>" name="chkSelectRow[]" value="<bean:write name="incomeTaxRangeDetailVO" property="detailId"/>" />
					</td>
					<td class="left"><a onclick="detailModify('<bean:write name="incomeTaxRangeDetailVO" property="encodedId"/>');"><bean:write name="incomeTaxRangeDetailVO" property="detailId"/></a></td>
					<td class="left"><bean:write name="incomeTaxRangeDetailVO" property="rangeFrom"/></td>
					<td class="left"><bean:write name="incomeTaxRangeDetailVO" property="rangeTo"/></td>
					<td class="left"><bean:write name="incomeTaxRangeDetailVO" property="percentage"/></td>
					<td class="left"><bean:write name="incomeTaxRangeDetailVO" property="deduct"/></td>
					<td class="left"><bean:write name="incomeTaxRangeDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="incomeTaxRangeDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="7" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="incomeTaxRangeDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="incomeTaxRangeDetailHolder" property="indexStart" /> - <bean:write name="incomeTaxRangeDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listIncomeTaxRangeDetail_form', null, '<bean:write name="incomeTaxRangeDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listIncomeTaxRangeDetail_form', null, '<bean:write name="incomeTaxRangeDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listIncomeTaxRangeDetail_form', null, '<bean:write name="incomeTaxRangeDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listIncomeTaxRangeDetail_form', null, '<bean:write name="incomeTaxRangeDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="incomeTaxRangeDetailHolder" property="realPage" />/<bean:write name="incomeTaxRangeDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>