<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="popup modal large content hide" id="workflowListModalId">
    <div class="modal-header" id="clientHeader">
        <a class="close" data-dismiss="modal" onclick="$('#workflowListModalId').addClass('hide').hide();$('#shield').hide();" title="<bean:message bundle="public" key="button.close" />">¡Á</a>
        <label id="importExcelTitleLableId"><bean:message bundle="workflow" key="workflow.step" />: <bean:write name="enCodeWorkflowId" /></label>
    </div>
    <div class="modal-body">
    	<div class="top">
		</div>
<%
	PagedListHolder workflowActualStepsHolder = (PagedListHolder) request.getAttribute("workflowActualStepsHolder");
%>
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="workflow" key="workflow.step.step.index" />
			</th>
			<th style="width: 20%" class="header-nosort">
				<bean:message bundle="workflow" key="workflow.define.name.cn" />
			</th>
			<th style="width: 20%" class="header-nosort">
				<bean:message bundle="workflow" key="workflow.define.name.en" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="workflow" key="workflow.step.audit.position" />
					&amp; 
				<bean:message bundle="workflow" key="workflow.step.audit.staff" />
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="workflow" key="workflow.step.handle.time" />
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="public" key="public.description" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="public" key="public.status" />
			</th>
		</tr>
	</thead>
	<logic:notEqual name="workflowActualStepsHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="workflowActualStepsVO" name="workflowActualStepsHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left"><bean:write name="workflowActualStepsVO" property="stepIndex" /></td>
					<td><bean:write name="workflowActualStepsVO" property="searchField.workflowNameZH" /></td>
					<td><bean:write name="workflowActualStepsVO" property="searchField.workflowNameEN" /></td>
					<td>
					<%
						if( "ZH".equalsIgnoreCase( request.getLocale().getLanguage()  )){
					%>
						<bean:write name="workflowActualStepsVO" property="searchField.positionTitleZH" />
					<%
						} else {
					%>
						<bean:write name="workflowActualStepsVO" property="searchField.positionTitleEN" />
					<%
						}
					%>
					</td>
					<td class="left"><bean:write name="workflowActualStepsVO" property="decodeHandleDate" /></td>
					<td class="left"><bean:write name="workflowActualStepsVO" property="description" /></td>
					<td class="left">
						<bean:write name="workflowActualStepsVO" property="decodeStatus" />
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
<%-- 	<logic:present name="workflowActualStepsHolder"> --%>
<!-- 		<tfoot> -->
<!-- 			<tr class="total"> -->
<!-- 				<td></td> -->
<!-- 				<td colspan="5" class="right"> -->
<%-- 					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="workflowActualStepsHolder" property="holderSize" /> </label>  --%>
<%-- 					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="workflowActualStepsHolder" property="indexStart" /> - <bean:write name="workflowActualStepsHolder" property="indexEnd" /></label>  --%>
<%-- 					<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listworkflowModule_form', null, '<bean:write name="workflowActualStepsHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>  --%>
<%-- 					<label>&nbsp;<a href="#" onclick="submitForm('listworkflowModule_form', null, '<bean:write name="workflowActualStepsHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>  --%>
<%-- 					<label>&nbsp;<a href="#" onclick="submitForm('listworkflowModule_form', null, '<bean:write name="workflowActualStepsHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>  --%>
<%-- 					<label>&nbsp;<a href="#" onclick="submitForm('listworkflowModule_form', null, '<bean:write name="workflowActualStepsHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>  --%>
<%-- 					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="workflowActualStepsHolder" property="realPage" />/<bean:write name="workflowActualStepsHolder" 	property="pageCount" /></label>&nbsp; --%>
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 		</tfoot> -->
<%-- 	</logic:present> --%>
</table>

    </div>
    <div class="modal-footer"></div>
</div>
<!-- Module Box HTML: Ends -->
