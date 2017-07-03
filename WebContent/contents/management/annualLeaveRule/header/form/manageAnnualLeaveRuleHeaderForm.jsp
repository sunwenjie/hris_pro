<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="annualLeaveRuleHeaderAction.do?proc=add_object" styleClass="manageAnnualLeaveRuleHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" class="annualLeaveRuleHeader_headerId" value="<bean:write name="annualLeaveRuleHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="annualLeaveRuleHeaderForm" property="subAction"/>" />
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="management" key="management.annual.leave.rule.header.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="annualLeaveRuleHeader_nameZH" /> 
			</li>
			<li>
				<label><bean:message bundle="management" key="management.annual.leave.rule.header.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="annualLeaveRuleHeader_nameEN" /> 
			</li>
			<li>
				<label><bean:message bundle="management" key="management.annual.leave.rule.header.base.on" /><em> *</em></label> 
				<html:select property="baseOn" styleClass="annualLeaveRuleHeader_baseOn">
					<html:optionsCollection property="baseOns" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="management" key="management.annual.leave.rule.header.divide.type" /></label> 
				<html:select property="divideType" styleClass="annualLeaveRuleHeader_divideType">
					<html:optionsCollection property="divideTypes" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="annualLeaveRuleHeader_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>		
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="annualLeaveRuleHeader_description"></html:textarea>
			</li>
		</ol>	
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// JS—È÷§
		validate_manage_primary_form = function() {
		    var flag = 0;
		    flag = flag + validate("annualLeaveRuleHeader_nameZH", true, "common", 100, 0);
		    flag = flag + validate("annualLeaveRuleHeader_nameEN", false, "common", 100, 0);
			flag = flag + validate("annualLeaveRuleHeader_baseOn", true, "select", 0, 0);
			flag = flag + validate("annualLeaveRuleHeader_description", false, "common", 500, 0);
			flag = flag + validate("annualLeaveRuleHeader_status", true, "select", 0, 0); 
		    return flag;
		};
	})(jQuery);
</script>