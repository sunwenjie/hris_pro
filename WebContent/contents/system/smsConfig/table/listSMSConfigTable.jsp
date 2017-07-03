<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder smsConfigHolder = (PagedListHolder) request.getAttribute("smsConfigHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 25%" class="header <%=smsConfigHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listsmsConfig_form', null, null, 'nameZH', '<%=smsConfigHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">配置名 （中文）</a>
			</th>
			<th style="width: 25%" class="header <%=smsConfigHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listsmsConfig_form', null, null, 'nameEN', '<%=smsConfigHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">配置名 （英文）</a>
			</th>
			<th style="width: 26%" class="header <%=smsConfigHolder.getCurrentSortClass("serverHost")%>">
				<a onclick="submitForm('listsmsConfig_form', null, null, 'serverHost', '<%=smsConfigHolder.getNextSortOrder("serverHost")%>', 'tableWrapper');">服务器地址</a>
			</th>
			<th style="width: 8%" class="header <%=smsConfigHolder.getCurrentSortClass("serverPort")%>">
				<a onclick="submitForm('listsmsConfig_form', null, null, 'serverPort', '<%=smsConfigHolder.getNextSortOrder("serverPort")%>', 'tableWrapper');">服务器端口</a>
			</th>
			<th style="width: 8%" class="header <%=smsConfigHolder.getCurrentSortClass("price")%>">
				<a onclick="submitForm('listsmsConfig_form', null, null, 'price', '<%=smsConfigHolder.getNextSortOrder("price")%>', 'tableWrapper');">价格（元）</a>
			</th>
			<th style="width: 8%" class="header <%=smsConfigHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listsmsConfig_form', null, null, 'status', '<%=smsConfigHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="smsConfigHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="smsConfigVO" name="smsConfigHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="smsConfigVO" property="configId"/>" name="chkSelectRow[]" value="<bean:write name="smsConfigVO" property="configId"/>" />
					</td>
					<td><a onclick="link('smsConfigAction.do?proc=to_objectModify&configId=<bean:write name="smsConfigVO" property="encodedId"/>');"><bean:write name="smsConfigVO" property="nameZH" /></a></td>
					<td><a onclick="link('smsConfigAction.do?proc=to_objectModify&configId=<bean:write name="smsConfigVO" property="encodedId"/>');"><bean:write name="smsConfigVO" property="nameEN" /></a></td>
					<td class="left"><bean:write name="smsConfigVO" property="serverHost" /></td>
					<td class="left"><bean:write name="smsConfigVO" property="serverPort" /></td>
					<td class="rigth"><bean:write name="smsConfigVO" property="price" /></td>
					<td class="left"><bean:write name="smsConfigVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="smsConfigHolder">
		<tfoot>
			<tr class="total">
				<td colspan="7" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="smsConfigHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="smsConfigHolder" property="indexStart" /> - <bean:write name="smsConfigHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listsmsConfig_form', null, '<bean:write name="smsConfigHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listsmsConfig_form', null, '<bean:write name="smsConfigHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listsmsConfig_form', null, '<bean:write name="smsConfigHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listsmsConfig_form', null, '<bean:write name="smsConfigHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="smsConfigHolder" property="realPage" />/<bean:write name="smsConfigHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>