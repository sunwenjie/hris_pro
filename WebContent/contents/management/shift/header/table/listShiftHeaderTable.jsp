<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder shiftHeaderHolder = (PagedListHolder) request.getAttribute("shiftHeaderHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=shiftHeaderHolder.getCurrentSortClass("headerId")%>">
				<a onclick="submitForm('listShiftHeader_form', null, null, 'headerId', '<%=shiftHeaderHolder.getNextSortOrder("headerId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.shift.header.id" /></a>
			</th>
			<th style="width: 25%" class="header <%=shiftHeaderHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listShiftHeader_form', null, null, 'nameZH', '<%=shiftHeaderHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.shift.header.name.cn" /></a>
			</th>
			<th style="width: 25%" class="header <%=shiftHeaderHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listShiftHeader_form', null, null, 'nameEN', '<%=shiftHeaderHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.shift.header.name.en" /></a>
			</th>
			<th style="width: 15%" class="header <%=shiftHeaderHolder.getCurrentSortClass("shiftType")%>">
				<a onclick="submitForm('listShiftHeader_form', null, null, 'shiftType', '<%=shiftHeaderHolder.getNextSortOrder("shiftType")%>', 'tableWrapper');"><bean:message bundle="management" key="management.shift.header.type" /></a>
			</th>
			<th style="width: 15%" class="header <%=shiftHeaderHolder.getCurrentSortClass("shiftIndex")%>">
				<a onclick="submitForm('listShiftHeader_form', null, null, 'shiftIndex', '<%=shiftHeaderHolder.getNextSortOrder("shiftIndex")%>', 'tableWrapper');"><bean:message bundle="management" key="management.shift.header.cycle" /></a>
			</th>
			<th style="width: 10%" class="header <%=shiftHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listShiftHeader_form', null, null, 'status', '<%=shiftHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="shiftHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="shiftHeaderVO" name="shiftHeaderHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<!-- Super -->
						<logic:equal name="accountId" value="1">
							<logic:equal name="shiftHeaderVO" property="extended" value="2">
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="shiftHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="shiftHeaderVO" property="headerId"/>" />
							</logic:equal>
						</logic:equal>
						
						<!-- Common Account -->
						<logic:notEqual name="accountId" value="1">
							<logic:notEqual name="shiftHeaderVO" property="accountId" value="1">
								<logic:equal name="shiftHeaderVO" property="extended" value="2">
									<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="shiftHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="shiftHeaderVO" property="headerId"/>" />
								</logic:equal>
							</logic:notEqual>
						</logic:notEqual>
					</td>
					<td class="left">
						<a onclick="link('shiftDetailAction.do?proc=list_object&id=<bean:write name="shiftHeaderVO" property="encodedId"/>');"><bean:write name="shiftHeaderVO" property="headerId"/></a>
					</td>
					<td class="left">
						<a onclick="link('shiftDetailAction.do?proc=list_object&id=<bean:write name="shiftHeaderVO" property="encodedId"/>');"><bean:write name="shiftHeaderVO" property="nameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('shiftDetailAction.do?proc=list_object&id=<bean:write name="shiftHeaderVO" property="encodedId"/>');"><bean:write name="shiftHeaderVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="shiftHeaderVO" property="decodeShiftType"/></td>
					<td class="left"><bean:write name="shiftHeaderVO" property="shiftIndex"/></td>
					<td class="left"><bean:write name="shiftHeaderVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="shiftHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="7" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="shiftHeaderHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="shiftHeaderHolder" property="indexStart" /> - <bean:write name="shiftHeaderHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listShiftHeader_form', null, '<bean:write name="shiftHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listShiftHeader_form', null, '<bean:write name="shiftHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listShiftHeader_form', null, '<bean:write name="shiftHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listShiftHeader_form', null, '<bean:write name="shiftHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="shiftHeaderHolder" property="realPage" />/<bean:write name="shiftHeaderHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>