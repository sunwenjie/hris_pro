<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder orderDetailTempHolder = (PagedListHolder) request.getAttribute("orderDetailTempHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" checked="checked" disabled="disabled"/>
			</th>
			<th style="width: 6%" class="header <%=orderDetailTempHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'itemId', '<%=orderDetailTempHolder.getNextSortOrder("itemId")%>', 'tableWrapper');">科目ID</a>
			</th>
			<th style="width: 12%" class="header <%=orderDetailTempHolder.getCurrentSortClass("itemNo")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'itemNo', '<%=orderDetailTempHolder.getNextSortOrder("itemNo")%>', 'tableWrapper');">科目编号</a>
			</th>
			<th style="width: 21%" class="header <%=orderDetailTempHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'nameZH', '<%=orderDetailTempHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">科目名称（中文）</a>
			</th>
			<th style="width: 21%" class="header <%=orderDetailTempHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'nameEN', '<%=orderDetailTempHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">科目名称（英文）</a>
			</th>
			<th style="width: 10%" class="header <%=orderDetailTempHolder.getCurrentSortClass("billAmountCompany")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'billAmountCompany', '<%=orderDetailTempHolder.getNextSortOrder("billAmountCompany")%>', 'tableWrapper');">公司营收</a>
			</th>
			<th style="width: 10%" class="header <%=orderDetailTempHolder.getCurrentSortClass("costFixCompany")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'costAmountCompany', '<%=orderDetailTempHolder.getNextSortOrder("costAmountCompany")%>', 'tableWrapper');">公司成本</a>
			</th>
			<th style="width: 10%" class="header <%=orderDetailTempHolder.getCurrentSortClass("billAmountPersonal")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'billAmountPersonal', '<%=orderDetailTempHolder.getNextSortOrder("billAmountPersonal")%>', 'tableWrapper');">个人收入</a>
			</th>
			<th style="width: 10%" class="header <%=orderDetailTempHolder.getCurrentSortClass("costFixPersonal")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'costAmountPersonal', '<%=orderDetailTempHolder.getNextSortOrder("costAmountPersonal")%>', 'tableWrapper');">个人支出</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="orderDetailTempHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="orderDetailTempVO" name="orderDetailTempHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td><input type="checkbox" disabled="disabled" id="kanList_chkSelectRecord_<bean:write name="orderDetailTempVO" property="orderDetailId"/>" name="chkSelectRow[]" value="<bean:write name="orderDetailTempVO" property="encodedId"/>" value="" checked="checked" disabled="disabled"/></td>
					<td class="left"><span><bean:write name="orderDetailTempVO" property="itemId" /></span></td>
					<td class="left"><span><bean:write name="orderDetailTempVO" property="itemNo" /></span></td>
					<td class="left"><span><bean:write name="orderDetailTempVO" property="nameZH" /></span></td>
					<td class="left"><span><bean:write name="orderDetailTempVO" property="nameEN" /></span></td>
					<td class="right"><span><bean:write name="orderDetailTempVO" property="billAmountCompany" /></span></td>
					<td class="right"><span><bean:write name="orderDetailTempVO" property="costAmountCompany" /></span></td>
					<td class="right"><span><bean:write name="orderDetailTempVO" property="billAmountPersonal" /></span></td>
					<td class="right"><span><bean:write name="orderDetailTempVO" property="costAmountPersonal" /></span></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="orderDetailTempHolder">
		<tfoot>
			<tr class="total">
				<td colspan="14" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="orderDetailTempHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="orderDetailTempHolder" property="indexStart" /> - <bean:write name="orderDetailTempHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="orderDetailTempHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="orderDetailTempHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="orderDetailTempHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="orderDetailTempHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="orderDetailTempHolder" property="realPage" />/<bean:write name="orderDetailTempHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;跳转至：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="orderDetailTempHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />页</label>&nbsp;
				</td>
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
	
	function forward() {
		var value = Number($('#forwardPage').val());
		// 如果页数无效自动跳转到第一页
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage = Number($('.forwardPage').val()) - 1;
		submitForm('listDetail_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>