<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.hro.domain.biz.employee.EmployeeEmergencyVO"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="com.kan.base.web.renders.util.ManageRender"%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_EMERGENCY", "employeeEmergencyForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {	
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_EMERGENCY", null, null, null ) %>
		
		$("#btnList").val('<bean:message bundle="public" key="button.back.fh" />');
		$('#btnList').click(function() {
			<%
			 final EmployeeEmergencyVO employeeEmergencyVO = (EmployeeEmergencyVO)request.getAttribute("employeeEmergencyForm");
			 final String employeeId = employeeEmergencyVO.getEmployeeId();
			 final String targateId = employeeEmergencyVO.encodedField(employeeId);
			 %>
	        link('employeeAction.do?proc=to_objectModify&id=<%=targateId%>');
		});
		 
		  //用来显示雇员姓名和id的链接 
		 $('.required').parent().after(
				 '<ol class="auto">'
				 +'<li id="employeeIdLI" style="width: 50%;"><label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal></label> <a href="javascript:void(0)" onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name='employeeEmergencyForm' property='encodedEmployeeId' />\');"  ><bean:write name="employeeEmergencyForm" property="employeeId"  /></a></li>'
				 +'<li id="employeeNameLI" style="width: 50%;"><label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name" /></logic:equal></label> <a href="javascript:void(0)" onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name='employeeEmergencyForm' property='encodedEmployeeId' />\');" ><bean:write name="employeeEmergencyForm" property="employeeName" /></a></li>'
				 +'</ol>'
		 );		
		
		//修改HRM显示文字
		<%
			if( request.getLocale().getLanguage().equalsIgnoreCase("zh")){
		%>
			$("#relationshipIdLI label").html("<%="1".equals(BaseAction.getRole(request,null))?"与雇员关系":"与员工关系"%>");
		
		<%
			}
		%>
	})(jQuery);
	
</script>

