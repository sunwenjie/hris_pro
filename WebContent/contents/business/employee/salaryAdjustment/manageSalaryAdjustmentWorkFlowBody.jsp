<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.hro.web.actions.biz.employee.EmployeeSalaryAdjustmentAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

	<div id="systemLocation" class="box toggableForm">
		<ol class="auto">
			<li id="employeeIdLI" style="width: 50%;">
				<label><bean:message bundle="public" key="public.contract2.id" />:</label>
				<label><bean:write name="employeeSalaryAdjustmentForm" property="contractId"/></label>
			</li>
			<li id="employeeIdLI" style="width: 50%;">
				<label><bean:message bundle="public" key="public.contract2.name" />:</label>
				<label><bean:write name="employeeSalaryAdjustmentForm" property="employeeContractName"/></label>
			</li>
			<li id="employeeIdLI" style="width: 50%;">
				<label><bean:message bundle="public" key="public.contract2.start.date" />:</label>
				<label><bean:write name="employeeSalaryAdjustmentForm" property="employeeContractStartDate"/></label>
			</li>
			<li id="employeeIdLI" style="width: 50%;">
				<label><bean:message bundle="public" key="public.contract2.end.date" />:</label>
				<label><bean:write name="employeeSalaryAdjustmentForm" property="employeeContractEndDate"/></label>
			</li>
		</ol>
		<ol class="auto">
			<li style="width: 50%;">
				<label><bean:message bundle="public" key="public.employee2.id" />:</label>
				<label><bean:write name="employeeSalaryAdjustmentForm" property="employeeId"/></label>
			</li>
		</ol>	
		<ol class="auto">	
			<li id="employeeIdLI" style="width: 50%;">
				<label><bean:message bundle="public" key="public.employee2.name" />:</label>
				<label><bean:write name="employeeSalaryAdjustmentForm" property="employeeName"/></label>
			</li>
			<li style="width: 50%;">
				<label><bean:message bundle="public" key="public.certificate.number" />:</label>
				<label><bean:write name="employeeSalaryAdjustmentForm" property="employeeCertificateNumber"/></label>
			</li>
		</ol>
		<ol class="auto">		
			<li style="width: 50%;">
				<label><bean:message bundle="business" key="employee.salary.adjustment.item" />:</label>
				<%
					if( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" )){
				%>
					<label><bean:write name="employeeSalaryAdjustmentForm" property="itemNameZH"/></label>
				<%
					}else{
				%>
					<label><bean:write name="employeeSalaryAdjustmentForm" property="itemNameEN"/></label>
				<% 
					}
				%>
			</li>
		</ol>	
		<ol class="auto">		
			<li style="width: 50%;">
				<label><bean:message bundle="business" key="employee.salary.adjustment.adjustment.before.base" />:</label>
				<label><bean:write name="employeeSalaryAdjustmentForm" property="oldBase"/></label>
			</li>
		</ol>
		<ol class="auto">
			<li style="width: 50%;">
				<label><bean:message bundle="business" key="employee.salary.adjustment.adjustment.before.start.date" />:</label>
				<label><bean:write name="employeeSalaryAdjustmentForm" property="oldStartDate"/></label>
			</li>
			<li style="width: 50%;">
				<label><bean:message bundle="business" key="employee.salary.adjustment.adjustment.before.end.date" />:</label>
				<label><bean:write name="employeeSalaryAdjustmentForm" property="oldEndDate"/></label>
			</li>
		</ol>	
		<ol class="auto">		
			<li style="width: 50%;">
				<label style="font-weight : bold;"><bean:message bundle="business" key="employee.salary.adjustment.adjustment.after.base" />:</label>
				<label style="color : red;"><bean:write name="employeeSalaryAdjustmentForm" property="newBase"/></label>
			</li>
		</ol>
		<ol class="auto">
			<li style="width: 50%;">
				<label style="font-weight : bold;"><bean:message bundle="business" key="employee.salary.adjustment.adjustment.after.start.date" />:</label>
				<label style="color : red;"><bean:write name="employeeSalaryAdjustmentForm" property="newStartDate"/></label>
			</li>
			<li style="width: 50%;">
				<label style="font-weight : bold;"><bean:message bundle="business" key="employee.salary.adjustment.adjustment.after.end.date" />:</label>
				<label style="color : red;"><bean:write name="employeeSalaryAdjustmentForm" property="newEndDate"/></label>
			</li>
		</ol>	
		<ol class="auto">
			<li style="width: 50%;">
				<label><bean:message bundle="business" key="employee.position.change.description" />:</label>
				<label><bean:write name="employeeSalaryAdjustmentForm" property="decodeChangeReason"/></label>
			</li>
			<li style="width: 50%;">
				<label><bean:message bundle="public" key="public.note" />:</label>
				<label><bean:write name="employeeSalaryAdjustmentForm" property="description"/></label>
			</li>
		</ol>
	</div>
