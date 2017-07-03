<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder industryTypeHolder = (PagedListHolder) request.getAttribute("industryTypeHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>	
			<th style="width: 7%" class="header <%=industryTypeHolder.getCurrentSortClass("typeId")%>">
				<a onclick="submitForm('listIndustryType_form', null, null, 'typeId', '<%=industryTypeHolder.getNextSortOrder("typeId")%>', 'tableWrapper');">行业类型ID</a>
			</th>						
			<th style="width: 35%" class="header <%=industryTypeHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listIndustryType_form', null, null, 'nameZH', '<%=industryTypeHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">行业类型名称（中文）</a>
			</th>
			<th style="width: 35%" class="header <%=industryTypeHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listIndustryType_form', null, null, 'nameEN', '<%=industryTypeHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">行业类型名称（英文）</a>
			</th>
			<th style="width: 7%" class="header-nosort">修改人</th>
			<th style="width: 11%" class="header-nosort">修改时间</th>
			<th style="width: 5%" class="header <%=industryTypeHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listIndustryType_form', null, null, 'status', '<%=industryTypeHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="industryTypeHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="industryTypeVO" name="industryTypeHolder" property="source" indexId="number">
				<tr class="<%= number % 2 == 1 ? "odd" : "even" %>">
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="industryTypeVO" property="typeId"/>" name="chkSelectRow[]" value="<bean:write name="industryTypeVO" property="typeId"/>" />
					</td>
					<td class="left">
						<a onclick="link('industryTypeAction.do?proc=to_objectModify&typeId=<bean:write name="industryTypeVO" property="encodedId"/>');"><bean:write name="industryTypeVO" property="typeId"/></a>
					</td>
					<td class="left"><bean:write name="industryTypeVO" property="nameZH"/></td>
					<td class="left"><bean:write name="industryTypeVO" property="nameEN"/></td>
					<td class="left"><bean:write name="industryTypeVO" property="decodeModifyBy" /></td>
					<td class="left"><bean:write name="industryTypeVO" property="decodeModifyDate" /></td>
					<td class="left"><bean:write name="industryTypeVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="industryTypeHolder">
		<tfoot>
			<tr class="total">
				<td  colspan="7" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="industryTypeHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="industryTypeHolder" property="indexStart" /> - <bean:write name="industryTypeHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listIndustryType_form', null, '<bean:write name="industryTypeHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listIndustryType_form', null, '<bean:write name="industryTypeHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listIndustryType_form', null, '<bean:write name="industryTypeHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listIndustryType_form', null, '<bean:write name="industryTypeHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="industryTypeHolder" property="realPage" />/<bean:write name="industryTypeHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>