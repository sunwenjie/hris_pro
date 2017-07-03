<%@page import="com.kan.hro.domain.biz.attendance.LeaveHeaderVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
		<%
			final boolean inHouse = request.getAttribute( "role" ).equals( "2" );
		%>

<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- 修改类型的审批流程 --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			LeaveHeaderVO passObject = ( LeaveHeaderVO )request.getAttribute("passObject");
			LeaveHeaderVO originalObject = ( LeaveHeaderVO )request.getAttribute("originalObject");
			out.print(KANUtil.getCompareHTML(passObject.getEmployeeId(),originalObject.getEmployeeId(), inHouse ? "员工" : "雇员" + "ID"));
			if( request.getLocale().getLanguage().equalsIgnoreCase("zh") )
			{
			   out.print(KANUtil.getCompareHTML(passObject.getEmployeeNameZH(),originalObject.getEmployeeNameZH(), inHouse ? "员工" : "雇员" + "姓名"));
			}
			else
			{
			   out.print(KANUtil.getCompareHTML(passObject.getEmployeeNameZH(),originalObject.getEmployeeNameEN(), inHouse ? "员工" : "雇员" + "姓名"));
			}
			out.print(KANUtil.getCompareHTML(passObject.getEmployeeNo(),originalObject.getEmployeeNo(), inHouse ? "员工" : "雇员" + "编号"));
			out.print(KANUtil.getCompareHTML(passObject.getCertificateNumber(),originalObject.getCertificateNumber(),"证件号码"));
			out.print(KANUtil.getCompareHTML(passObject.getCertificateNumber(),originalObject.getDecodeItemId(),"假期类别"));

			out.print(KANUtil.getCompareHTML(passObject.getEstimateStartDate(),originalObject.getEstimateStartDate(),"开始时间"));
			out.print(KANUtil.getCompareHTML(passObject.getEstimateEndDate(),originalObject.getEstimateEndDate(),"结束时间"));
			out.print(KANUtil.getCompareHTML(passObject.getEstimateBenefitHours(),originalObject.getEstimateBenefitHours(),"休假时间（小时）"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeStatus(),originalObject.getDecodeStatus(),"状态"));
			out.print(KANUtil.getCompareHTML(passObject.getDescription(),originalObject.getDescription(),"描述","textarea"));
		%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label><%=inHouse ? "员工" : "雇员"%>ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeId"/>' /></li>
		<li><label><%=inHouse ? "员工" : "雇员"%>姓名</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeName"/>' /></li>
		<li><label><%=inHouse ? "员工" : "雇员"%>编号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeNo"/>' /></li>
		<li><label>证件号码</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateNumber"/>' /></li>
		<li><label>开始时间</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="estimateStartDate"/>' /></li>
		<li><label>结束时间</label><input type="text" disabled="disabled"   value='<bean:write name="originalObject" property="estimateEndDate"/>' /></li>
	</ol>	
	<ol id="detailOL" class="auto">
		<li><label>假期类别</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeItemId"/>' /></li>
	</ol>	
	<ol id="detailOL" class="auto">		
		<li><label>休假时间（福利）</label><input type="text" disabled="disabled"   value='<bean:write name="originalObject" property="estimateBenefitHours"/>' /></li>
		<li><label>休假时间（法定）</label><input type="text" disabled="disabled"   value='<bean:write name="originalObject" property="estimateLegalHours"/>' /></li>
<%-- 		<li><label>状态</label><input type="text" disabled="disabled"   value='<bean:write name="originalObject" property="decodeStatus"/>' /></li> --%>
		<li><label>描述</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
	</ol>
</div>
</logic:equal>

<%-- 非修改类型的审批流程 --%>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
	<div id="tabContent1"  class="kantab" >
		<ol id="detailOL" class="auto">
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
					<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.id" /></logic:notEqual>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="employeeId"/>' /></li>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name" /></logic:equal>
					<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.name" /></logic:notEqual>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="employeeNameZH"/>' /></li>
			<li>
				<label>
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.no" /></logic:equal>
					<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.no" /></logic:notEqual>
				</label>
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="employeeNo"/>' /></li>
			<li><label><bean:message bundle="business" key="business.leave.type" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeItemId"/>' /></li>
		</ol>	
		<!-- 请假审批 -->
		<logic:empty name="passObject" property="actualStartDate">
		<logic:empty name="passObject" property="actualEndDate">
			<ol id="detailOL" class="auto">		
				<li><label><bean:message bundle="public" key="public.start.time" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="estimateStartDate"/>' /></li>
				<li><label><bean:message bundle="public" key="public.end.time" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="estimateEndDate"/>' /></li>
				<li><label><bean:message bundle="business" key="business.leave.legal.hours" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="estimateLegalHours"/>' /></li>
				<li><label><bean:message bundle="business" key="business.leave.benefit.hours" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="estimateBenefitHours"/>' /></li>
			</ol>
		</logic:empty>
		</logic:empty>
		
		<!-- 销假审批 -->
		<logic:notEmpty name="passObject" property="actualStartDate">
		<logic:notEmpty name="passObject" property="actualEndDate">
			<ol id="detailOL" class="auto">	
				<li><label><bean:message bundle="public" key="public.start.time" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="estimateStartDate"/>' /></li>
				<li><label><bean:message bundle="public" key="public.end.time" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="estimateEndDate"/>' /></li>
				<li><label><bean:message bundle="business" key="business.leave.legal.hours" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="estimateLegalHours"/>' /></li>
				<li><label><bean:message bundle="business" key="business.leave.benefit.hours" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="estimateBenefitHours"/>' /></li>
				<li><label><bean:message bundle="public" key="public.start.time" /><span class="highlight"><bean:message bundle="business" key="business.leave.retrieve.tips" /></span></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="actualStartDate"/>' /></li>
				<li><label><bean:message bundle="public" key="public.end.time" /><span class="highlight"><bean:message bundle="business" key="business.leave.retrieve.tips" /></span></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="actualEndDate"/>' /></li>
				<li><label><bean:message bundle="business" key="business.leave.legal.hours" /><span class="highlight"><bean:message bundle="business" key="business.leave.retrieve.tips" /></span></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="actualLegalHours"/>' /></li>
				<li><label><bean:message bundle="business" key="business.leave.benefit.hours" /><span class="highlight"><bean:message bundle="business" key="business.leave.retrieve.tips" /></span></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="actualBenefitHours"/>' /></li>
			</ol>
		</logic:notEmpty>
		</logic:notEmpty>
		<ol id="detailOL" class="auto">	
			<li><label><bean:message bundle="public" key="public.description" /></label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
<%-- 			<li><label><bean:message bundle="public" key="public.status" /></label><input type="text" disabled="disabled"   value='<bean:write name="passObject" property="decodeStatus"/>' /></li> --%>
		</ol>
		<br>
		<div id="special_info_tab" >
		
		</div>
	</div>
</logic:notEqual>

<script type="text/javascript">
	// 加载请假记录
	loadHtml('#special_info_tab', 'leaveHeaderAction.do?proc=list_special_info_html&leaveHeaderId=<%=( ( LeaveHeaderVO )request.getAttribute( "passObject" ) ).getLeaveHeaderId()%>&contractId=<%=( ( LeaveHeaderVO )request.getAttribute( "passObject" ) ).getContractId()%>&noTab=true' , false, null); 
</script>		