<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder salaryBatchHolder = (PagedListHolder) request.getAttribute("salaryBatchHolder");
%>
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header  <%=salaryBatchHolder.getCurrentSortClass("batchId")%>">
				<a onclick="submitForm('list_form', null, null, 'batchId', '<%=salaryBatchHolder.getNextSortOrder("batchId")%>', 'tableWrapper');"><bean:message bundle="payment" key="payment.salary.import.batch.id" /></a>
			</th>
			<th style="width: 30%" class="header-nosort ">
				<bean:message bundle="payment" key="payment.salary.import.excel.name" />
			</th>
			<th style="width: 30%" class="header-nosort">
				<bean:message bundle="public" key="public.note" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="payment" key="payment.salary.import.uploader" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="payment" key="payment.salary.import.upload.time" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="public" key="public.status" />
			</th>
		</tr>
	</thead>
	<logic:notEqual name="salaryBatchHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="commonBatchVO" name="salaryBatchHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
					<logic:equal   name="commonBatchVO" property="status" value="1">
					<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="commonBatchVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="commonBatchVO" property="encodedId"/>" />
					</logic:equal>
					
					</td>
					
					
					<td class="left"><a onclick="link('salaryAction.do?proc=to_salaryHeader&batchId=<bean:write name="commonBatchVO" property="encodedId"/>');"><bean:write name="commonBatchVO" property="batchId" /></a></td>								
					<td class="left"> <bean:write name="commonBatchVO" property="importExcelName"/>  </td>
					<td class="left"> <bean:write name="commonBatchVO" property="remark2"/>  </td>
					
					<td class="left"><bean:write name="commonBatchVO" property="decodeCreateBy" /></td>
					<td class="left"><bean:write name="commonBatchVO" property="decodeCreateDate" /></td>
					
					<td class="left">
						<logic:iterate id="mappingVO" name="salaryHeaderForm" property="statuses">
							<logic:equal name="mappingVO" property="mappingId" value="${commonBatchVO.status}">
								<bean:write name="mappingVO" property="mappingValue" />
							</logic:equal>
						</logic:iterate>
					 </td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="salaryBatchHolder">
		<tfoot>
			<tr class="total">
				<td colspan="13" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="salaryBatchHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="salaryBatchHolder" property="indexStart" /> - <bean:write name="salaryBatchHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="salaryBatchHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="salaryBatchHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="salaryBatchHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="salaryBatchHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="salaryBatchHolder" property="realPage" />/<bean:write name="salaryBatchHolder" property="pageCount" /> </label>&nbsp;</td>
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