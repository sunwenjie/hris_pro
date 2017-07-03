<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder countryHolder = (PagedListHolder)request.getAttribute("countryHolder");
%>
				
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 30%" class="header <%=countryHolder.getCurrentSortClass("countryNameZH")%>">
				<a onclick="submitForm('listcountry_form', null, null, 'countryNameZH', '<%=countryHolder.getNextSortOrder("countryNameZH")%>', 'tableWrapper');">国家名（中文）</a>
			</th>
			<th style="width: 30%" class="header <%=countryHolder.getCurrentSortClass("countryNameEN")%>">
				<a onclick="submitForm('listcountry_form', null, null, 'countryNameEN', '<%=countryHolder.getNextSortOrder("countryNameEN")%>', 'tableWrapper');">国家名（英文）</a>
			</th>
			<th style="width: 11%" class="header <%=countryHolder.getCurrentSortClass("countryNumber")%>">
				<a onclick="submitForm('listcountry_form', null, null, 'countryNumber', '<%=countryHolder.getNextSortOrder("countryNumber")%>', 'tableWrapper');">国家编号</a>
			</th>
			<th style="width: 11%" class="header <%=countryHolder.getCurrentSortClass("countryCode")%>">
				<a onclick="submitForm('listcountry_form', null, null, 'countryCode', '<%=countryHolder.getNextSortOrder("countryCode")%>', 'tableWrapper');">国家编码（简写）</a>
			</th>
			<th style="width: 11%" class="header <%=countryHolder.getCurrentSortClass("countryISO3")%>">
				<a onclick="submitForm('listcountry_form', null, null, 'countryISO3', '<%=countryHolder.getNextSortOrder("countryISO3")%>', 'tableWrapper');">国家编码（ISO3）</a>
			</th>
			<th style="width: 7%" class="header <%=countryHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listcountry_form', null, null, 'status', '<%=countryHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>							
			</tr>
		</thead>			
	<logic:notEqual name="countryHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="countryVO" name="countryHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="countryVO" property="countryId"/>" name="chkSelectRow[]" value="<bean:write name="countryVO" property="countryId"/>" />
					</td>
					<td class="left">
						<a onclick="link('provinceAction.do?proc=list_object&countryId=<bean:write name="countryVO" property="encodedId"/>');"><bean:write name="countryVO" property="countryNameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('provinceAction.do?proc=list_object&countryId=<bean:write name="countryVO" property="encodedId"/>');"><bean:write name="countryVO" property="countryNameEN"/></a>
					</td>
					<td class="left"><bean:write name="countryVO" property="countryNumber"/></td>
					<td class="left"><bean:write name="countryVO" property="countryCode"/></td>
					<td class="left"><bean:write name="countryVO" property="countryISO3"/></td>
					<td class="left"><bean:write name="countryVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="countryHolder">
		<tfoot>
			<tr class="total">
				<td colspan="7" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="countryHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="countryHolder" property="indexStart" /> - <bean:write name="countryHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listcountry_form', null, '<bean:write name="countryHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listcountry_form', null, '<bean:write name="countryHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listcountry_form', null, '<bean:write name="countryHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listcountry_form', null, '<bean:write name="countryHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="countryHolder" property="realPage" />/<bean:write name="countryHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>