<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder salaryAdjustmentHolder = (PagedListHolder) request.getAttribute("salaryAdjustmentHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<logic:equal name="isPaged" value="1">
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 8%" class="header <%=salaryAdjustmentHolder.getCurrentSortClass("salaryAdjustmentId")%>">
				<a onclick="submitForm('salaryAdjustment_form', null, null, 'salaryAdjustmentId', '<%=salaryAdjustmentHolder.getNextSortOrder("salaryAdjustmentId")%>', 'tableWrapper');">
					ID
				</a>
			</th>
			<th style="width: 8%" class="header <%=salaryAdjustmentHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('salaryAdjustment_form', null, null, 'employeeId', '<%=salaryAdjustmentHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.employee2.id" />
				</a>
			</th>
			<th style="width: 12%" class="header-nosort">
				<bean:message bundle="public" key="public.employee2.name" />
			</th>
			<th style="width: 12%" class="header <%=salaryAdjustmentHolder.getCurrentSortClass("employeeCertificateNumber")%>">
				<a onclick="submitForm('salaryAdjustment_form', null, null, 'employeeCertificateNumber', '<%=salaryAdjustmentHolder.getNextSortOrder("employeeCertificateNumber")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.certificate.number" />
				</a>
			</th>
			<th style="width: 8%" class="header <%=salaryAdjustmentHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('salaryAdjustment_form', null, null, 'contractId', '<%=salaryAdjustmentHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.contract2.id" />
				</a>
			</th>
			<th style="width: 12%" class="header-nosort">
				<bean:message bundle="public" key="public.contract2.name" />
			</th>
			</logic:equal>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.salary.adjustment.item" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.salary.adjustment.adjustment.before.base" />
			</th>
			<th style="width: 12%" class="header-nosort">
				<bean:message bundle="business" key="employee.salary.adjustment.adjustment.before.period" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.salary.adjustment.adjustment.after.base" />
			</th>
			<th style="width: 12%" class="header-nosort">
				<bean:message bundle="business" key="employee.salary.adjustment.adjustment.after.period" />
			</th>
			<!-- <th style="width: 8%" class="header-nosort">
				生效日期
			</th> -->
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="public" key="public.status" />
			</th>
		</tr>
	</thead>
	<logic:notEqual name="salaryAdjustmentHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="salaryAdjustmentVO" name="salaryAdjustmentHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<logic:equal name="isPaged" value="1">
					<td>
						<logic:equal name="salaryAdjustmentVO" property="status" value="1">
							<logic:empty name="salaryAdjustmentVO" property="workflowId" >
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="salaryAdjustmentVO" property="salaryAdjustmentId"/>" name="chkSelectRow[]" value="<bean:write name="salaryAdjustmentVO" property="salaryAdjustmentId"/>" />
							</logic:empty>
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('employeeSalaryAdjustmentAction.do?proc=to_objectModify&id=<bean:write name="salaryAdjustmentVO" property="encodedId"/>');"><bean:write name="salaryAdjustmentVO" property="salaryAdjustmentId"/></a>
					</td>
					<td class="left"><bean:write name="salaryAdjustmentVO" property="employeeId"/></td>
					<td class="left"><bean:write name="salaryAdjustmentVO" property="employeeName"/></td>
					<td class="left"><bean:write name="salaryAdjustmentVO" property="employeeCertificateNumber"/></td>
					<td class="left"><bean:write name="salaryAdjustmentVO" property="contractId"/></td>
					<td class="left"><bean:write name="salaryAdjustmentVO" property="employeeContractName" /></td>
					</logic:equal>
					<td class="left"><bean:write name="salaryAdjustmentVO" property="decodeItemId" /></td>
					<td class="left"><bean:write name="salaryAdjustmentVO" property="oldBase" /></td>
					<td class="left">
						<logic:notEmpty name="salaryAdjustmentVO" property="oldStartDate">
							<bean:write name="salaryAdjustmentVO" property="oldStartDate" />  ~  <bean:write name="salaryAdjustmentVO" property="oldEndDate" />
						</logic:notEmpty>
					</td>
					<td class="left"><bean:write name="salaryAdjustmentVO" property="newBase" /></td>
					<td class="left">
						<bean:write name="salaryAdjustmentVO" property="newStartDate" />  ~  <bean:write name="salaryAdjustmentVO" property="newEndDate" />
					</td>
					<%-- <td class="left">
						<bean:write name="salaryAdjustmentVO" property="effectiveDate" />
					</td> --%>
					<td class="left">
						<bean:write name="salaryAdjustmentVO" property="decodeStatus" />
						<logic:notEmpty name="salaryAdjustmentVO" property="workflowId" >
							&nbsp;&nbsp;<img src='images/magnifer.png' onclick=popupWorkflow('<bean:write name="salaryAdjustmentVO" property="workflowId" />'); />
						</logic:notEmpty>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:equal name="isPaged" value="1">
	<logic:present name="salaryAdjustmentHolder">
		<tfoot>
			<tr class="total">
	  			<td colspan="14" class="left"> 
	  				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="salaryAdjustmentHolder" property="holderSize" /></label>
	  				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="salaryAdjustmentHolder" property="indexStart" /> - <bean:write name="salaryAdjustmentHolder" property="indexEnd" /></label>
	  				<label>&nbsp;&nbsp;<a onclick="submitForm('salaryAdjustment_form', null, '<bean:write name="salaryAdjustmentHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('salaryAdjustment_form', null, '<bean:write name="salaryAdjustmentHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('salaryAdjustment_form', null, '<bean:write name="salaryAdjustmentHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('salaryAdjustment_form', null, '<bean:write name="salaryAdjustmentHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
	  				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="salaryAdjustmentHolder" property="realPage" />/<bean:write name="salaryAdjustmentHolder" property="pageCount" /></label>&nbsp;
	  			</td>					
	    	</tr>
		</tfoot>
	</logic:present>
	</logic:equal>
</table>

<script type="text/javascript">
	//useFixColumn(4);
</script>