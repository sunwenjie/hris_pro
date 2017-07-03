<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder employeeStatusHolder = (PagedListHolder)request.getAttribute("employeeStatusHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 8%" class="header <%=employeeStatusHolder.getCurrentSortClass("employeeStatusId")%>">
				<a onclick="submitForm('listemployeeStatus_form', null, null, 'employeeStatusId', '<%=employeeStatusHolder.getNextSortOrder("employeeStatusId")%>', 'tableWrapper');">
					<logic:equal value="1" name="role">雇佣状态ID</logic:equal>
					<logic:equal value="2" name="role">员工状态ID</logic:equal>
				</a>
			</th>
			<th style="width: 30%" class="header <%=employeeStatusHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listemployeeStatus_form', null, null, 'nameZH', '<%=employeeStatusHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">
					<logic:equal value="1" name="role">雇员状态名称（中文）</logic:equal>
					<logic:equal value="2" name="role">员工状态名称（中文）</logic:equal>
				</a>
			</th>
			<th style="width: 40%" class="header <%=employeeStatusHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listemployeeStatus_form', null, null, 'nameEN', '<%=employeeStatusHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">
					<logic:equal value="1" name="role">雇佣状态名称（英文）</logic:equal>
					<logic:equal value="2" name="role">员工状态名称（英文）</logic:equal>
				</a>
			</th>
			<th style="width: 10%" class="header-nosort">修改人</th>
			<th style="width: 12%" class="header-nosort">修改时间</th>
			<th style="width: 10%" class="header <%=employeeStatusHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listemployeeStatus_form', null, null, 'status', '<%=employeeStatusHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>			
	<logic:notEqual name="employeeStatusHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="employeeStatusVO" name="employeeStatusHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="employeeStatusVO" property="employeeStatusId"/>" name="chkSelectRow[]" value="<bean:write name="employeeStatusVO" property="employeeStatusId"/>" />
					</td>
					<td class="left">
						<a onclick="link('employeeStatusAction.do?proc=to_objectModify&encodedId=<bean:write name="employeeStatusVO" property="encodedId"/>');"><bean:write name="employeeStatusVO" property="employeeStatusId"/></a>
					</td>
					<td class="left">
						<bean:write name="employeeStatusVO" property="nameZH"/>
							<logic:equal name="employeeStatusVO" property="setDefault" value="1">
								<span class="agreelight">（默认）</span>
							</logic:equal>
					</td>
					<td class="left">
						<bean:write name="employeeStatusVO" property="nameEN"/>
						<logic:equal name="employeeStatusVO" property="setDefault" value="1">
							<span class="agreelight">（Default）</span>
						</logic:equal>
					</td>
					<td class="left"><bean:write name="employeeStatusVO" property="decodeModifyBy"/></td>
					<td class="left"><bean:write name="employeeStatusVO" property="decodeModifyDate"/></td>
					<td class="left"><bean:write name="employeeStatusVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="employeeStatusHolder">
		<tfoot>
			<tr class="total">
				<td colspan="7" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="employeeStatusHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="employeeStatusHolder" property="indexStart" /> - <bean:write name="employeeStatusHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listemployeeStatus_form', null, '<bean:write name="employeeStatusHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listemployeeStatus_form', null, '<bean:write name="employeeStatusHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listemployeeStatus_form', null, '<bean:write name="employeeStatusHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listemployeeStatus_form', null, '<bean:write name="employeeStatusHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="employeeStatusHolder" property="realPage" />/<bean:write name="employeeStatusHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>