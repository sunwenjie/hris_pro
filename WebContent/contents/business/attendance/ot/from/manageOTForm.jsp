<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="otHeaderAction.do?proc=add_object" styleClass="manageOT_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="timesheetId" name="timesheetId" value="<bean:write name="otHeaderForm" property="timesheetId"/>" />
	<input type="hidden" id="otHeaderId" name="id" value="<bean:write name="otHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="otHeaderForm" property="subAction"/>" /> 
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
					<html:text property="employeeId" maxlength="10" styleClass="manageOT_employeeId" styleId="employeeId"/> 
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>
						<logic:equal name="role" value="5"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
					</label> 
					<html:text property="employeeNameZH" maxlength="50" styleClass="manageOT_employeeNameZH" styleId="employeeNameZH"/> 
				</li>
				<li>
					<label>
						<logic:equal name="role" value="5"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
					</label> 
					<html:text property="employeeNameEN" maxlength="50" styleClass="manageOT_employeeNameEN" styleId="employeeNameEN"/> 
				</li>	
			</ol>	
			<ol class="auto" style="display: none;">	
				<li>
					<label><bean:message bundle="public" key="public.certificate.number" /></label> 
					<html:text property="certificateNumber" maxlength="20" styleClass="manageOT_certificateNumber" styleId="certificateNumber"/> 
				</li>	
				<li>
					<label><logic:notEqual name="role" value="2">雇员</logic:notEqual><logic:equal name="role" value="2">员工</logic:equal>编号</label> 
					<html:text property="employeeNo" maxlength="20" styleClass="manageOT_employeeNo" styleId="employeeNo"/> 
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
				<logic:notEqual name="role" value="2">
					<li id="clientIdLI">
						<label>客户 <em> *</em></label> 
						<select name="clientId" class="manageOT_clientId" id="clientId">
							<option value="0">请选择</option>
						</select>
					</li>
				</logic:notEqual>
				<li id="contractIdLI">
					<label>
						<logic:equal name="role" value="5"><bean:message bundle="public" key="public.contract1" /></logic:equal>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2" /></logic:equal>
						<em> *</em>
					</label> 
					<select name="contractId" class="manageOT_contractId" id="contractId">
						<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
					</select>
				</li>
				
				<logic:equal name="accountId" value="100056">
				<li >
					<label>
						<bean:message bundle="business" key="business.ot.special.overtime" />
						<em> *</em>
					</label> 
					<label>
					<html:radio property="specialOT" value="1"></html:radio>是
					&nbsp;&nbsp;<html:radio property="specialOT" value="2" ></html:radio>否
					</label>
				</li>
				</logic:equal>
				
			</ol>
			<ol class="auto" id="otDateOL">	
				<li>
					<label><bean:message bundle="public" key="public.start.time" /> </label> 
					<html:text property="estimateStartDate" maxlength="20" readonly="true" styleClass="manageOT_estimateStartDate" styleId="estimateStartDate" /> 
				</li>
				<li>
					<label><bean:message bundle="public" key="public.end.time" /></label> 
					<html:text property="estimateEndDate" maxlength="20" readonly="true" styleClass="manageOT_estimateEndDate" styleId="estimateEndDate" /> 
				</li>
			</ol>
			<ol class="auto">	
				<li>
					<label><bean:message bundle="business" key="business.ot.type" /></label> 
					<html:text  property="description" styleClass="manageOT_description"></html:text>
				</li>
				<li>
					<label><bean:message bundle="business" key="business.ot.hours" /><em> *</em></label> 
					<html:text property="estimateHours" maxlength="20" readonly="true" styleClass="manageOT_estimateHours" styleId="estimateHours" /> 
				</li>
			</ol>
			<ol class="auto">	
				<li>
					<label><bean:message bundle="public" key="public.description" /></label> 
					<html:textarea property="remark2" styleClass="manageOT_remark2"></html:textarea>
				</li>
				<li id="statusLI">
					<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
					<html:select property="status" styleClass="manageOT_status" styleId="status">
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
	    flag = flag + validate('manageOT_employeeId', true, 'common', 0, 0, 0, 0);
	    <logic:notEqual name="role" value="2">
	   		flag = flag + validate('manageOT_clientId', true, 'select', 0, 0, 0, 0);
	    </logic:notEqual>
	    flag = flag + validate('manageOT_contractId', true, 'select', 0, 0, 0, 0);
	    flag = flag + validate('manageOT_estimateStartDate', true, 'common', 20, 0, 0, 0); 
	    flag = flag + validate('manageOT_estimateEndDate', true, 'common', 20, 0, 0, 0);
	    flag = flag + validate('manageOT_estimateHours', true, 'common', 20, 0, 0, 0);
	    flag = flag + validate('manageOT_description', false, 'common', 500, 0, 0, 0);
	    flag = flag + validate('manageOT_status', true, 'select', 0, 0, 0, 0);
	    if (flag == 0) {
	        enableForm('manageOT_form');
	    }
	    return flag;
	};
</script>