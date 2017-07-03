<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder cbBatchHolder = (PagedListHolder) request.getAttribute("cbBatchHolder");
	final boolean IN_HOUSE = request.getAttribute( "role" ).equals( "2" );
%>
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 7%" class="header  <%=cbBatchHolder.getCurrentSortClass("batchId")%>">
				<a onclick="submitForm('list_form', null, null, 'batchId', '<%=cbBatchHolder.getNextSortOrder("batchId")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.batch.id" /></a>
			</th>
			<th style="width: 7%" class="header  <%=cbBatchHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('list_form', null, null, 'monthly', '<%=cbBatchHolder.getNextSortOrder("monthly")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.batch.month" /></a>
			</th>
			<logic:equal name="role" value="1">
				<th style="width: 8%" class="header  <%=cbBatchHolder.getCurrentSortClass("amountSalesPrice")%>">
					<a onclick="submitForm('list_form', null, null, 'amountSalesPrice', '<%=cbBatchHolder.getNextSortOrder("amountSalesPrice")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.batch.amount.sales.price" /></a>
				</th>
			</logic:equal>	 
			<th style="width: 8%" class="header  <%=cbBatchHolder.getCurrentSortClass("amountSalesCost")%>">
				<a onclick="submitForm('list_form', null, null, 'amountSalesCost', '<%=cbBatchHolder.getNextSortOrder("amountPurchaseCost")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.batch.amount.sales.cost" /></a>
			</th>	 
			<th style="width: <%=IN_HOUSE ? "48" : "40" %>%" class="header-nosort">
				<span><bean:message bundle="public" key="public.note" /></span>
			</th>
			<th style="width: 12%" class="header  <%=cbBatchHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('list_form', null, null, 'startDate', '<%=cbBatchHolder.getNextSortOrder("startDate")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.batch.start.time" /></a>
			</th>
			<th style="width: 12%" class="header  <%=cbBatchHolder.getCurrentSortClass("endDate")%>">
				<a onclick="submitForm('list_form', null, null, 'endDate', '<%=cbBatchHolder.getNextSortOrder("endDate")%>', 'tableWrapper');"><bean:message bundle="cb" key="cb.batch.end.time" /></a>
			</th>	
			<th style="width: 6%" class="header-nosort">
				<span><bean:message bundle="public" key="public.status" /></span>
			</th>	 
		</tr>
	</thead>
	<logic:notEqual name="cbBatchHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="cbBatchVO" name="cbBatchHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td><logic:equal name="cbBatchVO" property="additionalStatus" value="3"><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="cbBatchVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="cbBatchVO" property="encodedId"/>" /></logic:equal></td>
					<td class="left"><a href="#" onclick="link('cbHeaderAction.do?proc=list_object&statusFlag=submit&batchId=<bean:write name="cbBatchVO" property="encodedId"/>');"><bean:write name="cbBatchVO" property="batchId" /></a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="link('cbHeaderAction.do?proc=list_object&statusFlag=submit&batchId=<bean:write name="cbBatchVO" property="encodedId"/>');"></a></td>								
					<td class="left"><bean:write name="cbBatchVO" property="monthly" /></td>
					<logic:equal name="role" value="1">
					<td class="right"><bean:write name="cbBatchVO" property="decodeAmountPurchaseCost" /></td>			
					</logic:equal>					
					<td class="right"><bean:write name="cbBatchVO" property="decodeAmountPurchaseCost" /></td>								
					<td class="left">
					<%
						if( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ){
					%>
						<logic:equal name="role" value="1">
							客户：<bean:write name="cbBatchVO" property="countClientId" />；
							订单：<bean:write name="cbBatchVO" property="countOrderId" />；
							派送信息：<bean:write name="cbBatchVO" property="countContractId" />；
						</logic:equal>
						<logic:equal name="role" value="2">
							结算规则：<bean:write name="cbBatchVO" property="countOrderId" />；
							商保人次：<bean:write name="cbBatchVO" property="countContractId" />；
						</logic:equal>
						<logic:greaterThan name="cbBatchVO" property="employeeListSize" value="5">
							<logic:equal name="role" value="2">员工（<bean:write name="cbBatchVO" property="employeeListSize" />人）</logic:equal><logic:equal name="role" value="1">雇员</logic:equal>：<bean:write name="cbBatchVO" property="employeeNameTop5List" />
							<img src="images/tips.png" title="<bean:write name="cbBatchVO" property="employeeNameList" />" />
						</logic:greaterThan>
						<logic:lessThan name="cbBatchVO" property="employeeListSize" value="6">
							<logic:equal name="role" value="2">员工（<bean:write name="cbBatchVO" property="employeeListSize" />人）</logic:equal><logic:equal name="role" value="1">雇员</logic:equal>：<bean:write name="cbBatchVO" property="employeeNameTop5List" />
						</logic:lessThan>
					<%
						}else{
					%>	
						<logic:equal name="role" value="1">
							Client: <bean:write name="cbBatchVO" property="countClientId" />;
							Order：<bean:write name="cbBatchVO" property="countOrderId" />;
							Contract：<bean:write name="cbBatchVO" property="countContractId" />;
						</logic:equal>
						<logic:equal name="role" value="2">
							Calculation Rule: <bean:write name="cbBatchVO" property="countOrderId" />;
							The number of Commercial Insurance: <bean:write name="cbBatchVO" property="countContractId" />;
						</logic:equal>
						<logic:greaterThan name="cbBatchVO" property="employeeListSize" value="5">
							<logic:equal name="role" value="2">
								Employee(<bean:write name="cbBatchVO" property="employeeListSize" /> people)
							</logic:equal>
							<logic:equal name="role" value="1">
								Employee
							</logic:equal>: <bean:write name="cbBatchVO" property="employeeNameTop5List" />
							<img src="images/tips.png" title="<bean:write name="cbBatchVO" property="employeeNameList" />" />
						</logic:greaterThan>
						<logic:lessThan name="cbBatchVO" property="employeeListSize" value="6">
							<logic:equal name="role" value="2">Employee(<bean:write name="cbBatchVO" property="employeeListSize" /> people)</logic:equal>
							<logic:equal name="role" value="1">Employee</logic:equal>: <bean:write name="cbBatchVO" property="employeeNameTop5List" />
						</logic:lessThan>
					<%
						}
					%>	
					</td>						
					<td class="left"><bean:write name="cbBatchVO" property="startDate" /></td>	
					<td class="left"><bean:write name="cbBatchVO" property="endDate" /></td>	
					<td class="left"><bean:write name="cbBatchVO" property="decodeAdditionalStatus" /></td>								
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="cbBatchHolder">
		<tfoot>
			<tr class="total">
				<td colspan="9" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="cbBatchHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="cbBatchHolder" property="indexStart" /> - <bean:write name="cbBatchHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="cbBatchHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="cbBatchHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="cbBatchHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="cbBatchHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="cbBatchHolder" property="realPage" />/<bean:write name="cbBatchHolder" property="pageCount" /> </label>&nbsp;
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="cbBatchHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
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