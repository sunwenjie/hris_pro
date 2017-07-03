<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder workflowDefineHolder = (PagedListHolder) request.getAttribute( "workflowDefineHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" /></th>
			<th style="width: 34%" class="header <%=workflowDefineHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listworkflowDefine_form', null, null, 'nameZH', '<%=workflowDefineHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="workflow" key="workflow.define.name.cn" /></a>
			</th>
			<th style="width: 34%" class="header <%=workflowDefineHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listworkflowDefine_form', null, null, 'nameEN', '<%=workflowDefineHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="workflow" key="workflow.define.name.en" /></a>
			</th>
			<th style="width: 20%" class="header <%=workflowDefineHolder.getCurrentSortClass("approvalType")%>">
				<a onclick="submitForm('listworkflowDefine_form', null, null, 'approvalType', '<%=workflowDefineHolder.getNextSortOrder("approvalType")%>', 'tableWrapper');"><bean:message bundle="workflow" key="workflow.define.approval.type" /></a>
			</th>
			<th style="width: 12%" class="header <%=workflowDefineHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listworkflowDefine_form', null, null, 'status', '<%=workflowDefineHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="workflowDefineHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="workflowDefineVO" name="workflowDefineHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal value="2" name="workflowDefineVO" property="extended">
							<logic:equal name="workflowDefineVO" property="defineId" value="0">
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="workflowDefineVO" property="defineId"/>" name="chkSelectRow[]" value="" disabled="disabled" />
							</logic:equal> 
							<logic:notEqual name="workflowDefineVO" property="defineId" value="0">
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="workflowDefineVO" property="defineId"/>" name="chkSelectRow[]" value="<bean:write name="workflowDefineVO" property="defineId"/>" />
							</logic:notEqual>
						</logic:equal>
					</td>
					<td><a onclick="link('workflowDefineAction.do?proc=to_objectModify&defineId=<bean:write name="workflowDefineVO" property="encodedId"/>');"><bean:write name="workflowDefineVO" property="nameZH" /></a></td>
					<td><a onclick="link('workflowDefineAction.do?proc=to_objectModify&defineId=<bean:write name="workflowDefineVO" property="encodedId"/>');"><bean:write name="workflowDefineVO" property="nameEN" /></a></td>
					<td><bean:write name="workflowDefineVO"  property="decodeApprovalType"/></td>
					<td class="left"><bean:write name="workflowDefineVO" property="decodeStatus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="workflowDefineHolder">
		<tfoot>
			<tr class="total">
				<td colspan="6" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="workflowDefineHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="workflowDefineHolder" property="indexStart" /> - <bean:write name="workflowDefineHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listworkflowDefine_form', null, '<bean:write name="workflowDefineHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listworkflowDefine_form', null, '<bean:write name="workflowDefineHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listworkflowDefine_form', null, '<bean:write name="workflowDefineHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listworkflowDefine_form', null, '<bean:write name="workflowDefineHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="workflowDefineHolder" property="realPage" />/<bean:write name="workflowDefineHolder" 	property="pageCount" /></label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>