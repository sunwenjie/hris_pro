<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder sickLeaveSalaryHeaderHolder = (PagedListHolder) request.getAttribute("sickLeaveSalaryHeaderHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 8%; display:none;" class="header <%=sickLeaveSalaryHeaderHolder.getCurrentSortClass("headerId")%>">
				<a onclick="submitForm('listSickLeaveSalaryHeader_form', null, null, 'headerId', '<%=sickLeaveSalaryHeaderHolder.getNextSortOrder("headerId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sick.leave.header.id" /></a>
			</th>
			<th style="width: 30%" class="header <%=sickLeaveSalaryHeaderHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listSickLeaveSalaryHeader_form', null, null, 'nameZH', '<%=sickLeaveSalaryHeaderHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sick.leave.header.name.cn" /></a>
			</th>
			<th style="width: 30%" class="header <%=sickLeaveSalaryHeaderHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listSickLeaveSalaryHeader_form', null, null, 'nameEN', '<%=sickLeaveSalaryHeaderHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sick.leave.header.name.en" /></a>
			</th>
			<th style="width: 14%; display:none;" class="header <%=sickLeaveSalaryHeaderHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listSickLeaveSalaryHeader_form', null, null, 'itemId', '<%=sickLeaveSalaryHeaderHolder.getNextSortOrder("itemId")%>', 'tableWrapper');">¿ÆÄ¿ID</a>
			</th>
			<th style="width: 15%" class="header <%=sickLeaveSalaryHeaderHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listSickLeaveSalaryHeader_form', null, null, 'itemId', '<%=sickLeaveSalaryHeaderHolder.getNextSortOrder("itemId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sick.leave.header.item" /></a>
			</th>
			<th style="width: 15%" class="header <%=sickLeaveSalaryHeaderHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listSickLeaveSalaryHeader_form', null, null, 'baseOn', '<%=sickLeaveSalaryHeaderHolder.getNextSortOrder("baseOn")%>', 'tableWrapper');"><bean:message bundle="management" key="management.sick.leave.header.base.on" /></a>
			</th>
			<th style="width: 10%" class="header <%=sickLeaveSalaryHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listSickLeaveSalaryHeader_form', null, null, 'status', '<%=sickLeaveSalaryHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="sickLeaveSalaryHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="sickLeaveSalaryHeaderVO" name="sickLeaveSalaryHeaderHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<!-- Super -->
					 	<logic:equal name="accountId" value="1">
							<logic:equal name="sickLeaveSalaryHeaderVO" property="extended" value="2">
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="sickLeaveSalaryHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="sickLeaveSalaryHeaderVO" property="headerId"/>" />
							</logic:equal>
						</logic:equal>
						
						<!-- Common Account -->
						 <logic:notEqual name="accountId" value="1">
							<logic:notEqual name="sickLeaveSalaryHeaderVO" property="accountId" value="1">
								<logic:equal name="sickLeaveSalaryHeaderVO" property="extended" value="2"> 
									<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="sickLeaveSalaryHeaderVO" property="headerId"/>" name="chkSelectRow[]" value="<bean:write name="sickLeaveSalaryHeaderVO" property="headerId"/>" />
								 </logic:equal>
							</logic:notEqual>
						</logic:notEqual> 
					</td>
					<td style="display:none;" class="left">
						<a onclick="link('sickLeaveSalaryDetailAction.do?proc=list_object&id=<bean:write name="sickLeaveSalaryHeaderVO" property="encodedId"/>');"><bean:write name="sickLeaveSalaryHeaderVO" property="headerId"/></a>
					</td>
					<td class="left">
						<a onclick="link('sickLeaveSalaryDetailAction.do?proc=list_object&id=<bean:write name="sickLeaveSalaryHeaderVO" property="encodedId"/>');"><bean:write name="sickLeaveSalaryHeaderVO" property="nameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('sickLeaveSalaryDetailAction.do?proc=list_object&id=<bean:write name="sickLeaveSalaryHeaderVO" property="encodedId"/>');"><bean:write name="sickLeaveSalaryHeaderVO" property="nameEN"/></a>
					</td>
					<td style="display:none;" class="left">
						<bean:write name="sickLeaveSalaryHeaderVO" property="itemId"/>
					</td>
					<td class="left">
						<bean:write name="sickLeaveSalaryHeaderVO" property="decodeitemId"/>
					</td>
					<td class="left"><bean:write name="sickLeaveSalaryHeaderVO" property="decodebaseOn"/></td>
					<td class="left"><bean:write name="sickLeaveSalaryHeaderVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
		<tfoot>
			<tr class="total">
				<td colspan="9" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="sickLeaveSalaryHeaderHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="sickLeaveSalaryHeaderHolder" property="indexStart" /> - <bean:write name="sickLeaveSalaryHeaderHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listSickLeaveSalaryHeader_form', null, '<bean:write name="sickLeaveSalaryHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSickLeaveSalaryHeader_form', null, '<bean:write name="sickLeaveSalaryHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSickLeaveSalaryHeader_form', null, '<bean:write name="sickLeaveSalaryHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listSickLeaveSalaryHeader_form', null, '<bean:write name="sickLeaveSalaryHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="sickLeaveSalaryHeaderHolder" property="realPage" />/<bean:write name="sickLeaveSalaryHeaderHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
</table>