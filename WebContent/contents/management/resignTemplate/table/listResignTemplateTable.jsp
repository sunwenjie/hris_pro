<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder resignTemplateHolder = (PagedListHolder) request.getAttribute("resignTemplateHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>							
			<th style="width: 10%" class="header <%=resignTemplateHolder.getCurrentSortClass("templateId")%>">
				<a onclick="submitForm('listResignTemplate_form', null, null, 'templateId', '<%=resignTemplateHolder.getNextSortOrder("templateId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.employee.resign.template.id" /></a>
			</th>
			<th style="width: 20%" class="header <%=resignTemplateHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listResignTemplate_form', null, null, 'nameZH', '<%=resignTemplateHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.employee.resign.template.name.cn" /></a>
			</th>
			<th style="width: 20%" class="header <%=resignTemplateHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listResignTemplate_form', null, null, 'nameEN', '<%=resignTemplateHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.employee.resign.template.name.en" /></a>
			</th>
			<th style="width: 20%" class="header <%=resignTemplateHolder.getCurrentSortClass("templateType")%>">
				<a onclick="submitForm('listResignTemplate_form', null, null, 'templateType', '<%=resignTemplateHolder.getNextSortOrder("templateType")%>', 'tableWrapper');"><bean:message bundle="management" key="management.employee.resign.template.type" /></a>
			</th>
			<th style="width: 15%" class="header <%=resignTemplateHolder.getCurrentSortClass("contentType")%>">
				<a onclick="submitForm('listResignTemplate_form', null, null, 'contentType', '<%=resignTemplateHolder.getNextSortOrder("contentType")%>', 'tableWrapper');"><bean:message bundle="management" key="management.employee.resign.template.content.type" /></a>
			</th>
			
			<th style="width: 15%" class="header-nosort"><bean:message bundle="public" key="public.modify.by" /></th>
			<th style="width: 10%" class="header-nosort"><bean:message bundle="public" key="public.modify.date" /></th>
			<th style="width: 10%" class="header <%=resignTemplateHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listResignTemplate_form', null, null, 'status', '<%=resignTemplateHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="resignTemplateHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="resignTemplateVO" name="resignTemplateHolder" property="source" indexId="number">
				<tr class="<%= number % 2 == 1 ? "odd" : "even" %>">
					<td>
						<logic:notEqual value="1" name="resignTemplateVO" property="extended">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="resignTemplateVO" property="templateId"/>" name="chkSelectRow[]" value="<bean:write name="resignTemplateVO" property="templateId"/>" />
						</logic:notEqual>
					</td>
					<td class="left">
						<a onclick="link('resignTemplateAction.do?proc=to_objectModify&templateId=<bean:write name="resignTemplateVO" property="encodedId"/>');"><bean:write name="resignTemplateVO" property="templateId"/></a>
					</td>
					<td class="left"><bean:write name="resignTemplateVO" property="nameZH"/></td>
					<td class="left"><bean:write name="resignTemplateVO" property="nameEN"/></td>
					<td class="left"><bean:write name="resignTemplateVO" property="decodeTemplateType"/></td>
					<td class="left"><bean:write name="resignTemplateVO" property="decodeContentType"/></td>
					<td class="left"><bean:write name="resignTemplateVO" property="decodeModifyBy"/></td>
					<td class="left"><bean:write name="resignTemplateVO" property="decodeModifyDate"/></td>
					<td class="left"><bean:write name="resignTemplateVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="resignTemplateHolder">
		<tfoot>
			<tr class="total">
				<td  colspan="11" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="resignTemplateHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="resignTemplateHolder" property="indexStart" /> - <bean:write name="resignTemplateHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listResignTemplate_form', null, '<bean:write name="resignTemplateHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listResignTemplate_form', null, '<bean:write name="resignTemplateHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listResignTemplate_form', null, '<bean:write name="resignTemplateHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listResignTemplate_form', null, '<bean:write name="resignTemplateHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="resignTemplateHolder" property="realPage" />/<bean:write name="resignTemplateHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>