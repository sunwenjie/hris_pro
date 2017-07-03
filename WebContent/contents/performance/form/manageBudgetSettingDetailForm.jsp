<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="budgetSettingDetailAction.do?proc=add_object" styleClass="manageBudgetSettingDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="id" class="headerId" value="<bean:write name="budgetSettingHeaderForm" property="encodedId"/>"/>
	<input type="hidden" name="detailId" class="detailId" value="<bean:write name="budgetSettingDetailForm" property="encodedId"/>"/>
	<input type="hidden" name="subAction" class="subAction" id="subAction" value="<bean:write name="budgetSettingDetailForm" property="subAction"/>" />
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="performance" key="budget.setting.parentBranchId" /><em> *</em></label> 	
				<html:select property="parentBranchId" styleClass="manageBudgetSettingDetail_parentBranchId" >
					<html:optionsCollection property="depts" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="performance" key="budget.setting.ttc" /><em> *</em></label> 
				<html:text property="ttc" maxlength="12" styleClass="manageBudgetSettingDetail_ttc" /> 
			</li>
			<li>
				<label><bean:message bundle="performance" key="budget.setting.bonus" /><em> *</em></label> 
				<html:text property="bonus" maxlength="100" styleClass="manageBudgetSettingDetail_bonus" /> 
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 	
				<html:select property="status" styleClass="manageBudgetSettingDetail_status" >
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
	</fieldset>
</html:form>