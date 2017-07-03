<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder workflowDefineStepsHolder = (PagedListHolder)request.getAttribute( "workflowDefineStepsHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 5%" class="header <%=workflowDefineStepsHolder.getCurrentSortClass("stepIndex")%>">
				<a onclick="submitForm('liststeps_form', null, null, 'stepIndex', '<%=workflowDefineStepsHolder.getNextSortOrder("stepIndex")%>', 'tableWrapper_steps');"><bean:message bundle="workflow" key="workflow.step.step.index" /></a>
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="workflow" key="workflow.step.audit.position" /> &amp; <bean:message bundle="workflow" key="workflow.step.audit.staff" />
			</th>
			<th style="width: 10%" class="header <%=workflowDefineStepsHolder.getCurrentSortClass("stepType")%>">
				<a onclick="submitForm('liststeps_form', null, null, 'stepType', '<%=workflowDefineStepsHolder.getNextSortOrder("stepType")%>', 'tableWrapper_steps');"><bean:message bundle="workflow" key="workflow.step.step.type" /></a>
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="workflow" key="workflow.step.join.type" />
			</th>
			<th style="width: 15%" class="header <%=workflowDefineStepsHolder.getCurrentSortClass("positionId")%>">
				<bean:message bundle="workflow" key="workflow.step.refer.position" /> &amp; <bean:message bundle="workflow" key="workflow.step.refer.position.grade" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="workflow" key="workflow.step.join.order.type" />
			</th>
			<th style="width: 20%" class="header-nosort">
				<bean:message bundle="workflow" key="workflow.define.msg.remind" />
			</th>						
			<th style="width: 10%" class="header <%=workflowDefineStepsHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('liststeps_form', null, null, 'status', '<%=workflowDefineStepsHolder.getNextSortOrder("status")%>', 'tableWrapper_steps');"><bean:message bundle="public" key="public.status" /></a>
			</th>							
		</tr>
	</thead>
	<logic:notEqual name="workflowDefineStepsHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="setpsVO" name="workflowDefineStepsHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="setpsVO" property="stepId"/>" name="chkSelectRow[]" value="<bean:write name="setpsVO" property="stepId"/>" />
					</td>
					<td class="left">
						<a onclick="to_objectModify_steps('<bean:write name="setpsVO" property="encodedId"/>')"><bean:write name="setpsVO" property="stepIndex"/></a>
					</td>
					<td class="left">
						<bean:write name="setpsVO" property="decodeAuditObject" />
					</td>
					<td class="left">
						<a onclick="to_objectModify_steps('<bean:write name="setpsVO" property="encodedId"/>')"><bean:write name="setpsVO" property="decodeStepType"/></a>
					</td>
					<td class="left">
						<bean:write name="setpsVO" property="decodeJoinType" />
					</td>
					<td class="left">
						<bean:write name="setpsVO" property="decodeJoinObject" />
					</td>
					<td class="left">
						<bean:write name="setpsVO" property="decodeJoinOrderType" />
					</td>	
					<td class="left">
						<bean:write name="setpsVO" property="decodeMessageRemind" />
					</td>
					<td class="left"><bean:write name="setpsVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="workflowDefineStepsHolder">
		<tfoot>
			<tr class="total">
				<td colspan="10" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="workflowDefineStepsHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="workflowDefineStepsHolder" property="indexStart" /> - <bean:write name="workflowDefineStepsHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('liststeps_form', null, '<bean:write name="workflowDefineStepsHolder" property="firstPage" />', null, null, 'tableWrapper_steps');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('liststeps_form', null, '<bean:write name="workflowDefineStepsHolder" property="previousPage" />', null, null, 'tableWrapper_steps');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('liststeps_form', null, '<bean:write name="workflowDefineStepsHolder" property="nextPage" />', null, null, 'tableWrapper_steps');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('liststeps_form', null, '<bean:write name="workflowDefineStepsHolder" property="lastPage" />', null, null, 'tableWrapper_steps');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="workflowDefineStepsHolder" property="realPage" />/<bean:write name="workflowDefineStepsHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>