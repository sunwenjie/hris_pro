<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder rightHolder = (PagedListHolder) request.getAttribute("rightHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 30%" class="header <%=rightHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listright_form', null, null, 'nameZH', '<%=rightHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">权限名称 （中文）</a>
			</th>
			<th style="width: 30%" class="header <%=rightHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listright_form', null, null, 'nameEN', '<%=rightHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">权限名称 （英文）</a>
			</th>
			<th style="width: 25%" class="header <%=rightHolder.getCurrentSortClass("rightType")%>">
				<a onclick="submitForm('listright_form', null, null, 'rightType', '<%=rightHolder.getNextSortOrder("rightType")%>', 'tableWrapper');">权限类型</a>
			</th>
			<th style="width: 15%" class="header <%=rightHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listright_form', null, null, 'status', '<%=rightHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="rightHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="rightVO" name="rightHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="rightVO" property="encodedId"/>" name="chkSelectRow[]" value="<bean:write name="rightVO" property="rightId"/>" />
					</td>
					<td><a onclick="link('rightAction.do?proc=to_objectModify&rightId=<bean:write name="rightVO" property="encodedId"/>');"><bean:write name="rightVO" property="nameZH" /></a></td>
					<td><a onclick="link('rightAction.do?proc=to_objectModify&rightId=<bean:write name="rightVO" property="encodedId"/>');"><bean:write name="rightVO" property="nameEN" /></a></td>
					<td class="left"><bean:write name="rightVO" property="decodeRightType" /></td>
					<td class="left"><bean:write name="rightVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="rightHolder">
		<tfoot>
			<tr class="total">
				<td colspan="5" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="rightHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="rightHolder" property="indexStart" /> - <bean:write name="rightHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listright_form', null, '<bean:write name="rightHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listright_form', null, '<bean:write name="rightHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listright_form', null, '<bean:write name="rightHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listright_form', null, '<bean:write name="rightHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="rightHolder" property="realPage" />/<bean:write name="rightHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>