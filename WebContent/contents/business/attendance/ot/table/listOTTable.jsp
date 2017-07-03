<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.OTHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder otHeaderHolder = ( PagedListHolder ) request.getAttribute( "otHeaderHolder" );
	if( KANUtil.filterEmpty( request.getAttribute( "definedMessage" ) ) == null )
	{
	   request.setAttribute( "definedMessage", "" );
	}
%>

<logic:notEmpty name="MESSAGE">
<div id="_USER_DEFINE_MSG" class="message <bean:write name="MESSAGE_CLASS" /> fadable" style="display:none;"><bean:write name="MESSAGE" /></div>
</logic:notEmpty>

<input type="hidden" name="definedMessage" id="definedMessage" value="<bean:write name="definedMessage" />" />
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 5%" class="header <%=otHeaderHolder.getCurrentSortClass("otHeaderId")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'otHeaderId', '<%=otHeaderHolder.getNextSortOrder("otHeaderId")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ot.id" /></a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "8" : "10"%>%" class="header <%=otHeaderHolder.getCurrentSortClass("estimateStartDate")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'estimateStartDate', '<%=otHeaderHolder.getNextSortOrder("estimateStartDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.start.time" /></a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "8" : "10"%>%" class="header <%=otHeaderHolder.getCurrentSortClass("estimateEndDate")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'estimateEndDate', '<%=otHeaderHolder.getNextSortOrder("estimateEndDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.end.time" /></a>
			</th>
			<th style="width: 5%" class="header-nosort"><bean:message bundle="public" key="public.note" /></th>
			<th style="width: 5%" class="header-nosort"><bean:message bundle="business" key="business.ot.hours" /></th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "8" : "10"%>%" class="header <%=otHeaderHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'employeeId', '<%=otHeaderHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
				    <logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "10" : "15"%>%" class="header <%=otHeaderHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'employeeNameZH', '<%=otHeaderHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "10" : "15"%>%" class="header <%=otHeaderHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'employeeNameEN', '<%=otHeaderHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "8" : "10"%>%" class="header <%=otHeaderHolder.getCurrentSortClass("contractId")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'contractId', '<%=otHeaderHolder.getNextSortOrder("contractId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
				</a>
			</th>
			<logic:equal name="role" value="1">
				<th style="width: 10%" class="header <%=otHeaderHolder.getCurrentSortClass("clientId")%>">
					<a onclick="submitForm('searchOT_form', null, null, 'clientId', '<%=otHeaderHolder.getNextSortOrder("clientId")%>', 'tableWrapper');">客户ID</a>
				</th>
				<th style="width: 10%" class="header-nosort">
					客户名称
				</th>
			</logic:equal>
			<logic:equal name="accountId" value="100056">
			<th style="width: 5%" class="header <%=otHeaderHolder.getCurrentSortClass("specialOT")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'specialOT', '<%=otHeaderHolder.getNextSortOrder("specialOT")%>', 'tableWrapper');"><bean:message bundle="business" key="business.ot.special.overtime" /></a>
			</th>
			</logic:equal>
			
			<th style="width: 5%" class="header <%=otHeaderHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'status', '<%=otHeaderHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			<logic:notEqual name="role" value="5">
				<th style="width: 5%" class="header <%=otHeaderHolder.getCurrentSortClass("modifyBy")%>">
					<a onclick="submitForm('searchOT_form', null, null, 'modifyBy', '<%=otHeaderHolder.getNextSortOrder("modifyBy")%>', 'tableWrapper');"><bean:message bundle="public" key="public.modify.by" /></a>
				</th>
			</logic:notEqual>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "8" : "10"%>%" class="header <%=otHeaderHolder.getCurrentSortClass("modifyDate")%>">
				<a onclick="submitForm('searchOT_form', null, null, 'modifyDate', '<%=otHeaderHolder.getNextSortOrder("modifyDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.modify.date" /></a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="otHeaderHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="otHeaderVO" name="otHeaderHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="otHeaderVO" property="status" value="1">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="otHeaderVO" property="otHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="otHeaderVO" property="encodedId"/>" />
						</logic:equal>
						<logic:equal name="otHeaderVO" property="status" value="3">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="otHeaderVO" property="otHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="otHeaderVO" property="encodedId"/>" />
						</logic:equal>
						<logic:equal name="otHeaderVO" property="status" value="6">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="otHeaderVO" property="otHeaderId"/>" name="chkSelectRow[]" value="<bean:write name="otHeaderVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('otHeaderAction.do?proc=to_objectModify&id=<bean:write name="otHeaderVO" property="encodedId"/>');"><bean:write name="otHeaderVO" property="otHeaderId" /></a>
						<logic:empty name="otHeaderVO" property="actualStartDate"><span class="highlight"><%=request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "（预）" : "(Estimate)" %></span></logic:empty>
					</td>
					<td class="left">
						<logic:notEqual name="otHeaderVO" property="dataFrom" value="2">
							<bean:write name="otHeaderVO" property="estimateStartDate" />
						</logic:notEqual>
						<logic:equal name="otHeaderVO" property="dataFrom" value="2">
							-- -- 
						</logic:equal>
					</td>
					<td class="left">
						<logic:notEqual name="otHeaderVO" property="dataFrom" value="2">
							<bean:write name="otHeaderVO" property="estimateEndDate" />
						</logic:notEqual>
						<logic:equal name="otHeaderVO" property="dataFrom" value="2">
							-- -- 
						</logic:equal>
					</td>
					<td class="left"><bean:write name="otHeaderVO" property="description" /></td>
					<td class="right">
						<logic:equal name="otHeaderVO" property="status" value="1">
							<bean:write name="otHeaderVO" property="estimateHours" />
						</logic:equal>
						<logic:equal name="otHeaderVO" property="status" value="2">
							<bean:write name="otHeaderVO" property="estimateHours" />
						</logic:equal>
						<logic:equal name="otHeaderVO" property="status" value="3">
							<bean:write name="otHeaderVO" property="estimateHours" />
						</logic:equal>
						<logic:equal name="otHeaderVO" property="status" value="4">
							<bean:write name="otHeaderVO" property="estimateHours" />
						</logic:equal>
						<logic:equal name="otHeaderVO" property="status" value="5">
							<bean:write name="otHeaderVO" property="actualHours" />
						</logic:equal>
						<logic:equal name="otHeaderVO" property="status" value="6">
							<logic:notEmpty name="otHeaderVO" property="actualHours" >
								<bean:write name="otHeaderVO" property="actualHours" />
							</logic:notEmpty>
							<logic:empty name="otHeaderVO" property="actualHours" >
								<bean:write name="otHeaderVO" property="estimateHours" />
							</logic:empty>
						</logic:equal>
						<logic:equal name="otHeaderVO" property="status" value="7">
							<logic:notEmpty name="otHeaderVO" property="actualHours" >
								<bean:write name="otHeaderVO" property="actualHours" />
							</logic:notEmpty>
							<logic:empty name="otHeaderVO" property="actualHours" >
								<bean:write name="otHeaderVO" property="estimateHours" />
							</logic:empty>
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="otHeaderVO" property="encodedEmployeeId" />');"><bean:write name="otHeaderVO" property="employeeId" /></a>
					</td>
					<td class="left"><bean:write name="otHeaderVO" property="employeeNameZH" /></td>
					<td class="left"><bean:write name="otHeaderVO" property="employeeNameEN" /></td>
					<td class="left">
						<a onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="otHeaderVO" property="encodedContractId" />');">
							<bean:write name="otHeaderVO" property="contractId" />
						</a>	
					</td>
					<logic:equal name="role" value="1">
						<td class="left">
							<a onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="otHeaderVO" property="encodedClientId" />');"><bean:write name="otHeaderVO" property="clientId" /></a>
						</td>
						<td class="left"><bean:write name="otHeaderVO" property="clientName" /></td>
					</logic:equal>
					
					<logic:equal name="accountId" value="100056">
						<td class="left"><bean:write name="otHeaderVO" property="decodeSpecialOT" /></td>
					</logic:equal>
					<td class="left">
						<bean:write name="otHeaderVO" property="decodeStatus" />
						<logic:equal name="otHeaderVO" property="status" value="1" >
							&nbsp;&nbsp;
							<kan:auth right="submit" action="<%=OTHeaderAction.accessAction%>">
								<a onclick="submit_object('<bean:write name="otHeaderVO" property="encodedId"/>','submitObject','<bean:write name="otHeaderVO" property="employeeId"/>','<bean:write name="otHeaderVO" property="contractId"/>','<bean:write name="otHeaderVO" property="estimateStartDate"/>')"><bean:message bundle="public" key="button.submit" /></a>
							</kan:auth>
						</logic:equal>
						<logic:equal name="otHeaderVO" property="status" value="2" >
							&nbsp;&nbsp;<img src='images/magnifer.png' onclick=popupWorkflow('<bean:write name="otHeaderVO" property="workflowId"/>'); />
						</logic:equal>
						<logic:notEqual name="role" value="5">
							<logic:equal name="otHeaderVO" property="status" value="3" >
								&nbsp;&nbsp;
								<kan:auth right="confirm" action="<%=OTHeaderAction.accessAction%>">
									<a onclick="submit_object('<bean:write name="otHeaderVO" property="encodedId"/>','confirmObject','<bean:write name="otHeaderVO" property="employeeId"/>','<bean:write name="otHeaderVO" property="contractId"/>','<bean:write name="otHeaderVO" property="estimateStartDate"/>')"><bean:message bundle="public" key="button.confirm" /></a>
								</kan:auth>
							</logic:equal>
						</logic:notEqual>
						<logic:equal name="otHeaderVO" property="status" value="4" >
							&nbsp;&nbsp;<img src='images/magnifer.png' onclick=popupWorkflow('<bean:write name="otHeaderVO" property="workflowId"/>'); />
						</logic:equal>
						<logic:equal name="otHeaderVO" property="status" value="6" >
							&nbsp;&nbsp;
							<kan:auth right="submit" action="<%=OTHeaderAction.accessAction%>">
								<a onclick="submit_object('<bean:write name="otHeaderVO" property="encodedId"/>','submitObject','<bean:write name="otHeaderVO" property="employeeId"/>','<bean:write name="otHeaderVO" property="contractId"/>','<bean:write name="otHeaderVO" property="estimateStartDate"/>')"><bean:message bundle="public" key="button.submit" /></a>
							</kan:auth>
						</logic:equal>
					</td>
					<logic:notEqual name="role" value="5">
						<td class="left"><bean:write name="otHeaderVO" property="decodeModifyBy" /></td>
					</logic:notEqual>
					<td class="left"><bean:write name="otHeaderVO" property="decodeModifyDate" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="otHeaderHolder">
		<tfoot>
			<tr class="total">
				<td colspan="15" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="otHeaderHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="otHeaderHolder" property="indexStart" /> - <bean:write name="otHeaderHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchOT_form', null, '<bean:write name="otHeaderHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchOT_form', null, '<bean:write name="otHeaderHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchOT_form', null, '<bean:write name="otHeaderHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchOT_form', null, '<bean:write name="otHeaderHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="otHeaderHolder" property="realPage" />/<bean:write name="otHeaderHolder" property="pageCount" /> </label>&nbsp;
					<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage_render" class="forwardPage_render" style="width:23px;" value="<bean:write name='otHeaderHolder' property='realPage' />" onkeydown="if(event.keyCode == 13){pageForward();}" />页</label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>