<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder educationHolder = (PagedListHolder)request.getAttribute("educationHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 35%" class="header <%=educationHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listeducation_form', null, null, 'nameZH', '<%=educationHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.edcation.name.cn" /></a>
			</th>
			<th style="width: 35%" class="header <%=educationHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listeducation_form', null, null, 'nameEN', '<%=educationHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.edcation.name.en" /></a>
			</th>
			<th style="width: 10%" class="header <%=educationHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listeducation_form', null, null, 'status', '<%=educationHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			<th style="width: 8%" class="header-nosort"><bean:message bundle="public" key="public.modify.by" /></th>
			<th style="width: 12%" class="header-nosort"><bean:message bundle="public" key="public.modify.date" /></th>
		</tr>
	</thead>			
	<logic:notEqual name="educationHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="educationVO" name="educationHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<logic:notEqual value="1" name="educationVO" property="extended">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="educationVO" property="educationId"/>" name="chkSelectRow[]" value="<bean:write name="educationVO" property="educationId"/>" />
						</logic:notEqual>
					</td>
					<td class="left">
						<a onclick="link('educationAction.do?proc=to_objectModify&encodedId=<bean:write name="educationVO" property="encodedId"/>');"><bean:write name="educationVO" property="nameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('educationAction.do?proc=to_objectModify&encodedId=<bean:write name="educationVO" property="encodedId"/>');"><bean:write name="educationVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="educationVO" property="decodeStatus"/></td>
					<td class="left"><bean:write name="educationVO" property="decodeModifyBy"/></td>
					<td class="left"><bean:write name="educationVO" property="decodeModifyDate"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="educationHolder">
		<tfoot>
			 <tr class="total">
				<td colspan="6" class="left"> 
					  <label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="educationHolder" property="holderSize" /></label>
					  <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="educationHolder" property="indexStart" /> - <bean:write name="educationHolder" property="indexEnd" /></label>
					  <label>&nbsp;&nbsp;<a  onclick="submitForm('listeducation_form', null, '<bean:write name="educationHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					  <label>&nbsp;<a onclick="submitForm('listeducation_form', null, '<bean:write name="educationHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					  <label>&nbsp;<a onclick="submitForm('listeducation_form', null, '<bean:write name="educationHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					  <label>&nbsp;<a onclick="submitForm('listeducation_form', null, '<bean:write name="educationHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					  <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="educationHolder" property="realPage" />/<bean:write name="educationHolder" property="pageCount" /></label>&nbsp;
				</td>					
			 </tr>
		</tfoot>
	</logic:present>
</table>