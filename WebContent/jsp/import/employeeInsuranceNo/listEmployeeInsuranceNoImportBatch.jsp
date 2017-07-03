<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) );%>

<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" type="String"><%=BaseAction.getTitle( request, response )%> - 保险账号导入批次</tiles:put>
  <tiles:put name="body" value="/contents/import/employeeInsuranceNo/listEmployeeInsuranceNoImportBatchBody.jsp" />
</tiles:insert>