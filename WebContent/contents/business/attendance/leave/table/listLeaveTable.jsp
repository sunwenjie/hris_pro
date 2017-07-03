<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@page import="com.kan.hro.web.actions.biz.attendance.LeaveHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final PagedListHolder leaveHeaderHolder = ( PagedListHolder ) request.getAttribute( "leaveHeaderHolder" );
%>

<logic:notEmpty name="MESSAGE">
<div id="_USER_DEFINE_MSG" class="message <bean:write name="MESSAGE_CLASS" /> fadable" style="display:none;"><bean:write name="MESSAGE" /></div>
</logic:notEmpty>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 4%" class="header <%=leaveHeaderHolder.getCurrentSortClass("leaveHeaderId")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'leaveHeaderId', '<%=leaveHeaderHolder.getNextSortOrder("leaveHeaderId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.leave.id" /></a>
			</th>
			<th style="width: 5%" class="header <%=leaveHeaderHolder.getCurrentSortClass("itemId")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'itemId', '<%=leaveHeaderHolder.getNextSortOrder("itemId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.leave.type" /></a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "12"%>%" class="header <%=leaveHeaderHolder.getCurrentSortClass("estimateStartDate")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'estimateStartDate', '<%=leaveHeaderHolder.getNextSortOrder("estimateStartDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.start.time" /></a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "12"%>%" class="header <%=leaveHeaderHolder.getCurrentSortClass("estimateEndDate")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'estimateEndDate', '<%=leaveHeaderHolder.getNextSortOrder("estimateEndDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.end.time" /></a>
			</th>
			<th style="width: 6%" class="header-nosort"><bean:message bundle="business" key="business.leave.hours" /></th>
			<th style="width: 6%" class="header <%=leaveHeaderHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'employeeId', '<%=leaveHeaderHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
				    <logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "12"%>%" class="header <%=leaveHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'employeeNameZH', '<%=leaveHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "12"%>%" class="header <%=leaveHeaderHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'employeeNameEN', '<%=leaveHeaderHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</a>
			</th>
			<th style="width: 6%" class="header <%=leaveHeaderHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'contractId', '<%=leaveHeaderHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
				</a>
			</th> 
			<logic:equal name="role" value="1">
				<th style="width: 7%" class="header <%=leaveHeaderHolder.getCurrentSortClass("clientId")%>">
					<a onclick="submitForm('searchLeave_form', null, null, 'clientId', '<%=leaveHeaderHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">客户ID</a>
				</th>
				<th style="width: 13%" class="header-nosort">
					客户名称
				</th>
			</logic:equal>
			<th style="width: 7%" class="header <%=leaveHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'status', '<%=leaveHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			<logic:notEqual name="role" value="5">
				<th style="width: 7%" class="header <%=leaveHeaderHolder.getCurrentSortClass("modifyBy")%>">
					<a onclick="submitForm('searchLeave_form', null, null, 'modifyBy', '<%=leaveHeaderHolder.getNextSortOrder("modifyBy")%>', 'tableWrapper');"><bean:message bundle="public" key="public.modify.by" /></a>
				</th>
			</logic:notEqual>
			<th style="width: 11%" class="header <%=leaveHeaderHolder.getCurrentSortClass("modifyDate")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'modifyDate', '<%=leaveHeaderHolder.getNextSortOrder("modifyDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.modify.date" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="leaveHeaderHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="leaveHeaderVO" name="leaveHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="leaveHeaderVO" property="status" value="1" >
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="leaveHeaderVO" property="leaveHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="leaveHeaderVO" property="encodedId"/>" />
						</logic:equal>
						<logic:equal name="leaveHeaderVO" property="status" value="4" >
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="leaveHeaderVO" property="leaveHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="leaveHeaderVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('leaveHeaderAction.do?proc=to_objectModify&id=<bean:write name="leaveHeaderVO" property="encodedId"/>');">
							<bean:write name="leaveHeaderVO" property="leaveHeaderId" />
						</a>
					</td>
					<td class="left"><bean:write name="leaveHeaderVO" property="decodeItemId" /></td>
					<td class="left">
						<logic:equal name="leaveHeaderVO" property="dataFrom" value="2">
							-- -- 
						</logic:equal>
						<logic:notEqual name="leaveHeaderVO" property="dataFrom" value="2">
							<logic:empty name="leaveHeaderVO" property="actualStartDate">
								<bean:write name="leaveHeaderVO" property="estimateStartDate" />
							</logic:empty>
							<logic:notEmpty name="leaveHeaderVO" property="actualStartDate">
								<bean:write name="leaveHeaderVO" property="actualStartDate" />
							</logic:notEmpty>
						</logic:notEqual>	
					</td>
					<td class="left">
						<logic:equal name="leaveHeaderVO" property="dataFrom" value="2">
							-- -- 
						</logic:equal>
						<logic:notEqual name="leaveHeaderVO" property="dataFrom" value="2">
							<logic:empty name="leaveHeaderVO" property="actualEndDate">
								<bean:write name="leaveHeaderVO" property="estimateEndDate" />
							</logic:empty>
							<logic:notEmpty name="leaveHeaderVO" property="actualEndDate">
								<bean:write name="leaveHeaderVO" property="actualEndDate" />
							</logic:notEmpty>
						</logic:notEqual>
					</td>
					<td class="right">
						<bean:write name="leaveHeaderVO" property="useHours" />
					</td>
					<td class="left">
						<logic:empty name="pm_hide">
							<a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="leaveHeaderVO" property="encodedEmployeeId" />');">
								<bean:write name="leaveHeaderVO" property="employeeId" />
							</a>
						</logic:empty>
						<logic:notEmpty name="pm_hide">
							<bean:write name="leaveHeaderVO" property="employeeId" />
						</logic:notEmpty>
					</td>
					<td class="left"><bean:write name="leaveHeaderVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="leaveHeaderVO" property="employeeNameEN" /></td>
					<td class="left">
						<logic:empty name="pm_hide">
							<a class="pm_hide" onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="leaveHeaderVO" property="encodedContractId" />');">
								<bean:write name="leaveHeaderVO" property="contractId" />
							</a>	
						</logic:empty>
						<logic:notEmpty name="pm_hide">
							<bean:write name="leaveHeaderVO" property="contractId" />
						</logic:notEmpty>
					</td>
					<logic:equal name="role" value="1">
						<td class="left">
							<a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="leaveHeaderVO" property="encodedClientId" />');">
								<bean:write name="leaveHeaderVO" property="clientId" />
							</a>	
						</td>
						<td class="left"><bean:write name="leaveHeaderVO" property="clientName" /></td>
					</logic:equal>
					<td class="left">
						<bean:write name="leaveHeaderVO" property="decodeStatus" />
						<logic:equal name="leaveHeaderVO" property="status" value="1" >
							&nbsp;&nbsp;
							<kan:auth right="submit" action="<%=LeaveHeaderAction.accessAction%>">
								<a onclick="submit_object('<bean:write name="leaveHeaderVO" property="encodedId"/>','<bean:write name="leaveHeaderVO" property="employeeId"/>','<bean:write name="leaveHeaderVO" property="contractId"/>','<bean:write name="leaveHeaderVO" property="estimateStartDate"/>')"><bean:message bundle="public" key="button.submit" /></a>
							</kan:auth>
						</logic:equal>
						<logic:equal name="leaveHeaderVO" property="status" value="4" >
							&nbsp;&nbsp;
							<kan:auth right="submit" action="<%=LeaveHeaderAction.accessAction%>">
								<a onclick="submit_object('<bean:write name="leaveHeaderVO" property="encodedId"/>','<bean:write name="leaveHeaderVO" property="employeeId"/>','<bean:write name="leaveHeaderVO" property="contractId"/>','<bean:write name="leaveHeaderVO" property="estimateStartDate"/>')"><bean:message bundle="public" key="button.submit" /></a>
							</kan:auth>
						</logic:equal>
						<bean:write name="leaveHeaderVO" filter="false" property="isLink" />
					</td>
					<logic:notEqual name="role" value="5">
						<td class="left"><bean:write name="leaveHeaderVO" property="decodeModifyBy" /></td>
					</logic:notEqual>
					<td class="left"><bean:write name="leaveHeaderVO" property="decodeModifyDate" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="leaveHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="15" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="leaveHeaderHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="leaveHeaderHolder" property="indexStart" /> - <bean:write name="leaveHeaderHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchLeave_form', null, '<bean:write name="leaveHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchLeave_form', null, '<bean:write name="leaveHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchLeave_form', null, '<bean:write name="leaveHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchLeave_form', null, '<bean:write name="leaveHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="leaveHeaderHolder" property="realPage" />/<bean:write name="leaveHeaderHolder" property="pageCount" /> </label>&nbsp;
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage_render" class="forwardPage_render" style="width:23px;" value="<bean:write name='leaveHeaderHolder' property='realPage' />" onkeydown="if(event.keyCode == 13){pageForward();}" />页</label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>