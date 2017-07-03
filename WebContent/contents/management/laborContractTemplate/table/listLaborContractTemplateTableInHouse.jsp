<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder laborContractTemplateHolder = (PagedListHolder) request.getAttribute("laborContractTemplateHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>							
			<th style="width: 8%" class="header <%=laborContractTemplateHolder.getCurrentSortClass("templateId")%>">
				<a onclick="submitForm('listLaborContractTemplate_form', null, null, 'templateId', '<%=laborContractTemplateHolder.getNextSortOrder("templateId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.employee.contract.template.id" /></a>
			</th>
			<th style="width: 15%" class="header <%=laborContractTemplateHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listLaborContractTemplate_form', null, null, 'nameZH', '<%=laborContractTemplateHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.employee.contract.template.name.cn" /></a>
			</th>
			<th style="width: 15%" class="header <%=laborContractTemplateHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listLaborContractTemplate_form', null, null, 'nameEN', '<%=laborContractTemplateHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.employee.contract.template.name.en" /></a>
			</th>
			<th style="width: 20%" class="header-nosort <%=laborContractTemplateHolder.getCurrentSortClass("entityNameList")%>">
				<bean:message bundle="security" key="security.entity" />
			</th>
			<%-- <th style="width: 10%" class="header-nosort <%=laborContractTemplateHolder.getCurrentSortClass("businessTypeNameList")%>">
				<bean:message bundle="security" key="security.business.type" />
			</th> --%>
			<th style="width: 8%" class="header <%=laborContractTemplateHolder.getCurrentSortClass("contentType")%>">
				<a onclick="submitForm('listLaborContractTemplate_form', null, null, 'contentType', '<%=laborContractTemplateHolder.getNextSortOrder("contentType")%>', 'tableWrapper');"><bean:message bundle="management" key="management.employee.contract.template.content.type" /></a>
			</th>
			<th style="width: 8%" class="header <%=laborContractTemplateHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listLaborContractTemplate_form', null, null, 'status', '<%=laborContractTemplateHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			<th style="width: 8%" class="header-nosort"><bean:message bundle="public" key="public.modify.by" /></th>
			<th style="width: 8%" class="header-nosort"><bean:message bundle="public" key="public.modify.date" /></th>
		</tr>
	</thead>
	<logic:notEqual name="laborContractTemplateHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="laborContractTemplateVO" name="laborContractTemplateHolder" property="source" indexId="number">
				<tr class="<%= number % 2 == 1 ? "odd" : "even" %>">
					<td>
						<logic:notEqual value="1" name="laborContractTemplateVO" property="extended">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="laborContractTemplateVO" property="templateId"/>" name="chkSelectRow[]" value="<bean:write name="laborContractTemplateVO" property="templateId"/>" />
						</logic:notEqual>
					</td>
					<td class="left">
						<a onclick="link('laborContractTemplateAction.do?proc=to_objectModify&templateId=<bean:write name="laborContractTemplateVO" property="encodedId"/>');"><bean:write name="laborContractTemplateVO" property="templateId"/></a>
					</td>
					<td class="left"><bean:write name="laborContractTemplateVO" property="nameZH"/></td>
					<td class="left"><bean:write name="laborContractTemplateVO" property="nameEN"/></td>
					<td class="left"><bean:write name="laborContractTemplateVO" property="decodeEntityIds"/></td>
					<%-- <td class="left"><bean:write name="laborContractTemplateVO" property="decodeBusinessTypeIds"/></td> --%>
					<td class="left"><bean:write name="laborContractTemplateVO" property="decodeContentType"/></td>
					<td class="left"><bean:write name="laborContractTemplateVO" property="decodeStatus"/></td>
					<td class="left"><bean:write name="laborContractTemplateVO" property="decodeModifyBy"/></td>
					<td class="left"><bean:write name="laborContractTemplateVO" property="decodeModifyDate"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="laborContractTemplateHolder">
		<tfoot>
			<tr class="total">
				<td  colspan="11" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="laborContractTemplateHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="laborContractTemplateHolder" property="indexStart" /> - <bean:write name="laborContractTemplateHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listLaborContractTemplate_form', null, '<bean:write name="laborContractTemplateHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listLaborContractTemplate_form', null, '<bean:write name="laborContractTemplateHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listLaborContractTemplate_form', null, '<bean:write name="laborContractTemplateHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listLaborContractTemplate_form', null, '<bean:write name="laborContractTemplateHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="laborContractTemplateHolder" property="realPage" />/<bean:write name="laborContractTemplateHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>