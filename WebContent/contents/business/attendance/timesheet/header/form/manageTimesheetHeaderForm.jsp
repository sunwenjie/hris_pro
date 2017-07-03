<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="timesheetHeaderAction.do?proc=add_object" styleClass="manageTimesheetHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="timesheetHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="timesheetHeaderForm" property="subAction"/>" /> 
		<fieldset>
			<ol class="auto">
				<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
			</ol>
			<ol class="auto">
				<li>
					<label><bean:message bundle="business" key="business.ts.month" /></label> 
					<html:select property="monthly" styleClass="manageTimesheetHeader_monthly" styleId="monthly" >
						<html:optionsCollection property="last2Months" value="mappingId" label="mappingValue" />
					</html:select>
				</li>
			</ol>
			<ol class="auto">	
				<li id="employeeIdLI">
					<label>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
						<logic:equal name="role" value="4"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
					</label> 
					<html:text property="employeeId" maxlength="10" styleClass="manageTimesheetHeader_employeeId" styleId="employeeId"/> 
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
						<logic:equal name="role" value="4"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
					</label> 
					<html:text property="employeeNameZH" maxlength="50" styleClass="manageTimesheetHeader_employeeNameZH" styleId="employeeNameZH"/> 
				</li>
				<li>
					<label>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
						<logic:equal name="role" value="4"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
					</label>
					<html:text property="employeeNameEN" maxlength="50" styleClass="manageTimesheetHeader_employeeNameEN" styleId="employeeNameEN"/> 
				</li>	
			</ol>
			<logic:equal name="role" value="1">
				<ol class="auto">
					<li><a id="moreInfo_LINK"><bean:message bundle="public" key="link.more.info" /></a></li>
				</ol>
			</logic:equal>
			<logic:equal name="role" value="2">
				<ol class="auto">
					<li><a id="moreInfo_LINK"><bean:message bundle="public" key="link.more.info" /></a></li>
				</ol>
			</logic:equal>
			<div id="moreInfo" style="display:none; border: 1px solid rgb(170, 170, 170); border-top-left-radius: 3px; border-top-right-radius: 3px; border-bottom-right-radius: 3px; border-bottom-left-radius: 3px; margin: 2px 10px 20px; padding: 10px 10px 0px;">
				<ol class="auto">	
					<li id="clientIdLI" class="auto" <logic:equal name="role" value="2">style="display : none;"</logic:equal>>	
						<label>客户ID</label> 
						<html:text property="clientId" maxlength="10" styleClass="manageTimesheetHeader_clientId" styleId="clientId"/> 
					</li>
				</ol>
				<ol class="auto" <logic:equal name="role" value="2">style="display:none;"</logic:equal>>	
					<li>
						<label>客户名称（中文）</label> 
						<html:text property="clientNameZH" maxlength="100" styleClass="manageTimesheetHeader_clientNameZH" styleId="clientNameZH"/> 
					</li>
					<li>
						<label>客户名称（英文）</label> 
						<html:text property="clientNameEN" maxlength="100" styleClass="manageTimesheetHeader_clientNameEN" styleId="clientNameEN"/> 
					</li>
				</ol>
				<ol class="auto">
					<li id="orderIdLI">
						<label>
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
							<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
						</label> 
						<html:text property="orderId" maxlength="10" styleClass="manageTimesheetHeader_orderId" styleId="orderId"/> 
					</li>
					<li id="contractIdLI">
						<label>
							<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
							<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
						</label>
						<html:text property="contractId" maxlength="10" styleClass="manageTimesheetHeader_contractId" styleId="contractId"/> 
					</li>
				</ol>
			</div>
			<ol class="auto">	
				<li>
					<label><bean:message bundle="business" key="business.ts.ts.start" /></label> 
					<html:text property="startDate" maxlength="20" styleClass="manageTimesheetHeader_startDate" styleId="startDate" /> 
				</li>
				<li>
					<label><bean:message bundle="business" key="business.ts.ts.end" /></label> 
					<html:text property="endDate" maxlength="20" styleClass="manageTimesheetHeader_endDate" styleId="endDate" /> 
				</li>
				<li>
					<label><bean:message bundle="business" key="business.ts.work.hours" /></label> 
					<html:text property="totalWorkHours" maxlength="20" styleClass="manageTimesheetHeader_totalWorkHours" styleId="totalWorkHours" /> 
				</li>
				<li>
					<label><bean:message bundle="business" key="business.ts.work.days" /></label> 
					<html:text property="totalWorkDays" maxlength="20" styleClass="manageTimesheetHeader_totalWorkDays" styleId="totalWorkDays" /> 
				</li>
				<li>
					<label><bean:message bundle="business" key="business.ts.toal.full.hours" /></label> 
					<html:text property="totalFullHours" maxlength="20" styleClass="manageTimesheetHeader_totalFullHours" styleId="totalFullHours" /> 
				</li>
				<li>
					<label><bean:message bundle="business" key="business.ts.toal.full.days" /></label> 
					<html:text property="totalFullDays" maxlength="20" styleClass="manageTimesheetHeader_totalFullDays" styleId="totalFullDays" /> 
				</li>
				<li>
					<label><bean:message bundle="business" key="business.ts.need.audit" /></label> 
					<html:select property="needAudit" styleClass="manageTimesheetHeader_needAudit" styleId="needAudit" >
						<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
					</html:select>
				</li>	
				<li>
					<label><bean:message bundle="business" key="business.ts.is.normal" /></label> 
					<html:select property="isNormal" styleClass="manageTimesheetHeader_isNormal" styleId="isNormal">
						<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
					</html:select>
				</li>	
			</ol>
			<ol class="auto">
				<li>
					<label><bean:message bundle="public" key="public.description" /></label> 
					<html:textarea property="description" styleClass="manageTimesheetHeader_description"></html:textarea>
				</li>
				<li id="statusLI">
					<label><bean:message bundle="public" key="public.status" />&nbsp;&nbsp;&nbsp;</label> 
					<html:select property="status" styleClass="manageTimesheetHeader_status" styleId="status">
						<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
					</html:select>
				</li>
			</ol>
			<div id="append_info"></div>	
		</fieldset>
</html:form>

<script type="text/javascript">

	validate_manage_primary_form = function() {
	    var flag = 0;
	    
 	    flag = flag + validate('manageTimesheetHeader_description', false, 'common', 500, 0, 0, 0);
 	    
	    return flag;
	};
	
</script>