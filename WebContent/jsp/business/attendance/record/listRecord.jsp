<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ page import="com.kan.base.web.action.BaseAction" %>

<% request.setAttribute("title", BaseAction.getTitle( request, response ) );%>

<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" type="String">${title} - ´ò¿¨¼ÇÂ¼</tiles:put>
  <tiles:put name="body" value="/contents/business/attendance/record/listRecordBody.jsp" />
</tiles:insert>