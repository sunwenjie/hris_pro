<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder membershipHolder = (PagedListHolder) request.getAttribute("membershipHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>							
			<th style="width: 35%" class="header <%=membershipHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listmembership_form', null, null, 'nameZH', '<%=membershipHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.membership.name.cn" /></a>
			</th>
			<th style="width: 35%" class="header <%=membershipHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listmembership_form', null, null, 'nameEN', '<%=membershipHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.membership.name.en" /></a>
			</th>
			<th style="width: 10%" class="header <%=membershipHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listmembership_form', null, null, 'status', '<%=membershipHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			<th style="width: 8%" class="header-nosort"><bean:message bundle="public" key="public.modify.by" /></th>
			<th style="width: 12%" class="header-nosort"><bean:message bundle="public" key="public.modify.date" /></th>
		</tr>
	</thead>
	<logic:notEqual name="membershipHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="membershipVO" name="membershipHolder" property="source" indexId="number">
				<tr class="<%= number % 2 == 1 ? "odd" : "even" %>">
					<td>
						<logic:equal value="2" name="membershipVO" property="extended">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="membershipVO" property="membershipId"/>" name="chkSelectRow[]" value="<bean:write name="membershipVO" property="membershipId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('membershipAction.do?proc=to_objectModify&membershipId=<bean:write name="membershipVO" property="encodedId"/>');"><bean:write name="membershipVO" property="nameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('membershipAction.do?proc=to_objectModify&membershipId=<bean:write name="membershipVO" property="encodedId"/>');"><bean:write name="membershipVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="membershipVO" property="decodeStatus"/></td>
					<td class="left"><bean:write name="membershipVO" property="decodeModifyBy"/></td>
					<td class="left"><bean:write name="membershipVO" property="decodeModifyDate"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="membershipHolder">
		<tfoot>
			<tr class="total">
				<td  colspan="6" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="membershipHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="membershipHolder" property="indexStart" /> - <bean:write name="membershipHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listmembership_form', null, '<bean:write name="membershipHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listmembership_form', null, '<bean:write name="membershipHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listmembership_form', null, '<bean:write name="membershipHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listmembership_form', null, '<bean:write name="membershipHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="membershipHolder" property="realPage" />/<bean:write name="membershipHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>