<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder calendarHeaderHolder = (PagedListHolder) request.getAttribute("calendarHeaderHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=calendarHeaderHolder.getCurrentSortClass("headerId")%>">
				<a onclick="submitForm('listCalendarHeader_form', null, null, 'headerId', '<%=calendarHeaderHolder.getNextSortOrder("headerId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.calendar.header.id" /></a>
			</th>
			<th style="width: 40%" class="header <%=calendarHeaderHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listCalendarHeader_form', null, null, 'nameZH', '<%=calendarHeaderHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.calendar.header.name.cn" /></a>
			</th>
			<th style="width: 40%" class="header <%=calendarHeaderHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listCalendarHeader_form', null, null, 'nameEN', '<%=calendarHeaderHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.calendar.header.name.en" /></a>
			</th>
			<th style="width: 10%" class="header <%=calendarHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listCalendarHeader_form', null, null, 'status', '<%=calendarHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="calendarHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="calendarHeaderVO" name="calendarHeaderHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<!-- Super -->
						<logic:equal name="accountId" value="1">
							<logic:equal name="calendarHeaderVO" property="extended" value="2">
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="calendarHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="calendarHeaderVO" property="headerId"/>" />
							</logic:equal>
						</logic:equal>
						
						<!-- Common Account -->
						<logic:notEqual name="accountId" value="1">
							<logic:notEqual name="calendarHeaderVO" property="accountId" value="1">
								<logic:equal name="calendarHeaderVO" property="extended" value="2">
									<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="calendarHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="calendarHeaderVO" property="headerId"/>" />
								</logic:equal>
							</logic:notEqual>
						</logic:notEqual>		
					</td>
					<td class="left">
						<a onclick="link('calendarDetailAction.do?proc=list_object&headerId=<bean:write name="calendarHeaderVO" property="encodedId"/>');"><bean:write name="calendarHeaderVO" property="headerId"/></a>
					</td>
					<td class="left">
						<a onclick="link('calendarDetailAction.do?proc=list_object&headerId=<bean:write name="calendarHeaderVO" property="encodedId"/>');"><bean:write name="calendarHeaderVO" property="nameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('calendarDetailAction.do?proc=list_object&headerId=<bean:write name="calendarHeaderVO" property="encodedId"/>');"><bean:write name="calendarHeaderVO" property="nameEN"/></a>
					</td>
					<td class="left">
						<bean:write name="calendarHeaderVO" property="decodeStatus"/>&nbsp;
						<a class="kanhandle" onclick="quickCalendarPopup('<bean:write name="calendarHeaderVO" property="encodedId" />');"><img src="images/search.png" title="快速查看日历"  /></a>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="calendarHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="6" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="calendarHeaderHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="calendarHeaderHolder" property="indexStart" /> - <bean:write name="calendarHeaderHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listCalendarHeader_form', null, '<bean:write name="calendarHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listCalendarHeader_form', null, '<bean:write name="calendarHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listCalendarHeader_form', null, '<bean:write name="calendarHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listCalendarHeader_form', null, '<bean:write name="calendarHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="calendarHeaderHolder" property="realPage" />/<bean:write name="calendarHeaderHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>