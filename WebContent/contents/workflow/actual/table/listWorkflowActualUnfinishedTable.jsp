<%@page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder workflowActualHolder = (PagedListHolder) request.getAttribute("workflowActualHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th class="header <%=workflowActualHolder.getCurrentSortClass("workflowId")%>">
				<a onclick="submitForm('listworkflowActual_form', null, null, 'workflowId', '<%=workflowActualHolder.getNextSortOrder("workflowId")%>', 'tableWrapper');"><bean:message bundle="workflow" key="workflow.actual.id" /></a>
			</th>
			<th class="header <%=workflowActualHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listworkflowActual_form', null, null, 'nameZH', '<%=workflowActualHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="workflow" key="workflow.actual.name.cn" /></a>
			</th>
			<th class="header-nosort">
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
				<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.id" /></logic:notEqual>
			</th>
			<th class="header-nosort">
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name" /></logic:equal>
				<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.name" /></logic:notEqual>
			</th>
			<th class="header-nosort">
				<bean:message bundle="security" key="security.entity" />
			</th>
			<th class="header-nosort">
				<logic:equal name="role" value="1"><bean:message bundle="workflow" key="workflow.actual.bill.branch" /></logic:equal>
				<logic:notEqual name="role" value="1"><bean:message bundle="workflow" key="workflow.actual.cost.branch" /></logic:notEqual>
			</th>
			<th class="header-nosort">
				<bean:message bundle="workflow" key="workflow.actual.department" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="workflow" key="workflow.actual.position" />
			</th>
			<th class="header-nosort" style="display: none;">
				<bean:message bundle="workflow" key="workflow.actual.id.no" />
			</th>
			<th class="header-nosort">
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
				<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.contract2.id" /></logic:notEqual>
			</th>
			<th class="header-nosort">
				<bean:message bundle="sb" key="sb.solution" />
			</th>
			<th class="header-nosort">
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.start.date" /></logic:equal>
				<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.contract2.start.date" /></logic:notEqual>
			</th>
			<th class="header-nosort">
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.end.date" /></logic:equal>
				<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.contract2.end.date" /></logic:notEqual>
			</th>
			<logic:equal name="role" value="1">
				<th class="header-nosort">
					¿Í»§Ãû³Æ
				</th>
			</logic:equal>
			<th class="header-nosort">
				<bean:message bundle="workflow" key="workflow.actual.owner" />
			</th>
			<th class="header <%=workflowActualHolder.getCurrentSortClass("createBy")%>">
				<a onclick="submitForm('listworkflowActual_form', null, null, 'createBy', '<%=workflowActualHolder.getNextSortOrder("createBy")%>', 'tableWrapper');"><bean:message bundle="workflow" key="workflow.actual.report.people" /></a>
			</th>
			<th class="header <%=workflowActualHolder.getCurrentSortClass("createDate")%>">
				<a onclick="submitForm('listworkflowActual_form', null, null, 'createDate', '<%=workflowActualHolder.getNextSortOrder("createDate")%>', 'tableWrapper');"><bean:message bundle="workflow" key="workflow.actual.report.time" /></a>
			</th>
			<th class="header <%=workflowActualHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listworkflowActual_form', null, null, 'status', '<%=workflowActualHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
		<tbody>
			<logic:iterate id="workflowActualVO" name="workflowActualHolder" property="source" indexId="number">
				<bean:define id="colorstatus" name="workflowActualVO" property="actualStepStatus" ></bean:define>
				<%
					if ("3".equals(colorstatus) || "4".equals(colorstatus) || "5".equals(colorstatus) || "6".equals(colorstatus)){
				%>
					<tr class='odd'>
				<%
					}else{
			    %>
					<tr class='even'>
				<%
					}
				%>
					<td>
						<logic:equal name="workflowActualVO" property="actualStepStatus" value="2" >
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="workflowActualVO" property="workflowId"/>" name="chkSelectRow[]" value="<bean:write name="workflowActualVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('workflowActualStepsAction.do?proc=list_object&workflowId=<bean:write name="workflowActualVO" property="encodedId"/>');"><bean:write name="workflowActualVO" property="workflowId" /></a>
					</td>
					<td><a onclick="link('workflowActualStepsAction.do?proc=list_object&workflowId=<bean:write name="workflowActualVO" property="encodedId"/>');"><bean:write name="workflowActualVO" property="nameZH" /></a></td>
					<td class="left"><bean:write name="workflowActualVO" property="employeeId" /></td> 
					<td class="left"><bean:write name="workflowActualVO" property="employeeName" /></td> 
						<td class="left"><bean:write name="workflowActualVO" property="decodeEntityId" /></td> 
					<td class="left"><bean:write name="workflowActualVO" property="decodeSettlementBranch" /></td>
					<td class="left"><bean:write name="workflowActualVO" property="decodeDepartment" /></td>
					<td class="left"><bean:write name="workflowActualVO" property="decodePositionId" /></td>
					<td class="left" style="display: none;"><bean:write name="workflowActualVO" property="certificateNumber" /></td>
					<td class="left"><bean:write name="workflowActualVO" property="contractId" /></td>
					<td class="left"><bean:write name="workflowActualVO" property="decodeSbSolutionIds" /></td>
					<td class="left">
							<bean:write name="workflowActualVO" property="contractStartDate" />
					</td>
					<td class="left">
							<bean:write name="workflowActualVO" property="contractEndDate" />
					</td>
					<logic:equal name="role" value="1">
						<td class="left"><bean:write name="workflowActualVO" property="clientName" /></td>
					</logic:equal>
					<td class="left"><bean:write name="workflowActualVO" property="decodeOwner" /></td> 
					<td class="left"><bean:write name="workflowActualVO" property="decodeCreateBy" /></td>
					<td class="left"><bean:write name="workflowActualVO" property="decodeCreateDate" /></td>
					<td class="left"><bean:write name="workflowActualVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	<logic:present name="workflowActualHolder">
		<tfoot>
			<tr class="total">
				<td colspan="18" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="workflowActualHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="workflowActualHolder" property="indexStart" /> - <bean:write name="workflowActualHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listworkflowActual_form', null, '<bean:write name="workflowActualHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listworkflowActual_form', null, '<bean:write name="workflowActualHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listworkflowActual_form', null, '<bean:write name="workflowActualHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listworkflowActual_form', null, '<bean:write name="workflowActualHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="workflowActualHolder" property="realPage" />/<bean:write name="workflowActualHolder" 	property="pageCount" /></label>&nbsp;
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />£º<input type="text" id="forwardPage_render" class="forwardPage_render" style="width:23px;" value="<bean:write name='workflowActualHolder' property='realPage' />" onkeydown="if(event.keyCode == 13){pageForward();}" />Ò³</label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>