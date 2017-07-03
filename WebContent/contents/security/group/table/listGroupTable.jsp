<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder groupHolder = (PagedListHolder) request.getAttribute("groupHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=groupHolder.getCurrentSortClass("groupId")%>">
				<a onclick="submitForm('listgroup_form', null, null, 'groupId', '<%=groupHolder.getNextSortOrder("groupId")%>', 'tableWrapper');"><bean:message bundle="security" key="security.position.group.id" /></a>
			</th>
			<th style="width: 30%" class="header <%=groupHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listgroup_form', null, null, 'nameZH', '<%=groupHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="security" key="security.position.group.id" /></a>
			</th>
			<th style="width: 25%" class="header <%=groupHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listgroup_form', null, null, 'nameEN', '<%=groupHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="security" key="security.position.group.name.en" /></a>
			</th>
			<th style="width: 10%" class="header <%=groupHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listgroup_form', null, null, 'status', '<%=groupHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			<th style="width: 10%" class="header-nosort"><bean:message bundle="public" key="public.modify.by" /></th>
			<th style="width: 15%" class="header-nosort"><bean:message bundle="public" key="public.modify.date" /></th>
		</tr>
	</thead>
	<logic:notEqual name="groupHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="groupVO" name="groupHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>' ondblclick="kanlist_dbclick(this,'listgroup_form');">
					<td>
						<logic:equal name="groupVO" property="extended" value="2">					
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="groupVO" property="groupId"/>" name="chkSelectRow[]" value="<bean:write name="groupVO" property="groupId"/>" />
						</logic:equal>	
					</td>
					<td class="left">
						<a href="#" onclick="link('groupAction.do?proc=to_objectModify&groupId=<bean:write name="groupVO" property="encodedId"/>');"><bean:write name="groupVO" property="groupId"/></a>
					</td>
					<td class="left"><bean:write name="groupVO" property="nameZH" /></td>
					<td class="left"><bean:write name="groupVO" property="nameEN" /></td>
					<td class="left"><bean:write name="groupVO" property="decodeStatus" /></td>
					<td class="left"><bean:write name="groupVO" property="decodeModifyBy" /></td>
					<td class="left"><bean:write name="groupVO" property="decodeModifyDate" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="groupHolder">
		<tfoot>
			<tr class="total">
	  			<td colspan="7" class="left"> 
		  			<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="groupHolder" property="holderSize" /></label>
		  			<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="groupHolder" property="indexStart" /> - <bean:write name="groupHolder" property="indexEnd" /></label>
		  			<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listgroup_form', null, '<bean:write name="groupHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
		  			<label>&nbsp;<a href="#" onclick="submitForm('listgroup_form', null, '<bean:write name="groupHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
		  			<label>&nbsp;<a href="#" onclick="submitForm('listgroup_form', null, '<bean:write name="groupHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
		  			<label>&nbsp;<a href="#" onclick="submitForm('listgroup_form', null, '<bean:write name="groupHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
		  			<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="groupHolder" property="realPage" />/<bean:write name="groupHolder" property="pageCount" /></label>&nbsp;
	  			</td>					
	    	</tr>
		</tfoot>
	</logic:present>
</table>