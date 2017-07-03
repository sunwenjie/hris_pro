<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder attendanceImportDetailHolder = ( PagedListHolder ) request.getAttribute("attendanceImportDetailHolder");
%>

<logic:notEmpty name="MESSAGE">
	<div id="_USER_DEFINE_MSG" class="message <bean:write name="MESSAGE_CLASS" /> fadable" style="display:none;">
		<bean:write name="MESSAGE" />
   		<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
	</div>
</logic:notEmpty>
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th class="header  <%=attendanceImportDetailHolder.getCurrentSortClass("detailId")%>">
				<a onclick="submitForm('search_attendanceImportDetailForm', null, null, 'detailId', '<%=attendanceImportDetailHolder.getNextSortOrder("detailId")%>', 'tableWrapper');">
					ID
				</a>
			</th>
			<th class="header <%=attendanceImportDetailHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('search_attendanceImportDetailForm', null, null, 'itemId', '<%=attendanceImportDetailHolder.getNextSortOrder("itemId")%>', 'tableWrapper');">
					科目ID
				</a>
			</th>
			<th class="header <%=attendanceImportDetailHolder.getCurrentSortClass("itemName")%>">
				<a onclick="submitForm('search_attendanceImportDetailForm', null, null, 'itemName', '<%=attendanceImportDetailHolder.getNextSortOrder("itemName")%>', 'tableWrapper');">
					科目名称
				</a>
			</th>
			<th class="header <%=attendanceImportDetailHolder.getCurrentSortClass("hours")%>">
				<a onclick="submitForm('search_attendanceImportDetailForm', null, null, 'hours', '<%=attendanceImportDetailHolder.getNextSortOrder("hours")%>', 'tableWrapper');">
					考勤小时数	
				</a>
			</th>
			<th class="header-nosort">
				<bean:message bundle="public" key="public.note" />
			</th>
			<th class="header <%=attendanceImportDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('search_attendanceImportDetailForm', null, null, 'status', '<%=attendanceImportDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="attendanceImportDetailHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="attendanceImportDetailVO" name="attendanceImportDetailHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="attendanceImportDetailVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="attendanceImportDetailVO" property="encodedId"/>" />
					</td>
					<td class="left"> <bean:write name="attendanceImportDetailVO" property="detailId"/>  </td>
					<td class="left"> <bean:write name="attendanceImportDetailVO" property="itemId"/>  </td>
					<td class="left"> <bean:write name="attendanceImportDetailVO" property="itemName"/>  </td>
					<td class="left"> <bean:write name="attendanceImportDetailVO" property="hours"/>  </td>
					<td class="left" style="color: red;"> <bean:write name="attendanceImportDetailVO" property="description"/>  </td>
					<td class="left"> <bean:write name="attendanceImportDetailVO" property="decodeStatus"/>  </td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="attendanceImportDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="7" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="attendanceImportDetailHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="attendanceImportDetailHolder" property="indexStart" /> - <bean:write name="attendanceImportDetailHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('search_attendanceImportDetailForm', null, '<bean:write name="attendanceImportDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('search_attendanceImportDetailForm', null, '<bean:write name="attendanceImportDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('search_attendanceImportDetailForm', null, '<bean:write name="attendanceImportDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('search_attendanceImportDetailForm', null, '<bean:write name="attendanceImportDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="attendanceImportDetailHolder" property="realPage" />/<bean:write name="attendanceImportDetailHolder" property="pageCount" /> </label>&nbsp;</td>
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