<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ page import="com.kan.hro.web.actions.biz.client.ClientAction"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractVO"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<%
	final PagedListHolder employeeContractHolder = (PagedListHolder) request.getAttribute("pagedListHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 6%" class="header-nosort">
				<logic:equal value="2" name="role"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
				<logic:equal value="1" name="role"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
				<logic:equal value="4" name="role"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
			</th>
			<th style="width: 16%" class="header-nosort">
				<logic:equal value="2" name="role"><bean:message bundle="public" key="public.contract2.name" /></logic:equal>
				<logic:equal value="1" name="role"><bean:message bundle="public" key="public.contract1.name" /></logic:equal>
				<logic:equal value="4" name="role"><bean:message bundle="public" key="public.contract1.name" /></logic:equal>
			</th>	 
			<th style="width: 6%" class="header-nosort">
				<bean:message bundle="public" key="public.start.date" />
			</th>
			<th style="width: 6%" class="header-nosort">
				<bean:message bundle="public" key="public.end.date" />
			</th>
			<logic:notEqual value="4" name="role">			
				<th style="width: 8%" class="header-nosort">
					<logic:equal value="2" name="role"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
					<logic:equal value="1" name="role"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
				</th>
			</logic:notEqual>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="public" key="public.contract.resign.date" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="public" key="public.modify.by" />
			</th>
			<th style="width: 8%" class="header-nosort">
				<bean:message bundle="public" key="public.modify.date" />
			</th>
			<th style="width: 6%" class="header-nosort">
				<bean:message bundle="public" key="public.status" />
			</th>
			<th style="width: 6%" class="header-nosort">
				<bean:message bundle="public" key="public.employee.status" />
			</th>
		</tr>
	</thead>
	<logic:notEqual name="pagedListHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="employeeContractVO" name="pagedListHolder" property="source" indexId="number">
			  	<%
					PagedListHolder holder = (PagedListHolder)request.getAttribute( "pagedListHolder");
				    EmployeeContractVO vo = (EmployeeContractVO)(holder.getSource().get(number));
				    String ownerId = vo.getOwner() == null ? "" : vo.getOwner();
				%>
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left">
						<kan:auth right="view" action="<%=EmployeeContractAction.getAccessAction(request,response)  %>" owner="<%=ownerId %>">
							<a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId" />');">
						</kan:auth >
						<bean:write name="employeeContractVO" property="contractId" />
						<kan:auth right="view" action="<%=EmployeeContractAction.getAccessAction(request,response)  %>" owner="<%=ownerId %>">
							</a>
						</kan:auth >
					</td>															
					<td class="left"><bean:write name="employeeContractVO" property="nameZH" /></td>								
					<td class="left"><bean:write name="employeeContractVO" property="startDate" /></td>								
					<td class="left"><bean:write name="employeeContractVO" property="endDate" /></td>
					<logic:notEqual value="4" name="role">						
						<td class="left">
							<logic:equal value="1" name="role">
								<a href="#" onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedOrderId" />');">
									<bean:write name="employeeContractVO" property="orderId" />
								</a>
							</logic:equal>
							<logic:notEqual value="1" name="role">
								<bean:write name="employeeContractVO" property="orderId" />
							</logic:notEqual>
							
						</td>
					</logic:notEqual>
					<logic:equal value="1" name="role">
						<td class="left">
						<kan:auth  right="Modify" action="<%=ClientAction.accessAction%>" owner="<%=ownerId %>">
							<a href="#" onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedClientId" />');">
						</kan:auth >		
								<bean:write name="employeeContractVO" property="clientId" />
						<kan:auth  right="Modify" action="<%=ClientAction.accessAction%>" owner="<%=ownerId %>">		
							</a>
						</kan:auth >		
						</td>
						<td class="left">
							<bean:write name="employeeContractVO" property="clientName" /> 
						</td>
					</logic:equal>							
					<td class="left"><bean:write name="employeeContractVO" property="resignDate" /></td>								
					<td class="left"><bean:write name="employeeContractVO" property="decodeModifyBy" /></td>								
					<td class="left"><bean:write name="employeeContractVO" property="decodeModifyDate" /></td>								
					<td class="left"><bean:write name="employeeContractVO" property="decodeStatus" /></td>								
					<td class="left"><bean:write name="employeeContractVO" property="decodeEmployStatus" /></td>								
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
</table>