<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
	PagedListHolder workflowModuleHolder = (PagedListHolder) request.getAttribute("workflowModuleHolder");
%>
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 30%" class="header <%=workflowModuleHolder.getCurrentSortClass("nameZH")%>">
				<a href="#" onclick="submitForm('listworkflowModule_form', null, null, 'nameZH', '<%=workflowModuleHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">工作流名称（中文）</a>
			</th>
			<th style="width: 30%" class="header <%=workflowModuleHolder.getCurrentSortClass("nameEN")%>">
				<a href="#" onclick="submitForm('listworkflowModule_form', null, null, 'nameEN', '<%=workflowModuleHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">工作流名称（英文）</a>
			</th>
			<th style="width: 20%" class="header <%=workflowModuleHolder.getCurrentSortClass("moduleTitle")%>">
				<a href="#" onclick="submitForm('listworkflowModule_form', null, null, 'moduleTitle', '<%=workflowModuleHolder.getNextSortOrder("moduleTitle")%>', 'tableWrapper');">系统模块（标识）</a>
			</th>
			<th style="width: 10%" class="header <%=workflowModuleHolder.getCurrentSortClass("scopeType")%>">
				<a href="#" onclick="submitForm('listworkflowModule_form', null, null, 'scopeType', '<%=workflowModuleHolder.getNextSortOrder("scopeType")%>', 'tableWrapper');">作用范围</a>
			</th>
			<th style="width: 10%" class="header <%=workflowModuleHolder.getCurrentSortClass("status")%>">
				<a href="#" onclick="submitForm('listworkflowModule_form', null, null, 'status', '<%=workflowModuleHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="workflowModuleHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="workflowModuleVO" name="workflowModuleHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="workflowModuleVO" property="workflowModuleId"/>" name="chkSelectRow[]" value="<bean:write name="workflowModuleVO" property="workflowModuleId"/>" /></td>
					<td><a onclick="link('workflowModuleAction.do?proc=to_objectModify&workflowModuleId=<bean:write name="workflowModuleVO" property="encodedId"/>');"><bean:write name="workflowModuleVO" property="nameZH" /></a></td>
					<td><a onclick="link('workflowModuleAction.do?proc=to_objectModify&workflowModuleId=<bean:write name="workflowModuleVO" property="encodedId"/>');"><bean:write name="workflowModuleVO" property="nameEN" /></a></td>
					<td><a onclick="link('moduleAction.do?proc=to_objectModify&moduleId=<bean:write name="workflowModuleVO" property="encodedModuleId"/>');"><bean:write name="workflowModuleVO" property="moduleTitle" /></a></td>
					<td><a ><bean:write name="workflowModuleVO" property="decodeScopeType" /></a></td>
					<td><bean:write name="workflowModuleVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="workflowModuleHolder">
		<tfoot>
			<tr class="total">
				<td colspan="6" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="workflowModuleHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="workflowModuleHolder" property="indexStart" /> - <bean:write name="workflowModuleHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listworkflowModule_form', null, '<bean:write name="workflowModuleHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('listworkflowModule_form', null, '<bean:write name="workflowModuleHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('listworkflowModule_form', null, '<bean:write name="workflowModuleHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a href="#" onclick="submitForm('listworkflowModule_form', null, '<bean:write name="workflowModuleHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="workflowModuleHolder" property="realPage" />/<bean:write name="workflowModuleHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>