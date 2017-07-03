<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder attendanceImportHeaderHolder = ( PagedListHolder ) request.getAttribute("attendanceImportHeaderHolder");
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
			<th class="header  <%=attendanceImportHeaderHolder.getCurrentSortClass("headerId")%>">
				<a onclick="submitForm('search_attendanceImportHeaderForm', null, null, 'headerId', '<%=attendanceImportHeaderHolder.getNextSortOrder("headerId")%>', 'tableWrapper');">
					ID
				</a>
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="business.ts.month" />
			</th>
			<th class="header-nosort ">
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
				<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>	
			</th>
			<th class="header-nosort ">
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
				<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>	
			</th>
			<th class="header-nosort ">
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
				<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>	
			</th>
			<th class="header-nosort">
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
				<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>	
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="business.ts.work.hours" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="business.ts.work.days" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="business.ts.toal.full.hours" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="business" key="business.ts.toal.full.days" />
			</th>
			<th class="header-nosort">
				<bean:message bundle="public" key="public.note" />
			</th>
			<th class="header <%=attendanceImportHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('search_attendanceImportHeaderForm', null, null, 'batchId', '<%=attendanceImportHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="attendanceImportHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="attendanceImportHeaderVO" name="attendanceImportHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="attendanceImportHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="attendanceImportHeaderVO" property="encodedId"/>" />
					</td>
					<td class="left"> <a onclick="link('attendanceImportDetailAction.do?proc=list_object&headerId=<bean:write name="attendanceImportHeaderVO" property="encodedId"/>')"><bean:write name="attendanceImportHeaderVO" property="headerId"/></a>  </td>
					<td class="left"> <bean:write name="attendanceImportHeaderVO" property="monthly"/>  </td>
					<td class="left"> <bean:write name="attendanceImportHeaderVO" property="employeeId"/>  </td>
					<td class="left"> <bean:write name="attendanceImportHeaderVO" property="employeeNameZH"/>  </td>
					<td class="left"> <bean:write name="attendanceImportHeaderVO" property="employeeNameEN"/>  </td>
					<td class="left"> <bean:write name="attendanceImportHeaderVO" property="contractId"/>  </td>
					<td class="left"> <bean:write name="attendanceImportHeaderVO" property="totalWorkHours"/>  </td>
					<td class="left"> <bean:write name="attendanceImportHeaderVO" property="totalWorkDays"/>  </td>
					<td class="left"> <bean:write name="attendanceImportHeaderVO" property="totalFullHours"/>  </td>
					<td class="left"> <bean:write name="attendanceImportHeaderVO" property="totalFullDays"/>  </td>
					<td class="left" style="color: red;"> <bean:write name="attendanceImportHeaderVO" property="description"/>  </td>
					<td class="left"> <bean:write name="attendanceImportHeaderVO" property="decodeStatus"/>  </td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="attendanceImportHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="13" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="attendanceImportHeaderHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="attendanceImportHeaderHolder" property="indexStart" /> - <bean:write name="attendanceImportHeaderHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('search_attendanceImportHeaderForm', null, '<bean:write name="attendanceImportHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('search_attendanceImportHeaderForm', null, '<bean:write name="attendanceImportHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('search_attendanceImportHeaderForm', null, '<bean:write name="attendanceImportHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('search_attendanceImportHeaderForm', null, '<bean:write name="attendanceImportHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="attendanceImportHeaderHolder" property="realPage" />/<bean:write name="attendanceImportHeaderHolder" property="pageCount" /> </label>&nbsp;</td>
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