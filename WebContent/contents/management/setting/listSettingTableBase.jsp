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
			
			<tr class="odd">
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
			
		</tbody>
	</logic:notEmpty>
</table>
</html:form>
