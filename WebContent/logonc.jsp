<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.util.KANConstants" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<% request.setAttribute("title", KANConstants.PRODUCT_NAME_HRS );%>

<tiles:insert definition="com.kan.hro.logon" flush="true">
	<tiles:put name="title" value="${title} - Login" />
	<tiles:put name="body" value="/contents/logon_inClientBody.jsp" />
	<%
		BaseAction.removeObjectFromClient( request, response, "KANUser" );
		BaseAction.removeObjectFromClient( request, response, "KANSecurity" );
	%>
</tiles:insert>