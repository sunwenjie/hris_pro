<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="sbAdjustmentHeaderAction.do?proc=add_object" styleClass="manageSBAdjustmentHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="id" name="id" value="<bean:write name="sbAdjustmentHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="sbAdjustmentHeaderForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /> 
			<img src="images/tips.png" title="<bean:message bundle="sb" key="sb.adjustment.header.tips" />" /></label></li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="sb" key="sb.adjustment.header.monthly" /> <img src="images/tips.png" title="<bean:message bundle="sb" key="sb.adjustment.header.monthly.tips" />" /><em> *</em></label> 
				<html:select property="monthly" styleClass="manageSBAdjustmentHeader_monthly">
					<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">	
			<li id="contractIdLI">
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal><em> *</em>
				</label> 
				<html:text property="contractId" maxlength="10" styleClass="manageSBAdjustmentHeader_contractId" styleId="contractId"/> 
			</li>
			<li>
				<label><bean:message bundle="sb" key="sb.adjustment.header.sb" /><em> *</em></label> 
				<select name="employeeSBId" class="manageSBAdjustmentHeader_employeeSBId" id="employeeSBId">
					<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
				</select>
			</li>
		</ol>
		<ol class="auto" style="display: none;">
			<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
				<label><bean:message bundle="security" key="security.entity" /></label> 
				<html:select property="entityId" styleClass="manageSBAdjustmentHeader_entityId">
					<html:optionsCollection property="entities" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
				<label><bean:message bundle="security" key="security.business.type" /></label> 
				<html:select property="businessTypeId" styleClass="manageSBAdjustmentHeader_businessTypeId">
					<html:optionsCollection property="businessTypies" value="mappingId" label="mappingValue" />
				</html:select>
			</li>	
		</ol>
		<ol class="auto">	
			<li id="employeeIdLI">
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</label> 
				<html:text property="employeeId" maxlength="11" styleClass="manageSBAdjustmentHeader_employeeId" /> 
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</label> 
				<html:text property="employeeNameZH" maxlength="50" styleClass="manageSBAdjustmentHeader_employeeNameZH" /> 
			</li>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</label> 
				<html:text property="employeeNameEN" maxlength="50" styleClass="manageSBAdjustmentHeader_employeeNameEN" /> 
			</li>
		</ol>
		<ol class="auto" style="display: none;">	
			<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
				<label>客户ID</label> 
				<html:text property="clientId" maxlength="10" styleClass="manageSBAdjustmentHeader_clientId" /> 
			</li>
			<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
				<label>订单ID</label>
				<html:text property="orderId" maxlength="10" styleClass="manageSBAdjustmentHeader_orderId" /> 
			</li>
			<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
				<label>客户名称（中文）</label> 
				<html:text property="clientNameZH" maxlength="50" styleClass="manageSBAdjustmentHeader_clientNameZH" /> 
			</li>
			<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
				<label>客户名称（英文）</label> 
				<html:text property="clientNameEN" maxlength="50" styleClass="manageSBAdjustmentHeader_clientNameEN" /> 
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="sb" key="sb.adjustment.header.amount.company" /> <img src="images/tips.png" title="<bean:message bundle="sb" key="sb.adjustment.header.amount.company.tips" />" /></label> 
				<html:text property="amountCompany" maxlength="10" styleClass="manageSBAdjustmentHeader_amountCompany" /> 
			</li>
			<li>
				<label><bean:message bundle="sb" key="sb.adjustment.header.amount.personal" /> <img src="images/tips.png" title="<bean:message bundle="sb" key="sb.adjustment.header.amount.personal.tips" />" /></label> 
				<html:text property="amountPersonal" maxlength="10" styleClass="manageSBAdjustmentHeader_amountPersonal" /> 
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageSBAdjustmentHeader_description"></html:textarea>
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageSBAdjustmentHeader_status" styleId="status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>				
		</ol>	
	</fieldset>
</html:form>

<script type="text/javascript">
	validate_manage_primary_form = function() {
	    var flag = 0;
	    
	    flag = flag + validate('manageSBAdjustmentHeader_contractId', true, 'common', 10, 0, 0, 0);
	    flag = flag + validate('manageSBAdjustmentHeader_employeeSBId', true, 'select', 0, 0, 0, 0);
	    flag = flag + validate('manageSBAdjustmentHeader_monthly', true, 'select', 0, 0, 0, 0);
	    flag = flag + validate('manageSBAdjustmentHeader_description', false, 'common', 500, 0, 0, 0);
	    flag = flag + validate('manageSBAdjustmentHeader_status', true, 'select', 0, 0, 0, 0);
	    
	    return flag;
	};
</script>