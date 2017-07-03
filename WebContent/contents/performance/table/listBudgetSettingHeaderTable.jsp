<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import=" com.kan.base.web.actions.management.BankAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder budgetSettingHeaderHolder = (PagedListHolder) request.getAttribute("budgetSettingHeaderHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th class="header <%=budgetSettingHeaderHolder.getCurrentSortClass("headerId")%>">
				<a onclick="submitForm('listBudgetSettingHeader_form', null, null, 'headerId', '<%=budgetSettingHeaderHolder.getNextSortOrder("headerId")%>', 'tableWrapper');">
					ID
				</a>
			</th>
			<th class="header <%=budgetSettingHeaderHolder.getCurrentSortClass("year")%>">
				<a onclick="submitForm('listBudgetSettingHeader_form', null, null, 'year', '<%=budgetSettingHeaderHolder.getNextSortOrder("year")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="budget.setting.year" />
				</a>
			</th>
			<th class="header <%=budgetSettingHeaderHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('listBudgetSettingHeader_form', null, null, 'startDate', '<%=budgetSettingHeaderHolder.getNextSortOrder("startDate")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="budget.setting.self.assessment.write.startDate" />
				</a>
			</th>
			<th class="header <%=budgetSettingHeaderHolder.getCurrentSortClass("endDate")%>">
				<a onclick="submitForm('listBudgetSettingHeader_form', null, null, 'endDate', '<%=budgetSettingHeaderHolder.getNextSortOrder("endDate")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="budget.setting.self.assessment.write.endDate" />
				</a>
			</th>
			<th class="header <%=budgetSettingHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listBudgetSettingHeader_form', null, null, 'status', '<%=budgetSettingHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.status" />
				</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="budgetSettingHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="budgetSettingHeaderVO" name="budgetSettingHeaderHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="budgetSettingHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="budgetSettingHeaderVO" property="headerId"/>" /></td>
					<td class="left"><bean:write name="budgetSettingHeaderVO" property="headerId"/></td>
					<td class="left">
						<a onclick="link('budgetSettingDetailAction.do?proc=list_object&id=<bean:write name="budgetSettingHeaderVO" property="encodedId" />')">
							<bean:write name="budgetSettingHeaderVO" property="year"/>
						</a>
					</td>
					<td class="left"><bean:write name="budgetSettingHeaderVO" property="startDate"/></td>
					<td class="left"><bean:write name="budgetSettingHeaderVO" property="endDate"/></td>
					<td class="left"><bean:write name="budgetSettingHeaderVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="budgetSettingHeaderHolder">
		<tfoot>
			<tr class="total">
			  	<td colspan="6" class="left"> 
				  	<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="budgetSettingHeaderHolder" property="holderSize" /></label>
				  	<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="budgetSettingHeaderHolder" property="indexStart" /> - <bean:write name="budgetSettingHeaderHolder" property="indexEnd" /></label>
				  	<label>&nbsp;&nbsp;<a onclick="submitForm('listBudgetSettingHeader_form', null, '<bean:write name="budgetSettingHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listBudgetSettingHeader_form', null, '<bean:write name="budgetSettingHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listBudgetSettingHeader_form', null, '<bean:write name="budgetSettingHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listBudgetSettingHeader_form', null, '<bean:write name="budgetSettingHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
				  	<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="budgetSettingHeaderHolder" property="realPage" />/<bean:write name="budgetSettingHeaderHolder" property="pageCount" /></label>&nbsp;
				</td>					
		   </tr>
		</tfoot>
	</logic:present>
</table>