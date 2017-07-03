<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder salaryHeaderHolder = (PagedListHolder) request.getAttribute("salaryHeaderHolder");
%>
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 8%" class="header  <%=salaryHeaderHolder.getCurrentSortClass("salaryHeaderId")%>">
				<a onclick="submitForm('list_form', null, null, 'salaryHeaderId', '<%=salaryHeaderHolder.getNextSortOrder("salaryHeaderId")%>', 'tableWrapper');">工资ID</a>
			</th>
			<th style="width: 8%" class="header  <%=salaryHeaderHolder.getCurrentSortClass("orderId")%>">
				<a onclick="submitForm('list_form', null, null, 'orderId', '<%=salaryHeaderHolder.getNextSortOrder("orderId")%>', 'tableWrapper');">订单ID</a>
			</th>
			<th style="width: 8%" class="header  <%=salaryHeaderHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('list_form', null, null, 'contractId', '<%=salaryHeaderHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">派遣协议ID</a>
			</th>
			<th style="width: 8%" class="header  <%=salaryHeaderHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('list_form', null, null, 'employeeId', '<%=salaryHeaderHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">雇员ID</a>
			</th>
			<th style="width: 12%" class="header  <%=salaryHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('list_form', null, null, 'employeeNameZH', '<%=salaryHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">雇员姓名（中文）</a>
			</th>
			<th style="width: 12%" class="header  <%=salaryHeaderHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('list_form', null, null, 'employeeNameEN', '<%=salaryHeaderHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">雇员姓名（英文）</a>
			</th>
			<th style="width: 15%" class="header  <%=salaryHeaderHolder.getCurrentSortClass("certificateNumber")%>">
				<a onclick="submitForm('list_form', null, null, 'certificateNumber', '<%=salaryHeaderHolder.getNextSortOrder("certificateNumber")%>', 'tableWrapper');">证件号码</a>
			</th>
			<th style="width: 8%" class="header  <%=salaryHeaderHolder.getCurrentSortClass("billAmountCompany")%>">
				<a onclick="submitForm('list_form', null, null, 'billAmountCompany', '<%=salaryHeaderHolder.getNextSortOrder("billAmountCompany")%>', 'tableWrapper');">合计（个人收入）</a>
			</th>
			<th style="width: 8%" class="header  <%=salaryHeaderHolder.getCurrentSortClass("costAmountPersonal")%>">
				<a onclick="submitForm('list_form', null, null, 'costAmountPersonal', '<%=salaryHeaderHolder.getNextSortOrder("costAmountPersonal")%>', 'tableWrapper');">合计（个人支出）</a>
			</th>
			<th style="width: 8%" class="header  <%=salaryHeaderHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('list_form', null, null, 'monthly', '<%=salaryHeaderHolder.getNextSortOrder("monthly")%>', 'tableWrapper');">工资月份</a>
			</th>
			<th style="width: 6%" class="header  <%=salaryHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('list_form', null, null, 'status', '<%=salaryHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
			
		</tr>
	</thead>
	<logic:notEqual name="salaryHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="salaryHeaderVO" name="salaryHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><a href="#" onclick="link('salaryAction.do?proc=to_salaryDetail&headerId=<bean:write name="salaryHeaderVO" property="encodedId"/>');"><bean:write name="salaryHeaderVO" property="salaryHeaderId" /></a></td>								
					<td class="left"> <bean:write name="salaryHeaderVO" property="orderId"/>  </td>
					<td class="left"> <bean:write name="salaryHeaderVO" property="contractId"/>  </td>
					<td class="left"> <bean:write name="salaryHeaderVO" property="employeeId"/>  </td>
					<td class="left"> <bean:write name="salaryHeaderVO" property="employeeNameZH"/>  </td>
					<td class="left"> <bean:write name="salaryHeaderVO" property="employeeNameEN"/>  </td>
					<td class="left"> <bean:write name="salaryHeaderVO" property="certificateNumber"/>  </td>
					<td class="left"> <bean:write name="salaryHeaderVO" property="billAmountCompany"/>  </td>
					<td class="left"> <bean:write name="salaryHeaderVO" property="costAmountPersonal"/>  </td>
					<td class="left"> <bean:write name="salaryHeaderVO" property="monthly"/>  </td>
					<td class="left"> <bean:write name="salaryHeaderVO" property="decodeStatus"/>  </td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="salaryHeaderHolder">
		<tfoot>
			<tr class="total">
				<td></td>
				<td colspan="12" class="right">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="salaryHeaderHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="salaryHeaderHolder" property="indexStart" /> - <bean:write name="salaryHeaderHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="salaryHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="salaryHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="salaryHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="salaryHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="salaryHeaderHolder" property="realPage" />/<bean:write name="salaryHeaderHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
	})(jQuery);	
</script>