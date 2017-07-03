<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="sbAdjustmentDetailAction.do?proc=add_object" styleClass="manageSBAdjustmentDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="sbAdjustmentHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="adjustmentHeaderId" name="adjustmentHeaderId" value="<bean:write name="sbAdjustmentHeaderForm" property="adjustmentHeaderId"/>" />
	<input type="hidden" id="detailId" name="detailId" value="<bean:write name="sbAdjustmentDetailForm" property="encodedId"/>" />
	<input type="hidden" id="monthly" name="monthly" value="" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="sbAdjustmentDetailForm" property="subAction"/>" /> 
		<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="sb" key="sb.adjustment.detail.monthly" /></label><span><bean:write name="sbAdjustmentHeaderForm" property="monthly"/></span>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="sb" key="sb.adjustment.detail.account.monthly" /></label> 
				<html:select property="accountMonthly" styleClass="manageSBAdjustmentDetail_accountMonthly">
					<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="sb" key="sb.adjustment.detail.sb.item" /><em> *</em></label> 
				<select name="itemId" class="manageSBAdjustmentDetail_itemId">
					<logic:notEmpty name="items">
						<logic:iterate id="itemMappingVO" name="items">
							<option value="<bean:write name="itemMappingVO" property="mappingId" />">
								<bean:write name="itemMappingVO" property="mappingValue"/>
							</option>
						</logic:iterate>
					</logic:notEmpty>
				</select>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="sb" key="sb.adjustment.detail.money.company" /> <img src="images/tips.png" title="<bean:message bundle="sb" key="sb.adjustment.detail.money.company.tips" />" /><em> *</em></label> 
				<html:text property="amountCompany" maxlength="8" styleClass="manageSBAdjustmentDetail_amountCompany" /> 
			</li>
			<li>
				<label><bean:message bundle="sb" key="sb.adjustment.detail.money.personal" /> <img src="images/tips.png" title="<bean:message bundle="sb" key="sb.adjustment.detail.money.personal.tips" />" /><em> *</em></label> 
				<html:text property="amountPersonal" maxlength="8" styleClass="manageSBAdjustmentDetail_amountPersonal" /> 
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageSBAdjustmentDetail_description"></html:textarea>
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageSBAdjustmentDetail_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>				
		</ol>	
	</fieldset>
</html:form>