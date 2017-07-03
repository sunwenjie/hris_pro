<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder optionHeaderHolder = ( PagedListHolder ) request.getAttribute( "optionHeaderHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 45%" class="header <%=optionHeaderHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listoptionHeader_form', null, null, 'nameZH', '<%=optionHeaderHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="define" key="define.option.header.name.cn" /></a>
			</th>
			<th style="width: 45%" class="header <%=optionHeaderHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listoptionHeader_form', null, null, 'nameEN', '<%=optionHeaderHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="define" key="define.option.header.name.en" /></a>
			</th>												
			<th style="width: 10%" class="header <%=optionHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listoptionHeader_form', null, null, 'status', '<%=optionHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="optionHeaderHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="optionHeaderVO" name="optionHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="optionHeaderVO" property="extended" value="2">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="optionHeaderVO" property="optionHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="optionHeaderVO" property="optionHeaderId"/>" />
						</logic:equal>
					</td>
					<td class="left"><a onclick="link('optionDetailAction.do?proc=list_object&id=<bean:write name="optionHeaderVO" property="encodedId"/>');"><bean:write name="optionHeaderVO" property="nameZH" /></a></td>
					<td class="left"><a onclick="link('optionDetailAction.do?proc=list_object&id=<bean:write name="optionHeaderVO" property="encodedId"/>');"><bean:write name="optionHeaderVO" property="nameEN" /></a></td>
					<td class="left"><bean:write name="optionHeaderVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="optionHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="4" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="optionHeaderHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="optionHeaderHolder" property="indexStart" /> - <bean:write name="optionHeaderHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listoptionHeader_form', null, '<bean:write name="optionHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listoptionHeader_form', null, '<bean:write name="optionHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listoptionHeader_form', null, '<bean:write name="optionHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listoptionHeader_form', null, '<bean:write name="optionHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="optionHeaderHolder" property="realPage" />/<bean:write name="optionHeaderHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>