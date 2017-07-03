<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
		final PagedListHolder workflowActualHolder = (PagedListHolder) request.getAttribute("workflowActualHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 5%" class="header <%=workflowActualHolder.getCurrentSortClass("workflowId")%>">
				<a onclick="submitForm('listworkflowActual_form', null, null, 'workflowId', '<%=workflowActualHolder.getNextSortOrder("workflowId")%>', 'tableWrapper');"><bean:message bundle="workflow" key="workflow.actual.id" /></a>
			</th>
			<th style="width: 20%" class="header <%=workflowActualHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listworkflowActual_form', null, null, 'nameZH', '<%=workflowActualHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');"><bean:message bundle="workflow" key="workflow.actual.name.cn" /></a>
			</th>
			<th style="width: 20%" class="header <%=workflowActualHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listworkflowActual_form', null, null, 'nameEN', '<%=workflowActualHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');"><bean:message bundle="workflow" key="workflow.actual.name.en" /></a>
			</th>
			<%
				if( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ){
			%>
			<th style="width: 10%" class="header <%=workflowActualHolder.getCurrentSortClass("rightNameZH")%>">
				<a onclick="submitForm('listworkflowActual_form', null, null, 'rightNameZH', '<%=workflowActualHolder.getNextSortOrder("rightNameZH")%>', 'tableWrapper');"><bean:message bundle="workflow" key="workflow.actual.report.type" /></a>
			</th>
			<%
				} else {
			%>
			<th style="width: 10%" class="header <%=workflowActualHolder.getCurrentSortClass("rightNameEN")%>">
				<a onclick="submitForm('listworkflowActual_form', null, null, 'rightNameEN', '<%=workflowActualHolder.getNextSortOrder("rightNameEN")%>', 'tableWrapper');"><bean:message bundle="workflow" key="workflow.actual.report.type" /></a>
			</th>
			<%
				}
			%>
			<th style="width: 10%" class="header <%=workflowActualHolder.getCurrentSortClass("createDate")%>">
				<a onclick="submitForm('listworkflowActual_form', null, null, 'createDate', '<%=workflowActualHolder.getNextSortOrder("createDate")%>', 'tableWrapper');"><bean:message bundle="workflow" key="workflow.actual.report.time" /></a>
			</th>
			<th style="width: 10%" class="header <%=workflowActualHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listworkflowActual_form', null, null, 'status', '<%=workflowActualHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			
		</tr>
	</thead>
		<tbody>
			<logic:iterate id="workflowActualVO" name="workflowActualHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><a onclick="link('workflowActualStepsAction.do?proc=list_object&workflowId=<bean:write name="workflowActualVO" property="encodedId"/>');"><bean:write name="workflowActualVO" property="workflowId" /></a></td>
					<td><a onclick="link('workflowActualStepsAction.do?proc=list_object&workflowId=<bean:write name="workflowActualVO" property="encodedId"/>');"><bean:write name="workflowActualVO" property="nameZH" /></a></td>
					<td><a onclick="link('workflowActualStepsAction.do?proc=list_object&workflowId=<bean:write name="workflowActualVO" property="encodedId"/>');"><bean:write name="workflowActualVO" property="nameEN" /></a></td>
					<td class="left">
						<%
							if( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ){
						%>
							<bean:write name="workflowActualVO" property="rightNameZH" />
						<%
							} else {
						%>
							<bean:write name="workflowActualVO" property="rightNameEN" />
						<%
							}
						%>
					</td>
					<td class="left"><bean:write name="workflowActualVO" property="decodeCreateDate" /></td>
					<td class="left"><bean:write name="workflowActualVO" property="decodeStatus" /></td>
					<!-- 
					<td class="left"><bean:write name="workflowActualVO" property="decodeCreateBy" /></td>
					<td class="left"><bean:write name="workflowActualVO" property="decodeActualStepStatus" /></td>
					<td class="left"><bean:write name="workflowActualVO" property="decodeHandleDate" /></td>
					 -->
				</tr>
			</logic:iterate>
		</tbody>
	<logic:present name="workflowActualHolder">
		<tfoot>
			<tr class="total">
				<td colspan="9" class="left">
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="workflowActualHolder" property="holderSize" /> </label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="workflowActualHolder" property="indexStart" /> - <bean:write name="workflowActualHolder" property="indexEnd" /></label> 
					<label>&nbsp;&nbsp;<a onclick="submitForm('listworkflowActual_form', null, '<bean:write name="workflowActualHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listworkflowActual_form', null, '<bean:write name="workflowActualHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listworkflowActual_form', null, '<bean:write name="workflowActualHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
					<label>&nbsp;<a onclick="submitForm('listworkflowActual_form', null, '<bean:write name="workflowActualHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="workflowActualHolder" property="realPage" />/<bean:write name="workflowActualHolder" 	property="pageCount" /></label>&nbsp;
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />£º<input type="text" id="forwardPage_render" class="forwardPage_render" style="width:23px;" value="<bean:write name='workflowActualHolder' property='realPage' />" onkeydown="if(event.keyCode == 13){pageForward();}" />Ò³</label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>