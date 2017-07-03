<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="incomeTaxRangeDetailAction.do?proc=add_object" styleClass="manageIncomeTaxRangeDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="incomeTaxRangeHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="detailId" name="detailId" value="<bean:write name="incomeTaxRangeDetailForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="incomeTaxRangeDetailForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label>收入基数（开始）<em>*</em> </label> 
				<html:text property="rangeFrom" maxlength="20" styleClass="manageIncomeTaxRangeDetail_rangeFrom" /> 
			</li>	
			<li>
				<label>收入基数（结束）<em>*</em></label> 
				<html:text property="rangeTo" maxlength="20" styleClass="manageIncomeTaxRangeDetail_rangeTo" /> 
			</li>
			<li>
				<label>税率 </label> 
				<html:text property="percentage" maxlength="20" styleClass="manageIncomeTaxRangeDetail_percentage" /> 
			</li>	
			<li>
				<label>扣除金额</label> 
				<html:text property="deduct" maxlength="20" styleClass="manageIncomeTaxRangeDetail_deduct" /> 
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageIncomeTaxRangeDetail_description"></html:textarea>
			</li>					
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageIncomeTaxRangeDetail_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>	
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// JS验证
		validate_manage_secondary_form = function() {
			var flag = 0;
			
			flag = flag + validate("manageIncomeTaxRangeDetail_rangeFrom", true, "currency", 20, 0); 
			flag = flag + validate("manageIncomeTaxRangeDetail_rangeTo", true, "currency", 20, 0); 
			flag = flag + validate("manageIncomeTaxRangeDetail_percentage", false, "currency", 20, 0); 
			flag = flag + validate("manageIncomeTaxRangeDetail_deduct", false, "currency", 20, 0); 
			flag = flag + validate("manageIncomeTaxRangeDetail_description", false, "common", 500, 0);
			flag = flag + validate("manageIncomeTaxRangeDetail_status", true, "select", 0, 0);
			
			return flag;
		};
	})(jQuery);
</script>