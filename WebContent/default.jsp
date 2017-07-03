<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) );%>
<tiles:insert definition="com.kan.hro.logon" flush="true">
	<tiles:put name="title" value="${title} - Ê×Ò³" />
	<tiles:put name="body" value="/contents/defaultPage.jsp" />
</tiles:insert>