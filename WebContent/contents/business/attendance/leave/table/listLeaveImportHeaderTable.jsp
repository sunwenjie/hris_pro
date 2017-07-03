<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder leaveImportHeaderHolder = ( PagedListHolder ) request.getAttribute( "leaveImportHeaderHolder" );
%>
<logic:notEmpty name="MESSAGE">
<div id="_USER_DEFINE_MSG" class="message <bean:write name="MESSAGE_CLASS" /> fadable" style="display:none;"><bean:write name="MESSAGE" /></div>
</logic:notEmpty>
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col" rowspan="2">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value=""  />
			</th>
			<th style="width: 5%" class="header <%=leaveImportHeaderHolder.getCurrentSortClass("leaveHeaderId")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'leaveHeaderId', '<%=leaveImportHeaderHolder.getNextSortOrder("leaveHeaderId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.leave.id" /></a>
			</th>
			<th style="width: 6%" class="header <%=leaveImportHeaderHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'itemId', '<%=leaveImportHeaderHolder.getNextSortOrder("itemId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.leave.type" /></a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "10"%>%" class="header <%=leaveImportHeaderHolder.getCurrentSortClass("estimateStartDate")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'estimateStartDate', '<%=leaveImportHeaderHolder.getNextSortOrder("estimateStartDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.start.time" /></a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "10"%>%" class="header <%=leaveImportHeaderHolder.getCurrentSortClass("estimateEndDate")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'estimateEndDate', '<%=leaveImportHeaderHolder.getNextSortOrder("estimateEndDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.end.time" /></a>
			</th>
			<th style="width: 6%" class="header-nosort"><bean:message bundle="business" key="business.leave.hours" /></th>
			<th style="width: 7%" class="header <%=leaveImportHeaderHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'employeeId', '<%=leaveImportHeaderHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "13"%>%" class="header <%=leaveImportHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'employeeNameZH', '<%=leaveImportHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "13"%>%" class="header <%=leaveImportHeaderHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'employeeNameEN', '<%=leaveImportHeaderHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</a>
			</th>
			<th style="width: 6%" class="header <%=leaveImportHeaderHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'contractId', '<%=leaveImportHeaderHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
				</a>
			</th>
			<logic:equal name="role" value="1">
				<th style="width: 7%" class="header <%=leaveImportHeaderHolder.getCurrentSortClass("clientId")%>">
					<a onclick="submitForm('searchLeave_form', null, null, 'clientId', '<%=leaveImportHeaderHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">客户ID</a>
				</th>
				<th style="width: 16%" class="header-nosort">
					客户名称
				</th>
			</logic:equal>
			<th style="width: 7%" class="header <%=leaveImportHeaderHolder.getCurrentSortClass("modifyBy")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'modifyBy', '<%=leaveImportHeaderHolder.getNextSortOrder("modifyBy")%>', 'tableWrapper');"><bean:message bundle="public" key="public.modify.by" /></a>
			</th>
			<th style="width: 10%" class="header <%=leaveImportHeaderHolder.getCurrentSortClass("modifyDate")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'modifyDate', '<%=leaveImportHeaderHolder.getNextSortOrder("modifyDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.modify.date" /></a>
			</th>
			<th style="width: 7%" class="header <%=leaveImportHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'status', '<%=leaveImportHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="leaveImportHeaderHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="leaveHeaderVO" name="leaveImportHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="timesheetBatchForm" property="status" value="1">
						<logic:equal name="leaveHeaderVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="leaveHeaderVO" property="encodedId"/>" name="chkSelectRow[]" value="<bean:write name="leaveHeaderVO" property="encodedId"/>" />
						</logic:equal>
						</logic:equal>
					</td>
					<td class="left"><bean:write name="leaveHeaderVO" property="leaveHeaderId" /></td>
					<td class="left"><bean:write name="leaveHeaderVO" property="decodeItemId" /></td>
					<td class="left">
						<bean:write name="leaveHeaderVO" property="estimateStartDate" />
						<logic:notEmpty name="leaveHeaderVO" property="estimateStartDate"></logic:notEmpty>
					</td>
					<td class="left">
						<bean:write name="leaveHeaderVO" property="estimateEndDate" />
						<logic:notEmpty name="leaveHeaderVO" property="estimateEndDate"></logic:notEmpty>
					</td>
					<td class="right"><bean:write name="leaveHeaderVO" property="useHours" /></td>
					<td class="left">
						<a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="leaveHeaderVO" property="encodedEmployeeId" />');">
							<bean:write name="leaveHeaderVO" property="employeeId" />
						</a>
					</td>
					<td class="left"><bean:write name="leaveHeaderVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="leaveHeaderVO" property="employeeNameEN" /></td>
					<td class="left">
						<a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="leaveHeaderVO" property="encodedContractId" />');">
							<bean:write name="leaveHeaderVO" property="contractId" />
						</a>
					</td>
					<logic:equal name="role" value="1">
						<td class="left"><bean:write name="leaveHeaderVO" property="clientId" /></td>
						<td class="left"><bean:write name="leaveHeaderVO" property="clientName" /></td>
					</logic:equal>
					<td class="left"><bean:write name="leaveHeaderVO" property="decodeModifyBy" /></td>
					<td class="left"><bean:write name="leaveHeaderVO" property="decodeModifyDate" /></td>
					<td class="left"><bean:write name="leaveHeaderVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="leaveImportHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="14" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="leaveImportHeaderHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="leaveImportHeaderHolder" property="indexStart" /> - <bean:write name="leaveImportHeaderHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="leaveImportHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="leaveImportHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="leaveImportHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="leaveImportHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="leaveImportHeaderHolder" property="realPage" />/<bean:write name="leaveImportHeaderHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
	})(jQuery);	
	
	$('#checkHeader').click( function(){
		if ($(this).attr("checked")) {
			$("input[name='ids']").attr("checked",true);
		}else{
			$("input[name='ids']").attr("checked",false);
		}
	});
</script>