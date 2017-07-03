<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="optionHeaderAction.do?proc=add_object" styleClass="optionHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="id" id="optionHeaderId" value="<bean:write name="optionHeaderForm" property="encodedId"/>"/>
	<input type="hidden" name="subAction" id="subAction" value="<bean:write name="optionHeaderForm" property="subAction"/>"/> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="define" key="define.option.header.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageoptionHeader_nameZH" /> 					
			</li>
			<li>
				<label><bean:message bundle="define" key="define.option.header.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageoptionHeader_nameEN" />
			</li>
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageoptionHeader_description"></html:textarea>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageoptionHeader_status">
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
		    
   			flag = flag + validate('manageoptionHeader_nameZH', true, 'common', 100, 0);
			flag = flag + validate('manageoptionHeader_nameEN', false, 'common', 100, 0);
			flag = flag + validate('manageoptionHeader_status', true, 'select', 0, 0);
			flag = flag + validate('manageoptionHeader_description', false, 'common', 500, 0);
		
		    return flag;
		};
	})(jQuery);
</script>
