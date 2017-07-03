<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ page import=" com.kan.hro.web.actions.biz.payment.PaymentAdjustmentHeaderAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder paymentAdjustmentHeaderHolder = (PagedListHolder) request.getAttribute("paymentAdjustmentHeaderHolder");
%>

<div class="inner">
	<div id="messageWrapper">
		<logic:present name="MESSAGE_HEADER">
			<logic:present name="MESSAGE">
				<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
					<bean:write name="MESSAGE" />
	    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
				</div>
			</logic:present>
		</logic:present>
	</div>
	<!-- top 如果是台账 没有添加和过账 退回操作-->
	<div class="top">
		<kan:auth right="approve" action="HRO_PAYMENT_ADJUSTMENT_CONFIRM">
			<input type="button" class="function" name="btnApprove" id="btnApprove" value="<bean:message bundle="public" key="button.approve" />" />
		</kan:auth>
		<kan:auth right="back" action="HRO_PAYMENT_ADJUSTMENT_CONFIRM">
			<input type="button" class="delete" name="btnRollback" id="btnRollback" value="<bean:message bundle="public" key="button.return" />" />
		</kan:auth>
		<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
	</div>
	<!-- top -->
	<div id="tableWrapper">
		<table class="table hover" id="resultTable">
			<thead>
				<tr>
					<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
					<th style="width: 6%" class="header  <%=paymentAdjustmentHeaderHolder.getCurrentSortClass("adjustmentHeaderId")%>">
						<a onclick="submitForm('list_form', null, null, 'adjustmentHeaderId', '<%=paymentAdjustmentHeaderHolder.getNextSortOrder("adjustmentHeaderId")%>', 'search-results');">
							<bean:message bundle="payment" key="payment.salary.adjustment.header.id" />
						</a>
					</th>
					<th style="width: 6%" class="header  <%=paymentAdjustmentHeaderHolder.getCurrentSortClass("monthly")%>">
						<a onclick="submitForm('list_form', null, null, 'monthly', '<%=paymentAdjustmentHeaderHolder.getNextSortOrder("monthly")%>', 'search-results');">
							<bean:message bundle="payment" key="payment.salary.adjustment.header.monthly" />
						</a>
					</th> 
					<logic:equal name="role" value="1">
						<th style="width: 7%" class="header  <%=paymentAdjustmentHeaderHolder.getCurrentSortClass("clientId")%>">
							<a onclick="submitForm('list_form', null, null, 'clientId', '<%=paymentAdjustmentHeaderHolder.getNextSortOrder("clientId")%>', 'search-results');">客户ID</a>
						</th>
					</logic:equal>
					<th style="width: 7%" class="header  <%=paymentAdjustmentHeaderHolder.getCurrentSortClass("orderId")%>">
						<a onclick="submitForm('list_form', null, null, 'orderId', '<%=paymentAdjustmentHeaderHolder.getNextSortOrder("orderId")%>', 'search-results');">
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
							<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
						</a>
					</th>	 
					<th style="width: 7%" class="header  <%=paymentAdjustmentHeaderHolder.getCurrentSortClass("employeeId")%>">
						<a onclick="submitForm('list_form', null, null, 'employeeId', '<%=paymentAdjustmentHeaderHolder.getNextSortOrder("employeeId")%>', 'search-results');">
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
							<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
						</a>
					</th>	 
					<th style="width: <logic:equal name="role" value="1">5</logic:equal><logic:equal name="role" value="2">8</logic:equal>%" class="header  <%=paymentAdjustmentHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
						<a onclick="submitForm('list_form', null, null, 'employeeNameZH', '<%=paymentAdjustmentHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'search-results');">
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
							<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
						</a>
					</th>	 
					<th style="width: <logic:equal name="role" value="1">5</logic:equal><logic:equal name="role" value="2">8</logic:equal>%" class="header  <%=paymentAdjustmentHeaderHolder.getCurrentSortClass("employeeNameEN")%>">
						<a onclick="submitForm('list_form', null, null, 'employeeNameEN', '<%=paymentAdjustmentHeaderHolder.getNextSortOrder("employeeNameEN")%>', 'search-results');">
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
							<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
						</a>
					</th>	 
					<th style="width: <logic:equal name="role" value="1">7</logic:equal><logic:equal name="role" value="2">8</logic:equal>%" class="header  <%=paymentAdjustmentHeaderHolder.getCurrentSortClass("contractId")%>">
						<a onclick="submitForm('list_form', null, null, 'contractId', '<%=paymentAdjustmentHeaderHolder.getNextSortOrder("contractId")%>', 'search-results');">
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
							<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
						</a>
					</th>
					<th style="width: 6%" class="header  <%=paymentAdjustmentHeaderHolder.getCurrentSortClass("billAmountPersonal")%>">
						<a onclick="submitForm('list_form', null, null, 'billAmountPersonal', '<%=paymentAdjustmentHeaderHolder.getNextSortOrder("billAmountPersonal")%>', 'search-results');">
							<bean:message bundle="payment" key="payment.salary.adjustment.header.billAmountPersonal" />
						</a>
					</th>
					<th style="width: 6%" class="header  <%=paymentAdjustmentHeaderHolder.getCurrentSortClass("costAmountPersonal")%>">
						<a onclick="submitForm('list_form', null, null, 'costAmountPersonal', '<%=paymentAdjustmentHeaderHolder.getNextSortOrder("costAmountPersonal")%>', 'search-results');">
							<bean:message bundle="payment" key="payment.salary.adjustment.header.costAmountPersonal" />
						</a>
					</th>	 
					<th style="width: 6%" class="header  <%=paymentAdjustmentHeaderHolder.getCurrentSortClass("taxAmountPersonal")%>">
						<a onclick="submitForm('list_form', null, null, 'taxAmountPersonal', '<%=paymentAdjustmentHeaderHolder.getNextSortOrder("taxAmountPersonal")%>', 'search-results');">
							<bean:message bundle="payment" key="payment.salary.adjustment.header.taxAmountPersonal" />
						</a>
					</th>	 
					<th style="width: 6%" class="header-nosort">
						<span><bean:message bundle="payment" key="payment.salary.adjustment.header.amountAdjustment" /></span>
					</th>
					<th style="width: 8%" class="header  <%=paymentAdjustmentHeaderHolder.getCurrentSortClass("modifyBy")%>">
						<a onclick="submitForm('list_form', null, null, 'modifyBy', '<%=paymentAdjustmentHeaderHolder.getNextSortOrder("modifyBy")%>', 'search-results');"><bean:message bundle="public" key="public.modify.by" /></a>
					</th>
					<th style="width: 11%" class="header  <%=paymentAdjustmentHeaderHolder.getCurrentSortClass("modifyDate")%>">
						<a onclick="submitForm('list_form', null, null, 'modifyDate', '<%=paymentAdjustmentHeaderHolder.getNextSortOrder("modifyDate")%>', 'search-results');"><bean:message bundle="public" key="public.modify.date" /></a>
					</th>
					<th style="width: 7%" class="header  <%=paymentAdjustmentHeaderHolder.getCurrentSortClass("status")%>">
						<a onclick="submitForm('list_form', null, null, 'status', '<%=paymentAdjustmentHeaderHolder.getNextSortOrder("status")%>', 'search-results');"><bean:message bundle="public" key="public.status" /></a>
					</th>
				</tr>
			</thead>
			<logic:notEqual name="paymentAdjustmentHeaderHolder" property="holderSize" value="0">
				<tbody>
					<logic:iterate id="paymentAdjustmentHeaderVO" name="paymentAdjustmentHeaderHolder" property="source" indexId="number">
						<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
							<td>
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="paymentAdjustmentHeaderVO" property="adjustmentHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="paymentAdjustmentHeaderVO" property="encodedId"/>" />
							</td>
							<td class="left"><a href="#" onclick="link('paymentAdjustmentDetailAction.do?proc=list_object_confirm&adjustmentHeaderId=<bean:write name="paymentAdjustmentHeaderVO" property="encodedId"/>');"><bean:write name="paymentAdjustmentHeaderVO" property="adjustmentHeaderId" /></a></td>								
							<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="monthly" /></td>
							<logic:equal name="role" value="1">
								<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="clientId" /></td>
							</logic:equal>								
							<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="orderId" /></td>
							<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="employeeId" /></td>
							<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="employeeNameZH" /></td>
							<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="employeeNameEN" /></td>
							<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="contractId" /></td>
							<td class="right"><bean:write name="paymentAdjustmentHeaderVO" property="billAmountPersonal" /></td>
							<td class="right"><bean:write name="paymentAdjustmentHeaderVO" property="costAmountPersonal" /></td>
							<td class="right"><bean:write name="paymentAdjustmentHeaderVO" property="taxAmountPersonal" /></td>
							<td class="right"><bean:write name="paymentAdjustmentHeaderVO" property="amountAdjustment" /></td>
							<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="decodeModifyBy" /></td>	
							<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="decodeModifyDate" /></td>	
							<td class="left"><bean:write name="paymentAdjustmentHeaderVO" property="decodeStatus" /></td>
						</tr>
					</logic:iterate>
				</tbody>
			</logic:notEqual>
			<logic:present name="paymentAdjustmentHeaderHolder">
				<tfoot>
					<tr class="total">
						<td colspan="16" class="left">
							<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="paymentAdjustmentHeaderHolder" property="holderSize" /> </label> 
							<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="paymentAdjustmentHeaderHolder" property="indexStart" /> - <bean:write name="paymentAdjustmentHeaderHolder" property="indexEnd" /> </label> 
							<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="paymentAdjustmentHeaderHolder" property="firstPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.first" /></a></label> 
							<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="paymentAdjustmentHeaderHolder" property="previousPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.previous" /></a></label> 
							<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="paymentAdjustmentHeaderHolder" property="nextPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.next" /></a></label> 
							<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="paymentAdjustmentHeaderHolder" property="lastPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.last" /></a></label> 
							<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="paymentAdjustmentHeaderHolder" property="realPage" />/<bean:write name="paymentAdjustmentHeaderHolder" property="pageCount" /> </label>&nbsp;
							<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="paymentAdjustmentHeaderHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
						</td>
					</tr>
				</tfoot>
			</logic:present>
		</table>
	</div>
	<div class="bottom">
		<p>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
		
		<logic:present name="MESSAGE">
			messageWrapperFada();
		</logic:present>
	})(jQuery);	

	function forward() {
		var value = Number($('#forwardPage').val());
		// 如果页数无效自动跳转到第一页
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage = Number($('.forwardPage').val()) - 1;
		submitForm('list_form', null, forwardPage, null, null, 'search-results');
	}
</script>