<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder cbDetailHolder = (PagedListHolder) request.getAttribute("cbDetailHolder");
	final boolean IN_HOUSE = request.getAttribute( "role" ).equals( "2" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: <%=IN_HOUSE ? "15" : "10" %>%" class="header <%=cbDetailHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'itemId', '<%=cbDetailHolder.getNextSortOrder("itemId")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.detail.item.id" /></a>
			</th>
			<th style="width: <%=IN_HOUSE ? "15" : "10" %>%" class="header <%=cbDetailHolder.getCurrentSortClass("itemNo")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'itemNo', '<%=cbDetailHolder.getNextSortOrder("itemNo")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.detail.item.no" /></a>
			</th>
			<th style="width: <%=IN_HOUSE ? "35" : "30" %>%" class="header <%=cbDetailHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'nameZH', '<%=cbDetailHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.detail.item.name" /></a>
			</th>
			<th style="width: <%=IN_HOUSE ? "15" : "10" %>%" class="header <%=cbDetailHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'monthly', '<%=cbDetailHolder.getNextSortOrder("monthly")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.header.month" /></a>
			</th>
			<logic:equal name="role" value="1">
			<th style="width: 10%" class="header <%=cbDetailHolder.getCurrentSortClass("amountSalesPrice")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'amountSalesPrice', '<%=cbDetailHolder.getNextSortOrder("amountSalesPrice")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.batch.amount.sales.price" /></a>
			</th>
			</logic:equal>
			<th style="width: 10%" class="header <%=cbDetailHolder.getCurrentSortClass("amountSalesCost")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'amountSalesCost', '<%=cbDetailHolder.getNextSortOrder("amountSalesCost")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.batch.amount.sales.cost" /></a>
			</th>
			<logic:equal name="role" value="1">
			<th style="width: 10%" class="header <%=cbDetailHolder.getCurrentSortClass("amountPurchaseCost")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'amountPurchaseCost', '<%=cbDetailHolder.getNextSortOrder("amountPurchaseCost")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.header.amount.purchase.cost" /></a>
			</th>
			</logic:equal>
			<th style="width: 10%" class="header <%=cbDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listDetail_form', null, null, 'status', '<%=cbDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="cbDetailHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="cbDetailVO" name="cbDetailHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" disabled="disabled" id="kanList_chkSelectRecord_<bean:write name="cbDetailVO" property="detailId"/>" name="chkSelectRow[]" value="<bean:write name="cbDetailVO" property="encodedId"/>" />
					</td>
					<td class="left"><span><bean:write name="cbDetailVO" property="itemId" /></span></td>
					<td class="left"><span><bean:write name="cbDetailVO" property="itemNo" /></span></td>
					<td class="left"><span><bean:write name="cbDetailVO" property="nameZH" /></span></td>
					<td class="left"><bean:write name="cbDetailVO" property="monthly" /></td>
					<logic:equal name="role" value="1">
					<td class="right"><span><bean:write name="cbDetailVO" property="amountSalesPrice" /></span></td>
					</logic:equal>
					<td class="right"><span><bean:write name="cbDetailVO" property="amountSalesCost" /></span></td>
					<logic:equal name="role" value="1">
					<td class="right"><span><bean:write name="cbDetailVO" property="amountPurchaseCost" /></span></td>
					</logic:equal>
					<td class="left"><bean:write name="cbDetailVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="cbDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="9" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="cbDetailHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="cbDetailHolder" property="indexStart" /> - <bean:write name="cbDetailHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="cbDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="cbDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="cbDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('listDetail_form', null, '<bean:write name="cbDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="cbDetailHolder" property="realPage" />/<bean:write name="cbDetailHolder" property="pageCount" /> </label>&nbsp;
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="cbDetailHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
			</tr>
		</tfoot>
	</logic:present>
</table>
<div class="bottom">
	<p></p>
</div>
						
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
		
		$('#kanList_chkSelectAll').attr('checked', 'checked');
		$('#kanList_chkSelectAll').click();
		$('#kanList_chkSelectAll').attr('checked', 'checked');
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
