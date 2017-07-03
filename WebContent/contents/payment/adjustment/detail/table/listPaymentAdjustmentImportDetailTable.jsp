<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder paymentAdjustmentImportDetailHolder = (PagedListHolder) request.getAttribute("paymentAdjustmentImportDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			
			<th style="width: 6%" class="header-nosort">
				序号
			</th>
			<th style="width: 8%" class="header-nosort">
				科目ID
			</th>
			<th style="width: 10%" class="header-nosort">
				科目编号
			</th>
			<th style="width: 25%" class="header-nosort">
				科目名称（中文）
			</th>
			<th style="width: 25%" class="header-nosort">
				科目名称（英文）
			</th>
			<th style="width: 10%" class="header-nosort">
				收入金额
			</th>
			<th style="width: 10%" class="header-nosort">
				支出金额
			</th>
			<th style="width: 6%" class="header-nosort">
				状态
			</th>
		</tr>
	</thead>
	<logic:notEmpty name="paymentAdjustmentImportDetailHolder">
	
		<tbody>
			<logic:iterate id="adjustmentDetailVO" name="paymentAdjustmentImportDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					
					<td class="left"><bean:write name="adjustmentDetailVO" property="adjustmentDetailId" /></td>
					<td class="left"><bean:write name="adjustmentDetailVO" property="itemId" /></td>
					<td class="left"><bean:write name="adjustmentDetailVO" property="itemNo" /></td>
					<td class="left"><bean:write name="adjustmentDetailVO" property="nameZH" /></td>
					<td class="left"><bean:write name="adjustmentDetailVO" property="nameEN" /></td>
					<td class="right"><bean:write name="adjustmentDetailVO" property="billAmountPersonal" /></td>
					<td class="right"><bean:write name="adjustmentDetailVO" property="costAmountPersonal" /></td>
					<td class="left"><bean:write name="adjustmentDetailVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEmpty>
	<logic:present name="paymentAdjustmentImportDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="11" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="paymentAdjustmentImportDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="paymentAdjustmentImportDetailHolder" property="indexStart" /> - <bean:write name="paymentAdjustmentImportDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listPaymentAdjustmentImportDetail_form', null, '<bean:write name="paymentAdjustmentImportDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listPaymentAdjustmentImportDetail_form', null, '<bean:write name="paymentAdjustmentImportDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listPaymentAdjustmentImportDetail_form', null, '<bean:write name="paymentAdjustmentImportDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listPaymentAdjustmentImportDetail_form', null, '<bean:write name="paymentAdjustmentImportDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="paymentAdjustmentImportDetailHolder" property="realPage" />/<bean:write name="paymentAdjustmentImportDetailHolder" property="pageCount" /></label>&nbsp;
					<label>&nbsp;&nbsp;跳转至：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="paymentAdjustmentImportDetailHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />页</label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>

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
		submitForm('listPaymentAdjustmentImportDetail_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>