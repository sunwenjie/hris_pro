<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<%
	final PagedListHolder positionChangeHolder = (PagedListHolder) request.getAttribute("positionChangeHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<logic:equal name="isPaged" value="1">
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 8%" class="header <%=positionChangeHolder.getCurrentSortClass("positionChangeId")%>">
				<a onclick="submitForm('listPositionChange_form', null, null, 'positionChangeId', '<%=positionChangeHolder.getNextSortOrder("positionChangeId")%>', 'tableWrapper');">
					ID
				</a>
			</th>
			<th style="width: 8%" class="header <%=positionChangeHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('listPositionChange_form', null, null, 'employeeId', '<%=positionChangeHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.employee2.id" />
				</a>
			</th>
			<th style="width: 12%" class="header-nosort">
				<bean:message bundle="public" key="public.employee2.name" />
			</th>
			<th style="width: 12%" class="header <%=positionChangeHolder.getCurrentSortClass("employeeCertificateNumber")%>">
				<a onclick="submitForm('listPositionChange_form', null, null, 'employeeCertificateNumber', '<%=positionChangeHolder.getNextSortOrder("employeeCertificateNumber")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.certificate.number" />
				</a>
			</th>
			</logic:equal>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.oldBranchName" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.oldParentBranchName" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.oldPositionName" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.oldParentPositionName" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.oldPositionGradeName" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.oldParentPositionOwnersName" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.newBranchId" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.newParentBranchName" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.newPositionId" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.newParentPositionName" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.newPositionGradeName" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.newParentPositionOwnersName" />
			</th>
			<th style="width: 8%" class="header-nosort">
				New Direct Report Manager (Biz Leader) 
			</th>
			<th style="width: 8%" class="header-nosort">
				Job Role
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.effectiveDate" />
			</th>
			<logic:equal name="isPaged" value="1">
				<th style="width: 8%" class="header-nosort">
					<bean:message bundle="public" key="public.status" />
				</th>
			</logic:equal>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.description" />
			</th>
		</tr>
	</thead>
	<logic:notEqual name="positionChangeHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="positionChangeVO" name="positionChangeHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<logic:equal name="isPaged" value="1">
					<td>
						<logic:equal name="positionChangeVO" property="status" value="1">
							<logic:empty name="positionChangeVO" property="workflowId" >
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="positionChangeVO" property="positionChangeId"/>" name="chkSelectRow[]" value="<bean:write name="positionChangeVO" property="positionChangeId"/>" />
							</logic:empty>
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('employeePositionChangeAction.do?proc=to_objectModify&id=<bean:write name="positionChangeVO" property="encodedId"/>');"><bean:write name="positionChangeVO" property="positionChangeId"/></a>
					</td>
					<td class="left"><bean:write name="positionChangeVO" property="employeeId"/></td>
					<td class="left"><bean:write name="positionChangeVO" property="employeeName"/></td>
					<td class="left"><bean:write name="positionChangeVO" property="employeeCertificateNumber"/></td>
					</logic:equal>
					<td class="left"><bean:write name="positionChangeVO" property="oldBranchName"/></td>
					<td class="left"><bean:write name="positionChangeVO" property="oldParentBranchName"/></td>
					<td class="left"><bean:write name="positionChangeVO" property="oldPositionName" /></td>
					<td class="left"><bean:write name="positionChangeVO" property="oldParentPositionName" /></td>
					<td class="left"><bean:write name="positionChangeVO" property="oldPositionGradeName" /></td>
					<td class="left"><bean:write name="positionChangeVO" property="oldParentPositionOwnersName" /></td>
					<td class="left"><bean:write name="positionChangeVO" property="newBranchName"/></td>
					<td class="left"><bean:write name="positionChangeVO" property="newParentBranchName"/></td>
					<td class="left"><bean:write name="positionChangeVO" property="newPositionName" /></td>
					<td class="left"><bean:write name="positionChangeVO" property="newParentPositionName" /></td>
					<td class="left"><bean:write name="positionChangeVO" property="newPositionGradeName" /></td>
					<td class="left"><bean:write name="positionChangeVO" property="newParentPositionOwnersName" /></td>
					<td class="left"><bean:write name="positionChangeVO" property="remark2" /></td>
					<td class="left"><bean:write name="positionChangeVO" property="decodeJobRole" /></td>
					<td class="left">
						<bean:write name="positionChangeVO" property="effectiveDate" />
					</td>
					<logic:equal name="isPaged" value="1">
					<td class="left">
						<bean:write name="positionChangeVO" property="decodeStatus" />
						<logic:notEmpty name="positionChangeVO" property="workflowId" >
							&nbsp;&nbsp;<img src='images/magnifer.png' onclick=popupWorkflow('<bean:write name="positionChangeVO" property="workflowId"/>'); />
						</logic:notEmpty>
					</td>
					</logic:equal>
					<td class="left">
						<bean:write name="positionChangeVO" property="decodeChangeReason" />
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:equal name="isPaged" value="1">
		<logic:present name="positionChangeHolder">
			<tfoot>
				<tr class="total">
		  			<td colspan="22" class="left"> 
		  				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="positionChangeHolder" property="holderSize" /></label>
		  				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="positionChangeHolder" property="indexStart" /> - <bean:write name="positionChangeHolder" property="indexEnd" /></label>
		  				<label>&nbsp;&nbsp;<a onclick="submitForm('listPositionChange_form', null, '<bean:write name="positionChangeHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
		  				<label>&nbsp;<a onclick="submitForm('listPositionChange_form', null, '<bean:write name="positionChangeHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
		  				<label>&nbsp;<a onclick="submitForm('listPositionChange_form', null, '<bean:write name="positionChangeHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
		  				<label>&nbsp;<a onclick="submitForm('listPositionChange_form', null, '<bean:write name="positionChangeHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
		  				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="positionChangeHolder" property="realPage" />/<bean:write name="positionChangeHolder" property="pageCount" /></label>&nbsp;
		  			</td>					
		    	</tr>
			</tfoot>
		</logic:present>
	</logic:equal>
</table>
<script type="text/javascript">
	useFixColumn(4);
</script>