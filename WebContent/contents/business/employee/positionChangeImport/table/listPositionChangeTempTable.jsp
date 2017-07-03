<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder employeePositionChangeTempHolder = (PagedListHolder) request.getAttribute("employeePositionChangeTempHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 8%" class="header <%=employeePositionChangeTempHolder.getCurrentSortClass("positionChangeId")%>">
				<a onclick="submitForm('listPositionChange_form', null, null, 'positionChangeId', '<%=employeePositionChangeTempHolder.getNextSortOrder("positionChangeId")%>', 'tableWrapper');">
					ID
				</a>
			</th>
			<th style="width: 8%" class="header <%=employeePositionChangeTempHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('listPositionChange_form', null, null, 'employeeId', '<%=employeePositionChangeTempHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.employee2.id" />
				</a>
			</th>
			<th style="width: 12%" class="header-nosort">
				<bean:message bundle="public" key="public.employee2.name" />
			</th>
			<th style="width: 12%" class="header <%=employeePositionChangeTempHolder.getCurrentSortClass("employeeCertificateNumber")%>">
				<a onclick="submitForm('listPositionChange_form', null, null, 'employeeCertificateNumber', '<%=employeePositionChangeTempHolder.getNextSortOrder("employeeCertificateNumber")%>', 'tableWrapper');">
					<bean:message bundle="public" key="public.certificate.number" />
				</a>
			</th>
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
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="public" key="public.status" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="business" key="employee.position.change.description" />
			</th>
		</tr>
	</thead>
	<logic:notEqual name="employeePositionChangeTempHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="positionChangeVO" name="employeePositionChangeTempHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<logic:equal name="positionChangeVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="positionChangeVO" property="positionChangeId"/>" name="chkSelectRow[]" value="<bean:write name="positionChangeVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<bean:write name="positionChangeVO" property="positionChangeId"/>
					</td>
					<td class="left"><bean:write name="positionChangeVO" property="employeeId"/></td>
					<td class="left"><bean:write name="positionChangeVO" property="employeeName"/></td>
					<td class="left"><bean:write name="positionChangeVO" property="employeeCertificateNumber"/></td>
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
					<td class="left">
						<bean:write name="positionChangeVO" property="decodeStatus" />
					</td>
					<td class="left">
						<bean:write name="positionChangeVO" property="decodeChangeReason" />
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="employeePositionChangeTempHolder">
		<tfoot>
			<tr class="total">
	  			<td colspan="22" class="left"> 
	  				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="employeePositionChangeTempHolder" property="holderSize" /></label>
	  				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="employeePositionChangeTempHolder" property="indexStart" /> - <bean:write name="employeePositionChangeTempHolder" property="indexEnd" /></label>
	  				<label>&nbsp;&nbsp;<a onclick="submitForm('positionChangeTemp_form', null, '<bean:write name="employeePositionChangeTempHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('positionChangeTemp_form', null, '<bean:write name="employeePositionChangeTempHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('positionChangeTemp_form', null, '<bean:write name="employeePositionChangeTempHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
	  				<label>&nbsp;<a onclick="submitForm('positionChangeTemp_form', null, '<bean:write name="employeePositionChangeTempHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
	  				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="employeePositionChangeTempHolder" property="realPage" />/<bean:write name="employeePositionChangeTempHolder" property="pageCount" /></label>&nbsp;
	  			</td>					
	    	</tr>
		</tfoot>
	</logic:present>
</table>

<script type="text/javascript">
	(function($) {
		kanList_init();
		kanCheckbox_init();
	})(jQuery);	
</script>