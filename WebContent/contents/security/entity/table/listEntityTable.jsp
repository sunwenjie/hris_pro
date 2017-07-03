<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder entityHolder = (PagedListHolder) request.getAttribute("entityHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 5%" class="header <%=entityHolder.getCurrentSortClass("entityId")%>">
				<a onclick="submitForm('listentity_form', null, null, 'entityId', '<%=entityHolder.getNextSortOrder("entityId")%>', 'tableWrapper');">
					<bean:message bundle="security" key="security.entity.id" />
				</a>
			</th>
			<th style="width: 10%" class="header <%=entityHolder.getCurrentSortClass("title")%>">
				<a onclick="submitForm('listentity_form', null, null, 'title', '<%=entityHolder.getNextSortOrder("title")%>', 'tableWrapper');">
					<bean:message bundle="security" key="security.entity.in.short" />
				</a>
			</th>
			<th style="width: 15%" class="header <%=entityHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listentity_form', null, null, 'nameZH', '<%=entityHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">
					<bean:message bundle="security" key="security.entity.name.cn" />
				</a>
			</th>
			<th style="width: 15%" class="header <%=entityHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listentity_form', null, null, 'nameEN', '<%=entityHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">
					<bean:message bundle="security" key="security.entity.name.en" />
				</a>
			</th>
			<th style="width: 15%" class="header <%=entityHolder.getCurrentSortClass("locationId")%>">
				<a onclick="submitForm('listentity_form', null, null, 'locationId', '<%=entityHolder.getNextSortOrder("locationId")%>', 'tableWrapper');">
					<bean:message bundle="security" key="security.entity.register.address" />
				</a>
			</th>
			<th style="width: 12%" class="header <%=entityHolder.getCurrentSortClass("bizType")%>">
				<a onclick="submitForm('listentity_form', null, null, 'bizType', '<%=entityHolder.getNextSortOrder("bizType")%>', 'tableWrapper');">
					<bean:message bundle="security" key="security.entity.business.type" />
				</a>
			</th>
			<th style="width: 8%" class="header <%=entityHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listentity_form', null, null, 'status', '<%=entityHolder.getNextSortOrder("status")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.status" />
				</a>
			</th>
			<th style="width: 8%" class="header-nosort"><bean:message bundle="public" key="public.modify.by" /></th>
			<th style="width: 12%" class="header-nosort>"><bean:message bundle="public" key="public.modify.date" /></th>
		</tr>
	</thead>
	<logic:notEqual name="entityHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="entityVO" name="entityHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>' ondblclick="kanlist_dbclick(this,'listentity_form');">
					<td>
						<logic:equal name="entityVO" property="extended" value="2">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="entityVO" property="entityId"/>" name="chkSelectRow[]" value="<bean:write name="entityVO" property="entityId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('entityAction.do?proc=to_objectModify&id=<bean:write name="entityVO" property="encodedId"/>');"><bean:write name="entityVO" property="entityId"/></a>
					</td>
					<td class="left"><bean:write name="entityVO" property="title"/></td>
					<td class="left"><bean:write name="entityVO" property="nameZH"/></td>
					<td class="left"><bean:write name="entityVO" property="nameEN"/></td>
					<td class="left"><bean:write name="entityVO" property="decodeLocationId"/></td>
					<td class="left"><bean:write name="entityVO" property="decodeBizType"/></td>
					<td class="left"><bean:write name="entityVO" property="decodeStatus"/></td>
					<td class="left"><bean:write name="entityVO" property="decodeModifyBy"/></td>
					<td class="left"><bean:write name="entityVO" property="decodeModifyDate"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="entityHolder">
		<tfoot>
			<tr class="total">
				<td colspan="10" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="entityHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="entityHolder" property="indexStart" /> - <bean:write name="entityHolder" property="indexEnd" /></label>
					<label>&nbsp;&nbsp;<a onclick="submitForm('listentity_form', null, '<bean:write name="entityHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listentity_form', null, '<bean:write name="entityHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listentity_form', null, '<bean:write name="entityHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					<label>&nbsp;<a onclick="submitForm('listentity_form', null, '<bean:write name="entityHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="entityHolder" property="realPage" />/<bean:write name="entityHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>