<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	PagedListHolder cityHolder = (PagedListHolder)request.getAttribute("cityHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 30%" class="header <%=cityHolder.getCurrentSortClass("cityNameZH")%>">
				<a onclick="submitForm('listcity_form', null, null, 'cityNameZH', '<%=cityHolder.getNextSortOrder("cityNameZH")%>', 'tableWrapper');">城市名（中文）</a>
			</th>	
			<th style="width: 30%" class="header <%=cityHolder.getCurrentSortClass("cityNameEN")%>">
				<a onclick="submitForm('listcity_form', null, null, 'cityNameEN', '<%=cityHolder.getNextSortOrder("cityNameEN")%>', 'tableWrapper');">城市名（英文）</a>
			</th>	
			<th style="width: 15%" class="header <%=cityHolder.getCurrentSortClass("cityCode")%>">
				<a onclick="submitForm('listcity_form', null, null, 'cityCode', '<%=cityHolder.getNextSortOrder("cityCode")%>', 'tableWrapper');">城市编号</a>
			</th>
			<th style="width: 15%" class="header <%=cityHolder.getCurrentSortClass("cityISO3")%>">
				<a onclick="submitForm('listcity_form', null, null, 'cityISO3', '<%=cityHolder.getNextSortOrder("cityISO3")%>', 'tableWrapper');">城市编码（ISO3）</a>
			</th>						
			<th style="width: 10%" class="header <%=cityHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listcity_form', null, null, 'status', '<%=cityHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>							
		</tr>
	</thead>							
	<logic:notEqual name="cityHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="cityVO" name="cityHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="cityVO" property="cityId"/>" name="chkSelectRow[]" value="<bean:write name="cityVO" property="cityId"/>" />
					</td>
					<td class="left">
						<a onclick="link('cityAction.do?proc=to_objectModify&cityId=<bean:write  name="cityVO" property="encodedId"/>&provinceId=<bean:write name="provinceForm" property="encodedId"/>')"><bean:write name="cityVO" property="cityNameZH"/></a>
					</td>
					<td class="left">
						<a onclick="link('cityAction.do?proc=to_objectModify&cityId=<bean:write  name="cityVO" property="encodedId"/>&provinceId=<bean:write name="provinceForm" property="encodedId"/>')"><bean:write name="cityVO" property="cityNameEN"/></a>
					</td>	
					<td class="left"><bean:write name="cityVO" property="cityCode"/></td>
					<td class="left"><bean:write name="cityVO" property="cityISO3"/></td>	
					<td class="left"><bean:write name="cityVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="cityHolder">
		<tfoot>
			<tr class="total">
				<td colspan="6" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="cityHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="cityHolder" property="indexStart" /> - <bean:write name="cityHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listcity_form', null, '<bean:write name="cityHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listcity_form', null, '<bean:write name="cityHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listcity_form', null, '<bean:write name="cityHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listcity_form', null, '<bean:write name="cityHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="cityHolder" property="realPage" />/<bean:write name="cityHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>