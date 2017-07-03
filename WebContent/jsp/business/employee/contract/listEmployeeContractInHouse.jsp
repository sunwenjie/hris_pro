<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.action.BaseAction" %>
<%@ page import="com.kan.base.util.KANUtil"%>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) + " - " + KANUtil.getProperty( request.getLocale(), "employee.contract.list" ) );%>
<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" type="String">${title}</tiles:put>
  <tiles:put name="body" value="/contents/business/employee/contract/listEmployeeContractBodyInHouse.jsp" />
</tiles:insert>