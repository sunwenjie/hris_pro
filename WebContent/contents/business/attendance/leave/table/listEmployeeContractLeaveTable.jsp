<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="header-nosort">
				<bean:message bundle="business" key="business.leave.setting.id" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="business.leave.item.id" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="business.leave.type" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="business.leave.toal.legal.hours" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="business.leave.left.legal.hours" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="business.leave.toal.benefit.hours" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="business.leave.left.benefit.hours" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="business.leave.use.end.date" />
			</th>
		</tr>
	</thead>
	<tbody>
		<logic:present name="employeeContractLeaveVOs">
			<logic:iterate id="employeeContractLeaveVO" name="employeeContractLeaveVOs" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>' id="tr_itemId_<bean:write name="employeeContractLeaveVO" property="itemId"/>">
					<td class="left"><bean:write name="employeeContractLeaveVO" property="employeeLeaveId"/></td>
					<td class="left"><bean:write name="employeeContractLeaveVO" property="itemId"/></td>
					<td class="left">
						<bean:write name="employeeContractLeaveVO" property="decodeItemId"/>
						<%-- 如果是用明年的假期，提示框 --%>	
						<logic:notEmpty name="employeeContractLeaveVO" property="useNextYearHours">
							<logic:equal name="employeeContractLeaveVO" property="useNextYearHours" value="1">
								<img src="images/tips.png" id="useNextYearHours_tips" title="<bean:message bundle="public" key="img.title.tips.next.year.annual.leave" />" />
							</logic:equal>
						</logic:notEmpty>
					</td>
					
					<!-- 年假Start-->
					<logic:equal name="employeeContractLeaveVO" property="itemId" value="41">
						<!-- 法定总计小时数、法定剩余小时数 -->
						<td class="right">
							<bean:write name="employeeContractLeaveVO" property="legalQuantity" />
						</td>
						<logic:equal name="employeeContractLeaveVO" property="leftLegalQuantity" value="0">
							<td class="right highlight"><bean:write name="employeeContractLeaveVO" property="leftLegalQuantity"/></td>
						</logic:equal>
						<logic:notEqual name="employeeContractLeaveVO" property="leftLegalQuantity" value="0">
							<td class="right agreelight"><bean:write name="employeeContractLeaveVO" property="leftLegalQuantity"/></td>
						</logic:notEqual>
					
						<!-- 福利总计小时数、福利剩余小时数 -->
						<td class="right">
							<bean:write name="employeeContractLeaveVO" property="benefitQuantity"/>
						</td>
						<logic:equal name="employeeContractLeaveVO" property="leftBenefitQuantity" value="0">
							<td class="right highlight">
								<bean:write name="employeeContractLeaveVO" property="leftBenefitQuantity"/>
							</td>
						</logic:equal>
						<logic:notEqual name="employeeContractLeaveVO" property="leftBenefitQuantity" value="0">
							<td class="right agreelight">
								<bean:write name="employeeContractLeaveVO" property="leftBenefitQuantity"/>
							</td>
						</logic:notEqual>
					</logic:equal>
					
					<logic:equal name="employeeContractLeaveVO" property="itemId" value="48">
						<!-- 法定总计小时数、法定剩余小时数 -->
						<td class="right">
							<bean:write name="employeeContractLeaveVO" property="legalQuantity" />
						</td>
						<logic:equal name="employeeContractLeaveVO" property="leftLegalQuantity" value="0">
							<td class="right highlight"><bean:write name="employeeContractLeaveVO" property="leftLegalQuantity"/></td>
						</logic:equal>
						<logic:notEqual name="employeeContractLeaveVO" property="leftLegalQuantity" value="0">
							<td class="right agreelight"><bean:write name="employeeContractLeaveVO" property="leftLegalQuantity"/></td>
						</logic:notEqual>
					
						<!-- 福利总计小时数、福利剩余小时数 -->
						<td class="right">
							<bean:write name="employeeContractLeaveVO" property="benefitQuantity"/>
						</td>
						<logic:equal name="employeeContractLeaveVO" property="leftBenefitQuantity" value="0">
							<td class="right highlight">
								<bean:write name="employeeContractLeaveVO" property="leftBenefitQuantity"/>
							</td>
						</logic:equal>
						<logic:notEqual name="employeeContractLeaveVO" property="leftBenefitQuantity" value="0">
							<td class="right agreelight">
								<bean:write name="employeeContractLeaveVO" property="leftBenefitQuantity"/>
							</td>
						</logic:notEqual>
					</logic:equal>
					
					<logic:equal name="employeeContractLeaveVO" property="itemId" value="49">
						<!-- 法定总计小时数、法定剩余小时数 -->
						<td class="right">
							<bean:write name="employeeContractLeaveVO" property="legalQuantity" />
						</td>
						<logic:equal name="employeeContractLeaveVO" property="leftLegalQuantity" value="0">
							<td class="right highlight"><bean:write name="employeeContractLeaveVO" property="leftLegalQuantity"/></td>
						</logic:equal>
						<logic:notEqual name="employeeContractLeaveVO" property="leftLegalQuantity" value="0">
							<td class="right agreelight"><bean:write name="employeeContractLeaveVO" property="leftLegalQuantity"/></td>
						</logic:notEqual>
					
						<!-- 福利总计小时数、福利剩余小时数 -->
						<td class="right">
							<bean:write name="employeeContractLeaveVO" property="benefitQuantity"/>
						</td>
						<logic:equal name="employeeContractLeaveVO" property="leftBenefitQuantity" value="0">
							<td class="right highlight">
								<bean:write name="employeeContractLeaveVO" property="leftBenefitQuantity"/>
							</td>
						</logic:equal>
						<logic:notEqual name="employeeContractLeaveVO" property="leftBenefitQuantity" value="0">
							<td class="right agreelight">
								<bean:write name="employeeContractLeaveVO" property="leftBenefitQuantity"/>
							</td>
						</logic:notEqual>
					</logic:equal>
					<!-- 年假 End -->
					
					<!-- 其他Start -->
					<logic:notEqual name="employeeContractLeaveVO" property="itemId" value="41">
					<logic:notEqual name="employeeContractLeaveVO" property="itemId" value="48">
					<logic:notEqual name="employeeContractLeaveVO" property="itemId" value="49">
						<!-- 福利总计小时数、福利剩余小时数 -->
						<td class="right">
							<bean:write name="employeeContractLeaveVO" property="benefitQuantity"/>
						</td>
						<logic:equal name="employeeContractLeaveVO" property="leftBenefitQuantity" value="0">
							<td class="right highlight">
								<bean:write name="employeeContractLeaveVO" property="leftBenefitQuantity"/>
							</td>
						</logic:equal>
						<logic:notEqual name="employeeContractLeaveVO" property="leftBenefitQuantity" value="0">
							<td class="right agreelight">
								<bean:write name="employeeContractLeaveVO" property="leftBenefitQuantity"/>
							</td>
						</logic:notEqual>
						
						<!-- 法定总计小时数、法定剩余小时数 -->
						<td class="right"></td>
						<td class="right highlight"></td>
					</logic:notEqual>
					</logic:notEqual>	
					</logic:notEqual>
					<!-- 其他 End -->
					<td>
						<logic:equal name="employeeContractLeaveVO" property="itemId" value="48">
							<bean:write name="employeeContractLeaveVO" property="leftLastYearLegalQuantityEndDate"/>
						</logic:equal>
						<logic:equal name="employeeContractLeaveVO" property="itemId" value="49">
							<bean:write name="employeeContractLeaveVO" property="leftLastYearBenefitQuantityEndDate"/>
						</logic:equal>
					</td>
				</tr>
			</logic:iterate>
		</logic:present>
	</tbody>
	<tfoot>
		<tr class="total">
			<td colspan="11" class="left"> 
				<label>&nbsp;<bean:message bundle="public" key="page.total" />：<logic:empty name="countEmployeeContractLeaveVO">0</logic:empty><logic:notEmpty name="countEmployeeContractLeaveVO"><bean:write name="countEmployeeContractLeaveVO" /></logic:notEmpty></label>
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