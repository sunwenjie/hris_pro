<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder timesheetHeaderHolder = ( PagedListHolder ) request.getAttribute( "timesheetHeaderHolder" );
	final boolean IN_HOUSE = request.getAttribute( "role" ).equals( KANConstants.ROLE_IN_HOUSE );
	final boolean isHRFunction = BaseAction.isHRFunction( request, null );
	request.setAttribute( "isHRFunction", isHRFunction ? "1" : "2" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 5%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("headerId")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'headerId', '<%=timesheetHeaderHolder.getNextSortOrder("headerId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ts.id" /></a>
			</th>
			<th style="width: 5%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'monthly', '<%=timesheetHeaderHolder.getNextSortOrder("monthly")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ts.month" /></a>
			</th>
			<th style="width: 8%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'startDate', '<%=timesheetHeaderHolder.getNextSortOrder("startDate")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ts.ts.start" /></a>
			</th>
			<th style="width: 8%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("endDate")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'endDate', '<%=timesheetHeaderHolder.getNextSortOrder("endDate")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ts.ts.end" /></a>
			</th>
			<th style="width: <%=IN_HOUSE ? "8" : "5" %>%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'employeeId', '<%=timesheetHeaderHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=IN_HOUSE ? "8" : "5" %>%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'employeeNameZH', '<%=timesheetHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=IN_HOUSE ? "8" : "5" %>%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'employeeNameEN', '<%=timesheetHeaderHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</a>
			</th>
			<th style="width: 5%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("totalFullHours")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'totalFullHours', '<%=timesheetHeaderHolder.getNextSortOrder("totalFullHours")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ts.toal.full.hours" /></a>
			</th>
			<th style="width: 5%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("totalWorkHours")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'totalWorkHours', '<%=timesheetHeaderHolder.getNextSortOrder("totalWorkHours")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ts.work.hours" /></a>  
			</th>
			<th style="width: <%=IN_HOUSE ? "8" : "5" %>%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'contractId', '<%=timesheetHeaderHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: 8%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("shiftId")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'shiftId', '<%=timesheetHeaderHolder.getNextSortOrder("shiftId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.client.order.shift" /></a>
			</th>
			
			<logic:equal name="role" value="1">
				<th style="width: 5%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("clientId")%>">
					<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'clientId', '<%=timesheetHeaderHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">客户ID</a>
				</th>
				<th style="width: 10%" class="header-nosort">
					客户名称
				</th>
			</logic:equal>
			
			<th style="width: 8%" class="header <%=timesheetHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchTimesheetHeader_form', null, null, 'status', '<%=timesheetHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			<th style="width: <%=IN_HOUSE ? "8" : "5" %>%" class="header-nosort">
				<bean:message bundle="public" key="public.modify.by" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="public" key="public.modify.date" />
			</th>
		</tr>
	</thead>
	<logic:notEqual name="timesheetHeaderHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="timesheetHeaderVO" name="timesheetHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="timesheetHeaderVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="timesheetHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="timesheetHeaderVO" property="encodedId"/>" />
						</logic:equal>	
						<logic:equal name="timesheetHeaderVO" property="status" value="4">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="timesheetHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="timesheetHeaderVO" property="encodedId"/>" />
						</logic:equal>	
					</td>
					<td class="left">
						<a onclick="link('timesheetHeaderAction.do?proc=to_objectModify&id=<bean:write name="timesheetHeaderVO" property="encodedId"/>&batchId=<bean:write name="timesheetHeaderVO" property="encodedBatchId"/>');">
							<bean:write name="timesheetHeaderVO" property="headerId" />
						</a>
					</td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="monthly" /></td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="startDate" /></td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="endDate" /></td>
					<td class="left">
						<a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="timesheetHeaderVO" property="encodedEmployeeId" />');"><bean:write name="timesheetHeaderVO" property="employeeId" /></a>
					</td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="employeeNameEN" /></td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="totalFullHours" /></td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="totalWorkHours" /></td>
					<td class="left">
						<a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="timesheetHeaderVO" property="encodedContractId" />');"><bean:write name="timesheetHeaderVO" property="contractId" /></a>
					</td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="decodeShiftId" /></td>
					<logic:equal name="role" value="1">
						<td class="left">
							<a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="timesheetHeaderVO" property="encodedClientId" />');"><bean:write name="timesheetHeaderVO" property="clientId" /></a>
						</td>
						<td class="left"><bean:write name="timesheetHeaderVO" property="clientName" /></td>
					</logic:equal>
					<td class="left">
						<bean:write name="timesheetHeaderVO" property="decodeStatus" />
						<logic:equal name="timesheetHeaderVO" property="status" value="2" >
							<logic:equal name="isHRFunction" value="1" >
								&nbsp;&nbsp;<img src='images/magnifer.png' title="<bean:message bundle="public" key="img.title.tips.view.detials" />"  onclick=popupWorkflow('<bean:write name="timesheetHeaderVO" property="workflowId"/>'); />
							</logic:equal>
						</logic:equal>
					</td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="decodeModifyBy" /></td>
					<td class="left"><bean:write name="timesheetHeaderVO" property="decodeModifyDate" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="timesheetHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan=<%=IN_HOUSE ? "15" : "17" %> class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="timesheetHeaderHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="timesheetHeaderHolder" property="indexStart" /> - <bean:write name="timesheetHeaderHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="timesheetHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="timesheetHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="timesheetHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="timesheetHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="timesheetHeaderHolder" property="realPage" />/<bean:write name="timesheetHeaderHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>