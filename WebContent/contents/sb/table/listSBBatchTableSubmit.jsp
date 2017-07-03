<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder sbBatchHolder = (PagedListHolder) request.getAttribute("sbBatchHolder");
%>
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 7%" class="header  <%=sbBatchHolder.getCurrentSortClass("batchId")%>">
				<a onclick="submitForm('list_form', null, null, 'batchId', '<%=sbBatchHolder.getNextSortOrder("batchId")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.batch.id" /></a>
			</th>
			<th style="width: 7%" class="header  <%=sbBatchHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('list_form', null, null, 'monthly', '<%=sbBatchHolder.getNextSortOrder("monthly")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.monthly" /></a>
			</th>
			<th style="width: 6%" class="header  <%=sbBatchHolder.getCurrentSortClass("sbType")%>">
				<a onclick="submitForm('list_form', null, null, 'sbType', '<%=sbBatchHolder.getNextSortOrder("sbType")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.type" /></a>
			</th>
			<th style="width: 8%" class="header  <%=sbBatchHolder.getCurrentSortClass("amountCompany")%>">
				<a onclick="submitForm('list_form', null, null, 'amountCompany', '<%=sbBatchHolder.getNextSortOrder("amountCompany")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.company" /></a>
			</th>
			<th style="width: 8%" class="header  <%=sbBatchHolder.getCurrentSortClass("amountPersonal")%>">
				<a onclick="submitForm('list_form', null, null, 'amountPersonal', '<%=sbBatchHolder.getNextSortOrder("amountPersonal")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.personal" /></a>
			</th>	 
			<th style="width: 40%" class="header-nosort">
				<span><bean:message bundle="public" key="public.note" /></span>
			</th>
			<th style="width: 9%" class="header  <%=sbBatchHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('list_form', null, null, 'startDate', '<%=sbBatchHolder.getNextSortOrder("startDate")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.oper.start.time" /></a>
			</th>
			<th style="width: 9%" class="header  <%=sbBatchHolder.getCurrentSortClass("endDate")%>">
				<a onclick="submitForm('list_form', null, null, 'endDate', '<%=sbBatchHolder.getNextSortOrder("endDate")%>', 'tableWrapper');"><bean:message bundle="sb" key="sb.oper.end.time" /></a>
			</th>
			<th style="width: 6%" class="header-nosort">
				<span>状态</span>
			</th>	 
		</tr>
	</thead>
	<logic:notEqual name="sbBatchHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="sbBatchVO" name="sbBatchHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="sbBatchVO" property="additionalStatus" value="3">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="sbBatchVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="sbBatchVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left"><a href="#" onclick="link('sbHeaderAction.do?proc=list_object&statusFlag=submit&batchId=<bean:write name="sbBatchVO" property="encodedId"/>');"><bean:write name="sbBatchVO" property="batchId" /></a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="link('sbHeaderAction.do?proc=list_object&statusFlag=submit&batchId=<bean:write name="sbBatchVO" property="encodedId"/>');"></a></td>								
					<td class="left"><bean:write name="sbBatchVO" property="monthly" /></td>
					<td class="left"><bean:write name="sbBatchVO" property="decodeSBType" /></td>
					<td class="right"><bean:write name="sbBatchVO" property="decodeAmountCompany" /></td>								
					<td class="right"><bean:write name="sbBatchVO" property="decodeAmountPersonal" /></td>
					<td class="left">	
						<%
							if( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ){
						%>							
							<logic:equal name="role" value="1">
								客户：<bean:write name="sbBatchVO" property="countClientId" />；订单：<bean:write name="sbBatchVO" property="countOrderId" />；
							</logic:equal>
							<logic:equal name="role" value="2">
								结算规则：<bean:write name="sbBatchVO" property="countOrderId" />；社保公积金人次：<bean:write name="sbBatchVO" property="countContractId" />；
							</logic:equal>
							<logic:greaterThan name="sbBatchVO" property="employeeListSize" value="5">
								<logic:equal name="role" value="2">员工</logic:equal><logic:equal name="role" value="1">雇员</logic:equal>
								（<bean:write name="sbBatchVO" property="countEmployeeId" /> 人）：<bean:write name="sbBatchVO" property="employeeNameTop5List" />
								<img src="images/tips.png" title="<bean:write name="sbBatchVO" property="employeeNameList" />" />
							</logic:greaterThan>
							<logic:lessThan name="sbBatchVO" property="employeeListSize" value="6">
								<logic:equal name="role" value="2">员工</logic:equal><logic:equal name="role" value="1">雇员</logic:equal>
								（<bean:write name="sbBatchVO" property="countEmployeeId" /> 人）：<bean:write name="sbBatchVO" property="employeeNameList" />
							</logic:lessThan>
						<%
							}else{
						%>
							<logic:equal name="role" value="1">
								Client： <bean:write name="sbBatchVO" property="countClientId" />;
								Order：<bean:write name="sbBatchVO" property="countOrderId" />;
							</logic:equal>
							<logic:equal name="role" value="2">
								Calculation Rules: <bean:write name="sbBatchVO" property="countOrderId" />;
								The number of Social Benefits: <bean:write name="sbBatchVO" property="countContractId" />;
							</logic:equal>
							<logic:greaterThan name="sbBatchVO" property="employeeListSize" value="5">
								Employee(<bean:write name="sbBatchVO" property="countEmployeeId" /> people): 
								<bean:write name="sbBatchVO" property="employeeNameTop5List" />
								<img src="images/tips.png" title="<bean:write name="sbBatchVO" property="employeeNameList" />" />
							</logic:greaterThan>
							<logic:lessThan name="sbBatchVO" property="employeeListSize" value="6">
								Employee(<bean:write name="sbBatchVO" property="countEmployeeId" /> people): 
								<bean:write name="sbBatchVO" property="employeeNameList" />
							</logic:lessThan>
						<%
							}
						%>
					</td>
					<td class="left"><bean:write name="sbBatchVO" property="startDate" /></td>	
					<td class="left"><bean:write name="sbBatchVO" property="endDate" /></td>							
					<td class="left"><bean:write name="sbBatchVO" property="decodeAdditionalStatus" /></td>						
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="sbBatchHolder">
		<tfoot>
			<tr class="total">
				<td colspan="10" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="sbBatchHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="sbBatchHolder" property="indexStart" /> - <bean:write name="sbBatchHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="sbBatchHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="sbBatchHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="sbBatchHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="sbBatchHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="sbBatchHolder" property="realPage" />/<bean:write name="sbBatchHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="sbBatchHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
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