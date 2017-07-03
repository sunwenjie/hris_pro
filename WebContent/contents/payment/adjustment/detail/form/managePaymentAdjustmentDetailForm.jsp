<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="paymentAdjustmentDetailAction.do?proc=add_object" styleClass="managePaymentAdjustmentDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="adjustmentHeaderId" name="adjustmentHeaderId" class="managePaymentAdjustmentDetail_adjustmentHeaderId" value="<bean:write name="paymentAdjustmentDetailForm" property="encodedAdjustmentHeaderId"/>" />
	<input type="hidden" id="adjustmentDetailId" name="adjustmentDetailId" class="managePaymentAdjustmentDetail_adjustmentDetailId" value="<bean:write name="paymentAdjustmentDetailForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="paymentAdjustmentDetailForm" property="subAction"/>" /> 
		<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="payment" key="payment.salary.adjustment.detail" /><em> *</em></label> 
				<html:select property="itemId" styleClass="managePaymentAdjustmentDetail_itemId">
					<html:optionsCollection property="items" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="payment" key="payment.salary.adjustment.detail.billAmountPersonal" /><em> *</em></label> 
				<html:text property="billAmountPersonal" maxlength="9" styleClass="managePaymentAdjustmentDetail_billAmountPersonal" /> 
			</li>
			<li>
				<label><bean:message bundle="payment" key="payment.salary.adjustment.detail.costAmountPersonal" /><em> *</em></label> 
				<html:text property="costAmountPersonal" maxlength="9" styleClass="managePaymentAdjustmentDetail_costAmountPersonal" /> 
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="managePaymentAdjustmentDetail_description"></html:textarea>
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="managePaymentAdjustmentDetail_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>				
		</ol>	
	</fieldset>
</html:form>