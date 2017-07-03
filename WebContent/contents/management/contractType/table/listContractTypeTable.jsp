<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder contractTypeHolder = (PagedListHolder) request.getAttribute("contractTypeHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>							
			<th style="width: 10%" class="header <%=contractTypeHolder.getCurrentSortClass("typeId")%>">
				<a onclick="submitForm('listcontractType_form', null, null, 'typeId', '<%=contractTypeHolder.getNextSortOrder("typeId")%>', 'tableWrapper');">劳动合同类型ID</a>
			</th>
			<th style="width: 35%" class="header <%=contractTypeHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listcontractType_form', null, null, 'nameZH', '<%=contractTypeHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">劳动合同类型名称（中文）</a>
			</th>
			<th style="width: 30%" class="header <%=contractTypeHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listcontractType_form', null, null, 'nameEN', '<%=contractTypeHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">劳动合同类型名称（英文）</a>
			</th>
			<th style="width: 10%" class="header-nosort">修改人</th>
			<th style="width: 15%" class="header-nosort">修改时间</th>
			<th style="width: 10%" class="header <%=contractTypeHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listcontractType_form', null, null, 'status', '<%=contractTypeHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="contractTypeHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="contractTypeVO" name="contractTypeHolder" property="source" indexId="number">
				<tr class="<%= number % 2 == 1 ? "odd" : "even" %>">
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="contractTypeVO" property="typeId"/>" name="chkSelectRow[]" value="<bean:write name="contractTypeVO" property="typeId"/>" />
					</td>
					<td class="left">
						<a onclick="link('contractTypeAction.do?proc=to_objectModify&typeId=<bean:write name="contractTypeVO" property="encodedId"/>');"><bean:write name="contractTypeVO" property="typeId"/></a>
					</td>
					<td class="left"><bean:write name="contractTypeVO" property="nameZH"/></td>
					<td class="left"><bean:write name="contractTypeVO" property="nameEN"/></td>
					<td class="left"><bean:write name="contractTypeVO" property="decodeModifyBy"/></td>
					<td class="left"><bean:write name="contractTypeVO" property="decodeModifyDate"/></td>
					<td class="left"><bean:write name="contractTypeVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="contractTypeHolder">
		<tfoot>
			<tr class="total">
				<td  colspan="7" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="contractTypeHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="contractTypeHolder" property="indexStart" /> - <bean:write name="contractTypeHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listcontractType_form', null, '<bean:write name="contractTypeHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listcontractType_form', null, '<bean:write name="contractTypeHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listcontractType_form', null, '<bean:write name="contractTypeHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listcontractType_form', null, '<bean:write name="contractTypeHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="contractTypeHolder" property="realPage" />/<bean:write name="contractTypeHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>