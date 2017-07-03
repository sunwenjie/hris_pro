<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder languageHolder = (PagedListHolder)request.getAttribute("languageHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 35%" class="header <%=languageHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listlanguage_form', null, null, 'nameZH', '<%=languageHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.language.name.cn" /></a>
			</th>
			<th style="width: 35%" class="header <%=languageHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listlanguage_form', null, null, 'nameEN', '<%=languageHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.language.name.en" /></a>
			</th>
			<th style="width: 10%" class="header <%=languageHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listlanguage_form', null, null, 'status', '<%=languageHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			<th style="width: 8%" class="header-nosort"><bean:message bundle="public" key="public.modify.by" /></th>
			<th style="width: 12%" class="header-nosort"><bean:message bundle="public" key="public.modify.date" /></th>
		</tr>
	</thead>			
	<logic:notEqual name="languageHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="languageVO" name="languageHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<logic:equal value="2" name="languageVO" property="extended">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="languageVO" property="languageId"/>" name="chkSelectRow[]" value="<bean:write name="languageVO" property="languageId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('languageAction.do?proc=to_objectModify&encodedId=<bean:write name="languageVO" property="encodedId"/>');"><bean:write name="languageVO" property="nameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('languageAction.do?proc=to_objectModify&encodedId=<bean:write name="languageVO" property="encodedId"/>');"><bean:write name="languageVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="languageVO" property="decodeStatus"/></td>
					<td class="left"><bean:write name="languageVO" property="decodeModifyBy"/></td>
					<td class="left"><bean:write name="languageVO" property="decodeModifyDate"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="languageHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left"> 
					 <label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="languageHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="languageHolder" property="indexStart" /> - <bean:write name="languageHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listlanguage_form', null, '<bean:write name="languageHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listlanguage_form', null, '<bean:write name="languageHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listlanguage_form', null, '<bean:write name="languageHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listlanguage_form', null, '<bean:write name="languageHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="languageHolder" property="realPage" />/<bean:write name="languageHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>