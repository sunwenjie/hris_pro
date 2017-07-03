<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder recordHolder = (PagedListHolder)request.getAttribute("recordHolder");
	final boolean IN_HOUSE = request.getAttribute( "role" ).equals( "2" ) ? true : false;
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=recordHolder.getCurrentSortClass("recordId")%>">
				<a onclick="submitForm('listRecord_form', null, null, 'recordId', '<%=recordHolder.getNextSortOrder("recordId")%>', 'tableWrapper');">打卡记录ID</a>
			</th>
			<th style="width: 10%" class="header <%=recordHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('listRecord_form', null, null, 'employeeId', '<%=recordHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');"><%=IN_HOUSE ? "员工" : "雇员" %>ID</a>
			</th>
			<th style="width: 20%" class="header <%=recordHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('listRecord_form', null, null, 'employeeNameZH', '<%=recordHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');"><%=IN_HOUSE ? "员工" : "雇员" %>姓名（中文）</a>
			</th>
			<th style="width: 20%" class="header <%=recordHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('listRecord_form', null, null, 'employeeNameEN', '<%=recordHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');"><%=IN_HOUSE ? "员工" : "雇员" %>姓名（英文）</a>
			</th>
			<th style="width: 20%" class="header <%=recordHolder.getCurrentSortClass("signDate")%>">
				<a onclick="submitForm('listRecord_form', null, null, 'signDate', '<%=recordHolder.getNextSortOrder("signDate")%>', 'tableWrapper');">打卡日期</a>
			</th>
			<th style="width: 10%" class="header <%=recordHolder.getCurrentSortClass("signTime")%>">
				<a onclick="submitForm('listRecord_form', null, null, 'signTime', '<%=recordHolder.getNextSortOrder("signTime")%>', 'tableWrapper');">打卡时间</a>
			</th>
			<th style="width: 10%" class="header <%=recordHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listRecord_form', null, null, 'status', '<%=recordHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>							
		</tr>
	</thead>	
	<logic:notEqual name="recordHolder" property="holderSize" value="0">						
		<tbody>
			<logic:iterate id="recordVO" name="recordHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="recordVO" property="recordId"/>" name="chkSelectRow[]" value="<bean:write name="recordVO" property="encodedId"/>" />
					</td>
					<td class="left">
						<a onclick="to_recordModify('<bean:write name="recordVO" property="encodedId"/>');"><bean:write name="recordVO" property="recordId"/></a>
					</td>	
					<td class="left"><bean:write name="recordVO" property="employeeId"/></td>
					<td class="left"><bean:write name="recordVO" property="employeeNameZH"/></td>
					<td class="left"><bean:write name="recordVO" property="employeeNameEN"/></td>
					<td class="left"><bean:write name="recordVO" property="signDate"/></td>
					<td class="left"><bean:write name="recordVO" property="signTime"/></td>
					<td class="left"><bean:write name="recordVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="recordHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="recordHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="recordHolder" property="indexStart" /> - <bean:write name="recordHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listRecord_form', null, '<bean:write name="recordHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listRecord_form', null, '<bean:write name="recordHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listRecord_form', null, '<bean:write name="recordHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listRecord_form', null, '<bean:write name="recordHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="recordHolder" property="realPage" />/<bean:write name="recordHolder" property="pageCount" /></label>&nbsp;
				</td>					
		 	</tr>
		</tfoot>
	</logic:present>
</table>