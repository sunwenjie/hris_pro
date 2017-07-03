<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="incomeTaxBaseAction.do?proc=add_object" styleClass="manageIncomeTaxBase_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" id="baseId" name="id" value="<bean:write name="incomeTaxBaseForm" property="encodedId"/>" />
	<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="incomeTaxBaseForm" property="subAction"/>" /> 
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">
			<li>
				<label>�������ƣ����ģ�<em>*</em> </label> 
				<html:text property="nameZH" maxlength="100" styleClass="manageIncomeTaxBase_nameZH" /> 
			</li>	
			<li>
				<label>�������ƣ�Ӣ�ģ�</label> 
				<html:text property="nameEN" maxlength="100" styleClass="manageIncomeTaxBase_nameEN" /> 
			</li>
			<li>
				<label>��Ч����</label> 
				<html:text property="startDate" maxlength="10" styleId="startDate" styleClass="Wdate manageIncomeTaxRangeHeader_startDate" onfocus="WdatePicker({ maxDate: '#F{$dp.$D(\'endDate\')}' })" />
			</li>
			<li>
				<label>ʧЧ����</label> 
				<html:text property="endDate" maxlength="10" styleId="endDate" styleClass="Wdate manageIncomeTaxRangeHeader_endDate" />
			</li>
		</ol>
		<ol class="auto">	
			<li>
				<label>��˰������</label> 
				<html:text property="base" maxlength="20" styleClass="manageIncomeTaxBase_base" /> 
			</li>
			<li>
				<label>��˰�����㣨�⼮��</label> 
				<html:text property="baseForeigner" maxlength="20" styleClass="manageIncomeTaxBase_baseForeigner" /> 
			</li>
		</ol>
		<ol class="auto">
			<li>
				<label>�Ƿ�Ĭ��</label> 
				<html:checkbox property="isDefault" styleClass="manageIncomeTaxBase_isDefault" />
			</li>
		</ol>
		<ol class="auto">		
			<li>
				<label>����С�� </label> 
				<html:select property="accuracy" styleClass="manageIncomeTaxBase_accuracy">
					<html:optionsCollection property="accuracys" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
			<li>
				<label>������ʽ </label> 
				<html:select property="round" styleClass="manageIncomeTaxBase_round">
					<html:optionsCollection property="rounds" value="mappingId" label="mappingValue" />
				</html:select>	
			</li>
		</ol>	
		<ol class="auto">
			<li>
				<label><bean:message bundle="public" key="public.description" /></label> 
				<html:textarea property="description" styleClass="manageIncomeTaxBase_description"></html:textarea>
			</li>					
			<li>
				<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 
				<html:select property="status" styleClass="manageIncomeTaxBase_status">
					<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
				</html:select>
			</li>
		</ol>	
	</fieldset>
</html:form>