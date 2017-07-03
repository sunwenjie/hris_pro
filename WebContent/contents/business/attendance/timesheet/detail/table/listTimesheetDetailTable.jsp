<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder timesheetDetailHolder = ( PagedListHolder ) request.getAttribute( "timesheetDetailHolder" );
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="business" key="business.ts.detail.day" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="business" key="business.ts.detail.day.type" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="business" key="business.ts.work.hours" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="business" key="business.ts.detail.work.time" />
			</th>
			<th style="width: 40%" class="header-nosort">
				<bean:message bundle="public" key="public.note" />
			</th>
			<th style="width: 20%" class="header-nosort">
				<bean:message bundle="public" key="public.oper" />
			</th>
		</tr>
	</thead>
		<logic:notEqual name="timesheetDetailHolder" property="holderSize" value="0">
			<tbody id="mybody">
				<logic:iterate id="timesheetDetailVO" name="timesheetDetailHolder" property="source" indexId="number">
					<tr class='<%= number % 2 == 1 ? "odd" : "even" %>' >
						<td class="left"><bean:write name="timesheetDetailVO" property="day"/></td>
						<td class="left">
							<input type="hidden" id="dayType_<%=number %>" value="<bean:write name="timesheetDetailVO" property="dayType"/>" />
							<logic:notEmpty name="timesheetDetailVO" property="dayType">
								<input type="hidden" name="dayTypeArray" id="dayType_<bean:write name="timesheetDetailVO" property="detailId"/>" value="<bean:write name="timesheetDetailVO" property="detailId"/>" />
								<select id="dayTypeArray_<bean:write name="timesheetDetailVO" property="detailId"/>">
									<logic:iterate id="mappingVO" name="timesheetDetailVO" property="dayTypies" >
										<logic:notEqual name="mappingVO" property="mappingId" value="0">
											<logic:equal name="mappingVO" property="mappingId" value="${timesheetDetailVO.dayType}">
												<option value="<bean:write name="mappingVO" property="mappingId" />" selected="selected">
													<bean:write name="mappingVO" property="mappingValue" />
												</option>
											</logic:equal>
											<logic:notEqual name="mappingVO" property="mappingId" value="${timesheetDetailVO.dayType}">
												<option value="<bean:write name="mappingVO" property="mappingId" />">
													<bean:write name="mappingVO" property="mappingValue" />
												</option>
											</logic:notEqual>
										</logic:notEqual>
									</logic:iterate>
								</select>
							</logic:notEmpty>
						</td>
						<td class="left">
							<logic:notEmpty name="timesheetDetailVO" property="dayType">
								<%-- 薪酬方案基本工资按小时 --%>
								<logic:notEmpty name="byHour">
									<%-- 工作日 --%>
									<logic:equal name="timesheetDetailVO" property="dayType" value="1">
										<input type="hidden" name="workHoursArray" id="workHours_<bean:write name="timesheetDetailVO" property="detailId"/>" value="<bean:write name="timesheetDetailVO" property="detailId"/>" />
										<input type="text" class="small-ex workHoursArray_<bean:write name="timesheetDetailVO" property="detailId"/>" id="workHoursArray_<bean:write name="timesheetDetailVO" property="detailId"/>" value="<bean:write name="timesheetDetailVO" property="workHours"/>"/>
									</logic:equal>
									<%-- 休息日 --%>
									<logic:notEqual name="timesheetDetailVO" property="dayType" value="1">
										<bean:write name="timesheetDetailVO" property="workHours"/>
									</logic:notEqual>
								</logic:notEmpty>	
								<logic:empty name="byHour">
									<input type="hidden" name="workHoursArray" id="workHours_<bean:write name="timesheetDetailVO" property="detailId"/>" value="<bean:write name="timesheetDetailVO" property="detailId"/>" />
									<input type="text" class="small-ex workHoursArray_<bean:write name="timesheetDetailVO" property="detailId"/>" id="workHoursArray_<bean:write name="timesheetDetailVO" property="detailId"/>" value="<bean:write name="timesheetDetailVO" property="workHours"/>"/>
								</logic:empty>	
							</logic:notEmpty>
						</td>
						<td class="left"><bean:write name="timesheetDetailVO" property="decodeWorkPeriod"/></td>
						<td class="left"><bean:write name="timesheetDetailVO" property="description"/></td>
						<td class="left">
							<%
								String role = BaseAction.getRole(request,response);
								if (!KANConstants.ROLE_CLIENT.equals(role)){
							%>
							<logic:equal name="timesheetHeaderForm" property="status" value="1">
								<%-- 工作日 --%>
								<logic:equal name="timesheetDetailVO" property="dayType" value="1">
									<a onclick="to_otNew('<bean:write name="timesheetHeaderForm" property="headerId" />','<bean:write name="timesheetHeaderForm" property="employeeId" />','<bean:write name="timesheetHeaderForm" property="clientId"/>','<bean:write name="timesheetHeaderForm" property="contractId"/>','<bean:write name="timesheetDetailVO" property="day"/>')"><bean:message bundle="public" key="link.ot.apply" /></a>&nbsp;&nbsp;
									<a onclick="to_leaveNew('<bean:write name="timesheetHeaderForm" property="headerId" />','<bean:write name="timesheetHeaderForm" property="employeeId" />','<bean:write name="timesheetHeaderForm" property="clientId"/>','<bean:write name="timesheetHeaderForm" property="contractId"/>','<bean:write name="timesheetDetailVO" property="day"/>');"><bean:message bundle="public" key="link.leave.apply" /></a>
								</logic:equal>
								<%-- 休息日 --%>
								<logic:equal name="timesheetDetailVO" property="dayType" value="2">
									<a onclick="to_otNew('<bean:write name="timesheetHeaderForm" property="headerId" />','<bean:write name="timesheetHeaderForm" property="employeeId" />','<bean:write name="timesheetHeaderForm" property="clientId"/>','<bean:write name="timesheetHeaderForm" property="contractId"/>','<bean:write name="timesheetDetailVO" property="day"/>');"><bean:message bundle="public" key="link.ot.apply" /></a>
								</logic:equal>
								<%-- 节假日 --%>
								<logic:equal name="timesheetDetailVO" property="dayType" value="3">
									<a onclick="to_otNew('<bean:write name="timesheetHeaderForm" property="headerId" />','<bean:write name="timesheetHeaderForm" property="employeeId" />','<bean:write name="timesheetHeaderForm" property="clientId"/>','<bean:write name="timesheetHeaderForm" property="contractId"/>','<bean:write name="timesheetDetailVO" property="day"/>');"><bean:message bundle="public" key="link.ot.apply" /></a>
								</logic:equal>
							</logic:equal>
							<%
								}
							%>
						</td>
					</tr>
				</logic:iterate>
			</tbody>
		</logic:notEqual>
		<logic:present name="timesheetDetailHolder">
			<tfoot>
				<tr class="total">
				  	<td  colspan="6" class="left"> 
					  	<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="timesheetDetailHolder" property="holderSize" /></label>
					  	&nbsp;
					  	&nbsp;
					</td>					
			   </tr>
			</tfoot>
		</logic:present>
</table>