<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder ruleHolder = (PagedListHolder) request.getAttribute("ruleHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 30%" class="header <%=ruleHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listrule_form', null, null, 'nameZH', '<%=ruleHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">规则名称 （中文）</a>
			</th>
			<th style="width: 30%" class="header <%=ruleHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listrule_form', null, null, 'nameEN', '<%=ruleHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">规则名称 （英文）</a>
			</th>
			<th style="width: 25%" class="header <%=ruleHolder.getCurrentSortClass("ruleType")%>">
				<a onclick="submitForm('listrule_form', null, null, 'ruleType', '<%=ruleHolder.getNextSortOrder("ruleType")%>', 'tableWrapper');">规则类型</a>
			</th>
			<th style="width: 15%" class="header <%=ruleHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listrule_form', null, null, 'status', '<%=ruleHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="ruleHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="ruleVO" name="ruleHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="ruleVO" property="ruleId"/>" name="chkSelectRow[]" value="<bean:write name="ruleVO" property="ruleId"/>" />
					</td>
					<td><a onclick="link('ruleAction.do?proc=to_objectModify&ruleId=<bean:write name="ruleVO" property="encodedId"/>');"><bean:write name="ruleVO" property="nameZH" /></a></td>
					<td><a onclick="link('ruleAction.do?proc=to_objectModify&ruleId=<bean:write name="ruleVO" property="encodedId"/>');"><bean:write name="ruleVO" property="nameEN" /></a></td>
					<td class="left"><bean:write name="ruleVO" property="decodeRuleType" /></td>
					<td class="left"><bean:write name="ruleVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="ruleHolder">
		<tfoot>
			<tr class="total">
				<td colspan="5" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="ruleHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="ruleHolder" property="indexStart" /> - <bean:write name="ruleHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listrule_form', null, '<bean:write name="ruleHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listrule_form', null, '<bean:write name="ruleHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listrule_form', null, '<bean:write name="ruleHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listrule_form', null, '<bean:write name="ruleHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="ruleHolder" property="realPage" />/<bean:write name="ruleHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>