<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder taxTemplateDetailHolder = (PagedListHolder)request.getAttribute("taxTemplateDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 20%" class="header <%=taxTemplateDetailHolder.getCurrentSortClass("propertyName")%>">
				<a onclick="submitForm('listTaxTemplateDetail_form', null, null, 'propertyName', '<%=taxTemplateDetailHolder.getNextSortOrder("propertyName")%>', 'tableWrapper');"><bean:message bundle="management" key="management.salary.template.detail.property.name" /></a>
			</th>
			<th style="width: 20%" class="header <%=taxTemplateDetailHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listTaxTemplateDetail_form', null, null, 'nameZH', '<%=taxTemplateDetailHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.salary.template.detail.column.name.cn" /></a>
			</th>
			<th style="width: 20%" class="header <%=taxTemplateDetailHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listTaxTemplateDetail_form', null, null, 'nameEN', '<%=taxTemplateDetailHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.salary.template.detail.column.name.en" /></a>
			</th>
			<th style="width: 10%" class="header <%=taxTemplateDetailHolder.getCurrentSortClass("columnIndex")%>">
				<a onclick="submitForm('listTaxTemplateDetail_form', null, null, 'columnIndex', '<%=taxTemplateDetailHolder.getNextSortOrder("columnIndex")%>', 'tableWrapper');"><bean:message bundle="management" key="management.salary.template.detail.column.index" /></a>
			</th>
			<th style="width: 20%" class="header <%=taxTemplateDetailHolder.getCurrentSortClass("columnWidth")%>">
				<a onclick="submitForm('listTaxTemplateDetail_form', null, null, 'columnWidth', '<%=taxTemplateDetailHolder.getNextSortOrder("columnWidth")%>', 'tableWrapper');"><bean:message bundle="management" key="management.salary.template.detail.column.width" /></a>
			</th>
			<th style="width: 10%" class="header <%=taxTemplateDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listTaxTemplateDetail_form', null, null, 'status', '<%=taxTemplateDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>							
		</tr>
	</thead>	
	<logic:notEqual name="taxTemplateDetailHolder" property="holderSize" value="0">						
		<tbody>
			<logic:iterate id="taxTemplateDetailVO" name="taxTemplateDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="taxTemplateDetailVO" property="templateDetailId"/>" name="chkSelectRow[]" value="<bean:write name="taxTemplateDetailVO" property="templateDetailId"/>" />
					</td>
					<td class="left"><bean:write name="taxTemplateDetailVO" property="propertyName"/></td>
					<td class="left">
						<a onclick="taxTemplateDetailModify('<bean:write name="taxTemplateDetailVO" property="encodedId"/>');"><bean:write name="taxTemplateDetailVO" property="nameZH"/></a>
					</td>	
					<td class="left">
						<a onclick="taxTemplateDetailModify('<bean:write name="taxTemplateDetailVO" property="encodedId"/>');"><bean:write name="taxTemplateDetailVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="taxTemplateDetailVO" property="columnIndex"/></td>
					<td class="left"><bean:write name="taxTemplateDetailVO" property="columnWidth"/></td>
					<td class="left"><bean:write name="taxTemplateDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="taxTemplateDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="taxTemplateDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="taxTemplateDetailHolder" property="indexStart" /> - <bean:write name="taxTemplateDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listTaxTemplateDetail_form', null, '<bean:write name="taxTemplateDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listTaxTemplateDetail_form', null, '<bean:write name="taxTemplateDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listTaxTemplateDetail_form', null, '<bean:write name="taxTemplateDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listTaxTemplateDetail_form', null, '<bean:write name="taxTemplateDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="taxTemplateDetailHolder" property="realPage" />/<bean:write name="taxTemplateDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
		 	</tr>
		</tfoot>
	</logic:present>
</table>