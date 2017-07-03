<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder moduleHolder = (PagedListHolder) request.getAttribute("moduleHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 14%" class="header <%=moduleHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listmodule_form', null, null, 'nameZH', '<%=moduleHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">模块名称 （中文）</a>
			</th>
			<th style="width: 14%" class="header <%=moduleHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listmodule_form', null, null, 'nameEN', '<%=moduleHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">模块名称 （英文）</a>
			</th>
			<th style="width: 21%" class="header <%=moduleHolder.getCurrentSortClass("moduleName")%>">
				<a onclick="submitForm('listmodule_form', null, null, 'moduleName', '<%=moduleHolder.getNextSortOrder("moduleName")%>', 'tableWrapper');">模块ID</a>
			</th>
			<th style="width: 18%" class="header <%=moduleHolder.getCurrentSortClass("accessAction")%>">
				<a onclick="submitForm('listmodule_form', null, null, 'accessAction', '<%=moduleHolder.getNextSortOrder("accessAction")%>', 'tableWrapper');">访问链接</a>
			</th>
			<th style="width: 21%" class="header <%=moduleHolder.getCurrentSortClass("property")%>">
				<a onclick="submitForm('listmodule_form', null, null, 'property', '<%=moduleHolder.getNextSortOrder("property")%>', 'tableWrapper');">资源标签</a>
			</th>
			<th style="width: 6%" class="header <%=moduleHolder.getCurrentSortClass("moduleType")%>">
				<a onclick="submitForm('listmodule_form', null, null, 'moduleType', '<%=moduleHolder.getNextSortOrder("moduleType")%>', 'tableWrapper');">模块类型</a>
			</th>
			<th style="width: 6%" class="header <%=moduleHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listmodule_form', null, null, 'status', '<%=moduleHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="moduleHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="moduleVO" name="moduleHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="moduleVO" property="moduleId"/>" name="chkSelectRow[]" value="<bean:write name="moduleVO" property="moduleId"/>" />
					</td>
					<td><a onclick="link('moduleAction.do?proc=to_objectModify&moduleId=<bean:write name="moduleVO" property="encodedId"/>');"><bean:write name="moduleVO" property="nameZH" /></a></td>
					<td><a onclick="link('moduleAction.do?proc=to_objectModify&moduleId=<bean:write name="moduleVO" property="encodedId"/>');"><bean:write name="moduleVO" property="nameEN" /></a></td>
					<td class="left"><bean:write name="moduleVO" property="moduleName" /></td>
					<td class="left"><bean:write name="moduleVO" property="accessAction" /></td>
					<td class="left"><bean:write name="moduleVO" property="property" /></td>
					<td class="left"><bean:write name="moduleVO" property="decodeModuleType" /></td>
					<td class="left"><bean:write name="moduleVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="moduleHolder">
		<tfoot>
			<tr class="total">
				<td colspan="8" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="moduleHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="moduleHolder" property="indexStart" /> - <bean:write name="moduleHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listmodule_form', null, '<bean:write name="moduleHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmodule_form', null, '<bean:write name="moduleHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmodule_form', null, '<bean:write name="moduleHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listmodule_form', null, '<bean:write name="moduleHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="moduleHolder" property="realPage" />/<bean:write name="moduleHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>