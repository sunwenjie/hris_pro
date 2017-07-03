<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%																									
	final PagedListHolder annualLeaveRuleDetailHolder = (PagedListHolder)request.getAttribute("annualLeaveRuleDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th class="header <%=annualLeaveRuleDetailHolder.getCurrentSortClass("positionGradeId")%>">
				<a onclick="submitForm('listAnnualLeaveRuleDetail_form', null, null, 'positionGradeId', '<%=annualLeaveRuleDetailHolder.getNextSortOrder("positionGradeId")%>', 'tableWrapper');"><bean:message bundle="security" key="security.position.grade" /></a>
			</th>
			<th class="header <%=annualLeaveRuleDetailHolder.getCurrentSortClass("seniority")%>">
				<a onclick="submitForm('listAnnualLeaveRuleDetail_form', null, null, 'seniority', '<%=annualLeaveRuleDetailHolder.getNextSortOrder("seniority")%>', 'tableWrapper');"><bean:message bundle="management" key="management.annual.leave.rule.detail.seniority" /></a>
			</th>
			<th class="header <%=annualLeaveRuleDetailHolder.getCurrentSortClass("legalHours")%>">
				<a onclick="submitForm('listAnnualLeaveRuleDetail_form', null, null, 'legalHours', '<%=annualLeaveRuleDetailHolder.getNextSortOrder("legalHours")%>', 'tableWrapper');"><bean:message bundle="management" key="management.annual.leave.rule.detail.legal.hours" /></a>
			</th>
			<th class="header <%=annualLeaveRuleDetailHolder.getCurrentSortClass("benefitHours")%>">
				<a onclick="submitForm('listAnnualLeaveRuleDetail_form', null, null, 'benefitHours', '<%=annualLeaveRuleDetailHolder.getNextSortOrder("benefitHours")%>', 'tableWrapper');"><bean:message bundle="management" key="management.annual.leave.rule.detail.benefit.hours" /></a>
			</th>
			<th class="header <%=annualLeaveRuleDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listAnnualLeaveRuleDetail_form', null, null, 'status', '<%=annualLeaveRuleDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
 	<logic:notEqual name="annualLeaveRuleDetailHolder" property="holderSize" value="0">
		 <tbody>
			<logic:iterate id="annualLeaveRuleDetailVO" name="annualLeaveRuleDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="annualLeaveRuleDetailVO" property="detailId"/>" name="chkSelectRow[]" value="<bean:write name="annualLeaveRuleDetailVO" property="detailId"/>" />
					</td>
					<td class="left"><a onclick="annualLeaveRuleDetailModify('<bean:write name="annualLeaveRuleDetailVO" property="detailId"/>');"><bean:write name="annualLeaveRuleDetailVO" property="decodePositionGradeId"/></a></td>
					<td class="left"><a onclick="annualLeaveRuleDetailModify('<bean:write name="annualLeaveRuleDetailVO" property="detailId"/>');"><bean:write name="annualLeaveRuleDetailVO" property="decodeSeniority"/></a></td>
					<td class="right"><bean:write name="annualLeaveRuleDetailVO" property="legalHours"/></td>
					<td class="right"><bean:write name="annualLeaveRuleDetailVO" property="benefitHours"/></td>
					<td class="left"><bean:write name="annualLeaveRuleDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody> 
	</logic:notEqual> 
	<tfoot>
		<tr class="total">
			<td colspan="6" class="left"> 
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="annualLeaveRuleDetailHolder" property="holderSize" /></label>
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="annualLeaveRuleDetailHolder" property="indexStart" /> - <bean:write name="annualLeaveRuleDetailHolder" property="indexEnd" /></label>
				<label>&nbsp;&nbsp;<a onclick="submitForm('listAnnualLeaveRuleDetail_form', null, '<bean:write name="annualLeaveRuleDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
				<label>&nbsp;<a onclick="submitForm('listAnnualLeaveRuleDetail_form', null, '<bean:write name="annualLeaveRuleDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
				<label>&nbsp;<a onclick="submitForm('listAnnualLeaveRuleDetail_form', null, '<bean:write name="annualLeaveRuleDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
				<label>&nbsp;<a onclick="submitForm('listAnnualLeaveRuleDetail_form', null, '<bean:write name="annualLeaveRuleDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="annualLeaveRuleDetailHolder" property="realPage" />/<bean:write name="annualLeaveRuleDetailHolder" property="pageCount" /></label>&nbsp;
			</td>					
		</tr>
	</tfoot>
</table>