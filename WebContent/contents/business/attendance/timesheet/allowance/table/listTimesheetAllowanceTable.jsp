<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.TimesheetBatchAction"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder timesheetAllowanceHolder = (PagedListHolder) request.getAttribute("timesheetAllowanceHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="business" key="business.ts.allowance.item.id" />
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="business" key="business.ts.allowance.item.no" />
			</th>
			<th style="width: 25%" class="header-nosort">
				<bean:message bundle="business" key="business.ts.allowance.item.name.cn" />
			</th>
			<th style="width: 25%" class="header-nosort">
				<bean:message bundle="business" key="business.ts.allowance.item.name.en" />
			</th>
			<th style="width: 20%" class="header-nosort">
				<bean:message bundle="business" key="business.ts.allowance.money" />
			</th>
		</tr>
	</thead>
		<logic:notEqual name="timesheetAllowanceHolder" property="holderSize" value="0">
			<tbody>
				<logic:iterate id="timesheetAllowanceVO" name="timesheetAllowanceHolder" property="source" indexId="number">
					<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
						<td class="left">
							<kan:auth right="modify" action="<%=TimesheetBatchAction.accessActionInHouse%>">
								<logic:equal name="timesheetHeaderForm" property="status" value="1">
									<a onclick="to_timesheetAllowanceModify_ajax('<bean:write name="timesheetAllowanceVO" property="encodedId"/>');">
								</logic:equal>
								<logic:equal name="timesheetHeaderForm" property="status" value="4">
									<a onclick="to_timesheetAllowanceModify_ajax('<bean:write name="timesheetAllowanceVO" property="encodedId"/>');">
								</logic:equal>
							</kan:auth>
									<bean:write name="timesheetAllowanceVO" property="itemId"/>
							<kan:auth right="modify" action="<%=TimesheetBatchAction.accessActionInHouse%>">
								<logic:equal name="timesheetHeaderForm" property="status" value="1">
									</a>
								</logic:equal>
								<logic:equal name="timesheetHeaderForm" property="status" value="4">
									</a>
								</logic:equal>
							</kan:auth>
						</td>
						<td class="left"><bean:write name="timesheetAllowanceVO" property="itemNo"/></td>
						<td class="left"><bean:write name="timesheetAllowanceVO" property="itemNameZH"/></td>
						<td class="left"><bean:write name="timesheetAllowanceVO" property="itemNameEN"/></td>
						<td class="left">
							<input type="hidden" name="baseArray" id="base_<bean:write name="timesheetAllowanceVO" property="allowanceId"/>" value="<bean:write name="timesheetAllowanceVO" property="allowanceId"/>"/>
							<input type="text" id="baseArray_<bean:write name="timesheetAllowanceVO" property="allowanceId"/>" class="small-ex baseArray_<bean:write name="timesheetAllowanceVO" property="allowanceId"/>"  value="<bean:write name="timesheetAllowanceVO" property="base"/>"/>
						</td>
					</tr>
				</logic:iterate>
			</tbody>
		</logic:notEqual>
		<logic:present name="timesheetAllowanceHolder">
			<tfoot>
				<tr class="total">
				  	<td  colspan="6" class="left"> 
					  	<label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="timesheetAllowanceHolder" property="holderSize" /></label>
					  	&nbsp;
					  	&nbsp;
					</td>					
			   </tr>
			</tfoot>
		</logic:present>
</table>