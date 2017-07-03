<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="leaveHeaderAction.do?proc=add_object" styleClass="manageLeave_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="timesheetId" name="timesheetId" value="<bean:write name="leaveHeaderForm" property="timesheetId"/>" />
	<input type="hidden" id="leaveHeaderId" name="id" value="<bean:write name="leaveHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="leaveHeaderForm" property="subAction"/>" /> 
	<html:hidden styleClass="useNextYearHours" styleId="useNextYearHours" property="useNextYearHours" />
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
					<html:text property="employeeId" maxlength="10" styleClass="manageLeave_employeeId" styleId="employeeId"/> 
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>
						<logic:equal name="role" value="5"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
					</label> 
					<html:text property="employeeNameZH" maxlength="50" styleClass="manageLeave_employeeNameZH" styleId="employeeNameZH"/> 
				</li>
				<li>
					<label>
						<logic:equal name="role" value="5"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
					</label> 
					<html:text property="employeeNameEN" maxlength="50" styleClass="manageLeave_employeeNameEN" styleId="employeeNameEN"/> 
				</li>	
			</ol>	
			<ol class="auto" style="display: none;">
				<li>
					<label><bean:message bundle="public" key="public.certificate.number" /></label> 
					<html:text property="certificateNumber" maxlength="20" styleClass="manageLeave_certificateNumber" styleId="certificateNumber"/> 
				</li>	
				<li>
					<label><logic:equal name="role" value="5">雇员</logic:equal><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>编号</label> 
					<html:text property="employeeNo" maxlength="20" styleClass="manageLeave_employeeNo" styleId="employeeNo"/> 
				</li>	
			</ol>
			<ol class="auto">
				<li>
					<label><bean:message bundle="business" key="business.leave.contract.start.date" /></label> 
					<input type="text" class="contractStartDate" id="contractStartDate" />
				</li>	
				<li>
					<label><bean:message bundle="business" key="business.leave.contract.end.date" /></label> 
					<input type="text" class="contractEndDate" id="contractEndDate" />
				</li>	
			</ol>
			<ol class="auto">	
				<logic:equal name="role" value="1">
				<li id="clientIdLI">
					<label>客户 <em> *</em></label> 
					<select name="clientId" class="manageLeave_clientId" id="clientId">
						<option value="0">请选择</option>
					</select>
				</li>
				</logic:equal>
				<li id="contractIdLI">
					<label>
						<logic:equal name="role" value="5"><bean:message bundle="public" key="public.contract1" /></logic:equal>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2" /></logic:equal>
						<em> *</em>
					</label> 
					<select name="contractId" class="manageLeave_contractId" id="contractId">
						<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
					</select>
				</li>
				<li>
					<label><bean:message bundle="business" key="business.leave.type" /> <em> *</em></label> 
					<select name="itemId" class="manageLeave_itemId" id="itemId">
						<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
					</select>
				</li>
			</ol>
			<ol class="auto" id="leaveDateOL">	
				<li>
					<label><bean:message bundle="public" key="public.start.time" /></label> 
					<html:text property="estimateStartDate" maxlength="20" readonly="true" styleClass="Wdate manageLeave_estimateStartDate" styleId="estimateStartDate" /> 
				</li>
				<li>
					<label><bean:message bundle="public" key="public.end.time" /></label> 
					<html:text property="estimateEndDate" maxlength="20" readonly="true" styleClass="Wdate manageLeave_estimateEndDate" styleId="estimateEndDate" /> 
				</li>
			</ol>
			<ol class="auto">	
				<li id="estimateLegalHoursLI" style="display: none;">
					<label><bean:message bundle="business" key="business.leave.legal.hours" /><em> *</em></label> 
					<html:text property="estimateLegalHours" maxlength="20" readonly="true" styleClass="manageLeave_estimateLegalHours" styleId="estimateLegalHours" /> 
				</li>
				<li id="estimateBenefitHoursLI">
					<label id="benefit_hours"><bean:message bundle="business" key="business.leave.hours" /><em> *</em></label> 
					<html:text property="estimateBenefitHours" maxlength="20" readonly="true" styleClass="manageLeave_estimateBenefitHours" styleId="estimateBenefitHours" /> 
				</li>
			</ol>
			<ol class="auto">	
				<li>
					<label><bean:message bundle="business" key="business.leave.description" /></label> 
					<html:textarea property="description" styleClass="manageLeave_description"></html:textarea>
				</li>	
				<li id="statusLI">
					<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
					<html:select property="status" styleClass="manageLeave_status" styleId="status">
						<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
					</html:select>
				</li>	
				<li <logic:notEqual name="leaveHeaderForm" property="status" value="3">style="display:none;"</logic:notEqual>>
					<label><bean:message bundle="business" key="business.leave.retrieve.status" /><em> *</em></label> 
					<html:select property="retrieveStatus" styleClass="manageLeave_retrieveStatus" styleId="retrieveStatus" >
						<html:optionsCollection property="retrieveStatuses" value="mappingId" label="mappingValue" />
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
	    flag = flag + validate('manageLeave_employeeId', true, 'common', 0, 0, 0, 0);
	    <logic:equal name="role" value="1">
	    	flag = flag + validate('manageLeave_clientId', true, 'select', 0, 0, 0, 0);
	    </logic:equal>
	    flag = flag + validate('manageLeave_contractId', true, 'select', 0, 0, 0, 0);
	    flag = flag + validate('manageLeave_itemId', true, 'select', 0, 0, 0, 0);
	    flag = flag + validate('manageLeave_estimateStartDate', true, 'common', 20, 0, 0, 0); 
	    flag = flag + validate('manageLeave_estimateEndDate', true, 'common', 20, 0, 0, 0);
	    if( $('#itemId').val() == 48 ){
	    	flag = flag + validate('manageLeave_estimateLegalHours', true, 'common', 20, 0, 0, 0);
	    }else if( $('#itemId').val() == 49 ){
	    	flag = flag + validate('manageLeave_estimateBenefitHours', true, 'common', 20, 0, 0, 0);
	    }else if( $('#itemId').val() == 41 ){
	    	flag = flag + validate('manageLeave_estimateLegalHours', true, 'common', 20, 0, 0, 0);
	    	flag = flag + validate('manageLeave_estimateBenefitHours', true, 'common', 20, 0, 0, 0);
	    }else{
	    	 flag = flag + validate('manageLeave_estimateBenefitHours', true, 'common', 20, 0, 0, 0);
	    } 
	    flag = flag + validate('manageLeave_description', false, 'common', 500, 0, 0, 0);
	    flag = flag + validate('manageLeave_status', true, 'select', 0, 0, 0, 0);
	    if (flag == 0) {
	        enableForm('manageLeave_form');
	    }
	   
	    return flag;
	};
</script>