<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="bankTemplateHeaderAction.do?proc=add_object" styleClass="manageBankTemplateHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="id" name="id" value="<bean:write name="bankTemplateHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="bankTemplateHeaderForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="management" key="management.salary.template.header.bank" /><em> *</em></label> 	
				<html:select property="bankId" styleClass="manageBankTemplateHeader_bankId">
					<html:optionsCollection property="banks" value="mappingId" label="mappingValue" />
				</html:select>		
			</li>
		</ol>	
		<ol class="auto">	
			<li>
				<label><bean:message bundle="management" key="management.salary.template.header.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageBankTemplateHeader_nameZH" /> 					
			</li>
			<li>
				<label><bean:message bundle="management" key="management.salary.template.header.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageBankTemplateHeader_nameEN" />
			</li>
		</ol>	
		<ol class="auto">					
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" cols="3" styleClass="manageBankTemplateHeader_description"></html:textarea>
			</li>																
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageBankTemplateHeader_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// JS—È÷§
		validate_manage_primary_form = function() {
		    var flag = 0;
		    
			flag = flag + validate('manageBankTemplateHeader_bankId', true, 'select', 0, 0); 
			flag = flag + validate('manageBankTemplateHeader_nameZH', true, 'common', 100, 0);			
			flag = flag + validate('manageBankTemplateHeader_status', true, 'select', 0, 0);
			flag = flag + validate('manageBankTemplateHeader_description', false, 'common', 500, 0);
		    
		    return flag;
		};
	})(jQuery);
</script>