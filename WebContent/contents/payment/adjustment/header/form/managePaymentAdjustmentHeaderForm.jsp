<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="paymentAdjustmentHeaderAction.do?proc=add_object" styleClass="managePaymentAdjustmentHeader_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="adjustmentHeaderId" name="adjustmentHeaderId" class="managePaymentAdjustmentHeader_adjustmentHeaderId" value="<bean:write name="paymentAdjustmentHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="paymentAdjustmentHeaderForm" property="subAction"/>" /> 
		<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /> <img src="images/tips.png" title="<bean:message bundle="payment" key="payment.salary.adjustment.tips" />" /></label></li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="payment" key="payment.salary.adjustment.header.monthly" /><em>*</em></label> 
				<html:select property="monthly" styleClass="managePaymentAdjustmentHeader_monthly" styleId="monthly">
					<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">
			<li id="contractIdLI">
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal><em>*</em>
				</label> 
				<html:text property="contractId" maxlength="10" styleClass="managePaymentAdjustmentHeader_contractId" styleId="contractId"/>
				<a id="popupContractSearchLink" onclick="popupContractSearch();" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>
			</li>
		</ol>
		<ol class="auto">
			<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
				<label>客户ID</label> 
				<html:text disabled="true" property="clientId" maxlength="10" styleClass="managePaymentAdjustmentHeader_clientId" /> 
			</li>
		</ol>
		<ol class="auto">
			<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
				<label>客户名称（中文）</label> 
				<html:text disabled="true" property="clientNameZH" maxlength="50" styleClass="managePaymentAdjustmentHeader_clientNameZH" /> 
			</li>
			<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
				<label>客户名称（英文）</label> 
				<html:text disabled="true" property="clientNameEN" maxlength="50" styleClass="managePaymentAdjustmentHeader_clientNameEN" /> 
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</label> 
				<html:text disabled="true" property="employeeId" maxlength="10" styleId="employeeId" styleClass="managePaymentAdjustmentHeader_employeeId" /> 
			</li>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
				</label> 
				<html:text disabled="true" property="orderId" maxlength="10" styleId="orderId" styleClass="managePaymentAdjustmentHeader_orderId" /> 
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</label>
				<html:text disabled="true" property="employeeNameZH" maxlength="50" styleId="employeeNameZH" styleClass="managePaymentAdjustmentHeader_employeeNameZH" /> 
			</li>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</label> 
				<html:text disabled="true" property="employeeNameEN" maxlength="50" styleId="employeeNameEN" styleClass="managePaymentAdjustmentHeader_employeeNameEN" /> 
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="payment" key="payment.salary.adjustment.header.billAmountPersonal" /> <img src="images/tips.png" title="<bean:message bundle="payment" key="payment.salary.adjustment.tips" />" /></label> 
				<html:text disabled="true" property="billAmountPersonal" maxlength="9" styleClass="managePaymentAdjustmentHeader_billAmountPersonal" /> 
			</li>
			<li>
				<label><bean:message bundle="payment" key="payment.salary.adjustment.header.costAmountPersonal" /> <img src="images/tips.png" title="<bean:message bundle="payment" key="payment.salary.adjustment.tips" />" /></label> 
				<html:text disabled="true" property="costAmountPersonal" maxlength="9" styleClass="managePaymentAdjustmentHeader_costAmountPersonal" /> 
			</li>
			<li>
				<label><bean:message bundle="payment" key="payment.salary.adjustment.header.taxAmountPersonal" /></label> 
				<html:text property="taxAmountPersonal" maxlength="9" styleClass="managePaymentAdjustmentHeader_taxAmountPersonal" /> 
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="managePaymentAdjustmentHeader_description"></html:textarea>
			</li>
			<li>
				<label><bean:message bundle="public" key="public.status" /></label> 
				<html:select property="status" styleClass="managePaymentAdjustmentHeader_status" disabled="true">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>				
		</ol>	
	</fieldset>
</html:form>

<script type="text/javascript">
	validate_manage_primary_form = function() {
	    var flag = 0;
	    
	    flag = flag + validate('managePaymentAdjustmentHeader_monthly', true, 'select', 0, 0, 0, 0);
	    flag = flag + validate('managePaymentAdjustmentHeader_contractId', true, 'common', 10, 0, 0, 0);
	    flag = flag + validate('managePaymentAdjustmentHeader_description', false, 'common', 500, 0, 0, 0);
	    flag = flag + validate('managePaymentAdjustmentHeader_taxAmountPersonal', false, 'currency', 0, 0, 0, 0);
	    flag = flag + validate('managePaymentAdjustmentHeader_status', true, 'select', 0, 0, 0, 0);
	    
	    return flag;
	};
</script>
