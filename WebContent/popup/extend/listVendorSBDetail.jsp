<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="tab" style="display:none"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="changeTab(1,1)" class="hover first">�籣��������ϸ</li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab">
			<div id="tableWrapper">
				<table class="table hover" id="resultTable">
					<thead>
						<tr>
							<th style="width: 5%" class="header-nosort" >
								 ��Ŀ ID
							</th>
							<th style="width: 7%" class="header-nosort" >
								 ��Ŀ���
							</th>
							<th style="width: 12%" class="header-nosort">
								 ��Ŀ���� 
							</th>
							<th style="width: 16%" class="header-nosort" >
								 �������� 
							</th>
							<th style="width: 16%" class="header-nosort" >
								 ���������� 
							</th>
							<th style="width: 7%" class="header-nosort" >
								 ���� %����
							</th>
							<th style="width: 7%" class="header-nosort" >
								 ���� %������ 
							</th>
							<th style="width: 7%" class="header-nosort" >
								 �̶����� 
							</th>
							<th style="width: 7%" class="header-nosort" >
								 �̶��𣨸��� 
							</th>
							<th style="width: 8%" class="header-nosort" >
								 �걨��ʼ����
							</th>
							<th style="width: 8%" class="header-nosort" >
								 �걨��������
							</th>
						</tr>
					</thead>
					<tbody>
						<logic:notEmpty name="employeeContractSBDetailVOs">
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
									<td class="left">
										<bean:write name="employeeContractSBDetailVO" property="companyFloor"/>&#8804;
										<input name="baseCompanyArray" value="<bean:write name="employeeContractSBDetailVO" property="baseCompany"/>" class="small-ex" >
										&#8804; <bean:write name="employeeContractSBDetailVO" property="companyCap"/>
									</td>
									<td class="left">
										<bean:write name="employeeContractSBDetailVO" property="personalFloor"/>&#8804;
										<input name="basePersonalArray" value="<bean:write name="employeeContractSBDetailVO" property="basePersonal"/>" class="small-ex">
										&#8804; <bean:write name="employeeContractSBDetailVO" property="personalCap"/>
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
						</logic:notEmpty>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<input type="hidden" id="forwardURL" name="forwardURL" value="" />

<script type="text/javascript">
		var employeeSBId = '<bean:write name="employeeContractSBForm" property="employeeSBId"/>';
</script>