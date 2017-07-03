<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder businessContractTemplateHolder = (PagedListHolder) request.getAttribute("businessContractTemplateHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>	
			<th style="width: 5%" class="header <%=businessContractTemplateHolder.getCurrentSortClass("templateId")%>">
				<a onclick="submitForm('listBusinessContractTemplate_form', null, null, 'templateId', '<%=businessContractTemplateHolder.getNextSortOrder("templateId")%>', 'tableWrapper');">模板ID</a>
			</th>						
			<th style="width: 12%" class="header <%=businessContractTemplateHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listBusinessContractTemplate_form', null, null, 'nameZH', '<%=businessContractTemplateHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">模板名称（中文）</a>
			</th>
			<th style="width: 16%" class="header <%=businessContractTemplateHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listBusinessContractTemplate_form', null, null, 'nameEN', '<%=businessContractTemplateHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">模板名称（英文）</a>
			</th>
			<th style="width: 22%" class="header-nosort <%=businessContractTemplateHolder.getCurrentSortClass("entityNameList")%>">
				<a>法务实体</a>
			</th>
			<th style="width: 22%" class="header-nosort <%=businessContractTemplateHolder.getCurrentSortClass("businessTypeNameList")%>">
				<a>业务类型</a>
			</th>
			<th style="width: 7%" class="header-nosort">修改人</th>
			<th style="width: 11%" class="header-nosort">修改时间</th>
			<th style="width: 5%" class="header <%=businessContractTemplateHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listBusinessContractTemplate_form', null, null, 'status', '<%=businessContractTemplateHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="businessContractTemplateHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="businessContractTemplateVO" name="businessContractTemplateHolder" property="source" indexId="number">
				<tr class="<%= number % 2 == 1 ? "odd" : "even" %>">
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="businessContractTemplateVO" property="templateId"/>" name="chkSelectRow[]" value="<bean:write name="businessContractTemplateVO" property="templateId"/>" />
					</td>
					<td class="left">
						<a onclick="link('businessContractTemplateAction.do?proc=to_objectModify&templateId=<bean:write name="businessContractTemplateVO" property="encodedId"/>');"><bean:write name="businessContractTemplateVO" property="templateId"/></a>
					</td>
					<td class="left"><bean:write name="businessContractTemplateVO" property="nameZH"/></td>
					<td class="left"><bean:write name="businessContractTemplateVO" property="nameEN"/></td>
					<td class="left"><bean:write name="businessContractTemplateVO" property="decodeEntityIds"/></td>
					<td class="left"><bean:write name="businessContractTemplateVO" property="decodeBusinessTypeIds"/></td>
					<td class="left"><bean:write name="businessContractTemplateVO" property="decodeModifyBy" /></td>
					<td class="left"><bean:write name="businessContractTemplateVO" property="decodeModifyDate" /></td>
					<td class="left"><bean:write name="businessContractTemplateVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="businessContractTemplateHolder">
		<tfoot>
			<tr class="total">
				<td  colspan="9" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="businessContractTemplateHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="businessContractTemplateHolder" property="indexStart" /> - <bean:write name="businessContractTemplateHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listBusinessContractTemplate_form', null, '<bean:write name="businessContractTemplateHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listBusinessContractTemplate_form', null, '<bean:write name="businessContractTemplateHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listBusinessContractTemplate_form', null, '<bean:write name="businessContractTemplateHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listBusinessContractTemplate_form', null, '<bean:write name="businessContractTemplateHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="businessContractTemplateHolder" property="realPage" />/<bean:write name="businessContractTemplateHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>