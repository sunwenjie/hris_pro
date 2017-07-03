<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="travelAction.do?proc=add_object" styleClass="manageTravel_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="travelId" name="id" value="<bean:write name="travelForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="travelForm" property="subAction"/>" /> 
		<fieldset>
			<ol class="auto">
				<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
			</ol>
			<ol class="auto">	
				<li id="employeeIdLI">
					<label>
						<logic:equal name="role" value="5"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
						<em> *</em>
					</label> 
					<html:text property="employeeId" maxlength="10" styleClass="manageTravel_employeeId" styleId="employeeId"/> 
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>
						<logic:equal name="role" value="5"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
					</label> 
					<html:text property="nameZH" maxlength="50" styleClass="manageTravel_employeeNameZH" styleId="nameZH"/> 
				</li>
				<li>
					<label>
						<logic:equal name="role" value="5"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
					</label> 
					<html:text property="nameEN" maxlength="50" styleClass="manageTravel_employeeNameEN" styleId="nameEN"/> 
				</li>	
			</ol>	
	

			
		
			<ol class="auto" id="leaveDateOL">	
				<li>
					<label><bean:message bundle="public" key="public.start.time" /><em> *</em></label> 
					<html:text property="startDate" maxlength="20" readonly="true" styleClass="Wdate manageTravel_startDate" styleId="startDate" /> 
				</li>
				<li>
					<label><bean:message bundle="public" key="public.end.time" /><em> *</em></label> 
					<html:text property="endDate" maxlength="20" readonly="true" styleClass="Wdate manageTravel_endDate" styleId="endDate" /> 
				</li>
			</ol>

			<ol class="auto">	
				<li>
					<label><bean:message bundle="public" key="public.description" /></label> 
					<html:textarea property="description" styleClass="manageTravel_description"></html:textarea>
				</li>	
				<li id="statusLI">
					<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
					<html:select property="status" styleClass="manageTravel_status" styleId="status">
						<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
					</html:select>
				</li>	
				
			</ol>	
			<div id="special_info">
				
			</div>
		</fieldset>
</html:form>

<script type="text/javascript">

	validate_manage_primary_form = function() {
	    var flag = 0;
	    flag = flag + validate('manageTravel_employeeId', true, 'common', 0, 0, 0, 0);
	    flag = flag + validate('manageTravel_startDate', true, 'common', 20, 0, 0, 0); 
	    flag = flag + validate('manageTravel_endDate', true, 'common', 20, 0, 0, 0);
	    flag = flag + validate('manageTravel_description', false, 'common', 500, 0, 0, 0);
	    flag = flag + validate('manageTravel_status', true, 'select', 0, 0, 0, 0);
	    if (flag == 0) {
	        enableForm('manageTravel_form');
	    }
	   
	    return flag;
	};
	
	$('.manageTravel_startDate').focus(function(){
		WdatePicker({ 
			maxDate: '#F{$dp.$D(\'endDate\')}' 
		});
	});
	
	$('.manageTravel_endDate').focus(function(){
		WdatePicker({ 
			minDate: '#F{$dp.$D(\'startDate\')}'
		});
	});
</script>