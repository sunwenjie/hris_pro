<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder reportHeaderPagedListHolder = (PagedListHolder) request.getAttribute("reportHeaderPagedListHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 25%" class="header <%=reportHeaderPagedListHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listreportHeader_form', null, null, 'nameZH', '<%=reportHeaderPagedListHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="define" key="define.report.header.name.cn" /></a>
			</th>
			<th style="width: 25%" class="header <%=reportHeaderPagedListHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listreportHeader_form', null, null, 'nameEN', '<%=reportHeaderPagedListHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="define" key="define.report.header.name.en" /></a>
			</th>	
			<th style="width: 20%" class="header <%=reportHeaderPagedListHolder.getCurrentSortClass("tableId")%>">
				<a onclick="submitForm('listreportHeader_form', null, null, 'tableId', '<%=reportHeaderPagedListHolder.getNextSortOrder("tableId")%>', 'tableWrapper');"><bean:message bundle="define" key="define.table" /></a>
			</th>
			<th style="width: 10%" class="header <%=reportHeaderPagedListHolder.getCurrentSortClass("pageSize")%>">
				<a onclick="submitForm('listreportHeader_form', null, null, 'pageSize', '<%=reportHeaderPagedListHolder.getNextSortOrder("pageSize")%>', 'tableWrapper');"><bean:message bundle="define" key="define.list.header.page.size" /></a>
			</th>
			<th style="width: 10%" class="header <%=reportHeaderPagedListHolder.getCurrentSortClass("usePagination")%>">
				<a onclick="submitForm('listreportHeader_form', null, null, 'usePagination', '<%=reportHeaderPagedListHolder.getNextSortOrder("usePagination")%>', 'tableWrapper');"><bean:message bundle="define" key="define.list.header.use.pagination" /></a>
			</th>
			<th style="width: 10%" class="header <%=reportHeaderPagedListHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listreportHeader_form', null, null, 'status', '<%=reportHeaderPagedListHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
		</thead>
			<logic:notEqual name="reportHeaderPagedListHolder" property="holderSize" value="0">
				<tbody>
					<logic:iterate id="reportHeaderVO" name="reportHeaderPagedListHolder" property="source" indexId="number">
						<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
							<td>
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="reportHeaderVO" property="reportHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="reportHeaderVO" property="reportHeaderId"/>" />
							</td>
							<td class="left"><a onclick="link('reportHeaderAction.do?proc=to_objectModify&id=<bean:write name="reportHeaderVO" property="encodedId"/>');"><bean:write name="reportHeaderVO" property="nameZH" /></a></td>
							<td class="left"><a onclick="link('reportHeaderAction.do?proc=to_objectModify&id=<bean:write name="reportHeaderVO" property="encodedId"/>');"><bean:write name="reportHeaderVO" property="nameEN" /></a></td>
							<td class="left"><bean:write name="reportHeaderVO" property="decodeTable" /></td>
							<td class="left"><bean:write name="reportHeaderVO" property="pageSize" /></td>
							<td class="left"><bean:write name="reportHeaderVO" property="decodeUsePagination" /></td>
							<td class="left"><bean:write name="reportHeaderVO" property="decodeStatus" /></td>
						</tr>
					</logic:iterate>
				</tbody>
			</logic:notEqual>
		<logic:present name="reportHeaderPagedListHolder">
			<tfoot>
				<tr class="total">
					<td colspan="8" class="left">
						<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="reportHeaderPagedListHolder" property="holderSize" /> </label> 
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="reportHeaderPagedListHolder" property="indexStart" /> - <bean:write name="reportHeaderPagedListHolder" property="indexEnd" /></label> 
						<label>&nbsp;&nbsp;<a  onclick="submitForm('listreportHeader_form', null, '<bean:write name="reportHeaderPagedListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('listreportHeader_form', null, '<bean:write name="reportHeaderPagedListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('listreportHeader_form', null, '<bean:write name="reportHeaderPagedListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('listreportHeader_form', null, '<bean:write name="reportHeaderPagedListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="reportHeaderPagedListHolder" property="realPage" />/<bean:write name="reportHeaderPagedListHolder" 	property="pageCount" /></label>&nbsp;
					</td>
				</tr>
			</tfoot>
		</logic:present>
</table>