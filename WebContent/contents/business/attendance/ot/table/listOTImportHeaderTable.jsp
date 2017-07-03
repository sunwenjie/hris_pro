<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder otImportHeader = ( PagedListHolder ) request.getAttribute( "otImportHeaderHolder" );
%>
<form class="otBackForm">
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" name="checkHeader" id="checkHeader">
			</th>
			<th style="width: 4%" class="header <%=otImportHeader.getCurrentSortClass("otHeaderId")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'otImportHeaderId', '<%=otImportHeader.getNextSortOrder("otHeaderId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ot.id" /></a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "15"%>%" class="header <%=otImportHeader.getCurrentSortClass("estimateStartDate")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'estimateStartDate', '<%=otImportHeader.getNextSortOrder("estimateStartDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.start.time" /></a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "15"%>%" class="header <%=otImportHeader.getCurrentSortClass("estimateEndDate")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'estimateEndDate', '<%=otImportHeader.getNextSortOrder("estimateEndDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.end.time" /></a>
			</th>
			<th style="width: 6%" class="header"><bean:message bundle="business" key="business.ot.hours" /></th>
			<th style="width: 7%" class="header <%=otImportHeader.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'employeeId', '<%=otImportHeader.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "11"%>%" class="header <%=otImportHeader.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'employeeNameZH', '<%=otImportHeader.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "11"%>%" class="header <%=otImportHeader.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'employeeNameEN', '<%=otImportHeader.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</a>
			</th>
			<th style="width: 6%" class="header <%=otImportHeader.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'contractId', '<%=otImportHeader.getNextSortOrder("contractId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
				</a>
			</th>
			<logic:equal name="role" value="1">
				<th style="width: 7%" class="header <%=otImportHeader.getCurrentSortClass("clientId")%>">
					<a onclick="submitForm('searchOT_form', null, null, 'clientId', '<%=otImportHeader.getNextSortOrder("clientId")%>', 'tableWrapper');">客户ID</a>
				</th>
				<th style="width: 17%" class="header-nosort">
					客户名称
				</th>
			</logic:equal>
			<th style="width: 7%" class="header <%=otImportHeader.getCurrentSortClass("modifyBy")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'modifyBy', '<%=otImportHeader.getNextSortOrder("modifyBy")%>', 'tableWrapper');"><bean:message bundle="public" key="public.modify.by" /></a>
			</th>
			<th style="width: 11%" class="header <%=otImportHeader.getCurrentSortClass("modifyDate")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'modifyDate', '<%=otImportHeader.getNextSortOrder("modifyDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.modify.date" /></a>
			</th>
			<th style="width: 7%" class="header <%=otImportHeader.getCurrentSortClass("status")%>">
				<a onclick="submitForm('otImportHeader', null, null, 'status', '<%=otImportHeader.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="otImportHeaderHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="otImportHeaderVO" name="otImportHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="center">
						<logic:equal name="timesheetBatchForm" property="status" value="1">
						<logic:equal name="otImportHeaderVO" property="status" value="1">
							<input type="checkbox" name="ids" id="kanList_chkSelectRecord_<bean:write name="otImportHeaderVO" property="otHeaderId" />" value="<bean:write name="otImportHeaderVO" property="otHeaderId" />">
						</logic:equal>
						</logic:equal>
					</td>
					<td class="left">
						<bean:write name="otImportHeaderVO" property="otHeaderId" />
						<logic:empty name="otImportHeaderVO" property="actualStartDate"><span class="highlight"><%=request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "（预）" : "(Estimate)" %></span></logic:empty>
					</td>
					<td class="left">
						<bean:write name="otImportHeaderVO" property="estimateStartDate" />
					</td>
					<td class="left">
						<bean:write name="otImportHeaderVO" property="estimateEndDate" />
					</td>
					<td class="right">
						<logic:equal name="otImportHeaderVO" property="status" value="1">
							<bean:write name="otImportHeaderVO" property="estimateHours" />
						</logic:equal>
						<logic:equal name="otImportHeaderVO" property="status" value="2">
							<bean:write name="otImportHeaderVO" property="estimateHours" />
						</logic:equal>
						<logic:equal name="otImportHeaderVO" property="status" value="3">
							<bean:write name="otImportHeaderVO" property="estimateHours" />
						</logic:equal>
						<logic:equal name="otImportHeaderVO" property="status" value="4">
							<bean:write name="otImportHeaderVO" property="actualHours" />
						</logic:equal>
						<logic:equal name="otImportHeaderVO" property="status" value="5">
							<bean:write name="otImportHeaderVO" property="actualHours" />
						</logic:equal>
						<logic:equal name="otImportHeaderVO" property="status" value="6">
							<bean:write name="otImportHeaderVO" property="actualHours" />
						</logic:equal>
						<logic:equal name="otImportHeaderVO" property="status" value="7">
							<bean:write name="otImportHeaderVO" property="actualHours" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="otImportHeaderVO" property="encodedEmployeeId" />');"><bean:write name="otImportHeaderVO" property="employeeId" /></a>
					</td>
					<td class="left"><bean:write name="otImportHeaderVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="otImportHeaderVO" property="employeeNameEN" /></td>
					<td class="left">
						<a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="otImportHeaderVO" property="encodedContractId" />');">
							<bean:write name="otImportHeaderVO" property="contractId" />
						</a>	
					</td>
					<logic:equal name="role" value="1">
						<td class="left">
							<a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="otImportHeaderVO" property="encodedClientId" />');"><bean:write name="otImportHeaderVO" property="clientId" /></a>
						</td>
						<td class="left"><bean:write name="otImportHeaderVO" property="clientName" /></td>
					</logic:equal>
					<td class="left"><bean:write name="otImportHeaderVO" property="decodeModifyBy" /></td>
					<td class="left"><bean:write name="otImportHeaderVO" property="decodeModifyDate" /></td>
					<td class="left"><bean:write name="otImportHeaderVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="otImportHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="14" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="otImportHeaderHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="otImportHeaderHolder" property="indexStart" /> - <bean:write name="otImportHeaderHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="otImportHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="otImportHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="otImportHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchTimesheetHeader_form', null, '<bean:write name="otImportHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="otImportHeaderHolder" property="realPage" />/<bean:write name="otImportHeaderHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>
</form>
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