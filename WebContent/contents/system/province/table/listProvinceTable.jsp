<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	PagedListHolder provinceHolder = (PagedListHolder)request.getAttribute("provinceHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 45%" class="header <%=provinceHolder.getCurrentSortClass("provinceNameZH")%>">
				<a onclick="submitForm('listprovince_form', null, null, 'provinceNameZH', '<%=provinceHolder.getNextSortOrder("provinceNameZH")%>', 'tableWrapper');">省份名（中文）</a>
			</th>
			<th style="width: 45%" class="header <%=provinceHolder.getCurrentSortClass("provinceNameEN")%>">
				<a onclick="submitForm('listprovince_form', null, null, 'provinceNameEN', '<%=provinceHolder.getNextSortOrder("provinceNameEN")%>', 'tableWrapper');">省份名（英文）</a>
			</th>								
			<th style="width: 10%" class="header <%=provinceHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listprovince_form', null, null, 'status', '<%=provinceHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>							
		</tr>
	</thead>							
	<logic:notEqual name="provinceHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="provinceVO" name="provinceHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="provinceVO" property="provinceId"/>" name="chkSelectRow[]" value="<bean:write name="provinceVO" property="provinceId"/>" />
					</td>
					<td class="left">
						<a onclick="link('cityAction.do?proc=list_object&provinceId=<bean:write name="provinceVO" property="encodedId"/>')"><bean:write name="provinceVO" property="provinceNameZH"/></a>
					</td>	
					<td class="left">
						<a onclick="link('cityAction.do?proc=list_object&provinceId=<bean:write name="provinceVO" property="encodedId"/>')"><bean:write name="provinceVO" property="provinceNameEN"/></a>
					</td>	
					<td class="left"><bean:write name="provinceVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="provinceHolder">
		<tfoot>
			<tr class="total">
				<td colspan="4" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="provinceHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="provinceHolder" property="indexStart" /> - <bean:write name="provinceHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listprovince_form', null, '<bean:write name="provinceHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listprovince_form', null, '<bean:write name="provinceHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a onclick="submitForm('listprovince_form', null, '<bean:write name="provinceHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listprovince_form', null, '<bean:write name="provinceHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="provinceHolder" property="realPage" />/<bean:write name="provinceHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>