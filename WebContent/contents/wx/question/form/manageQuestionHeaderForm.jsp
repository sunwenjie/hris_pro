<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="questionHeaderAction.do?proc=add_object" styleClass="manageQuestionHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="id" name="id" value="<bean:write name="questionHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="questionHeaderForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">	
			<li>
				<label>Question Title (Chinese)<em> *</em></label> 
				<html:textarea property="titleZH" cols="3" styleClass="manageQuestionHeader_titleZH" />	
			</li>
			<li>
				<label>Question Title (English)<em> *</em></label> 	
				<html:textarea property="titleEN" cols="3" styleClass="manageQuestionHeader_titleEN" />	
			</li>
		</ol>	
		<ol class="auto">		
			<li>
				<label>Radio / Multiple<em> *</em></label> 
				<html:select property="isSingle" styleClass="manageQuestionHeader_isSingle">
					<html:optionsCollection property="isSingles" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
		</ol>	
		<ol class="auto">		
			<li>
				<label>Expiration Date<em> *</em></label> 	
				<html:text property="expirationDate" maxlength="12" styleClass="Wdate manageQuestionHeader_expirationDate" onfocus="WdatePicker({lang:'en',dateFmt:'yyyy-MM-dd'})" /> 
			</li>
			<li>
				<label>True Answer<em> *</em></label> 	
				<html:text property="answer" maxlength="5" styleClass="manageQuestionHeader_answer" /> 
			</li>
			<li>
				<label>Lucky Number<em> *</em></label> 	
				<html:text property="luckyNumber" maxlength="3" styleClass="manageQuestionHeader_luckyNumber" /> 
			</li>
			<li>
				<label>Lucky Type<em> *</em></label> 
				<html:select property="luckyType" styleClass="manageQuestionHeader_luckyType">
					<html:optionsCollection property="luckyTypes" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>	
		<ol class="auto">					
			<li>
				<label>Chinese Tips<em> *</em></label> 
				<html:textarea property="tipsZH" cols="3" styleClass="manageQuestionHeader_tipsZH"></html:textarea>
			</li>															
			<li>
				<label>English Tips<em> *</em></label> 
				<html:textarea property="tipsEN" cols="3" styleClass="manageQuestionHeader_tipsEN"></html:textarea>
			</li>
		</ol>
		<ol class="auto">					
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" cols="3" styleClass="manageQuestionHeader_description"></html:textarea>
			</li>																
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageQuestionHeader_status">
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
		    flag = flag + validate('manageQuestionHeader_titleZH', true, 'common', 500, 0);
		    flag = flag + validate('manageQuestionHeader_titleEN', true, 'common', 500, 0);
		    flag = flag + validate('manageQuestionHeader_isSingle', true, 'select', 0, 0);
		    flag = flag + validate('manageQuestionHeader_expirationDate', true, 'common', 12, 0);
		    flag = flag + validate('manageQuestionHeader_answer', true, 'common', 5, 0);
		    flag = flag + validate('manageQuestionHeader_luckyNumber', true, 'numeric', 2, 0);
		    flag = flag + validate('manageQuestionHeader_luckyType', true, 'select', 0, 0);
		    flag = flag + validate('manageQuestionHeader_tipsZH', true, 'common', 500, 0);
		    flag = flag + validate('manageQuestionHeader_tipsEN', true, 'common', 500, 0);
			flag = flag + validate('manageQuestionHeader_status', true, 'select', 0, 0);
			flag = flag + validate('manageQuestionHeader_description', false, 'common', 500, 0);
		    
		    return flag;
		};
	})(jQuery);
</script>