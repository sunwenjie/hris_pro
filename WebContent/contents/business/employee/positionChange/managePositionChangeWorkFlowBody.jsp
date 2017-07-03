<%@page import="com.kan.hro.domain.biz.employee.EmployeePositionChangeVO"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeePositionChangeAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

	<style type="text/css">
		#systemLocation ol li label{
			margin-left: 5px;
		}
	</style>

	<div id="systemLocation" class="box toggableForm">
		<ol class="auto">
			<li id="employeeIdLI" style="width: 50%;">
				<label><bean:message bundle="public" key="public.employee2.id" />��</label>
				<label><bean:write name="employeePositionChangeForm" property="employeeId"/></label>
			</li>
			<li id="employeeIdLI" style="width: 50%;">
				<label><bean:message bundle="public" key="public.employee2.name" />��</label>
				<label><bean:write name="employeePositionChangeForm" property="employeeName"/></label>
			</li>
			<li style="width: 50%;">
				<label><bean:message bundle="public" key="public.employee2.no" />��</label>
				<label><bean:write name="employeeVO" property="employeeNo"/></label>
			</li>
			<li style="width: 50%;">
				<label><bean:message bundle="public" key="public.certificate.number" />��</label>
				<label><bean:write name="employeePositionChangeForm" property="employeeCertificateNumber"/></label>
			</li>
		</ol>
		
		
		<ol class="auto">		
			<li style="width: 50%;">
				<label><bean:message bundle="business" key="employee.position.change.oldParentBranchName" />��</label>
				<label><bean:write name="employeePositionChangeForm" property="oldParentBranchName"/></label>
			</li>
			<li style="width: 50%;">
				<logic:notEqual name="employeePositionChangeForm" property="oldParentBranchName" value="${employeePositionChangeForm.newParentBranchName }" >
					<label style="font-weight : bold;"><bean:message bundle="business" key="employee.position.change.newParentBranchName" />��</label>
					<label style="color : red;"><bean:write name="employeePositionChangeForm" property="newParentBranchName"/></label>
				</logic:notEqual>
				<logic:equal name="employeePositionChangeForm" property="oldParentBranchName" value="${employeePositionChangeForm.newParentBranchName }" >
					<label><bean:message bundle="business" key="employee.position.change.newParentBranchName" />��</label>
					<label><bean:write name="employeePositionChangeForm" property="newParentBranchName"/></label>
				</logic:equal>
			</li>
		</ol>
		
		<ol class="auto">	
			<li style="width: 50%;">
				<label><bean:message bundle="business" key="employee.position.change.oldBranchName" />��</label>
				<label><bean:write name="employeePositionChangeForm" property="oldBranchName"/></label>
			</li>
			<li style="width: 50%;">
				<logic:notEqual name="employeePositionChangeForm" property="oldBranchName" value="${employeePositionChangeForm.newBranchName }" >
					<label style="font-weight : bold;"><bean:message bundle="business" key="employee.position.change.newBranchId" />��</label>
					<label style="color : red;"><bean:write name="employeePositionChangeForm" property="newBranchName"/></label>
				</logic:notEqual>
				<logic:equal name="employeePositionChangeForm" property="oldBranchName" value="${employeePositionChangeForm.newBranchName }" >
					<label><bean:message bundle="business" key="employee.position.change.newBranchId" />��</label>
					<label><bean:write name="employeePositionChangeForm" property="newBranchName"/></label>
				</logic:equal>
			</li>
		</ol>
		
		<ol class="auto">	
			<li style="width: 50%;">
				<label><bean:message bundle="business" key="employee.position.change.oldPositionGradeName" />��</label>
				<label><bean:write name="employeePositionChangeForm" property="oldPositionGradeName"/></label>
			</li>
			<li style="width: 50%;">
				<logic:notEqual name="employeePositionChangeForm" property="oldPositionGradeName" value="${employeePositionChangeForm.newPositionGradeName }" >
					<label style="font-weight : bold;"><bean:message bundle="business" key="employee.position.change.newPositionGradeName" />��</label>
					<label style="color : red;"><bean:write name="employeePositionChangeForm" property="newPositionGradeName"/></label>
				</logic:notEqual>
				<logic:equal name="employeePositionChangeForm" property="oldPositionGradeName" value="${employeePositionChangeForm.newPositionGradeName }" >
					<label><bean:message bundle="business" key="employee.position.change.newPositionGradeName" />��</label>
					<label><bean:write name="employeePositionChangeForm" property="newPositionGradeName"/></label>
				</logic:equal>
			</li>
		</ol>
		
		<ol class="auto">	
			<li style="width: 50%;">
				<label><bean:message bundle="business" key="employee.position.change.oldPositionName" />��</label>
				<label><bean:write name="employeePositionChangeForm" property="oldPositionName"/></label>
			</li>
			<li style="width: 50%;">
				<logic:notEqual name="employeePositionChangeForm" property="oldPositionName" value="${employeePositionChangeForm.newPositionName }" >
					<label style="font-weight : bold;"><bean:message bundle="business" key="employee.position.change.newPositionId" />��</label>
					<label style="color : red;"><bean:write name="employeePositionChangeForm" property="newPositionName"/></label>
				</logic:notEqual>
				<logic:equal name="employeePositionChangeForm" property="oldPositionName" value="${employeePositionChangeForm.newPositionName }" >
					<label><bean:message bundle="business" key="employee.position.change.newPositionId" />��</label>
					<label><bean:write name="employeePositionChangeForm" property="newPositionName"/></label>
				</logic:equal>
			</li>
		</ol>
		
		<ol class="auto">		
			<li style="width: 50%;">
				<label><bean:message bundle="business" key="employee.position.change.oldParentPositionOwnersName" />��</label>
				<label><bean:write name="employeePositionChangeForm" property="oldParentPositionOwnersName"/></label>
			</li>
			<li style="width: 50%;">
				<logic:notEqual name="employeePositionChangeForm" property="oldParentPositionOwnersName" value="${employeePositionChangeForm.newParentPositionOwnersName }" >
					<label style="font-weight : bold;"><bean:message bundle="business" key="employee.position.change.newParentPositionOwnersName" />��</label>
					<label style="color : red;"><bean:write name="employeePositionChangeForm" property="newParentPositionOwnersName"/></label>
				</logic:notEqual>
				<logic:equal name="employeePositionChangeForm" property="oldParentPositionOwnersName" value="${employeePositionChangeForm.newParentPositionOwnersName }" >
					<label><bean:message bundle="business" key="employee.position.change.newParentPositionOwnersName" />��</label>
					<label><bean:write name="employeePositionChangeForm" property="newParentPositionOwnersName"/></label>
				</logic:equal>
			</li>
		</ol>
		
		<ol class="auto">
			<li style="width: 50%;">
				<label><bean:message bundle="business" key="employee.position.change.oldParentPositionName" />��</label>
				<label><bean:write name="employeePositionChangeForm" property="oldParentPositionName"/></label>
			</li>
			<li style="width: 50%;">
				<logic:notEqual name="employeePositionChangeForm" property="oldParentPositionName" value="${employeePositionChangeForm.newParentPositionName }" >
					<label style="font-weight : bold;"><bean:message bundle="business" key="employee.position.change.newParentPositionName" />��</label>
					<label style="color : red;"><bean:write name="employeePositionChangeForm" property="newParentPositionName"/></label>
				</logic:notEqual>
				<logic:equal name="employeePositionChangeForm" property="oldParentPositionName" value="${employeePositionChangeForm.newParentPositionName }" >
					<label><bean:message bundle="business" key="employee.position.change.newParentPositionName" />��</label>
					<label><bean:write name="employeePositionChangeForm" property="newParentPositionName"/></label>
				</logic:equal>
			</li>
		</ol>
		
		<logic:notEmpty name="employeePositionChangeForm" property="remark2" >
			<ol class="auto">
				<li>
					<label>New Working Title / Direct Report Manager (Biz Leader)��</label>
					<label style="color : red;"><bean:write name="employeePositionChangeForm" property="remark2"/></label>
				</li>
			</ol>
		</logic:notEmpty>
		
		<logic:notEmpty name="employeePositionChangeForm" property="remark1" >
			<ol class="auto">
				<li>
					<label>Job Role��</label>
					<label style="color : red;"><bean:write name="employeePositionChangeForm" property="decodeJobRole"/></label>
				</li>
			</ol>
		</logic:notEmpty>
		
		<ol class="auto">		
			<li style="width: 50%;">
				<label><bean:message bundle="business" key="employee.position.change.effectiveDate" />��</label>
				<label><bean:write name="employeePositionChangeForm" property="effectiveDate"/></label>
			</li>
			<li style="width: 50%;">
				<label><bean:message bundle="business" key="employee.position.change.description" />��</label>
				<label><bean:write name="employeePositionChangeForm" property="decodeChangeReason"/></label>
			</li>
			<li style="width: 50%;">
				<label><bean:message bundle="public" key="public.note" />��</label>
				<label><bean:write name="employeePositionChangeForm" property="description"/></label>
			</li>
		</ol>
	</div>
