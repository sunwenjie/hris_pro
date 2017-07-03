<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder shiftExceptionHolder = (PagedListHolder) request.getAttribute( "shiftExceptionHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 15%" class="header <%=shiftExceptionHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listShiftException_form', null, null, 'nameZH', '<%=shiftExceptionHolder.getNextSortOrder("nameZH")%>', 'tableWrapper_exception');"><bean:message bundle="management" key="management.shift.exception.name.cn" /></a>
			</th>
			<th style="width: 15%" class="header <%=shiftExceptionHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listShiftException_form', null, null, 'nameEN', '<%=shiftExceptionHolder.getNextSortOrder("nameEN")%>', 'tableWrapper_exception');"><bean:message bundle="management" key="management.shift.exception.name.en" /></a>
			</th>
			<th style="width: 8%" class="header <%=shiftExceptionHolder.getCurrentSortClass("exceptionType")%>">
				<a onclick="submitForm('listShiftException_form', null, null, 'exceptionType', '<%=shiftExceptionHolder.getNextSortOrder("exceptionType")%>', 'tableWrapper_exception');"><bean:message bundle="management" key="management.shift.exception.type" /></a>
			</th>
			<th style="width: 8%" class="header <%=shiftExceptionHolder.getCurrentSortClass("dayType")%>">
				<a onclick="submitForm('listShiftException_form', null, null, 'dayType', '<%=shiftExceptionHolder.getNextSortOrder("dayType")%>', 'tableWrapper_exception');"><bean:message bundle="management" key="management.shift.exception.day.type" /></a>
			</th>
			<th style="width: 8%" class="header <%=shiftExceptionHolder.getCurrentSortClass("exceptionDay")%>">
				<a onclick="submitForm('listShiftException_form', null, null, 'exceptionDay', '<%=shiftExceptionHolder.getNextSortOrder("exceptionDay")%>', 'tableWrapper_exception');"><bean:message bundle="management" key="management.shift.exception.day" /></a>
			</th>
			<th style="width: 24%" class="header-nosort"><bean:message bundle="management" key="management.shift.detail.period" /></th>
			<th style="width: 12%" class="header <%=shiftExceptionHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listShiftException_form', null, null, 'itemId', '<%=shiftExceptionHolder.getNextSortOrder("itemId")%>', 'tableWrapper_exception');"><bean:message bundle="management" key="management.shift.exception.item" /></a>
			</th>
			<th style="width: 10%" class="header <%=shiftExceptionHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listShiftException_form', null, null, 'status', '<%=shiftExceptionHolder.getNextSortOrder("status")%>', 'tableWrapper_exception');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="shiftExceptionHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="shiftExceptionVO" name="shiftExceptionHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<!-- Super -->
						<logic:equal name="accountId" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="shiftExceptionVO" property="encodedId"/>" name="chkSelectRow[]" value="<bean:write name="shiftExceptionVO" property="encodedId"/>" />
						</logic:equal>
						
						<!-- Common Account -->
						<logic:notEqual name="accountId" value="1">
							<logic:notEqual name="shiftExceptionVO" property="accountId" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="shiftExceptionVO" property="encodedId"/>" name="chkSelectRow[]" value="<bean:write name="shiftExceptionVO" property="encodedId"/>" />
							</logic:notEqual>
						</logic:notEqual>
					</td>
					<td class="left">
						<a onclick="to_objectModify_ajax_exception('<bean:write name="shiftExceptionVO" property="encodedId"/>');">
							<bean:write name="shiftExceptionVO" property="nameZH" />
						</a>
					</td>
					<td class="left">
						<a onclick="to_objectModify_ajax_exception('<bean:write name="shiftExceptionVO" property="encodedId"/>');">
							<bean:write name="shiftExceptionVO" property="nameEN" />
						</a>
					</td>
					<td class="left"><bean:write name="shiftExceptionVO" property="decodeExceptionType"/></td>
					<td class="left"><bean:write name="shiftExceptionVO" property="decodeDayType"/></td>
					<td class="left"><bean:write name="shiftExceptionVO" property="exceptionDay"/></td>
					<td class="left"><bean:write name="shiftExceptionVO" property="decodeExceptionPeriod"/></td>
					<td class="left"><bean:write name="shiftExceptionVO" property="decodeItemId"/></td>
					<td class="left"><bean:write name="shiftExceptionVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="shiftExceptionHolder">
		<tfoot>
			<tr class="total">
				<td colspan="9" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="shiftExceptionHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="shiftExceptionHolder" property="indexStart" /> - <bean:write name="shiftExceptionHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listShiftException_form', null, '<bean:write name="shiftExceptionHolder" property="firstPage" />', null, null, 'tableWrapper_exception');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listShiftException_form', null, '<bean:write name="shiftExceptionHolder" property="previousPage" />', null, null, 'tableWrapper_exception');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listShiftException_form', null, '<bean:write name="shiftExceptionHolder" property="nextPage" />', null, null, 'tableWrapper_exception');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listShiftException_form', null, '<bean:write name="shiftExceptionHolder" property="lastPage" />', null, null, 'tableWrapper_exception');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="shiftExceptionHolder" property="realPage" />/<bean:write name="shiftExceptionHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>

<script type="text/javascript">
	kanList_init('tableWrapper_exception');
	kanCheckbox_init('tableWrapper_exception');
</script>