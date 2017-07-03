<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="mgtPositionGradeCurrencyAction.do?proc=add_object" styleClass="managePositionGradeCurrency_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="positionGradeId" name="positionGradeId" value="<bean:write name="mgtPositionGradeForm" property="encodedId"/>"/>
	<input type="hidden" id="currencyId" name="currencyId" value="<bean:write name="mgtPositionGradeCurrencyForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="mgtPositionGradeCurrencyForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label>币种<em> *</em></label>
				<html:select property="currencyType" styleClass="managePositionGradeCurrency_currencyType">
					<html:optionsCollection property="currencyTypes" value="mappingId" label="mappingValue"/>
				</html:select>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label>最高薪资</label> 
				<html:text property="maxSalary" maxlength="20" styleClass="managePositionGradeCurrency_maxSalary" /> 
			</li>
			<li>
				<label>最低薪资</label> 
				<html:text property="minSalary" maxlength="20" styleClass="managePositionGradeCurrency_minSalary" /> 
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="managePositionGradeCurrency_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>	
		</ol>
	</fieldset>	
</html:form>