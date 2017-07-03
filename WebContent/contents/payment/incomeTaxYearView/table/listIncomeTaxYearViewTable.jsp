<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.payment.IncomeTaxYearViewAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final PagedListHolder incomeTaxYearViewHolder = (PagedListHolder) request.getAttribute("incomeTaxYearViewHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 10%" class="header <%=incomeTaxYearViewHolder.getCurrentSortClass("year")%>">
				<a onclick="submitForm('list_form', null, null, 'year', '<%=incomeTaxYearViewHolder.getNextSortOrder("year")%>', 'tableWrapper');"><bean:message bundle="payment" key="payment.tax.year" /></a>
			</th>
			<th style="width: 10%" class="header <%=incomeTaxYearViewHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('list_form', null, null, 'employeeId', '<%=incomeTaxYearViewHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: 20%" class="header <%=incomeTaxYearViewHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('list_form', null, null, 'employeeNameZH', '<%=incomeTaxYearViewHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</a>
			</th>
			<th style="width: 10%" class="header <%=incomeTaxYearViewHolder.getCurrentSortClass("orderId")%>">
				<a onclick="submitForm('list_form', null, null, 'orderId', '<%=incomeTaxYearViewHolder.getNextSortOrder("orderId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: 20%" class="header-nosort">
				<bean:message bundle="payment" key="payemnt.tax.addtional.bill.amount.personal" />
			</th>
			<th style="width: 20%" class="header <%=incomeTaxYearViewHolder.getCurrentSortClass("taxAmountPersonal")%>">
				<a onclick="submitForm('list_form', null, null, 'taxAmountPersonal', '<%=incomeTaxYearViewHolder.getNextSortOrder("taxAmountPersonal")%>', 'tableWrapper');">
					<bean:message bundle="payment" key="payemnt.tax.amount.personal" />
				</a>
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="public" key="button.export.excel" />
			</th>
		</tr>
	</thead>
	<logic:notEqual name="incomeTaxYearViewHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="incomeTaxYearView" name="incomeTaxYearViewHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td class="left"><bean:write name="incomeTaxYearView" property="year" /></td>
					<td class="left"><bean:write name="incomeTaxYearView" property="employeeId" /></td>
					<td class="left"><bean:write name="incomeTaxYearView" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="incomeTaxYearView" property="orderId" /></td>
					<td class="right"><bean:write name="incomeTaxYearView" property="addtionalBillAmountPersonal" /></td>
					<td class="right"><bean:write name="incomeTaxYearView" property="taxAmountPersonal" /></td>
					<td class="right">
						<kan:auth right="export" action="<%=IncomeTaxYearViewAction.ACCESSACTION %>">
							<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm('list_form', 'downloadObjects', 'incomeTaxYearViewAction.do?proc=genreate_excel', 'contractId=<bean:write name="incomeTaxYearView" property="contractId"/>&year=<bean:write name="incomeTaxYearView" property="year"/>')">
								<img src="images/appicons/excel_16.png" />
							</a> 
						</kan:auth>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="incomeTaxYearViewHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="incomeTaxYearViewHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="incomeTaxYearViewHolder" property="indexStart" /> - <bean:write name="incomeTaxYearViewHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="incomeTaxYearViewHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="incomeTaxYearViewHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="incomeTaxYearViewHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="incomeTaxYearViewHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="incomeTaxYearViewHolder" property="realPage" />/<bean:write name="incomeTaxYearViewHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>