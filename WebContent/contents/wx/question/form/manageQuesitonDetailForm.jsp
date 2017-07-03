<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="questionDetailAction?proc=add_object" styleClass="manageQuestionDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="id" value="<bean:write name="questionHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="headerId" name="headerId" value="<bean:write name="questionHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="detailId" name="detailId" value="<bean:write name="questionDetailForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="questionDetailForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">	
			<li>
				<label>Chinese Option<em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageQuestionDetail_nameZH" /> 
			</li>
			<li>
				<label>English Option<em> *</em></label>
				<html:text property="nameEN" maxlength="100" styleClass="manageQuestionDetail_nameEN" /> 
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageQuestionDetail_status">
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
		    
		    flag = flag + validate('manageQuestionDetail_nameZH', true, 'common', 100, 0);
			flag = flag + validate('manageQuestionDetail_nameEN', true, 'common', 100, 0);	
			flag = flag + validate('manageQuestionDetail_status', true, 'select', 0, 0);
					    
		    return flag;
		};
	})(jQuery);
</script>