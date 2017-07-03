<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 5%" class="header-nosort" >
				<bean:message bundle="sb" key="sb.id" />
			</th>
			<th style="width: 7%" class="header-nosort" >
				<bean:message bundle="sb" key="sb.no" />
			</th>
			<th style="width: 12%" class="header-nosort">
				<bean:message bundle="sb" key="sb.name" />
			</th>
			<th style="width: 16%" class="header-nosort" >
				<bean:message bundle="sb" key="sb.base.company" />
			</th>
			<th style="width: 16%" class="header-nosort" >
				<bean:message bundle="sb" key="sb.base.personal" />
			</th>
			<th style="width: 7%" class="header-nosort" >
				<bean:message bundle="sb" key="sb.rate.company" />
			</th>
			<th style="width: 7%" class="header-nosort" >
				<bean:message bundle="sb" key="sb.rate.personal" />
			</th>
			<th style="width: 7%" class="header-nosort" >
				<bean:message bundle="sb" key="sb.fix.company" />
			</th>
			<th style="width: 7%" class="header-nosort" >
				<bean:message bundle="sb" key="sb.fix.personal" />
			</th>
			<th style="width: 8%" class="header-nosort" >
				<bean:message bundle="management" key="management.sb.solution.header.start.date.limit" />
			</th>
			<th style="width: 8%" class="header-nosort" >
				<bean:message bundle="management" key="management.sb.solution.header.end.date.limit" />
			</th>
		</tr>
	</thead>
	<tbody>
		<logic:iterate id="employeeContractSBDetailVO" name="employeeContractSBDetailVOs" indexId="number">
			<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
				<td class="left">
					<input type="hidden" name="solutionDetailIdArray" id="manageSocialBenefitSolutionDetail_sysDetailId" value="<bean:write name="employeeContractSBDetailVO" property="solutionDetailId"/>"/>
					<bean:write name="employeeContractSBDetailVO" property="itemId"/>
				</td>
				<td class="left">
					<bean:write name="employeeContractSBDetailVO" property="itemNo"/>
				</td>
				<td class="left">
					<bean:write name="employeeContractSBDetailVO" property="name"/>
				</td>
				<td id="companyValueTD" class="left">
					<input type="hidden" id="companyFloor" value="<bean:write name='employeeContractSBDetailVO' property='companyFloor'/>">
					<bean:write name="employeeContractSBDetailVO" property="companyFloor"/>&#8804;
					<input name="baseCompanyArray" value="<bean:write name="employeeContractSBDetailVO" property="baseCompany"/>" maxValue="<bean:write name="employeeContractSBDetailVO" property="companyCap"/>" minValue="<bean:write name="employeeContractSBDetailVO" property="companyFloor"/>"  class="small-ex" >
					&#8804; <bean:write name="employeeContractSBDetailVO" property="companyCap"/>
					<input type="hidden" id="companyCap" value="<bean:write name='employeeContractSBDetailVO' property='companyCap'/>">
				</td>
				<td id="personalValueTD" class="left">
					<input type="hidden" id="personalFloor" value="<bean:write name='employeeContractSBDetailVO' property='personalFloor'/>">
					<bean:write name="employeeContractSBDetailVO" property="personalFloor"/>&#8804;
					<input name="basePersonalArray" value="<bean:write name="employeeContractSBDetailVO" property="basePersonal"/>" maxValue="<bean:write name="employeeContractSBDetailVO" property="personalCap"/>" minValue="<bean:write name="employeeContractSBDetailVO" property="personalFloor"/>"  class="small-ex">
					&#8804; <bean:write name="employeeContractSBDetailVO" property="personalCap"/>
					<input type="hidden" id="personalCap" value="<bean:write name='employeeContractSBDetailVO' property='personalCap'/>">
				</td>
				<td class="right">
					<bean:write name="employeeContractSBDetailVO" property="companyPercent"/>
				</td>
				<td class="right">
					<bean:write name="employeeContractSBDetailVO" property="personalPercent"/>
				</td>
				<td class="right">
					<bean:write name="employeeContractSBDetailVO" property="companyFixAmount"/>
				</td>
				<td class="right">
					<bean:write name="employeeContractSBDetailVO" property="personalFixAmount"/>
				</td>
				<td class="left">
					<bean:write name="employeeContractSBDetailVO" property="startDateLimit"/>
				</td>	
				<td class="left">
					<bean:write name="employeeContractSBDetailVO" property="endDateLimit"/>
				</td>
			</tr>
		</logic:iterate>
	</tbody>
	<tfoot>
		<tr class="total">
			<td colspan="11" class="left"> 
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º<logic:empty name="countEmployeeContractSBDetailVOs">0</logic:empty><logic:notEmpty name="countEmployeeContractSBDetailVOs"><bean:write name="countEmployeeContractSBDetailVOs" /></logic:notEmpty></label>
				<label>&nbsp;&nbsp;</label>
				<label>&nbsp;&nbsp;</label>
				<label>&nbsp;</label>
				<label>&nbsp;</label>
				<label>&nbsp;</label>
				<label>&nbsp;&nbsp;</label>&nbsp;
			</td>					
		</tr>
	</tfoot>	
</table>
