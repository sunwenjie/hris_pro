<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<% request.setAttribute("title", "KAN - 供应商管理系统" );%>

<tiles:insert definition="com.kan.hro.logon" flush="true">
	<tiles:put name="title" value="${title} - Login" />
	<tiles:put name="body" value="/contents/logon_inVendorBody.jsp" />
	<%
		BaseAction.removeObjectFromClient( request, response, "KANUser" );
		BaseAction.removeObjectFromClient( request, response, "KANSecurity" );
	%>
</tiles:insert>