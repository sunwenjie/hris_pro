<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) );%>
<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" value="${title} - 社保公积金 - 申报调整导入" />
  <tiles:put name="body" value="/contents/settlement/adjustment/detail/listSettlementAdjustmentImportDetailBody.jsp" />
</tiles:insert>