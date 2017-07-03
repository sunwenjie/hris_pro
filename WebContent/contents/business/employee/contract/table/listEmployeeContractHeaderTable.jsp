<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder pagedListHolder = (PagedListHolder) request.getAttribute("pagedListHolder");
%>
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 8%" class="header  <%=pagedListHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('list_form', null, null, 'contractId', '<%=pagedListHolder.getNextSortOrder("contractId")%>', 'tableWrapper');"> 派送协议ID</a>
			</th>
			<th style="width: 8%" class="header  <%=pagedListHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('list_form', null, null, 'employeeId', '<%=pagedListHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">雇员ID</a>
			</th>
			<th style="width: 10%" class="header  <%=pagedListHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('list_form', null, null, 'employeeNameZH', '<%=pagedListHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">雇员姓名（中文）</a>
			</th>
			<th style="width: 10%" class="header  <%=pagedListHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('list_form', null, null, 'employeeNameEN', '<%=pagedListHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">雇员姓名（英文）</a>
			</th>
			<th style="width: 8%" class="header  <%=pagedListHolder.getCurrentSortClass("contractNo")%>">
				<a onclick="submitForm('list_form', null, null, 'contractNo', '<%=pagedListHolder.getNextSortOrder("contractNo")%>', 'tableWrapper');">编号</a>
			</th>
			<th style="width: 15%" class="header  <%=pagedListHolder.getCurrentSortClass("contractNo")%>">
				<a onclick="submitForm('list_form', null, null, 'contractNo', '<%=pagedListHolder.getNextSortOrder("contractNo")%>', 'tableWrapper');">派送协议名称</a>
			</th>
			<th style="width: 10%" class="header  <%=pagedListHolder.getCurrentSortClass("orderId")%>">
				<a onclick="submitForm('list_form', null, null, 'orderId', '<%=pagedListHolder.getNextSortOrder("orderId")%>', 'tableWrapper');">订单ID</a>
			</th>
			<th style="width: 10%" class="header  <%=pagedListHolder.getCurrentSortClass("clientId")%>">
				<a onclick="submitForm('list_form', null, null, 'clientId', '<%=pagedListHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">客户Id</a>
			</th>
			<th style="width: 8%" class="header  <%=pagedListHolder.getCurrentSortClass("employStatus")%>">
				<a onclick="submitForm('list_form', null, null, 'employStatus', '<%=pagedListHolder.getNextSortOrder("employStatus")%>', 'tableWrapper');">雇佣状态</a>
			</th>
			<th style="width: 8%" class="header  <%=pagedListHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('list_form', null, null, 'status', '<%=pagedListHolder.getNextSortOrder("status")%>', 'tableWrapper');">协议状态</a>
			</th>
			<th style="width: 8%" class="header  <%=pagedListHolder.getCurrentSortClass("tempStatus")%>">
				<a onclick="submitForm('list_form', null, null, 'tempStatus', '<%=pagedListHolder.getNextSortOrder("tempStatus")%>', 'tableWrapper');">状态</a>
			</th>
			
		</tr>
	</thead>
	<logic:notEqual name="pagedListHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="employeeContractTempVO" name="pagedListHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal value="1" name="employeeContractTempVO" property="tempStatus">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="employeeContractTempVO" property="encodedId"/>" name="chkSelectRow[]" value="<bean:write name="employeeContractTempVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left"><a href="#" onclick="link('employeeContractTempAction.do?proc=to_objectModify&id=<bean:write name="employeeContractTempVO" property="encodedId"/>');"><bean:write name="employeeContractTempVO" property="contractId" /></a></td>								
					<td class="left"> <bean:write name="employeeContractTempVO" property="employeeId"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="employeeNameZH"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="employeeNameEN"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="contractNo"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="nameZH"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="orderId"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="clientId"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="decodeEmployStatus"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="decodeStatus"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="decodeTempStatus"/>  </td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="pagedListHolder">
		<tfoot>
			<tr class="total">
				<td colspan="12" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="pagedListHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="pagedListHolder" property="indexStart" /> - <bean:write name="pagedListHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="pagedListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="pagedListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="pagedListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="pagedListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="pagedListHolder" property="realPage" />/<bean:write name="pagedListHolder" property="pageCount" /> </label>&nbsp;</td>
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