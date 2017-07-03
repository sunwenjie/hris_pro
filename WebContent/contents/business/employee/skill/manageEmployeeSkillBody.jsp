<%@page import="com.kan.hro.domain.biz.employee.EmployeeSkillVO"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="com.kan.base.web.renders.util.ManageRender"%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_SKILL", "employeeSkillForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {	
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_SKILL", null, null, null ) %>
		
		//重新绑定取消按钮事件
		 $('#btnCancel').unbind( "click" );
		 $('#btnCancel').click(function() {
			 <%
			 final EmployeeSkillVO employeeSkillVO = (EmployeeSkillVO)request.getAttribute("employeeSkillForm");
			 final String employeeId = employeeSkillVO.getEmployeeId();
			 final String targateId = employeeSkillVO.encodedField(employeeId);
			 %>
	        link('employeeAction.do?proc=to_objectModify&id=<%=targateId%>');
	     });
		 
		//用来显示雇员姓名和id的链接 
		 $('.required').parent().after(
				 '<ol class="auto">'
				 +'<li id="employeeIdLI" style="width: 50%;"><label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal><em> *</em></label> <a href="javascript:void(0)" onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name='employeeSkillForm' property='encodedEmployeeId' />\');"  ><bean:write name="employeeSkillForm" property="employeeId"  /></a></li>'
				 +'<li id="employeeNameLI" style="width: 50%;"><label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name" /></logic:equal><em> *</em></label> <a href="javascript:void(0)" onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name='employeeSkillForm' property='encodedEmployeeId' />\');" ><bean:write name="employeeSkillForm" property="employeeName" /></a></li>'
				 +'</ol>'
		 );		
	})(jQuery);
</script>

