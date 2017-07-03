<%@ page pageEncoding="GB2312"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) );%>
<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" value="${title} - 系统发票 - 合并" />
  <tiles:put name="body" value="/contents/finance/invoice/listCompoundInvoiceBody.jsp" />
</tiles:insert>