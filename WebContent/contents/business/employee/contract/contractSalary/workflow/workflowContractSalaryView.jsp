<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="employeeContractSalaryWorkflow" class="box toggableForm">
	<ol class="auto">
		<li>
			<label><bean:message bundle="public" key="public.contract2.id" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="contractId"/></label>
		</li>
		<li>
			<label><bean:message bundle="public" key="public.contract2.name" />��</label>
			<%
				if( request.getLocale().getLanguage().equals( "zh" )){
			%>
				<label><bean:write name="employeeContractSalaryForm" property="contractNameZH"/></label>
			<%
				}else{
			%>
				<label><bean:write name="employeeContractSalaryForm" property="contractNameEN"/></label>
			<%
				}
			%>
		</li>
		<li>
			<label><bean:message bundle="public" key="public.employee2.name.cn" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="employeeNameZH"/></label>
		</li>
		<li>
			<label><bean:message bundle="public" key="public.employee2.name.en" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="employeeNameEN"/></label>
		</li>
	</ol>
	<ol class="auto">
		<li>
			<label style="font-weight : bold;"><bean:message bundle="payment" key="payment.employee.salary.item" />��</label>
			<label style="color : red;"><bean:write name="employeeContractSalaryForm" property="decodeItemId"/></label>
		</li>
	</ol>
	<ol class="auto">
		<li>
			<label style="font-weight : bold;"><bean:message bundle="payment" key="payment.employee.salary.base" />��</label>
			<label style="color : red;"><bean:write name="employeeContractSalaryForm" property="base"/></label>
		</li>
	</ol>
	<ol class="auto">
		<li>
			<label><bean:message bundle="payment" key="payment.employee.salary.cycle" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="decodeCycle"/></label>
		</li>
	</ol>
	<ol class="auto">
		<li>
			<label><bean:message bundle="payment" key="payment.employee.salary.start.date" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="startDate"/></label>
		</li>
		<li>
			<label><bean:message bundle="payment" key="payment.employee.salary.end.date" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="endDate"/></label>
		</li>
		<li>
			<label><bean:message bundle="payment" key="payment.employee.salary.formular" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="formular"/></label>
		</li>
		<li>
			<label><bean:message bundle="payment" key="payment.employee.salary.show.to.ts" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="decodeShowToTS"/></label>
		</li>
		<li>
			<label><bean:message bundle="payment" key="payment.employee.salary.probationUsing" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="decodeProbationUsing"/></label>
		</li>
		<li>
			<label><bean:message bundle="public" key="public.description" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="description"/></label>
		</li>
	</ol>
	<br/>
	<ol class="auto">
		<li>
			<a id="CG13_Link"><bean:message bundle="public" key="public.moreInfo" /></a>
		</li>
	</ol>
	<div id="CG13"  style="border: 1px solid #aaa; -webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px;margin: 2px 10px 20px 10px;padding: 10px 10px 10px 10px;display: none;" >
	<ol class="auto">
		<li>
			<label><bean:message bundle="payment" key="payment.employee.salary.type" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="decodeSalaryType"/></label>
		</li>
		<li>
			<label><bean:message bundle="payment" key="payment.employee.salary.type" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="decodeDivideType"/></label>
		</li>
	</ol>
	<ol class="auto">
		<li>
			<label><bean:message bundle="payment" key="payment.employee.salary.not.working.day" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="decodeExcludeDivideItemIds"/></label>
		</li>	
	</ol>
	<ol class="auto">
		<li>
			<label><bean:message bundle="payment" key="payment.employee.salary.base.from" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="decodeBaseFrom"/></label>
		</li>	
	</ol>
	<ol class="auto">
		<li>
			<label><bean:message bundle="payment" key="payment.employee.salary.percentage" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="percentage"/></label>
		</li>
		<li>
			<label><bean:message bundle="payment" key="payment.employee.salary.fix" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="fix"/></label>
		</li>		
	</ol>
	<ol class="auto">
		<li>
			<label><bean:message bundle="payment" key="payment.employee.salary.result.cap" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="resultCap"/></label>
		</li>
		<li>
			<label><bean:message bundle="payment" key="payment.employee.salary.result.floor" />��</label>
			<label><bean:write name="employeeContractSalaryForm" property="resultFloor"/></label>
		</li>		
	</ol>
	</div>
</div>
<style type="text/css">
	#CG13{
		border: 1px solid #aaa;
		-webkit-border-radius: 3px;
		-moz-border-radius: 3px;
		border-radius: 3px;
		margin: 2px 10px 20px 10px;
		padding: 10px 10px 10px 10px;
		display: none;
	}
</style>
<script type="text/javascript">
	$('#CG13_Link').click(function() {
	    if ($('#CG13').is(':visible')) {
	        $('#CG13').hide();
	    } else {
	        $('#CG13').show();
	    }
	});
</script>