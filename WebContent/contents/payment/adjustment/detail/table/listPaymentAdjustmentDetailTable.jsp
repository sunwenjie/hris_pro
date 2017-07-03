<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder paymentAdjustmentDetailHolder = (PagedListHolder) request.getAttribute("paymentAdjustmentDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 6%" class="header-nosort">
				<bean:message bundle="payment" key="payment.salary.adjustment.detail.id" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="payment" key="payment.salary.adjustment.detail.item.id" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="payment" key="payment.salary.adjustment.detail.item.no" />
			</th>
			<th style="width: 25%" class="header-nosort">
				<bean:message bundle="payment" key="payment.salary.adjustment.detail.item.name.cn" />
			</th>
			<th style="width: 25%" class="header-nosort">
				<bean:message bundle="payment" key="payment.salary.adjustment.detail.item.name.en" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="payment" key="payment.salary.adjustment.detail.billAmountPersonal" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="payment" key="payment.salary.adjustment.detail.costAmountPersonal" />
			</th>
			<th style="width: 6%" class="header-nosort">
				<bean:message bundle="public" key="public.status" />
			</th>
		</tr>
	</thead>
	<logic:notEmpty name="paymentAdjustmentDetailHolder">
		<tbody>
			<logic:iterate id="adjustmentDetailVO" name="paymentAdjustmentDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<logic:equal name="paymentAdjustmentHeaderForm" property="status" value="1"><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="adjustmentDetailVO" property="encodedId"/>" name="chkSelectRow[]" value="<bean:write name="adjustmentDetailVO" property="encodedId"/>" /></logic:equal>
						<logic:equal name="paymentAdjustmentHeaderForm" property="status" value="4"><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="adjustmentDetailVO" property="encodedId"/>" name="chkSelectRow[]" value="<bean:write name="adjustmentDetailVO" property="encodedId"/>" /></logic:equal>
					</td>
					<td class="left"><a onclick="paymentAdjustmentDetailModify('<bean:write name="adjustmentDetailVO" property="encodedId"/>');"><bean:write name="adjustmentDetailVO" property="adjustmentDetailId" /></a></td>
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
	<logic:present name="paymentAdjustmentDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="12" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="paymentAdjustmentDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="paymentAdjustmentDetailHolder" property="indexStart" /> - <bean:write name="paymentAdjustmentDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listPaymentAdjustmentDetail_form', null, '<bean:write name="paymentAdjustmentDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listPaymentAdjustmentDetail_form', null, '<bean:write name="paymentAdjustmentDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listPaymentAdjustmentDetail_form', null, '<bean:write name="paymentAdjustmentDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listPaymentAdjustmentDetail_form', null, '<bean:write name="paymentAdjustmentDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="paymentAdjustmentDetailHolder" property="realPage" />/<bean:write name="paymentAdjustmentDetailHolder" property="pageCount" /></label>&nbsp;
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="paymentAdjustmentDetailHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
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
		submitForm('listPaymentAdjustmentDetail_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>