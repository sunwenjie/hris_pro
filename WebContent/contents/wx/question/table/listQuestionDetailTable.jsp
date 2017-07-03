<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder questionDetailHolder = (PagedListHolder)request.getAttribute("questionDetailHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 40%" class="header <%=questionDetailHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listQuestionDetail_form', null, null, 'nameZH', '<%=questionDetailHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">
					Chinese Option
				</a>
			</th>
			<th style="width: 40%" class="header <%=questionDetailHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listQuestionDetail_form', null, null, 'nameEN', '<%=questionDetailHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">
					English Option
				</a>
			</th>
			<th style="width: 20%" class="header <%=questionDetailHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listQuestionDetail_form', null, null, 'status', '<%=questionDetailHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>							
		</tr>
	</thead>	
	<logic:notEqual name="questionDetailHolder" property="holderSize" value="0">						
		<tbody>
			<logic:iterate id="questionDetailVO" name="questionDetailHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="questionDetailVO" property="detailId"/>" name="chkSelectRow[]" value="<bean:write name="questionDetailVO" property="detailId"/>" />
					</td>
					<td class="left">
						<a onclick="questionDetailModify('<bean:write name="questionDetailVO" property="encodedId"/>');">
						<bean:write name="questionDetailVO" property="nameZH"/>
						</a>
					</td>
					<td class="left">
						<a onclick="questionDetailModify('<bean:write name="questionDetailVO" property="encodedId"/>');">
						<bean:write name="questionDetailVO" property="nameEN"/>
						</a>
					</td>
					<td class="left"><bean:write name="questionDetailVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="questionDetailHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="questionDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="questionDetailHolder" property="indexStart" /> - <bean:write name="questionDetailHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listQuestionDetail_form', null, '<bean:write name="questionDetailHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listQuestionDetail_form', null, '<bean:write name="questionDetailHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listQuestionDetail_form', null, '<bean:write name="questionDetailHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listQuestionDetail_form', null, '<bean:write name="questionDetailHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="questionDetailHolder" property="realPage" />/<bean:write name="questionDetailHolder" property="pageCount" /></label>&nbsp;
				</td>					
		 	</tr>
		</tfoot>
	</logic:present>
</table>