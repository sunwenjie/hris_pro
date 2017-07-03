<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder taxTemplateHeaderHolder = (PagedListHolder) request.getAttribute( "taxTemplateHeaderHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=taxTemplateHeaderHolder.getCurrentSortClass("templateHeaderId")%>">
				<a onclick="submitForm('listTaxTemplateHeader_form', null, null, 'templateHeaderId', '<%=taxTemplateHeaderHolder.getNextSortOrder("templateHeaderId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.tax.template.header.id" /></a>
			</th>	
			<th style="width: 30%" class="header <%=taxTemplateHeaderHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listTaxTemplateHeader_form', null, null, 'nameZH', '<%=taxTemplateHeaderHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.tax.template.header.name.cn" /></a>
			</th>					
			<th style="width: 30%" class="header <%=taxTemplateHeaderHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listTaxTemplateHeader_form', null, null, 'nameEN', '<%=taxTemplateHeaderHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.tax.template.header.name.en" /></a>
			</th>	
			<th style="width: 20%" class="header <%=taxTemplateHeaderHolder.getCurrentSortClass("cityId")%>">
				<a onclick="submitForm('listTaxTemplateHeader_form', null, null, 'cityId', '<%=taxTemplateHeaderHolder.getNextSortOrder("cityId")%>', 'tableWrapper');"><bean:message bundle="public" key="public.city" /></a>
			</th>	
			<th style="width: 10%" class="header <%=taxTemplateHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listTaxTemplateHeader_form', null, null, 'status', '<%=taxTemplateHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="taxTemplateHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="taxTemplateHeaderVO" name="taxTemplateHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="taxTemplateHeaderVO" property="templateHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="taxTemplateHeaderVO" property="templateHeaderId"/>" />
					</td>
					<td class="left"><a onclick="link('taxTemplateDetailAction.do?proc=list_object&id=<bean:write name="taxTemplateHeaderVO" property="encodedId"/>');"><bean:write name="taxTemplateHeaderVO" property="templateHeaderId" /></a></td>
					<td class="left"><a onclick="link('taxTemplateDetailAction.do?proc=list_object&id=<bean:write name="taxTemplateHeaderVO" property="encodedId"/>');"><bean:write name="taxTemplateHeaderVO" property="nameZH" /></a></td>
					<td class="left"><a onclick="link('taxTemplateDetailAction.do?proc=list_object&id=<bean:write name="taxTemplateHeaderVO" property="encodedId"/>');"><bean:write name="taxTemplateHeaderVO" property="nameEN" /></a></td>
					<td class="left"><bean:write name="taxTemplateHeaderVO" property="decodeCityId" /></td>
					<td class="left"><bean:write name="taxTemplateHeaderVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="taxTemplateHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="7" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="taxTemplateHeaderHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="taxTemplateHeaderHolder" property="indexStart" /> - <bean:write name="taxTemplateHeaderHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listTaxTemplateHeader_form', null, '<bean:write name="taxTemplateHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listTaxTemplateHeader_form', null, '<bean:write name="taxTemplateHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listTaxTemplateHeader_form', null, '<bean:write name="taxTemplateHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listTaxTemplateHeader_form', null, '<bean:write name="taxTemplateHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="taxTemplateHeaderHolder" property="realPage" />/<bean:write name="taxTemplateHeaderHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>