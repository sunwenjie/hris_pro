<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ page import="com.kan.base.web.actions.management.ItemMappingAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	PagedListHolder itemMappingHolder = (PagedListHolder) request.getAttribute("itemMappingHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>	
			<th style="width: 10%" class="header <%=itemMappingHolder.getCurrentSortClass("mappingId")%>">
				<a onclick="submitForm('listitemMapping_form', null, null, 'mappingId', '<%=itemMappingHolder.getNextSortOrder("mappingId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item.mapping.id" /></a>
			</th>						
			<th style="width: 20%" class="header <%=itemMappingHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listitemMapping_form', null, null, 'itemId', '<%=itemMappingHolder.getNextSortOrder("itemId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item" /></a>
			</th>
			<th style="width: 30%" class="header <%=itemMappingHolder.getCurrentSortClass("entityId")%>">
				<a onclick="submitForm('listitemMapping_form', null, null, 'entityId', '<%=itemMappingHolder.getNextSortOrder("entityId")%>', 'tableWrapper');"><bean:message bundle="security" key="security.entity" /></a>
			</th>
			<th style="width: 30%" class="header <%=itemMappingHolder.getCurrentSortClass("businessTypeId")%>">
				<a onclick="submitForm('listitemMapping_form', null, null, 'businessTypeId', '<%=itemMappingHolder.getNextSortOrder("businessTypeId")%>', 'tableWrapper');"><bean:message bundle="security" key="security.business.type" /></a>
			</th>
			<th style="width: 10%" class="header <%=itemMappingHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listitemMapping_form', null, null, 'status', '<%=itemMappingHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="itemMappingHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="itemMappingVO" name="itemMappingHolder" property="source" indexId="number">
				<tr class="<%= number % 2 == 1 ? "odd" : "even" %>">
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="itemMappingVO" property="mappingId"/>" name="chkSelectRow[]" value="<bean:write name="itemMappingVO" property="mappingId"/>" />
					</td>
					<td class="left">
					<kan:auth right="modify" action="<%=ItemMappingAction.accessAction%>">
						<a onclick="link('itemMappingAction.do?proc=to_objectModify&mappingId=<bean:write name="itemMappingVO" property="encodedId"/>');">\
					</kan:auth>	
						<bean:write name="itemMappingVO" property="mappingId"/>
					<kan:auth right="modify" action="<%=ItemMappingAction.accessAction%>">	
						</a>
					</kan:auth>	
					</td>
					<td class="left">
					<kan:auth right="modify" action="<%=ItemMappingAction.accessAction%>">
						<a onclick="link('itemMappingAction.do?proc=to_objectModify&mappingId=<bean:write name="itemMappingVO" property="encodedId"/>');">
					</kan:auth>	
						<bean:write name="itemMappingVO" property="decodeItem"/>
					<kan:auth right="modify" action="<%=ItemMappingAction.accessAction%>">	
						</a>
					</kan:auth>	
					</td>
					<td class="left">
					<kan:auth right="modify" action="<%=ItemMappingAction.accessAction%>">
						<a onclick="link('itemMappingAction.do?proc=to_objectModify&mappingId=<bean:write name="itemMappingVO" property="encodedId"/>');">
					</kan:auth>
						<bean:write name="itemMappingVO" property="decodeEntity"/>
					<kan:auth right="modify" action="<%=ItemMappingAction.accessAction%>">	
						</a>
					</kan:auth>	
					</td>
					<td class="left">
					<kan:auth right="modify" action="<%=ItemMappingAction.accessAction%>">
						<a onclick="link('itemMappingAction.do?proc=to_objectModify&mappingId=<bean:write name="itemMappingVO" property="encodedId"/>');">
					</kan:auth>	
						<bean:write name="itemMappingVO" property="decodeBusinessType"/>
					<kan:auth right="modify" action="<%=ItemMappingAction.accessAction%>">	
						</a>
					</kan:auth>	
					</td>
					<td class="left"><bean:write name="itemMappingVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="itemMappingHolder">
		<tfoot>
			<tr class="total">
				<td  colspan="6" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="itemMappingHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="itemMappingHolder" property="indexStart" /> - <bean:write name="itemMappingHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listitemMapping_form', null, '<bean:write name="itemMappingHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listitemMapping_form', null, '<bean:write name="itemMappingHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listitemMapping_form', null, '<bean:write name="itemMappingHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listitemMapping_form', null, '<bean:write name="itemMappingHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="itemMappingHolder" property="realPage" />/<bean:write name="itemMappingHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>