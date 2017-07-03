<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ page import="com.kan.base.util.KANUtil"%>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) + " - " + KANUtil.getProperty( request.getLocale(), "payment.payslip.list" ) );%>
<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" value="${title}" />
  <tiles:put name="body" value="/contents/payment/payslip/listPayslipViewBody.jsp" />
</tiles:insert>