<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder importDetailHolder = (PagedListHolder)request.getAttribute("importDetailHolder");
%>
<div id="messageWrapper">
	<logic:present name="MESSAGE_HEADER_PUBLISH">
		<logic:present name="MESSAGE">
			<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
				<bean:write name="MESSAGE" />
    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
			</div>
		</logic:present>
	</logic:present>
</div>
<!-- ListnDetail - information -->	
<html:form action="importDetailAction.do?proc=list_object" styleClass="listImportDetail_form">
	<fieldset>		
		<input type="hidden" name="importHeaderId" value="<bean:write name="importHeaderForm" property="encodedId"/>"/>			
		<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="importDetailHolder" property="sortColumn" />" /> 
		<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="importDetailHolder" property="sortOrder" />" />
		<input type="hidden" name="page" id="page" value="<bean:write name="importDetailHolder" property="page" />" />
		<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="importDetailHolder" property="selectedIds" />" />
		<input type="hidden" name="subAction" id="subAction" value="" />					
	</fieldset>
</html:form>
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
			</th>
			<th style="width: 15%" class="header <%=importDetailHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'nameZH', '<%=importDetailHolder.getNextSortOrder("nameZH")%>', 'tableWrapper2');"><bean:message bundle="define" key="define.manager.detail.name.cn" /></a>
			</th>
			<th style="width: 15%" class="header <%=importDetailHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'nameEN', '<%=importDetailHolder.getNextSortOrder("nameEN")%>', 'tableWrapper2');"><bean:message bundle="define" key="define.manager.detail.name.en" /></a>
			</th>
			<th style="width: 15%" class="header <%=importDetailHolder.getCurrentSortClass("columnId")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'columnId', '<%=importDetailHolder.getNextSortOrder("columnId")%>', 'tableWrapper2');"><bean:message bundle="define" key="define.manager.detail.original.column.name" /></a>
			</th>
			<th style="width: 15%" class="header <%=importDetailHolder.getCurrentSortClass("tempValue")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'tempValue', '<%=importDetailHolder.getNextSortOrder("tempValue")%>', 'tableWrapper2');"><bean:message bundle="define" key="define.import.detail.demo.value" /></a>
			</th>
			<th style="width: 10%" class="header <%=importDetailHolder.getCurrentSortClass("columnWidth")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'columnWidth', '<%=importDetailHolder.getNextSortOrder("columnWidth")%>', 'tableWrapper2');"><bean:message bundle="define" key="define.list.detail.column.width" /></a>
			</th>
			<th style="width: 10%" class="header <%=importDetailHolder.getCurrentSortClass("columnIndex")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'columnIndex', '<%=importDetailHolder.getNextSortOrder("columnIndex")%>', 'tableWrapper2');"><bean:message bundle="define" key="define.column.column.index" /></a>
			</th>
			<th style="width: 10%" class="header <%=importDetailHolder.getCurrentSortClass("fontSize")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'fontSize', '<%=importDetailHolder.getNextSortOrder("fontSize")%>', 'tableWrapper2');"><bean:message bundle="define" key="define.search.detail.font.size" /></a>
			</th>
			<th style="width: 10%" class="header <%=importDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listImportDetail_form', null, null, 'status', '<%=importDetailHolder.getNextSortOrder("status")%>', 'tableWrapper2');"><bean:message bundle="public" key="public.status" /></a>
			</th>							
		</tr>
	</thead>							
	<logic:notEqual name="importDetailHolder" property="holderSize" value="0">						
		<tbody>
			<logic:iterate id="importDetailVO" name="importDetailHolder" property="source" indexId="number">
				<input type="hidden" id="columnId" name="columnId" value="<bean:write name="importDetailVO" property="columnId"/>" />
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<img title="<bean:message bundle="public" key="button.delete" />" src="images/warning-btn.png" width="12px" height="12px" id="warning_img" name="warning_img"  onclick="removeColumn('<bean:write name="importDetailVO" property="importDetailId" />','<bean:write name="importDetailVO" property="nameZH" />');" />
					</td>
					<td class="left">
						<a onclick="importDetailModify('<bean:write name="importDetailVO" property="encodedId"/>', '<bean:write name="importDetailVO" property="columnId"/>');"><bean:write name="importDetailVO" property="nameZH"/></a>
					</td>	
					<td class="left">
						<a onclick="importDetailModify('<bean:write name="importDetailVO" property="encodedId"/>', '<bean:write name="importDetailVO" property="columnId"/>');"><bean:write name="importDetailVO" property="nameEN"/></a>
					</td>
					<td class="left"><bean:write name="importDetailVO" property="decodeColumn"/></td>
					<td class="left"><bean:write name="importDetailVO" property="tempValue"/></td>
					<td class="left"><bean:write name="importDetailVO" property="columnWidth"/></td>
					<td class="left"><bean:write name="importDetailVO" property="columnIndex"/></td>
					<td class="left"><bean:write name="importDetailVO" property="fontSize"/></td>
					<td class="left"><bean:write name="importDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="importDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="11" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="importDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="importDetailHolder" property="indexStart" /> - <bean:write name="importDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listImportDetail_form', null, '<bean:write name="importDetailHolder" property="firstPage" />', null, null, 'tableWrapper2');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listImportDetail_form', null, '<bean:write name="importDetailHolder" property="previousPage" />', null, null, 'tableWrapper2');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listImportDetail_form', null, '<bean:write name="importDetailHolder" property="nextPage" />', null, null, 'tableWrapper2');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listImportDetail_form', null, '<bean:write name="importDetailHolder" property="lastPage" />', null, null, 'tableWrapper2');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="importDetailHolder" property="realPage" />/<bean:write name="importDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
		 	</tr>
		</tfoot>
	</logic:present>
</table>