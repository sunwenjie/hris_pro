<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.web.actions.management.ItemAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	PagedListHolder itemHolder = (PagedListHolder) request.getAttribute("itemHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 8%" class="header <%=itemHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('listitem_form', null, null, 'itemId', '<%=itemHolder.getNextSortOrder("itemId")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item.id" /></a>
			</th>
			<th style="width: 23%" class="header <%=itemHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listitem_form', null, null, 'nameZH', '<%=itemHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item.name.cn" /></a>
			</th>
			<th style="width: 23%" class="header <%=itemHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listitem_form', null, null, 'nameEN', '<%=itemHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item.name.en" /></a>
			</th>
			<th style="width: 12%" class="header <%=itemHolder.getCurrentSortClass("itemNo")%>">
				<a onclick="submitForm('listitem_form', null, null, 'itemNo', '<%=itemHolder.getNextSortOrder("itemNo")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item.no" /></a>
			</th>
			<th style="width: 12%" class="header <%=itemHolder.getCurrentSortClass("itemType")%>">
				<a onclick="submitForm('listitem_form', null, null, 'itemType', '<%=itemHolder.getNextSortOrder("itemType")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item.type" /></a>
			</th>
			<th style="width: 12%" class="header <%=itemHolder.getCurrentSortClass("calculateType")%>">
				<a onclick="submitForm('listitem_form', null, null, 'calculateType', '<%=itemHolder.getNextSortOrder("calculateType")%>', 'tableWrapper');"><bean:message bundle="management" key="management.item.calculateType" /></a>
			</th>
			<th style="width: 10%" class="header <%=itemHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listitem_form', null, null, 'status', '<%=itemHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="itemHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="itemVO" name="itemHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<!-- Super -->
						<logic:equal name="accountId" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="itemVO" property="itemId"/>" name="chkSelectRow[]" value="<bean:write name="itemVO" property="itemId"/>" />
						</logic:equal>
						
						<!-- Account -->
						<logic:notEqual name="accountId" value="1">
							<logic:notEqual name="itemVO" property="accountId" value="1">							
								  <logic:equal name="itemVO" property="extended" value="2">						
							           <input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="itemVO" property="itemId"/>" name="chkSelectRow[]" value="<bean:write name="itemVO" property="itemId"/>" />
							      </logic:equal>
							</logic:notEqual>						           
						</logic:notEqual>
					</td>
					<td class="left">
					<kan:auth right="modify" action="<%=ItemAction.accessAction%>">
						<a onclick="link('itemAction.do?proc=to_objectModify&id=<bean:write name="itemVO" property="encodedId"/>');">
					</kan:auth>	
						<bean:write name="itemVO" property="itemId"/></a>
					</td>
					<td class="left">
					<kan:auth right="modify" action="<%=ItemAction.accessAction%>">
						<a onclick="link('itemAction.do?proc=to_objectModify&id=<bean:write name="itemVO" property="encodedId"/>');">
					</kan:auth>	
						<bean:write name="itemVO" property="nameZH"/>
					<kan:auth right="modify" action="<%=ItemAction.accessAction%>">	
						</a>
					</kan:auth>	
					</td>
					<td class="left">
					<kan:auth right="modify" action="<%=ItemAction.accessAction%>">
						<a onclick="link('itemAction.do?proc=to_objectModify&id=<bean:write name="itemVO" property="encodedId"/>');">
					</kan:auth>	
						<bean:write name="itemVO" property="nameEN"/>
					<kan:auth right="modify" action="<%=ItemAction.accessAction%>">	
						</a>
					</kan:auth>	
					</td>
					<td class="left"><bean:write name="itemVO" property="itemNo"/></td>
					<td class="left"><bean:write name="itemVO" property="decodeItemType"/></td>
					<td class="left"><bean:write name="itemVO" property="decodeCalculateType"/></td>
					<td class="left"><bean:write name="itemVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="itemHolder">
		<tfoot>
			<tr class="total">
			  	<td  colspan="8" class="left"> 
				  	<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="itemHolder" property="holderSize" /></label>
				  	<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="itemHolder" property="indexStart" /> - <bean:write name="itemHolder" property="indexEnd" /></label>
				  	<label>&nbsp;&nbsp;<a onclick="submitForm('listitem_form', null, '<bean:write name="itemHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listitem_form', null, '<bean:write name="itemHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listitem_form', null, '<bean:write name="itemHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
				  	<label>&nbsp;<a onclick="submitForm('listitem_form', null, '<bean:write name="itemHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
				  	<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="itemHolder" property="realPage" />/<bean:write name="itemHolder" property="pageCount" /></label>&nbsp;
				 </td>					
		   </tr>
		</tfoot>
	</logic:present>
</table>