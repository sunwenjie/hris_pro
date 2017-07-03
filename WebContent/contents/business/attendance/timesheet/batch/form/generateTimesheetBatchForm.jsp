<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="timesheetBatchAction.do?proc=add_object" styleClass="timesheetBatch_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="timesheetBatchForm" property="subAction" />" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="business" key="business.ts.month" /><em> *</em></label> 
				<html:select property="monthly" styleClass="timesheetBatch_monthly">
					<html:optionsCollection property="last2Months" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label><bean:message bundle="security" key="security.entity" /></label> 
				<html:select property="entityId" styleClass="timesheetBatch_entityId">
					<html:optionsCollection property="entitys" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li>
				<label><bean:message bundle="security" key="security.business.type" /></label> 
				<html:select property="businessTypeId" styleClass="timesheetBatch_businessTypeId">
					<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
			<li <logic:equal name="role" value="2">style="display :none;"</logic:equal>>
				<label>¿Í»§ID</label> 
				<html:text property="clientId" maxlength="10" styleClass="timesheetBatch_clientId" styleId="clientId" /> 
				<a onclick="popupClientSearch();" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>
			</li>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
				</label>
				<logic:equal name="role" value="1">
					<html:text property="orderId" maxlength="10" styleClass="timesheetBatch_orderId" styleId="orderId" /> 
					<a onclick="popupOrderSearch();" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>
				</logic:equal>
				<logic:equal name="role" value="2">
					<logic:notEmpty name="clientOrderHeaderMappingVOs">
						<html:select property="orderId" styleClass="timesheetBatch_orderId">
							<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
						</html:select>
  					</logic:notEmpty>
				</logic:equal>
			</li>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</label> 
				<html:text property="employeeId" maxlength="10" styleClass="timesheetBatch_employeeId" styleId="employeeId"/> 
				<a onclick="popupEmployeeSearch();" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>
			</li>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
				</label> 
				<html:text property="contractId" maxlength="10" styleId="contractId" styleClass="timesheetBatch_contractId" /> 
				<a onclick="popupContractSearch()" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="timesheetBatch_description"></html:textarea>
			</li>
		</ol>
	</fieldset>
</html:form>