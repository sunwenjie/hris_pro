<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder sbHeaderHolder = (PagedListHolder) request.getAttribute("sbHeaderHolder");
%>

<div id="tableWrapper">	
	<table class="table hover" id="resultTable">
		<thead>
			<tr>
				<th style="width: 8%" class="header  <%=sbHeaderHolder.getCurrentSortClass("vendorId")%>">
					<a onclick="submitForm('list_form', null, null, 'vendorId', '<%=sbHeaderHolder.getNextSortOrder("vendorId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.vendor.id" /></a>
				</th>
				<th style="width: 16%" class="header-nosort">
					<span><bean:message bundle="business" key="business.vendor.payment.vendor.name" /></span>
				</th>
				<th style="width: 8%" class="header  <%=sbHeaderHolder.getCurrentSortClass("monthly")%>">
					<a onclick="submitForm('list_form', null, null, 'monthly', '<%=sbHeaderHolder.getNextSortOrder("monthly")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.sb.monthly" /></a>
				</th>
				<th style="width: 10%" class="header  <%=sbHeaderHolder.getCurrentSortClass("amountCompany")%>">
					<a onclick="submitForm('list_form', null, null, 'amountCompany', '<%=sbHeaderHolder.getNextSortOrder("amountCompany")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.sb.company" /></a>
				</th>
				<th style="width: 10%" class="header  <%=sbHeaderHolder.getCurrentSortClass("amountPersonal")%>">
					<a onclick="submitForm('list_form', null, null, 'amountPersonal', '<%=sbHeaderHolder.getNextSortOrder("amountPersonal")%>', 'tableWrapper');"><bean:message bundle="business" key="business.vendor.payment.sb.personal" /></a>
				</th>	  
				<th style="width: 40%" class="header-nosort">
					<span><bean:message bundle="public" key="public.note" /></span>
				</th>
				<th style="width: 8%" class="header  <%=sbHeaderHolder.getCurrentSortClass("additionalStatus")%>">
					<a onclick="submitForm('list_form', null, null, 'additionalStatus', '<%=sbHeaderHolder.getNextSortOrder("additionalStatus")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
				</th>
			</tr>
		</thead>
		<logic:notEqual name="sbHeaderHolder" property="holderSize" value="0">
			<tbody>
				<logic:iterate id="sbHeaderVO" name="sbHeaderHolder" property="source" indexId="number">
					<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
						<td class="left"><a onclick="link('vendorPaymentAction.do?proc=to_vendorDetail&vendorId=<bean:write name="sbHeaderVO" property="encodedVendorId"/>&monthly=<bean:write name="sbHeaderVO" property="monthly"/>');" title="点击查看当前供应商明细" ><bean:write name="sbHeaderVO" property="vendorId" /></a></td>								
						<td class="left"><bean:write name="sbHeaderVO" property="vendorName" /></td>
						<td class="left"><bean:write name="sbHeaderVO" property="monthly" /></td>
						<td class="right"><bean:write name="sbHeaderVO" property="decodeAmountCompany" /></td>								
						<td class="right"><bean:write name="sbHeaderVO" property="decodeAmountPersonal" /></td>								
						<td class="left">
							<logic:equal name="role" value="1">
								客户：<bean:write name="sbHeaderVO" property="countClientId" />；订单：<bean:write name="sbHeaderVO" property="countOrderId" />；服务协议：<bean:write name="sbHeaderVO" property="countContractId" />；社保公积金方案：<bean:write name="sbHeaderVO" property="countEmployeeSBId" />；科目：<bean:write name="sbHeaderVO" property="countItemId" />
								<logic:greaterThan name="sbHeaderVO" property="employeeListSize" value="3">
									&nbsp;&nbsp;&nbsp;包含：<bean:write name="sbHeaderVO" property="employeeNameTop3List" />
									<img src="images/tips.png" title="<bean:write name="sbHeaderVO" property="employeeNameList" />" />
								</logic:greaterThan>
								<logic:lessThan name="sbHeaderVO" property="employeeListSize" value="4">
									&nbsp;&nbsp;&nbsp;包含：<bean:write name="sbHeaderVO" property="employeeNameTop3List" />
								</logic:lessThan>
							</logic:equal>
							<logic:equal name="role" value="2">
								结算规则：<bean:write name="sbHeaderVO" property="countOrderId" />；劳动合同：<bean:write name="sbHeaderVO" property="countContractId" />；社保公积金方案：<bean:write name="sbHeaderVO" property="countEmployeeSBId" />；科目：<bean:write name="sbHeaderVO" property="countItemId" />
								<logic:greaterThan name="sbHeaderVO" property="employeeListSize" value="3">
									&nbsp;&nbsp;&nbsp;包含：<bean:write name="sbHeaderVO" property="employeeNameTop3List" />
									<img src="images/tips.png" title="<bean:write name="sbHeaderVO" property="employeeNameList" />" />
								</logic:greaterThan>
								<logic:lessThan name="sbHeaderVO" property="employeeListSize" value="4">
									&nbsp;&nbsp;&nbsp;包含：<bean:write name="sbHeaderVO" property="employeeNameTop3List" />
								</logic:lessThan>
							</logic:equal>
						</td>
						<td class="left"><bean:write name="sbHeaderVO" property="decodeAdditionalStatus" /></td>								
					</tr>
				</logic:iterate>
			</tbody>
		</logic:notEqual>
		<logic:present name="sbHeaderHolder">
			<tfoot>
				<tr class="total">
					<td colspan="9" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="sbHeaderHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="sbHeaderHolder" property="indexStart" /> - <bean:write name="sbHeaderHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="sbHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="sbHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="sbHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="sbHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="sbHeaderHolder" property="realPage" />/<bean:write name="sbHeaderHolder" property="pageCount" /> </label>&nbsp;</td>
				</tr>
			</tfoot>
		</logic:present>
	</table>
</div>
						
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
	})(jQuery);	
</script>