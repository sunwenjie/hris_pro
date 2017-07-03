<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ page import=" com.kan.hro.web.actions.biz.payment.PaymentAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder paymentBatchHolder = (PagedListHolder) request.getAttribute("paymentBatchHolder");
%>
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 6%" class="header  <%=paymentBatchHolder.getCurrentSortClass("batchId")%>">
				<a onclick="submitForm('list_form', null, null, 'batchId', '<%=paymentBatchHolder.getNextSortOrder("batchId")%>', 'tableWrapper');"><bean:message bundle="payment" key="payment.batch.id" /></a>
			</th>
			<th style="width: 8%" class="header  <%=paymentBatchHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('list_form', null, null, 'monthly', '<%=paymentBatchHolder.getNextSortOrder("monthly")%>', 'tableWrapper');"><bean:message bundle="payment" key="payment.batch.month" /></a>
			</th> 
			<th style="width: 8%" class="header  <%=paymentBatchHolder.getCurrentSortClass("costAmountCompany")%>">
				<a onclick="submitForm('list_form', null, null, 'costAmountCompany', '<%=paymentBatchHolder.getNextSortOrder("costAmountCompany")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="payment" key="payment.batch.cost.amount.company1" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="payment" key="payment.batch.cost.amount.company2" /></logic:equal>
				</a>
			</th>
			<th style="width: 8%" class="header  <%=paymentBatchHolder.getCurrentSortClass("billAmountPersonal")%>">
				<a onclick="submitForm('list_form', null, null, 'billAmountPersonal', '<%=paymentBatchHolder.getNextSortOrder("billAmountPersonal")%>', 'tableWrapper');"><bean:message bundle="payment" key="payment.batch.bill.amount.personal" /></a>
			</th>	 
			<th style="width: 8%" class="header  <%=paymentBatchHolder.getCurrentSortClass("costAmountPersonal")%>">
				<a onclick="submitForm('list_form', null, null, 'costAmountPersonal', '<%=paymentBatchHolder.getNextSortOrder("costAmountPersonal")%>', 'tableWrapper');"><bean:message bundle="payment" key="payment.batch.cost.amount.personal" /></a>
			</th>	 
			<th style="width: 30%" class="header-nosort">
				<span><bean:message bundle="public" key="public.note" /></span>
			</th>
			<th style="width: 12%" class="header  <%=paymentBatchHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('list_form', null, null, 'startDate', '<%=paymentBatchHolder.getNextSortOrder("startDate")%>', 'tableWrapper');"><bean:message bundle="payment" key="payment.batch.start.date" /></a>
			</th>
			<th style="width: 12%" class="header  <%=paymentBatchHolder.getCurrentSortClass("endDate")%>">
				<a onclick="submitForm('list_form', null, null, 'endDate', '<%=paymentBatchHolder.getNextSortOrder("endDate")%>', 'tableWrapper');"><bean:message bundle="payment" key="payment.batch.end.date" /></a>
			</th>
			<th style="width: 8%" class="header  <%=paymentBatchHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('list_form', null, null, 'status', '<%=paymentBatchHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="paymentBatchHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="paymentBatchVO" name="paymentBatchHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="paymentBatchVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="paymentBatchVO" property="encodedId"/>" /></td>
					<td class="left"><a href="#" onclick="link('paymentHeaderAction.do?proc=list_object&statusFlag=<bean:write name="statusFlag"/>&batchId=<bean:write name="paymentBatchVO" property="encodedId"/>');"><bean:write name="paymentBatchVO" property="batchId" /></a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="link('paymentHeaderAction.do?proc=list_object&statusFlag=<bean:write name="statusFlag"/>&batchId=<bean:write name="paymentBatchVO" property="encodedId"/>');"></a></td>								
					<td class="left"><bean:write name="paymentBatchVO" property="monthly" /></td>
					<td class="right"><bean:write name="paymentBatchVO" property="costAmountCompany" /></td>	
					<td class="right"><bean:write name="paymentBatchVO" property="billAmountPersonal" /></td>								
					<td class="right"><bean:write name="paymentBatchVO" property="costAmountPersonal" /></td>
					<td class="left">
						<%
							if( request.getLocale().getLanguage().equalsIgnoreCase( "zh") ){
						%>
							<logic:equal name="role" value="2">
								结算规则：<bean:write name="paymentBatchVO" property="countOrderIds" />；员工人次：<bean:write name="paymentBatchVO" property="countContractIds" />						
							</logic:equal>
							<logic:greaterThan name="paymentBatchVO" property="employeeListSize" value="5">
								<logic:equal name="role" value="2">员工（<bean:write name="paymentBatchVO" property="employeeListSize" />人）</logic:equal><logic:equal name="role" value="1">雇员</logic:equal>：<bean:write name="paymentBatchVO" property="employeeNameTop5List" />
								<img src="images/tips.png" title="<bean:write name="paymentBatchVO" property="employeeNameList" />" />
							</logic:greaterThan>
							<logic:lessThan name="paymentBatchVO" property="employeeListSize" value="6">
								<logic:equal name="role" value="2">员工（<bean:write name="paymentBatchVO" property="employeeListSize" />人）</logic:equal><logic:equal name="role" value="1">雇员</logic:equal>：<bean:write name="paymentBatchVO" property="employeeNameTop5List" />
							</logic:lessThan>
						<%
							}else{
						%>
							<logic:equal name="role" value="2">
								Calculation Rule: <bean:write name="paymentBatchVO" property="countOrderIds" />;
								The number of Employee: <bean:write name="paymentBatchVO" property="countContractIds" />						
							</logic:equal>
							<logic:greaterThan name="paymentBatchVO" property="employeeListSize" value="5">
								<logic:equal name="role" value="2">Employee(<bean:write name="paymentBatchVO" property="employeeListSize" /> people)</logic:equal>
								<logic:equal name="role" value="1">Employee</logic:equal>: <bean:write name="paymentBatchVO" property="employeeNameTop5List" />
								<img src="images/tips.png" title="<bean:write name="paymentBatchVO" property="employeeNameList" />" />
							</logic:greaterThan>
							<logic:lessThan name="paymentBatchVO" property="employeeListSize" value="6">
								<logic:equal name="role" value="2">Employee(<bean:write name="paymentBatchVO" property="employeeListSize" /> people)</logic:equal>
								<logic:equal name="role" value="1">Employee</logic:equal>: <bean:write name="paymentBatchVO" property="employeeNameTop5List" />
							</logic:lessThan>
						<%
							}
						%>
					</td>			
					<td class="left"><bean:write name="paymentBatchVO" property="startDate" /></td>	
					<td class="left"><bean:write name="paymentBatchVO" property="endDate" /></td>
					<td class="left"><bean:write name="paymentBatchVO" property="decodeStatus" /></td>	
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="paymentBatchHolder">
		<tfoot>
			<tr class="total">
				<td colspan="11" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="paymentBatchHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="paymentBatchHolder" property="indexStart" /> - <bean:write name="paymentBatchHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="paymentBatchHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="paymentBatchHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="paymentBatchHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="paymentBatchHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="paymentBatchHolder" property="realPage" />/<bean:write name="paymentBatchHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="paymentBatchHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
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
		submitForm('list_form', null, forwardPage, null, null, 'tableWrapper');
	}
</script>