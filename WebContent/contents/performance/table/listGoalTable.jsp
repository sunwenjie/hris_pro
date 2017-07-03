<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import=" com.kan.base.web.actions.management.BankAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder selfAssessmentHolder = (PagedListHolder) request.getAttribute("selfAssessmentHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th class="header <%=selfAssessmentHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('listSelfAssessment_form', null, null, 'employeeId', '<%=selfAssessmentHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.employee2.id" />
				</a>
			</th>
			<th class="header <%=selfAssessmentHolder.getCurrentSortClass("year")%>">
				<a onclick="submitForm('listSelfAssessment_form', null, null, 'year', '<%=selfAssessmentHolder.getNextSortOrder("year")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="YERR.year" />
				</a>
			</th>
			<th class="header <%=selfAssessmentHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('listSelfAssessment_form', null, null, 'employeeNameZH', '<%=selfAssessmentHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.employee2.name.cn" />
				</a>
			</th>
			<th class="header <%=selfAssessmentHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('listSelfAssessment_form', null, null, 'employeeNameEN', '<%=selfAssessmentHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.employee2.name.en" />
				</a>
			</th>
			<th class="header <%=selfAssessmentHolder.getCurrentSortClass("parentBranchId")%>">
				<a onclick="submitForm('listSelfAssessment_form', null, null, 'parentBranchId', '<%=selfAssessmentHolder.getNextSortOrder("parentBranchId")%>', 'tableWrapper');">
					BU/Function
				</a>
			</th>
			<th class="header <%=selfAssessmentHolder.getCurrentSortClass("directLeaderNameZH")%>">
				<a onclick="submitForm('listSelfAssessment_form', null, null, 'directLeaderNameZH', '<%=selfAssessmentHolder.getNextSortOrder("directLeaderNameZH")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="emp.self.assessment.directLeaderNameZH" />
				</a>
			</th>
			<th class="header <%=selfAssessmentHolder.getCurrentSortClass("directLeaderNameEN")%>">
				<a onclick="submitForm('listSelfAssessment_form', null, null, 'directLeaderNameEN', '<%=selfAssessmentHolder.getNextSortOrder("directLeaderNameEN")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="emp.self.assessment.directLeaderNameEN" />
				</a>
			</th>
			
			<th class="header <%=selfAssessmentHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listSelfAssessment_form', null, null, 'status', '<%=selfAssessmentHolder.getNextSortOrder("status")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.status" />
				</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="selfAssessmentHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="selfAssessmentVO" name="selfAssessmentHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td></td>
					<td class="left"><bean:write name="selfAssessmentVO" property="employeeId"/></td>
					<td class="left"><bean:write name="selfAssessmentVO" property="year"/></td>
					<td class="left">
						<a onclick="link('selfAssessmentAction.do?proc=to_goalModify&id=<bean:write name="selfAssessmentVO" property="encodedId" />')">
							<bean:write name="selfAssessmentVO" property="employeeNameZH"/>
						</a>
					</td>
					<td class="left"><bean:write name="selfAssessmentVO" property="employeeNameEN"/></td>
					<td class="left"><bean:write name="selfAssessmentVO" property="decodeParentBranchId"/></td>
					<td class="left"><bean:write name="selfAssessmentVO" property="directLeaderNameZH"/></td>
					<td class="left"><bean:write name="selfAssessmentVO" property="directLeaderNameEN"/></td>
					<td class="left"><bean:write name="selfAssessmentVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="selfAssessmentHolder">
		<tfoot>
			<tr class="total">
			  	<td colspan="12" class="left"> 
				  	<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="selfAssessmentHolder" property="holderSize" /></label>
				  	<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="selfAssessmentHolder" property="indexStart" /> - <bean:write name="selfAssessmentHolder" property="indexEnd" /></label>
				  	<label>&nbsp;&nbsp;<a onclick="submitForm('listSelfAssessment_form', null, '<bean:write name="selfAssessmentHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listSelfAssessment_form', null, '<bean:write name="selfAssessmentHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listSelfAssessment_form', null, '<bean:write name="selfAssessmentHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listSelfAssessment_form', null, '<bean:write name="selfAssessmentHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
				  	<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="selfAssessmentHolder" property="realPage" />/<bean:write name="selfAssessmentHolder" property="pageCount" /></label>&nbsp;
				</td>					
		   </tr>
		</tfoot>
	</logic:present>
</table>