<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ page import="com.kan.base.util.KANUtil"%>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) + " - " + KANUtil.getProperty( request.getLocale(), "security.entity.list" ) );%>
<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" value="${title}"></tiles:put>
  <tiles:put name="body" value="/contents/security/entity/listEntityBody.jsp" />
</tiles:insert>