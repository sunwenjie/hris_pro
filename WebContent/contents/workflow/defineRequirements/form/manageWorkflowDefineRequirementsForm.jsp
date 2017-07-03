<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="workflowDefineRequirementsAction.do?proc=add_object" styleClass="workflowDefineRequirements_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="subAction" class="subAction" id="subAction" value="<bean:write name="workflowDefineRequirementsForm" property="subAction"/>"/>
	<input type="hidden" name="requirementId" class="requirementId" id="requirementId" value="<bean:write name="workflowDefineRequirementsForm" property="encodedId"/>"/>
	<logic:empty name="workflowDefineForm">
		<input type="hidden" name="defineId" id="defineId" value="<bean:write name="workflowDefineForm" property="encodedId"/>" />
	</logic:empty>
	<logic:notEmpty name="workflowDefineForm">
		<input type="hidden" name="defineId" id="defineId" value="<bean:write name="workflowDefineForm" property="encodedId"/>"/>
	</logic:notEmpty>
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="workflow" key="workflow.requirement.column.index" /><em> *</em><img src="images/tips.png" title="<bean:message bundle="workflow" key="workflow.requirement.column.index.tips" />" /></label> 
				<html:text property="columnIndex" maxlength="2" styleClass="manageWorkflowDefineRequirements_columnIndex" />
			</li>
			<li>
				<label id="compareColumnId">
					<logic:equal name="isLeaveAccessAction" value="true"><bean:message bundle="workflow" key="workflow.requirement.leave.hours" /></logic:equal>
					<logic:equal name="isOTAccessAction" value="true"><bean:message bundle="workflow" key="workflow.requirement.ot.hours" /></logic:equal><em>*</em>
				</label> 
				<html:select property="compareType" styleClass="manageWorkflowDefineRequirements_compareType small" >
					<html:optionsCollection property="compareTypes" value="mappingId" label="mappingValue" />
				</html:select>
				<html:text property="compareValue" maxlength="4" styleClass="manageWorkflowDefineRequirements_compareValue small" />
			</li>
		</ol>
		<ol class="auto">
			<li style="display: none;">
				<label><bean:message bundle="define" key="define.report.detail.logic.condition" /></label>
				<html:select property="combineType" styleClass="manageWorkflowDefineRequirements_combineType" >
					<html:optionsCollection property="combineTypes" value="mappingId" label="mappingValue" />
				</html:select> 
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageWorkflowDefineRequirements_status" >
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
	</fieldset>
</html:form>
<br/>
