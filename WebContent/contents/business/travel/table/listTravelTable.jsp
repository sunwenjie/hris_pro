<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@page import="com.kan.hro.web.actions.biz.travel.TravelAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final PagedListHolder travelHolder = ( PagedListHolder ) request.getAttribute( "travelHolder" );
%>

<logic:notEmpty name="MESSAGE">
<div id="_USER_DEFINE_MSG" class="message <bean:write name="MESSAGE_CLASS" /> fadable" style="display:none;"><bean:write name="MESSAGE" /></div>
</logic:notEmpty>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col"  style="width:2%;">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th style="width: 4%" class="header <%=travelHolder.getCurrentSortClass("travelId")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'travelId', '<%=travelHolder.getNextSortOrder("travelId")%>', 'tableWrapper');">²îÂÃID</a>
			</th>
	
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "12"%>%" class="header <%=travelHolder.getCurrentSortClass("startDate")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'startDate', '<%=travelHolder.getNextSortOrder("startDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.start.time" /></a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "12"%>%" class="header <%=travelHolder.getCurrentSortClass("endDate")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'endDate', '<%=travelHolder.getNextSortOrder("endDate")%>', 'tableWrapper');"><bean:message bundle="public" key="public.end.time" /></a>
			</th>
			<th style="width: 6%" class="header <%=travelHolder.getCurrentSortClass("employeeId")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'employeeId', '<%=travelHolder.getNextSortOrder("employeeId")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
				    <logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "12"%>%" class="header <%=travelHolder.getCurrentSortClass("employeeNameZH")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'employeeNameZH', '<%=travelHolder.getNextSortOrder("employeeNameZH")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
				</a>
			</th>
			<th style="width: <%=request.getAttribute( "role" ).equals( "1" ) ? "7" : "12"%>%" class="header <%=travelHolder.getCurrentSortClass("employeeNameEN")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'employeeNameEN', '<%=travelHolder.getNextSortOrder("employeeNameEN")%>', 'tableWrapper');">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
				</a>
			</th>
			
			
			<th  class="header <%=travelHolder.getCurrentSortClass("description")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'status', '<%=travelHolder.getNextSortOrder("description")%>', 'tableWrapper');"><bean:message bundle="public" key="public.description" /></a>
			</th>
			
			
			<th style="width: 7%" class="header <%=travelHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('searchLeave_form', null, null, 'status', '<%=travelHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			
			
		
		</tr>
	</thead>
	<logic:notEqual name="travelHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="travelVO" name="travelHolder" property="source" indexId="number">
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td>
						<logic:equal name="travelVO" property="status" value="1" >
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="travelVO" property="travelId"/>" name="chkSelectRow[]" value="<bean:write name="travelVO" property="encodedId"/>" />
						</logic:equal>
						<logic:equal name="travelVO" property="status" value="4" >
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="travelVO" property="travelId"/>" name="chkSelectRow[]" value="<bean:write name="travelVO" property="encodedId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<a onclick="link('travelAction.do?proc=to_objectModify&id=<bean:write name="travelVO" property="encodedId"/>');">
							<bean:write name="travelVO" property="travelId" />
						</a>
					</td>
					<td class="left">
								<bean:write name="travelVO" property="startDate" />
					</td>
					<td class="left">
								<bean:write name="travelVO" property="endDate" />
					</td>
					
					<td class="left">
						<a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="travelVO" property="encodedEmployeeId" />');">
							<bean:write name="travelVO" property="employeeId" />
						</a>
					</td>
					<td class="left"><bean:write name="travelVO" property="nameZH" /></td>
					<td class="left"><bean:write name="travelVO" property="nameEN" /></td>
					
					<td class="left"><bean:write name="travelVO" property="description" /></td>
					<td class="left">
						<bean:write name="travelVO" property="decodeStatus" />
						
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="travelHolder">
		<tfoot>
			<tr class="total">
				<td colspan="15" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="travelHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="travelHolder" property="indexStart" /> - <bean:write name="travelHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchLeave_form', null, '<bean:write name="travelHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchLeave_form', null, '<bean:write name="travelHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchLeave_form', null, '<bean:write name="travelHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchLeave_form', null, '<bean:write name="travelHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="travelHolder" property="realPage" />/<bean:write name="travelHolder" property="pageCount" /> </label>&nbsp;
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />£º<input type="text" id="forwardPage_render" class="forwardPage_render" style="width:23px;" value="<bean:write name='travelHolder' property='realPage' />" onkeydown="if(event.keyCode == 13){pageForward();}" />Ò³</label>&nbsp;
				</td>
			</tr>
		</tfoot>
	</logic:present>
</table>