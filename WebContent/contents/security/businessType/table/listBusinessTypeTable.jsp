<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder businessTypeHolder = (PagedListHolder) request.getAttribute("businessTypeHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>							
			<th style="width: 10%" class="header <%=businessTypeHolder.getCurrentSortClass("businessTypeId")%>">
				<a onclick="submitForm('listbusinessType_form', null, null, 'businessTypeId', '<%=businessTypeHolder.getNextSortOrder("businessTypeId")%>', 'tableWrapper');">
					<bean:message bundle="security" key="security.business.type.id" />
				</a>
			</th>
			<th style="width: 30%" class="header <%=businessTypeHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listbusinessType_form', null, null, 'nameZH', '<%=businessTypeHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">
					<bean:message bundle="security" key="security.business.type.name.cn" />
				</a>
			</th>
			<th style="width: 25%" class="header <%=businessTypeHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listbusinessType_form', null, null, 'nameEN', '<%=businessTypeHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">
					<bean:message bundle="security" key="security.business.type.name.en" />
				</a>
			</th>
			<th style="width: 10%" class="header <%=businessTypeHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listbusinessType_form', null, null, 'status', '<%=businessTypeHolder.getNextSortOrder("status")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.status" />
				</a>
			</th>
			<th style="width: 10%" class="header-nosort"><bean:message bundle="public" key="public.modify.by" /></th>
			<th style="width: 15%" class="header-nosort"><bean:message bundle="public" key="public.modify.date" /></th>
		</tr>
	</thead>
	<logic:notEqual name="businessTypeHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="businessTypeVO" name="businessTypeHolder" property="source" indexId="number">
				<tr class="<%= number % 2 == 1 ? "odd" : "even" %>" ondblclick="kanlist_dbclick(this,'listbusinessType_form');">
					<td>
						<logic:equal name="businessTypeVO" property="extended" value="2">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="businessTypeVO" property="businessTypeId"/>" name="chkSelectRow[]" value="<bean:write name="businessTypeVO" property="businessTypeId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('businessTypeAction.do?proc=to_objectModify&id=<bean:write name="businessTypeVO" property="encodedId"/>');"><bean:write name="businessTypeVO" property="businessTypeId"/></a>
					</td>
					<td class="left"><bean:write name="businessTypeVO" property="nameZH"/></td>
					<td class="left"><bean:write name="businessTypeVO" property="nameEN"/></td>
					<td class="left"><bean:write name="businessTypeVO" property="decodeStatus"/></td>
					<td class="left"><bean:write name="businessTypeVO" property="decodeModifyBy"/></td>
					<td class="left"><bean:write name="businessTypeVO" property="decodeModifyDate"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="businessTypeHolder">
		<tfoot>
			<tr class="total">
				<td  colspan="7" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="businessTypeHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="businessTypeHolder" property="indexStart" /> - <bean:write name="businessTypeHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listbusinessType_form', null, '<bean:write name="businessTypeHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listbusinessType_form', null, '<bean:write name="businessTypeHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listbusinessType_form', null, '<bean:write name="businessTypeHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listbusinessType_form', null, '<bean:write name="businessTypeHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="businessTypeHolder" property="realPage" />/<bean:write name="businessTypeHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>