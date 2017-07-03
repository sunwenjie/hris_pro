<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractVO"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder serviceContractHolder = (PagedListHolder) request.getAttribute("serviceContractHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>	
			<th style="width: 8%" class="header <%=serviceContractHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('listServiceContract_form', null, null, 'contractId', '<%=serviceContractHolder.getNextSortOrder("contractId")%>', 'tableWrapperContractService');">
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: 9%" class="header <%=serviceContractHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('listServiceContract_form', null, null, 'startDate', '<%=serviceContractHolder.getNextSortOrder("startDate")%>', 'tableWrapperContractService');">
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="1"><bean:message bundle="public" key="public.contract1.start.date" /></logic:equal>
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="2"><bean:message bundle="public" key="public.contract2.start.date" /></logic:equal>
				</a>
			</th>
			<th style="width: 9%" class="header <%=serviceContractHolder.getCurrentSortClass("endDate")%>">
				<a onclick="submitForm('listServiceContract_form', null, null, 'endDate', '<%=serviceContractHolder.getNextSortOrder("endDate")%>', 'tableWrapperContractService');">
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="1"><bean:message bundle="public" key="public.contract1.end.date" /></logic:equal>
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="2"><bean:message bundle="public" key="public.contract2.end.date" /></logic:equal>
				</a>
			</th>
			<th style="width: 8%" class="header <%=serviceContractHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('listServiceContract_form', null, null, 'employeeId', '<%=serviceContractHolder.getNextSortOrder("employeeId")%>', 'tableWrapperContractService');">
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: 11%" class="header <%=serviceContractHolder.getCurrentSortClass("employeeNo")%>">
				<a onclick="submitForm('listServiceContract_form', null, null, 'employeeNo', '<%=serviceContractHolder.getNextSortOrder("employeeNo")%>', 'tableWrapperContractService');">
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="1"><bean:message bundle="public" key="public.employee1.no" /></logic:equal>
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="2"><bean:message bundle="public" key="public.employee2.no" /></logic:equal>
				</a>
			</th>
			<th style="width: 11%" class="header <%=serviceContractHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('listServiceContract_form', null, null, 'employeeNameZH', '<%=serviceContractHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapperContractService');">
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</a>
			</th>
			<th style="width: 11%" class="header <%=serviceContractHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('listServiceContract_form', null, null, 'employeeNameEN', '<%=serviceContractHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapperContractService');">
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</a>				
			</th>
			<th style="width: 19%" class="header <%=serviceContractHolder.getCurrentSortClass("certificateNumber")%>">
				<a onclick="submitForm('listServiceContract_form', null, null, 'certificateNumber', '<%=serviceContractHolder.getNextSortOrder("certificateNumber")%>', 'tableWrapperContractService');"><bean:message bundle="public" key="public.certificate.number" /></a>
			</th>
			<th style="width: 7%" class="header <%=serviceContractHolder.getCurrentSortClass("employStatus")%>">
				<a onclick="submitForm('listServiceContract_form', null, null, 'employStatus', '<%=serviceContractHolder.getNextSortOrder("employStatus")%>', 'tableWrapperContractService');">
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="1"><bean:message bundle="public" key="public.employee1.status" /></logic:equal>
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="2"><bean:message bundle="public" key="public.employee2.status" /></logic:equal>
				</a>
			</th>
			<th style="width: 7%" class="header <%=serviceContractHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listServiceContract_form', null, null, 'status', '<%=serviceContractHolder.getNextSortOrder("status")%>', 'tableWrapperContractService');">
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="1"><bean:message bundle="public" key="public.contract1.status" /></logic:equal>
					<logic:equal name="__COOKIE_USER_JSON" property="role" value="2"><bean:message bundle="public" key="public.contract1.status" /></logic:equal>
				</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="serviceContractHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="employeeContractVO" name="serviceContractHolder" property="source" indexId="number">
				<%
				    final EmployeeContractVO tempEmployeeContractVO = (EmployeeContractVO) pageContext.getAttribute( "employeeContractVO" );
					final String ownerId = tempEmployeeContractVO == null || tempEmployeeContractVO.getOwner() == null ? "" : tempEmployeeContractVO.getOwner();
				%>
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>' >
					<td>
						<logic:equal name="employeeContractVO" property="status" value="1">
							<kan:auth right="delete" action="<%=EmployeeContractAction.getAccessAction(request, response)  %>" owner="<%=ownerId %>">
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="employeeContractVO" property="contractId"/>" name="chkSelectRow[]" value="<%=ownerId %>" />
							</kan:auth>
						</logic:equal>
						<logic:equal name="employeeContractVO" property="status" value="4">
							<kan:auth right="delete" action="<%=EmployeeContractAction.getAccessAction(request, response)  %>" owner="<%=ownerId %>">
								<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="employeeContractVO" property="contractId"/>" name="chkSelectRow[]" value="<%=ownerId %>" />
							</kan:auth>
						</logic:equal>
					</td>
					<td class="left">
						<kan:auth right="Modify" action="<%=EmployeeContractAction.getAccessAction(request, response)  %>" owner="<%=ownerId %>">
							<a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>');">
						</kan:auth>
							<bean:write name="employeeContractVO" property="contractId"/>
						<kan:auth  right="Modify" action="<%=EmployeeContractAction.getAccessAction(request,response)  %>" owner="<%=ownerId %>">	
							</a>
						</kan:auth>	
					</td>
					<td class="left"><bean:write name="employeeContractVO" property="startDate"/></td>
					<td class="left"><bean:write name="employeeContractVO" property="endDate"/></td>
					<td class="left">
						<kan:auth  right="Modify" action="<%=EmployeeAction.accessAction  %>" owner="<%=ownerId %>">
							<a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedEmployeeId"/>');">
						</kan:auth>
						<bean:write name="employeeContractVO" property="employeeId"/>
						<kan:auth  right="Modify" action="<%=EmployeeAction.accessAction  %>" owner="<%=ownerId %>">
							</a>
						</kan:auth>
					</td>
					<td class="left"><bean:write name="employeeContractVO" property="employeeNo"/></td>
					<td class="left"><bean:write name="employeeContractVO" property="employeeNameZH"/></td>
					<td class="left"><bean:write name="employeeContractVO" property="employeeNameEN"/></td>
					<td class="left"><bean:write name="employeeContractVO" property="certificateNumber"/></td>
					<td class="left"><bean:write name="employeeContractVO" property="decodeEmployStatus"/></td>
					<td class="left"><bean:write name="employeeContractVO" property="decodeStatus"/>
						<logic:equal value="1" name="employeeContractVO" property="status">
							<kan:auth right="submit" action="<%=EmployeeContractAction.getAccessAction(request,response)%>" owner="<%=ownerId %>">
								<a onclick="submitLink('<bean:write name="employeeContractVO" property="encodedId"/>')"><bean:message bundle="public" key="button.submit" /></a>
							</kan:auth>
						</logic:equal>
						<logic:equal value="4" name="employeeContractVO" property="status">
							<kan:auth right="submit" action="<%=EmployeeContractAction.getAccessAction(request,response)%>" owner="<%=ownerId %>">
								<a onclick="submitLink('<bean:write name="employeeContractVO" property="encodedId"/>')"><bean:message bundle="public" key="button.submit" /></a>
							</kan:auth>
						</logic:equal>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="serviceContractHolder">
		<tfoot>
			<tr class="total">
				<td colspan="11" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="serviceContractHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="serviceContractHolder" property="indexStart" /> - <bean:write name="serviceContractHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a onclick="submitForm('listServiceContract_form', null, '<bean:write name="serviceContractHolder" property="firstPage" />', null, null, 'tableWrapperContractService');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a onclick="submitForm('listServiceContract_form', null, '<bean:write name="serviceContractHolder" property="previousPage" />', null, null, 'tableWrapperContractService');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a onclick="submitForm('listServiceContract_form', null, '<bean:write name="serviceContractHolder" property="nextPage" />', null, null, 'tableWrapperContractService');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a onclick="submitForm('listServiceContract_form', null, '<bean:write name="serviceContractHolder" property="lastPage" />', null, null, 'tableWrapperContractService');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="serviceContractHolder" property="realPage" />/<bean:write name="serviceContractHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>

<script type="text/javascript">
	(function($) {
		kanList_init('tableWrapperContractService');
		kanCheckbox_init('tableWrapperContractService');
	})(jQuery);
	
	function submitLink(id){
		$.post("employeeContractAction.do?proc=submit_object&comeFrom=order&contractId=" + id, {}, function(html){
			$("#tableWrapperContractService").html(html);
		}, 'text');
	};
</script>