<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder bankTemplateHeaderHolder = (PagedListHolder) request.getAttribute( "bankTemplateHeaderHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=bankTemplateHeaderHolder.getCurrentSortClass("templateHeaderId")%>">
				<a onclick="submitForm('listBankTemplateHeader_form', null, null, 'templateHeaderId', '<%=bankTemplateHeaderHolder.getNextSortOrder("templateHeaderId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.salary.template.header.id" /></a>
			</th>	
			<th style="width: 30%" class="header <%=bankTemplateHeaderHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listBankTemplateHeader_form', null, null, 'nameZH', '<%=bankTemplateHeaderHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.salary.template.header.name.cn" /></a>
			</th>					
			<th style="width: 30%" class="header <%=bankTemplateHeaderHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listBankTemplateHeader_form', null, null, 'nameEN', '<%=bankTemplateHeaderHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.salary.template.header.name.en" /></a>
			</th>	
			<th style="width: 20%" class="header <%=bankTemplateHeaderHolder.getCurrentSortClass("bankId")%>">
				<a onclick="submitForm('listBankTemplateHeader_form', null, null, 'bankId', '<%=bankTemplateHeaderHolder.getNextSortOrder("bankId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.salary.template.header.bank" /></a>
			</th>	
			<th style="width: 10%" class="header <%=bankTemplateHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listBankTemplateHeader_form', null, null, 'status', '<%=bankTemplateHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="bankTemplateHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="bankTemplateHeaderVO" name="bankTemplateHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="bankTemplateHeaderVO" property="templateHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="bankTemplateHeaderVO" property="templateHeaderId"/>" />
					</td>
					<td class="left"><a onclick="link('bankTemplateDetailAction.do?proc=list_object&id=<bean:write name="bankTemplateHeaderVO" property="encodedId"/>');"><bean:write name="bankTemplateHeaderVO" property="templateHeaderId" /></a></td>
					<td class="left"><a onclick="link('bankTemplateDetailAction.do?proc=list_object&id=<bean:write name="bankTemplateHeaderVO" property="encodedId"/>');"><bean:write name="bankTemplateHeaderVO" property="nameZH" /></a></td>
					<td class="left"><a onclick="link('bankTemplateDetailAction.do?proc=list_object&id=<bean:write name="bankTemplateHeaderVO" property="encodedId"/>');"><bean:write name="bankTemplateHeaderVO" property="nameEN" /></a></td>
					<td class="left"><bean:write name="bankTemplateHeaderVO" property="decodeBank" /></td>
					<td class="left"><bean:write name="bankTemplateHeaderVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="bankTemplateHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="7" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="bankTemplateHeaderHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="bankTemplateHeaderHolder" property="indexStart" /> - <bean:write name="bankTemplateHeaderHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listBankTemplateHeader_form', null, '<bean:write name="bankTemplateHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listBankTemplateHeader_form', null, '<bean:write name="bankTemplateHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listBankTemplateHeader_form', null, '<bean:write name="bankTemplateHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listBankTemplateHeader_form', null, '<bean:write name="bankTemplateHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="bankTemplateHeaderHolder" property="realPage" />/<bean:write name="bankTemplateHeaderHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>