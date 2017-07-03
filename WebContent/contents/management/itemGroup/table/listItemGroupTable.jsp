<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ page import="com.kan.base.web.actions.management.ItemGroupAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	PagedListHolder itemGroupHolder = (PagedListHolder) request.getAttribute("itemGroupHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=itemGroupHolder.getCurrentSortClass("itemGroupId")%>">
				<a onclick="submitForm('listitemGroup_form', null, null, 'itemGroupId', '<%=itemGroupHolder.getNextSortOrder("itemGroupId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item.group.id" /></a>
			</th>
			<th style="width: 20%" class="header <%=itemGroupHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listitemGroup_form', null, null, 'nameZH', '<%=itemGroupHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item.group.name.cn" /></a>
			</th>
			<th style="width: 20%" class="header <%=itemGroupHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listitemGroup_form', null, null, 'nameEN', '<%=itemGroupHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item.group.name.en" /></a>
			</th>
			<th style="width: 10%" class="header <%=itemGroupHolder.getCurrentSortClass("bindItemId")%>">
				<a onclick="submitForm('listitemGroup_form', null, null, 'bindItemId', '<%=itemGroupHolder.getNextSortOrder("bindItemId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item.group.bind.item" /></a>
			</th>
			<th style="width: 10%" class="header <%=itemGroupHolder.getCurrentSortClass("listMerge")%>">
				<a onclick="submitForm('listitemGroup_form', null, null, 'listMerge', '<%=itemGroupHolder.getNextSortOrder("listMerge")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item.group.listMerge" /></a>
			</th>
			<th style="width: 10%" class="header <%=itemGroupHolder.getCurrentSortClass("reportMerge")%>">
				<a onclick="submitForm('listitemGroup_form', null, null, 'reportMerge', '<%=itemGroupHolder.getNextSortOrder("reportMerge")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item.group.reportMerge" /></a>
			</th>
			<th style="width: 10%" class="header <%=itemGroupHolder.getCurrentSortClass("invoiceMerge")%>">
				<a onclick="submitForm('listitemGroup_form', null, null, 'invoiceMerge', '<%=itemGroupHolder.getNextSortOrder("invoiceMerge")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item.group.invoiceMerge" /></a>
			</th>
			<th style="width: 10%" class="header <%=itemGroupHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listitemGroup_form', null, null, 'status', '<%=itemGroupHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="itemGroupHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="itemGroupVO" name="itemGroupHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="itemGroupVO" property="itemGroupId"/>" name="chkSelectRow[]" value="<bean:write name="itemGroupVO" property="itemGroupId"/>" />
					</td>
					<td class="left">
						<kan:auth right="modify" action="<%=ItemGroupAction.accessAction%>">
							<a onclick="link('itemGroupAction.do?proc=to_objectModify&encodedId=<bean:write name="itemGroupVO" property="encodedId"/>');">
						</kan:auth>
						<bean:write name="itemGroupVO" property="itemGroupId"/>
						<kan:auth right="modify" action="<%=ItemGroupAction.accessAction%>">
							</a>
						</kan:auth>
					</td>
					<td class="left">
						<kan:auth right="modify" action="<%=ItemGroupAction.accessAction%>">
							<a onclick="link('itemGroupAction.do?proc=to_objectModify&encodedId=<bean:write name="itemGroupVO" property="encodedId"/>');">
						</kan:auth>	
								<bean:write name="itemGroupVO" property="nameZH"/>
						<kan:auth right="modify" action="<%=ItemGroupAction.accessAction%>">
							</a>
						</kan:auth>
					</td>
					<td class="left">
						<kan:auth right="modify" action="<%=ItemGroupAction.accessAction%>">
							<a onclick="link('itemGroupAction.do?proc=to_objectModify&encodedId=<bean:write name="itemGroupVO" property="encodedId"/>');">
						</kan:auth>	
								<bean:write name="itemGroupVO" property="nameEN"/>
						<kan:auth right="modify" action="<%=ItemGroupAction.accessAction%>">	
							</a>
						</kan:auth>	
					</td>
					<td class="left"><bean:write name="itemGroupVO" property="decodeBindItemId"/></td>
					<td class="left"><bean:write name="itemGroupVO" property="decodeListMerge"/></td>
					<td class="left"><bean:write name="itemGroupVO" property="decodeReportMerge"/></td>
					<td class="left"><bean:write name="itemGroupVO" property="decodeInvoiceMerge"/></td>
					<td class="left"><bean:write name="itemGroupVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="itemGroupHolder">
		<tfoot>
			<tr class="total">
				<td colspan="9" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="itemGroupHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="itemGroupHolder" property="indexStart" /> - <bean:write name="itemGroupHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listitemGroup_form', null, '<bean:write name="itemGroupHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listitemGroup_form', null, '<bean:write name="itemGroupHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listitemGroup_form', null, '<bean:write name="itemGroupHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listitemGroup_form', null, '<bean:write name="itemGroupHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="itemGroupHolder" property="realPage" />/<bean:write name="itemGroupHolder" property="pageCount" /></label>&nbsp;
				 </td>					
			</tr>
		</tfoot>
	</logic:present>
</table>