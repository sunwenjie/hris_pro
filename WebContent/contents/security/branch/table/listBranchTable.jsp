<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder branchHolder = (PagedListHolder) request.getAttribute("branchHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 10%" class="header <%=branchHolder.getCurrentSortClass("branchId")%>">
				<a onclick="submitForm('listbranch_form', null, null, 'branchId', '<%=branchHolder.getNextSortOrder("branchId")%>', 'tableWrapper');"><bean:message bundle="security" key="security.branch.id" /></a>
			</th>
			<th style="width: 10%" class="header <%=branchHolder.getCurrentSortClass("branchCode")%>">
				<a onclick="submitForm('listbranch_form', null, null, 'branchCode', '<%=branchHolder.getNextSortOrder("branchCode")%>', 'tableWrapper');"><bean:message bundle="security" key="security.branch.code" /></a>
			</th>
			<th style="width: 25%" class="header <%=branchHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listbranch_form', null, null, 'nameZH', '<%=branchHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="security" key="security.branch.name.cn" /></a>
			</th>
			<th style="width: 20%" class="header <%=branchHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listbranch_form', null, null, 'nameEN', '<%=branchHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="security" key="security.branch.name.en" /></a>
			</th>
			<th style="width: 10%" class="header-nosort"><bean:message bundle="security" key="security.branch.parent.dept" /></th>
			<th style="width: 10%" class="header <%=branchHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listbranch_form', null, null, 'status', '<%=branchHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			<th style="width: 10%" class="header-nosort"><bean:message bundle="public" key="public.modify.by" /></th>
			<th style="width: 15%" class="header-nosort"><bean:message bundle="public" key="public.modify.date" /></th>
		</tr>
	</thead>
	<logic:notEqual name="branchHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="branchVO" name="branchHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>' ondblclick="kanlist_dbclick(this,'listbranch_form');">
					<td>
						<logic:equal name="branchVO" property="extended" value="2">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="branchVO" property="branchId"/>" name="chkSelectRow[]" value="<bean:write name="branchVO" property="branchId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('branchAction.do?proc=to_objectModify&id=<bean:write name="branchVO" property="encodedId"/>');"><bean:write name="branchVO" property="branchId"/></a>
					</td>
					<td class="left"><bean:write name="branchVO" property="branchCode" /></td>
					<td class="left"><bean:write name="branchVO" property="nameZH" /></td>
					<td class="left"><bean:write name="branchVO" property="nameEN" /></td>
					<td class="left"><bean:write name="branchVO" property="parentBranchName" /></td>
					<td class="left"><bean:write name="branchVO" property="decodeStatus" /></td>
					<td class="left"><bean:write name="branchVO" property="decodeModifyBy" /></td>
					<td class="left"><bean:write name="branchVO" property="decodeModifyDate" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="branchHolder">
		<tfoot>
			<tr class="total">
	  			<td colspan="9" class="left"> 
	  				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="branchHolder" property="holderSize" /></label>
	  				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="branchHolder" property="indexStart" /> - <bean:write name="branchHolder" property="indexEnd" /></label>
	  				<label>&nbsp;&nbsp;<a onclick="submitForm('listbranch_form', null, '<bean:write name="branchHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('listbranch_form', null, '<bean:write name="branchHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('listbranch_form', null, '<bean:write name="branchHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('listbranch_form', null, '<bean:write name="branchHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
	  				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="branchHolder" property="realPage" />/<bean:write name="branchHolder" property="pageCount" /></label>&nbsp;
	  			</td>					
	    	</tr>
		</tfoot>
	</logic:present>
</table>