<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) );%>

<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" value="${title} - ¶©µ¥ÁÐ±í" />
  <tiles:put name="body" value="/contents/business/client/clientOrder/listClientOrderHeaderBody.jsp" />
</tiles:insert>