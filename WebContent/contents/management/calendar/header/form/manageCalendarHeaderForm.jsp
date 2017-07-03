<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="calendarHeaderAction.do?proc=add_object" styleClass="manageCalendarHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="headerId" value="<bean:write name="calendarHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" value="<bean:write name="calendarHeaderForm" property="subAction"/>" />
		<fieldset>
			<ol class="auto">
				<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
			</ol>
			<ol class="auto">
			<li>
				<label><bean:message bundle="management" key="management.calendar.header.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageCalendarHeader_nameZH" /> 
			</li>
			<li>
				<label><bean:message bundle="management" key="management.calendar.header.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageCalendarHeader_nameEN" /> 
			</li>
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageCalendarHeader_description"></html:textarea>
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageCalendarHeader_status">
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
		    
			flag = flag + validate("manageCalendarHeader_nameZH", true, "common", 100, 0);
			flag = flag + validate("manageCalendarHeader_description", false, "common", 500, 0);
			flag = flag + validate("manageCalendarHeader_status", true, "select", 0, 0);
		
		    return flag;
		};
	})(jQuery);
</script>
