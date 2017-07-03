<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.domain.management.SettingVO"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	SettingVO settingVO = (SettingVO) request.getAttribute("settingVO");
%>
<html:form action="settingAction.do?proc=modify_object" styleClass="modifySetting_form">
<%= BaseAction.addToken( request ) %>
<input type="hidden" name="subAction" id="subAction" value="viewObject" />
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 33%" class="header-nosort">
				<bean:message bundle="management" key="management.dashboard.name" />
			</th>
			<th style="width: 33%" class="header-nosort">
				<bean:message bundle="management" key="management.dashboard.index" />
			</th>
			<th style="width: 34%" class="header-nosort">
				<bean:message bundle="management" key="management.dashboard.display" />
			</th>
		</tr>
	</thead>
	<logic:notEmpty name="settingVO">
		<tbody>
			<tr class="even">
				<td class="left">
					<bean:message bundle="management" key="management.dashboard.base.info" />
					<logic:equal value="2" name="settingVO" property="baseInfo">
						<label class="highlight"><bean:message bundle="public" key="public.stop" /></label>
					</logic:equal>
				</td>
				<td class="left">
					<input type="text" name="baseInfoRank" class="manageSetting_baseInfoRank" value='<bean:write name="settingVO" property="baseInfoRank"></bean:write>'/>
				</td>
				<td class="left">
					<html:select name="settingVO" property="baseInfo">
						<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
					</html:select>
				</td>
			</tr>
			<tr class="odd">
				<td class="left">
					<bean:message bundle="management" key="management.dashboard.notice" />
					<logic:equal value="2" name="settingVO" property="message">
						<label class="highlight"><bean:message bundle="public" key="public.stop" /></label>
					</logic:equal>
				</td>
				<td class="left">
					<input type="text" name="messageRank" class="manageSetting_messageRank" value='<bean:write name="settingVO" property="messageRank"></bean:write>'/>
				</td>
				<td class="left">
					<html:select name="settingVO" property="message">
						<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
					</html:select>
				</td>
			</tr>
			<tr class="even">
				<td class="left">
					<bean:message bundle="management" key="management.dashboard.data.view" />
					<logic:equal value="2" name="settingVO" property="dataView">
						<label class="highlight"><bean:message bundle="public" key="public.stop" /></label>
					</logic:equal>
				</td>
				<td class="left">
					<input type="text" name="dataViewRank" class="manageSetting_dataViewRank" value='<bean:write name="settingVO" property="dataViewRank"></bean:write>'/>
				</td>
				<td class="left">
					<html:select name="settingVO" property="dataView">
						<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
					</html:select>
				</td>
			</tr>
			
			<tr class="odd">
				<td class="left">
					<bean:message bundle="management" key="management.dashboard.contract2" />
					<logic:equal value="2" name="settingVO" property="contractService">
						<label class="highlight"><bean:message bundle="public" key="public.stop" /></label>
					</logic:equal>
				</td>
				<td class="left">
					<input type="text" name="contractServiceRank" class="manageSetting_contractServiceRank" value='<bean:write name="settingVO" property="contractServiceRank"></bean:write>'/>
				</td>
				<td class="left">
					<html:select name="settingVO" property="contractService">
						<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
					</html:select>
				</td>
			</tr>
			<tr class="even">
				<td class="left">
					<bean:message bundle="management" key="management.dashboard.attendance" />
					<logic:equal value="2" name="settingVO" property="attendance">
						<label class="highlight"><bean:message bundle="public" key="public.stop" /></label>
					</logic:equal>
				</td>
				<td class="left">
					<input type="text" name="attendanceRank" class="manageSetting_attendanceRank" value='<bean:write name="settingVO" property="attendanceRank"></bean:write>'/>
				</td>
				<td class="left">
					<html:select name="settingVO" property="attendance">
						<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
					</html:select>
				</td>
			</tr>
			<tr class="odd">
				<td class="left">
					<bean:message bundle="management" key="management.dashboard.sb" />
					<logic:equal value="2" name="settingVO" property="sb">
						<label class="highlight"><bean:message bundle="public" key="public.stop" /></label>
					</logic:equal>
				</td>
				<td class="left">
					<input type="text" name="sbRank" class="manageSetting_sbRank" value='<bean:write name="settingVO" property="sbRank"></bean:write>'/>
				</td>
				<td class="left">
					<html:select name="settingVO" property="sb">
						<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
					</html:select>
				</td>
			</tr>
			<tr class="even">
				<td class="left">
					<bean:message bundle="management" key="management.dashboard.cb" />
					<logic:equal value="2" name="settingVO" property="cb">
						<label class="highlight"><bean:message bundle="public" key="public.stop" /></label>
					</logic:equal>
				</td>
				<td class="left">
					<input type="text" name="cbRank" class="manageSetting_cbRank" value='<bean:write name="settingVO" property="cbRank"></bean:write>'/>
				</td>
				<td class="left">
					<html:select name="settingVO" property="cb">
						<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
					</html:select>
				</td>
			</tr>
			
			<tr class="even">
				<td class="left">
					<bean:message bundle="management" key="managment.dashboard.payment" />
					<logic:equal value="2" name="settingVO" property="payment">
						<label class="highlight"><bean:message bundle="public" key="public.stop" /></label>
					</logic:equal>
				</td>
				<td class="left">
					<input type="text" name="paymentRank" class="manageSetting_paymentRank" value='<bean:write name="settingVO" property="paymentRank"></bean:write>'/>
				</td>
				<td class="left">
					<html:select name="settingVO" property="payment">
						<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
					</html:select>
				</td>
			</tr>
			
			<tr class="even">
				<td class="left">
					<bean:message bundle="management" key="management.dashboard.employee.change" />
					<logic:equal value="2" name="settingVO" property="employeeChange">
						<label class="highlight"><bean:message bundle="public" key="public.stop" /></label>
					</logic:equal>
				</td>
				<td class="left">
					<input type="text" name="employeeChangeRank" class="manageSetting_employeeChangeRank" value='<bean:write name="settingVO" property="employeeChangeRank"></bean:write>'/>
				</td>
				<td class="left">
					<html:select name="settingVO" property="employeeChange">
						<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
					</html:select>
				</td>
			</tr>
			
			
			<!-- HR SERVICE ONLY -->
			<logic:equal value="1" name="role">
			<tr class="odd">
				<td class="left">
				收款
				<logic:equal value="2" name="settingVO" property="income">
					<label class="highlight"><bean:message bundle="public" key="public.stop" /></label>
				</logic:equal>
				</td>
				<td class="left">
					<input type="text" name="incomeRank" class="manageSetting_incomeRank" value='<bean:write name="settingVO" property="incomeRank"></bean:write>'/>
				</td>
				<td class="left">
					<html:select name="settingVO" property="income">
						<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
					</html:select>
				</td>
			</tr>
			<tr class="odd">
				<td class="left">
				商务合同
				<logic:equal value="2" name="settingVO" property="clientContract">
						<label class="highlight">（停用）</label>
				</logic:equal>
				</td>
				<td class="left">
					<input type="text" name="clientContractRank" class="manageSetting_clientContractRank" value='<bean:write name="settingVO" property="clientContractRank"></bean:write>'/>
				</td>
				<td class="left">
					<html:select name="settingVO" property="clientContract">
						<html:option value="1">是</html:option>
						<html:option value="2">否</html:option>
					</html:select>
				</td>
			</tr>
			<tr class="even">
				<td class="left">
				订单
				<logic:equal value="2" name="settingVO" property="orders">
						<label class="highlight">（停用）</label>
				</logic:equal>
				</td>
				<td class="left">
					<input type="text" name="ordersRank" class="manageSetting_ordersRank" value='<bean:write name="settingVO" property="ordersRank"></bean:write>'/>
				</td>
				<td class="left">
					<html:select name="settingVO" property="orders">
						<html:option value="1">是</html:option>
						<html:option value="2">否</html:option>
					</html:select>
				</td>
			</tr>
			<tr class="odd">
				<td class="left">
				结算
				<logic:equal value="2" name="settingVO" property="settlement">
						<label class="highlight">（停用）</label>
				</logic:equal>
				</td>
				<td class="left">
					<input type="text" name="settlementRank" class="manageSetting_settlementRank" value='<bean:write name="settingVO" property="settlementRank"></bean:write>'/>
				</td>
				<td class="left">
					<html:select name="settingVO" property="settlement">
						<html:option value="1">是</html:option>
						<html:option value="2">否</html:option>
					</html:select>
				</td>
			</tr>
			</logic:equal>
			
		</tbody>
	</logic:notEmpty>
</table>
</html:form>
