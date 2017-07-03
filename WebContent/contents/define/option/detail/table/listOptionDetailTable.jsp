<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	PagedListHolder optionDetailHolder = (PagedListHolder)request.getAttribute("optionDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 25%" class="header <%=optionDetailHolder.getCurrentSortClass("optionNameZH")%>">
				<a onclick="submitForm('listoptionDetail_form', null, null, 'optionNameZH', '<%=optionDetailHolder.getNextSortOrder("optionNameZH")%>', 'tableWrapper');"><bean:message bundle="define" key="define.option.detail.value.name.cn" /></a>
			</th>
			<th style="width: 25%" class="header <%=optionDetailHolder.getCurrentSortClass("optionNameEN")%>">
				<a onclick="submitForm('listoptionDetail_form', null, null, 'optionNameEN', '<%=optionDetailHolder.getNextSortOrder("optionNameEN")%>', 'tableWrapper');"><bean:message bundle="define" key="define.option.detail.value.name.en" /></a>
			</th>
			<th style="width: 25%" class="header <%=optionDetailHolder.getCurrentSortClass("optionIndex")%>">
				<a onclick="submitForm('listoptionDetail_form', null, null, 'optionIndex', '<%=optionDetailHolder.getNextSortOrder("optionIndex")%>', 'tableWrapper');"><bean:message bundle="define" key="define.option.detail.value.index" /></a>
			</th>
			<th style="width: 25%" class="header <%=optionDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listoptionDetail_form', null, null, 'status', '<%=optionDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>							
		</tr>
	</thead>							
	<logic:notEqual name="optionDetailHolder" property="holderSize" value="0">						
		<tbody>
			<logic:iterate id="optionDetailVO" name="optionDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="optionDetailVO" property="optionDetailId"/>" name="chkSelectRow[]" value="<bean:write name="optionDetailVO" property="optionDetailId"/>" />
					</td>
					<td class="left"><a onclick="optionDetailModify('<bean:write name="optionDetailVO" property="encodedId"/>')"><bean:write name="optionDetailVO" property="optionNameZH"/></a></td>
					<td class="left"><a onclick="optionDetailModify('<bean:write name="optionDetailVO" property="encodedId"/>')"><bean:write name="optionDetailVO" property="optionNameEN"/></a></td>
					<td class="left"><bean:write name="optionDetailVO" property="optionIndex"/></td>	
					<td class="left"><bean:write name="optionDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="optionDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="9" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="optionDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="optionDetailHolder" property="indexStart" /> - <bean:write name="optionDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listoptionDetail_form', null, '<bean:write name="optionDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listoptionDetail_form', null, '<bean:write name="optionDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listoptionDetail_form', null, '<bean:write name="optionDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listoptionDetail_form', null, '<bean:write name="optionDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="optionDetailHolder" property="realPage" />/<bean:write name="optionDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>