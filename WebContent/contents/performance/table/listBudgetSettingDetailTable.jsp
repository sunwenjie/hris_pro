<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import=" com.kan.base.web.actions.management.BankAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder budgetSettingDetailHolder = (PagedListHolder) request.getAttribute("budgetSettingDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th class="header <%=budgetSettingDetailHolder.getCurrentSortClass("detailId")%>">
				<a onclick="submitForm('listBudgetSettingDetail_form', null, null, 'detailId', '<%=budgetSettingDetailHolder.getNextSortOrder("detailId")%>', 'tableWrapper');">
					ID
				</a>
			</th>
			<th class="header <%=budgetSettingDetailHolder.getCurrentSortClass("parentBranchId")%>">
				<a onclick="submitForm('listBudgetSettingDetail_form', null, null, 'parentBranchId', '<%=budgetSettingDetailHolder.getNextSortOrder("parentBranchId")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="budget.setting.parentBranchId" />
				</a>
			</th>
			<th class="header <%=budgetSettingDetailHolder.getCurrentSortClass("ttc")%>">
				<a onclick="submitForm('listBudgetSettingDetail_form', null, null, 'ttc', '<%=budgetSettingDetailHolder.getNextSortOrder("ttc")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="budget.setting.ttc" />
				</a>
			</th>
			<th class="header <%=budgetSettingDetailHolder.getCurrentSortClass("bonus")%>">
				<a onclick="submitForm('listBudgetSettingDetail_form', null, null, 'bonus', '<%=budgetSettingDetailHolder.getNextSortOrder("bonus")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="budget.setting.bonus" />
				</a>
			</th>
			<th class="header <%=budgetSettingDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listBudgetSettingDetail_form', null, null, 'status', '<%=budgetSettingDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.status" />
				</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="budgetSettingDetailHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="budgetSettingDetailVO" name="budgetSettingDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="budgetSettingDetailVO" property="detailId"/>" name="chkSelectRow[]" value="<bean:write name="budgetSettingDetailVO" property="detailId"/>" /></td>
					<td class="left"><bean:write name="budgetSettingDetailVO" property="detailId"/></td>
					<td class="left">
						<a onclick="to_objectModify_ajax('<bean:write name="budgetSettingDetailVO" property="encodedId" />')">
							<bean:write name="budgetSettingDetailVO" property="decodeParentBranchId"/>
						</a>
					</td>
					<td class="left"><bean:write name="budgetSettingDetailVO" property="ttc"/></td>
					<td class="left"><bean:write name="budgetSettingDetailVO" property="bonus"/></td>
					<td class="left"><bean:write name="budgetSettingDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="budgetSettingDetailHolder">
		<tfoot>
			<tr class="total">
			  	<td colspan="6" class="left"> 
				  	<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="budgetSettingDetailHolder" property="holderSize" /></label>
				  	<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="budgetSettingDetailHolder" property="indexStart" /> - <bean:write name="budgetSettingDetailHolder" property="indexEnd" /></label>
				  	<label>&nbsp;&nbsp;<a onclick="submitForm('listBudgetSettingDetail_form', null, '<bean:write name="budgetSettingDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listBudgetSettingDetail_form', null, '<bean:write name="budgetSettingDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listBudgetSettingDetail_form', null, '<bean:write name="budgetSettingDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listBudgetSettingDetail_form', null, '<bean:write name="budgetSettingDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
				  	<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="budgetSettingDetailHolder" property="realPage" />/<bean:write name="budgetSettingDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
		   </tr>
		</tfoot>
	</logic:present>
</table>