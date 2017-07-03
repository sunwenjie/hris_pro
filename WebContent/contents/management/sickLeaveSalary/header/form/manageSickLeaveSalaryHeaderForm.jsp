<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ page import="com.kan.base.domain.management.SickLeaveSalaryHeaderVO" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final SickLeaveSalaryHeaderVO sickLeaveSalayHeaderVO = (SickLeaveSalaryHeaderVO)request.getAttribute("sickLeaveSalaryHeaderForm");
%>

<html:form action="sickLeaveSalaryHeaderAction.do?proc=add_object" styleClass="manageSickLeaveSalaryHeader_form">
	<%= BaseAction.addToken( request ) %>
	 <input type="hidden" id="headerId" name="id" class="sickLeaveSalaryHeader_headerId" value="<bean:write name="sickLeaveSalaryHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="sickLeaveSalaryHeaderForm" property="subAction"/>" />
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="management" key="management.sick.leave.header.item" /><em> *</em></label> 
				<html:select property="itemId" styleClass="sickLeaveSalaryHeader_itemId">
					<html:optionsCollection property="itemIds" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="management" key="management.sick.leave.header.name.cn" /><em> *</em></label> 
				<html:text property="nameZH" maxlength="100" styleClass="sickLeaveSalaryHeader_nameZH" /> 
			</li>
			<li>
				<label><bean:message bundle="management" key="management.sick.leave.header.name.en" /></label> 
				<html:text property="nameEN" maxlength="100" styleClass="sickLeaveSalaryHeader_nameEN" /> 
			</li>
			<li>
				<label><bean:message bundle="management" key="management.sick.leave.header.base.on" /><em> *</em></label> 
				<html:select property="baseOn" styleClass="sickLeaveSalaryHeader_baseOn">
					<html:optionsCollection property="baseOns" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="sickLeaveSalaryHeader_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>		
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="sickLeaveSalaryHeader_description"></html:textarea>
			</li>
			<li>
				<label> </label> 
				<logic:notEmpty name="salaryItems">
					<div style="width:215px;">
						<span ><bean:message bundle="management" key="management.sick.leave.header.salary.item.tipes" /></span><p/>
						<logic:iterate id="salaryItem" name="salaryItems" >
						<logic:notEqual name="salaryItem" property="mappingId" value="5">
						<span>
							<!-- <input type="checkbox" value="<bean:write name="salaryItem" property="mappingId"/>"/> -->
							<b><bean:write name="salaryItem" property="mappingValue"/></b> <br/>
						</span>
						</logic:notEqual>
						</logic:iterate>
					</div>
				</logic:notEmpty>
			</li>
		</ol>	
	</fieldset>
</html:form>


<script type="text/javascript">
	(function($) {
		
		// JS—È÷§
		validate_manage_primary_form = function() {
		    var flag = 0;
		    flag = flag + validate("sickLeaveSalaryHeader_itemId", true, "select", 0, 0);
		    flag = flag + validate("sickLeaveSalaryHeader_nameZH", true, "common", 100, 0);
		    flag = flag + validate("sickLeaveSalaryHeader_itemId", true, "common", 100, 0);
			flag = flag + validate("sickLeaveSalaryHeader_baseOn", true, "select", 0, 0);
			flag = flag + validate("sickLeaveSalaryHeader_description", false, "common", 500, 0);
			flag = flag + validate("sickLeaveSalaryHeader_status", true, "select", 0, 0); 
		    
		    return flag;
		};
		
	})(jQuery);
</script>
