<%@page import="com.kan.base.web.actions.workflow.WorkflowActualStepsAction"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder workflowActualStepsHolder = (PagedListHolder) request.getAttribute("workflowActualStepsHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 5%" class="header-nosort">
				<bean:message bundle="workflow" key="workflow.step.step.index" />
			</th>
			<th style="width: 20%" class="header-nosort ">
				<bean:message bundle="workflow" key="workflow.actual.name.cn" />
			</th>
			<th style="width: 20%" class="header-nosort ">
				<bean:message bundle="workflow" key="workflow.actual.name.en" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="workflow" key="workflow.step.audit.position" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="workflow" key="workflow.step.handle.time" />
			</th>
			<th style="width: 20%" class="header-nosort">
				<bean:message bundle="public" key="public.description" />
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="workflow" key="workflow.actual.audit.status" />
			</th>
		</tr>
	</thead>
	<logic:notEqual name="workflowActualStepsHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="workflowActualStepsVO" name="workflowActualStepsHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><bean:write name="workflowActualStepsVO" property="stepIndex" /></td>
					<td><bean:write name="workflowActualStepsVO" property="searchField.workflowNameZH" /></td>
					<td><bean:write name="workflowActualStepsVO" property="searchField.workflowNameEN" /></td>
					<td><bean:write name="workflowActualStepsVO" property="searchField.positionTitleZH" /></td>
					<td class="left"><bean:write name="workflowActualStepsVO" property="decodeHandleDate" /></td>
					<td class="left"><bean:write name="workflowActualStepsVO" property="description" /></td>
					<td class="left">
						<bean:write name="workflowActualStepsVO" property="decodeStatus" />
						<logic:equal name="workflowActualStepsVO" property="status"  value="2">
							<logic:equal name="workflowActualStepsVO" property="auditType" value="1">
								<logic:iterate id="positionId" name="positionIds" >
									<logic:equal name="workflowActualStepsVO" property="auditTargetId" value="${positionId}">
										<input type="hidden" name="allow_audit_stepId" id="stepId" value="<bean:write name="workflowActualStepsVO" property="encodedId"/>"/>
										£¨
											<kan:auth right="agree" action="<%=WorkflowActualStepsAction.ACCESSACTION%>"> 
												<a href="javascript:void(0);" onclick="workflowAudit(3);"><bean:message bundle="public" key="button.agree" /></a> £¬
											</kan:auth>
											<kan:auth right="refuse" action="<%=WorkflowActualStepsAction.ACCESSACTION%>">
												<a href="javascript:void(0);" onclick="workflowAudit(4);"><bean:message bundle="public" key="button.refuse" /></a>
											</kan:auth>
										 £©
									</logic:equal>
								</logic:iterate>
							</logic:equal>
							<logic:equal name="workflowActualStepsVO" property="auditType" value="4">
								<logic:equal name="workflowActualStepsVO" property="auditTargetId" value="${COOKIE_USER_JSON.userId}">
									<input type="hidden" name="allow_audit_stepId" id="stepId" value="<bean:write name="workflowActualStepsVO" property="encodedId"/>"/>
									£¨
										<kan:auth right="agree" action="<%=WorkflowActualStepsAction.ACCESSACTION%>"> 
											<a href="javascript:void(0);" onclick="workflowAudit(3);"><bean:message bundle="public" key="button.agree" /></a> £¬
										</kan:auth>
										<kan:auth right="refuse" action="<%=WorkflowActualStepsAction.ACCESSACTION%>">
											<a href="javascript:void(0);" onclick="workflowAudit(4);"><bean:message bundle="public" key="button.refuse" /></a>
										</kan:auth>
									 £©
								</logic:equal>
								<logic:equal name="workflowActualStepsVO" property="auditTargetId" value="${staffId}">
									<input type="hidden" name="allow_audit_stepId" id="stepId" value="<bean:write name="workflowActualStepsVO" property="encodedId"/>"/>
									£¨
										<kan:auth right="agree" action="<%=WorkflowActualStepsAction.ACCESSACTION%>"> 
											<a href="javascript:void(0);" onclick="workflowAudit(3);"><bean:message bundle="public" key="button.agree" /></a> £¬
										</kan:auth>
										<kan:auth right="refuse" action="<%=WorkflowActualStepsAction.ACCESSACTION%>">
											<a href="javascript:void(0);" onclick="workflowAudit(4);"><bean:message bundle="public" key="button.refuse" /></a>
										</kan:auth>
									 £©
								</logic:equal>
							</logic:equal>
						</logic:equal>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="workflowActualStepsHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="workflowActualStepsHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="workflowActualStepsHolder" property="indexStart" /> - <bean:write name="workflowActualStepsHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listworkflowModule_form', null, '<bean:write name="workflowActualStepsHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listworkflowModule_form', null, '<bean:write name="workflowActualStepsHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listworkflowModule_form', null, '<bean:write name="workflowActualStepsHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listworkflowModule_form', null, '<bean:write name="workflowActualStepsHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="workflowActualStepsHolder" property="realPage" />/<bean:write name="workflowActualStepsHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
