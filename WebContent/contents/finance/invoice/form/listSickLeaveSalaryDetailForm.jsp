<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GB2312"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="sickLeaveSalaryDetailAction.do?proc=add_object" styleClass="managesickLeaveSalaryDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="sickLeaveSalaryHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="detailId" name="detailId" value="<bean:write name="sickLeaveSalaryDetailForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="sickLeaveSalaryDetailForm" property="subAction"/>" /> 
		<fieldset>
 		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">		
			<li>
				<label>工作月数（开始）<em> *</em></label> 
				<html:select property="rangeFrom" styleClass="managesickLeaveSalaryDetail_rangeFrom">
					<html:optionsCollection property="rangeFroms" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label>工作月数（结束）<em> *</em></label> 
				<html:select property="rangeTo" styleClass="managesickLeaveSalaryDetail_rangeTo">
					<html:optionsCollection property="rangeTos" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label>带薪比例（%）<em> *</em></label> 
				<html:text property="percentage" maxlength="10" styleClass="managesickLeaveSalaryDetail_percentage" /> 
			</li>
			<li>
				<label>固定金（元）</label> 
				<html:text property="fix" maxlength="10" styleClass="managesickLeaveSalaryDetail_fix" /> 
			</li>
			
		</ol>	
		<ol class="auto">
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="managesickLeaveSalaryDetail_description" ></html:textarea>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="managesickLeaveSalaryDetail_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>				
		</ol>
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// Detail JS验证
		validate_manage_secondary_form = function() {
		    var flag = 0;
			flag = flag + validate("managesickLeaveSalaryDetail_rangeFrom", false, "select", 0, 0);
			flag = flag + validate("managesickLeaveSalaryDetail_rangeTo", false, "select", 0, 0);
			flag = flag + validate("managesickLeaveSalaryDetail_status", true, "select", 0, 0);
			flag = flag + validate("managesickLeaveSalaryDetail_percentage", true, "numeric", 0,0,100,0);
			flag = flag + validate("managesickLeaveSalaryDetail_fix", false, "numeric", 0, 0, 0, 0);
		    return flag;
		};
	})(jQuery);
	
	
</script>