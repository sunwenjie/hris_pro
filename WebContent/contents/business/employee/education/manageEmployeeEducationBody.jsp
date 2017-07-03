<%@page import="com.kan.hro.domain.biz.employee.EmployeeEducationVO"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="com.kan.base.web.renders.util.ManageRender"%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_EDUCATION", "employeeEducationForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {	
		// JS of the List
		<% 
		final String checkMajor = "if($('#major').val()=='输入关键字查看提示...'){$('#major').val('');}if($('#schoolName').val()=='输入关键字查看提示...'){$('#schoolName').val('');}";
		%>
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_EDUCATION", null, null,null, checkMajor ) %>
		
		$("#btnList").val('<bean:message bundle="public" key="button.back.fh" />');
		//重新绑定取消按钮事件
		 $('#btnList').unbind( "click" );
		 $('#btnList').click(function() {
			 <%
			 final EmployeeEducationVO employeeEducationVO = (EmployeeEducationVO)request.getAttribute("employeeEducationForm");
			 final String employeeId = employeeEducationVO.getEmployeeId();
			 final String targateId = employeeEducationVO.encodedField(employeeId);
			 %>
	        link('employeeAction.do?proc=to_objectModify&id=<%=targateId%>');
	     });
		 	kanThinking_column_ajax('schoolName',null, 'employeeEducationAction.do?proc=list_schoolName_json');
		    kanThinking_column_ajax('major', null, 'employeeEducationAction.do?proc=list_major_json');
		  //用来显示雇员姓名和id的链接 
			 $('.required').parent().after(
					 '<ol class="auto">'
					 +'<li id="employeeIdLI" style="width: 50%;"><label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal></label> <a href="javascript:void(0)" onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name='employeeEducationForm' property='encodedEmployeeId' />\');"  ><bean:write name="employeeEducationForm" property="employeeId"  /></a></li>'
					 +'<li id="employeeNameLI" style="width: 50%;"><label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name" /></logic:equal></label> <a href="javascript:void(0)" onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name='employeeEducationForm' property='encodedEmployeeId' />\');" ><bean:write name="employeeEducationForm" property="employeeName" /></a></li>'
					 +'</ol>'
			 );		
	})(jQuery);
</script>

