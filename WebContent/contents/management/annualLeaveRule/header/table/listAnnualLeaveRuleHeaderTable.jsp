<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder annualLeaveRuleHeaderHolder = (PagedListHolder) request.getAttribute("annualLeaveRuleHeaderHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th class="header <%=annualLeaveRuleHeaderHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listAnnualLeaveRuleHeader_form', null, null, 'nameZH', '<%=annualLeaveRuleHeaderHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.annual.leave.rule.header.name.cn" /></a>
			</th>
			<th class="header <%=annualLeaveRuleHeaderHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listAnnualLeaveRuleHeader_form', null, null, 'nameEN', '<%=annualLeaveRuleHeaderHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.annual.leave.rule.header.name.en" /></a>
			</th>
			<th class="header <%=annualLeaveRuleHeaderHolder.getCurrentSortClass("baseOn")%>">
				<a onclick="submitForm('listAnnualLeaveRuleHeader_form', null, null, 'baseOn', '<%=annualLeaveRuleHeaderHolder.getNextSortOrder("baseOn")%>', 'tableWrapper');"><bean:message bundle="management" key="management.annual.leave.rule.header.base.on" /></a>
			</th>
			<th class="header <%=annualLeaveRuleHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listAnnualLeaveRuleHeader_form', null, null, 'status', '<%=annualLeaveRuleHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="annualLeaveRuleHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="annualLeaveRuleHeaderVO" name="annualLeaveRuleHeaderHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<!-- Common Account -->
						 <logic:notEqual name="accountId" value="1">
							<logic:notEqual name="annualLeaveRuleHeaderVO" property="accountId" value="1">
								<logic:equal name="annualLeaveRuleHeaderVO" property="extended" value="2"> 
									<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="annualLeaveRuleHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="annualLeaveRuleHeaderVO" property="headerId"/>" />
								 </logic:equal>
							</logic:notEqual>
						</logic:notEqual> 
					</td>
					<td class="left">
						<a onclick="link('annualLeaveRuleDetailAction.do?proc=list_object&id=<bean:write name="annualLeaveRuleHeaderVO" property="encodedId"/>');"><bean:write name="annualLeaveRuleHeaderVO" property="nameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('annualLeaveRuleDetailAction.do?proc=list_object&id=<bean:write name="annualLeaveRuleHeaderVO" property="encodedId"/>');"><bean:write name="annualLeaveRuleHeaderVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="annualLeaveRuleHeaderVO" property="decodebaseOn"/></td>
					<td class="left"><bean:write name="annualLeaveRuleHeaderVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
		<tfoot>
			<tr class="total">
				<td colspan="5" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="annualLeaveRuleHeaderHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="annualLeaveRuleHeaderHolder" property="indexStart" /> - <bean:write name="annualLeaveRuleHeaderHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listAnnualLeaveRuleHeader_form', null, '<bean:write name="annualLeaveRuleHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listAnnualLeaveRuleHeader_form', null, '<bean:write name="annualLeaveRuleHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listAnnualLeaveRuleHeader_form', null, '<bean:write name="annualLeaveRuleHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listAnnualLeaveRuleHeader_form', null, '<bean:write name="annualLeaveRuleHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="annualLeaveRuleHeaderHolder" property="realPage" />/<bean:write name="annualLeaveRuleHeaderHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
</table>