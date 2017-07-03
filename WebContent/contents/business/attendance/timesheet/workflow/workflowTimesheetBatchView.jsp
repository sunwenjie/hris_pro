<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.hro.domain.biz.attendance.TimesheetBatchVO"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final boolean inHouse = request.getAttribute( "role" ).equals( "2" );
%>

<logic:equal  name="historyVO" property="rightId" value="3">
	<div id="tabContent1" class="kantab">
		<ol id="detailOL" class="auto">
			<%
				final TimesheetBatchVO passObject = ( TimesheetBatchVO )request.getAttribute( "passObject" );
				final TimesheetBatchVO originalObject = ( TimesheetBatchVO )request.getAttribute( "originalObject" );
			
				out.print(KANUtil.getCompareHTML( passObject.getDescription(), originalObject.getDescription(), inHouse ? "员工" : "雇员" + "列表", "textarea"));
				
			%>
		</ol>
	</div>
	
	<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		
	</ol>
</div>
</logic:equal>


<!-- 非修改类型的审批流程 -->
<logic:notEqual name="historyVO" property="rightId" value="3">
	<div id="tabContent1"  class="kantab"  >
		<!-- 审批批次信息 -->
		<logic:equal name="historyVO" property="serviceBean" value="timesheetBatchService">
			<ol class="auto">
				<li>
					<label>
						<logic:equal name="role" value="1"><bean:message bundle="business" key="business.ts.workflow.employee1.list" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="business" key="business.ts.workflow.employee2.list" /></logic:equal>
					</label>
					<textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>
						<logic:equal name="role" value="1"><bean:message bundle="business" key="business.ts.workflow.employee1.number" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="business" key="business.ts.workflow.employee2.number" /></logic:equal>
					</label>
					<input type="text" disabled="disabled" value="<bean:write name="passObject" property="countHeaderId"/>" />
				</li>
			</ol>
			<ol class="auto">
				<li><label><bean:message bundle="business" key="business.ts.workflow.work.hours" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="totalWortHours"/>" /></li>
				<li><label><bean:message bundle="business" key="business.ts.workflow.leave.hours" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="totalLeaveHours"/>" /></li>
				<li><label><bean:message bundle="business" key="business.ts.workflow.ot.hours" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="totalOTHours"/>" /></li>
			</ol>
			<ol class="auto">	
				<li><label><bean:message bundle="business" key="business.ts.month" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="monthly"/>" /></li>
			</ol>	
			<ol class="auto">	
				<li><label><bean:message bundle="public" key="public.status" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="decodeStatus"/>" /></li>
			</ol>
		</logic:equal>
		
		<!-- 审批考勤表信息 -->
		<logic:equal name="historyVO" property="serviceBean" value="timesheetHeaderService">
			<ol class="auto">
				<li>
					<label>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name" /></logic:equal>
					</label>
					<input type="text" disabled="disabled" value="<bean:write name="passObject" property="employeeNameZH"/>" />
				</li>
				<li>
					<label>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.no" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.no" /></logic:equal>
					</label>
					<input type="text" disabled="disabled" value="<bean:write name="passObject" property="employeeNo"/>" />
				</li>
			</ol>
			<ol class="auto">
				<li><label><bean:message bundle="business" key="business.ts.month" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="monthly"/>" /></li>
			</ol>
			<ol class="auto">
				<li><label><bean:message bundle="business" key="business.ts.ts.start" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="startDate"/>" /></li>
				<li><label><bean:message bundle="business" key="business.ts.ts.end" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="endDate"/>" /></li>
			</ol>
			<ol class="auto">
				<li><label><bean:message bundle="business" key="business.ts.work.hours" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="totalWorkHours"/>" /></li>
				<li><label><bean:message bundle="business" key="business.ts.work.days" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="totalWorkDays"/>" /></li>
				<li><label><bean:message bundle="business" key="business.ts.toal.full.hours" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="totalFullHours"/>" /></li>
				<li><label><bean:message bundle="business" key="business.ts.toal.full.days" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="totalFullDays"/>" /></li>
				<li><label><bean:message bundle="business" key="business.ts.need.audit" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="decodeNeedAudit"/>" /></li>
				<li><label><bean:message bundle="business" key="business.ts.is.normal" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="decodeIsNormal"/>" /></li>
			</ol>	
			<ol class="auto">
				<li><label><bean:message bundle="public" key="public.note" /></label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
				<li><label><bean:message bundle="public" key="public.status" /></label><input type="text" disabled="disabled" value="<bean:write name="passObject" property="decodeStatus"/>" /></li>
			</ol>	
		</logic:equal>
	</div>
</logic:notEqual>