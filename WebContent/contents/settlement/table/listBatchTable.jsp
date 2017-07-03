<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder batchHolder = (PagedListHolder) request.getAttribute("batchHolder");
%>
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 7%" class="header  <%=batchHolder.getCurrentSortClass("batchId")%>">
				<a onclick="submitForm('list_form', null, null, 'batchId', '<%=batchHolder.getNextSortOrder("batchId")%>', 'tableWrapper');">批次ID</a>
			</th>
			<th style="width: 7%" class="header  <%=batchHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('list_form', null, null, 'monthly', '<%=batchHolder.getNextSortOrder("monthly")%>', 'tableWrapper');">结算月份</a>
			</th> 
			<th style="width: 8%" class="header  <%=batchHolder.getCurrentSortClass("billAmountCompany")%>">
				<a onclick="submitForm('list_form', null, null, 'billAmountCompany', '<%=batchHolder.getNextSortOrder("billAmountCompany")%>', 'tableWrapper');">公司营收</a>
			</th>
			<th style="width: 8%" class="header  <%=batchHolder.getCurrentSortClass("costAmountCompany")%>">
				<a onclick="submitForm('list_form', null, null, 'costAmountCompany', '<%=batchHolder.getNextSortOrder("costAmountCompany")%>', 'tableWrapper');">公司人工成本</a>
			</th>	 	 
			<th style="width: 8%" class="header  <%=batchHolder.getCurrentSortClass("billAmountPersonal")%>">
				<a onclick="submitForm('list_form', null, null, 'billAmountPersonal', '<%=batchHolder.getNextSortOrder("billAmountPersonal")%>', 'tableWrapper');">应发合计</a>
			</th>	 
			<th style="width: 8%" class="header  <%=batchHolder.getCurrentSortClass("costAmountPersonal")%>">
				<a onclick="submitForm('list_form', null, null, 'costAmountPersonal', '<%=batchHolder.getNextSortOrder("costAmountPersonal")%>', 'tableWrapper');">个人扣款 </a>
			</th>	 
			<th style="width: 30%" class="header-nosort">
				<span>备注</span>
			</th>
			<th style="width: 12%" class="header  <%=batchHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('list_form', null, null, 'startDate', '<%=batchHolder.getNextSortOrder("startDate")%>', 'tableWrapper');">结算开始时间</a>
			</th>
			<th style="width: 12%" class="header  <%=batchHolder.getCurrentSortClass("endDate")%>">
				<a onclick="submitForm('list_form', null, null, 'endDate', '<%=batchHolder.getNextSortOrder("endDate")%>', 'tableWrapper');">结算结束时间</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="batchHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="batchVO" name="batchHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="batchVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="batchVO" property="encodedId"/>" /></td>
					<td class="left"><a href="#" onclick="link('settlementHeaderAction.do?proc=list_object&batchId=<bean:write name="batchVO" property="encodedId"/>');"><bean:write name="batchVO" property="batchId" /></a></td>								
					<td class="left"><bean:write name="batchVO" property="monthly" /></td>
					<td class="right"><bean:write name="batchVO" property="billAmountCompany" /></td>		
					<td class="right"><bean:write name="batchVO" property="costAmountCompany" /></td>							
					<td class="right"><bean:write name="batchVO" property="billAmountPersonal" /></td>								
					<td class="right"><bean:write name="batchVO" property="costAmountPersonal" /></td>								
					<td class="left">
						客户：<bean:write name="batchVO" property="countClientId" />；订单：<bean:write name="batchVO" property="countOrderId" />；雇员人次：<bean:write name="batchVO" property="countContractId" />；
						<logic:greaterThan name="batchVO" property="employeeListSize" value="5">
							<logic:equal name="role" value="2">员工</logic:equal><logic:equal name="role" value="1">雇员</logic:equal>
							（<bean:write name="batchVO" property="countEmployeeId" /> 人）：<bean:write name="batchVO" property="employeeNameTop5List" />
							<img src="images/tips.png" title="<bean:write name="batchVO" property="employeeNameList" />" />
						</logic:greaterThan>
						<logic:lessThan name="batchVO" property="employeeListSize" value="6">
							<logic:equal name="role" value="2">员工</logic:equal><logic:equal name="role" value="1">雇员</logic:equal>
							（<bean:write name="batchVO" property="countEmployeeId" /> 人）：<bean:write name="batchVO" property="employeeNameList" />
						</logic:lessThan>	
					</td>							
					<td class="left"><bean:write name="batchVO" property="startDate" /></td>	
					<td class="left"><bean:write name="batchVO" property="endDate" /></td>	
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="batchHolder">
		<tfoot>
			<tr class="total">
				<td colspan="10" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="batchHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="batchHolder" property="indexStart" /> - <bean:write name="batchHolder" property="indexEnd" /> </label> 
					<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="batchHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="batchHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="batchHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="batchHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="batchHolder" property="realPage" />/<bean:write name="batchHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;跳转至：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="batchHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" />页</label>&nbsp;
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