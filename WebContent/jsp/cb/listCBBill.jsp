<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.util.KANUtil"%>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) + " - " + KANUtil.getProperty( request.getLocale(), "cb.bill" ) );%>
<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" value="${title}" />
  <tiles:put name="body" value="/contents/cb/listCBBillBody.jsp" />
</tiles:insert>