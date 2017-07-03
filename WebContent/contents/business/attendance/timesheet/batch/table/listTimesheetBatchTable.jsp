<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder timesheetBatchHolder = ( PagedListHolder ) request.getAttribute( "timesheetBatchHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=timesheetBatchHolder.getCurrentSortClass("batchId")%>">
				<a onclick="submitForm('searchTimesheetBatch_form', null, null, 'batchId', '<%=timesheetBatchHolder.getNextSortOrder("batchId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ts.batch.id" /></a>
			</th>
			<th style="width: 10%" class="header <%=timesheetBatchHolder.getCurrentSortClass("monthly")%>">
				<a onclick="submitForm('searchTimesheetBatch_form', null, null, 'monthly', '<%=timesheetBatchHolder.getNextSortOrder("monthly")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ts.month" /></a>
			</th>
			<th style="width: 40%" class="header-nosort">
				<span><bean:message bundle="public" key="public.note" /></span>
			</th>
			<th style="width: 15%" class="header  <%=timesheetBatchHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('searchTimesheetBatch_form', null, null, 'startDate', '<%=timesheetBatchHolder.getNextSortOrder("startDate")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ts.oper.start.date" /></a>
			</th>
			<th style="width: 15%" class="header  <%=timesheetBatchHolder.getCurrentSortClass("endDate")%>">
				<a onclick="submitForm('searchTimesheetBatch_form', null, null, 'endDate', '<%=timesheetBatchHolder.getNextSortOrder("endDate")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ts.oper.end.date" /></a>
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
						<!-- 批次中考勤表全部为退回也能提交 -->
						<logic:equal name="timesheetBatchVO" property="extended" value="2">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="timesheetBatchVO" property="batchId"/>" name="chkSelectRow[]" value="<bean:write name="timesheetBatchVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="linkBatch('timesheetHeaderAction.do?proc=list_object&batchId=<bean:write name="timesheetBatchVO" property="encodedId" />');"><bean:write name="timesheetBatchVO" property="batchId" /></a>
					</td>
					<td class="left"><bean:write name="timesheetBatchVO" property="monthly" /></td>
					<td class="left">
						<%
							final boolean lang_en = request.getLocale().getLanguage().equalsIgnoreCase( "en" ); 
						%>
						<%=lang_en ? "TS:" : "考勤表："%><bean:write name="timesheetBatchVO" property="countHeaderId" /><%=lang_en ? ";" : "个；" %>
						<%=lang_en ? "Work:" : "工作：" %><bean:write name="timesheetBatchVO" property="totalWortHours" /><%=lang_en ? " hours;" : "小时；" %>
						<%=lang_en ? "Leave:" : "休假：" %><bean:write name="timesheetBatchVO" property="totalLeaveHours" /><%=lang_en ? " hours;" : "小时；" %>
						<%=lang_en ? "OT:" : "加班：" %><bean:write name="timesheetBatchVO" property="totalOTHours" /><%=lang_en ? " hours;" : "小时；" %>
						<!-- header记录小于3大于0 -->
						<logic:notEmpty name="timesheetBatchVO" property="employeeNameList">
							<logic:lessEqual name="timesheetBatchVO" property="countHeaderId" value="3">
								<logic:greaterThan name="timesheetBatchVO" property="countHeaderId" value="0">
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2" /></logic:equal><%=lang_en ? ":" : "：" %><bean:write name="timesheetBatchVO" property="employeeNameList" /><%=lang_en ? ";" : "；" %>
								</logic:greaterThan>
							</logic:lessEqual>
						</logic:notEmpty>
						<!-- header记录大于3 -->
						<logic:notEmpty name="timesheetBatchVO" property="employeeNameTop3">
							<logic:greaterThan name="timesheetBatchVO" property="countHeaderId" value="3">
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2" /></logic:equal><%=lang_en ? ":" : "：" %><bean:write name="timesheetBatchVO" property="employeeNameTop3" /><%=lang_en ? ";" : "等；" %>
								<img src="images/tips.png" title="<bean:write name="timesheetBatchVO" property="totalOTEmployeeName" />" />
							</logic:greaterThan>
						</logic:notEmpty>
					</td>
					<td class="left"><bean:write name="timesheetBatchVO" property="startDate" /></td>
					<td class="left"><bean:write name="timesheetBatchVO" property="endDate" /></td>
					<td class="left">
						<bean:write name="timesheetBatchVO" property="decodeStatus" />
						<logic:equal name="timesheetBatchVO" property="status" value="2" >
								&nbsp;&nbsp;<img src='images/magnifer.png' title="<bean:message bundle="public" key="img.title.tips.view.detials" />" onclick=popupWorkflow('<bean:write name="timesheetBatchVO" property="workflowId"/>'); />
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

<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
</script>
