<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="adjustmentDetailAction.do?proc=add_object" styleClass="manageAdjustmentDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="headerId" name="id" value="<bean:write name="adjustmentHeaderForm" property="encodedId"/>" />
	<input type="hidden" id="detailId" name="detailId" value="<bean:write name="adjustmentDetailForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" id="subAction" class="subAction" value="<bean:write name="adjustmentDetailForm" property="subAction"/>" /> 
		<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">	
			<li>
				<label>��Ŀ<em> *</em></label> 
				<html:hidden property="itemId" styleClass="manageAdjustmentDetail_itemId" /> 
				<input name="itemName" type="text" class="manageAdjustmentDetail_itemName"/>
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label>�ϼƣ��������룩<em> *</em></label> 
				<html:text property="billAmountPersonal" maxlength="10" styleClass="manageAdjustmentDetail_billAmountPersonal" /> 
			</li>
			<li>
				<label>�ϼƣ���˾Ӫ�գ� <em> *</em></label> 
				<html:text property="billAmountCompany" maxlength="10" styleClass="manageAdjustmentDetail_billAmountCompany" /> 
			</li>
				<li>
				<label>�ϼƣ�����֧����<em> *</em></label> 
				<html:text property="costAmountPersonal" maxlength="10" styleClass="manageAdjustmentDetail_costAmountPersonal" /> 
			</li>
			<li>
				<label>�ϼƣ���˾�ɱ��� <em> *</em></label> 
				<html:text property="costAmountCompany" maxlength="10" styleClass="manageAdjustmentDetail_costAmountCompany" /> 
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageAdjustmentDetail_description"></html:textarea>
			</li>	
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageAdjustmentDetail_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>				
		</ol>	
	</fieldset>
</html:form>