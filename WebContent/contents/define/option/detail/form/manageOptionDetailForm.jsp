<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="optionDetailAction.do?proc=add_object" styleClass="optionDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="id" id="optionHeaderId" value="<bean:write name="optionHeaderForm" property="encodedId"/>">
	<input type="hidden" name="optionDetailId" id="optionDetailId" value="<bean:write name="optionDetailForm" property="encodedId"/>">		
	<input type="hidden" name="subAction" id="subAction" value="<bean:write name="optionDetailForm" property="subAction"/>"/>						
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="define" key="define.option.detail.value.name.cn" /><em> *</em></label> 
				<html:text property="optionNameZH" maxlength="100" styleClass="manageOptionDetail_optionNameZH" />
			</li>
			<li>
				<label><bean:message bundle="define" key="define.option.detail.value.name.en" /></label> 
				<html:text property="optionNameEN" maxlength="100" styleClass="manageOptionDetail_optionNameEH" />
			</li>							
			<li>
				<label><bean:message bundle="define" key="define.option.detail.value.index" /><em> *</em></label> 
				<html:text property="optionIndex" maxlength="3" styleClass="manageOptionDetail_optionIndex" />
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageOptionDetail_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 	
				<html:textarea property="description" cols="3" styleClass="manageOptionDetail_description"></html:textarea>
			</li>										
		</ol>
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// Detail JS—È÷§
		validate_manage_secondary_form = function() {
		    var flag = 0;
		    
		    flag = flag + validate('manageOptionDetail_optionNameZH', true, 'common', 100, 0);
			flag = flag + validate('manageOptionDetail_optionIndex', false, 'numeric', 3, 0);
			flag = flag + validate('manageOptionDetail_description', false, 'common', 500, 0);
			flag = flag + validate('manageOptionDetail_status', true, 'select', 0, 0);
		    
		    return flag;
		};
	})(jQuery);
</script>