<%@ page pageEncoding="GB2312"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder listDetailHolder = (PagedListHolder) request.getAttribute("listDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" checked="checked" disabled="disabled"/>
			</th>
			<th style="width: 6%" class="header <%=listDetailHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'itemId', '<%=listDetailHolder.getNextSortOrder("itemId")%>', 'tableWrapper');">科目ID</a>
			</th>
			<th style="width: 12%" class="header <%=listDetailHolder.getCurrentSortClass("itemNo")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'itemNo', '<%=listDetailHolder.getNextSortOrder("itemNo")%>', 'tableWrapper');">科目编号</a>
			</th>
			<th style="width: 21%" class="header <%=listDetailHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'nameZH', '<%=listDetailHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">科目名称（中文）</a>
			</th>
			<th style="width: 21%" class="header <%=listDetailHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'nameEN', '<%=listDetailHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">科目名称（英文）</a>
			</th>
			<th style="width: 10%" class="header <%=listDetailHolder.getCurrentSortClass("billAmountCompany")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'billAmountCompany', '<%=listDetailHolder.getNextSortOrder("billAmountCompany")%>', 'tableWrapper');">公司营收</a>
			</th>
			<th style="width: 10%" class="header <%=listDetailHolder.getCurrentSortClass("costFixCompany")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'costAmountCompany', '<%=listDetailHolder.getNextSortOrder("costAmountCompany")%>', 'tableWrapper');">公司成本</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="listDetailHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="invoiceDetailVO" name="listDetailHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td><input type="checkbox" disabled="disabled" id="kanList_chkSelectRecord_<bean:write name="invoiceDetailVO" property="invoiceDetailId"/>" name="chkSelectRow[]" value="<bean:write name="invoiceDetailVO" property="encodedId"/>" value="" checked="checked" disabled="disabled"/></td>
					<td class="left"><span><bean:write name="invoiceDetailVO" property="itemId" /></span></td>
					<td class="left"><span><bean:write name="invoiceDetailVO" property="itemNo" /></span></td>
					<td class="left"><span><bean:write name="invoiceDetailVO" property="nameZH" /></span></td>
					<td class="left"><span><bean:write name="invoiceDetailVO" property="nameEN" /></span></td>
					<td class="right"><span><bean:write name="invoiceDetailVO" property="billAmountCompany" /></span></td>
					<td class="right"><span><bean:write name="invoiceDetailVO" property="costAmountCompany" /></span></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="listDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="14" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="listDetailHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="listDetailHolder" property="indexStart" /> - <bean:write name="listDetailHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="listDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="listDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="listDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="listDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="listDetailHolder" property="realPage" />/<bean:write name="listDetailHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
<div class="bottom">
	<p/>
</div>
						
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
</script>
