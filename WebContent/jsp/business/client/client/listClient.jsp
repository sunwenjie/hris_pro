<%@ page pageEncoding="GBK"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) );%>

<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" value='${title} - 客户列表' />
  <tiles:put name="body" value="/contents/business/client/client/listClientBody.jsp" />
</tiles:insert>