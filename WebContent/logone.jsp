<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<% request.setAttribute("title", "KAN - 员工自助管理系统" );%>

<tiles:insert definition="com.kan.hro.logon" flush="true">
	<tiles:put name="title" value="${title} - Login" />
	<tiles:put name="body" value="/contents/logon_employeeBody.jsp" />
	<%
		BaseAction.removeObjectFromClient( request, response, "KANEmployeeUser" );
		BaseAction.removeObjectFromClient( request, response, "__COOKIE_EMPLOYEE_USER_JSON" );
	%>
</tiles:insert>