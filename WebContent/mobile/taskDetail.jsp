<%@page import="com.kan.base.util.KANConstants"%>
<%@page import="com.kan.base.domain.workflow.WorkflowActualVO"%>
<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/kan.js"></script>
<link rel="stylesheet" href="mobile/css/mobile.css" />
<title><bean:message bundle="wx" key="wx.logon.title" /></title>
</head>
<body>
<div id="layout">
<html:form action="workflowActualStepsAction.do?proc=modify_object_mobile" styleClass="actualSteps_form">
		<%= BaseAction.addToken( request ) %>
		<input type="hidden" name="stepId" id="stepId" value="<bean:write name="workflowActualStepsForm" property="stepId" />" />
		<input type="hidden" name="status" id="status" value="" />
		<input type="hidden" name="workflowId" id="workflowId" value="<bean:write name="workflowActualVO" property="workflowId" />"/>
      <div class="biaoti2"><bean:write name="workflowActualVO" property="nameZH" /></div>
	  <div class="shijian"><bean:write name="workflowActualVO" property="decodeCreateDate" /></div>
	  <div class="zhengwen">
	  <!-- 请假 -->
	  <%
	  	final boolean inHouse = BaseAction.getRole(request,null).equals( KANConstants.ROLE_IN_HOUSE );
	  %>
	  <logic:equal value="501" name="workflowActualVO" property="systemModuleId">
	  	<%
	  		if(inHouse&&"zh".equalsIgnoreCase(request.getLocale().getLanguage())){
	  		   out.print("员工ID");
	  		}else if(!inHouse&&"zh".equalsIgnoreCase(request.getLocale().getLanguage())){
	  		   out.print("雇员ID");
	  		}else{
	  		   out.print("EmployeeId");
	  		}
	  	%>
	  		：<bean:write name="passObject" property="employeeId" /><br/>
	  		
	  	<%
	  		if(inHouse&&"zh".equalsIgnoreCase(request.getLocale().getLanguage())){
	  		   out.print("员工姓名");
	  		}else if(!inHouse&&"zh".equalsIgnoreCase(request.getLocale().getLanguage())){
	  		   out.print("雇员姓名");
	  		}else{
	  		   out.print("EmployeeName");
	  		}
	  	%>：<font color="#C30"><bean:write name="passObject" property="employeeNameZH" /></font><br/>
	  	<div style="display: none;">	
	  	<%
	  		if(inHouse&&"zh".equalsIgnoreCase(request.getLocale().getLanguage())){
	  		   out.print("员工编号");
	  		}else if(!inHouse&&"zh".equalsIgnoreCase(request.getLocale().getLanguage())){
	  		   out.print("雇员编号");
	  		}else{
	  		   out.print("EmployeeNO");
	  		}
	  	%>
	  		：<bean:write name="passObject" property="employeeNo" /><br/>
	  	<bean:message bundle='wx' key='wx.task.detail.employee.certificate' />：<bean:write name="passObject" property="certificateNumber" /><br/>
	  	</div>
	  	<bean:message bundle='wx' key='wx.task.detail.item.type' />：<font color="#C30"><bean:write name="passObject" property="decodeItemId" /></font><br/>
	  	<bean:message bundle='wx' key='wx.task.detail.start.date' />：<font color="#C30"><bean:write name="passObject" property="estimateStartDate" /></font><br/>
	  	<bean:message bundle='wx' key='wx.task.detail.end.date' />：<font color="#C30"><bean:write name="passObject" property="estimateEndDate" /></font><br/>
	  	<bean:message bundle='wx' key='wx.task.detail.estimate.benefit.hours' />：<bean:write name="passObject" property="estimateBenefitHours" /><br/>
	  	<%-- <bean:message bundle='wx' key='wx.task.detail.estimate.legal.hours' />：<bean:write name="passObject" property="estimateLegalHours" /><br/> --%>
	  	<logic:notEmpty name="passObject" property="actualStartDate">
			<logic:notEmpty name="passObject" property="actualEndDate">
				<logic:notEmpty name="passObject" property="actualBenefitHours">
					<bean:message bundle='wx' key='wx.task.detail.start.date.actual' />：<font color="#C30"><bean:write name="passObject" property="actualStartDate"/></font><br/>
					<bean:message bundle='wx' key='wx.task.detail.end.date.actual' />：<font color="#C30"><bean:write name="passObject" property="actualEndDate"/></font><br/>
					<bean:message bundle='wx' key='wx.task.detail.estimate.benefit.hours.actual' />：<bean:write name="passObject" property="actualBenefitHours"/><br/>
				</logic:notEmpty>
			</logic:notEmpty>
		</logic:notEmpty>
	  	<bean:message bundle='wx' key='wx.task.detail.description' />：<bean:write name="passObject" property="description" /><br/>
	  </logic:equal>
	  <!-- 加班 -->
	  <logic:equal value="502" name="workflowActualVO" property="systemModuleId">
	  	<%
	  		if(inHouse&&"zh".equalsIgnoreCase(request.getLocale().getLanguage())){
	  		   out.print("员工Id");
	  		}else if(!inHouse&&"zh".equalsIgnoreCase(request.getLocale().getLanguage())){
	  		   out.print("雇员ID");
	  		}else{
	  		   out.print("EmployeeId");
	  		}
	  	%>
	  		：<bean:write name="passObject" property="employeeId"/><br/>
		<%
	  		if(inHouse&&"zh".equalsIgnoreCase(request.getLocale().getLanguage())){
	  		   out.print("员工姓名");
	  		}else if(!inHouse&&"zh".equalsIgnoreCase(request.getLocale().getLanguage())){
	  		   out.print("雇员姓名");
	  		}else{
	  		   out.print("EmployeeName");
	  		}
	  	%>
			：<font color="#C30"><bean:write name="passObject" property="employeeName"/></font><br/>
		<%
	  		if(inHouse&&"zh".equalsIgnoreCase(request.getLocale().getLanguage())){
	  		   out.print("员工编号");
	  		}else if(!inHouse&&"zh".equalsIgnoreCase(request.getLocale().getLanguage())){
	  		   out.print("雇员编号");
	  		}else{
	  		   out.print("EmployeeNO");
	  		}
	  	%>
			：<bean:write name="passObject" property="employeeNo"/><br/>
		<bean:message bundle='wx' key='wx.task.detail.employee.certificate' />：<bean:write name="passObject" property="certificateNumber"/><br/>
		<bean:message bundle='wx' key='wx.task.detail.item.type' />：<font color="#C30"><bean:write name="passObject" property="decodeItemId" /></font><br/>
		<bean:message bundle='wx' key='wx.task.detail.start.date' />：<font color="#C30"><bean:write name="passObject" property="estimateStartDate"/></font><br/>
		<bean:message bundle='wx' key='wx.task.detail.end.date' />：<font color="#C30"><bean:write name="passObject" property="estimateEndDate"/></font><br/>
		<bean:message bundle='wx' key='wx.task.detail.estimate.hours' />：<bean:write name="passObject" property="estimateHours"/><br/>
		<bean:message bundle='wx' key='wx.task.detail.description' />：<bean:write name="passObject" property="description"/><br/>
	  </logic:equal>
	  <br /></div>
	  <div class="neirong3"><span><bean:message bundle='wx' key='wx.task.detail.reject.reason' />：</span>
	  		<html:textarea property="description" styleClass="shurukuang3" />
	  </div>
	  <!-- 未开始    黄色 -->
	  <logic:equal value="1" name="workflowActualStepsForm" property="status">
	  	<a href="#" class="button orange" onclick="btnReturn();"><bean:message bundle='wx' key='wx.task.detail.back' /></a>
	  </logic:equal>
	  <!-- 带操作  蓝色-->	 
	  <logic:equal value="2" name="workflowActualStepsForm" property="status">	  
		<a href="#" class="button orange" onclick="btnClick(3);"><bean:message bundle='wx' key='wx.task.detail.confirm' /></a>
		<a href="#" class="button gray" onclick="btnClick(4);"><bean:message bundle='wx' key='wx.task.detail.reject' /></a>
	  </logic:equal>
	  <!-- 同意    绿色 -->
	  <logic:equal value="3" name="workflowActualStepsForm" property="status">
	  	<a href="#" class="button orange" onclick="btnReturn();"><bean:message bundle='wx' key='wx.task.detail.back' /></a>
	  </logic:equal>
	  <!-- 拒绝    红色 -->
	  <logic:equal value="4" name="workflowActualStepsForm" property="status">
	  	<a href="#" class="button orange" onclick="btnReturn();"><bean:message bundle='wx' key='wx.task.detail.back' /></a>
	  </logic:equal>
<br /><br />
</html:form>
</div>
</body>
<script type="text/javascript">
	(function($) {	
	})(jQuery);
	function btnClick(status){
		$("#status").val(status);
		var key=(status==3?"<bean:message bundle='wx' key='wx.task.detail.confirm' />":"<bean:message bundle='wx' key='wx.task.detail.reject' />");
		if(confirm(key+"?")){
			$(".actualSteps_form").submit();
		}else{
			return false;
		}
	}
	function btnReturn(){
		link('workflowActualAction.do?proc=list_object_unfinished_mobile');
	}
</script>

