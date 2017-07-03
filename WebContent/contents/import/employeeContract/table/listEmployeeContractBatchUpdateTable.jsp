<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder employeeContractBatchHolder = (PagedListHolder) request.getAttribute("employeeContractBatchHolder");
%>
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header  <%=employeeContractBatchHolder.getCurrentSortClass("batchId")%>">
				<a onclick="submitForm('list_form', null, null, 'batchId', '<%=employeeContractBatchHolder.getNextSortOrder("batchId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.employee.contract.import.batch.id" /></a>
			</th>
			<th style="width: 20%" class="header-nosort ">
				<bean:message bundle="business" key="business.employee.contract.import.batch.report.name" />
			</th>
			<th style="width: 20%" class="header-nosort">
				<bean:message bundle="public" key="public.description" />
			</th>
			<th style="width: 10%" class="header <%=employeeContractBatchHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('list_form', null, null, 'batchId', '<%=employeeContractBatchHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			<th style="width: 20%" class="header-nosort">
				<bean:message bundle="business" key="business.employee.contract.import.batch.operator" />
			</th>
			<th style="width: 20%" class="header-nosort">
				<bean:message bundle="business" key="business.employee.contract.import.batch.operation.time" />
			</th>
		</tr>
	</thead>
	<logic:notEqual name="employeeContractBatchHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="commonBatchVO" name="employeeContractBatchHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="commonBatchVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="commonBatchVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="commonBatchVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left"> <a onclick="link('employeeContractTempAction.do?proc=list_detail&batchId=<bean:write name="commonBatchVO" property="batchId"/>');"><bean:write name="commonBatchVO" property="batchId" /></a></td>								
					<td class="left"> <bean:write name="commonBatchVO" property="importExcelName"/>  </td>
					<td class="left"> <bean:write name="commonBatchVO" property="description"/>  </td>
					<td class="left"> <bean:write name="commonBatchVO" property="decodeStatus"/>  </td>
					<td class="left"> <bean:write name="commonBatchVO" property="decodeCreateBy"/>  </td>
					<td class="left"> <bean:write name="commonBatchVO" property="decodeCreateDate"/>  </td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="employeeContractBatchHolder">
		<tfoot>
			<tr class="total">
				<td colspan="13" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="employeeContractBatchHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="employeeContractBatchHolder" property="indexStart" /> - <bean:write name="employeeContractBatchHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="employeeContractBatchHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="employeeContractBatchHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="employeeContractBatchHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="employeeContractBatchHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="employeeContractBatchHolder" property="realPage" />/<bean:write name="employeeContractBatchHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
						
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
	})(jQuery);	
</script>