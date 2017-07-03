<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder calendarDetailHolder = (PagedListHolder) request.getAttribute("calendarDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 30%" class="header <%=calendarDetailHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listCalendarDetail_form', null, null, 'nameZH', '<%=calendarDetailHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.calendar.detail.day.name.cn" /></a>
			</th>
			<th style="width: 30%" class="header <%=calendarDetailHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listCalendarDetail_form', null, null, 'nameEN', '<%=calendarDetailHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.calendar.detail.day.name.en" /></a>
			</th>
			<th style="width: 16%" class="header <%=calendarDetailHolder.getCurrentSortClass("day")%>">
				<a onclick="submitForm('listCalendarDetail_form', null, null, 'day', '<%=calendarDetailHolder.getNextSortOrder("day")%>', 'tableWrapper');"><bean:message bundle="management" key="management.calendar.detail.day" /></a>
			</th>
			<th style="width: 16%" class="header <%=calendarDetailHolder.getCurrentSortClass("dayType")%>">
				<a onclick="submitForm('listCalendarDetail_form', null, null, 'dayType', '<%=calendarDetailHolder.getNextSortOrder("dayType")%>', 'tableWrapper');"><bean:message bundle="management" key="management.calendar.detail.day.type" /></a>
			</th>
			<th style="width: 8%" class="header <%=calendarDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listCalendarDetail_form', null, null, 'status', '<%=calendarDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="calendarDetailHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="calendarDetailVO" name="calendarDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="calendarDetailVO" property="detailId"/>" name="chkSelectRow[]" value="<bean:write name="calendarDetailVO" property="detailId"/>" />
					</td>
					<td class="left"><a onclick="socialDetailModify('<bean:write name="calendarDetailVO" property="encodedId"/>');"><bean:write name="calendarDetailVO" property="nameZH"/></a></td>
					<td class="left"><a onclick="socialDetailModify('<bean:write name="calendarDetailVO" property="encodedId"/>');"><bean:write name="calendarDetailVO" property="nameEN"/></a></td>
					<td class="left"><bean:write name="calendarDetailVO" property="day"/></td>
					<td class="left"><bean:write name="calendarDetailVO" property="decodeDayType"/></td>
					<td class="left"><bean:write name="calendarDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="calendarDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="7" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="calendarDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="calendarDetailHolder" property="indexStart" /> - <bean:write name="calendarDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listCalendarDetail_form', null, '<bean:write name="calendarDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listCalendarDetail_form', null, '<bean:write name="calendarDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listCalendarDetail_form', null, '<bean:write name="calendarDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listCalendarDetail_form', null, '<bean:write name="calendarDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="calendarDetailHolder" property="realPage" />/<bean:write name="calendarDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>