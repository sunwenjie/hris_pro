<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="incomeTaxRangeHeaderAction.do?proc=add_object" styleClass="manageIncomeTaxRangeHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="incomeTaxRangeHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="incomeTaxRangeHeaderForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label>税率名称（中文）<em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageIncomeTaxRangeHeader_nameZH" /> 
			</li>
			<li>
				<label>税率名称（英文）</label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageIncomeTaxRangeHeader_nameEN" /> 
			</li>
			<li>
				<label>生效日期</label> 
				<html:text property="startDate" maxlength="10" styleId="startDate" styleClass="Wdate manageIncomeTaxRangeHeader_startDate" />
			</li>
			<li>
				<label>失效日期</label> 
				<html:text property="endDate" maxlength="10" styleId="endDate" styleClass="Wdate manageIncomeTaxRangeHeader_endDate" />
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label>是否默认</label> 
				<html:checkbox property="isDefault" styleClass="manageIncomeTaxRangeHeader_isDefault" />
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageIncomeTaxRangeHeader_description"></html:textarea>
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageIncomeTaxRangeHeader_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>				
		</ol>	
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// JS验证
		validate_manage_primary_form = function() {
			var flag = 0;
			
			flag = flag + validate("manageIncomeTaxRangeHeader_nameZH", true, "common", 100, 0);
			flag = flag + validate("manageIncomeTaxRangeHeader_description", false, "common", 500, 0);
			flag = flag + validate("manageIncomeTaxRangeHeader_status", true, "select", 0, 0);
			
			return flag;
		};
	})(jQuery);
</script>
