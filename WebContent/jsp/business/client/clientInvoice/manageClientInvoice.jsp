<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) );%>

<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" value="${title} - 客户 - 管理客户账单信息" />
  <tiles:put name="body" value="/contents/business/client/clientInvoice/manageClientInvoiceBody.jsp" />
</tiles:insert>