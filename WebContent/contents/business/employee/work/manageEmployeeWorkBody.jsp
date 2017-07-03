<%@page import="com.kan.hro.domain.biz.employee.EmployeeWorkVO"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="com.kan.base.web.renders.util.ManageRender"%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_WORK", "employeeWorkForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {	
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_WORK", null, null, null ) %>
		
		$("#btnList").val('<bean:message bundle="public" key="button.back.fh" />');
		//重新绑定取消按钮事件
		 $('#btnList').unbind( "click" );
		 $('#btnList').click(function() {
			 <%
			 final EmployeeWorkVO employeeWorkVO = (EmployeeWorkVO)request.getAttribute("employeeWorkForm");
			 final String employeeId = employeeWorkVO.getEmployeeId();
			 final String targateId = employeeWorkVO.encodedField(employeeId);
			 %>
	        link('employeeAction.do?proc=to_objectModify&id=<%=targateId%>');
	     });
		 
		 
		 //用来显示雇员姓名和id的链接 
		 $('.required').parent().after(
				 '<ol class="auto">'
				 +'<li id="employeeIdLI" style="width: 50%;"><label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal><em> *</em></label> <a href="javascript:void(0)" onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name='employeeWorkForm' property='encodedEmployeeId' />\');"  ><bean:write name="employeeWorkForm" property="employeeId"  /></a></li>'
				 +'<li id="employeeNameLI" style="width: 50%;"><label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name" /></logic:equal><em> *</em></label> <a href="javascript:void(0)" onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name='employeeWorkForm' property='encodedEmployeeId' />\');" ><bean:write name="employeeWorkForm" property="employeeName" /></a></li>'
				 +'</ol>'
		 );		 
		 kanThinking_column_ajax("companyName", "", "employeeWorkAction.do?proc=list_companyName_json", null, null);
		 
	})(jQuery);
</script>