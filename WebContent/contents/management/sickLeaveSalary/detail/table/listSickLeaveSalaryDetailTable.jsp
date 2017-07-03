<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%																									
	PagedListHolder sickLeaveSalaryDetailHolder = (PagedListHolder)request.getAttribute("sickLeaveSalaryDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 8%; display:none;" class="header <%=sickLeaveSalaryDetailHolder.getCurrentSortClass("detailId")%>">
				<a onclick="submitForm('listsickLeaveSalaryDetail_form', null, null, 'detailId', '<%=sickLeaveSalaryDetailHolder.getNextSortOrder("detailId")%>', 'tableWrapper');">²¡¼Ù¹¤×ÊID</a>
			</th>
			<th style="width: 30%" class="header <%=sickLeaveSalaryDetailHolder.getCurrentSortClass("rangeFrom")%>">
				<a onclick="submitForm('listsickLeaveSalaryDetail_form', null, null, 'rangeFrom', '<%=sickLeaveSalaryDetailHolder.getNextSortOrder("rangeFrom")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sick.leave.detail.range.from" /></a>
			</th>
			<th style="width: 30%" class="header <%=sickLeaveSalaryDetailHolder.getCurrentSortClass("rangeTo")%>">
				<a onclick="submitForm('listsickLeaveSalaryDetail_form', null, null, 'rangeTo', '<%=sickLeaveSalaryDetailHolder.getNextSortOrder("rangeTo")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sick.leave.detail.range.to" /></a>
			</th>
			<th style="width: 20%" class="header <%=sickLeaveSalaryDetailHolder.getCurrentSortClass("percentage")%>">
				<a onclick="submitForm('listsickLeaveSalaryDetail_form', null, null, 'percentage', '<%=sickLeaveSalaryDetailHolder.getNextSortOrder("percentage")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sick.leave.detail.percentage" /></a>
			</th>
			<th style="width: 15%; display:none;" class="header <%=sickLeaveSalaryDetailHolder.getCurrentSortClass("fix")%>">
				<a onclick="submitForm('listsickLeaveSalaryDetail_form', null, null, 'fix', '<%=sickLeaveSalaryDetailHolder.getNextSortOrder("fix")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sick.leave.detail.fix" /></a>
			</th>
			<th style="width: 15%;" class="header <%=sickLeaveSalaryDetailHolder.getCurrentSortClass("deduct")%>">
				<a onclick="submitForm('listsickLeaveSalaryDetail_form', null, null, 'deduct', '<%=sickLeaveSalaryDetailHolder.getNextSortOrder("deduct")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sick.leave.detail.deduct" /></a>
			</th>
			<th style="width: 20%" class="header <%=sickLeaveSalaryDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listsickLeaveSalaryDetail_form', null, null, 'status', '<%=sickLeaveSalaryDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
 	<logic:notEqual name="sickLeaveSalaryDetailHolder" property="holderSize" value="0">
		 <tbody>
			<logic:iterate id="sickLeaveSalaryDetailVO" name="sickLeaveSalaryDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="sickLeaveSalaryDetailVO" property="detailId"/>" name="chkSelectRow[]" value="<bean:write name="sickLeaveSalaryDetailVO" property="detailId"/>" />
					</td>
					<td style="display:none;" class="left"><a onclick="sickLeaveSalaryDetailModify('<bean:write name="sickLeaveSalaryDetailVO" property="detailId"/>');"><bean:write name="sickLeaveSalaryDetailVO" property="detailId"/></a></td>
					<td class="left"><a onclick="sickLeaveSalaryDetailModify('<bean:write name="sickLeaveSalaryDetailVO" property="detailId"/>');"><bean:write name="sickLeaveSalaryDetailVO" property="decodeRangeFrom"/></a></td>
					<td class="left"><a onclick="sickLeaveSalaryDetailModify('<bean:write name="sickLeaveSalaryDetailVO" property="detailId"/>');"><bean:write name="sickLeaveSalaryDetailVO" property="decodeRangeTo"/></a></td>
					<td class="right"><bean:write name="sickLeaveSalaryDetailVO" property="decodePercentage"/>%</td>
					<td style="display:none;" class="right"><bean:write name="sickLeaveSalaryDetailVO" property="decodeFix"/></td>
					<td class="right">
							<bean:write name="sickLeaveSalaryDetailVO" property="decodeDeduct"/>
					</td>
					<td class="left"><bean:write name="sickLeaveSalaryDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody> 
	</logic:notEqual> 
	<tfoot>
		<tr class="total">
			<td colspan="9" class="left"> 
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="sickLeaveSalaryDetailHolder" property="holderSize" /></label>
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="sickLeaveSalaryDetailHolder" property="indexStart" /> - <bean:write name="sickLeaveSalaryDetailHolder" property="indexEnd" /></label>
				<label>&nbsp;&nbsp;<a onclick="submitForm('listSickLeaveSalaryDetail_form', null, '<bean:write name="sickLeaveSalaryDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
				<label>&nbsp;<a onclick="submitForm('listSickLeaveSalaryDetail_form', null, '<bean:write name="sickLeaveSalaryDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
				<label>&nbsp;<a onclick="submitForm('listSickLeaveSalaryDetail_form', null, '<bean:write name="sickLeaveSalaryDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
				<label>&nbsp;<a onclick="submitForm('listSickLeaveSalaryDetail_form', null, '<bean:write name="sickLeaveSalaryDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="sickLeaveSalaryDetailHolder" property="realPage" />/<bean:write name="sickLeaveSalaryDetailHolder" property="pageCount" /></label>&nbsp;
			</td>					
		</tr>
	</tfoot>
</table>