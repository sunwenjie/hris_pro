<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder bankTemplateDetailHolder = (PagedListHolder)request.getAttribute("bankTemplateDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 20%" class="header <%=bankTemplateDetailHolder.getCurrentSortClass("propertyName")%>">
				<a onclick="submitForm('listBankTemplateDetail_form', null, null, 'propertyName', '<%=bankTemplateDetailHolder.getNextSortOrder("propertyName")%>', 'tableWrapper');"><bean:message bundle="management" key="management.salary.template.detail.property.name" /></a>
			</th>
			<th style="width: 20%" class="header <%=bankTemplateDetailHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listBankTemplateDetail_form', null, null, 'nameZH', '<%=bankTemplateDetailHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.salary.template.detail.column.name.cn" /></a>
			</th>
			<th style="width: 20%" class="header <%=bankTemplateDetailHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listBankTemplateDetail_form', null, null, 'nameEN', '<%=bankTemplateDetailHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.salary.template.detail.column.name.en" /></a>
			</th>
			<th style="width: 10%" class="header <%=bankTemplateDetailHolder.getCurrentSortClass("columnIndex")%>">
				<a onclick="submitForm('listBankTemplateDetail_form', null, null, 'columnIndex', '<%=bankTemplateDetailHolder.getNextSortOrder("columnIndex")%>', 'tableWrapper');"><bean:message bundle="management" key="management.salary.template.detail.column.index" /></a>
			</th>
			<th style="width: 20%" class="header <%=bankTemplateDetailHolder.getCurrentSortClass("columnWidth")%>">
				<a onclick="submitForm('listBankTemplateDetail_form', null, null, 'columnWidth', '<%=bankTemplateDetailHolder.getNextSortOrder("columnWidth")%>', 'tableWrapper');"><bean:message bundle="management" key="management.salary.template.detail.column.width" /></a>
			</th>
			<th style="width: 10%" class="header <%=bankTemplateDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listBankTemplateDetail_form', null, null, 'status', '<%=bankTemplateDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>							
		</tr>
	</thead>	
	<logic:notEqual name="bankTemplateDetailHolder" property="holderSize" value="0">						
		<tbody>
			<logic:iterate id="bankTemplateDetailVO" name="bankTemplateDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="bankTemplateDetailVO" property="templateDetailId"/>" name="chkSelectRow[]" value="<bean:write name="bankTemplateDetailVO" property="templateDetailId"/>" />
					</td>
					<td class="left"><bean:write name="bankTemplateDetailVO" property="propertyName"/></td>
					<td class="left">
						<a onclick="bankTemplateDetailModify('<bean:write name="bankTemplateDetailVO" property="encodedId"/>');"><bean:write name="bankTemplateDetailVO" property="nameZH"/></a>
					</td>	
					<td class="left">
						<a onclick="bankTemplateDetailModify('<bean:write name="bankTemplateDetailVO" property="encodedId"/>');"><bean:write name="bankTemplateDetailVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="bankTemplateDetailVO" property="columnIndex"/></td>
					<td class="left"><bean:write name="bankTemplateDetailVO" property="columnWidth"/></td>
					<td class="left"><bean:write name="bankTemplateDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="bankTemplateDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="bankTemplateDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="bankTemplateDetailHolder" property="indexStart" /> - <bean:write name="bankTemplateDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listBankTemplateDetail_form', null, '<bean:write name="bankTemplateDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listBankTemplateDetail_form', null, '<bean:write name="bankTemplateDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listBankTemplateDetail_form', null, '<bean:write name="bankTemplateDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listBankTemplateDetail_form', null, '<bean:write name="bankTemplateDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="bankTemplateDetailHolder" property="realPage" />/<bean:write name="bankTemplateDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
		 	</tr>
		</tfoot>
	</logic:present>
</table>