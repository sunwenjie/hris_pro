<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder workflowDefineRequirementsHolder = (PagedListHolder)request.getAttribute("workflowDefineRequirementsHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 25%" class="header <%=workflowDefineRequirementsHolder.getCurrentSortClass("columnIndex")%>">
				<a onclick="submitForm('listRequirements_form', null, null, 'columnIndex', '<%=workflowDefineRequirementsHolder.getNextSortOrder("columnIndex")%>', 'tableWrapper_req');"><bean:message bundle="workflow" key="workflow.requirement.column.index" /></a>
			</th>
			<th style="width: 50%" class="header-nosort">
				<bean:message bundle="workflow" key="workflow.requirement.compare.type" />
			</th>						
			<th style="width: 25%" class="header <%=workflowDefineRequirementsHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listRequirements_form', null, null, 'status', '<%=workflowDefineRequirementsHolder.getNextSortOrder("status")%>', 'tableWrapper_req');"><bean:message bundle="public" key="public.status" /></a>
			</th>							
		</tr>
	</thead>
	<logic:notEqual name="workflowDefineRequirementsHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="requirementsVO" name="workflowDefineRequirementsHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="requirementsVO" property="requirementId"/>" name="chkSelectRow[]" value="<bean:write name="requirementsVO" property="requirementId"/>" />
					</td>
					<td class="left">
						<a onclick="to_objectModify_requirements('<bean:write name="requirementsVO" property="encodedId"/>')"><bean:write name="requirementsVO" property="columnIndex"/></a>
					</td>
					<td class="left">
						<logic:equal name="isLeaveAccessAction" value="true"><bean:message bundle="workflow" key="workflow.requirement.leave.hours" /></logic:equal>
						<logic:equal name="isOTAccessAction" value="true"><bean:message bundle="workflow" key="workflow.requirement.ot.hours" /></logic:equal>
						<bean:write name="requirementsVO" property="decodeCompareType" /> <bean:write name="requirementsVO" property="compareValue" /> 
					</td>
					<td class="left"><bean:write name="requirementsVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="workflowDefineRequirementsHolder">
		<tfoot>
			<tr class="total">
				<td colspan="4" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="workflowDefineRequirementsHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="workflowDefineRequirementsHolder" property="indexStart" /> - <bean:write name="workflowDefineRequirementsHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listRequirements_form', null, '<bean:write name="workflowDefineRequirementsHolder" property="firstPage" />', null, null, 'tableWrapper_req');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listRequirements_form', null, '<bean:write name="workflowDefineRequirementsHolder" property="previousPage" />', null, null, 'tableWrapper_req');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listRequirements_form', null, '<bean:write name="workflowDefineRequirementsHolder" property="nextPage" />', null, null, 'tableWrapper_req');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listRequirements_form', null, '<bean:write name="workflowDefineRequirementsHolder" property="lastPage" />', null, null, 'tableWrapper_req');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="workflowDefineRequirementsHolder" property="realPage" />/<bean:write name="workflowDefineRequirementsHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>