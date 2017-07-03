<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="annualLeaveRuleDetailAction.do?proc=add_object" styleClass="manageAnnualLeaveRuleDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="annualLeaveRuleHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="detailId" name="detailId" value="<bean:write name="annualLeaveRuleDetailForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="annualLeaveRuleDetailForm" property="subAction"/>" /> 
	<fieldset>
 		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">		
			<li>
				<label><bean:message bundle="security" key="security.position.grade" /></label>
				<html:select property="positionGradeId" styleClass="manageAnnualLeaveRuleDetail_positionGradeId">
					<html:optionsCollection property="positionGradeIds" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="management" key="management.annual.leave.rule.detail.seniority" /></label> 
				<html:select property="seniority" styleClass="manageAnnualLeaveRuleDetail_seniority">
					<html:optionsCollection property="seniorities" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>	
		<ol class="auto">
			<li>
				<label><bean:message bundle="management" key="management.annual.leave.rule.detail.legal.hours" /><em> *</em></label> 
				<html:text property="legalHours" maxlength="10" styleClass="manageAnnualLeaveRuleDetail_legalHours" /> 
			</li>
			<li >
				<label><bean:message bundle="management" key="management.annual.leave.rule.detail.benefit.hours" /><em> *</em></label> 
				<html:text property="benefitHours" maxlength="10" styleClass="manageAnnualLeaveRuleDetail_benefitHours" /> 
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageAnnualLeaveRuleDetail_description" ></html:textarea>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageAnnualLeaveRuleDetail_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>				
		</ol>
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// Detail JS—È÷§
		validate_manage_secondary_form = function() {
		    var flag = 0;
			flag = flag + validate("manageAnnualLeaveRuleDetail_seniority", false, "select", 0, 0);
			flag = flag + validate("manageAnnualLeaveRuleDetail_legalHours", true, "currency", 0, 0, 1000, 0);
			flag = flag + validate("manageAnnualLeaveRuleDetail_benefitHours", true, "currency", 0, 0, 1000, 0);
			flag = flag + validate("manageAnnualLeaveRuleDetail_status", true, "select", 0, 0);
		    return flag;
		};
	})(jQuery);	
</script>