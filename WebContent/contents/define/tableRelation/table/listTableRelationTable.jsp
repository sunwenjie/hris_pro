<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder tablePagedListHolder = (PagedListHolder) request.getAttribute("tablePagedListHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<logic:present name="accountId">
				<logic:equal name="accountId" value="1">
					<th class="checkbox-col" style="width:1%;"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
				</logic:equal>
			</logic:present>
			<th style="width: 25%" class="header <%=tablePagedListHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listtable_form', null, null, 'nameZH', '<%=tablePagedListHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">模块字典名称</a>
			</th>
		
			
			<th style="width: 20%" class="header <%=tablePagedListHolder.getCurrentSortClass("accessAction")%>">
				<a onclick="submitForm('listtable_form', null, null, 'accessAction', '<%=tablePagedListHolder.getNextSortOrder("accessAction")%>', 'tableWrapper');">链接</a>
			</th>
			<th style="width: 25%" class="header <%=tablePagedListHolder.getCurrentSortClass("accessName")%>">
				<a onclick="submitForm('listtable_form', null, null, 'accessName', '<%=tablePagedListHolder.getNextSortOrder("accessName")%>', 'tableWrapper');">模块字典名称（数据库）</a>
			</th>
	
		</tr>
	</thead>
	<logic:notEqual name="tablePagedListHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="tableRelationVO" name="tablePagedListHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<logic:present name="accountId">
						<logic:equal name="accountId" value="1">
							<td>
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="tableRelationVO" property="tableRelationId"/>" name="chkSelectRow[]" value="<bean:write name="tableRelationVO" property="tableRelationId"/>" />
							</td>
						</logic:equal>
					</logic:present>
					<td class="left"><a onclick="link('tableRelationAction.do?proc=to_objectModify&masterTableId=<bean:write name="tableRelationVO" property="masterTableId"/>');"><bean:write name="tableRelationVO" property="decodeMasterTableName" /></a></td>
					<td class="left"><bean:write name="tableRelationVO" property="accessAction" /></td>
					<td class="left"><bean:write name="tableRelationVO" property="accessName" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="tablePagedListHolder">
		<tfoot>
			<tr class="total">
				<td colspan="10" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="tablePagedListHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="tablePagedListHolder" property="indexStart" /> - <bean:write name="tablePagedListHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listtable_form', null, '<bean:write name="tablePagedListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listtable_form', null, '<bean:write name="tablePagedListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listtable_form', null, '<bean:write name="tablePagedListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listtable_form', null, '<bean:write name="tablePagedListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="tablePagedListHolder" property="realPage" />/<bean:write name="tablePagedListHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>