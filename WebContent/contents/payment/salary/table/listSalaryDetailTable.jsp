<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.List"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.domain.biz.payment.PaymentDTO"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder salaryDTOHolder = (PagedListHolder) request.getAttribute("salaryDTOHolder");
%>

	<logic:iterate id="salaryDTO" name="salaryDTOHolder" property="source">
			<bean:define id="defineSalaryDetailVOs" name="salaryDTO" property="salaryDetailVOs" />
	</logic:iterate>
	<logic:notEmpty name="defineSalaryDetailVOs">
		<logic:iterate id="defineSalaryDetailVO" name="defineSalaryDetailVOs">
			<logic:equal name="defineSalaryDetailVO" property="itemType" value="7">
				<bean:define id="itemSB" name="defineSalaryDetailVO" property="itemType" />
			</logic:equal>
		</logic:iterate>
	</logic:notEmpty>			
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"  <logic:equal name="itemSB" value="7">rowspan="2"</logic:equal> >
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value=""  />
			</th>
			<th class="header <%=salaryDTOHolder.getCurrentSortClass("salaryHeaderId")%>"  rowspan="2" >
				<a onclick="submitForm('list_form', null, null, 'salaryHeaderId', '<%=salaryDTOHolder.getNextSortOrder("salaryHeaderId")%>', 'tableWrapper');">
					<bean:message bundle="payment" key="payment.salary.import.header.id" />
					&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=salaryDTOHolder.getCurrentSortClass("employeeId")%>"  rowspan="2" >
				<a onclick="submitForm('list_form', null, null, 'employeeId', '<%=salaryDTOHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=salaryDTOHolder.getCurrentSortClass("employeeNameZH")%>"  rowspan="2" >
				<a onclick="submitForm('list_form', null, null, 'employeeNameZH', '<%=salaryDTOHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=salaryDTOHolder.getCurrentSortClass("employeeNameEN")%>"  rowspan="2" >
				<a onclick="submitForm('list_form', null, null, 'employeeNameEN', '<%=salaryDTOHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=salaryDTOHolder.getCurrentSortClass("certificateNumber")%>"  rowspan="2" >
				<a onclick="submitForm('list_form', null, null, 'certificateNumber', '<%=salaryDTOHolder.getNextSortOrder("certificateNumber")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.certificate.number" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=salaryDTOHolder.getCurrentSortClass("bankNameZH")%>"  rowspan="2" >
				<a onclick="submitForm('list_form', null, null, 'bankNameZH', '<%=salaryDTOHolder.getNextSortOrder("bankNameZH")%>', 'tableWrapper');">
					<bean:message bundle="payment" key="payment.salary.import.header.bank.name" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=salaryDTOHolder.getCurrentSortClass("bankAccount")%>"  rowspan="2" >
				<a onclick="submitForm('list_form', null, null, 'bankAccount', '<%=salaryDTOHolder.getNextSortOrder("bankAccount")%>', 'tableWrapper');">
					<bean:message bundle="payment" key="payment.salary.import.header.bank.account" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=salaryDTOHolder.getCurrentSortClass("monthly")%>"  rowspan="2" >
				<a onclick="submitForm('list_form', null, null, 'monthly', '<%=salaryDTOHolder.getNextSortOrder("monthly")%>', 'tableWrapper');"><bean:message bundle="payment" key="payment.salary.import.header.salary.month" />&nbsp;&nbsp;&nbsp;&nbsp;</a>
			</th>
			<logic:equal name="role" value="1">
				<th class="header <%=salaryDTOHolder.getCurrentSortClass("clientId")%>"  rowspan="2" >
					<a onclick="submitForm('list_form', null, null, 'clientId', '<%=salaryDTOHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">¿Í»§ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
				</th>
			</logic:equal>
			<th class="header <%=salaryDTOHolder.getCurrentSortClass("orderId")%>"  rowspan="2" >
				<a onclick="submitForm('list_form', null, null, 'orderId', '<%=salaryDTOHolder.getNextSortOrder("orderId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=salaryDTOHolder.getCurrentSortClass("orderContractId")%>"  rowspan="2" >
				<a onclick="submitForm('list_form', null, null, 'orderContractId', '<%=salaryDTOHolder.getNextSortOrder("orderContractId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
		 <logic:notEmpty name="defineSalaryDetailVOs">
			<logic:iterate id="defineSalaryDetailVO" name="defineSalaryDetailVOs">
				<logic:equal name="defineSalaryDetailVO" property="itemType" value="7">
					<th class="header-nosort center" colspan="2">
						<%
							if( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ){
						%>
							<bean:write name="defineSalaryDetailVO" property="nameZH"/>
						<%
							} else {
						%>
							<bean:write name="defineSalaryDetailVO" property="nameEN"/>
						<%
							}
						%>
					</th>
				</logic:equal>
				<logic:notEqual name="defineSalaryDetailVO" property="itemType" value="7">
					<th class="header-nosort center" colspan="2"  rowspan="2" >
						<%
							if( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ){
						%>
								<bean:write name="defineSalaryDetailVO" property="nameZH"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<%
							} else {
						%>
								<bean:write name="defineSalaryDetailVO" property="nameEN"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<%
							}
						%>
					</th>
				</logic:notEqual>
			</logic:iterate>
			</logic:notEmpty>
		
			<th class="header-nosort"  rowspan="2" >
				<bean:message bundle="payment" key="payment.salary.import.header.estimate.salary" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			
				
			<th class="header-nosort"  rowspan="2" >
				<bean:message bundle="payment" key="payment.salary.import.header.bill.amount.personal" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			
			<th class="header-nosort"  rowspan="2" >
				<bean:message bundle="payment" key="payment.salary.import.header.tax.amount.personal" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th class="header-nosort"  rowspan="2" >
				<bean:message bundle="payment" key="payment.salary.import.header.actual.salary" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th class="header  <%=salaryDTOHolder.getCurrentSortClass("status")%>"  rowspan="2" >
				<a onclick="submitForm('list_form', null, null, 'status', '<%=salaryDTOHolder.getNextSortOrder("status")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.status" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</th>
		</tr>
		<tr>
		<logic:notEmpty name="defineSalaryDetailVOs">
			<logic:iterate id="defineSalaryDetailVO" name="defineSalaryDetailVOs">
				<logic:equal name="defineSalaryDetailVO" property="itemType" value="7">
					<th class="header-nosort">
						<bean:message bundle="payment" key="payment.salary.import.header.company" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</th>
					<th class="header-nosort">
						<bean:message bundle="payment" key="payment.salary.import.header.personal" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</th>
				</logic:equal>
			</logic:iterate>
		</logic:notEmpty>
		</tr>
	</thead>
	<logic:notEqual name="salaryDTOHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="salaryDTO" name="salaryDTOHolder" property="source" indexId="number">
				<bean:define id="defineSalaryHeaderVO" name="salaryDTO" property="salaryHeaderVO" ></bean:define>
				<bean:define id="defineSalaryDetailVOs" name="salaryDTO" property="salaryDetailVOs" ></bean:define>
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal   name="defineSalaryHeaderVO" property="status" value="1">
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="defineSalaryHeaderVO" property="encodedId"/>" name="chkSelectRow[]" value="<bean:write name="defineSalaryHeaderVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left"><bean:write name="defineSalaryHeaderVO" property="salaryHeaderId"/></td>
					<td class="left"><a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="defineSalaryHeaderVO" property="encodedEmployeeId"/>');"><bean:write name="defineSalaryHeaderVO" property="employeeId" /></a></td>
					<td class="left"><bean:write name="defineSalaryHeaderVO" property="employeeNameZH"/></td>
					<td class="left"><bean:write name="defineSalaryHeaderVO" property="employeeNameEN"/></td>
					<td class="left"><bean:write name="defineSalaryHeaderVO" property="certificateNumber"/></td>
					<td class="left"><bean:write name="defineSalaryHeaderVO" property="bankNameZH"/></td>
					<td class="left"><bean:write name="defineSalaryHeaderVO" property="bankAccount"/></td>
					<td class="left"><bean:write name="defineSalaryHeaderVO" property="monthly"/></td>
					<logic:equal name="role" value="1">
						<td class="left"><a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="defineSalaryHeaderVO" property="encodedClientId"/>');"><bean:write name="defineSalaryHeaderVO" property="clientId" /></a></td>
					</logic:equal>
					<td class="left"><a onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="defineSalaryHeaderVO" property="encodedOrderId"/>');"><bean:write name="defineSalaryHeaderVO" property="orderId"/></a></td>
					<td class="left"><a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="defineSalaryHeaderVO" property="encodedContractId"/>');"><bean:write name="defineSalaryHeaderVO" property="contractId"/></a></td>
					<logic:iterate id="defineSalaryDetailVO" name="defineSalaryDetailVOs" >
						<logic:equal name="defineSalaryDetailVO" property="itemType" value="7">
							<td class="right">
								<logic:notEmpty name="defineSalaryDetailVO" property="costAmountCompany">
									<bean:write name="defineSalaryDetailVO" property="costAmountCompany"/>
								</logic:notEmpty>
							</td>
							<td class="right">
								<logic:notEmpty name="defineSalaryDetailVO" property="costAmountPersonal">
									<bean:write name="defineSalaryDetailVO" property="costAmountPersonal"/>
								</logic:notEmpty>
							</td>
						</logic:equal>
						<logic:notEqual name="defineSalaryDetailVO" property="itemType" value="7">
							<td class="right" colspan="2">
								<logic:notEmpty name="defineSalaryDetailVO" property="billAmountPersonal">
									<logic:equal name="defineSalaryDetailVO" property="billAmountPersonal" value="0">
										<bean:write name="defineSalaryDetailVO" property="costAmountPersonal"/>
									</logic:equal>
									
									<logic:notEqual name="defineSalaryDetailVO" property="billAmountPersonal" value="0">
										<bean:write name="defineSalaryDetailVO" property="billAmountPersonal"/>
									</logic:notEqual>
								</logic:notEmpty>
							</td>
						</logic:notEqual>
					</logic:iterate>
					<td class="right"><bean:write name="defineSalaryHeaderVO" property="estimateSalary"/></td>
					<td class="right"><bean:write name="defineSalaryHeaderVO" property="billAmountPersonal"/></td>
					<td class="right"><bean:write name="defineSalaryHeaderVO" property="taxAmountPersonal"/></td>
					<td class="right"><bean:write name="defineSalaryHeaderVO" property="actualSalary"/></td>
					<td class="left"><bean:write name="defineSalaryHeaderVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="salaryDTOHolder">
		<tfoot>
			<tr class="total">
				<td></td>
				<td colspan="<bean:write name='salaryDTOHolder' property="additionalObject" />" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="salaryDTOHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="salaryDTOHolder" property="indexStart" /> - <bean:write name="salaryDTOHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="salaryDTOHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="salaryDTOHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="salaryDTOHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="salaryDTOHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="salaryDTOHolder" property="realPage" />/<bean:write name="salaryDTOHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
<input type="hidden"  id="pageCount" value="<bean:write name="salaryDTOHolder" property="pageCount" />" />						
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
</script>
