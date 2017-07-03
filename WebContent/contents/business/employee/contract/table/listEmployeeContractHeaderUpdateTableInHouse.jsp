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
			<th style="width: 8%" class="header  <%=pagedListHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('list_form', null, null, 'contractId', '<%=pagedListHolder.getNextSortOrder("contractId")%>', 'tableWrapper');"><bean:message bundle="public" key="public.contract2.id" /></a>
			</th>
			<th style="width: 8%" class="header  <%=pagedListHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('list_form', null, null, 'employeeId', '<%=pagedListHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');"><bean:message bundle="public" key="public.employee2.id" /></a>
			</th>
			<th style="width: 10%" class="header  <%=pagedListHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('list_form', null, null, 'employeeNameZH', '<%=pagedListHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');"><bean:message bundle="public" key="public.employee2.name.cn" /></a>
			</th>
			<th style="width: 8%" class="header  <%=pagedListHolder.getCurrentSortClass("contractNo")%>">
				<a onclick="submitForm('list_form', null, null, 'contractNo', '<%=pagedListHolder.getNextSortOrder("contractNo")%>', 'tableWrapper');"><bean:message bundle="public" key="public.contract2.no" /></a>
			</th>
			<th style="width: 15%" class="header  <%=pagedListHolder.getCurrentSortClass("entityId")%>">
				<a onclick="submitForm('list_form', null, null, 'entityId', '<%=pagedListHolder.getNextSortOrder("entityId")%>', 'tableWrapper');"><bean:message bundle="public" key="public.entity" /></a>
			</th>
			<th style="width: 10%" class="header  <%=pagedListHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('list_form', null, null, 'startDate', '<%=pagedListHolder.getNextSortOrder("startDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.start.date" /></a>
			</th>
			<th style="width: 10%" class="header  <%=pagedListHolder.getCurrentSortClass("endDate")%>">
				<a onclick="submitForm('list_form', null, null, 'startDate', '<%=pagedListHolder.getNextSortOrder("endDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.end.date" /></a>
			</th>
			<th style="width: 10%" class="header  <%=pagedListHolder.getCurrentSortClass("shiftId")%>">
				<a onclick="submitForm('list_form', null, null, 'shiftId', '<%=pagedListHolder.getNextSortOrder("shiftId")%>', 'tableWrapper');"><bean:message bundle="public" key="public.shift.detail" /></a>
			</th>
			<th style="width: 8%" class="header  <%=pagedListHolder.getCurrentSortClass("employStatus")%>">
				<a onclick="submitForm('list_form', null, null, 'employStatus', '<%=pagedListHolder.getNextSortOrder("employStatus")%>', 'tableWrapper');"><bean:message bundle="public" key="public.employee.status" /></a>
			</th>
			<th style="width: 8%" class="header  <%=pagedListHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('list_form', null, null, 'status', '<%=pagedListHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.contract2.status" /></a>
			</th>
			<th style="width: 8%" class="header  <%=pagedListHolder.getCurrentSortClass("tempStatus")%>">
				<a onclick="submitForm('list_form', null, null, 'tempStatus', '<%=pagedListHolder.getNextSortOrder("tempStatus")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="pagedListHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="employeeContractTempVO" name="pagedListHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><a href="#" onclick="link('employeeContractTempAction.do?proc=to_objectModify_update&id=<bean:write name="employeeContractTempVO" property="encodedId"/>');"><bean:write name="employeeContractTempVO" property="contractId" /></a></td>								
					<td class="left"> <bean:write name="employeeContractTempVO" property="employeeId"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="employeeNameZH"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="contractNo"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="decodeEntityId"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="startDate" format="yyyy-MM-dd"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="endDate" format="yyyy-MM-dd"/>  </td>
					<td class="left"> <bean:write name="employeeContractTempVO" property="decodeShiftId"/>  </td>
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
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="pagedListHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="pagedListHolder" property="indexStart" /> - <bean:write name="pagedListHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="pagedListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="pagedListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="pagedListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="pagedListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="pagedListHolder" property="realPage" />/<bean:write name="pagedListHolder" property="pageCount" /> </label>&nbsp;</td>
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