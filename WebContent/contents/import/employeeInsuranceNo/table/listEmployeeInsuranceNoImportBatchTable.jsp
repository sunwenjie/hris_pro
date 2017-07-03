<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder employeeInsuranceNoImportBatchHolder = (PagedListHolder) request.getAttribute("employeeInsuranceNoImportBatchHolder");
%>
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header  <%=employeeInsuranceNoImportBatchHolder.getCurrentSortClass("batchId")%>">
				<a onclick="submitForm('list_form', null, null, 'batchId', '<%=employeeInsuranceNoImportBatchHolder.getNextSortOrder("batchId")%>', 'tableWrapper');">导入批次ID</a>
			</th>
			<th style="width: 20%" class="header-nosort ">
				导入报表名称
			</th>
			<th style="width: 20%" class="header-nosort">
				描述
			</th>
			<th style="width: 10%" class="header <%=employeeInsuranceNoImportBatchHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('list_form', null, null, 'batchId', '<%=employeeInsuranceNoImportBatchHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
			<th style="width: 20%" class="header-nosort">
				操作人
			</th>
			<th style="width: 20%" class="header-nosort">
				操作时间
			</th>
		</tr>
	</thead>
	<logic:notEqual name="employeeInsuranceNoImportBatchHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="commonBatchVO" name="employeeInsuranceNoImportBatchHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="commonBatchVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="commonBatchVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="commonBatchVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left"> <a href="#" onclick="link('employeeInsuranceNoImportHeaderAction.do?proc=list_object&batchId=<bean:write name="commonBatchVO" property="batchId"/>');"><bean:write name="commonBatchVO" property="batchId" /></a></td>								
					<td class="left"> <bean:write name="commonBatchVO" property="importExcelName"/>  </td>
					<td class="left"> <bean:write name="commonBatchVO" property="remark2"/>  </td>
					<td class="left"> <bean:write name="commonBatchVO" property="decodeStatus"/>  </td>
					<td class="left"> <bean:write name="commonBatchVO" property="decodeCreateBy"/>  </td>
					<td class="left"> <bean:write name="commonBatchVO" property="decodeCreateDate"/>  </td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="employeeInsuranceNoImportBatchHolder">
		<tfoot>
			<tr class="total">
				<td colspan="13" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="employeeInsuranceNoImportBatchHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="employeeInsuranceNoImportBatchHolder" property="indexStart" /> - <bean:write name="employeeInsuranceNoImportBatchHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeInsuranceNoImportBatchHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeInsuranceNoImportBatchHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeInsuranceNoImportBatchHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeInsuranceNoImportBatchHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="employeeInsuranceNoImportBatchHolder" property="realPage" />/<bean:write name="employeeInsuranceNoImportBatchHolder" property="pageCount" /> </label>&nbsp;</td>
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