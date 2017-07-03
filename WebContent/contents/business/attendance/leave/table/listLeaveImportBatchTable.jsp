<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder timesheetBatchHolder = ( PagedListHolder ) request.getAttribute( "timesheetBatchHolder" );
%>
<logic:notEmpty name="MESSAGE">
<div id="_USER_DEFINE_MSG" class="message <bean:write name="MESSAGE_CLASS" /> fadable" style="display:none;"><bean:write name="MESSAGE" /></div>
</logic:notEmpty>
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=timesheetBatchHolder.getCurrentSortClass("batchId")%>">
				<a onclick="submitForm('searchTimesheetBatch_form', null, null, 'batchId', '<%=timesheetBatchHolder.getNextSortOrder("batchId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.batch.id" /></a>
			</th>
			<th style="width: 40%" class="header-nosort">
				<span><bean:message bundle="public" key="public.note" /></span>			
			</th>
			<th style="width: 15%" class="header  <%=timesheetBatchHolder.getCurrentSortClass("importExcelName")%>">
				<a onclick="submitForm('searchTimesheetBatch_form', null, null, 'importExcelName', '<%=timesheetBatchHolder.getNextSortOrder("importExcelName")%>', 'tableWrapper');"><bean:message bundle="business" key="business.file.name" /></a>
			</th>
					<th style="width: 10%" class="header <%=timesheetBatchHolder.getCurrentSortClass("createBy")%>">
				<a onclick="submitForm('searchTimesheetBatch_form', null, null, 'createBy', '<%=timesheetBatchHolder.getNextSortOrder("createBy")%>', 'tableWrapper');"><bean:message bundle="business" key="business.import.uploader" /></a>
			</th>
			<th style="width: 15%" class="header  <%=timesheetBatchHolder.getCurrentSortClass("createDate")%>">
				<a onclick="submitForm('searchTimesheetBatch_form', null, null, 'createDate', '<%=timesheetBatchHolder.getNextSortOrder("createDate")%>', 'createDate');"><bean:message bundle="business" key="business.import.upload.date" /></a>
			</th>
			<th style="width: 10%" class="header  <%=timesheetBatchHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchTimesheetBatch_form', null, null, 'status', '<%=timesheetBatchHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>	
		</tr>
	</thead>
	<logic:notEqual name="timesheetBatchHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="timesheetBatchVO" name="timesheetBatchHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="timesheetBatchVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="timesheetBatchVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="timesheetBatchVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="linkBatch('leaveImportHeaderAction.do?proc=list_object&batchId=<bean:write name="timesheetBatchVO" property="encodedId" />&batchStatus=<bean:write name="timesheetBatchVO" property="status" />');"><bean:write name="timesheetBatchVO" property="batchId" /></a>
					</td>
					<td class="left">
						<bean:message bundle="business" key="business.leave.number" />：<bean:write name="timesheetBatchVO" property="countHeaderId" />；
						<bean:message bundle="business" key="business.leave.ot.hours.number" />：<bean:write name="timesheetBatchVO" property="totalLeaveHours" />；
						<!-- header记录小于3大于0 -->
						<logic:notEmpty name="timesheetBatchVO" property="employeeNameList">
							<logic:lessEqual name="timesheetBatchVO" property="countHeaderId" value="3">
								<logic:greaterThan name="timesheetBatchVO" property="countHeaderId" value="0">
									员工：<bean:write name="timesheetBatchVO" property="employeeNameList" />；
								</logic:greaterThan>
							</logic:lessEqual>
						</logic:notEmpty>
						<!-- header记录大于3 -->
						<logic:notEmpty name="timesheetBatchVO" property="employeeNameTop3">
							<logic:greaterThan name="timesheetBatchVO" property="countHeaderId" value="3">
								员工：<bean:write name="timesheetBatchVO" property="employeeNameTop3" />等；
								<img src="images/tips.png" title="<bean:write name="timesheetBatchVO" property="description" />" />
							</logic:greaterThan>
						</logic:notEmpty>
					</td>
					<td class="left"><bean:write name="timesheetBatchVO" property="importExcelName" /></td>
					<td class="left"><bean:write name="timesheetBatchVO" property="decodeCreateBy" /></td>
					<td class="left"><bean:write name="timesheetBatchVO" property="decodeCreateDate" /></td>
					<td class="left">
						<logic:equal name="timesheetBatchVO" property="status" value="1">
							新建
						</logic:equal>
						<logic:equal name="timesheetBatchVO" property="status" value="2">
							已提交
						</logic:equal>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="timesheetBatchHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="timesheetBatchHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="timesheetBatchHolder" property="indexStart" /> - <bean:write name="timesheetBatchHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchTimesheetBatch_form', null, '<bean:write name="timesheetBatchHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetBatch_form', null, '<bean:write name="timesheetBatchHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetBatch_form', null, '<bean:write name="timesheetBatchHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetBatch_form', null, '<bean:write name="timesheetBatchHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="timesheetBatchHolder" property="realPage" />/<bean:write name="timesheetBatchHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>