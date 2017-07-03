<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder mappingHeaderPagedListHolder = (PagedListHolder) request.getAttribute("mappingHeaderPagedListHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 13%" class="header <%=mappingHeaderPagedListHolder.getCurrentSortClass("mappingHeaderId")%>">
				<a onclick="submitForm('listmappingHeader_form', null, null, 'mappingHeaderId', '<%=mappingHeaderPagedListHolder.getNextSortOrder("mappingHeaderId")%>', 'tableWrapper');">匹配ID</a>
			</th>
			<th style="width: 13%" class="header <%=mappingHeaderPagedListHolder.getCurrentSortClass("clientId")%>">
				<a onclick="submitForm('listmappingHeader_form', null, null, 'clientId', '<%=mappingHeaderPagedListHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">客户ID</a>
			</th>
			<th style="width: 20%" class="header <%=mappingHeaderPagedListHolder.getCurrentSortClass("clientName")%>">
				<a onclick="submitForm('listmappingHeader_form', null, null, 'clientName', '<%=mappingHeaderPagedListHolder.getNextSortOrder("clientName")%>', 'tableWrapper');">客户名称</a>
			</th>
			<th style="width: 20%" class="header <%=mappingHeaderPagedListHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listmappingHeader_form', null, null, 'nameZH', '<%=mappingHeaderPagedListHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">匹配名称（中文）</a>
			</th>
			<th style="width: 20%" class="header <%=mappingHeaderPagedListHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listmappingHeader_form', null, null, 'nameEN', '<%=mappingHeaderPagedListHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">匹配名称（英文）</a>
			</th>												
			<th style="width: 13%" class="header <%=mappingHeaderPagedListHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listmappingHeader_form', null, null, 'status', '<%=mappingHeaderPagedListHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="mappingHeaderPagedListHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="mappingHeaderVO" name="mappingHeaderPagedListHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="mappingHeaderVO" property="mappingHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="mappingHeaderVO" property="mappingHeaderId"/>" />
					</td>
					<td class="left"><a onclick="link('mappingDetailAction.do?proc=list_object&flag=<bean:write name='flag' />&id=<bean:write name="mappingHeaderVO" property="encodedId"/>');"><bean:write name="mappingHeaderVO" property="mappingHeaderId" /></a></td>
					<td class="left"><a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="mappingHeaderVO" property="encodedClientId" />');"><bean:write name="mappingHeaderVO" property="clientId" /></a></td>
					<td class="left"><bean:write name="mappingHeaderVO" property="clientName" /></td>
					<td class="left"><bean:write name="mappingHeaderVO" property="nameZH" /></td>
					<td class="left"><bean:write name="mappingHeaderVO" property="nameEN" /></td>
					<td class="left"><bean:write name="mappingHeaderVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="mappingHeaderPagedListHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="mappingHeaderPagedListHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="mappingHeaderPagedListHolder" property="indexStart" /> - <bean:write name="mappingHeaderPagedListHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listmappingHeader_form', null, '<bean:write name="mappingHeaderPagedListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmappingHeader_form', null, '<bean:write name="mappingHeaderPagedListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmappingHeader_form', null, '<bean:write name="mappingHeaderPagedListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmappingHeader_form', null, '<bean:write name="mappingHeaderPagedListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="mappingHeaderPagedListHolder" property="realPage" />/<bean:write name="mappingHeaderPagedListHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>